package com.numberone.emp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.numberone.emp.domain.EmpAttendinfo;
import com.numberone.emp.domain.EmpAttendinfoExample;
/**
 * 考勤日常打卡 数据层
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年1月26日 下午4:18:35
 * @param:
 */
public interface EmpAttendinfoMapper {
    int countByExample(EmpAttendinfoExample example);

    int deleteByExample(EmpAttendinfoExample example);

    int deleteByPrimaryKey(String attendinfoId);

    int insert(EmpAttendinfo record);

    int insertSelective(EmpAttendinfo record);

    List<EmpAttendinfo> selectByExample(EmpAttendinfoExample example);

    EmpAttendinfo selectByPrimaryKey(String attendinfoId);

    int updateByExampleSelective(@Param("record") EmpAttendinfo record, @Param("example") EmpAttendinfoExample example);

    int updateByExample(@Param("record") EmpAttendinfo record, @Param("example") EmpAttendinfoExample example);

    int updateByPrimaryKeySelective(EmpAttendinfo record);

    int updateByPrimaryKey(EmpAttendinfo record);

	List<EmpAttendinfo> selectAttendinfoList(Long userId);
}