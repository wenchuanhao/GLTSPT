<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			System.out.println("aaa");
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:tvns>

<head>
<title></title>
<lINK href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet">
</head>
<frameset  cols="238,*" frameborder="no" border="0" framespacing="0">
    
	  <frame src="<%=basePath %>core/portal/account_menu" name="account_menuFrame" scrolling="yes" noresize id="left" title="">
	   <frame  name="account_mainFrame" src="<%=basePath %>core/portal/account_main" scrolling="yes" noresize id="frmmain" >
	
      <!-- <frame src="<%=basePath %>core/portal/pm_main" name="frmmain" scrolling="yes" noresize id="frmmain" title="frmmain"> -->
		    
		</frameset>
	<noframes>
		<body>
		</body>

</noframes>
</html>