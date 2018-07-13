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
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
		<link rel="stylesheet" type="text/css" href="/SRMC/dc/css/combotree/easyui.css">
		<script type="text/javascript" src="/SRMC/dc/js/combotree/jquery.easyui.min.js"></script>  
		<script type="text/javascript">
			var j = jQuery.noConflict(true);
		</script>
		<link href="/SRMC/rmpb/css/date_input.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<title>信息补录列表</title>
</head>
<body>
<form action="<%=basePath%>account/infoCollectIndex?type=${type}" method="post" id="pageForm">
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${accountForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${accountForm.pageSize}" id="pageSize"	name="pageSize"/>
     <input type="hidden" value="${type}" id="type"	name="type"/>
     <input type="hidden" name="department" id="department" value="${accountForm.department}"/>
     <input type="hidden" name="costType" id="costType" value="${accountForm.costType}"/>
     
	<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
		<input name="" type="button" onclick="setTab(3)"  class="btn_search r" value="查 询" />
	</div>
	  <table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	  <tr>
	    <th width="7%" align="right">费用类型：</th>
	    <td width="17%">
	    <div class="checxTreeWrap"  style="width:170px;">
		    <%-- <input id="cosId" name="cosId" value="${accountForm.costType}"/>  --%>
		    <select  multiple style="width:200px" id="cosId" name="cosId" class="easyui-combotree" data-options="url:'<%=basePath%>/account/getCostType',method:'get'" multiple style="width:180px;">
		    <option selected="selected">${accountForm.costType}</option>
		    </select>
		    </div>
          </td>
	    <th width="7%" align="right">报账部门：</th>
	    <td width="17%">
	  	<div class="checxTreeWrap"  style="width:170px;">
		    <select multiple style="width:185px" id="departmentId" name="departmentId" class="easyui-combotree" data-options="url:'<%=basePath%>/account/getDepartment',method:'get'" >
		    <option selected="selected">${accountForm.department}</option>
		    </select>
		    </div>
	    </td>
	      <td width="20%"><input  name="people" id="people" type="text"   placeholder="报账单号或报账人" value="${people }" class="text01" style="width:180px;"   </td> 
	  </tr>
	  <tbody style="display:block;" id='submenuboard1'>
  </tbody>   
</table>
  <div class="ge01"></div>
  <div class="tabpages">
    <ul class="l">
      <%-- <%-- <li <c:if test="${type==1 }">class="current"</c:if> id="one1" onclick="setTab(1);">待办报账单据列表</li>
      <li  <c:if test="${type==2 }">class="current"</c:if> id="one2" onclick="setTab(2);">已办报账单据列表</li> --%>
      <li <c:if test="${type==1 }">class="current"</c:if> id="one1" onclick="setTab(1);">未补录报账单据列表</li>
      <li  <c:if test="${type==2 }">class="current"</c:if> id="one2" onclick="setTab(2);">已补录报账单据列表</li>
    </ul>
    <div class="otherButtons r"> <input name="" type="button" onclick="window.location.href='<%=basePath%>account/importInfoCollect'" class="btn_common01" value="信息补录" /> </div>
  </div>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
  <tr>
    <th>序号</th>
    <th>报账单号</th>
    <th>报账部门</th>
    <th>费用类型</th>
    <th>报账人</th>
    <th>初审会计</th>
    <th>日期</th>
    <th>信息补录时间</th>
    <th>所处环节</th>
  </tr>
  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
  <tr <c:if test="${i.index % 2==0}" >class="td01"</c:if>>
    <td >${i.index+1 }</td>
    <td ><a style="color:#005ea7;" href="<%=basePath%>account/trailDetail?source=infoCollectIndex&sourceType=${type}&orderId=${item.orderId}&type=2&id=${item.id}" >${item.orderId }</a></td>
    <td >${item.department }</td>
    <td >${item.costType }</td>
    <td >${item.sementPeople }</td>
    <td >${item.trialAccount }</td>
    <td ><fmt:formatDate value="${item.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    <td >
    	<c:if test="${ empty item.recordDate }">-</c:if> 
    	<c:if test="${not empty item.recordDate }"><fmt:formatDate value="${item.recordDate }" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>
    </td>
    <td ><span>${ item.currentLink }</span></td>
  </tr>
  </c:forEach>
</table>
 <div class="gd_page">
      <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
 </div>
  <div class="ge01"></div>
</div>
</form>           
<script src="/SRMC/dc/account/js/jquery.js"></script>
<script src="/SRMC/dc/account/js/ui-select.js"></script>
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script  type="text/javascript">
	function clean(){
		if (event.keyCode == 13) {
        } 
	}
	
//部门联想
function autoDepartment(){
	jQuery("#sel_02").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>account/queryDepartment",
					dataType: "json",
					data: {
						Value: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
				     				value:item[0].userName,//+" - "+item[0].account+" - "+item[1].orgName
									userName:item[0].userName,
									userId:item[0].userId,
									account:item[0].account,
									orgName:item[1].orgName
								};
							}));
						}else{
							return false;
						}
					}
				});
			},
			minLength: 1,
			select: function( event, ui ) {
				jQuery("#sel_02").val(ui.item.userName);
				jQuery("#departmentId_input").val(ui.item.userId);
				return false;
			}
		});
		if(jQuery("#sel_02").val()!=null && jQuery("#sel_02").val()!=""){
		}
}

function setTab(cursel){
		jQuery("#costType").val(j("#cosId").combotree("getText"));
		jQuery("#department").val(j("#departmentId").combotree("getText"));	
		var ss = jQuery("#type").val(cursel);
		
		if(cursel=="3"){
			document.forms[0].submit();
		}else{
		
			window.location.href="<%=basePath%>account/infoCollectIndex?type="+cursel;
		}		
}

$(function (){
	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	// 获取已经实例化的select对象
	var obj = $('#sel_api').data('ui-select');
	
	j("#departmentId").combotree({
		onClick: function(node) {
			if (!j(this).tree('isLeaf', node.target)) {
				j('#departmentId').combo('showPanel');
			}
		},
		
   	    url:'<%=basePath%>/account/getDepartment' ,
   		width:170,
   		lines:'true',
		checkbox:true ,
		cascadeCheck:false,
		required:false,
		editable:false,
		multiple:false//设置是否多选	
		
	       });  
	       
	       j("#cosId").combotree({
	       onBeforeSelect: function(node) {
			if (!j(this).tree('isLeaf', node.target)) {
				return false;
			}
		},
		onClick: function(node) {
			if (!j(this).tree('isLeaf', node.target)) {
				j('#cosId').combo('showPanel');
			}
		},
		
   	    url:'<%=basePath%>/account/getCostType' ,
   		width:170,
   		lines:'true',
		checkbox:true ,
		cascadeCheck:false,
		required:false,
		editable:false,
		multiple:false//设置是否多选	
		
	       }); 
});

</script>                
</body>
</html>