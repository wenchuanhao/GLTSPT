<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.trustel.common.ItemPage"%>
<%@page import="org.trustel.system.SystemConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%--<%--%>
<%--	ItemPage itempage = (ItemPage) request.getAttribute("DOCUMENTITEM");--%>
<%--	System.out.println(itempage.getPageIndex()+"页码");--%>
<%--	int pageIndex = 1;--%>
<%--	long pageCount = 0;--%>
<%--	long total = 0;--%>
<%--	String pageSize = String.valueOf(itempage.getPageSize());--%>
<%--	if (pageSize == null || pageSize.trim().equals(""))--%>
<%--		pageSize = "10";--%>
<%--	if (itempage != null) {--%>
<%--		pageIndex = itempage.getPageIndex();--%>
<%--		pageCount = itempage.getPageCount();--%>
<%--		total = itempage.getTotal();--%>
<%--		if(pageCount!=0 && pageCount>13){--%>
<%--			if(pageIndex+13>pageCount){--%>
<%--			--%>
<%--				itempage.setPageCount(14-(pageIndex+13-pageCount));--%>
<%--			}else{--%>
<%--				itempage.setPageCount(13);--%>
<%--			}--%>
<%--			request.setAttribute("pageCount", pageCount);--%>
<%--			request.setAttribute("flags", "NEW");--%>
<%--			request.setAttribute("DOCUMENTITEM", itempage);--%>
<%--		}else{--%>
<%--		   request.setAttribute("flags", "OLD");--%>
<%--		}--%>
<%--		--%>
<%--	}else{--%>
<%--		 request.setAttribute("flags", "OLD");--%>
<%--	}--%>
<%--%>--%>
<%----%>
<%----%>
<%--<div class="pageBox">--%>
<%--	<div class="page">--%>
<%--		<a href="javascript:void(0);" onclick="javascript:gotoPage3('next')" class="next">下一页</a>--%>
<%--		<c:if test='${flags=="NEW"}'>--%>
<%--		<c:forEach varStatus="i" begin="1" end="${DOCUMENTITEM.pageCount}">--%>
<%--			<a href="javascript:void(0);" onclick="javascript:gotoPage3(${DOCUMENTITEM.pageIndex - i.count + DOCUMENTITEM.pageCount});" --%>
<%--			<c:if test="${DOCUMENTITEM.pageIndex == (DOCUMENTITEM.pageIndex - i.count + DOCUMENTITEM.pageCount)}">class="current"</c:if> >${DOCUMENTITEM.pageIndex - i.count + DOCUMENTITEM.pageCount}</a>--%>
<%--		</c:forEach>--%>
<%--		</c:if>--%>
<%--		<c:if test='${flags=="OLD"}'>--%>
<%--		<c:forEach varStatus="i" begin="1" end="${DOCUMENTITEM.pageCount}">--%>
<%--			<a href="javascript:void(0);" onclick="javascript:gotoPage3(${DOCUMENTITEM.pageCount + 1 - i.count});" <c:if test="${DOCUMENTITEM.pageIndex == (DOCUMENTITEM.pageCount + 1 - i.count)}">class="current"</c:if> >${DOCUMENTITEM.pageCount + 1 - i.count}</a>--%>
<%--		</c:forEach>--%>
<%--		</c:if>--%>
<%--		<a href="javascript:void(0);" onclick="javascript:gotoPage3('pre')" class="prev">上一页</a>--%>
<%--		--%>
<%--		<span class="pageBox_font">页</span>--%>
<%--		<a href="javascript:void(0);" onclick="javascript:gotoPage3(-50);" <c:if test="${DOCUMENTITEM.pageSize == '50'}">class="current2"</c:if>>50</a>--%>
<%--		<a href="javascript:void(0);" onclick="javascript:gotoPage3(-20);" <c:if test="${DOCUMENTITEM.pageSize == '20'}">class="current2"</c:if>>20</a>--%>
<%--		<a href="javascript:void(0);" onclick="javascript:gotoPage3(-10);" <c:if test="${DOCUMENTITEM.pageSize == '10'}">class="current2"</c:if>>10</a>--%>
<%--		<span class="pageBox_font">每页显示:</span>--%>
<%--	</div>--%>
<%--</div>--%>
<%----%>
<%--<!--input type="hidden" name="pageIndex" value="<%=pageIndex%>"-->--%>
<%--<!--input type="hidden" name="pageSize" value="<%=pageSize%>"-->--%>
<%----%>
<%--<script type="text/javascript">--%>
<%--	function gotoPage3(pageaction) {--%>
<%--		var pageIndex = document.all("pageIndex").value;--%>
<%--		var pageCount = <%out.print(pageCount);%>;--%>
<%--		if (pageaction == -10) {--%>
<%--			document.all("pageIndex").value = 1;--%>
<%--			document.all("pageSize").value = 10;--%>
<%--		} else if (pageaction == -20) {--%>
<%--			document.all("pageIndex").value = 1;--%>
<%--			document.all("pageSize").value = 20;--%>
<%--		} else if (pageaction == -50) {--%>
<%--			document.all("pageIndex").value = 1;--%>
<%--			document.all("pageSize").value = 50;--%>
<%--		} else if(pageaction == "pre") {--%>
<%--			if(document.all("pageIndex").value > 1){--%>
<%--				pageIndex--;--%>
<%--				document.all("pageIndex").value = pageIndex;--%>
<%--			}--%>
<%--		} else if(pageaction == "next") {--%>
<%--			if(pageIndex < pageCount){--%>
<%--				pageIndex++;--%>
<%--				document.all("pageIndex").value = pageIndex;--%>
<%--			}--%>
<%--		} else{--%>
<%--			document.all("pageIndex").value = pageaction;--%>
<%--		}--%>
<%--		var url = document.forms[2].action+"&pageIndex="+document.all("pageIndex").value+"&pageSize="+document.all("pageSize").value;--%>
<%--		document.forms[2].action = url;--%>
<%--		document.forms[2].submit();--%>
<%--	}--%>
<%----%>
<%--</script>--%>

<%
	//CHENJUFU MODIFY 20140107
	ItemPage itempage = (ItemPage) request.getAttribute("DOCUMENTITEM");
	
	int pagesize = 11;//每页显示记录数
	int liststep = 12;//最多显示分页页数
	int pages =1;//默认显示第一页
	long count = 0;//假设取出记录总数
	int pagescount = 0;
	int listbegin= 0;
	int listend = 0;
	if (itempage != null) {
		count = itempage.getTotal();
		pages = itempage.getPageIndex();
		pagesize = itempage.getPageSize();
		pagescount = (int) Math.ceil((double) count / pagesize);//求总页数，ceil（num）取整不小于num
		if (pagescount < pages) {
	             pages = pagescount;//如果分页变量大总页数，则将分页变量设计为总页数
		}
		if (pages < 1) {
			pages = 1;//如果分页变量小于１,则将分页变量设为１
		}
		listbegin = (pages - (int) Math.ceil((double) liststep / 2))+1;//从第几页开始显示分页信息
		if (listbegin < 1) {
			listbegin = 1;
		}
		if(pages<6&&pagescount>=11){
			listend=11;
		}
		else {
			listend = pages + liststep/2;//分页信息显示到第几页
			if(listend>10){
				listend =listend-1;
			}
			if(pagescount<=11){
				listend=pagescount;
			}
			if (listend > pagescount) {
					listend = pagescount;
					listbegin= pagescount-10;
			}
		}
	}
%>

<input type="hidden" name="pageIndex" id="pageIndex2" value="<%=pages%>">
<input type="hidden" name="pageSize" id="pageSize2" value="<%=pagesize%>">
<div class="pageBox" style="width:600px;">
	<div class="page"  style="width:600px; float:right;">
	<%if(pages>1){ %>
		<a href="#" class="prev_b" onclick="javascript:gotoPage2(1)">首页</a>
		<a href="javascript:void(0);" onclick="javascript:gotoPage2(<%=pages-1 %>)" class="prev">上一页</a>
		<%} %>
	<% for (int i = listbegin; i <= listend; i++) {
			if (i == pages) { 
		%>
			<a href="javascript:void(0);" onclick="javascript:gotoPage2(<%=i %>)" class="current"><%=i %></a>
		<% 
		} else{
		 %>	
		 <a href="javascript:void(0);" onclick="javascript:gotoPage2(<%=i %>)"  ><%=i %></a>
		<%} 
		}%>
		<%if (pages != pagescount&&pagescount>0){ %>
		<a href="javascript:void(0);" onclick="javascript:gotoPage2(<%=pages+1 %>)" class="next">下一页</a>
		<a href="javascript:void(0);" onclick="javascript:gotoPage2(<%=pagescount %>)" class="next_b">尾页</a>
		<%} %>
		</div>
		<br/>
		<div class="page" style="float:right; width:195px;">
		<span class="pageBox_font">每页显示:</span>
			<a href="javascript:void(0);" onclick="javascript:gotoPage2('-10');"<%if (pagesize == 10){ %>class="current2"<%} %> >10</a>
			<a href="javascript:void(0);" onclick="javascript:gotoPage2('-20');" <%if (pagesize == 20){ %>class="current2"<%} %>>20</a>
			<a href="javascript:void(0);" onclick="javascript:gotoPage2('-50');" <%if (pagesize == 50){ %>class="current2"<%} %>>50</a>
		<span class="pageBox_font" style="float:right;">页</span>
	</div>
</div>
<script type="text/javascript">
	function gotoPage2(pageaction) {
		if(pageaction=='-10'){
			document.getElementById("pageIndex2").value =1;
			document.getElementById("pageSize2").value =10;
		}else if(pageaction=='-20'){
			document.getElementById("pageIndex2").value =1;
			document.getElementById("pageSize2").value =20;
		}else if(pageaction=='-50'){
			document.getElementById("pageIndex2").value =1;
			document.getElementById("pageSize2").value =50;
		}else{
			document.getElementById("pageIndex2").value = pageaction;
		}
		
		var url = document.getElementById("formDocument").action+"&pageIndex="+document.getElementById("pageIndex2").value+"&pageSize="+document.getElementById("pageSize2").value;
		document.getElementById("formDocument").action = url;
		document.getElementById("formDocument").submit();
	}

</script>
