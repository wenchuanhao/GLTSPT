<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link href="/SRMC/rmpb/css/left_a01.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>

<body class="main_bg">
	<div class="mm_main_top01">
    		<span class="mm_main_top01a">
    			<img src="/SRMC/rmpb/images/005.png" width="33" height="32" />
    		</span>
    		<span class="mm_main_top01c">当前位置：系统管理 &gt; 用户管理 &gt; <span class="or">用户列表</span></span>
    </div>
      <div class="ge01"></div>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
	        <tr>
	        		<td width="16%" valign="top">
								<table width="98%" border="0" align="center" cellpadding="0"
									cellspacing="0" class="main_table05">
									<tr>
										<td align="center">
											<iframe id="typical" name="typical"
												style="height: 450px; width: 100%; visibility: inherit;"
												scrolling="auto" frameborder="0" marginwidth="0"
												marginheight="0" src="<%=basePath%>sys/user/userDeltree"></iframe>
										</td>
									</tr>
								</table>
				</td>
				<td width="84%">
						<table width="98%" border="0" align="center" cellpadding="0"
									cellspacing="0" >
									<tr>
										<td align="center">
											<iframe id="typical" name="queryUser"
												style="height: 450px; width: 100%; visibility: inherit;"
												scrolling="auto" frameborder="0" marginwidth="0"
												marginheight="0" src="<%=basePath%>sys/user/queryDelUser"></iframe>
										</td>
									</tr>
								</table>
				</td>
	        </tr>
	      	</table>
	<div class="ge01"></div>
</body>
</html>