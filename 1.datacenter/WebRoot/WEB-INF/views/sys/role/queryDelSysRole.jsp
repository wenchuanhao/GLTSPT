<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link href="/SRMC/rmpb/css/left_a01.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/common.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/autoDialog.js"></script>
<script type="text/javascript">
	function getSelectCount() {
		var ids="";
		document.getElementById("roleIds").value="";  
		var chks = document.getElementsByName('checkbox');
		var j = 0;
		for ( var i = 0; i < chks.length; i++) {
			if (chks[i].checked){
				j++;
				ids+=chks[i].value+",";
			}
		}
		document.getElementById("roleIds").value=ids;
		return j;
	}
	function doDeleteItems() {
		if (getSelectCount() < 1) {
			alert("请选择需要删除的名单！");
		} else if (confirm("数据删除后不可以恢复，您确定要删除吗？")) {
			form.action = '<%=basePath%>sys/role/deleteSysRole';
			form.submit();
		}
	}
	function toAddSysRole(){
		window.location.href = "<%=basePath%>sys/role/addSysRole";
	}
	function toModifySysRole(roleID){
		window.location.href = '<%=basePath%>sys/role/modifySysRole/'+roleID+'?'+Math.random();
	}
	//打开权限列表--为角色分配权限
	function openPrivilege(roleId) {
		$("#srId").val(roleId);
		var url = "<%=basePath%>sys/privilege/listModule/" + roleId + "?index=" + Math.random();
		dialog = new AutoDialog({title:"分配权限", width: 300, height: 400, padding: 0});
		dialog.setContent('<iframe src="'+url+'" width="100%" height="100%" frameborder="0" border="0"></iframe>');
		dialog.show();
		
	}
	//打开权限列表--为角色分配区域
	function openOrg(roleId) {
		$("#srId").val(roleId);
		var url = "<%=basePath%>sys/privilege/listOrg/" + roleId + "?index=" + Math.random();
		dialog = new AutoDialog({title:"分配区域", width: 300, height: 400, padding: 0});
		dialog.setContent('<iframe src="'+url+'" width="100%" height="100%" frameborder="0" border="0"></iframe>');
		dialog.show();
		
	}
	//打开用户列表--为角色分配权限
	function openUser(roleId) {
		var url = "<%=basePath%>sys/privilege/listUser/" + roleId + "?index=" + Math.random();
		dialog = new AutoDialog({title:"分配用户", width: 800, height: 550, padding: 0});
		dialog.setContent('<iframe src="'+url+'" width="100%" height="100%" frameborder="0" border="0"></iframe>');
		dialog.show();
	}
		function allocatedUser(roleId) {
		var url = "<%=basePath%>sys/privilege/allocatedUser/" + roleId + "?index=" + Math.random();
		dialog = new AutoDialog({title:"查看已分配用户", width: 800, height: 550, padding: 0});
		dialog.setContent('<iframe src="'+url+'" width="100%" height="100%" frameborder="0" border="0"></iframe>');
		dialog.show();
	}
	function  setVal(o){
			var srId=$("#srId").val();
			var privileges = o;
			$.post("<%=basePath%>sys/privilege/assignprivilege", {
				"privileges" : privileges,
				"srId" : srId
			}, function(data) {
				if (data != "") {
					if (data == "ok") {
						alert("分配成功！");
						dialog.destroy();
					} else {
						alert("分配失败");
					}
				}
			});
			$("#srId").val("");
		}
	function  setOrgVal(o){
			var srId=$("#srId").val();
			var orgs = o;
			$.post("<%=basePath%>sys/privilege/assignOrgs", {
				"orgs" : orgs,
				"srId" : srId
			}, function(data) {
				if (data != "") {
					if (data == "ok") {
						alert("分配成功！");
						dialog.destroy();
					} else {
						alert("分配失败");
					}
				}
			});
			$("#srId").val("");
		}
</script>
</head>

<body class="main_bg">
    <div class="mm_main_bj">
    
    <div class="mm_main_top01">
    	<span class="mm_main_top01a">
    		<img src="/SRMC/rmpb/images/005.png" width="33" height="32" />
    	</span>
    	<span class="mm_main_top01c">当前位置： 系统管理 &gt; 角色管理 &gt; <span class="or">角色列表</span></span>
    </div>
    <form name="form" method="post" action="<%=basePath%>sys/role/querySysRole">
    	<input type="hidden" id="srId" />
    	<input  type="hidden" name="roleIds" id="roleIds"/>
	    <div class="mm_main_bd01">
	      <div class="mm_m_bd_a">
	        <span class="mm_m_bd_b"><img src="/SRMC/rmpb/images/mm_pic07.gif" width="38" height="35" /></span>
	        <span class="mm_m_bd_c">查询</span>
	      </div>
	      
	      <div class="ge01"></div>
			  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table03">
				   <tr>
					  <th width="15%" ><span class="or">*</span>角色名称</th>
					  <td width="25%">
							<input class="blue_bt_a" type="text" value="${form.roleName}" name="roleName"/>
					  </td>
					  <td width="25%">
							<input class="bnt_class07" type="button" value="查 询" onclick="doSearch()"/>
							<input class="bnt_class08" type="reset" value="重 置" />
					  </td>
				  </tr>
			  </table>
	      <div class="ge02"></div>
	  </div>
	  
	      
	     <div class="mm_main_bd01">
			  <div class="mm_m_bd_a">
				<span class="mm_m_bd_b"><img src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" /></span>
				<span class="mm_m_bd_c">角色管理</span>
			  </div>
			  
			  <div class="ge01"></div>
			  <table width="98%" align="center">
				<tr>
				  <td height="30"><span class="pic01">
					  <input class="bnt_class06" type="button" name="button2" id="button2" value="删除" onclick="javascript:doDeleteItems();"/>
					  </span></td>
				</tr>
			 </table>
			  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table01">
						<tr>
						  <th width="30">&nbsp;</th>
						  <th>角色名称</th>
						  <th>角色描述</th>
						  <th>创建时间</th>                   
						</tr>
					 <c:forEach items="${ITEMPAGE.items}" var="role" varStatus="i">
						<tr <c:if test="${i.index%2 != 0}">class="main_table04_bg"</c:if> style="cursor: hand;" ondblclick="toModifySysRole('${role.roleId}')">
						  <td><input type="checkbox" name="checkbox" id="checkbox" value="${role.roleId}"/></td>
						  <td>${role.roleName}</td>
						  <td>${role.roleDesc}</td>
						  <td>	<fmt:formatDate value="${role.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
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