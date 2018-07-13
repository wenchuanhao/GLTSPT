<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>组织机构树</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dtree.css" />
		<script src="/SRMC/rmpb/js/jquery-1.7.1.js" type="text/javascript"></script>
		<script src="/SRMC/rmpb/js/dtree/dtree.js" type="text/javascript"></script>
		<script type="text/javascript">
			function queryUser(orgName){
				alert();
				window.parent.user.location="<%=basePath%>sys/user/queryUserList?organizationName="+orgName+"&index="+Math.random();
			}
		</script>
	</head>
	<body>
	<form name="form">
			<script type="text/javascript">
			d = new dTree('d');
				<c:forEach items="${orgs}" var="org">
					<c:choose>
					<c:when test="${org.orgName == '南方基地'}">
							d.add('${org.orgId}', '-1', '<font color=#EF5900><b>组织机构</b></font>', '', '', '', '','//');
						</c:when>
						<c:otherwise>
							d.add('${org.orgId}','${org.parentId}', '${org.orgName}', '<%=basePath%>sys/user/queryUserList?organizationId=${org.orgId} & index=Math.random();', '', 'user');
						</c:otherwise>
					</c:choose>	
				</c:forEach>
			document.write(d);
		</script>
		</form>
	</body>
</html>
