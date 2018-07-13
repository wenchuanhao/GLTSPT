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
	<!--文件上传样式，js -->
	<!-- <link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script> -->
	
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadifive/uploadifive.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/jquery.uploadifive.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/uploadcommon.js"></script>
	
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
	<!--公共JS -->
	<script type="text/javascript" >var basePath = "<%=basePath%>rulesController";var isCcFlag = "1";</script>
	<script type="text/javascript" src="/SRMC/dc/rules/js/docupload.js"></script>
	<style>
		#uploadifive-uploadFiles{
    		margin-left: 80%;
    		border: 0;		
		}
	</style>
</head>

<body>


	<!-- 遮罩内容开始 -->

	<div class="taskCreatBox">
         <div class="box_title" style="color:#4084b6;">
             	上传文档<span></span>
         </div>
         <div class="box_task">
            <table width="99%" id="table01" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" style="border:0px;">
			   <form action=""  method="post"  id="form1">
			   	<input type="hidden" name="fileId" id="fileId_input" value="${rulesFileUpload.fileId}"/>
			   	<input type="hidden" name="fileTempId" id="fileTempId" value="${fileTempId}"/>
			   	<input type="hidden" name="isParent" value="1"/>
			   	<input type="hidden" name="types" id="types_input" value="${rulesFileUpload.types}"/>
			   	<input type="hidden" id="busTypes_input" name="busTypes" value="${busTypeName }" />
			   	<input type="hidden" name="status" value="1"/>
			   <tr>
				    <th width="16%" align="right"><b>*</b>名称：</th>
				    <td><input name="abstractName" id="abstractName" value="${rulesFileUpload.abstractName}" type="text" class="text01" style="width:500px;"/></td>
			   </tr>
			   <tr>
				    <th width="16%" align="right"><b>*</b>文档分类：</th>
				    <td id="sel_01_td">
				    	<input id="busTypes" name="busTypesValue" value="${busTypesValue }" readonly="readonly" type="text"  class="text01" style="width:215px;"  />
				    	<select class="ui-select" id="sel_01" style="width:280px;">
								<option <c:if test="${empty rulesFileUpload.types }">selected="selected"</c:if> value="">请选择</option>
					          	<c:forEach items="${busTypesList}" var="item" varStatus="i">
					    			<option ${rulesFileUpload.types==item.typeId ? "selected=\"selected\"":null}  value="${item.typeId}">${item.typeName}</option>
								</c:forEach>
				        </select>
				    </td>
			   </tr>
			  <tr id="fi_box_tr">
				    <th align="right"><b>*</b>上传文件：<br/></th>
				    <td>
				    	<div class="text01" style="width:500px;" id="fi_box_div">
				    	<c:if test="${userRoles != '3' }">
					    	<input name="fileName" id="uploadFiles" type="file" class="btn_upload" style="display: none">
				    	</c:if>
			            </div>
				    </td>
			    </tr>
			  </form>
			 <tr>
			   	 <th colspan="2" align="center" height="50">
			   	 <c:if test="${userRoles != '3' }">
			   	 	<input name="" type="button" class="btn_common02" id="submit" onclick="ev_submit();" value="提 交" /></td>
			   	 </c:if>
					<input name="" type="button" class="btn_common02" onclick="javascript:parent.$.fancybox.close();" value="返 回" />
				 </th>
			 </tr>    
 		 </table>
             
          </div>
        </div>
<!-- 遮罩内容结束 -->
</body>
</html>

<script type="text/javascript">
//登录用户ID
var loginUserId = "${sessionScope.VISITOR.userId}";
//用户角色
var userRoles = "${userRoles}";
$(function(){
	var fileTempId = jQuery("#fileTempId").val();
	console.log(basePath);
	config.formData.fileTempId = fileTempId;
	config.formData.type = jQuery("#types_input").val();
	config.formData.busTypes = jQuery("#busTypes_input").val();
	jQuery('#uploadFiles').uploadifive(config);
	queryFileList("#uploadFiles");
});
$(document).ready(function(){
	$('.ui-select').ui_select();
	    //文档类型
	var obj1 = $('#sel_01').data('ui-select');
	obj1.onClick = function(value) {
		 $("#types_input").val(value);
	}
});
//滚动页面并弹框
function animateAlert(id,focusId,msg){
	//清除未选择，未填写的提示
	jQuery("html,body").find(".noselected_tips").remove();
	//滚动页面至表格
	jQuery("html,body").animate({scrollTop:jQuery("#"+id).offset().top},10);
	//提示
	if(focusId == "sel_01"){
		$("#"+focusId).parent().parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}else{
		$("#"+focusId).parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}
}

function checkSubmit(){

	//文档名称
	if( jQuery.trim(jQuery("#abstractName").val())=="" ){
		animateAlert("table01","abstractName", "名称不能为空" );
		return false;
	}
	if( $("#abstractName").val().length > 30 ){
		animateAlert("table01","abstractName","名称不得超过30个字" );
		return false;
	}
	if( jQuery.trim(jQuery("#types_input").val())=="" ){
		animateAlert("table01","sel_01", "你选择文档分类" );
		return false;
	}
	
	if($("#table01").find(".fj_p").length <= 0){
		animateAlert("table01","fi_box_div","请至少上传一个文件" );
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
       	url:"<%=basePath%>rulesController/docSubmit",
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
</script>