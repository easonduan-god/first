package com.numberone.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.numberone.common.config.Global;
import com.numberone.emp.domain.EmpNonworkday;
import com.numberone.emp.service.IAttendWorkdayService;
import com.numberone.framework.web.base.BaseController;
import com.numberone.system.domain.SysMenu;
import com.numberone.system.domain.SysNotice;
import com.numberone.system.domain.SysUser;
import com.numberone.system.service.ISysMenuService;
import com.numberone.system.service.ISysNoticeService;

/**
 * 首页 业务处理
 * 
 * @author numberone
 */
@Controller
public class SysIndexController extends BaseController
{
    @Autowired
    private ISysMenuService menuService;

    @Autowired 
    private ISysNoticeService noticeService;
    
    @Autowired 
    private IAttendWorkdayService attendWorkdayService;
    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        SysUser user = getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", Global.getCopyrightYear());
        return "index";
    }
    // 日历
    @GetMapping("/index/calendar")
    public String calendar(ModelMap mmap)
    {
    	return "calendar";
    }
    // 日历
    @RequestMapping("/index/calendarJson")
    @ResponseBody
    public Map<String,Object> calendarJson(ModelMap mmap)
    {
    	Map<String,Object> map = new HashMap<String,Object>();
    	
	    	Map<String,List<String>> itemMap = new HashMap<String,List<String>>();
	    	
	    	
	    	List<EmpNonworkday> list = attendWorkdayService.selectWorkdayList(new EmpNonworkday());
	    	for (EmpNonworkday empNonworkday : list) {
	    		String workdateStr = empNonworkday.getWorkdateStr();
	    		String workdateName = empNonworkday.getWorkdateName();
	    		Integer workdateFlag = empNonworkday.getWorkdateFlag();//日程状态(0正常 1工作日 2休息日)
	    		/**
	    		 *考勤代码 0事假 1年假 2调休假 3忘记打卡 4迟到 5早退 6矿工 7迟到+早退（迟到） 9休息日
					10 工作日
	    		 */
	    		if(workdateFlag==1){//工作日 1->10
	    			List<String> itemList = new ArrayList<String>();
	    			itemList.add(10+"");
	    			itemList.add(workdateName);
	    			itemMap.put(workdateStr, itemList);
	    		}
	    		if(workdateFlag==2){//休息日 2->9
	    			List<String> itemList = new ArrayList<String>();
	    			itemList.add(9+"");
	    			itemList.add(workdateName);
	    			itemMap.put(workdateStr, itemList);
	    		}
	    	}
	    	List<String> itemList2 = new ArrayList<String>();
	    	itemList2.add("4");
	    	itemList2.add("迟到");
	    	List<String> itemList3 = new ArrayList<String>();
	    	itemList3.add("9");
	    	itemList3.add("休息日");
	    	List<String> itemList4 = new ArrayList<String>();
	    	itemList4.add("10");
	    	itemList4.add("工作日");
	    	itemMap.put("2020-03-23",itemList2);
	    	itemMap.put("2020-03-25",itemList3);
	    	itemMap.put("2020-03-15",itemList4);
	    map.put("data", itemMap);
	    return map;
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        List<SysNotice> noticeList = noticeService.selectMainNotice();
        mmap.put("version", Global.getVersion());
        mmap.put("noticeList", noticeList);
        return "main";
    }
}
