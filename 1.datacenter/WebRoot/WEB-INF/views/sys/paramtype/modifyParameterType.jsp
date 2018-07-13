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
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">
	    function checkNamexUniqueness(OldParameterTypeName){
				var parameterTypeName =$("#parameterTypeName").val();
				if(parameterTypeName!=OldParameterTypeName && parameterTypeName!=""){
					$.post("<%=basePath%>/sys/paramtype/checknameuniqueness?"+Math.random(), {
						"parameterTypeName" :parameterTypeName
					}, function(data) {
						if (data == "0"){
								form.submit();
							}
						if (data == "1"){
							alert("参数类型名称已经存在!");
						}
					});
				}
				if(OldParameterTypeName==parameterTypeName){
						form.submit();
					}
			}
	      function doSave() {
				var oldCode =$("#oldCode").val();
				if(checkVal("parameterTypeName",50,"请输入参数类型名称!",false))
				if(checkVal("parameterTypeCode",50,"请输入参数类型编码!",false))
				if(checkVal("parameterTypeValue",50,"请输入参数类型值!",false))
				if(checkValLenth("parameterTypeDesc",256))
				checkNamexUniqueness(oldCode);
				
			}
</script>
	</head>

	<body class="bg_c_g">
		<form action="<%=basePath%>sys/paramtype/modifyParameterType"
			method="post" name="form">
			<input type="hidden" name="parameterTypeId" id="parameterTypeId"
				value="${parameterType.parameterTypeId}" />
			<input type="hidden" name="oldCode" id="oldCode"
				value="${parameterType.parameterTypeName}" />
			<div class="gl_m_r_nav">
				当前位置 : 系统管理 &gt; 系统参数 &gt; 编辑参数类型
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
							  <td><input class="gl_text01" type="text" name="parameterTypeName" id="parameterTypeName" value="${parameterType.parameterTypeName}"/></td>
							  <th width="100">参数类型编码</th>
							  <td><input class="gl_text01" type="text" name="parameterTypeCode" id="parameterTypeCode" value="${parameterType.parameterTypeCode}"/></td>
							</tr>
							 <tr>
							  <th width="100" >参数类型值</th>
							  <td ><input class="gl_text01" type="text" name="parameterTypeValue" id="parameterTypeValue" value="${parameterType.parameterTypeValue}"/></td>
							  <th width="100">是否允许修改：</th>
			                   <td> 是：<input type="radio" name="allowUpdate" id="allowUpdate" <c:if test='${parameterType.allowUpdate == "1" }'>checked="checked"</c:if> value="1" />
									否：<input type="radio" name="allowUpdate" id="allowUpdate" <c:if test='${parameterType.allowUpdate == "0" }'>checked="checked"</c:if> value="0"/>
							   </td>
							</tr>
							<tr>
							  <th width="120">参数类型描述</th>
							  <td colspan="3" >
						        <textarea name="parameterTypeDesc" id="parameterTypeDesc" class="gl_text01_c">${parameterType.parameterTypeDesc}</textarea>
					          </td>
							</tr>
				         </table>	
				          -->
						<table class="gl_table_a01" width="100%" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<th width="100">
									参数类型名称:
								</th>
								<td>
									<input class="gl_text01_a" type="text" name="parameterTypeName"
										id="parameterTypeName"
										value="${parameterType.parameterTypeName}" />
								</td>
								<th width="100">
									参数类型编码:
								</th>
								<td>
									<input class="gl_text01_a" type="text" name="parameterTypeCode"
										id="parameterTypeCode"
										value="${parameterType.parameterTypeCode}" />
								</td>
								<th width="100">
									参数类型值:
								</th>
								<td>
									<input class="gl_text01_a" type="text"
										name="parameterTypeValue" id="parameterTypeValue"
										value="${parameterType.parameterTypeValue}" />
								</td>
							</tr>
							<tr>
								<th width="100">
									是否允许修改:
								</th>
								<td colspan="5">
									<input type="radio" name="allowUpdate" id="allowUpdate" <c:if test='${parameterType.allowUpdate == "1" }'>checked="checked"</c:if> value="1" />是
									&nbsp;&nbsp;<input type="radio" name="allowUpdate" id="allowUpdate" <c:if test='${parameterType.allowUpdate == "0" }'>checked="checked"</c:if> value="0"/>否
								</td>
							</tr>
							<tr>
								<th width="100">
									参数类型描述:
								</th>
								<td colspan="5">
									<textarea name="parameterTypeDesc" id="parameterTypeDesc" class="gl_text01_c">${parameterType.parameterTypeDesc}</textarea>	
								</td>
							</tr>
						</table>
						<div class="gl_ipt_03">
							<input name="input" type="button" class="gl_cx_bnt03" value="保 存"
								onclick="doSave();" />
							&nbsp;
							<input name="input" type="button" class="gl_cx_bnt03" value="取 消"
								onclick="doReturn('${location}')" />
								<!-- onclick="javascript:history.back();" /> -->
						</div>
					</td>
				</tr>
			</table>
		</form>
		<div class="ge02"></div>
		</div>
		<script type="text/javascript">		
		 function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		        }
	   </script>
	</body>
</html>
