<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script src="/SRMC/dc/js/checkboxList.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>advertisement";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	

<!--文件上传样式，js -->
	<!-- <link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script> -->
	
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadifive/uploadifive.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/jquery.uploadifive.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/uploadcommon.js"></script>
	
<!--公共JS -->
	<script type="text/javascript" >var basePath11 = "<%=basePath%>rulesController";</script>
	<script type="text/javascript" src="/SRMC/dc/advertisement/js/common.js"></script>  
<!-- 	表单异步提交 -->
<script src="/SRMC/rmpb/js/jquery.form.js"></script>
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
	<style>
		#uploadifive-uploadFiles13{
    		margin-left: 80%;
    		border: 0;		
		}
	</style> 
</head>
  
<body>
<div class="gl_import_m">
		<div class="searchCondition">
		<span class="searchCondition_header">配置</span>
	</div>
	<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" " align="center" id="table01">
				<!-- 附件临时ID -->
		<input type="hidden" name="fileTempId" id="fileTempId" value="${fileTempId}"/>
		<input type="hidden" name="configId" id="configId" value="${ad.configId}"/>
			<tr>
		    	<th width="30%" align="right"><b>*</b>序号：</th>
		    	<td>
		    		<input name="picSort"  id="picSort"  type="text" class="text01" style="width:500px;" placeholder="请填写"  value="${ad.picSort}"/>
		   		</td>
	    	</tr>
			<tr>
		    	<th width="30%" align="right"><b>*</b>图片广告标题：</th>
		    	<td>
		    		<input name="picTitle"  id="picTitle"  type="text" class="text01" style="width:500px;" placeholder="请填写"  value="${ad.picTitle}"/>
		   		</td>
	    	</tr>
	    	<tr id="fi_box_tr_13">
		    	<th width="30%" align="right"><b>*</b>图片广告路径：</th>
		    	<td>
		    		<div class="text01" style="width:500px;" id="fi_box_div_13" value="13">
                    	<input name="fileName" id="uploadFiles13" type="file" class="btn_upload" style="display: none">
                	</div>
		   		</td>
	    	</tr>
	    	<tr>
		    	<th width="30%" align="right"><b>*</b>图片广告链接：</th>
		    	<td>
		    		<input name="picUrl"  id="picUrl"  type="text" class="text01" style="width:500px;" placeholder="请填写"  value="${ad.picUrl}"/>
		   		</td>
	    	</tr>
	    	<tr>
		    	<th width="30%" align="right">图片广告描述：</th>
		    	<td>
		    		<input name="picDesc"  id="picDesc"  type="text" class="text01" style="width:500px;" placeholder="请填写"  value="${ad.picDesc}"/>
		   		</td>
	    	</tr>
	    	<tr>
		    	<th width="30%" align="right">图片广告类型：</th>
		    	<td>
		    		<input name="picType"  id="picType"  type="text" class="text01" style="width:500px;" placeholder="请填写"  value="${ad.picType}"/>
		   		</td>
	    	</tr>
	    	<tr>
	   		<th colspan="2" align="center" height="50">
	   			<input name="" type="button" class="btn_common02" id="dddd" onclick="ev_save()" value="提 交" /> 
	   		 	<input name="" type="button" class="btn_common04" onclick="javascript:window.location.href='<%=basePath%>advertisement/list';" value="取 消" />
	   		</th>
	   </tr>    
	</table>
	</form>
</div>
</body>
</html>

<script type="text/javascript">
$(function(){
	queryFileList("#uploadFiles13");
});
$(document).ready(function(){
	
});


function ev_save(){
	if(!ev_required_item()){
		return false;
	}
	
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>advertisement/saveOrUpdate",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result == 1){
	        	window.location.href = "<%=basePath%>advertisement/list";
        	}else{
        		alert("保存失败!");
        	}
        },
        error:function () {
        	alert("保存失败!");
        }
    });
}

/**
 * 必填项
 * @author weifei
 * @date Mar 8, 2012 10:02:40 AM
 */
function ev_required_item(){
		var k = 0;
		var b = true;
		var k1 = true;
		
		if(bindChange("#picTitle",50,"图片广告标题不得超过50个字")){
			return false;
		}
		if(bindChange("#picUrl",500,"图片广告链接不得超过500个字")){
			return false;
		}
		if(bindChange("#picDesc",1000,"图片广告描述不得超过1000个字")){
			return false;
		}
		if(bindChange("#picType",25,"图片广告类型不得超过25个字")){
			return false;
		}
		if(bindChange("#picSort",4,"序号不得超过4个字")){
			return false;
		}
		
		//图片广告标题
		var picTitle = $("#picTitle");
		if(picTitle.val() == ""){
       		k++;bindC(picTitle);			
		}
		//图片广告链接
		var picUrl = $("#picUrl");
		if(picUrl.val() == ""){
       		k++;bindC(picUrl);			
		}
		//排序
		var picSort = $("#picSort");
		if(picSort.val() == ""){
       		k++;bindC(picSort);			
		}
		//图片
		if($("#table01").find(".fj_p13").length <= 0){
			k++;bindC(fi_box_div_13);
		}
		if(k > 0){
      		alert("带*号的为必填字段，请完善必填字段。");
      		b = false;
      		return b;
      	}
		
      	//序号必须为数字
		var picSort = $("#picSort");
		var value = document.getElementById("picSort").value;
		var reg=/^\d+$/; //只能输入数字
		if(picSort.val() != "" && reg.test(value)!=true){
			bindC(picSort);
			k1 = false;
		}
		
		if(!k1){
      		alert("请确认输入的只为数字");
      		b = false;
      	}
      	
	    
		return b;
}

function bindC(field){
	$(field).css("border-color","red");
    $(field).bind("click", function(){
		$(this).css("border-color","");
	});
}
function bindPic(field){
	$(field).css("border-color","red");
    $(field).bind("click", function(){
		$(this).css("border-color","#ffffff");
	});
}

function bindChange(field,max,msg){
	if( $(field).val().length > max ){
		$(field).css("border-color","red");
	    $(field).bind("change", function(){
			$(this).css("border-color","");
		});
		alert(msg);
		return true;
	}
}
</script>