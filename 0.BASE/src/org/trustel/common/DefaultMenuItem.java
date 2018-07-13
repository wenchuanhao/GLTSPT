package org.trustel.common;

/**
 * 
 * 类 名：系统菜单
 * 
 * 版 本：0.0.0.1
 * 
 * 设 计：万志勇
 * 
 * 日 期：2011-03-18
 * 
 * 数据源：一般情况下映射到菜单视图（如v_menus)
 * 
 * 描 述：
 * 
 */

public class DefaultMenuItem extends DefaultTreeItem implements IMenuItem {

	private String url;

	public DefaultMenuItem(String code, String title, String ucode, String url) {
		super(code, title, ucode);
		this.url = url;
	}

	public DefaultMenuItem(String code, String title, String ucode) {
		super(code, title, ucode);
		url = "";
	}

	public String getUrl() {
		return url;
	}

}
