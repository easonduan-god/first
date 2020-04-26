package com.numberone.emp.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.numberone.emp.domain.EmpNonworkday;

/**
 * 工作日管理 数据层
 * 
 * @author eason
 * @date 2020-04-24
 */
public interface EmpNonworkdayMapper {
	/**
     * 查询工作日管理信息
     * 
     * @param nonworkId 工作日管理ID
     * @return 工作日管理信息
     */
	public EmpNonworkday selectEmpNonworkdayById(String nonworkId);
	
	/**
     * 查询工作日管理列表
     * 
     * @param empNonworkday 工作日管理信息
     * @return 工作日管理集合
     */
	public List<EmpNonworkday> selectEmpNonworkdayList(EmpNonworkday empNonworkday);
	
	/**
     * 新增工作日管理
     * 
     * @param empNonworkday 工作日管理信息
     * @return 结果
     */
	public int insertEmpNonworkday(EmpNonworkday empNonworkday);
	
	/**
     * 修改工作日管理
     * 
     * @param empNonworkday 工作日管理信息
     * @return 结果
     */
	public int updateEmpNonworkday(EmpNonworkday empNonworkday);
	
	/**
     * 删除工作日管理
     * 
     * @param nonworkId 工作日管理ID
     * @return 结果
     */
	public int deleteEmpNonworkdayById(String nonworkId);
	
	/**
     * 批量删除工作日管理
     * 
     * @param nonworkIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteEmpNonworkdayByIds(String[] nonworkIds);

	int checkWorkdateUnique(Date workdate);

	int checkWorkdateUniqueForUpdate(EmpNonworkday empNonworkday);

	Map<String, Integer> selectDateAndTypeMap();

	/**
	 * 根据日期查询工作日
	 * @param: @param startDate
	 * @param: @return
	 * @return: EmpNonworkday
	 */
	EmpNonworkday selectWorkdayByDate(@Param("startDate") Date startDate);
}