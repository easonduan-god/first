package com.numberone.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.common.support.Convert;
import com.numberone.system.domain.SysFileBean;
import com.numberone.system.mapper.SysFileMapper;
import com.numberone.system.service.ISysFileService;

/**
 * 文件 服务层实现
 * 
 * @author eason
 * @date 2020-04-21
 */
@Service
public class SysFileServiceImpl implements ISysFileService 
{
	@Autowired
	private SysFileMapper sysFileMapper;

	/**
     * 查询文件信息
     * 
     * @param fileId 文件ID
     * @return 文件信息
     */
    @Override
	public SysFileBean selectSysFileById(String fileId)
	{
	    return sysFileMapper.selectSysFileById(fileId);
	}
	
	/**
     * 查询文件列表
     * 
     * @param sysFile 文件信息
     * @return 文件集合
     */
	@Override
	public List<SysFileBean> selectSysFileList(SysFileBean sysFile)
	{
	    return sysFileMapper.selectSysFileList(sysFile);
	}
	
    /**
     * 新增文件
     * 
     * @param sysFile 文件信息
     * @return 结果
     */
	@Override
	public int insertSysFile(SysFileBean sysFile)
	{
	    return sysFileMapper.insertSysFile(sysFile);
	}
	
	/**
     * 修改文件
     * 
     * @param sysFile 文件信息
     * @return 结果
     */
	@Override
	public int updateSysFile(SysFileBean sysFile)
	{
	    return sysFileMapper.updateSysFile(sysFile);
	}

	/**
     * 删除文件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSysFileByIds(String ids)
	{
		return sysFileMapper.deleteSysFileByIds(Convert.toStrArray(ids));
	}
	
}
