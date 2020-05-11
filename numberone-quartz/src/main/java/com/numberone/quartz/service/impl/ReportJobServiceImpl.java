package com.numberone.quartz.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.numberone.common.utils.DateUtils;
import com.numberone.common.utils.StringUtils;
import com.numberone.quartz.domain.EmpAttenddayQuartz;
import com.numberone.quartz.domain.SysUserQuartz;
import com.numberone.quartz.mapper.ReportJobMapper;
import com.numberone.quartz.mapper.SysJobMapper;
import com.numberone.quartz.service.IReportJobService;

@Service
public class ReportJobServiceImpl implements IReportJobService {
    @Autowired
    private SysJobMapper jobMapper;
    @Autowired
    private ReportJobMapper reportMapper;

    //private static final Logger log = LoggerFactory.getLogger(SysJobServiceImpl.class);
	/**
	 * 定时生成员工考勤月报
	 */
	@Scheduled(cron = "${krystal.quarz.order.cron}")
	@Override
	public void generateEmpAttendReport() {
    	//只允许在每月10号以前的早上5点到六点每天执行一次 总共执行10次
		Date currTime = new Date();
		
		long month_day = DateUtils.getFragmentInDays(currTime, Calendar.MONTH);
		double hour = DateUtils.getHourAndMminutesDoubleValue(currTime);
		if(month_day>10 || hour > 6 || hour < 5){
			System.out.println("非考勤执行时间");
			return;
		}
		//公共数据
			//月份 --当前月份的一号
			Date month = DateUtils.formatDateToDate(currTime , DateUtils.YYYY_MM);
			//上月月份
			Date lastMonth = DateUtils.getAroundDate(month, -10, DateUtils.YYYY_MM);
			//上月自然月份天数
			Integer lastMonthDays = DateUtils.getDaysOfMonth(lastMonth);
			//上月工作日天数=周一-周五的天数-工作日表中的工作日天数
			int workdays = DateUtils.getWorkDays(lastMonth)+reportMapper.selectWorkdayIsWorkdayCount(lastMonth);
			
			
		//1、员工考勤任务月报:
		//------------------------------------------------------------------------------------------------------------
			//1.1、查询所有用户
			List<SysUserQuartz> userList = jobMapper.selectUserAll();
			for(SysUserQuartz user : userList) {
				Boolean insertFlag = false;//默认为修改
				
				Long userId = user.getUserId();
				//查询上月员工报表
				Map<String,Object> empReport = reportMapper.selectEmpAttendReportByUserIdAndMonth(userId,lastMonth);
				
				if(empReport==null || empReport.get("record_id")==null){
					//记录id
					empReport = new HashMap<String,Object>();
					empReport.put("record_id",StringUtils.getUUID());       
					insertFlag = true;
				}
				//1.2、补齐数据
					//用户id
					empReport.put("user_id",userId);             
					//员工号
					empReport.put("emp_id",user.getEmpId());              
					//用户名
					empReport.put("user_name",user.getUserName());           
					//部门id
					empReport.put("dept_id",user.getDeptId());             
					//部门名
					empReport.put("dept_name",user.getDeptName());           
					//月份
					empReport.put("month",lastMonth);               
					//报表生成时间
					empReport.put("create_time",currTime);         
				
				//1.3、查询该员工上月考勤日信息
					Map<String, Object> queryMap = new HashMap<String,Object>();
				List<EmpAttenddayQuartz> attenddayList =  reportMapper.selectAttenddayByMonthAndUserId(userId,lastMonth);
				
				//1.4、遍历考勤日信息得到一系列报表数据
				Integer late_count = 0;
				Integer early_count = 0;
				Integer absent_count = 0;
				Integer forget_count = 0;//
				Integer leave_count = 0;//
				Integer leave_days = 0;//
				Double leave_hours = 0.0;//
				Integer overtime_count = 0;//
				Double overtime_hours = 0.0;//
				for (EmpAttenddayQuartz attendday : attenddayList) {
					//考勤结果 0正常 1迟到 2早退 3旷工 4异常 5迟到+早退 6请假
					Integer attendResult = attendday.getAttendResult();
					//迟到次数
					if(attendResult == 1 || attendResult == 5){
						late_count++;
					}
					//早退 次数
					if(attendResult == 2 || attendResult == 5){
						early_count++;
					}
					//旷工 次数
					if(attendResult == 3){
						absent_count++;
					}
				}
				//1.5、查询考勤单
				queryMap.put("user_id", userId);
				queryMap.put("month", lastMonth);
				List<Map<String,Object>> attendBillList =  reportMapper.selectAttendBillList(queryMap);
				//1.6、遍历考勤单 考勤类型(0事假 1年假 2调休假 3忘记打卡)
				for (Map<String, Object> attendBill : attendBillList) {
					Integer attend_type = (Integer) attendBill.get("attend_type");
					if(attend_type<3){
						leave_count++;
						
						//查询考勤单日项
						queryMap.put("attend_bill_id", attendBill.get("attend_bill_id"));
						List<Map<String,Object>> attendBillItemList = reportMapper.selectAttendBillItems(queryMap);
						//遍历考勤日项
						for (Map<String, Object> attendBillItem : attendBillItemList) {
							leave_days++;
							leave_hours += (Double)attendBillItem.get("workday_time");
						}
					}else{
						forget_count++;
					}
					
					
				}
				//1.7、查询延时工单
				queryMap.put("attend_bill_id", null);
				List<Map<String,Object>> overtimeBillList =  reportMapper.selectOvertimeBillList(queryMap);
				for (Map<String, Object> overtimeBill : overtimeBillList) {
					queryMap.put("overtime_bill_id", overtimeBill.get("overtime_bill_id"));
					//查询延时工单项
					Map<String,Object> overtimeBillItem = reportMapper.selectOvertimeBillItem(queryMap);
					//遍历考勤日项
					if(overtimeBillItem!=null){
						overtime_count++;
						overtime_hours += (Double)overtimeBillItem.get("hour");
					}
				}
					//迟到次数
					empReport.put("late_count",late_count);          
					//早退次数
					empReport.put("early_count",early_count);         
					//旷工次数
					empReport.put("absent_count",absent_count);        
					//忘记打卡次数
					empReport.put("forget_count",forget_count);        
					//请假次数
					empReport.put("leave_count",leave_count);         
					//请假天数
					empReport.put("leave_days",leave_days);          
					//请假工时
					empReport.put("leave_hours",leave_hours);         
					//到勤率 旷工才算缺勤
					empReport.put("attend_rate",(workdays-absent_count)/(workdays*0.01));
					//考勤异常率 早退 迟到 旷工 都算
					empReport.put("abnormal_rate",(late_count+early_count+absent_count)/(workdays*0.01));   
					//累计加班次数
					empReport.put("overtime_count",overtime_count);     
					//加班率
					empReport.put("overtime_rate",overtime_count/(lastMonthDays*0.01));      
					//累计加班工时
					empReport.put("overtime_hours",overtime_hours);      
					//平均加班工时（工时/次数）
					empReport.put("avg_overtime_hours",overtime_hours==0?0:overtime_hours/overtime_count);  
				
				//新增
				if(insertFlag){
					reportMapper.insertEmpAttendReport(empReport);
				}else{//更新
					reportMapper.updateEmpAttendReport(empReport);
				}
		}
		//------------------------------------------------------------------------------------------------------------
			
	}
	
	/**
	 * 定时生成部门考勤月报
	 */
	@Scheduled(cron = "${krystal.quarz.order.cron}")
	@Override
	public void generateDeptAttendReport() {
    	//只允许在每月10号以前的早上5点到六点每天执行一次 总共执行10次
		Date currTime = new Date();
		
		long month_day = DateUtils.getFragmentInDays(currTime, Calendar.MONTH);
		double hour = DateUtils.getHourAndMminutesDoubleValue(currTime);
		if(month_day>10 || hour > 6 || hour < 5){
			System.out.println("非考勤执行时间");
			return;
		}
		//公共数据
			//月份 --当前月份的一号
			Date month = DateUtils.formatDateToDate(currTime , DateUtils.YYYY_MM);
			//上月月份
			Date lastMonth = DateUtils.getAroundDate(month, -10, DateUtils.YYYY_MM);
			//上月自然月份天数
			//Integer lastMonthDays = DateUtils.getDaysOfMonth(lastMonth);
			//上月工作日天数=周一-周五的天数-工作日表中的工作日天数
			int workdays = DateUtils.getWorkDays(lastMonth)+reportMapper.selectWorkdayIsWorkdayCount(lastMonth);
		
		//1、查询所有部门
			List<Map<String, Object>> deptList = reportMapper.selectDeptAll();
			for (Map<String, Object> dept : deptList) {
				
				
				Boolean insertFlag = false;//默认为修改
				
				Integer deptId = (Integer)dept.get("dept_id");
				//2、查询上月部门报表
				Map<String,Object> deptReport = reportMapper.selectDeptAttendReportByUserIdAndMonth(deptId,lastMonth);
				
				if(deptReport==null || deptReport.get("record_id")==null){
					//记录id
					deptReport = new HashMap<String,Object>();
					deptReport.put("record_id",StringUtils.getUUID());       
					insertFlag = true;
				}
				Integer emp_count=0;
				Integer early_count=0;
				Integer late_count=0;
				Integer absent_count=0;
				Integer forget_count=0;
				Integer leave_count=0;
				Integer leave_days=0;
				Double leave_hours=0.0;
				Integer overtime_count=0;
				Integer perfect_count=0;
				Double overtime_hours=0.0;
				Integer overtime_person_count=0;
				Integer no_overtime_person_count=0;
				Integer overtime_less30=0;
				Integer overtime_more100=0;
				
				//2、根据部门id查询对应的员工考勤报表
				List<Map<String, Object>> empReportList = reportMapper.selectEmpAttendReportByDeptIdAndMonth(deptId, lastMonth);
				for (Map<String, Object> empReport : empReportList) {
					emp_count++;
					early_count += (Integer)empReport.get("early_count");
					late_count += (Integer)empReport.get("late_count");
					absent_count += (Integer)empReport.get("absent_count");
					forget_count += (Integer)empReport.get("forget_count");
					leave_count += (Integer)empReport.get("leave_count");
					leave_days += (Integer)empReport.get("leave_days");
					leave_hours += (Double)empReport.get("leave_hours");
					overtime_count += (Integer)empReport.get("overtime_count");
					if((Double)empReport.get("attend_rate")==100){
						perfect_count ++;
					}
					if((Integer)empReport.get("overtime_count")>0){
						overtime_person_count ++;
					}else{
						no_overtime_person_count ++;
					}
					if((Double)empReport.get("overtime_hours")<30){
						overtime_less30 ++;
					}
					if((Double)empReport.get("overtime_hours")>=100){
						overtime_more100 ++;
					}
					overtime_hours += (Double)empReport.get("overtime_hours");
				}
				//emp_count 
				//补齐数据
				deptReport.put("dept_id",deptId);
				deptReport.put("dept_name",dept.get("dept_name"));
				deptReport.put("dept_leader",dept.get("leader"));
				deptReport.put("month",lastMonth);
				deptReport.put("create_time",currTime);
				deptReport.put("emp_count",emp_count);
				deptReport.put("late_count",late_count);
				deptReport.put("late_rate",emp_count==0?0:(late_count/emp_count)/(workdays*0.01));
				deptReport.put("early_count",early_count);
				deptReport.put("early_rate",emp_count==0?0:(early_count/emp_count)/(workdays*0.01));
				deptReport.put("absent_count",absent_count);
				deptReport.put("absent_rate",emp_count==0?0:(absent_count/emp_count)/(workdays*0.01));
				deptReport.put("forget_count",forget_count);
				deptReport.put("forget_rate",emp_count==0?0:(late_count/emp_count)/(workdays*0.01));
				deptReport.put("leave_count",leave_count);
				deptReport.put("leave_rate",emp_count==0?0:(leave_count/emp_count)/(workdays*0.01));
				deptReport.put("leave_days",leave_days);
				deptReport.put("leave_hours",leave_hours);
				deptReport.put("abnormal_rate",emp_count==0?0:((late_count+early_count+absent_count)/emp_count)/(workdays*0.01));
				deptReport.put("overtime_count",overtime_count);
				deptReport.put("perfect_count",perfect_count);
				deptReport.put("overtime_rate",emp_count==0?0:(overtime_person_count)/(emp_count*0.01));
				deptReport.put("overtime_hours",overtime_hours);
				deptReport.put("overtime_person_count",overtime_person_count);
				deptReport.put("avg_overtime_hours",overtime_person_count==0?0:overtime_hours/overtime_person_count);
				deptReport.put("no_overtime_person_count",no_overtime_person_count);
				deptReport.put("overtime_less30",overtime_less30);
				deptReport.put("overtime_more100",overtime_more100);
				
				//新增
				if(insertFlag){
					reportMapper.insertDeptAttendReport(deptReport);
				}else{//更新
					reportMapper.updateDeptAttendReport(deptReport);
				}
			}
	}

	/**
	 * 定时生成员工任务月报
	 */
	@Scheduled(cron = "${krystal.quarz.order.cron}")
	@Override
	public void generateEmpTaskReport() {
		//只允许在每月10号以前的早上5点到六点每天执行一次 总共执行10次
		Date currTime = new Date();
		
		long month_day = DateUtils.getFragmentInDays(currTime, Calendar.MONTH);
		double hour = DateUtils.getHourAndMminutesDoubleValue(currTime);
		if(month_day>10 || hour > 6 || hour < 5){
			System.out.println("非考勤执行时间");
			return;
		}
		//公共数据
			//月份 --当前月份的一号
			Date month = DateUtils.formatDateToDate(DateUtils.getAroundDate(currTime,-20), DateUtils.YYYY_MM);
			//上月月份
			Date lastMonth = DateUtils.getAroundDate(month, -10, DateUtils.YYYY_MM);
			
			
		//1、查询所有用户
		List<SysUserQuartz> userList = jobMapper.selectUserAll();
		//2、遍历用户，为每一个用户生成一条任务月报
		for (SysUserQuartz user : userList) {
			Long userId = user.getUserId();
			Boolean insertFlag = false;//默认为更新
			//2.1、查询对应的任务月报
			Map<String, Object> queryMap = new HashMap<String,Object>();
			queryMap.put("user_id", userId);
			queryMap.put("month", lastMonth);
			Map<String, Object> empReport= reportMapper.selectEmpTaskReport(queryMap);
			if(empReport==null){
				insertFlag = true;
				empReport = new HashMap<String, Object>();
				empReport.put("record_id",StringUtils.getUUID());
			}
			empReport.put("user_id",userId);
			empReport.put("emp_id",user.getEmpId());
			empReport.put("dept_id",user.getDeptId());
			empReport.put("dept_name",user.getDeptName());
			empReport.put("user_name",user.getUserName());
			empReport.put("month",lastMonth);
			empReport.put("create_time",currTime);
			//2.2、查询工时表信息
			Map<String,Object> monthHour = reportMapper.selectMonthHour(queryMap);
			
			
			Integer task_count = 0;
			Double total_hours = (Double) monthHour.get("hour");
			//2.3、查询上月所有任务 任务处理时间deal_time
			List<Map<String, Object>> taskList = reportMapper.selectTaskList(queryMap);
			task_count  = taskList.size();
			
			empReport.put("total_hours",total_hours);
			empReport.put("task_count",task_count);
			empReport.put("avg_task_hours",task_count==0?0:total_hours/task_count);
			if(insertFlag){
				reportMapper.insertEmpTaskReport(empReport);
			}else{
				reportMapper.updateEmpTaskReport(empReport);
			}
		}
	}

	/**
	 * 定时生成部门任务月报
	 */
	@Scheduled(cron = "${krystal.quarz.order.cron}")
	@Override
	public void generateDeptTaskReport() {
    	//只允许在每月10号以前的早上5点到六点每天执行一次 总共执行10次
		Date currTime = new Date();
		
		long month_day = DateUtils.getFragmentInDays(currTime, Calendar.MONTH);
		double hour = DateUtils.getHourAndMminutesDoubleValue(currTime);
		if(month_day>10 || hour > 6 || hour < 5){
			System.out.println("非考勤执行时间");
			return;
		}
		//公共数据
			//月份 --当前月份的一号
			Date month = DateUtils.formatDateToDate(DateUtils.getAroundDate(currTime,-20), DateUtils.YYYY_MM);
			//上月月份
			Date lastMonth = DateUtils.getAroundDate(month, -10, DateUtils.YYYY_MM);
		
		//1、查询所有部门
			List<Map<String, Object>> deptList = reportMapper.selectDeptAll();
			for (Map<String, Object> dept : deptList) {
				
				
				Boolean insertFlag = false;//默认为修改
				
				Integer deptId = (Integer) dept.get("dept_id");
				//2、查询上月部门报表
				Map<String, Object> queryMap = new HashMap<String,Object>();
				queryMap.put("dept_id", deptId);
				queryMap.put("month", lastMonth);
				Map<String,Object> deptReport = reportMapper.selectDeptTaskReport(queryMap);
				
				if(deptReport==null || deptReport.get("record_id")==null){
					//记录id
					deptReport = new HashMap<String,Object>();
					deptReport.put("record_id",StringUtils.getUUID());       
					insertFlag = true;
				}
				Integer emp_count = 0;
				Double total_hours = 0.0;
				Integer task_count = 0;
				//2、根据部门id查询对应的员工考勤报表--审核通过的
				List<Map<String, Object>> empReportList = reportMapper.selectEmpTaskReportList(queryMap);
				for (Map<String, Object> empReport : empReportList) {
					emp_count++;
					task_count++;
					total_hours += (Double)empReport.get("total_hours");
				}
				//补齐数据 
				deptReport.put("dept_id",dept.get("dept_id"));
				deptReport.put("dept_name",dept.get("dept_name"));
				deptReport.put("dept_leader",dept.get("leader"));
				deptReport.put("month",lastMonth);
				deptReport.put("create_time",currTime);
				deptReport.put("emp_count",emp_count);
				deptReport.put("total_hours",total_hours);
				deptReport.put("task_count",task_count);
				deptReport.put("avg_task_hours",task_count==0?0:total_hours/task_count);
				deptReport.put("avg_task_count",emp_count==0?0:task_count/emp_count);
				//新增
				if(insertFlag){
					reportMapper.insertDeptTaskReport(deptReport);
				}else{//更新
					reportMapper.updateDeptTaskReport(deptReport);
				}
			}
	}

	/**
	 * 定时生成工时记录，每月一号为每个员工生成一条工时记录
	 */
	@Scheduled(cron = "${krystal.quarz.order.cron}")
	@Override
	public void generateMonthHour() {
    	//只允许在每月1,2号的早上5点到六点每天执行一次 总共执行10次
		Date currTime = new Date();
		
		long month_day = DateUtils.getFragmentInDays(currTime, Calendar.MONTH);
		double hour = DateUtils.getHourAndMminutesDoubleValue(currTime);
		if(month_day>2 || hour > 6 || hour < 5){
			System.out.println("非执行时间");
			return;
		}
		//月份 --当前月份的一号
		Date month = DateUtils.formatDateToDate(new Date() , DateUtils.YYYY_MM);
		//1.1、查询所有用户
		List<SysUserQuartz> userList = jobMapper.selectUserAll();
		for (SysUserQuartz user : userList) {
			Long userId = user.getUserId();
			//根据月份和用户id查询工时记录
			//有则不处理 无则新增
			Boolean insertFlag = false;//默认为修改
			Map<String, Object> queryMap = new HashMap<String,Object>();
			queryMap.put("user_id", userId);
			queryMap.put("month", month);
			Map<String,Object> monthHour = reportMapper.selectMonthHour(queryMap);
			if(monthHour==null){
				insertFlag = true;
				monthHour = new HashMap<String,Object>();
				monthHour.put("record_id",StringUtils.getUUID());
				monthHour.put("user_id",userId);
				monthHour.put("dept_id",user.getDeptId());
				monthHour.put("dept_name",user.getDeptName());
				monthHour.put("user_name",user.getUserName());
				monthHour.put("month",month);
				monthHour.put("emp_id",user.getEmpId());
				monthHour.put("hour",0);
				monthHour.put("remark",null);
			}
			
			if(insertFlag){
				reportMapper.insertMonthHour(monthHour);
			}
		}
	}
}
