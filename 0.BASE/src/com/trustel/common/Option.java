/*
 * 创建日期 2005-6-29
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.trustel.common;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * 公共参数类
 */
public class Option implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 参数类型
	 */
	public String type;
	/**
	 * 参数编码
	 */
	public String code;
	/**
	 * 参数描述
	 */
	public String description;
	/**
	 * 参数值
	 */
	public String value;
	
	public Option() {
		
	}
	
	public Option(String type, String code, String value, String description) {
		this.type = type;
		this.code = code;
		this.value = value;
		this.description = description;
	}
}
