<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"></link>
	<link href="/SRMC/rmpb/css/left_a01.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
	 <style type="text/css">
    .mm_main_bd01{ width:400px; margin:0 auto;}
    .gl_table_a02 td{ text-align:left; padding-left:3px;}
    </style>
	<script type="text/javascript">
			function doUpdatepPwd() {
				var oldPwd = $("#oldPwd")[0];
				var newPwd1 = $("#newPwd1")[0];
				var newPwd = $("#newPwd")[0];
				  
				if(oldPwd.value== null || oldPwd.value==""){
						alert("请输入旧密码！");
						oldPwd.focus();		
						return;
				}
				if(newPwd.value ==null || newPwd.value==""){
					alert("请输入新密码！");
					newPwd.focus();
					return;
				}
                var r=/^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{6,24}$/;
                var password=newPwd.value;
                if(!r.test(password)){
                	alert("您好，您修改的密码强度太弱！\n用户密码需满足以下规则：至少包含字母（大小写其中一种皆可）和数字；特殊符号等可选；至少6位，最高24位。");
                	newPwd.value="";
					newPwd1.value="";
					newPwd.focus();
                	return false;
                }
                var reg =/\s/;
                 if(reg.test(password)){
					alert("密码不能包含空格！");
					newPwd.value="";
					newPwd1.value="";
					newPwd.focus();
	            	return false;
			    }
                var re=/[^\x00-\xff]/g;
				if(re.test(password)){
					alert("您好，密码不允许输入中文，中文符号或全角字符。");
                	newPwd.value="";
					newPwd1.value="";
					newPwd.focus();
                	return false;
				}
				if(newPwd1.value ==null || newPwd1.value==""){
					alert("请确认新密码！");
					newPwd1.focus();
					return;
				}
			   
				if(newPwd.value != newPwd1.value){
					alert("您两次输入的密码不一致！");
					newPwd.value="";
					newPwd1.value="";
					newPwd1.focus();
					return;
				}
				
				$.post("<%=basePath%>sys/myuser/modifyPwd", {
					"oldPwd" : oldPwd.value,
					"newPwd" : newPwd.value
				}, function (data){
					if(data=="1"){
						alert("您输入的旧密码不正确！");
						oldPwd.value="";
						newPwd1.value="";
						newPwd.value="";
						oldPwd.focus();		
					}else if(data=="2"){
						alert("密码修改成功!");
						window.location.href="<%=basePath%>core/toLogin";
						$("#pwd").hide();
						$("#info").toggle();
					}else{
						alert("修改密码失败，请稍后再试!");
					}
				});	
							
		}
	</script>
	</head>

	<body  class="bg_c_g" id="body">
			<div style="height:25%;"></div>
		
		<div class="mm_main_bd01" style="margin:0 auto;">
		  <div  class="gl_bt_bnt01"><span class="mm_m_bd_c">修改密码</span>
		 </div>
		  <form action="<%=basePath%>sys/paramtype/addParameterType" method="post" name="form">
		           <input type="hidden" name="type" value="${type }"/>
				  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0"  class="gl_table_a01">
					<tr>
					  <th width="120" ><span class="or" style="color:red; font-family:微软雅黑; font-size:14px;">*</span>旧密码</th>
					  <td><input class="gl_text01_a" type="password" name="oldPwd" id="oldPwd" style="width:98%;"/></td>
				    </tr>
				   </table>
				   <div style="height: 12px"></div>
				    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0"  class="gl_table_a01">
					  <tr>
					    <th width="120"><span class="or" style="color:red; font-family:微软雅黑; font-size:14px;">*</span>新密码</th>
					    <td><input class="gl_text01_a" type="password" name="newPwd" id="newPwd" style="width:98%;"/></td>
				    </tr>
				    </table >
				    <div style="height: 12px"></div>
				    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0"  class="gl_table_a01">
					   <tr>
					  <th width="120" ><span class="or" style="color:red; font-family:微软雅黑; font-size:14px;">*</span>确认新密码</th>
					  <td><input class="gl_text01_a" type="password" name="newPwd1" id="newPwd1" style="width:98%;"/></td>
					</tr>
				  </table>		     
				  <table width="96%" align="center">
			    <tr >
						  <td height="60"  colspan="4" align="center">
								<input  class="ipt_tb_n03" type="button" name="button3" id="button3" value="确定" onclick="doUpdatepPwd()" />&nbsp;
								<input  class="ipt_tb_n03" type="button" name="button4" id="button4" value="取 消" onclick="javascript:history.back()" />
						  </td>
				    </tr>
		    </table>
			</form>
		  <div class="ge02"></div>
		</div>
	</body>
</html>
