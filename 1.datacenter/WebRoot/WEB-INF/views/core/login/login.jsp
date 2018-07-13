<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String msg = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>南方基地管理提升平台</title>
    <link rel="shortcut icon" href="/SRMC/rmpb/images/chinamobile.ico" type="image/x-icon"/>
    <link rel="bookmark" href="/SRMC/rmpb/images/chinamobile.ico" type="image/x-icon"/>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />

    <script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/SRMC/rmpb/js/jquery.form.js"></script>
    <script type="text/javascript" src="/SRMC/rmpb/js/jslogin/js.js"></script>
    <script src="/SRMC/rmpb/js/jslogin/Checkable.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
    <script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
    <script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <script type="text/javascript" src="/SRMC/rmpb/js/encrypt/aes.js"></script>
    <script type="text/javascript" src="/SRMC/rmpb/js/encrypt/mode-ecb-min.js"></script>
    <script type="text/javascript" src="/SRMC/rmpb/js/encrypt/aesHelper.js"></script>
    <script type="text/javascript" src="/SRMC/rmpb/js/encrypt/md5.js"></script>
    
    <link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div class="login_wrap">
      <img src="/SRMC/dc/images/logo_white.png" class="logo_img"/>
      <form action="<%=basePath%>core/doLogin" id="loginForm" name="loginForm" method="post">
      <div class="login_area">
          <p class="login_title">南基管理提升平台</p>
          <dl class="login_form">
              <dd class="user_icon" >
                <input type="text" style="height:1.76em;margin-top:4.6px;margin-left:1.5px;width:193px;padding-top:3px;" class="login_text_a01" id="maccount" onkeyup="resetInfo();" onFocus="if(value==defaultValue){value='';this.style.color='#43698a'}" onBlur="if(!value){value=defaultValue;this.style.color='#43698a'}" />
                <input type="hidden" name="account" id="account" />
              </dd>
              <dd class="pass_icon">
                <input  type="password" style="height:1.76em;margin-top:4.3px;margin-left:1.5px;width:193px;padding-top:3px;" id="mpwd" class="login_text_a01" autocomplete="off" onFocus="if(value==defaultValue){value='';this.style.color='#43698a'}" onBlur="if(!value){value=defaultValue;this.style.color='#43698a'}"/>
                <input  type="hidden" name="code" id="pwd"/>
              </dd>
              <dt>
                <a href="javascript:void(0);" class="login_btn" onclick="doLogin();">登 录</a>
              </dt>
          </dl>
          
          <div class="lg_bottom">
            <span>建议使用IE8或者Chrome浏览器访问此系统</span>
          </div>
      </div>
      </form>
</div>

</body>
</html>
<script type="text/javascript">
    //监听回车事件
    //document.getElementById("account").focus();
    document.onkeydown = keyDown;

    function keyDown() {
        if (event.keyCode == 13) {
            doLogin();
        }
    }

    function resetInfo(){
        document.getElementById("pwd").value = "";
        jQuery("#rememberPassword").removeAttr("checked");
        jQuery("#rememberPasswordDiv").find("a").removeClass("checked");
    }

    function doLogin(){
    	var account = jQuery("#maccount").val();
    	var pwd = jQuery("#mpwd").val();
    	//加密
    	account = aesEncrypt(account,null);
    	jQuery("#account").val(account);
    	pwd = md5(pwd);
    	jQuery("#pwd").val(pwd);
       	
        var options = {
		    url : "<%=basePath%>core/doLogin",
		    type : "post",
		    success: function(data) {
		    	var datas = data.split("@#@");
		      	if(datas[0] == "0"){
		      		window.location = "<%=basePath%>" + datas[1];
				} else if(datas[0] == "1") {
					jQuery("#maccount").val("");
					jQuery("#mpwd").val("");
					alert(datas[1]);
				} else if(datas[0] == "2") {
					jQuery("#maccount").val("");
					jQuery("#mpwd").val("");
					alert(datas[1]);
				} else if(datas[0] == "3") {
					jQuery("#maccount").val("");
					jQuery("#mpwd").val("");
					alert(datas[1]);
				} else if(datas[0] == "4") {
					jQuery("#maccount").val("");
					jQuery("#mpwd").val("");
					alert(datas[1]);
				} else if(datas[0] == "5") {
					jQuery("#maccount").val("");
					jQuery("#mpwd").val("");
					alert(datas[1]);
				}
			}
		};
		jQuery("#loginForm").ajaxSubmit(options);
		return false;
    }
    
</script>