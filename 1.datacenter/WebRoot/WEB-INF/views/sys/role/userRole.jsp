<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String basePath = request.getContextPath() + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>给角色赋予用户</title>
	
	<link href="/SRMC/rmpb/css/style3.css" type="text/css" rel="stylesheet" />
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dTree.css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<SCRIPT src="/SRMC/rmpb/js/dtree.js" type=text/javascript></SCRIPT>
	<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
	<script src="/SRMC/rmpb/js/dtree.js" type=text/javascript></script>
	
	<style type="text/css">
	.gl_table_a01 td {
		border: none;
	}
	
	.gl_table_a01 {
		margin: 0 auto;
	}
	</style>

<script type="text/javascript">
  	 function  setNodeName(nodeId,nodeName,liId){
	   	document.getElementById("nodeNameText").innerHTML=nodeName;
	   	var organizationId = document.getElementById("organizationId").value;
	   	var liName = document.getElementsByTagName("li");
	   	for(i=0;i<liName.length;i++){
	   		liName[i].setAttribute("class","");
	   	}
	   	document.getElementById(liId).setAttribute("class","hover");
	   	jQuery("#flowNodeId").val(nodeId);
	   	jQuery("#flowNodeIdTemp").val(nodeId);
	   	document.getElementById("toGetUser").src="<%=basePath%>sys/role/userList?flowNodeId=" + nodeId +"&organizationId="+organizationId;
		document.getElementById("userFlowActor").src="<%=basePath%>sys/role/getAllConfigUser?flowNodeId=" + nodeId;
		document.getElementById("toTree").src="<%=basePath%>sys/role/getOrganizationTree?flowNodeId=" + nodeId;
  	}
  	$(document).ready(function(){
  		if("${roleId}" != ""){
	  		$("#ones1").click();
  		}
  	});
  	 //查询
     function doSerachs(){
       $("#userForm").attr("action","<%=basePath%>sys/role/userList");
       form.method="post";
       form.target="toGetUser";
       
       form.submit();
     }
  	
  	function doSerachsByOrgId(organizationId){
              
    	$("#userForm").attr("action","<%=basePath%>sys/role/userList");
       	jQuery("#organizationId").val(organizationId);
       	form.method="post";
        form.target="toGetUser";
        form.submit();
    }
    
    function doReturn(location){
        var path = "<%=basePath%>" + location;
        window.location.href = path;
	}
</script>

</head>

<body class="bg_c_g">
	<div class="gl_m_r_nav">当前位置 : 系统管理 > 角色管理 > 给角色赋予用户</div>
	<input type="hidden" name="tflowCode" id="tflowCode" value="${requestScope.flowCode}" />

	<table class="tuand_tab01" border="0" cellspacing="0" cellpadding="0" id="showTable">
		<input type="hidden" value="" id="flowNodeId" name="flowNodeId" />
		<input type="hidden" value="${pageSize2}" id="pageSize2"
			name="pageSize2" />
		<tr>
			<th style="width:15%">第一步: 选择角色</th>
			<th colspan="2" style="width:50%">第二步: 分配用户</th>
			<th style="width:35%">第三步: 已选择的用户</th>
		</tr>
		<tr>
			<td valign="top">
				<ul class="tuand_ul01" scrolling="yes">
					<li>
						<p class="tuand_li_l" id="roleName1">
							<strong>系统角色名称:</strong>
						</p></li>
					<c:forEach items="${sysRoleList}" var="flowNode" varStatus="i">
						<li id="ones${i.index+1}"
							onclick="setNodeName('${flowNode.roleId}','${flowNode.roleName}','ones${i.index+1}')"
							title="${flowNode.roleName}">
							<p class="tuand_li_l">${flowNode.roleName}(${flowNode.roleCode})</p></li>
					</c:forEach>
				</ul>
				<div class="ge01"></div>
			</td>
			
			<td colspan="2" valign="top">
				<table width="100%" class="tuan02_bao01" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<td width="85" style="padding-left:5px;">当前角色名称:</td>
						<td><p class="tuand_li_l" id="nodeNameText"></p>
						</td>
					</tr>
				</table>
				<table width="100%" class="tuan02_bao01" border="0" cellspacing="0"
					cellpadding="0">
					<tr style="height:1px; overflow: hidden;">
						<td colspan="5" style="padding: 0 7px;"><div
								style="height:0px; overflow:hidden; border-bottom:#CCC 1px dotted;"></div>
						</td>
					</tr>
				</table>
				<table width="100%" class="tuan02_bao01" border="0" cellspacing="0"
					cellpadding="0">
					<tr style="height:1px; overflow: hidden;">
						<td colspan="5" style="padding: 0 7px;"><div
								style="height:0px; overflow:hidden; border-bottom:#CCC 1px dotted;"></div>
						</td>
					</tr>
				</table>
				<form id="userForm" name="form" action="<%=basePath%>sys/role/userList">
					<table class="tuand_tab01" border="0" cellspacing="0" cellpadding="0" id="userFormTable">
						<input type="hidden" value="" id="organizationId" name="organizationId" />
						<!--  <input type="hidden" value="" id="pageSize" name="pageSize"/> -->
						<input type="hidden" value="" id="pageSize1" name="pageSize1" />
						<input type="hidden" value="" id="pageIndex1" name="pageIndex1" />
						<input type="hidden" value="" id="flowNodeIdTemp" name="flowNodeIdTemp" />
						<!--  <input type="hidden" value="" id="pageSizeRole" name="pageSizeRole"/> -->
						<tr>
							<td colspan="2">
								<table class="gl_table_a01" width="100%" border="0"
									cellspacing="0" cellpadding="0">
									<tr>
										<td style="width:42%;">登录账号：<input style=" width:60%;"
											type="text" name="account" id="account"
											value="${form.account }" />
										</td>
										<td style="width:42%;">用户姓名： <input style=" width:60%;"
											type="text" name="userName" id="userName"
											value="${form.userName }" />
										</td>
										<td style="padding-top:2px;"><input id="img" name="img"
											type="button" class="gl_cx_bnt03" value="查 询"
											onclick="doSerachs();" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table class="tuand_tab01" border="0" cellspacing="0"
						cellpadding="0" id="userListTable">
						<tr>
							<td width="50%" valign="top">
								<iframe id="toTree"
									name="toTree"
									style="height: 450px; width: 100%; visibility: inherit;"
									scrolling="no" frameborder="0" marginwidth="0"
									marginheight="0"
									src="<%=basePath%>sys/role/getOrganizationTree"></iframe>

							</td>
							<td width="50%">
								<div style="padding-top: 4px">
									<font color=#EF5900><b>&nbsp;&nbsp;&nbsp;用户列表</b></font>
								</div>
								<div class="tuan_d02">
									<iframe id="toGetUser" name="toGetUser"
										style="height: 450px; width: 100%; visibility: inherit;"
										scrolling="no" frameborder="0" marginwidth="0"
										marginheight="0" src="<%=basePath%>sys/role/userList"></iframe>
								</div>
								<div class="ge01"></div>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</td>
			<td valign="top">
				<table class="tuand_tab01" border="0" cellspacing="0"
					cellpadding="0" id="userNodeListTable">
					<div class="tuan_d02">
						<!-- 查看配置关系 -->
						<iframe id="userFlowActor" name="userFlowActor"
							style="height: 550px;width: 100%; visibility: inherit;"
							scrolling="no" frameborder="0" marginwidth="0" marginheight="0"
							src="<%=basePath%>sys/role/getAllConfigUser"> </iframe>
					</div>
				</table>
			</td>
		</tr>
	</table>
	<c:if test="${empty roleId }">
		<div class="gl_ipt_03">
			<input name="input" type="button" class="gl_cx_bnt03" value="返回"
				onclick="doReturn('${location}')" />
		</div>
	</c:if>
</body>

</html>
