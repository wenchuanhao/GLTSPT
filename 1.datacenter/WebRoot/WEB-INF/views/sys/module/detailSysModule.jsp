<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>管理模块</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
 <!--<script type="text/javascript" src="/SRMC/rmpb/js/test.js"></script>-->
</head>
<body class="bg_c_g">
	<form action="<%=basePath%>sys/module/modifySysModule" method="post" name="moduleForm">
		<input type="hidden" name="moduleId" id="moduleId"  value="${sysModule.moduleId}"/>
		<input type="hidden" name="oldCode" id="oldCode"  value="${sysModule.moduleCode}"/>
	  <div class="gl_m_r_nav">当前位置 : 系统管理 &gt; 管理模块 &gt; 查看模块</div>
	  <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
	    <tr>
	      <td  valign="top" class="gl_m_r_n_tb01_m"></td>
          <td valign="top">
          
           <div class="gl_bt_bnt01">模块信息</div>
            <table class="gl_table_a01_6L" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <th width="100">是否是根节点:</th>
                 <td>
                 <c:if test="${sysModule.parentCode=='ROOT'}"><input type="hidden" name="isRoot" id="isRoot"  onclick="changeParentModule()" value="1"/>是</c:if>
		  		<c:if test="${sysModule.parentCode!='ROOT'}"><input type="hidden" name="isRoot" id="isRoot"  onclick="changeParentModule()" value="0"/>否</c:if>
		 		<!-- 
		 		是：<input type="radio" name="isRoot" id="isRoot"  onclick="changeParentModule()" value="1"  <c:if test="${sysModule.parentCode=='ROOT'}">checked="checked"</c:if> />
				否：<input type="radio" name="isRoot" id="isRoot"  onclick="changeParentModule()" value="0" <c:if test="${sysModule.parentCode!='ROOT'}">checked="checked"</c:if>/>
				   
		 		 -->
		 </td>
                 <th width="100">父模块:</th>
                 <td>
                 <div id="parentModule"  >
                 <c:if test="${sysModule.parentCode=='ROOT'}"></c:if> 
                  <c:if test="${sysModule.parentCode!='ROOT'}">
					    	<c:forEach items="${modules}" var="module">
					    		<c:if test="${module.moduleCode==sysModule.parentCode}">${module.moduleName}</c:if>
						    	<c:forEach items="${module.nextList}" var="v2">
						    		<c:if test="${v2.moduleCode==sysModule.parentCode}">${v2.moduleName}</c:if>
							    	<c:forEach items="${v2.nextList}" var="v3">
							    		<c:if test="${v3.moduleCode==sysModule.parentCode}">${v3.moduleName}</c:if>
								    	<c:forEach items="${v3.nextList}" var="v4">
								    		<c:if test="${v4.moduleCode==sysModule.parentCode}">${v4.moduleName}</c:if>
								    	</c:forEach>							    		
							    	</c:forEach>						    		
						    	</c:forEach>					    		
					    	</c:forEach>					
                 </c:if> 
                 </div>
                 <!--  
				 <div id="uboxstyle" >
                       <select id="parentModule" disabled="disabled" <c:if test="${sysModule.parentCode=='ROOT'}">style="display: none;"</c:if> name="parentCode" class="select_new01">
					    	<option value="-1">-选择-</option>
					    	<c:forEach items="${modules}" var="module">
					    		<option value="${module.moduleCode}" <c:if test="${module.moduleCode==sysModule.parentCode}">selected="selected"</c:if>>-${module.moduleName}-</option>
					    	</c:forEach>
					    </select>
					 </div>
					 -->
		 </td>
                 <th width="100">模块名称:</th>
                 <td>
		 ${sysModule.moduleName}
		 </td>
               </tr>
               <tr>
                 <th>模块编码:</th>
                 <td>
               ${sysModule.moduleCode}
                 </td>
                 <th>是否是菜单:</th>
                 <td>
                  <c:if test='${sysModule.isMenu == "1" }'>是</c:if> 
				<c:if test='${sysModule.isMenu == "0" }'>否</c:if> 
                 </td>
                 <th>请求路径:</th>
                 <td>
                 ${sysModule.url}
		</td>
               </tr>
               <tr>
                 <th>显示排序:</th>
                 <td >${sysModule.seq}</td>
              
                 <th>菜单级别:</th>
                 <td>
		 					 <c:if test='${sysModule.menuLevel == "1" }'>一级菜单</c:if>
							<c:if test='${sysModule.menuLevel == "2" }'>二级菜单</c:if>
							 <c:if test='${sysModule.menuLevel == "3" }'>三级菜单</c:if>
							<c:if test='${sysModule.menuLevel == "4" }'>四级菜单</c:if>
						
		</td>
                 <th>菜单图标:</th>
                 <td>
                 <img src="/SRMC/cdc/sys/menu/${sysModule.imgName}"  />
				</td>
                </tr>
                <tr>
                <th width="100">
						模块描述:
				</th>
				<td colspan="5" >
						${sysModule.description}
					</td>
				</tr>
             </table>
           
           
               
            <div class="gl_ipt_03">
		        <!-- <input name="input" type="button" class="gl_cx_bnt03" value="返 回" onclick="javascript:history.back();"/> -->
                <input name="input" type="button" class="gl_cx_bnt03" value="返 回" onclick="doReturn('${location}')"/>
            </div>
          </td>
	    </tr>
     </table>
	</form>	
	<script type="text/javascript">
		function changeParentModule(){
			var obj = document.getElementById("parentModule");
		 	if(obj.style.display=="none"){
		  		obj.style.display="block";
		 	}
		  	else{
		  		obj.style.display="none";
		  	}
		}
	</script>
	<script type="text/javascript">		
		 function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		        }
	   </script>
</body>
</html>
