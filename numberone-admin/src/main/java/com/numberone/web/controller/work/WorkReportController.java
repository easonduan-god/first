package com.numberone.web.controller.work;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.DateUtils;
import com.numberone.common.utils.poi.ExcelUtil;
import com.numberone.framework.web.base.BaseController;
import com.numberone.work.domain.WorkTaskDeptReport;
import com.numberone.work.domain.WorkTaskEmpReport;
import com.numberone.work.service.IWorkReportService;

/**
 * 部门任务月报：存储部门每月处理任务的数据 信息操作处理
 * 
 * @author eason
 * @date 2020-05-09
 */
@Controller
@RequestMapping("/work/report")
public class WorkReportController extends BaseController
{
    private String prefix = "work/report";
	
	@Autowired
	private IWorkReportService workReportService;
	
	@RequiresPermissions("work:report:emp")
	@GetMapping("/emp")
	public String emp(ModelMap mmap)
	{
		//月份 --当前月份的一号
		Date month = DateUtils.formatDateToDate(new Date() , DateUtils.YYYY_MM);
		//上月月份
		Date lastMonth = DateUtils.getAroundDate(month, -10, DateUtils.YYYY_MM);
		mmap.put("currMonth", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, lastMonth));
		return prefix + "/emp";
	}
	@RequiresPermissions("work:report:dept")
	@GetMapping("/dept")
	public String dept(ModelMap mmap)
	{
		//月份 --当前月份的一号
		Date month = DateUtils.formatDateToDate(new Date() , DateUtils.YYYY_MM);
		//上月月份
		Date lastMonth = DateUtils.getAroundDate(month, -10, DateUtils.YYYY_MM);
		mmap.put("currMonth", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, lastMonth));
		return prefix + "/dept";
	}
	
	/**
	 * 查询部门任务月报：存储部门每月处理任务的数据列表
	 */
	@RequiresPermissions("work:report:emp")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(WorkTaskEmpReport workTaskEmpReport)
	{
		startPage();
		//月份 --当前月份的一号
		Date month = DateUtils.formatDateToDate(new Date() , DateUtils.YYYY_MM);
		//上月月份
		Date lastMonth = DateUtils.getAroundDate(month, -10, DateUtils.YYYY_MM);
		startPage();
		if(workTaskEmpReport.getDeptId()==null){
			workTaskEmpReport.setDeptId(getSysUser().getDeptId());
		}
		if(workTaskEmpReport.getMonth()==null){
			workTaskEmpReport.setMonth(lastMonth);
		}
		return getDataTable(workReportService.selectWorkTaskEmpReportList(workTaskEmpReport));
	}
	
	/**
	 * 报表json数据
	 * @return
	 */
	@RequiresPermissions("work:report:dept")
	@PostMapping("/reportData")
	@ResponseBody
	public AjaxResult reportData(WorkTaskDeptReport workTaskDeptReport){
		//获取本月月份和上月月份
		Date month = workTaskDeptReport.getMonth();
		String monthStr = DateUtils.parseDateToStr(DateUtils.YYYYMM, month);
		String lastMonthStr = DateUtils.parseDateToStr(DateUtils.YYYYMM, DateUtils.getAroundDate(month, -10, DateUtils.YYYY_MM));
		
		//查询本月月报
		WorkTaskDeptReport report = workReportService.selectDeptReport(workTaskDeptReport);
		workTaskDeptReport.setMonth(DateUtils.getAroundDate(month, -10, DateUtils.YYYY_MM));
		//查询上月月报
		WorkTaskDeptReport lastReport = workReportService.selectDeptReport(workTaskDeptReport);
		List<List<Object>> list = new ArrayList<List<Object>>();
		//填充6行数据
			List<Object> item1 = new ArrayList<Object>();
			List<Object> item2 = new ArrayList<Object>();
			List<Object> item3 = new ArrayList<Object>();
			List<Object> item4 = new ArrayList<Object>();
			List<Object> item5 = new ArrayList<Object>();
			List<Object> item6 = new ArrayList<Object>();
			if(report!=null){
				//任务报表
				item1.add(report.getEmpCount());
				item1.add(report.getTotalHours());
				item1.add(report.getTaskCount());
				item1.add(report.getAvgTaskHours());
				item1.add(report.getAvgTaskCount());
				//第一行任务同比
				item2.add(report.getEmpCount());
				item2.add(report.getTotalHours());
				item2.add(report.getTaskCount());
				item2.add(report.getAvgTaskHours());
				item2.add(report.getAvgTaskCount());
				
			}
			if(lastReport!=null){
				//第二行任务同比
				item3.add(report.getEmpCount());
				item3.add(report.getTotalHours());
				item3.add(report.getTaskCount());
				item3.add(report.getAvgTaskHours());
				item3.add(report.getAvgTaskCount());
				
			}
			list.add(item1);
			list.add(item2);
			list.add(item3);
			list.add(item4);
			list.add(item5);
			list.add(item6);
			
		
		AjaxResult ajaxResult = AjaxResult.success();
		ajaxResult.put("dataArr", list);
		ajaxResult.put("monthStr", monthStr);
		ajaxResult.put("lastMonthStr", lastMonthStr);
		return ajaxResult;
	}
	
    @Log(title = "报表管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("work:report:dept")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WorkTaskDeptReport workTaskDeptReport)
    {
		Date month = workTaskDeptReport.getMonth();
		String monthStr = DateUtils.parseDateToStr(DateUtils.YYYYMM, month);
		
		//查询本月月报
		
		WorkTaskDeptReport report = workReportService.selectDeptReport(workTaskDeptReport);
		List<WorkTaskDeptReport> list = new ArrayList<WorkTaskDeptReport>();
		list.add(report);
        ExcelUtil<WorkTaskDeptReport> util = new ExcelUtil<WorkTaskDeptReport>(WorkTaskDeptReport.class);
        return util.exportExcel(list, monthStr+"期考勤报表");
    }
	
}
