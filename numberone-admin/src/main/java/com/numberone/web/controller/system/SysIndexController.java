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
import com.numberone.emp.mapper.EmpOvertimeBillMapper;
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
    @Autowired 
    private EmpOvertimeBillMapper empOvertimeBillMapper;
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
	    	
	    	List<Map<String,String>> list = attendWorkdayService.selectCalendarJson(getSysUser());
	    	for (Map<String, String> map2 : list) {
    			List<String> itemList = new ArrayList<String>();
    			itemList.add(map2.get("attend_code"));
    			itemList.add(map2.get("attend_label"));
    			itemMap.put(map2.get("date"), itemList);
			}
	    map.put("data", itemMap);
	    return map;
    }

    // 控制台
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        List<SysNotice> noticeList = noticeService.selectMainNotice();
        mmap.put("version", Global.getVersion());
        mmap.put("noticeList", noticeList);
        
        //待办事项数 延时待办 考勤待办 个人任务待办 部门任务待办 任务审核待办
        Map<String, String> backlogMap = empOvertimeBillMapper.selectBacklogCount(getUserId(),getSysUser().getDeptId());
        mmap.put("backlogMap", backlogMap);
        
        return "main";
    }

    // test页面
    @GetMapping("/system/test")
    public String test(ModelMap mmap)
    {
    	return "test";
    }
    // 预览页面
    @GetMapping("/system/preview")
    public String preview(ModelMap mmap)
    {
    	return "preview";
    }
    /*@GetMapping("/system/json/{jsonName}")
    @ResponseBody
    public String animationJson(@PathVariable(name="jsonName") String jsonName){
    	
    	Resource res = null;
    	String json = "";
		try {
			res = new ClassPathResource("static/json/"+jsonName+".json");
			json = IOUtils.toString(res.getInputStream(), Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return json;
    }*/
}
