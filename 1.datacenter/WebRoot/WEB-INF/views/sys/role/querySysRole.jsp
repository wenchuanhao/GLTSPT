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
<title>查询角色</title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />

<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/rmpb/js/Checkable.js" type="text/javascript"></script>
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

	function doReset(){
		jQuery("#textfield").val("");
		jQuery("#textfield2").val("");
	}

</script>
</head>

<body class="bg_c_g">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 角色管理 > 查询角色</div>
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
             <td valign="top">
               <div class="gl_bt_bnt01">
               <input name="" type="button" class="gl_cx_bnt02" value="默认项目" style="display: none;"/>
               <input name="" type="button" class="gl_cx_bnt01" value="收起查询" onclick="toggleQueryDiv()" id="toggleQueryButton"/>
               查询</div>
               <form name="form"  id="pageForm" method="post" action="<%=basePath%>sys/role/querySysRole">
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
                 </tr>
               </table>
               <div id="serchBox" class="gl_ipt_03">
					<!-- <input id="img" name="" type="submit" class="gl_cx_bnt03" value="查 询" />&nbsp; -->
                 <input id="img" name="" type="button" class="gl_cx_bnt03" value="查 询" onclick="javascript:doSubmit();"/>&nbsp;
                 <input name="" type="button" class="gl_cx_bnt03" value="重 置" onclick="doReset();"/>
				</div>
				</div>
               <!-- </form> -->
              
               <div class="ge_a01"></div>
               
               <div class="gl_bt_bnt01">角色列表</div>
               
              
             
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <th>序号</th>
                <th>角色编码</th>
                <th>角色名称</th>
<!--                <th>已配角色</th>-->
                <th>创建人</th>
                <th>创建时间</th>
                <th>操作</th>
                </tr>
                <c:forEach items="${ITEMPAGE.items}" var="org" varStatus="i">
                <tr>
                  <td>${i.index+1}</td>
                  <td>${org.roleCode}</td>
                  <td>${org.roleName}</td>
<!--                  <td>暂无</td>-->
                  <td>${org.createPerson}</td>
                  <td><fmt:formatDate value="${org.createTime}" pattern="yyyy-MM-dd"/></td>
                  <td align="center"><span class="gl_tab_tr_l" style="margin-right: 2px;"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/></span><span class="gl_tab_tr_r"><a href="<%=basePath%>sys/role/sysRoleDetailSearch/${org.roleId}">查看</a></span></td>
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