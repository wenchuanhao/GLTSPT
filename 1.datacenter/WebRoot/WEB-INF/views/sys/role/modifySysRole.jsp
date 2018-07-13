<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑角色</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
		var flag=true;
		function checkNamexUniqueness(){
				var roleName=$("#roleName").val();
				var roleNamexx=$("#roleNamexx").val();
				if(roleName!=""){
				   if(roleName==roleNamexx){
				       checkCodeUniqueness();
				      
				   }else{
				       $.post("<%=basePath%>/sys/role/checknameuniqueness", {
						"roleName" :roleName
					}, function(data) {
						
						if (data == "1"){
							 alert("角色名称已经存在!");
							 return false;
						}else{
						  checkCodeUniqueness();
						   
						}
					});
				   }
				
				
					
				}
			}
			function checkCodeUniqueness(){
				var roleCode =$("#roleCode").val();
				var roleCodexx =$("#roleCodexx").val();
				if(roleCode!=""){
				    if(roleCode==roleCodexx){
				    	form.action='<%=basePath%>/sys/role/modifySysRole';
						form.submit();
				    }else{
				       $.post("<%=basePath%>/sys/role/checkcodeuniqueness", {
						"roleCode" :roleCode
					}, function(data) {
						if (data == "0"){
							form.action='<%=basePath%>/sys/role/modifySysRole';
							form.submit();
						}
						if (data == "1"){
							 alert("角色编码已经存在!");
							 return false;
						}
					});
				   }	
				}
			}
		function doSave() {
				var roleName=$("#roleName").val();
				var roleCode=$("#roleCode").val();
				if(roleName==""){
				  alert("请输入角色名称!");
				  return false;
				}else{
				  if(roleName.length>50){
				  	alert("角色名称不能超过50个字符！");
				  	return false;
				  }
				}
				if(roleCode==""){
				  alert("请输入角色编码!");
				  return false;
				}else{
					if(roleCode.length>50){
				  	alert("角色编码不能超过50个字符！");
				  	return false;
				  }
				}
				if(checkValLenth("roleDesc",256))
				//form.action='<%=basePath%>/sys/role/modifySysRole';
				//form.submit();
				checkNamexUniqueness();
				//checkCodeUniqueness();
				
			}
</script>
</head>
<body class="bg_c_g">
<form action="<%=basePath%>sys/role/saveSysRole" method="post" name="form">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 角色管理 > 编辑角色</div>
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
             <td valign="top">
               <div class="gl_bt_bnt01">角色信息</div>
               <!-- 
               <table class="gl_table_a01_b" width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <th width="100">角色名称：</th>
                   <td>${role.roleName}</td>
                   <th width="100">角色编码：</th>
                   <td>
                    ${role.roleCode}
                    <input  type="hidden" name="roleId" value="${role.roleId}"/>
                    <input  type="hidden" name="createrId" value="${role.createrId}"/>
                    <input  type="hidden" name="roleName" value="${role.roleName}"/>
                    <input  type="hidden" name="roleCode" value="${role.roleCode}"/>
                   </td>
                 </tr>
                
                 <tr>
                   <th>描　　述：</th>
                   <td colspan="3"><textarea class="gl_text01_c" name="roleDesc" id="roleDesc">${role.roleDesc}</textarea></td>
                 </tr>
               </table>
                -->
                <table class="gl_table_a01" width="100%" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<th width="100">
									角色名称:
								</th>
								<td>
									<input class="gl_text01_a" type="text" name="roleName" id="roleName" value="${role.roleName}"  style="width: 90%;"/>
								</td>
								<th width="100">
									角色编码:
								</th>
								<td>
								 <c:choose>
				                      <c:when test="${role.roleCode=='admin'}">
				                         <div class="gl_text02" style="border:0px"> ${role.roleCode}</div>                         
				                         <input class="gl_text01_a" type="hidden" name="roleCode" id="roleCode" value="${role.roleCode}"  style="width: 90%;"/>                     
				                      </c:when>
				                      <c:otherwise>
				                        <input class="gl_text01_a" type="text" name="roleCode" id="roleCode" value="${role.roleCode}"  style="width: 90%;"/>
				                      </c:otherwise>                    
			                    </c:choose>      
									<%-- <input class="gl_text01_a" type="text" name="roleCode" id="roleCode" value="${role.roleCode}"  style="width: 98%;"/> --%>
				                    <input  type="hidden" name="roleId" value="${role.roleId}"/>
				                    <input  type="hidden" name="createrId" value="${role.createrId}"/>
				                    <input  type="hidden" name="roleNamexx" value="${role.roleName}" id="roleNamexx"/>
				                    <input  type="hidden" name="roleCodexx" value="${role.roleCode}" id="roleCodexx"/>
				                    <input  type="hidden" name="isDefaultRole" value="${role.isDefaultRole}"/>
				                    <input  type="hidden" name="createTime" value="${role.createTime}" id="createTime"/>
								</td>
							</tr>
							<tr>
								<th width="100">
									描　　述：
								</th>
								<td colspan="3"><textarea class="gl_text01_c" name="roleDesc" id="roleDesc">${role.roleDesc}</textarea></td>
							</tr>
						</table>
               <div  class="gl_ipt_03">
        		<input name="input" type="button" class="gl_cx_bnt03" value="保 存" onclick="doSave()"/>&nbsp;
        		<input name="input" type="button" class="gl_cx_bnt03" value="取 消" onclick="doReturn('${location}')"/>
        		<%-- <input name="input" type="button" class="gl_cx_bnt03" value="取 消" onclick="history.go(-1);"/> --%>
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
			location.href="<%=basePath%>sys/role/roleConfigIndex";
		}
		function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		}
	</script>
</body>
</html>