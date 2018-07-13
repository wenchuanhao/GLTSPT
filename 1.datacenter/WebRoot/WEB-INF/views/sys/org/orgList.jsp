<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>组织机构</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dtree.css" />
		<script src="/SRMC/rmpb/js/jquery-1.7.1.js" type="text/javascript"></script>
		<script src="/SRMC/rmpb/js/dtree/dtreeRadio.js" type="text/javascript"></script>
	</head>
	<body>
		<form name="form">
			<script type="text/javascript">
			d = new dTree('d');
				<c:forEach items="${orgs}" var="org">
					<c:choose>
					<c:when test="${org.orgName == '南方基地'}">
							d.add('${org.orgId}', '-1', '<font color=#EF5900><b>组织机构</b></font>', '', '', '', '//');
						</c:when>
						<c:otherwise>
							d.add('${org.orgId}','${org.parentId}', '${org.orgName}', false, '', '');
						</c:otherwise>
					</c:choose>	
				</c:forEach>
			document.write(d);
		</script>
		</form>
		<input class="text_bt02" type="button" onclick="doSave()" value=" 确 定 " />

		<script type="text/javascript">
				function doSave(){
				var orgId ="";
				var orgName="";
				$("input[type=radio]:checked").each(function(){
					orgId=$(this).attr("id");
					orgName=$(this).val();
				   }
				 );		 
				 if(""==orgId || ""==orgName){
				 	alert("您没有选中任何组织!");
				 	return;
				 }
				var o = {"orgId":orgId,"orgName":orgName};
				window.parent.setVal(o);
			}
		</script>
	</body>
</html>
