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
  		<th width="9%" align="right">分类：</th>
	    <td width="30%">
	    	<select onchange="changeBusTypes('ROOT','#parentDatasourceId')" id="busTypes" name="busTypes" class="ui-select" style="width:180px;" defaultValue="${cooperationForm.busTypes }"  dictType="DATASOURCE_TYPE"/>
	    </td> 
  		
	    <th width="9%" align="right">用户：</th>
	    <td width="30%">
	    <input  onfocus="if(this.value=='请填写创建人'){this.value=''};this.style.color='#333333';autoCompletes();" 
			                     onkeyup="clean()"
			                     onblur="if(this.value==''||this.value=='请填写创建人'){jQuery('#userid_input').val('');this.style.color='#b6b6b6';}"
                      name="username" id="username" value="${cooperationForm.username}" type="text" class="text01" style="width:174px;" placeholder="请填写创建人">
	    </td> 
	</tr>
	<tr>
		<th width="9%" align="right">名称：</th>
	    <td width="30%">
	    	<select onchange="changeBusTypes(this,'#datasourceId')" class="ui-select" id="parentDatasourceId" name="parentDatasourceId" style="width:180px;">
				<option <c:if test="${empty cooperationForm.parentDatasourceId }">selected="selected"</c:if> value="">请选择</option>
	    		<c:forEach items="${parentDatasourceList}" var="item" varStatus="i">
    				<option ${cooperationForm.parentDatasourceId ==item.datasourceId ? "selected=\"selected\"":null}  value="${item.datasourceId}">${item.datasourceName}</option>
				</c:forEach>
	        </select>
	    </td>
	    <th width="9%" align="right"></th>
	    <td width="30%">
	    	<select class="ui-select" id="datasourceId" name="datasourceId" style="width:180px;">
				<option <c:if test="${empty cooperationForm.datasourceId }">selected="selected"</c:if> value="">请选择</option>
	    		<c:forEach items="${datasourceTypeList}" var="item" varStatus="i">
    				<option ${cooperationForm.datasourceId ==item.datasourceId ? "selected=\"selected\"":null}  value="${item.datasourceId}">${item.datasourceName}</option>
				</c:forEach>
	        </select>
	    </td>
	</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
      		<li class="current">数据权限列表<em>(${ITEMPAGE.total })</em></li>
    	</ul>
    	<div class="otherButtons r">
<!--     		<input name="" id="" type="button" class="btn_common01" value="给用户配置数据权限" /> -->
    		<input name="" id="addDSConfigInput" type="button" class="btn_common01" value="新增数据源配置" />
			<input name="" id="addYBConfigInput" type="button" class="btn_common01" value="新增业务报表配置" />
<!-- 			<input id="uploadFiles" name="fileName" type="file" class="btn_common01" value="导 入"/> -->
		</div>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	    <th>序号</th>
	    <th>分类</th>
	    <th>子分类</th>
	    <th>名称</th>
	    <th>权限</th>
	    <th>用户</th>
	    <th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="tr_id_${item[0].typeRoleUserId}">
	  	  	<td >${i.count}</td>
		    <td >
		    	<jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="BUS_TYPE"></jsp:param>
					<jsp:param name="paramCode" value="${item[1].busTypes}"></jsp:param>
				</jsp:include> 
		    </td>
		    <td >${item[1].parentDatasourceName}</td>
		    <td >${item[1].datasourceName}</td>
		    <td >${item[2].roleName}</td>
		    <td >
		    ${item[0].userList}
		    
		    </td>
		    <td >
		    	<i><a class="fancybox_Configure" id="ia_${item[0].typeRoleUserId}" bustype="${item[1].busTypes}" value="${item[1].datasourceId}" href="javascript:void(0)">配置</a></i>
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
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<!--文件上传样式，js -->
<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/ex_import.js"></script> 

<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<jsp:include page="../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="DATASOURCE_TYPE"></jsp:param>
</jsp:include>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/common.js"></script>
<script type="text/javascript">
$(function(){

});
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	//配置弹窗初始化
	datasourceConfigure();
	//新增配置
	iframeDialog("addDSConfigInput", basePath + "/datasourceConfigure?id=&bustype=DS");
	iframeDialog("addYBConfigInput", basePath + "/datasourceConfigure?id=&bustype=YB");
	
	setConfig("<%=basePath%>copperationController/importDsConfigure");
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>copperationController/queryPrivilges";
	document.forms[0].submit();
}

//用户可选择创建人
function autoCompletes(){
		jQuery("#username").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>rulesController/searchUser",
					dataType: "json",
					data: {
						userValue: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
				     			 value:item[0].userName+" -- "+item[1].orgName,
									userName:item[0].userName,
									userId:item[0].userId,
									account:item[0].account,
									orgName:item[1].orgName,
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
				jQuery("#username").val(ui.item.userName);
				jQuery("#userid_input").val(ui.item.userId);
				return false;
			}
		});
	}
function clean(){
	if (event.keyCode == 13) {
        //js 监听对应的id
         //document.getElementById("demandFzrId_input_focus").value="";
      } 
}

</script>
</body>
</html>
