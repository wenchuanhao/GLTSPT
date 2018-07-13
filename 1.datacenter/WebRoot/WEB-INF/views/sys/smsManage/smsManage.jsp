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
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span></div>
	<form name="form" id="pageForm" method="post"  >
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${form.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${form.pageSize}" id="pageSize"	name="pageSize"/>	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
	    <th width="10%" align="right">短信组名称：</th>
	      <td width="30%"><input id="smsGroupNmae" name="smsGroupNmae" value="${form.smsGroupNmae }"  type="text"  placeholder="请填写数据类型名称" class="text01" style="width:100%;"  /></td> 
	    <%-- <th width="10%" align="right">数据类型编号：</th>
	      <td width="30%"><input id="parameterTypeCode" name="parameterTypeCode" value="${form.parameterTypeCode }"  type="text"  placeholder="请填写数据类型编号" class="text01" style="width:100%;"  /></td> --%> 
	    <td align="right" style="padding-right:5px;"><input name="" onclick="ev_search()" type="button" class="btn_search" value="查询" /></td> 
  	</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
      		<li onclick="" class="current">短信组列表<em>(${ITEMPAGE.total })</em></li>
    	</ul>
    	<div class="otherButtons r">
			<input name="" id="addSmsManage" onclick="window.location.href='<%=basePath%>sys/sms/addSmsManage'" type="button" class="btn_common01" value="新 增" />
			<input name="" onclick="batchDel()" type="button" class="btn_common01" value="删 除" />
			
		</div>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	    <th><input id="checkAll" type="checkbox"></th>
	    <th>序号</th>
	    <th>业务名称</th>
	    <th>短信组名称</th>
	    <th>发送内容</th>
	    <th>创建时间</th>
	    <th>操作</th>
	  </tr>
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
		  	<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="tr_id_${item.id}">
			    <td ><input name="subBox" type="checkbox" value="${item.id}"></td>
			    <td>${i.count }</td>
			    <td ><a class="fancybox_Dictedit" href="<%=basePath%>sys/sms/editSmsManage?smsId=${item.id}&type=2" id="tda_${item.id}" value="${item.id}">${item.pName}</a></td>
			    <td >${item.smsGroupNmae }</td>
			    <td >${item.content }</td>
			    <td ><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			    <td >
			    	<b><a class="fancybox_Dictedit" href="<%=basePath%>sys/sms/editSmsManage?smsId=${item.id}&type=1" id="ba_${item.id}" value="${item.id}">编辑</a></b> 
			    	<i><a href="javascript:delDict('${item.id}')" >删除</a></i>
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
<script type="text/javascript" >var basePath = "<%=basePath%>sys/dict";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	


			<!--文件上传样式，js -->
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script>

	<script type="text/javascript" src="/SRMC/dc/js/ex_import.js"></script> 
	
<script type="text/javascript">
$(function(){

});
 $(document).ready(function(){
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
    });
    var $subBox = $("input[name='subBox']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
    });
    setConfig("<%=basePath%>sys/dict/importParameterType");
//     iframeDialog("addDict", basePath + "/addDict");
//     编辑
//     dictEdit();
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>sys/sms/querySms";
	document.forms[0].submit();
}

function exportParameter(){
	document.forms[0].action="<%=basePath%>sys/dict/exportParameterType";
	document.forms[0].submit();
}

function ajaxDel(spId){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>sys/sms/delSmsManage",
        data:"ids=" + spId,
        success:function(data){
        	if(data == "1"){
        		ev_search();
//         		$("#tr_id_"+id).remove();
        	}else{
        		alert("删除失败！");
        	}
        },
        error:function(){
            alert("删除失败！");
        }
    });
}

//批量删除
function batchDel(){
	var $subBoxChecks = $("input[name='subBox']:checked");
	if($subBoxChecks.length == 0){
		alert("请至少选中一项参数数据");
		return;
	}
	if(confirm("确定批量删除"+$subBoxChecks.length+"项参数数据么（所属该类型的系统参数将全部删除）？")){
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


//删除草稿
function delDict(id){
	if(confirm("确定删除该参数类型么（所属该类型的系统参数将全部删除）？")){
		ajaxDel(id);
	}
}
</script>
</body>
</html>
