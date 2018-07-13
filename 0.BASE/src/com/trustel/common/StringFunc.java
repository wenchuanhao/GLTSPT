package com.trustel.common;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Administrator
 * 
 * 字符串处理类
 */
public class StringFunc {
	/**
	 * 逗号分割符
	 */
	public final static String COMMA_SEPARATOR = ",";

	/**
	 * 分号分隔符
	 */
	public final static String SEMICOLON_SEPARATOR = ";";

	/**
	 * 正斜杠分隔符
	 */
	public final static String SLASH_SEPARATOR = "/";

	/**
	 * 反斜杠分隔符
	 */
	public final static String BACKLASH_SEPARATOR = "\\";

	/**
	 * 减号分隔符
	 */
	public final static String SUBTRACTION_SEPARATOR = "-";

	/**
	 * 下横线分隔符
	 */
	public final static String UNDERLINE_SEPARATOR = "_";

	/**
	 * 与分隔符
	 */
	public final static String AND_SEPARATOR = "&";

	/**
	 * 等号分隔符
	 */
	public final static String EQUAL_SEPARATOR = "=";

	/**
	 * ＃号分隔符
	 */
	public final static String WELL_SEPARATOR = "#";

	public final static String NBSP = "&nbsp";

	/**
	 * 空格
	 */
	public final static String SPACE = "";

	/**
	 * 竖线
	 */
	public final static String ERECT_SEPARATOR = "|";

	/**
	 * 将以分隔符分隔的字符串转换为字符串数组
	 * 
	 * @param exp
	 *            待分割字符串
	 * @param separator
	 *            分隔符
	 * @return 分割后字符串数组
	 */
	public static String[] separate(String exp, String separator) {
		Vector array = new Vector();
		String[] strings = null;

		while (exp.indexOf(separator) != -1) {
			String part = exp.substring(0, exp.indexOf(separator));
			array.add(part);
			exp = exp.substring(exp.indexOf(separator) + 1);
		}

		if (exp != null && exp.length() > 0) {
			array.add(exp);
		}

		strings = new String[array.size()];
		for (int i = 0; i < array.size(); i++) {
			strings[i] = (String) array.elementAt(i);
		}

		return strings;
	}

	/**
	 * 将以分隔符分隔的字符串转换为字符串数组(最后如果是也空格作为一部分)
	 * 
	 * @param exp
	 *            待分割字符串
	 * @param separator
	 *            分隔符
	 * @return 分割后字符串数组
	 */
	public static String[] split(String exp, String separator) {
		Vector array = new Vector();
		String[] strings = null;

		while (exp.indexOf(separator) != -1) {
			String part = exp.substring(0, exp.indexOf(separator));
			array.add(part);
			exp = exp.substring(exp.indexOf(separator) + 1);
		}

		array.add(exp == null ? "" : exp);

		strings = new String[array.size()];
		for (int i = 0; i < array.size(); i++) {
			strings[i] = (String) array.elementAt(i);
		}

		return strings;
	}
	
	/**
	 * 将字符串分割为List
	 * 
	 * @param exp
	 *            字符串
	 * @param separator
	 *            分割符
	 * @return
	 */
	public static java.util.List splitToList(String exp, String separator) {
		java.util.List list = new java.util.ArrayList();

		if (exp != null) {
			while (exp.indexOf(separator) != -1) {
				String part = exp.substring(0, exp.indexOf(separator));
				list.add(part);
				exp = exp.substring(exp.indexOf(separator) + 1);
			}

			if (exp != null && exp.length() > 0) {
				list.add(exp);
			}
		}

		return list;
	}

	/**
	 * 将以分隔符分隔的字符串转换为字符串数组
	 * 
	 * @param exp
	 *            待分割字符串
	 * @param separator
	 *            分隔符
	 * @return 分割后字符串数组
	 */
	public static String[] separate(String exp, char separator) {
		Vector array = new Vector();
		String[] strings = null;

		while (exp.indexOf(separator) != -1) {
			String part = exp.substring(0, exp.indexOf(separator));
			array.add(part);
			exp = exp.substring(exp.indexOf(separator) + 1);
		}

		if (exp != null && exp.length() > 0) {
			array.add(exp);
		}

		strings = new String[array.size()];
		for (int i = 0; i < array.size(); i++) {
			strings[i] = (String) array.elementAt(i);
		}

		return strings;
	}

	/**
	 * 将字符串数组转换为以分隔符分隔的字符串
	 * <p>
	 * 转换过程中将去掉输入字符串数组中的重复内容
	 * 
	 * @param exps
	 *            字符串数组
	 * @param separator
	 *            分隔符
	 * @return 合并后的字符串
	 */
	public static String union(String[] items, String separator) {
		String exp = null;

		if (items != null) {
			if (items.length == 1)
				return items[0];

			Hashtable distinctItems = new Hashtable();

			// remove duplicate string
			for (int i = 0; i < items.length; i++) {
				distinctItems.put(items[i], items[i]);
			}

			exp = union(distinctItems, separator);
		}

		return exp;
	}

	/**
	 * 将字符串数组转换为以分隔符分隔的字符串
	 * <p>
	 * 转换过程中将去掉输入字符串数组中的重复内容
	 * 
	 * @param exps
	 *            字符串数组
	 * @param separator
	 *            分隔符
	 * @return 合并后的字符串
	 */
	public static String union(java.util.List items, String separator) {
		String exp = null;

		if (items != null) {
			if (items.size() == 1)
				return (String) items.get(0);

			Hashtable distinctItems = new Hashtable();

			// remove duplicate string
			for (int i = 0; i < items.size(); i++) {
				distinctItems.put(items.get(i), items.get(i));
			}

			exp = union(distinctItems, separator);
		}

		return exp;
	}

	/**
	 * 将字符串数组转换为以分隔符分隔的字符串
	 * <p>
	 * 转换过程中将去掉输入字符串数组中的重复内容
	 * 
	 * @param exps
	 *            字符串数组
	 * @param separator
	 *            分隔符
	 * @return 合并后的字符串
	 */
	public static String union(Hashtable items, String separator) {
		StringBuffer buf = new StringBuffer();

		if (items != null) {
			Enumeration en = items.elements();
			while (en.hasMoreElements()) {
				buf.append(en.nextElement() + separator);
			}

			if (buf.length() > 0)
				buf.deleteCharAt(buf.length() - 1); // remove the end separator
		}

		return buf.toString();
	}

	/**
	 * 替换空串为'&nbsp'
	 * 
	 * @param source
	 *            源字符串
	 * @return
	 */
	public static String nullMarkup(String source) {
		return nullMarkup(source, NBSP);
	}

	/**
	 * 替换空串
	 * 
	 * @param source
	 *            源字符串
	 * @param markup
	 *            替换字符串
	 * @return
	 */
	public static String nullMarkup(String source, String markup) {
		return (source == null || source.length() == 0) ? markup : source;
	}
}
