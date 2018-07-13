<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${listRoles}" var="org" varStatus="i">
	<input type="checkbox" value="${org.roleId}" name="roles" />${org.roleName}&nbsp;&nbsp;
<!--       <c:if test="${(i.index+1)%4==0}"> -->
<!-- 		<br /> -->
<!-- 	  </c:if> -->
</c:forEach>