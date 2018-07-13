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
<title>手机登录页面</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
<link href="/SRMC/dc/command/css/css.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/command/css/main.css" rel="stylesheet" type="text/css" />
</style>
</head>
<body>
<div class="data_fill">
	<div class="top2">
		<img src="/SRMC/dc/command/images/logo.png">
		<p class="title">南方基地管理提升平台</p>
		<p>工程工作指令</p>
	</div>
	<ul class="data_A">
		<li class="data1">
			<span>手机号：</span>
			<input type="text" id="mobile" placeholder="请输入移动手机号码" maxlength="11">
		</li>
		<li class="data1 border_no">
			<span>验证码：</span>
			<input type="text" id="smsCode" placeholder="请输入验证码" maxlength="6">
			<a href="javascript:void(0)" onclick="sendSms()" id="s" class="code1">获取验证码</a>
		</li>
	</ul>
	<div class="but_A">
		<button type="button" id="b" onclick="checkSms()" class="but1 ClickMe">下一步</button>
	</div>
</div>
<div id="goodcover_div" class="goodcover"></div>
<!-- 	弹窗 -->
<div id="code_div" class="code">
  	<div class="goodtxt">
  		<img src="/SRMC/dc/command/images/icon_06.png" class="img_1">
		<p class="p1">验证码输出错误</p>
  	</div>
	<div class="close1"><a href="javascript:void(0)" class="closebt">我知道了</a></div>
</div>
<script type="text/javascript" src="/SRMC/dc/command/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/command/js/Popup.js"></script>
<script type="text/javascript">
var timeInterval;
var cansend = true;
    function sendSms(){
    	if(!cansend){
        	return false;
        }
        
		var mobile=$("#mobile").val();
		var sec=90;
		if(mobile==null||mobile==""){
			$("#s").text("请输入手机号码");
			setTimeout(function(){
				$("#s").text("获取验证码");
			},"3000");
			return;
		}else if(mobile.length<11){
			$("#s").text("请输入正确的手机号码");
			setTimeout(function(){
				$("#s").text("获取验证码");
			},"3000");
			return;			
		}
		var isMobileNum = /^1[3458][0-9]\d{8}$/;
	    if(!isMobileNum.exec(mobile)){
	    	$("#s").text("请输入正确的手机号码");
			setTimeout(function(){
				$("#s").text("获取验证码");
			},"3000");
			return;	
	    }
		$("#s").text("获取中...");
		cansend = false;
		$.ajax({
			url:'<%=basePath%>command/h5/sendSms',
			data:{"mobile":mobile},
			dataType:'json',
			type:'post',
			success:function(result){
				if(result.result){
					//$("#mobile").attr("readonly","readonly");
					cansend = false;
					$("#s").text("获取成功");
					timeInterval=setInterval(function(){
						sec-=1;
						$("#s").text(sec+"秒");
						if(sec<=0){
							clearInterval(timeInterval);
							cansend = true;
							$("#s").text("获取验证码");
						}
					},"1000");
				}else{
					if(result.errcode){
						alertMsg(result.errcode);
					}
					$("#s").text("获取失败");
					setTimeout(function(){
						cansend = true;
						$("#s").text("获取验证码");
					},"5000");
				}
			},
			error:function(){
				$("#s").text("网络异常");
				setTimeout(function(){
					cansend = true;
					$("#s").text("获取验证码");
				},"5000");
			}
		})
	
    }
    
    
var cancheck = true;
function checkSms(){
   	if(!cancheck){
   		return false;
   	}
	var mobile=$("#mobile").val();
	if(mobile==null||mobile==""){
		$("#b").html("请输入手机号码");
		setTimeout(function(){
			$("#b").html("下一步");
		},"1000");
		return;
	}else if(mobile.length<11){
		$("#b").html("请输入正确的手机号码");
		setTimeout(function(){
			$("#b").html("下一步");
		},"1000");
		return;			
	}
	var isMobileNum = /^1[3458][0-9]\d{8}$/;
    if(!isMobileNum.exec(mobile)){
    	$("#b").html("请输入正确的手机号码");
		setTimeout(function(){
			$("#b").html("下一步");
		},"1000");
		return;	
    }
	var smsCode=$("#smsCode").val();
	if(smsCode.length != 6){
		$("#b").html("验证码是6位数");
		setTimeout(function(){
			$("#b").html("下一步");
		},"1000");
		return false;
	}
	cancheck = false;
	$.ajax({
		url:'<%=basePath%>command/h5/checkSms',
		data:{"smsCode":smsCode,"mobile":mobile},
		dataType:'json',
		type:'post',
		success:function(result){
			if(result.result){
				cansend = true;
				cancheck = true;
				$("#s").text("验证成功");
				clearInterval(timeInterval);
				window.location.href="<%=basePath%>command/h5/doAccountLogin?id=${id}";
			}else{
				alertMsg();
				cancheck = true;
				$("#s").text("验证失败");
				setTimeout(function(){
					cansend = true;
					$("#s").text("获取验证码");
				},"3000");
			}
		},
		error:function(){
			cancheck = true;
			$("#s").text("网络异常");
			setTimeout(function(){
				cansend = true;
				$("#s").text("获取验证码");
			},"3000");
		}
	});
}
</script>
</body>
</html>
