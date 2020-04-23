package com.numberone.work.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.numberone.common.base.BaseEntity;
import com.numberone.system.domain.SysFileBean;

/**
 * 任务执行表 work_task_perform
 * 
 * @author eason
 * @date 2020-04-11
 */
public class WorkTaskPerform extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 任务执行表id */
	private String taskPerformId;
	/** 任务记录id */
	private String taskRecordId;
	/** 任务号 */
	private String taskId;
	/** 任务执行人用户id */
	private Long performUserId;
	/** 任务执行人工号 */
	private String performEmpId;
	/** 任务执行时间 */
	private Date performTime;
	/** 执行人部门id */
	private Long dealDeptId;
	/** 执行人部门名 */
	private String dealDeptName;
	/** 变更执行人用户id */
	private Long alterUserId;
	/** 变更执行人工号 */
	private String alterEmpId;
	/** 变更时间 */
	private Date alterTime;
	/** 处理说明 */
	private String dealDesc;
	/** 任务状态 待处理1/处理中2/处理完成3/已审核4/返工5/变更审核中6 */
	private Integer taskFlag;
	/** 执行人变更标志 正常0/已变更1，变更前需审核，审核后作废 */
	private Integer alterPerformFlag;
	/** 删除标志 0正常/1删除 */
	private Integer delFlag;
	/** 工作提交物 */
	private String performAppendix;
	/** 备注 */
	private String remark;
	private SysFileBean appendix;
	private WorkTask workTask;
	private WorkTaskAudit workTaskAudit;
	public void setTaskPerformId(String taskPerformId) 
	{
		this.taskPerformId = taskPerformId;
	}

	public String getTaskPerformId() 
	{
		return taskPerformId;
	}
	public void setTaskRecordId(String taskRecordId) 
	{
		this.taskRecordId = taskRecordId;
	}

	public String getTaskRecordId() 
	{
		return taskRecordId;
	}
	public void setTaskId(String taskId) 
	{
		this.taskId = taskId;
	}

	public String getTaskId() 
	{
		return taskId;
	}
	public void setPerformUserId(Long performUserId) 
	{
		this.performUserId = performUserId;
	}

	public Long getPerformUserId() 
	{
		return performUserId;
	}
	public void setPerformEmpId(String performEmpId) 
	{
		this.performEmpId = performEmpId;
	}

	public String getPerformEmpId() 
	{
		return performEmpId;
	}
	public void setPerformTime(Date performTime) 
	{
		this.performTime = performTime;
	}

	public Date getPerformTime() 
	{
		return performTime;
	}
	public void setDealDeptId(Long dealDeptId) 
	{
		this.dealDeptId = dealDeptId;
	}

	public Long getDealDeptId() 
	{
		return dealDeptId;
	}
	public void setDealDeptName(String dealDeptName) 
	{
		this.dealDeptName = dealDeptName;
	}

	public String getDealDeptName() 
	{
		return dealDeptName;
	}
	public void setAlterUserId(Long alterUserId) 
	{
		this.alterUserId = alterUserId;
	}

	public Long getAlterUserId() 
	{
		return alterUserId;
	}
	public void setAlterEmpId(String alterEmpId) 
	{
		this.alterEmpId = alterEmpId;
	}

	public String getAlterEmpId() 
	{
		return alterEmpId;
	}
	public void setAlterTime(Date alterTime) 
	{
		this.alterTime = alterTime;
	}

	public Date getAlterTime() 
	{
		return alterTime;
	}
	public void setDealDesc(String dealDesc) 
	{
		this.dealDesc = dealDesc;
	}

	public String getDealDesc() 
	{
		return dealDesc;
	}
	public void setTaskFlag(Integer taskFlag) 
	{
		this.taskFlag = taskFlag;
	}

	public Integer getTaskFlag() 
	{
		return taskFlag;
	}
	public void setAlterPerformFlag(Integer alterPerformFlag) 
	{
		this.alterPerformFlag = alterPerformFlag;
	}

	public Integer getAlterPerformFlag() 
	{
		return alterPerformFlag;
	}
	public void setDelFlag(Integer delFlag) 
	{
		this.delFlag = delFlag;
	}

	public Integer getDelFlag() 
	{
		return delFlag;
	}

    public SysFileBean getAppendix() {
		return appendix;
	}

	public void setAppendix(SysFileBean appendix) {
		this.appendix = appendix;
	}

	public String getPerformAppendix() {
		return performAppendix;
	}

	public void setPerformAppendix(String performAppendix) {
		this.performAppendix = performAppendix;
	}

	public WorkTask getWorkTask() {
		return workTask;
	}

	public void setWorkTask(WorkTask workTask) {
		this.workTask = workTask;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public WorkTaskAudit getWorkTaskAudit() {
		return workTaskAudit;
	}

	public void setWorkTaskAudit(WorkTaskAudit workTaskAudit) {
		this.workTaskAudit = workTaskAudit;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskPerformId", getTaskPerformId())
            .append("taskRecordId", getTaskRecordId())
            .append("taskId", getTaskId())
            .append("performUserId", getPerformUserId())
            .append("performEmpId", getPerformEmpId())
            .append("performTime", getPerformTime())
            .append("dealDeptId", getDealDeptId())
            .append("dealDeptName", getDealDeptName())
            .append("alterUserId", getAlterUserId())
            .append("alterEmpId", getAlterEmpId())
            .append("alterTime", getAlterTime())
            .append("dealDesc", getDealDesc())
            .append("taskFlag", getTaskFlag())
            .append("alterPerformFlag", getAlterPerformFlag())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
