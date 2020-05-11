package com.numberone.work.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.numberone.common.annotation.Excel;
import com.numberone.common.annotation.Excel.Type;
import com.numberone.common.base.BaseEntity;

/**
 * 员工任务月报：存储员工每月处理任务数据表 work_task_emp_report
 * 
 * @author eason
 * @date 2020-05-06
 */
public class WorkTaskEmpReport extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录id */
	private String recordId;
	/** 用户id */
	@Excel(name = "用户编号")
	private Integer userId;
	/** 员工号 */
	@Excel(name = "员工号")
	private String empId;
	/** 用户名 */
	@Excel(name = "用户名")
	private String userName;
	private Long deptId;
	@Excel(name = "部门名")
	private String deptName;
	/** 月份 */
	@Excel(name = "月份", dateFormat = "yyyyMM", type = Type.EXPORT)
	private Date month;
	/** 报表生成时间 */
	@Excel(name = "报表生成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
	private Date createTime;
	/** 总工时 */
	@Excel(name = "总工时")
	private Double totalHours;
	/** 处理任务数 */
	@Excel(name = "处理任务数")
	private Integer taskCount;
	/** 平均任务工时 */
	@Excel(name = "平均任务工时")
	private Double avgTaskHours;
	/** 任务完成率 */
	@Excel(name = "任务完成率")
	private Double taskCompleteRate;
	/** 任务返工率 */
	@Excel(name = "任务返工率")
	private Double taskReworkRate;
	/** 任务返工次数 */
	@Excel(name = "任务返工次数")
	private Integer taskReworkCount;
	/** 任务变更率 */
	@Excel(name = "任务变更率")
	private Double taskAlterRate;
	/** 任务变更次数 */
	@Excel(name = "任务变更次数")
	private Integer taskAlterCount;

	public void setRecordId(String recordId) 
	{
		this.recordId = recordId;
	}

	public String getRecordId() 
	{
		return recordId;
	}
	public void setUserId(Integer userId) 
	{
		this.userId = userId;
	}

	public Integer getUserId() 
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
	public void setTotalHours(Double totalHours) 
	{
		this.totalHours = totalHours;
	}

	public Double getTotalHours() 
	{
		return totalHours;
	}
	public void setTaskCount(Integer taskCount) 
	{
		this.taskCount = taskCount;
	}

	public Integer getTaskCount() 
	{
		return taskCount;
	}
	public void setAvgTaskHours(Double avgTaskHours) 
	{
		this.avgTaskHours = avgTaskHours;
	}

	public Double getAvgTaskHours() 
	{
		return avgTaskHours;
	}
	public void setTaskCompleteRate(Double taskCompleteRate) 
	{
		this.taskCompleteRate = taskCompleteRate;
	}

	public Double getTaskCompleteRate() 
	{
		return taskCompleteRate;
	}
	public void setTaskReworkRate(Double taskReworkRate) 
	{
		this.taskReworkRate = taskReworkRate;
	}

	public Double getTaskReworkRate() 
	{
		return taskReworkRate;
	}
	public void setTaskReworkCount(Integer taskReworkCount) 
	{
		this.taskReworkCount = taskReworkCount;
	}

	public Integer getTaskReworkCount() 
	{
		return taskReworkCount;
	}
	public void setTaskAlterRate(Double taskAlterRate) 
	{
		this.taskAlterRate = taskAlterRate;
	}

	public Double getTaskAlterRate() 
	{
		return taskAlterRate;
	}
	public void setTaskAlterCount(Integer taskAlterCount) 
	{
		this.taskAlterCount = taskAlterCount;
	}

	public Integer getTaskAlterCount() 
	{
		return taskAlterCount;
	}

    public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("userId", getUserId())
            .append("empId", getEmpId())
            .append("userName", getUserName())
            .append("month", getMonth())
            .append("createTime", getCreateTime())
            .append("totalHours", getTotalHours())
            .append("taskCount", getTaskCount())
            .append("avgTaskHours", getAvgTaskHours())
            .append("taskCompleteRate", getTaskCompleteRate())
            .append("taskReworkRate", getTaskReworkRate())
            .append("taskReworkCount", getTaskReworkCount())
            .append("taskAlterRate", getTaskAlterRate())
            .append("taskAlterCount", getTaskAlterCount())
            .toString();
    }
}
