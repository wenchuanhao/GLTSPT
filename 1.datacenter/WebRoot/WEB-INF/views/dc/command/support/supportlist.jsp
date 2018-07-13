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
<style type="text/css">
.shenglue{
 width:5em;
 overflow: hidden; 
 text-overflow: ellipsis;
 white-space:nowrap; 
 color:#4084b6;
}
</style>
</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
	    <input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
		<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />
	</div>
	
	<form name="form" id="pageForm" method="post"  >
	
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${support.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${support.pageSize}" id="pageSize"	name="pageSize"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	  	<tr>
	  		<th width="9%" align="right">支撑单位名称：</th>
		    <td width="20%">
		    	<input id="supportorgName" name="supportorgName" value="${support.supportorgName }"  type="text"  placeholder="请填写支撑单位名称" class="text01" style="width:150px;"  />
			</td> 
		</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >单位列表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
   			<a class="btn_common01" href="javascript:void(0)" id="supportAdd" /><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新 增</span></a>
    		<a class="btn_common01" href="javascript:batchDel()" /><img src="/SRMC/dc/images/btnIcon/del.png" /><span>删 除</span></a>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox"></th>
	  		<th>序号</th>
			<th>支撑单位名称</th>
			<th>新增时间</th>
			<th>操作</th>
	  		 
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" type="checkbox" value="${vo.supportorgId}"></td>
	  	  	<td>${i.count }</td>
		    <td>
			    ${vo.supportorgName}
		    </td>
		    <td><fmt:formatDate value="${vo.createTime}" pattern="yyyy-MM-dd"/></td>
		    <td >
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a class="fancybox_supportAdd" href="javascript:void(0)" value="${vo.supportorgId}" id="${vo.supportorgId}">编辑</a></span>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${vo.supportorgId}')">删除</a></span>
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
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>support";var baseUrl = "<%=basePath%>";</script>
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<!-- 页面弹窗 -->
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript" src="/SRMC/dc/command/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
<script type="text/javascript">
$(function(){

});
 $(document).ready(function(){
  	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
    });
    var $subBox = $("input[name='subBox']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
    });
    supportAdd();
    supportEdit();
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>support/list";
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#supportorgName").val("");
	ev_search();
}


//批量删除
function batchDel(){
	var $subBoxChecks = $("input[name='subBox']:checked");
	if($subBoxChecks.length == 0){
		alert("请至少选中一项配置");
		return;
	}
	if(confirm("确定批量删除"+$subBoxChecks.length+"项配置么？")){
		var rulesIds = '';
		$.each($subBoxChecks,function(k,v){
			if(k == 0){
				rulesIds += $(v).val();
			}else{
				rulesIds += ","+$(v).val();
			}
		});
		ajaxDel(rulesIds);
	}
}


//删除配置
function ev_delete(id){
	if(confirm("确定删除该配置么？")){
		ajaxDel(id);
	}
}


function ajaxDel(id){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>support/delSupport",
        data:"id=" + id,
        success:function(data){
        	if(data == 1){
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
