package com.trustel.common;

import java.io.UnsupportedEncodingException;

/**
 * @author Administrator
 *
 * 字符编码转换类
 */
public class CharsetConverter {
	/**
	 * 将GBK转换为Unicode
	 * 
	 * @param source 输入字符串
	 * @return 输出字符串
	 */
	public static String fromGBKtoUnicode(String source) {	
		return transcode(source, "GBK", "ISO-8859-1");
	}
	
	/**
	 * 将Unicode转换为GBK
	 * 
	 * @param source 输入字符串
	 * @return 输出字符串
	 */
	public static String fromUnicodetoGBK(String source) {	
		return transcode(source, "ISO-8859-1", "GBK");
	}
	
	/**
	 * 将UTF-8转换为Unicode
	 * 
	 * @param source 输入字符串
	 * @return 输出字符串
	 */
	public static String fromUTF8toUnicode(String source) {		
		return transcode(source, "UTF-8", "ISO-8859-1");
	}
	
	/**
	 * 将Unicode转换为UTF-8
	 * 
	 * @param source 输入字符串
	 * @return 输出字符串
	 */
	public static String fromUnicodetoUTF8(String source) {		
		return transcode(source, "ISO-8859-1", "UTF-8");
	}
	
	/**
	 * 转换字符集
	 * 
	 * @param source 输入字符串
	 * @param fromCharSet 源字符集
	 * @param toCharSet 目的字符集
	 * @return 输出字符串
	 */
	public static String transcode(String source, String fromCharSet, String toCharSet) {
		String dest = null;
		try {
			dest = new String(source.getBytes(fromCharSet), toCharSet);
		} catch (UnsupportedEncodingException e) {
			dest = e.getMessage();
		}
		
		return dest;
	}
}
