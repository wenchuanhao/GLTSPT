package org.trustel.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.trustel.common.DefaultListItem;
import org.trustel.common.IListItem;
import org.trustel.common.Utils;

/**
 * 
 * @author 万志勇<br>
 *         字母 日期或时间元素 表示 示例 <br>
 *         G Era 标志符 Text AD <br>
 *         y 年 Year 1996; 96 M 年中的月份 Month July; Jul; 07 <br>
 *         w 年中的周数 Number 27 <br>
 *         W 月份中的周数 Number 2 <br>
 *         D 年中的天数 Number 189 <br>
 *         d 月份中的天数 Number 10 <br>
 *         F 月份中的星期 Number 2 E 星期中的天数 Text Tuesday; Tue <br>
 *         a Am/pm 标记 Text PM <br>
 *         H 一天中的小时数（0-23） Number 0 <br>
 *         k 一天中的小时数（1-24） Number 24 <br>
 *         K am/pm 中的小时数（0-11） Number 0 <br>
 *         h am/pm 中的小时数（1-12） Number 12 <br>
 *         m 小时中的分钟数 Number 30 <br>
 *         s 分钟中的秒数 Number 55 <br>
 *         S 毫秒数 Number 978 <br>
 *         z 时区 General time zone Pacific Standard Time; PST; GMT-08:00 <br>
 *         Z 时区 RFC 822 time zone -0800 <br>
 * 
 * 
 */
public class DateUtils {
	public final static int TIME_MONTH = Calendar.MONTH;

	public final static int TIME_HOUROFDAY = Calendar.HOUR_OF_DAY;

	public final static int TIME_DAYOFMONTH = Calendar.DAY_OF_MONTH;
	public final static int TIME_DAYOFYEAR = Calendar.DAY_OF_YEAR;

	public final static int TIME_MINUTE = Calendar.MINUTE;

	public final static int TIME_SECOND = Calendar.SECOND;
	public final static int TIME_YEAR = Calendar.YEAR;

	public final static int TIME_DAYOFWEEK = Calendar.DAY_OF_WEEK;

	/**
	 * 日期运算
	 * 
	 * @param date
	 *            源
	 * @param part
	 *            操作部份
	 * @param value
	 *            改变值
	 * @return 计算后的日期
	 */
	public static Date add(Date date, int part, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(part, value);
		return calendar.getTime();
	}

	/**
	 * 取两个日期的差值
	 * 
	 * @param from
	 *            开始时间
	 * @param to
	 *            结束时间
	 * @param part
	 *            Time_Minute--相关多少分,Time_HourOfDay-时，other-天
	 * @return 差值
	 */
	public static long diff(Date from, Date to, int part) {
		if (to == null || from == null)
			return 0;
		long d = to.getTime() - from.getTime();
		switch (part) {
		case TIME_MINUTE:
			return d / 1000 / 60;
		case TIME_HOUROFDAY:
			return d / 1000 / 60 / 60;
		default:
			return d / 1000 / 60 / 60 / 24;
		}

	}

	/**
	 * 日期格式化函数
	 * 
	 * @param date
	 * @param format
	 * @param timeZone
	 *            时区如东八区GMT+8
	 * @return
	 */
	public static String format(Date date, String format, String timeZone) {

		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if (timeZone != null && !timeZone.trim().equals(""))
			formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		return formatter.format(date);
	}

	/**
	 * 获取日期的部份数据
	 * 
	 * @param date
	 * @param part
	 * @param timeZone
	 *            时区如GMT+8
	 * @return
	 */
	public static int getDatePart(Date date, int part, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (timeZone != null && !timeZone.trim().equals(""))
			calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		int ret = calendar.get(part);
		if (part == TIME_MONTH)
			ret++;
		return ret;

	}

	/**
	 * 时区转换
	 * 
	 * @param date
	 * @param locate
	 * @return
	 */
	public static Date get(Date date, String locate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (locate != null && !locate.trim().equals(""))
			calendar.setTimeZone(TimeZone.getTimeZone(locate));
		return calendar.getTime();
	}

	/**
	 * 取出时间月份的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date, String timeZone) {
		Calendar cal = new GregorianCalendar(getYear(new Date(), timeZone),
				getMonth(new Date(), timeZone), 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	}

	/**
	 * 天数
	 * 
	 * @return List/DefaultListItem/IListItem
	 */
	public static List<IListItem> getDays(Date date, String timeZone) {
		List<IListItem> list = new ArrayList<IListItem>();

		for (int i = 1; i <= Utils.getDay(date, timeZone); i++) {
			list.add(new DefaultListItem(i));
		}
		return list;
	}

	/**
	 * 取出时间中的小时
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {

		return getTimePart(date, Calendar.HOUR_OF_DAY, "");

	}

	/**
	 * 小时
	 * 
	 * @return List/DefaultListItem/IListItem
	 */
	public static List<IListItem> getHours() {
		List<IListItem> list = new ArrayList<IListItem>();
		for (int i = 1; i <= 24; i++) {
			list.add(new DefaultListItem(i));
		}
		return list;
	}

	/**
	 * 取出时间中的分钟
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {

		return getTimePart(date, Calendar.MINUTE, "");

	}

	/**
	 * 分钟
	 * 
	 * @return List/DefaultListItem/IListItem
	 */
	public static List<IListItem> getMinutes() {
		List<IListItem> list = new ArrayList<IListItem>();
		for (int i = 1; i <= 60; i++) {
			list.add(new DefaultListItem(i));
		}
		return list;
	}

	/**
	 * 取出时间中的月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date, String timeZone) {

		return getTimePart(date, Calendar.MONTH, timeZone);

	}

	/**
	 * 月数
	 * 
	 * @return List/DefaultListItem/IListItem
	 */
	public static List<IListItem> getMonths() {
		List<IListItem> list = new ArrayList<IListItem>();
		for (int i = 1; i <= 12; i++) {
			list.add(new DefaultListItem(i));
		}
		return list;
	}

	/**
	 * 获取日期的部份数据
	 * 
	 * @param date
	 * @param part
	 * @param timeZone
	 *            时区如GMT+8
	 * @return
	 */
	public static int getTimePart(Date date, int part, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (timeZone != null && !timeZone.trim().equals(""))
			calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		return calendar.get(part);

	}

	/**
	 * 取当前日期
	 * 
	 * @param includeHours
	 *            是否包括时分
	 * @param zone
	 *            时区
	 * @return Date
	 */
	public static Date getToday(boolean includeHours, String zone) {
		if (includeHours)
			return get(new Date(), zone);
		else
			return valueOf(format(new Date(), "yyyy-MM-dd", zone), "");
	}

	/**
	 * 取出时间中的年份数据
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date, String timeZone) {

		return getTimePart(date, Calendar.YEAR, timeZone);

	}

	/**
	 * 字符转换为日期。
	 * 
	 * @param source
	 * @param patterns日期格式串如yyyy
	 *            -MM-dd HH:mm:ss
	 * @return
	 */
	public static Date stringToDate(String source, String patterns) {
		return stringToDate(source, patterns, true);
	}

	/**
	 * 字符转换为日期。
	 * 
	 * @param source
	 * @param patterns日期格式串如yyyy
	 *            -MM-dd HH:mm:ss
	 * @param locate
	 *            true--转化为东八区时间
	 * @return
	 */
	public static Date stringToDate(String source, String patterns,
			boolean locate) {
		if (locate)
			return stringToDate(source, patterns, "GMT+8");
		else
			return stringToDate(source, patterns, "");
	}

	/**
	 * 字符串转换为指定时区时间
	 * 
	 * @param source
	 * @param patterns
	 * @param timeZone如东八区GMT
	 *            +8
	 * @return
	 */
	public static Date stringToDate(String source, String patterns,
			String timeZone) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patterns);
		Date date = null;
		if (source == null)
			return date;
		if (timeZone != null && !timeZone.trim().equals(""))
			dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		try {
			date = dateFormat.parse(source);
		} catch (java.text.ParseException e) {
			System.out.println("[string to date]" + e.getMessage());
		}

		return date;
	}

	/**
	 * 字符转换为日期。
	 * 
	 * @param source
	 * @param patterns日期格式串如yyyy
	 *            -MM-dd HH:mm:ss
	 * @return
	 */
	public static Date valueOf(String source, String patterns) {
		return valueOf(source, patterns, "GMT+8");
	}

	/**
	 * 字符串转换为指定时区时间
	 * 
	 * @param value
	 * @param patterns
	 *            如yyyy-MM-dd HH:mm:ss
	 * @param timeZone如东八区GMT
	 *            +8
	 * @return
	 */
	public static Date valueOf(String value, String patterns, String timeZone) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patterns);
		Date date = null;
		if (value == null)
			return date;
		if (timeZone != null && !timeZone.trim().equals(""))
			dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		try {
			date = dateFormat.parse(value);
		} catch (java.text.ParseException e) {
			System.out.println("[string to date]" + e.getMessage());
		}

		return date;
	}

	/**
	 * 字符串转换为指定时区时间
	 * 
	 * 默认根据字串长度格式为yyyy-MM-dd或yyyy-MM-dd HH:mm
	 * 
	 * @param value
	 *            日期
	 * @param timeZone
	 *            timeZone如东八区GMT+8
	 * @return
	 */
	public static Date valueOfA(String value, String timeZone) {
		if (value == null || value.length() == 10)
			return valueOf(value, "yyyy-MM-dd", timeZone);
		else
			return Utils.valueOf(value, "yyyy-MM-dd HH:mm", timeZone);
	}
	
	
	/** 
     * 将Date类转换为XMLGregorianCalendar 
     * @param date 
     * @return  
     */  
    public static XMLGregorianCalendar dateToXmlDate(Date date){  
            Calendar cal = Calendar.getInstance();  
            cal.setTime(date);  
            DatatypeFactory dtf = null;  
             try {  
                dtf = DatatypeFactory.newInstance();  
            } catch (DatatypeConfigurationException e) {  
            }  
            XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();  
            dateType.setYear(cal.get(Calendar.YEAR));  
            //由于Calendar.MONTH取值范围为0~11,需要加1  
            dateType.setMonth(cal.get(Calendar.MONTH)+1);  
            dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));  
            dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));  
            dateType.setMinute(cal.get(Calendar.MINUTE));  
            dateType.setSecond(cal.get(Calendar.SECOND));  
            return dateType;  
        }   
  
    /** 
     * 将XMLGregorianCalendar转换为Date 
     * @param cal 
     * @return  
     */  
    public static Date xmlDate2Date(XMLGregorianCalendar cal){  
        return cal.toGregorianCalendar().getTime();  
    }  
}
