<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>项目统一管理平台</title>
<link rel="shortcut icon" href="/SRMC/rmpb/images/chinamobile.ico" type="image/x-icon"/>
<link rel="bookmark" href="/SRMC/rmpb/images/chinamobile.ico" type="image/x-icon"/>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet"
	href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css" />
<link href="/SRMC/rmpb/css/ieTip/css/style.css" type="text/css"
	rel="stylesheet" />
<link href="/SRMC/rmpb/css/adapterManage/css/style.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
<link href="/SRMC/rmpb/css/getPwd.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.infoClass {
	font-size: 15px;
	color: #6A6464;
	text-align: left;
	display: none;
}
</style>
<script type="text/javascript"> 
     
	function toLogin(){
	   window.location.href="<%=basePath%>core/toLogin";
	}
</script>
</head>
<body style="font-family:'微软雅黑'">
	<!-- <div class="header">
		<img src="/SRMC/rmpb/images/header.jpg" />
	</div> -->
	<div class="yfgl_top" >
		<span class="yfgl_top_l" style="height:98px; display:block; background:url(/SRMC/rmpb/images/yfgl_bj_l.jpg) no-repeat left top;">
			<ul class="yfgl_top_r" style="width:100px">              
				<li><span class="yfgl_top_r_a"><img src="/SRMC/rmpb/images/yfgl_bnt01.gif" /></span><span class="yfgl_top_r_b"><a href="javascript:void(0);" id="updatepassword" onclick="toLogin()">登录 </a></span></li>
				
			</ul>
		</span>
	</div>	
	<div>
		<div class="ydgl_content">
			<!--面包屑 start-->
			<div class="ydgl_tit">
				<span class="ydgl_iconloca l"></span>
				<h2 class="l">找回密码 > 完成</h2>
			</div>
			<!--面包屑 end-->
			<!--进度条 start-->
			<div class="ydgl_process" style="padding-left:2px">
				<ul>
					<li>第一步：填写帐号信息</li>
					<li>第二步：身份验证</li>
					<li>第三步：设置新密码</li>
					<li class="active">第四步：完成</li>
				</ul>
			</div>
			<!--进度条 end-->
			<!--内容部分 start-->
			<div class="margint16">
				<div class="ydgl_maintop"></div>
				<div class="ydgl_maincont">
					<table width="600" border="0" align="center" cellpadding="2"
						cellspacing="0" class="userTable">
						<tr>
							<td style="text-align:center"><img
								src="/SRMC/rmpb/images/ydgl_smile.png" width="93" height="93" />
							</td>

						</tr>
					</table>
					<table width="600" border="0" align="center" cellpadding="2"
						cellspacing="0" style="padding-left:6%" class="userTable">
						<tr>
							<td><img src="/SRMC/rmpb/images/yesgt.png" width="67" height="54" />
							</td>
							<td style="font-weight:bold;">新密码设置成功！请点击“确定”按钮返回登录页。<br />
							<em>温馨提示：请牢记您新设置的密码。</em>
							</td>
						</tr>
					</table>
					<table width="600" border="0" align="center" cellpadding="2"
						cellspacing="5" class="userTable">
						<tr>
							<td style="text-align:center"><input type="button" style="outline:0;"
								class="ydgl_btnsure" value="" onclick="toLogin()"/>
							</td>

						</tr>
					</table>
				</div>
				<div class="ydgl_mainfoot"></div>
			</div>
			<!--内容部分 end-->
		</div>
	</div>
</body>
</html>
