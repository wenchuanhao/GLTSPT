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
		<!--<script type="text/javascript" src="/SRMC/rmpb/js/test.js"></script>-->

		<script type="text/javascript">
		function checkNamexUniqueness(){
			var parameterName =jQuery("#parameterName").val();
			if(parameterName!=""){
				jQuery.post("<%=basePath%>sys/parameter/checknameuniquenessadd", {
					"parameterName" :parameterName
					}, function(data) {
					if (data == "0"){
						 form.submit();
					}	
					if (data == "1"){
						alert("参数名称已经存在");
					}
				});
			}
		}
		function doSave() {
		    var parameterTypeId=jQuery("#parameterTypeId").val();
		    if(null==parameterTypeId || ''==parameterTypeId){
		      alert("请选择参数类型!");
		      return false;
		    }
			if(checkVal("parameterName",50,"请输入参数名称!",false))
			if(checkVal("parameterCode",50,"请输入参数编码!",false))
			if(checkVal("parameterValue",200,"请输入参数值!",false))
			if(checkValLenth("parameterDesc",256))
				checkNamexUniqueness();
				
		}
		
		function canels(){
		 window.location.href = "<%=basePath%>sys/parameter/queryParameter";
		}
		
</script>
	</head>

	<body class="bg_c_g">
		<form action="<%=basePath%>sys/parameter/addParameter" method="post"
			name="form">
			<div class="gl_m_r_nav">
				当前位置 : 系统管理 > 系统参数 > 新增参数
			</div>
			<div class="gl_import_m">
				<div class="gl_bt_bnt01">
					参数信息
				</div>
				<!-- 
               <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <th width="100">参数类型名称</th>
					  <td>
					  <div id="uboxstyle">
					  	<select name="parameterTypeId" id="parameterTypeId" class="select_new01">
							<c:forEach items="${paramList}" var="paramType">
							    <option value="">请选择</option>
								<option value="${paramType.parameterTypeId}">${paramType.parameterTypeName}</option>
							</c:forEach>
					</select>
					</div></td>
                   <th width="100">参数名称：</th>
                   <td><input class="gl_text01" type="text" name="parameterName" id="parameterName" /></td>
                   
                 </tr>
                 <tr>
                   <th width="100">参数编码：</th>
                   <td><input class="gl_text01" type="text" name="parameterCode" id="parameterCode" /></td>
                   <th>参数值：</th>
                   <td><input class="gl_text01" type="text" name="parameterValue" id="parameterValue"  /></td>
                   
                 </tr>
                 <tr>
                    <th>是否允许修改：</th>
                    <td colspan="3"> 是：<input type="radio" name="allowUpdate" id="allowUpdate"  checked="checked"  value="1" />
						否：<input type="radio" name="allowUpdate" id="allowUpdate"   value="0"/>
					</td>
                 </tr>
                 <tr>
                   <th>参数描述：</th>
                   <td colspan="3"><textarea class="gl_text01_c" name="parameterDesc" id="parameterDesc"></textarea></td>
                 </tr>
               </table>
                -->
				<table class="gl_table_a01" width="100%" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th width="120">
							参数类型名称:
						</th>
						<td>
							<div id="uboxstyle">
								<select name="parameterTypeId" id="parameterTypeId"
									class="select_new01">
									<option value="">
											请选择
										</option>
									<c:forEach items="${paramList}" var="paramType">
										<option value="${paramType.parameterTypeId}">
											${paramType.parameterTypeName}
										</option>
									</c:forEach>
								</select>
							</div>
						</td>
						<th width="120">
							参数名称:
						</th>
						<td>
							<input class="gl_text01_a" type="text" name="parameterName"
								id="parameterName" />
						</td>
						<th width="120">
							参数编码:
						</th>
						<td>
							<input class="gl_text01_a" type="text" name="parameterCode"
								id="parameterCode" />
						</td>
					</tr>
					<tr>
						<th width="120">
							参数值:
						</th>
						<td>
							<input class="gl_text01_a" type="text" name="parameterValue"
								id="parameterValue" />
						</td>
						<th width="120">
							是否允许修改:
						</th>
						<td colspan="3">
						<input type="radio" name="allowUpdate" id="allowUpdate"
								checked="checked" value="1" />
							是
                               &nbsp;&nbsp;<input type="radio" name="allowUpdate" id="allowUpdate" value="0" />
							否
							
						</td>
					</tr>
					<tr>
						<th>
							参数描述：
						</th>
						<td colspan="5">
							<textarea class="gl_text01_c" name="parameterDesc" id="parameterDesc"></textarea>
						</td>
					</tr>
				</table>
				<div class="gl_ipt_03">
					<input name="input" type="button" class="gl_cx_bnt03" value="保 存"
						onclick="doSave();" />
					&nbsp;
					<input name="input" type="button" class="gl_cx_bnt03" value="取 消"
						onclick="canels();" />
				</div>

			</div>
		</form>
	</body>
</html>
