<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<jsp:include page="../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="PROJECT_STATUS,PROJECT_TYPE"></jsp:param>
</jsp:include>
</head>
<body>
<div class="gl_import_m">
	<div class="ge01"></div>
	<div style="margin:0 auto 20px; max-width:100%; overflow:hidden; text-align:center;" ><img src="/SRMC/dc/images/explain.jpg" /></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >建设项目一览表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<a class="btn_common01" href="javascript:ev_jsxmMore()" /><span>MORE></span></a>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox"></th>
			<th>项目类型</th>
			<th>建设项目编号</th>
			<th>建设项目名称</th>
			<th>项目状态</th>
			<th>建设总控</th>
			<th>建设项目管理员</th>
			<th>创建时间</th>
			<th>操作</th>
	  </tr>
	  
	  <c:forEach items="${jsxmList}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" type="checkbox" value="${vo.id}"></td>
		    <td>
				<jsp:include page="../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="PROJECT_TYPE"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.column01}"></jsp:param>
			    </jsp:include>		    
		    </td>
		    <td><span><a href="javascript:ev_edit('${vo.id}')">${vo.column02}</a></span></td>
		    <td>${vo.column03}</td>
		    <td>
					${vo.column08 ne '1' ? '':'立项完成'}
					${vo.column08 ne '2' ? '':'采购完成'}
					${vo.column08 ne '3' ? '':'设计完成'}
					${vo.column08 ne '4' ? '':'施工完成'}
					${vo.column08 ne '5' ? '':'初验完成'}
					${vo.column08 ne '6' ? '':'终验完成'}					    
		    </td>
			<td>${vo.column04Name}</td>
			<td>${vo.column10Name}</td>
		    <td><fmt:formatDate value="${vo.column07}" pattern="yyyy-MM-dd "/></td>
		    <td >
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="javascript:ev_tree('${vo.id}');">树形</a></span>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/export.png"><a href="javascript:ev_openFile('${vo.id}','${vo.column01}')">全部文档</a></span>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="javascript:ev_tzbm('${vo.id}');">投资编码</a></span>
		    </td>
		 </tr>
	   </c:forEach>
	   <c:if test="${empty jsxmList}">
	   <tr><td colspan="9">找不到对应的数据</td></tr>
	   </c:if>
	</table>
	
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >投资一张表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<a class="btn_common01" href="javascript:ev_tzbmMore()" /><span>MORE></span></a>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox"></th>
			<th>投资编号</th>
			<th>项目名称</th>
			<th>项目总投资<br/>（万元）</th>
			<th>年度安排<br/>资本开支<br/>（万元）</th>
			<th>至上年度安排<br/>投资计划<br/>（万元）</th>
			<th>累计签订合同<br/>金额不含税<br/>（万元）</th>
			<th>累计完成<br/>资本开支<br/>（万元）</th>
			<th>年度资本开支<br/>（万元）</th>
			<th>本年度资本<br/>开支百分比</th>
			<th>年度转资目标<br/>（万元）</th>
			<th>本年累计转资<br/>（万元）</th>
			<th>累计付款<br/>（万元）</th>
			<th>负责人</th>
			<th>计划书文号</th>
			<th>任务书文号</th>
	  </tr>
	  
	  <c:forEach items="${tzbmList}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" type="checkbox" value="${vo.id}"></td>
	  	  	<td><span><a href="javascript:ev_tz_edit('${vo.id}')">${vo.a}</a></span></td><!-- 投资编号 -->
		    <td>${vo.b}</td><!-- 项目名称 -->
		    <td><fmt:formatNumber value="${vo.c}" type="currency"  maxFractionDigits="2"/></td><!-- 项目总投资（万元） -->
			<td><fmt:formatNumber value="${vo.d}" type="currency"  maxFractionDigits="2"/></td><!-- 资本开支目标（万元） -->
			<td><fmt:formatNumber value="${vo.e}" type="currency"  maxFractionDigits="2"/></td><!-- 至上年度安排投资计划（万元） -->
			<td><fmt:formatNumber value="${vo.f}" type="currency"  maxFractionDigits="2"/></td><!-- 累计签订合同金额不含税（万元） -->
		    <td><fmt:formatNumber value="${vo.g}" type="currency"  maxFractionDigits="2"/></td><!-- 累计完成资本开支（万元） -->
		    <td><fmt:formatNumber value="${vo.h}" type="currency"  maxFractionDigits="2"/></td><!-- 年度资本开支（万元） -->
		    <td>${vo.i}</td><!-- 本年度资本开支百分比 -->
			<td><fmt:formatNumber value="${vo.j}" type="currency"  maxFractionDigits="2"/></td><!-- 年度转资目标（万元） -->
			<td><fmt:formatNumber value="${vo.k}" type="currency"  maxFractionDigits="2"/></td><!-- 本年累计转资（万元） -->
			<td><fmt:formatNumber value="${vo.l}" type="currency"  maxFractionDigits="2"/></td><!-- 累计付款（万元） -->
		    <td>${vo.column13Name}</td><!-- 负责人 -->
		    <td>${vo.n}</td><!-- 计划书文号 -->
		    <td>${vo.o}</td><!-- 任务书文号 -->    
		 </tr>
	   </c:forEach>
	   <c:if test="${empty tzbmList}">
	   <tr><td colspan="16">找不到对应的数据</td></tr>
	   </c:if>	   
	</table>
	
	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >周报一张表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<a class="btn_common01" href="javascript:ev_zbMore()" /><span>MORE></span></a>
		</div>    	
  	</div>
  	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox"></th>
			<th>项目类型</th>
			<th>项目编号</th>
			<th>项目名称</th>
			<th>周报名称</th>
			<th>事项状态</th>
			<th>本周状态</th>
			<th>汇报周期</th>
			<th>本周工作</th>
			<th>下周计划</th>
			<th>关键点</th>
			<th>存在问题</th>
			<th>汇报人</th>
			<th>汇报时间</th>
	  </tr>
	  
	  <c:forEach items="${zbList.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" type="checkbox" value="${vo.id}"></td>
		    <td>
				<jsp:include page="../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="PROJECT_TYPE"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.column14}"></jsp:param>
			    </jsp:include>
		    </td>
		    <td>
			    ${vo.column02}
		    </td>
		    <td title="${vo.projectName}">
			    <a style="color: #40baff;" href="javascript:void(0);" onclick="window.open('<%=basePath%>zb/view?id=${vo.id}&code=${vo.projectId}')"><div class="shenglue">${vo.projectName}</div></a>
		    </td>
		    <td title="${vo.column01}">
		    	<a style="color: #40baff;" href="<%=basePath%>zb/view?id=${vo.id}"><div class="shenglue">${vo.column01}</div></a>
		    </td>
			<td>
			    <jsp:include page="../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="PROJECT_STATUS"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.column10}"></jsp:param>
			    </jsp:include>
			</td>
			<td>
				<jsp:include page="../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="PROJECT_STATUS"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.column11}"></jsp:param>
			    </jsp:include>
			</td>
			<td><fmt:formatDate value="${vo.column08}" pattern="yyyy/MM/dd"/>--<fmt:formatDate value="${vo.column09}" pattern="yyyy/MM/dd"/></td>
			<td title="${vo.column03}"><div class="shenglue">${vo.column03}</div></td>
			<td title="${vo.column04}"><div class="shenglue">${vo.column04}</div></td>
			<td title="${vo.column05}"><div class="shenglue">${vo.column05}</div></td>
			<td title="${vo.column06}"><div class="shenglue">${vo.column06}</div></td>
			<td>${vo.column07}</td>
		    <td><fmt:formatDate value="${vo.column13}" pattern="yyyy-MM-dd"/></td>
		 </tr>
		 <c:if test="${empty zbList.items}">
		   <tr><td colspan="14">找不到对应的数据</td></tr>
		   </c:if>
	   </c:forEach>
	</table>	
 	<div class="gd_page">
     </div>
     <div class="ge01"></div>
</div>
<form name="form" id="pageForm" method="post"  ></form>
<script type="text/javascript">
function ev_jsxmMore(){
	window.open("<%=basePath%>jsxmfile/list?key="+Math.random());
}

function ev_tzbmMore(){
	window.open("<%=basePath%>tzReport/tzList?key="+Math.random());
}

function ev_zbMore(){
	window.open("<%=basePath%>zbReport/list?key="+Math.random());
}
function ev_openFile(id,type){
	window.open("<%=basePath%>jsxmfile/fileList?id="+id+"&type="+type);
}

function ev_zb(id){
	var url = "<%=basePath%>zb/add?proType=5&code="+id;
	document.forms[0].action = url;
	document.forms[0].submit();
}

function ev_tzbm(id){
	window.open("<%=basePath%>jsxm/tzbmList?pageSource=zb&id="+id);
}

function ev_edit(id){
	window.open("<%=basePath%>jsxm/edit?pageSource=zb&id="+id);
}

function ev_tz_edit(id){
	window.open("<%=basePath%>tzbm/edit?pageSource=zb&id="+id);
}
function ev_tree(id){
	window.open("<%=basePath%>jsxm/treeList?pageSource=zb&id="+id);
}

function ev_download(id){
	window.location.href = "<%=basePath%>fileUpload/downFile?key="+Math.random()+"&id="+id;
}
</script>
</body>
</html>
