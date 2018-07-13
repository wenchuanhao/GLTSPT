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
</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
			<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
			<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />	
	</div>
	
	<form name="form" id="pageForm" method="post"  >
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${form.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${form.pageSize}" id="pageSize"	name="pageSize"/>
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
<!--	<th width="9%" align="right">数据列名：</th>
	    <td width="30%">
	    	<input id="column_name" name="column_name" value="${form.column_name}" type="text" placeholder="请填写数据列名" class="text01" style="width:195px;"  />
		</td>  -->	 
  		<th align="right">字段名：</th>
	    <td width="30%">
	    	<input id="column_cname" name="column_cname" value="${form.column_cname}" type="text" placeholder="请填写字段名" class="text01" style="width:195px;"  />
		</td>
		<th align="right">文本类型：</th>
		<td width="30%">
	    	<select class="ui-select" id="Column_type" name="Column_type" style="width:200px;">
						<option value="">请选择</option>
						<option ${form.column_type eq '1' ? 'selected="selected"':''} value="1">文本</option>
						<option ${form.column_type eq '2' ? 'selected="selected"':''} value="2">时间</option>
	         </select>
		</td>
	</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >数据列列表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    			<a class="btn_common01" href="javascript:ev_add()" /><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
		<th>序号</th>
<!--	<th>数据列名</th>  -->	
		<th>字段名</th>
		<th>文本类型</th>
		<th>文本长度</th>
		<th>操作</th>
 	  </tr>
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
			<td>${i.count}</td>
<!--		<td>${vo.column_name}</td>   -->	
			<td>${vo.column_cname}</td>
			<td>
				<c:if test="${vo.column_type  == 1}">文本</c:if>
				<c:if test="${vo.column_type  == 2}">时间</c:if>
			</td>
			<td>${vo.column_length}</td>	  	
			<td>
				<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${vo.id}');">删除</a></span>
			</td>  	
		 </tr>
	   </c:forEach>
	</table>
 	 <div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
<script type="text/javascript" >var basePath = "<%=basePath%>purchase";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
    <!--导入相关js-->
 	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="/SRMC/dc/purchase/ex_import.js"></script>
<script type="text/javascript">

$(document).ready(function(){
   $('.ui-select').ui_select();
 
});
//新增   

function ev_add(){
    window.location.href = "<%=basePath%>purchase/dataColunmAdd";
}

//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>purchase/dataColunmList?key="+Math.random();
	document.forms[0].submit();
}

//重置
function ev_reset(){
	jQuery("#columnName").val("");
	jQuery("#columnCname").val("");
	jQuery("#columnLength").val("");
	window.location.href="<%=basePath%>purchase/dataColunmList";
}

//删除服务
function ev_delete(id){
	if(confirm("确定删除该记录么？")){
		ajaxDel(id);
	}
}

function ajaxDel(id){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>purchase/deleteDataColumn",
        data:"id=" + id,
        success:function(data){
        	if(data == 1){
        		alert("删除成功！");
        		ev_search();
        	}else{
        		alert("删除失败！");
        	}
        },
        error:function(){
            alert("删除失败！");
        }
    });
}
	 
</script>
</body>
</html>
