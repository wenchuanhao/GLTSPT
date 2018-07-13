<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统管理--用户信息</title>
		<link href="/SRMC/tec/css/style.css" type="text/css" rel="stylesheet" />
	</head>
	<body>
		 <div class="lyj_tb_nav_cc" id="con_one_1">
		<iframe id="paraTyp" name="paraTyp"
			src="<%=basePath%>sys/rolegroup/getUser/1/${roleGroupId}?index=${rand}"
			scrolling="no" width="100%" height="500" frameborder=0>
		</iframe>
		
		</div>
	</body>
</html>