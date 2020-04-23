package com.numberone.web.controller.attend;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.exception.BusinessException;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.DateUtils;
import com.numberone.common.utils.StringUtils;
import com.numberone.common.utils.poi.ExcelUtil;
import com.numberone.emp.domain.EmpYearVacation;
import com.numberone.emp.service.IAttendYearVacationService;
import com.numberone.framework.web.base.BaseController;
import com.numberone.system.domain.SysUser;

/**
 * 年假管理控制层
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年2月6日 上午11:33:16
 * @param:
 */
@Controller
@RequestMapping("/attend/yearVacation")
public class AttendYearVacationController  extends BaseController{
	private String prefix = "attend/yearVacation";
	
	@Autowired
	private IAttendYearVacationService attendYearVacationService;
	
	/*
	@Autowired
	private ISysUserService userService;*/
	/**
	 * 加载部门员工，显示下拉框,跳转到查询年假页面
	 * @param: @param mmap
	 * @param: @return 参数说明
	 * @return: String 返回类型
	 * @throws
	 */
    @RequiresPermissions("attend:yearVacation")
    @GetMapping("/yearVacation")
    public String yearVacation(ModelMap mmap)
    {
    	/*判断是否用户岗位是否是部门领导，部门领导可以加载全部，普通员工角色只能查询自己
    	ceo和人资可以查询所有员工*/
    	List<SysUser> list = getSysUser().getOperableUserList();
    	mmap.put("userList", list);
    	mmap.put("user", getSysUser());
    	mmap.put("currYear", DateUtils.parseDateToStr(DateUtils.YYYY, new Date()));
        return prefix + "/yearVacation";
    }
    /**
     * 查询年假信息
     * @param: @param empYearVacation
     * @param: @return 参数说明
     * @return: TableDataInfo 返回类型
     * @throws
     */
    @RequiresPermissions("attend:yearVacation")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(EmpYearVacation empYearVacation)
    {
    	startPage();
    	Map<String, Object> params = empYearVacation.getParams();
    	List<EmpYearVacation> list = null;
    	if(StringUtils.isEmpty((String)params.get("userIds"))){//查询全部用户
    		list = attendYearVacationService.selectListByOperableUserIds(empYearVacation,getSysUser().getOperableUserIds());
    	}else{
    		if("1".equals("2")){
    			params.put("userIds", getUserId()+"");//默认查询自己
    		}else{
	    		//若查询的用户id不包括在可操作列表中
	    		if(!getSysUser().getOperableUserIds().contains(Long.parseLong((String)params.get("userIds")))){
	    			throw new BusinessException("异常操作");
	    		}
    		}
    		list = attendYearVacationService.selectYearVacationList(empYearVacation);
    		
    	}
        return getDataTable(list);
    }
    
    @Log(title = "年假管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("attend:yearVacation")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EmpYearVacation empYearVacation)
    {
    	startPage();
    	Map<String, Object> params = empYearVacation.getParams();
    	List<EmpYearVacation> list = null;
    	if(params.get("userId")!=null && params.get("userId").equals("0")){//查询全部用户
    		list = attendYearVacationService.selectListByOperableUserIds(empYearVacation,getSysUser().getOperableUserIds());
    	}else{
    		if(StringUtils.isEmpty((String)params.get("userId"))){
    			params.put("userId", getUserId());//默认查询自己
    		}else{
	    		//若查询的用户id不包括在可操作列表中
	    		if(!getSysUser().getOperableUserIds().contains(Long.parseLong((String)params.get("userId")))){
	    			throw new BusinessException("异常操作");
	    		}
    		}
    		list = attendYearVacationService.selectYearVacationList(empYearVacation);
    		
    	}
        //若查询的用户id不包括在可操作列表中
        ExcelUtil<EmpYearVacation> util = new ExcelUtil<EmpYearVacation>(EmpYearVacation.class);
        return util.exportExcel(list, "年假数据");
    }
}
