<%@page import="org.apache.xmlbeans.impl.xb.xsdschema.IncludeDocument.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=9,chrome=1">
    <title>南方基地管理提升平台</title>
    <link rel="shortcut icon" href="/SRMC/rmpb/images/chinamobile.ico"  type="image/x-icon"/>
    <link rel="bookmark" href="/SRMC/rmpb/images/chinamobile.ico"  type="image/x-icon"/>
    
	<link type="text/css"  href="/SRMC/dc/css/style.css" rel="stylesheet" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<style type="text/css">
	  #iframe{
	    margin-top: 118px;
	  }
	</style>	

</head>

<body style="overflow:hidden;">
<!-- 头部 S -->
<jsp:include page="/WEB-INF/views/core/main/header.jsp"/>
<jsp:include page="/WEB-INF/views/core/main/processbar.jsp"/>
<!-- 头部 E -->

<iframe src="<%=basePath%>${url}" width="100%" frameborder="0" scrolling="auto" id="content"></iframe>
<!-- <div class="footer_wrap"> -->
<!-- 	<iframe src="core/portal/footer" width="100%" frameborder="0" scrolling="no" height="50" id="footer"></iframe> -->
<!-- </div> -->

<script type="text/javascript" language="javascript">
$(function(){
	$("#content").load(function(){
		var client_h=document.documentElement.clientHeight || document.body.clientHeight;
		var iframe_h=client_h-$(".f_wrap").height() - 4;//-$("#footer").height();
	   $(this).height(iframe_h);
	});
});
$(window).resize(function() {
  var client_h=document.documentElement.clientHeight || document.body.clientHeight;
  var iframe_h=client_h-$(".f_wrap").height()-4;
  $("#content").height(iframe_h);
});
</script>
</body>
</html>
