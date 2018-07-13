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
<title>合同开支列表</title>
<base  target="_self"/>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<body>
<div class="gl_import_m" >
	 <form name="form" id="pageForm" method="post"  >
	 	<input type="hidden" value="N" name="isPages" id="isPages"/>
		<input type="hidden" value="${htKz.pageIndex}" id="pageIndex"	name="pageIndex"/>
	    <input type="hidden" value="${htKz.pageSize}" id="pageSize"	name="pageSize"/>
	 </form>
  	<div class="ge01"></div>
  	<div class="tabpages" >
    	<ul class="l" id="tab">
	      		<li id="tab1"  class="current">合同开支列表<em></em></li>
	      		<li id="tab2" >子项目合同详情<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<c:if test="${role ne null && role ne 'GC_QTRY'}">
    		<a class="btn_common01" href="javascript:ev_add()" /><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>
    		</c:if>    	
    		<a class="btn_common01" href="javascript:window.close();" /><img src="/SRMC/dc/images/btnIcon/invalid.png" /><span>关闭</span></a>
		</div>    	
  	</div>

	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable" id="table1">
	  <tr>
	  		<th><input id="checkAll" type="checkbox"></th>
			<th>投资编号</th>
			<th>合同编号</th>
			<th>合同名称</th>
			<th>合同不含税金额<br />（万元）</th>
			<th>合同含税金额<br />（万元）</th>
			<th>合同对方</th>
			<th>合同类型</th>
			<th>累计形象进度/<br />MIS接收金额（万元）</th>
			<th>本年形象进度/<br />MIS接收金额（万元）</th>
			<th>累计转资金额<br />（万元）</th>
			<th>本年转资金额<br />（万元）</th>
			<th>累计付款金额<br />（万元）</th>
			<th>负责人</th>
			<th>记录时间</th>
			<th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" type="checkbox" value="${vo[0].id}"></td>
		    <td>${vo[0].column02}</td>
		    <c:set var="zxmHt" value="${vo[0].zxmHt}"/>
		    <td><span><a href="javascript:ev_edit('${vo[0].id}')">${zxmHt.column01}</a></span></td>
		    <td>${zxmHt.column03}</td>
			<td><fmt:formatNumber value="${vo[0].column03}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo[0].column04}" type="currency"  maxFractionDigits="6"/></td>
			<td>${vo[0].column05}</td>
			<td>${vo[0].column06 eq '1' ? '费用类':''}${vo[0].column06 eq '2' ? '订单类':''}</td>
			<td><fmt:formatNumber value="${vo[0].column07}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo[0].column08}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo[0].column09}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo[0].column10}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo[0].column11}" type="currency"  maxFractionDigits="6"/></td>
			<td>${vo[0].column13SysUser.userName}</td>
		    <td><fmt:formatDate value="${vo[0].column12}" pattern="yyyy-MM-dd"/></td>
		    <td >
		    	<c:if test="${role ne null && (role eq 'GC_YWGLY' or (role eq 'GC_XMFZR' && fn:contains(zxmHt.column21, sysUserId)))}">
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="<%=basePath%>htKz/add1?id=${vo[0].id}">编辑</a></span><br/>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${vo[0].id}')">删除</a></span>
		    	</c:if>
		    </td>
		 </tr>
	   </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="16">找不到对应的数据</td></tr>
	   </c:if>	   
	</table>
	
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" style="display: none;" id="table2">
		<jsp:include page="../zxmHt/form.jsp" />
  </table>
  	
 	<div class="gd_page">
 	<jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>copperationController";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
<script type="text/javascript" src="/SRMC/dc/cooperation/js/common.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
	var m = "${mess}";
	if(m != "" && m == "s"){
		alert("删除成功");
	}
	else if(m != "" && m == "e"){
		alert("删除失败");
	} 
 
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});

function ev_edit(id){
	ev_submit("<%=basePath%>htKz/edit?id="+id);
	//window.showModalDialog("<%=basePath%>htKz/edit?id="+id,window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px");
}

function ev_add(){
	window.location.href =  "<%=basePath%>htKz/add1?htId=${htId}";
}

function ev_delete(id){
	if(confirm("确定删除？")){
		jQuery.ajax({
	        type:"POST",
	        async:true,
	        url:"<%=basePath%>htKz/delete",
	        data:"id=" + id,
	        dataType:"json",
	        success:function(data){
	        	if(data == "1"){
	        		alert("删除成功");
	        		ev_search();
	        	}else{
	        		alert("删除失败");
	        	}
	        },
	        error:function(){
	        	alert("删除失败");
	        }
    	});
	}
}
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>zxmHt/htKzList?id=${htId}";
	document.forms[0].submit();
}
$(function(){
	$("#tab").find("li").each(function(){
		var id = $(this).attr("id");
		$(this).hover(
			function(){
				if(id == "tab1"){
					$("#tab1").addClass("current"); 
					$("#tab2").removeClass("current");
					$("#table1").show();
					$("#table2").hide();
				}else{
					$("#tab1").removeClass("current"); 
					$("#tab2").addClass("current");				
					$("#table1").hide();
					$("#table2").show();				
				}
			},
			function(){
			}
		);
	});
});
function ev_submit(url){
	document.forms[0].action = url;
	document.forms[0].submit();
}
function ev_openFile(id,type){
	if(type == ""){
		alert("没有关联子项目，不能上传文档");return;
	}
	var url = "<%=basePath%>zxmHt/fileList?id="+id+"&type="+type;
	ev_submit(url);
	//window.showModalDialog(url,window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
}
</script>
</body>
</html>
