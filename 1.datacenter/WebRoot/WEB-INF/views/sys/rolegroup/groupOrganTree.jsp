<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配置团队角色</title>

<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dTree.css" />
<SCRIPT src="/SRMC/rmpb/js/dtree.js" type=text/javascript></SCRIPT>
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
<script src="/SRMC/rmpb/js/dtree.js" type=text/javascript></script>
</head>
<body class="bg_c_g"  onload="clearCookieDD();">
<div class="d_tree_div">
<script type="text/javascript">
function doSerachsByOrgId(organizationId){   
        window.parent.document.getElementById("pageIndex1").value=1;    
	    window.parent.doSerachsByOrgId(organizationId); 
	 
}
    
    
function clearCookieDD(){   
//        d.closeAll();      
}
    
</script>
<SCRIPT type=text/javascript>
		 	d = new dTree('d');
				<c:forEach items="${orgs}" var="org">
					<c:choose>
					<c:when test="${org.orgName == '南方基地'}">
							d.add('${org.organizationId}', '-1', "<font oncontextmenu=loadMenu('${org.organizationId}') onclick=doSerachsByOrgId('${org.organizationId}') color=#EF5900><b>公司组织架构</b></font>", '', '', '', '','//');
						</c:when>
						<c:otherwise>
						   d.add('${org.organizationId}','${org.parentId}', "<font oncontextmenu=loadMenu('${org.organizationId}')  >${org.orgName}</font>", 'javascript:doSerachsByOrgId(\'${org.organizationId}\')', '', '');
						</c:otherwise>
					</c:choose>	
				</c:forEach>
			document.write(d);
	</SCRIPT>
</div>
</body>
</html>	