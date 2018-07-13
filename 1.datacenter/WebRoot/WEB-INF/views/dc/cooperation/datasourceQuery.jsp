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
</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
		<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />
	</div>
	<form name="form" id="pageForm" method="post"  >
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${datasourceType.datasourceSource }" name="flag_month" id="flag_month"/>
	<input type="hidden" value="${cooperationForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${cooperationForm.pageSize}" id="pageSize"	name="pageSize"/>	
    <input type="hidden" value="${cooperationForm.userid}" id="userid_input"	name="userid"/>	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
	    <th width="9%" align="right">报表名称：</th>
	    <td width="50%">
	    	<select onchange="changeBusTypesByList(this,'#datasourceId')" class="ui-select" id="parentDatasourceId" name="parentDatasourceId" style="width:180px;">
				<option <c:if test="${empty cooperationForm.parentDatasourceId }">selected="selected"</c:if> value="">请选择</option>
	    		<c:forEach items="${parentDatasourceList}" var="item" varStatus="i">
    				<option ${cooperationForm.parentDatasourceId ==item.datasourceId ? "selected=\"selected\"":null}  value="${item.datasourceId}">${item.datasourceName}</option>
				</c:forEach>
	        </select>
	    	<select onchange="changeChildDatasource(this)" class="ui-select" id="datasourceId" name="datasourceId" style="width:180px;">
				<option <c:if test="${empty cooperationForm.datasourceId }">selected="selected"</c:if> value="">请选择</option>
	    		<c:forEach items="${datasourceTypeList}" var="item" varStatus="i">
	    			<c:if test="${cooperationForm.parentDatasourceId == item.parentDatasourceId }">
    					<option ${cooperationForm.datasourceId ==item.datasourceId ? "selected=\"selected\"":null}  value="${item.datasourceId}">${item.datasourceName}</option>
	    			</c:if>
				</c:forEach>
	        </select>
	    </td>
	    <th width="9%" align="right">统计周期：</th>
	    <td width="10%">
	    	<div class="date l" style="width: 163px;">
	    		 <c:choose>
				    <c:when test="${datasourceType.datasourceSource eq 2 }">
						<input readonly="readonly" name="month" id="month" type="text"  placeholder="请您输入"  class="text02 l" value="${cooperationForm.month}"
							  onfocus="doWdatePicker()" style="width:90%;" /><i></i>
				    </c:when>
				    <c:otherwise>
				    	<input readonly="readonly" name="month" id="month" type="text"  placeholder="请您输入"  class="text02 l" value="${cooperationForm.month}"
				              onfocus="doWdatePicker()" style="width:90%;" /><i></i>
				    </c:otherwise>
				</c:choose>
	        </div>
		</td> 
	</tr>
	</table>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
      		<li onclick="" class="current">业务报表</li>
    	</ul>
    	<div class="otherButtons r">
    		<a class="btn_common01" href="javascript:exportQueryExport()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导出</span></a>
    	</div>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox"></th>
			<th>序号</th>
			<c:forEach items="${interfaceTableName}" var="item" varStatus="i">
				<th>${item }</th>
			</c:forEach>
	  </tr>
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
	  	 	<td ><input name="subBox" type="checkbox" value="${i.index}"></td>
	  	  	<td >${i.count}</td>
	  	  	<c:forEach items="${interfaceTableName}" var="iTable"  varStatus="j">
	  	  		<td>${item[j.count]}</td>
		    </c:forEach>
		 </tr>
	   </c:forEach>
	</table>
	</form>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
           
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>copperationController";</script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/common.js"></script>
<script type="text/javascript">

var sonList = $.parseJSON('${sonList}'.replace("\r\n", "\\r\\n"));
$(function(){

});
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
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>copperationController/query";
	document.forms[0].submit();
}

//导出报表列表
function exportQueryExport(){
	var str="";
    $("input[name='subBox']:checked").each(function(){ 
        if(str==""){
        	str+=""+$(this).val()+"";
        }else{
        	str+=","+$(this).val()+"";
        }
    });
    if(str == ""){
    	alert("请至少选择一条报表！");
    }else{
	    document.forms[0].action="<%=basePath%>copperationController/exportQuery";
		document.forms[0].submit();
		document.forms[0].action="<%=basePath%>copperationController/query";
    }
	
}
</script>
</body>
</html>
