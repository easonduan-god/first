package com.numberone.emp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.common.support.Convert;
import com.numberone.emp.domain.EmpAttendAudit;
import com.numberone.emp.mapper.EmpAttendAuditMapper;
import com.numberone.emp.service.IEmpAttendAuditService;

/**
 * 上级领导审核考勤单 服务层实现
 * 
 * @author eason
 * @date 2020-02-20
 */
@Service
public class EmpAttendAuditServiceImpl implements IEmpAttendAuditService 
{
	@Autowired
	private EmpAttendAuditMapper empAttendAuditMapper;

	/**
     * 查询上级领导审核考勤单信息
     * 
     * @param attendAuditId 上级领导审核考勤单ID
     * @return 上级领导审核考勤单信息
     */
    @Override
	public EmpAttendAudit selectEmpAttendAuditById(String attendAuditId)
	{
	    return empAttendAuditMapper.selectEmpAttendAuditById(attendAuditId);
	}
	
	/**
     * 查询上级领导审核考勤单列表
     * 
     * @param empAttendAudit 上级领导审核考勤单信息
     * @return 上级领导审核考勤单集合
     */
	@Override
	public List<EmpAttendAudit> selectEmpAttendAuditList(EmpAttendAudit empAttendAudit)
	{
	    return empAttendAuditMapper.selectEmpAttendAuditList(empAttendAudit);
	}
	
    /**
     * 新增上级领导审核考勤单
     * 
     * @param empAttendAudit 上级领导审核考勤单信息
     * @return 结果
     */
	@Override
	public int insertEmpAttendAudit(EmpAttendAudit empAttendAudit)
	{
	    return empAttendAuditMapper.insertEmpAttendAudit(empAttendAudit);
	}
	
	/**
     * 修改上级领导审核考勤单
     * 
     * @param empAttendAudit 上级领导审核考勤单信息
     * @return 结果
     */
	@Override
	public int updateEmpAttendAudit(EmpAttendAudit empAttendAudit)
	{
	    return empAttendAuditMapper.updateEmpAttendAudit(empAttendAudit);
	}

	/**
     * 删除上级领导审核考勤单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteEmpAttendAuditByIds(String ids)
	{
		return empAttendAuditMapper.deleteEmpAttendAuditByIds(Convert.toStrArray(ids));
	}
	
}
