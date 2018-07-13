package com.trustel.common;
/**
 * @version 1.0
 * @author Liu Gang
 * 
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.Timestamp;
public class DateUtil {
	/**
	 * 将字符串转换为时间 yyyy-MM-dd
	 * @param s
	 * @return
	 */
	public static Date parseToDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy-MM-dd");
        Date date = null;
        if(s==null||s.length()<1)
        {
            return null;
        }
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
	/**
	 * 将字符串转换为时间类型 yyyyMMddHHmmss
	 * @param s
	 * @return
	 */
	public static Date parseToDate1(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyyMMddHHmmss");
        Date date = null;
        if(s==null||s.length()<1)
        {
            return null;
        }
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
	/**
	 * 将字符串转换为时间类型 yyyyMMddHHmmss
	 * @param s
	 * @return
	 */
	public static Date parseToDate5(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        if(s==null||s.length()<1)
        {
            return null;
        }
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
	/**
	 * 将字符串转换为时间类型 yyyyMMddHHmmss
	 * @param s
	 * @return
	 */
	public static Date parseToDate6(String s) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm");
		Date date = null;
		if(s==null||s.length()<1)
		{
			return null;
		}
		try {
			date = simpleDateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 将字符串转换为时间类型 yyyy/MM/dd
	 * @param s
	 * @return
	 */
	public static Date parseToDate2(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy/MM/dd");
        Date date = null;
        if(s==null||s.length()<10)
        {
            return null;
        }
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
	/**
	 * 将字符串转换为时间类型 yyyyMMdd
	 * @param s
	 * @return
	 */
	public static Date parseToDate3(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyyMMdd");
        Date date = null;
        if(s==null||s.length()<8)
        {
            return null;
        }
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
	/**
	 * 将时间转换为字符串 yyyy/MM/dd
	 * @param date
	 * @return
	 */
    public static String parseToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy/MM/dd");
        String str=null;
        if(date==null)
        {
            return null;
        }
        str=simpleDateFormat.format(date);
        return str;
    }
    /**
	 * 将时间转换为字符串 yyyy-MM-dd
	 * @param date格式为
	 * @return
	 */
    public static String timeToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy-MM-dd");
        String str=null;
        if(date==null)
            return null;
        str=simpleDateFormat.format(date);
        return str;
    }
    
	/**
	 * 方法说明：取当前日期(格式 20030801)
	 * 
	 */
	public static String getStrNowDate() {
		java.util.Date tdate = new java.util.Date();
		String nowtime = new Timestamp(tdate.getTime()).toString();
		nowtime = nowtime.substring(0, 10);
		String nowYear = nowtime.substring(0, 4);
		String nowMonth = nowtime.substring(5, 7);
		String nowDay = nowtime.substring(8, 10);
		String nowdate = nowYear + nowMonth + nowDay;
		return nowdate;

	}
	
}