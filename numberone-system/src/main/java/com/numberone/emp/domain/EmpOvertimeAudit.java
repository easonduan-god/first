package com.numberone.emp.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.numberone.common.base.BaseEntity;

/**
 * 延时工作单审核表 emp_overtime_audit
 * 
 * @author eason
 * @date 2020-04-06
 */
public class EmpOvertimeAudit extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	
	/** 延时工作单审核表单id */
	private String overtimeAuditId;
	/** 延时工作单id */
	private String overtimeBillId;
	/** 员工工号 */
	private String empId;
	/** 用户id */
	private Long userId;
	/** 审核人 */
	private String auditName;
	/** 审核人id */
	private Long auditUserId;
	/** 审核人工号 */
	private String auditEmpId;
	/** 审核人职位 */
	private String auditJob;
	/** 审核时间 */
	private Date auditTime;
	/** 审核状态(0未审核 1审核中 2审核不通过 3审核已通过) */
	private Integer auditFlag;
	/** 审核状态(0未审核 1审核中 2审核不通过 3审核已通过) */
	private String auditFlagText;
	/** 备注 */
	private String remark;
	
	private EmpOvertimeBill empOvertimeBill;
	public void setOvertimeAuditId(String overtimeAuditId) 
	{
		this.overtimeAuditId = overtimeAuditId;
	}

	public String getOvertimeAuditId() 
	{
		return overtimeAuditId;
	}
	public void setOvertimeBillId(String overtimeBillId) 
	{
		this.overtimeBillId = overtimeBillId;
	}

	public String getOvertimeBillId() 
	{
		return overtimeBillId;
	}
	public void setEmpId(String empId) 
	{
		this.empId = empId;
	}

	public String getEmpId() 
	{
		return empId;
	}
	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}

	public Long getUserId() 
	{
		return userId;
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
	public void setAuditTime(Date auditTime) 
	{
		this.auditTime = auditTime;
	}

	public Date getAuditTime() 
	{
		return auditTime;
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
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("overtimeAuditId", getOvertimeAuditId())
            .append("overtimeBillId", getOvertimeBillId())
            .append("empId", getEmpId())
            .append("userId", getUserId())
            .append("auditName", getAuditName())
            .append("auditUserId", getAuditUserId())
            .append("auditEmpId", getAuditEmpId())
            .append("auditJob", getAuditJob())
            .append("auditTime", getAuditTime())
            .append("auditFlag", getAuditFlag())
            .append("remark", getRemark())
            .toString();
    }

    public EmpOvertimeBill getEmpOvertimeBill() {
		return empOvertimeBill;
	}

	public void setEmpOvertimeBill(EmpOvertimeBill empOvertimeBill) {
		this.empOvertimeBill = empOvertimeBill;
	}
}
