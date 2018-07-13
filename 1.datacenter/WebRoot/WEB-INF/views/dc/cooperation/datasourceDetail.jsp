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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
</head>
<body>
<div class="gl_import_m2">
<c:if test="${flag != '1' }">
	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
      		<li class="current">${talbeInfoList[0][3]}</li>
    	</ul>
  	</div>
</c:if>
<form name="form" id="pageForm" method="post"  >
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${cooperationForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${cooperationForm.pageSize}" id="pageSize"	name="pageSize"/>	
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	    <c:forEach items="${talbeInfoList}" var="item" varStatus="i">
		    <th>${item[2]}</th>
	    </c:forEach>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> >
	  	  	<c:forEach items="${talbeInfoList}" var="iTable"  varStatus="j">
	  	  		<td>${item[j.index]}</td>
		    </c:forEach>
		 </tr>
		 
	   </c:forEach>
	   
	</table>
</form>
<div class="gd_page">
	<jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
</div>
</div>
</body>
</html>
