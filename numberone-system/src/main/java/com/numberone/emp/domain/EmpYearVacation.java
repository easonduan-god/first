package com.numberone.emp.domain;

import com.numberone.common.annotation.Excel;
import com.numberone.common.base.BaseEntity;
import com.numberone.system.domain.SysDept;
import com.numberone.system.domain.SysUser;

public class EmpYearVacation extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8880938569430622489L;

	/** 记录号 */
	@Excel(name = "记录号")
	private String yearVacationId;
	/** 员工工号 */
	@Excel(name = "员工工号")
	private String empId;
	/** 年度 */
	@Excel(name = "年度")
	private Integer year;
	/** 年假共计 */
	@Excel(name = "年假共计")
	private Double timeTotal;
	/** 年假已使用 */
	@Excel(name = "年假已使用")
	private Double timeUsed;
	/** 年假剩余 */
	@Excel(name = "年假剩余")
	private Double timeSurplus;
	/** 备注 */
	@Excel(name = "备注")
	private String remark;
    private SysUser user;
    private SysDept dept;
    public String getYearVacationId() {
        return yearVacationId;
    }

    public void setYearVacationId(String yearVacationId) {
        this.yearVacationId = yearVacationId == null ? null : yearVacationId.trim();
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getTimeTotal() {
        return timeTotal;
    }

    public void setTimeTotal(Double timeTotal) {
        this.timeTotal = timeTotal;
    }

    public Double getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Double timeUsed) {
        this.timeUsed = timeUsed;
    }

    public Double getTimeSurplus() {
        return timeSurplus;
    }

    public void setTimeSurplus(Double timeSurplus) {
        this.timeSurplus = timeSurplus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public SysDept getDept() {
		return dept;
	}

	public void setDept(SysDept dept) {
		this.dept = dept;
	}
}