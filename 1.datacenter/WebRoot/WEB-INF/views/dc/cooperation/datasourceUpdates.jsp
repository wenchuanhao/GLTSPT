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
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">月份：</th>
	    <td width="40%">
	    	<div class="date l" style="width: 163px;">
	    	<input readonly="readonly" name="month" id="month" type="text"  placeholder="请您输入"  class="text02 l" value="${cooperationForm.month}"
	                      onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM',alwaysUseStartDate:true})" style="width:90%;" /><i></i>
	        </div>
		</td> 
  		
	    <th width="9%" align="right">状态：</th>
	    <td width="30%">
	    	<select   class="ui-select" id="status" name="status" style="width:180px;">
				<option <c:if test="${empty cooperationForm.status }">selected="selected"</c:if> value="">请选择</option>
				<option ${cooperationForm.status==7 ? "selected=\"selected\"":null} value="7">同步成功</option>
				<option ${cooperationForm.status==8 ? "selected=\"selected\"":null} value="8">同步失败</option>
	        </select>
	    </td>
	</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
      		<li class="current">接口更新<em>(${ITEMPAGE.total })</em></li>
    	</ul>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox"></th>
			<th>序号</th>
			<th>数据源分类</th>
			<th>数据源名称</th>
			<th>接口方式</th>
			<th>状态</th>
			<th>系统名称</th>
			<th>月份</th>
			<th>时间</th>
			<th>操作</th>
	  </tr>
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" type="checkbox" value="${item[0]}"></td>
	  	  	<td>${i.count }</td>
		    <td>${item[4] }</td>
		    <td>${item[2] }</td>
		    <td>
		    	<jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="DATA_SOURCE"></jsp:param>
					<jsp:param name="paramCode" value="${item[5] }"></jsp:param>
				</jsp:include> 
		    </td>
		    <td>
		    	<jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="DATASOURCE_STATUS"></jsp:param>
					<jsp:param name="paramCode" value="${item[11]}"></jsp:param>
				</jsp:include> 
		    </td>
		    <td>${item[7] }</td>
		    <td>${item[8]}</td>
		    <td>${item[10]}</td>
		    <td >
		    	<c:if test="${item[11] == '9'}">
			       	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="javascript:void(0)">同步中</a></span> 
		    	</c:if>
		    	<c:if test="${item[11] != '9'}">
			       	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="javascript:void(0)" onclick="syncData('${item[0]}');">同步</a></span> 
		    	</c:if>
		       	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="<%=basePath%>copperationController/datasourceDetailList?id=${item[1]}">明细</a></span> 
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/export.png"><a  href="<%=basePath%>copperationController/datasourceExport?id=${item[1]}">导出</a></span>
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
	document.forms[0].action="<%=basePath%>copperationController/updates";
	document.forms[0].submit();
}

function syncData(id){
	jQuery.ajax({
		type:"POST",
		async:false,
		url:"<%=basePath%>copperationController/syncData",
		data:"datasourceId="+id,
		success:function(data){
			ev_search();
			if(data=="1"){
				alert("同步进行中，请稍候！");
			}else{
				alert("同步失败！");
			}
		},
		error:function(){
            alert("同步失败！");
        }
	});
}

</script>
</body>
</html>
