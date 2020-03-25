package com.numberone.emp.service;

import com.numberone.emp.domain.EmpAttendAudit;
import java.util.List;

/**
 * 上级领导审核考勤单 服务层
 * 
 * @author eason
 * @date 2020-02-20
 */
public interface IEmpAttendAuditService 
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
     * 删除上级领导审核考勤单信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEmpAttendAuditByIds(String ids);
	
}
