<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<title></title>
		<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		
	</head>

<body class="bg_c_g">
	<form action="">
		<div class="" style="padding:5px 10px;">
			<div class="wd_div_top">
				设置列表默认项目
			</div>
			<br/>
	
			<table class="gl_table_a01" width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<th width="100">
						所属项目:
					</th>
					<td>
						<select id="projectId" name="projectId" class="select_new01"
							onchange="javascript:searchVersionByProject();">
							<option value="-1">
								请选择所属项目
							</option>
							<c:forEach items="${projectList}" var="projectList"
								varStatus="i">
								<option value="${projectList[1]}"
									<c:if test="${projectList[1]==form.projectId}">selected</c:if>>
									${projectList[0]}
								</option>
							</c:forEach>
						</select>
					</td>
					<th width="100">
						项目版本:
					</th>
					<td>
						<div id="versionIdDiv">
							<select id="versionId" name="versionId" class="select_new01">
								<option value="-1">
									请选择项目版本
								</option>
								<c:forEach items="${versionList}" var="versionList"
									varStatus="i">
									<option value="${versionList.versionId}"
										<c:if test="${versionList.versionId==form.versionId}">selected</c:if>>
										${versionList.versionCode}
									</option>
								</c:forEach>
							</select>
						</div>
					</td>
				</tr>
			</table>
			
			<br />
			<div class="yan_zhu01">
				<font style="color: red;">注：设置默认项目后，列表数据默认展示为设置的项目数据</font>
			</div>
			<br />
			
			<div class="gl_ipt_03">
				<input name="input" type="button" class="gl_cx_bnt03" value="确定" onclick="doSubmit();"/> &nbsp;
				<input name="input" type="button" class="gl_cx_bnt03" value="取消" onclick="doClose();"/>
	 		</div>
			
		</div>
	</form>
	
	
<script type="text/javascript">
	function doSubmit(){
		var projectId = jQuery("#projectId").val();
		var versionId = jQuery("#versionId").val();
		var url = "<%=basePath%>core/portal/saveDefaultProject";
		var params = {
			projectId:projectId,
			versionId:versionId
		};
		jQuery.post(url, params, function(data){
			jQuery("#projectId", window.parent.document).val(projectId);
			jQuery("#versionId", window.parent.document).val(versionId);
			window.parent.doRefresh();
		} , 'json');
	}
	
	function searchVersionByProject(){
		var url = "<%=basePath%>developFlowManage/searchVersionByProject";
		var params = {
			projectId:jQuery("#projectId").val()
		};
		jQuery.post(url, params, function(data){
			var versionList = data;
			var option = '<option value="-1">请选择项目版本</option>';
			for(var i=0; i<versionList.length; i++){
				option += '<option value="' + versionList[i].versionId + '">' + versionList[i].versionCode + '</option>';
			}
			jQuery("#versionId").html(option);
			//rSelectsSepecify("versionIdDiv");
		} , 'json');
	}
	
	function doClose(){
		window.parent.doClose();
	}
	
</script>

</body>




</html>