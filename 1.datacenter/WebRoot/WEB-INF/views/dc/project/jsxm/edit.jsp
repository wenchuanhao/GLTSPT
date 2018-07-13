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
	<title>查看-建设项目</title>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	
	<!-- 联想查询 -->
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
</head>

<body>

<div class="gl_import_m">
  	<div class="ge01"></div>
  	<div class="tabpages" >
    	<ul class="l" id="tab"> 
	      		<li id="tab1"  class="current searchCondition_header">查看-建设项目<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<c:choose>
    			<c:when test="${pageSource eq 'zb' || pageSource eq 'jsxm_list' || pageSource eq 'jsxmFileList'}">
    				<a class="btn_common01" href="javascript:window.close();" /><img src="/SRMC/dc/images/btnIcon/invalid.png" /><span>关闭</span></a>
    			</c:when>
    			<c:otherwise>
    				<a class="btn_common01" href="javascript:window.history.back()" /><img src="/SRMC/dc/images/btnIcon/back.png" /><span>返回</span></a>
    			</c:otherwise>
    		</c:choose>
		</div>    	
  	</div>	
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<jsp:include page="../jsxm/form.jsp" />	   
  	</table>
</div>
</body>
</html>
<script>
 $(document).ready(function(){
 
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});
</script>