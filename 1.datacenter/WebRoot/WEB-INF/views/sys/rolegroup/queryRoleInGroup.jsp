<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"></link>
<link href="/SRMC/rmpb/css/left_a01.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
</head>
<body class="main_bg">
	<div class="mm_main_top01">
		<span class="mm_main_top01a">
			<img src="/SRMC/rmpb/images/005.png" width="33" height="32" />
		</span> 
		<span class="mm_main_top01c">当前位置： 系统管理 &gt; 角色组管理 &gt; <span class="or">查看角色</span></span>
	</div>

	<div class="mm_main_bd01">
		<div class="mm_m_bd_a">
			<span class="mm_m_bd_b"><img
				src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" />
			</span> <span class="mm_m_bd_c">角色信息</span>
		</div>

		<div class="ge01"></div>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="main_table03">
				<tr>
					<th width="15%"><span class="or">*</span>角色名称</th>
					<td width="85%" colspan="3">
						${roleGroup.roleGroupname}
					</td>
				</tr>
				<tr>
					<th width="15%"><span class="or">*</span>所拥有角色名称</th>
					<td width="85%" colspan="3">
						<ul class="box">
							<c:forEach items="${role}" var="role">
								<li>${role[1].roleName}</li>
							</c:forEach>
						</ul>
					</td>
				</tr>
				
				<tr>
					<th width="15%"><span class="or">*</span>角色组描述</th>
					<td width="85%" colspan="3">
					   ${roleGroup.roleGroupdesc}
					</td>
				</tr>
			</table>
		<div class="ge02"></div>
	</div>
</body>
</html>