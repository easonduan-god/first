package com.numberone.quartz.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.numberone.quartz.service.ISysJobService;

/**
 * OA定时任务
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年1月29日 下午7:11:24
 * @param:
 */
@Component("OATask")
public class OATask {
	@Autowired
	private ISysJobService sysJobService;
	
	/**
	 * 每日定时更新员工考勤信息
	 * @param:  参数说明
	 * @return: void 返回类型
	 * @throws
	 */
	
	public void updateAttendInfo(){
		
		sysJobService.updateAttendInfo();
	}
}
