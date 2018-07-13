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
		<title>用户角色详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">
		
		function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		}		
		</script>
	</head>
	<body class="bg_c_g">
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 > 角色管理 > 用户角色详情信息
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
							</td>
							<th width="100">
								用户姓名:
							</th>
							<td>
								${user.userName}
							</td>
								<th width="100">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户状态:
							</th>
							<td>
								${zt}
							</td>
						</tr>
						<tr>
							<th width="100">
								&nbsp;&nbsp;&nbsp;用户手机号:
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
								是否接受SMS:
							</th>
							<td>
								${js}
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
					<div class="ge_a01b"></div>
					<div class="gl_bt_bnt01">
						用户角色配置信息
					</div>
					<table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
						
						<tr>
							<th width="100">
								已配置角色:
							</th>
							<td colspan="4" style="word-break: break-all;">
								<div id="roleDisplay" style="display: block;">
								${roleNames}
								</div>
							</td>
						</tr>
						<tr>
							<th width="100">
								已配置区域:
							</th>
							<td colspan="4">
						       <div id="roleDisplay" style="display: block;">
								${areaNames}
								</div>
							</td>
						</tr>
					</table>
					<div class="gl_ipt_03">
						<!-- <input name="input" type="button" class="gl_cx_bnt03" value="返回" onclick="history.go(-1);" /> -->
					    <input name="input" type="button" class="gl_cx_bnt03" value="返回" onclick="doReturn('${location}')" />
					</div>
                </div>
	</body>
</html>
