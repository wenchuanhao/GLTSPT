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
	<input type="hidden" value="${tzbm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${tzbm.pageSize}" id="pageSize"	name="pageSize"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">投资编号：</th>
	    <td width="30%">
	    	<input id="column02" name="column02" value="${tzbm.column02 }"  type="text"  placeholder="请填写投资编号" class="text01" style="width:195px;"  />
		</td> 
  		
  		<th width="9%" align="right">投资项目名称：</th>
	    <td width="30%">
	    	<input id="column03" name="column03" value="${tzbm.column03 }"  type="text"  placeholder="请填写投资项目名称" class="text01" style="width:195px;"  />
		</td> 
	</tr>
	<tr>
		<th width="9%" align="right">投资项目联系人：</th>
	    <td width="30%">
	    	<input type="hidden" name="column13" id="column13"  value="${tzbm.column13}"/>
	    	<input id="column13Name" name="column13Name" value="${tzbm.column13Name}"  type="text"  placeholder="请填写投资项目联系人" class="text01" style="width:195px;"  onfocus="autoCompletes(this);" 
	    	onclick="$('#column13').val('');$('#column13Name').val('');"/>
		</td> 
 		<th width="9%" align="right">投资科室：</th>
    	<td width="30%">
			<select class="ui-select"  style="width:200px;" id="ks" name="ks" >
				<option value="">请选择</option>
				<option ${tzbm.ks eq '0313f5eef20914f55aae79b647b79fdf' ? 'selected="selected"':''} value="0313f5eef20914f55aae79b647b79fdf">网络建设室</option>
				<option ${tzbm.ks eq '379725f12591b6c2dad22d72178917fd' ? 'selected="selected"':''} value="379725f12591b6c2dad22d72178917fd">项目管理室</option>
				<option ${tzbm.ks eq '48300b5a9c196bc8ab1c3e5398441c34' ? 'selected="selected"':''} value="48300b5a9c196bc8ab1c3e5398441c34">工程建设室</option>
			</select>
		</td>	
	</tr>
	</table>
	
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >投资一张表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    	<c:if test="${role ne null && role ne 'GC_QTRY'}">
    		<a class="btn_common01" href="javascript:ev_export()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>
    	</c:if>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox" onclick="ev_checked()"></th>
			<th>投资编号</th>
			<th>项目名称</th>
			<th>项目总投资<br/>（万元）</th>
			<th>年度安排<br/>资本开支<br/>（万元）</th>
			<th>至上年度安排<br/>投资计划<br/>（万元）</th>
			<th>累计签订合同<br/>金额不含税<br/>（万元）</th>
			<th>累计完成<br/>资本开支<br/>（万元）</th>
			<th>年度资本开支<br/>（万元）</th>
			<th>本年度资本<br/>开支百分比</th>
			<th>年度转资目标<br/>（万元）</th>
			<th>本年累计转资<br/>（万元）</th>
			<th>累计付款<br/>（万元）</th>
			<th>负责人</th>
			<th>计划书文号</th>
			<th>任务书文号</th>
			<th>操作</th>
	  </tr>
	  <c:if test="${not empty ITEMPAGE.items}">
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> ${vo.b eq '总计' ? 'style=\"background-color: #d6ebf9;\"':''}>
	  	  	<td ><input name="subBox" type="checkbox" value="${vo.id}"></td>
  	  		<td><span><a href="javascript:ev_edit('${vo.id}')" title="查看投资编码明细">${vo.a}</a></span></td><!-- 投资编号 -->
	    	<td>${vo.b}</td><!-- 项目名称 -->
		    <td><fmt:formatNumber value="${vo.c}" type="currency"  maxFractionDigits="2"/></td><!-- 项目总投资（万元） -->
			<td><fmt:formatNumber value="${vo.d}" type="currency"  maxFractionDigits="2"/></td><!-- 资本开支目标（万元） -->
			<td><fmt:formatNumber value="${vo.e}" type="currency"  maxFractionDigits="2"/></td><!-- 至上年度安排投资计划（万元） -->
			<td><fmt:formatNumber value="${vo.f}" type="currency"  maxFractionDigits="2"/></td><!-- 累计签订合同金额不含税（万元） -->
		    <td><fmt:formatNumber value="${vo.g}" type="currency"  maxFractionDigits="2"/></td><!-- 累计完成资本开支（万元） -->
		    <td><fmt:formatNumber value="${vo.h}" type="currency"  maxFractionDigits="2"/></td><!-- 年度资本开支（万元） -->
		    <td>${vo.i}</td><!-- 本年度资本开支百分比 -->
			<td><fmt:formatNumber value="${vo.j}" type="currency"  maxFractionDigits="2"/></td><!-- 年度转资目标（万元） -->
			<td><fmt:formatNumber value="${vo.k}" type="currency"  maxFractionDigits="2"/></td><!-- 本年累计转资（万元） -->
			<td><fmt:formatNumber value="${vo.l}" type="currency"  maxFractionDigits="2"/></td><!-- 累计付款（万元） -->
		    <td>${vo.column13Name}</td><!-- 负责人 -->
		    <td>${vo.n}</td><!-- 计划书文号 -->
		    <td>${vo.o}</td><!-- 任务书文号 -->    
		    <td><span><a href="javascript:ev_search_zxm('${vo.id}')" title="查看子项目投资一张表">子项目</a></span></td>
		 </tr>
	   </c:forEach>
	  	 <tr style="background-color: #d6ebf9;">
			<td colspan="3">合计：</td>
		    <td><fmt:formatNumber value="${tzbmT.c}" type="currency"  maxFractionDigits="2"/></td><!-- 项目总投资（万元） -->
			<td><fmt:formatNumber value="${tzbmT.d}" type="currency"  maxFractionDigits="2"/></td><!-- 资本开支目标（万元） -->
			<td><fmt:formatNumber value="${tzbmT.e}" type="currency"  maxFractionDigits="2"/></td><!-- 至上年度安排投资计划（万元） -->
			<td><fmt:formatNumber value="${tzbmT.f}" type="currency"  maxFractionDigits="2"/></td><!-- 累计签订合同金额不含税（万元） -->
		    <td><fmt:formatNumber value="${tzbmT.g}" type="currency"  maxFractionDigits="2"/></td><!-- 累计完成资本开支（万元） -->
		    <td><fmt:formatNumber value="${tzbmT.h}" type="currency"  maxFractionDigits="2"/></td><!-- 年度资本开支（万元） -->
		    <td>&nbsp;</td>
			<td><fmt:formatNumber value="${tzbmT.j}" type="currency"  maxFractionDigits="2"/></td><!-- 年度转资目标（万元） -->
			<td><fmt:formatNumber value="${tzbmT.k}" type="currency"  maxFractionDigits="2"/></td><!-- 本年累计转资（万元） -->
			<td><fmt:formatNumber value="${tzbmT.l}" type="currency"  maxFractionDigits="2"/></td><!-- 累计付款（万元） -->
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>    
		    <td>&nbsp;</td>
		 </tr>	   
	   </c:if>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="17">找不到对应的数据</td></tr>
	   </c:if>	   
	</table>
	</form>
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

function ev_checked(){
	var checkAll = document.getElementById("checkAll");
	if(checkAll != null && checkAll.type=="checkbox"){
		var ids = document.getElementsByName("subBox");
		 if(ids != null){
			for(var i=0;i<ids.length;i++){
				var id = ids[i];
				if(checkAll.checked){
					id.checked=true;
				}else{
					id.checked=false;
				}
			}	 
		 }
	}
}

//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>tzReport/tzList?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#column02").val("");
	$("#column03").val("");
	$("#column13").val("");
	$("#column13Name").val("");
	$("#ks").data("ui-select").val("");
	ev_search();
}

function ev_export(){
	var obj = $("input[name='subBox']:checked");
	if(obj.length > 0){
		document.forms[0].action="<%=basePath%>tzReport/exportFile?key="+Math.random();
		document.forms[0].submit();
		document.forms[0].action="<%=basePath%>tzReport/tzList?key="+Math.random();
	}else{
		alert("请选择投资项目！");
	}
}

function ev_edit(id){
	window.open("<%=basePath%>tzbm/edit?pageSource=reportList&id="+id);
}

function ev_search_zxm(id){
	window.open("<%=basePath%>tzReport/zxmList?tzbmId="+id+"&key="+Math.random());
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
