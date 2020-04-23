package com.numberone.work.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.numberone.common.base.BaseEntity;
import com.numberone.system.domain.SysFileBean;

/**
 * 任务分配，执行，审核表 work_task
 * 
 * @author eason
 * @date 2020-04-11
 */
public class WorkTask extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录id */
	private String taskRecordId;
	/** 任务号：task_id组成 TS+年月日+四位数序号 */
	private String taskId;
	/** 任务标题 */
	private String taskTitle;
	/** 任务状态：task_flag待处理1/处理中2/处理完成3/已审核4/返工5 /变更审核中6*/
	private Integer taskFlag;
	/** 任务类型：task_type需求设计0/需求开发1/需求测试2/缺陷修复3/其他4 */
	private Integer taskType;
	private String taskTypeText;
	/** 难易程度：diff_level 简单任务（0.7/0.8/0.9）/一般难度（1.0）/较高难度（1.1）/次高难度（1.2）/最高难度（1.3）
             */
	private String diffLevel;
	/** 优先级：priority重要且紧急1/重要2/紧急3/普通4/基金相关 */
	private Integer priority;
	/** 计划开始时间：plan_start_date 年月日 */
	private Date planStartDate;
	/** 计划完成时间 */
	private Date planEndDate;
	/** 实际开始时间：plan_start_date 年月日 */
	private Date realStartDate;
	/** 实际完成时间 */
	private Date realEndDate;
	/** 预计工时 */
	private Integer planHours;
	/** 处理工时 */
	private Integer dealHours;
	/** 审核工时 */
	private Integer auditHours;
	/** 任务描述 */
	private String taskDesc;
	/** 任务说明附件 文件id*/
	private String taskAppendix;
	/** 处理部门id */
	private Long dealDeptId;
	/** 处理部门名 */
	private String dealDeptName;
	/** 创建人用户id */
	private Long createUserId;
	/** 创建人工号 */
	private String createEmpId;
	/** 创建人姓名 */
	private String createUserName;
	/** 创建时间 精确到时分秒 */
	private Date createTime;
	/** 处理人用户id */
	private Long dealUserId;
	/** 处理人工号 */
	private String dealEmpId;
	/** 处理人姓名 */
	private String dealUserName;
	/** 处理时间 */
	private Date dealTime;
	/** 审核人用户id */
	private Long auditUserId;
	/** 审核人工号 */
	private String auditEmpId;
	/** 审核人姓名 */
	private String auditUserName;
	/** 审核时间 */
	private Date auditTime;
	/** 删除标志 0正常/1删除 */
	private Integer delFlag;
	/** 任务执行id */
	private String taskPerformId;
	/** 任务审核id */
	private String taskAuditId;
	private SysFileBean appendix;
	private WorkTaskPerform workTaskPerform;
	public WorkTask() {
		super();
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
	public void setTaskTitle(String taskTitle) 
	{
		this.taskTitle = taskTitle;
	}

	public String getTaskTitle() 
	{
		return taskTitle;
	}
	public void setTaskFlag(Integer taskFlag) 
	{
		this.taskFlag = taskFlag;
	}

	public Integer getTaskFlag() 
	{
		return taskFlag;
	}
	public void setTaskType(Integer taskType) 
	{
		this.taskType = taskType;
	}

	public Integer getTaskType() 
	{
		return taskType;
	}
	public void setDiffLevel(String diffLevel) 
	{
		this.diffLevel = diffLevel;
	}

	public String getDiffLevel() 
	{
		return diffLevel;
	}
	public void setPriority(Integer priority) 
	{
		this.priority = priority;
	}

	public Integer getPriority() 
	{
		return priority;
	}
	public void setPlanStartDate(Date planStartDate) 
	{
		this.planStartDate = planStartDate;
	}

	public Date getPlanStartDate() 
	{
		return planStartDate;
	}
	public void setPlanEndDate(Date planEndDate) 
	{
		this.planEndDate = planEndDate;
	}

	public Date getPlanEndDate() 
	{
		return planEndDate;
	}
	public void setPlanHours(Integer planHours) 
	{
		this.planHours = planHours;
	}

	public Integer getPlanHours() 
	{
		return planHours;
	}
	public void setDealHours(Integer dealHours) 
	{
		this.dealHours = dealHours;
	}

	public Integer getDealHours() 
	{
		return dealHours;
	}
	public void setAuditHours(Integer auditHours) 
	{
		this.auditHours = auditHours;
	}

	public Integer getAuditHours() 
	{
		return auditHours;
	}
	public void setTaskDesc(String taskDesc) 
	{
		this.taskDesc = taskDesc;
	}

	public String getTaskDesc() 
	{
		return taskDesc;
	}
	public void setTaskAppendix(String taskAppendix) 
	{
		this.taskAppendix = taskAppendix;
	}

	public String getTaskAppendix() 
	{
		return taskAppendix;
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
	public void setCreateUserId(Long createUserId) 
	{
		this.createUserId = createUserId;
	}

	public Long getCreateUserId() 
	{
		return createUserId;
	}
	public void setCreateEmpId(String createEmpId) 
	{
		this.createEmpId = createEmpId;
	}

	public String getCreateEmpId() 
	{
		return createEmpId;
	}
	public void setCreateUserName(String createUserName) 
	{
		this.createUserName = createUserName;
	}

	public String getCreateUserName() 
	{
		return createUserName;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setDealUserId(Long dealUserId) 
	{
		this.dealUserId = dealUserId;
	}

	public Long getDealUserId() 
	{
		return dealUserId;
	}
	public void setDealEmpId(String dealEmpId) 
	{
		this.dealEmpId = dealEmpId;
	}

	public String getDealEmpId() 
	{
		return dealEmpId;
	}
	public void setDealUserName(String dealUserName) 
	{
		this.dealUserName = dealUserName;
	}

	public String getDealUserName() 
	{
		return dealUserName;
	}
	public void setDealTime(Date dealTime) 
	{
		this.dealTime = dealTime;
	}

	public Date getDealTime() 
	{
		return dealTime;
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
	public void setAuditUserName(String auditUserName) 
	{
		this.auditUserName = auditUserName;
	}

	public String getAuditUserName() 
	{
		return auditUserName;
	}
	public void setAuditTime(Date auditTime) 
	{
		this.auditTime = auditTime;
	}

	public Date getAuditTime() 
	{
		return auditTime;
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

	public Date getRealStartDate() {
		return realStartDate;
	}

	public void setRealStartDate(Date realStartDate) {
		this.realStartDate = realStartDate;
	}

	public Date getRealEndDate() {
		return realEndDate;
	}

	public void setRealEndDate(Date realEndDate) {
		this.realEndDate = realEndDate;
	}

	public String getTaskTypeText() {
		return taskTypeText;
	}

	public void setTaskTypeText(String taskTypeText) {
		this.taskTypeText = taskTypeText;
	}

	public WorkTaskPerform getWorkTaskPerform() {
		return workTaskPerform;
	}

	public void setWorkTaskPerform(WorkTaskPerform workTaskPerform) {
		this.workTaskPerform = workTaskPerform;
	}

	public String getTaskPerformId() {
		return taskPerformId;
	}

	public void setTaskPerformId(String taskPerformId) {
		this.taskPerformId = taskPerformId;
	}

	public String getTaskAuditId() {
		return taskAuditId;
	}

	public void setTaskAuditId(String taskAuditId) {
		this.taskAuditId = taskAuditId;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskRecordId", getTaskRecordId())
            .append("taskId", getTaskId())
            .append("taskTitle", getTaskTitle())
            .append("taskFlag", getTaskFlag())
            .append("taskType", getTaskType())
            .append("diffLevel", getDiffLevel())
            .append("priority", getPriority())
            .append("planStartDate", getPlanStartDate())
            .append("planEndDate", getPlanEndDate())
            .append("realStartDate", getRealStartDate())
            .append("realEndDate", getRealEndDate())
            .append("planHours", getPlanHours())
            .append("dealHours", getDealHours())
            .append("auditHours", getAuditHours())
            .append("taskDesc", getTaskDesc())
            .append("taskAppendix", getTaskAppendix())
            .append("dealDeptId", getDealDeptId())
            .append("dealDeptName", getDealDeptName())
            .append("createUserId", getCreateUserId())
            .append("createEmpId", getCreateEmpId())
            .append("createUserName", getCreateUserName())
            .append("createTime", getCreateTime())
            .append("dealUserId", getDealUserId())
            .append("dealEmpId", getDealEmpId())
            .append("dealUserName", getDealUserName())
            .append("dealTime", getDealTime())
            .append("auditUserId", getAuditUserId())
            .append("auditEmpId", getAuditEmpId())
            .append("auditUserName", getAuditUserName())
            .append("auditTime", getAuditTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
