<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>重置用户密码</title>
		<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/SRMC/rmpb/css/new.css" rel="stylesheet" type="text/css" />
		<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dTree.css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script src="/SRMC/rmpb/js/dtree2.js" type=text/javascript></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>


		<script type="text/javascript">
		
		  	$(document).ready(function(){
		  		if("${model.account}" != ""){
			  		$("#input2").click();
		  		}
		  	});
		function searchUser(){
			var acc=document.getElementById("acc").value;
			if(trim(acc)==''){
				alert("请输入用户帐户！");
				document.getElementById("succ").style.display='none';
				return false;
			}
			var url2 = "<%=basePath%>sys/user/getInfoUsers";
			var params = {
				acc:acc
			};
			$.post(url2, params, function(data){
			if(data==null){
				alert("抱歉！系统中不存在此用户信息，请检验用户帐户是否填写正确。");
				document.getElementById("succ").style.display='none';
				document.getElementById("sumButt").disabled="disabled";
				$("#reallName").html("");
				$("#depName").html("");
				$("#userId").val("");
				$("#pwd").val("");
				$("#cpwd").val("");
				return;
				
			}
			if(data=='1001'){
				alert("抱歉！您没有权限变更该用户的密码。");
				document.getElementById("succ").style.display='none';
				document.getElementById("sumButt").disabled="disabled";
				$("#reallName").html("");
				$("#depName").html("");
				$("#userId").val("");
				$("#pwd").val("");
				$("#cpwd").val("");
				return;
				
			}
			if(data=='1002'){
				alert("抱赚！查询用户信息失败，参数传递出错：用户组织编号为空。");
				document.getElementById("succ").style.display='none';
				document.getElementById("sumButt").disabled="disabled";
				$("#reallName").html("");
				$("#depName").html("");
				$("#userId").val("");
				$("#pwd").val("");
				$("#cpwd").val("");
				return;
				
			}
			document.getElementById("sumButt").disabled="";
			var userinfo = data;
			$("#reallName").html(userinfo.userName);
			$("#depName").html(userinfo.dNames);
			$("#userId").val(userinfo.userId);
			document.getElementById("succ").style.display='none';
			var ss=document.getElementById("userId").value;
			} , 'json');
		}
		function doChangePwd(){
			var acc=document.getElementById("userId").value;
			if(trim(acc)==''){
				alert("抱赚！提交失败，请先点击查询按钮。");
				document.getElementById("succ").style.display='none';
				return false;
			}
			var pwd1=document.getElementById("pwd").value;
			var pwd2=document.getElementById("cpwd").value;
			var r=/^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{6,24}$/;
			var pattern=/[^\x00-\xff]/g;
			var reg =/\s/;
			if(trim(pwd1)==''){
				alert("请输入新密码！");
				return false;
			}
			if(!r.test(pwd1)){
            	alert("新密码格式不对：至少包含字母（大小写其中一种皆可）和数字；特殊符号等可选；至少6位，最高24位。");
            	return false;
        	}
        	if(pattern.test(pwd1)){
			 	alert("新密码格式不对：密码不能包含中文！");
            	return false;
			}
			if(reg.test(pwd1)){
				alert("新密码格式不对：密码不能包含空格！");
            	return false;
			}
			if(trim(pwd2)==''){
				alert("请确认新密码！");
				return false;
			}
			if(!r.test(pwd2)){
            	alert("确认新密码格式不对：至少包含字母（大小写其中一种皆可）和数字；特殊符号等可选；至少6位，最高24位。");
            	return false;
        	}
        	if(pattern.test(pwd2)){
			 	alert("确认新密码格式不对：密码不能包含中文！");
            	return false;
			}
			if(reg.test(pwd2)){
				alert("确认新密码格式不对：密码不能包含空格！");
            	return false;
			}
			if(pwd1!=pwd2){
				alert("您两次输入的密码不一致！");
				return false;
			}
			document.getElementById("sumButt").disabled="disabled";
			var url2 = "<%=basePath%>sys/user/changeUserPwd";
			var params = {
				acc : acc,
				pwd : pwd1
			};
		$.post(url2, params, function(data) {
			if (data == '1') {
				alert("抱赚！变更用户密码失败，参数传递出错：用户编号为空。");
				document.getElementById("sumButt").disabled = "";
				document.getElementById("succ").style.display = 'none';
				return false;
			}
			if (data == '11') {
				alert("抱赚！变更用户密码失败，参数传递出错：新密码为空。");
				document.getElementById("sumButt").disabled = "";
				document.getElementById("succ").style.display = 'none';
				return false;
			}
			if (data == '2') {
				alert("抱赚！变更用户密码失败，查不到用户信息。");
				document.getElementById("succ").style.display = 'none';
				document.getElementById("sumButt").disabled = "";
				return false;
			}
			if (data == '3') {
				document.getElementById("sumButt").disabled = "";
				document.getElementById("succ").style.display = 'block';
				document.getElementById("sumButt").disabled = "disabled";
				return false;
			}
		});
	}
</script>
	</head>

	<body class="bg_c">
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 > 用户管理 > 用户密码变更
		</div>

		<div class="gl_import_m">

			<div class="reset_01">
				<ul>
					<li>
						第一步：请您输入需要密码变更的用户账号，确认无误后点击&quot;查询&quot;按钮
					</li>
					<li>
						第二步：请您输入用户需变更的新密码，确认无误后点击&quot;密码变更&quot;按钮
					</li>
					<li>
						温馨提示：您的所有行为将被记录和审计，请确定您在进行密码变更业务前已经知会相关用户并得到许可
					</li>
				</ul>
			</div>

			<div class="reset_k01" style="margin: auto;">
				<div class="reset_k_top">
					请输入需要变更密码的用户账号信息：
				</div>
				<table class="reset_tab01" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th width="80">
							用户账号：
						</th>
						<td>
							<input style="width: 89%;" name="acc" type="text"
								class="jygl_text01" id="acc" value="${model.account }" maxlength="30"/>
							&nbsp;
						</td>
						<td width="90"><input class="reset_btn01" id="input2" name="input2" type="button" onclick="searchUser()"/></td>
					</tr>
					<tr>
						<th>
							新密码：
						</th>
						<td colspan="2">
							<input name="pwd" type="text" class="jygl_text01"
								id="pwd" maxlength="30"/>
						</td>
					</tr>
					<tr>
						<th>
							确认新密码：
						</th>
						<td colspan="2">
							<input name="cpwd" type="text" class="jygl_text01"
								id="cpwd"  maxlength="30"/>
						</td>
					</tr>
					<tr>
						<th>
							用户姓名：
						</th>
						<td colspan="2" id="reallName"></td>
					</tr>
					<tr>
						<th>
							组织架构：
						</th>
						<td colspan="2" id="depName"></td>
					</tr>
					<tr>
						<td colspan="3" align="center">
							<input name="input" type="button" class="ipt_tb_n03" value="密码变更" id="sumButt" onclick="doChangePwd()"/>
						</td>
					</tr>
				</table>
				<div style="display: none;">
				  <input type="hidden" name="userId" id="userId"/>
				</div>
				<div style="height: 12px;"></div>

			</div>

			<div class="reset_p" id="succ" style="display: none;">
				密码变更成功！请通知用户及时修改密码以保证用户信息安全。
			</div>

		</div>

	</body>
</html>
