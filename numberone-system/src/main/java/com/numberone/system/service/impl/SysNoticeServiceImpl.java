package com.numberone.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.common.support.Convert;
import com.numberone.common.utils.StringUtils;
import com.numberone.system.domain.SysFileBean;
import com.numberone.system.domain.SysNotice;
import com.numberone.system.mapper.SysFileMapper;
import com.numberone.system.mapper.SysNoticeMapper;
import com.numberone.system.service.ISysNoticeService;

/**
 * 公告 服务层实现
 * 
 * @author numberone
 * @date 2018-06-25
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService
{
    @Autowired
    private SysNoticeMapper noticeMapper;
    @Autowired
    private SysFileMapper fileMapper;

    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId)
    {
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * 查询公告列表
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * 新增公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(SysNotice notice)
    {
    	SysFileBean sysFile = notice.getSysFile();
    	if(sysFile!=null && !StringUtils.isEmpty(sysFile.getFilePath())){
    		sysFile.setFileId(StringUtils.getUUID());
    		//新增文件
    		fileMapper.insertSysFile(sysFile);
    		notice.setFileId(sysFile.getFileId());
    	}
        return noticeMapper.insertNotice(notice);
    }

    /**
     * 修改公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(SysNotice notice)
    {
    	SysFileBean sysFile = notice.getSysFile();
    	if(sysFile!=null && !StringUtils.isEmpty(sysFile.getFilePath())){
	    	if(!StringUtils.isEmpty(sysFile.getFileId())){//没有修改附件
	    		//修改文件
	    		fileMapper.updateSysFile(notice.getSysFile());
	    		
	    	}else{
	    		notice.getSysFile().setFileId(StringUtils.getUUID());
	    		//新增文件
	    		fileMapper.insertSysFile(notice.getSysFile());
	    		
	    	}
    	}
    	notice.setFileId(sysFile.getFileId());
        return noticeMapper.updateNotice(notice);
    }

    /**
     * 删除公告对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(String ids)
    {
        return noticeMapper.deleteNoticeByIds(Convert.toStrArray(ids));
    }

	/** 
	 * @Description: 
	 * @param: @return
	 * @throws
	 */
	@Override
	public List<SysNotice> selectMainNotice() {
		return noticeMapper.selectMainNotice();
	}
}
