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
<title>项目统一管理平台||确认用户信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/styleChangePwd.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
//返回登陆页
function toLogin(){
	window.location = "<%=basePath%>core/toLogin";
}
//进入修改密码页面
function toChangePassword(){
	window.location = "<%=basePath%>core/toChangePassword";
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
      <h2 class="l">确认用户信息</h2>
    </div>
    <!--面包屑 end--> 
    <!--进度条 start-->
    <div class="ydgl_process">
      <ul>
        <li class="active">第一步：确认用户信息</li>
        <li>第二步：<c:choose><c:when test='${actionType eq "activtionByMobile"}'>手机验证</c:when><c:otherwise>修改登录密码</c:otherwise></c:choose></li>
        <li>第三步：<c:choose><c:when test='${actionType eq "activtionByMobile"}'>账户激活成功</c:when><c:otherwise>密码修改成功</c:otherwise></c:choose></li>
      </ul>
    </div>
    <!--进度条 end--> 
    <!--内容部分 start-->
    <div class="margint16">
      <div class="ydgl_main">
        <div class="ydgl_mtit">
          <h3>请确认以下信息为您本人信息，核实无误后进行下一步操作!</h3>
        </div>
        <div class="ydgl_cont" style="padding-top:30px;">

		<div class="borderOut" >
          <div class="ydgl_infor">
            <div class="ydgl_inforline"> <span class="ydgl_icon01"></span>
              <h4 style="width:96px;">用户账号：</h4>
              <p>${user.account }</p>
            </div>
            <div class="ydgl_inforline"> <span class="ydgl_icon02"></span>
              <h4 style="width:96px;">用户姓名：</h4>
              <p>${user.userName }</p>
            </div>
            <div class="ydgl_inforline"> <span class="ydgl_icon03"></span>
              <h4 style="width:96px;">用户组织：</h4>
              <p>${userOrg }</p>
            </div>
            <div class="ydgl_inforline"> <span class="ydgl_icon04"></span>
              <h4 style="width:96px;">手机号码：</h4>
              <p>${userPhone }</p>
            </div>
          </div>
		  </div>
          <div class="ydgl_btnline" style="padding-top:40px;">
            <input type="button" class=" ydgl_btnnext" value="" onclick="toChangePassword();"/>
            <input type="button" class=" ydgl_btnback" value="" onclick="toLogin();"/>
          </div>
        </div>
        <div class="ydgl_mainbott"></div>
      </div>
      <div class="ydgl_sider">
        <div class="ydgl_sidertop"></div>
        <div class="ydgl_sidermain">
          <div class="ydgl_sidertit">
            <div class="ydgl_star"></div>
            <p>温馨提示:</p>
          </div>
          <div class="ydgl_sidercont">
            <ul>
              <li>1.为充分保障您的账户信息正确，请认真核对您的个人信息，确定无误；</li>
              <li>2.您的所有操作信息将会被记录和审计，如非本人操作，请立即退出系统，非法操作将会追究法律责任；</li>
              <li>3.如在使用中出现任何问题，请联系系统管理员，我们会及时提供技术支持服务。</li>
            </ul>
          </div>
        </div>
        <div class="ydgl_siderbott"></div>
      </div>
    </div>
    <!--内容部分 end--> 
  </div>
</div>
</body>
</html>
