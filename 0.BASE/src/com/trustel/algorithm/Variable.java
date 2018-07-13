package com.trustel.algorithm;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * 变量
 */
public class Variable implements Serializable {
	/**
	 * 变量名
	 */
	public String key;
	/**
	 * 变量类型
	 */
	public String type;
	/**
	 * 变量值
	 */
	public Object value;
}
