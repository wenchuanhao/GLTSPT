package org.trustel.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class StringUtils {
	
	public static String subfix = "...";

	public static String valueOf(String source, int maxlength) {
		return valueOf(source, "", maxlength);
	}

	public static String valueOf(String source) {
		return valueOf(source, "");
	}

	public static String valueOf(String source, String defaultValue) {
		return valueOf(source, defaultValue, 0);
	}

	public static String valueOf(String source, String defaultValue,
			int maxlength) {
		if (source == null || source.trim().equals(""))
			return defaultValue;

		if (maxlength < 1)
			return source.trim();
		source = source.trim();
		if (source.length() > maxlength + 1)
			source = source.substring(0, maxlength) + subfix;

		return source;
	}

	/**
	 * 将字符串转为HTML串，当源串为NULL或空时返回&nbsp;
	 * 
	 * @param source
	 * @return
	 */
	public static String null2HTML(String source) {
		return null2HTML(source, 0);

	}

	/**
	 * 当字串超过长度时截短显示，为NULL时显示一个空格
	 * 
	 * @param source
	 * @param len
	 * @return
	 */
	public static String null2HTML(String source, int len) {
		return valueOf(source, "&nbsp;", len).trim();
	}

	/**
	 * 当字串超过指定长度时截断
	 * 
	 * @param source
	 *            源串
	 * @param max
	 *            最大长度
	 * @return 限制长度串
	 */
	public static String null2Str(String source, int max) {
		if (source == null)
			return "";
		byte[] b = source.getBytes();
		if (b.length > max)
			source = new String(b, 0, max);
		return source;
	}

	/**
	 * 返回以0作前缀的指定长度的序列号
	 */
	public static String fixLength(String value, int fixLen) {
		return fixLength(value, fixLen, '0');
	}

	/**
	 * 返回指定名称和长度以及指定前缀的序列号，通常使用0作前缀
	 */
	public static String fixLength(String value, int fixLen, char fixChar) {
		String stmp = "";
		for (int i = 0; i < fixLen - value.length(); i++) {
			stmp = stmp + fixChar;
		}

		return stmp + value;
	}
	
	/**
	 * 工具方法：返回第二天
	 * @param dateTemp
	 * @return
	 */
	public static String getNextDay(String dateTemp, String pattern){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = dateFormat.parse(dateTemp);
		} catch (Exception e) {
           e.printStackTrace();
		}    	
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return dateStr;
	}

	/**
	 * 判断是否为空start
	 */
	public static boolean isNotBlank(String str){
		return (str != null && !"".equals(str.trim()));
	}
	
	public static boolean isBlank(String str){
		return (str == null || "".equals(str.trim()));
	}
	/**
	 * 判断是否为空end
	 */
}
