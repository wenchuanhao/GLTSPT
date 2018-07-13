package org.trustel.common;

/**
 * 
 * 类 名：通用描述信息
 * 
 * 版 本：0.0.0.1
 * 
 * 设 计：万志勇
 * 
 * 日 期：2011-03-18
 * 
 * 数据源：system_descriptions(通用描述信息)
 * 
 * 描 述：本类用来支持国际化应用时涉及文本信息的国际化内容
 * 
 */

public class Description extends Text {
	/**
	 * 描述编码
	 */
	private String code;

	/**
	 * 关联信息编码
	 */
	private String ucode;

	/**
	 * 
	 * @return 描述编码(code<--code)
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 
	 * @return 关联信息编码(ucode<--ucode)
	 */
	public String getUcode() {
		return ucode;
	}

	/**
	 * 设置描述编码
	 * 
	 * @param 描述编码
	 *            (code-->code)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 设置关联信息编码
	 * 
	 * @param 关联信息编码
	 *            (ucode-->ucode)
	 */
	public void setUcode(String ucode) {
		this.ucode = ucode;
	}

}
