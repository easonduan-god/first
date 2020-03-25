package com.numberone.quartz.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.numberone.common.base.BaseEntity;
import com.numberone.common.utils.DateUtils;

public class EmpNonworkdayQuartz extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7550699955973997241L;

	private String nonworkId;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date workdate;
	private String workdateStr;
    private Integer workdateType;

    private String workdateName;

    private Integer workdateFlag;

    private String description;

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