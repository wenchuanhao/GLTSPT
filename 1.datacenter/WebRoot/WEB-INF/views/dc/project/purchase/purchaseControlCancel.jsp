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
	<jsp:include flush="true" page="public/purchaseEv.jsp"></jsp:include>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >采购已取消项目列表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    			<a class="btn_common01" href="javascript:ev_export()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>   			
		</div>    	
  	</div>
  	<div style="overflow: auto; width: 99%;">
	<table border="0" style="min-width: 100%" align="center" cellpadding="0" cellspacing="0" class="listTable">
		<tr>
		    <th><input id="checkAll" type="checkbox"></th>
			<th>序号</th>
			<th>项目名称</th>
			<th>需求部门</th>
			<th>项目所属年份</th>
			<th>是否计划内项目</th>
			<th>开支类型</th>
			<th>预算金额（万元）</th>
			<th>经办人</th>
			<th>代理公司名称</th>
			<th>采购方式</th>
			<th>取消原因</th>
		</tr>
 		<c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
 			<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
 			    <td ><input name="subBox" type="checkbox" value="${vo.id}"></td>
 				<td>${i.count}</td>
				<td>${vo.columnA}</td>
				<td>${vo.columnC}</td>
				<td>${vo.columnD}</td>
				<td>${vo.columnE}</td>
				<td>${vo.columnF}</td>
				<td>${fn:contains(vo.columnN, '.') ?  fn:substring(vo.columnN, 0, fn:indexOf(vo.columnN, '.')+3)  : vo.columnN }</td>								
				<td>${vo.columnB}</td>
				<td>${vo.columnH}</td>
				<td>${vo.columnJ}</td>
				<td>${vo.columnBm}</td>
 			</tr>
 		</c:forEach>
	    <c:if test="${empty ITEMPAGE.items}">
	       <tr> <td colspan="12">找不到对应的数据</td></tr>
	   </c:if>
	</table>
	</div>
 	 <div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
<script type="text/javascript" >var basePath = "<%=basePath%>purchaseControl";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	

<script type="text/javascript">
$(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
    });
    var $subBox = $("input[name='subBox']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
    });
});
function ev_search(){
	document.forms[0].action="<%=basePath%>purchaseControl/cancellist?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
	$("#columnC").val("");
	$("#columnB").val("");
	//ev_search();
}

function ev_export(){
    var $subBoxChecks = $("input[name='subBox']:checked");
	var zbIds = "";
	$.each($subBoxChecks,function(k,v){
		if(k == 0){
			zbIds += "'"+$(v).val()+"'";
		}else{
			zbIds += ",'"+$(v).val()+"'";
		}
	});
	document.forms[0].action="<%=basePath%>purchaseControl/cancelFile?ids="+zbIds+"&key="+Math.random();
	document.forms[0].submit();
	document.forms[0].action="<%=basePath%>purchaseControl/cancellist?key="+Math.random();
}
	 
</script>
</body>
</html>
