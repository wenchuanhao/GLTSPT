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
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>

</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
			<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
			<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />	
	</div>
	
	<form name="form" id="pageForm" method="post"  >
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${purchaseForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${purchaseForm.pageSize}" id="pageSize"	name="pageSize"/>
    	
  	<table border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	  	<tr>
  			
  		<th width="2%" align="right">省公司采购结果确认时间：</th>
	    <td width="30%"  colspan="3">
	    	<div class="date l" >
	    	<input readonly="readonly" name="beginCreateTime" id="beginCreateTime" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value='${purchaseForm.beginCreateTime}' pattern="yyyy-MM-dd"/>"
	                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
	    	<div class="date l" >
	    	<input readonly="readonly" name="endCreateTime" id="endCreateTime" type="text"  placeholder="请输入结束时间"  class="text02 l" 
	    					value="<fmt:formatDate value='${purchaseForm.endCreateTime}' pattern="yyyy-MM-dd"/>"
	                     	onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreateTime\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
	</tr>
	
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >南方基地项目时段采购统计汇总表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    			<a class="btn_common01" href="javascript:ev_export()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>   			
		</div>    	
  	</div>
	<table border="0" width="99%" style="min-width: 99%"  align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
<th rowspan="2">采购方式</th>
<th colspan="4">${btime} 至 ${etime}</th>
 </tr>
<tr>
<th>项目数量(个)</th>
<th>项目量占比</th>
<th>决策总额(万元)</th>
<th>决策额占比</th>
</tr>
</tr>
 <c:forEach items="${perData}" var="vo" varStatus="i">	  
<tr>
<td>${vo.type eq 'heji'?'合计':vo.type}</td>
<td>${vo.c1}</td>
<td>${vo.c2}</td>
<td>${vo.c3}</td>
<td>${vo.c4}</td>	  	  	
</tr>
</c:forEach>

	 <c:if test="${empty perData}">
	   <tr><td colspan="9">找不到对应的数据</td></tr>
	   </c:if>
	</table>
 	 <div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
<script type="text/javascript" >var basePath = "<%=basePath%>purchase";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	

<script type="text/javascript">
function ev_search(){
    var beginTime = $('#beginCreateTime').val();
    var endTime = $('#endCreateTime').val(); 
    if(beginTime =""){
    alert("开始日期不能为空!");
    return;
    }
    if(endTime =""){
    alert("结束日期不能为空!");
    return;
    }
	document.forms[0].action="<%=basePath%>purchaseExp/purchasePerQuery?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
}

function ev_export(){
	document.forms[0].action="<%=basePath%>purchaseExp/exportPerFile?key="+Math.random();
	document.forms[0].submit();
	document.forms[0].action="<%=basePath%>purchaseExp/purchasePerQuery?key="+Math.random();
	
}
	 
</script>
</body>
</html>
