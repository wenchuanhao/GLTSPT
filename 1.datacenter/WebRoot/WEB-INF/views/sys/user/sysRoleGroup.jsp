<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
<link href="/SRMC/rmpb/css/left_a01.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/select2css.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/common.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/autoDialog.js"></script>
<script type="text/javascript">
function doSava(groupId,groupName){
	window.parent.doSava(groupId,groupName);
}
</script>
</head>

<body class="main_bg">
	<div class="mm_main_bj">
		<form name="form" method="post">
			<input type="hidden" id="roleGroupid" /> <input type="hidden"
				name="roleGroupIds" id="roleGroupIds" />


			<div class="mm_main_bd01">
				<div class="mm_m_bd_a">
					<span class="mm_m_bd_b"><img
						src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" />
					</span> <span class="mm_m_bd_c">角色组信息</span>
				</div>

				<div class="ge01"></div>
				<table width="98%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="main_table01">
					<tr>
						<th>角色组名称</th>
						<th>角色描述描述</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${ITEMPAGE.items}" var="roleGroup" varStatus="i">
						<tr <c:if test="${i.index%2 != 0}">class="main_table04_bg"</c:if>
							style="cursor: hand;">
							<td>${roleGroup.roleGroupname}</td>
							<td>${roleGroup.roleGroupdesc}</td>
							<td>
								<input class="bnt_class07" type="button" name="button4" id="button3" value="分 配" onclick="doSava('${roleGroup.roleGroupid}','${roleGroup.roleGroupname}')"/> 
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="hy_next">
					<jsp:include flush="true" page="/public/include/navigate.jsp"></jsp:include>
				</div>
				<div class="ge02"></div>
			</div>
		</form>
	</div>
	<div class="ge01"></div>
</body>
</html>