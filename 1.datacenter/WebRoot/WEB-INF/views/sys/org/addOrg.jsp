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
		<title>添加组织机构</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">
	
		function checkNamexUniqueness(){
				var orgName =$("#orgName").val();
				var parentId=$("#parentId").val();
				if(orgName!=""){
					$.post("<%=basePath%>/sys/org/checknameuniqueness", {
						"orgName" :orgName,
						"parentId" :parentId
					}, function(data) {
						if (data == "0"){
							// orgForm.submit();
							$.post("<%=basePath%>sys/org/addOrg", jQuery("#orgForm").serializeArray(),function(s){
								if(s=="ok"){
									alert("保存成功！");
									location.href='<%=basePath%>sys/org/queryOrg';
								}else{
								    alert("保存失败！");
								}
						   });
							
						}
						if (data == "1"){
							alert("组织名称已经存在!");
						}
					});
				}
			}
		function doSave() {
				if(checkVal("orgName",50,"请输入组织名称!",false))
				if(checkValLenth("descrption",100))
				 checkNamexUniqueness();
				
			}
</script>
	</head>
	<body class="bg_c_g" onload="">
		<form action="<%=basePath%>sys/org/addOrg" method="post"
			name="orgForm" id="orgForm">
			<div class="gl_m_r_nav">
				当前位置 : 系统管理 > 组织架构 > 新增组织
			</div>
			<table class="gl_m_r_n_tb01" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td valign="top">
						<div class="gl_bt_bnt01">
							组织机构信息
						</div>
						
						<table class="gl_table_a01" width="100%" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<th width="100">
									组织名称：
								</th>
								<td width="21%;">
									<input class="gl_text01_a" style="width: 90%;" type="text" name="orgName"
										id="orgName" />
								</td>
								<th width="100">
									上级组织：
								</th>
								<td width="160px;">${perOrg.orgName}
									<input type="hidden" name="parentId" id="parentId"
										value="${perOrg.organizationId}" />
									<input type="hidden" class="" value="${perOrg.orgName}"/>
								</td>
								<th width="100">
									排 序：
								</th>
								<td width="160px;">${ordNumber}
									<input class="" type="hidden" name="orgOrder"
										id="orgOrder" value="${ordNumber}" />
									
									<input type="hidden" class="" value="${ordNumber}" />
								</td>
							</tr>
							<tr>
								<th width="100">
									描 述：
								</th>
								<td colspan="5">
									<textarea class="gl_text01_c" name="description"
										id="descrption"></textarea>
								</td>
							</tr>
						</table>

						<div class="gl_ipt_03">
							<input  type="button" class="gl_cx_bnt03" value="保 存"
								onclick="doSave()" />
							&nbsp;
							<input  type="button" class="gl_cx_bnt03" value="取 消"
								onclick="qx()" />
						</div>
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
		function  doAaddOrg(){
			orgForm.submit();
		}
		function qx(){
			location.href="<%=basePath%>sys/org/queryOrg";
		}
		
		jQuery(function(){
			//页面进入获得焦点
			jQuery("#orgName").focus();
		});
	</script>
	</body>
</html>
