<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>项目统一管理平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/styleChangePwd.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
   function toLogin(){
	   window.location.href="<%=basePath%>core/toLogin";
   }
</script>
<!--[if IE 6]>
<script type="text/javascript" src="js/DD_belatedPNG.js" ></script>

<script type="text/javascript">
DD_belatedPNG.fix('.*');
</script>
<![endif]-->
</head>

<body>
<div class="header"><img src="/SRMC/rmpb/images/header.jpg" /></div>
<div>
  <div class="ydgl_content"> 
    <!--面包屑 start-->
    <div class="ydgl_tit"> <span class="ydgl_iconloca l"></span>
      <h2 class="l">修改密码成功</h2>
    </div>
    <!--面包屑 end--> 
    <!--进度条 start-->
    <div class="ydgl_process">
      <ul>
        <li>第一步：确认用户信息</li>
        <li>第二步：<c:choose><c:when test='${actionType eq "activtionByMobile"}'>手机验证</c:when><c:otherwise>修改登录密码</c:otherwise></c:choose></li>
        <li class="active">第三步：<c:choose><c:when test='${actionType eq "activtionByMobile"}'>账户激活成功</c:when><c:otherwise>密码修改成功</c:otherwise></c:choose></li>
      </ul>
    </div>
    <!--进度条 end--> 
    <!--内容部分 start-->
    <div class="margint16">
      <div class="ydgl_maintop"></div>
      <div class="ydgl_maincont"> <img style="margin-top:86px;" src="/SRMC/rmpb/images/ydgl_smile.png" width="93" height="93" />
        <p>尊敬的用户，<c:choose><c:when test='${actionType eq "activtionByMobile"}'>您的账户激活成功，请重新登陆！</c:when><c:otherwise>您的密码已修改成功，请用新密码重新登录！</c:otherwise></c:choose></p>
        <input type="button" value="" class="ydgl_btnrelogin" onclick="toLogin()"/>
      </div>
      <div class="ydgl_mainfoot"></div>
    </div>
    <!--内容部分 end--> 
  </div>
</div>
</body>
</html>
