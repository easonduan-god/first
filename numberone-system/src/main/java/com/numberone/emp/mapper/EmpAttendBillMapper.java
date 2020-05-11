package com.numberone.emp.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.numberone.emp.domain.EmpAttendBill;
import com.numberone.emp.domain.EmpAttendBillExample;

/**
 * 考勤单管理 数据层
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年1月26日 下午4:20:24
 * @param:
 */
public interface EmpAttendBillMapper {
    int countByExample(EmpAttendBillExample example);

    int deleteByExample(EmpAttendBillExample example);

    int deleteByPrimaryKey(String attendBillId);

    int insert(EmpAttendBill record);

    int insertSelective(EmpAttendBill record);

    List<EmpAttendBill> selectByExample(EmpAttendBillExample example);

    EmpAttendBill selectByPrimaryKey(String attendBillId);

    int updateByExampleSelective(@Param("record") EmpAttendBill record, @Param("example") EmpAttendBillExample example);

    int updateByExample(@Param("record") EmpAttendBill record, @Param("example") EmpAttendBillExample example);

    int updateByPrimaryKeySelective(EmpAttendBill record);

    int updateByPrimaryKey(EmpAttendBill record);

    Map<String,Long> selectLastMonth(String empId);
    
    /**
     * 新增临时表
     * @param: @param empAttendBill
     * @param: @return 参数说明
     * @return: Long 返回类型
     * @throws
     */
	Long insertAttendBillTemp(EmpAttendBill empAttendBill);
	
	/**
	 * 删除临时表，根据id
	 * @param: @param attendBillTempId
	 * @param: @return 参数说明
	 * @return: Long 返回类型
	 * @throws
	 */
	Long deleteTempById(String attendBillTempId);
	
	/**
	 * 查询临时表根据id
	 * @param: @param attendBillTempId
	 * @param: @return 参数说明
	 * @return: EmpAttendBill 返回类型
	 * @throws
	 */
	EmpAttendBill selectTempById(String attendBillTempId);
	/**
	 * 根据考勤单临时表id查询休假日项临时表 并直接插入到休假日项，同时关联上考勤单id
	 * @param: @param map
	 * @param: @return 参数说明
	 * @return: Long 返回类型
	 * @throws
	 */
	Long insertBatchLeaveItem(Map<String, String> map);

	/**
	 * 根据考勤审核id查询
	 * @param: @param attendBillId
	 * @param: @return
	 * @return: EmpAttendBill
	*/
	EmpAttendBill selectAttendBillByAuditId(String attendAuditId);

	/**
	 * 查询自己的考勤单
	 * @param: @param empAttendBill
	 * @param: @return
	 * @return: List<EmpAttendBill>
	*/
	List<EmpAttendBill> selectAttendBillOfMine(EmpAttendBill empAttendBill);
	
	/**
	 * @param userId 
	 * 查询考勤单根据考勤单id
	 * @param: @param attendBillId
	 * @param: @return      
	 * @return: EmpAttendBill      
	 * @throws
	 */
	EmpAttendBill selectAttendBillById(String attendBillId);
	/**
	 * 查看与自己相关的考勤单详情
	 * @param: @param attendBillId
	 * @param: @param userId
	 * @param: @return
	 * @return: EmpAttendBill
	 */
	EmpAttendBill selectAttendBillByIdAndRelationMine(@Param("attendBillId") String attendBillId,@Param("userId") Long userId);

	/**
	 * 查询审核是否结束 完成状态是否为已完成
	 * @param: @param attendBillId
	 * @param: @return
	 * @return: int
	 */
	int selectAttendAuditIsEnd(String attendBillId);

	/**
	 * 查询usernames，依据userid数组，开始时间
	 * @param: @param idArr
	 * @param: @return
	 * @return: List<String>
	 */
	List<String> selectUsernamesByUserIdsAndStartDate(@Param("userIds") String[] userIds,@Param("startDate") Date startDate);

	/**
	 * 一个月内忘记打卡不准超过3次 包括未审核的
	 * @param userId
	 * @param startDate
	 * @return
	 */
	int selectForgetCountByUserIdAndMonth(@Param("userId") Long userId,@Param("month") Date month);


	
	
}