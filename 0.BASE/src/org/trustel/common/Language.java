package org.trustel.common;

/**
 * 
 * 类 名：语言定义
 * 
 * 版 本：0.0.0.1
 * 
 * 设 计：万志勇
 * 
 * 日 期：2011-03-30
 * 
 * 数据源：system_languages(语言定义)
 * 
 * 描 述：
 * 
 */
public class Language implements IListItem {

	public final static String LANG_DEFAULT = "zh_CN";

	/**
	 * 编码
	 */
	private String code = "zh_CN";

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 代码页 (映射code_page字段)
	 */
	private int codePage = 0;

	/**
	 * 
	 * @return 编码(code<--code)
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 
	 * @return 名称(name<--name)
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return 代码页(codePage<--code_page)
	 */
	public int getCodePage() {
		return codePage;
	}

	/**
	 * 设置编码
	 * 
	 * @param 编码(code-->code)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 设置名称
	 * 
	 * @param 名称(name-->name)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设置代码页
	 * 
	 * @param 代码页(codePage-->code_page)
	 */
	public void setCodePage(int codePage) {
		this.codePage = codePage;
	}

	public String getTitle() {
		return getName();
	}

	public String getTip() {
		return null;
	}

}
