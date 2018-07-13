<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath(); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<script>
function printPage2(str) {
	$("."+str).printArea(); 
}
</script>
<div class="gl_import_m" style="width: 800px;margin:0 auto;padding:0;">
 	<div class="tabpages">
		<div class="otherButtons r" style="text-align: right;">
    		<a class="btn_common01" href="javascript:void(0)" onclick="printPage2('contract')" /><img src="/SRMC/dc/images/btnIcon/modify.png" /><span>打印</span></a>
    		<a class="btn_common01" href="javascript:window.history.back();" /><img src="/SRMC/dc/images/btnIcon/back.png" /><span>返回</span></a>
		</div>
	</div>
</div>