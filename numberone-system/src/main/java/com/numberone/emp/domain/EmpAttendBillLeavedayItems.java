package com.numberone.emp.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.numberone.common.base.BaseEntity;

/**
 * 新增考勤单休假日项表 emp_attend_bill_leaveday_items
 * 
 * @author eason
 * @date 2020-02-19
 */
public class EmpAttendBillLeavedayItems extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录id */
	private Integer recordId;
	/** 用户id */
	private Long userId;
	/** 员工号 */
	private String empId;
	/** 考勤单id */
	private String attendBillId;
	/** 休假日项 */
	private Date leavedayItem;
	/** 工时 */
	private Double workdayTime;
	/** 考勤类型 */
	private Integer attendType;

	public void setRecordId(Integer recordId) 
	{
		this.recordId = recordId;
	}

	public Integer getRecordId() 
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
	public void setEmpId(String empId) 
	{
		this.empId = empId;
	}

	public String getEmpId() 
	{
		return empId;
	}
	public void setAttendBillId(String attendBillId) 
	{
		this.attendBillId = attendBillId;
	}

	public String getAttendBillId() 
	{
		return attendBillId;
	}
	public void setLeavedayItem(Date leavedayItem) 
	{
		this.leavedayItem = leavedayItem;
	}

	public Date getLeavedayItem() 
	{
		return leavedayItem;
	}
	public void setWorkdayTime(Double workdayTime) 
	{
		this.workdayTime = workdayTime;
	}

	public Double getWorkdayTime() 
	{
		return workdayTime;
	}
	public void setAttendType(Integer attendType) 
	{
		this.attendType = attendType;
	}

	public Integer getAttendType() 
	{
		return attendType;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("userId", getUserId())
            .append("empId", getEmpId())
            .append("attendBillId", getAttendBillId())
            .append("leavedayItem", getLeavedayItem())
            .append("workdayTime", getWorkdayTime())
            .append("attendType", getAttendType())
            .toString();
    }
}
