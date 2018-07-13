<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<frameset cols="180,700" frameborder="no" border="0" framespacing="0">
	<frame id="list" name="list"
		src="<%=basePath%>sys/user/dtreeUserByGoing" scrolling="auto"
		noresize id="frmheader"   /> 
	<frame id="orgtree"
		name="user" src="<%=basePath%>sys/user/queryUserListByGoing"
		scrolling="auto" noresize id="frmindex"   /> 
</frameset>
<noframes>
<body></body>
</noframes>
</html>
