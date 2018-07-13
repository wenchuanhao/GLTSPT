<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.trustel.common.ItemPage"%>
<%@page import="org.trustel.system.SystemConstant"%>
<!-- 为解决流程人员配置分页显示及全选问题 -->
<%
	//CHENJUFU MODIFY 20140107
	ItemPage itempage = (ItemPage) request.getAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE);
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

<input type="hidden" name="pageIndex" id="pageIndex" value="<%=pages%>">
<input type="hidden" name="pageSize" id="pageSize" value="<%=pagesize%>">
<input type="hidden" value="<%=pagesize%>" id="pageSize2" name="pageSize2" />  
<div class="pageBox" style="width:600px;">
	<div class="page"  style="width:600px; float:right;">
	<%if(pages>1){ %>
		<a href="#" class="prev_b" onclick="javascript:gotoPage(1)">首页</a>
		<a href="javascript:void(0);" onclick="javascript:gotoPage(<%=pages-1 %>)" class="prev">上一页</a>
		<%} %>
	<% for (int i = listbegin; i <= listend; i++) {
			if (i == pages) { 
		%>
			<a href="javascript:void(0);" onclick="javascript:gotoPage(<%=i %>)" class="current"><%=i %></a>
		<% 
		} else if(i!=0){
		 %>	
		  <a href="javascript:void(0);" onclick="javascript:gotoPage(<%=i %>)"  ><%=i %></a> 
		<%} 
		}%>
		<%if (pages != pagescount&&pagescount>0){ %>
		<a href="javascript:void(0);" onclick="javascript:gotoPage(<%=pages+1 %>)" class="next">下一页</a>
		<a href="javascript:void(0);" onclick="javascript:gotoPage(<%=pagescount %>)" class="next_b">尾页</a>
		<%} %>
		</div>
		<br/>
		<div class="page" style="float:right; width:195px;">
		<span class="pageBox_font">每页显示:</span>
			<a href="javascript:void(0);" onclick="javascript:gotoPage('-10');"<%if (pagesize == 10){ %>class="current2"<%} %> >10</a>
			<a href="javascript:void(0);" onclick="javascript:gotoPage('-20');" <%if (pagesize == 20){ %>class="current2"<%} %>>20</a>
			<a href="javascript:void(0);" onclick="javascript:gotoPage('-50');" <%if (pagesize == 50){ %>class="current2"<%} %>>50</a>
		<span class="pageBox_font" style="float:right;">页</span>
	</div>
</div>
<script type="text/javascript">
	function gotoPage(pageaction) {
		if(pageaction=='-10'){
			document.getElementById("pageIndex").value =1;
			document.getElementById("pageIndexTemp").value =1;
			document.getElementById("pageSize").value =10;
			document.getElementById("pageSize2").value=10;
			document.getElementById("pageSize3").value=10;
			
			
		}else if(pageaction=='-20'){
			document.getElementById("pageIndex").value =1;
			document.getElementById("pageIndexTemp").value =1;
			document.getElementById("pageSize").value =20;
			document.getElementById("pageSize2").value=20;
			document.getElementById("pageSize3").value=20;
			
			
		}else if(pageaction=='-50'){
			document.getElementById("pageIndex").value =1;
			document.getElementById("pageIndexTemp").value =1;
			document.getElementById("pageSize").value =50;
			document.getElementById("pageSize2").value=50;
			document.getElementById("pageSize3").value=50;
			
			
		}else{
	    	document.getElementById("pageIndexTemp").value =pageaction;
			document.getElementById("pageIndex").value = pageaction;
			
		}
		
		document.getElementById("pageForm").submit();
	}

</script>