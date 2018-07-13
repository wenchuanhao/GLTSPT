<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<title>配置用户角色</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">
		function dosubmits(){
		    var roles=document.getElementsByName("roles");
		    var account=document.getElementById("account").value;
		    var count=0;
		    var roleId="";
		    var userId=$("#userId").val();
		    for(var f=0;f<roles.length;f++){
		    	if(roles[f].checked==true){
		    		roleId=roles[f].value;
		    		count++;
		    		break;
		    	}
		    }
		    if(count==0){
		    	alert("请选择角色！");
		    	return false;
		    }
			
			  
		
		
			if(userId==""){
				alert("用户编号不能为空！");
				return false;
			}
			$.post("<%=basePath%>sys/role/addUserRole",{"userId":userId,"roleId":roleId},function(data){
				if(data=="y"){
					alert("用户角色设置成功!");
					window.location.href="<%=basePath%>sys/role/queryUserRoleIndex?account="+account;
				}
				if(data=="n"){
					alert("用户角色设置失败!");
					window.location.href="<%=basePath%>sys/role/queryUserRoleIndex?account="+account;
				}
			});
		}
	
</script>
	</head>
	<body class="bg_c_g">
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 > 角色管理 > 配置用户角色
		</div>
		<div class="gl_import_m">
					<div class="gl_bt_bnt01">
						用户信息
					</div>
					<table class="gl_table_a01_6L" width="100%" border="0"
						cellspacing="0" cellpadding="0">
						<tr>
							<th width="100">
								用户登录帐号:
							</th>
							<td>
								${user.account}
								<input type="hidden" value="${account}" id="account"/>
							</td>
							
							<th width="100">
								用户姓名:
							</th>
							<td>
								${user.userName}
							</td>
							<th width="100">
								用户所属组织:
							</th>
							<td>
								${orga}
							</td>
						</tr>
						<tr>
							<th width="100">
								用户手机号码:
							</th>
							<td>
								${user.mobile}
							</td>
							<th width="100">
								用户邮箱:
							</th>
							<td>
								${user.email}
							</td>
							<th width="100">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户状态:
							</th>
							<td>
								${zt}
								<input type="hidden" id="userId" value="${userid}"/>
							</td>
							
						</tr>
						<tr>
							<th width="100">
								用户所属组织:
							</th>
							<td colspan="5">
								${orga}
							</td>
					  </tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td valign="top">
					<div class="gl_bt_bnt01">
						配置角色
					</div>
					<table class="gl_table_a01" width="100%" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
							<th width="100">
								角色名称:
							</th>
							<td colspan="3">
								<input class="gl_text01_a" type="text" id="xx" name="xx" size="35" />
								&nbsp;
								<input type="button" onclick="searchRole()" class="gl_cx_bnt03"
									value="查询" />
							</td>
						</tr>
						<tr>
							<th width="100">
								可选角色:
							</th>
							<td colspan="4" style="word-break: break-all;">
								<div id="roleDisplay" style="display: block;">
									<c:forEach items="${listRoles}" var="org" varStatus="i">
										<input type="radio" value="${org.roleId}" name="roles" />${org.roleName}&nbsp;
		                    	 	    <c:if test="${(i.index+1)%4==0}">
										<br/>	
										</c:if>
									</c:forEach>
								</div>
							</td>
						</tr>
			
					</table>
					<div class="gl_ipt_03">
									<input name="input" type="button" class="gl_cx_bnt03"
										value="增加" onclick="dosubmits()" />
									&nbsp;
									<input name="input" type="button" class="gl_cx_bnt03"
										value="返回" onclick="doReturn('${location}')" />
									<!-- <input name="input" type="button" class="gl_cx_bnt03"
										value="返回" onclick="history.go(-1);" /> -->
								</div>
		</div>
		<script type="text/javascript">
		function  doAaddOrg(){
			orgForm.submit();
		}
		function searchRole(){
			var roleName=$("#xx").val();
			var userId = $("#userId").val();
			$.post("<%=basePath%>sys/role/getRolesByText",{"roleName":roleName,"userId":userId},function(data){
				if(data!='n'){
					document.getElementById("roleDisplay").innerHTML=data;
				}
			});
		}
		function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		}
	</script>
	</body>
</html>
