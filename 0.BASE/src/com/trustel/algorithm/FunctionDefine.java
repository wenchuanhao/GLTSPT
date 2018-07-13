package com.trustel.algorithm;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * 函数定义
 */
public class FunctionDefine implements Serializable {
	/**
	 * 函数名
	 */
	public String name;
	/**
	 * 函数实现类名
	 */
	public String className;
	/**
	 * 函数描述
	 */
	public String description;
	
	public FunctionDefine(String name, String className, String description) {
		this.name = name;
		this.className = className;
		this.description = description;
	}
}
