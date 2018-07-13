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
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!--公共JS -->
	<script type="text/javascript" >var basePath = "<%=basePath%>zb";</script>
	
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<jsp:include page="../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="PROJECT_STATUS,PROJECT_TYPE,ZB_STATUS"></jsp:param>
</jsp:include>
</head>

<body>

<div class="gl_import_m">
	<div class="tabpages">
		<ul class="l" id="tab">
	      		<li id="tab1"  class="current">汇报详情<em></em></li>
    	</ul>
		<div class="otherButtons r">
    		<a class="btn_common01" href="javascript:window.history.back()" /><img src="/SRMC/dc/images/btnIcon/back.png" /><span>返回</span></a>
		</div>
	</div>
<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<tr>
		    <th width="20%" align="right">周报名称：</th>
		    <td>${vo.column01}</td>
		    <th align="right">汇报周期：</th>
		    <td>
				<fmt:formatDate value='${vo.column08}' pattern='yyyy-MM-dd'/> --<fmt:formatDate value='${vo.column09}' pattern='yyyy-MM-dd'/>
		    </td>		    
	    </tr>
		<tr>
		    <th align="right">项目编码：</th>
		    <td>
		     ${vo.column02}
		    </td>		    		    
		    <th align="right">事项状态：</th>
		    <td id="rulesGrade_td">
		    	<jsp:include page="../../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="PROJECT_STATUS"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.column10}"></jsp:param>
			    </jsp:include>
		    </td>
	    </tr>
	    
	    <tr>
		    <th align="right">项目名称：</th>
		    <td>${vo.projectName }</td>		    		    
		    <th align="right">本周状态：</th>
		    <td id="rulesGrade_td">
		    	<jsp:include page="../../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="PROJECT_STATUS"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.column11}"></jsp:param>
			    </jsp:include>
		    </td>
	    </tr>
	    
	    <tr>
		    <th align="right">项目类型：</th>
		    <td>
		    	<jsp:include page="../../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="PROJECT_TYPE"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.column14}"></jsp:param>
			    </jsp:include>
		    </td>
		    <th align="right">${vo.column12Name}：</th>
		    <td>
		    ${vo.column12}
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right">汇报人：</th>
		    <td>
		    	${vo.column07}
		    </td>
		    <th align="right">汇报时间：</th>
		    <td>
		    <fmt:formatDate value='${vo.column13}' pattern='yyyy-MM-dd HH:mm:ss'/>
		    </td>		    
	    </tr>
	    <tr>
		    <th align="right">审核人：</th>
		    <td>
		    	${vo.auditUsername}
		    </td>
	    </tr>
	    
	    <tr>
		    <th align="right">本周工作：</th>
		    <td colspan="3"><textarea rows="5" cols="20" class="textArea01" readonly="readonly" style="width:70%;background-color: #f5f5f5;" placeholder="请填写本周工作" id="column03" name="column03">${vo.column03}</textarea></td>
	    </tr>
	    <tr>
		    <th align="right">下周计划：</th>
		    <td colspan="3"><textarea rows="5" cols="20" class="textArea01" readonly="readonly" style="width:70%;background-color: #f5f5f5;" placeholder="请填写下周计划" id="column04" name="column04">${vo.column04}</textarea></td>
	    </tr>
	    <tr>
		    <th align="right">关键点：</th>
		    <td colspan="3"><textarea rows="5" cols="20" class="textArea01" readonly="readonly" style="width:70%;background-color: #f5f5f5;" placeholder="请填写关键点" id="column05" name="column05">${vo.column05}</textarea></td>
	    </tr>
	    <tr>
		    <th align="right">存在问题：</th>
		    <td colspan="3"><textarea rows="5" cols="20" class="textArea01" readonly="readonly" style="width:70%;background-color: #f5f5f5;" placeholder="请填写存在问题" id="column06" name="column06">${vo.column06}</textarea></td>
	    </tr>
	    
  </table>
</form>
	<c:if test="${not empty list }">
	  	<div class="tabpages">
	            <ul class="l">
			      		<li class="current" >审核记录<em></em></li>
		    	</ul>
		</div>
	    <div class="box_task">
	       	<table  width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	           		<tr style="font-weight: bold;">
					    <th style="width: 3%;">序号</th>
					    <th style="width: 5%;">审核人</th>
					    <th style="width: 10%;">审核时间</th>
					    <th style="width: 5%;">审核结果</th>
					    <th style="width: 50%;">审核意见</th>
				    </tr>
	           		<c:forEach items="${list}" var="vo" varStatus="i">
	           			<tr style="font-weight: bold;">
						    <td>${i.count }</td>
						    <td>${vo.auditUsername }</td>
						    <td><fmt:formatDate value="${vo.auditTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						    <td>
						    	<jsp:include page="../../sys/dict/include/dict_config.jsp">
								    <jsp:param name="paramTypeCode" value="ZB_STATUS"></jsp:param>
								    <jsp:param name="paramCode" value="${vo.auditResult }"></jsp:param>
							    </jsp:include>
						    </td>
						    <td>${vo.auditDesc }</td>
					    </tr>
	           		</c:forEach>
	 		 </table>
	  	</div>
  	</c:if>
</div>
<div class="gl_import_m" id="gl_import_m_div2" style="display: none;">
	<div class="searchCondition">
		<span>历史周报</span>
	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable" id="listTable_load">
	  
	</table>
</div>
</body>
</html>