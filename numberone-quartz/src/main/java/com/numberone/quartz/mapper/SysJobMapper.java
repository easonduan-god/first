package com.numberone.quartz.mapper;

import java.util.Date;
import java.util.List;

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
	
	public void insertAttendday(EmpAttenddayQuartz empAttendday);

	public void updateAttendday(EmpAttenddayQuartz empAttendday);

	public EmpNonworkdayQuartz selectNowWorkday();
}
