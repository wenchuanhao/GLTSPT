package org.trustel.common;

/**
 * 类 名：系统参数表
 * 
 * 日 期：2007-08-03
 * 
 * 设 计：万志勇
 * 
 * 版 本：0.0.0.1
 * 
 */
public class Parameter extends AbstractParameter {

	/**
	 * 参数描述
	 */
	private String description;

	/**
	 * 参数描述
	 */
	public String getDescription() {
		return description;
	}

	public String getTip() {
		return description;
	}

	/**
	 * 参数描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
