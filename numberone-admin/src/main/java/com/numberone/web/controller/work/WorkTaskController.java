package com.numberone.web.controller.work;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.numberone.common.base.AjaxResult;
import com.numberone.common.exception.BusinessException;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.support.Convert;
import com.numberone.common.utils.DateUtils;
import com.numberone.framework.web.base.BaseController;
import com.numberone.system.domain.SysFileBean;
import com.numberone.work.domain.WorkTask;
import com.numberone.work.domain.WorkTaskAudit;
import com.numberone.work.domain.WorkTaskPerform;
import com.numberone.work.mapper.WorkTaskAuditMapper;
import com.numberone.work.service.IWorkTaskService;

/**
 * 任务管理
 * <p>Title:WorkTaskController</p>
 * @author 段佳佳
 * @date 2020年4月11日
 */
@Controller
@RequestMapping("/work/task")
public class WorkTaskController extends BaseController{
	private String prefix = "work/task";
	
	@Autowired
	private IWorkTaskService workTaskService;
	
	@Autowired
    private CacheManager cacheManager;
    Cache<String, AtomicInteger> taskSequenceCache;

    @PostConstruct
    public void init()
    {
    	taskSequenceCache = cacheManager.getCache("taskSequenceCache");
    	
    }
	/**
	 * 任务分配页面
	 * @param: @param mmap
	 * @param: @return
	 * @return: String
	 */
	@RequiresPermissions("work:task:allocate")
    @GetMapping("/allocate")
    public String allocate(ModelMap mmap)
    {
		
        return prefix + "/allocate";
    }
	/**
	 * 部门任务待办查询页面
	 * @param mmap
	 * @return
	 */
	@RequiresPermissions("work:task:deptBacklog")
	@GetMapping("/deptBacklog")
	public String deptBacklog(ModelMap mmap)
	{
		
		return prefix + "/deptBacklog";
	}
	/**
	 * 编辑页面
	 * @param mmap
	 * @return
	 */
	@RequiresPermissions("work:task:allocate")
	@RequestMapping("/edit/{taskRecordId}")
	public String edit(@PathVariable("taskRecordId") String taskRecordId,ModelMap mmap)
	{
		if(workTaskService.selectTaskNoEditByIds(taskRecordId)>0){
			throw new BusinessException("只能编辑未处理的任务");
		}
		WorkTask task = workTaskService.selectWorkTaskById(taskRecordId);
		if(task.getAppendix()==null){
			task.setAppendix(new SysFileBean());
		}
		mmap.put("task",task);
		return prefix + "/edit";
	}
	/**
	 * 个人任务待办处理页面
	 * @param mmap
	 * @return
	 */
	@RequiresPermissions("work:task:mineBacklog")
	@GetMapping("/mineBacklog")
	public String mineBacklog(ModelMap mmap)
	{
		
		return prefix + "/mineBacklog";
	}
	/**
	 * 任务分配处理
	 * @param: @return
	 * @return: AjaxResult
	 */
	@RequiresPermissions("work:task:allocate")
	@PostMapping("/launchAllocate")
	@ResponseBody
	public AjaxResult launchAllocate(WorkTask workTask){
		AtomicInteger taskSequence = taskSequenceCache.get("taskSequence");
    	if(taskSequence==null){
    		taskSequence = new AtomicInteger(0);
    	}
		int row = 0;
		try {
			row = workTaskService.launchAllocate(workTask, getSysUser(), taskSequence.intValue());
		} catch (Exception e) {
			taskSequence.incrementAndGet();
			taskSequenceCache.put("taskSequence", taskSequence);
			throw new BusinessException("分配任务失败，请联系管理员！");
		}
		if(row>0){
			taskSequence.incrementAndGet();
			taskSequenceCache.put("taskSequence", taskSequence);
		} 
		return toAjax(row);
	}
	/**
	 * 查询当前用户已分配未完成任务
	 * @param: @param mmap
	 * @param: @return
	 * @return: TableDataInfo
	 */
	@RequiresPermissions("work:task:allocate")
    @PostMapping("/allocate")
	@ResponseBody
	public TableDataInfo allocated(ModelMap mmap){
		startPage();
		List<WorkTask> list = workTaskService.selectNoCompleteByUserId(getUserId());
		return getDataTable(list);
	}
	
	/**
	 * 查询本部门的待办 管理员查询全部
	 * @param workTask
	 * @return
	 */
	@RequiresPermissions("work:task:deptBacklog")
	@PostMapping("/deptBacklogList")
	@ResponseBody
	public TableDataInfo deptBacklogList(WorkTask workTask){
		startPage();
		if(workTask.getTaskFlag() == null){
			workTask.setTaskFlag(1);//默认查询待处理
		}
		if(workTask.getTaskFlag() == 0) workTask.setTaskFlag(null);
		List<WorkTask> list = workTaskService.selectListByDeptId(workTask,getSysUser());
		return getDataTable(list);
	}
	
	/**
	 * 个人任务待办
	 * @param workTask
	 * @return
	 */
	@RequiresPermissions("work:task:mineBacklog")
	@PostMapping("/mineBacklogList")
	@ResponseBody
	public TableDataInfo mineBacklogList(WorkTask workTask){
		startPage();
		if(workTask.getTaskFlag() == null){
			workTask.setTaskFlag(1);//默认查询待处理
		}
		List<WorkTask> list = workTaskService.selectListOfMine(workTask,getSysUser());
		return getDataTable(list);
	}
	/**
	 * 任务详情页面
	 * @param taskRecordId
	 * @param mmap
	 * @return
	 */
	@RequiresPermissions("work:task:deptBacklog")
    @GetMapping("/detail/{taskRecordId}")
    public String detail(@PathVariable("taskRecordId") String taskRecordId, ModelMap mmap)
	{
		WorkTask task = workTaskService.selectWorkTaskById(taskRecordId);
		if(task.getAppendix()==null){
			task.setAppendix(new SysFileBean());
		}
		mmap.put("task", task);
		WorkTaskPerform taskPerform = workTaskService.selectPerformByTaskRecordId(taskRecordId);
		if(taskPerform==null){
			taskPerform = new WorkTaskPerform();
		}
		if(taskPerform.getAppendix()==null){
			taskPerform.setAppendix(new SysFileBean());
		}
		
		mmap.put("taskPerform", taskPerform);
		mmap.put("currUserId", getUserId());
		mmap.put("currDate", DateUtils.getDate());
        return prefix + "/detail";
    }
	@RequiresPermissions("work:task:audit")
	@GetMapping("/audit")
	public String audit(ModelMap mmap)
	{
		mmap.put("currUserId", getUserId());
		mmap.put("currDate", DateUtils.getDate());
		return prefix + "/audit";
	}
	/**
	 * 任务处理页面
	 * @param taskRecordId
	 * @param mmap
	 * @return
	 */
	@RequiresPermissions("work:task:mineBacklog")
	@GetMapping("/perform/{taskRecordId}")
	public String perform(@PathVariable("taskRecordId") String taskRecordId, ModelMap mmap)
	{
		WorkTask task = workTaskService.selectFlag1And2And5(taskRecordId);
		if(task==null){
			throw new BusinessException("这个任务不允许处理");
		}
		if(task.getAppendix()==null){
			task.setAppendix(new SysFileBean());
		}
		mmap.put("task", task);
		WorkTaskPerform taskPerform = workTaskService.selectPerformByTaskRecordId(taskRecordId);
		if(task.getDealUserId()!=getUserId()){
			throw new BusinessException("任务处理人与当前用户必须为同一人");
		}
		if(taskPerform==null){
			taskPerform = new WorkTaskPerform();
		}
		if(taskPerform.getAppendix()==null){
			taskPerform.setAppendix(new SysFileBean());
		}
		mmap.put("taskPerform", taskPerform);
		mmap.put("currUserId", getUserId());
		mmap.put("currDate", DateUtils.getDate());
		return prefix + "/perform";
	}
	
    /**
     * 批量删除任务
     * @param: @return
     * @return: String
     */
    @PostMapping("/remove")
    @RequiresPermissions("work:task:allocate")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult remove(String ids)
    {
    	return toAjax(workTaskService.deleteWorkTaskByIds(ids));
    }
    /**
     * 编辑任务
     * @param ids
     * @return
     */
    @PostMapping("/edit")
    @RequiresPermissions("work:task:allocate")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult edit(WorkTask workTask)
    {
    	return toAjax(workTaskService.updateWorkTask(workTask));
    }
    /**
     * 查询不可编辑
     * @param workTask
     * @return
     */
    @PostMapping("/noEdit/{taskRecordId}")
    @RequiresPermissions("work:task:allocate")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult noEdit(@PathVariable("taskRecordId") String taskRecordId)
    {
		if(workTaskService.selectTaskNoEditByIds(taskRecordId)>0){
			throw new BusinessException("只能编辑未处理的任务");
		}
    	return AjaxResult.success();
    }
    
    /**
     * 保存任务处理信息
     * @param perform
     * @return
     */
    @PostMapping("/savePerform")
    @RequiresPermissions("work:task:mineBacklog")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult savePerform(WorkTaskPerform perform)
    {
    	
    	return toAjax(workTaskService.savePerform(perform,getSysUser()));
    }
    
    /**
     * 解决任务 更新任务处理时间 更新任务状态 更新任务执行信息 发起任务审批
     * @param workTask
     * @return
     */
    @PostMapping("/resolved")
    @RequiresPermissions("work:task:mineBacklog")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult resolved(WorkTask workTask)
    {
    	return toAjax(workTaskService.resolved(workTask,getSysUser()));
    }
    
    /**
     * 变更执行人
     * @param perform
     * @return
     */
    @PostMapping("/alter")
    @RequiresPermissions("work:task:mineBacklog")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult alter(WorkTaskPerform perform)
    {
    	return toAjax(workTaskService.alter(perform,getSysUser()));
    }
    
    @Autowired
    private WorkTaskAuditMapper workTaskAuditMapper;
    /**
     * 批量审核
     * @param audit
     * @return
     */
    @PostMapping("/batchAudit")
    @RequiresPermissions("work:task:audit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult batchAudit(WorkTaskAudit audit)
    {
		String auditTypeText = audit.getParams().get("auditTypeText").toString();
		Integer auditFlag = audit.getAuditFlag();
		int row = 0;
		//1.首先判断审核状态为通过还是驳回
		//审核不通过
		if(auditFlag==0){
			/** 审核类型 任务提交审核submit 1/任务变更审核alter 2*/
			String taskRecordIds = audit.getTaskRecordId();
			List<String> submitRecordIds = workTaskAuditMapper.selectSubmitRecordIdInRecordIds(Convert.toStrArray(taskRecordIds));
			List<String> alterRecordIds = workTaskAuditMapper.selectAlterRecordIdInRecordIds(Convert.toStrArray(taskRecordIds));
			//提交审核
			if(submitRecordIds!=null && submitRecordIds.size()!=0)
				row = workTaskService.batchSubmitAuditRefuse(audit,getSysUser(),submitRecordIds);
			//变更审核
			if(alterRecordIds!=null && alterRecordIds.size()!=0)
				row = workTaskService.batchAlterAuditRefuse(audit,getSysUser(),alterRecordIds);
			
		}
		//审核通过
		if(auditFlag==1){
			if("submit".equals(auditTypeText)){//提交审核
				row = workTaskService.submitAuditPass(audit,getSysUser());
				
			}else if("alter".equals(auditTypeText)){//变更审核
				row = workTaskService.alterAuditPass(audit,getSysUser());
			}
		}
    	return toAjax(row);
    }
    
    /**
     * 变更详情
     * @param taskRecordId
     * @return
     */
    @PostMapping("/alterDetail")
    @RequiresPermissions("work:task:mineBacklog")
    @ResponseBody
    public AjaxResult alterDetail(String taskRecordId)
    {
    	
    	AjaxResult ajaxResult = new AjaxResult();
    	ajaxResult.put("code", "0");
    	ajaxResult.put("data", workTaskService.selectPerformBeforeAlter(taskRecordId));
    	return ajaxResult;
    }
    
    /**
     * 返工原因
     * @param taskRecordId
     * @return
     */
    @PostMapping("/reworkDetail")
    @RequiresPermissions("work:task:mineBacklog")
    @ResponseBody
    public AjaxResult reworkDetail(String taskRecordId)
    {
    	
    	AjaxResult ajaxResult = new AjaxResult();
    	ajaxResult.put("code", "0");
    	ajaxResult.put("data", workTaskService.selectSubmitAuditRecently(taskRecordId));
    	return ajaxResult;
    }
	
    @RequiresPermissions("work:task:audit")
    @PostMapping("/auditList")
	@ResponseBody
	public TableDataInfo auditList(WorkTaskAudit audit){
		startPage();
		audit.setAuditUserId(getUserId());
		List<WorkTaskAudit> list = workTaskService.selectAuditList(audit);
		return getDataTable(list);
	}
}
