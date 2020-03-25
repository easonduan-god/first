package com.numberone.emp.domain;

import java.util.Date;

import com.numberone.common.base.BaseEntity;
import com.numberone.system.domain.SysUser;

public class EmpAttendBill extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8764923382946783128L;

	private String attendBillId;

    private String empId;

    private Long userId;

    private String theme;

    private Integer attendType;

    private String deptName;

    private Long deptId;

    private String userName;

    private Date applyDate;

    private Date startDate;

    private Date endDate;

    private Date startTime;

    private Date endTime;

    private Double applyWorkdays;

    private Double applyWorkdayTimes;

    private String matter;

    private Integer isOffet;
    /**
     * 完成状态(0未完成 1已完成)
     */
    private Integer completeFlag;
    /**
     * 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
     */
    private Integer auditFlag;

    private Integer delFlag;
    
    private SysUser user;
    
    public String getAttendBillId() {
        return attendBillId;
    }

    public void setAttendBillId(String attendBillId) {
        this.attendBillId = attendBillId == null ? null : attendBillId.trim();
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    public Integer getAttendType() {
        return attendType;
    }

    public void setAttendType(Integer attendType) {
        this.attendType = attendType;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getApplyWorkdays() {
        return applyWorkdays;
    }

    public void setApplyWorkdays(Double applyWorkdays) {
        this.applyWorkdays = applyWorkdays;
    }

    public Double getApplyWorkdayTimes() {
        return applyWorkdayTimes;
    }

    public void setApplyWorkdayTimes(Double applyWorkdayTimes) {
        this.applyWorkdayTimes = applyWorkdayTimes;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter == null ? null : matter.trim();
    }

    public Integer getIsOffet() {
        return isOffet;
    }

    public void setIsOffet(Integer isOffet) {
        this.isOffet = isOffet;
    }

    public Integer getCompleteFlag() {
        return completeFlag;
    }

    public void setCompleteFlag(Integer completeFlag) {
        this.completeFlag = completeFlag;
    }

    public Integer getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(Integer auditFlag) {
        this.auditFlag = auditFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}