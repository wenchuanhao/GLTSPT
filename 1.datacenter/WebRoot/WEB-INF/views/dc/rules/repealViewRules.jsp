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
                	制度废止
            </div>
            <div class="box_task">
            <table cellpadding="0" cellspacing="5" id="table01" class="taskCreate" width="100%">
            	<form action=""  method="post"  id="form1">
					<input type="hidden" name="rulesId" id="rulesId_input" value="${rulesId}"/>
	            <tr>
	               <th width="15%" align="right" style="color:#595959;"><em>*</em>废止原因：</th>
	               <td align="left"><textarea id="handelOpinion" name="handelOpinion" cols="" rows="" class="textArea01"></textarea></td>
	            </tr>
		 	    </form>
             	<tr>
	               <td colspan="2" align="center">
	               <input name="" type="button" class="btn_common02" id="submit" onclick="ev_submit()" value="提 交" /></td>
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
	$("#"+focusId).parent().append('<em class="noselected_tips"><br/>'+msg+'！</em>');
}

function checkSubmit(){

	//申请事由和业务说明
	if( jQuery.trim(jQuery("#handelOpinion").val())=="" ){
		animateAlert("table01","handelOpinion", "废止原因不能为空" );
		return false;
	}
	if( $("#handelOpinion").val().length > 200 ){
		animateAlert("table01","handelOpinion","废止原因不得超过200个字" );
		return false;
	}
	return true;
}

function ev_submit(){
	if(!checkSubmit()){
		return;
	}
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>rulesController/repealRules",
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