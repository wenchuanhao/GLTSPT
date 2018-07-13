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
<title>审核不通过</title>
		<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
			<!-- 	表单异步提交 -->
		<script src="/SRMC/rmpb/js/jquery.form.js"></script>
</head>
<body>
<!-- 遮罩内容开始 -->
<div class="taskCreatBox">
            <div class="box_title" style="color:#4084b6;">
               	 审核不通过<span></span>
            </div>
            <div class="box_task">
           	<form action=""  method="post"  id="form1">
            <table id="table01" cellpadding="0" cellspacing="15" class="taskCreate" width="100%">   
					<input type="hidden" name="id" id="id" value="${id}"/>
	            <tr>
	               <th width="20%" align="right"><em>*</em>审核意见：</th>
	               <td align="left"><textarea id="auditDesc" name="auditDesc" cols="" rows="" class="textArea01" style="width:480px;height:150px;"></textarea></td>
	            </tr>
	            <tr>
	               <td colspan="2" align="center">
	               <input name="" type="button" onclick="ev_submit()" class="btn_common02" value="保存" />  
	               <input name="" onclick="javascript:parent.$.fancybox.close();" type="button" class="btn_common01" value="取消" />
	               </td>
                </tr>
            </table>
            </form>
          </div>
        </div>
<!-- 遮罩内容结束 -->

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
	$("#"+focusId).parent().append('<em class="noselected_tips"><br/>'+msg+'！</em>');
}

function checkSubmit(){

	//处理意见
	if( jQuery.trim(jQuery("#auditDesc").val())=="" ){
		animateAlert("table01","auditDesc", "审核意见不能为空" );
		return false;
	}
	if( $("#auditDesc").val().length > 200 ){
		animateAlert("table01","auditDesc","审核意见不得超过200个字" );
		return false;
	}
	return true;
}

function ev_submit(){
	if(!checkSubmit()){
		return;
	}
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>zbremain/returnRemain",
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
</body>
</html>