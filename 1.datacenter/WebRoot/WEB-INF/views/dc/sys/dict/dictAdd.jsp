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
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
	<!--公共JS -->
	<script type="text/javascript" >var basePath = "<%=basePath%>sys/dict";var isCcFlag = "0";</script>
	
	<script src="/SRMC/dc/dict/js/common.js"></script>
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<span class="searchCondition_header">系统参数类型</span>
	</div>
	<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<tr>
		    <th width="16%" align="right"><b>*</b>参数类型名称:</th>
		    <td><input name="parameterTypeName" id="parameterTypeName"  type="text" class="text01" style="width:400px;"/></td>
	   	</tr>
		<tr>
		    <th width="16%" align="right"><b>*</b>参数类型编码:</th>
		    <td><input name="parameterTypeCode" id="parameterTypeCode"  type="text" class="text01" style="width:400px;"/></td>
	   	</tr>
		<tr>
		    <th width="16%" align="right"><b>*</b>参数类型值:</th>
		    <td><input name="parameterTypeValue" id="parameterTypeValue"  type="text" class="text01" style="width:400px;"/></td>
	   	</tr>
		<tr>
		    <th width="16%" align="right"><b>*</b>是否允许修改:</th>
		    <td>
				<input type="radio" name="allowUpdate" id="allowUpdate" checked="checked" value="1" />
				是
				&nbsp;&nbsp;<input type="radio" name="allowUpdate" id="allowUpdate" value="0" />
				否
			</td>
	   	</tr>
	   	<tr>
		    <th width="16%" align="right">参数类型描述:</th>
		    <td>
		    	<textarea class="text01" name="parameterTypeDesc" id="parameterTypeDesc" style="width:400px;"></textarea>
		    </td>
	   	</tr>
	   	<tr>
		     
	   	</tr>
	   	
  </table>
	
  	<div class="tabpages">
    	<ul class="l">
      		<li class="current">系统参数</li>
    	</ul>
    	<div class="otherButtons r">
			<input name="" onclick="addParameterLine()"  type="button" class="btn_common01" value="添加一行" />
			<input name="" onclick="delParameterLine()" type="button" class="btn_common01" value="删除多行" />
		</div>
  	</div>
  <table id="parameter_tb" width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  	<th><input id="checkAll" type="checkbox"></th>
	    <th>参数名称</th>
	    <th>参数编码</th>
	    <th>参数值</th>
	    <th>显示顺序</th>
	    <th style="width:5%;">是否允许修改</th>
	    <th>参数描述</th>
	  </tr>
	  	
	</table>
	</form>
	<table width="99%" id="table03" border="0" cellspacing="0" cellpadding="0" class="editTable" align="center" style="border:0px;">
		<tr>
	   		<th colspan="2" align="center" height="50">
	   			<input name="" type="button" class="btn_common02" id="submit" onclick="ev_submit()" value="提 交" /> 
	   		 	<input name="" type="button" class="btn_common01" onclick="javascript:window.history.back();" value="取 消" />
	   		</th>
	   	</tr>   
	</table>
</div>
</body>
</html>

<script type="text/javascript">
var indexOfLine = 0;
function ev_submit(){
	if(!checkSubmit()){
		return;
	}
	
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>sys/dict/submit",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result == 1){
	        	window.location.href="<%=basePath%>sys/dict/queryDictList";
        	}else if(result == 2){
	        	alert("数据类型编号重复，请更改");
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