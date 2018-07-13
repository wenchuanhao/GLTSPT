package org.trustel.privilege;

import java.util.Random;

import org.trustel.common.IMenuItem;
import org.trustel.common.ITreeItem;
import org.trustel.common.ITreeItemGenerator;
import org.trustel.common.Utils;

public class MenuTreeItemGenertor implements ITreeItemGenerator {
	public String topURL = "";

	private boolean visible = false;

	private Random rnd = new Random();

	private boolean restFul = false;

	public MenuTreeItemGenertor(boolean restFul) {
		this.restFul = restFul;
	}

	/**
	 * 
	 * @param visible
	 *            子菜单 true--默认展开 false－－不展开
	 */
	public MenuTreeItemGenertor(boolean visible, boolean restFul) {
		this.visible = visible;
		this.restFul = restFul;
	}

	protected String getProperty(ITreeItem item, int level, int index,
			int siblingCount, int childCount) {
		return "";
	}

	private String getTopItem(ITreeItem item, int level, int index,
			int siblingCount, int childCount) {
		String listStyle = (index + 1 == siblingCount) ? " class='nochildren'"
				: " class='children'";// 最后一个节点不需要输出多个列线
		String style = " style='display:none'";
		String menu = "menu1";
		if (visible) {
			style = "";
			menu = "menu1_1";
		}
		String ext = getProperty(item, level, index, siblingCount, childCount);
		return "<tr"
				+ ext
				+ "><td class='"
				+ menu
				+ "' onmouseup='showlinks(this,document.all.m"
				+ item.getCode()
				+ ")'>"
				+ item.getTitle()
				+ "</td></tr>\n"
				+ "<tr id='m"
				+ item.getCode()
				+ "' "
				+ style
				+ "><td"
				+ listStyle
				+ ">\n"
				+ "<table width='100%' border='0' cellspacing='0' cellpadding='2'>\n";
	}

	private String getSecondItem(ITreeItem item, int level, int index,
			int siblingCount, int childCount) {
		IMenuItem menuItem = (IMenuItem) item;
		String url = menuItem.getUrl();

		String style = (index + 1 == siblingCount) ? "class='menu2_last'"
				: "class='menu2'";
		StringBuffer buf = new StringBuffer("");
		String ext = getProperty(item, level, index, siblingCount, childCount);
		if (url == null || url.trim().equals(""))
			buf
					.append("<tr"
							+ ext
							+ "><td "
							+ style
							+ " height='20'><a href='javascript:' onclick='alert(\"本功能正在开发中!\");'>"
							+ item.getTitle() + "</a></td></tr>\n");
		else {
			if (restFul)
				url += "/" + rnd.nextInt(100000);
			else if (url.lastIndexOf("?") >= 0)
				url = url + "&index=" + rnd.nextInt(100000);
			else
				url = url + "?index=" + rnd.nextInt(100000);
			if (topURL.equals(""))
				topURL = url;

			String tip = Utils.null2Str(item.getTip());
			buf.append("<tr title='" + tip + "'" + ext + "><td  " + style
					+ "><a href='" + url + "'>" + item.getTitle()
					+ "</a></td></tr> \n");
		}

		return buf.toString();
	}

	public String afterOutItem(ITreeItem item, int level, int index,
			int siblingCount, int childCount) {
		return (level == 0) ? "</table></td></tr>\n" : "";
	}

	public String execute(ITreeItem item, int level, int index,
			int siblingCount, int childCount) {
		if (level == 0)
			return getTopItem(item, level, index, siblingCount, childCount);
		else if (level == 1)
			return getSecondItem(item, level, index, siblingCount, childCount);
		else
			return "";
	}

}
