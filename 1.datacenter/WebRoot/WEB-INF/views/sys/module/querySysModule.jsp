<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>模块管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
	
	
function ellipsisStr(str){
		 	var newStr = '';
		 	var array = new Array(); 
		 	array = str.split("");
		 	if(array.length > 16){
		 		newStr = str.substr(0, 16) + "..."; 
		 	}else{
		 		newStr = str;
		 	}
			return newStr;
		}
		
</script>
</head>
<body class="bg_c_g">
	<form method="post" name="pageForm" id="pageForm">
		<input type="hidden"  id="moduleIds" name="moduleIds" />
		<div class="gl_m_r_nav">当前位置 : 系统管理 &gt; 管理模块 &gt; 查询模块</div>	
		<table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
		   <tr>
	        <td  valign="top" class="gl_m_r_n_tb01_m"></td>
            <td valign="top">
               <div class="gl_bt_bnt01" >
               <input name="" type="button" class="gl_cx_bnt01b" id="setDivs" value="展开查询" onclick="setDiv();"/>
               查询</div>
             <div id="serachDisplay" style="display: none;">
              <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <th width="100">模块名称:</th>
                 <td>
               <input style="width:98%;font-family:'微软雅黑'; height:20px; line-height:20px; border:#cfd0d0 1px solid; font-size:12px;" class="" type="text" name="moduleName" id="moduleName"  value="${form.moduleName}"/>                 
                 </td>
                 <th width="100">模块编码:</th>
                 <td>
               <input style="width:98%;font-family:'微软雅黑'; height:20px; line-height:20px; border:#cfd0d0 1px solid; font-size:12px;"  type="text" name="moduleCode" id="moduleCode" value="${form.moduleCode}"/>
			  </td>
               </tr>
              
             </table>
             
                 <div class="gl_ipt_03">
					<input name="input" type="button" class="gl_cx_bnt03" value="查 询" onclick="doSearch();"/>
					&nbsp;
					<input name="input2" type="button" class="gl_cx_bnt03" value="重 置" onclick="cleanValue();"/>
					
				</div>
               </div>
               <div class="ge_a01"></div>
               
               <div class="gl_bt_bnt01">模块列表</div>
             
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
	                
	                <th width="10%">模块名称</th>
					<th width="8%">所属模块</th>
					<th width="28%">模块编码</th>
					<th width="28%">请求路径</th>
					<th width="9%">菜单级别</th>
					<th width="8%">是否是菜单</th>
	                <th width="9%">操作</th>
                </tr>
               	<c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
					<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if> >
						
						<td>${item[0].moduleName}</td>
						<td>${item[1].moduleName}</td>
						<td><a rel="tooltip" title="${item[0].moduleCode}">${item[0].moduleCode}</a></td>
						<td style="word-break:break-all;text-align:left;padding:3px 8px 3px 8px;line-height:19px;"><a rel="tooltip" title="${item[0].url}">${item[0].url}</a></td>
						<td>
							<c:choose>
								<c:when test="${item[0].menuLevel ==1}">
									一级菜单
								</c:when>
								<c:when test="${item[0].menuLevel ==2}">
									二级菜单
								</c:when>
								<c:when test="${item[0].menuLevel ==3}">
									三级菜单
								</c:when>
								<c:when test="${item[0].menuLevel ==4}">
									四级菜单
								</c:when>
							</c:choose>
						</td> 
						<td>
							<c:choose>
								<c:when test="${item[0].isMenu ==1}">
									是
								</c:when>
								<c:otherwise>
									不是
								</c:otherwise>
							</c:choose>
						</td> 
						<td align="center">
						<span>
						  <img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/></span>
						  <span><a href="javascript:moduleDetail('${item[0].moduleId }');">查看</a></span> 
						</td>
					</tr>
				</c:forEach>
               </table>
               
               <div class="pageBox" >
                 <jsp:include flush="true" page="/public/include/navigate2.jsp"></jsp:include>
               </div>
            
            </td>
           </tr>
		</table>
	</form>
	<script type="text/javascript" src="/SRMC/rmpb/js/list-common.js"></script>
	<script type="text/javascript">
	
	function moduleDetail(mouduleId){
	  window.location.href = '<%=basePath%>sys/module/detailSysModuleByIdAdd/'+mouduleId+'?'+Math.random();
	}
	
	
	//重置
	function cleanValue(){
	    document.getElementById("moduleName").value="";
	    document.getElementById("moduleCode").value="";
	}
	function doSearch(){
		document.all("pageIndex").value = "1";
	document.forms[0].submit();
	}

	</script>
</body>
</html>