<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加用户</title>
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link href="/SRMC/rmpb/css/left_a01.css" type="text/css"  rel="stylesheet" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/common.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/autoDialog.js"></script>
<script type="text/javascript">
	//打开组织列表
		function openPrivilege(roleId) {
		var url = "<%=basePath%>sys/org/queryOrglist?index="+ Math.random();
		dialog = new AutoDialog({title:"分配权限", width: 300, height: 400, padding: 0});
		dialog.setContent('<iframe src="'+url+'" width="100%" height="100%" frameborder="0" border="0"></iframe>');
		dialog.show();
		
		}
		function setVal(o){
			$("#organizationName").val(o.orgName);
			$("#organizationId").val(o.orgId);
			dialog.destroy();
		}
		function checkAccount(msg,type){
				var account = $("#account").val();
				if(account!=null && account!="" ){
					if(account.length < 3){
						alert("用户名在3-20个字符之间");
						$("#account")[0].focus();
						return false;
					}else{
						return checkVal("account",20,"请输入登陆账号!",false);
					}
				}else
				{
					if(type==2){
						alert(msg);
						$("#account")[0].focus();
					}
					return false;
				}
			}
		function checkNamexUniqueness(){
				var account =$("#account").val();
				if(checkAccount("请输入登陆账号",1))
				{
					$.post("<%=basePath%>/sys/user/checknameuniqueness", {
						"account" :account
					}, function(data) {
						if (data == "0"){
								  form.submit();
							}
						if (data == "1"){
							alert("登陆账号存在!");
							$("#account")[0].focus();
							return false;
						}
					});
				}
			}
		function doSave() {
				if(checkVal("userName",20,"请输入用户真实姓名!",false))
				if(checkAccount("请输入登陆账号",2))
				if(checkVal("password",20,"请输入用户密码!",false))				
				if(checkVal("mobile",13,"请输入手机号码!",false))
				if(isMobil("mobile"))
				if(checkVal("email",50,"请输入邮箱!",false))
				if(checkEmail("email"))				
				if(checkVal("organizationName",100,"请输入所属组织!",false))
				  checkNamexUniqueness();
				
			}
</script>
</head>
<body class="main_bg">
	<form action="<%=basePath%>/sys/user/addSysUser" method="post" name="form">
		<div class="mm_main_bd11">
			<div class="mm_m_bd_a">
				<span class="mm_m_bd_b"><img
					src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" /> </span> <span
					class="mm_m_bd_c">新增用户</span>
			</div>
	
			<div class="ge01"></div>
				<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table03">
					<tr>
						<th width="15%">
							<span class="or">*</span>用户登录账号
						</th>
						<td width="35%">
							<input class="blue_bt_a" type="text" name="account" id="account"  onkeyup="value=value.replace(/[^\w\/]/ig,'')"/>
						</td>
						<th width="15%">
							<span class="or">*</span>用户姓名
						</th>
						<td width="35%">
							<input class="blue_bt_a" type="text" name="userName" id="userName" />
						</td>
					</tr>
					<tr>
						<th width="15%">
							<span class="or">*</span>是否接受SMS
						</th>
						<td width="35%%">
							<input type="radio" value="1" name="isReceiveSms" checked="checked"/>接受
							<input type="radio" value="0" name="isReceiveSms" />不接受
						</td>
						<th width="15%">
							<span class="or">*</span>用户状态
						</th>
						<td width="35%%">
							<input type="radio" value="1" name="isActivate" />正常
							<input type="radio" value="0" name="isActivate" checked="checked"/>禁用
						</td>
					</tr>
					<tr>
						<th width="15%">
							<span class="or">*</span>用户登录密码
						</th>
						<td width="35%">
							<input class="blue_bt_a" type="password" name="password" id="password" />
						</td>
						<th width="15%"><span class="or">*</span>用户邮箱</th>
						<td width="35%">
							<input class="blue_bt_a" type="text" name="email" id="email" />
						</td>
					</tr>
					<tr>
						<th width="15%">
							<span class="or">*</span>用户手机号码
						</th>
						<td width="35%">
							<input class="blue_bt_a" type="text" name="mobile" id="mobile" />
						</td>
						<th width="15%">
							<span class="or">*</span>用户所属组织
						</th>
						<td width="35%">
							<c:choose>
			          		<c:when test="${org!='1'}">
			          			<input class="blue_bt_a" type="text" name="orgName" id="orgName" value="${org.orgName}" disabled="disabled"/>
								<input class="blue_bt_a" type="hidden" name="organizationName" id="organizationName" value="${org.orgName}"/>
								<input class="blue_bt_a" type="hidden" name="organizationId" id="organizationId" value="${org.orgId}"/>
			          		</c:when>
			          		<c:otherwise>
			          			<input type="hidden" name="organizationId" id="organizationId"/>
								<input class="blue_bt_a" type="text" name="organizationName" id="organizationName" />
								<input style="width:89px; height:25px; font-size:12px; font-weight:bold; color:#fff; background:url(/SRMC/rmpb/images/bnt04.gif) no-repeat; border:none; cursor:pointer; padding-left:5px;  font:'微软雅黑';" type="button" onclick="openPrivilege()" value="选择组织"/>
			          		</c:otherwise>
			        	  	</c:choose>
						</td>
					</tr>
				</table>
				<table width="96%" align="center">
					<tr>
						<td height="60" colspan="4" align="center">
							<input class="bnt_class02" type="button" name="button3" id="button3" value="保 存" onclick="doSave()"/>
							<input class="bnt_class03" type="button" name="button4" id="button4" value="取 消" onclick="javascript:history.back()" />
						</td>
					</tr>
				</table>
			<div class="ge02"></div>
		</div>
	</form>
</body>
</html>
