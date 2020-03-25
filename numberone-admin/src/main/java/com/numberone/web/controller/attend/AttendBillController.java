package com.numberone.web.controller.attend;

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

import com.numberone.common.base.AjaxResult;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.DateUtils;
import com.numberone.emp.domain.EmpAttendAudit;
import com.numberone.emp.domain.EmpAttendBill;
import com.numberone.emp.service.IAttendBillService;
import com.numberone.framework.manager.AsyncManager;
import com.numberone.framework.manager.factory.AsyncFactory;
import com.numberone.framework.web.base.BaseController;
import com.numberone.system.domain.SysUser;
import com.numberone.system.service.ISysUserService;

/**
 * 考勤单管理
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年2月15日 下午9:12:56
 * @param:
 */
@Controller
@RequestMapping("/attend/attendBill")
public class AttendBillController extends BaseController{
	private String prefix = "attend/attendBill";
	@Autowired
	private ISysUserService userService;
	@Autowired
	private IAttendBillService attendBillService;
	
	
	@RequiresPermissions("attend:attendBill:apply")
    @GetMapping("/apply")
    public String apply(ModelMap mmap)
    {
		//查询 上月累计请假 本月累计请假 年假总天数 年假剩余天数
		Map<String,Double> resutlMap = attendBillService.selectLeaveAndYearVaction(getEmpId());
		
		mmap.put("resutlMap", resutlMap);
    	mmap.put("currDate", DateUtils.getDate());
        return prefix + "/apply";
    }
	/**
	 * 考勤待办
	 * @param: @param mmap
	 * @param: @return 参数说明
	 * @return List<EmpAttendAudit> 返回类型
	 */
	@RequiresPermissions("attend:attendBill:backlog")
	@GetMapping("/backlog")
	public String backlog(ModelMap mmap)
	{
		/*判断是否用户岗位是否是部门领导，部门领导可以加载全部，普通员工角色只能查询自己
    	ceo和人资可以查询所有员工*/
    	List<SysUser> list = userService.selectUserByUserIdAndPost(getSysUser());
    	mmap.put("userList", list);
		return prefix + "/backlog";
	}
	/**
	 * 根据考勤审核单id查看考勤单详细信息
	 * @param: @param attendAuditId
	 * @param: @return 参数说明
	 * @return List<EmpAttendAudit> 返回类型
	 */
	@RequiresPermissions("attend:attendBill:backlog")
    @GetMapping("/detail/{attendAuditId}")
    public String detail(@PathVariable("attendAuditId") String attendAuditId, ModelMap mmap)
    {
        mmap.put("empAttendBill", attendBillService.selectAttendBillByAuditId(attendAuditId));
        return prefix + "/detail";
    }
	
	@RequiresPermissions("attend:attendBill:backlog")
	@PostMapping("/backlogList")
	@ResponseBody
	public TableDataInfo backlogList(EmpAttendAudit empAttendAudit)
	{
		startPage();
		//只能查询分配给自己考勤单
		empAttendAudit.setAuditUserId(getUserId());
		return getDataTable(attendBillService.selectAuditListByAuditId(empAttendAudit));
	}
	
	/**
     * 计算申请工时
     */
	@RequiresPermissions("attend:attendBill:apply")
    @PostMapping("/calcWorkdays")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult calcWorkdays(EmpAttendBill empAttendBill)
    {
		empAttendBill.setUser(getSysUser());
		AjaxResult result = attendBillService.calcWorkdays(empAttendBill);
		//定时删除临时表
		if("0".equals(result.get("code")+"")){
			AsyncManager.me().execute(AsyncFactory.deleteAttendTemp((String)result.get("empAttendBillTempId")), 1000*5);
		}
        return result;
    }
	/**
	 * 提交审批
	 */
	@RequiresPermissions("attend:attendBill:apply")
	@PostMapping("/launchAudit")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public AjaxResult launchAudit(EmpAttendBill empAttendBill)
	{
		
		return toAjax(attendBillService.launchAudit(empAttendBill,getSysUser()));
	}
	/**
	 * 批量审核
	 * @param: @param auditIds
	 * @param: @param auditFlag
	 * @param: @return
	 * @return: AjaxResult
	 */
	@RequiresPermissions("attend:attendBill:backlog")
	@PostMapping("/audit")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public AjaxResult batchAudit(EmpAttendAudit empAttendAudit){
		Integer auditFlag = empAttendAudit.getAuditFlag();
		String auditIds = empAttendAudit.getParams().get("auditIds")+"";
		String remark = empAttendAudit.getRemark();
		//1.首先判断审核状态为通过还是驳回
		//审核不通过
		int row = 0;
		if(auditFlag==0){
			row = attendBillService.batchAuditRefuse(auditIds,getSysUser(),remark);
		}
		//审核通过
		if(auditFlag==1){
			row = attendBillService.batchAuditPass(auditIds,getSysUser(),remark);
			
		}
		return row>0?AjaxResult.success("审核成功"):AjaxResult.error("审核失败,请联系管理员！");
	}
	
}
