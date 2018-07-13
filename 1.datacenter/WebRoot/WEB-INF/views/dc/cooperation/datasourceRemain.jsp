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
	<input type="hidden" value="${cooperationForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${cooperationForm.pageSize}" id="pageSize"	name="pageSize"/>	
    <input type="hidden" value="${cooperationForm.userid}" id="userid_input"	name="userid"/>	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">采集日期：</th>
	    <td width="40%">
	    	<div class="date l" style="width: 163px;">
	    	<input readonly="readonly" name="beginMonth" id="begin_month" type="text"  placeholder="请您输入"  class="text02 l" value="${cooperationForm.beginMonth}"
	                      onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM',alwaysUseStartDate:true})" style="width:90%;" /><i></i>
	        </div>
	    	<div class="date l" style="width: 163px;">
	    	<input readonly="readonly" name="endMonth" id="end_month" type="text"  placeholder="请您输入"  class="text02 l" value="${cooperationForm.endMonth}"
	                      onclick="WdatePicker({minDate:'#F{$dp.$D(\'begin_month\')||\'%y-%M\'}', dateFmt:'yyyy-MM',alwaysUseStartDate:true})" style="width:90%;" /><i></i>
	        </div>
		</td> 
  		
	    <th width="9%" align="right">数据源名称：</th>
	    <td width="30%">
	    	<select onchange="changeBusTypesByList(this,'#datasourceId')" class="ui-select" id="parentDatasourceId" name="parentDatasourceId" style="width:180px;">
				<option <c:if test="${empty cooperationForm.parentDatasourceId }">selected="selected"</c:if> value="">请选择</option>
	    		<c:forEach items="${parentDatasourceList}" var="item" varStatus="i">
    				<option ${cooperationForm.parentDatasourceId ==item.datasourceId ? "selected=\"selected\"":null}  value="${item.datasourceId}">${item.datasourceName}</option>
				</c:forEach>
	        </select>
	    	<select class="ui-select" id="datasourceId" name="datasourceId" style="width:180px;">
				<option <c:if test="${empty cooperationForm.datasourceId }">selected="selected"</c:if> value="">请选择</option>
	    		<c:forEach items="${datasourceTypeList}" var="item" varStatus="i">
	    			<c:if test="${cooperationForm.parentDatasourceId == item.parentDatasourceId }">
    					<option ${cooperationForm.datasourceId ==item.datasourceId ? "selected=\"selected\"":null}  value="${item.datasourceId}">${item.datasourceName}</option>
	    			</c:if>
				</c:forEach>
	        </select>
	    </td>
	</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li <c:if test="${cooperationForm.status==2}">class="current"</c:if> onclick="document.forms[0].action='<%=basePath%>copperationController/remain?status=2';document.forms[0].submit();">审核中<em>(${shz })</em></li>
	      		<li <c:if test="${cooperationForm.status!=2}">class="current"</c:if> onclick="document.forms[0].action='<%=basePath%>copperationController/remain?status=3';document.forms[0].submit();">已审核<em>(${ysh })</em></li>
    	</ul>
    	<c:if test="${cooperationForm.status==2}">
	    	<div class="otherButtons r">
	    		<a class="btn_common01" href="javascript:batchPast()" /><img src="/SRMC/dc/images/btnIcon/publish.png" /><span>批量审核通过</span></a>
				<a class="btn_common01" href="javascript:void(0)"  id="batchReturnDatasourceRemain"/><img src="/SRMC/dc/images/btnIcon/invalid.png" /><span>批量审核不通过</span></a>
			</div>
		</c:if>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox"></th>
			<th>序号</th>
			<th>数据源分类</th>
			<th>数据源名称</th>
			<th>采集方式</th>
			<th>采集时间</th>
			<th>状态</th>
			<th>创建人</th>
			<th>创建时间</th>
			<c:if test="${cooperationForm.status!=2}">
			<th>审核人</th>
			<th>审核时间</th>
			</c:if>
			<th>原始文件</th>
			<th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" type="checkbox" value="${item[0].recordId}"></td>
	  	  	<td>${i.count }</td>
		    <td>${item[1].parentDatasourceName }</td>
		    <td>${item[1].datasourceName }</td>
		    <td>
		    	<jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="DATA_SOURCE"></jsp:param>
					<jsp:param name="paramCode" value="${item[1].datasourceSource }"></jsp:param>
				</jsp:include> 
		    </td>
		    <td>${item[0].month }</td>
		    <td>
		    	<jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="DATASOURCE_STATUS"></jsp:param>
					<jsp:param name="paramCode" value="${item[0].status}"></jsp:param>
				</jsp:include> 
		    </td>
		    <td>${item[0].createUsername }</td>
		    <td><fmt:formatDate value="${item[0].createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		    <c:if test="${cooperationForm.status!=2}">
			    <td >
		    	<c:choose>
				    <c:when test="${not empty item[0].checkUsername}">
				    	${item[0].checkUsername}
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
			    </td>
			    <td >
			    	<c:choose>
					    <c:when test="${not empty item[0].checkTime}">
					    	<fmt:formatDate value="${item[0].checkTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					    </c:when>
					    <c:otherwise>
					    	-
					    </c:otherwise>
					</c:choose>
			    </td>
		    </c:if>
		    <td >
		    	<c:choose>
				    <c:when test="${not empty item[0].fileId}">
				    	<a style="color: #007fcc;" class="fileName" href="<%=basePath%>rulesController/downloadRulesFile?fileId=${item[0].fileId }">${item[0].fileInfo.fileName }</a>
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
		    </td>
		    <td >
		       	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="<%=basePath%>copperationController/dataEnterDetail?id=${item[0].recordId}">明细</a></span> 
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/export.png"><a href="<%=basePath%>copperationController/exportDatasource?id=${item[0].recordId}">导出</a></span>
		    	<c:if test="${cooperationForm.status ==2}">
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/publish.png"><a href="javascript:pastRemain('${item[0].recordId}')">通过</a></span>
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/invalid.png"><a class="fancybox_Return" id="ba_${item[0].recordId}" value="${item[0].recordId}" href="javascript:void(0)">不通过</a></span>
		    	</c:if>
		    </td>
		 </tr>
	   </c:forEach>
	</table>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
           
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>copperationController";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
<script type="text/javascript" src="/SRMC/dc/cooperation/js/common.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script type="text/javascript">
var sonList = $.parseJSON('${sonList}'.replace("\r\n", "\\r\\n"));
$(function(){

});
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	returnDatasourceRemain();
	batchReturnDatasourceRemain();
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
		batchReturnDatasourceRemain();
    });
    var $subBox = $("input[name='subBox']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
		batchReturnDatasourceRemain();
    });
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>copperationController/remain?status=${status}";
	document.forms[0].submit();
}

//数据源审核通过
function ajaxPast(recordId){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>copperationController/pastRemain",
        data:"recordId=" + recordId,
        success:function(data){
        	if(data == 1){
        		ev_search();
        	}else{
        		alert("审核通过失败！");
        	}
        },
        error:function(){
            alert("审核通过失败！");
        }
    });
}

//单个通过
function pastRemain(recordId){
	ajaxPast(recordId);
}

//批量通过
function batchPast(){
	var $subBoxChecks = $("input[name='subBox']:checked");
	if($subBoxChecks.length == 0){
		alert("请至少选中一项数据");
		return;
	}
//倒序批量审批，最新的数据才能通过
	var recordId = '';
	for(var i = $subBoxChecks.length - 1; i >= 0; i--){
		if(i == $subBoxChecks.length - 1){
			recordId += $($subBoxChecks[i]).val();
		}else{
			recordId += ","+$($subBoxChecks[i]).val();
		}
	}
	ajaxPast(recordId);
}
</script>
</body>
</html>
