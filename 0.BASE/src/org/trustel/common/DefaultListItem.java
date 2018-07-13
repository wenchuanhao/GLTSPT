package org.trustel.common;

/**
 * 
 * 类 名：缺省的下列表项
 * 
 * 版 本：0.0.0.1
 * 
 * 设 计：万志勇
 * 
 * 日 期：2011-04
 * 
 * 数据源：该类型不需要映射，辅助完成下拉列表等业务功能
 * 
 * 描 述：
 * 
 */

public class DefaultListItem implements IListItem {
	private String code;

	private String title;

	private String tip;

	/**
	 * 通过编码构建下拉列表中的项（假设显示信息即值）
	 */
	public DefaultListItem(int code) {
		this.code = String.valueOf(code);
		this.title = this.code;
	}

	/**
	 * 通过编码及标题构建下列表（最常用）[假设显示信息和对应值不同。如男性用M表示]
	 */
	public DefaultListItem(String code, String title) {
		this.code = code;
		this.title = title;
	}

	public DefaultListItem(String code, String title, String tip) {
		this.code = code;
		this.title = title;
		this.tip = tip;
	}

	public String getCode() {
		return code;
	}

	public String getTip() {
		return tip;
	}

	public String getTitle() {
		return title;
	}
}