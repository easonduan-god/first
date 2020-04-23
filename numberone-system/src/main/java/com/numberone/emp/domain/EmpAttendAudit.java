package com.numberone.emp.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.numberone.common.base.BaseEntity;

/**
 * 上级领导审核考勤单表 emp_attend_audit
 * 
 * @author eason
 * @date 2020-02-20
 */
public class EmpAttendAudit extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 考勤审核单id */
	private String attendAuditId;
	/** 考勤单id */
	private String attendBillId;
	/** 用户id */
	private Long userId;
	/** 员工id */
	private String empId;
	/** 审核时间 */
	private Date auditTime;
	/** 审核人 */
	private String auditName;
	/** 审核人用户id */
	private Long auditUserId;
	/** 审核人工号 */
	private String auditEmpId;
	/** 审核人职位 */
	private String auditJob;
	/** 审核状态(0未审核 1审核中 2审核不通过 3审核已通过) */
	private Integer auditFlag;
	/** 审核状态(0未审核 1审核中 2审核不通过 3审核已通过) */
	private String auditFlagText;
	
	private String remark;
	
	private EmpAttendBill empAttendBill;
	
	public void setAttendAuditId(String attendAuditId) 
	{
		this.attendAuditId = attendAuditId;
	}

	public String getAttendAuditId() 
	{
		return attendAuditId;
	}
	public void setAttendBillId(String attendBillId) 
	{
		this.attendBillId = attendBillId;
	}

	public String getAttendBillId() 
	{
		return attendBillId;
	}
	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}

	public Long getUserId() 
	{
		return userId;
	}
	public void setEmpId(String empId) 
	{
		this.empId = empId;
	}

	public String getEmpId() 
	{
		return empId;
	}
	public void setAuditTime(Date auditTime) 
	{
		this.auditTime = auditTime;
	}

	public Date getAuditTime() 
	{
		return auditTime;
	}
	public void setAuditName(String auditName) 
	{
		this.auditName = auditName;
	}

	public String getAuditName() 
	{
		return auditName;
	}
	public void setAuditUserId(Long auditUserId) 
	{
		this.auditUserId = auditUserId;
	}

	public Long getAuditUserId() 
	{
		return auditUserId;
	}
	public void setAuditEmpId(String auditEmpId) 
	{
		this.auditEmpId = auditEmpId;
	}

	public String getAuditEmpId() 
	{
		return auditEmpId;
	}
	public void setAuditJob(String auditJob) 
	{
		this.auditJob = auditJob;
	}

	public String getAuditJob() 
	{
		return auditJob;
	}
	public void setAuditFlag(Integer auditFlag) 
	{
		this.auditFlag = auditFlag;
	}

	public Integer getAuditFlag() 
	{
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
	

	public EmpAttendBill getEmpAttendBill() {
		return empAttendBill;
	}

	public void setEmpAttendBill(EmpAttendBill empAttendBill) {
		this.empAttendBill = empAttendBill;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("attendAuditId", getAttendAuditId())
            .append("attendBillId", getAttendBillId())
            .append("userId", getUserId())
            .append("empId", getEmpId())
            .append("auditTime", getAuditTime())
            .append("auditName", getAuditName())
            .append("auditUserId", getAuditUserId())
            .append("auditEmpId", getAuditEmpId())
            .append("auditJob", getAuditJob())
            .append("auditFlag", getAuditFlag())
            .append("remark", getRemark())
            .toString();
    }
}
