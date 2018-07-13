<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<title>角色设置角色</title>
		<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
		<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dtree.css" />
		<script type="text/javascript" src="/SRMC/rmpb/js/dtreeCheckbox.js"></script>

		<script type="text/javascript">
			function check(nodeid, treecheckbox) {
				for ( var i = 0; i < document.form.length; i++) {
					var e = document.form[i];
					if (e.type == "checkbox" && e.value == nodeid) {
						if (e.checked == true && treecheckbox == '') {
							treecheckbox = true;
						}
						break;
					}
				}
				if (treecheckbox == true) {
					checkFather(nodeid);
				}
			
				if (checkIfHasChild(nodeid)) {
					checkChildByFather(nodeid, treecheckbox);
				}
			
				function checkFather(fid) {
					if (fid == "-1") {
						return;
					}
					for ( var i = 0; i < document.form.length; i++) {
						var e = document.form[i];
						if (e.type == "checkbox" && e.value == fid) {
							e.checked = true;
							checkFather(e.pid);
						}
					}
				}
			
				//选中所有的子节点
				function checkChildByFather(sid, isCheck) {
			
					if (!checkIfHasChild(sid)) {
						return;
					}
			
					for ( var i = 0; i < document.form.length; i++) {
						var e = document.form[i];
						if (e.type == "checkbox" && e.pid == sid) {
							if (e.disabled == false) {
								e.checked = isCheck;
							}
							checkChildByFather(e.value, isCheck);
						}
					}
				}
			
				//返回是否有子节点
				function checkIfHasChild(ssid) {
					for ( var i = 0; i < document.form.length; i++) {
						var e = document.form[i];
						if (e.type == "checkbox" && e.pid == ssid) {
							return true;
						}
					}
					return false;
				}
			}
			//保存选中的组织机构
			function doSave(){
				var privileges = new Array();
				var roleId=jQuery("#roleId").val();
				$("input[type=checkbox]:checked").each(function(){
					privileges.push($(this).val());
				   }
				 );		
				window.parent.setVal(privileges.toString(),roleId);
			}
			
			function doSearchs(){
			  var roleId=$("#roleid").val();
			  form.action="<%=basePath%>sys/role/selectRoleIndexDefault/"+roleId;
			  form.submit();
			}
			function del(v){
			   var roleId=$("#roleid").val();
			   form.action="<%=basePath%>sys/role/roleDelRole?roleId="+roleId+"&pid="+v;
			   form.submit();
			}
		 function addRoles(v){
		   var roleId=$("#roleid").val();
	       form.action="<%=basePath%>sys/role/roleAddRole?roleids="+roleId+"&pid="+v;
	       form.submit();
		 }
		 function selectAll() {
			for (var i=0;i<form.length ;i++) {
				var e = form[i];
				if (e.name == 'itemOffset') {
		   			e.checked = form.checkAll.checked;
	   			}
	   		}
		}
	function getSelectCount() {
			var ids="";
			document.getElementById("orgIds").value="";
			var chks = document.getElementsByName('itemOffset');
			var j = 0;
			for ( var i = 0; i < chks.length; i++) {
				if (chks[i].checked){
					j++;
					ids+=chks[i].value+",";
				}
			}
			document.getElementById("orgIds").value=ids;
			return j;
		}
		function doDeleteItems() {
				if (getSelectCount() < 1) {
					alert("请选择需要添加的角色！");
				} else{
						getSelectCount();
					var orgIds=document.getElementById("orgIds").value;
					checkSub(orgIds);
				}
			}
			function checkSub(orgIds){
				var roleId=$("#roleid").val();
				window.parent.setValRole(orgIds,roleId);
		}
		</script>
	</head>

	<body class="bg_c_g">
		<form name="form" method="post" action="" id="form">
			<table class="gl_m_r_n_tb01" border="0" cellspacing="0"
				cellpadding="0">

				<tr>
					<td width="18" valign="top"></td>
					<td valign="top">
						<!-- 
               <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0" id="queryDiv" style="display: block;">
	               <tr>
		               <td>&nbsp;&nbsp;&nbsp;角色名称：
		                 <input type="hidden" value="${roleId}" name="roleid" id="roleid"/>
		                  <input type="hidden" value="${roledd}" name="roledd" id="roledd"/>
		                  <input type="hidden" id="orgIds" name="orgIds"/>
		                 <input class="gl_text01" type="text" name="roleName" id="roleName" value="${form.roleName}"/>&nbsp;
		                 <input id="img" name="" type="button" class="gl_cx_bnt03" value="查 询" onclick="doSearchs();"/>
		               </td>
	               </tr>
	               <tr>
	               	<c:if test='${haveis=="n"}'>
	                 <td>已配置角色：
	                 <c:forEach items="${roleSets}" var="org" varStatus="i">
               	 	  <input type="hidden" value="${org[0].roleId}"/><input type="button" value="${org[1].roleName}" class="wd_cx_bnt01" onclick="del('${org[1].roleId}')"/>&nbsp;
               	 	    <c:if test="${(i.index+1)%4==0}">
               	      	<br/>
               	      </c:if>
               	      </c:forEach>
	                 </td>
	                </c:if>
	                <c:if test='${haveis=="y"}'>
	                 <td>暂无配置角色</td>
	                </c:if>
	               </tr>
	               <tr>
	                 <td colspan="2" ><input name="" type="button" class="gl_cx_bnt04" value="添加" onclick="doDeleteItems()"/></td>
	               </tr>
               </table>
                -->
						<div style="height: 20px;"></div>
						<table class="gl_table_a01_1" align="left" border="0"
							style="width: 100%;" cellspacing="0" cellpadding="0" id="queryDiv"
							style="display: block;">
							<tr>
								<td width="100" align="center"
									style="border: 1px rgb(207, 208, 208) solid;">
									<span
										style="font-size: 14px; font-weight: bold; margin-bottom: 1px;">角色名称：</span>
								</td>
								<td>
									&nbsp;
									<input type="hidden" value="${roleId}" name="roleid"
										id="roleid" />
									<input type="hidden" value="${roledd}" name="roledd"
										id="roledd" />
									<input type="hidden" id="orgIds" name="orgIds" />
									<input class="gl_text01_a"
										style="width: 95%; margin-bottom: 1px; height: 25px;"
										type="text" name="roleName" id="roleName"
										value="${form.roleName}" />
									&nbsp;

								</td>
								<td width="80">
									<input id="img" name="" type="button" class="ipt_tb_n03"
										value="查 询" onclick="doSearchs();" style="margin-bottom: 1px;" />
								</td>
							</tr>
						</table>
						<div class="ge_a01"></div>
						<table style="width: 100%;">
							<tr>
								<c:if test='${haveis=="x"}'>
									<td>
										已配置角色：
										<c:forEach items="${roleSets}" var="org" varStatus="i">
											<input type="hidden" value="${org[0].roleId}" />
											<input type="button" value="${org[1].roleName}"
												class="wd_cx_bnt01" onclick="del('${org[1].roleId}')" />&nbsp;
               	 	    <c:if test="${(i.index+1)%4==0}">
												<br />
											</c:if>
										</c:forEach>
									</td>
								</c:if>
								<c:if test='${haveis=="x"}'>
									<td>
										暂无配置角色
									</td>
								</c:if>
							</tr>
							<tr style="display: none;">
								<td align="right">
									<input name="" type="button" class="gl_cx_bnt04" value="添加"
										onclick="doDeleteItems()" />
								</td>
							</tr>
						</table>
						<div>
						   已配置角色<br/>
							<table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th>
										序号
									</th>
									<th>
										角色名称
									</th>
								</tr>
								<c:if test='${roleFlag=="y"}'>
									<c:forEach items="${roleNames}" var="org" varStatus="i">
									<tr>
										<td>
											${i.index+1}
										</td>
										<td>
											${org.roleName}
										</td>
									</tr>
								</c:forEach>
								</c:if>
								<c:if test='${roleFlag=="n"}'>
								<c:forEach items="${roleSets}" var="org" varStatus="i">
									<tr>
										<td>
											${i.index+1}
										</td>
										<td>
											${org[1].roleName}
										</td>
									</tr>
								</c:forEach>
								</c:if>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
