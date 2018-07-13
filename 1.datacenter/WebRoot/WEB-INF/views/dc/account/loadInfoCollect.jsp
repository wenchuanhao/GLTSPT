<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
<c:if test="${type==1 }">
	  <tr>
	    <th>序号</th>
	    <th>报账单号</th>
	    <th>税务认证时间</th>
	    <th>备注</th>
	  </tr>
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
		  <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
		    <td>${item[0] }</td>
		    <td>${item[1] }</td>
		    <td>${item[11] }</td>
		    <td>${item[20] }</td>
		  </tr>
	  </c:forEach>
</c:if>
<c:if test="${type==2 }">
	  <tr>
	    <th>序号</th>
	    <th>报账单号</th>
	    <th>摘要</th>
	<!--     <th>合同名称</th> -->
	<!--     <th>合同编码</th> -->
	<!--     <th>供应商</th> -->
	    <th>发票代码</th>
	    <th>发票金额(不含税)</th>
	    <th>增值税发票号码</th>
	    <th>抵和凭证类型</th>
	    <th>税率</th>
	  </tr>
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
	  <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
	    <td>${item[0] }</td>
	    <td>${item[1] }</td>
	    <td>${item[20] }</td>
	    <td>${item[2] }</td>
	    <td>${item[8] }</td>
	    <td>${item[3] }</td>
	    <td>${item[12] }</td>
	    <td>${item[10] * 100 }%</td>
	  </tr>
	  </c:forEach>
</c:if>
<c:if test="${type==3 }">
  <tr>
    <th>序号</th>
    <th>报账单号</th>
    <th colspan="2">提交电子报账单</th>
    <th colspan="2">室经理审批</th>
    <th colspan="2">部门领导审批</th>
    <th colspan="2">初审会计审批</th>
    <th colspan="2">税务会计审批</th>
    <th colspan="2">主办会计审批</th>
    <th colspan="2">财务经理审批</th>
    <th colspan="2">南基领导审批</th>
    <th colspan="2">省财务审批</th>
    <th colspan="2">出纳付款</th>
    </tr>
    <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
  <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
    <td>${i.index+1 }</td>
    <td>${item[1] }</td>
    <td>${item[2] }</td>
    <td>${item[3] }</td>
    <td>${item[4] }</td>
    <td>${item[5] }</td>
    <td>${item[6] }</td>
    <td>${item[7] }</td>
    <td>${item[8] }</td>
    <td>${item[9] }</td>
    <td>${item[10] }</td>
    <td>${item[11] }</td>
    <td>${item[12] }</td>
    <td>${item[13] }</td>
    <td>${item[14] }</td>
    <td>${item[15] }</td>
    <td>${item[16] }</td>
    <td>${item[17] }</td>
    <td>${item[18] }</td>
    <td>${item[19] }</td>
    <td>${item[20] }</td>
    <td>${item[21] }</td>
  </tr>
  </c:forEach>
</c:if>
</body>
</html>