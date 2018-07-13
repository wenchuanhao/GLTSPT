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
<c:if test="${not empty info.createDate }">
<li class="child1 current">接单录入<em><fmt:formatDate value="${info.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></em></li>
</c:if>
<c:if test="${empty info.createDate }">
 <li class="child1">接单录入<em><fmt:formatDate value="${info.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></em></li>
</c:if>

<c:if test="${not empty info.noticeUpTime}">
<li class="child2 current">通知整改<em><fmt:formatDate value="${info.noticeUpTime}" pattern="yyyy-MM-dd HH:mm:ss"/></em></li>
</c:if>
<c:if test="${empty info.noticeUpTime}">
 <li class="child2">通知整改<em><fmt:formatDate value="${info.noticeUpTime}" pattern="yyyy-MM-dd HH:mm:ss"/></em></li>
</c:if>

<c:if test="${not empty info.noticeEndTime }">
<li class="child3 current">整改结束<em><fmt:formatDate value="${info.noticeEndTime }" pattern="yyyy-MM-dd HH:mm:ss"/></em></li>
</c:if>
<c:if test="${empty info.noticeEndTime  }">
 <li class="child3">整改结束<em><fmt:formatDate value="${info.noticeEndTime }" pattern="yyyy-MM-dd HH:mm:ss"/></em></li>
</c:if>
 
 <c:if test="${not empty info.submitDate }">
<li class="child4 current">初审提交<em><fmt:formatDate value="${info.submitDate }" pattern="yyyy-MM-dd HH:mm:ss"/></em></li>
</c:if>
<c:if test="${empty info.submitDate }">
 <li class="child4">初审提交<em><fmt:formatDate value="${info.submitDate }" pattern="yyyy-MM-dd HH:mm:ss"/></em></li>
</c:if>

 <c:if test="${not empty info.recordDate }">
<li class="child5 current">信息补录<em><fmt:formatDate value="${info.recordDate }" pattern="yyyy-MM-dd HH:mm:ss"/></em></li>
</c:if>
<c:if test="${empty info.recordDate }">
 <li class="child5">信息补录<em><fmt:formatDate value="${info.recordDate }" pattern="yyyy-MM-dd HH:mm:ss"/></em></li>
</c:if>
 
 </body>
 </html>