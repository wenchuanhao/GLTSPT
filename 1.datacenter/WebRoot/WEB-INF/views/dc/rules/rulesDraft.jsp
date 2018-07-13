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
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
		<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
		<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />
	</div>
	<form name="form" id="pageForm" method="post"  >
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${rulesForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${rulesForm.pageSize}" id="pageSize"	name="pageSize"/>	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
	    <th width="5%" align="right">制度名称：</th>
	      <td width="30%"><input id="rulesName" name="rulesName" value="${rulesForm.rulesName }"  type="text"  placeholder="请填写制度名称" class="text01" style="width:50%;"  /></td> 
  	</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/mine';document.forms[0].submit();">我的制度<em>(${mineCount })</em></li>
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/draft';document.forms[0].submit();" class="current">草稿箱<em>(${ITEMPAGE.total })</em></li>
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/remind';document.forms[0].submit();">提醒<em>(${remindCount })</em></li>
    	</ul>
    	<div class="otherButtons r">
			<a class="btn_common01" href="javascript:batchDel()" /><img src="/SRMC/dc/images/btnIcon/del.png" /><span>删 除</span></a>
		</div>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	    <th><input id="checkAll" type="checkbox"></th>
	    <th>序号</th>
	    <th>制度名称</th>
	    <th>制度等级</th>
	    <th>制度分类</th>
	    <th>到达时间</th>
	    <th>操作</th>
	  </tr>
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
	  	<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="tr_id_${item[0]}">
		    <td ><input name="subBox" type="checkbox" value="${item[0]}"></td>
		    <td>${i.count }</td>
		    <td ><a href="<%=basePath%>rulesController/add?rulesId=${item[0]}&fromDraft=1">${item[2]}</a></td>
		    <td >
		    	<jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="RULES_GRADES_CONFIG"></jsp:param>
					<jsp:param name="paramCode" value="${item[3] }"></jsp:param>
				</jsp:include> 
		    </td>
		    <td >${item[13] }/${item[11] }</td>
		    <td >${item[6]}</td>
		    <td >
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png" /><a href="<%=basePath%>rulesController/add?rulesId=${item[0]}&fromDraft=1">编辑</a></span> 
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/publish.png" /><a href="javascript:submitCheck('${item[0]}')" >提交</a></span>  
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png" /><a href="javascript:delDraft('${item[0]}')" >删除</a></span>
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
<script type="text/javascript" >var basePath = "<%=basePath%>rulesController";</script>
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
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>rulesController/draft";
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#rulesName").val("");
	ev_search();
}

function ajaxDel(rulesIds){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>rulesController/delDraft",
        data:"rulesId=" + rulesIds,
        success:function(data){
        	if(data == 1){
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
		alert("请至少选中一项草稿");
		return;
	}
	if(confirm("确定批量删除"+$subBoxChecks.length+"项草稿么？")){
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
function delDraft(id){
	if(confirm("确定删除该草稿么？")){
		ajaxDel(id);
	}
}

function doSubmitDraft(id){
	jQuery.ajax({
	    type:"POST",
	    async:false,
	    url:"<%=basePath%>rulesController/submitDraft",
	    data:"rulesId=" + id,
	    success:function(data){
	    	//alert("提交成功！");
	   		if(data == 1){
        		ev_search();
        	}else{
        		alert("提交失败！");
        	}
	    },
	    error:function(){
	        alert("提交失败！");
	    }
	});
}

//草稿提交
function submitCheck(id){
	if(confirm("确定提交该草稿么？")){
		jQuery.ajax({
	        type:"POST",
	        async:false,
	        url:"<%=basePath%>rulesController/checkDraft",
	        data:"rulesId=" + id,
	        success:function(data){
	       		 //	校验信息
	        	if(!checkSubmit($.parseJSON(data.replace("\r\n", "\\r\\n")))){
	        		return;
	        	}else{
	        		doSubmitDraft(id);
	        	}
	        },
	        error:function(){
	            alert("提交失败！");
	        }
	    });
	}
}


function checkSubmit(data){

	if(data == 0){
		alert("制度名称已存在！");
		return false;
	}
	//制度名称
	if( $.trim(data.rulesInfo.rulesName)=="" ){
		alert("请填写制度名称");
		return false;
	}
	if( data.rulesInfo.rulesName.length > 30 ){
		alert("制度名称不得超过30个字");
		return false;
	}
	//制度等级
	if( data.rulesInfo.rulesGrade =="" ){
		alert("请选择制度等级" );
		return false;
	}
	//制度分类
	if( data.rulesInfo.rulesTypeId =="" ){
		alert("请选择制度分类" );
		return false;
	}
	//制度文件
	if( data.zdwj <= 0){
		alert("请上传一个制度文件" );
		return false;
	}
	if( data.zdwj > 1){
		alert("制度文件不得超过1个" );
		return false;
	}
	//基地级、跨部门级制度必须上传
	if($.trim(data.rulesInfo.rulesGrade) != "BMJ" && data.fbyj <= 0 ){
		alert("请上传发布依据" );
		return false;
	}
	return true;
}
</script>
</body>
</html>
