<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>
<title>账号登录页面</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
<link href="/SRMC/dc/command/css/css.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/command/css/main.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/command/css/style.css" rel="stylesheet" type="text/css" />
</style>
<script type="text/javascript" src="/SRMC/dc/command/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/command/js/Popup.js"></script>
<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
    <script type="text/javascript" src="<%=basePath %>dwr/interface/encrypt.js"></script>
    <script type="text/javascript" src="<%=basePath %>dwr/engine.js"></script>
    <script type="text/javascript" src="<%=basePath %>dwr/util.js"></script>
</head>
<body>
<div class="data_fill">
	<!---大logo头部-->
	<div class="top2">
		<img src="/SRMC/dc/command/images/logo.png">
		<p class="title">南方基地管理提升平台</p>
		<p>工程工作指令</p>
	</div>
	<form action=""  method="post"  id="loginForm">
		<!---内容-->
		<ul class="data_A">
			<li class="data1">
				<span>帐&#12288;&#12288;号：</span>
				<input type="text" id="maccount" placeholder="请输入账号" maxlength="20">
				<input type="hidden" name="account" id="account" />
			</li>
			<li class="data1 border_no">
				<span>密&#12288;&#12288;码：</span>
				<input type="password" id="mpassword"  placeholder="请输入密码" maxlength="20">
				<input type="hidden" name="code" id="password" />
			</li>
		</ul>
	</form>
	<div class="but_A">
		<button type="button" id="b" onclick="doLogin()" class="but1 ClickMe">下一步</button>
	</div>
</div>
<!---弹窗开始-->
<div id="goodcover_div" class="goodcover"></div>
	<div id="code_div" class="code">
	  <div class="goodtxt">
	  <img src="/SRMC/dc/command/images/icon_06.png" class="img_1">
		<p class="p1" id="msg_p">你输入的帐号或密码错误</p>
  	</div>
	<div class="close1"><a href="javascript:void(0)" class="closebt">我知道了</a></div>
</div>
<!---弹窗结束-->
<!--遮罩层-->
<div id="areaMask" class="mask" style="display: none;"></div>

<script type="text/javascript">

document.onkeydown = function(e) {
	e = e ? e : window.event;
	var keyCode = e.which ? e.which : e.keyCode;
	if(keyCode == 13){
		doLogin();
	}
};
var cancheck = true;
function doLogin(){
   	if(!cancheck){
   		return false;
   	}
	var account=$("#maccount").val();
	if(account==null||account==""){
		$("#b").html("请输入账号");
		setTimeout(function(){
			$("#b").html("下一步");
		},"1000");
		return;
	}
	var password=$("#mpassword").val();
	if(password==null||password==""){
		$("#b").html("请输入密码");
		setTimeout(function(){
			$("#b").html("下一步");
		},"1000");
		return;
	}
	cancheck = false;
	//设置同步
   	try{
   		dwr.engine.setAsync(false);
          	//对用户名加密
          	encrypt.ecrypt(account,{callback:function(data){
              	jQuery("#account").val(data);
           }});
           //对密码加密
          	encrypt.ecrypt(password,{callback:function(data){
              	jQuery("#password").val(data);
           }});
           dwr.engine.setAsync(true);
   	}catch(e){
   		
   	}
	
	var options = {
	    url : "<%=basePath%>core/doLogin?removeSession=0",
	    type : "post",
	    success: function(data) {
	    	var datas = data.split("@#@");
	    	cancheck = true;
	      	if(datas[0] == "0"){
	      			//校验登录账号是否是
	      			//业主、专家顾问、施工单位、监理单位、造价单位
	      			$.ajax({
						url:'<%=basePath%>command/h5/afterLogin',
						dataType:'json',
						type:'post',
						async:false,
						success:function(result){
							if(result.result){
								window.location.href="<%=basePath%>command/h5/receiveDoc?id=${id}";
							}else{
								jQuery("#maccount").val("");
								jQuery("#mpassword").val("");
								alertMsg(result.message);
							}
						},
						error:function(){
							alertMsg("系统繁忙，请稍后再试!");
						}
					});
			} else if(datas[0] == "1") {
				jQuery("#maccount").val("");
				jQuery("#mpassword").val("");
				alertMsg(datas[1]);
			} else if(datas[0] == "2") {
				jQuery("#maccount").val("");
				jQuery("#mpassword").val("");
				alertMsg(datas[1]);
			} else if(datas[0] == "3") {
				jQuery("#maccount").val("");
				jQuery("#mpassword").val("");
				alertMsg(datas[1]);
			} else if(datas[0] == "4") {
				jQuery("#maccount").val("");
				jQuery("#mpassword").val("");
				alertMsg(datas[1]);
			} else if(datas[0] == "5") {
				jQuery("#maccount").val("");
				jQuery("#mpassword").val("");
				alertMsg(datas[1]);
			}
		}
	};
	jQuery("#loginForm").ajaxSubmit(options);
	return false;
}
</script>
</body>
</html>
