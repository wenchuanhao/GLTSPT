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
<title>系统角色管理</title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/rmpb/js/Checkable.js" type="text/javascript"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript">
   function doSubmit(){
	   document.all("pageIndex").value = "1";
	   document.forms[0].submit();
	}
   </script>
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
/**
* 全选
*/
function selectAll() {
	for (var i=0;i<form.length ;i++) {
		var e = form[i];
		if (e.name == 'roleradio') {
   			e.checked = form.checkAll.checked;
  			}
  		}
}

function getFlag(){
	var isDefaultRole = document.getElementsByName("isDefaultRole");
	for(var i=0;i<isDefaultRole.length;i++){
		if(isDefaultRole[i].checked==true){
			 return isDefaultRole[i].value;
		}
	}
}

function addRole(){
	var flag = getFlag();
	window.location.href = "<%=basePath%>sys/role/addSysRole?flag="+flag;
}
function allotUser(){
	var flag = getFlag();
	window.location.href = "<%=basePath%>sys/role/userRole?flag="+flag;
}
function delRole(v){
	var roleIds = document.getElementsByName("roleradio");
	var count=0;
	var roleid="";
	for(var i=0;i<roleIds.length;i++){
		if(roleIds[i].checked==true){
			count++;
			roleid +=roleIds[i].value+",";
		}
	}
	if(count==0){
	    if(v=='del'){
	    	alert("请选择您要删除的角色!");
	    }
		return false;
		
	}else{
		if(v=='del'){
			if(confirm("数据删除后不可以恢复，您确定要删除吗？")){
			var flag = getFlag();
			form.action = '<%=basePath%>sys/role/deleteSysRole?flag='+flag+'&roleIds='+roleid;
			form.submit();
		  }
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
/**
   * 分配团队角色权限
 */
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
 
 function closeFanc(){
 	jQuery.fancybox.close();
 }
function doReset(){
	jQuery("#textfield").val("");
	jQuery("#textfield2").val("");
	doSubmit();
}

/**
* 删除单个角色
*/
function delRoleById(roleid){
	if(confirm("数据删除后不可以恢复，您确定要删除吗？")){
		var flag = getFlag();
		form.action = '<%=basePath%>sys/role/deleteSysRole?flag='+flag+'&roleIds='+roleid;
		form.submit();
	}
}
/**
* 分配权限
*/
function allotQx(roleid){
	  jQuery("#a_"+roleid).attr("href","<%=basePath%>sys/role/selectModuleIndex/"+roleid);
      jQuery("#a_"+roleid).fancybox({
		'width'				: 430,
		'height'			: '100%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
      });
}
/**
* 查看已分配用户
*/
function viewAllot(roleid){
	  jQuery("#view_"+roleid).attr("href","<%=basePath%>sys/role/fpRoleUser?roleId="+roleid)
      jQuery("#view_"+roleid).fancybox({
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
      });
}

/**
* 分配用户
*/
function userAllot(roleid){
	  jQuery("#user_"+roleid).attr("href","<%=basePath%>sys/role/userRole?roleId="+roleid)
      jQuery("#user_"+roleid).fancybox({
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
      });
}

</script>
</head>

<body class="bg_c_g">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 角色管理 > 角色管理</div>
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
				<td valign="top">
               <div class="gl_bt_bnt01">
               <input name="" type="button" class="gl_cx_bnt02" value="默认项目" style="display: none;"/>
               <input name="" type="button" class="gl_cx_bnt01" value="收起查询" onclick="toggleQueryDiv()" id="toggleQueryButton"/>
               查询</div>
               <form name="form" method="post" id="pageForm" action="<%=basePath%>sys/role/roleConfigIndex">
                <div id="queryDiv" style="display: block;">
                <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0" >
               <tr>
               	 <th width="100">角色编码:</th>
                 <td>
                   <input class="gl_text01_a" style="width: 90%;" type="text" name="roleCode" id="textfield" value="${form.roleCode}"/>
                 </td>
                 <th width="100">角色名称:</th>
                 <td>
                   <input class="gl_text01_a" style="width: 90%;" type="text" name="roleName" id="textfield2" value="${form.roleName}"/>
                 </td>
                 <th width="100">是否显示默认角色:</th>
                 <td>
                  <input type="radio" name="isDefaultRole" onclick="javascript:doSubmit();"  <c:if test="${form.isDefaultRole=='2'}">checked="checked"</c:if> value="2"/>是&nbsp;
                  <input type="radio" name="isDefaultRole" onclick="javascript:doSubmit();" <c:if test="${form.isDefaultRole=='1' || form.isDefaultRole==null}">checked="checked"</c:if> value="1"/>否
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
               
               <div class="gl_bt_bnt01">角色列表</div>
               
               <div class="gl_bnt_nav01" style="border-bottom:none;">
                 <input name="" type="button" class="gl_cx_bnt04" value="新增角色" onclick="addRole()"/>
                 <input name="" type="button" class="gl_cx_bnt04" value="分配用户" onclick="allotUser()" />
                 <input name="" type="button" class="gl_cx_bnt04" value="批量删 除" onclick="delRole('del')"/>
               </div>
             
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <th><input type="checkbox" onclick="selectAll()" name="checkAll"/></th>
                <th>序号</th>
                <th>角色编码</th>
                <th>角色名称</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>操作</th>
                </tr>
                <c:forEach items="${ITEMPAGE.items}" var="org" varStatus="i">
                <tr>
                  <td><input type="checkbox" value="${org.roleId}" name="roleradio"/></td>
                  <td>${i.index+1}</td>
                  <td>${org.roleCode}</td>
                  <td>${org.roleName}</td>
                  <td>${org.createPerson}</td>
                  <td><fmt:formatDate value="${org.createTime}" pattern="yyyy-MM-dd"/></td>
                  <td align="center">
                  
	                  	
	                  <span class="gl_tab_tr_l" style="margin-right: 2px;"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/></span>
	                  <span class="gl_tab_tr_r"><a href="<%=basePath%>sys/role/sysRoleDetailAdd/${org.roleId}">查看</a></span>
	               		
	               		<span style="padding-left: 5px;"></span>
	                    <span class="gl_tab_tr_l" style="margin-right: 2px;">
		                  		<img src="/SRMC/rmpb/images/tab_tb06.png" align="middle">
		                  	</span>
		                  	<span class="gl_tab_tr_r">
		                  		<a href="<%=basePath%>sys/role/modifySysRoleAdd/${org.roleId}">编辑</a>
		                  	</span>
	                  
	                  <span style="padding-left: 5px;"></span>
		                  <span class="gl_tab_tr_l" style="margin-right: 2px;">
		                  	<img src="/SRMC/rmpb/images/tab_tb07.png" align="middle"> 
		                  </span>
	                  	<span class="gl_tab_tr_r">
	                  		<a href="javascript:void(0)" onclick="delRoleById('${org.roleId}')">删除</a>
	                  	</span>
					  
					  <span style="padding-left: 5px;"></span>
						  <span class="gl_tab_tr_r">
						  	<a id="view_${org.roleId}">
						  		<input name="" onclick="viewAllot('${org.roleId}')" type="button" class="gl_cx_bnt04" value="查看已分配用户" style="width:96px; background:url(/SRMC/rmpb/images/tab_bnt01a.png)" />
						  	</a>
						  </span>
					  
					  <span style="padding-left: 5px;"></span>
						  <span class="gl_tab_tr_r">
						  	<a id="a_${org.roleId}">
						  		<input name="" onclick="allotQx('${org.roleId}')" type="button" class="gl_cx_bnt04" value="分配权限" />
						  	</a>
						  </span>
					  
					  
					  <span style="padding-left: 5px;"></span>
						  <span class="gl_tab_tr_r">
						  	<a id="user_${org.roleId}">
						  		<input name="" onclick="userAllot('${org.roleId}')" type="button" class="gl_cx_bnt04" value="分配用户" style="width:96px; background:url(/SRMC/rmpb/images/tab_bnt01a.png)" />
						  	</a>
						  </span>
                  </td>
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