package com.numberone.quartz.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.numberone.quartz.domain.EmpAttenddayQuartz;

/**
 * 报表任务调度持久层接口
 * <p>Title:ReportJobMapper</p>
 * @author 段佳佳
 * @date 2020年5月8日
 */
public interface ReportJobMapper
{

	/**
	 * 查询改员工上月考勤月信息
	 * @param userId
	 * @param lastMonth 
	 */
	public List<EmpAttenddayQuartz> selectAttenddayByMonthAndUserId(@Param("userId") Long userId,@Param("month") Date month);

	/**
	 * 查询上月员工月报
	 * @param userId
	 * @param month
	 */
	public Map<String,Object> selectEmpAttendReportByUserIdAndMonth(@Param("userId") Long userId,@Param("month") Date month);

	/**
	 * 工作日表中的工作日天数
	 * @param lastMonth
	 * @return
	 */
	public int selectWorkdayIsWorkdayCount(Date month);

	/**
	 * 查询上月的考勤单信息
	 * @param queryMap
	 * @return
	 */
	public List<Map<String, Object>> selectAttendBillList(Map<String, Object> queryMap);

	/**
	 * 查询考勤单日项
	 * @param queryMap
	 * @return
	 */
	public List<Map<String, Object>> selectAttendBillItems(Map<String, Object> queryMap);

	/**
	 * 查询延时工单
	 * @param queryMap
	 * @return
	 */
	public List<Map<String, Object>> selectOvertimeBillList(Map<String, Object> queryMap);

	/**
	 * 查询延时工单项 emp_overtime_user
	 * @param queryMap
	 * @return
	 */
	public Map<String, Object> selectOvertimeBillItem(Map<String, Object> queryMap);

	/**
	 * 新增员工考勤月报
	 * @param empReport
	 */
	public void insertEmpAttendReport(Map<String, Object> empReport);

	/**
	 * 更新员工考勤月报
	 * @param empReport
	 */
	public void updateEmpAttendReport(Map<String, Object> empReport);

	/**
	 * 查询所有部门
	 * @return
	 */
	public List<Map<String, Object>> selectDeptAll();

	/**
	 * 查询部门月报
	 * @param deptId
	 * @param month
	 * @return
	 */
	public Map<String, Object> selectDeptAttendReportByUserIdAndMonth(@Param("dept_id") Integer deptId,@Param("month") Date month);

	/**
	 * 新增部门考勤月报
	 * @param deptReport
	 */
	public void insertDeptAttendReport(Map<String, Object> deptReport);

	/**
	 * 更新部门考勤月报
	 * @param deptReport
	 */
	public void updateDeptAttendReport(Map<String, Object> deptReport);

	public List<Map<String, Object>> selectEmpAttendReportByDeptIdAndMonth(@Param("deptId") Integer deptId,@Param("month") Date month);

	/**
	 * 查询工时记录
	 * @param queryMap
	 * @return
	 */
	public Map<String, Object> selectMonthHour(Map<String, Object> queryMap);

	/**
	 * 新增工时记录
	 * @param monthHour
	 */
	public void insertMonthHour(Map<String, Object> monthHour);

	/**
	 * 查询员工任务月报
	 * @param queryMap
	 * @return
	 */
	public Map<String, Object> selectEmpTaskReport(Map<String, Object> queryMap);

	/**
	 * 新增员工任务月报
	 * @param empReport
	 */
	public void insertEmpTaskReport(Map<String, Object> empReport);

	/**
	 * 更新员工任务月报
	 * @param empReport
	 */
	public void updateEmpTaskReport(Map<String, Object> empReport);
	/**
	 * 新增部门任务月报
	 * @param empReport
	 */
	public void insertDeptTaskReport(Map<String, Object> empReport);
	
	/**
	 * 更新部门任务月报
	 * @param empReport
	 */
	public void updateDeptTaskReport(Map<String, Object> empReport);

	/**
	 * 查询所有任务
	 * @param queryMap
	 * @return
	 */
	public List<Map<String, Object>> selectTaskList(Map<String, Object> queryMap);

	public Map<String, Object> selectDeptTaskReport(Map<String, Object> queryMap);

	public List<Map<String, Object>> selectEmpTaskReportList(Map<String, Object> queryMap);
}
