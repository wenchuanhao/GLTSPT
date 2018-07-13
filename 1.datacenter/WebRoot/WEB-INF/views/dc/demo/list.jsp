<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String msg = (String) request.getAttribute("message");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>项目统一管理平台</title>
    <link rel="shortcut icon" href="/SRMC/rmpb/images/chinamobile.ico" type="image/x-icon"/>
    <link rel="bookmark" href="/SRMC/rmpb/images/chinamobile.ico" type="image/x-icon"/>
    <link href="/SRMC/rmpb/css/styleLogin.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/SRMC/rmpb/js/jquery.form.js"></script>
    <script type="text/javascript" src="/SRMC/rmpb/js/jslogin/js.js"></script>
    <script src="/SRMC/rmpb/js/jslogin/Checkable.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
    <script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
    <script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <script type="text/javascript" src="<%=basePath %>dwr/interface/encrypt.js"></script>
    <script type="text/javascript" src="<%=basePath %>dwr/engine.js"></script>
    <script type="text/javascript" src="<%=basePath %>dwr/util.js"></script>
    <style type="text/css">
       a:hover {color:red; text-decoration:none;}    
    </style>
</head>

<body class="login_bj">
<form action="<%=basePath%>core/doLogin" id="loginForm" name="loginForm" method="post">
    <div class="login_main">
        <div class="login_main_c">

            <div class="login_main_c_a" style="margin-left:0px;padding-left:135px">
                <input type="text" style="height:1.76em;margin-top:4.6px;margin-left:1.5px;width:193px;padding-top:3px;" class="login_text_a01" id="maccount" onkeyup="resetInfo();" onFocus="if(value==defaultValue){value='';this.style.color='#43698a'}" onBlur="if(!value){value=defaultValue;this.style.color='#43698a'}" />
                <input type="hidden" name="account" id="account" />
            </div>
            <div class="login_main_c_b" style="margin-left:0px;padding-left:135px">
                <input  type="password" style="height:1.76em;margin-top:4.3px;margin-left:1.5px;width:193px;padding-top:3px;" id="mpwd" class="login_text_a01" autocomplete="off" onFocus="if(value==defaultValue){value='';this.style.color='#43698a'}" onBlur="if(!value){value=defaultValue;this.style.color='#43698a'}"/>
                <input  type="hidden" name="code" id="pwd"/>
            </div>
            <div id="rememberPasswordDiv" class="login_main_c_c">
                <input type="checkbox" class="myClass" id="rememberPassword" name="rememberPassword"/>&nbsp;记住密码<a href="<%=basePath%>getPwd/toGetPwd"  class="myClass"><span  style="font-family:'微软雅黑';padding-left:60px">忘记密码了？</span></a>
            </div>

            <div class="login_main_c_c" style="margin-top: 0px;"><div class="yan_zhu01">建议使用IE8或Chrome浏览器访问此系统</div>

            </div>
            <div class="login_main_c_d" style="border-bottom:none;">

                <input class="login_text_a02" name="" type="button" value="" onclick="doLogin();"/>

                <!--
                <p><a href="#">新用户通道</a></p>
                 -->
            </div>
            <a id="submitButton" ></a>

        </div>
    </div>

    <div class="login_bottom">  <div class="login_bottom">Copyright (C) 2013-2014  China Mobile Internet Base All rights reserved</div></div>
</form>


<script type="text/javascript">
    //监听回车事件
    //document.getElementById("account").focus();
    document.onkeydown = keyDown;

    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; ");
    for(var i=0; i<arrCookie.length; i++){
        var arr = arrCookie[i].split("=");
        if("account" == arr[0]){
            //document.getElementById("account").value = decodeURIComponent(arr[1]);
            var account = "";
        	//设置同步
        	try{
	        	dwr.engine.setAsync(false);
	        	encrypt.dencrypt(decodeURIComponent(arr[1]),{callback:function(data){  
	            	account = data;
	            	document.getElementById("maccount").value = account;
	            	document.getElementById("account").value = account;
	           	}});
	            dwr.engine.setAsync(true);
        	}catch(e){
        		
        	}
        }
        if("pwd" == arr[0]){
        	var pwd = "";
        	//设置同步
        	try{
        		dwr.engine.setAsync(false);
            	encrypt.dencrypt(decodeURIComponent(arr[1]),{callback:function(data){  
                	pwd = data;    
                	document.getElementById("pwd").value = pwd;
                	document.getElementById("mpwd").value = pwd;
               	}});
                dwr.engine.setAsync(true);
        	}catch(e){
        		
        	}
        	
        }
        if("rememberPassword" == arr[0]){
            if("on" == arr[1]){
                document.getElementById("rememberPassword").click();
            }
        }
    }

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
       	//设置同步
    	try{
    		dwr.engine.setAsync(false);
           	//对用户名加密
           	encrypt.ecrypt(account,{callback:function(data){
               	jQuery("#account").val(data);
            }});
            //对密码加密
           	encrypt.ecrypt(pwd,{callback:function(data){
               	jQuery("#pwd").val(data);
            }});
            dwr.engine.setAsync(true);
    	}catch(e){
    		
    	}
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

</body>

</html>
