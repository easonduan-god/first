package com.numberone.emp.domain;

import java.util.Date;

import com.numberone.system.domain.SysUser;

public class EmpAttendinfo {
    private String attendinfoId;

    private Long userId;

    private String empId;

    private Date attendDate;

    private Integer week;

    private Date recordTime;

    private String remark;
    private SysUser user;
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

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}
}