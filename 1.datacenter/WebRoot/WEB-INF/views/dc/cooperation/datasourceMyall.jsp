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
  		<th width="9%" align="right">月份：</th>
	    <td width="10%">
	    	<div class="date l" style="width: 163px;">
	    	<input readonly="readonly" name="month" id="month" type="text"  placeholder="请您输入"  class="text02 l" value="${cooperationForm.month}"
	                      onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM',alwaysUseStartDate:true})" style="width:90%;" /><i></i>
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
      		<li onclick="tabCurrent(0);" <c:if test="${type==0 }">class="current"</c:if>>数据源列表<em>(${MY })</em></li>
    	</ul>
    	<ul class="l">
      		<li onclick="tabCurrent(3);" <c:if test="${type==3 }">class="current"</c:if>>已审核<em>(${SH })</em></li>
    	</ul>
    	<ul class="l">
      		<li onclick="tabCurrent(2);" <c:if test="${type==2 }">class="current"</c:if>>审核中<em>(${SHZ })</em></li>
    	</ul>
    	<ul class="l">
      		<li onclick="tabCurrent(1);" <c:if test="${type==1 }">class="current"</c:if>>草稿<em>(${CG })</em></li>
    	</ul>
    	<ul class="l">
      		<li onclick="tabCurrent(5);" <c:if test="${type==5 }">class="current"</c:if>>已修订<em>(${XD })</em></li>
    	</ul>
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
			<c:if test="${type!=1 }">
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
		   <c:if test="${type!=1 }">
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
		       	<c:if test="${item[0].status == '1' }">
		    		<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/publish.png"><a href="javascript:void(0)" onclick="submitMyData('${item[0].recordId}');">提交</a></span> 
		       	</c:if>
		       	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="<%=basePath%>copperationController/dataEnterDetail?id=${item[0].recordId}">明细</a></span> 
		       	<c:if test="${item[0].status == '1' }">
		    		<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:void(0)" onclick="delMyData('${item[0].recordId}');">删除</a></span> 
		       	</c:if>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/export.png"><a  href="<%=basePath%>copperationController/exportDatasource?id=${item[0].recordId}">导出</a></span>
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
<script type="text/javascript" src="/SRMC/dc/cooperation/js/common.js"></script>
<script type="text/javascript">
//删除 
function delMyData(id){
	if(!confirm("删除后无法恢复,确定删除吗？")){return;}
	jQuery.ajax({
		type:"POST",
		async:false,
		url:"<%=basePath%>copperationController/delMyData",
		data:"dataSourceID="+id,
		success:function(data){
			if(data=="1"){
				window.location.href="<%=basePath%>copperationController/myAll";
			}else{
				alert("删除失败");
			}
		},
		error:function(){
            alert("删除失败！");
        }
	});
}

var sonList = $.parseJSON('${sonList}'.replace("\r\n", "\\r\\n"));
var currentType = "${type}";
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
	
	document.forms[0].action="<%=basePath%>copperationController/myAll?type="+currentType;
	document.forms[0].submit();
}

function tabCurrent(current){
	currentType = current;
	document.forms[0].action="<%=basePath%>copperationController/myAll?type="+current;
	document.forms[0].submit();
}

//提交
function submitMyData(id){
	jQuery.ajax({
		type:"POST",
		async:false,
		url:"<%=basePath%>copperationController/submitMyData",
		data:"datasourceRecordsId="+id + "&status=2",
		success:function(data){
			if(data=="1"){
				window.location.href="<%=basePath%>copperationController/myAll?type="+currentType;
			}else{
				alert("提交失败！");
			}
		},
		error:function(){
            alert("提交失败！");
        }
	});
}
</script>
</body>
</html>
