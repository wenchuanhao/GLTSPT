<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath(); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
</head>

<body>

	<!-- 遮罩内容开始 -->

	<div class="taskCreatBox">
		  	<div class="tabpages">
		            <ul class="l">
				      		<li class="current" >流转记录<em></em></li>
			    	</ul>
			</div>
            <div class="box_task">
             	<table  width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
             		<c:forEach items="${list}" var="vo" varStatus="i">
             			<tr>
             				<td>${i.count }</td>
             				<td><fmt:formatDate value="${vo.flowTime}" pattern="yyyy-MM-dd"/></td>
             				<td><fmt:formatDate value="${vo.flowTime}" pattern="E"/></td>
             				<td><fmt:formatDate value="${vo.flowTime}" pattern="HH:mm:ss"/></td>
             				
             				<td>
             				【${vo.flowRolename }】
             				<c:choose>
						    <c:when test="${vo.flowStatus eq 1}">
						    	发起人
						    </c:when>
						    <c:when test="${vo.flowStatus eq 2}">
						    	接收人
						    </c:when>
						    <c:when test="${vo.flowStatus eq 3}">
						    	归档人
						    </c:when>
						    <c:when test="${vo.flowStatus eq 4}">
						    	作废人
						    </c:when>
						    <c:when test="${vo.flowStatus eq 5}">
						    	撤销归档
						    </c:when>
						    <c:otherwise>
						    	-
						    </c:otherwise>
						    </c:choose>：${vo.flowUsername }，${vo.flowMobile }</td>
             			</tr>
             		</c:forEach>
             		<c:if test="${not empty showback && showback eq 1 }">
					 <tr>
					   	 <td  colspan="5" align="center" height="50">
							<input name="" type="button" class="btn_common02" onclick="javascript:parent.$.fancybox.close();" value="返 回" />
						 </td>
					 </tr>    
             		</c:if>
		 		 </table>
          	</div>
        </div>
<!-- 遮罩内容结束 -->
</body>
</html>

<script type="text/javascript">
$(function(){

});
$(document).ready(function(){
	
});
</script>