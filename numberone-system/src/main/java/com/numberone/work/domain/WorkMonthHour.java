package com.numberone.work.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.numberone.common.base.BaseEntity;

/**
 * 月度工时：记录员工月度处理任务的工时表 work_month_hour
 * 
 * @author eason
 * @date 2020-05-06
 */
public class WorkMonthHour extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String recordId;
	/**  */
	private Long userId;
	/**  */
	private Long deptId;
	/**  */
	private String deptName;
	/**  */
	private String userName;
	/**  */
	private Date month;
	/**  */
	private String empId;
	/**  */
	private Double hour;
	/**  */
	private String remark;

	public void setRecordId(String recordId) 
	{
		this.recordId = recordId;
	}

	public String getRecordId() 
	{
		return recordId;
	}
	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}

	public Long getUserId() 
	{
		return userId;
	}
	public void setDeptId(Long deptId) 
	{
		this.deptId = deptId;
	}

	public Long getDeptId() 
	{
		return deptId;
	}
	public void setDeptName(String deptName) 
	{
		this.deptName = deptName;
	}

	public String getDeptName() 
	{
		return deptName;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getUserName() 
	{
		return userName;
	}
	public void setMonth(Date month) 
	{
		this.month = month;
	}

	public Date getMonth() 
	{
		return month;
	}
	public void setEmpId(String empId) 
	{
		this.empId = empId;
	}

	public String getEmpId() 
	{
		return empId;
	}
	public void setHour(Double hour) 
	{
		this.hour = hour;
	}

	public Double getHour() 
	{
		return hour;
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
            .append("recordId", getRecordId())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("userName", getUserName())
            .append("month", getMonth())
            .append("empId", getEmpId())
            .append("hour", getHour())
            .append("remark", getRemark())
            .toString();
    }
}
