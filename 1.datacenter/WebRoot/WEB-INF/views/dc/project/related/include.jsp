<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

  	<div class="tabpages">
    	<ul class="l">
      		<li onclick="document.forms[0].action='<%=basePath%>related/jsxm';document.forms[0].submit();" ${current eq '1' ? 'class="current"':''} >建设项目</li>
      		<li onclick="document.forms[0].action='<%=basePath%>related/tzbm';document.forms[0].submit();" ${current eq '2' ? 'class="current"':''}>投资编码</li>
      		<li onclick="document.forms[0].action='<%=basePath%>related/zxm';document.forms[0].submit();" ${current eq '3' ? 'class="current"':''}>子项目</li>
      		<li onclick="document.forms[0].action='<%=basePath%>related/zxmHt';document.forms[0].submit();" ${current eq '4' ? 'class="current"':''}>子项目合同</li>
      		<li onclick="document.forms[0].action='<%=basePath%>related/zb';document.forms[0].submit();" ${current eq '5' ? 'class="current"':''}>周报</li>
    	</ul>
  	</div>
