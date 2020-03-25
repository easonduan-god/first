package com.numberone.web.controller.attend;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.DateUtils;
import com.numberone.emp.domain.EmpNonworkday;
import com.numberone.emp.service.IAttendWorkdayService;
import com.numberone.framework.web.base.BaseController;

/**
 * 考勤日常打卡管理
 * @Description: 描述
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年1月26日 下午3:22:47
 * @param:
 */
@Controller
@RequestMapping("/attend/workday")
public class AttendWorkdayController extends BaseController
{
	
	@Autowired
	private IAttendWorkdayService attendWorkdayService;
	
    private String prefix = "attend/workday";

    @RequiresPermissions("attend:workday")
    @GetMapping()
    public String workday()
    {
        return prefix + "/workday";
    }
    /**
     * 查询工作日列表
     * @param: @param empAttendday
     * @param: @return 参数说明
     * @return: TableDataInfo 返回类型
     * @throws
     */
    @RequiresPermissions("attend:workday:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(EmpNonworkday empNonworkday)
    {
    	startPage();
    	List<EmpNonworkday> list = attendWorkdayService.selectWorkdayList(empNonworkday);
    	
        return getDataTable(list);
    }
    /**
     * 新增
     * @param: @param mmap
     * @param: @return 参数说明
     * @return: String 返回类型
     * @throws
     */
    @GetMapping("/add")
    public String add()
    {
//        mmap.put("roles", roleService.selectRoleAll());
        return prefix + "/add";
    }
    /**
     * 新增保存
     */
    @Log(title = "日程管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(EmpNonworkday empNonworkday)
    {
    	
        return toAjax(attendWorkdayService.insertWorkday(empNonworkday));
    }
    
    /**
     * 修改日程
     * @param: @param userId
     * @param: @param mmap
     * @param: @return 参数说明
     * @return: String 返回类型
     * @throws
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
    	EmpNonworkday empNonworkday = attendWorkdayService.selectWorkdayById(id);
    	empNonworkday.setWorkdate(DateUtils.getOnlyDate(empNonworkday.getWorkdate()));
    	mmap.put("empNonworkday", empNonworkday);
        return prefix + "/edit";
    }

    /**
     * 保存修改日程
     * @param: @param user
     * @param: @return 参数说明
     * @return: AjaxResult 返回类型
     * @throws
     */
    @Log(title = "日程管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(EmpNonworkday empNonworkday)
    {
        return toAjax(attendWorkdayService.updateWorkday(empNonworkday));
    }
    
    @Log(title = "日程管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(attendWorkdayService.deleteWorkdayByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }
    
    /**
     * 校验日程唯一
     */
    @PostMapping("/checkWorkdateUnique")
    @ResponseBody
    public String checkWorkdateUnique(Date workdate)
    {
        return attendWorkdayService.checkWorkdateUnique(workdate);
    }
    
    /**
     * 更新时校验日程唯一
     */
    @PostMapping("/checkWorkdateUniqueForUpdate")
    @ResponseBody
    public String checkWorkdateUniqueForUpdate(EmpNonworkday empNonworkday)
    {
    	return attendWorkdayService.checkWorkdateUniqueForUpdate(empNonworkday);
    }
    
    /**
     * 查询时间日程
     */
    @PostMapping("/dateJson")
    @ResponseBody
    public Map<String,String> dateJson(Date workdate)
    {
    	
        return attendWorkdayService.selectWorkdayListForCalendar();
    }
}