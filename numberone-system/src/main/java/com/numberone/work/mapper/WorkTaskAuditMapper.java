package com.numberone.work.mapper;

import com.numberone.work.domain.WorkTaskAudit;
import java.util.List;
import java.util.Map;	

/**
 * 任务审核 work_task_audit 数据层
 * 
 * @author eason
 * @date 2020-04-11
 */
public interface WorkTaskAuditMapper 
{
	/**
     * 查询任务审核 work_task_audit信息
     * 
     * @param taskAuditId 任务审核 work_task_auditID
     * @return 任务审核 work_task_audit信息
     */
	public WorkTaskAudit selectWorkTaskAuditById(String taskAuditId);
	
	/**
     * 查询任务审核 work_task_audit列表
     * 
     * @param workTaskAudit 任务审核 work_task_audit信息
     * @return 任务审核 work_task_audit集合
     */
	public List<WorkTaskAudit> selectWorkTaskAuditList(WorkTaskAudit workTaskAudit);
	
	/**
     * 新增任务审核 work_task_audit
     * 
     * @param workTaskAudit 任务审核 work_task_audit信息
     * @return 结果
     */
	public int insertWorkTaskAudit(WorkTaskAudit workTaskAudit);
	
	/**
     * 修改任务审核 work_task_audit
     * 
     * @param workTaskAudit 任务审核 work_task_audit信息
     * @return 结果
     */
	public int updateWorkTaskAudit(WorkTaskAudit workTaskAudit);
	
	/**
     * 删除任务审核 work_task_audit
     * 
     * @param taskAuditId 任务审核 work_task_auditID
     * @return 结果
     */
	public int deleteWorkTaskAuditById(String taskAuditId);
	
	/**
     * 批量删除任务审核 work_task_audit
     * 
     * @param taskAuditIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteWorkTaskAuditByIds(String[] taskAuditIds);

	/**
	 * 根据任务记录id查询审核信息 只查询审核状态未完成和审核中的记录 只查询最近的一条
	 * @param taskRecordId
	 * @return
	 */
	public WorkTaskAudit selectAuditByTaskRecordId(String taskRecordId);

	/**
	 * 从输入的列表中获取为提交审核的记录id
	 * @param strArray
	 */
	public List<String> selectSubmitRecordIdInRecordIds(String[] strArray);
	/**
	 * 从输入的列表中获取为变更审核的记录id
	 * @param strArray
	 */
	public List<String> selectAlterRecordIdInRecordIds(String[] strArray);

	/**
	 * 查询最近的审核不通过的审核单 审核类型为提交审核 submit 1
	 * @param taskRecordId
	 * @return
	 */
	public Map<String, String> selectSubmitAuditRecently(String taskRecordId);
	
}