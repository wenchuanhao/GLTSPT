package com.trustel.algorithm;

import org.hibernate.Session;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 * 函数接口
 */
public interface Function {
	/**
	 * 无参数的函数方法
	 * 
	 * @param session 数据库连接
	 * @return
	 * @throws RuntimeException
	 */
	public Double calculate(Session session) throws RuntimeException;
	/**
	 * 带参数的函数方法
	 * 
	 * @param session 数据库连接
	 * @param args 参数
	 * @return
	 * @throws RuntimeException
	 */
	public Double calculate(Session session, String[] args) throws RuntimeException;
}
