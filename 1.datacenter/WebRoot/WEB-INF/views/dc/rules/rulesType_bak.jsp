<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />

<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>rulesController";</script>
<!-- 树形 -->
<link type="text/css" rel="stylesheet" href="/SRMC/dc/css/dTree.css" />
<script src="/SRMC/dc/js/dtree.js" type=text/javascript></script>
<script type="text/javascript">
var busTypeName = "${busTypesName }";
function createTree(){
	d = new dTree('d');
		d.add('ROOT',-1,'${busTypesName}');
	var data = $.parseJSON('${rulesTypeList}'.replace("\r\n", "\\r\\n"));
	$.each(data,function(k,v){
		d.add(v.typeId,v.parentTypeId,v.typeName,'','','');
	});
	return d;
}
</script>
</head>
<body>

<div class="gl_import_m">
<div class="searchCondition">
	<span class="searchCondition_header">${busTypesName }配置</span>
</div>

<table width="99%" height="50%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	<tr>
    	<td width="25%" valign="top" style="border-right:1px dashed #e6e6e6;overflow-y: auto;">
		    <div class="d_tree_div">
			    <script type=text/javascript>
			    	var type = createTree();
					document.write(type);
					type.openAll();
				</script>
		    </div>
	    </td>
	    <td valign="top">
	    	<table id="table01" cellpadding="0" cellspacing="5" class="taskCreate" width="100%">
	    		<form action=""  method="post"  id="form1">
	    		<input type="hidden" name="busTypes" id="busTypes_input" value="${busTypes }"/>
	    		<input type="hidden" name="typeId" id="typeId_input" value=""/>
	    		<input type="hidden" name="parentTypeId" id="parentTypeId_input" value=""/>
	    		<input type="hidden" name="parentTypeName" id="parentTypeName_input" value=""/>
	            <tr id="rules_type_tr01">
	               <th width="35%"><em>*</em>${busTypesName }一级：</th>
	               <td>
		               	<select class="ui-select" id="sel_01" style="width:180px;">
							<option value="">请选择</option>
				          	<c:forEach items="${list}" var="item" varStatus="i">
				    			<c:if test="${item.isRoot == 1 }">
				    				<option value="${item.typeId}">${item.typeName}</option>
				    			</c:if>
							</c:forEach>
				        </select>
	        	   </td>
	            </tr>
	             <tr>
	               <th width="35%" id="rules_type_th02"><em>*</em>${busTypesName }二级：</th>
	               <td><input  name="typeName" id="typeName_input"  type="text" class="text01" style="width:174px;" /></td>
	             </tr>
	             </form>
	            <tr>
	               <th>&nbsp;</th>
	               <td> <input onclick="ev_submit()"  id="submit" name="" type="button" class="btn_common02" value="编辑配置" />
						<input onclick="ev_save()"  id="save" name="" type="button" class="btn_common01" value="新增配置" />
						<input onclick="ev_delete()"  id="delete" name="" type="button" class="btn_common01" value="删除配置" />
				   </td>
	            </tr>
	         </table>
	    </td>
    </tr>
</table>
</div> 
<script type="text/javascript">
var obj2 ;
$(function(){

});
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
    //${busTypesName }
	obj2 = $('#sel_01').data('ui-select');
	obj2.onClick = function(value) {
		 $("#parentTypeId_input").val(value);
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

	if( jQuery.trim(jQuery("#parentTypeId_input").val())=="" ){
		animateAlert("table01","sel_01", "你选择${busTypesName }一级" );
		return false;
	}
	if( jQuery.trim(jQuery("#typeName_input").val())=="" ){
		animateAlert("table01","typeName_input", "${busTypesName }名称不能为空" );
		return false;
	}
	if( $("#typeName_input").val().length > 30 ){
		animateAlert("table01","typeName_input","${busTypesName }名称不得超过30个字" );
		return false;
	}
	
	return true;
}

function ev_submit(){
	if(!checkSubmit()){
		return;
	}
	//制度相关文档保存
	$("#form1").attr("action","<%=basePath%>rulesController/addRulesType");
	$("#form1").submit();
}

function ev_save(){
	if(!checkSubmit()){
		return;
	}
	$("#typeId_input").val("");
	//制度相关文档保存
	$("#form1").attr("action","<%=basePath%>rulesController/saveRulesType");
	$("#form1").submit();
}

function ev_delete(){

	if( jQuery.trim(jQuery("#typeId_input").val())=="" ){
		alert("请选择你要删除的配置项！" );
		return false;
	}
	var id = jQuery("#typeId_input").val();
	if(confirm("确定删除该配置及其子配置项么？")){
		jQuery.ajax({
	        type:"POST",
	        async:false,
	        url:"<%=basePath%>rulesController/delRulesType",
	        data:"typeId=" + id + "&busTypes=${busTypes }",
	        success:function(data){
	        	if(data == 1){
	        		window.location.href="<%=basePath%>rulesController/queryRulesType?busTypes=${busTypes }";
	        	}else{
	        		alert("删除失败！");
	        	}
	        },
	        error:function(){
	            alert("删除失败！");
	        }
	    });
	}
}

</script>
</body>
</html>
