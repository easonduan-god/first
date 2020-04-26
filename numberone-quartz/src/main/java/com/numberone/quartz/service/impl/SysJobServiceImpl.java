package com.numberone.quartz.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.common.config.Global;
import com.numberone.common.constant.AttendResult;
import com.numberone.common.constant.ScheduleConstants;
import com.numberone.common.support.Convert;
import com.numberone.common.utils.Arith;
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
    /**
     * 定时更新员工考勤信息
     */
	@Override
	public void updateAttendInfo() {
		//上班时间
		Date goWorkTimeFixed = DateUtils.getGoWorkTime();
		//下班时间
		Date offWorkTimeFixed = DateUtils.getOffWorkTime();
    	Double offWorkHours = Double.parseDouble(Global.getConfig("OAManage.offWorkTime"));
		//200331 只允许在早上5点到六点执行该任务
		double hour = DateUtils.getHourAndMminutesDoubleValue(new Date());
		if(hour > 6 || hour < 5){
			System.out.println("非考勤执行时间");
			return;
		}
		
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
		if(workday!=null){
			//日程状态：(0正常 1工作日 2休息日)
			if(workday.getWorkdateFlag()==2){
				work_flag = false;
			}
			//工作日
			if(workday.getWorkdateFlag()==1){
				work_flag = true;
			}
		}
		
		Date attendDate = DateUtils.getOnlyDate(DateUtils.getAroundDate(new Date(), -1));
		//1、查询所有员工，遍历员工；
		List<SysUserQuartz> userList = jobMapper.selectUserAll();
		for (SysUserQuartz user : userList) {
			Long userId = user.getUserId();
			//2、根据员工id查询该员工前一天6点到当前日期5点的打卡数据；
			List<EmpAttendinfoQuartz> empAttendinfoList = jobMapper.selectAttendBetween6To5(userId);
				//人为的制造两次正常考勤信息
				EmpAttendinfoQuartz attendInfo1 = new EmpAttendinfoQuartz();
				attendInfo1.setAttendDate(attendDate);
				attendInfo1.setEmpId(user.getEmpId());
				attendInfo1.setUserId(userId);
				attendInfo1.setWeek(DateUtils.dayOfWeek(attendDate));
				attendInfo1.setRecordTime(DateUtils.getDateSomeTime(attendDate, 9, 0, 0));
				EmpAttendinfoQuartz attendInfo2 = new EmpAttendinfoQuartz();
				attendInfo2.setAttendDate(attendDate);
				attendInfo2.setEmpId(user.getEmpId());
				attendInfo2.setUserId(userId);
				attendInfo2.setWeek(DateUtils.dayOfWeek(attendDate));
				attendInfo2.setRecordTime(DateUtils.getDateSomeTime(attendDate, 17, 0, 0));
				empAttendinfoList.add(attendInfo1);
				empAttendinfoList.add(attendInfo2);
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

							if(DateUtils.getDateIntervalInHours(goWorkTimeFixed,firstTime)>0.5 || DateUtils.getDateIntervalInHours(offWorkTime,offWorkTimeFixed)>0.5){
								//旷工
								if(work_flag)
									empAttendDay.setAttendResult(AttendResult.ABSENT);
							}
							//200408修改 无论是否旷工都要记录额外时间
							if(offWorkTime!=null){
								double additionalTime = Math.floor(DateUtils.getDateIntervalInHours(offWorkTime,DateUtils.setHours(attendDate, 19)));
								empAttendDay.setAdditionalTime(additionalTime>=0?additionalTime:0);
							}else{
								empAttendDay.setAdditionalTime(0.0);
							}
							
							
						}else{
							Date offWorkTime = offWorkTimes.get(0);
							empAttendDay.setLastTime(offWorkTime);
							//--200215调整 若迟到或早退的时间大于30分钟，记为矿工
							if(DateUtils.getDateIntervalInHours(goWorkTimeFixed,firstTime)>0.5 || DateUtils.getDateIntervalInHours(offWorkTime,offWorkTimeFixed)>0.5){
								//旷工
								if(work_flag)
									empAttendDay.setAttendResult(AttendResult.ABSENT);
							}
							//200408修改 无论是否旷工都要记录额外时间
							if(offWorkTime!=null){
								double additionalTime = Math.floor(DateUtils.getDateIntervalInHours(offWorkTime,DateUtils.setHours(attendDate, 19)));
								empAttendDay.setAdditionalTime(additionalTime>=0?additionalTime:0);
							}else{
								empAttendDay.setAdditionalTime(0.0);
							}
						}
					}
				}
				empAttendDay.setAttendType(null);
				
					//200426 休息日加班 记录额外时间 9点-17点 19点-凌晨四点
				if(work_flag == false){
					//获取上下班时间 goWorkHour offWorkHours goWorkHour
					double firstHour = DateUtils.getHourAndMminutesDoubleValue(empAttendDay.getFirstTime());
					double lastTime = DateUtils.getHourAndMminutesDoubleValue(empAttendDay.getLastTime());
					double additional = 0;
					if(lastTime <= offWorkHours){
						additional = lastTime - firstHour;
					}else{
						additional += 17 - firstHour;
						additional += lastTime>19 ? lastTime-19 : 0;
					}
					empAttendDay.setAdditionalTime(additional);	
				}
				
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

	
	/**
	 * 定时更新延时工单
	 */
	@Override
	public void updateOvertimeBill() {
		Double goWorkTime = Double.parseDouble(Global.getConfig("OAManage.goWorkTime"));
    	Double offWorkTime = Double.parseDouble(Global.getConfig("OAManage.offWorkTime"));
    	double goWorkHour = offWorkTime-goWorkTime;//上班工作多少小时
		//只允许在早上5点到六点执行该任务
		double hour = DateUtils.getHourAndMminutesDoubleValue(new Date());
		if(hour > 6 || hour < 5){
			System.out.println("非更新延时工单时间");
			return;
		}
			//当前考勤日期 指执行这个任务的前一天
			//Date attendDate = DateUtils.getAroundDate(DateUtils.getNowDate(), -1, DateUtils.YYYY_MM_DD);
		//1、所有审核通过 未生效的延时工单
		List<Map<String,Object>> overtimeBillList = jobMapper.selectNoActiveOvertimeBillByAttendDate(null);
		//2、遍历延时工单
		overtimeBillList.forEach(overtimeBill -> {
			//2.1、遍历关联的工作人员
			for (Long userId : Convert.toLongArray(Convert.toStr(overtimeBill.get("work_persons")))) {
				Date attendDate = (Date) overtimeBill.get("start_date");
				//2.2、查询对应的考勤日表emp_attendday信息 考勤结果(0正常 1迟到 2早退 3旷工 4请假)只能是 0 1 2
				EmpAttenddayQuartz attendday = jobMapper.selectEmpAttenddayResultIn0_1_2ByUserIdAndAttendDate(userId,attendDate);
				//2.3、将延时工单中的申请工时与考勤日表中的额外工时进行比对，规则如下
				Double additionalHours = attendday.getAdditionalTime();
				Double applyHours = Convert.toDouble(overtimeBill.get("apply_worktimes"));
				
					//一切以申请工时为准
					//额外>=申请 取申请
					//额外<申请 取额外
				additionalHours = additionalHours >= applyHours ? applyHours : additionalHours;
				//2.4、更新该作人员的年假信息，根据最后得到的加班工时,此处需将额外工时换算成天数
				jobMapper.updateYearVacationForQuartz(Arith.div(additionalHours, goWorkHour, 1));
			}
			//2.3、更新延时工单 完成状态为已生效2    
			jobMapper.updateOverTimeToEffect(overtimeBill);
			
		});
	}

	/**
	 * 更新补卡
	 */
	@Override
	public void updateAttendBill_ForgetClock() {
		Double goWorkTime = Double.parseDouble(Global.getConfig("OAManage.goWorkTime"));
    	Double offWorkTime = Double.parseDouble(Global.getConfig("OAManage.offWorkTime"));
    	double goWorkHour = offWorkTime-goWorkTime;//上班工作多少小时
		//只允许在早上5点到六点执行该任务
		double hour = DateUtils.getHourAndMminutesDoubleValue(new Date());
		if(hour > 6 || hour < 5){
			System.out.println("非更新延时工单时间");
			return;
		}
		//当前考勤日期 指执行这个任务的前一天
		//Date attendDate = DateUtils.getAroundDate(DateUtils.getNowDate(), -1, DateUtils.YYYY_MM_DD);
		//忘记打卡 考勤类型(0事假 1年假 2调休假 3忘记打卡)
		//要点：根据忘记打卡的日期，需补全那一日的延时工单信息，若是存在
		//1、获取所有已审核通过的忘记打卡未生效考勤单
		List<Map<String,Object>> forgetClockList = jobMapper.selectNoActiveForgetClock();
		//2、遍历考勤单
		forgetClockList.forEach(forgetClock -> {
			//2.1、首先处理可能存在的延时工单信息，根据userid查询延时工单
			Long userId = Convert.toLong(forgetClock.get("user_id"));
			//若存在则取出该用户在此补卡工单的开始日期
			Date startDate = (Date) forgetClock.get("start_date");
			Map<String,Object> overtimeBill = jobMapper.selectNoActiveOvertimeBillByUserId(userId,startDate);
				
				//根据取出的考勤日期查询考勤日信息 只查询旷工信息
				EmpAttenddayQuartz attendday = jobMapper.selectAttenddayByAttendateAndUserId(startDate,userId,AttendResult.ABSENT);
				if(attendday==null){
					return;
				}
				//得到额外工时，然后与延时工单中的申请工时比对，规则如下
				Double additionalHours = attendday.getAdditionalTime();
				Double applyHours = Convert.toDouble(overtimeBill.get("apply_worktimes"));
					//一切以申请工时为准
					//额外>=申请 取申请
					//额外<申请 取额外
				additionalHours = additionalHours >= applyHours ? applyHours : additionalHours;
				//2.4、更新该作人员的年假信息，根据最后得到的加班工时,此处需将额外工时换算成天数
				jobMapper.updateYearVacationForQuartz(Arith.div(additionalHours, goWorkHour, 1));
				//更新延时工单 完成状态为已生效2 
				jobMapper.updateOverTimeToEffect(overtimeBill);
			//2.2、处理忘记打卡
					//根据得到的忘记打卡日期查询考勤日信息 (上边代码中已获得)
					//（提醒，补卡只允许补上旷下旷全旷，其他不允许补）
					//故要先判断是否是旷工(已判断)
				//若是旷工，则将考勤日信息中的早晚打卡时间设置为忘记打卡考勤单上的开始结束时间 
				Date startTime = (Date) forgetClock.get("start_time");
				double startHour = DateUtils.getHourAndMminutesDoubleValue(startTime);
		    	Date endTime = (Date) forgetClock.get("end_time");
		    	double endHour = DateUtils.getHourAndMminutesDoubleValue(endTime);
		    	double diffHour = endHour-endHour;//数据差
				//此时再判断考勤日信息中的早晚打卡时间何如
		    	int attendResult = 0;//考勤结果(0正常 1迟到 2早退 3旷工 4请假)
				//晚-早>=goWorkHour 正常
		    	if(diffHour >= goWorkHour){
		    		 attendResult = AttendResult.NORMAL;
		    	}else{
		    		//晚-早<goWorkHour 判断一下 早>goWorkTime-->迟到 晚<offWorkTime-->早退 同时存在-->迟到+早退
		    		if(startHour > goWorkHour && endHour < offWorkTime){
		    			attendResult = AttendResult.LATE_EARLY;
		    		}
		    		if(startHour > goWorkHour){
		    			attendResult = AttendResult.LATE;
		    		}else{
		    			attendResult = AttendResult.EARLY;
		    		}
		    	}
				//更新考勤日信息
		    	attendday.setAttendResult(attendResult);
		    	attendday.setAttendType(3);
		    	jobMapper.updateAttendday(attendday);
			//2.3、更新补卡考勤单为已生效2
		    	jobMapper.updateAttendBillToEffect(overtimeBill);
			//2.4、查询此补卡考勤单对应的该用户的有效的考勤日历项
		    	Map<String,Object> calendar = jobMapper.selectActiveCalendarByUserIdAndAttendDate(userId,startDate);
				//存在则记为无效
		    	if(calendar!=null){
		    		jobMapper.updateCalendarToNoEffect(calendar);
		    	}
				//然后新增考勤日历表记录
		    	//查询用户
		    	SysUserQuartz user = jobMapper.selectUserByUserId(userId);
		    	Map<String,Object> newCalendar = new HashMap<String,Object>();
		    	newCalendar.put("calendar_id",StringUtils.getUUID() );
		    	newCalendar.put("user_id", user.getUserId());
		    	newCalendar.put("emp_id", user.getEmpId());
		    	newCalendar.put("user_name", user.getUserName());
		    	newCalendar.put("dept_id", user.getDeptId());
		    	newCalendar.put("DATE", startDate);
		    	//考勤代码 0事假 1年假 2调休假 3忘记打卡 4迟到 5早退 6矿工 7迟到+早退（迟到） 9休息日 10 工作日
		    	newCalendar.put("attend_code", 3);
		    	newCalendar.put("attend_label", "忘记打卡");
		    	newCalendar.put("effect_flag", 1);
		    	//新增
		    	jobMapper.insertCalendar(newCalendar);
		});
	}

	/**
	 * 更新事假 事假处理 考勤类型(0事假 1年假 2调休假 3忘记打卡)
	 */
	@Override
	public void updateAttendBill_PersonalLeave() {
		Double goWorkTime = Double.parseDouble(Global.getConfig("OAManage.goWorkTime"));
    	Double offWorkTime = Double.parseDouble(Global.getConfig("OAManage.offWorkTime"));
		
		//---------------------------------------------------------------------
		//1、获取所有已审核通过的未完全生效的事假考勤单或者已生效单存在未生效考勤日项
			List<Map<String,Object>> personalLeaveList = jobMapper.selectNoEntireActivePersonalLeave(null);
		//2、遍历考勤单
			personalLeaveList.forEach(personalLeave -> {
				//2.1、查询所对应的考勤日信息（任务执行日截止 不包括执行日）
				List<Map<String, Object>> attendBillItems = jobMapper.selectAttendBillItems(personalLeave);
				Date start_date = (Date) personalLeave.get("start_date");
				Date end_date = (Date) personalLeave.get("end_date");
				//2.2、遍历考勤单日
				attendBillItems.forEach(attendBillItem -> {
					//2.3、获取userid和对应的考勤日期
					Long userId = Convert.toLong(attendBillItem.get("user_id"));
					Date attendDate = (Date) attendBillItem.get("leaveday_item");
					
					//2.4、查询用户当前考勤日期所对应的考勤日信息					
					EmpAttenddayQuartz attendday = jobMapper.selectAttenddayByAttendateAndUserId(attendDate, userId,null);
					//2.5、考勤日项可能存在两种情况，1.申请工时为8 2.小于8
					Double workday_time = (Double) attendBillItem.get("workday_time");
					
					//2.6、若是大于等于8,那么就更新考勤日期对应的考勤日信息，改考勤结果为请假，考勤类型为事假 
					if(workday_time >= 8){
						//并在此处更新日项信息 return继续循环
						attendday.setAttendResult(AttendResult.LEAVE);
						attendday.setAttendType(0);
						jobMapper.updateAttendday(attendday);
						attendBillItem.put("effect_flag", 1);
						jobMapper.updateEmpAttendBillLeavedayItems(attendBillItem);
						
						//更新日历 考勤日项为已生效
						Map<String,Object> calendar = jobMapper.selectActiveCalendarByUserIdAndAttendDate(userId,attendDate);
						//存在则记为无效
				    	if(calendar!=null){
				    		jobMapper.updateCalendarToNoEffect(calendar);
				    	}
						//然后新增考勤日历表记录
				    	//查询用户
				    	SysUserQuartz user = jobMapper.selectUserByUserId(userId);
				    	Map<String,Object> newCalendar = new HashMap<String,Object>();
				    	newCalendar.put("calendar_id",StringUtils.getUUID() );
				    	newCalendar.put("user_id", user.getUserId());
				    	newCalendar.put("emp_id", user.getEmpId());
				    	newCalendar.put("user_name", user.getUserName());
				    	newCalendar.put("dept_id", user.getDeptId());
				    	newCalendar.put("DATE", attendDate);
				    	//考勤代码 0事假 1年假 2调休假 3忘记打卡 4迟到 5早退 6矿工 7迟到+早退（迟到） 9休息日 10 工作日
				    	newCalendar.put("attend_code", 0);
				    	newCalendar.put("attend_label", "事假");
				    	newCalendar.put("effect_flag", 1);
				    	//新增
				    	jobMapper.insertCalendar(newCalendar);
						return;
					}
					//2.7、若是小于8
						//判断这一天是考勤的首日还是结束日
						//首日和尾日（尾日=考勤单结束日期）为同一天，取考勤单开始时间和结束时间
					Double startHour = goWorkTime;
					Double endHour = offWorkTime;
						if(end_date.compareTo(start_date)==0){
							startHour = DateUtils.getHourAndMminutesDoubleValue((Date) personalLeave.get("start_time"));
							endHour = DateUtils.getHourAndMminutesDoubleValue((Date) personalLeave.get("end_time"));
						}else if(end_date.compareTo(attendDate)==0){
						//为尾日，取考勤单结束时间，开始时间为上班时间
							startHour = goWorkTime;
							endHour = DateUtils.getHourAndMminutesDoubleValue((Date) personalLeave.get("end_time"));
						}else{
						//为首日，取考勤单开始时间，结束时间为下班时间
							startHour = DateUtils.getHourAndMminutesDoubleValue((Date) personalLeave.get("start_time"));
							endHour = offWorkTime;
						}
						//若是员工有旷工		更新开始与结束时间为上面最终得到的
						if(attendday.getAttendResult()==AttendResult.ABSENT){
							attendday.setFirstTime(DateUtils.getDateSomeTime(attendDate, startHour.intValue(), (int)((startHour%1.0)*60), 0));
							attendday.setLastTime(DateUtils.getDateSomeTime(attendDate, endHour.intValue(), (int)((endHour%1.0)*60), 0));
							//并在此处更新日项信息 return继续循环
							attendday.setAdditionalTime(0.0);
							attendday.setAttendType(0);
							//申请公式小于7个小时，记为旷工
							Integer attendResult = 0;
							if(workday_time < 7){
								attendResult = AttendResult.ABSENT;
							}else{
					    		//晚-早<goWorkHour 判断一下 早>goWorkTime-->迟到 晚<offWorkTime-->早退 同时存在-->迟到+早退
					    		if(startHour > goWorkTime && endHour < offWorkTime){
					    			attendResult = AttendResult.LATE_EARLY;
					    		}
					    		if(startHour > goWorkTime){
					    			attendResult = AttendResult.LATE;
					    		}else{
					    			attendResult = AttendResult.EARLY;
					    		}
							}
							attendday.setAttendResult(attendResult);
							jobMapper.updateAttendday(attendday);
							//-------------------
							attendBillItem.put("effect_flag", 1);
							jobMapper.updateEmpAttendBillLeavedayItems(attendBillItem);
							
							
							//更新日历 考勤日项为已生效
							Map<String,Object> calendar = jobMapper.selectActiveCalendarByUserIdAndAttendDate(userId,attendDate);
							//存在则记为无效
					    	if(calendar!=null){
					    		jobMapper.updateCalendarToNoEffect(calendar);
					    	}
							//然后新增考勤日历表记录
					    	//查询用户
					    	SysUserQuartz user = jobMapper.selectUserByUserId(userId);
					    	Map<String,Object> newCalendar = new HashMap<String,Object>();
					    	newCalendar.put("calendar_id",StringUtils.getUUID() );
					    	newCalendar.put("user_id", user.getUserId());
					    	newCalendar.put("emp_id", user.getEmpId());
					    	newCalendar.put("user_name", user.getUserName());
					    	newCalendar.put("dept_id", user.getDeptId());
					    	newCalendar.put("DATE", attendDate);
					    	//考勤代码 0事假 1年假 2调休假 3忘记打卡 4迟到 5早退 6矿工 7迟到+早退（迟到） 9休息日 10 工作日
					    	newCalendar.put("attend_code", 0);
					    	newCalendar.put("attend_label", "事假");
					    	newCalendar.put("effect_flag", 1);
					    	//新增
					    	jobMapper.insertCalendar(newCalendar);
							return;
						}
					
					//2.8、考勤单的开始结束时间与考勤日信息中早晚时间取并集，并经如下规则
						//考勤单开始结束小时数
						Double end_start = endHour-startHour;//数据差
						//考勤日早晚小时数
						Double firstHour = DateUtils.getHourAndMminutesDoubleValue(attendday.getFirstTime());
						Double lastHour = DateUtils.getHourAndMminutesDoubleValue(attendday.getLastTime());
						Double last_first = endHour-endHour;//数据差
						
						//考勤结果
						Integer attendResult = 0; 
						
						//若得到的并集中存在空洞如9点-12点 14点-17点 的考勤结果为取时间区间跨度大的 即为考勤日信息的早晚时间
						if((endHour-firstHour) * (endHour-lastHour)>0){//并集 空洞
							if(end_start>last_first){
								firstHour = startHour;
								lastHour = endHour;
							}
							//再加上 若早于9点 则记为早退 若晚于17点 则记为迟到 考勤类型为事假 总上班时间小于7个小时 为旷工
							if(lastHour - firstHour >= 8){
								attendResult = AttendResult.LEAVE;
							}else if(lastHour - firstHour < 7){
								attendResult = AttendResult.ABSENT;
							}else if(firstHour>9 && lastHour<17){
								attendResult = AttendResult.LATE_EARLY;
							}else if(firstHour>9){
								attendResult = AttendResult.LATE;
							}else{
								attendResult = AttendResult.LATE;
							}
							
						//若得到的并集不存在空洞,那么就取交集
						}else{
							//获取并集
							if(endHour-firstHour>end_start){
								lastHour = endHour;
							}else if(lastHour-startHour>last_first){
								firstHour = startHour;
							}else if(end_start>last_first){
								firstHour = startHour;
								lastHour = endHour;
							}
							//再加上 若早于9点 则记为早退 若晚于17点 则记为迟到 考勤类型为事假 总上班时间小于7个小时 为旷工
							if(lastHour - firstHour >= 8){
								attendResult = AttendResult.LEAVE;
							}else if(lastHour - firstHour < 7){
								attendResult = AttendResult.ABSENT;
							}else if(firstHour>9 && lastHour<17){
								attendResult = AttendResult.LATE_EARLY;
							}else if(firstHour>9){
								attendResult = AttendResult.LATE;
							}else{
								attendResult = AttendResult.LATE;
							}
						}
						attendday.setFirstTime(DateUtils.getDateSomeTime(attendDate, firstHour.intValue(), (int)((firstHour%1.0)*60), 0));
						attendday.setLastTime(DateUtils.getDateSomeTime(attendDate, lastHour.intValue(), (int)((lastHour%1.0)*60), 0));
						//并在此处更新日项信息 return继续循环
						attendday.setAdditionalTime(0.0);
						attendday.setAttendType(0);
						attendday.setAttendResult(attendResult);
						jobMapper.updateAttendday(attendday);
						//-------------------
						attendBillItem.put("effect_flag", 1);
						jobMapper.updateEmpAttendBillLeavedayItems(attendBillItem);
						
						//更新日历 考勤日项为已生效
						Map<String,Object> calendar = jobMapper.selectActiveCalendarByUserIdAndAttendDate(userId,attendDate);
						//存在则记为无效
				    	if(calendar!=null){
				    		jobMapper.updateCalendarToNoEffect(calendar);
				    	}
						//然后新增考勤日历表记录
				    	//查询用户
				    	SysUserQuartz user = jobMapper.selectUserByUserId(userId);
				    	Map<String,Object> newCalendar = new HashMap<String,Object>();
				    	newCalendar.put("calendar_id",StringUtils.getUUID() );
				    	newCalendar.put("user_id", user.getUserId());
				    	newCalendar.put("emp_id", user.getEmpId());
				    	newCalendar.put("user_name", user.getUserName());
				    	newCalendar.put("dept_id", user.getDeptId());
				    	newCalendar.put("DATE", attendDate);
				    	//考勤代码 0事假 1年假 2调休假 3忘记打卡 4迟到 5早退 6矿工 7迟到+早退（迟到） 9休息日 10 工作日
				    	newCalendar.put("attend_code", 0);
				    	newCalendar.put("attend_label", "事假");
				    	newCalendar.put("effect_flag", 1);
				    	//新增
				    	jobMapper.insertCalendar(newCalendar);
				    	return;
						
				});
				//2.9、查询是否还存在未生效的日项	
				if(jobMapper.selectAttendBillItems(personalLeave)==null){
					//不存在则更新考勤单为完全生效
					personalLeave.put("complete_flag", 2);
					personalLeave.put("entire_effect", 1);
					jobMapper.updateAttendBill(personalLeave);
				}else{
					//部分生效
					personalLeave.put("complete_flag", 2);
					personalLeave.put("entire_effect", 0);
					jobMapper.updateAttendBill(personalLeave);
				}
			});
	}

	/**
	 * 更新年假 调休假
	 */
	@Override
	public void updateAttendBill_Year_AdjustLeave() {
		Double goWorkTime = Double.parseDouble(Global.getConfig("OAManage.goWorkTime"));
    	Double offWorkTime = Double.parseDouble(Global.getConfig("OAManage.offWorkTime"));
    	double goWorkHour = offWorkTime-goWorkTime;//上班工作多少小时
		
		//---------------------------------------------------------------------
		//1、获取所有已审核通过的未完全生效的事假考勤单或者已生效单存在未生效考勤日项
			List<Map<String,Object>> Year_AdjustLeaveList = jobMapper.selectNoEntireActive_Year_AdjustLeave(null);
		//2、遍历考勤单
			Year_AdjustLeaveList.forEach(Year_AdjustLeave -> {
				//2.1、查询所对应的考勤日信息（任务执行日截止 不包括执行日）
				List<Map<String, Object>> attendBillItems = jobMapper.selectAttendBillItems(Year_AdjustLeave);
				Date start_date = (Date) Year_AdjustLeave.get("start_date");
				Date end_date = (Date) Year_AdjustLeave.get("end_date");
				//2.2、遍历考勤单日
				attendBillItems.forEach(attendBillItem -> {
					//2.3、获取userid和对应的考勤日期
					Long userId = Convert.toLong(attendBillItem.get("user_id"));
					Date attendDate = (Date) attendBillItem.get("leaveday_item");
					
					//2.4、查询用户当前考勤日期所对应的考勤日信息					
					EmpAttenddayQuartz attendday = jobMapper.selectAttenddayByAttendateAndUserId(attendDate, userId,null);
					//2.5、考勤日项可能存在两种情况，1.申请工时为8 2.小于8
					Double workday_time = (Double) attendBillItem.get("workday_time");
					
					//2.6、若是大于等于8,那么就更新考勤日期对应的考勤日信息，改考勤结果为请假，考勤类型为事假 
					if(workday_time >= 8){
						//并在此处更新日项信息 return继续循环
						attendday.setAttendResult(AttendResult.LEAVE);
						attendday.setAttendType((Integer) Year_AdjustLeave.get("attend_type"));
						jobMapper.updateAttendday(attendday);
						attendBillItem.put("effect_flag", 1);
						jobMapper.updateEmpAttendBillLeavedayItems(attendBillItem);
						
						//更新日历 考勤日项为已生效
						Map<String,Object> calendar = jobMapper.selectActiveCalendarByUserIdAndAttendDate(userId,attendDate);
						//存在则记为无效
				    	if(calendar!=null){
				    		jobMapper.updateCalendarToNoEffect(calendar);
				    	}
						//然后新增考勤日历表记录
				    	//查询用户
				    	SysUserQuartz user = jobMapper.selectUserByUserId(userId);
				    	Map<String,Object> newCalendar = new HashMap<String,Object>();
				    	newCalendar.put("calendar_id",StringUtils.getUUID() );
				    	newCalendar.put("user_id", user.getUserId());
				    	newCalendar.put("emp_id", user.getEmpId());
				    	newCalendar.put("user_name", user.getUserName());
				    	newCalendar.put("dept_id", user.getDeptId());
				    	newCalendar.put("DATE", attendDate);
				    	//考勤代码 0事假 1年假 2调休假 3忘记打卡 4迟到 5早退 6矿工 7迟到+早退（迟到） 9休息日 10 工作日
				    	newCalendar.put("attend_code", (Integer) Year_AdjustLeave.get("attend_type"));
				    	newCalendar.put("attend_label", Year_AdjustLeave.get("attend_type").equals(1)?"年假":"调休假");
				    	newCalendar.put("effect_flag", 1);
				    	//新增
				    	jobMapper.insertCalendar(newCalendar);
						return;
					}
					//2.7、若是小于8
						//判断这一天是考勤的首日还是结束日
						//首日和尾日（尾日=考勤单结束日期）为同一天，取考勤单开始时间和结束时间
					Double startHour = offWorkTime;
					Double endHour = offWorkTime;
						if(end_date.compareTo(start_date)==0){
							startHour = DateUtils.getHourAndMminutesDoubleValue((Date) Year_AdjustLeave.get("start_time"));
							endHour = DateUtils.getHourAndMminutesDoubleValue((Date) Year_AdjustLeave.get("end_time"));
						}else if(end_date.compareTo(attendDate)==0){
						//为尾日，取考勤单结束时间，开始时间为上班时间
							startHour = goWorkTime;
							endHour = DateUtils.getHourAndMminutesDoubleValue((Date) Year_AdjustLeave.get("end_time"));
						}else{
						//为首日，取考勤单开始时间，结束时间为下班时间
							startHour = DateUtils.getHourAndMminutesDoubleValue((Date) Year_AdjustLeave.get("start_time"));
							endHour = offWorkTime;
						}
						//若是员工有旷工		更新开始与结束时间为上面最终得到的
						if(attendday.getAttendResult()==AttendResult.ABSENT){
							attendday.setFirstTime(DateUtils.getDateSomeTime(attendDate, startHour.intValue(), (int)((startHour%1.0)*60), 0));
							attendday.setLastTime(DateUtils.getDateSomeTime(attendDate, endHour.intValue(), (int)((endHour%1.0)*60), 0));
							//并在此处更新日项信息 return继续循环
							attendday.setAdditionalTime(0.0);
							attendday.setAttendType((Integer) Year_AdjustLeave.get("attend_type"));
							//申请工时小于7个小时，记为旷工
							Integer attendResult = 0;
							if(workday_time < 7){
								attendResult = AttendResult.ABSENT;
							}else{
					    		//晚-早<goWorkHour 判断一下 早>goWorkTime-->迟到 晚<offWorkTime-->早退 同时存在-->迟到+早退
					    		if(startHour > goWorkTime && endHour < offWorkTime){
					    			attendResult = AttendResult.LATE_EARLY;
					    		}
					    		if(startHour > goWorkTime){
					    			attendResult = AttendResult.LATE;
					    		}else{
					    			attendResult = AttendResult.EARLY;
					    		}
							}
							attendday.setAttendResult(attendResult);
							jobMapper.updateAttendday(attendday);
							//-------------------
							attendBillItem.put("effect_flag", 1);
							jobMapper.updateEmpAttendBillLeavedayItems(attendBillItem);
							
							
							//更新日历 考勤日项为已生效
							Map<String,Object> calendar = jobMapper.selectActiveCalendarByUserIdAndAttendDate(userId,attendDate);
							//存在则记为无效
					    	if(calendar!=null){
					    		jobMapper.updateCalendarToNoEffect(calendar);
					    	}
							//然后新增考勤日历表记录
					    	//查询用户
					    	SysUserQuartz user = jobMapper.selectUserByUserId(userId);
					    	Map<String,Object> newCalendar = new HashMap<String,Object>();
					    	newCalendar.put("calendar_id",StringUtils.getUUID() );
					    	newCalendar.put("user_id", user.getUserId());
					    	newCalendar.put("emp_id", user.getEmpId());
					    	newCalendar.put("user_name", user.getUserName());
					    	newCalendar.put("dept_id", user.getDeptId());
					    	newCalendar.put("DATE", attendDate);
					    	//考勤代码 0事假 1年假 2调休假 3忘记打卡 4迟到 5早退 6矿工 7迟到+早退（迟到） 9休息日 10 工作日
					    	newCalendar.put("attend_code", (Integer) Year_AdjustLeave.get("attend_type"));
					    	newCalendar.put("attend_label", Year_AdjustLeave.get("attend_type").equals(1)?"年假":"调休假");
					    	newCalendar.put("effect_flag", 1);
					    	//新增
					    	jobMapper.insertCalendar(newCalendar);
							return;
						}
					
					//2.8、考勤单的开始结束时间与考勤日信息中早晚时间取并集，并经如下规则
						//考勤单开始结束小时数
						Double end_start = endHour-startHour;//数据差
						//考勤日早晚小时数
						Double firstHour = DateUtils.getHourAndMminutesDoubleValue(attendday.getFirstTime());
						Double lastHour = DateUtils.getHourAndMminutesDoubleValue(attendday.getLastTime());
						Double last_first = endHour-endHour;//数据差
						
						//考勤结果
						Integer attendResult = 0; 
						
						//若得到的并集中存在空洞如9点-12点 14点-17点 的考勤结果为取时间区间跨度大的 即为考勤日信息的早晚时间
						if((endHour-firstHour) * (endHour-lastHour)>0){//并集 空洞
							if(end_start>last_first){
								firstHour = startHour;
								lastHour = endHour;
							}
							//再加上 若早于9点 则记为早退 若晚于17点 则记为迟到 考勤类型为事假 总上班时间小于7个小时 为旷工
							if(lastHour - firstHour >= 8){
								attendResult = AttendResult.LEAVE;
							}else if(lastHour - firstHour < 7){
								attendResult = AttendResult.ABSENT;
							}else if(firstHour>9 && lastHour<17){
								attendResult = AttendResult.LATE_EARLY;
							}else if(firstHour>9){
								attendResult = AttendResult.LATE;
							}else{
								attendResult = AttendResult.LATE;
							}
							
						//若得到的并集不存在空洞,那么就取交集
						}else{
							//获取并集
							if(endHour-firstHour>end_start){
								lastHour = endHour;
							}else if(lastHour-startHour>last_first){
								firstHour = startHour;
							}else if(end_start>last_first){
								firstHour = startHour;
								lastHour = endHour;
							}
							//再加上 若早于9点 则记为早退 若晚于17点 则记为迟到 考勤类型为事假 总上班时间小于7个小时 为旷工
							if(lastHour - firstHour >= 8){
								attendResult = AttendResult.LEAVE;
							}else if(lastHour - firstHour < 7){
								attendResult = AttendResult.ABSENT;
							}else if(firstHour>9 && lastHour<17){
								attendResult = AttendResult.LATE_EARLY;
							}else if(firstHour>9){
								attendResult = AttendResult.LATE;
							}else{
								attendResult = AttendResult.LATE;
							}
						}
						attendday.setFirstTime(DateUtils.getDateSomeTime(attendDate, firstHour.intValue(), (int)((firstHour%1.0)*60), 0));
						attendday.setLastTime(DateUtils.getDateSomeTime(attendDate, lastHour.intValue(), (int)((lastHour%1.0)*60), 0));
						//并在此处更新日项信息 return继续循环
						attendday.setAdditionalTime(0.0);
						attendday.setAttendType((Integer) Year_AdjustLeave.get("attend_type"));
						attendday.setAttendResult(attendResult);
						jobMapper.updateAttendday(attendday);
						//-------------------
						attendBillItem.put("effect_flag", 1);
						jobMapper.updateEmpAttendBillLeavedayItems(attendBillItem);
						
						//更新日历 考勤日项为已生效
						Map<String,Object> calendar = jobMapper.selectActiveCalendarByUserIdAndAttendDate(userId,attendDate);
						//存在则记为无效
				    	if(calendar!=null){
				    		jobMapper.updateCalendarToNoEffect(calendar);
				    	}
						//然后新增考勤日历表记录
				    	//查询用户
				    	SysUserQuartz user = jobMapper.selectUserByUserId(userId);
				    	Map<String,Object> newCalendar = new HashMap<String,Object>();
				    	newCalendar.put("calendar_id",StringUtils.getUUID() );
				    	newCalendar.put("user_id", user.getUserId());
				    	newCalendar.put("emp_id", user.getEmpId());
				    	newCalendar.put("user_name", user.getUserName());
				    	newCalendar.put("dept_id", user.getDeptId());
				    	newCalendar.put("DATE", attendDate);
				    	//考勤代码 0事假 1年假 2调休假 3忘记打卡 4迟到 5早退 6矿工 7迟到+早退（迟到） 9休息日 10 工作日
				    	newCalendar.put("attend_code", (Integer) Year_AdjustLeave.get("attend_type"));
				    	newCalendar.put("attend_label", Year_AdjustLeave.get("attend_type").equals(1)?"年假":"调休假");
				    	newCalendar.put("effect_flag", 1);
				    	//新增
				    	jobMapper.insertCalendar(newCalendar);
				    	return;
						
				});
				
				//更新用户年假 只有在未生效时才可以
				if(Year_AdjustLeave.get("complete_flag").equals(1)){
					//2.9、更新该作人员的年假信息，根据最后得到的加班工时,此处需将额外工时换算成天数
					jobMapper.updateYearVacationForQuartz(-Arith.div((double) Year_AdjustLeave.get("apply_workday_times"), goWorkHour, 1));
					
				}
				//2.10、查询是否还存在未生效的日项	
				if(jobMapper.selectAttendBillItems(null)==null){
					//不存在则更新考勤单为完全生效
					Year_AdjustLeave.put("complete_flag", 2);
					Year_AdjustLeave.put("entire_effect", 1);
					jobMapper.updateAttendBill(Year_AdjustLeave);
				}else{
					//部分生效
					Year_AdjustLeave.put("complete_flag", 2);
					Year_AdjustLeave.put("entire_effect", 0);
					jobMapper.updateAttendBill(Year_AdjustLeave);
				}
				
			});
	}
	
	@Test
	public void test(){
		//9-11 13-17
		Object i = 1;
		System.out.println(i.equals(1));
	}
    
}
