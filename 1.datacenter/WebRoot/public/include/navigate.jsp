<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.trustel.common.ItemPage"%>
<%@page import="org.trustel.system.SystemConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	ItemPage itempage = (ItemPage) request
			.getAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE);
	int pageIndex = 1;
	long pageCount = 0;
	long recordCount = 0;
	String sPageSize = String.valueOf(itempage.getPageSize());
	if (sPageSize == null || sPageSize.trim().equals(""))
		sPageSize = "10";
	if (itempage != null) {
		pageIndex = itempage.getPageIndex();
		pageCount = itempage.getPageCount();
		recordCount = itempage.getTotal();
	}
%>
第
<span class="font_orange01"> <input name="pageIndex"
	id="pageIndex" onkeypress="intOnly(this);" type="text"
	style='width: 30px'
	onkeyup="this.value=this.value.replace(/[^\d]/g,'') "
	onafterpaste="this.value=this.value.replace(/[^\d]/g-,'')"
	onkeyup="value=value.replace(/[^\w\/]/ig,'')" maxlength="4"
	value='<%out.print(pageIndex);%>'> </span>
页
<input class="bnt_class010" type="button" name="button6" id="button6"
	value="GO" onclick="javascript:gotoPage(3);" />
&nbsp;
<span class="font_orange01"> <%
 	out.print(pageIndex);
 %>/<%
 	out.print(pageCount);
 %> </span>
页
<span class="font_gray01">|</span>
<a href="javascript:void(0);" onclick="javascript:gotoPage(0);">首页</a>
<span class="font_gray01">|</span>
<a href="javascript:void(0);" onclick="javascript:gotoPage(-1);">上一页</a>
<span class="font_gray01">|</span>
<a href="javascript:void(0);" onclick="javascript:gotoPage(1);">下一页</a>
<span class="font_gray01">|</span>
<a href="javascript:void(0);" onclick="javascript:gotoPage(2);">尾页</a>

<input type="hidden" name="pageSize" value='<%=sPageSize%>'>
<script type="text/javascript">
<!--
	var pageCount = <%out.print(pageCount);%>;
	function gotoPage(pageaction) {
		var pageIndex = document.all("pageIndex").value;
		if (isNaN(pageIndex)) {
			alert("输入的页码格式不正确！");
			document.all("pageIndex").value = "";
			return;
		}
		if (pageaction == 3) {
			if (pageIndex > pageCount) {
			    document.all("pageIndex").value = "";
				alert("无此页码！");
				return;
			}
		} else if (pageaction == 0) {
			if (pageIndex != 1) {
				pageIndex = 1;
			} else {
				alert("已达首页!");
				return;
			}
		} else if (pageaction == 1) {
			if (pageIndex >= pageCount) {
				alert("已达末页!");
				return;
			} else {
				pageIndex++;
			}
		} else if (pageaction == -1) {
			if (pageIndex != 1) {
				pageIndex--;
			} else {
				alert("已达首页!");
				return;
			}
		} else if (pageaction == 2) {
			if (pageIndex >= pageCount) {
				alert("已达末页!");
				return;
			} else {
				pageIndex = pageCount;
			}
		}
		if (pageIndex < 1)
			pageIndex = 1;
		document.all("pageIndex").value = pageIndex;
		document.forms[0].submit();
	}

	//1.控制键盘输入，只允许数字键录入整数
	function intOnly() {
		if (!((window.event.keyCode >= 48) && (window.event.keyCode <= 57))) {
			window.event.keyCode = 0;
		}
	}
</script>
