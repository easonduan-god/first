package com.numberone.web.controller.attend;

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
import com.numberone.emp.domain.EmpOvertimeAudit;
import com.numberone.emp.domain.EmpOvertimeBill;
import com.numberone.emp.service.IAttendOvertimeBillService;
import com.numberone.framework.web.base.BaseController;
import com.numberone.system.domain.SysUser;

/**
 * 延时工资单控制层
 * @author eason
 * @date 2020-02-20
 */
@RequestMapping("/attend/overtimeBill")
@Controller
public class AttendOvertimeBillController extends BaseController{
	private String prefix = "attend/overtimeBill";
	@Autowired
	private IAttendOvertimeBillService attendOvertimeBillService;
	/**
	 * 延时工作单申请页面
	 * @param: @param mmap
	 * @param: @return
	 * @return: String
	 */
	@GetMapping("/apply")
	@RequiresPermissions("attend:overtimeBill:apply")
	public String apply(ModelMap mmap){
		SysUser sysUser = getSysUser();
		
		mmap.put("sysUser", sysUser);
		mmap.put("currdate",DateUtils.getDate());
		return prefix+"/apply";
	}
	
	/**
	 * 延时工作单待办页面
	 * @param: @param mmap
	 * @param: @return
	 * @return: String
	 */
	@GetMapping("/backlog")
	@RequiresPermissions("attend:overtimeBill:apply")
	public String backlog(ModelMap mmap){
		
		return prefix+"/backlog";
	}
	/**
	 * 延时待办列表
	 * @param: @param empOvertimeAudit
	 * @param: @return
	 * @return: TableDataInfo
	 */
	@RequiresPermissions("attend:overtimeBill:backlog")
	@PostMapping("/backlogList")
	@ResponseBody
	public TableDataInfo backlogList(EmpOvertimeAudit empOvertimeAudit)
	{
		startPage();
		//只能查询分配给自己考勤单
		empOvertimeAudit.setAuditUserId(getUserId());
		return getDataTable(attendOvertimeBillService.selectAuditListByAuditId(empOvertimeAudit));
	}
	/**
	 * 我的延时工作单页面
	 * @param: @param mmap
	 * @param: @return
	 * @return: String
	 */
	@GetMapping("/mine")
	@RequiresPermissions("attend:overtimeBill:mine")
	public String mine(ModelMap mmap){
		
		return prefix+"/mine";
	}
	/**
	 * 查询我的延时工单
	 * @param: @param empOvertimeBill
	 * @param: @return
	 * @return: TableDataInfo
	 */
	@RequiresPermissions("attend:overtimeBill:mine")
	@PostMapping("/mineList")
	@ResponseBody
	public TableDataInfo mineList(EmpOvertimeBill empOvertimeBill)
	{
		startPage();
		empOvertimeBill.setUserId(getUserId());
		return getDataTable(attendOvertimeBillService.selectOvertimeBillOfMine(empOvertimeBill));
	}
	/**
	 * 发起审批
	 * @param: @param mmap
	 * @param: @return
	 * @return: String
	 */
	@PostMapping("/launchAudit")
	@RequiresPermissions("attend:overtimeBill:apply")
	@Log(title = "延时工作单管理", businessType = BusinessType.INSERT)
	@ResponseBody
	public AjaxResult launchAudit(EmpOvertimeBill overtimeBill){
		return toAjax(attendOvertimeBillService.launchAudit(overtimeBill,getSysUser()));
	}
	
	/**
	 * 我的延时工单详情 只能查看自己发起或参与延时工单/交由自己审核的延时工单详情 	管理员除外
	 * @param: @param attendBillId
	 * @param: @param mmap
	 * @param: @return
	 * @return: String
	 */
	@RequiresPermissions("attend:overtimeBill:mine")
    @RequestMapping("/detailMine/{overtimeBillId}")
    public String detailMine(@PathVariable("overtimeBillId") String overtimeBillId, ModelMap mmap)
    {
        mmap.put("empOvertimeBill", attendOvertimeBillService.selectByIdAndRelationMine(overtimeBillId,getSysUser()));
        return prefix + "/detail";
    }
	/**
	 * 查看延时工单审核单详情 只能查看与自己相关的 管理员除外
	 * @param: @param overtimeAuditId
	 * @param: @param mmap
	 * @param: @return
	 * @return: String
	 */
	@RequiresPermissions("attend:overtimeBill:backlog")
	@RequestMapping("/detailAudit/{overtimeAuditId}")
	public String detailAudit(@PathVariable("overtimeAuditId") String overtimeAuditId, ModelMap mmap)
	{
		mmap.put("empOvertimeAudit", attendOvertimeBillService.selectOvertimeAuditByAuditIdAndRelationMine(overtimeAuditId,getSysUser()));
		return prefix + "/detailAudit";
	}
	
	@RequiresPermissions("attend:overtimeBill:backlog")
	@PostMapping("/audit")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public AjaxResult batchAudit(EmpOvertimeAudit empOvertimeAudit){
		Integer auditFlag = empOvertimeAudit.getAuditFlag();
		String overtimeAuditIds = empOvertimeAudit.getParams().get("overtimeAuditIds")+"";
		String remark = empOvertimeAudit.getRemark();
		if(overtimeAuditIds.trim() == ""){
			return AjaxResult.error("审核失败,请联系管理员！");
		}
		//1.首先判断审核状态为通过还是驳回
		//审核不通过
		int row = 0;
		if(auditFlag==0){
			row = attendOvertimeBillService.batchAuditRefuse(overtimeAuditIds,getSysUser(),remark);
		}
		//审核通过
		if(auditFlag==1){
			row = attendOvertimeBillService.batchAuditPass(overtimeAuditIds,getSysUser(),remark);
			
		}
		return row>0?AjaxResult.success("审核成功"):AjaxResult.error("审核失败,请联系管理员！");
	}
	
	/**
	 * 追踪延时工单审核
	 * @param: @param overtimeBillId
	 * @param: @param mmap
	 * @param: @return
	 * @return: String
	 */
	@RequiresPermissions("attend:overtimeBill:mine")
	@RequestMapping("/trace/{overtimeBillId}")
	public String trace(@PathVariable("overtimeBillId") String overtimeBillId, ModelMap mmap)
	{
		mmap.put("rsList", attendOvertimeBillService.selectOvertimeBillTrace(overtimeBillId));
		return prefix + "/trace";
	}
}
