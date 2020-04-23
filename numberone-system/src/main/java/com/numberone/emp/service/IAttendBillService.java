package com.numberone.emp.service;

import java.util.List;
import java.util.Map;

import com.numberone.common.base.AjaxResult;
import com.numberone.emp.domain.EmpAttendAudit;
import com.numberone.emp.domain.EmpAttendBill;
import com.numberone.emp.domain.EmpAttendBillLeavedayItems;
import com.numberone.system.domain.SysUser;

/**
 * 考勤单管理业务层接口
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年2月17日 下午1:18:11
 * @param:
 */
public interface IAttendBillService {
	
	/**
	 * 查询 上月累计请假 本月累计请假 年假总天数 年假剩余天数
	 * @param: @param empId
	 * @param: @return
	 * @return: Map<String,Double>
	 */
	Map<String, Double> selectLeaveAndYearVaction(String empId);
	
	/**
	 * 将工时工作日数据存入临时表
	 * @param: @param empAttendBill
	 * @param: @return
	 * @return: Long
	 */
	Long insertAttendBillTemp(EmpAttendBill empAttendBill);
	
	/**
	 * 新增休假日项临时表
	 * @param: @param value
	 * @param: @return
	 * @return: Long
	 */
	Long insertLeavedayItemsTemp(EmpAttendBillLeavedayItems value);
	
	/**
	 * 删除两张临时表，根据临时表id
	 * @param: @param attendBillTempId
	 * @param: @return
	 * @return: Long
	 */
	Long deleteAttendBillTempAndLeavedayTemp(String attendBillTempId);
	
	/**
	 * 根据临时表id查询考勤dan临时表
	 * @param: @param attendBillTempId
	 * @param: @return
	 * @return: EmpAttendBill
	 */
	EmpAttendBill selectTempById(String attendBillTempId);
	
	/**
	 * 
	 * @param: @param empAttendBill
	 * @param: @return
	 * @return: Long
	 */
	Long insertAttendBill(EmpAttendBill empAttendBill);
	
	/**
	 * 根据考勤单临时表id查询休假日项临时表 并直接插入到休假日项，同时关联上考勤单id
	 * @param: @param map
	 * @param: @return
	 * @return: Long
	 */
	Long insertBatchLeaveItem(Map<String, String> map);
	
	/**
	 * 根据用户id，开始日期，结束日期,查询考勤冲突日期
	 * @param: @param empAttendBill
	 * @param: @return
	 * @return: Long
	 */
	Long selectDateConflict(EmpAttendBill empAttendBill);
	
	/**
	 * 发起审批
	 * @param: @param empAttendBill
	 * @param: @param sysUser
	 * @param: @return
	 * @return: int
	 */
	int launchAudit(EmpAttendBill empAttendBill, SysUser sysUser);
	
	/**
	 * 计算工时
	 * @param: @param empAttendBill
	 * @param: @return
	 * @return: AjaxResult
	 */
	AjaxResult calcWorkdays(EmpAttendBill empAttendBill);
	
	/**
	 * 查询考勤审核单列表
	 * @param: @param empAttendAudit
	 * @param: @return
	 * @return: List<EmpAttendAudit>
	 */
	List<EmpAttendAudit> selectAuditList(EmpAttendAudit empAttendAudit);
	
	/**
	 * 查询分配给自己的考勤审核
	 * @param: @param empAttendAudit
	 * @param: @return
	 * @return: List<EmpAttendAudit>
	 */
	List<EmpAttendAudit> selectAuditListByAuditId(EmpAttendAudit empAttendAudit);
	
	/**
	 * 根据考勤审核单id查看考勤单详细信息
	 * @param: @param attendAuditId
	 * @param: @return
	 * @return: EmpAttendBill
	 */
	EmpAttendBill selectAttendBillByAuditId(String attendAuditId);
	/**
	 * 批量审批不通过
	 * @param auditIds 
	 * @param sysUser 
	 * @param: @return
	 * @return: int
	 */
	int batchAuditRefuse(String auditIds, SysUser sysUser,String remark);
	
	/**
	 * 批量审核通过
	 * @param: @return
	 * @return: int
	 */
	int batchAuditPass(String auditIds, SysUser sysUser,String remark);

	/**
	 * 查询自己的考勤单
	 * @param: @param empAttendBill
	 * @param: @return
	 * @return: List<?>
	*/
	List<EmpAttendBill> selectAttendBillOfMine(EmpAttendBill empAttendBill);

	/**
	 * 根据考勤单id查询详情
	 * @param: @param attendBillId
	 * @param: @return
	 * @return: EmpAttendBill
	*/
	EmpAttendBill selectAttendBillById(String attendBillId);
	
	/**
	 * 
	 * 根据考勤单id 查询考勤单审核流程信息 也就是相关考勤审核信息
	 * @param: @param attendBillId
	 * @param: @return
	 * @return: List<Map<String,Object>> 考勤单信息 考勤审核信息
	 */
	List<Map<String,Object>> selectAttendBillTrace(String attendBillId);

	/**
	 * 删除考勤单 根据id
	 * @param: @param attendBillId
	 * @param: @param sysUser
	 * @param: @return
	 * @return: int
	 */
	int removeMine(String attendBillId, SysUser sysUser);

	/**
	 * 查看与自己相关的考勤单详情 根据考勤单id
	 * @param: @param attendBillId
	 * @param: @param sysUser
	 * @param: @return
	 * @return: EmpAttendBill
	 */
	EmpAttendBill selectAttendBillByIdAndRelationMine(String attendBillId, SysUser sysUser);

	/**
	 * 查看考勤审核单详情 只能查看与自己相关的 管理员除外
	 * @param: @param attendAuditId
	 * @param: @param sysUser
	 * @param: @return
	 * @return: Object
	 */
	EmpAttendAudit selectAttendAuditByAuditIdAndRelationMine(String attendAuditId, SysUser sysUser);

	
}
