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
	<input type="hidden" value="${tzlhReport.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${tzlhReport.pageSize}" id="pageSize"	name="pageSize"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	  	<tr>
  		<th width="9%" align="right">部门：</th>
	    <td width="30%">
	    	<input type="hidden" value="${tzlhReport.dept}" id="dept"	name="dept"/>
	    	<select class="ui-select"  disabled="disabled" style="width:200px;">
				<option <c:if test="${empty tzlhReport.dept}">selected="selected"</c:if> value="">请选择</option>
				<c:forEach items="${depList}" var="item" varStatus="i">
					<option ${tzlhReport.dept eq item.orgName ? "selected=\"selected\"":null} value="${item.orgName}">${item.orgName}</option>
				</c:forEach>
	        </select>
		</td>  	
  		<th width="9%" align="right">投资科室：</th>
	    <td width="30%">
	    	<input type="hidden" value="${tzlhReport.ks}" name="ks" id= "ks" />
		    <input  value="${tzlhReport.ks}" type="text" class="text01" style="width:195px;" disabled="disabled"/>
		</td> 
  		
	</tr>
	<tr>
		<th width="9%" align="right">时间：</th>
	    <td width="30%" >
	    	<div class="date l" >
	    	<input readonly="readonly" name="beginCreateTime" id="beginCreateTime" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${tzlhReport.beginCreateTime}" pattern="yyyy-MM-dd"/>"
	                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
		<th width="9%" align="right"></th>
	    <td width="30%" >
	    	<div class="date l" >
	    	<input readonly="readonly" name="endCreateTime" id="endCreateTime" type="text"  placeholder="请输入结束时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${tzlhReport.endCreateTime}" pattern="yyyy-MM-dd"/>"
	                     	onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreateTime\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
	</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >行政经分报表<em></em></li><font color="red" size="3">&nbsp;&nbsp;&nbsp;温馨提示：数据来源于投资管理系统。</font>
    	</ul>
    	<div class="otherButtons r">
    		<c:if test="${role ne null && role ne 'GC_QTRY'}">
    			<a class="btn_common01" href="javascript:ev_export()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>
    		</c:if>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
			<th rowspan="2">序号</th>
			<th rowspan="2">年度</th>
			<th rowspan="2">项目编码</th>
			<th rowspan="2">项目名称</th>
			<!--<th colspan="3">转资相关</th>-->
			<th colspan="3">资本开支相关</th>
			<th colspan="4">合同相关</th>
	  </tr>	
	  <tr>
			<!--<th>计划转资金额<br/>汇总（万元）</th>
			<th>实际转资金额<br/>汇总（万元）</th>
			<th>项目转资率</th>-->
			<th>年度投资计划<br/>（万元）</th>
			<th>年度完成资本<br/>开支（万元）</th>
			<th>年度投资计划<br/>完成率</th>
			<th>项目投资总额<br/>（万元）</th>
			<th>合同金额<br/>（万元）</th>
			<th>合同数量</th>
			<th>合同完成率</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> ${vo.b eq null ? 'style=\"background-color: #d6ebf9;\"':''}>
	  	  	<td ${vo.b eq null ? 'colspan=4':''}>
	  	  		<c:if test="${vo.b ne null}">${i.count}</c:if>
	  	  		<c:if test="${vo.b eq null}">${vo.yyyymm} 总计：</c:if>
	  	  	</td><!-- 序号 -->
	  	  	<c:if test="${vo.b ne null}">
		  	  	<td>${vo.yyyymm}</td><!-- 年度 -->
		  	  	<td>${vo.a}</td><!-- 项目编码 -->
			    <td>${vo.b}</td><!-- 项目名称 -->
		    </c:if>
		    <!--<td><fmt:formatNumber value="${vo.c}" type="currency"  currencySymbol=""  maxFractionDigits="6"/></td><!-- 计划转资金额汇总（万元） -->
			<!--<td><fmt:formatNumber value="${vo.d}" type="currency"  currencySymbol=""  maxFractionDigits="6"/></td><!-- 实际转资金额汇总（万元） -->
			<!--<td>${vo.e}%</td><!-- 项目转资率 -->
			<td><fmt:formatNumber value="${vo.f}" type="currency"  currencySymbol=""  maxFractionDigits="6"/></td><!-- 年度资本开支进度计划（万元） -->
		    <td><fmt:formatNumber value="${vo.g}" type="currency"  currencySymbol=""  maxFractionDigits="6"/></td><!-- 年度完成资本开支（万元） -->
		    <td>${vo.h}%</td><!-- 年度投资计划完成率 -->
		    <td><fmt:formatNumber value="${vo.i}" type="currency"  currencySymbol=""  maxFractionDigits="6"/></td><!-- 项目投资总额（万元） -->
			<td><fmt:formatNumber value="${vo.j}" type="currency"  currencySymbol=""  maxFractionDigits="6"/></td><!-- 合同金额（万元） -->
			<td>${vo.k}</td><!-- 合同数量 -->
			<td>${vo.l}%</td><!-- 合同完成率 -->
		 </tr>
	   </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="11">找不到对应的数据</td></tr>
	   </c:if>	   
	</table>
 	<div class="gd_page">
 	<jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
<script type="text/javascript">
$(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});

//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>GCXzjf/index?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
	ev_search();
}

function ev_export(){
	document.forms[0].action="<%=basePath%>GCXzjf/exportFile?key="+Math.random();
	document.forms[0].submit();
	document.forms[0].action="<%=basePath%>GCXzjf/index?key="+Math.random();
}

//失去焦点事件
function onblurs(target){
	if(target.value==''||target.value=='请填写投资科室'){
		jQuery('#ks').val('');
		target.style.color='#b6b6b6';
	}
}

//光标选中输入框事件
function onfocuses(target){
	if(target.value=='请填写投资科室'){
		target.value='';
	}
	target.style.color='#333333';
	autoCompletes();
}

//用户可选择创建人
function autoCompletes(){
		jQuery("#ks").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>zb/searchDepartmenByName",
					dataType: "json",
					data: {
						code: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
						
				     		response(jQuery.map(data, function( item ) {
				     			return {
				     				value:item[0].organization_name,
				     			 	orgCode:item[0].organization_id,//+" - "+item[0].account+" - "+item[1].orgName
									orgName:item[0].organization_name,
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
				$("#ks").val(ui.item.orgName.split("->")[1]);
				return false;
			}
		});
	}
</script>
</body>
</html>
