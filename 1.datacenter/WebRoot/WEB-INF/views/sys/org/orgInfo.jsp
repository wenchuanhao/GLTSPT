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
		<title>组织机构详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">
		function checkNamexUniqueness(){
				var orgName =$("#orgName").val();
				if(orgName!=""){
					$.post("<%=basePath%>/sys/org/checknameuniqueness", {
						"orgName" :orgName
					}, function(data) {
						if (data == "0"){
							 orgForm.submit();
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
	<body class="bg_c_g">
		<form action="<%=basePath%>sys/org/addOrg" method="post"
			name="orgForm">
			<div class="gl_m_r_nav">
				当前位置 : 系统管理 > 组织架构 > 组织机构详情
			</div>
			<table class="gl_m_r_n_tb01" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td valign="top">
						<div class="gl_bt_bnt01">
							组织机构信息
						</div>
						<!-- 
               <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <th width="100">组织名称：</th>
                   <td>${org.orgName}</td>
                   <th width="100">上级组织：</th>
                   <td>
                    ${PerOrg.orgName}
                   </td>
                 </tr>
                 <tr>
                   <th>排　　序：</th>
                   <td colspan="3">
                   ${org.orgOrder}
                   </td>
                 </tr>
                 <tr>
                   <th>描　　述：</th>
                   <td colspan="3">${org.description}</td>
                 </tr>
               </table>
                -->
						<table class="gl_table_a01_6L" width="100%" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<th width="100">
									组织名称：
								</th>
								<td>
									${org.orgName}
								</td>
								<th width="100">
									上级组织：
								</th>
								<td>
									${PerOrg.orgName}
								</td>
								<th width="100">
									排 序：
								</th>
								<td>
									${org.orgOrder}
								</td>
							</tr>
							<tr>
								<th>
									描 述：
								</th>
								<td colspan="5">
									${org.description}
								</td>
							</tr>
						</table>
						<div class="gl_ipt_03">
							<input name="input" type="button" class="gl_cx_bnt03" value="返回"
								onclick="doReturn('${location}')" />
								<%-- value="返回" onclick="history.go(-1);"  /> --%>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
		function  doAaddOrg(){
			orgForm.submit();
		}
		
		 function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		        }
	</script>
	</body>
</html>
