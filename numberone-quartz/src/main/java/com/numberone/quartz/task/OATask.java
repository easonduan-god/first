package com.numberone.quartz.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.numberone.quartz.service.IReportJobService;
import com.numberone.quartz.service.ISysJobService;

/**
 * OA定时任务
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年1月29日 下午7:11:24
 * @param:
 */
@Component("OATask")
@Transactional(rollbackFor = Exception.class)
public class OATask {
	@Autowired
	private ISysJobService sysJobService;
	@Autowired
	private IReportJobService reportJobService;
	
	
	/**
	 * 每日定时更新员工考勤信息
	 */
	public void updateAttendInfo(){
		
		sysJobService.updateAttendInfo();
	}
	
	/**
	 * 定时更新员工延时工单
	 */
	public void updateOvertimeBill(){
		sysJobService.updateOvertimeBill();
	}
	
	/**
	 * 定时更新日历
	 */
	public void updateAttendCalendar(){
		sysJobService.updateAttendCalendar();
	}
	/**
	 * 定时更新年假 调休
	 */
	public void updateAttendBill_Year_AdjustLeave(){
		sysJobService.updateAttendBill_Year_AdjustLeave();
	}
	/**
	 * 定时更新事假
	 */
	public void updateAttendBill_PersonalLeave(){
		sysJobService.updateAttendBill_PersonalLeave();
	}
	/**
	 * 定时更新忘记打卡
	 */
	public void updateAttendBill_ForgetClock(){
		sysJobService.updateAttendBill_ForgetClock();
	}
	
	/**
	 * 定时备份数据库
	 */
	public void backupDatabase(){
		sysJobService.backupDatabase();
	}
	
	/**
	 * 定时生成员工考勤月报
	 */
	public void generateEmpAttendReport(){
		reportJobService.generateEmpAttendReport();
	}
	/**
	 * 定时生成部门考勤月报
	 */
	public void generateDeptAttendReport(){
		reportJobService.generateDeptAttendReport();
	}
	/**
	 * 定时生成员工任务月报
	 */
	public void generateEmpTaskReport(){
		reportJobService.generateEmpTaskReport();
	}
	/**
	 * 定时生成部门任务月报
	 */
	public void generateDeptTaskReport(){
		reportJobService.generateDeptTaskReport();
	}
	
	/**
	 * 定时生成工时记录，每月一号为每个员工生成一条工时记录
	 */
	public void generateMonthHour(){
		reportJobService.generateMonthHour();
	}
	
	
}
