<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>
<title>选择支撑单位</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<style type="text/css">
.shenglue{
 width:5em;
 overflow: hidden; 
 text-overflow: ellipsis;
 white-space:nowrap; 
 color:#4084b6;
}
</style>
</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
	    <input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
		<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />
	</div>
	
	<form name="form" id="pageForm" method="post"  >
	
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${support.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${support.pageSize}" id="pageSize"	name="pageSize"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	  	<tr>
	  		<th width="9%" align="right">支撑单位名称：</th>
		    <td width="20%">
		    	<input id="supportorgName" name="supportorgName" value="${support.supportorgName }"  type="text"  placeholder="请填写支撑单位名称" class="text01" style="width:150px;"  />
			</td> 
		</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >单位列表<em></em></li>
    	</ul>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th>序号</th>
			<th>支撑单位名称</th>
			<th>新增时间</th>
			<th>操作</th>
	  		 
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="" onclick="ev_confirm('${vo.supportorgId}_${vo.supportorgName}')">
	  	  	<td>${i.count }</td>
		    <td>
			    ${vo.supportorgName}
		    </td>
		    <td><fmt:formatDate value="${vo.createTime}" pattern="yyyy-MM-dd"/></td>
		    <td>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="javascript:ev_confirm('${vo.supportorgId}_${vo.supportorgName}');">使用</a></span>
		    </td>
		 </tr>
	   </c:forEach>
	</table>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
           
<script type="text/javascript">
$(function(){

});
 $(document).ready(function(){
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>support/selectList?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#supportorgName").val("");
	ev_search();
}
//使用
<%--function ev_confirm(v){--%>
<%--		window.returnValue = v;--%>
<%--	   	window.close();--%>
<%--}--%>
function ev_confirm(v){
   	if (window.opener != undefined) {  
     //for chrome    
     window.opener.putValue(v); 
 }  
 else {  
     window.returnValue = v;  
 } 
   	window.close();
}
</script>
</body>
</html>
