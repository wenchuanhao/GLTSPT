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
<title>子项目投资一张表</title>
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
	<input type="hidden" value="${zxm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${zxm.pageSize}" id="pageSize"	name="pageSize"/>	
    <input type="hidden" value="${tzbmId}" id="tzbmId"	name="tzbmId"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">子项目编号：</th>
	    <td width="30%">
	    	<input id="column02" name="column02" value="${zxm.column02 }"  type="text"  placeholder="请填写投资编号" class="text01" style="width:195px;"  />
		</td> 
  		
  		<th width="9%" align="right">子项目名称：</th>
	    <td width="30%">
	    	<input id="column03" name="column03" value="${zxm.column03 }"  type="text"  placeholder="请填写投资项目名称" class="text01" style="width:195px;"  />
		</td> 
	</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >子项目投资一张表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    	<c:if test="${role ne null && role ne 'GC_QTRY'}">
    		<a class="btn_common01" href="javascript:ev_export()"  style="display: none;"/><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>
    	</c:if>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox"></th>
			<th>子项目编号</th>
			<th>子项目名称</th>
			<th>累计签订合同金额不含税<br/>（万元）</th>
			<th>累计完成资本开支<br/>（万元）</th>
			<th>年度资本开支<br/>（万元）</th>
			<th>本年累计转资<br/>（万元）</th>
			<th>累计付款<br/>（万元）</th>
			<th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> ${vo.b eq '总计' ? 'style=\"background-color: #d6ebf9;\"':''}>
	  	  	<td ><input name="subBox" type="checkbox" value="${vo.id}"></td>
	  	  	<c:if test="${vo.b eq '总计'}"><td colspan="2">总计：</td></c:if>
	  	  	<c:if test="${vo.b ne '总计'}">
	  	  		<td><span><a href="javascript:ev_edit('${vo.id}')" title="查看子项目详情">${vo.a}</a></span></td><!-- 投资编号 -->
		    	<td>${vo.b}</td><!-- 项目名称 -->
		    </c:if>		    
			<td><fmt:formatNumber value="${vo.f}" type="currency"  maxFractionDigits="2"/></td><!-- 累计签订合同金额不含税（万元） -->
		    <td><fmt:formatNumber value="${vo.g}" type="currency"  maxFractionDigits="2"/></td><!-- 累计完成资本开支（万元） -->
		    <td><fmt:formatNumber value="${vo.h}" type="currency"  maxFractionDigits="2"/></td><!-- 年度资本开支（万元） -->
			<td><fmt:formatNumber value="${vo.k}" type="currency"  maxFractionDigits="2"/></td><!-- 本年累计转资（万元） -->
			<td><fmt:formatNumber value="${vo.l}" type="currency"  maxFractionDigits="2"/></td><!-- 累计付款（万元） -->
			<td><c:if test="${vo.b ne '总计'}"><span><a href="javascript:ev_search_htkz('${vo.id}')" title="查看合同开支列表">合同开支</a></span></c:if></td>
		 </tr>
	   </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="9">找不到对应的数据</td></tr>
	   </c:if>	   
	</table>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01" ></div>
</div>

<script type="text/javascript">
 $(document).ready(function(){
	var m = "${mess}";
	if(m != "" && m == "s"){
		alert("删除成功");
	}
	else if(m != "" && m == "e"){
		alert("删除失败");
	} 
 
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});

//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>tzReport/zxmList?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#column02").val("");
	$("#column03").val("");
	ev_search();
}

function ev_export(){
	document.forms[0].action="<%=basePath%>tzReport/exportFile?key="+Math.random();
	document.forms[0].submit();
	document.forms[0].action="<%=basePath%>tzReport/tzList?key="+Math.random();
}

function ev_edit(id){
	document.forms[0].action="<%=basePath%>zxm/edit?id="+id;
	document.forms[0].submit();
}

function ev_search_htkz(id){
	window.open("<%=basePath%>tzReport/htkzList?zxmId="+id+"&key="+Math.random());
}

//用户可选择创建人
function autoCompletes(target){
	jQuery(target).autocomplete({
		source: function( request, response ) {
			jQuery.ajax({
				url: "<%=basePath%>jsxm/searchUser",
				dataType: "json",
				data: {
					userValue: request.term
				},
				type: "POST",
				success: function(data) {
					if(data!=null){
			     		response(jQuery.map(data, function( item ) {
			     			return {
			     			 value:item[0].userName+"------"+item[0].orgName,
								userName:item[0].userName,
								userId:item[0].userId,
								account:item[0].account,
								orgName:item[0].orgName
							}
						}));
					}else{
						return false;
					}
				}
			});
		},
		minLength: 1,
		select: function( event, ui ) {
					jQuery("#column13").val(ui.item.userId);
					jQuery("#column13Name").val(ui.item.userName);
					return false;
		}
	});
}
</script>
</body>
</html>
