<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		function checkNamexUniqueness(){
			var parameterTypeName =$("#parameterTypeName").val();
			if(parameterTypeName!=""){
				$.post("<%=basePath%>/sys/paramtype/checknameuniquenessadd", {
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
		}
		function doSave() {
		
			//if(checkVal("parameterTypeName",50,"请输入参数类型名称!",false))
			//if(checkVal("parameterTypeCode",50,"请输入参数类型编码!",false))
			//if(checkVal("parameterTypeValue",50,"请输入参数类型值!",false))
			if(checkValLenth("parameterTypeDesc",256))
				checkNamexUniqueness();
				
		}
		
		function canels(){
		 window.location.href = "<%=basePath%>sys/paramtype/queryParameterType";
		}
		
</script>
	</head>

	<body class="bg_c_g">
		<form action="<%=basePath%>sys/paramtype/addParameterType"
			method="post" name="form">
			<div class="gl_m_r_nav">
				当前位置 : 系统管理 > 系统参数 > 新增参数类型
			</div>
			<div class="gl_import_m">
				<div class="gl_bt_bnt01">
					参数类型信息
				</div>
				<!-- 
               <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <th width="100">参数类型名称：</th>
                   <td><input class="gl_text01" type="text" name="parameterTypeName" id="parameterTypeName" /></td>
                   <th width="100">参数类型编码：</th>
                   <td><input class="gl_text01" type="text" name="parameterTypeCode" id="parameterTypeCode" /></td>
                 </tr>
                 <tr>
                   <th>参数类型值：</th>
                   <td><input class="gl_text01" type="text" name="parameterTypeValue" id="parameterTypeValue"  /></td>
                   <th>是否允许修改：</th>
                    <td> 是：<input type="radio" name="allowUpdate" id="allowUpdate"  checked="checked"  value="1" />
						否：<input type="radio" name="allowUpdate" id="allowUpdate"   value="0"/>
					</td>
                 </tr>
                 <tr>
                   <th>参数类型描述：</th>
                   <td colspan="3"><textarea class="gl_text01_c" name="parameterTypeDesc" id="parameterTypeDesc"></textarea></td>
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
							<input class="gl_text01_a" type="text" name="parameterTypeName"
								id="parameterTypeName" />
						</td>
						<th width="120">
							参数类型编码:
						</th>
						<td>
							<input class="gl_text01_a" type="text" name="parameterTypeCode"
								id="parameterTypeCode" />
						</td>
						<th width="120">
							参数类型值:
						</th>
						<td>
							<input class="gl_text01_a" type="text" name="parameterTypeValue"
								id="parameterTypeValue" />
						</td>
					</tr>
					<tr>
						<th width="120">
							是否允许修改:
						</th>
						<td colspan="5">
						<input type="radio" name="allowUpdate" id="allowUpdate"
								checked="checked" value="1" />
							是
							&nbsp;&nbsp;<input type="radio" name="allowUpdate" id="allowUpdate" value="0" />
							否
							
						</td>
					</tr>
					<tr>
						<th width="120">
							参数类型描述:
						</th>
						<td colspan="5">
							<textarea class="gl_text01_c" name="parameterTypeDesc" id="parameterTypeDesc"></textarea>
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
	
<script type="text/javascript">
jQuery(function(){
	//页面进入获得焦点
	jQuery("#parameterTypeName").focus();
});
</script>

</html>
