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

<style>
	#uploadifive-uploadFiles{
   		margin-bottom: -29%;
    	margin-left: -100%;
	}
</style>
</head>
<body>
<div class="gl_import_m">
	<form name="form" id="pageForm" method="post"  >
		<input type="hidden" value="N" name="isPages" id="isPages"/>
		<input type="hidden" value="${fxk.pageIndex}" id="pageIndex"	name="pageIndex"/>
	    <input type="hidden" value="${fxk.pageSize}" id="pageSize"	name="pageSize"/>	
    </form>
	<!-- 附件临时ID -->
	<input type="hidden" name="fileTempId" id="fileTempId" value="${fileTempId}"/>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >风险管理库列表<em></em></li>
    	</ul>
    	<c:if test="${not empty userRoles && userRoles eq 1}">
    	<div class="otherButtons r">
    		<input name="fileName" id="uploadFiles" type="file" class="btn_upload" style="display: none">   
    		<!-- <div class="text01 l btn_upload" style="width:100px;margin-right: 5px;" id="fi_box_div_1" value="1">
		    	<span style="color: #40baff">导 入</span>
		    	<input name="fileName" id="uploadFiles" type="file" class="btn_upload" style="display: none">
            </div> -->
    		<a href="<%=basePath%>rulesController/downloadRulesFile?fileId=${fileId }" /><img src="/SRMC/dc/fxk/output.png" /></a>
		</div>    	
    	</c:if>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th>序号</th>
			<th>项目阶段</th>
			<th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td>${i.count }</td>
		    <td>
				<a href="<%=basePath%>fxk/view?viewId=${vo.stageId}">${vo.stageName }</a>
		    </td>
		    <td >
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="<%=basePath%>fxk/view?viewId=${vo.stageId}">详情</a></span>
		    </td>
		 </tr>
	   </c:forEach>
	</table>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
<script src="/SRMC/dc/js/jquery.js"></script>
<script type="text/javascript" >var tempPath = "<%=basePath%>";var basePath = "<%=basePath%>fxk";</script>
<!--文件上传样式，js -->
<!-- 
<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script> -->

<link rel="stylesheet" href="/SRMC/rmpb/js/uploadifive/uploadifive.css" type="text/css" />
<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/jquery.uploadifive.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/uploadcommon.js"></script>

<!-- 文件上传自定义 -->
<script type="text/javascript" src="/SRMC/dc/fxk/js/ex_import.js"></script> 
<script type="text/javascript">
$(document).ready(function(){
	//上传文件初始化
	setConfig(tempPath+"rulesController/uploadFile");
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>fxk/list";
	document.forms[0].submit();
}

//清空所有风险库数据
function ev_delete(){
	if(confirm("确定清空所有风险库数据？")){
		jQuery.ajax({
	        type:"POST",
	        async:false,
	        url:"<%=basePath%>fxk/delData",
	        success:function(data){
	        	if(data == 1){
	        		alert("数据清空成功！");
	        		ev_search();
	//         		$("#tr_id_"+id).remove();
	        	}else{
	        		alert("数据清空失败！");
	        	}
	        },
	        error:function(){
	            alert("数据清空失败！");
	        }
	    });
	}
}
</script>
</body>
</html>
