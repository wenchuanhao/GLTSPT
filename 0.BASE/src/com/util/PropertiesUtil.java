package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Properties工具，可以对指定的Properties文件进行读写操作
 * @author WEIFEI
 * @date 2016-6-5 下午5:06:33
 */
public class PropertiesUtil {
	
	private Properties props = null;
	private File configFile = null;
	
	/**
	 * 构造函数
	 * @param fileName 文件名
	 */
	public PropertiesUtil(String fileName) {
		
		URL url = PropertiesUtil.class.getClassLoader().getResource(fileName);

		configFile = new File(url.getFile());
		try {
			if(!configFile.exists())
				configFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		props = new Properties();
		load();
	}

	/**
	 * 加载属性文件
	 */
	private void load() {
		try {
			props.load(new FileInputStream(configFile));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 读取属性
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return props.getProperty(key);
	}
	
	/**
	 * 修改属性
	 * @param key
	 * @param value
	 */
	public void setProperty(String key,String value){
		props.setProperty(key, value);
		try {
			OutputStream out = new FileOutputStream(configFile);
			props.store(out, null);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
