package com.numberone.system.service;

import java.util.List;

import com.numberone.system.domain.SysFileBean;

/**
 * 文件 服务层
 * 
 * @author eason
 * @date 2020-04-21
 */
public interface ISysFileService 
{
	/**
     * 查询文件信息
     * 
     * @param fileId 文件ID
     * @return 文件信息
     */
	public SysFileBean selectSysFileById(String fileId);
	
	/**
     * 查询文件列表
     * 
     * @param sysFile 文件信息
     * @return 文件集合
     */
	public List<SysFileBean> selectSysFileList(SysFileBean sysFile);
	
	/**
     * 新增文件
     * 
     * @param sysFile 文件信息
     * @return 结果
     */
	public int insertSysFile(SysFileBean sysFile);
	
	/**
     * 修改文件
     * 
     * @param sysFile 文件信息
     * @return 结果
     */
	public int updateSysFile(SysFileBean sysFile);
		
	/**
     * 删除文件信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSysFileByIds(String ids);
	
}
