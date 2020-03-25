package com.numberone.emp.service;

import java.util.List;

import com.numberone.emp.domain.EmpYearVacation;

public interface IAttendYearVacationService {
	
	/**
	 * 条件查询年假列表
	 * @param: @param empYearVacation
	 * @param: @return 参数说明
	 * @return: List<EmpYearVacation> 返回类型
	 * @throws
	 */
	List<EmpYearVacation> selectYearVacationList(EmpYearVacation empYearVacation);
	
	/**
	 * 条件查询年假列表 其中用户未所有可操作用户
	 * @param: @param empAttendAudit
	 * @param: @return 所有可操作用户的id
	 * @return List<EmpAttendAudit> 返回类型
	 */
	List<EmpYearVacation> selectListByOperableUserIds(EmpYearVacation empYearVacation, List<Long> operableUserIds);

}
