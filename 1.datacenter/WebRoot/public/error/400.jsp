<%@ page language="java" pageEncoding="UTF-8" isErrorPage="true"%>
<jsp:directive.page import="org.trustel.system.SystemConstant"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统异常</title>
		<%
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			if (request.getServerPort() == 80)
				basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		%>
		<style>
        body{text-align:center;padding-top:100px;}
        #error{ width:430px;height:240px;text-align:left; font-size:10.5pt}
        #error p{font-size:9pt;margin-top:114px;color:#333;margin-left:120px;width:265px;line-height:22px}
    </style>
	</head>
	<body>
		<table border=0 align=center id="error"
			background="/SRMC/themes/default/images/error.png">
			<tr>
				<td style="padding-left:100;padding-top:48px;padding-right:12px">
					系统异常或您的请求数据被窜改！
					<br />
					<font style="font-size:5px">&nbsp;</font>
					<br />
				</td>
			</tr>
		</table>
	</body>
</html>



