package com.cdc.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.trustel.util.DateUtils;

public class DateUtil {

	/**
	 * 根据指定时间区间得出环比或同比的其他时间区间
	 * @param startTime 指定开始时间
	 * @param endTime 指定结束时间
	 * @param type 环比=1，同比=2
	 * @param num 时间区间个数
	 * @return 返回时间区间list
	 */
	public static List<String> getTimePeriods(String startTime, String endTime, int type, int num){
		List<String> list = new ArrayList<String>();
		list.add(startTime + "-" + endTime);
		
		/*
		 * 环比：
		 * 	2016-02~2016-04
		 * 	2015-11~2016-01
		 * 	2015-08~2015-10
		 * 	2015-05~2015-07
		 * 	2015-02~2015-04
		 */
		if(1 == type){//环比
			/*
			 * 1、先计算startTiem和endTime相隔x个月
			 * 2、循环num-1次
			 * 		endTime = startTime - 1
			 * 		startTime = endTime - x
			 * 3、添加到list中
			 */
			Date date1 = DateUtils.valueOf(startTime, "yyyyMM");
			Date date2 = DateUtils.valueOf(endTime, "yyyyMM");
			int delta = getMonthDiff(date1, date2);
			for(int i = 0; i < num-1; i++){
				date2 = DateUtils.add(date1, DateUtils.TIME_MONTH, -1);
				date1 = DateUtils.add(date2, DateUtils.TIME_MONTH, -delta);
				list.add(DateUtils.format(date1, "yyyyMM", null) + "-"
						+ DateUtils.format(date2, "yyyyMM", null));
			}
		} else if(2 == type){//同比
			/*
			 * 同比：
			 * 	2015-02~2016-04
			 * 	2013-02~2014-04
			 * 	2011-02~2012-04
			 * 	2009-02~2010-04
			 * 	2007-02~2008-04
			 * 
			 * 1、先计算startTime和endTime相隔x个年
			 * 2、循环num-1
			 * 		startTime = startTime - (x+1)年
			 * 		endTime = endTime - (x+1)年
			 * 3、添加到list中
			 */
			Date date1 = DateUtils.valueOf(startTime, "yyyyMM");
			Date date2 = DateUtils.valueOf(endTime, "yyyyMM");
			int delta = DateUtils.getYear(date2, null) - DateUtils.getYear(date1, null);
			for(int i = 0; i < num-1; i++){
				date1 = DateUtils.add(date1, DateUtils.TIME_YEAR, -delta-1);
				date2 = DateUtils.add(date2, DateUtils.TIME_YEAR, -delta-1);
				list.add(DateUtils.format(date1, "yyyyMM", null) + "-"
						+ DateUtils.format(date2, "yyyyMM", null));
			}
		}
		return list;
	}
	
	/**
	 * 计算两个日期相差月数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getMonthDiff(Date date1, Date date2){
		if(date1 == null || date2 == null){
			return -1;
		}
		
		if(date1.getTime() > date2.getTime()){
			Date temp = date1;
			date1 = date2;
			date2 = temp;
		}
		
		return 12 * (DateUtils.getYear(date2, null) - DateUtils.getYear(date1, null)) +
				(DateUtils.getMonth(date2, null) - DateUtils.getMonth(date1, null));
	}
	
	public static void main(String[] args) {
		List<String> list = getTimePeriods("201602", "201604", 2, 5);
		for(String date : list){
			System.out.println(date);
		}
	}
}
