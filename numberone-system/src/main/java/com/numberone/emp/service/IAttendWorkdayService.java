package com.numberone.emp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.numberone.emp.domain.EmpNonworkday;

public interface IAttendWorkdayService {
	
	/**
	 * 查询工作日列表
	 * @param: @param empNonworkday
	 * @param: @return 参数说明
	 * @return: List<EmpNonworkday> 返回类型
	 * @throws
	 */
	public List<EmpNonworkday> selectWorkdayList(EmpNonworkday empNonworkday);
	
	/**
	 * 新增工作日
	 * @param: @param empNonworkday
	 * @param: @return 参数说明
	 * @return: int 返回类型
	 * @throws
	 */
	public int insertWorkday(EmpNonworkday empNonworkday);
	
	/**
	 * 更新工作日
	 * @param: @param empNonworkday
	 * @param: @return 参数说明
	 * @return: int 返回类型
	 * @throws
	 */
	public int updateWorkday(EmpNonworkday empNonworkday);
	
	/**
	 * 根据id查询
	 * @param: @param id
	 * @param: @return 参数说明
	 * @return: Object 返回类型
	 * @throws
	 */
	public EmpNonworkday selectWorkdayById(String id);
	
	/**
	 * 批量删除
	 * @param: @param ids
	 * @param: @return 参数说明
	 * @return: int 返回类型
	 * @throws
	 */
	public int deleteWorkdayByIds(String ids);
	/**
	 * 删除单个
	 * @param: @param ids
	 * @param: @return 参数说明
	 * @return: int 返回类型
	 * @throws
	 */
	public int deleteWorkdayById(String id);
	
	/**
	 * 检验日程唯一
	 * @param: @param workdate
	 * @param: @return 参数说明
	 * @throws
	 */
	public String checkWorkdateUnique(Date workdate);
	/**
	 * 更新时检验日期
	 * @param: @param workdate
	 * @param: @param id
	 * @param: @return 参数说明
	 * @return: String 返回类型
	 * @throws
	 */
	public String checkWorkdateUniqueForUpdate(EmpNonworkday empNonworkday);

	public Map<String, String> selectWorkdayListForCalendar();

	public Map<String, Integer> selectDateAndTypeMap();
}
