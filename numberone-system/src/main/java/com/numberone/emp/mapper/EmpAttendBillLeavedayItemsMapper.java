package com.numberone.emp.mapper;

import java.util.List;

import com.numberone.emp.domain.EmpAttendBill;
import com.numberone.emp.domain.EmpAttendBillLeavedayItems;	

/**
 * 新增考勤单休假日项 数据层
 * 
 * @author eason
 * @date 2020-02-17
 */
public interface EmpAttendBillLeavedayItemsMapper 
{
	/**
     * 查询新增考勤单休假日项信息
     * 
     * @param recordId 新增考勤单休假日项ID
     * @return 新增考勤单休假日项信息
     */
	public EmpAttendBillLeavedayItems selectEmpAttendBillLeavedayItemsById(Integer recordId);
	
	/**
     * 查询新增考勤单休假日项列表
     * 
     * @param empAttendBillLeavedayItems 新增考勤单休假日项信息
     * @return 新增考勤单休假日项集合
     */
	public List<EmpAttendBillLeavedayItems> selectEmpAttendBillLeavedayItemsList(EmpAttendBillLeavedayItems empAttendBillLeavedayItems);
	
	/**
     * 新增新增考勤单休假日项
     * 
     * @param empAttendBillLeavedayItems 新增考勤单休假日项信息
     * @return 结果
     */
	public int insertEmpAttendBillLeavedayItems(EmpAttendBillLeavedayItems empAttendBillLeavedayItems);
	
	/**
     * 修改新增考勤单休假日项
     * 
     * @param empAttendBillLeavedayItems 新增考勤单休假日项信息
     * @return 结果
     */
	public int updateEmpAttendBillLeavedayItems(EmpAttendBillLeavedayItems empAttendBillLeavedayItems);
	
	/**
     * 删除新增考勤单休假日项
     * 
     * @param recordId 新增考勤单休假日项ID
     * @return 结果
     */
	public int deleteEmpAttendBillLeavedayItemsById(Integer recordId);
	
	/**
     * 批量删除新增考勤单休假日项
     * 
     * @param recordIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteEmpAttendBillLeavedayItemsByIds(String[] recordIds);
	/**
	 * 查询上月累计请假
	 * @param: @param empId
	 * @param: @return
	 * @return: Double
	 */
	public Double selectLastMonthLeave(String empId);
	/**
	 * 本月累计请假
	 * @param: @param empId
	 * @param: @return
	 * @return: Double
	 */
	public Double selectCurrMonthLeave(String empId);
	
	/**
	 * 考勤日项临时表
	 * @param: @param leavedayItemsTemp
	 * @param: @return
	 * @return: Long
	 */
	public Long insertLeavedayItemsTemp(EmpAttendBillLeavedayItems leavedayItemsTemp);
	
	/**
	 * 根据考勤单临时表id删除
	 * @param: @param attendBillTempId
	 * @param: @return
	 * @return: Long
	 */
	public Long deleteEmpAttendBillLeavedayItemsByAttendBillTempId(String attendBillTempId);
	
	/**
	 * 根据用户id，开始日期，结束日期,查询考勤冲突日期
	 * @param: @param empAttendBill
	 * @param: @return
	 * @return: Long
	 */
	public Long selectDateConflict(EmpAttendBill empAttendBill);

	/**
	 * 根据考勤单表id删除
	 * @param: @param attendBillId
	 * @return: void
	 */
	public Long deleteEmpAttendBillLeavedayItemsByAttendBillId(String attendBillId);

	
}