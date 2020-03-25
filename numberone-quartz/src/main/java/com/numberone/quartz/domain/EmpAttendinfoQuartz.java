package com.numberone.quartz.domain;

import java.util.Date;
/**
 * 考勤日常打卡信息
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年1月26日 下午8:56:20
 * @param:
 */
public class EmpAttendinfoQuartz{
	/**
	 * 日常打卡信息id
	 */
    private String attendinfoId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 员工号
     */
    private String empId;
    /**
     * 考勤日期
     */
    private Date attendDate;
    /**
     * 星期
     */
    private Integer week;
    /**
     * 打卡时间
     */
    private Date recordTime;
    /**
     * 备注
     */
    private String remark;

    public String getAttendinfoId() {
        return attendinfoId;
    }

    public void setAttendinfoId(String attendinfoId) {
        this.attendinfoId = attendinfoId == null ? null : attendinfoId.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public Date getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(Date attendDate) {
        this.attendDate = attendDate;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}