<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>删除用户</title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dtree.css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/rmpb/js/Checkable.js" type="text/javascript"></script>
<script src="/SRMC/rmpb/js/dtree.js" type=text/javascript></script>


<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>


<script type="text/javascript">
function search(){
	if(document.getElementById("queryDiv").style.display=='block'){
		document.getElementById("queryDiv").style.display='none';
		document.getElementById("serchBox").style.display='none';
		document.getElementById("toggleQueryButton").value="展开查询";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01b";
	}else{
		document.getElementById("queryDiv").style.display='block';
		document.getElementById("serchBox").style.display='block';
		document.getElementById("toggleQueryButton").value="收起查询";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01";
	}
}
function selectAll() {
		for (var i=0;i<form.length ;i++) {
			var e = form[i];
			if (e.name == 'itemOffset') {
	   			e.checked = form.checkAll.checked;
   			}
   		}
	}
function getSelectCount() {
	var ids="";
	document.getElementById("orgIds").value="";
	var chks = document.getElementsByName('itemOffset');
	var j = 0;
	for ( var i = 0; i < chks.length; i++) {
		if (chks[i].checked){
			j++;
			ids+=chks[i].value+",";
		}
	}
	document.getElementById("orgIds").value=ids;
	return j;
}
function doDeleteItems() {
		if (getSelectCount() < 1) {
			alert("请选择需要删除的用户！");
		} else if (confirm("数据删除后不可以恢复，您确定要删除吗？")) {
				getSelectCount();
			var orgIds=document.getElementById("orgIds").value;
			checkSub(orgIds);
		}
	}
function doDeleteItems2() {
	if (getSelectCount() < 1) {
		alert("请选择需要删除的用户！");
		return false;
	} else{
	if(getSelectCount()>1){
		alert("只能删除单个用户");
		return false;
	}else{
	    if(confirm("数据删除后不可以恢复，您确定要删除吗？")){
	    	getSelectCount();
		    var orgIds=document.getElementById("orgIds").value;
		    checkSub(orgIds);
	    }
	  }	
	}
}
function checkSub(orgIds){
		form.action = '<%=basePath%>sys/user/deleteSysUser?'+Math.random();
		form.submit();
					
}
	function doReset(){
		jQuery("#textfield").val("");
		jQuery("#textfield2").val("");
	}
</script>
<script type="text/javascript">
   function doSubmit(){
       jQuery("#st").val("1");
	   document.all("pageIndex").value = "1";
	   document.forms[0].submit();
	}
   </script>
</head>

<body class="bg_c_g" onload="clearCookieDD();">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 用户管理 > 删除用户</div>
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
             <td width="26%" valign="top">
               
               <div class="gl_bt_bnt01">组织架构</div>
              
               <div class="gl_bnt_tree01">
                 <div class="gl_bnt_tree02" style="display: block;">
                   <!--<table  class="gl_bnt_tree03" border="0" cellspacing="0" cellpadding="0">
                     <tr>
                     <td><a href="#"><img src="../images/gl_tab_b01.gif" width="25" height="25" /></a></td>
                     <td><a href="#"><img src="../images/gl_tab_b02.gif" width="25" height="25" /></a></td>
                     <td width="30"></td>
                     <td><a href="#"><img src="../images/gl_tab_b03.gif" width="25" height="25" /></a></td>
                     <td><a href="#"><img src="../images/gl_tab_b04.gif" width="25" height="25" /></a></td>
                     </tr>
                   </table>
                 --></div>
                 <!--tree-->
                 <div class="d_tree_div">
           <script type="text/javascript">
		d = new dTree('d');
				<c:forEach items="${orgs}" var="org">
					<c:choose>
					<c:when test="${org.orgName == '南方基地'}">
							d.add('${org.organizationId}', '-1', "<font loadMenu='${org.organizationId}' color=#EF5900><b>公司组织架构</b></font>", '<%=basePath%>sys/user/queryUserInDel?st=1&organizationId=${org.organizationId}&index=Math.random();', '', 'sysM_mainFrame', '','//');
						</c:when>
						<c:otherwise>
							d.add('${org.organizationId}','${org.parentId}', "<font loadMenu='${org.organizationId}'>${org.orgName}</font>", '<%=basePath%>sys/user/queryUserInDel?st=1&organizationId=${org.organizationId}&index=Math.random();', '', 'sysM_mainFrame');
						</c:otherwise>
					</c:choose>	
				</c:forEach>
			document.write(d);
			
			function clearCookieDD(){
			    var st=null;    
			    st=document.getElementById("st").value;        
			    if(st==null||st==""){              
			       d.closeAll();
			    };   
			}
		
	</script>
		
    </div>
<!--tree END-->                 
               </div>
             </td>
             <td width="18" valign="top" class="gl_m_r_n_tb01_m"></td>
             <td valign="top">
             
               <div class="gl_bt_bnt01">
                 
               <input name="" type="button" class="gl_cx_bnt02" value="默认项目" style="display: none;"/>
               <input name="" type="button" class="gl_cx_bnt01" value="收起查询" onclick="search()" id="toggleQueryButton"/>
            查询</div>
               <form name="form" id="pageForm" method="post" action="<%=basePath%>sys/user/queryUserInDel">
                <input type="hidden" value="${st}" id="st"	name="st"/> 
               <!-- 
               <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0" id="queryDiv" style="display: block;">
               <div style="height: 30px"></div>
               <tr>
               <td width="50%">登录账号：
                 <input class="gl_text01" type="text" name="account" id="textfield" value="${form.account}"/></td>
               <td>用户姓名：
                 <input class="gl_text01" type="text" name="userName" id="textfield2" value="${form.userName}"/></td>
               </tr>
               
               <tr>
               <td colspan="2" align="center"><div style="height: 30px"></div>
                 <input id="img" name="" type="submit" class="gl_cx_bnt03" value="查 询" />&nbsp;
                 <input name="" type="reset" class="gl_cx_bnt03" value="重 置" />
               </td>
               </tr>
               </table>
                -->
                <div id="queryDiv" style="display: block;">
                <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0" >
               <tr>
               	 <th width="100">登录账号:</th>
                 <td>
                   <input class="gl_text01_a" style="width: 98%;" type="text" name="account" id="textfield" value="${form.account}"/>
                 </td>
                 <th width="100">用户姓名:</th>
                 <td>
                   <input class="gl_text01_a" style="width: 98%;" type="text" name="userName" id="textfield2" value="${form.userName}"/>
                 </td>
                 </tr>
             
               </table>
               
                 <div class="gl_ipt_03" id="serchBox" style="display: block;">
					<!-- <input id="img" name="" type="submit" class="gl_cx_bnt03" value="查 询" />&nbsp; -->
                 	<input id="img" name="" type="button" class="gl_cx_bnt03" value="查 询" onclick="javascript:doSubmit();"/>&nbsp;
                 	<input name="" type="button" class="gl_cx_bnt03" value="重 置" onclick="doReset();"/>
                  	<input type="hidden" value="${form.organizationId}" id="dep"	name="organizationId"/>
                	<input type="hidden" value="${form.pageIndex}" id="pageIndex"	name="pageIndex"/>
                 	<input type="hidden" value="${form.pageSize}" id="pageSize"	name="pageSize"/>
				</div>
				</div>
              <!-- </form> --> 
              
               <div class="ge_a01"></div>
               
               <div class="gl_bt_bnt01">用户列表</div>
               <div class="gl_bnt_nav01" style="border-bottom:none;">
                 <input name="input" type="button" class="gl_cx_bnt04" value="删除" onclick="doDeleteItems2()"/>
                 <input type="button" class="gl_cx_bnt04" value="批量删除" onclick="doDeleteItems()"/>
               </div>
              
             <!--  <form name="form2" method="post">-->
             <input type="hidden" id="orgIds" name="orgIds"/>
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <th width="32"><input type="checkbox"   id="Checkbox1" name="checkAll" onclick="selectAll()"/></th>
                <th>登录账号</th>
                <th>用户姓名</th>
                <th>所属机构</th>
                <th>联系方式</th>
                <th>默认角色</th>
                </tr>
                <c:forEach items="${ITEMPAGE.items}" var="org" varStatus="i">
                <tr>
                  <td><input type="checkbox"  value="${org[0].userId}"  name="itemOffset"/></td>
                  <td>${org[0].account}</td>
                  <td>${org[0].userName}</td>
                  <td>${org[3].orgName}>>${org[2].orgName}</td>
                  <td>${org[0].mobile}</td>
                  <td>${org[1].roleName}</td>
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