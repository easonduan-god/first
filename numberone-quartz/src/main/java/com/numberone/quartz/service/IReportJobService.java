package com.numberone.quartz.service;

public interface IReportJobService {
	/**
	 * 定时生成员工考勤月报
	 */
	public void generateEmpAttendReport();
	/**
	 * 定时生成部门考勤月报
	 */
	public void generateDeptAttendReport();

	/**
	 * 定时生成员工任务月报
	 */
	public void generateEmpTaskReport();
	/**
	 * 定时生成部门任务月报
	 */
	public void generateDeptTaskReport();
	
	/**
	 * 定时生成工时记录，每月一号为每个员工生成一条工时记录
	 */
	public void generateMonthHour();
}
