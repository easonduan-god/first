package com.numberone.emp.service.impl;

import java.util.ArrayList;
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
		String userIds = (String) empYearVacation.getParams().get("userIds");
		List<Integer> list = new ArrayList<Integer>();
		for(String userId : userIds.split(",")){
			list.add(Integer.parseInt(userId));
		}
		empYearVacation.getParams().put("userIds", list);
		return empYearVacationMapper.selectYearVacationList(empYearVacation);
	}
	@Override
	public List<EmpYearVacation> selectListByOperableUserIds(EmpYearVacation empYearVacation,
			List<Long> operableUserIds) {
		
		return empYearVacationMapper.selectListByOperableUserIds(empYearVacation,operableUserIds);
	}

}
