package com.numberone.framework.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.framework.util.ShiroUtils;
import com.numberone.system.domain.SysDictData;
import com.numberone.system.service.ISysDictDataService;

/**
 * numberone首创 html调用 thymeleaf 实现字典读取
 * 
 * @author numberone
 */
@Service("dict")
public class DictService
{
    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 根据字典类型查询字典数据信息
     * 
     * @param dictType 字典类型
     * @return 参数键值
     */
    public List<SysDictData> getType(String dictType)
    {
        return dictDataService.selectDictDataByType(dictType);
    }
    /**
     * 根据字典类型以及用户id查询字典数据信息
     * 
     * @param dictType 字典类型
     * @return 参数键值
     */
    public List<SysDictData> getTypeByUserId(String dictType)
    {
    	if("admin".equals(ShiroUtils.getLoginName())){
    		return getType(dictType);
    	}
    	String keys = ShiroUtils.getSysUser().getRolesKey();
		if("ceo".equals(keys) || "hr".equals(keys) ){
			return getType(dictType);
		}
    	SysDictData sysDictData = new SysDictData();
    	sysDictData.setDictType(dictType);
    	sysDictData.getParams().put("userId", ShiroUtils.getUserId());
    	return dictDataService.selectDictDataByTypeAndUserId(sysDictData);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String getLabel(String dictType, String dictValue)
    {
        return dictDataService.selectDictLabel(dictType, dictValue);
    }
}
