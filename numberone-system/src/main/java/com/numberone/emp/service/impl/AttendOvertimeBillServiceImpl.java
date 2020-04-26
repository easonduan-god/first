/**
 * 
 */
package com.numberone.emp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.common.constant.AttendConstants;
import com.numberone.common.exception.BusinessException;
import com.numberone.common.support.Convert;
import com.numberone.common.utils.DateUtils;
import com.numberone.common.utils.StringUtils;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.emp.domain.EmpNonworkday;
import com.numberone.emp.domain.EmpOvertimeAudit;
import com.numberone.emp.domain.EmpOvertimeBill;
import com.numberone.emp.mapper.EmpAttendBillMapper;
import com.numberone.emp.mapper.EmpNonworkdayMapper;
import com.numberone.emp.mapper.EmpOvertimeAuditMapper;
import com.numberone.emp.mapper.EmpOvertimeBillMapper;
import com.numberone.emp.service.IAttendOvertimeBillService;
import com.numberone.system.domain.SysUser;
import com.numberone.system.mapper.SysUserMapper;
import com.numberone.system.service.ISysUserService;

/**
 * 延时工作单service层实现类
 * <p>Title:AttendOvertimeBillServiceImpl</p>
 * @author 段佳佳
 * @date 2020年4月4日
 */
@Service
public class AttendOvertimeBillServiceImpl implements IAttendOvertimeBillService {
	@Autowired
	private EmpOvertimeBillMapper empOvertimeBillMapper;
	@Autowired
	private EmpNonworkdayMapper empNonworkdayMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private EmpAttendBillMapper empAttendBillMapper;
	@Autowired
	private EmpOvertimeAuditMapper empOvertimeAuditMapper;
	
	/** 
	 * @Description: 发起审核延时工作单
	 * @param: @param overtimeBill
	 * @param: @param sysUser
	 * @param: @return
	 * @throws
	 */
	@Override
	public int launchAudit(EmpOvertimeBill overtimeBill, SysUser sysUser) {
	//1、首先判断请求参数是否合规
		//获取参数
		Date startDate = overtimeBill.getStartDate();
		Date endDate = overtimeBill.getEndDate();
		Map<String, Object> params = overtimeBill.getParams();
		Long deptId = sysUser.getDeptId();
		Long userId = sysUser.getUserId();
		String username = sysUser.getUserName();
		String rolesName = sysUser.getRolesName();
		String ids = null;
		String[] idArr;
		if(StringUtils.isNull(params.get("ids"))){
			throw new BusinessException("工作人员为空");
		}
		ids = (String) params.get("ids");
		Double applyWorktimes = overtimeBill.getApplyWorktimes();//每日申请工时
		if(applyWorktimes>17 || applyWorktimes<=0){
			throw new BusinessException("每日申请工时请勿大于17，小于等于0");
		}
		//开始时间结束时间不能为空，开始不能大于结束,结束时间与开始时间最多差一天
		//获取开始与结束间隔
		double intervalDays = DateUtils.getDateIntervalInDays(startDate, endDate);
		if(startDate==null || endDate==null || startDate.after(endDate) || intervalDays>1){
			throw new BusinessException("开始时间结束时间不能为空，开始不能大于结束,结束时间与开始时间最多差一天");
		}
		//开始时间不能早于当前日期，且不大于10
		double intervalStart_Curr = DateUtils.getDateIntervalInDays(startDate, new Date());
		if(intervalStart_Curr>=1 || intervalStart_Curr<-15){
			throw new BusinessException("开始时间不能早于当前日期，且不大于15天");
		}
		
		//从数据库中查询工作日表
    	EmpNonworkday workday = empNonworkdayMapper.selectWorkdayByDate(startDate);
    	//判断startdate是否是周日
    	Boolean isWeekend = DateUtils.isWeekend(startDate);
    	
    	//非工作日加班，申请工时不超过17
    	//日程为2（休息日） 或者是周末
    	if((workday!=null && workday.getWorkdateFlag()==2 ) || isWeekend){
    		if(applyWorktimes>17){
    			throw new BusinessException(DateUtils.parseDateToStr("yyyy-MM-dd", startDate)+"为休息日，申请工时不允许超过17");
    		}
    		//若开始日期与结束日期为同一天，则不超过13,若超过13，必须将endDate设为start后一天
    		if(applyWorktimes>13){
    			endDate = DateUtils.getAroundDate(startDate, 1);
    		}else{
    			endDate = startDate;
    		}
    	}
		//正常工作日,日程状态(0正常 1工作日 2休息日)，申请工时为正整数 范围1-9
    	//日程为1（工作日） 或者不是周末
    	if( (workday!=null && workday.getWorkdateFlag()==1) || !isWeekend){
    		if(applyWorktimes>9){
    			throw new BusinessException(DateUtils.parseDateToStr("yyyy-MM-dd", startDate)+"为正常工作日，申请工时不允许超过9");
    		}
    		//若开始日期与结束日期为同一天，则不超过5,若超过5，必须将endDate设为start后一天
    		if(applyWorktimes>5){
    			endDate = DateUtils.getAroundDate(startDate, 1);
    		}else{
    			endDate = startDate;
    		}
    	}
    	
    	
    	//id 的数组
    	idArr = ids.split(",");
    	//提交审批的人若是并不是admin ceo hr，只允许为自己为部门的员工填写申请单，只要有一个申请人不属于自己部门就不行
    	if(!"admin".equals(sysUser.getLoginName()) && !rolesName.contains("ceo") && !rolesName.contains("hr")){
    		Integer row = sysUserMapper.selectCountByUserIdsAndDeptId(idArr,deptId);
    		if(row!=idArr.length){
    			throw new BusinessException("无法操作非本部门员工");
    		}
    	}
    	//判断申请的人员中是否存在相同延时工单，只要是在一个考勤周期内，延时工单的周期（只管开始日期），都算重复
    	List<String> illegalUsernames = empOvertimeBillMapper.selectUsernamesByUserIdsAndStartDate(idArr,startDate);
    	//非法username，并提示到前台
    	if(illegalUsernames!=null && illegalUsernames.size()!=0){
    		//根据userid数组查询用户名
    		throw new BusinessException(StringUtils.join(illegalUsernames, ",")+"等用户已存在相同延时工作单");
    	}
    	
    	//判断接收人员是否存在考勤单 包括年假 调休假 事假，以上不允许填写延时工单,查询考勤单表和用户表
    	//审核状态(0未审核 1审核中 2审核不通过 3审核已通过) 不能是不通过
    	illegalUsernames = empAttendBillMapper.selectUsernamesByUserIdsAndStartDate(idArr,startDate);
    	//非法username，并提示到前台
    	if(illegalUsernames!=null && illegalUsernames.size()!=0){
    		//根据userid数组查询用户名
    		throw new BusinessException(StringUtils.join(illegalUsernames, ",")+"等用户已存在考勤单");
    	}
		
    	
	//2、保存延时工单overtimeBill
		//新建orvertimeBill对象，并补全数据
    	
    	/** 延时工作单id */
    	String overtimeBillId = StringUtils.getUUID();
    	overtimeBill.setOvertimeBillId(overtimeBillId);
    	/** 员工id */
    	overtimeBill.setEmpId(sysUser.getEmpId());
    	/** 用户id */
    	overtimeBill.setUserId(userId);
    	/** 主题 */
    	overtimeBill.setTheme(username+"_延时工单_"+DateUtils.dateTimeNow("yyyyMMdd"));
    	/** 所属部门 */
    	overtimeBill.setDeptName(sysUser.getDeptName());
    	/** 所属部门编号 */
    	overtimeBill.setDeptId(deptId);
    	/** 申请人 */
    	overtimeBill.setUserName(username);
    	/** 申请日期 */
    	overtimeBill.setApplyDate(new Date());
    	/** 开始日期 */
    	overtimeBill.setStartDate(startDate);
    	/** 结束日期 */
    	overtimeBill.setEndDate(endDate);
    	/** 工作人员 */
    	overtimeBill.setWorkPersons(ids);
    	/** 工作人员姓名 */
    	overtimeBill.setWorkPersonNames((String)params.get("usernames"));
    	/** 每日申请时长 */
    	overtimeBill.setApplyWorktimes(Math.floor(applyWorktimes));
    	/** 事由 */
    	/** 完成状态(0未完成 1已完成) */
    	overtimeBill.setCompleteFlag(AttendConstants.IS_NOT_COMPLETE);
    	/** 审核状态(0未审核 1审核中 2审核不通过 3审核已通过) */
    	overtimeBill.setAuditFlag(AttendConstants.IN_AUDIT);
    	/** 删除标志(0未删除 1已删除) */
    	overtimeBill.setDelFlag(0);
    	//保存到数据库
    	int row = 0;
		//保存orvertimeBill表，同时记得关联上相应用户，此处需要用到overtime_user中间表
    	row += empOvertimeBillMapper.insertEmpOvertimeBill(overtimeBill);
    	
    	//关联中间表 overtime_user
    	row += empOvertimeBillMapper.relateMiddleTable(idArr,overtimeBillId);
	//3、提交上级审核
		//查询上级是谁，发过去就好了
    	SysUser leader = sysUserService.selectLeader(sysUser);
    	if(leader==null){
    		throw new BusinessException("后台出错");
    	}
		//关联上上面的延时工单号，保存到延时工单审核表 
    	EmpOvertimeAudit empOvertimeAudit = new EmpOvertimeAudit();
    	/** 延时工作单审核表单id */
    	empOvertimeAudit.setOvertimeAuditId(StringUtils.getUUID());
    	/** 延时工作单id */
    	empOvertimeAudit.setOvertimeBillId(overtimeBillId);
    	/** 用户id */
    	empOvertimeAudit.setUserId(userId);
    	/** 工号 */
    	empOvertimeAudit.setEmpId(sysUser.getEmpId());
    	/** 审核人 */
    	empOvertimeAudit.setAuditName(leader.getUserName());
    	/** 审核人id */
    	empOvertimeAudit.setAuditUserId(leader.getUserId());
    	/** 审核人工号 */
    	empOvertimeAudit.setAuditEmpId(leader.getEmpId());
    	/** 审核人职位 */
    	empOvertimeAudit.setAuditJob(leader.getRolesName());
    	/** 审核状态(0未审核 1审核中 2审核不通过 3审核已通过) */
    	empOvertimeAudit.setAuditFlag(1);
    	row += empOvertimeAuditMapper.insertEmpOvertimeAudit(empOvertimeAudit);
		return row;
	}

	/** 
	 * @Description: 查询我的延时工单 包括我发起，或者我参与
	 * @param: @param empOvertimeBill
	 * @param: @return
	 * @throws
	 */
	@Override
	public List<EmpOvertimeBill> selectOvertimeBillOfMine(EmpOvertimeBill empOvertimeBill) {
		return empOvertimeBillMapper.selectOvertimeBillOfMine(empOvertimeBill);
	}

	/** 
	 * @Description: 我的延时工单详情 只能查看自己发起或参与延时工单/交由自己审核的延时工单详情 	管理员除外
	 * @param: @param overtimeBillId
	 * @param: @param sysUser
	 * @param: @return
	 * @throws
	 */
	@Override
	public EmpOvertimeBill selectByIdAndRelationMine(String overtimeBillId, SysUser sysUser) {
		if("admin".equals(sysUser.getLoginName())){
			return empOvertimeBillMapper.selectEmpOvertimeBillById(overtimeBillId);
		}
		
		return empOvertimeBillMapper.selectByIdAndRelationMine(overtimeBillId,sysUser.getUserId());
	}

	/** 
	 * @Description: 查询分配给自己的延时待办
	 * @param: @param empOvertimeAudit
	 * @param: @return
	 * @throws
	 */
	@Override
	public List<EmpOvertimeAudit> selectAuditListByAuditId(EmpOvertimeAudit empOvertimeAudit) {
		String usreIds = (String) empOvertimeAudit.getParams().get("userIds");
		if(StringUtils.isNotEmpty(usreIds)){
			String[] userIdArr = Convert.toStrArray(usreIds);
			empOvertimeAudit.getParams().put("userIdArr", userIdArr);
		}
		return empOvertimeAuditMapper.selectListAllocateMine(empOvertimeAudit);
	}

	/** 
	 * @Description: 批量审核不通过
	 * @param: @param overtimeAuditIds
	 * @param: @param sysUser
	 * @param: @param remark
	 * @param: @return
	 * @throws
	 */
	@Override
	public int batchAuditRefuse(String overtimeAuditIds, SysUser sysUser, String remark) {
		int row = 0;
		for (String overtimeAuditId : overtimeAuditIds.split(",")) {
			//根据审核id获取考勤审核单
			EmpOvertimeAudit overtimeAudit = empOvertimeAuditMapper.selectEmpOvertimeAuditById(overtimeAuditId);
			
			//先判断考勤审核人用户id是否一致
			if(overtimeAudit.getAuditUserId()!=sysUser.getUserId()){
				throw new BusinessException("考勤单审核人与当前用户不一致");
			}
			
			//审核通过 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
			overtimeAudit.setAuditFlag(2);//审核状态
			overtimeAudit.setAuditTime(new Date());
			overtimeAudit.setRemark(remark);//备注
			row += empOvertimeAuditMapper.updateEmpOvertimeAudit(overtimeAudit);
			
				//200320 修改 批量审核不通过 忘了修改考勤单状态
				//设置考勤单为完成状态 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
				//获取考勤单
				EmpOvertimeBill empOvertimeBill = empOvertimeBillMapper.selectEmpOvertimeBillById(overtimeAudit.getOvertimeBillId());
				
				//设置考勤单为完成状态 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
				empOvertimeBill.setAuditFlag(2);
				//完成状态(0未完成 1已完成)
				empOvertimeBill.setCompleteFlag(1);
				
				//更新数据库
				row += empOvertimeBillMapper.updateEmpOvertimeBill(empOvertimeBill);
				
		}
		return row;
	}

	/** 
	 * @Description: 批量审核通过
	 * @param: @param overtimeAuditIds
	 * @param: @param sysUser
	 * @param: @param remark
	 * @param: @return
	 * @throws
	 */
	@Override
	public int batchAuditPass(String overtimeAuditIds, SysUser sysUser, String remark) {
		int row = 0;
		SysUser leader = null;
		if(StringUtils.isEmpty(overtimeAuditIds)){
			throw new BusinessException("id为空");
		}
		//1.根据考勤审核单ids，以及审核人用户id批量审核，然后判断当前用户是否有上级，提交到上级审核
		for (String auditId : overtimeAuditIds.split(",")) {
			//根据审核id获取考勤审核单
			EmpOvertimeAudit overtimeAudit = empOvertimeAuditMapper.selectEmpOvertimeAuditById(auditId);
			
			//先判断考勤审核人用户id是否一致
			if(overtimeAudit.getAuditUserId()!=sysUser.getUserId()){
				throw new BusinessException("考勤单审核人与当前用户不一致");
			}
			
			//审核通过 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
			overtimeAudit.setAuditFlag(3);//审核状态
			overtimeAudit.setAuditTime(new Date());
			overtimeAudit.setRemark(remark);//备注
			empOvertimeAuditMapper.updateEmpOvertimeAudit(overtimeAudit);
			
			//查询当前用户上级领导
			if(leader==null){
				leader = sysUserService.selectLeader(sysUser);
			}
			//2.提交给领导审核
			if(leader!=null && leader.getUserId()!=sysUser.getUserId()){
				//复制overtimeAudit属性到新的overtimeAudit对象中
				EmpOvertimeAudit newOvertimeAudit = new EmpOvertimeAudit();
				BeanUtils.copyBeanProp(newOvertimeAudit, overtimeAudit);
				
				//更新新对象的参数
				newOvertimeAudit.setOvertimeAuditId(StringUtils.getUUID());
				newOvertimeAudit.setAuditTime(null);
				newOvertimeAudit.setRemark("");
				newOvertimeAudit.setAuditName(leader.getUserName());
				newOvertimeAudit.setAuditUserId(leader.getUserId());
				newOvertimeAudit.setAuditEmpId(leader.getEmpId());
				newOvertimeAudit.setAuditFlag(1);
				newOvertimeAudit.setAuditJob(leader.getRolesName());
				
				//更新到数据库
				row += empOvertimeAuditMapper.insertEmpOvertimeAudit(newOvertimeAudit);
			}
				
			//3.若没有上级了，也就是领导为自身或者为null
			if(leader==null || leader.getUserId()==sysUser.getUserId()){
				//获取考勤单
				EmpOvertimeBill empOvertimeBill = empOvertimeBillMapper.selectEmpOvertimeBillById(overtimeAudit.getOvertimeBillId());
				
				//设置考勤单为完成状态 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
				empOvertimeBill.setAuditFlag(3);
				//完成状态(0未完成 1已完成)
				empOvertimeBill.setCompleteFlag(1);
				
				//更新数据库
				row += empOvertimeBillMapper.updateEmpOvertimeBill(empOvertimeBill);
			}
			
		}
		
		return row;
	}

	/** 
	 * @Description: 延时工单追踪
	 * @param: @param overtimeBillId
	 * @param: @return
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> selectOvertimeBillTrace(String overtimeBillId) {
		List<Map<String, Object>> rslist = new ArrayList<Map<String, Object>>();
		//1.查询考勤单信息
		EmpOvertimeBill overtimeBill = empOvertimeBillMapper.selectEmpOvertimeBillById(overtimeBillId);
		Map<String, Object> itemMap = new HashMap<String,Object>();
		BeanUtils.copyProperties(itemMap, overtimeBill,true);
		rslist.add(itemMap);
		//2.查询考勤审核信息
		List<EmpOvertimeAudit> auditList = empOvertimeAuditMapper.selectOvertimeAuditByOvertimeBillId(overtimeBillId);
		for (EmpOvertimeAudit empOvertimeAudit : auditList) {
			Map<String, Object> auditMap = new HashMap<String,Object>();
			BeanUtils.copyProperties(auditMap, empOvertimeAudit,true);
			rslist.add(auditMap);
		}
		
		//3.查询审核是否结束 完成状态是否为已完成
		int row = empOvertimeBillMapper.selectOvertimeAuditIsEnd(overtimeBillId);
		Map<String, Object> endMap = new HashMap<String,Object>();
		if(row>0){
			endMap.put("isComplete", 1);
			rslist.add(endMap);
		}
		return rslist;
	}

	/** 
	 * @Description: 查看延时工单审核单详情 只能查看与自己相关的 管理员除外
	 * @param: @param overtimeAuditId
	 * @param: @param sysUser
	 * @param: @return
	 * @throws
	 */
	@Override
	public EmpOvertimeAudit selectOvertimeAuditByAuditIdAndRelationMine(String overtimeAuditId, SysUser sysUser) {
		if("admin".equals(sysUser.getLoginName())){
			return empOvertimeAuditMapper.selectEmpOvertimeAuditById(overtimeAuditId);
		}
		return empOvertimeAuditMapper.selectOvertimeAuditByAuditIdAndRelationMine(overtimeAuditId, sysUser.getUserId());
	}

}
