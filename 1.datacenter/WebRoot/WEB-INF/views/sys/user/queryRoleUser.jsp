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
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/select2css.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/common.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/autoDialog.js"></script>
<script type="text/javascript">
	//打开组织列表
		function openRoleGroup(userId,roleName,roleId) {
			$("#roleId").val(roleId);
			$("#roleName").val(roleName);
			$("#userId").val(userId);
			var url = "<%=basePath%>sys/user/sysRoleGroup";
			dialog = new AutoDialog({title:"分配角色组", width: 800, height: 400, padding: 0});
			dialog.setContent('<iframe src="'+url+'" width="100%" height="100%" frameborder="0" border="0"></iframe>');
			dialog.show();
		}
		function doSava(groupId,groupName){
			var roleId=$("#roleId").val();
			var roleName =$("#roleName").val();
			var userId=$("#userId").val();
			$.post("<%=basePath%>sys/user/assignvisitor", {
							"roleGroupId" : groupId,
							"userId" : userId,
							"flag" :1,
							"roleName":roleName,
							"groupName":groupName,
							"roleId":roleId
						}, function(data) {
							if (data != "") {
								if (data == "ok") {
									alert("分配成功");
									dialog.destroy();
								} else {
									alert("分配失败！");
								}
							}
						});
			$("#roleId").val("");
			$("#roleName").val("");
			$("#userId").val("");
}
</script>
<script type="text/javascript">
   function doSubmit(){
	   document.all("pageIndex").value = "1";
	   document.forms[0].submit();
	}
   </script>
</head>
<body class="main_bg">

  <form name="form" method="post" action="<%=basePath%>sys/user/queryRoleUser">
  	<input type="hidden"  id="userId" name="userId" />
  	<input type="hidden"  id="roleName" name="roleName" />
	<input type="hidden"  id="roleId" name="roleId" />
	<input type="hidden"  id="userIds" name="userIds" />
    <div class="mm_main_bj">
    <div class="mm_main_bd11">
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
	          <td>
	          	<!-- <input class="bnt_class07" type="submit" name="button3" id="button3" value="查 询" /> -->
	          	<input class="bnt_class07" type="button" name="button3" id="button3" value="查 询" onclick="javascript:doSubmit();"/>
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
	        <span class="mm_m_bd_c">用户信息</span>
	      </div>
	      
     	 <div class="ge01"></div>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table01">
	        <tr>
	          <th width="20%">登录账号</th>
	          <th width="15%">用户姓名</th>
			  <th width="25%">所属机构</th>
	        </tr>
				<tr <c:if test="${i.index%2 != 0}">class="main_table04_bg"</c:if> style="cursor: hand;" ondblclick="sysUserInfo('${user.userId}')">
		          <td>${user.account}</td>
		          <td>${user.userName}</td>
				  <td>${user.organizationName}</td>
		        </tr>
	      	</table>
			<div class="ge02"></div>
		</div>
		<div class="mm_main_bd01">
	      <div class="mm_m_bd_a">
	        <span class="mm_m_bd_b">
	        	<img src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" />
	        </span>
	        <span class="mm_m_bd_c">用户信息</span>
	      </div>
	      
     	 <div class="ge01"></div>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table01">
	        <tr>
	          <th width="20%">所属角色</th>
			  <th width="25%">操作</th>
	        </tr>
	        	<c:forEach items="${roleList}" var="role" varStatus="i">
	        	<tr>
		          <td>${role[0].roleName}</td>
				  <td>
				    <a href="javascript:void(0);" onclick="openRoleGroup('${user.userId}','${role[0].roleName}','${role[0].roleId}')" title="分配角色组">分配角色组</a>
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