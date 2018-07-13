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
	<script type="text/javascript" src="/SRMC/dc/rules/js/common.js"></script>
	
	<style>
		#uploadifive-uploadFiles4{
    		margin-left: 84%;
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
         <div class="box_task" >
            <table width="99%" id="table01" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable"  align="center" style="border:0px;">
			   <form action=""  method="post"  id="form1">
			   	<input type="hidden" name="fileId" id="fileId_input" value="${rulesFileUpload.fileId}"/>
			   	<input type="hidden" name="rulesId" id="rulesId_input" value="${rulesInfo.rulesId}"/>
			   	<input type="hidden" name="fileTempId" id="fileTempId" value="${rulesInfo.rulesId}"/>
			   	<input type="hidden" name="isParent" value="1"/>
			   	<input type="hidden" name="type" value="4"/>
			   	<input type="hidden" name="status" value="1"/>
			   <tr>
				    <th width="15%" align="right"><b>*</b>名称：</th>
				    <td><input name="abstractName" id="abstractName" value="${rulesFileUpload.abstractName}" type="text" class="text01" style="width:96%;"/></td>
			   </tr>
			  <tr id="fi_box_tr_4">
				    <th align="right"><b>*</b>上传文件：<br/></th>
				    <td>
				    	<div class="text01" style="width:96%;" id="fi_box_div_4" value="4">
					    	<input name="fileName" id="uploadFiles4" type="file" class="btn_upload" style="display: none">
			            </div>
			            <b>该制度相关的文档，如省公司下发制度、新发的相关通知、制度相关图表等</b>
				    </td>
			    </tr>
			  </form>
			 <tr>
			   	 <th colspan="2" align="center" height="50">
			   	 	<input name="" type="button" class="btn_common02" id="submit" onclick="ev_submit();" value="提 交" /></td>
					<input name="" type="button" class="btn_common02" onclick="ev_cancel()" value="返 回" />
				 </th>
			 </tr>    
 		 </table>
             
          </div>
        </div>
<!-- 遮罩内容结束 -->
</body>
</html>

<script type="text/javascript">
$(function(){
	var fileTempId = jQuery("#fileTempId").val();
	config_4.formData.fileTempId = fileTempId;
	config_4.formData.type = "4";
	jQuery('#uploadFiles4').uploadifive(config_4);
// 	queryFileList("#uploadFiles4");
});
$(document).ready(function(){
	
});
//滚动页面并弹框
function animateAlert(id,focusId,msg){
	//清除未选择，未填写的提示
	jQuery("html,body").find(".noselected_tips").remove();
	//滚动页面至表格
	jQuery("html,body").animate({scrollTop:jQuery("#"+id).offset().top},10);
	//提示
	$("#"+focusId).parent().append('<b class="noselected_tips">'+msg+'！</b>');
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
	
	if($("#table01").find(".fj_p4").length <= 0){
		animateAlert("table01","fi_box_div_4","请至少上传一个文件" );
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
       	url:"<%=basePath%>rulesController/documentsRules",
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

function ev_cancel(){
	//制度相关文档保存
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>rulesController/docCancel",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result == 1){
	        	parent.window.location.reload();
        	}else{
        		//alert("提交失败");
        		parent.window.location.reload();
        	}
        },
        error:function () {
        	//alert("提交失败");
        }
    });
}
</script>