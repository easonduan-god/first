package com.numberone.work.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.numberone.common.annotation.Excel;
import com.numberone.common.annotation.Excel.Type;
import com.numberone.common.base.BaseEntity;

/**
 * 部门任务月报：存储部门每月处理任务的数据表 work_task_dept_report
 * 
 * @author eason
 * @date 2020-05-09
 */
public class WorkTaskDeptReport extends BaseEntity
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
	@Excel(name = "deptLeader")
	private String deptLeader;
	/** 月份 */
	@Excel(name = "月份", width = 30, dateFormat = "yyyyMM", type = Type.EXPORT)
	private Date month;
	/** 报表生成时间 */
	@Excel(name = "报表生成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
	private Date createTime;
	/** 员工数 */
	@Excel(name = "deptLeader")
	private Integer empCount;
	/** 总工时 */
	@Excel(name = "deptLeader")
	private Double totalHours;
	/** 处理任务数 */
	@Excel(name = "deptLeader")
	private Integer taskCount;
	/** 平均任务工时 */
	@Excel(name = "deptLeader")
	private Double avgTaskHours;
	/** 平均任务数 */
	@Excel(name = "deptLeader")
	private Integer avgTaskCount;
	/** 任务完成率 */
	private Double taskCompleteRate;
	/** 任务返工率 */
	private Double taskReworkRate;
	/** 任务返工次数 */
	private Integer taskReworkCount;
	/** 任务变更率 */
	private Double taskAlterRate;
	/** 任务变更次数 */
	private Integer taskAlterCount;

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
	public void setAvgTaskCount(Integer avgTaskCount) 
	{
		this.avgTaskCount = avgTaskCount;
	}

	public Integer getAvgTaskCount() 
	{
		return avgTaskCount;
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

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("deptLeader", getDeptLeader())
            .append("month", getMonth())
            .append("createTime", getCreateTime())
            .append("empCount", getEmpCount())
            .append("totalHours", getTotalHours())
            .append("taskCount", getTaskCount())
            .append("avgTaskHours", getAvgTaskHours())
            .append("avgTaskCount", getAvgTaskCount())
            .append("taskCompleteRate", getTaskCompleteRate())
            .append("taskReworkRate", getTaskReworkRate())
            .append("taskReworkCount", getTaskReworkCount())
            .append("taskAlterRate", getTaskAlterRate())
            .append("taskAlterCount", getTaskAlterCount())
            .toString();
    }
}
