<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统权限</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dtree.css" />
		<script src="/SRMC/rmpb/js/jquery-1.7.1.js" type="text/javascript"></script>
		<script src="/SRMC/rmpb/js/dtree/dtreeCheckbox.js" type="text/javascript"></script>
	</head>
	<body>
		<form name="form">
			<script type="text/javascript">
			d = new dTree('d');
			d.add('ROOT', -1, '<font color=#EF5900>【<b>系统权限</b>】</font>', '', '', '', '','//');
				<c:forEach items="${privileges}" var="privilege">
					<c:choose>
						<c:when test="${privilege.status==3}">
							d.add('${privilege.moduleCode}','${privilege.parentCode}', '${privilege.moduleName}', true, '', '');
						</c:when>
						<c:otherwise>
							d.add('${privilege.moduleCode}','${privilege.parentCode}', '${privilege.moduleName}', false, '', '');
						</c:otherwise>
					</c:choose>			 
				</c:forEach>
			document.write(d);
		</script>
		</form>
		<input style="width:79px; height:25px; font-size:12px; font-weight:bold; color:#fff; background:url(/SRMC/rmpb/images/bnt04.gif) no-repeat; border:none; cursor:pointer; padding-left:5px;  font:'微软雅黑';" type="button" onclick="doSave()" value=" 分 配 权 限 " />

		<script type="text/javascript">
			function check(nodeid, treecheckbox) {
				for ( var i = 0; i < document.form.length; i++) {
					var e = document.form[i];
					if (e.type == "checkbox" && e.value == nodeid) {
						if (e.checked == true && treecheckbox == '') {
							treecheckbox = true;
						}
						break;
					}
				}
				if (treecheckbox == true) {
					checkFather(nodeid);
				}
			
				if (checkIfHasChild(nodeid)) {
					checkChildByFather(nodeid, treecheckbox);
				}
			
				function checkFather(fid) {
					if (fid == "-1") {
						return;
					}
					for ( var i = 0; i < document.form.length; i++) {
						var e = document.form[i];
						if (e.type == "checkbox" && e.value == fid) {
							e.checked = true;
							checkFather(e.pid);
						}
					}
				}
			
				//选中所有的子节点
				function checkChildByFather(sid, isCheck) {
			
					if (!checkIfHasChild(sid)) {
						return;
					}
			
					for ( var i = 0; i < document.form.length; i++) {
						var e = document.form[i];
						if (e.type == "checkbox" && e.pid == sid) {
							if (e.disabled == false) {
								e.checked = isCheck;
							}
							checkChildByFather(e.value, isCheck);
						}
					}
				}
			
				//返回是否有子节点
				function checkIfHasChild(ssid) {
					for ( var i = 0; i < document.form.length; i++) {
						var e = document.form[i];
						if (e.type == "checkbox" && e.pid == ssid) {
							return true;
						}
					}
					return false;
				}
			}
			//保存选中的组织机构
			function doSave(){
				var privileges = new Array();
				$("input[type=checkbox]:checked").each(function(){
					privileges.push($(this).val());
				   }
				 );		
				window.parent.setVal(privileges.toString());
			}
		</script>
	</body>
</html>
