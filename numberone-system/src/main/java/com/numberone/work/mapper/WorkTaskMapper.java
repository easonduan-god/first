package com.numberone.work.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.numberone.work.domain.WorkTask;	

/**
 * 任务分配，执行，审核 数据层
 * 
 * @author eason
 * @date 2020-04-11
 */
public interface WorkTaskMapper 
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
     * 删除任务分配，执行，审核
     * 
     * @param taskRecordId 任务分配，执行，审核ID
     * @return 结果
     */
	public int deleteWorkTaskById(String taskRecordId);
	
	/**
     * 批量删除任务分配，执行，审核
     * 
     * @param taskRecordIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteWorkTaskByIds(String[] taskRecordIds);

	/**
	 * 查询当前用户已分配未完成任务
	 * @param: @param userId
	 * @param: @return
	 * @return: List<SysUser>
	 */
	public List<WorkTask> selectNoCompleteByUserId(Long userId);
	
	/**
	 * 查询本部门任务待办
	 * @param workTask
	 * @param deptId
	 * @return
	 */
	public List<WorkTask> selectListByDeptId(@Param("workTask") WorkTask workTask,@Param("deptId") Long deptId);
	
	/**
	 * 查询个人任务待办
	 * @param workTask
	 * @param deptId
	 * @return
	 */
	public List<WorkTask> selectListOfMine(@Param("workTask") WorkTask workTask,@Param("userId") Long userId);
	
	/**
	 * 查询任务状态为 待处理1 因为只有这两个才可以执行
	 * @param taskRecordId
	 * @return
	 */
	public WorkTask selectFlag1And2And5(String taskRecordId);
	
	/**
	 * 查询不可编辑的数量根据recordids
	 * @param strArray
	 * @return
	 */
	public int selectTaskNoEditByIds(String[] ids);
	
	/**
	 * 此更新用于更改执行人
	 * @param task
	 * @return
	 */
	public int updateWorkTaskForAlter(WorkTask task);
	
}