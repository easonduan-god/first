package com.numberone.emp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.common.constant.AttendConstants;
import com.numberone.common.support.Convert;
import com.numberone.common.utils.StringUtils;
import com.numberone.emp.domain.EmpNonworkday;
import com.numberone.emp.mapper.EmpNonworkdayMapper;
import com.numberone.emp.service.IAttendWorkdayService;
import com.numberone.system.domain.SysUser;

@Service
public class AttendWorkdayServiceImpl implements IAttendWorkdayService {
	
	@Autowired
	private EmpNonworkdayMapper empNonworkdayMapper;
	
	@Override
	public List<EmpNonworkday> selectWorkdayList(EmpNonworkday empNonworkday) {
		
		return empNonworkdayMapper.selectEmpNonworkdayList(empNonworkday);
	}

	@Override
	public int insertWorkday(EmpNonworkday empNonworkday) {
		//查询是否存在此日期的记录
		int row = empNonworkdayMapper.checkWorkdateUnique(empNonworkday.getWorkdate());
		if(row>0){
			return 0;
		}else{
			empNonworkday.setNonworkId(StringUtils.getUUID());
			return empNonworkdayMapper.insertEmpNonworkday(empNonworkday);
		}
	}

	@Override
	public int updateWorkday(EmpNonworkday empNonworkday) {
		//查询是否存在此日期的记录
		int row = empNonworkdayMapper.checkWorkdateUniqueForUpdate(empNonworkday);
		//日期必须唯一
		if(row>0){
			return 0;
		}else{
			return empNonworkdayMapper.updateEmpNonworkday(empNonworkday);
		}
	}

	@Override
	public EmpNonworkday selectWorkdayById(String id) {
		
		return empNonworkdayMapper.selectEmpNonworkdayById(id);
	}

	@Override
	public int deleteWorkdayByIds(String ids) {
		if(StringUtils.isNotEmpty(ids)){
			String[] nonworkIds = Convert.toStrArray(ids);
			return empNonworkdayMapper.deleteEmpNonworkdayByIds(nonworkIds);
		}
		return 0;
	}

	@Override
	public int deleteWorkdayById(String id) {
		return empNonworkdayMapper.deleteEmpNonworkdayById(id);
	}

	public String checkWorkdateUnique(Date workdate) {
		int row = empNonworkdayMapper.checkWorkdateUnique(workdate);
		if(row>0){
			return AttendConstants.WORKDATE_NOT_UNIQUE;
		}
		return AttendConstants.WORKDATE_UNIQUE;
	}

	@Override
	public String checkWorkdateUniqueForUpdate(EmpNonworkday empNonworkday) {
		int row = empNonworkdayMapper.checkWorkdateUniqueForUpdate(empNonworkday);
		if(row>0){
			return AttendConstants.WORKDATE_NOT_UNIQUE;
		}
		return AttendConstants.WORKDATE_UNIQUE;
	}

	@Override
	public Map<String, String> selectWorkdayListForCalendar() {
		List<EmpNonworkday> list = empNonworkdayMapper.selectEmpNonworkdayList(new EmpNonworkday());
		Map<String, String> map = new HashMap<String, String>();
		for (EmpNonworkday item : list) {
			map.put(item.getWorkdateStr(), item.getWorkdateName());
		}
		return map;
	}

	@Override
	public Map<String, Integer> selectDateAndTypeMap() {
		List<EmpNonworkday> list = empNonworkdayMapper.selectEmpNonworkdayList(new EmpNonworkday());
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(list.size()!=0){
			for (EmpNonworkday empNonworkday : list) {
				map.put(empNonworkday.getWorkdateStr(), empNonworkday.getWorkdateType());
			}
		}
		return map;
	}

	@Override
	public List<Map<String, String>> selectCalendarJson(SysUser sysUser) {
		return empNonworkdayMapper.selectCalendarJson(sysUser.getUserId());
	}

}
