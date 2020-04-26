package com.numberone.quartz.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.numberone.quartz.domain.EmpAttenddayQuartz;
import com.numberone.quartz.domain.EmpAttendinfoQuartz;
import com.numberone.quartz.domain.EmpNonworkdayQuartz;
import com.numberone.quartz.domain.SysJob;
import com.numberone.quartz.domain.SysUserQuartz;

/**
 * 调度任务信息 数据层
 * 
 * @author numberone
 */
public interface SysJobMapper
{
    /**
     * 查询调度任务日志集合
     * 
     * @param job 调度信息
     * @return 操作日志集合
     */
    public List<SysJob> selectJobList(SysJob job);

    /**
     * 查询所有调度任务
     * 
     * @return 调度任务列表
     */
    public List<SysJob> selectJobAll();

    /**
     * 通过调度ID查询调度任务信息
     * 
     * @param jobId 调度ID
     * @return 角色对象信息
     */
    public SysJob selectJobById(Long jobId);

    /**
     * 通过调度ID删除调度任务信息
     * 
     * @param jobId 调度ID
     * @return 结果
     */
    public int deleteJobById(Long jobId);

    /**
     * 批量删除调度任务信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteJobByIds(Long[] ids);

    /**
     * 修改调度任务信息
     * 
     * @param job 调度任务信息
     * @return 结果
     */
    public int updateJob(SysJob job);

    /**
     * 新增调度任务信息
     * 
     * @param job 调度任务信息
     * @return 结果
     */
    public int insertJob(SysJob job);
    
    /**
     * 查询所有员工
     * @param: @return 参数说明
     * @return: SysUserQuartz 返回类型
     * @throws
     */
	public List<SysUserQuartz> selectUserAll();
	
	/**
	 * 根据员工id查询员工打卡信息 前一天6点到当前日期5点
	 * @param: @param userId
	 * @param: @return 参数说明
	 * @return: EmpAttendinfo 返回类型
	 * @throws
	 */
	public List<EmpAttendinfoQuartz> selectAttendBetween6To5(Long userId);
	
	/**
	 * 根据员工id查询员工打卡次数，时间间隔为上班时间9到17点
	 * @param: @param userId
	 * @param: @return 参数说明
	 * @return: int 返回类型
	 * @throws
	 */
	public int selectAttendinfoCountBetween9to17(Long userId);
	
	/**
	 * @param userId 
	 * 获取下班打卡时间，也就是8点半以后两次打卡记录
	 * @param: @return 参数说明
	 * @return: Date 返回类型
	 * @throws
	 */
	public List<Date> getOffWorkTime(Long userId);
	
	/**
	 * 查询本次考勤区间的考勤记录
	 * @param: @param userId
	 * @param: @return 参数说明
	 * @return: EmpAttenddayQuartz 返回类型
	 * @throws
	 */
	public EmpAttenddayQuartz selectEmpAttenddayByUserId(Long userId);
	
	/**
	 * 查询对应的考勤日表emp_attendday信息 考勤结果(0正常 1迟到 2早退 3旷工 4请假)只能是 0 1 2
	 * @param userId
	 * @param attendDate
	 * @return
	 */
	public EmpAttenddayQuartz selectEmpAttenddayResultIn0_1_2ByUserIdAndAttendDate(@Param("userId") Long userId,@Param("attendDate") Date attendDate);
	
	public void insertAttendday(EmpAttenddayQuartz empAttendday);

	public void updateAttendday(EmpAttenddayQuartz empAttendday);

	public List<EmpNonworkdayQuartz> selectNowWorkdayList();

	/**
	 * 根据当前日期查询所有审核通过 未生效的延时工单（只管开始时间）
	 * @param attendDate
	 * @return
	 */
	public List<Map<String, Object>> selectNoActiveOvertimeBillByAttendDate(Date attendDate);

	/**
	 * 用于定时任务的更新年假信息 主要是更新天数
	 */
	public int updateYearVacationForQuartz(Double days);

	/**
	 * 更新延时工单完成状态为 已生效2
	 * @param overtimeBill
	 */
	public int updateOverTimeToEffect(Map<String, Object> overtimeBill);

	/**
	 * 获取所有已审核通过的忘记打卡未生效考勤单
	 * @return
	 */
	public List<Map<String, Object>> selectNoActiveForgetClock();

	/**
	 * 根据取出的考勤日期查询考勤日信息 外加条件考勤结果 用户id
	 * @param startDate
	 * @param userId
	 * @return
	 */
	public EmpAttenddayQuartz selectAttenddayByAttendateAndUserId(@Param("attendDate")Date attendDate,@Param("userId") Long userId,@Param("attendResult") Integer attendResult);

	/**
	 * 根据userid和考勤日期查询延时工单
	 * @param userId
	 * @param attendDate 
	 * @return
	 */
	public Map<String, Object> selectNoActiveOvertimeBillByUserId(@Param("userId")Long userId,@Param("startDate") Date attendDate);

	/**
	 * 更新考勤单为已生效2
	 * @param overtimeBill
	 */
	public void updateAttendBillToEffect(Map<String, Object> overtimeBill);

	/**
	 * 查询此延时工单对应的该用户的有效的考勤日历项
	 * @param userId
	 * @param startDate 
	 */
	public Map<String,Object> selectActiveCalendarByUserIdAndAttendDate(@Param("userId")Long userId,@Param("attendDate") Date attendDate);

	/**
	 * 将日历项记为无效 0
	 * @param calendar
	 */
	public void updateCalendarToNoEffect(Map<String, Object> calendar);

	public SysUserQuartz selectUserByUserId(Long userId);

	/**
	 * 新增日历
	 */
	public void insertCalendar(Map<String,Object> newCalendar);

	/**
	 * 获取所有已审核通过的未完全生效的事假考勤单，条件为当前考勤日期
	 * @param attendDate
	 * @return
	 */
	public List<Map<String, Object>> selectNoEntireActivePersonalLeave(Date attendDate);
	/**
	 * 获取所有已审核通过的未完全生效的年假 调休假考勤单，条件为当前考勤日期
	 * @param attendDate
	 * @return
	 */
	public List<Map<String, Object>> selectNoEntireActive_Year_AdjustLeave(Date attendDate);

	/**
	 * 查询未生效的考勤单日项
	 * @param personalLeave
	 * @return
	 */
	public List<Map<String, Object>> selectAttendBillItems(Map<String, Object> personalLeave);

	public void updateEmpAttendBillLeavedayItems(Map<String, Object> attendBillItem);

	public void updateAttendBill(Map<String, Object> attendBill);

	public Map<String, Object> selectActiveWorkdayCalendar(Date workdate);

	/**
	 * 更新工作日历到无效
	 * @param calendar
	 */
	public void updateWorkdayCalendarToNoEffect(Map<String, Object> calendar);

	/**
	 * 新增工作日历
	 * @param newCalendar
	 */
	public void insertWorkdayCalendar(Map<String, Object> newCalendar);

	public EmpNonworkdayQuartz selectNowWorkday();

	/**
	 * 更新
	 * @param newCalendar
	 */
	public void updateWorkdayCalendar(Map<String, Object> newCalendar);

	public EmpAttenddayQuartz selectEmpAttenddayResultIn1_2_3_5ByUserIdAndAttendDate(@Param("userId") Long userId,@Param("attendDate") Date attendDate);
}
