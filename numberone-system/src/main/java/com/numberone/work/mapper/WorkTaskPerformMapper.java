package com.numberone.work.mapper;

import java.util.List;
import java.util.Map;

import com.numberone.work.domain.WorkTaskPerform;	

/**
 * 任务执行 数据层
 * 
 * @author eason
 * @date 2020-04-11
 */
public interface WorkTaskPerformMapper 
{
	/**
     * 查询任务执行信息
     * 
     * @param taskPerformId 任务执行ID
     * @return 任务执行信息
     */
	public WorkTaskPerform selectWorkTaskPerformById(String taskPerformId);
	
	/**
     * 查询任务执行列表
     * 
     * @param workTaskPerform 任务执行信息
     * @return 任务执行集合
     */
	public List<WorkTaskPerform> selectWorkTaskPerformList(WorkTaskPerform workTaskPerform);
	
	/**
     * 新增任务执行
     * 
     * @param workTaskPerform 任务执行信息
     * @return 结果
     */
	public int insertWorkTaskPerform(WorkTaskPerform workTaskPerform);
	
	/**
     * 修改任务执行
     * 
     * @param workTaskPerform 任务执行信息
     * @return 结果
     */
	public int updateWorkTaskPerform(WorkTaskPerform workTaskPerform);
	
	/**
     * 删除任务执行
     * 
     * @param taskPerformId 任务执行ID
     * @return 结果
     */
	public int deleteWorkTaskPerformById(String taskPerformId);
	
	/**
     * 批量删除任务执行
     * 
     * @param taskPerformIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteWorkTaskPerformByIds(String[] taskPerformIds);
	
	/**
	 * 通过任务记录id查询任务执行信息
	 * @param taskRecordId
	 * @return
	 */
	public WorkTaskPerform selectPerformByTaskRecordId(String taskRecordId);

	/**
	 * 查询变更前执行
	 * @param taskRecordId
	 * @return
	 */
	public Map<String,String> selectPerformBeforeAlter(String taskRecordId);
	
	/**
	 * 根据taskrecordId批量删除执行表数据
	 * @param strArray
	 */
	public int deletePerformByTaskRecordIds(String[] strArray);

	/**
	 * 用作更改 根据任务执行id查询
	 * @param taskPerformId
	 * @return
	 */
	public WorkTaskPerform selectWorkTaskPerformByIdAlter(String taskPerformId);
	
}