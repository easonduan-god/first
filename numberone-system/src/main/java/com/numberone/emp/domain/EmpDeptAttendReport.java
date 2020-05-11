package com.numberone.emp.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.numberone.common.annotation.Excel;
import com.numberone.common.annotation.Excel.Type;
import com.numberone.common.base.BaseEntity;

/**
 * 部门考勤月报：用于统计每个部门的总体考勤情况，每个月的月初统计上个月的考勤   表 emp_dept_attend_report
 * 
 * @author eason
 * @date 2020-05-09
 */
public class EmpDeptAttendReport extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录id */
	private String recordId;
	/** 部门id */
	private Long deptId;
	/** 部门名 */
	@Excel(name = "部门名")
	private String deptName;
	/** 部门领导 */
	@Excel(name = "部门领导")
	private String deptLeader;
	/** 月份 */
	@Excel(name = "月份", width = 30, dateFormat = "yyyyMM", type = Type.EXPORT)
	private Date month;
	/** 报表生成时间 */
	@Excel(name = "报表生成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
	private Date createTime;
	/** 员工数 */
	@Excel(name = "员工数")
	private Integer empCount;
	/** 到勤率 */
	private Double attendRate;
	/** 迟到率 */
	@Excel(name = "迟到率")
	private Double lateRate;
	/** 早退率 */
	@Excel(name = "早退率")
	private Double earlyRate;
	/** 旷工率 */
	@Excel(name = "旷工率")
	private Double absentRate;
	/** 忘记打卡率 */
	@Excel(name = "忘记打卡率")
	private Double forgetRate;
	/** 请假率 */
	@Excel(name = "请假率")
	private Double leaveRate;
	/** 到勤次数 */
	private Integer attendCount;
	/** 早退次数 */
	@Excel(name = "早退次数")
	private Integer earlyCount;
	/** 迟到次数 */
	@Excel(name = "迟到次数")
	private Integer lateCount;
	/** 旷工次数 */
	@Excel(name = "旷工次数")
	private Integer absentCount;
	/** 忘记打卡次数 */
	@Excel(name = "忘记打卡次数")
	private Integer forgetCount;
	/** 请假次数 */
	@Excel(name = "请假次数")
	private Integer leaveCount;
	/** 请假天数 */
	@Excel(name = "请假天数")
	private Integer leaveDays;
	/** 请假工时 */
	@Excel(name = "请假工时")
	private Double leaveHours;
	/** 考勤异常率 */
	@Excel(name = "考勤异常率")
	private Double abnormalRate;
	/** 累计加班次数 */
	@Excel(name = "累计加班次数")
	private Integer overtimeCount;
	/** 完美考勤人次（没请假，没异常，可以忘记打卡） */
	@Excel(name = "完美考勤人次")
	private Integer perfectCount;
	/** 加班率 */
	@Excel(name = "加班率")
	private Double overtimeRate;
	/** 累计加班工时 */
	@Excel(name = "累计加班工时")
	private Double overtimeHours;
	/** 累计加班人次 */
	@Excel(name = "累计加班人次")
	private Integer overtimePersonCount;
	/** 每人平均加班工时 */
	@Excel(name = "每人平均加班工时",width = 30)
	private Double avgOvertimeHours;
	/** 未加班人次 */
	@Excel(name = "未加班人次")
	private Integer noOvertimePersonCount;
	/** 加班工时小于30人次 */
	@Excel(name = "加班工时小于30人次",width = 30)
	private Integer overtimeLess30;
	/** 加班工时大于100人次 */
	@Excel(name = "加班工时大于100人次",width = 30)
	private Integer overtimeMore100;
	/** 休息日加班人次 */
	private Integer restOvertimeCount;
	/** 休息日加班人数 */
	private Integer restOvertimePersonCounta;
	/** 休息日加班率 */
	private Double restOvertimeRate;
	/** 休息日加班工时 */
	private Double restOvertimeHours;
	/** 休息日平均加班工时 */
	private Double avgRestOvertimeHours;
	/** 休息日未加班人次 */
	private Integer restNoOvertimePersonCount;

	public void setRecordId(String recordId) 
	{
		this.recordId = recordId;
	}

	public String getRecordId() 
	{
		return recordId;
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
	public void setDeptLeader(String deptLeader) 
	{
		this.deptLeader = deptLeader;
	}

	public String getDeptLeader() 
	{
		return deptLeader;
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
	public void setEmpCount(Integer empCount) 
	{
		this.empCount = empCount;
	}

	public Integer getEmpCount() 
	{
		return empCount;
	}
	public void setAttendRate(Double attendRate) 
	{
		this.attendRate = attendRate;
	}

	public Double getAttendRate() 
	{
		return attendRate;
	}
	public void setLateRate(Double lateRate) 
	{
		this.lateRate = lateRate;
	}

	public Double getLateRate() 
	{
		return lateRate;
	}
	public void setEarlyRate(Double earlyRate) 
	{
		this.earlyRate = earlyRate;
	}

	public Double getEarlyRate() 
	{
		return earlyRate;
	}
	public void setAbsentRate(Double absentRate) 
	{
		this.absentRate = absentRate;
	}

	public Double getAbsentRate() 
	{
		return absentRate;
	}
	public void setForgetRate(Double forgetRate) 
	{
		this.forgetRate = forgetRate;
	}

	public Double getForgetRate() 
	{
		return forgetRate;
	}
	public void setLeaveRate(Double leaveRate) 
	{
		this.leaveRate = leaveRate;
	}

	public Double getLeaveRate() 
	{
		return leaveRate;
	}
	public void setAttendCount(Integer attendCount) 
	{
		this.attendCount = attendCount;
	}

	public Integer getAttendCount() 
	{
		return attendCount;
	}
	public void setEarlyCount(Integer earlyCount) 
	{
		this.earlyCount = earlyCount;
	}

	public Integer getEarlyCount() 
	{
		return earlyCount;
	}
	public void setLateCount(Integer lateCount) 
	{
		this.lateCount = lateCount;
	}

	public Integer getLateCount() 
	{
		return lateCount;
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
	public void setPerfectCount(Integer perfectCount) 
	{
		this.perfectCount = perfectCount;
	}

	public Integer getPerfectCount() 
	{
		return perfectCount;
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
	public void setOvertimePersonCount(Integer overtimePersonCount) 
	{
		this.overtimePersonCount = overtimePersonCount;
	}

	public Integer getOvertimePersonCount() 
	{
		return overtimePersonCount;
	}
	public void setAvgOvertimeHours(Double avgOvertimeHours) 
	{
		this.avgOvertimeHours = avgOvertimeHours;
	}

	public Double getAvgOvertimeHours() 
	{
		return avgOvertimeHours;
	}
	public void setNoOvertimePersonCount(Integer noOvertimePersonCount) 
	{
		this.noOvertimePersonCount = noOvertimePersonCount;
	}

	public Integer getNoOvertimePersonCount() 
	{
		return noOvertimePersonCount;
	}
	public void setOvertimeLess30(Integer overtimeLess30) 
	{
		this.overtimeLess30 = overtimeLess30;
	}

	public Integer getOvertimeLess30() 
	{
		return overtimeLess30;
	}
	public void setOvertimeMore100(Integer overtimeMore100) 
	{
		this.overtimeMore100 = overtimeMore100;
	}

	public Integer getOvertimeMore100() 
	{
		return overtimeMore100;
	}
	public void setRestOvertimeCount(Integer restOvertimeCount) 
	{
		this.restOvertimeCount = restOvertimeCount;
	}

	public Integer getRestOvertimeCount() 
	{
		return restOvertimeCount;
	}
	public void setRestOvertimePersonCounta(Integer restOvertimePersonCounta) 
	{
		this.restOvertimePersonCounta = restOvertimePersonCounta;
	}

	public Integer getRestOvertimePersonCounta() 
	{
		return restOvertimePersonCounta;
	}
	public void setRestOvertimeRate(Double restOvertimeRate) 
	{
		this.restOvertimeRate = restOvertimeRate;
	}

	public Double getRestOvertimeRate() 
	{
		return restOvertimeRate;
	}
	public void setRestOvertimeHours(Double restOvertimeHours) 
	{
		this.restOvertimeHours = restOvertimeHours;
	}

	public Double getRestOvertimeHours() 
	{
		return restOvertimeHours;
	}
	public void setAvgRestOvertimeHours(Double avgRestOvertimeHours) 
	{
		this.avgRestOvertimeHours = avgRestOvertimeHours;
	}

	public Double getAvgRestOvertimeHours() 
	{
		return avgRestOvertimeHours;
	}
	public void setRestNoOvertimePersonCount(Integer restNoOvertimePersonCount) 
	{
		this.restNoOvertimePersonCount = restNoOvertimePersonCount;
	}

	public Integer getRestNoOvertimePersonCount() 
	{
		return restNoOvertimePersonCount;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("deptLeader", getDeptLeader())
            .append("month", getMonth())
            .append("createTime", getCreateTime())
            .append("empCount", getEmpCount())
            .append("attendRate", getAttendRate())
            .append("lateRate", getLateRate())
            .append("earlyRate", getEarlyRate())
            .append("absentRate", getAbsentRate())
            .append("forgetRate", getForgetRate())
            .append("leaveRate", getLeaveRate())
            .append("attendCount", getAttendCount())
            .append("earlyCount", getEarlyCount())
            .append("lateCount", getLateCount())
            .append("absentCount", getAbsentCount())
            .append("forgetCount", getForgetCount())
            .append("leaveCount", getLeaveCount())
            .append("leaveDays", getLeaveDays())
            .append("leaveHours", getLeaveHours())
            .append("abnormalRate", getAbnormalRate())
            .append("overtimeCount", getOvertimeCount())
            .append("perfectCount", getPerfectCount())
            .append("overtimeRate", getOvertimeRate())
            .append("overtimeHours", getOvertimeHours())
            .append("overtimePersonCount", getOvertimePersonCount())
            .append("avgOvertimeHours", getAvgOvertimeHours())
            .append("noOvertimePersonCount", getNoOvertimePersonCount())
            .append("overtimeLess30", getOvertimeLess30())
            .append("overtimeMore100", getOvertimeMore100())
            .append("restOvertimeCount", getRestOvertimeCount())
            .append("restOvertimePersonCounta", getRestOvertimePersonCounta())
            .append("restOvertimeRate", getRestOvertimeRate())
            .append("restOvertimeHours", getRestOvertimeHours())
            .append("avgRestOvertimeHours", getAvgRestOvertimeHours())
            .append("restNoOvertimePersonCount", getRestNoOvertimePersonCount())
            .toString();
    }
}
