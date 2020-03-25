package com.numberone.emp.domain;

import java.util.Date;

import com.numberone.common.base.BaseEntity;
import com.numberone.system.domain.SysDept;
import com.numberone.system.domain.SysUser;
/**
 * 日常考勤实体类
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年1月26日 下午8:52:22
 * @param:
 */
public class EmpAttendday extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4363864900214959479L;
	/**
	 * 日常考勤单id
	 */
    private String attenddayId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 员工工号
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
     * 最开始打卡时间
     */
    private Date firstTime;
    /**
     * 最后打卡时间
     */
    private Date lastTime;
    /**
     * 考勤结果 0正常 1迟到 2早退 3旷工 4异常
     */
    private Integer attendResult;
    /**
     * 考勤类型 
     */
    private Integer attendType;
    /**
     * 额外时间
     */
    private Double additionalTime;
    /**
     * 备注
     */
    private String remark;
    
    private SysUser user;
    
    private SysDept dept;
    
    public String getAttenddayId() {
        return attenddayId;
    }

    public void setAttenddayId(String attenddayId) {
        this.attenddayId = attenddayId == null ? null : attenddayId.trim();
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

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Integer getAttendResult() {
        return attendResult;
    }

    public void setAttendResult(Integer attendResult) {
        this.attendResult = attendResult;
    }

    public Integer getAttendType() {
        return attendType;
    }

    public void setAttendType(Integer attendType) {
        this.attendType = attendType;
    }

    public Double getAdditionalTime() {
        return additionalTime;
    }

    public void setAdditionalTime(Double additionalTime) {
        this.additionalTime = additionalTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public SysDept getDept() {
		return dept;
	}

	public void setDept(SysDept dept) {
		this.dept = dept;
	}
}