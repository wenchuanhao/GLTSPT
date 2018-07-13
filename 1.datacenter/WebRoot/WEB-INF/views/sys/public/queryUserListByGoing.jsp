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
<script type="text/javascript" src="/SRMC/rmpb/js/png_t.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/select2css.js"></script>
<script type="text/javascript">
	function doSava(userName,userId,account,organizationName,organizationId){
			var ids="";
			var chks = document.getElementsByName('checkbox');
			var j = 0;
			for ( var i = 0; i < chks.length; i++) {
				if (chks[i].checked){
					j++;
					ids+=chks[i].value+",";
				}
			}
			if (j < 1) {
				alert("请选择用户！");
			} else {
				form.action = '<%=basePath%>sys/user/deleteSysUser?'+Math.random();
				form.submit();
			}
		var o = {"userId":userId,"userName":userName,"account":account,"organizationName":organizationName,"organizationId":organizationId};
		window.parent.parent.setVal(o);
	}
</script>
</head>

<body class="main_bg">
  <form name="form" method="post" action="<%=basePath%>sys/user/querySysUser">
  	<input type="hidden"  id="userIds" name="userIds" />
    <div class="mm_main_bd01">
      <div class="mm_m_bd_a">
        <span class="mm_m_bd_b">
        	<img src="/SRMC/rmpb/images/mm_pic07.gif" width="38" height="35" />
        </span>
        <span class="mm_m_bd_c">查询</span>
      </div>
      
      <div class="ge01"></div>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table03">
	        <tr>
	          <th width="100" >
	          	登录名
	          </th>
	          <td>
	          	<input class="blue_bt_a" type="text" name="account" id="account" />
	          </td>
	          <th width="100">
	          	用户姓名
	          </th>
	          <td>
	          	<input class="blue_bt_a" type="text" name="userName" id="userName" />
	          </td>
	          <td>
	          	<input class="bnt_class07" type="button" name="button3" id="button3" value="查 询" onclick="doSearch()"/>
	          	<input class="bnt_class08" type="reset" name="button3" id="button6" value="重 置" />
	          </td>
	        </tr>
	      </table>
      <div class="ge02"></div>
  	</div>
  
      
     <div class="mm_main_bd01">
	      <div class="mm_m_bd_a">
	        <span class="mm_m_bd_b">
	        	<img src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" />
	        </span>
	        <span class="mm_m_bd_c">用户管理</span>
	      </div>
	      
      <div class="ge01"></div>
      	<table width="98%" align="center">
	        <tr>
	          <td height="30">
	          	<span class="pic01">
	            	<input class="bnt_class05" type="button" name="button" id="button" value="添加" onclick="toAddSysUser()"/>
	            </span>
	           </td>
	        </tr>
	      </table>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table01">
	        <tr>
	          <th width="5%">&nbsp;</th>
	          <th width="20%">登录账号</th>
	          <th width="15%">用户姓名</th>
			  <th width="25%">所属机构</th>
			  <th width="15%">创建人</th>
			  <th width="10%">状态</th>
	        </tr>
			<c:forEach items="${ITEMPAGE.items}" var="user" varStatus="i">
				<tr <c:if test="${i.index%2 != 0}">class="main_table04_bg"</c:if> style="cursor: hand;">
				  <td><input type="checkbox" name="checkbox" id="checkbox" value="${user.userId}"/></td>
		          <td>${user.account}</td>
		          <td>${user.userName}</td>
				  <td>${user.organizationName}</td>
				  <td>${user.createrName}</td>
				  <td>
				  	<c:choose>
		          		<c:when test='${user.isActivate=="1"}'>
		          			正常
		          		</c:when>
		          		<c:otherwise>
		          			禁用
		          		</c:otherwise>
		          	</c:choose>
				  </td>
		        </tr>
			</c:forEach>
	      	</table>
			<div class="hy_next">
				<jsp:include flush="true" page="/public/include/navigate.jsp"></jsp:include>
			</div>
			<div class="ge02"></div>
		</div>
	<div class="ge01"></div>
	</form>
</body>
</html>