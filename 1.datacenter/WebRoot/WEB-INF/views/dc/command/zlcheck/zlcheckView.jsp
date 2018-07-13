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
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript"
	src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<!--公共JS -->
	<script type="text/javascript" src="/SRMC/dc/command/js/jquery.PrintArea.js"></script>
	<script type="text/javascript" src="/SRMC/dc/command/js/common.js"></script>
<script type="text/javascript">var basePath = "<%=basePath%>command";</script>
<!-- 	表单异步提交 -->
<script src="/SRMC/rmpb/js/jquery.form.js"></script>
<!-- word.css -->
<link href="/SRMC/dc/command/css/word.css" rel="stylesheet" type="text/css" />
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet"
	href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css" />
<script type="text/javascript"
	src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<jsp:include page="../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="PROJECT_STATUS,PROJECT_TYPE"></jsp:param>
</jsp:include>
</head>
<body>
<!-- 包含返回页面 -->
 <jsp:include flush="true" page="../manage/viewBack.jsp"></jsp:include>
<!-- 包含word格式页面 -->
 <jsp:include flush="true" page="include/zlcheckWord.jsp"></jsp:include>
</body>
</html>

<script type="text/javascript">
window.onload = function() {

}
</script>