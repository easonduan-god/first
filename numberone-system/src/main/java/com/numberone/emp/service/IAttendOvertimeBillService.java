/**
 * 
 */
package com.numberone.emp.service;

import java.util.List;
import java.util.Map;

import com.numberone.emp.domain.EmpOvertimeAudit;
import com.numberone.emp.domain.EmpOvertimeBill;
import com.numberone.system.domain.SysUser;

/**
 * 延时工作单service层接口
 * <p>Title:IAttendOvertimeBillService</p>
 * @author 段佳佳
 * @date 2020年4月4日
 */
public interface IAttendOvertimeBillService {

	/**
	 * 发起审核延时工作单
	 * @param: @param overtimeBill
	 * @param: @param sysUser
	 * @param: @return
	 * @return: int
	 */
	int launchAudit(EmpOvertimeBill overtimeBill, SysUser sysUser);

	/**
	 * 查询我的延时工单 包括我发起，或者我参与
	 * @param: @param empOvertimeBill
	 * @param: @return
	 * @return: List<?>
	 */
	List<EmpOvertimeBill> selectOvertimeBillOfMine(EmpOvertimeBill empOvertimeBill);

	/**
	 * 我的延时工单详情 只能查看自己发起或参与延时工单/交由自己审核的延时工单详情 	管理员除外
	 * @param: @param overtimeBillId
	 * @param: @param sysUser
	 * @param: @return
	 * @return: Object
	 */
	EmpOvertimeBill selectByIdAndRelationMine(String overtimeBillId, SysUser sysUser);

	/**
	 * 查询分配给自己的延时待办
	 * @param: @param empOvertimeAudit
	 * @param: @return
	 * @return: List<?>
	 */
	List<EmpOvertimeAudit> selectAuditListByAuditId(EmpOvertimeAudit empOvertimeAudit);

	/**
	 * 批量审核不通过
	 * @param: @param overtimeAuditIds
	 * @param: @param sysUser
	 * @param: @param remark
	 * @param: @return
	 * @return: int
	 */
	int batchAuditRefuse(String overtimeAuditIds, SysUser sysUser, String remark);

	/**
	 * 批量审核通过
	 * @param: @param overtimeAuditIds
	 * @param: @param sysUser
	 * @param: @param remark
	 * @param: @return
	 * @return: int
	 */
	int batchAuditPass(String overtimeAuditIds, SysUser sysUser, String remark);


	/**
	 * 追踪延时工单审核
	 * @param: @param overtimeBillId
	 * @param: @return
	 * @return: List<Map<String,Object>>延时工单信息 延时工单审核信息
	 */
	List<Map<String,Object>> selectOvertimeBillTrace(String overtimeBillId);

	/**
	 * 查看延时工单审核单详情 只能查看与自己相关的 管理员除外
	 * @param: @param overtimeAuditId
	 * @param: @param sysUser
	 * @param: @return
	 * @return: EmpOvertimeAudit
	 */
	EmpOvertimeAudit selectOvertimeAuditByAuditIdAndRelationMine(String overtimeAuditId, SysUser sysUser);

}
