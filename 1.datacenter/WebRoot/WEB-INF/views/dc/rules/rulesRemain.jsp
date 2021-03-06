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
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/remain';document.forms[0].submit();" class="current">待办审核<em>(${ITEMPAGE.total })</em></li>
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/dones';document.forms[0].submit();">已办审核<em>(${donesCount})</em></li>
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/tobeRead';document.forms[0].submit();">待阅<em>(${tobeReadCount })</em></li>
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/haveRead';document.forms[0].submit();">已阅<em>(${haveReadCount })</em></li>
    	</ul>
    	<div class="otherButtons r">
			<a class="btn_common01" href="javascript:batchRelease()" /><img src="/SRMC/dc/images/btnIcon/publish.png" /><span>批量发布</span></a>
			<a class="btn_common01" id="batchReturnInput" href="javascript:void(0)" /><img src="/SRMC/dc/images/btnIcon/back.png" /><span>批量退回</span></a>
		</div>
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
        <th>超时状态</th>
        <th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
	  	<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="tr_id_${item[0].rulesId}">
		    <td ><input name="subBox" type="checkbox" value="${item[0].rulesId}"></td>
		    <td>${i.count }</td>
		    <td ><a href="<%=basePath%>rulesController/viewRule?rulesId=${item[0].rulesId}">${item[0].rulesCode}</a></td>
		    <td ><a href="<%=basePath%>rulesController/viewRule?rulesId=${item[0].rulesId}">${item[0].rulesName}</a></td>
		    <td >
		    	<jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="RULES_GRADES_CONFIG"></jsp:param>
					<jsp:param name="paramCode" value="${item[0].rulesGrade }"></jsp:param>
				</jsp:include> 
		    </td>
		    <td >${item[1].parentTypeName }/${item[1].typeName }</td>
		    <td >${item[2].orgName }</td>
            <td ><fmt:formatDate value="${item[3].createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td >
				<c:choose>
				    <c:when test="${item[3].timeoutStatus == 0}">
				       	未超时
				    </c:when>
				    <c:when test="${item[3].timeoutStatus == 1}">
				       	<i>已超时</i>
				    </c:when>
				 </c:choose>
			</td>
		    <td >
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/publish.png"><a href="javascript:releaseRemain('${item[0].rulesId}')">发布</a></span>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/back.png"><a class="fancybox_Return" id="ba_${item[0].rulesId}" value="${item[0].rulesId}" href="javascript:void(0)">退回</a></span>
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
<script type="text/javascript" >var basePath = "<%=basePath%>rulesController";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script src="/SRMC/dc/js/checkboxList.js"></script>
<script type="text/javascript">
$(function(){

});
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	//制度退回初始化
	returnRemain();
	//批量退回初始化
    batchReturnInput();
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
        batchReturnInput();
    });
    var $subBox = $("input[name='subBox']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
        batchReturnInput();
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
	document.forms[0].action="<%=basePath%>rulesController/remain";
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

//制度发布
function ajaxRelease(rulesIds){
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
//批量发布
function batchRelease(){
	var $subBoxChecks = $("input[name='subBox']:checked");
	if($subBoxChecks.length == 0){
		alert("请至少选中一项制度");
		return;
	}
	var rulesIds = '';
	$.each($subBoxChecks,function(k,v){
		if(k == 0){
			rulesIds += $(v).val();
		}else{
			rulesIds += ","+$(v).val();
		}
	});
	
	ajaxRelease(rulesIds);
}

//单个发布
function releaseRemain(rulesIds){
	ajaxRelease(rulesIds);
}
</script>
</body>
</html>
