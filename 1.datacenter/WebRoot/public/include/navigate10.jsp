<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.trustel.common.ItemPage"%>
<%@page import="org.trustel.system.SystemConstant"%>
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
<input type="hidden" name="pagescount" id="pagescount" value="<%=pagescount %>">

<!--当页面为1的时候，麻烦把标签改为span 当页码为2就切换为a-->
<%if(pages <= 1){ %>
	<span class="pre">&nbsp;</span>
<%} %>
<%if(pages > 1){ %>
	<a href="javascript:void(0);" onclick="javascript:gotoPage(<%=pages-1 %>)" class="pre">&nbsp;</a>
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


<%if (pages != pagescount&&pagescount > 0){ %>
	<a href="javascript:void(0);" onclick="javascript:gotoPage(<%=pages+1 %>)" class="next">&nbsp;</a>
<%} %>
<%if (pages == pagescount&&pagescount > 0){ %>
	<span class="next">&nbsp;</span>
<%} %>
<!--<a href="###" class="next">&nbsp;</a>-->
<!--next可以点击上一页可以切换class为next_hover 可以参考我的注释-->
<!--<a href="###" class="next_hover">&nbsp;</a>-->
	共<%=pagescount %>页，跳转到
<input value="<%=pages%>" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" id="page_number_input" type="text" class="page_number">
<a href="javascript:void(0);" onclick="javascript:gotoPage('-1')" class="go">Go</a>
 
<script type="text/javascript">
	function gotoPage(pageaction) {
	
	    document.getElementById("isPages").value='Y';
	    
		if(pageaction=='-10'){
			document.getElementById("pageIndex").value =1;
			document.getElementById("pageSize").value =10;
		}else if(pageaction=='-20'){
			document.getElementById("pageIndex").value =1;
			document.getElementById("pageSize").value =20;
		}else if(pageaction=='-50'){
			document.getElementById("pageIndex").value =1;
			document.getElementById("pageSize").value =50;
		}else if(pageaction=='-1'){
			if( $.trim($("#page_number_input").val())=="" ){
				return;
			}
			var gotoPage = $("#page_number_input").val();
			var pagescount = $("#pagescount").val();
// 			alert(gotoPage + "|" + pagescount + "|" + (gotoPage > pagescount));
			if(gotoPage > pagescount){
				document.getElementById("pageIndex").value = pagescount;
			}else{
				document.getElementById("pageIndex").value = $("#page_number_input").val();
			}
		}else{
			document.getElementById("pageIndex").value = pageaction;
		}
		
		document.getElementById("pageForm").submit();
	}

</script>