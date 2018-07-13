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
<title>项目统一管理平台||激活账户信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/styleChangePwd.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/ajax.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript">
function resetForm(){
	$("#verifyCode").val("");
}

function goBack(){
	window.location.href="<%=basePath%>core/toConfirm";
}


function setTime(){
	///* 按钮倒数/////////
	var validCode_gray_text = "秒后可重新获取"; //文本
	var timeOutsecond = 60; //秒数
	this.waitForMinute = function(){
		var cur_minute = parseInt( jQuery("#validCode_gray").val() );
		cur_minute--;
		jQuery("#validCode_gray").val(cur_minute + validCode_gray_text );
		if(cur_minute>0 ){
			window.setTimeout( this.waitForMinute , 1000);
		}else{
			jQuery("#validCode_gray").hide();
			jQuery("#validCode").show();
			jQuery("#validCode_gray").val( timeOutsecond+validCode_gray_text );
		}
	}
	jQuery("#validCode").hide();
	jQuery("#validCode_gray").val( timeOutsecond + validCode_gray_text );
	jQuery("#validCode_gray").show();
	window.setTimeout( this.waitForMinute , 1000);
	///*********////////
}

function sendSMS(){
	var code = $("#code").val();
	var mob = $("#phone").val();
	if(mob=="" || mob==null){
		alert("您的账号中没有预留手机号，请联系管理员进行设置！");
		$("#phoneError").show();
		$("#differentPhone").hide();
		$("#sendCode").hide();
		$("#phone").focus();
		return;
	}
	if (mob.length !=11){
		alert("您的预留手机号码不是正确格式的手机号，请联系管理员进行设置！");
		$("#phoneError").show();
		$("#differentPhone").hide();
		$("#sendCode").hide();
		$("#phone").focus();
		return;
	}
	if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(mob))){ 
		alert("您的预留手机号码不是正确格式的手机号，请联系管理员进行设置！");
		$("#phoneError").show();
		$("#differentPhone").hide();
		$("#sendCode").hide();
		$("#phone").focus(); 
		return false; 
	}
	
	
	$.post("<%=basePath%>core/tocheckPhone", {
		"phone" : mob
	}, function (data){
		if(data=="no"){
			$("#differentPhone").show();
			$("#phoneError").hide();
			$("#sendCode").hide();
			//alert("您输入的手机号码与您预留的手机号码不符！");
			$("#phone").focus();		
		}else if(data=="nofind"){
			alert("您没有预留手机号码，请联系管理员进行设置!");
			$("#phone").focus();
		}else if(data=="error"){
			alert("网络错误，请稍后再试!");
		}else if(data=="noLogin"){
			alert("请先登录系统");
			window.location.href="<%=basePath%>core/toLogin";
		}else if(data=="yes"){
			$("#differentPhone").hide();
			$("#phoneError").hide();
			$("#sendCode").hide();
			
					
			$.post("<%=basePath%>core/getDynamicCode",{"servMobile":mob},function(data){
				if(data != null && data != "" ){
				   if(data =="sms_error"){
				      alert("发送短信失败，请稍候再试");
				      return ;
				   }else if (data.indexOf("sms_error")==0){
					      alert("发送短信失败，请稍候再试("+data.substring(10)+")");
					      return ;
				   }else if(data =="toLogin"){
					   alert("当前用户已失效，请重新登录系统！");
				   } else if(data=="errorPhone"){
					   //alert("您输入的手机号与系统预留手机号不符！");
					   $("#differentPhone").show();
					   $("#sendCode").hide();
					   return;
				   }else if(data=="numberOut"){
					   $("#outTimes").show();
					   $("#noTimes").hide();
					   $("#times").hide();
					   jQuery("#validCode").hide();
					   jQuery("#validCode_gray").val("");
					   jQuery("#validCode_gray").hide();
					   jQuery("#noValidCode").show();
				   }else{
					   //发送成功
					   var code = data.substring(0,6);
					   var lastTimes = data.substring(6,7);
					   var time = data.substring(7);
					   $("#code").val(code);
					   if(lastTimes=="4"){
						 setTime();
						 $("#sendCode").show();
						 $("#differentPhone").hide();
						 $("#phoneError").hide();
						 $("#times").hide();
					   }else if(lastTimes=='0'){
						   //setTime();
						   jQuery("#validCode").show();
						   $("#noTimes").show();
						   $("#sendCode").hide();
						   $("#differentPhone").hide();
						   $("#phoneError").hide();
						   $("#times").hide();
						   
					   }else{
						   setTime();
						   $("#time").val(time);
						   $("#lastTimes").html(lastTimes);
						   $("#times").show();
						   $("#sendCode").hide();
						   $("#differentPhone").hide();
						   $("#phoneError").hide();
					   } 
				   }
				}
			});
				   
		}
	});	
	
	
}


function doUpdatepPwd() {
	var phone = $("#phone").val();
	var verifyCode = $("#verifyCode").val();
	
	if(phone==""){
		alert("您的账号中没有预留手机号，请联系管理员进行设置！");
		$("#sendCode").hide();
		$("#phone").focus();
		return;
	}
	
	if (phone.length !=11){
		alert("您的预留手机号码不是正确格式的手机号，请联系管理员进行设置！");
		//$("#phoneError").show();
		$("#sendCode").hide();
		$("#phone").focus();
		return;
	}
	if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(phone))){ 
		alert("您的预留手机号码不是正确格式的手机号，请联系管理员进行设置！");
		$("#phoneError").show();
		$("#sendCode").hide();
		$("#phone").focus(); 
		return false; 
	}
	
	if(verifyCode==null || verifyCode==''){
		alert("请输入验证码！");
		return false; 
	}
	//保存
	jQuery.post("<%=basePath%>core/modifyPwd", $("#form").serialize(),function(data) {
		if('1'==data){
	    	$("#oldPwdError").show();
	    	oldPwd.focus();	
	    	return;
	    }
		if('2'==data){
	    	window.location.href="<%=basePath%>core/changeSuccess";
	    }
	    if(data=='3'){
	    	$("#codeError").show();
	    	//alert("您输入的验证码不正确！");
			return;
	    }
	    if(data=='toLogin'){
	    	alert("当前账号已失效，请重新登录系统！");
	    }
	    if(data=='outTime'){
	    	alert("验证码已过期，请重新获取验证码！");
	    	return;
	    } 
	    if(data=='toGetCode'){
	    	alert("请先获取验证码,再进行密码修改操作！");
	    	return;
	    }
	    if(data=='error'){
	    	alert("网络异常，请稍后再试!");
	    	return;
	    }
	    
	});
	
	//保存
	
	//defaultAjax("<%=basePath%>core/modifyPwd",$("#form").serializeArray(),'text',onSuccess,onError);
	//function onSuccess(data){
	//    if('1'==data){
	//    	$("#oldPwdError").show();
	//   	oldPwd.focus();	
	//    	return;
	//     }
	//    if('2'==data){
	//   	window.location.href="<%=basePath%>core/changeSuccess";
	//    }
	//    if(data=='3'){
	//    	$("#codeError").show();
	    	//alert("您输入的验证码不正确！");
	//		return;
	//    }
	//    if(data=='toLogin'){
	//    	alert("当前账号已失效，请重新登录系统！");
	//    }
	//    if(data=='4'){
	//    	alert("网络异常，请稍后再试!");
	//    	return;
	//    }
	//    if(data=='outTime'){
	//    	alert("验证码已过期，请重新获取验证码！");
	//   	return;
	//    }
	//}
	
	//function onError(){
	//  alert("请输入验证码！");
	//}
    
	
	//form.submit();
	<%--
	$.post("<%=basePath%>core/modifyPwd", {
		"oldPwd" : oldPwd.value,
		"newPwd" : newPwd.value,
		"verifyCode" : verifyCode
	}, function (data){
		if(data=="1"){
			alert("您输入的旧密码不正确！");
			//oldPwd.value="";
			//newPwd1.value="";
			//newPwd.value="";
			oldPwd.focus();		
		}else if(data=="2"){
			window.location.href="<%=basePath%>core/changeSuccess";
		}else if(data=="3"){
			alert("您输入的验证码不正确！");
			return;
		}else{
			alert("修改密码失败，请稍后再试!");
		}
	});	
	--%>
				
}


function tocheckPhone(){
	var phone = $("#phone").val();
	
	$.post("<%=basePath%>core/tocheckPhone", {
		"phone" : phone
	}, function (data){
		if(data=="no"){
			$("#phoneError").show();
			$("#phoneOK").hide();
			//alert("您输入的手机号码与您预留的手机号码不符！");
			$("#phone").focus();		
		}else if(data=="nofind"){
			alert("您没有预留手机号码，请联系管理员进行设置!");
			$("#phone").focus();
		}else if(data=="error"){
			alert("网络错误，请稍后再试!");
		}else if(data=="noLogin"){
			alert("请先登录系统");
			window.location.href="<%=basePath%>core/toLogin";
		}else if(data=="yes"){
			$("#phoneError").hide();
			$("#phoneOK").show();
			
		}
	});	
}
//检查旧密码
function tocheckOldPwd(){
	var oldPwd = $("#oldPwd").val();
	if(oldPwd!=null && oldPwd!=''){

		$.post("<%=basePath%>core/tocheckOldPwd", {
			"oldPwd" : oldPwd
		}, function (data){
			if(data=="no"){
				//alert("您输入的旧密码与您的密码不符，请重新输入！");
				$("#oldPwdError").show();
				$("#oldPwdOK").hide();
				//$("#oldPwd").focus();		
			}else if(data=="error"){
				alert("网络错误，请稍后再试!");
			}else if(data=="noLogin"){
				alert("请先登录系统");
				window.location.href="<%=basePath%>core/toLogin";
			}else if(data=="yes"){
				$("#oldPwdError").hide();
				$("#oldPwdOK").show();
			}
		});	
	}else{
		$("#oldPwdError").hide();
	}
	
}
//检查新密码
function tocheckNewPwd(){
	var newPwd = $("#newPwd").val();
	var newPwd1 = $("#newPwd1").val();
    var oldPwd = $("#oldPwd").val();
	var flag = true;
    if(newPwd == oldPwd) {
        alert("新密码不能和上次使用的密码相同");
        $("#newPwd").focus();
        return false;
    }

	if(newPwd ==null || newPwd==""){
		//alert("请输入新密码！");
		$("#newPwdOK").hide();
		$("#newPwd1OK").hide();
		$("#newPwd").focus();
		return false;
	}
    var r=/^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{6,24}$/;
    if(!r.test(newPwd)){
    	
    	//alert("您好，您修改的密码强度太弱！\n用户密码需满足以下规则：至少包含字母（大小写其中一种皆可）和数字；特殊符号等可选；至少6位，最高24位。");
    	$("#newPwdError").show();
    	$("#tips").hide();
    	$("#newPwdOK").hide();
    	$("#newPwd1OK").hide();
    	//$("#newPwd").val("");
    	//$("#newPwd1").val("");
    	$("#newPwd").focus();
    	return false;
    }
    var reg =/\s/;
    if(reg.test(newPwd)){
		alert("密码不能包含空格！");
		$("#newPwdOK").hide();
		$("#newPwd1OK").hide();
		//$("#newPwd").val("");
    	//$("#newPwd1").val("");
    	$("#newPwd").focus();
    	return false;
    }
    var re=/[^\x00-\xff]/g;
	if(re.test(newPwd)){
		alert("您好，密码不允许输入中文，中文符号或全角字符。");
		$("#newPwdOK").hide();
		$("#newPwd1OK").hide();
		//$("#newPwd").val("");
    	//$("#newPwd1").val("");
    	$("#newPwd").focus();
    	return false;
	}
	if(flag){
		$("#newPwdOK").show();
		$("#newPwdError").hide();
		$("#different").hide();
	}
	
	if(newPwd1!=null && newPwd1!=""){
		if(newPwd != newPwd1){
			//alert("您两次输入的密码不一致！");
			$("#different").show();
			$("#newPwd1OK").hide();
	    	//$("#newPwd1").val("");
	    	$("#newPwd1").focus();
		}else{
			$("#newPwd1OK").show();
		}
	}
	
}


//检查两次输入的密码
function toConfirmPwd(){
	var newPwd = $("#newPwd").val();
	var newPwd1 = $("#newPwd1").val();
	if(newPwd==null || newPwd==""){
		//alert("请输入新密码！");
		$("#newPwdOK").hide();
		$("#newPwd1OK").hide();
		$("#different").hide();
		$("#newPwd").focus();
		return false;
	}
	if(newPwd1 ==null || newPwd1==""){
		alert("请输入确认密码！");
		$("#newPwd1OK").hide();
		$("#newPwd1").focus();
		return;
	}
   
	if(newPwd != newPwd1){
		//alert("您两次输入的密码不一致！");
		$("#different").show();
		$("#newPwd1OK").hide();
		//$("#newPwd").val("");
    	//$("#newPwd1").val("");
    	$("#newPwd1").focus();
		return;
	}
	if(newPwd == newPwd1){
		$("#different").hide();
		$("#newPwd1OK").show();
	}
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
      <h2 class="l">手机验证</h2>
    </div>
    <!--面包屑 end--> 
    <!--进度条 start-->
    <div class="ydgl_process">
      <ul>
        <li>第一步：确认用户信息</li>
        <li class="active">第二步：<c:choose><c:when test='${actionType eq "activtionByMobile"}'>手机验证</c:when><c:otherwise>修改登录密码</c:otherwise></c:choose></li>
        <li>第三步：<c:choose><c:when test='${actionType eq "activtionByMobile"}'>账户激活成功</c:when><c:otherwise>密码修改成功</c:otherwise></c:choose></li>
      </ul>
    </div>
    <!--进度条 end--> 
    <!--内容部分 start-->
    <div class="margint16">
      <div class="ydgl_main">
       <form action="<%=basePath%>core/modifyPwd" name="form" id="form" >
          <input type="hidden" name="code" id="code"/>
          <input type="hidden" name="time" id="time"/>
        <div class="ydgl_mtit" >
            <h3>手机验证.</h3>
          </div>
        <div class="ydgl_cont01">
            <div class="ydgl_infor">
                <div class="ydgl_inforline" style="height:28px; line-height:28px;"> <span class="ydgl_icon07" style="margin-top:2px;margin-left: 40px;"></span>
                    <h4>手机号码：</h4>
                    <div class="ydgl_txtbox">
                        ${userPhone }
                        <!--  <input type="text" class="ydgl_txt" name="phone1" id="phone1" value="${userPhone }"  readonly="readonly" />
                -->
                        <input type="hidden" name="phone" id="phone" value="${user.mobile }" />

                        <c:choose>
                            <c:when test="${codeSize==6 }">
                                <img src="/SRMC/rmpb/images/btn_yanzhengma_gray.gif" allcheck=""
                                     disabled="disabled" style="cursor: pointer;position: absolute;left: 115px;top: -2px;  "/>
                            </c:when>
                            <c:otherwise>
                                <img src="/SRMC/rmpb/images/btn_yanzhengma.gif" allcheck=""
                                     id="validCode" onclick="sendSMS()" style="cursor: pointer;position: absolute;left: 115px;top: -2px; "/>
                            </c:otherwise>
                        </c:choose>

                        <input id="validCode_gray" type="button" value="" disabled="disabled"
                               style="width:130px; color:#222; height:27px;
                 position:absolute;left:115px;top:-2px;  display:none;   " />

                        <img src="/SRMC/rmpb/images/btn_yanzhengma_gray.gif" allcheck=""
                             id="noValidCode"  disabled="disabled" style="cursor: pointer;display:none;position: absolute;left: 115px;top: -2px; "/>
                    </div>
                    <div class="clear"></div>
                </div>

                <div class="ydgl_inforline" id="sendCode" style="height:20px; line-height:20px; padding-top:0px;display:none;">
                    <p class="ydgl_gray"  style="padding:3px 0 0 180px;height:26px;">验证码已下发至您的手机，请在3分钟内输入正确的验证码。</p>
                    <div class="clear"></div>
                </div>
                <div class="ydgl_inforline" id="times" style=" line-height:20px; padding-top:0px;display:none;">
                    <p  class="ydgl_gray" style="padding:3px 0 0 180px;height:26px;margin-right: 90px;">验证码已重新下发至您的手机，请在3分钟内输入正确的验证码。请注意，本日您还可获取
                        <b id="lastTimes" >3</b> 次短信验证码。</p>
                    <div class="clear"></div>
                </div>

                <div class="ydgl_inforline" id="noTimes" style="  line-height:20px; padding-top:0px;display:none;">
                    <p class="ydgl_gray" style="padding:3px 0 20px 180px;height:26px;margin-right: 80px;">验证码已重新下发至您的手机，请在3分钟内输入正确的验证码。请注意，您今日获取验证码短信次数已达到5次最大限制，为了保障您的信息安全，系统将无法再次进行下发验证码短信操作。
                    </p>
                    <div class="clear"></div>
                </div>
                <c:choose>
                    <c:when test="${codeSize==6 }">
                        <div class="ydgl_inforline"  style="height:20px; line-height:20px; padding-top:0px;">
                            <p class="ydgl_gray" style="padding:3px 0 0 180px;height:26px;margin-right: 80px;">您今日获取验证码短信次数已超过5次最大限制，为了保障您的信息安全，请您明日再进行相关操作，谢谢!</p>
                            <div class="clear"></div>
                        </div>
                        <div class="clear"></div>
                    </c:when>
                    <c:otherwise>
                        <div class="ydgl_inforline" id="outTimes" style="height:20px; line-height:20px; padding-top:0px;display:none;">
                            <p class="ydgl_gray" style="padding:3px 0 0 180px;height:26px;margin-right: 80px;">您今日获取验证码短信次数已超过5次最大限制，为了保障您的信息安全，请您明日再进行相关操作，谢谢!</p>
                            <div class="clear"></div>
                        </div>
                    </c:otherwise>
                </c:choose>

                <div class="clear"></div>
                <div class="ydgl_inforline" style="height:28px; line-height:28px;"> <span class="ydgl_icon08" style="margin-top:2px;margin-left: 40px;"></span>
                    <h4>验&nbsp;证&nbsp;码：</h4>
                    <div class="ydgl_txtbox">
                        <input type="text" class="ydgl_txt"  name="verifyCode" id="verifyCode" />

                    </div>
                    <div class="clear"></div>
                </div>

                <div class="ydgl_inforline" id="codeError" style="height:26px; line-height:26px; padding-top:0px;display:none;">

                    <p class="ydgl_red" style="padding-left:148px;">验证码输入错误，请重新输入！</p>
                    <div class="clear"></div>
                </div>

            </div>
          <div class="ydgl_btnline">
            <input type="button" class="ydgl_btnsure" value="" onclick="doUpdatepPwd()"/>&nbsp;<input type="button" class=" ydgl_btnreset" value="" onclick="resetForm()"/>
			&nbsp;&nbsp;<input type="button" class="ydgl_return" value="" onclick="goBack();"/>
          </div>
        </div>
        <div class="ydgl_mainbott"></div>
        </form>
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
