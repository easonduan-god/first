package com.numberone.work.mapper;

import com.numberone.work.domain.WorkTaskDeptReport;
import com.numberone.work.domain.WorkTaskEmpReport;
import java.util.List;	

/**
 * 员工任务月报：存储员工每月处理任务数据 数据层
 * 
 * @author eason
 * @date 2020-05-09
 */
public interface WorkReportMapper 
{
	
	/**
     * 查询员工任务月报：存储员工每月处理任务数据列表
     * 
     * @param workTaskEmpReport 员工任务月报：存储员工每月处理任务数据信息
     * @return 员工任务月报：存储员工每月处理任务数据集合
     */
	public List<WorkTaskEmpReport> selectWorkTaskEmpReportList(WorkTaskEmpReport workTaskEmpReport);
	
	

	public WorkTaskDeptReport selectDeptReport(WorkTaskDeptReport workTaskDeptReport);
	
}