package com.numberone.web.controller.attend;

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
import com.numberone.emp.domain.EmpAttendReport;
import com.numberone.emp.domain.EmpDeptAttendReport;
import com.numberone.emp.service.IAttendReportService;
import com.numberone.framework.web.base.BaseController;

/**
 * 员工考勤月报：用于统计每一个员工的月度考勤，每一个月的月初统计上个月的考勤 信息操作处理
 * 
 * @author eason
 * @date 2020-05-09
 */
@Controller
@RequestMapping("/attend/report")
public class AttendReportController extends BaseController
{
    private String prefix = "attend/report";
	
	@Autowired
	private IAttendReportService attendReportService;
	
	@RequiresPermissions("attend:report:emp")
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
	@RequiresPermissions("attend:report:dept")
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
	 * 员工考勤月报,经理可以重新部门的，管理员查询全部
	 */
	@RequiresPermissions("attend:report:emp")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(EmpAttendReport empAttendReport)
	{
		//月份 --当前月份的一号
		Date month = DateUtils.formatDateToDate(new Date() , DateUtils.YYYY_MM);
		//上月月份
		Date lastMonth = DateUtils.getAroundDate(month, -10, DateUtils.YYYY_MM);
		startPage();
		if(empAttendReport.getDeptId()==null){
			empAttendReport.setDeptId(getSysUser().getDeptId());
		}
		if(empAttendReport.getMonth()==null){
			empAttendReport.setMonth(lastMonth);
		}
        List<EmpAttendReport> list = attendReportService.selectEmpAttendReportList(empAttendReport);
		return getDataTable(list);
	}
	/**
	 * 报表json数据
	 * @return
	 */
	@RequiresPermissions("attend:report:dept")
	@PostMapping("/reportData")
	@ResponseBody
	public AjaxResult reportData(EmpDeptAttendReport empDeptAttendReport){
		//获取本月月份和上月月份
		Date month = empDeptAttendReport.getMonth();
		String monthStr = DateUtils.parseDateToStr(DateUtils.YYYYMM, month);
		String lastMonthStr = DateUtils.parseDateToStr(DateUtils.YYYYMM, DateUtils.getAroundDate(month, -10, DateUtils.YYYY_MM));
		
		//查询本月月报
		EmpDeptAttendReport report = attendReportService.selectDeptReport(empDeptAttendReport);
		empDeptAttendReport.setMonth(DateUtils.getAroundDate(month, -10, DateUtils.YYYY_MM));
		//查询上月月报
		EmpDeptAttendReport lastReport = attendReportService.selectDeptReport(empDeptAttendReport);
		List<List<Object>> list = new ArrayList<List<Object>>();
		//填充6行数据
			List<Object> item1 = new ArrayList<Object>();
			List<Object> item2 = new ArrayList<Object>();
			List<Object> item3 = new ArrayList<Object>();
			List<Object> item4 = new ArrayList<Object>();
			List<Object> item5 = new ArrayList<Object>();
			List<Object> item6 = new ArrayList<Object>();
			if(report!=null){
				//考勤报表
				item1.add(report.getLateRate());
				item1.add(report.getEarlyRate());
				item1.add(report.getAbsentRate());
				item1.add(report.getForgetRate());
				item1.add(report.getLeaveRate());
				item1.add(report.getAbnormalRate());
				//延时工单月报
				item4.add(report.getOvertimeHours());
				item4.add(report.getOvertimeCount());
				item4.add(report.getAvgOvertimeHours());
				item4.add(report.getOvertimePersonCount());
				item4.add(report.getNoOvertimePersonCount());
				item4.add(report.getOvertimeMore100());
				item4.add(report.getOvertimeLess30());
				//第一行考勤同比
				item2.add(report.getEmpCount());
				item2.add(report.getEarlyCount());
				item2.add(report.getLateCount());
				item2.add(report.getAbsentCount());
				item2.add(report.getForgetCount());
				item2.add(report.getLeaveCount());
				item2.add(report.getLeaveDays());
				item2.add(report.getLeaveHours());
				item2.add(report.getPerfectCount());
				
				//第一行延时同比
				item5.add(report.getOvertimeHours());
				item5.add(report.getOvertimeCount());
				item5.add(report.getAvgOvertimeHours());
				item5.add(report.getOvertimePersonCount());
				item5.add(report.getNoOvertimePersonCount());
				item5.add(report.getOvertimeMore100());
				item5.add(report.getOvertimeLess30());
			}
			if(lastReport!=null){
				//第二行考勤同比
				item3.add(lastReport.getEmpCount());
				item3.add(lastReport.getEarlyCount());
				item3.add(lastReport.getLateCount());
				item3.add(lastReport.getAbsentCount());
				item3.add(lastReport.getForgetCount());
				item3.add(lastReport.getLeaveCount());
				item3.add(lastReport.getLeaveDays());
				item3.add(lastReport.getLeaveHours());
				item3.add(lastReport.getPerfectCount());
				
				//第二行延时同比
				item6.add(lastReport.getOvertimeHours());
				item6.add(lastReport.getOvertimeCount());
				item6.add(lastReport.getAvgOvertimeHours());
				item6.add(lastReport.getOvertimePersonCount());
				item6.add(lastReport.getNoOvertimePersonCount());
				item6.add(lastReport.getOvertimeMore100());
				item6.add(lastReport.getOvertimeLess30());
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
    @RequiresPermissions("attend:report:dept")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EmpDeptAttendReport empDeptAttendReport)
    {
		Date month = empDeptAttendReport.getMonth();
		String monthStr = DateUtils.parseDateToStr(DateUtils.YYYYMM, month);
		
		//查询本月月报
		
		EmpDeptAttendReport report = attendReportService.selectDeptReport(empDeptAttendReport);
		List<EmpDeptAttendReport> list = new ArrayList<EmpDeptAttendReport>();
		list.add(report);
        ExcelUtil<EmpDeptAttendReport> util = new ExcelUtil<EmpDeptAttendReport>(EmpDeptAttendReport.class);
        return util.exportExcel(list, monthStr+"期考勤报表");
    }
}
