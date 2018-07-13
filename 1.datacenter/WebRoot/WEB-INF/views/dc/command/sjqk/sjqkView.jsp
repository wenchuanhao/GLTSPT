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
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<title>南方基地管理提升平台</title>
	<link href="/SRMC/dc/command/css/word.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script type="text/javascript" src="/SRMC/dc/command/js/jquery.PrintArea.js"></script>
	<script type="text/javascript" src="/SRMC/dc/command/js/common.js"></script>
	<!--公共JS -->
	<script type="text/javascript" >var basePath = "<%=basePath%>command";</script>
</head>
  
<body>
<!-- 包含返回页面 -->
 <jsp:include flush="true" page="../manage/viewBack.jsp"></jsp:include>
<!-- 包含word格式页面 -->
 <jsp:include flush="true" page="include/sjqkWord.jsp"></jsp:include>
</body>
</html>
