package org.trustel.common;

public class Text {

	/**
	 * 语言
	 */
	private String lang = "zh_CN";

	/**
	 * 描述信息
	 */
	private String description;

	/**
	 * 
	 * @return 语言(lang<--lang)
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * 
	 * @return 描述信息(description<--description)
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置语言
	 * 
	 * @param 语言
	 *            (lang-->lang)
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * 设置描述信息
	 * 
	 * @param 描述信息
	 *            (description-->description)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
