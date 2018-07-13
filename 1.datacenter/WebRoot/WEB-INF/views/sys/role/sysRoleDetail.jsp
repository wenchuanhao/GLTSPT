<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>角色详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">
		var flag=true;
		function checkNamexUniqueness(){
				var roleName =$("#roleName").val();
				if(roleName!=""){
					$.post("<%=basePath%>/sys/role/checknameuniqueness", {
						"roleName" :roleName
					}, function(data) {
						
						if (data == "1"){
							 alert("角色名称已经存在!");
						}
					});
				}
			}
			function checkCodeUniqueness(){
				var roleCode =$("#roleCode").val();
				if(roleCode!=""){
					$.post("<%=basePath%>/sys/role/checkcodeuniqueness", {
						"roleCode" :roleCode
					}, function(data) {
						if (data == "0"){
							 form.action='<%=basePath%>/sys/role/modifySysRole';
							 form.submit();
						}
						if (data == "1"){
							 alert("角色编码已经存在!");
						}
					});
				}
			}
		function doSave() {
				if(checkVal("roleName",50,"请输入角色名称!",false))
				if(checkVal("roleCode",50,"请输入角色编码!",false))
				if(checkValLenth("roleDesc",256))
				checkNamexUniqueness();
				checkCodeUniqueness();
				
			}
</script>
	</head>
	<body class="bg_c_g">
		<form action="<%=basePath%>sys/role/saveSysRole" method="post"
			name="form">
			<div class="gl_m_r_nav">
				当前位置 : 系统管理 > 角色管理 > 角色详情
			</div>
			<table class="gl_m_r_n_tb01" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td valign="top">
						<div class="gl_bt_bnt01">
							角色信息
						</div>
						<!-- 
               <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                   <th width="100">创建人：</th>
                   <td>${role.createPerson}</td>
                   <th width="100">创建时间：</th>
                   <td>
                    <fmt:formatDate value="${role.createTime}" pattern="yyyy-MM-dd"/>
                   </td>
                 </tr>
                 <tr>
                   <th width="100">角色名称：</th>
                   <td>${role.roleName}</td>
                   <th width="100">角色编码：</th>
                   <td>
                    ${role.roleCode}
                   </td>
                 </tr>
                 <tr>
                   <th>已配置角色：</th>
                   <td colspan="3">暂无</td>
                 </tr>
                 <tr>
                   <th>描　　述：</th>
                   <td colspan="3">${role.roleDesc}</td>
                 </tr>
               </table>
                -->
						<table class="gl_table_a01_6L" width="100%" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<th width="100">
									创建人:
								</th>
								<td>
									${role.createPerson}
								</td>
								<th width="100">
									创建时间:
								</th>
								<td>
									<fmt:formatDate value="${role.createTime}" pattern="yyyy-MM-dd" />
								</td>
								<th width="100">
									角色名称:
								</th>
								<td>
									${role.roleName}
								</td>
							</tr>
							<tr>
								<th width="100">
									角色编码:
								</th>
								<td>
									${role.roleCode}
								</td>
								<th width="100">
									已配置角色:
								</th>
								<td colspan="3">
									暂无
								</td>
							</tr>
							<tr>
								<th>
									描 述：
								</th>
								<td colspan="5">
									${role.roleDesc}
								</td>
							</tr>
						</table>
						<div class="gl_ipt_03">
							<input name="input" type="button" class="gl_cx_bnt03" value="返回"
								onclick="doReturn('${location}')" />
								<!-- onclick="history.go(-1);" /> -->
						</div>
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
		function  doAaddOrg(){
			orgForm.submit();
		}
		function qx(){
			location.href="<%=basePath%>sys/role/roleConfigIndex";
		}
		
		function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		}
	</script>
	</body>
</html>