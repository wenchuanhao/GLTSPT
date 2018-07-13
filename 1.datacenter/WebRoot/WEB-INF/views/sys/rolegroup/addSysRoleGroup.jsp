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
<title></title>
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"></link>
<link href="/SRMC/rmpb/css/left_a01.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/common.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/autoDialog.js"></script>
<script type="text/javascript">
		var flag=true;
		function checkNamexUniqueness(){
				var roleName =$("#roleName").val();
				if(roleName!=""){
					$.post("<%=basePath%>/sys/role/checknameuniqueness", {
			    "roleName" : roleName
		    }, function(data)
		    {
			    if (data == "0") {
				    form.submit();
			    }
			    if (data == "1") {
				    alert("角色组名称已经存在!");
			    }
		    });
	    }
    }
    function doSave()
    {
	    if (checkVal("roleGroupname", 50, "请输入角色组名称!", false))
		    if (checkValLenth("roleGroupdesc", 256)){
				 form.submit();
		    }
			    // 				checkNamexUniqueness();

    }
   //打开组织列表
		function openPrivilege(roleId) {
			var url = "<%=basePath%>sys/org/queryOrglist?index="+ Math.random();
			dialog = new AutoDialog({title:"分配权限", width: 300, height: 400, padding: 0});
			dialog.setContent('<iframe src="'+url+'" width="100%" height="100%" frameborder="0" border="0"></iframe>');
			dialog.show();
		}
		function setVal(o){
			$("#organizationName").val(o.orgName);
			$("#orgId").val(o.orgId);
			$.post("<%=basePath%>/sys/rolegroup/getRoles", {
			    "orgId" : o.orgId
		    }, function(role)
		    {
		    	$("#ul").empty();
			    if (!role=="") {
				    var str="";
				    for(var i=0;i<role.length;i++){
				    	str+='<li><input type="checkbox" name="roleIds" value="' +role[i].roleId+  '"/>' + role[i].roleName+ '</li> ';
			    	}
			    	$("#ul").append(str);
			    }
		    });
			dialog.destroy();
		}
</script>
</head>
<body class="main_bg">
	<div class="mm_main_top01">
		<span class="mm_main_top01a"> <img
			src="/SRMC/rmpb/images/005.png" width="33" height="32" /> </span> <span
			class="mm_main_top01c">当前位置： 系统管理 &gt; 角色管理 &gt; 角色组管理 &gt;<span
			class="or">添加角色组</span> </span>
	</div>

	<div class="mm_main_bd01">
		<div class="mm_m_bd_a">
			<span class="mm_m_bd_b"><img
				src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" /> </span> <span
				class="mm_m_bd_c">角色组信息</span>
		</div>

		<div class="ge01"></div>
		<form action="<%=basePath%>sys/rolegroup/addSysRoleGroup" method="post" name="form">
		<input type="hidden" name="orgId" id="orgId" />
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="main_table03">
				<tr>
					<th width="15%"><span class="or">*</span>角色组名称</th>
					<td width="85%" colspan="3"><input class="blue_bt_a"
						type="text" name="roleGroupname" id="roleGroupname" /></td>
				</tr>
				<tr>
					<th width="15%"><span class="or">*</span>所属区域</th>
					<td width="85%" colspan="3">	
					<input class="blue_bt_a" type="text" name="organizationName" id="organizationName" />
					<input style="width:89px; height:25px; font-size:12px; font-weight:bold; color:#fff; background:url(/SRMC/rmpb/images/bnt04.gif) no-repeat; border:none; cursor:pointer; padding-left:5px;  font:'微软雅黑';" type="button" onclick="openPrivilege()" value="选择区域"/>
					</td>
				</tr>
				<tr>
					<th width="15%" valign="top"><span class="or">*</span>角色名称</th>
					<td width="85%" colspan="3">
						<ul class="box" id="ul">
						</ul>
					</td>
				</tr>
				<tr>
					<th width="15%"><span class="or">*</span>角色组描述</th>
					<td width="85%" colspan="3"><textarea rows="4"
							style="width: 90%" name="roleGroupdesc" id="roleGroupdesc"></textarea>
					</td>
				</tr>
			</table>
			<table width="96%" align="center">
				<tr>
					<td height="60" colspan="4" align="center"><input
						class="bnt_class02" type="button" name="button3" id="button3"
						value="保 存" onclick="doSave()" /> <input class="bnt_class03"
						type="button" name="button4" id="button4" value="取 消"
						onclick="javascript:history.back()" />
					</td>
				</tr>
			</table>
		</form>
		<div class="ge02"></div>
	</div>
</body>
</html>