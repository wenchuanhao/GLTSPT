<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>角色组组配置角色</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		
	</head>
	<body class="bg_c_g">
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 > 角色管理 > 角色组配置
		</div>
		        
                <div class="gl_import_m">
        
					<div class="gl_bt_bnt01">
						角色组信息
					</div>
					
					<table class="gl_table_a01_6L" width="100%" border="0"
						cellspacing="0" cellpadding="0">
						<tr>
							<th width="100">
								角色组编码:
							</th>
							<td>
								<input type="hidden" value="${vo.roleGroupid}" name="roleGroupid" id="roleGroupid"/>
								<input class="gl_text01_a" style="width: 48%;" type="text" onkeyup="value=value.replace(/[^\w\/]/ig,'')" name="roleGroupcode" id="roleGroupcode" value="${vo.roleGroupcode}"/>
							</td>
							<th width="100">
								角色组名称:
							</th>
							<td>
								<input class="gl_text01_a" style="width: 48%;" type="text" name="roleGroupname" id="roleGroupname" value="${vo.roleGroupname}"/>
							</td>
						</tr>
						<tr>
							<th width="100">
								角色组描述:
							</th>
							<td colspan="3">
								<textarea class="gl_text01_c" name="roleGroupdesc" id="roleGroupdesc" >${vo.roleGroupdesc}</textarea>
							</td>
						</tr>
					</table>
				  <div class="ge_a01b"></div>
				  <div class="gl_bt_bnt01"> 已分配角色</div>
				  <c:if test="${empty has_list}">
				     	此角色组暂无分配角色
				  </c:if>
				  <c:if test="${not empty has_list}">
			          <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
			          	<tr class="tuan01a">
				          <th>序号</th>
				          <th>角色名称</th>
				          <th>角色编码</th>
				          <th>操作</th>
				        </tr>
			          	<c:forEach items="${has_list}" var="org" varStatus="i">
							  <tr id="tr_${org.roleId }">
							    <th style=" width:5%; font-family:Arial; background:#e6f3fb">${i.index+1}</th>
							    <td style="width:40%">${org.roleName}</td>
							    <td style="width:40%">${org.roleCode}</td>
							    <td style="width:5%">
				                  <a href="javascript:delGroupRole('${org.roleId }');">删除</a>
							   </td>
						      </tr>
					    </c:forEach>
					  </table>
				  </c:if>
				  <div class="ge_a01b"></div>
				  <div class="gl_bt_bnt01">角色配置</div>
				  <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
							<th width="100">
								角色名称:
							</th>
							<td colspan="3">
								<input class="gl_text01_a" type="text" id="xx" name="xx" size="35" />
								&nbsp;
								<input type="button" onclick="searchRole()" class="gl_cx_bnt03"
									value="查询" />
							</td>
						</tr>
						<tr>
							<th width="100">
								可选角色:
							</th>
							<td colspan="4" style="word-break: break-all;">
								<div id="roleDisplay" style="display: block;">
									<c:forEach items="${not_list}" var="org" varStatus="i">

										<input type="checkbox" value="${org.roleId}" name="roles" />${org.roleName}&nbsp;&nbsp;
									</c:forEach>
								</div>
							</td>
						</tr>
					</table>
				
					<div class="gl_ipt_03">
									<input name="input" type="button" class="gl_cx_bnt03"
										value="保存" onclick="dosubmits()" />
									&nbsp;
									<input name="input" type="button" class="gl_cx_bnt03"
										value="返回" onclick="doReturn('${location}')"  />
								</div>
				
                </div>
                
	<script type="text/javascript">
		function doReturn(location){
            var path = "<%=basePath%>" + location;
            window.location.href = path;
		}
		/**
		* 根据角色名称查询角色信息
		*/
		function searchRole(){
			var roleName=$("#xx").val();
			var roleGroupid=$("#roleGroupid").val();
			$.post("<%=basePath%>sys/rolegroup/getRoleByRoleName",{"roleName":roleName,"roleGroupid":roleGroupid},function(data){
				if(data!='n'){
					document.getElementById("roleDisplay").innerHTML=data;
				}
			});
		}
		/**
		* 删除角色组角色配置
		*/
		function delGroupRole(roleId){
			if(confirm("数据删除后不可以恢复，您确定要删除吗？")){
				var roleGroupid=$("#roleGroupid").val();
			      jQuery.ajax({
						type:"POST",
						url:"<%=basePath%>sys/rolegroup/delGroupRole",
						data:{"roleGroupid":roleGroupid,"roleId":roleId},
						success:function(result){
						  if(result=='1'){
						    alert("删除成功!");
						    window.location.href = "<%=basePath%>sys/rolegroup/addRoleGroup?roleGroupid="+roleGroupid;
						  }if(result=='0'){
						    alert("删除失败!");
						  }
						},
						error:function(){
							alert("删除失败!");
						}
			  	  });
			}
		}
		
		/**
		* 保存提交
		*/
		function dosubmits(){
		    var roles = document.getElementsByName("roles");
		    var count=0;
		    var roleIds= "";
		    var roleGroupid=$("#roleGroupid").val();
		    //角色组编号、名称、描述
		    var roleGroupcode = $("#roleGroupcode").val();
		    var roleGroupname = $("#roleGroupname").val();
		    var roleGroupdesc = $("#roleGroupdesc").val();
		    for(var f=0;f<roles.length;f++){
		    	if(roles[f].checked==true){
		    		roleIds += roles[f].value + "|";
		    		count++;
		    	}
		    }
		    
			if(roleGroupcode == ""){
				alert("角色组编号不能为空！");
				return false;
			}
			if( roleGroupcode.length > 30 ){
				alert("角色组编号长度不能大于30！");
				return false;
			}
			if(roleGroupname == ""){
				alert("角色组名称不能为空！");
				return false;
			}
			if( roleGroupname.length > 30 ){
				alert("角色组名称不能大于30！");
				return false;
			}
			if( roleGroupdesc.length > 60 ){
				alert("角色组描述不能大于60！");
				return false;
			}
			//新增时必须选择角色
		    if(roleGroupid == "" && count==0){
		    	alert("请选择角色！");
		    	return false;
		    }
			$.post("<%=basePath%>sys/rolegroup/configGroupRole",
				{"roleGroupid":roleGroupid,"roleGroupcode":roleGroupcode,"roleGroupname":roleGroupname,"roleGroupdesc":roleGroupdesc,"roleIds":roleIds},
			function(data){
				if(data=="1"){
					alert("角色组编号不能重复!");
				}
				if(data=="2"){
					alert("角色组名称不能重复!");
				}
				if(data=="y"){
					alert("角色组角色设置成功!");
					window.location.href="<%=basePath%>sys/rolegroup/querySysRoleGroup";
				}
				if(data=="n"){
					alert("角色组角色设置失败!");
					window.location.href="<%=basePath%>sys/rolegroup/querySysRoleGroup";
				}
			});
		}
	</script>
</body>
</html>
