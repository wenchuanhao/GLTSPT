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
<title>管理用户角色</title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dTree.css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/rmpb/js/Checkable.js" type="text/javascript"></script>
<script src="/SRMC/rmpb/js/dtree.js" type=text/javascript></script>


<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/openBox_v1.1.js"></script>
<script type="text/javascript">
   function doSubmit(){
	   document.all("pageIndex").value = "1";
	   document.forms[0].submit();
	}
	//全选
	function checkAllbox(){
		for (var i=0;i<form.length ;i++) {
			var e = form[i];
			if (e.name == 'subBox') {
	   			e.checked = form.checkAll.checked;
   			}
   		}
	}
	//选中
	function getSelectCount() {
		var ids="";
		document.getElementById("roleuserIds").value="";
		var chks = document.getElementsByName('subBox');
		var j = 0;
		for ( var i = 0; i < chks.length; i++) {
			if (chks[i].checked){
				j++;
				ids+=chks[i].value+"_";
			}
		}
		document.getElementById("roleuserIds").value=ids;
		return j;
	}
	function doDeleteItems() {
		if (getSelectCount() < 1) {
			alert("请选择需要删除的角色配置！");
		} else{
			var roleuserIds=document.getElementById("roleuserIds").value;
			del(roleuserIds);
		}
	}
   </script>
<script type="text/javascript">
function search(){
	if(document.getElementById("queryDiv").style.display=='none'){
		document.getElementById("queryDiv").style.display='block';
		document.getElementById("serchBox").style.display='block';
		document.getElementById("toggleQueryButton").value="收起查询";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01";
	}else{
		document.getElementById("queryDiv").style.display='none';
		document.getElementById("serchBox").style.display='none';
		document.getElementById("toggleQueryButton").value="展开查询";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01b";
	}
}
function del(v){
 var account=document.getElementById("textfield").value;
 var pageIndex=document.getElementById("pageIndex").value;
 var pageSize=document.getElementById("pageSize").value;
 if(window.confirm("您确实要删除该角色吗？")){
	 location.href='<%=basePath%>sys/role/delUserRole?account='+account+'&userRoleId='+v+'&pageIndex='+pageIndex+'&pageSize='+pageSize;
 }
}
function add(v){
 var account=document.getElementById("textfield").value;
 location.href='<%=basePath%>sys/role/editUserRole?account='+account+'&userId='+v;
 
}
function detail(v){
 location.href='<%=basePath%>sys/role/userRoleDetail?userId='+v;
 
}

function edits(v,y){
 location.href='<%=basePath%>sys/role/toEditRoleUserView?userId='+v+'&roleId='+y;
 
}


function doReset(){
	jQuery("#textfield").val("");
	jQuery("#textfield2").val("");
	var isDisplay=document.getElementsByName("isDisplay");
	for(var i=0;i<isDisplay.length;i++){
		if(isDisplay[i].value=='2'){
			isDisplay[i].checked=true;
			break;
		}
	}
}
</script>
</head>

<body class="bg_c_g">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 角色管理 > 用户权限管理</div>
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
          
             <td valign="top">
             
               <div class="gl_bt_bnt01">
               <input name="" type="button" class="gl_cx_bnt01" value="收起查询" onclick="search()" id="toggleQueryButton"/>
               查询</div>
             <form name="form" method="post" id="pageForm" action="<%=basePath%>sys/role/queryUserRoleIndex">
              <div id="queryDiv" style="display: block;">
              <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
               	 <th width="100">登录账号:</th>
                 <td>
                   <input class="gl_text01_a" style="width: 68%;" type="text" name="account" id="textfield" value="${form.account}"/>
                 </td>
                 <th width="100">用户姓名:</th>
                 <td>
                   <input class="gl_text01_a" style="width: 68%;" type="text" name="userName" id="textfield2" value="${form.userName}"/>
                 </td>
                 <th width="100">是否显示默认角色:</th>
                 <td>
                  <input type="radio" name="isDisplay"   <c:if test="${form.isDisplay=='1'}">checked="checked"</c:if> value="1"/>是&nbsp;
                  <input type="radio" name="isDisplay" <c:if test="${form.isDisplay=='2' || form.isDisplay==null}">checked="checked"</c:if> value="2"/>否
                 </td>
                 </tr>
               </table>
               </div>
              <div id="serchBox" class="gl_ipt_03" style="display: block;">
					<!-- <input id="img" name="" type="submit" class="gl_cx_bnt03" value="查 询" />&nbsp; -->
                 	<input id="img" name="" type="button" class="gl_cx_bnt03" value="查 询" onclick="javascript:doSubmit();"/>&nbsp;
                 	<input name="" type="button" class="gl_cx_bnt03" value="重 置" onclick="doReset();"/>
                 	<input type="hidden" value="${form.pageIndex}" id="pageIndex"	name="pageIndex"/>
                 	<input type="hidden" value="${form.pageSize}" id="pageSize"	name="pageSize"/>
				</div>
               <div class="ge_a01"></div>
               <input type="hidden" id="roleuserIds" name="roleuserIds"/>
               <div class="gl_bt_bnt01">用户角色列表</div>
               
              <div class="gl_bnt_nav01" style="border-bottom:none;">
                 <input name="" type="button" class="gl_cx_bnt04" value="删 除" onclick="doDeleteItems()"/>
               </div>
             
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <th width="4%"><input id="checkAll" type="checkbox" onclick="checkAllbox()"></th>
                <th width="8%">登录账号</th>
                <th width="7%" >用户姓名</th>
                <th width="18%">所属机构</th>
                <th width="14%">已分配角色</th>
                <th width="35%">已分配区域</th>
                <th width="14%">操作</th>
                </tr>
                <c:forEach items="${ITEMPAGE.items}" var="org" varStatus="i">
                <tr>
                  <td><input type="checkbox" value="${org[0].roleuserId}" name="subBox"/></td>
                  <td>${org[1].account}</td>
                  <td>${org[1].userName}</td>
                  <td>${org[4].orgName}>>${org[3].orgName}</td>
                  <td>${org[2].roleName}</td>
                  <td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px">${org[0].areaName}</td>
                  <td align="center">
                  <span class="gl_tab_tr_l" style="margin-right: 2px;"><img src="/SRMC/rmpb/images/tab_tb06.png" align="middle"/></span>
                  <span class="gl_tab_tr_r"><a href="javascript:edits('${org[0].userId}','${org[0].roleId}');">编辑区域</a></span>   
                  <span style="margin-right: 2px;margin-left: 2px;"></span>
                  <c:if test="${org[2].isDefaultRole!='2'}">  
                    <span class="gl_tab_tr_l" style="margin-right: 2px;"><img src="/SRMC/rmpb/images/tab_tb07.png" align="middle"/></span>
                 	  <span class="gl_tab_tr_r">
                 	   <a href="javascript:del('${org[0].roleuserId}');">删除</a>
                  	  </span>
                   </c:if>
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