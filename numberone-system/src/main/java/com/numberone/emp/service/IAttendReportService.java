package com.numberone.emp.service;

import com.numberone.emp.domain.EmpAttendReport;
import com.numberone.emp.domain.EmpDeptAttendReport;

import java.util.List;

/**
 * 员工考勤月报：用于统计每一个员工的月度考勤，每一个月的月初统计上个月的考勤 服务层
 * 
 * @author eason
 * @date 2020-05-09
 */
public interface IAttendReportService 
{
	
	/**
     * 查询员工考勤月报：用于统计每一个员工的月度考勤，每一个月的月初统计上个月的考勤列表
     * 
     * @param empAttendReport 员工考勤月报：用于统计每一个员工的月度考勤，每一个月的月初统计上个月的考勤信息
     * @return 员工考勤月报：用于统计每一个员工的月度考勤，每一个月的月初统计上个月的考勤集合
     */
	public List<EmpAttendReport> selectEmpAttendReportList(EmpAttendReport empAttendReport);

	public EmpDeptAttendReport selectDeptReport(EmpDeptAttendReport empDeptAttendReport);
	
	
}
