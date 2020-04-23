package com.numberone.emp.service;

import java.util.List;

import com.numberone.emp.domain.EmpYearVacation;

public interface IAttendYearVacationService {
	
	/**
	 * 条件查询年假列表
	 * @param: @param empYearVacation
	 * @param: @return
	 * @return: List<EmpYearVacation>
	 */
	List<EmpYearVacation> selectYearVacationList(EmpYearVacation empYearVacation);
	
	/**
	 * 条件查询年假列表 其中用户未所有可操作用户
	 * @param: @param empYearVacation
	 * @param: @param operableUserIds
	 * @param: @return
	 * @return: List<EmpYearVacation>
	 */
	List<EmpYearVacation> selectListByOperableUserIds(EmpYearVacation empYearVacation, List<Long> operableUserIds);

}
