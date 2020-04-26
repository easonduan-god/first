package com.numberone.emp.domain;

import java.util.Date;

import com.numberone.common.base.BaseEntity;
import com.numberone.system.domain.SysUser;

/**
 * 考勤单实体类
 * @author eason
 * @date 2020-02-20
 */
public class EmpAttendBill extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8764923382946783128L;
	/**
	 * 考勤单id
	 */
	private String attendBillId;
	/**
	 * 员工id
	 */
    private String empId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 主题
     */
    private String theme;
    /**
     * 考勤类型(0事假 1年假 2调休假 3忘记打卡)
     */
    private Integer attendType;
    /**
     * 所属部门
     */
    private String deptName;
    /**
     * 所属部门编号
     */
    private Long deptId;
    /**
     * 员工名
     */
    private String userName;
    /**
     * 申请日期
     */
    private Date applyDate;
    /**
     * 开始日期
     */
    private Date startDate;
    /**
     * 结束日期
     */
    private Date endDate;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 申请工作日天数
     */
    private Double applyWorkdays;
    /**
     * 申请工作日工时
     */
    private Double applyWorkdayTimes;
    /**
     * 事由
     */
    private String matter;
    /**
     * 是否销假(0否 1是)
     */
    private Integer isOffet;
    /**
     * 完成状态(0未完成 1已完成 2生效)
     */
    private Integer completeFlag;
    /**
     * 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
     */
    private Integer auditFlag;
    /**
     * 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
     */
    private String auditFlagText;
    /**
     * 删除标志(0未删除 2已删除)
     */
    private Integer delFlag;
    /**
     * 
     */    
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
    /** 审核状态文本(0未审核 1审核中 2审核不通过 3审核已通过) */
	public String getAuditFlagText() 
	{
		if(auditFlag!=null){
			switch (auditFlag) {
			case 0:
				auditFlagText = "未审核";
				break;
			case 1:
				auditFlagText = "审核中";
				break;
			case 2:
				auditFlagText = "审核不通过";
				break;
			case 3:
				auditFlagText = "审核已通过";
				break;
			}
		}
		return auditFlagText;
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