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
</head>
<body>
<!--     流程信息 -->
  	<tr style="font-weight: bold;">
	    <th style="width: 3%;">序号</th>
	    <th style="width: 8%;">环节</th>
	    <th style="width: 5%;">处理人</th>
	    <th style="width: 10%;">到达时间</th>
	    <th style="width: 10%;">完成时间</th>
	    <th style="width: 5%;">耗时</th>
	    <th style="width: 5%;">审核结果</th>
	    <th>审核意见</th>
    </tr>
</body>
</html>
 