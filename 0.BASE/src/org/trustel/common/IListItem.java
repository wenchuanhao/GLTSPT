package org.trustel.common;

/**
 * 类 名：下拉列表接口
 * 
 * 日 期：2007-03-17
 * 
 * 设 计：万志勇
 * 
 * 版 本：0.0.0.1
 * 
 */
public interface IListItem {
	/**
	 * 
	 * @return 列表项编码(如下拉列表中显示信息所对应的值)
	 */
	public String getCode();

	/**
	 * 
	 * @return 列表项名称或标题(如下拉列表中显示信息）
	 */
	public String getTitle();

	/**
	 * 
	 * @return 提示信息
	 */
	public String getTip();
}
