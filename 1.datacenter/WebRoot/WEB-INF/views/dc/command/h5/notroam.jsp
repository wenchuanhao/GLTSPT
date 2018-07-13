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
<title>接收文档页面</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
<link href="/SRMC/dc/command/css/css.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/command/css/main.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/command/css/style.css" rel="stylesheet" type="text/css" />
</style>
<script type="text/javascript" src="/SRMC/dc/command/js/jquery-1.8.2.min.js"></script>
<jsp:include page="../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="COMMAND_STATUS,COMMAND_TYPE"></jsp:param>
</jsp:include>
</head>

<body style="background-color:#eeeeee;">
<div class="data_fill">
	<ul class="top">
		<li class="top_li"><a href="#">&nbsp;</a></li>
		<li class="cen">接收文档信息</li>
	</ul>
	<div class="top_false"> </div>
	<div class="top3">
  		<img src="/SRMC/dc/command/images/icon_08.png"> 
  	</div>
</div>
<!---弹窗开始-->
<div id="goodcover_div" class="goodcover" style="display: block;"></div>
	<div id="code_div" class="code" style="display: block;">
	  <div class="goodtxt">
		  <img src="/SRMC/dc/command/images/icon_06.png" class="img_1">
		<p class="p2">抱歉，该文件未流转！</p>
	  </div>
	<div class="close1">
		<a href="javascript:void(0)" onclick="$('.code').hide();$('.goodcover').hide();" class="a_1" style="color:#76cdff;">关闭</a>
	</div>
</div>
<!---弹窗结束-->
</body>
</html>
