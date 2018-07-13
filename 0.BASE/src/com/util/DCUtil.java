package com.util;


/**
 * 获取、修改dc.properties文件值
 * 
 * @author WEIFEI
 * @date 2016-6-5 下午5:09:13
 */
public class DCUtil {
	
	/**
	 * properties文件名
	 */
	private static String fileName = "dc.properties";
	
	/**
	 * 获取属性值
	 * 
	 * @date 2016-6-5 下午5:15:28
	 * @param key
	 * @return String
	 */
	public static String getProperty(String key) {
		return new PropertiesUtil(fileName).getProperty(key);
	}

	/**
	 * 修改属性值
	 * 
	 * @date 2016-6-5 下午5:15:28
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		new PropertiesUtil(fileName).setProperty(key, value);
	}
}
