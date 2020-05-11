package com.numberone.emp.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberone.common.annotation.Excel;
import com.numberone.common.annotation.Excel.Type;
import com.numberone.common.base.BaseEntity;

/**
 * 员工考勤月报：用于统计每一个员工的月度考勤，每一个月的月初统计上个月的考勤表 emp_attend_report
 * 
 * @author eason
 * @date 2020-05-09
 */
public class EmpAttendReport extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录id */
	@Excel(name = "recordId")
	private String recordId;
	/** 用户id */
	@Excel(name = "userId")
	private Long userId;
	/** 员工号 */
	@Excel(name = "userId")
	private String empId;
	/** 用户名 */
	@Excel(name = "userId")
	private String userName;
	/** 部门id */
	@Excel(name = "userId")
	private Long deptId;
	/** 部门名 */
	@Excel(name = "userId")
	private String deptName;
	/** 月份 */
	@JsonFormat(pattern = "yyyyMM")
	@Excel(name = "月份", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
	private Date month;
	/** 报表生成时间 */
	@Excel(name = "报表生成时间")
	private Date createTime;
	/** 迟到次数 */
	private Integer lateCount;
	/** 早退次数 */
	private Integer earlyCount;
	/** 旷工次数 */
	private Integer absentCount;
	/** 忘记打卡次数 */
	private Integer forgetCount;
	/** 请假次数 */
	private Integer leaveCount;
	/** 请假天数 */
	private Integer leaveDays;
	/** 请假工时 */
	private Double leaveHours;
	/** 到勤率 */
	private Double attendRate;
	/** 考勤异常率 */
	private Double abnormalRate;
	/** 累计加班次数 */
	private Integer overtimeCount;
	/** 加班率 */
	private Double overtimeRate;
	/** 累计加班工时 */
	private Double overtimeHours;
	/** 平均加班工时（工时/次数） */
	private Double avgOvertimeHours;

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
	public void setEmpId(String empId) 
	{
		this.empId = empId;
	}

	public String getEmpId() 
	{
		return empId;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getUserName() 
	{
		return userName;
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
	public void setMonth(Date month) 
	{
		this.month = month;
	}

	public Date getMonth() 
	{
		return month;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setLateCount(Integer lateCount) 
	{
		this.lateCount = lateCount;
	}

	public Integer getLateCount() 
	{
		return lateCount;
	}
	public void setEarlyCount(Integer earlyCount) 
	{
		this.earlyCount = earlyCount;
	}

	public Integer getEarlyCount() 
	{
		return earlyCount;
	}
	public void setAbsentCount(Integer absentCount) 
	{
		this.absentCount = absentCount;
	}

	public Integer getAbsentCount() 
	{
		return absentCount;
	}
	public void setForgetCount(Integer forgetCount) 
	{
		this.forgetCount = forgetCount;
	}

	public Integer getForgetCount() 
	{
		return forgetCount;
	}
	public void setLeaveCount(Integer leaveCount) 
	{
		this.leaveCount = leaveCount;
	}

	public Integer getLeaveCount() 
	{
		return leaveCount;
	}
	public void setLeaveDays(Integer leaveDays) 
	{
		this.leaveDays = leaveDays;
	}

	public Integer getLeaveDays() 
	{
		return leaveDays;
	}
	public void setLeaveHours(Double leaveHours) 
	{
		this.leaveHours = leaveHours;
	}

	public Double getLeaveHours() 
	{
		return leaveHours;
	}
	public void setAttendRate(Double attendRate) 
	{
		this.attendRate = attendRate;
	}

	public Double getAttendRate() 
	{
		return attendRate;
	}
	public void setAbnormalRate(Double abnormalRate) 
	{
		this.abnormalRate = abnormalRate;
	}

	public Double getAbnormalRate() 
	{
		return abnormalRate;
	}
	public void setOvertimeCount(Integer overtimeCount) 
	{
		this.overtimeCount = overtimeCount;
	}

	public Integer getOvertimeCount() 
	{
		return overtimeCount;
	}
	public void setOvertimeRate(Double overtimeRate) 
	{
		this.overtimeRate = overtimeRate;
	}

	public Double getOvertimeRate() 
	{
		return overtimeRate;
	}
	public void setOvertimeHours(Double overtimeHours) 
	{
		this.overtimeHours = overtimeHours;
	}

	public Double getOvertimeHours() 
	{
		return overtimeHours;
	}
	public void setAvgOvertimeHours(Double avgOvertimeHours) 
	{
		this.avgOvertimeHours = avgOvertimeHours;
	}

	public Double getAvgOvertimeHours() 
	{
		return avgOvertimeHours;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("userId", getUserId())
            .append("empId", getEmpId())
            .append("userName", getUserName())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("month", getMonth())
            .append("createTime", getCreateTime())
            .append("lateCount", getLateCount())
            .append("earlyCount", getEarlyCount())
            .append("absentCount", getAbsentCount())
            .append("forgetCount", getForgetCount())
            .append("leaveCount", getLeaveCount())
            .append("leaveDays", getLeaveDays())
            .append("leaveHours", getLeaveHours())
            .append("attendRate", getAttendRate())
            .append("abnormalRate", getAbnormalRate())
            .append("overtimeCount", getOvertimeCount())
            .append("overtimeRate", getOvertimeRate())
            .append("overtimeHours", getOvertimeHours())
            .append("avgOvertimeHours", getAvgOvertimeHours())
            .toString();
    }
}
