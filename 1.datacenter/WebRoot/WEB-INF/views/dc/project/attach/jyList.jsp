<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>文档借阅</title>
	<base  target="_self"/>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<style>
	.gl_import_m_file{
		width:90%;
		margin-bottom: 60px;
		margin-top: 0;
		margin-left: auto;
		margin-right: auto;
	}
	  </style>	
</head>

<body style="overflow-x:hidden;">
<div class="gl_import_m_file">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
			<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />	
	</div>
	
	<form name="form" id="pageForm" method="post" >
	
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${vo.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${vo.pageSize}" id="pageSize"	name="pageSize"/>	
    
    <input type="hidden" value="${vo.parentId}" name="parentId" id="parentId"/>
    <input type="hidden" name="attachId" id="attachId" value="${attach.id}"/><!-- 附件ID -->
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">状态：</th>
	    <td width="30%">
	    	<select class="ui-select" id="column07" name="column07" style="width:200px;">
						<option ${vo.column07 eq '1' ? 'selected="selected"':''} value="1">未归还</option>
						<option ${vo.column07 eq '2' ? 'selected="selected"':''} value="2">已归还</option>
	        </select>
		</td> 
	</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" ><c:if test="${vo.column07 eq '1'}">未归还列表</c:if><c:if test="${vo.column07 eq '2'}">已归还历史列表</c:if><em></em></li>
    	</ul>
    	<div class="otherButtons r">
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
			<th>序号</th>
			<th>文档名称</th>
			<th>借阅人</th>
			<th>状态</th>
			<th>借阅时间</th>
			<th>归还时间</th>
			<th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
		    <td>${i.count}</td>
		    <c:set var="attach" value="${vo.attach}"/>
		    <td>${attach.column01}</td>
		    <td>${vo.column06}</td>
			<td>
					${vo.column07 ne '1' ? '':'未归还'}
					${vo.column07 ne '2' ? '':'已归还'}			
			</td>
			<td><fmt:formatDate value="${vo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		    <td><fmt:formatDate value="${vo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		    <td >
		    	<span><c:if test="${vo.column07 eq '1'}"><a href="javascript:ev_gh('${vo.id}')">归还</a></c:if></span>
		    </td>
		 </tr>
	   </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="7">找不到对应的数据</td></tr>
	   </c:if>	   
	</table>
 	<div class="gd_page" >
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
</body>
</html>

<script type="text/javascript">
$(document).ready(function(){
	
	var m = "${mess}";
	if(m != "" && m == "s"){
		//alert("归还成功");
		window.dialogArguments.ev_refresh1("${attach.parentId}","${attach.column10}");
	}
	else if(m != "" && m == "e"){
		alert("归还失败");
	}
	
	 // 将所有.ui-select实例化
	$('.ui-select').ui_select();
});

//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>fileUpload/listJy?key="+Math.random();
	document.forms[0].submit();
}

function ev_gh(id){
	if(confirm("确定归还所借阅的文档？")){
		$("#pageForm").attr("action","<%=basePath%>fileUpload/gh?id="+id);
		$("#pageForm").submit();
	}
}
</script>