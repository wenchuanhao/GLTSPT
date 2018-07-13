package org.trustel.common;

/**
 * 
 * 类 名：消息资源信息
 * 
 * 版 本：0.0.0.1
 * 
 * 设 计：万志勇
 * 
 * 日 期：2011-03-30
 * 
 * 数据源：system_resources(消息资源信息)
 * 
 * 描 述：标签名称/提示等
 * 
 */

public class Message {
	/**
	 * 消息编码
	 */
	private String code;

	/**
	 * 消息名称
	 */
	private String name;

	/**
	 * 语言
	 */
	private String lang;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 
	 * @return 消息编码(code<--code)
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 
	 * @return 消息名称(name<--name)
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return 语言(lang<--lang)
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * 
	 * @return 描述(description<--description)
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置消息编码
	 * 
	 * @param 消息编码(code-->code)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 设置消息名称
	 * 
	 * @param 消息名称(name-->name)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设置语言
	 * 
	 * @param 语言(lang-->lang)
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * 设置描述
	 * 
	 * @param 描述(description-->description)
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
