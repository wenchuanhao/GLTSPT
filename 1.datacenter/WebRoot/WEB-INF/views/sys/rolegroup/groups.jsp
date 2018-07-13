<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>修改需求单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"></link>
<link href="/SRMC/rmpb/css/left_a01.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
<script type="text/javascript">
    function doAssign(){
    		var roleId ="";
    		var allRole ="";
			$("input[name='roles']:checked").each(function(){
			   roleId+= $(this).val()+",";
			});
			$("input[name='roles']").each(function(){
			   allRole+= "'"+$(this).val()+"',";
			});
			 var userId = $("#userId").val();
			  var params = {
		        roleId : roleId,
		        allRole : allRole,
		        userId : userId
	   		 };
			  var url ="<%=basePath%> sys/rolegroup/assignUserRole";
		    $.post(url, params, function(data)
		    {
			    if (data == "ok") {
				    alert("操作成功");
				    window.parent.Close();
			    } else {
				    alert("操作失败");
			    }
		    });
    }
</script>
</head>

<body class="main_bg">
	<input type="hidden" value="${userId}" id="userId" />
	<div class="mm_main_bj">
		<div class="mm_main_top01">
			<span class="mm_main_top01a"><img
				src="/SRMC/rmpb/images/005.png" width="33" height="32" /> </span> <span
				class="mm_main_top01c">当前位置：系统管理 &gt; 角色组管理 &gt; <span
				class="or"> 角色组配置</span> </span>
		</div>
		
			<div class="mm_main_bd01">
			<div class="mm_m_bd_a">
				<span class="mm_m_bd_b"><img src="/SRMC/rmpb/images/mm_pic05.gif" width="38" height="35" /> </span> 
				<span class="mm_m_bd_c">用户信息</span>
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
					</tr>
						<tr <c:if test="${i.index%2 != 0}">class="main_table04_bg"</c:if>
							style="cursor: hand;">
							<td>${user.account}</td>
							<td>${user.userName}</td>
							<td>${user.organizationName}</td>
							<td>${user.mobile}</td>
							<td>${user.email}</td>
						</tr>
				</table>
				<div class="ge01"></div>
			</div>
		
		
		<div class="mm_main_bd01">
			<div class="mm_m_bd_a">
				<span class="mm_m_bd_b"><img
					src="/SRMC/rmpb/images/mm_pic05.gif" width="38" height="35" /> </span> <span
					class="mm_m_bd_c">角色组列表</span>
			</div>
			<div class="ge01"></div>

			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="main_table03">

				<c:forEach items="${roleGroups}" var="roleGroup">
					<tr>
						<td>${roleGroup.roleGroupname}</td>
						<td><c:forEach items="${roleGroup.roles}" var="role">
								<input type="checkbox" name="roles" 
									<c:if test='${role.allowUpdate =="99" }'>checked="checked" </c:if>
									value="${role.roleId}" />${role.roleName}
								</c:forEach></td>
					</tr>
				</c:forEach>
			</table>
			<div class="ge01"></div>
			<table width="96%" align="center">
				<tr>
					<td height="60" colspan="4" align="center"><input
						class="bnt_class02" type="button" name="button3" id="button3"
						value="保 存" onclick="doAssign()" /> 
						<input class="bnt_class03"
						type="button" name="button4" id="button4" value="取 消"
						onclick="window.parent.Close()" />
					</td>
				</tr>
			</table>
				<div class="ge01"></div>
		</div>
	</div>
</body>
</html>
