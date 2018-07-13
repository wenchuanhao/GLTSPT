<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户排序</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link href="/SRMC/rmpb/css/left_a01.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/png_t.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/select2css.js"></script>
<script type="text/javascript">
	function doSave(){
		var ids="";
		var chks = document.getElementsByName('ids');
		for ( var i = 0; i < chks.length; i++) {
				ids+=chks[i].value+",";
		}
		var seqs="";
		var seqList = document.getElementsByName('seq');
		for ( var i = 0; i < seqList.length; i++) {
				seqs+=seqList[i].value+",";
		}
		window.location.href='<%=basePath%>sys/user/orderUpdate/'+ids+'/'+seqs+'?'+Math.random();
	}
</script>
</head>

<body class="main_bg">

  <form name="form" method="post" action="<%=basePath%>sys/user/queryUserLists">
     <div class="mm_main_bd11">
	      <div class="mm_m_bd_a">
	        <span class="mm_m_bd_b">
	        	<img src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" />
	        </span>
	        <span class="mm_m_bd_c">用户管理</span>
	      </div>
	      
      <div class="ge01"></div>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table01">
	        <tr>
	          <th width="20%">登录账号</th>
	          <th width="15%">用户姓名</th>
	          <th width="15%">排序序号</th>
	        </tr>
			<c:forEach items="${userList}" var="user" varStatus="i">
				<input type="hidden" value="${user.userId}" class="ids" name="ids"/>
				<tr <c:if test="${i.index%2 != 0}">class="main_table04_bg"</c:if> style="cursor: hand;">
		          <td>${user.account}</td>
		          <td>${user.userName}</td>
		          <td><input type="text" value="${user.seq}" class="seq" name="seq"/></td>
		        </tr>
			</c:forEach>
	      	</table>
	      	<table width="96%" align="center">
					<tr>
						<td height="60" colspan="4" align="center">
							<input class="bnt_class02" type="button" name="button3" id="button3" value="保 存" onclick="doSave()"/> 
							<input class="bnt_class03" type="button" name="button4" id="button4" value="取 消" onclick="javascript:history.back()" />
						</td>
					</tr>
				</table>
			<div class="ge02"></div>
		</div>
	</form>
</body>
</html>