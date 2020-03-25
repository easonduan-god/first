package com.numberone.emp.mapper;

import com.numberone.emp.domain.EmpNonworkday;
import com.numberone.emp.domain.EmpNonworkdayExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface EmpNonworkdayMapper {
    int countByExample(EmpNonworkdayExample example);

    int deleteByExample(EmpNonworkdayExample example);

    int deleteByPrimaryKey(String nonworkId);

    int insert(EmpNonworkday record);

    int insertSelective(EmpNonworkday record);

    List<EmpNonworkday> selectByExample(EmpNonworkdayExample example);

    EmpNonworkday selectByPrimaryKey(String nonworkId);

    int updateByExampleSelective(@Param("record") EmpNonworkday record, @Param("example") EmpNonworkdayExample example);

    int updateByExample(@Param("record") EmpNonworkday record, @Param("example") EmpNonworkdayExample example);

    int updateByPrimaryKeySelective(EmpNonworkday record);

    int updateByPrimaryKey(EmpNonworkday record);

    List<EmpNonworkday> selectWorkdayList(EmpNonworkday empNonworkday);
    
    int deleteWorkdayByIds(String[] nonworkIds);
    
    int deleteWorkdayById(String id);

	int checkWorkdateUnique(Date workdate);

	int checkWorkdateUniqueForUpdate(EmpNonworkday empNonworkday);

	Map<String, Integer> selectDateAndTypeMap();
}