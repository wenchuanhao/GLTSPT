package org.trustel.privilege;

import java.util.List;

import org.trustel.common.ITreeItem;
import org.trustel.util.TableTreeUtils;


public class MenuTreeUtils extends TableTreeUtils {

	public MenuTreeUtils(List<ITreeItem> list,boolean restFul) {
		super(list, new MenuTreeItemGenertor(restFul));
	}

	// public MenuTreeUtils(List<?> list, String defaultValue, boolean
	// allowSelectTop) {
	//
	// super(list, defaultValue, allowSelectTop);
	// // System.out.println(list.size());
	// }
	//
	// protected String afterOutItem(ITreeItem item, int level, boolean isTop) {
	// if (isTop) {
	// return "</table></td></tr>\n";
	// }
	// return "";
	// }
	//
	// private String getTopItem(ITreeItem item) {
	//
	// return "<tr><td class='leftmenu-bg01' onmouseup='showlinks(this)'>"
	// + item.getTitle()
	// + "</td></tr>\n"
	// + "<tr id='"
	// + item.getCode()
	// + "' style='display:none'><td class='child'>\n"
	// + "<table width='100%' border='0' cellspacing='0' cellpadding='2'>\n";
	// }
	//
	// public String topURL = "";
	//
	// private Random rnd = new Random();
	//
	// protected String getSecondItem(IMenuItem item) {
	//
	// if (item.getUrl() == null || item.getUrl().trim().equals(""))
	// return
	// "<tr><td class='leftmenu-bg02' height='20'><a href='javascript:' onclick='alert(\"本功能正在开发中!\");'>"
	// + item.getTitle() + "</a></td></tr>\n";
	// else {
	// String url = item.getUrl();
	//
	// if (url.lastIndexOf("?") >= 0)
	// url = url + "&index=" + rnd.nextInt(100000);
	// else
	// url = url + "?index=" + rnd.nextInt(100000);
	//
	// if (topURL.equals(""))
	// topURL = url;
	//
	// return "<tr><td class='leftmenu-bg02'><a href='" + url + "'　title='" +
	// item.getTip()
	// + "'>" + item.getTitle()
	// + "</a></td></tr> \n";
	// }
	// }
	//
	// protected String getTreeItem(ITreeItem item, int level, boolean isTop) {
	// if (isTop)
	// return getTopItem(item);
	// else if (level == 1)
	// return getSecondItem((IMenuItem) item);
	// else
	// return "";
	// }
	//
	// public String getMenuTree() {
	// StringBuffer buf = new StringBuffer("");
	// buf.append("<table height='100%' width='100%' border='0' "
	// + "cellpadding='0' cellspacing='0'>");
	//
	// buf.append("<tr>");
	// buf.append("<td valign='top'>");
	//
	// buf.append("<table width='100%' border='0' "
	// + "cellspacing='0' cellpadding='0'>");
	// buf.append("<tr>");
	// buf.append("<td id='mmenu'>");
	//
	// buf.append("<table width='100%' border='0' align='center' "
	// + "cellpadding='2' cellspacing='0'"
	// + " onselectstart='return false'>\n");
	//
	// buf.append(getTree());
	//
	// buf.append("</td>");
	// buf.append("</tr>");
	// buf.append("</table>");
	// buf.append("</td>");
	// buf.append("</tr>");
	// buf.append("</table>");
	// return buf.toString();
	// }
}
