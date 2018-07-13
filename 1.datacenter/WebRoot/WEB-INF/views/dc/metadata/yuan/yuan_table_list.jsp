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
<title>元数据</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<body>

<div class="gl_import_m">
	<div class="tabpages">
	<ul class="l">
	      		<li class="current">元数据</li>
    	</ul>
	</div>
<form action=""  method="post"  id="form1">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="editTable cifTable reeditTable" align="center">
	<tr>
	    <td valign="top">
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
				  <tr>
				  		<th style="text-align: center;">序号</th>
				  		<c:forEach items="${listColumn}" var="item" varStatus="i">
				  				<th style="text-align: center;">${item.comments}</th>
				  		</c:forEach>
				  </tr>
				  
				  <c:forEach items="${listResult}" var="item" varStatus="i">
				  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> >
				  	 		<td>${i.count}</td>
				  	 		<c:forEach items="${item}" var="vo" varStatus="j">
				  	  			<td>${vo}</td>
				  	  		</c:forEach>
					 </tr>
				   </c:forEach>
				</table>	    
	    </td>
    </tr>
</table>
</form>
</div> 
</body>
</html>
