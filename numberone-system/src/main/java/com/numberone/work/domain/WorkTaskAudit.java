package com.numberone.work.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.numberone.common.base.BaseEntity;
import com.numberone.system.domain.SysUser;

/**
 * 任务审核 work_task_audit表 work_task_audit
 * 
 * @author eason
 * @date 2020-04-11
 */
public class WorkTaskAudit extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 任务审核id */
	private String taskAuditId;
	/** 任务记录id */
	private String taskRecordId;
	/** 任务号 */
	private String taskId;
	/** 任务执行表id */
	private String taskPerformId;
	/** 审核类型 任务提交审核submit 1/任务变更审核alter 2*/
	private Integer auditType;
	/** 发起人用户id */
	private Long launchUserId;
	/** 发起人用户名 */
	private String launchUserName;
	/** 发起人工号 */
	private String launchEmpId;
	/** 发起时间 */
	private Date launchTime;
	/** 审核人用户id */
	private Long auditUserId;
	/** 审核人用户名 */
	private String auditUserName;
	/** 审核人工号 */
	private String auditEmpId;
	/** 审核人职位 */
	private String auditJob;
	/** 审核时间 */
	private Date auditTime;
	/** 审核状态 0未审核 1审核中 2审核不通过 3审核已通过 */
	private Integer auditFlag;
	/** 备注 */
	private String remark;
	private WorkTask task;
	private WorkTaskPerform perform;
	private SysUser user;
	public void setTaskAuditId(String taskAuditId) 
	{
		this.taskAuditId = taskAuditId;
	}

	public String getTaskAuditId() 
	{
		return taskAuditId;
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
	public void setTaskPerformId(String taskPerformId) 
	{
		this.taskPerformId = taskPerformId;
	}

	public String getTaskPerformId() 
	{
		return taskPerformId;
	}
	public void setAuditType(Integer auditType) 
	{
		this.auditType = auditType;
	}

	public Integer getAuditType() 
	{
		return auditType;
	}
	public void setLaunchUserId(Long launchUserId) 
	{
		this.launchUserId = launchUserId;
	}

	public Long getLaunchUserId() 
	{
		return launchUserId;
	}
	public void setLaunchEmpId(String launchEmpId) 
	{
		this.launchEmpId = launchEmpId;
	}

	public String getLaunchEmpId() 
	{
		return launchEmpId;
	}
	public void setLaunchTime(Date launchTime) 
	{
		this.launchTime = launchTime;
	}

	public Date getLaunchTime() 
	{
		return launchTime;
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
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}

    public WorkTask getTask() {
		return task;
	}

	public void setTask(WorkTask task) {
		this.task = task;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public String getLaunchUserName() {
		return launchUserName;
	}

	public void setLaunchUserName(String launchUserName) {
		this.launchUserName = launchUserName;
	}

	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	public WorkTaskPerform getPerform() {
		return perform;
	}

	public void setPerform(WorkTaskPerform perform) {
		this.perform = perform;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskAuditId", getTaskAuditId())
            .append("taskRecordId", getTaskRecordId())
            .append("taskId", getTaskId())
            .append("taskPerformId", getTaskPerformId())
            .append("auditType", getAuditType())
            .append("launchUserId", getLaunchUserId())
            .append("launchEmpId", getLaunchEmpId())
            .append("launchTime", getLaunchTime())
            .append("auditUserId", getAuditUserId())
            .append("auditEmpId", getAuditEmpId())
            .append("auditJob", getAuditJob())
            .append("auditTime", getAuditTime())
            .append("auditFlag", getAuditFlag())
            .append("remark", getRemark())
            .toString();
    }
}
