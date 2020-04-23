package com.numberone.work.service;

import java.util.List;
import java.util.Map;

import com.numberone.system.domain.SysUser;
import com.numberone.work.domain.WorkTask;
import com.numberone.work.domain.WorkTaskAudit;
import com.numberone.work.domain.WorkTaskPerform;

/**
 * 任务分配，执行，审核 服务层
 * 
 * @author eason
 * @date 2020-04-11
 */
public interface IWorkTaskService 
{
	/**
     * 查询任务分配，执行，审核信息
     * 
     * @param taskRecordId 任务分配，执行，审核ID
     * @return 任务分配，执行，审核信息
     */
	public WorkTask selectWorkTaskById(String taskRecordId);
	
	/**
     * 查询任务分配，执行，审核列表
     * 
     * @param workTask 任务分配，执行，审核信息
     * @return 任务分配，执行，审核集合
     */
	public List<WorkTask> selectWorkTaskList(WorkTask workTask);
	
	/**
     * 新增任务分配，执行，审核
     * 
     * @param workTask 任务分配，执行，审核信息
     * @return 结果
     */
	public int insertWorkTask(WorkTask workTask);
	
	/**
     * 修改任务分配，执行，审核
     * 
     * @param workTask 任务分配，执行，审核信息
     * @return 结果
     */
	public int updateWorkTask(WorkTask workTask);
		
	/**
     * 删除任务分配，执行，审核信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteWorkTaskByIds(String ids);
	/**
	 * 查询不可编辑的数量
	 * @param ids
	 * @return
	 */
	public int selectTaskNoEditByIds(String ids);
	/**
	 * 任务分配
	 * @param taskSequence 
	 * @param: @param workTask
	 * @param: @return
	 * @return: int
	 */
	public int launchAllocate(WorkTask workTask,SysUser sysUser, int taskSequence);

	/**
	 * 查询当前用户已分配未完成任务
	 * @param: @param userId
	 * @param: @return
	 * @return: List<WorkTask>
	 */
	public List<WorkTask> selectNoCompleteByUserId(Long userId);

	/**
	 * 查询本部门的待办任务 管理员查询全部
	 * @param workTask
	 * @param userId
	 * @return WorkTask
	 */
	public List<WorkTask> selectListByDeptId(WorkTask workTask, SysUser user);
	
	/**
	 * 查询个人任务待办
	 * @param workTask
	 * @param sysUser
	 * @return
	 */
	public List<WorkTask> selectListOfMine(WorkTask workTask, SysUser sysUser);

	/**
	 * 保存任务处理信息
	 * @param perform
	 * @param sysUser
	 * @return
	 */
	public int savePerform(WorkTaskPerform perform, SysUser sysUser);
	
	/**
	 * 通过任务记录id查询任务执行信息
	 * @param taskRecordId
	 * @return
	 */
	public WorkTaskPerform selectPerformByTaskRecordId(String taskRecordId);
	
	/**
	 * 解决任务 更新任务处理时间 更新任务状态 更新任务执行信息 发起任务审批
	 * @param workTask
	 * @param sysUser
	 * @return
	 */
	public int resolved(WorkTask workTask, SysUser sysUser);
	
	/**
	 * 变更执行人 将之前的执行表作废 并发起任务变更审批
	 * @param perform
	 * @param sysUser
	 * @return
	 */
	public int alter(WorkTaskPerform perform, SysUser sysUser);
	
	/**
	 * 查询任务状态为 待处理1/处理中2 因为只有这两个才可以执行
	 * @param taskRecordId 
	 * @return
	 */
	public WorkTask selectFlag1And2And5(String taskRecordId);

	/**
	 * 查询审核列表
	 * @param audit
	 * @return
	 */
	public List<WorkTaskAudit> selectAuditList(WorkTaskAudit audit);
	
	/**
	 * 查询变更前的任务执行
	 * @param taskRecordId 
	 */
	public Map<String,String> selectPerformBeforeAlter(String taskRecordId);
	
	/**
	 * 批量提交审核驳回
	 * @param audit
	 * @param sysUser
	 * @param submitRecordIds 
	 * @return
	 */
	public int batchSubmitAuditRefuse(WorkTaskAudit audit, SysUser sysUser, List<String> submitRecordIds);

	/**
	 * 批量变更审核驳回
	 * @param audit
	 * @param sysUser
	 * @return
	 */
	public int batchAlterAuditRefuse(WorkTaskAudit audit, SysUser sysUser, List<String> alterRecordIds);

	/**
	 * 提交审核通过
	 * @param audit
	 * @param sysUser
	 * @return
	 */
	public int submitAuditPass(WorkTaskAudit audit, SysUser sysUser);

	/**
	 * 变更审核通过
	 * @param audit
	 * @param sysUser
	 * @return
	 */
	public int alterAuditPass(WorkTaskAudit audit, SysUser sysUser);

	/**
	 * 查询最近的审核不通过的审核单 审核类型为提交审核 submit 1
	 * @param taskRecordId
	 * @return
	 */
	public Map<String,String> selectSubmitAuditRecently(String taskRecordId);
	
}
