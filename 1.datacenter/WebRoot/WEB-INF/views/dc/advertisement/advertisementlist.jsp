<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script src="/SRMC/dc/js/checkboxList.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>advertisement";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	

<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
</head>
  
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
			<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
			<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />	
	</div>
	<form name="form" id="pageForm" method="post"  >
		<input type="hidden" value="N" name="isPages" id="isPages"/>
		<input type="hidden" value="${ad.pageIndex}" id="pageIndex"	name="pageIndex"/>
    	<input type="hidden" value="${ad.pageSize}" id="pageSize"	name="pageSize"/>	
    	
    	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
    		<tr>
    			<th width="10%" align="right">序号：</th>
	    		<td width="23%">
	    			<input id="picSort" name="picSort" value="${ad.picSort }"  type="text"  placeholder="请填写序号" class="text01" style="width:250px;" />
				</td>
				<th width="10%" align="right">图片广告标题：</th>
	    		<td width="23%">
	    			<input id="picTitle" name="picTitle" value="${ad.picTitle }"  type="text"  placeholder="请填写标题" class="text01" style="width:250px;" />
				</td>
    		</tr>
    		<tr>
    			<th width="10%" align="right">创建人：</th>
	    		<td width="23%">
	    			<input id="createUsername" name="createUsername" value="${ad.createUsername }"  type="text"  placeholder="请填写创建人" class="text01" style="width:250px;" />
				</td>
    		</tr>
    	</table>
	</form>
	<div class="tabpages">
    	<ul class="l">
      		<li class="current">列表</li>
    	</ul>
    	<div class="otherButtons r">
    		<a class="btn_common01" href="javascript:ev_add()" /><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>
		</div>
  	</div>
  	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	    <th>序号</th>
	    <th>图片广告标题</th>
	    <th>创建人</th>
	    <th>创建时间</th>
	    <th>操作</th>
	  </tr>
	  <c:forEach items="${advertisement}" var="ad">
	  	<tr>
	  		<td>${ad.picSort }</td> 
	  		<td>${ad.picTitle }</td>
	  		<td>${ad.createUsername }</td>
	  		<td>${ad.createTime }</td>
	  		<td>
	  			<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_edit('${ad.configId}')" >编辑</a></span>
	  			<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${ad.configId}')" >删除</a></span> 
	  		</td>
	  	</tr>
	  </c:forEach>
	</table>
	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
</body>
</html>

<script type="text/javascript">
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>advertisement/list";
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#picSort").val("");
	$("#picTitle").val("");
	$("#createUsername").val("");
	ev_search();
}

function ev_add(){
	document.forms[0].action="<%=basePath%>advertisement/add";
	document.forms[0].submit();
}

function ev_edit(id){
	document.forms[0].action="<%=basePath%>advertisement/add?configId="+id;
	document.forms[0].submit();
}

function ev_delete(id){
	if(confirm("确定对该项进行删除处理？")){
		jQuery.ajax({
	        type:"POST",
	        async:false,
	        url:"<%=basePath%>advertisement/deleteAd",
	        data:"id=" + id,
	        success:function(result){
	        	if(result == 1){
	        		window.location.href = "<%=basePath%>advertisement/list";
	        	}else{
	        		alert("删除失败1！");
	        	}
	        },
	        error:function(){
	            alert("删除失败！");
	        }
	    });
	}
}
</script>
