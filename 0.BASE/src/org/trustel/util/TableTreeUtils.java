package org.trustel.util;

import java.util.List;

import org.trustel.common.ITreeItem;
import org.trustel.common.ITreeItemGenerator;

public class TableTreeUtils {
	private StringBuffer buf = new StringBuffer("");

	private ITreeItemGenerator genertor;

	private List<?> list;

	/**
	 * 
	 * @param list
	 *            List<ITreeItem>
	 * @param genertor
	 */
	public TableTreeUtils(List<?> list, ITreeItemGenerator genertor) {
		this.list = list;
		this.genertor = genertor;
	}

	private void addTreeItem(ITreeItem item, int level, int index,
			int siblingCount, int childCount) {
		buf.append(genertor.execute(item, level, index, siblingCount,
				childCount));
	}

	private String afterOutItem(ITreeItem item, int level, int index,
			int siblingCount, int childCount) {
		return genertor.afterOutItem(item, level, index, siblingCount,
				childCount);
	}

	private int getChildCount(ITreeItem item) {
		int ret = 0;
		for (int i = 0; i < list.size(); i++) {
			ITreeItem tmp = (ITreeItem) list.get(i);
			if (tmp.getUcode().equals(item.getCode())) {
				ret++;
			}
		}
		return ret;
	}

	public String getMenuTree() {
		StringBuffer buf = new StringBuffer("");
		buf.append("<table height='100%' width='100%' border='0' "
				+ "cellpadding='0' cellspacing='0'>");

		buf.append("<tr>");
		buf.append("<td valign='top'>");

		buf.append("<table width='100%' border='0' "
				+ "cellspacing='0' cellpadding='0'>");
		buf.append("<tr>");
		buf.append("<td id='mmenu'>");

		buf.append("<table width='100%' border='0' align='center' "
				+ "cellpadding='2' cellspacing='0'>\n");

		buf.append(getTree());

		//buf.append("</td>");
		//buf.append("</tr>");
		buf.append("</table>");
		buf.append("</td>");
		buf.append("</tr>");
		buf.append("</table>");
		buf.append("</td>");
		buf.append("</tr>");
		buf.append("</table>");
		return buf.toString();
	}

	private int getTopLevelCount() {
		int ret = 0;
		if (list != null)
			for (int i = 0; i < list.size(); i++) {
				ITreeItem item = (ITreeItem) list.get(i);
				if (isTop(item)) {
					ret++;
				}
			}
		return ret;
	}

	public String getTree() {

		if (list != null) {
			int siblingCount = getTopLevelCount();
			int index = 0;
			for (int i = 0; i < list.size(); i++) {
				ITreeItem item = (ITreeItem) list.get(i);
				if (isTop(item)) {
					int childCount = getChildCount(item);

					addTreeItem(item, 0, index, siblingCount, childCount);
					OutChildren(item, 1, childCount);
					buf.append(afterOutItem(item, 0, index, siblingCount,
							childCount));
					index++;
				}
			}
		}
		return buf.toString();
	}

	private boolean isTop(ITreeItem item) {
		for (int i = 0; i < list.size(); i++) {
			ITreeItem tmp = (ITreeItem) list.get(i);
			if (item.getUcode().equals(tmp.getCode()))
				return false;
		}
		return true;
	}

	private void OutChildren(ITreeItem item, int level, int siblingCount) {
		int l2 = level + 1;
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			ITreeItem tmp = (ITreeItem) list.get(i);
			if (tmp.getUcode().equals(item.getCode())) {
				int childCount = getChildCount(tmp);
				addTreeItem(tmp, level, index, siblingCount, childCount);
				OutChildren(tmp, l2, childCount);
				buf.append(afterOutItem(tmp, level, index, siblingCount,
						childCount));
				index++;
			}
		}
	}
}
