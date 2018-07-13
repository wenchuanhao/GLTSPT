<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<option <c:if test="${empty rulesForm.rulesGrade }">selected="selected"</c:if> value="">请选择</option>
<c:forEach items="${rulesGrades}" var="item" varStatus="i">
	<option ${rulesForm.rulesGrade==item.parameterCode ? "selected=\"selected\"":null}  value="${item.parameterCode}">${item.parameterValue}</option>
</c:forEach>
