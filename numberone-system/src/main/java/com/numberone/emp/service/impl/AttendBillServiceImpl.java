package com.numberone.emp.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.common.base.AjaxResult;
import com.numberone.common.config.Global;
import com.numberone.common.constant.AttendConstants;
import com.numberone.common.exception.BusinessException;
import com.numberone.common.support.Convert;
import com.numberone.common.utils.Arith;
import com.numberone.common.utils.DateUtils;
import com.numberone.common.utils.StringUtils;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.emp.domain.EmpAttendAudit;
import com.numberone.emp.domain.EmpAttendBill;
import com.numberone.emp.domain.EmpAttendBillExample;
import com.numberone.emp.domain.EmpAttendBillLeavedayItems;
import com.numberone.emp.domain.EmpYearVacation;
import com.numberone.emp.domain.EmpYearVacationExample;
import com.numberone.emp.mapper.EmpAttendAuditMapper;
import com.numberone.emp.mapper.EmpAttendBillLeavedayItemsMapper;
import com.numberone.emp.mapper.EmpAttendBillMapper;
import com.numberone.emp.mapper.EmpYearVacationMapper;
import com.numberone.emp.service.IAttendBillService;
import com.numberone.emp.service.IAttendWorkdayService;
import com.numberone.system.domain.SysUser;
import com.numberone.system.service.ISysUserService;

/**
 * 考勤单管理业务层实现类
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年2月17日 下午1:18:32
 * @param:
 */
@Service
public class AttendBillServiceImpl implements IAttendBillService {
	
	@Autowired
	private EmpAttendBillMapper empAttendBillMapper;
	
	@Autowired
	private EmpYearVacationMapper empYearVacationMapper;
	@Autowired
	private EmpAttendBillLeavedayItemsMapper empAttendBillLeavedayItemsMapper;
	@Autowired
	private EmpAttendAuditMapper empAttendAuditMapper;
	
	@Autowired
	private ISysUserService userService;
	
	@Autowired
	private IAttendWorkdayService attendWorkdayService;
	@Override
	public Map<String, Double> selectLeaveAndYearVaction(String empId) {
		/*查询上月请假天数*/
		Double lastMonthLeave = empAttendBillLeavedayItemsMapper.selectLastMonthLeave(empId);
		/*查询本月月请假天数*/
		Double currMonthLeave = empAttendBillLeavedayItemsMapper.selectCurrMonthLeave(empId);
		/*查询年假总天数和剩余天数*/
		EmpYearVacationExample example = new EmpYearVacationExample();
		example.createCriteria().andEmpIdEqualTo(empId).andYearEqualTo(Integer.parseInt(DateUtils.dateTimeNow(DateUtils.YYYY)));
		
		List<EmpYearVacation> yearList = empYearVacationMapper.selectByExample(example);
		EmpYearVacation yearVacation = null;
		if(StringUtils.isNotEmpty(yearList)){
			yearVacation = yearList.get(0);
		}
		
		//创建映射结果集
		Map<String, Double> resultMap = new HashMap<String, Double>();
		resultMap.put("lastMonthLeave", lastMonthLeave!=null?lastMonthLeave:0);
		resultMap.put("currMonthLeave", currMonthLeave!=null?currMonthLeave:0);
		if(yearVacation!=null){
			resultMap.put("yearVacationSurplus", yearVacation.getTimeSurplus());
			resultMap.put("yearVacationTotal", yearVacation.getTimeTotal());
		}
		return resultMap;
	}

	@Override
	public Long insertAttendBillTemp(EmpAttendBill empAttendBill) {
		return empAttendBillMapper.insertAttendBillTemp(empAttendBill);
	}

	@Override
	public Long insertLeavedayItemsTemp(EmpAttendBillLeavedayItems leavedayItemsTemp) {
		return empAttendBillLeavedayItemsMapper.insertLeavedayItemsTemp(leavedayItemsTemp);
	}

	@Override
	public Long deleteAttendBillTempAndLeavedayTemp(String attendBillTempId) {
		Long billResult = empAttendBillMapper.deleteTempById(attendBillTempId);
		Long leaveResult = empAttendBillLeavedayItemsMapper.deleteEmpAttendBillLeavedayItemsByAttendBillTempId(attendBillTempId);
		
		return billResult + leaveResult;
	}

	@Override
	public EmpAttendBill selectTempById(String attendBillTempId) {
		
		return empAttendBillMapper.selectTempById(attendBillTempId);
	}

	@Override
	public Long insertAttendBill(EmpAttendBill empAttendBill) {
		return (long) empAttendBillMapper.insert(empAttendBill);
	}

	@Override
	public Long insertBatchLeaveItem(Map<String, String> map) {
		return empAttendBillMapper.insertBatchLeaveItem(map);
	}

	@Override
	public Long selectDateConflict(EmpAttendBill empAttendBill) {
		return empAttendBillLeavedayItemsMapper.selectDateConflict(empAttendBill);
	}

	@Override
	public int launchAudit(EmpAttendBill empAttendBill, SysUser sysUser) {
		//1.根据考勤单临时表id查询数据中是否还存在，不存在则提示“请重新计算工时”
		String attendBillTempId = (String) empAttendBill.getParams().get("attendBillTempId");
		
		if(StringUtils.isEmpty(attendBillTempId)) throw new BusinessException("请重新提交");
		EmpAttendBill empAttendBillTemp = empAttendBillMapper.selectTempById(attendBillTempId);
			//不存在，提示错误
			if(empAttendBillTemp == null)  throw new BusinessException("请重新计算工时"); 
		
		//2.查询是否有日期冲突的考勤单
			empAttendBill.setUserId(sysUser.getUserId());//用户id
			//empAttendBill，根据用户id，开始日期，结束日期，在emp_attend_bill_leaveday_items表中检索
			//判断是否有符合的记录，符合的话，提示页面“与已有考勤单冲突”
			if(empAttendBillLeavedayItemsMapper.selectDateConflict(empAttendBill)>0) throw new BusinessException("与已有考勤单冲突");
			
			//200508 新增 一个月内忘记打卡不准超过3次 包括未审核的
			if(empAttendBillTemp.getAttendType()==3){
				if(empAttendBillMapper.selectForgetCountByUserIdAndMonth(sysUser.getUserId(),empAttendBillTemp.getStartDate())>3){
					throw new BusinessException("一个月只允许申请不超过3次补卡");
				}
				
			}
			
		//3.获取临时表中的工作日和工时，存到考勤单对象中,并不补全勤单数据；
			String attendBillId = StringUtils.getUUID();
			empAttendBill.setAttendBillId(attendBillId);//id
			empAttendBill.setEmpId(sysUser.getEmpId());//员工号
			empAttendBill.setUserId(sysUser.getUserId());//用户id
			empAttendBill.setUserName(sysUser.getUserName());//用户名称
			empAttendBill.setDeptId(sysUser.getDeptId());//部门id
			empAttendBill.setDeptName(sysUser.getDeptName());//部门名称
			Integer attendType = empAttendBillTemp.getAttendType();//考勤类型(0事假 1年假 2调休假 3忘记打卡)
			empAttendBill.setAttendType(empAttendBillTemp.getAttendType());//考勤类型
			empAttendBill.setApplyWorkdays(empAttendBillTemp.getApplyWorkdays());//工作日
			empAttendBill.setApplyWorkdayTimes(empAttendBillTemp.getApplyWorkdayTimes());//工时
			//只有年假和调休假销假(0否 1是)，也就是抵扣年假(0事假 1年假 2调休假 3忘记打卡)
			empAttendBill.setIsOffet((attendType==1||attendType==2)?AttendConstants.IS_OFFET:AttendConstants.IS_NOT_OFFET);
			empAttendBill.setCompleteFlag(AttendConstants.IS_NOT_COMPLETE);//完成状态(0未完成 1已完成)
			empAttendBill.setAuditFlag(AttendConstants.UNAUDIT);//审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
			empAttendBill.setDelFlag(0);
			
			//存入考勤单
			empAttendBillMapper.insert(empAttendBill);
		//4.根据临时表id获取工作日项临时表数据，并关联上新的考勤单id，存到工作日项中；
			Map<String, String> map = new HashMap<String,String>();
			map.put("attendBillId", attendBillId);
			map.put("attendBillTempId", attendBillTempId);
			empAttendBillMapper.insertBatchLeaveItem(map);
		//将考勤单交由上级审批（管理员/最高领导自己审核），同时创建考勤审批emp_attend_audit
			//查询上级领导
			SysUser leader = userService.selectLeader(sysUser);
			if(leader==null) throw new BusinessException("内部错误");
			//创建EmpAttendAudit，补全数据
			EmpAttendAudit empAttendAudit = new EmpAttendAudit();
			String attendAuditId = StringUtils.getUUID();
			empAttendAudit.setAttendAuditId(attendAuditId);//考勤审核单id
			empAttendAudit.setAttendBillId(attendBillId);//考勤单id
			empAttendAudit.setUserId(sysUser.getUserId());//申请人用户id
			empAttendAudit.setEmpId(sysUser.getEmpId());//申请人工号
			empAttendAudit.setAuditJob(leader.getRolesName());//审核人职位
			//empAttendAudit.setAuditTime(new Date());//审核时间
			empAttendAudit.setAuditUserId(leader.getUserId());//审核人用户id
			empAttendAudit.setAuditEmpId(leader.getEmpId());//审核人工号
			empAttendAudit.setAuditName(leader.getUserName());//审核人名称
			empAttendAudit.setAuditFlag(AttendConstants.IN_AUDIT);//审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
			//保存到数据库
		return empAttendAuditMapper.insertEmpAttendAudit(empAttendAudit);
	}

	@Override
	public AjaxResult calcWorkdays(EmpAttendBill empAttendBill) {
		SysUser user = empAttendBill.getUser();
		AjaxResult result = new AjaxResult();
		//获取上传参数
    	Date startTime = empAttendBill.getStartTime();
    	Date startDate = empAttendBill.getStartDate();
    	Date endTime = empAttendBill.getEndTime();
    	Date endDate = empAttendBill.getEndDate();
    	//获取公司上班和下班时间
    	Double goWorkTime = Double.parseDouble(Global.getConfig("OAManage.goWorkTime"));
    	Double offWorkTime = Double.parseDouble(Global.getConfig("OAManage.offWorkTime"));
    	double goWorkHour = offWorkTime-goWorkTime;//上班工作多少小时
    	
    	//不能为空
    	if(startTime==null || endTime==null){
    		throw new BusinessException("开始时间和结束时间不能为空");
    	}
    	//开始不能大于结束
    	if(endTime.compareTo(startTime)==-1){
    		throw new BusinessException("开始时间不能大于结束时间");
    	}
    	
    	
    	double differ = DateUtils.getDateIntervalInDays(startDate, DateUtils.formatDateToDate(new Date(), DateUtils.YYYY_MM_DD));
    	//只有忘记打卡才可以填写之前的日期
    	if(empAttendBill.getAttendType()!=3 && differ>0){
    		throw new BusinessException("只有忘记打卡才可以填写之前的日期");
    	}
    	//忘记打卡最多只能请到7天前
    	if(empAttendBill.getAttendType()==3 && (differ>7 || differ<0 || endDate.compareTo(startDate)!=0)){
    		throw new BusinessException("补卡只能补到一周内,且只能在一个考勤日期内");
    	}
    	//不能在上班区间之外
    	long startTimeHour = DateUtils.getFragmentInHours(startTime, Calendar.DAY_OF_YEAR);
    	long endTimeHour = DateUtils.getFragmentInHours(endTime, Calendar.DAY_OF_YEAR);
    	//事假的话 要么开始时间为上班时间，要么结束时间为下班日期，必选其一
    	if(empAttendBill.getAttendType()==0 && (startTimeHour>goWorkTime || endTimeHour<offWorkTime)){
    		throw new BusinessException("事假，需开始时间为上班时间或者结束时间为下班时间");
    	}
    	
    	if(startTimeHour<goWorkTime || startTimeHour>offWorkTime
    			|| endTimeHour<goWorkTime || endTimeHour>offWorkTime){
    		throw new BusinessException("不能在上班区间之外");
    	}
    	//请假不能大于30天
    	if(DateUtils.getDateIntervalInDays(startDate, endDate)>30){
    		throw new BusinessException("考勤天数不能大于30天");
    	}
    	
    	//1.将申请的时间区间拆分成每天,此处dateutils方法，使用list封装
    	List<String> dateList = DateUtils.splitDate(startDate, endDate);
    	if(dateList.size()==0){
    		throw new BusinessException("输入错误");
    	}
    	//2.从数据库中查询工作日表
    	Map<String,Integer> workdayMap = attendWorkdayService.selectDateAndFlagMap();
    	
    	//3.遍历dateMap与申请的时间进行比对，首先判断该日期是否是周末，且没有被设置为工作日
		Iterator<String> iterator = dateList.iterator();
    	while(iterator.hasNext()) {
    		String dateKey = iterator.next();
    		Date date = DateUtils.parseDate(dateKey);
    		//是周日
			if(DateUtils.isWeekend(date)){
				//并且不是工作日
				if((workdayMap.containsKey(dateKey) && workdayMap.get(dateKey)!=1)){
					iterator.remove();
					continue;
				}
				//不在列表中
				if(!workdayMap.containsKey(dateKey)){
					iterator.remove();
					continue;
				}
				
			}
			//休息日 日程状态(0正常 1工作日 2休息日)
			if(workdayMap.containsKey(dateKey) && workdayMap.get(dateKey)==2){
				iterator.remove();
				continue;
			}
			
		}
    	if(dateList.size()!=0){
    		//申请开始日期字符串
    		String startDateStr = DateUtils.dateTime(startDate);
    		//申请结束日期字符串
    		String endDateStr = DateUtils.dateTime(endDate);
    		//申请开始时间浮点型
    		double startTimeHourDouble = DateUtils.getHourAndMminutesDoubleValue(startTime);
    		//申请结束时间浮点型
    		double endTimeHourDouble = DateUtils.getHourAndMminutesDoubleValue(endTime);
    		//第一天的工时
    		double firstDayHours = 0;
    		//最后一天的工时
    		double endHours = 0;
    		int size = dateList.size();
    		//4.筛选结束后，计算工时
	    		//若第一天最后一天都没有被筛选掉，就综合俩个的工时，然后计算list容量-2*goWorkHour
    			//--新增休假日单项临时表调整
    			Map<String,EmpAttendBillLeavedayItems> items = new HashMap<String,EmpAttendBillLeavedayItems>();
    			String attendBillTempId = StringUtils.getUUID();
    			for (String dateItems : dateList) {
    				EmpAttendBillLeavedayItems item = new EmpAttendBillLeavedayItems();
    				item.setAttendBillId(attendBillTempId);
    				item.setUserId(user.getUserId());
    				item.setEmpId(user.getEmpId());
    				item.setLeavedayItem(DateUtils.parseDate(dateItems));
    				item.setWorkdayTime(goWorkHour);//默认8
    				items.put(dateItems, item);
				}
    		if(dateList.contains(startDateStr)&&dateList.contains(endDateStr)){
    			//请假一天
	    		if(size==1){
	    			firstDayHours = endTimeHourDouble - startTimeHourDouble;
	    			items.get(startDateStr).setWorkdayTime(firstDayHours);
	    			size--;
				}else{
					firstDayHours = offWorkTime - startTimeHourDouble;
					endHours = endTimeHourDouble - goWorkTime;
					size -= 2;
					items.get(startDateStr).setWorkdayTime(firstDayHours);
					items.get(endDateStr).setWorkdayTime(endHours);
				}
    			//若第一天被筛选掉了，最后一天没有被筛选掉，就用最后一天的结束时间-上班时间得到最后一天的工时，然后计算list容量-1*goWorkHour
    		}else if(!dateList.contains(startDateStr)&&dateList.contains(endDateStr)){
    			endHours = endTimeHourDouble - goWorkTime;
    			items.get(endDateStr).setWorkdayTime(endHours);
    			size -= 1;
    			//若第一天没有筛选掉了，最后一天被筛选掉，就用下班时间-第一天的开始时间的第一天的工时，然后计算其他工时
    		}else if(dateList.contains(startDateStr)&&!dateList.contains(endDateStr)){
    			firstDayHours = offWorkTime - startTimeHourDouble;
    			items.get(startDateStr).setWorkdayTime(firstDayHours);
    			size -= 1;
    			//若第一天和最后一天都被筛选掉了，就容量*goWorkHour就行了
    		}
    		double workdayHours = firstDayHours + endHours + (size*goWorkHour);
    		//5.计算工作日：工时/goWorkHour
    		double workDays = Arith.ceil0_5(workdayHours/goWorkHour);
    		workdayHours = Arith.ceil0_5(workdayHours);
    		//6.将工时存到临时表中，并取得临时表的id，将请假日项存到请假日项临时表中，关联考勤单临时表id
    		
    			//020330 年假 调休假 需要剩余年假工时大于等于申请工时
    			if(empAttendBill.getAttendType()==1 || empAttendBill.getAttendType()==2){
    				/*查询年假总天数和剩余天数*/
    				EmpYearVacationExample example = new EmpYearVacationExample();
    				example.createCriteria().andEmpIdEqualTo(user.getEmpId()).andYearEqualTo(Integer.parseInt(DateUtils.dateTimeNow(DateUtils.YYYY)));
    				
    				List<EmpYearVacation> yearList = empYearVacationMapper.selectByExample(example);
    				EmpYearVacation yearVacation = null;
    				if(!StringUtils.isEmpty(yearList)){
    					yearVacation = yearList.get(0);
    				}
    				if(StringUtils.isEmpty(yearList) || yearVacation.getTimeSurplus()<(workdayHours/goWorkHour)){
    					throw new BusinessException("剩余年假不足");
    				}
    			}
    		
    		//新增考勤单临时表
    		EmpAttendBill empAttendBillTemp = new EmpAttendBill();
    		empAttendBillTemp.setAttendBillId(attendBillTempId);
    		empAttendBillTemp.setApplyWorkdays(workDays);
    		empAttendBillTemp.setApplyWorkdayTimes(workdayHours);
    		empAttendBillTemp.setAttendType(empAttendBill.getAttendType());
    		empAttendBillMapper.insertAttendBillTemp(empAttendBillTemp);
    			//新增休假日单项临时表
    		for (Entry<String, EmpAttendBillLeavedayItems> entry : items.entrySet()) {
    			insertLeavedayItemsTemp(entry.getValue());
			}
    		
    		//7.输出到前端
    		result.put("workDays", workDays);
    		result.put("workdayHours", workdayHours);
    		result.put("attendBillTempId", attendBillTempId);
    		
    		result.put("theme", user.getUserName()+"_"+empAttendBill.getParams().get("attendTypeText")+"_"+DateUtils.dateTimeNow("yyyyMMdd"));
    		result.put("code", 0);
    	}else{
    		throw new BusinessException("申请工时为0,请检查申请日期中是否包含休息日。");
    	}
    	return result;
	}

	@Override
	public List<EmpAttendAudit> selectAuditList(EmpAttendAudit empAttendAudit) {
		String usreIds = (String) empAttendAudit.getParams().get("userIds");
		if(StringUtils.isNotEmpty(usreIds)){
			String[] userIdArr = Convert.toStrArray(usreIds);
			empAttendAudit.getParams().put("userIdArr", userIdArr);
		}
		return empAttendAuditMapper.selectAuditList(empAttendAudit);
	}

	@Override
	public List<EmpAttendAudit> selectAuditListByAuditId(EmpAttendAudit empAttendAudit) {
		String usreIds = (String) empAttendAudit.getParams().get("userIds");
		if(StringUtils.isNotEmpty(usreIds)){
			String[] userIdArr = Convert.toStrArray(usreIds);
			empAttendAudit.getParams().put("userIdArr", userIdArr);
		}
		return empAttendAuditMapper.selectAuditListByAuditId(empAttendAudit);
	}

	@Override
	public EmpAttendBill selectAttendBillByAuditId(String attendAuditId) {
		return empAttendBillMapper.selectAttendBillByAuditId(attendAuditId);
	}

	/** 
	 * @Description: 
	 * @param: @return
	 * @throws
	 */
	@Override
	public int batchAuditRefuse(String auditIds, SysUser sysUser,String remark) {
		int row = 0;
		for (String auditId : auditIds.split(",")) {
			//根据审核id获取考勤审核单
			EmpAttendAudit attendAudit = empAttendAuditMapper.selectEmpAttendAuditById(auditId);
			
			//先判断考勤审核人用户id是否一致
			if(attendAudit.getAuditUserId()!=sysUser.getUserId()){
				throw new BusinessException("考勤单审核人与当前用户不一致");
			}
			
			//审核通过 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
			attendAudit.setAuditFlag(2);//审核状态
			attendAudit.setAuditTime(new Date());
			attendAudit.setRemark(remark);//备注
			row += empAttendAuditMapper.updateEmpAttendAudit(attendAudit);
			
				//200320 修改 批量审核不通过 忘了修改考勤单状态
				//设置考勤单为完成状态 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
				//获取考勤单
				EmpAttendBill empAttendBill = empAttendBillMapper.selectAttendBillByAuditId(attendAudit.getAttendAuditId());
				
				//设置考勤单为完成状态 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
				empAttendBill.setAuditFlag(2);
				//完成状态(0未完成 1已完成)
				empAttendBill.setCompleteFlag(1);
				
				//更新数据库
				row += empAttendBillMapper.updateByPrimaryKeySelective(empAttendBill);
				
				//200406 修改 批量审核不通过 忘了删除考勤日项表
				row += empAttendBillLeavedayItemsMapper.deleteEmpAttendBillLeavedayItemsByAttendBillId(empAttendBill.getAttendBillId());
		}
		return row;
	}

	/** 
	 * @Description: 
	 * @param: @return
	 * @throws
	 */
	@Override
	public int batchAuditPass(String auditIds, SysUser sysUser,String remark) {
		int row = 0;
		SysUser leader = null;
		if(StringUtils.isEmpty(auditIds)){
			throw new BusinessException("id为空");
		}
		//1.根据考勤审核单ids，以及审核人用户id批量审核，然后判断当前用户是否有上级，提交到上级审核
		for (String auditId : auditIds.split(",")) {
			//根据审核id获取考勤审核单
			EmpAttendAudit attendAudit = empAttendAuditMapper.selectEmpAttendAuditById(auditId);
			
			//先判断考勤审核人用户id是否一致
			if(attendAudit.getAuditUserId()!=sysUser.getUserId()){
				throw new BusinessException("考勤单审核人与当前用户不一致");
			}
			
			//审核通过 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
			attendAudit.setAuditFlag(3);//审核状态
			attendAudit.setAuditTime(new Date());
			attendAudit.setRemark(remark);//备注
			empAttendAuditMapper.updateEmpAttendAudit(attendAudit);
			
			//查询当前用户上级领导
			if(leader==null){
				leader = userService.selectLeader(sysUser);
			}
			//2.提交给领导审核
			if(leader!=null && leader.getUserId()!=sysUser.getUserId()){
				//复制attendAudit属性到新的attendAudit对象中
				EmpAttendAudit newAttendAudit = new EmpAttendAudit();
				BeanUtils.copyBeanProp(newAttendAudit, attendAudit);
				
				//更新新对象的参数
				newAttendAudit.setAttendAuditId(StringUtils.getUUID());
				newAttendAudit.setAuditTime(null);
				newAttendAudit.setRemark("");
				newAttendAudit.setAuditName(leader.getUserName());
				newAttendAudit.setAuditUserId(leader.getUserId());
				newAttendAudit.setAuditFlag(1);
				newAttendAudit.setAuditJob(leader.getRolesName());
				
				//更新到数据库
				row += empAttendAuditMapper.insertEmpAttendAudit(newAttendAudit);
			}
				
			//3.若没有上级了，也就是领导为自身或者为null
			if(leader==null || leader.getUserId()==sysUser.getUserId()){
				//获取考勤单
				EmpAttendBill empAttendBill = empAttendBillMapper.selectAttendBillByAuditId(attendAudit.getAttendAuditId());
				
				//设置考勤单为完成状态 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
				empAttendBill.setAuditFlag(3);
				//完成状态(0未完成 1已完成)
				empAttendBill.setCompleteFlag(1);
				
				//更新数据库
				row += empAttendBillMapper.updateByPrimaryKeySelective(empAttendBill);
			}
			
		}
		
		return row;
	}

	/**
	 * 查询自己的考勤单
	 * @param: @param empAttendBill
	 * @param: @return
	 * @return: List<EmpAttendBill>
	 */
	@Override
	public List<EmpAttendBill> selectAttendBillOfMine(EmpAttendBill empAttendBill) {
		return empAttendBillMapper.selectAttendBillOfMine(empAttendBill);
	}

	@Override
	public EmpAttendBill selectAttendBillByIdAndRelationMine(String attendBillId,SysUser sysUser) {
		if("admin".equals(sysUser.getLoginName())){
			return empAttendBillMapper.selectAttendBillById(attendBillId);
		}
		return empAttendBillMapper.selectAttendBillByIdAndRelationMine(attendBillId,sysUser.getUserId());
	}

	@Override
	public List<Map<String, Object>> selectAttendBillTrace(String attendBillId) {
		List<Map<String, Object>> rslist = new ArrayList<Map<String, Object>>();
		//1.查询考勤单信息
		EmpAttendBill attendBill = empAttendBillMapper.selectAttendBillById(attendBillId);
		Map<String, Object> itemMap = new HashMap<String,Object>();
		BeanUtils.copyProperties(itemMap, attendBill,true);
		rslist.add(itemMap);
		//2.查询考勤审核信息
		List<EmpAttendAudit> auditList = empAttendAuditMapper.selectAttendAuditByAttendBillId(attendBillId);
		for (EmpAttendAudit empAttendAudit : auditList) {
			Map<String, Object> auditMap = new HashMap<String,Object>();
			BeanUtils.copyProperties(auditMap, empAttendAudit,true);
			rslist.add(auditMap);
		}
		
		//3.查询审核是否结束 完成状态是否为已完成
		int row = empAttendBillMapper.selectAttendAuditIsEnd(attendBillId);
		Map<String, Object> endMap = new HashMap<String,Object>();
		if(row>0){
			endMap.put("isComplete", 1);
			rslist.add(endMap);
		}
		return rslist;
	}

	/** 
	 * @Description: 删除考勤单id
	 * @param: @param attendBillId
	 * @param: @param sysUser
	 * @param: @return
	 * @throws
	 */
	@Override
	public int removeMine(String attendBillId, SysUser sysUser) {
		int row = 0;
		//1.先删除考勤审核单
		row += empAttendAuditMapper.deleteEmpAttendAuditByAttendBillId(attendBillId,sysUser.getUserId());
		
		//2.删除出考勤单
		EmpAttendBillExample example = new EmpAttendBillExample();
		example.createCriteria().andUserIdEqualTo(sysUser.getUserId()).andAttendBillIdEqualTo(attendBillId);
		row += empAttendBillMapper.deleteByExample(example);
		
		//3.删除考勤日项
		row += empAttendBillLeavedayItemsMapper.deleteEmpAttendBillLeavedayItemsByAttendBillId(attendBillId);
		return row;
	}

	/** 
	 * @Description: 根据考勤单id查询详情
	 * @param: @param attendBillId
	 * @param: @return
	 * @throws
	 */
	@Override
	public EmpAttendBill selectAttendBillById(String attendBillId) {
		return empAttendBillMapper.selectAttendBillById(attendBillId);
	}

	/** 
	 * @Description: 查看考勤审核单详情 只能查看与自己相关的 管理员除外
	 * @param: @param attendAuditId
	 * @param: @param sysUser
	 * @param: @return
	 * @throws
	 */
	@Override
	public EmpAttendAudit selectAttendAuditByAuditIdAndRelationMine(String attendAuditId, SysUser sysUser) {
		if("admin".equals(sysUser.getLoginName())){
			return empAttendAuditMapper.selectEmpAttendAuditById(attendAuditId);
		}
		return empAttendAuditMapper.selectAttendAuditByAuditIdAndRelationMine(attendAuditId, sysUser.getUserId());
	}

}
