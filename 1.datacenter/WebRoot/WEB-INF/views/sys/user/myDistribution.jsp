<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link href="/SRMC/rmpb/css/left_a01.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/png_t.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/select2css.js"></script>
<script type="text/javascript">
function undo(userId,groupId){
	form.action="<%=basePath%>sys/user/undo/"+userId+"/"+groupId;
	form.method="get";
	form.submit();
}
</script>
</head>

<body class="main_bg">
	 <div class="mm_main_top01">
    	<span class="mm_main_top01a">
    		<img src="/SRMC/rmpb/images/005.png" width="33" height="32" />
    	</span>
    	<span class="mm_main_top01c">当前位置： 系统管理 &gt; 用户管理 &gt; <span class="or">我的分配记录列表</span></span>
    </div>
     <div class="ge01"></div>
  <form name="form" method="post" action="<%=basePath%>sys/user/myDistribution">
  	<input type="hidden"  id="userIds" name="userIds" />
    <div class="mm_main_bj">
      
     <div class="mm_main_bd01">
	      <div class="mm_m_bd_a">
	        <span class="mm_m_bd_b">
	        	<img src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" />
	        </span>
	        <span class="mm_m_bd_c">我的分配记录</span>
	      </div>
	      
      <div class="ge01"></div>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table01">
	        <tr>
	          <th width="15%">操作的角色</th>
			  <th width="25%">分配的角色组</th>
			  <th width="25%">操作</th>
	        </tr>
			<c:forEach items="${userGroup}" var="userGroup" varStatus="i">
				<tr <c:if test="${i.index%2 != 0}">class="main_table04_bg"</c:if> style="cursor: hand;">
		          <td>${userGroup.roleName}</td>
		          <td>${userGroup.groupName}</td>
				  <td>
				    <a href="javascript:void(0);" onclick="undo('${userGroup.roleId}','${userGroup.groupId}')" title="撤销分配">撤销分配</a>
				  </td>
		        </tr>
			</c:forEach>
	      	</table>
			<div class="ge02"></div>
		</div>
	<div class="ge01"></div>
	</div>
	</form>
</body>
</html>