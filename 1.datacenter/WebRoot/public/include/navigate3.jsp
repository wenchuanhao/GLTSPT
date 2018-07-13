<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.trustel.common.ItemPage"%>
<%@page import="org.trustel.system.SystemConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	ItemPage itempage = (ItemPage) request.getAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE);
	System.out.println(itempage.getPageIndex()+"页码");
	int pageIndex = 1;
	long pageCount = 0;
	long total = 0;
	String pageSize = String.valueOf(itempage.getPageSize());
	if (pageSize == null || pageSize.trim().equals(""))
		pageSize = "10";
	if (itempage != null) {
		pageIndex = itempage.getPageIndex();
		pageCount = itempage.getPageCount();
		total = itempage.getTotal();
		if(pageCount!=0 && pageCount>10){
			if(pageIndex+10>pageCount){
			
				itempage.setPageCount(11-(pageIndex+10-pageCount));
			}else{
				itempage.setPageCount(10);
			}
			request.setAttribute("pageCount", pageCount);
			request.setAttribute("flags", "NEW");
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itempage);
		}else{
		   request.setAttribute("flags", "OLD");
		}
		
	}else{
		 request.setAttribute("flags", "OLD");
	}
%>


<div class="pageBox">
	<div class="page">
		<a href="javascript:void(0);" onclick="javascript:gotoPage('next')" class="next">下一页</a>
		<c:if test='${flags=="NEW"}'>
		<c:forEach varStatus="i" begin="1" end="${ITEMPAGE.pageCount}">
			<a href="javascript:void(0);" onclick="javascript:gotoPage(${ITEMPAGE.pageIndex - i.count + ITEMPAGE.pageCount});" 
			<c:if test="${ITEMPAGE.pageIndex == (ITEMPAGE.pageIndex - i.count + ITEMPAGE.pageCount)}">class="current"</c:if> >${ITEMPAGE.pageIndex - i.count + ITEMPAGE.pageCount}</a>
		</c:forEach>
		</c:if>
		<c:if test='${flags=="OLD"}'>
		<c:forEach varStatus="i" begin="1" end="${ITEMPAGE.pageCount}">
			<a href="javascript:void(0);" onclick="javascript:gotoPage(${ITEMPAGE.pageCount + 1 - i.count});" <c:if test="${ITEMPAGE.pageIndex == (ITEMPAGE.pageCount + 1 - i.count)}">class="current"</c:if> >${ITEMPAGE.pageCount + 1 - i.count}</a>
		</c:forEach>
		</c:if>
		<a href="javascript:void(0);" onclick="javascript:gotoPage('pre')" class="prev">上一页</a>
		<br/>
		<span class="pageBox_font">页</span>
		<!--  a href="javascript:void(0);" onclick="javascript:gotoPage(-50);" <c:if test="${ITEMPAGE.pageSize == '50'}">class="current2"</c:if>>50</a-->
		<a href="javascript:void(0);"  <c:if test="${ITEMPAGE.pageSize == '20'}">class="current2"</c:if>>20</a>
		<!--a href="javascript:void(0);" onclick="javascript:gotoPage(-10);" <c:if test="${ITEMPAGE.pageSize == '10'}">class="current2"</c:if>>10</a-->
		<span class="pageBox_font">每页显示:</span>
	</div>
</div>

<input type="hidden" name="pageIndex"  value="<%=pageIndex%>">
<input type="hidden" name="pageSize"  value="<%=pageSize%>">

<script type="text/javascript">
	function gotoPage(pageaction) {
		var pageIndex = window.parent.document.getElementById("pageIndex").value;//document.all("pageIndex").value;
		var pageCount = <%out.print(pageCount);%>;
		if (pageaction == -10) {
			//document.all("pageIndex").value = 1;
			//document.all("pageSize").value = 10;
			//window.parent.document.getElementById("pageIndex").value=1;
			//window.parent.document.getElementById("pageSize").value=10;
		} else if (pageaction == -20) {
			document.all("pageIndex").value = 1;
			document.all("pageSize").value = 20;
			//window.parent.document.getElementById("pageIndex").value=1;
			//window.parent.document.getElementById("pageSize").value=20;
		} else if (pageaction == -50) {
			document.all("pageIndex").value = 1;
			document.all("pageSize").value = 50;
			//window.parent.document.getElementById("pageIndex").value=1;
			//window.parent.document.getElementById("pageSize").value=50;
		} else if(pageaction == "pre") {
			if(pageIndex > 1){
				pageIndex--;
				//document.all("pageIndex").value = pageIndex;
				window.parent.document.getElementById("pageIndex").value=pageIndex;
			}
		} else if(pageaction == "next") {
			if(pageIndex < pageCount){
				pageIndex++;
				//document.all("pageIndex").value = pageIndex;
				window.parent.document.getElementById("pageIndex").value=pageIndex;
			}
		} else{
			//document.all("pageIndex").value = pageaction;
			//window.parent.document.getElementsByName("pageIndex").value=parseInt(pageaction);
			window.parent.document.getElementById("pageIndex").value=pageaction;
		}
		//return;
		var form1 = window.parent.document.forms[0];
		//var url=window.parent.document.getElementById("toGetUser");
		form1.method="post";
       form1.target="toGetUser";
       form1.submit();
		//window.parent.document.forms[0].submit();
	}

</script>
