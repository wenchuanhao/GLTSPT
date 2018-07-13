<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link href="/SRMC/rmpb/css/left_a01.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/common.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/autoDialog.js"></script>
</head>

<body class="main_bg">
	      
	     <div class="mm_main_bd01">
			  <div class="mm_m_bd_a">
				<span class="mm_m_bd_b"><img src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" /></span>
				<span class="mm_m_bd_c">角色管理</span>
			  </div>
			  
		  <div class="mm_main_bd01">
	      <div class="mm_m_bd_a">
	        <span class="mm_m_bd_b">
	        	<img src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" />
	        </span>
	        <span class="mm_m_bd_c">用户管理</span>
	      </div>
	      
      <div class="ge01"></div>
	      <table width="98%" align="center">
	        <tr>
	          <td height="30">
	          	<span class="pic01">
	            	<input class="bnt_class05" type="button" name="button" id="button" value="新增" onclick="toAddSysUser()"/>
	            </span>
	            <span class="pic01">
	              <input class="bnt_class06" type="button" name="button2" id="button2" value="删除" onclick="javascript:doDeleteItems();"/>
	            </span>
	           </td>
	        </tr>
	      </table>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table01">
	        <tr>
	          <th width="5%">&nbsp;</th>
	          <th width="10%">登录账号</th>
	          <th width="10%">用户姓名</th>
			  <th width="10%">所属机构</th>
	          <th width="12%">手机号码</th>
	          <th width="15%">办公邮件</th>
			  <th width="10%">创建人</th>
	          <th width="6%">接受SMS</th>
			  <th width="10%">状态</th>
	        </tr>
			<c:forEach items="${list}" var="user">
				<tr <c:if test="${i.index%2 != 0}">class="main_table04_bg"</c:if> style="cursor: hand;" ondblclick="toModifySysUser('${user.userId}')">
		          <td><input type="checkbox" name="checkbox" id="checkbox" value="${user.userId}"/></td>
		          <td>${user.account}</td>
		          <td>${user.userName}</td>
				  <td>${user.organizationName}</td>
		          <td>${user.mobile}</td>
		          <td>${user.email}</td>
				  <td>${user.createrName}</td>
		          <td>
		          	<c:choose>
		          		<c:when test='${user.isReceiveSms=="1"}'>
		          			接收
		          		</c:when>
		          		<c:otherwise>
		          			不接收
		          		</c:otherwise>
		          	</c:choose>
		          </td>
				  <td>
				  	<c:choose>
		          		<c:when test='${user.isActivate=="1"}'>
		          			正常
		          		</c:when>
		          		<c:otherwise>
		          			禁用
		          		</c:otherwise>
		          	</c:choose>
				  </td>
		        </tr>
			</c:forEach>
	      	</table>
			<div class="ge02"></div>
		</div>
	</div>
	<div class="ge01"></div>





</body>
</html>