<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>数据预警列表</title>
		<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
		<link href="/SRMC/rmpb/css/date_input.css" type="text/css" rel="stylesheet" />
		<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
		<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
		<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
</head>
<body>
<form action="" method="post" id="pageForm">
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${warnForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${warnForm.pageSize}" id="pageSize"	name="pageSize"/>
    <input type="hidden" value="${type}" id="type"	name="type"/>
<div class="gl_import_m">
<div class="searchCondition"><span>查询条件</span></div>
  <table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable" align="center">
  <tr>
    <th width="5%" align="left">开始时间：</th>
    <td width="20px" ><div class="date l" style="width:140px;">
		    <input name="startDate"  type="text" value="<fmt:formatDate value="${warnForm.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
		    onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  placeholder="请您输入"  class="text02 l" style="width:95%;" /><i></i>
		    </div> </td>
    <th width="5%" align="left">结束时间：</th>
    <td width="20px"><div class="date l" style="width:140px;">
		    <input name="endDate"  type="text" value="<fmt:formatDate value="${warnForm.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
		    onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  placeholder="请您输入"  class="text02 l" style="width:95%;" /><i></i>
		    </div> </td>
    <%--<th width="5%" align="right">数据源：</th>
     <td width="20px">
    <select class="ui-select" name="dataSource" id="sel_09" style="width:180px;">
	          <option value="">请选择</option>
	          <option value="1" <c:if test="${accountForm.includeBuckle =='1' }">selected="selected"</c:if> >是</option>
	          <option value="0" <c:if test="${accountForm.includeBuckle =='0' }">selected="selected"</c:if>>否</option>
        	</select>
    </td> --%>
    <td align="right" style="padding-right:5px;"><input name="" type="button" onclick="setTab(3)" class="btn_search" value="查询" /></td> 
  </tr>
  </table>
  <div class="ge01"></div>
  <div class="tabpages">
    <ul class="l">
    <c:if test="${type=='1' }">
      <li class="current"  id="one1" onclick="setTab(1);">预警列表</li>
      <li  id="one2" onclick="setTab(2);">设置数据源阀值</li>
      </c:if>
      <c:if test="${type=='2' }">
      <li  id="one1" onclick="setTab(1);">预警列表</li>
      <li  class="current" id="one2" onclick="setTab(2);">设置数据源阀值</li>
      </c:if>
    </ul>
    <div class="otherButtons r"><c:if test="${type=='2' }"><a id="outTime2"><input name="" id="outTime1" onclick="javascript:window.location.href='<%=basePath%>sys/dataWarn/addWarn?addType=1'" type="button" class="btn_common01" value="新增" /></a> </c:if> </div>
  </div>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable" id="checkboxDemo">
  <tr>
    <th>序号</th>
    <th>数据源</th>
    <th colspan="2">阀值区间</th>
    <c:if test="${type=='1' }"><th>实际值</th></c:if>
    <th width="20%">创建时间</th>
  </tr>
  <c:forEach  items="${ITEMPAGE.items}" var="item" varStatus="i">
  <tr>
    <td>${i.count }</td>
    <td >${item.dataSource }</td>
    <td >${item.aviliablevalueGe }</td>
    <td >${item.aviliablevalueLe }</td>
    <c:if test="${type=='1' }">
    <td <c:if test="${item.value>item.aviliablevalueLe ||  item.value<item.aviliablevalueGe}"> style="color:red" </c:if>>${item.value }</td>
    </c:if>
    <td >${item.warnTime }</td>
    <tr>
  </c:forEach>
  
</table>
 <div class="gd_page">
   	 <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
 </div>
  <div class="ge01"></div>
</div>
  </form>         
<script src="/SRMC/dc/account/js/ui-select.js"></script>
<script type="text/javascript" >
	function setTab(cursel){
		if(cursel=="3"){
			document.forms[0].action.href= "<%=basePath%>sys/dataWarn/queryIndex";
	 		document.forms[0].submit();
		}else{
			window.location.href="<%=basePath%>sys/dataWarn/queryIndex?type="+cursel;
		}
	}
	
	// 将所有.ui-select实例化
	$(function (){
		$('.ui-select').ui_select();
		// 获取已经实例化的select对象
		var obj = $('#sel_api').data('ui-select');
	});
</script>  
</body>
</html>