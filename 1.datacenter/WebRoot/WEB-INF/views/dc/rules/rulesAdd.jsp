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
	
	<!--公共JS -->
	<script type="text/javascript" >var basePath = "<%=basePath%>rulesController";var isCcFlag = "0";</script>
	<script type="text/javascript" src="/SRMC/dc/rules/js/common.js"></script>
	
	<style>
		#uploadifive-uploadFiles1{
    		margin-left: 80%;
    		border: 0;		
		}
		#uploadifive-uploadFiles2{
    		margin-left: 80%;
    		border: 0;		
		}
		#uploadifive-uploadFiles3{
    		margin-left: 80%;
    		border: 0;		
		}
	</style> 
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<span class="searchCondition_header">制度新增</span>
	</div>
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<form action=""  method="post"  id="form1">
		<!-- 附件临时ID -->
		<input type="hidden" name="fileTempId" id="fileTempId" value="${fileTempId}"/>
		<input type="hidden" name="rulesId" id="rulesId_input" value="${fileTempId}"/>
		<input type="hidden" name="rulesCode" id="rulesCode_input" value="${rulesInfo.rulesCode}"/>
		<input type="hidden" name="leadDepId" id="leadDepId_input" value="${rulesInfo.leadDepId}"/>
		<input type="hidden" name="rulesGrade" id="rulesGrade_input" value="${rulesInfo.rulesGrade}"/>
		<input type="hidden" name="rulesTypeId" id="rulesTypeId_input" value="${rulesInfo.rulesTypeId}"/>
		<!-- 制度状态，默认为草稿 -->
		<input type="hidden" name="status" id="status_input" value="1"/>
		<tr>
		    <th width="30%" align="right"><b>*</b>制度名称：</th>
		    <td>
		    	<input name="rulesName"  id="rulesName"  type="text" class="text01" style="width:500px;" placeholder="请填写制度名称"  value="${rulesInfo.rulesName}"/>
		    </td>
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>制度等级：</th>
		    <td id="rulesGrade_td">
		    	<select class="ui-select" id="sel_01"  name="rulesGrade_select" style="width:506px;">
					<jsp:include flush="true" page="include/rulesGradesForAdd.jsp"></jsp:include>
		        </select>
		    </td>
	    </tr>
	    <tr>
	    	<th align="right"><b>*</b>制度分类：</th>
	    	<td>
	    		<jsp:include flush="true" page="include/rulesTypeListForAdd.jsp"></jsp:include>
	      	</td>
	    </tr>
<!-- 	    制度文件 -->
	  	<tr id="fi_box_tr_1">
		    <th align="right"><b>*</b>制度文件：<br/><b>(只能上传一个制度文件)</b></th>
		    <td>
		    	<div class="text01" style="width:500px;" id="fi_box_div_1" value="1">
			    	<input name="fileName" id="uploadFiles1" type="file" class="btn_upload" style="display: none">
	            </div>
		    </td>
	    </tr>
<!-- 	    制度附件 -->
	  	<tr id="fi_box_tr_2">
		    <th align="right">制度附件：</th>
		    <td>
		    	<div class="text01" style="width:500px;" id="fi_box_div_2" value="2">
                    <input name="fileName" id="uploadFiles2" type="file" class="btn_upload" style="display: none">
                </div>
		    </td>
	    </tr>
    
<!--     发布依据 -->
	    <tr id="fi_box_tr_3">
		    <th align="right">发布依据：<br/><b>(可上传多个发布依据文件)</b></th>
		    <td>
		    	<div class="text01" style="width:500px;" id="fi_box_div_3" value="3">
                    <input name="fileName" id="uploadFiles3" type="file" class="btn_upload" style="display: none">
                </div>
		    	<b>基地级制度需上传决策会议纪要，跨部门级、部门级制度需上传经审核签字的扫描件</b>
		    </td>
	    </tr>
	   </form>
	   <c:if test="${not empty fromDraft && not empty flowInfoList.items}">
		   <tr>
		   		<th colspan="2" align="center">
				   	<div class="ge01"></div>
						<div class="tabpages">
						    <ul class="l">
						      <li class="current" onclick="javascript:$('#table_id').toggle();">审核信息</li> 默认收缩，点击展开
						    </ul>
						</div>
					<table id="table_id" style="display: none;" width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
						<jsp:include flush="true" page="include/rulesFlowInfos.jsp"></jsp:include>
					</table>
		   		</th>
			</tr>
	   </c:if>
		<tr>
	   		<th colspan="2" align="center" height="50">
	   			<input name="" type="button" class="btn_common02" id="submit" onclick="ev_submit()" value="提 交" /> 
	   		 	<input name="" type="button" class="btn_common04" id="save" onclick="ev_save()" value="暂 存" />
	   		 	<input name="" type="button" class="btn_common04" onclick="javascript:window.location.href='<%=basePath%>rulesController/mine';" value="取 消" />
	   		</th>
	   </tr>    
  </table>
</div>
</body>
</html>

<script type="text/javascript">
$(function(){
	queryFileList("#uploadFiles1");
	queryFileList("#uploadFiles2");
	queryFileList("#uploadFiles3");
});
$(document).ready(function(){
	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	
	// 获取已经实例化的select对象
	var obj = $('#sel_01').data('ui-select');
	obj.onClick = function(value) {
		 $("#rulesGrade_input").val(value);
	}
});

//根据id设置input值
function setInputValById(target,id,name){
	var value_input = $(target).attr("val");
    $("#"+id).val(value_input);
    if(name){
    	var name_input = $(target).html();
    	$("#"+name).val(name_input);
    }
}

function checkSubmit(){

	//制度名称
	if( $.trim($("#rulesName").val())=="" ){
		animateAlert("table01","rulesName","请填写制度名称" );
		return false;
	}
	if( $("#rulesName").val().length > 30 ){
		animateAlert("table01","rulesName","制度名称不得超过30个字" );
		return false;
	}
	//制度等级
	if( $.trim($("#rulesGrade_input").val())=="" ){
		animateAlert("table01","sel_01","请选择制度等级" );
		return false;
	}
	//制度分类
	if( $.trim($("#rulesTypeId_input").val())=="" ){
		animateAlert("table01","sel_02","请选择制度分类" );
		return false;
	}
	//制度文件
	if($("#table01").find(".fj_p1").length <= 0){
		animateAlert("table01","fi_box_div_1","请上传一个制度文件" );
		return false;
	}
	if($("#table01").find(".fj_p1").length > 1){
		animateAlert("table01","fi_box_div_1","制度文件不得超过1个" );
		return false;
	}
	//基地级、跨部门级制度必须上传
	if($.trim($("#rulesGrade_input").val()) != "BMJ" && $("#table01").find(".fj_p3").length <= 0 ){
		animateAlert("table01","fi_box_div_3","<br/>请上传发布依据" );
		return false;
	}
	return true;
}

//滚动页面并弹框
function animateAlert(id,focusId,msg){
	//清除未选择，未填写的提示
	jQuery("html,body").find(".noselected_tips").remove();
	//滚动页面至表格
	jQuery("html,body").animate({scrollTop:jQuery("#"+id).offset().top},10);
	//提示
	if(focusId == "sel_01" || focusId == "sel_02" ) {
		$("#"+focusId).parent().parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}else{
		$("#"+focusId).parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}
}

function ev_save(){
	if( $("#rulesName").val().length > 30 ){
		animateAlert("table01","rulesName","制度名称不得超过30个字" );
		return;
	}
	$("#status_input").val("1");
	$("#form1").attr("action","<%=basePath%>rulesController/save");
	$("#form1").submit();
}

function ev_submit(){
	if(!checkSubmit()){
		return;
	}
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>rulesController/checkDraft",
        data:"rulesName="+$("#rulesName").val(),
        success:function(data){
	        if(data == 0){
	        	animateAlert("table01","rulesName","制度名称已存在！" );
	        	return;
	        }else{
				$("#status_input").val("2");
				$("#form1").attr("action","<%=basePath%>rulesController/submit");
				$("#form1").submit();
	        }
        },
        error:function(){
            alert("验证制度名称失败！");
        }
    });
}

</script>