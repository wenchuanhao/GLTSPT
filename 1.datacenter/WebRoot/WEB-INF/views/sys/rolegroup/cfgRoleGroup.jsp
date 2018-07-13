<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/common.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/autoDialog.js"></script>
	
<script type="text/javascript">
//打开权限列表--为角色组分配权限
	function openGroup(userId) {
		var url = "<%=basePath%>sys/rolegroup/groups/" + userId + "?index=" + Math.random();
	        dialog = new AutoDialog({
	            title : "分配用户",
	            width : 800,
	            height : 550,
	            padding : 0
	        });
	        dialog.setContent('<iframe src="' + url + '" width="100%" height="100%" frameborder="0" border="0"></iframe>');
	        dialog.show();
        }
     function Close(){
     	dialog.destroy();
     }
</script>
<script type="text/javascript">
   function doSubmit(){
	   document.all("pageIndex").value = "1";
	   document.forms[0].submit();
	}
   </script>
</head>
<body class="main_bg">
	<div class="mm_main_top01">
		<span class="mm_main_top01a"> <img
			src="/SRMC/rmpb/images/005.png" width="33" height="32" /> </span> <span
			class="mm_main_top01c">当前位置： 系统管理 &gt; 角色管理 &gt; <span
			class="or">角色组配置</span>
		</span>
	</div>
	<form name="form" method="post">
		<div class="mm_main_bj">
			<div class="mm_main_bd01">
				<div class="mm_m_bd_a">
					<span class="mm_m_bd_b"> <img
						src="/SRMC/rmpb/images/mm_pic07.gif" width="38" height="35" /> </span> <span
						class="mm_m_bd_c">查询</span>
				</div>

				<div class="ge01"></div>
				<table width="98%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="main_table03">
					<tr>
						<th width="100">登录账号</th>
						<td><input class="blue_bt_a" type="text" name="account"
							id="account" />
						</td>
						<th width="100">用户姓名</th>
						<td><input class="blue_bt_a" type="text" name="userName"
							id="userName" />
						</td>
						<td><!-- <input class="bnt_class07" type="submit" name="button3"
							id="button3" value="查 询" /> --> 
							<input class="bnt_class07" type="button" name="button3"	 id="button3" value="查 询" onclick="javascript:doSubmit();"/>	
							<input class="bnt_class08"	type="reset" name="button3" id="button6" value="重 置" />
						</td>
					</tr>
				</table>
				<div class="ge02"></div>
			</div>


			<div class="mm_main_bd01">
				<div class="mm_m_bd_a">
					<span class="mm_m_bd_b"> <img
						src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" /> </span> <span
						class="mm_m_bd_c">未分配用户</span>
				</div>
				<div class="ge01"></div>
				<table width="98%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="main_table01">
					<tr>
						<th width="10%">登录账号</th>
						<th width="10%">用户姓名</th>
						<th width="10%">所属机构</th>
						<th width="12%">手机号码</th>
						<th width="15%">办公邮件</th>
						<th width="7%">操作</th>
					</tr>
					<c:forEach items="${ITEMPAGE.items}" var="user" varStatus="i">
						<tr <c:if test="${i.index%2 != 0}">class="main_table04_bg"</c:if>
							style="cursor: hand;">
							<td>${user.account}</td>
							<td>${user.userName}</td>
							<td>${user.organizationId}</td>
							<td>${user.mobile}</td>
							<td>${user.email}</td>
							<td><a href="javascript:openGroup('${user.userId}')">分配 </a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="hy_next">
					<jsp:include flush="true" page="/public/include/navigate.jsp"></jsp:include>
				</div>
				<div class="ge02"></div>
			</div>
		</div>
		<div class="ge01"></div>
	</form>
</body>
</html>