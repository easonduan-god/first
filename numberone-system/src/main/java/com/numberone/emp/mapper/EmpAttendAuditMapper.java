package com.numberone.emp.mapper;

import java.util.List;

import com.numberone.emp.domain.EmpAttendAudit;
import com.numberone.emp.domain.EmpAttendBill;	

/**
 * 
 * 上级领导审核考勤单 数据层
 * 
 * @author eason
 * @date 2020-02-20
 */
public interface EmpAttendAuditMapper 
{
	/**
     * 查询上级领导审核考勤单信息
     * 
     * @param attendAuditId 上级领导审核考勤单ID
     * @return 上级领导审核考勤单信息
     */
	public EmpAttendAudit selectEmpAttendAuditById(String attendAuditId);
	
	/**
     * 查询上级领导审核考勤单列表
     * 
     * @param empAttendAudit 上级领导审核考勤单信息
     * @return 上级领导审核考勤单集合
     */
	public List<EmpAttendAudit> selectEmpAttendAuditList(EmpAttendAudit empAttendAudit);
	
	/**
     * 新增上级领导审核考勤单
     * 
     * @param empAttendAudit 上级领导审核考勤单信息
     * @return 结果
     */
	public int insertEmpAttendAudit(EmpAttendAudit empAttendAudit);
	
	/**
     * 修改上级领导审核考勤单
     * 
     * @param empAttendAudit 上级领导审核考勤单信息
     * @return 结果
     */
	public int updateEmpAttendAudit(EmpAttendAudit empAttendAudit);
	
	/**
     * 删除上级领导审核考勤单
     * 
     * @param attendAuditId 上级领导审核考勤单ID
     * @return 结果
     */
	public int deleteEmpAttendAuditById(String attendAuditId);
	
	/**
     * 批量删除上级领导审核考勤单
     * 
     * @param attendAuditIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteEmpAttendAuditByIds(String[] attendAuditIds);
	/**
	 * 查询审核列表
	* @param: @param empAttendAudit
	* @param: @return 参数说明
	* @return List<EmpAttendAudit> 返回类型
	 */
	public List<EmpAttendAudit> selectAuditList(EmpAttendAudit empAttendAudit);
	
	/**
	 * 查询分配给自己的考勤审核单
	 * @param: @param empAttendAudit
	 * @param: @return 参数说明
	 * @return List<EmpAttendAudit> 返回类型
	 */
	public List<EmpAttendAudit> selectAuditListByAuditId(EmpAttendAudit empAttendAudit);
	/**
	 * 根据考勤审核单id查看考勤单详细信息
	 * @param: @param empAttendAudit
	 * @param: @return 参数说明
	 * @return List<EmpAttendAudit> 返回类型
	 */
	public EmpAttendBill selectAttendBillByAuditId(String attendAuditId);
	
	
	
}