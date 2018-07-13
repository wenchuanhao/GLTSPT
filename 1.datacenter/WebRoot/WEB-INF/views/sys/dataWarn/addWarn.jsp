<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>设置数据源阀值</title>
		<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
	</head>
	<body>
<div class="gl_import_m">
<form  action="" method="post" id="submitForm">
<div class="searchCondition"><span>设置数据源阀值</span></div>
  <table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable" align="center" >
	  <tr>
	    <th width="5%" align="left"><b>*</b> 数据源：</th>
	    <td width="40px"><input type="text" class="text01" name="dataSource"  id="dataSource" /></td>
	    <th width="5%" align="left"><b>*</b>阀值大于：</th>
	    <td width="20px"><input type="text" id="aviliablevalueGe" name="aviliablevalueGe" class="text01" /></td>
	  	<th width="5%" align="left"><b>*</b>阀值小于：</th>
	    <td width="20px"><input type="text" id="aviliablevalueLe" name="aviliablevalueLe" class="text01" /></td>
	  </tr>
	  <tr>
	      <td colspan="6" align="center"><input name="" type="button" class="btn_common02" onclick="doDraft()" value="保 存" /> <input name="" onclick="back()" type="button" class="btn_common01" value="取 消" /></td>
	  </tr>
	</table>
</form>
</div>
<script src="/SRMC/dc/account/js/jquery.js"></script>
<script src="/SRMC/dc/account/js/ui-select.js"></script>
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script>
	function back(){
		window.location.href="<%=basePath%>sys/dataWarn/queryIndex?type=2";
	}
	
	$(function (){
		// 将所有.ui-select实例化
		$('.ui-select').ui_select();
		// 获取已经实例化的select对象
		var obj = $('#sel_api').data('ui-select');
	});
	
	function doDraft(){
		if(jQuery.trim(jQuery("#dataSource").val()) == ""){
			alert("数据源不能为空");
			return;
		}
		var aviliablevalueGe = jQuery("#aviliablevalueGe").val();
		if(jQuery.trim(aviliablevalueGe) == ""){
			alert("阀值大于不能为空");
			return;
		}else{
			if(isNaN(jQuery.trim(aviliablevalueGe))){
				alert("阀值必须是数字");
				return ;
			}
		}
		var aviliablevalueLe = jQuery("#aviliablevalueLe").val();
		if(jQuery.trim(aviliablevalueLe) == ""){
			alert("阀值小于不能为空");
			return;
		}else{
			if(isNaN(jQuery.trim(aviliablevalueLe))){
				alert("阀值必须是数字");
				return;
			}
		}
		if(jQuery.trim(aviliablevalueGe) !="" && jQuery.trim(aviliablevalueLe)!=""){
			if(eval(aviliablevalueGe)>eval(aviliablevalueLe)){
				alert("阀值小于不能<"+aviliablevalueGe+"");
				return ;
			}
		}
		
		jQuery("#submitForm").attr("action","<%=basePath%>sys/dataWarn/addWarn");
		document.forms[0].submit();
	}
</script>        
</body>
</html>