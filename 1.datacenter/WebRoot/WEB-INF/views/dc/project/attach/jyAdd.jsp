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
	<title>文档借阅</title>
	<base  target="_self"/>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<script src="/SRMC/dc/js/jquery.js"></script>
	
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
	
	<style>
	.gl_import_m_file{
		width:90%;
		margin-bottom: 60px;
		margin-top: 0;
		margin-left: auto;
		margin-right: auto;
	}
	  </style>	
</head>

<body>

<div class="gl_import_m_file">
	<div class="searchCondition">
		<span class="searchCondition_header">文档借阅</span>
	</div>
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<form action=""  method="post"  id="form1" >
		<input type="hidden" name="id" id="id" value="${vo.id}"/><!-- 借阅资源ID -->
		<input type="hidden" name="parentId" id="parentId" value="${attach.parentId}"/><!-- 目录ID -->
		<input type="hidden" name="attachId" id="attachId" value="${attach.id}"/><!-- 附件ID -->
		
		<tr>
		    <th width="30%" align="right"><b></b>借阅人：</th>
		    <input type="hidden" name="column05" id="column05" value="${vo.column05}"/>
		    <input type="hidden"  name="column06"  id="column06"  value="${vo.column06}"/>
		    <td>${vo.column06}</td>
	    </tr>
		<tr>
		    <th width="30%" align="right"><b>*</b>借阅份数：</th>
		    <td><input name="jyfs"  id="jyfs"  type="text" class="text01" style="width:500px;" placeholder="请填写借阅份数"  value="1"/></td>
	    </tr>	    
	    <tr>
	    	<th width="10%" align="right"><b></b>存放位置：</th>
		    <td>
		    	<input name="column04" id="column04" type="text" class="text01" style="width:500px;" placeholder=""  value="${attach.column04}" readonly="readonly"/>
		    </td>	    
	    </tr>
	    	 
	    <tr><th align="right" colspan="2"></th></tr>
	   </form>
		<tr>
	   		<th colspan="2" align="center" height="50">
	   		 	<input name="" type="button" class="btn_common02" id="save" onclick="ev_save()" value="借阅"  />
	   		 	<input name="" type="button" class="btn_common04" id="close" onclick="javascript:ev_close()" value="关闭" /> 
	   		</th>
	   </tr>    
  </table></th></tr>
</div>
</body>
</html>

<script type="text/javascript">
$(document).ready(function(){
	
	var m = "${mess}";
	if(m != "" && m == "s"){
		alert("借阅成功");
		window.dialogArguments.ev_refresh1("${attach.parentId}","${attach.column10}");
		window.close();
	}
	else if(m != "" && m == "e"){
		alert("借阅失败");
	}
});

/**
 * 必填项
 */
function ev_required_item(){
		var form = document.getElementById("form1");
		var k = 0;
		var b = true;
		
      	if($("#jyfs").val() == ""){
      		k++;
      		$("#jyfs").css("border-color","red");
      	}else{
			    var reg = new RegExp("^[0-9]*$"); 
			    if(!reg.test($("#jyfs").val())){
			        $("#jyfs").css("border-color","red");
					alert("请输入数字");
					k++;		
					return false;	        
			    }else{
			    	if($("#jyfs").val() == 0){
			    		$("#jyfs").css("border-color","red");
			    		alert("不能输入0");return false;
			    	}
			    	if($("#jyfs").val() > parseInt("${attach.column03}")){
			    		$("#jyfs").css("border-color","red");
			    		$("#jyfs").val("${attach.column03}");
			    		alert("最多只能借阅${attach.column03}份");return false;
			    	}			    	
			    }
			    $("#jyfs").css("border-color","");  	
      	}
      	
      	if(k > 0){
      		alert("*为必填字段");
      		b = false;
      	}
		return b;
}

function ev_save(){
	if(ev_required_item()){
		$("#save").hide();
		$("#form1").attr("action","<%=basePath%>fileUpload/saveOrUpdateJy");
		$("#form1").submit();
	}
}

function ev_close(){
	window.close();
}

//自动匹配建设项目
function autoCompletes(){
		jQuery("#column06").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>jsxm/searchUser",
					dataType: "json",
					data: {
						userValue: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
									value:item[0].userName+"------"+item[0].orgName,
									id:item[0].userId,
									name:item[0].userName,
									loginId:item[0].account,
									type:item[0].orgName
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
					jQuery("#column05").val(ui.item.id);
					jQuery("#column06").val(ui.item.name);
					return false;
			}
		});
}
</script>