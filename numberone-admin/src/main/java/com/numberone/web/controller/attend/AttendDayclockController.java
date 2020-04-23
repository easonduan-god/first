package com.numberone.web.controller.attend;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.DateUtils;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.StringUtils;
import com.numberone.emp.domain.EmpAttendday;
import com.numberone.emp.domain.EmpAttendinfo;
import com.numberone.emp.service.IAttendDayclockService;
import com.numberone.framework.web.base.BaseController;
import com.numberone.system.domain.SysUser;

/**
 * 考勤日常打卡管理
 * @Description: 描述
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年1月26日 下午3:22:47
 * @param:
 */
@Controller
@RequestMapping("/attend/dayclock")
public class AttendDayclockController extends BaseController
{
	@Autowired
	private IAttendDayclockService attendDayclockService;
	
    private String prefix = "attend";

    @RequiresPermissions("attend:dayclock")
    @GetMapping()
    public String dayclock()
    {
        return prefix + "/dayclock";
    }
    /**
     * 查询日常考勤信息
     * @param: @param empAttendinfo
     * @param: @return 参数说明
     * @return: String 返回类型
     * @throws
     */
    @RequiresPermissions("attend:dayclock:list")
    @PostMapping("/list")
    @Log(title = "查询日常考勤信息", businessType = BusinessType.OTHER)
    @ResponseBody
    public TableDataInfo list(EmpAttendday empAttendday)
    {
        startPage();
        empAttendday.setUserId(getUserId());
        List<EmpAttendday> list = attendDayclockService.selectAttendDayList(empAttendday);
        return getDataTable(list);
    }
    /**
     * 签到
     * @param: @param user
     * @param: @return 参数说明
     * @return: AjaxResult 返回类型
     * @Transactional(rollbackFor = Exception.class)
     * @throws
     */
	@RequiresPermissions("attend:dayclock:checkin")
    @Log(title = "签到", businessType = BusinessType.INSERT)
    @PostMapping("/checkin")
	@ResponseBody
    public AjaxResult checkin(EmpAttendinfo empAttendnfo)
    {
    	//补全数据
		empAttendnfo.setAttendinfoId(StringUtils.getUUID());
    	SysUser user = getSysUser();
    	empAttendnfo.setUserId(user.getUserId());
    	empAttendnfo.setEmpId(user.getEmpId());
    	Date date = new Date();
    	empAttendnfo.setAttendDate(date);
    	empAttendnfo.setRecordTime(date);
    	empAttendnfo.setWeek(DateUtils.dayOfWeek(date));
    	empAttendnfo.setRemark("");
    	Integer currHour = DateUtils.hour(date);
    	if(currHour == 5) return error("现在是系统维护时间，无法签到");
    	int result = attendDayclockService.insertDayClock(empAttendnfo);
    	if(result>0)
    		return success("签到成功");
    	else
    		return error("签到失败");
    }
	/**
	 * 查询当前用户所有的打卡记录
	 * @param: @param empAttendinfo
	 * @param: @return 参数说明
	 * @return: AjaxResult 返回类型
	 * @throws
	 */
	@RequiresPermissions("attend:dayclock:checkin")
	@PostMapping("/attendInfoList")
	@ResponseBody
	public TableDataInfo attendInfoList()
	{
		startPage();
        List<EmpAttendinfo> list = attendDayclockService.selectAttendInfoList(getUserId());
        return getDataTable(list);
	}
	/**
	 * 跳转到原始数据页面
	 * @param: @param empAttendinfo
	 * @param: @return 参数说明
	 * @return: String 返回类型
	 * @throws
	 */
	@RequiresPermissions("attend:dayclock:originalData")
	@GetMapping("/originalData")
	public String originalData(EmpAttendinfo empAttendinfo)
	{
		return prefix + "/originaldata";
	}
	
}