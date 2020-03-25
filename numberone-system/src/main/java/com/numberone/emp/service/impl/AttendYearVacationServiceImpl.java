package com.numberone.emp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.emp.domain.EmpYearVacation;
import com.numberone.emp.mapper.EmpYearVacationMapper;
import com.numberone.emp.service.IAttendYearVacationService;

@Service
public class AttendYearVacationServiceImpl implements IAttendYearVacationService {
	
	@Autowired
	private EmpYearVacationMapper empYearVacationMapper;
	@Override
	public List<EmpYearVacation> selectYearVacationList(EmpYearVacation empYearVacation) {
		return empYearVacationMapper.selectYearVacationList(empYearVacation);
	}
	@Override
	public List<EmpYearVacation> selectListByOperableUserIds(EmpYearVacation empYearVacation,
			List<Long> operableUserIds) {
		
		return empYearVacationMapper.selectListByOperableUserIds(empYearVacation,operableUserIds);
	}

}
