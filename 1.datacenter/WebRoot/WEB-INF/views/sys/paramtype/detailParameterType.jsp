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
		<title>系统参数</title>
		<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"></link>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
	</head>

	<body class="bg_c_g">
		<form action="<%=basePath%>sys/paramtype/modifyParameterType"
			method="post" name="form">
			<input type="hidden" name="allowUpdate" value="1" />
			<input type="hidden" name="parameterTypeId" id="parameterTypeId"
				value="${parameterType.parameterTypeId}" />
			<input type="hidden" name="oldCode" id="oldCode"
				value="${parameterType.parameterTypeName}" />
			<div class="gl_m_r_nav">
				当前位置 : 系统管理 &gt; 系统参数 &gt; 查看参数类型
			</div>
			<table border="0" cellpadding="0" cellspacing="0"
				class="gl_m_r_n_tb01">
				<tr>
					<td valign="top" class="gl_m_r_n_tb01_m"></td>
					<td valign="top">
						<div class="gl_bt_bnt01">
							参数类型信息
						</div>
						<!-- 
			             <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							  
							  <th width="100" >参数类型名称</th>
							  <td><input class="gl_text01" disabled="disabled" type="text" name="parameterTypeName" id="parameterTypeName" value="${parameterType.parameterTypeName}"/></td>
							  <th width="100">参数类型编码</th>
							  <td><input class="gl_text01" disabled="disabled" type="text" name="parameterTypeCode" id="parameterTypeCode" value="${parameterType.parameterTypeCode}"/></td>
							</tr>
							 <tr>
							  <th width="100" >参数类型值</th>
							  <td ><input class="gl_text01" disabled="disabled" type="text" name="parameterTypeValue" id="parameterTypeValue" value="${parameterType.parameterTypeValue}"/></td>
							  <th width="100">是否允许修改：</th>
			                   <td> 是：<input type="radio" name="allowUpdate" id="allowUpdate" <c:if test='${parameterType.allowUpdate == "1" }'>checked="checked"</c:if> value="1" />
									否：<input type="radio" name="allowUpdate" id="allowUpdate" <c:if test='${parameterType.allowUpdate == "0" }'>checked="checked"</c:if> value="0"/>
							   </td>
							</tr>
							<tr>
							  <th width="120">参数类型描述</th>
							  <td colspan="3" >
						        <textarea name="description" disabled="disabled" id="description" class="gl_text01_c">${parameterType.parameterTypeDesc}</textarea>
					          </td>
							</tr>
				         </table>	
				          -->
						<table class="gl_table_a01_6L" width="100%" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<th width="100">
									参数类型名称:
								</th>
								<td>
									${parameterType.parameterTypeName}
								</td>
								<th width="100">
									参数类型编码:
								</th>
								<td>
									${parameterType.parameterTypeCode}
								</td>
								<th width="100">
									参数类型值:
								</th>
								<td>
									${parameterType.parameterTypeValue}
								</td>
							</tr>
							<tr>
								<th width="100">
									是否允许修改:
								</th>
								<td>
									<c:if test='${parameterType.allowUpdate == "1" }'>是</c:if>
									<c:if test='${parameterType.allowUpdate == "0" }'>否</c:if>
								</td>
								<th width="100">
								</th>
								<td>
								</td>
								<th width="100">
								</th>
								<td>
								</td>
							</tr>
							<tr>
								<th width="100">
									参数类型描述:
								</th>
								<td colspan="5">
									${parameterType.parameterTypeDesc}
								</td>
							</tr>
						</table>
						<div class="gl_ipt_03">
							<input name="input" type="button" class="gl_cx_bnt03" value="返 回"
							 onclick="doReturn('${location}')" />
								<!-- onclick="javascript:history.back();" /> -->
						</div>
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">		
		 function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		        }
	   </script>
	</body>
</html>
