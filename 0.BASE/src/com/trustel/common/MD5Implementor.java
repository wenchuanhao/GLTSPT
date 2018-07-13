package com.trustel.common;

import java.security.MessageDigest;

/**
 * @author Administrator
 *
 * MD5加密算法实现类
 */
public class MD5Implementor {
	/**
	 * 生成MD5字符串
	 * 
	 * @param origin 原始字符串
	 * @return 加密字符串
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString =
				ByteFunc.getInstance().byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {

		}

		return resultString;
	}

	/**
	 * 生成MD5字节串
	 * @param origin 原始字节串
	 * @return 加密字节串
	 */
	public static byte[] MD5Encode(byte[] origin) {
		byte[] resultBytes = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultBytes = md.digest(origin);
		} catch (Exception ex) {

		}

		return resultBytes;
	}
}
