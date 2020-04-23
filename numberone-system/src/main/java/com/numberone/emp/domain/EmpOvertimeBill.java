package com.numberone.emp.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.numberone.common.base.BaseEntity;

/**
 * 延时工作单表 emp_overtime_bill
 * 
 * @author eason
 * @date 2020-04-07
 */
public class EmpOvertimeBill extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 延时工作单id */
	private String overtimeBillId;
	/** 员工id */
	private String empId;
	/** 用户id */
	private Long userId;
	/** 主题 */
	private String theme;
	/** 所属部门 */
	private String deptName;
	/** 所属部门编号 */
	private Long deptId;
	/** 申请人 */
	private String userName;
	/** 申请日期 */
	private Date applyDate;
	/** 开始日期 */
	private Date startDate;
	/** 结束日期 */
	private Date endDate;
	/** 工作人员用户id */
	private String workPersons;
	/** 工作人员姓名 */
	private String workPersonNames;
	/** 每日申请时长 */
	private Double applyWorktimes;
	/** 事由 */
	private String matter;
	/** 完成状态(0未完成 1已完成) */
	private Integer completeFlag;
	/** 审核状态(0未审核 1审核中 2审核不通过 3审核已通过) */
	private Integer auditFlag;
	/** 删除标志(0未删除 1已删除) */
	private Integer delFlag;
	
	

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
	public void setTheme(String theme) 
	{
		this.theme = theme;
	}

	public String getTheme() 
	{
		return theme;
	}
	public void setDeptName(String deptName) 
	{
		this.deptName = deptName;
	}

	public String getDeptName() 
	{
		return deptName;
	}
	public void setDeptId(Long deptId) 
	{
		this.deptId = deptId;
	}

	public Long getDeptId() 
	{
		return deptId;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getUserName() 
	{
		return userName;
	}
	public void setApplyDate(Date applyDate) 
	{
		this.applyDate = applyDate;
	}

	public Date getApplyDate() 
	{
		return applyDate;
	}
	public void setStartDate(Date startDate) 
	{
		this.startDate = startDate;
	}

	public Date getStartDate() 
	{
		return startDate;
	}
	public void setEndDate(Date endDate) 
	{
		this.endDate = endDate;
	}

	public Date getEndDate() 
	{
		return endDate;
	}
	public void setWorkPersons(String workPersons) 
	{
		this.workPersons = workPersons;
	}

	public String getWorkPersons() 
	{
		return workPersons;
	}
	public void setWorkPersonNames(String workPersonNames) 
	{
		this.workPersonNames = workPersonNames;
	}

	public String getWorkPersonNames() 
	{
		return workPersonNames;
	}
	public void setApplyWorktimes(Double applyWorktimes) 
	{
		this.applyWorktimes = applyWorktimes;
	}

	public Double getApplyWorktimes() 
	{
		return applyWorktimes;
	}
	public void setMatter(String matter) 
	{
		this.matter = matter;
	}

	public String getMatter() 
	{
		return matter;
	}
	public void setCompleteFlag(Integer completeFlag) 
	{
		this.completeFlag = completeFlag;
	}

	public Integer getCompleteFlag() 
	{
		return completeFlag;
	}
	public void setAuditFlag(Integer auditFlag) 
	{
		this.auditFlag = auditFlag;
	}

	public Integer getAuditFlag() 
	{
		return auditFlag;
	}
	public void setDelFlag(Integer delFlag) 
	{
		this.delFlag = delFlag;
	}

	public Integer getDelFlag() 
	{
		return delFlag;
	}



	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("overtimeBillId", getOvertimeBillId())
            .append("empId", getEmpId())
            .append("userId", getUserId())
            .append("theme", getTheme())
            .append("deptName", getDeptName())
            .append("deptId", getDeptId())
            .append("userName", getUserName())
            .append("applyDate", getApplyDate())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("workPersons", getWorkPersons())
            .append("workPersonNames", getWorkPersonNames())
            .append("applyWorktimes", getApplyWorktimes())
            .append("matter", getMatter())
            .append("completeFlag", getCompleteFlag())
            .append("auditFlag", getAuditFlag())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
