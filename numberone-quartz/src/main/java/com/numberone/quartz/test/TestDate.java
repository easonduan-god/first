package com.numberone.quartz.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.numberone.common.utils.DateUtils;
public class TestDate {
	public void test1(){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(DateUtils.getNowDate());
		calendar.add(Calendar.DATE, -1);
		System.out.println(calendar.getTime());
	}
	public void test2() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.YYYY_MM_DD);
		Date date1 = format.parse(format.format(new Date()));
		System.out.println(date1);
		Date date = DateUtils.setSeconds(DateUtils.setMinutes(DateUtils.setHours(new Date(), 0),0),0);
		System.out.println(date);
	}
	public static void main(String[] args) throws ParseException {
	}
}
