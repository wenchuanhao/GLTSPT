package org.trustel.util;

import java.util.List;

import org.trustel.common.ITreeItem;


/**
 * 类 名：树处理类
 * 
 * 日 期：2007-03-20
 * 
 * 设 计：万志勇
 * 
 * 版 本：0.0.0.1
 */

public class OptionTreeUtils {
	private StringBuffer buf = new StringBuffer();

	protected String defaultValue;

	protected boolean allowSelectTop;

	protected List<?> data;

	public OptionTreeUtils(List<?> list, String defaultValue, final boolean allowSelectTop) {
		if (defaultValue == null)
			defaultValue = "";
		this.defaultValue = defaultValue;
		this.allowSelectTop = allowSelectTop;
		this.data = list;
	}

	private boolean isTop(ITreeItem item, List<?> list) {
		for (int i = 0; i < list.size(); i++) {
			ITreeItem tmp = (ITreeItem) list.get(i);
			if (item.getUcode().equals(tmp.getCode()))
				return false;
		}
		return true;
	}

	protected String getOptionValue(ITreeItem item, boolean allowSelectTop,
			boolean isTop) {
		String code = item.getCode();
		if (!allowSelectTop && isTop) {
			code = "";
		}
		return code;
	}

	protected String getTreeItem(ITreeItem item, int level, boolean isTop) {
		String title = item.getTitle();
		for (int i = 0; i < level; i++)
			title = "&nbsp;&nbsp;" + title;

		String tmp = "<option value='"
				+ getOptionValue(item, allowSelectTop, isTop) + "'";
		if (item.getCode().equals(defaultValue))
			tmp = tmp + " selected";
		return tmp + ">" + title + "</option>\n";
	}

	private void addTreeItem(ITreeItem item, int level, boolean isTop) {
		buf.append(getTreeItem(item, level, isTop));
	}

	private void OutChildren(ITreeItem item, List<?> list, int level) {
		int l = level + 1;
		for (int i = 0; i < list.size(); i++) {
			ITreeItem tmp = (ITreeItem) list.get(i);
			if (tmp.getUcode().equals(item.getCode())) {
				addTreeItem(tmp, level, false);
				OutChildren(tmp, list, l);
				buf.append(afterOutItem(tmp, level, false));
			}
		}
	}

	protected String afterOutItem(ITreeItem item, int level, boolean isTop) {
		return "";
	}

	public String getTree() {

		if (data != null)
			for (int i = 0; i < data.size(); i++) {
				ITreeItem item = (ITreeItem) data.get(i);
				if (isTop(item, data)) {
					addTreeItem(item, 0, true);
					OutChildren(item, data, 1);
					buf.append(afterOutItem(item, 0, true));
				}
			}
		return buf.toString();
	}

}
