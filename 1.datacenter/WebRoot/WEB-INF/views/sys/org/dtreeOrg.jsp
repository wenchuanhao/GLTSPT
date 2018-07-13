<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>组织机构树</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dtree.css" />
		<script src="/SRMC/rmpb/js/jquery-1.7.1.js" type="text/javascript"></script>
		<script src="/SRMC/rmpb/js/dtree/dtree.js" type="text/javascript"></script>
		 <script language="javascript" type="text/javascript">
		 
	 //右键菜单功能
	   document.oncontextmenu = nocontextmenu;//右键屏蔽
	   function nocontextmenu() {  
	        return false;
	   } 
		var param="";
	//点击节点后，将右键菜单更改为自定义的菜单
       function loadMenu(obj) {
           //获取参数
            param = obj;
           
           //重写 -- 页面点击右键菜单事件
           document.oncontextmenu = function() {
               //定位,显示
               contextmenu.style.posLeft = document.body.scrollLeft + event.x + 10;
               contextmenu.style.posTop = document.body.scrollTop + event.y + 10;
               contextmenu.style.display = "inline";
               return false;
            }
       }
			
			
	 //单击页面上除了菜单以外的地方，隐藏右键菜单
       document.onclick = function() {
           //判断单击的是否为右键菜单
           //如果不是，隐藏右键菜单
           if (document.activeElement != contextmenu) {
               contextmenu.style.display = "none"
               //还原页面本来的右键菜单
               document.oncontextmenu = '';
           }

       }
       //颜色变量
       var colorTemp = "";
        function toAddOrg(){
	 		parent.queryOrg.window.location.href = "<%=basePath%>sys/org/addOrg?perId="+param;
	  }
			
</script>
	</head>
	<body>
		<form name="form">
			<script type="text/javascript">
			d = new dTree('d');
				<c:forEach items="${orgs}" var="org">
					<c:choose>
					<c:when test="${org.orgName == '南方基地'}">
							d.add('${org.orgId}', '-1', "<font oncontextmenu=loadMenu('${org.orgId}') color=#EF5900><b>组织机构</b></font>", '<%=basePath%>sys/org/queryOrg?parentId=${org.orgId}&index=Math.random();', '', 'queryOrg', '','//');
						</c:when>
						<c:otherwise>
							d.add('${org.orgId}','${org.parentId}', "<font oncontextmenu=loadMenu('${org.orgId}')>${org.orgName}</font>", '<%=basePath%>sys/org/queryOrg?parentId=${org.orgId}&index=Math.random();', '', 'queryOrg');
						</c:otherwise>
					</c:choose>	
				</c:forEach>
			document.write(d);
		</script>
		<!-- 右键菜单开始 -->   
        <div id="contextmenu" align="center" style="FILTER:alpha(opacity=95);border:1px solid #81B1CB;background:#eeeeee; width:75px;padding:0px;display:none;position:absolute" >
            <table style=" width:75px;  font-size:12px; ">
                <tr>
                    <td align="center" id="modAttribute" onclick="javascript:toAddOrg();"  onmouseover="javascript:colorTemp=this.style.backgroundColor;this.style.backgroundColor='#98B4CE';" onmouseout="javascript:this.style.backgroundColor=colorTemp;"  > <!-- 编辑事件 -->
                          	新增组织
                    </td>
                </tr> 
               
            </table>            
        </div>     
     <!-- 右键菜单结束 -->
		</form>
	</body>
</html>
