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
<title>合同开支列表</title>
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
	<input type="hidden" value="${htKz.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${htKz.pageSize}" id="pageSize"	name="pageSize"/>	
    <input type="hidden" value="${zxmId}" id="zxmId"	name="zxmId"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">投资编号：</th>
	    <td width="30%"><input id="column02" name="column02" value="${htKz.column02}"  type="text"  placeholder="请填写投资编号" class="text01" style="width:195px;"  /></td> 
	</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >合同开支列表<em></em></li>
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
			<th>投资编号</th>
			<th>合同编号</th>
			<th>合同名称</th>
			<th>合同不含税金额<br />（万元）</th>
			<th>合同含税金额<br />（万元）</th>
			<th>合同对方</th>
			<th>合同类型</th>
			<th>累计形象进度/<br />MIS接收金额（万元）</th>
			<th>本年形象进度/<br />MIS接收金额（万元）</th>
			<th>累计转资金额<br />（万元）</th>
			<th>本年转资金额<br />（万元）</th>
			<th>累计付款金额<br />（万元）</th>
			<th>负责人</th>
			<th>记录时间</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" type="checkbox" value="${vo.id}"></td>
		    <td>${vo.column02}</td>
		    <c:set var="zxmHt" value="${vo.zxmHt}"/>
		    <td><span><a href="javascript:ev_edit('${vo.id}')">${zxmHt.column01}</a></span></td>
		    <td>${zxmHt.column03}</td>
			<td><fmt:formatNumber value="${vo.column03}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo.column04}" type="currency"  maxFractionDigits="6"/></td>
			<td>${vo.column05}</td>
			<td>${vo.column06 eq '1' ? '费用类':''}${vo.column06 eq '2' ? '订单类':''}</td>
			<td><fmt:formatNumber value="${vo.column07}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo.column08}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo.column09}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo.column10}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo.column11}" type="currency"  maxFractionDigits="6"/></td>
			<td>${vo.column13SysUser.userName}</td>
		    <td><fmt:formatDate value="${vo.column12}" pattern="yyyy-MM-dd"/></td>
		 </tr>
	   </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="15">找不到对应的数据</td></tr>
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
	document.forms[0].action="<%=basePath%>tzReport/htkzList?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#column02").val("");
	/*$("#column01").val("");
	$("#htName").val("");
	$("#column13").val("");
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
	$("#ks").data("ui-select").val("");*/
	ev_search();
}

function ev_export(){
	document.forms[0].action="<%=basePath%>tzReport/exportFile?key="+Math.random();
	document.forms[0].submit();
	document.forms[0].action="<%=basePath%>tzReport/tzList?key="+Math.random();
}

function ev_edit(id){
	document.forms[0].action="<%=basePath%>htKz/edit?id="+id;
	document.forms[0].submit();
}

function ev_search_htkz(id){
	document.forms[0].action="<%=basePath%>tzReport/htkzList?zxmId="+id+"&key="+Math.random();
	document.forms[0].submit();
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
