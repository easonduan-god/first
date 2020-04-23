package com.numberone.emp.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.numberone.emp.domain.EmpOvertimeBill;	

/**
 * 延时工作单 数据层
 * 
 * @author eason
 * @date 2020-03-30
 */
public interface EmpOvertimeBillMapper 
{
	/**
     * 查询延时工作单信息
     * 
     * @param overtimeBillId 延时工作单ID
     * @return 延时工作单信息
     */
	public EmpOvertimeBill selectEmpOvertimeBillById(String overtimeBillId);
	
	/**
     * 查询延时工作单列表
     * 
     * @param empOvertimeBill 延时工作单信息
     * @return 延时工作单集合
     */
	public List<EmpOvertimeBill> selectEmpOvertimeBillList(EmpOvertimeBill empOvertimeBill);
	
	/**
     * 新增延时工作单
     * 
     * @param empOvertimeBill 延时工作单信息
     * @return 结果
     */
	public Long insertEmpOvertimeBill(EmpOvertimeBill empOvertimeBill);
	
	/**
     * 修改延时工作单
     * 
     * @param empOvertimeBill 延时工作单信息
     * @return 结果
     */
	public int updateEmpOvertimeBill(EmpOvertimeBill empOvertimeBill);
	
	/**
     * 删除延时工作单
     * 
     * @param overtimeBillId 延时工作单ID
     * @return 结果
     */
	public int deleteEmpOvertimeBillById(String overtimeBillId);
	
	/**
     * 批量删除延时工作单
     * 
     * @param overtimeBillIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteEmpOvertimeBillByIds(String[] overtimeBillIds);

	/**
	 * 根据id数组和startDate查询延时工作单数量
	 * @param: @param idArr
	 * @param: @param userId
	 * @return: void
	 */
	public Integer selectOvertimeBillCountByUserIdsAndStartDate(@Param("userIds") String[] userIds,@Param("startDate") Date startDate);
	/**
	 * 根据userid和开始时间查询延时工单数
	 * @param: @param userId
	 * @param: @param startDate
	 * @param: @return
	 * @return: Integer
	 */
	public Integer selectOvertimeBillCountByUserIdAndStartDate(@Param("userId") String userId,@Param("startDate") Date startDate);

	/**
	 * 查询usernames，依据userid，延时工单开始时间查询延时工单，已得到相关username
	 * @param: @param id
	 * @param: @param startDate
	 * @param: @return
	 * @return: List<String>
	 */
	public List<String> selectUsernamesByUserIdsAndStartDate(@Param("userIds") String[] userIds,@Param("startDate")  Date startDate);

	/**
	 * 关联中间表 overtime_user
	 * @param: @param userIds
	 * @param: @param overtimeBillId
	 * @param: @return
	 * @return: int
	 */
	public int relateMiddleTable(@Param("userIds") String[] userIds,@Param("overtimeBillId") String overtimeBillId);

	/**
	 * 查询我的延时工单 包括我发起，或者我参与
	 * @param: @param empOvertimeBill
	 * @param: @return
	 * @return: List<EmpOvertimeBill>
	 */
	public List<EmpOvertimeBill> selectOvertimeBillOfMine(EmpOvertimeBill empOvertimeBill);

	/**
	 * 我的延时工单详情 只能查看自己发起或参与延时工单/交由自己审核的延时工单详情 	管理员除外
	 * @param: @param overtimeBillId
	 * @param: @param userId
	 * @param: @return
	 * @return: EmpOvertimeBill
	 */
	EmpOvertimeBill selectByIdAndRelationMine(@Param("overtimeBillId") String overtimeBillId,@Param("userId") Long userId);

	/**
	 * 查询审核是否结束 完成状态是否为已完成
	 * @param: @param overtimeBillId
	 * @param: @return
	 * @return: int
	 */
	public int selectOvertimeAuditIsEnd(String overtimeBillId);
	
}