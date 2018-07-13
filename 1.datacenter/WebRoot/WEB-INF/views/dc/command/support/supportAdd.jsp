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
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
</head>

<body>

	<!-- 遮罩内容开始 -->

	<div class="taskCreatBox">
            <div class="box_title" style="color:#4084b6;">
                	支撑单位新增
            </div>
            <div class="box_task">
            <table cellpadding="0" cellspacing="5" id="table01" class="taskCreate" width="100%">
            	<form action=""  method="post"  id="form1">
					<input type="hidden" name="supportorgId" id="supportorgId" value="${supportorg.supportorgId}"/>
	            <tr>
	               <th width="15%" align="left" style="color:#595959;"><em>*</em>支撑单位名称：</th>
	               <td width="65%" align="left">
	               <input id="supportorgName" name="supportorgName" value="${supportorg.supportorgName}"  type="text" class="text01" ></input>
	               </td>
	            </tr>
		 	    </form>
             	<tr>
	               <td colspan="2" align="center">
	               <input name="" type="button" class="btn_common02" onclick="ev_submit()" value="提 交" /></td>
              	</tr>
            </table>
          	</div>
        </div>
<!-- 遮罩内容结束 -->
</body>
</html>

<script type="text/javascript">
$(function(){

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
	$("#"+focusId).parent().append('<em class="noselected_tips">'+msg+'！</em>');
}

function checkSubmit(){

	//处理意见
	if( jQuery.trim(jQuery("#supportorgName").val())=="" ){
		animateAlert("table01","supportorgName", "支撑单位名称不能为空" );
		return false;
	}
	if( $("#supportorgName").val().length > 200 ){
		animateAlert("table01","supportorgName","支撑单位名称不得超过200个字" );
		return false;
	}
	return true;
}

function ev_submit(){
	if(!checkSubmit()){
		return;
	}
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>support/supportSave",
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