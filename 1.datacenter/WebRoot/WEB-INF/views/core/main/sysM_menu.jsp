<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/SRMC/rmpb/css/style3.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tree02.js"></script>
<script type="text/javascript">

	$(document).ready(function($) {
	$("#yfgl_sub_tb").on("click",".gl_m_r_ul li",function(e){
	$("#yfgl_sub_tb li.hover").removeClass("hover").next().hide();
	$(this).addClass("hover").next().show();
	});
	$("#yfgl_sub_tb").on("click",".gl_m_r_ul li a",function(e){
		$("#yfgl_sub_tb li a.hover").removeClass("hover");
		$(this).addClass("hover");
	});
});
	function onpen(url){
	var path = "<%=basePath%>" + url;
	 window.location.href = path;
	}
	
</script>

</head>

<body class="bg_c_g">
<!--main-->
<table class="yfgl_main_tb"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="yfgl_m_l" width="108" valign="top" >
      <ul class="yfgl_m_lu" style="overflow:scroll-y">
         	<c:forEach items="${menuForms}" var="menu" varStatus="i">
				<li id="one${i.count}"
					onclick="setTab('one',${i.count},${fn:length(menuForms)})"
				<c:if test="${i.index==0}">class='hover'</c:if>>
				<span <c:if test="${i.index==0}">class='font_l02'</c:if><c:if test="${i.index!=0}">class='font_l'</c:if>>
				 <p class="yl_m_pic"><img src="/SRMC/rmpb/images/bnt_l0${i.index+1 }.png"/></p><p class="yl_m_font">${menu.module.moduleName}</p></span>
				</li>
			</c:forEach>
		</ul>
    </td>
    <td valign="top" id="yfgl_sub_tb">
    <c:forEach items="${menuForms}" varStatus="i" var="menus">
			<div id="con_one_${i.count}" <c:if test="${i.count != 1 }">style="display: none;"</c:if>>
				<dl class="gl_m_r_dl">
					<c:forEach items="${menus.menuNodes}" var="threeMenu" varStatus="j">
						<dt>
						    <span class="gl_m_r_pic"><img src="/SRMC/rmpb/images/bnt_a0${j.count}.png"/></span>
							<!--span class="gl_m_r_font">${threeMenu.nowMenu.moduleName}</span-->
							<c:choose>
								<c:when test="${empty threeMenu.nowMenu.url || ''==threeMenu.nowMenu.url}">
								<span class="gl_m_r_font">${threeMenu.nowMenu.moduleName}</span>
								</c:when>
								<c:otherwise>
								
								<span style="display:block; width:96px; height:38px; float:left;"
								 onclick="parent.frmmain.location='<%=basePath%>${threeMenu.nowMenu.url}'"><a href="<%=basePath%>${threeMenu.nowMenu.url}" target="sysM_mainFrame">${threeMenu.nowMenu.moduleName}</a></span>
								</c:otherwise>
							</c:choose>
							<img src="/SRMC/rmpb/images/left_fold1b.gif"  name="subimg" width="20" height="33"
							id="subimgboard${i.count}${j.count}" /></span>
						</dt>
						<dd style="display : none;" id='submenuboard${i.count}${j.count}' >
							<c:forEach items="${threeMenu.nextNode}" var="fourMenu" varStatus="k">
								<a href="<%=basePath%>${fourMenu.url}" target="sysM_mainFrame">${fourMenu.moduleName}</a>
							</c:forEach>
						</dd>
					</c:forEach>
				</dl>
			</div>
		</c:forEach>
    </td>
  </tr>
</table>

<!--main END-->
</body>
</html>
