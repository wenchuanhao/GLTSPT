<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>默认角色管理</title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/rmpb/js/Checkable.js" type="text/javascript"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.pack.js"></script>

<script type="text/javascript">
function search(){
	if(document.getElementById("queryDiv").style.display=='block'){
		document.getElementById("queryDiv").style.display='none';
		document.getElementById("serchBox").style.display='none';
		document.getElementById("toggleQueryButton").value="展开查询";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01";
	}else{
		document.getElementById("queryDiv").style.display='block';
		document.getElementById("serchBox").style.display='block';
		document.getElementById("toggleQueryButton").value="收起查询";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01b";
	}
}
function addRole(){
	parent.sysM_mainFrame.window.location.href = "<%=basePath%>sys/role/addSysRole?flag=2";
}
function delRole(v){
	var roleIds = document.getElementsByName("roleradio");
	var count=0;
	var roleid="";
	for(var i=0;i<roleIds.length;i++){
		if(roleIds[i].checked==true){
			count++;
			roleid=roleIds[i].value;
			break;
		}
	}
	if(count==0){
	    if(v=='del'){
	    	alert("请选择您要删除的角色!");
	    }
	    if(v=='upd'){
	    	alert("请选择您要编辑的角色!");
	    }
		if(v=='det'){
	    	alert("请选择您要查看的角色!");
	    }
	    if(v=='qx'){
	    	alert("请选择您要分配权限的角色!");
	    }
	    if(v=='pzRole'){
	    	alert("请选择您要配置的角色!");
	    }
	    if(v=='isConfig'){
	    	alert("请选择您要查看的角色!");
	    }
		return false;
		
	}else{
		if(v=='del'){
			if(confirm("数据删除后不可以恢复，您确定要删除吗？")){
			form.action = '<%=basePath%>sys/role/deleteSysRole?flag=2&roleIds='+roleid+',';
			form.submit();
		  }
		}
		if(v=='upd'){
			parent.sysM_mainFrame.window.location.href = "<%=basePath%>sys/role/modifySysRole/"+roleid;
		}
		if(v=='det'){
			parent.sysM_mainFrame.window.location.href = "<%=basePath%>sys/role/sysRoleDetail/"+roleid;
		}
		if(v=='qx'){
		  jQuery("#submitButtonQX").attr("href","<%=basePath%>sys/role/selectModuleIndex/"+roleid);
	      jQuery("#submitButtonQX").fancybox({
			'width'				: 430,
			'height'			: '100%',
			'autoScale'			: false,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe'
	      });
		}
		if(v=='pzRole'){
		  jQuery("#submitButton3").attr("href","<%=basePath%>sys/role/selectRoleIndexDefault/"+roleid);
	      jQuery("#submitButton3").fancybox({
			'width'				: '80%',
			'height'			: '100%',
			'autoScale'			: false,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe'
	      });
		}
		if(v=='isConfig'){
		  jQuery("#submitButton4").attr("href","<%=basePath%>sys/role/fpRoleUserDefault?roleId="+roleid);
	      jQuery("#submitButton4").fancybox({
			'width'				: '100%',
			'height'			: '100%',
			'autoScale'			: false,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe'
	      });
		}
	}
}
//分配团队角色权限
function  setVal(o,roleId){
	var privileges = o;
	jQuery.post("<%=basePath%>sys/role/saveRoleprivilege", {
		"privileges" : privileges,
		"roleId" : roleId
	}, function(data) {
		if (data != "") {
			if (data == "ok") {
				alert("分配成功！");
				jQuery.fancybox.close();
			} else {
				alert("分配失败");
			}
		}
	});
 }
 
function closeFanc(){
	jQuery.fancybox.close();
}
 //分配团队角色权限
function  setValRole(o,roleId){
	var roles = o;
	jQuery.post("<%=basePath%>sys/role/saveRoles", {
		"roles" : roles,
		"roleId" : roleId
	}, function(data) {
		if (data != "") {
			if (data == "ok") {
				alert("配置成功！");
				jQuery.fancybox.close();
			} else {
				alert("分配失败");
			}
		}
	});
 }
  //分配团队角色权限
function  delValRole(o,roleId){
	var roles = o;
	jQuery.post("<%=basePath%>sys/role/deleRolsUsers", {
		"roles" : roles,
		"roleId" : roleId
	}, function(data) {
		if (data != "") {
			if (data == "ok") {
				alert("撤销成功！");
				jQuery.fancybox.close();
			} else {
				alert("撤销成功！");
			}
		}
	});
 }
 
	function doReset(){
		jQuery("#textfield").val("");
		jQuery("#textfield2").val("");
	}
	
	 function doSubmit(){
	   document.all("pageIndex").value = "1";
	   document.forms[0].submit();
	}
</script>
</head>

<body class="bg_c_g">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 角色管理 > 默认角色管理</div>
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
				<td valign="top">
               <div class="gl_bt_bnt01">
               <input name="" type="button" class="gl_cx_bnt02" value="默认项目" style="display: none;"/>
               <input name="" type="button" class="gl_cx_bnt01" value="收起查询" onclick="toggleQueryDiv()" id="toggleQueryButton"/>
               查询</div>
               <form name="form" id="pageForm" method="post" action="<%=basePath%>sys/role/defaultroleConfigIndex">
                <div id="queryDiv" style="display: block;">
                <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0" >
               <tr>
               	 <th width="100">角色编码:</th>
                 <td>
                   <input class="gl_text01_a" style="width: 98%;" type="text" name="roleCode" id="textfield" value="${form.roleCode}"/>
                 </td>
                 <th width="100">角色名称:</th>
                 <td>
                   <input class="gl_text01_a" style="width: 98%;" type="text" name="roleName" id="textfield2" value="${form.roleName}"/>
                 </td>
                 </tr>
               </table>
               
               <div id="serchBox" class="gl_ipt_03">
					<!-- <input id="img" name="" type="submit" class="gl_cx_bnt03" value="查 询" />&nbsp; -->
                    <input id="img" name="" type="button" class="gl_cx_bnt03" value="查 询" onclick="javascript:doSubmit();"/>&nbsp;
                 <input name="" type="button" class="gl_cx_bnt03" value="重 置" onclick="doReset();"/>
				</div>
				</div>
               <div class="ge_a01"></div>
               
               <div class="gl_bt_bnt01">默认角色列表</div>
               
               <div class="gl_bnt_nav01" style="border-bottom:none;">
                 <input name="" type="button" class="gl_cx_bnt04" value="新增角色" onclick="addRole()"/>
                 <input name="" type="button" class="gl_cx_bnt04" value="查 看" onclick="delRole('det')"/>
                 <input name="" type="button" class="gl_cx_bnt04" value="编 辑" onclick="delRole('upd')"/>
                 <input name="" type="button" class="gl_cx_bnt04" value="删 除" onclick="delRole('del')"/>
                 <a id="submitButtonQX">
                   <input  onclick="delRole('qx')" name="" type="button" class="gl_cx_bnt04" value="分配权限"/>
                 </a>
                 <a id="submitButton3"  style="display: none;">
                   <input name="" onclick="delRole('pzRole')" type="button" class="gl_cx_bnt04" value="配置角色" />
                  </a>
                <a id="submitButton4" >
                 <input name="" onclick="delRole('isConfig')" type="button" class="gl_cx_bnt04" value="查看已分配用户" style="width:96px; background:url(/SRMC/rmpb/images/tab_bnt01a.png)" />
                 </a>
               </div>
             
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <th>单选</th>
                <th>序号</th>
                <th>角色编码</th>
                <th>角色名称</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>操作</th>
                </tr>
                <c:forEach items="${ITEMPAGE.items}" var="org" varStatus="i">
                <tr>
                  <td><input type="radio" value="${org.roleId}" name="roleradio"/></td>
                  <td>${i.index+1}</td>
                  <td>${org.roleCode}</td>
                  <td>${org.roleName}</td>
                  <td>${org.createPerson}</td>
                  <td><fmt:formatDate value="${org.createTime}" pattern="yyyy-MM-dd"/></td>
                  <td align="center"><span class="gl_tab_tr_l" style="margin-right: 2px;"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/></span><span class="gl_tab_tr_r"><a href="<%=basePath%>sys/role/sysRoleDetail/${org.roleId}">查看</a></span></td>
                </tr>
                </c:forEach>
               </table>
               
                 <div class="pageBox">
				<jsp:include flush="true" page="/public/include/navigate2.jsp"></jsp:include>
				</div>
				</form>
                </td>
             </tr>
           </table>
</body>
<script type="text/javascript" src="/SRMC/rmpb/js/list-common.js"></script>
</html>