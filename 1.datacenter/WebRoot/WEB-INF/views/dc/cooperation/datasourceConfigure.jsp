<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
	<!--公共JS -->
	<script type="text/javascript" >var basePath = "<%=basePath%>copperationController";var isCcFlag = "1";</script>
	<!-- 联想查询 -->
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript" src="/SRMC/dc/cooperation/js/common.js"></script>
</head>

<body>

<div class="gl_import_m" style="min-width: 100px;">

	<!-- 遮罩内容开始 -->

	<div class="taskCreatBox" style="width:700px;">
         <div class="box_title" style="color:#4084b6">
             	数据权限配置<span></span>
         </div>
         <div class="box_task">
		   <form action=""  method="post"  id="form1">
            <table width="99%" id="table01" border="0" cellspacing="0" cellpadding="0" class="editTable" align="center" style="border:0px;">
			   <input type="hidden" value="${busTypes }" name="busTypes" id="busTypes"/>
			   <tr>
				    <th width="16%" align="right" style="color:#595959"><b>*</b>${busTypesName }名称：</th>
				    <td id="sel_01_td">
				    	<select onchange="changeBusTypes(this,'#datasourceId')" class="ui-select" id="parentDatasourceId" name="parentDatasourceId" style="width:180px;">
							<option <c:if test="${empty parentDatasourceId }">selected="selected"</c:if> value="">请选择</option>
				    		<c:forEach items="${parentDatasourceList}" var="item" varStatus="i">
			    				<option ${parentDatasourceId ==item.datasourceId ? "selected=\"selected\"":null}  value="${item.datasourceId}">${item.datasourceName}</option>
							</c:forEach>
				        </select>
				    	<select onchange="changeDatasourceIdLoad(this)"  class="ui-select" id="datasourceId" name="datasourceId" style="width:180px;">
							<option <c:if test="${empty datasourceId }">selected="selected"</c:if> value="">请选择</option>
				    		<c:forEach items="${datasourceTypeList}" var="item" varStatus="i">
			    				<option ${datasourceId ==item.datasourceId ? "selected=\"selected\"":null}  value="${item.datasourceId}">${item.datasourceName}</option>
							</c:forEach>
				        </select>
				    </td>
			   </tr>
			   
			    <tr <c:if test="${busTypes == 'YB'}">style="display: none;"</c:if>>
				    <th width="16%" align="right" style="color:#595959"><b>*</b>录入员：</th>
				    <td>
					    <input value="" name="importUser" type="text" class="text01" id="importUser"  onfocus="autoCompletes(this);" onkeyup="clean(this)"/>
						<span id="selected_importUser">
							<c:forEach items="${importerList}" var="item" varStatus="i">
								<a href="javascript:void(0);" class="" id="${item.userId }">
									<input type="hidden" id="importUserId" name="importUserId" value="${item.userId }">
									<span style="padding: 0 3px 0 0;">${item.userName }</span>
									<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10">
								</a>
							</c:forEach>
						</span>
				    </td>
			   </tr>
			    <tr <c:if test="${busTypes == 'YB'}">style="display: none;"</c:if>>
				    <th width="16%" align="right" style="color:#595959"><b>*</b>审核员：</th>
				    <td>
						<input value="" name="remainUser" type="text" class="text01" id="remainUser"  onfocus="autoCompletes(this);" onkeyup="clean(this)"/>
						<span id="selected_remainUser">
							<c:forEach items="${remainerList}" var="item" varStatus="i">
								<a href="javascript:void(0);" class="" id="${item.userId }">
									<input type="hidden" id="remainUserId" name="remainUserId" value="${item.userId }">
									<span style="padding: 0 3px 0 0;">${item.userName }</span>
									<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10">
								</a>
							</c:forEach>
						</span>
					</td>
			   </tr>
			    <tr>
				    <th width="16%" align="right" style="color:#595959">查询员：</th>
				    <td>
				    	<input value="" name="queryUser" type="text" class="text01" id="queryUser"  onfocus="autoCompletes(this);" onkeyup="clean(this)"/>
						<span id="selected_queryUser">
							<c:forEach items="${queryerList}" var="item" varStatus="i">
								<a href="javascript:void(0);" class="" id="${item.userId }">
									<input type="hidden" id="queryUserId" name="queryUserId" value="${item.userId }">
									<span style="padding: 0 3px 0 0;">${item.userName }</span>
									<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10">
								</a>
							</c:forEach>
						</span>
					</td>
			   </tr>
			   
			 <tr>
			   	 <th colspan="2" align="center" height="50">
			   	 	<input name="" type="button" class="btn_common02" id="" onclick="ev_submit();" value="提 交" /></td>
					<input name="" type="button" class="btn_common02" onclick="javascript:parent.$.fancybox.close();" value="返 回" />
				 </th>
			 </tr>    
 		 </table>
	  </form>
          </div>
        </div>
<!-- 遮罩内容结束 -->
</div>
</body>
</html>

<script type="text/javascript">
$(function(){

});
$(document).ready(function(){
	$('.ui-select').ui_select();
});

//用户可选择创建人
function autoCompletes(target){
	jQuery(target).autocomplete({
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
			if(jQuery("#selected_"+$(target).attr('id')).children("[id='" + ui.item.userId + "']").length != 0){
				jQuery(target).val("");
				return;
			}else{
				jQuery("#selected_"+$(target).attr('id')).append('<a href="javascript:void(0);" class="" id="' + ui.item.userId + '">\
																	<input type="hidden" id="'+$(target).attr('id')+'Id" name="'+$(target).attr('id')+'Id" value="' + ui.item.userId + '"/>\
																	<span style="padding: 0 3px 0 0;">' + ui.item.userName + '</span>\
																	<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10" />\
																  </a>');
																	
																	 
				jQuery(target).val("");
				return false;
			}
		}
	});
}

function clean(target){
	if (event.keyCode == 13) {   
        //js 监听对应的id   
//          document.getElementById("ccUserSearch1").value="";
		$(target).val("");
    } 
}

//滚动页面并弹框
function animateAlert(id,focusId,msg){
	//清除未选择，未填写的提示
	jQuery("html,body").find(".noselected_tips").remove();
	//滚动页面至表格
	jQuery("html,body").animate({scrollTop:jQuery("#"+id).offset().top},10);
	//提示
	if(focusId == "datasourceId" || focusId=="parentDatasourceId"){
		$("#"+focusId).parent().parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}else{
		$("#"+focusId).parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}
}

function checkSubmit(){

	if( jQuery.trim(jQuery("#parentDatasourceId").val())=="" ){
		animateAlert("table01","parentDatasourceId", "你选择一级分类" );
		return false;
	}
	if( jQuery.trim(jQuery("#datasourceId").val())=="" ){
		animateAlert("table01","datasourceId", "你选择二级分类" );
		return false;
	}
	//录入员不能为空
	if($("#busTypes").val() == "DS" && jQuery.trim(jQuery("#selected_importUser").html())=="" ){
		animateAlert("table01","selected_importUser", "录入员不能为空" );
		return false;
	}
	//审核员selected_remainUser
	if($("#busTypes").val() == "DS" && jQuery.trim(jQuery("#selected_remainUser").html())=="" ){
		animateAlert("table01","selected_remainUser", "录入员不能为空" );
		return false;
	}
	return true;
}

function ev_submit(){
	if(!checkSubmit()){
		return;
	}
	//制度相关文档保存
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>copperationController/configureSubmit",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result == 1){
	        	parent.window.location.reload();
        	}else{
        		alert("提交失败");
        	}
        },
        error:function () {
        	alert("提交失败");
        }
    });
}

function changeDatasourceIdLoad(target){
	var datasourceId = $(target).val();
	var busTypes = $("#busTypes").val();
	jQuery.ajax({
		url: "<%=basePath%>copperationController/loadConfigById",
		data: {
			datasourceId:datasourceId,
			busTypes:busTypes
		},
		dataType: "json",
		type: "POST",
		success: function(data) {
			if(data && busTypes == "DS"){
				//录入员
				var temp = '';
				$.each(data.importerList,function(k,v){
					temp += '<a id="'+v.userId+'" class="" href="javascript:void(0);">\
								<input type="hidden" value="'+v.userId+'" name="importUserId" id="importUserId">\
								<span style="padding: 0 3px 0 0;">'+v.userName+'</span>\
								<img width="10" height="10" src="/SRMC/dc/images/close_icon01.png" onclick="jQuery(this).parent().remove()">\
							</a>';
				});
				$("#selected_importUser").html(temp);
				//审核员
				temp = '';
				$.each(data.remainerList,function(k,v){
					temp += '<a id="'+v.userId+'" class="" href="javascript:void(0);">\
								<input type="hidden" value="'+v.userId+'" name="remainUserId" id="remainUserId">\
								<span style="padding: 0 3px 0 0;">'+v.userName+'</span>\
								<img width="10" height="10" src="/SRMC/dc/images/close_icon01.png" onclick="jQuery(this).parent().remove()">\
							</a>';
				});
				$("#selected_remainUser").html(temp);
				//查询员
				temp = '';
				$.each(data.queryerList,function(k,v){
					temp += '<a id="'+v.userId+'" class="" href="javascript:void(0);">\
								<input type="hidden" value="'+v.userId+'" name="queryUserId" id="queryUserId">\
								<span style="padding: 0 3px 0 0;">'+v.userName+'</span>\
								<img width="10" height="10" src="/SRMC/dc/images/close_icon01.png" onclick="jQuery(this).parent().remove()">\
							</a>';
				});
				$("#selected_queryUser").html(temp);
			}
			if(data && busTypes == "YB"){
				//查询员
				var temp = '';
				$.each(data.queryerList,function(k,v){
					temp += '<a id="'+v.userId+'" class="" href="javascript:void(0);">\
								<input type="hidden" value="'+v.userId+'" name="queryUserId" id="queryUserId">\
								<span style="padding: 0 3px 0 0;">'+v.userName+'</span>\
								<img width="10" height="10" src="/SRMC/dc/images/close_icon01.png" onclick="jQuery(this).parent().remove()">\
							</a>';
				});
				$("#selected_queryUser").html(temp);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
		}
	});
}
</script>