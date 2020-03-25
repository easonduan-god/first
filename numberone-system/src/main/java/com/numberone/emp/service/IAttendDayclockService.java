package com.numberone.emp.service;

import java.util.List;

import com.numberone.emp.domain.EmpAttendday;
import com.numberone.emp.domain.EmpAttendinfo;

/**
 * 考勤日常打卡管理事务接口
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年1月26日 下午4:05:11
 * @param:
 */
public interface IAttendDayclockService {
	
	/**
	 * 保存单次打卡记录
	 * @param: @return 参数说明
	 * @return: int 返回类型
	 * @throws
	 */
	public int insertDayClock(EmpAttendinfo empAttendinfo);
	
	/**
	 * 查询日常考勤信息
	 * @param: @param empAttendiday
	 * @param: @return 参数说明
	 * @return: List<EmpAttendday> 返回类型
	 * @throws
	 */
	public List<EmpAttendday> selectAttendDayList(EmpAttendday empAttendayday);
	
	
	/**
	 * 查询当前用户的打卡记录
	 * @param: @param userId
	 * @param: @return 参数说明
	 * @return: List<EmpAttendinfo> 返回类型
	 * @throws
	 */
	public List<EmpAttendinfo> selectAttendInfoList(Long userId);
}
