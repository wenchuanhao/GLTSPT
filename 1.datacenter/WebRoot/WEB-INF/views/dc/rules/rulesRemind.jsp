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
		<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
		<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />
	</div>
	<form name="form" id="pageForm" method="post"  >
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${rulesForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${rulesForm.pageSize}" id="pageSize"	name="pageSize"/>	
    <input type="hidden" name="status" id="status_input" value="${rulesForm.status}"/>
    <input type="hidden" name="rulesGrade" id="rulesGrade_input" value="${rulesForm.rulesGrade}"/>
	<input type="hidden" name="rulesTypeId" id="rulesTypeId_input" value="${rulesForm.rulesTypeId}"/>
	<input type="hidden" name="remindStatus" id="remindStatus_input" value="${rulesForm.remindStatus}"/>
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="7%" align="right">状态：</th>
	    <td width="17%">
	    	<select class="ui-select" id="sel_01" style="width:180px;">
		          <option value="">请选择</option>
		          <option ${rulesForm.status==3 ? "selected=\"selected\"":null} value="3">已发布</option>
		          <option ${rulesForm.status==2 ? "selected=\"selected\"":null} value="2">审核中</option>
	        </select>
	    </td>
	    <th width="7%" align="right">制度分类：</th>
	    <td width="17%">
          		<jsp:include flush="true" page="include/rulesTypeList.jsp"></jsp:include>
	    </td>
	 </tr>
	 <tr>
	 	<th width="7%" align="right">提醒类型：</th>
	    <td width="17%">
	        <select class="ui-select" id="sel_03"  style="width:180px;">
	    		<option value="">请选择</option>
		          <option ${rulesForm.remindStatus==2 ? "selected=\"selected\"":null} value="2">审核超时</option>
		          <option ${rulesForm.remindStatus==3 ? "selected=\"selected\"":null} value="3">更新超时</option>
	        </select>
	    </td>
	    <th width="7%" align="right">制度名称：</th>
	    
	    <td width="17%"><input id="rulesName" name="rulesName" value="${rulesForm.rulesName }"  type="text"  placeholder="请填写制度名称" class="text01" style="width:178px;"  /></td> 
  	
	 </tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/mine';document.forms[0].submit();">我的制度<em>(${mineCount })</em></li>
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/draft';document.forms[0].submit();">草稿箱<em>(${draftCount })</em></li>
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/remind';document.forms[0].submit();" class="current">提醒<em>(${ITEMPAGE.total })</em></li>
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
	    <th>状态</th>
	    <th>创建时间</th>
	    <th>发布时间</th>
	    <th>相关文档</th>
	    <th>提醒类型</th>
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
				       	审核中
				    </c:when>
				    <c:when test="${item[0].status == 3}">
				       	已发布
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
		    <td ><fmt:formatDate value="${item[0].createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		    <td >
		    	<c:choose>
				    <c:when test="${not empty item[2].handelTime}">
				    	<fmt:formatDate value="${item[2].handelTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
		    </td>
		    <td >
		    	<b><a class="fancybox_viewFile" id="upload_${item[0].rulesId}" value="${item[0].rulesId}" href="javascript:void(0)">${item[0].countFile }</a></b>
		    </td>
		    <td >
		    	 <c:choose>
				    <c:when test="${item[0].status == 3}">
				       	<i>更新超时</i>
				    </c:when>
				    <c:when test="${item[0].status == 2}">
				       	<i>审核超时</i>
				    </c:when>
				 </c:choose>
		    </td>
		    <td >
		    	<c:choose>
				    <c:when test="${item[0].status == 3}">
				    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a class="fancybox_Modal" id="modal_${item[0].rulesId}" value="${item[0].rulesId}" href="#inline2">修订</a></span> 
				    </c:when>
				    <c:otherwise>
				    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a href="<%=basePath%>rulesController/viewRule?rulesId=${item[0].rulesId}">查看</a></span>
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
 	//查看制度相关文档初始化
 	viewFileUpload();
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
    });
    var $subBox = $("input[name='subBox']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
    });
    //修订
	modalDialog();
    //制度状态
	var obj1 = $('#sel_01').data('ui-select');
	obj1.onClick = function(value) {
		 $("#status_input").val(value);
	}
	//制度等级
	var obj3 = $('#sel_03').data('ui-select');
	obj3.onClick = function(value) {
		 $("#remindStatus_input").val(value);
	}
    
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>rulesController/remind";
	document.forms[0].submit();
}
//重置查询
function ev_reset(){
	$("#status_input").val("");
	$("#rulesName").val("");
	$("#remindStatus_input").val("");
	$("#rulesTypeId_input").val("");
	ev_search();
}
</script>
</body>
</html>
