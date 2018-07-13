<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath() + "/"; 
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
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script>
<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
	<!--公共JS -->
	<script type="text/javascript" >var basePath = "<%=basePath%>rulesController";var isCcFlag = "1";</script>
	<script type="text/javascript" src="/SRMC/dc/rules/js/common.js"></script> 
</head>

<body>

	<!-- 遮罩内容开始 -->

	<div class="taskCreatBox">
		  	<div class="tabpages">
		            <ul class="l">
				      		<li class="current" >文件（指令）归档<em></em></li>
			    	</ul>
			</div>
            <div class="box_task">
             	<table width="99%" id="table01" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable"  align="center" style="border:0px;">
					   <form action=""  method="post"  id="form1">
					   	<input type="hidden" name="folderId" id="folderId_input" value="${vo.folderId}"/>
					   	<input type="hidden" name="fileTempId" id="fileTempId" value="${vo.folderId}"/>
					   	<input type="hidden" name="commandInfoid" id="commandInfoid_input" value="${vo.commandInfoid}"/>
					   	<input type="hidden" name="isParent" value="0"/>
					   	<input type="hidden" name="type" value="12"/>
					   <tr>
						    <th width="15%" align="right"><b>*</b>存放位置：</th>
						    <td><input name="folderPosition" id="folderPosition" value="${vo.folderPosition}" type="text" class="text01" style="width:96%;"/></td>
					   </tr>
					  <tr id="fi_box_tr_12">
						    <th align="right"><b>*</b>上传文档（指令）扫描件：<br/></th>
						    <td>
						    	<div class="text01" style="width:96%;" id="fi_box_div_12" value="12">
							    	<input name="fileName" id="uploadFiles12" type="file" class="btn_upload" style="display: none">
					            </div>
						    </td>
					    </tr>
					    
					    <tr>
						    <th width="15%" align="right"><b>*</b>摘要：</th>
						    <td>
						    	<textarea name="digEst" id=digEst clos=",20" rows="5" warp="virtual" value="${vo.digEst}" class="text01" style="width:96%; height:150px;"></textarea>
						    </td>
					   </tr>

					<c:forEach items="${folders}" var="item" varStatus="j">
						<tr class="fj_p12" value="12" id="${item.fileId }">
							<th align="right">&nbsp;</th>
							<td align="left">
								<div class="fileName l">
									<a href="<%=basePath%>rulesController/downloadRulesFile?fileId=${item.fileId }">${item.fileName } 
										<c:if test="${item.fileSize / 1024 > 1024}">
							    			(<fmt:formatNumber type="number" value="${item.fileSize / 1024 / 1024}" maxFractionDigits="2" />MB)
						    			
						    			</c:if> 
						    			<c:if test="${item.fileSize / 1024 <= 1024}">
							    			(<fmt:formatNumber type="number"
															value="${item.fileSize / 1024}" maxFractionDigits="2" />KB)
						    			</c:if> 
			    					</a>
								</div>
								<i>
									<img onclick="javascript:deleteFileUpload('${item.fileId }')" src="/SRMC/dc/images/close_icon01.png" width="17" height="17">
								</i>
							</td>
						</tr>
					</c:forEach>
				</form>
					 <tr>
					   	 <th colspan="2" align="center" height="50">
					   	 	<input name="" type="button" class="btn_common02" onclick="ev_submit();" value="提 交" /></td>
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
	config.scriptData.fileTempId = fileTempId;
	config.scriptData.type = "12";
	config.scriptData.busType = "ZL";
	jQuery('#uploadFiles12').uploadify(config);
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
	var pattern = /^[\u4E00-\u9FA5]$/;
	//文档名称
	if( jQuery.trim(jQuery("#folderPosition").val())=="" ){
		animateAlert("table01","folderPosition", "存放位置不能为空" );
		return false;
	}
	if( $("#folderPosition").val().length > 30 ){
		animateAlert("table01","folderPosition","存放位置不得超过30个字" );
		return false;
	}
	
	if($("#table01").find(".fj_p12").length <= 0){
		animateAlert("table01","fi_box_div_12","请至少上传一个扫描件" );
		return false;
	}
	
	//摘要digEst
	if(jQuery.trim(jQuery("#digEst").val())==""){
		animateAlert("table01","digEst", "存放位置不能为空" );
		return false;
	}	
	if( $("#digEst").val().length < 40  && $("#digEst").val().length==pattern){
		animateAlert("table01","digEst","存放位置不得少于40个字" );
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
       	url:"<%=basePath%>command/setFolder",
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
	parent.window.location.reload();
}
</script>