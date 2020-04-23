package com.numberone.emp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	
	
	/**
	 * 查询考勤审核 根据考勤单id
	 * @param: @param attendBillId
	 * @param: @return      
	 * @return: List<EmpAttendAudit>      
	 * @throws
	 */
	List<EmpAttendAudit> selectAttendAuditByAttendBillId(String attendBillId);

	/**
	 * 删除考勤审核 根据用户id 考勤单id
	 * @param: @param attendBillId
	 * @param: @param userId
	 * @param: @return
	 * @return: int
	 */
	public int deleteEmpAttendAuditByAttendBillId(@Param("attendBillId") String attendBillId,@Param("userId") Long userId);

	/**
	 * 查看考勤审核单详情 只能查看与自己相关的 管理员除外
	 * @param userId 
	 * @param: @param attendAuditId
	 * @param: @return
	 * @return: EmpAttendAudit
	 */
	public EmpAttendAudit selectAttendAuditByAuditIdAndRelationMine(@Param("attendAuditId") String attendAuditId,@Param("userId")  Long userId);
}