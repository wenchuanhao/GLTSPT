<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
</head>

<body class="bg_c_g">
	<div class="gl_import_m">
		<br /> <br /> <br />
		<table class="gl_table_a03" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td align="center">
					<div class="gl_bt_bnt01">
						对不起,输入了非法的字符串，为了保证系统的正确运行，请不要输入非法的字符串！
					</div>
				</td>
			</tr>
		</table>
		<br />
	</div>
</body>
</html>