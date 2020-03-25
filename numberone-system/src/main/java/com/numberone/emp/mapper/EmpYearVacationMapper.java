package com.numberone.emp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.numberone.emp.domain.EmpYearVacation;
import com.numberone.emp.domain.EmpYearVacationExample;

public interface EmpYearVacationMapper {
    int countByExample(EmpYearVacationExample example);

    int deleteByExample(EmpYearVacationExample example);

    int deleteByPrimaryKey(String yearVacationId);

    int insert(EmpYearVacation record);

    int insertSelective(EmpYearVacation record);

    List<EmpYearVacation> selectByExample(EmpYearVacationExample example);

    EmpYearVacation selectByPrimaryKey(String yearVacationId);

    int updateByExampleSelective(@Param("record") EmpYearVacation record, @Param("example") EmpYearVacationExample example);

    int updateByExample(@Param("record") EmpYearVacation record, @Param("example") EmpYearVacationExample example);

    int updateByPrimaryKeySelective(EmpYearVacation record);

    int updateByPrimaryKey(EmpYearVacation record);

	List<EmpYearVacation> selectYearVacationList(EmpYearVacation empYearVacation);
	/**
	 * 提交查询 其中用户的所有当前用户可操作的用户
	 * @param: @param empAttendAudit
	 * @param: @return 参数说明
	 * @return List<EmpAttendAudit> 返回类型
	 */
	List<EmpYearVacation> selectListByOperableUserIds(@Param("empYearVacation")EmpYearVacation empYearVacation, @Param("userIds")List<Long> operableUserIds);
}