package org.trustel.system;

import java.util.Random;

/**
 * 电子合同 系统常量定义
 * 
 * @author sunsf
 * 
 */
public class RMPBConstantDefine
{
	/**
	 * 路径分隔符 (会自动识别系统)
	 */
	public static String FILE_SEPARATOR = System.getProperty("file.separator");


	/**
	 * 生成随机密码
	 * 
	 * @param passLenth
	 *            生成的密码长度
	 * @return 随机密码
	 */
	public static String getPass(int passLenth)
	{

		StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < passLenth; i++)
		{
			// 生成指定范围类的随机数0—字符串长度(包括0、不包括字符串长度)
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}
}
