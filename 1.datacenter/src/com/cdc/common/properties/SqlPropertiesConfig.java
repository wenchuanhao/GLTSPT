package com.cdc.common.properties;

import com.cdc.util.DesUtil;
import com.util.PropertiesUtil;

/**
 * 获取、修改sql.properties文件值
 * 
 * @author WEIFEI
 * @date 2016-6-5 下午5:09:13
 */
public class SqlPropertiesConfig {
	
	public SqlPropertiesConfig(String configFileName){
		fileName = configFileName;
	}
	
	/**
	 * properties文件名
	 */
	private static String fileName = null;
	
	/**
	 * 获取属性值
	 * 
	 * @date 2016-6-5 下午5:15:28
	 * @param key
	 * @return String
	 */
	public String getProperty(String key) {
		return new PropertiesUtil(fileName).getProperty(key);
	}
	/**
	 * 获取des加密属性值
	 * 
	 * @date 2016-6-5 下午5:15:28
	 * @param key
	 * @return String
	 */
	public String getDesProperty(String key) {
		String value = new PropertiesUtil(fileName).getProperty(key);
		DesUtil des = new DesUtil();
		try {
			//解密
			return des.decrypt(value,DesUtil.DES_KEY_STRING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	
	/**
	 * 修改属性值
	 * 
	 * @date 2016-6-5 下午5:15:28
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value) {
		new PropertiesUtil(fileName).setProperty(key, value);
	}
}
