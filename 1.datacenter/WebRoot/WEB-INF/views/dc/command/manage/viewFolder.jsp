<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath(); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
</head>

<body>
 <div class="ge01"></div>
	<div class="tabpages">
	  <ul class="l">
	    <li class="current">归档信息</li>
	  </ul>
	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
		<tr>
		    <th width="30%" align="right">归档位置：</th>
		    <td>
		    	${cdfolder.folderPosition}
		    </td>
	    </tr>
		<tr>
		    <th width="30%" align="right">归档人：</th>
		    <td>
		    	${cdfolder.folderUsername}
		    </td>
	    </tr>
		<tr>
		    <th width="30%" align="right">归档时间：</th>
		    <td>
		    	<fmt:formatDate value="${cdfolder.folderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		    </td>
	    </tr>
		<tr>
		    <th width="30%" align="right">归档文件（指令）扫描件：</th>
		    <td>
		    	<c:forEach items="${folders}" var="item" varStatus="j">
			    		<a style="color: #007fcc;" class="fileName" href="<%=basePath%>../rulesController/downloadRulesFile?fileId=${item.fileId }">
			    			${item.fileName }
			    			<c:if test="${item.fileSize / 1024 > 1024}">
				    			(<fmt:formatNumber type="number" value="${item.fileSize / 1024 / 1024}" maxFractionDigits="2"/>MB)
			    			
			    			</c:if>
			    			<c:if test="${item.fileSize / 1024 <= 1024}">
				    			(<fmt:formatNumber type="number" value="${item.fileSize / 1024}" maxFractionDigits="2"/>KB)
			    			</c:if>
			    		</a>
		    		</br>
		    	</c:forEach>
		    </td>
	    </tr>
	</table>
</body>
</html>

<script type="text/javascript">
$(function(){

});
$(document).ready(function(){
	
});
</script>