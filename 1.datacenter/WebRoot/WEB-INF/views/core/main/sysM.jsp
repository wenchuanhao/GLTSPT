<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:tvns>

<head>
<title></title>
<lINK href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet">
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<frameset  cols="238,*" frameborder="no" border="0" framespacing="0">
    
	   <frame src="<%=basePath %>core/portal/sysM_menu?moduleCode=${moduleCode}" name="sysM_menuFrame" scrolling="yes" noresize id="left" title="">
	   <frame  src="<%=basePath %>core/portal/main" name="sysM_mainFrame"  scrolling="yes" noresize id="frmmain" >
		</frameset>
	<noframes>
		<body>
		</body>

</noframes>
</html>