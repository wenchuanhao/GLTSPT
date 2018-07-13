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
	<jsp:include flush="true" page="include/queryForRemain.jsp"></jsp:include>
  	<div class="tabpages">
    	<ul class="l">
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/remain';document.forms[0].submit();">待办审核<em>(${remainCount })</em></li>
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/dones';document.forms[0].submit();">已办审核<em>(${donesCount })</em></li>
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/tobeRead';document.forms[0].submit();">待阅<em>(${tobeReadCount })</em></li>
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/haveRead';document.forms[0].submit();" class="current">已阅<em>(${ITEMPAGE.total })</em></li>
    	</ul>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	    <th><input id="checkAll" type="checkbox"></th>
	    <th>序号</th>
	    <th>制度编号</th>
        <th>制度名称</th>
        <th>制度等级</th>
        <th>制度分类</th>
        <th>牵头部门</th>
        <th>到达时间</th>
        <th>事件</th>
        <th>阅读时间</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
	  	<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="tr_id_${item[0]}">
		    <td ><input name="subBox" type="checkbox" value="${item[0]}"></td>
		    <td>${i.count }</td>
		    <td ><a href="<%=basePath%>rulesController/viewRule?rulesId=${item[0]}">${item[1]}</a></td>
		    <td ><a href="<%=basePath%>rulesController/viewRule?rulesId=${item[0]}">${item[2]}</a></td>
		    <td >
		    	<jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="RULES_GRADES_CONFIG"></jsp:param>
					<jsp:param name="paramCode" value="${item[3] }"></jsp:param>
				</jsp:include> 
			</td>
		    <td >${item[5] }/${item[6] }</td>
		    <td >${item[7] }</td>
            <td >${item[8]}</td>
            <td >
				<c:choose>
				    <c:when test="${item[4] == 4}">
				       	该制度被废止
				    </c:when>
				    <c:when test="${item[4] == 5}">
				       	<em>该制度被修订</em>
				    </c:when>
				 </c:choose>
			</td>
		    <td >${item[10]}</td>
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
<script type="text/javascript" >var basePath = "<%=basePath%>rulesController";</script>
<script src="/SRMC/dc/js/checkboxList.js"></script>
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
    
    //牵头部门
	var obj1 = $('#sel_01').data('ui-select');
	obj1.onClick = function(value) {
		 $("#leadDepId_input").val(value);
	}
	//制度等级
	var obj3 = $('#sel_03').data('ui-select');
	obj3.onClick = function(value) {
		 $("#rulesGrade_input").val(value);
	}
    
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>rulesController/haveRead";
	document.forms[0].submit();
}


//重置查询
function ev_reset(){
	$("#status_input").val("");
	$("#rulesName").val("");
	$("#rulesGrade_input").val("");
	$("#remindStatus_input").val("");
	$("#rulesTypeId_input").val("");
	$("#leadDepId_input").val("");
	ev_search();
}

//发布
function releaseRemain(rulesIds){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>rulesController/releaseRemain",
        data:"rulesId=" + rulesIds,
        success:function(data){
        	if(data == 1){
        		ev_search();
        	}else{
        		alert("发布失败！");
        	}
        },
        error:function(){
            alert("发布失败！");
        }
    });
}
</script>
</body>
</html>
