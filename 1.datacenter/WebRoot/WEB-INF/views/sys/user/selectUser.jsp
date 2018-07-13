<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<title>查询用户</title>
		<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">
			function doSearch(){
				var acc=document.getElementById("acc").value;
				var na=document.getElementById("na").value;
				if(acc=="" && na==""){
					alert("请最少填写 一个查询条件!");
					return;
				}else{
				    $.post("<%=basePath%>/sys/user/queryUserSingle",jQuery("#form").serializeArray(),function(data){
				    	if(data!=null){
				    		document.getElementById("userinfo").innerHTML=data;
				    	}else{
				    		alert("无相关记录！");
				    	}
				    	document.getElementById("JS").style.display='block';
				    });
				}
			}
		</script>
	</head>

	<body class="bg_c_g">
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 > 用户管理 > 查询用户
		</div>
		<table class="gl_m_r_n_tb01" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<!--
            
             <td width="18" valign="top" class="gl_m_r_n_tb01_m"></td>
             -->
				<td valign="top">

					<div class="gl_bt_bnt01">
						查询
					</div>
					<form name="form" method="post" id="form"
						action="<%=basePath%>sys/user/queryUserSingle">
						<!-- 
               <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0" id="queryDiv" style="display: block;">
               <div style="height: 30px"></div> 
               <tr>
               <td width="50%">登录账号：
                 <input class="gl_text01" type="text" name="account" id="acc" /></td>
               <td>用户姓名：
                 <input class="gl_text01" type="text" name="userName" id="na" /></td>
               </tr>
               
               <tr> 
               <td colspan="2" align="center"><div style="height: 30px"></div>
                 <input id="img" name="" type="button" class="gl_cx_bnt03" value="查 询" onclick="doSearch()"/>&nbsp;
                 <input name="" type="reset" class="gl_cx_bnt03" value="重 置" />
               </td>
               </tr>
               </table>
                -->
						<table class="gl_table_a01" width="100%" border="0"
							cellspacing="0" cellpadding="0" id="queryDiv" style="display: block;">
							<tr>
								<th width="100">
									登录账号:
								</th>
								<td>
									<input class="gl_text01_a" type="text" name="account" id="acc" style="width: 98%;"/>
								</td>
								<th width="100">
									用户姓名:
								</th>
								<td>
									<input class="gl_text01_a" type="text" name="userName" id="na" style="width: 98%;" />
								</td>
							</tr>
						</table>
						<div id="serchBox" class="gl_ipt_03">
							<input id="img" name="" type="button" class="gl_cx_bnt03"
								value="查 询" onclick="doSearch()" />
							&nbsp;
							<input name="" type="reset" class="gl_cx_bnt03" value="重 置" />
						</div>

					</form>

					<div class="ge_a01"></div>

					<div class="gl_bt_bnt01" id="JS" style="display: none;">
						用户信息
					</div>
					<div id="userinfo"></div>




				</td>
			</tr>
		</table>
	</body>
</html>
