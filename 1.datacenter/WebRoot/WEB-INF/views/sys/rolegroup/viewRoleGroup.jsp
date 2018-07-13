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
								${vo.roleGroupcode}
							</td>
							<th width="100">
								角色组名称:
							</th>
							<td>
								${vo.roleGroupname}
							</td>
						</tr>
						<tr>
							<th width="100">
								角色组描述:
							</th>
							<td colspan="3">
								${vo.roleGroupdesc}
							</td>
						</tr>
					</table>
				  <div class="ge_a01b"></div>
				  <div class="gl_bt_bnt01"> 已分配角色</div>
				  <c:if test="${empty has_list}">
				     	此角色组暂无分配角色
				  </c:if>
				  <c:if test="${not empty has_list}">
			          <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
			          	<c:forEach items="${has_list}" var="org" varStatus="i">
							  <tr>
							    <th style=" width:3%; font-family:Arial; background:#e6f3fb">${i.index+1}</th>
							    <th style="width:13%">角色名称</th>
							    <td style="width:20%">${org.roleName}</td>
						      </tr>
					    </c:forEach>
					  </table>
				  </c:if>
				
					<div class="gl_ipt_03">
									<input name="input" type="button" class="gl_cx_bnt03"
										value="返回" onclick="doReturn('${location}')"  />
								</div>
				
                </div>
                
	<script type="text/javascript">
		function doReturn(location){
            var path = "<%=basePath%>" + location;
            window.location.href = path;
		}
	</script>
</body>
</html>
