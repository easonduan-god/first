package com.numberone.emp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.numberone.emp.domain.EmpOvertimeAudit;	

/**
 * 延时工作单审核 数据层
 * 
 * @author eason
 * @date 2020-03-30
 */
public interface EmpOvertimeAuditMapper 
{
	/**
     * 查询延时工作单审核信息
     * 
     * @param overtimeAuditId 延时工作单审核ID
     * @return 延时工作单审核信息
     */
	public EmpOvertimeAudit selectEmpOvertimeAuditById(String overtimeAuditId);
	
	/**
     * 查询延时工作单审核列表
     * 
     * @param empOvertimeAudit 延时工作单审核信息
     * @return 延时工作单审核集合
     */
	public List<EmpOvertimeAudit> selectEmpOvertimeAuditList(EmpOvertimeAudit empOvertimeAudit);
	
	/**
     * 新增延时工作单审核
     * 
     * @param empOvertimeAudit 延时工作单审核信息
     * @return 结果
     */
	public int insertEmpOvertimeAudit(EmpOvertimeAudit empOvertimeAudit);
	
	/**
     * 修改延时工作单审核
     * 
     * @param empOvertimeAudit 延时工作单审核信息
     * @return 结果
     */
	public int updateEmpOvertimeAudit(EmpOvertimeAudit empOvertimeAudit);
	
	/**
     * 删除延时工作单审核
     * 
     * @param overtimeAuditId 延时工作单审核ID
     * @return 结果
     */
	public int deleteEmpOvertimeAuditById(String overtimeAuditId);
	
	/**
     * 批量删除延时工作单审核
     * 
     * @param overtimeAuditIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteEmpOvertimeAuditByIds(String[] overtimeAuditIds);

	/**
	 * 查询分配给自己的延时待办
	 * @param: @param empOvertimeAudit
	 * @param: @return
	 * @return: List<EmpOvertimeAudit>
	 */
	public List<EmpOvertimeAudit> selectListAllocateMine(EmpOvertimeAudit empOvertimeAudit);

	/**
	 * 根据延时工单id查询延时工单审核列表
	 * @param: @param overtimeBillId
	 * @param: @return
	 * @return: List<EmpAttendAudit>
	 */
	public List<EmpOvertimeAudit> selectOvertimeAuditByOvertimeBillId(String overtimeBillId);

	/**
	 * 查看延时工单审核单详情 只能查看与自己相关的
	 * @param: @param overtimeAuditId
	 * @param: @param userId
	 * @param: @return
	 * @return: EmpOvertimeAudit
	 */
	public EmpOvertimeAudit selectOvertimeAuditByAuditIdAndRelationMine(@Param("overtimeAuditId") String overtimeAuditId,@Param("userId") Long userId);

	
}