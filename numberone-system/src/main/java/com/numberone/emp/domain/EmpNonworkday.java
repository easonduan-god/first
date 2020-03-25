package com.numberone.emp.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.numberone.common.base.BaseEntity;
import com.numberone.common.utils.DateUtils;

public class EmpNonworkday extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7550699955973997241L;
	
	/** */
	private String nonworkId;//记录id
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date workdate;//日期
	private String workdateStr;//日期字符串
    private Integer workdateType;//日程类型 (0普通 1法定假日 2公司调休 3其他)

    private String workdateName;//日程名称

    private Integer workdateFlag;//日程状态(0正常 1工作日 2休息日)

    private String description;//描述

    public String getNonworkId() {
        return nonworkId;
    }

    public void setNonworkId(String nonworkId) {
        this.nonworkId = nonworkId == null ? null : nonworkId.trim();
    }

    public Date getWorkdate() {
        return workdate;
    }
    public String getWorkdateStr() {
    	if(this.workdate!=null){
    		this.workdateStr = DateUtils.dateTime(this.workdate);
    		return workdateStr;
    	}
    	return "";
    }

    public void setWorkdate(Date workdate) {
        this.workdate = workdate;
    }

    public Integer getWorkdateType() {
        return workdateType;
    }

    public void setWorkdateType(Integer workdateType) {
        this.workdateType = workdateType;
    }

    public String getWorkdateName() {
        return workdateName;
    }

    public void setWorkdateName(String workdateName) {
        this.workdateName = workdateName == null ? null : workdateName.trim();
    }

    public Integer getWorkdateFlag() {
        return workdateFlag;
    }

    public void setWorkdateFlag(Integer workdateFlag) {
        this.workdateFlag = workdateFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}