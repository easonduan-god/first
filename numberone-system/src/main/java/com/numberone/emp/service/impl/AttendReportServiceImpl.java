package com.numberone.emp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.common.support.Convert;
import com.numberone.common.utils.StringUtils;
import com.numberone.emp.domain.EmpAttendReport;
import com.numberone.emp.domain.EmpDeptAttendReport;
import com.numberone.emp.mapper.EmpAttendReportMapper;
import com.numberone.emp.service.IAttendReportService;

/**
 * 员工考勤月报：用于统计每一个员工的月度考勤，每一个月的月初统计上个月的考勤 服务层实现
 * 
 * @author eason
 * @date 2020-05-09
 */
@Service
public class AttendReportServiceImpl implements IAttendReportService 
{
	@Autowired
	private EmpAttendReportMapper empAttendReportMapper;

	
	/**
     * 查询员工考勤月报：用于统计每一个员工的月度考勤，每一个月的月初统计上个月的考勤列表
     * 
     * @param empAttendReport 员工考勤月报：用于统计每一个员工的月度考勤，每一个月的月初统计上个月的考勤信息
     * @return 员工考勤月报：用于统计每一个员工的月度考勤，每一个月的月初统计上个月的考勤集合
     */
	@Override
	public List<EmpAttendReport> selectEmpAttendReportList(EmpAttendReport empAttendReport)
	{
		
		
		Map<String, Object> params = empAttendReport.getParams();
		String userIds = (String) params.get("userIds");
		if(!StringUtils.isEmpty(userIds)){
			params.put("userIds", Convert.toLongArray(userIds));
		}else{
			params.put("userIds",null);
		}
	    return empAttendReportMapper.selectEmpAttendReportList(empAttendReport);
	}


	@Override
	public EmpDeptAttendReport selectDeptReport(EmpDeptAttendReport empDeptAttendReport) {
		return empAttendReportMapper.selectDeptReport(empDeptAttendReport);
	}
	
	
}
