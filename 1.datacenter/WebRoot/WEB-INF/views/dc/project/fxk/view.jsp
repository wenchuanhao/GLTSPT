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
</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
		<input name="" onclick="javascript:location.href='<%=basePath%>fxk/list';" type="button" class="btn_back r" value="返回" />
	    <input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
		<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />
	</div>
	<form name="form" id="pageForm" method="post"  >
		<input type="hidden" value="N" name="isPages" id="isPages"/>
		<input type="hidden" value="${fxk.pageIndex}" id="pageIndex"	name="pageIndex"/>
	    <input type="hidden" value="${fxk.pageSize}" id="pageSize"	name="pageSize"/>	
	    
	    <input type="hidden" value="${fxk.id}" name="id"/>	
	    
	    <table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	    	<tr>
		  		<th width="9%" align="right">阶段：</th>
			    <td width="20%">
			    	<select class="ui-select" name="stageId" id="stageId" style="width:180px;">
				    	<option <c:if test="${empty fxk.stageId }">selected="selected"</c:if> value="">请选择</option>
						<c:forEach items="${list}" var="item" varStatus="i">
							<option ${fxk.stageId == item.stageId ? "selected=\"selected\"":null}  value="${item.stageId}">${item.stageName}</option>
						</c:forEach>
					</select>
				</td> 
		  		
		  		<th width="9%" align="right">内容：</th>
			    <td width="20%">
			    	<input id="contents" name="content" value="${fxk.content }"  type="text"  placeholder="请输入查找内容" class="text01" style="width:150px;"  />
				</td> 
			</tr>
	    </table>
    </form>
  	<div class="ge01"></div>
  	<div class="tabpages">
		<ul class="l" id="tab">
	      		<li id="tab1"  class="current">风险管理库阶段详情<em></em></li>
    	</ul>
	</div>
	<table width="99%" border="0"  cellpadding="0" cellspacing="0" class="listTable"  id="table01">
	  <tr align="center">
			<th>阶段</th>
			<th>序号</th>
			<th>风险因素</th>
			<th>风险导致的后果</th>
			<th>风险应对措施</th>
			<th>风险类别</th>
			
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="items" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	 	<td style="text-align: left;width: 10%;">${items[1].stageName}</td>
	  	 	<td style="text-align: center;width: 3%;">${items[0].seq}</td>
	  	 	<td style="text-align: left;width: 20%;">${items[0].riskFactor}</td>
	  	 	<td style="text-align: left;width: 20%;">${items[0].riskAfter}</td>
	  	 	<td style="text-align: left;width: 40%;">${items[0].riskMeasure}</td>
	  	 	<td style="text-align: left;width: 8%;">${items[0].riskType}</td>
		 </tr>
	   </c:forEach>
	   
	</table>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
</div>
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" >var tempPath = "<%=basePath%>";var basePath = "<%=basePath%>fxk";</script>
<script type="text/javascript">
$(document).ready(function(){

	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	
	var count = 1;//记录合并行的行数
	var trNum = 1;//记录合并的开始游标
	var $tabs = [];//保存应当去除的td
	
	$("#table01").find("tr").each(function(i) {
         if ($("#table01").find("tr:eq(" + i + ")").find("td").length != 0) {
         	$("#table01").find("tr:eq(" + i + ")").find("td").each(function(j) {
         		var getValue= $(this).html();
         		//从第二行开始，取第一例
         		if(i > 1 && j == 0){
	         		var prev = $("#table01").find("tr:eq(" + (i-1) + ")").find("td:eq(0)").html();
	         		var next = $("#table01").find("tr:eq(" + (i+1) + ")").find("td:eq(0)").html();
	         		//当前值和上一值相同,remove
	         		if(getValue == prev){
	         			var $tab = $(this);
	         			$tabs.push($tab);
	         			count++;
	         		}
	         		if(getValue != prev || !next){
	         			$("#table01").find("tr:eq(" + trNum + ")").find("td:eq(0)").prop("rowspan",count);//合并行
	         			count = 1;
	         			trNum = i;
	         		}
         		}
         		temp = $(this).html();
				var endValue=((getValue.replace(/<(.+?)>/gi,"&lt;$1&gt;")).replace(/ /gi,"&nbsp;")).replace(/\n/gi,"<br>");
				$(this).html(endValue);
			});
         }
    });
    $.each($tabs,function(k,v){
    	$(v).remove();
    });
    $("#table01").find("tr").find("td:eq(0)").css("background-color","#ffffff");//合并行的颜色
});


//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>fxk/view?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#stageId").data("ui-select").val("");
	$("#contents").val("");
	ev_search();
}
</script>
</body>
</html>
