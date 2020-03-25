package com.numberone.emp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.numberone.emp.domain.EmpAttendday;
import com.numberone.emp.domain.EmpAttenddayExample;

public interface EmpAttenddayMapper {
    int countByExample(EmpAttenddayExample example);

    int deleteByExample(EmpAttenddayExample example);

    int deleteByPrimaryKey(String attenddayId);

    int insert(EmpAttendday record);

    int insertSelective(EmpAttendday record);

    List<EmpAttendday> selectByExample(EmpAttenddayExample example);

    EmpAttendday selectByPrimaryKey(String attenddayId);

    int updateByExampleSelective(@Param("record") EmpAttendday record, @Param("example") EmpAttenddayExample example);

    int updateByExample(@Param("record") EmpAttendday record, @Param("example") EmpAttenddayExample example);

    int updateByPrimaryKeySelective(EmpAttendday record);

    int updateByPrimaryKey(EmpAttendday record);

	List<EmpAttendday> selectAttendDayList(EmpAttendday empAttendayday);
}