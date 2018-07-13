<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link href="/SRMC/rmpb/css/left_a01.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
<script type="text/javascript">
function doAssign(flag,id) {
			var srId =$("#roleId").val();
			var userId=id;
			var msg = flag == "0" ?"确定将选中的用户从此角色中移除吗?":"确定将此角色分配给选中的用户吗?";
			if (confirm(msg)){
				$.post("<%=basePath%>sys/privilege/assignvisitor", {
					"srId" : srId,
					"userId" : userId,
					"flag" :flag
				}, function(data) {
					if (data != "") {
						if (data == "ok") {
							alert("分配成功。");
							window.parent.location.href=window.parent.location.href;
						} else {
							alert("分配失败！");
						}
					}
				});
			}else{
			}
		}
</script>
</head>

<body class="main_bg">
  <form name="form" method="post" action="<%=basePath%>sys/privilege/getUser/0/${roleId}">
  	<input type="hidden"  id="roleId" name="roleId" value="${roleId}"/>
    <div class="mm_main_bj">
    <div class="mm_main_bd01">
      <div class="mm_m_bd_a">
        <span class="mm_m_bd_b">
        	<img src="/SRMC/rmpb/images/mm_pic07.gif" width="38" height="35" />
        </span>
        <span class="mm_m_bd_c">查询</span>
      </div>
      
      <div class="ge01"></div>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table03">
	        <tr>
	          <th width="100" >
	          	登录账号
	          </th>
	          <td>
	          	<input class="blue_bt_a" type="text" name="account" id="account" />
	          </td>
	          <th width="100">
	          	用户姓名
	          </th>
	          <td>
	          	<input class="blue_bt_a" type="text" name="userName" id="userName" />
	          </td>
	          <td>
	          	<input class="bnt_class07" type="button" name="button3" id="button3" value="查 询" onclick="doSearch()"/>
	          	<input class="bnt_class08" type="reset" name="button3" id="button6" value="重 置" />
	          </td>
	        </tr>
	      </table>
      <div class="ge02"></div>
  	</div>
  
      
     <div class="mm_main_bd01">
	      <div class="mm_m_bd_a">
	        <span class="mm_m_bd_b">
	        	<img src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" />
	        </span>
	        <span class="mm_m_bd_c">未分配用户</span>
	      </div>
	      
      <div class="ge01"></div>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table01">
	        <tr>
	          <th width="15%">登录账号</th>
	          <th width="15%">用户姓名</th>
			  <th width="15%">所属机构</th>
			  <th width="15%">创建人</th>
			  <th width="10%">状态</th>
			  <th >操作</th>
	        </tr>
			<c:forEach items="${ITEMPAGE.items}" var="user" varStatus="i">
				<tr <c:if test="${i.index%2 != 0}">class="main_table04_bg"</c:if> style="cursor: hand;" >
		          <td>${user.account}</td>
		          <td>${user.userName}</td>
				  <td>${user.organizationName}</td>
				  <td>${user.createrName}</td>
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
				    <td>
				  	<input class="bnt_class07" type="button"  value="分 配"  onclick="doAssign('1','${user.userId}')"/>
				  </td>
		        </tr>
			</c:forEach>
	      	</table>
	      	 <div class="hy_next">
					<jsp:include flush="true" page="/public/include/navigate.jsp"></jsp:include>
			 </div>
			<div class="ge02"></div>
		</div>
	</div>
	<div class="ge01"></div>
	</form>
</body>
</html>