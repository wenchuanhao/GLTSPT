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
<title>给用户赋予角色</title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dtree.css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/js.js"></script>
<script src="/SRMC/rmpb/js/Checkable.js" type="text/javascript"></script>
<script src="/SRMC/rmpb/js/dtree.js" type=text/javascript></script>


<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/openBox_v1.1.js"></script>
<script type="text/javascript">
function search(){
	if(document.getElementById("queryDiv").style.display=='block'){
		document.getElementById("queryDiv").style.display='none';
		document.getElementById("toggleQueryButton").value="展开查询";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01";
	}else{
		document.getElementById("queryDiv").style.display='block';
		document.getElementById("toggleQueryButton").value="收起查询";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01b";
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
					alert("请选择需要撤消的用户！");
				} else{
						getSelectCount();
					var orgIds=document.getElementById("orgIds").value;
					checkSub(orgIds);
				}
			}
			function checkSub(orgIds){
				var roleId='${roleId}';
				window.parent.delValRole(orgIds,roleId);
		}
		function cleanValus(){
			document.getElementById("textfield").value="";
			document.getElementById("textfield2").value="";
		}
</script>
</head>

<body class="bg_c_g">
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
             <td width="18" valign="top" class="gl_m_r_n_tb01_m"></td>
             <td valign="top">
               <form name="form" method="post" action="<%=basePath%>sys/role/fpRoleUserDefault" id="pageForm">
               <input type="hidden" value="${roleId}" name="roleId"/>
              
	     <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <th width="100">登录账号:</th>
                 <td>
                <input class="gl_text01" style="width: 90%;" type="text" name="account" id="textfield" value="${form.account}"/>
                 </td>
                 <th width="100">用户姓名:</th>
                 <td>
                <input class="gl_text01" style="width: 90%;" type="text" name="userName" id="textfield2" value="${form.userName}"/>
                 </td>
                 
                 
               </tr>
              
             </table>
             <div class="gl_ipt_03">
              <!-- <input id="img" name="" type="submit" class="gl_cx_bnt03" value="查 询" />&nbsp; -->
              <input id="img" name="" type="button" onclick="javascript:doSubmit();" class="gl_cx_bnt03" value="查 询" />&nbsp;
                 <input name="" type="button" class="gl_cx_bnt03" value="重 置" onclick="cleanValus()"/>
                 <input type="hidden" value="${form.organizationId}" id="dep"	name="organizationId"/>
                 <input type="hidden" value="${form.pageIndex}" id="pageIndex"	name="pageIndex"/>
                 <input type="hidden" value="${form.pageSize}" id="pageSize"	name="pageSize"/>
              </div>
              
             
              
               <div class="ge_a01"></div>
               <input type="hidden" id="orgIds" name="orgIds"/>
               <div class="gl_bt_bnt01">用户列表</div>
               <div class="gl_bnt_nav01" style="border-bottom:none;">
                 <input name="" type="button" class="gl_cx_bnt04" value="撤消分配" onclick="doDeleteItems()"/>
               </div>
<!--               <form name="form2" method="post" id="form2">-->
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
               
                <tr>
                <th><input type="checkbox" onclick="selectAll()" name="checkAll"/></th>
                <th>登录账号</th>
                <th>用户姓名</th>
                <th>所属机构</th>
                <th>联系方式</th>
                <th>邮箱</th>
                <th>操作</th>
                </tr>
                <c:forEach items="${ITEMPAGE.items}" var="org" varStatus="i">
                <tr>
                  <td><input type="checkbox" value="${org[1].roleuserId}" name="itemOffset"/></td>
                  <td>${org[0].account}</td>
                  <td>${org[0].userName}</td>
                  <td>${org[2].orgName}</td>
                  <td>${org[0].mobile}</td>
                  <td>${org[0].email}</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/></span><span class="gl_tab_tr_r"><a href="<%=basePath%>sys/role/deleRolsUser?userId=${org[0].userId}&roleId=${roleId}&userRoleId=${org[1].roleuserId}">撤销分配</a></span></td>
                </tr>
                </c:forEach>
               </table>
<!--               </form>-->
                 <div class="pageBox">
				<jsp:include flush="true" page="/public/include/navigate2.jsp"></jsp:include>
				</div>
				  </form>
                </td>
             </tr>
           </table>
</body>
<script type="text/javascript">
   function doSubmit(){
	   document.all("pageIndex").value = "1";
	   document.forms[0].submit();
	}
   </script>
<script type="text/javascript" src="/SRMC/rmpb/js/list-common.js"></script>
</html>