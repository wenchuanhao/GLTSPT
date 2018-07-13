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
<title>管理用户</title>
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


function doReset(){
	jQuery("#textfield").val("");
	jQuery("#textfield2").val("");
}

</script>
<script type="text/javascript">
   function doSubmit(){
	   document.all("pageIndex").value = "1";
	   document.forms[0].submit();
	}
   </script>
</head>

<body class="bg_c_g">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 用户管理 > 查询用户信息</div>
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
             <td width="18" valign="top" class="gl_m_r_n_tb01_m"></td>
             <td valign="top">
            <div class="gl_bt_bnt01">
               <input name="" type="button" class="gl_cx_bnt01" value="收起查询" onclick="search()" id="toggleQueryButton"/>
                                 查询</div>
             <form name="form" id="pageForm" method="post" action="<%=basePath%>sys/user/toSelectUser">
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
              <div id="serchBox" class="gl_ipt_03" style="display: block;">
				 <!-- <input id="img" name="" type="submit" class="gl_cx_bnt03" value="查 询" />&nbsp; -->
                 <input id="img" name="" type="button" class="gl_cx_bnt03" value="查 询" onclick="javascript:doSubmit();"/>&nbsp;
                 <input name="input" type="button" class="gl_cx_bnt03" value="重 置" onclick="doReset();"/>
			  </div>
			  </div>
               <div class="ge_a01"></div>
               <div class="gl_bt_bnt01">用户列表</div>
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <th>登录账号</th>
                <th>用户姓名</th>
                <th>性别</th>
                <th>移动电话</th>
                <th>所属机构</th>
                <th style="display: none;">操作</th>
                </tr>
                <c:forEach items="${ITEMPAGE.items}" var="org" varStatus="i">
                <tr>
                  <td>${org.account}</td>
                  <td>${org.userName}</td>
                  <td>
                      <c:choose>
                          <c:when test="${org.sex=='1'}">
                                                          男
                          </c:when>
                         <c:when test="${org.sex=='2'}">
                                                      女                         
                          </c:when>
                      </c:choose>
                  
                  </td>
                  <td>${org.telePhone}</td>
                  <td>${org.dNames}</td>
                  <td align="center" style="display: none;">       
                  	<span class="gl_tab_tr_r">
                  		<a href="<%=basePath%>sys/user/sysUserInfo/${org.userId}">查看</a>
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