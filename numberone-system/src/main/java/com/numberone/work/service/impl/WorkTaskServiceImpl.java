package com.numberone.work.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.common.exception.BusinessException;
import com.numberone.common.support.Convert;
import com.numberone.common.utils.DateUtils;
import com.numberone.common.utils.MailUtils;
import com.numberone.common.utils.StringUtils;
import com.numberone.common.utils.Threads;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.system.domain.SysDictData;
import com.numberone.system.domain.SysFileBean;
import com.numberone.system.domain.SysUser;
import com.numberone.system.mapper.SysFileMapper;
import com.numberone.system.mapper.SysUserMapper;
import com.numberone.system.service.ISysDictDataService;
import com.numberone.work.domain.WorkTask;
import com.numberone.work.domain.WorkTaskAudit;
import com.numberone.work.domain.WorkTaskPerform;
import com.numberone.work.mapper.WorkTaskAuditMapper;
import com.numberone.work.mapper.WorkTaskMapper;
import com.numberone.work.mapper.WorkTaskPerformMapper;
import com.numberone.work.service.IWorkTaskService;

/**
 * 任务分配，执行，审核 服务层实现
 * 
 * @author eason
 * @date 2020-04-11
 */
@Service
public class WorkTaskServiceImpl implements IWorkTaskService 
{
	@Autowired
	private WorkTaskMapper workTaskMapper;
	@Autowired
	private WorkTaskPerformMapper workTaskPerformMapper;
	@Autowired
	private WorkTaskAuditMapper workTaskAuditMapper;
    @Autowired
    private SysFileMapper fileMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private ISysDictDataService dictDataService;
    
	/**
     * 查询任务分配，执行，审核信息
     * 
     * @param taskRecordId 任务分配，执行，审核ID
     * @return 任务分配，执行，审核信息
     */
    @Override
	public WorkTask selectWorkTaskById(String taskRecordId)
	{
	    return workTaskMapper.selectWorkTaskById(taskRecordId);
	}
	
	/**
     * 查询任务分配，执行，审核列表
     * 
     * @param workTask 任务分配，执行，审核信息
     * @return 任务分配，执行，审核集合
     */
	@Override
	public List<WorkTask> selectWorkTaskList(WorkTask workTask)
	{
	    return workTaskMapper.selectWorkTaskList(workTask);
	}
	
    /**
     * 新增任务分配，执行，审核
     * 
     * @param workTask 任务分配，执行，审核信息
     * @return 结果
     */
	@Override
	public int insertWorkTask(WorkTask workTask)
	{
	    return workTaskMapper.insertWorkTask(workTask);
	}
	
	/**
     * 修改任务分配，执行，审核
     * 
     * @param workTask 任务分配，执行，审核信息
     * @return 结果
     */
	@Override
	public int updateWorkTask(WorkTask workTask)
	{
		if(selectTaskNoEditByIds(workTask.getTaskRecordId())>0){
			throw new BusinessException("只能编辑未处理的任务");
		}
		SysFileBean sysFile = workTask.getAppendix();
    	if(sysFile!=null && !StringUtils.isEmpty(sysFile.getFilePath())){
	    	if(!StringUtils.isEmpty(sysFile.getFileId())){//没有修改附件
	    		//修改文件
	    		fileMapper.updateSysFile(sysFile);
	    		
	    	}else{
	    		sysFile.setFileId(StringUtils.getUUID());
	    		//新增文件
	    		fileMapper.insertSysFile(sysFile);
	    		workTask.setTaskAppendix(sysFile.getFileId());
	    		
	    	}
    	}
    	
    	int row = workTaskMapper.updateWorkTask(workTask);
	    return row;
	    
	}
	public int selectTaskNoEditByIds(String ids){
		return workTaskMapper.selectTaskNoEditByIds(Convert.toStrArray(ids));
	}
	/**
     * 删除任务分配，执行，审核对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteWorkTaskByIds(String ids)
	{
		if(selectTaskNoEditByIds(ids)>0){
			throw new BusinessException("只能删除未处理的任务");
		}
		//首先删除相关执行表
		int row = workTaskPerformMapper.deletePerformByTaskRecordIds(Convert.toStrArray(ids));
		row += workTaskMapper.deleteWorkTaskByIds(Convert.toStrArray(ids));
		return row;
	}

	/**
	 * 任务分配【200422 代码重构 应当在任务分配时就加上任务执行信息】
	 * @param workTask
	 * @param sysUser
	 * @param taskSequence
	 * @return
	 */
	@Override
	public int launchAllocate(WorkTask workTask,SysUser sysUser,int taskSequence) {
		//1、处理文件
		SysFileBean appendix = workTask.getAppendix();
    	if(appendix!=null && !StringUtils.isEmpty(appendix.getFilePath())){
    		appendix.setFileId(StringUtils.getUUID());
    		//新增文件
    		fileMapper.insertSysFile(appendix);
    		workTask.setTaskAppendix(appendix.getFileId());
    	}
    	
    	//2、新增任务记录
    	//补全数据
    	/** 记录id */
    	String taskRecordId = StringUtils.getUUID();
    	workTask.setTaskRecordId(taskRecordId);
    	/** 任务号：task_id组成 TS+年月日+四位数序号 */
    	String taskId = StringUtils.genTaskId(taskSequence);
    	workTask.setTaskId(taskId);
    	/** 任务标题 */
    	/** 任务状态：task_flag待处理1/处理中2/处理完成3/已审核4/返工5 */
    	workTask.setTaskFlag(1);
    	/** 任务类型：task_type需求设计0/需求开发1/需求测试2/缺陷修复3/其他4 */
    	/** 难易程度：diff_level 简单任务（0.7/0.8/0.9）/一般难度（1.0）/较高难度（1.1）/次高难度（1.2）/最高难度（1.3）  */
    	/** 优先级：priority重要且紧急1/重要2/紧急3/普通4/基金相关 */
    	/** 计划开始时间：plan_start_date 年月日 */
    	/** 计划完成时间 */
    	/** 预计工时 */
    	/** 处理工时 */
    	/** 审核工时 */
    	/** 任务描述 */
    	/** 任务说明附件 文件id*/
    	/** 创建人用户id */
    	workTask.setCreateUserId(sysUser.getUserId());
    	/** 创建人工号 */
    	workTask.setCreateEmpId(sysUser.getEmpId());
    	/** 创建人姓名 */
    	workTask.setCreateUserName(sysUser.getUserName());
    	/** 创建时间 精确到时分秒 */
    	workTask.setCreateTime(DateUtils.getNowDate());
    	SysUser dealUser = userMapper.selectUserById(workTask.getDealUserId());
    	/** 处理部门id */
    	workTask.setDealDeptId(dealUser.getDeptId());
    	/** 处理部门名 */
    	workTask.setDealDeptName(dealUser.getDeptName());
    	/** 处理人用户id */
    	workTask.setDealUserId(dealUser.getUserId());
    	/** 处理人工号 */
    	workTask.setDealEmpId(dealUser.getEmpId());
    	/** 处理人姓名 */
    	workTask.setDealUserName(dealUser.getUserName());
    	/** 处理时间 */
    	/** 审核人用户id */
    	/** 审核人工号 */
    	/** 审核人姓名 */
    	/** 审核时间 */
    	/** 删除标志 0正常/1删除 */
    	workTask.setDelFlag(0);
    	int row = 0;
    		//3、新增任务执行表
    		WorkTaskPerform perform = new WorkTaskPerform();
    		/** 任务执行表id */
    		String taskPerformId = StringUtils.getUUID();
    		perform.setTaskPerformId(taskPerformId);
    		/** 任务记录id */
    		perform.setTaskRecordId(taskRecordId);
    		/** 任务号 */
    		perform.setTaskId(taskId);
    		/** 任务执行人用户id */
    		perform.setPerformUserId(dealUser.getUserId());
    		/** 任务执行人工号 */
    		perform.setPerformEmpId(dealUser.getEmpId());
    		/** 任务执行时间 */
    		/** 执行人部门id */
    		perform.setDealDeptId(dealUser.getDeptId());
    		/** 执行人部门名 */
    		perform.setDealDeptName(dealUser.getDeptName());
    		/** 变更执行人用户id */
    		/** 变更执行人工号 */
    		/** 变更时间 */
    		/** 处理说明 */
    		/** 任务状态 待处理1/处理中2/处理完成3/已审核4/返工5 */
    		perform.setTaskFlag(1);
    		/** 执行人变更标志 正常0/已变更1，变更前需审核，审核后作废 */
    		perform.setAlterPerformFlag(0);
    		/** 删除标志 0正常/1删除 */
    		perform.setDelFlag(0);
    		/** 工作提交物 */
    		/** 备注 */
    	/** 任务执行表id */
    	workTask.setTaskPerformId(taskPerformId);
    	//更新任务记录到数据库
    	row += workTaskMapper.insertWorkTask(workTask);
    	//新增任务执行记录到数据库
    	row += workTaskPerformMapper.insertWorkTaskPerform(perform);
    	//发送任务通知邮件
    	if(row>0){
    		new Thread(()->{
    			Map<String,String> map = new HashMap<String,String>();
    			BeanUtils.copyProperties(map, workTask);
    			map.put("receAddr", dealUser.getEmail());
    			map.put("appendixAddr", appendix.getFilePath());
    			map.put("originalFileName", appendix.getOriginalFileName());
    			List<SysDictData> list = dictDataService.selectDictDataByType("work_task_type");
    			for (SysDictData sysDictData : list) {
					if(sysDictData.getDictValue().equals(workTask.getTaskType().toString())){
						map.put("taskType", sysDictData.getDictLabel());
					}
				}
    			Threads.sleep(10);
    			try {
					MailUtils.sendTaskMail(map);
					System.out.println("成功发送邮件");
				} catch (Exception e) {
					e.printStackTrace();
					throw new BusinessException("邮件发送失败！");
				}
    		}).start();
    	}
		return row;
	}

	/**
	 * 查询当前用户已分配未完成任务
	 * @param userId
	 * @return
	 */
	@Override
	public List<WorkTask> selectNoCompleteByUserId(Long userId) {
		return workTaskMapper.selectNoCompleteByUserId(userId);
	}

	@Override
	public List<WorkTask> selectListByDeptId(WorkTask workTask, SysUser user) {
		Map<String, Object> params = workTask.getParams();
		if(params!=null && params.get("userIds")!=null && params.get("userIds").hashCode()!=0){
			params.put("userIds", Convert.toLongArray(params.get("userIds").toString()));
		}else{
			params.put("userIds",null);
		}
		if("admin".equals(user.getLoginName())){
			return workTaskMapper.selectWorkTaskList(workTask);
		}
		//总经理和hr也能查询全部
		String rolesName = user.getRolesName();
		if(rolesName.contains("hr") || rolesName.contains("ceo")){
			return workTaskMapper.selectWorkTaskList(workTask);
		}
		return workTaskMapper.selectListByDeptId(workTask,user.getDeptId());
	}

	@Override
	public List<WorkTask> selectListOfMine(WorkTask workTask, SysUser sysUser) {
		return workTaskMapper.selectListOfMine(workTask,sysUser.getUserId());
	}

	@Override
	public int savePerform(WorkTaskPerform perform, SysUser sysUser) {
		int row = 0;
    	SysFileBean appendix = perform.getAppendix();
    	WorkTaskPerform taskPerform = workTaskPerformMapper.selectWorkTaskPerformById(perform.getTaskPerformId());
    	//防止非法操作
    	if(taskPerform==null) throw new BusinessException("不允许修改");
    	if(appendix!=null && !StringUtils.isEmpty(appendix.getFilePath())){
	    	if(!StringUtils.isEmpty(appendix.getFileId())){//没有修改附件
	    		//修改文件
	    		//fileMapper.updateSysFile(appendix);
	    		
	    	}else{
	    		appendix.setFileId(StringUtils.getUUID());
	    		//新增文件
	    		fileMapper.insertSysFile(appendix);
	    		taskPerform.setPerformAppendix(appendix.getFileId());
	    	}
    	}
		//1、判读任务执行id是否为空
		if(perform!=null && !StringUtils.isEmpty(perform.getTaskPerformId())){
			//3、否则更新
			//补全数据
			/** 任务执行表id */
			taskPerform.setTaskPerformId(perform.getTaskPerformId());
			/** 处理说明 */
			taskPerform.setDealDesc(perform.getDealDesc());
			/** 任务状态 待处理1/处理中2/处理完成3/已审核4/返工5 */
			taskPerform.setTaskFlag(2);
			//更新到数据库
			row += workTaskPerformMapper.updateWorkTaskPerform(taskPerform);
			
			//更新任务状态
			WorkTask task = new WorkTask();
			task.setTaskFlag(2);
			task.setTaskRecordId(taskPerform.getTaskRecordId());
			row += workTaskMapper.updateWorkTask(task);
		}
		
		return row;
	}

	@Override
	public WorkTaskPerform selectPerformByTaskRecordId(String taskRecordId) {
		return workTaskPerformMapper.selectPerformByTaskRecordId(taskRecordId);
	}

	@Override
	public int resolved(WorkTask workTask, SysUser sysUser) {
		int row = 0 ;
		Date currTime = DateUtils.getNowDate();
		//1、根据任务记录id查询任务执行信息
		String taskRecordId = workTask.getTaskRecordId();
		WorkTaskPerform taskPerform = workTaskPerformMapper.selectPerformByTaskRecordId(taskRecordId);
		//2、更新任务执行信息
			/** 任务执行时间 */
			taskPerform.setPerformTime(currTime);
			/** 任务状态 待处理1/处理中2/处理完成3/已审核4/返工5 */
			taskPerform.setTaskFlag(3);
			//更新数据库
			row += workTaskPerformMapper.updateWorkTaskPerform(taskPerform);
		//3、更新任务信息
			//查询相应的任务信息
			WorkTask newWorkTask = workTaskMapper.selectWorkTaskById(taskRecordId);
	    	/** 任务状态：task_flag待处理1/处理中2/处理完成3/已审核4/返工5 */
			newWorkTask.setTaskFlag(3);
	    	/** 处理工时 */
			newWorkTask.setDealHours(workTask.getDealHours());
			/** 实际开始时间：plan_start_date 年月日 */
			newWorkTask.setRealStartDate(workTask.getRealStartDate());
			/** 实际完成时间 */
			newWorkTask.setRealEndDate(workTask.getRealEndDate());
	    	/** 处理时间 */
			newWorkTask.setDealTime(currTime);
	    	/** 审核人用户id */
			newWorkTask.setAuditUserId(newWorkTask.getCreateUserId());
	    	/** 审核人工号 */
			newWorkTask.setAuditEmpId(newWorkTask.getCreateEmpId());
	    	/** 审核人姓名 */
			newWorkTask.setAuditUserName(newWorkTask.getCreateUserName());
	    	/** 审核时间 */
			/** 任务审核id */
			String taskAuditId = StringUtils.getUUID();
			newWorkTask.setTaskAuditId(taskAuditId);
			//更新数据库
			row += workTaskMapper.updateWorkTask(newWorkTask);
		//4、发起审批 给任务创建人审批
			//创建任务审批信息
			WorkTaskAudit audit = new WorkTaskAudit();
			
			//查询创建人
			SysUser createUser = userMapper.selectUserById(newWorkTask.getCreateUserId());
			//补全信息
			/** 任务审核id */
			audit.setTaskAuditId(taskAuditId);
			/** 任务记录id */
			audit.setTaskRecordId(newWorkTask.getTaskRecordId());
			/** 任务号 */
			audit.setTaskId(newWorkTask.getTaskId());
			/** 任务执行表id */
			audit.setTaskPerformId(newWorkTask.getTaskPerformId());
			/** 审核类型 任务提交审核submit 1/任务变更审核alter 2*/
			audit.setAuditType(1);
			/** 发起人用户id */
			audit.setLaunchUserId(newWorkTask.getDealUserId());
			/** 发起人工号 */
			audit.setLaunchEmpId(newWorkTask.getDealEmpId());
			/** 发起人用户名 */
			audit.setLaunchUserName(newWorkTask.getDealUserName());
			/** 发起时间 */
			audit.setLaunchTime(newWorkTask.getDealTime());
			/** 审核人用户id */
			audit.setAuditUserId(createUser.getUserId());
			/** 审核人用户名 */
			audit.setAuditUserName(createUser.getUserName());
			/** 审核人工号 */
			audit.setAuditEmpId(createUser.getEmpId());
			/** 审核人职位 */
			audit.setAuditJob(createUser.getRolesName());
			/** 审核时间 */
			/** 审核状态 0未审核 1审核中 2审核不通过 3审核已通过 */
			audit.setAuditFlag(0);
			/** 备注 */
			
			//更新数据库
			row += workTaskAuditMapper.insertWorkTaskAudit(audit);
		return row;
	}

	@Override
	public int alter(WorkTaskPerform perform, SysUser sysUser) {
		int row = 0;
		//当前时间
		Date currTime = DateUtils.getNowDate();
		//1、根据任务记录id查询旧表数据
		String taskRecordId = perform.getTaskRecordId();
		//2、查询变更用户信息
			SysUser alterUser = userMapper.selectUserById(perform.getAlterUserId());
			if(alterUser.getUserId().equals(sysUser.getUserId())){
				throw new BusinessException("变更前后执行人为同一人");
			}
			//可能存在的非法操作
			WorkTaskPerform oldPerform = workTaskPerformMapper.selectWorkTaskPerformById(perform.getTaskPerformId());
			if(oldPerform==null){
				throw new BusinessException("无法更改");
			}

		//3、更新旧表数据
			/** 变更执行人用户id */
			oldPerform.setAlterUserId(alterUser.getUserId());
			/** 变更执行人工号 */
			oldPerform.setAlterEmpId(alterUser.getEmpId());
			/** 变更时间 */
			oldPerform.setAlterTime(currTime);
			/** 变更备注 */
			oldPerform.setRemark(perform.getRemark());
			/** 执行人变更标志 正常0/已变更1，变更前需审核，审核后作废 */
			oldPerform.setAlterPerformFlag(1);
			/** 任务状态 待处理1/处理中2/处理完成3/已审核4/返工5/变更审核中6*/
			oldPerform.setTaskFlag(6);
			/** 变更备注 */
			oldPerform.setDelFlag(0);
			//更新
			row += workTaskPerformMapper.updateWorkTaskPerform(oldPerform);
		//4、更新任务状态 等候变更审核完成 此处不需要更新审核人信息
			WorkTask workTask = new WorkTask();
			workTask.setTaskRecordId(taskRecordId);
			workTask.setTaskFlag(6);
			row += workTaskMapper.updateWorkTask(workTask);
			
		//5、新增任务变更审核信息 发起审核
			//查询任务信息
			workTask = workTaskMapper.selectWorkTaskById(taskRecordId);
			//查询创建人信息
			SysUser createUser = userMapper.selectUserById(workTask.getCreateUserId());
			WorkTaskAudit audit = new WorkTaskAudit();
			/** 任务审核id */
			audit.setTaskAuditId(StringUtils.getUUID());
			/** 任务记录id */
			audit.setTaskRecordId(workTask.getTaskRecordId());
			/** 任务号 */
			audit.setTaskId(workTask.getTaskId());
			/** 任务执行表id */
			audit.setTaskPerformId(oldPerform.getTaskPerformId());
			/** 审核类型 任务提交审核submit 1/任务变更审核alter 2*/
			audit.setAuditType(2);
			/** 发起人用户id */
			audit.setLaunchUserId(sysUser.getUserId());
			/** 发起人用户名 */
			audit.setLaunchUserName(sysUser.getUserName());
			/** 发起人工号 */
			audit.setLaunchEmpId(sysUser.getEmpId());
			/** 发起时间 */
			audit.setLaunchTime(currTime);
			/** 审核人用户id */
			audit.setAuditUserId(createUser.getUserId());
			/** 审核人用户名 */
			audit.setAuditUserName(createUser.getUserName());
			/** 审核人工号 */
			audit.setAuditEmpId(createUser.getEmpId());
			/** 审核人job */
			audit.setAuditJob(createUser.getRolesName());
			/** 审核时间 */
			/** 审核状态 0未审核 1审核中 2审核不通过 3审核已通过 */
			audit.setAuditFlag(0);
			/** 备注 */
			//更新数据库
			row += workTaskAuditMapper.insertWorkTaskAudit(audit);
		return row;
	}

	@Override
	public WorkTask selectFlag1And2And5(String taskRecordId) {
		return workTaskMapper.selectFlag1And2And5(taskRecordId);
	}
	
	@Override
	public List<WorkTaskAudit> selectAuditList(WorkTaskAudit audit) {
		Map<String, Object> params = audit.getParams();
		if(params!=null && params.get("userIds")!=null && params.get("userIds").hashCode()!=0){
			params.put("userIds", Convert.toLongArray(params.get("userIds").toString()));
		}else{
			params.put("userIds",null);
		}
		
		if(!"1".equals(params.get("auditStatus")) && !"0".equals(params.get("auditStatus"))){
			params.put("auditStatus", "0");
		}
		return workTaskAuditMapper.selectWorkTaskAuditList(audit);
	}

	@Override
	public Map<String,String> selectPerformBeforeAlter(String taskRecordId) {
		return workTaskPerformMapper.selectPerformBeforeAlter(taskRecordId);
	}

	@Override
	public int batchSubmitAuditRefuse(WorkTaskAudit audit, SysUser sysUser, List<String> submitRecordIds) {
		//更新任务表信息 更新审核表信息 更新执行表信息
		Date currDate = DateUtils.getNowDate();
		int row = 0;
		
		//驳回是可以批量的
		for (String taskRecordId : submitRecordIds) {
		
		//1、根据任务记录id查询任务
			WorkTask task = workTaskMapper.selectWorkTaskById(taskRecordId);
		//2、查询任务提交审核单
			String taskAuditId = task.getTaskAuditId();
			WorkTaskAudit taskAudit = workTaskAuditMapper.selectWorkTaskAuditById(taskAuditId);
			if(taskAudit.getAuditUserId()!=sysUser.getUserId()){
				throw new BusinessException("非法操作");
			}
		//3、查询任务执行单
			String taskPerformId = task.getTaskPerformId();
			WorkTaskPerform perform = workTaskPerformMapper.selectWorkTaskPerformById(taskPerformId);
		
		//4、更新审核信息
			/** 审核状态 0未审核 1审核中 2审核不通过 3审核已通过 */
			taskAudit.setAuditFlag(2);
			/** 审核时间 */
			taskAudit.setAuditTime(currDate);
			/** 备注 */
			taskAudit.setRemark(audit.getRemark());
			//更新到数据库 审核表
			row += workTaskAuditMapper.updateWorkTaskAudit(taskAudit);
 		//5、更新执行信息
			/** 任务状态 待处理1/处理中2/处理完成3/已审核4/返工5/变更审核中6 */
			perform.setTaskFlag(5);
			row += workTaskPerformMapper.updateWorkTaskPerform(perform);
			
		//6、更新任务信息
			task.setTaskAuditId("");
			task.setTaskFlag(5);
			task.setAuditTime(currDate);
			row += workTaskMapper.updateWorkTask(task);
		}
		return row;
	}

	@Override
	public int batchAlterAuditRefuse(WorkTaskAudit audit, SysUser sysUser, List<String> submitRecordIds) {
		//更新任务表信息 更新审核表信息 更新执行表信息
		Date currDate = DateUtils.getNowDate();
		int row = 0;
		//1、根据任务记录id查询任务
		for (String taskRecordId : submitRecordIds) {
			WorkTask task = workTaskMapper.selectWorkTaskById(taskRecordId);
		//2、查询任务提交审核单
			WorkTaskAudit taskAudit = workTaskAuditMapper.selectAuditByTaskRecordId(taskRecordId);
			if(taskAudit.getAuditUserId()!=sysUser.getUserId()){
				throw new BusinessException("非法操作");
			}
		//3、查询任务执行单
			String taskPerformId = task.getTaskPerformId();
			WorkTaskPerform perform = workTaskPerformMapper.selectWorkTaskPerformByIdAlter(taskPerformId);
		
		//4、更新审核信息
			/** 审核状态 0未审核 1审核中 2审核不通过 3审核已通过 */
			taskAudit.setAuditFlag(2);
			/** 审核时间 */
			taskAudit.setAuditTime(currDate);
			/** 备注 */
			taskAudit.setRemark(audit.getRemark());
			//更新到数据库 审核表
			row += workTaskAuditMapper.updateWorkTaskAudit(taskAudit);
 		//5、更新执行信息
			/** 任务状态 待处理1/处理中2/处理完成3/已审核4/返工5/变更审核中6 */
			perform.setTaskFlag(2);
			/** 执行人变更标志 正常0/已变更1，变更前需审核，审核后作废 */
			perform.setAlterPerformFlag(0);
			row += workTaskPerformMapper.updateWorkTaskPerform(perform);
			
		//6、更新任务信息
			task.setTaskFlag(2);
			row += workTaskMapper.updateWorkTask(task);
		}
		return row;
	}

	@Override
	public int submitAuditPass(WorkTaskAudit audit, SysUser sysUser) {
		//更新任务表信息 更新审核表信息 更新执行表信息
		Date currDate = DateUtils.getNowDate();
		int row = 0;
		//1、根据任务记录id查询任务
			String taskRecordId = audit.getTaskRecordId();
			WorkTask task = workTaskMapper.selectWorkTaskById(taskRecordId);
		//2、查询任务提交审核单
			WorkTaskAudit taskAudit = workTaskAuditMapper.selectAuditByTaskRecordId(taskRecordId);
			if(taskAudit.getAuditUserId()!=sysUser.getUserId()){
				throw new BusinessException("非法操作");
			}
		//3、查询任务执行单
			String taskPerformId = task.getTaskPerformId();
			WorkTaskPerform perform = workTaskPerformMapper.selectWorkTaskPerformById(taskPerformId);
		
		//4、更新审核信息
			/** 审核状态 0未审核 1审核中 2审核不通过 3审核已通过 */
			taskAudit.setAuditFlag(3);
			/** 审核时间 */
			taskAudit.setAuditTime(currDate);
			/** 备注 */
			taskAudit.setRemark(audit.getRemark());
			//更新到数据库 审核表
			row += workTaskAuditMapper.updateWorkTaskAudit(taskAudit);
			
 		//5、更新执行信息
			/** 任务状态 待处理1/处理中2/处理完成3/已审核4/返工5/变更审核中6 */
			perform.setTaskFlag(4);
			row += workTaskPerformMapper.updateWorkTaskPerform(perform);
			
		//6、更新任务信息
			task.setTaskFlag(4);
			/** 审核工时 */
			
			task.setAuditHours(Integer.parseInt(audit.getParams().get("auditHours").toString()));
			task.setAuditTime(currDate);
			row += workTaskMapper.updateWorkTask(task);
		return row;
	}

	@Override
	public int alterAuditPass(WorkTaskAudit audit, SysUser sysUser) {
		//更新任务表信息 更新审核表信息 更新执行表信息
		Date currDate = DateUtils.getNowDate();
		int row = 0;
		//1、根据任务记录id查询任务
			String taskRecordId = audit.getTaskRecordId();
			WorkTask task = workTaskMapper.selectWorkTaskById(taskRecordId);
		//2、查询任务提交审核单
			WorkTaskAudit taskAudit = workTaskAuditMapper.selectAuditByTaskRecordId(taskRecordId);
			if(taskAudit.getAuditUserId()!=sysUser.getUserId()){
				throw new BusinessException("非法操作");
			}
		//3、查询任务执行单
			String taskPerformId = task.getTaskPerformId();
			WorkTaskPerform perform = workTaskPerformMapper.selectWorkTaskPerformByIdAlter(taskPerformId);
		
		//4、更新审核信息
			/** 审核状态 0未审核 1审核中 2审核不通过 3审核已通过 */
			taskAudit.setAuditFlag(3);
			/** 审核时间 */
			taskAudit.setAuditTime(currDate);
			/** 备注 */
			taskAudit.setRemark(audit.getRemark());
			//更新到数据库 审核表
			row += workTaskAuditMapper.updateWorkTaskAudit(taskAudit);
			
 		//5、更新执行信息
			/** 任务状态 待处理1/处理中2/处理完成3/已审核4/返工5/变更审核中6 */
			perform.setTaskFlag(2);
			row += workTaskPerformMapper.updateWorkTaskPerform(perform);
			
		//6、更新任务信息
			//新增变更后的执行记录
			WorkTaskPerform newPerform = new WorkTaskPerform();
				//查询执行用户信息
				SysUser alterUser = userMapper.selectUserById(perform.getAlterUserId());
				//补全数据
				/** 任务执行表id */
				String newPerformId = StringUtils.getUUID();
				newPerform.setTaskPerformId(newPerformId);
				/** 任务记录id */
				newPerform.setTaskRecordId(perform.getTaskRecordId());
				/** 任务号 */
				newPerform.setTaskId(perform.getTaskId());
				/** 任务执行人用户id */
				newPerform.setPerformUserId(alterUser.getUserId());
				/** 任务执行人工号 */
				newPerform.setPerformEmpId(alterUser.getEmpId());
				/** 任务执行时间 */
				newPerform.setPerformTime(null);
				/** 执行人部门id */
				newPerform.setDealDeptId(alterUser.getDeptId());
				/** 执行人部门名 */
				newPerform.setDealDeptName(alterUser.getDeptName());
				/** 变更执行人用户id */
				/** 变更执行人工号 */
				/** 变更时间 */
				/** 处理说明 */
				/** 任务状态 待处理1/处理中2/处理完成3/已审核4/返工5/变更审核中6 */
				newPerform.setTaskFlag(2);
				/** 执行人变更标志 正常0/已变更1，变更前需审核，审核后作废 */
				newPerform.setAlterPerformFlag(0);
				/** 删除标志 0正常/1删除 */
				newPerform.setDelFlag(0);
				/** 工作提交物 */
				/** 备注 */
				//新增到数据库
				workTaskPerformMapper.insertWorkTaskPerform(newPerform);
			//更新任务信息
			/** 任务状态：task_flag待处理1/处理中2/处理完成3/已审核4/返工5 /变更审核中6*/
			task.setTaskFlag(2);
			/** 实际开始时间：plan_start_date 年月日 */
			task.setRealStartDate(null);
			/** 实际完成时间 */
			task.setRealEndDate(null);
			/** 处理工时 */
			task.setDealHours(null);
			/** 处理部门id */
			task.setDealDeptId(alterUser.getDeptId());
			/** 处理部门名 */
			task.setDealDeptName(alterUser.getDeptName());
			/** 处理人用户id */
			task.setDealUserId(alterUser.getUserId());
			/** 处理人工号 */
			task.setDealEmpId(alterUser.getEmpId());
			/** 处理人姓名 */
			task.setDealUserName(alterUser.getUserName());
			/** 处理时间 */
			task.setDealTime(null);
			/** 审核人用户id */
			/** 审核人工号 */
			/** 审核人姓名 */
			/** 审核时间 */
			task.setAuditTime(null);
			/** 删除标志 0正常/1删除 */
			/** 任务执行id */
			task.setTaskPerformId(newPerformId);
			/** 任务审核id */
			task.setTaskAuditId(null);
			row += workTaskMapper.updateWorkTaskForAlter(task);
		return row;
	}

	@Override
	public Map<String, String> selectSubmitAuditRecently(String taskRecordId) {
		return workTaskAuditMapper.selectSubmitAuditRecently(taskRecordId);
	}
	
}
