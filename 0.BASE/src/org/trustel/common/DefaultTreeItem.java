package org.trustel.common;

/**
 * 
 * 类 名：目录树
 * 
 * 版 本：0.0.0.1
 * 
 * 设 计：万志勇
 * 
 * 日 期：2011-04
 * 
 * 数据源：
 * 
 * 描 述：
 * 
 */

public class DefaultTreeItem extends DefaultListItem implements ITreeItem {

	private String ucode;

	public int childrenCount = 0;

	public DefaultTreeItem(String code, String title, String ucode) {
		super(code, title);
		this.ucode = ucode;
	}

	public DefaultTreeItem(String code, String title, String ucode,
			int childrenCount) {
		super(code, title);
		this.ucode = ucode;
		this.childrenCount = childrenCount;
	}

	public String getUcode() {
		return ucode;
	}

	public int getChildrenCount() {
		return childrenCount;
	}

}
