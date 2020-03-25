package com.numberone.quartz.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.common.config.Global;
import com.numberone.common.constant.AttendResult;
import com.numberone.common.constant.ScheduleConstants;
import com.numberone.common.support.Convert;
import com.numberone.common.utils.DateUtils;
import com.numberone.common.utils.StringUtils;
import com.numberone.quartz.domain.EmpAttenddayQuartz;
import com.numberone.quartz.domain.EmpAttendinfoQuartz;
import com.numberone.quartz.domain.EmpNonworkdayQuartz;
import com.numberone.quartz.domain.SysJob;
import com.numberone.quartz.domain.SysUserQuartz;
import com.numberone.quartz.mapper.SysJobMapper;
import com.numberone.quartz.service.ISysJobService;
import com.numberone.quartz.util.CronUtils;
import com.numberone.quartz.util.ScheduleUtils;

/**
 * 定时任务调度信息 服务层
 * 
 * @author numberone
 */
@Service
public class SysJobServiceImpl implements ISysJobService
{
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SysJobMapper jobMapper;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init()
    {
        List<SysJob> jobList = jobMapper.selectJobAll();
        for (SysJob job : jobList)
        {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getJobId());
            // 如果不存在，则创建
            if (cronTrigger == null)
            {
                ScheduleUtils.createScheduleJob(scheduler, job);
            }
            else
            {
                ScheduleUtils.updateScheduleJob(scheduler, job);
            }
        }
    }

    /**
     * 获取quartz调度器的计划任务列表
     * 
     * @param job 调度信息
     * @return
     */
    @Override
    public List<SysJob> selectJobList(SysJob job)
    {
        return jobMapper.selectJobList(job);
    }

    /**
     * 通过调度任务ID查询调度信息
     * 
     * @param jobId 调度任务ID
     * @return 调度任务对象信息
     */
    @Override
    public SysJob selectJobById(Long jobId)
    {
        return jobMapper.selectJobById(jobId);
    }

    /**
     * 暂停任务
     * 
     * @param job 调度信息
     */
    @Override
    public int pauseJob(SysJob job)
    {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            ScheduleUtils.pauseJob(scheduler, job.getJobId());
        }
        return rows;
    }

    /**
     * 恢复任务
     * 
     * @param job 调度信息
     */
    @Override
    public int resumeJob(SysJob job)
    {
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            ScheduleUtils.resumeJob(scheduler, job.getJobId());
        }
        return rows;
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     * 
     * @param job 调度信息
     */
    @Override
    public int deleteJob(SysJob job)
    {
        int rows = jobMapper.deleteJobById(job.getJobId());
        if (rows > 0)
        {
            ScheduleUtils.deleteScheduleJob(scheduler, job.getJobId());
        }
        return rows;
    }

    /**
     * 批量删除调度信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public void deleteJobByIds(String ids)
    {
        Long[] jobIds = Convert.toLongArray(ids);
        for (Long jobId : jobIds)
        {
            SysJob job = jobMapper.selectJobById(jobId);
            deleteJob(job);
        }
    }

    /**
     * 任务调度状态修改
     * 
     * @param job 调度信息
     */
    @Override
    public int changeStatus(SysJob job)
    {
        int rows = 0;
        String status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status))
        {
            rows = resumeJob(job);
        }
        else if (ScheduleConstants.Status.PAUSE.getValue().equals(status))
        {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * 立即运行任务
     * 
     * @param job 调度信息
     */
    @Override
    public int run(SysJob job)
    {
        return ScheduleUtils.run(scheduler, selectJobById(job.getJobId()));
    }

    /**
     * 新增任务
     * 
     * @param job 调度信息 调度信息
     */
    @Override
    public int insertJobCron(SysJob job)
    {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.insertJob(job);
        if (rows > 0)
        {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return rows;
    }

    /**
     * 更新任务的时间表达式
     * 
     * @param job 调度信息
     */
    @Override
    public int updateJobCron(SysJob job)
    {
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            ScheduleUtils.updateScheduleJob(scheduler, job);
        }
        return rows;
    }
    
    /**
     * 校验cron表达式是否有效
     * 
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression)
    {
        return CronUtils.isValid(cronExpression);
    }
	@Override
	public void updateAttendInfo() {
		Boolean late_flag = false;//迟到状态
		Boolean early_flag = false;//早退状态
		Boolean absent_flag = false;//旷工状态
		Boolean insert_flag = false;//新增状态
		Boolean work_flag = true;//工作日状态
		
		//---------------------------------------------------------------
		//0.1 20200204 添加非工作日考勤
			//查询当天的工作日是否是休息日，包括周末，法定假日，公司调休
			//同时查询原本是双休日的日期，若被指定为工作日的话，就正常考勤
			//查询当前的日程
		
		EmpNonworkdayQuartz workday = jobMapper.selectNowWorkday();
		//判断考勤日是否是双休日,1、2分别是星期天和星期一 我要获取昨天的日期，故如下设计
		if( DateUtils.dayOfWeek(new Date())==1 || DateUtils.dayOfWeek(new Date())==2 ){
			work_flag = false;
		}
		//日程状态：(0正常 1工作日 2休息日)
		if(workday.getWorkdateFlag()==2){
			work_flag = false;
		}
		//工作日
		if(workday.getWorkdateFlag()==1){
			work_flag = true;
		}
		
		
		//1、查询所有员工，遍历员工；
		List<SysUserQuartz> userList = jobMapper.selectUserAll();
		for (SysUserQuartz user : userList) {
			Long userId = user.getUserId();
			//2、根据员工id查询该员工前一天6点到当前日期5点的打卡数据；
			List<EmpAttendinfoQuartz> empAttendinfoList = jobMapper.selectAttendBetween6To5(userId);
			
			//---------------------------------------------------------------
			//0.2 20200204 添加非工作日考勤
				//若休息日当天没有打卡
			//---------------------------------------------------------------
			if(work_flag == false && empAttendinfoList.size()<=0){
				continue;
			}else{
				//查询上班时间打卡次数
				int row = jobMapper.selectAttendinfoCountBetween9to17(userId);
				//创建日常考勤对象，用于存储考勤数据,先从数据库查询
				EmpAttenddayQuartz empAttendDay = jobMapper.selectEmpAttenddayByUserId(userId);
				if(empAttendDay==null){
					insert_flag = true;
					empAttendDay = new EmpAttenddayQuartz();
				}
				
				
				//3、先计算员工是否旷工，若上班卡和下班都没打，或者只打了其中之一，记为旷工，旷工后，不需要判断其他情况；
				//一个考勤区间中，只有不大于1一次的考勤记录，必为旷工
				if(empAttendinfoList.size()<=1){
					absent_flag=true;
				}else{
					//若有不少于两次打卡记录，且至少有一次是在上班时间，则不计为旷工
					absent_flag=true;
					if(row>0){
						absent_flag = false;
					}
				}
				
				//---------------------------------------------------------------
				//若休息日有考勤数据，则不设置考勤结果
				if(work_flag==true){
					//4、非旷工状态；
					if(absent_flag==false){
						//获取最开的打卡时间
						EmpAttendinfoQuartz firstInfo = empAttendinfoList.get(0);
						double doubleHours = DateUtils.getHourAndMminutesDoubleValue(firstInfo.getRecordTime());
						//若第一次打卡的时间为上班时间以后，则迟到flag为true
						if( doubleHours > Double.parseDouble(Global.getConfig("OAManage.goWorkTime")) ){
							late_flag = true;
							//6、若为迟到状态，且有在上班时间打卡的记录（不包括上班打卡的记录），记为早退flag为true；
							if( row>1 ){
								early_flag = true;
							}
						}else{
							//5、若非迟到状态，且有在上班时间打卡的记录，早退flag为true；
							if( row>0 ){
								early_flag = true;
							}
						}
						//7、若早退flag和早退flag都为true，记为5迟到+早退；
						if(late_flag==true && early_flag==true){
							empAttendDay.setAttendResult(AttendResult.LATE_EARLY);
						}else if(late_flag==true && early_flag!=true){
							//迟到
							empAttendDay.setAttendResult(AttendResult.LATE);
						}else if(late_flag!=true && early_flag!=true){
							//早退
							empAttendDay.setAttendResult(AttendResult.EARLY);
						}else{
							//正常
							empAttendDay.setAttendResult(AttendResult.NORMAL);
						}
					}else{
						//旷工
						empAttendDay.setAttendResult(AttendResult.ABSENT);
					}
				}//----------------------------
				
				//8、补全数据
				//日常考勤单id
				if(insert_flag==true){
					empAttendDay.setAttenddayId(StringUtils.getUUID());
				}
				//用户id
				empAttendDay.setUserId(user.getUserId());
				//员工工号
				empAttendDay.setEmpId(user.getEmpId());
				//考勤日期
				Date attendDate = DateUtils.getOnlyDate(DateUtils.getAroundDate(new Date(), -1));
				empAttendDay.setAttendDate(attendDate);
				//星期
				empAttendDay.setWeek(DateUtils.dayOfWeek(attendDate));
				if(empAttendinfoList!=null && empAttendinfoList.size()!=0){
					//最开始打卡时间
					Date firstTime = empAttendinfoList.get(0).getRecordTime();
					empAttendDay.setFirstTime(firstTime);
					//下班时间
					List<Date> offWorkTimes = jobMapper.getOffWorkTime(userId);
					//补全最晚打卡时间，其实是下班第一次打卡时间，也就是9点以后的第一次下班打卡时间(不包括上班时间)；
					if(offWorkTimes.size()!=0){
						//如果包括上班打卡记录
						if(offWorkTimes.get(0).compareTo(empAttendinfoList.get(0).getRecordTime())==0){
							Date offWorkTime = offWorkTimes.get(1);
							empAttendDay.setLastTime(offWorkTime);
							//--200215调整 若迟到或早退的时间大于30个小时，记为矿工
							//上班时间
							Date goWorkTimeFixed = DateUtils.getGoWorkTime();
							//下班时间
							Date offWorkTimeFixed = DateUtils.getOffWorkTime();
							if(DateUtils.getDateIntervalInHours(goWorkTimeFixed,firstTime)>0.5 || DateUtils.getDateIntervalInHours(offWorkTime,offWorkTimeFixed)>0.5){
								//旷工
								empAttendDay.setAttendResult(AttendResult.ABSENT);
								empAttendDay.setAdditionalTime(0.0);
							}else{
								//补全额外时间，考勤正常的才可以
								double additionalTime = 0;
								if(empAttendDay.getAttendResult()==0){
									additionalTime = Math.floor(DateUtils.getDateIntervalInHours(offWorkTime,DateUtils.setHours(attendDate, 17)));
								}
								empAttendDay.setAdditionalTime(additionalTime>=0?additionalTime:0);
							}
							
						}else{
							Date offWorkTime = offWorkTimes.get(0);
							empAttendDay.setLastTime(offWorkTime);
							//--200215调整 若迟到或早退的时间大于30个小时，记为矿工
							//上班时间
							Date goWorkTimeFixed = DateUtils.getGoWorkTime();
							//下班时间
							Date offWorkTimeFixed = DateUtils.getOffWorkTime();
							if(DateUtils.getDateIntervalInHours(goWorkTimeFixed,firstTime)>0.5 || DateUtils.getDateIntervalInHours(offWorkTime,offWorkTimeFixed)>0.5){
								//旷工
								empAttendDay.setAttendResult(AttendResult.ABSENT);
								empAttendDay.setAdditionalTime(0.0);
							}else{
								//补全额外时间
								double additionalTime = DateUtils.getDateIntervalInHours(offWorkTime,DateUtils.setHours(attendDate, 17))*1.0;
								empAttendDay.setAdditionalTime(additionalTime>=0?additionalTime:0);
							}
						}
					}
				}
				empAttendDay.setAttendType(null);
				
				//新增日常考勤
				if(insert_flag==true){
					jobMapper.insertAttendday(empAttendDay);
					//更新日常考勤
				}else{
					jobMapper.updateAttendday(empAttendDay);
				}
				
			}
		}
	}
    
}
