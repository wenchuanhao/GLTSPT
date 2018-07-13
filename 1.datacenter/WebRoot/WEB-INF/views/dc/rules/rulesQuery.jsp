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
	<jsp:include flush="true" page="include/queryForChart.jsp"></jsp:include>
  	<div class="tabpages">
    	<ul class="l">
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/query';document.forms[0].submit();" class="current">明细清单<em>(${ITEMPAGE.total })</em></li>
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/charts';document.forms[0].submit();">统计列表</li>
    	</ul>
    	<div class="otherButtons r">
    		<c:if test="${userRoles != '3' }">
		    <a class="btn_common01" id="modifyRulesInput" href="#inline2" /><img src="/SRMC/dc/images/btnIcon/modify.png" /><span>修订</span></a>
		    <a class="btn_common01" id="uploadFileInput" href="javascript:void(0)" /><img src="/SRMC/dc/images/btnIcon/upload.png" /><span>上传文档</span></a>
		    <a class="btn_common01" id="batchRepealInput" href="javascript:void(0)" /><img src="/SRMC/dc/images/btnIcon/invalid.png" /><span>批量废止</span></a>
    		</c:if>
		    <a class="btn_common01" href="javascript:batchExport()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导出</span></a>
    	</div>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	    <th><input id="checkAll" type="checkbox" onclick="ev_checked()"></th>
	    <th>序号</th>
	    <th>制度编号</th>
        <th>制度名称</th>
        <th>制度等级</th>
        <th>制度分类</th>
        <th>状态	</th>
        <th>牵头部门</th>
        <th>创建时间</th>
        <th>发布时间</th>
        <c:if test="${rulesForm.status==4}">
	   		<th>废止时间</th>
	    </c:if>
        <th>相关文档</th>
        <th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
	  	<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="tr_id_${item[0].rulesId}">
		    <td ><input name="subBox" type="checkbox" val="${item[0].status }" value="${item[0].rulesId}"></td>
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
		    <td >
					<!--//   0:已删除;/ 1.草稿；2.已提交审核（审核中）；3.已发布；4.已废止；5.已修订；6;已退回; -->
	              <c:choose>
				    <c:when test="${item[0].status == 0}">
				       	已删除
				    </c:when>
				    <c:when test="${item[0].status == 1}">
				       	草稿中
				    </c:when>
				    <c:when test="${item[0].status == 2}">
				       	<em>审核中</em>
				    </c:when>
				    <c:when test="${item[0].status == 3}">
				       	<small>已发布</small>
				    </c:when>
				    <c:when test="${item[0].status == 4}">
				       	已废止
				    </c:when>
				    <c:when test="${item[0].status == 5}">
				       	已修订
				    </c:when>
				    <c:when test="${item[0].status == 6}">
				       	已退回
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
			</td>
		    <td >${item[2].orgName }</td>
            <td ><fmt:formatDate value="${item[0].createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		    <td >
		    	<c:choose>
				    <c:when test="${not empty item[0].rulesFB}">
				    	<fmt:formatDate value="${item[0].rulesFB}" pattern="yyyy-MM-dd HH:mm:ss"/>
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
		    </td>
		    <c:if test="${rulesForm.status==4}">
			    <td >
			    	<c:choose>
					    <c:when test="${not empty item[0].rulesFZ}">
					    	<fmt:formatDate value="${item[0].rulesFZ}" pattern="yyyy-MM-dd HH:mm:ss"/>
					    </c:when>
					    <c:otherwise>
					    	-
					    </c:otherwise>
					</c:choose>
			    </td>
		    </c:if>
		    <td >
		    	<b><a class="fancybox_viewFile" id="upload_${item[0].rulesId}" value="${item[0].rulesId}" href="javascript:void(0)">${item[0].countFile }</a></b> 
		   	</td>
		   	<td >
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a href="<%=basePath%>rulesController/viewRule?rulesId=${item[0].rulesId}">查看</a></span>
		    	<c:choose>
				    <c:when test="${item[0].status == 3}">
				    	<c:if test="${userRoles != '3' }">
				    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/invalid.png"><a class="fancybox_Repeal" id="ia_${item[0].rulesId}" value="${item[0].rulesId}" href="javascript:void(0)">废止</a></span>
				    	</c:if>
				    </c:when>
				    <c:otherwise>
				    </c:otherwise>
				</c:choose>
		    </td>
		 </tr>
	   </c:forEach>
	</table>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
<jsp:include flush="true" page="include/dialog.jsp"></jsp:include>           
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
function ev_checked(){
	var checkAll = document.getElementById("checkAll");
	if(checkAll != null && checkAll.type=="checkbox"){
		var ids = document.getElementsByName("subBox");
		 if(ids != null){
			for(var i=0;i<ids.length;i++){
				var id = ids[i];
				if(checkAll.checked){
					id.checked=true;
				}else{
					id.checked=false;
				}
			}	 
		 }
	}
}

$(function(){
	
	//修订初始化
		$("#modifyRulesInput").fancybox({
			'modal':false,
			'overlayShow':true,
			'hideOnOverlayClick':false,
			'hideOnContentClick':false,
			'enableEscapeButton':false,
			'showCloseButton':false,
			'centerOnScroll':true,
			'autoScale':false,
			'width':540,
			'height':360,
			'onStart' : function(current, previous) {
				var $subBoxChecks = $("input[name='subBox']:checked");
				if($subBoxChecks.length != 1){
					alert("请选中一项制度");
					return false;
				}
				
				if($($subBoxChecks[0]).attr("val") != 3){
					alert("只能修订已发布制度");
					return false;
				}
				$("#dialog_input").val($($subBoxChecks[0]).val());
			}
		});
});
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	repealRules();//废止
	viewFileUpload();//查看相关文档
	uploadFileInput();//上传文档
	batchRepealInput();//批量废止
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
        batchRepealInput();//批量废止
    });
    var $subBox = $("input[name='subBox']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
        //上传文档初始化
        uploadFileInput();
        batchRepealInput();//批量废止
    });
    
    //牵头部门
	var obj1 = $('#sel_01').data('ui-select');
	obj1.onClick = function(value) {
		 $("#leadDepId_input").val(value);
	}
    //制度状态
	var obj4 = $('#sel_04').data('ui-select');
	obj4.onClick = function(value) {
		 $("#status_input").val(value);
	}
	//制度等级
	var obj3 = $('#sel_03').data('ui-select');
	obj3.onClick = function(value) {
		 $("#rulesGrade_input").val(value);
	}
    
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>rulesController/query";
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
	$("#handelBeginDate").val("");
	$("#handelEndDate").val("");
	ev_search();
}

//导出制度详情列表
function batchExport(){
	var ids = document.getElementsByName("subBox");
	var subBoxValue = "";
    if(ids != null){
		for(var i=0;i<ids.length;i++){
			var id = ids[i];
			if(id.checked){
				subBoxValue += "," + id.value;
			}else{
			}
		}	 
	 }
	 $("#subBoxValue").val(subBoxValue);
	 if(subBoxValue == ""){
	 	alert("请至少选择一条制度记录！");
	 }else{
	 	document.forms[0].action="<%=basePath%>rulesController/exportFile?source=mxqd";
		document.forms[0].submit();
		document.forms[0].action="<%=basePath%>rulesController/query";
	 }
}
</script>
</body>
</html>
