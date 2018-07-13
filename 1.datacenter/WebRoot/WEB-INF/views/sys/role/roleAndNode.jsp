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
<title>业务配置</title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">

function selectAlls() {
		for (var i=0;i<form.length ;i++) {
			var e = form[i];
			if (e.name == 'checks') {
	   			e.checked = form.checkAlls.checked;
   			}
   		}
	}
function getSelectCounts() {
	var ids="";
	document.getElementById("teamRoleUserIds").value="";
	var chks = document.getElementsByName('checks');
	var j = 0;
	for ( var i = 0; i < chks.length; i++) {
		if (chks[i].checked){
			j++;
			ids+=chks[i].value+",";
		}
	}
	document.getElementById("teamRoleUserIds").value=ids;
	return j;
}

//单行删除
function delRoleUser(roleuserId){
   var flowNodeId=parent.window.document.getElementById("flowNodeId").value;
   var pageSize1=parent.window.document.getElementById("pageSize1").value;   
   var pageSize2=document.getElementById("pageSize2").value;
    jQuery.ajax({
		type:"POST",
		url:"<%=basePath%>sys/role/delRoleUser",
		data:{"roleuserId":roleuserId,"flowNodeId":flowNodeId},
		success:function(result){	
		  if(result=='1'){
		    alert("删除成功!");
		    document.getElementById("pageSize2").value=pageSize2;		   
			var pageIndex = document.getElementById("pageIndex").value;
			window.parent.document.getElementById("toGetUser").src="<%=basePath%>sys/role/userList?flowNodeId="+flowNodeId+ "&pageSize1="+pageSize1;
			window.parent.document.getElementById("userFlowActor").src="<%=basePath%>sys/role/getAllConfigUser?flowNodeId="+flowNodeId + "&pageSize2="+pageSize2+ "&pageIndex="+ pageIndex;
		  }if(result=='0'){
		    alert("删除失败!");
		  }
		},
		error:function(){
			alert("删除失败!");
		}
  });
}
</script>
</head>

<body class="bg_c_g">
<input type="hidden" id="projectId" value="${projectId }"/>
<form method="post" name="form" id="pageForm" >  
     <input type="hidden" value="" id="pageSize3" name="pageSize3" /> 
     <input type="hidden" value="" id="pageIndexTemp" name="pageIndexTemp" />
     <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr class="tuan01a">
          <th>登录账号</th>
          <th>用户姓名</th>
          <th>操作</th>
        </tr>
        	        <c:forEach items="${ITEMPAGE.items}" var="items" varStatus="i">
		        <tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if>>
	               <td>${items[1].account}</td>
	               <td>${items[1].userName}</td>
	               <td>
	           
	                  <a href="javascript:delRoleUser('${items[0].roleuserId }');">删除</a>
	               
				   </td>
		         </tr>
	        </c:forEach>
	    </table>
       <div class="ge01"></div>
		<jsp:include flush="true" page="/public/include/navigate6.jsp"></jsp:include>
	</form>
</body>
</html>