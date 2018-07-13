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
	    <th style="width: 50%;">审核意见</th>
    </tr>


<c:forEach items="${flowInfoList.items}" var="item" varStatus="i">
	  	<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> style="color: #403d3d;" id="tr_id_${item[0]}">
		    <td >${i.count }</td>
		    <td >
<!-- 		       1:制度创建;2:制度审核;3:制度发布;4:制度废止;5:制度修订;6:制度退回 -->

				<c:choose>
				    <c:when test="${item[2] == 1}">
				       	制度创建
				    </c:when>
				    <c:when test="${item[2] == 2}">
				       	制度审核
				    </c:when>
				    <c:when test="${item[2] == 3}">
				       	制度发布
				    </c:when>
				    <c:when test="${item[2] == 4}">
				       	制度废止
				    </c:when>
				    <c:when test="${item[2] == 5}">
				       	制度修订
				    </c:when>
				    <c:when test="${item[2] == 6}">
				       	制度退回
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
		    </td>
		    <td >${item[4] }</td>
		    <td >
		    	<c:choose>
				    <c:when test="${not empty item[12]}">
				        ${item[12] }
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
		    </td>
		    <td >
		    	<c:choose>
				    <c:when test="${not empty item[5]}">
				        ${item[5] }
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
			</td>
		    <td >
		    	<fmt:formatNumber type="number" value="${item[15] }" maxFractionDigits="1"/>工时
		    </td>
		    <td >
<!-- 					0:退回,1:发布;2:废止;3:修订;4：已阅;5：退回后提交; -->
	              <c:choose>
				    <c:when test="${item[14] == 0}">
				       	<i>退回</i>
				    </c:when>
				    <c:when test="${item[14] == 1}">
				       	发布
				    </c:when>
				    <c:when test="${item[14] == 2}">
				       	废止
				    </c:when>
				    <c:when test="${item[14] == 3}">
				       	修订
				    </c:when>
				    <c:when test="${item[14] == 4}">
				       	已阅
				    </c:when>
				    <c:when test="${item[14] == 5}">
				       	退回后提交
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
			</td>
		    <td >${item[8] }</td>
		    
		 </tr>
	   </c:forEach>
</body>
</html>
 