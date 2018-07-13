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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
</head>
<body>
<div class="gl_import_m">
	<c:forEach items="${tables}" var="item">
		<iframe src="<%=basePath%>copperationController/datasourceDetail?tab=${item}" scrolling="no" style="border: 0;" onLoad="iFrameHeight(this)"></iframe>
	</c:forEach>
	
	<div align="center" style="margin-top: 10px;margin-bottom: 10px;">
		<input name="" type="button" class="btn_common01" onclick="javascript:window.history.back();" value="返回" />
	</div>
</div>
<script type="text/javascript">
	function iFrameHeight(obj) {
		var ifm = obj;
		var subWeb = document.frames ? document.frames["iframepage"].document
				: ifm.contentDocument;
		if (ifm!=null && subWeb!=null) {
			ifm.height = subWeb.body.scrollHeight;
			ifm.width = subWeb.body.scrollWidth;
			var parentWidth = parseInt($(ifm).parent().css('width'));
			if(subWeb.body.scrollWidth < parentWidth){
				ifm.width = "100%";
			}
		}
	}
</script>
</body>
</html>
