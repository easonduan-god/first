package com.numberone.emp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.emp.domain.EmpAttendday;
import com.numberone.emp.domain.EmpAttendinfo;
import com.numberone.emp.mapper.EmpAttenddayMapper;
import com.numberone.emp.mapper.EmpAttendinfoMapper;
import com.numberone.emp.service.IAttendDayclockService;

/**
 * 考勤日常打卡业务层实现类
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年1月26日 下午4:12:47
 * @param:
 */
@Service
public class AttendDayclockServiceImpl implements IAttendDayclockService{
	
	@Autowired
	private EmpAttendinfoMapper attendInfoMapper;
	
	@Autowired
	private EmpAttenddayMapper empAttenddayMapper;
	
	@Override
	public int insertDayClock(EmpAttendinfo empAttendinfo) {
		
		return attendInfoMapper.insert(empAttendinfo);
	}

	@Override
	public List<EmpAttendday> selectAttendDayList(EmpAttendday empAttendayday) {
		return empAttenddayMapper.selectAttendDayList(empAttendayday);
	}


	@Override
	public List<EmpAttendinfo> selectAttendInfoList(Long userId) {
		return attendInfoMapper.selectAttendinfoList(userId);
	}

	
}
