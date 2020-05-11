package com.numberone.work.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.common.support.Convert;
import com.numberone.common.utils.StringUtils;
import com.numberone.work.domain.WorkTaskDeptReport;
import com.numberone.work.domain.WorkTaskEmpReport;
import com.numberone.work.mapper.WorkReportMapper;
import com.numberone.work.service.IWorkReportService;

/**
 * 员工任务月报：存储员工每月处理任务数据 服务层实现
 * 
 * @author eason
 * @date 2020-05-09
 */
@Service
public class WorkReportServiceImpl implements IWorkReportService 
{
	@Autowired
	private WorkReportMapper workReportMapper;

	
	/**
     * 查询员工任务月报：存储员工每月处理任务数据列表
     * 
     * @param workTaskEmpReport 员工任务月报：存储员工每月处理任务数据信息
     * @return 员工任务月报：存储员工每月处理任务数据集合
     */
	@Override
	public List<WorkTaskEmpReport> selectWorkTaskEmpReportList(WorkTaskEmpReport workTaskEmpReport)
	{
		Map<String, Object> params = workTaskEmpReport.getParams();
		String userIds = (String) params.get("userIds");
		if(!StringUtils.isEmpty(userIds)){
			params.put("userIds", Convert.toLongArray(userIds));
		}else{
			params.put("userIds",null);
		}
	    return workReportMapper.selectWorkTaskEmpReportList(workTaskEmpReport);
	}


	@Override
	public WorkTaskDeptReport selectDeptReport(WorkTaskDeptReport workTaskDeptReport) {
		return workReportMapper.selectDeptReport(workTaskDeptReport);
	}

	
}
