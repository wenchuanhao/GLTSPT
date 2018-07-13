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
	<input type="hidden" value="${zb.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${zb.pageSize}" id="pageSize"	name="pageSize"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">项目类型：</th>
	    <td width="30%">
	    	<select id="column14" name="column14" class="ui-select" style="width:200px;" defaultValue="${zb.column14 }"  dictType="PROJECT_TYPE"/>
		</td> 
  		
  		<th width="9%" align="right">周报名称：</th>
	    <td width="30%">
	    	<input id="column01" name="column01" value="${zb.column01 }"  type="text"  placeholder="请填周报名称" class="text01" style="width:195px;"  />
		</td> 
  		
	</tr>
	
	<tr>
		<th width="9%" align="right">项目编号：</th>
  		<td width="30%">
	    	<input id="column02" name="column02" onfocus="autoCompletesColumn02()" value="${zb.column02 }"  type="text"  placeholder="请填写项目编号" class="text01" style="width:195px;"  />
		</td> 
		<th width="9%" align="right">项目名称：</th>
	    <td width="30%">
	    	<input id="projectName" name="projectName" onfocus="autoCompletesProjectName()" value="${zb.projectName}"  type="text"  placeholder="请填写项目名称" class="text01" style="width:195px;"  />
		</td> 
	</tr>
	
	<tr>
  		<th width="9%" align="right">汇报人科室：</th>
	    <td width="30%">
			<select class="ui-select"  style="width:200px;;" id="column07Departmen" name="column07Departmen" >
			<option value="">请选择</option>
			<option ${zb.column07Departmen eq '0313f5eef20914f55aae79b647b79fdf' ? 'selected="selected"':''} value="0313f5eef20914f55aae79b647b79fdf">网络建设室</option>
			<option ${zb.column07Departmen eq '379725f12591b6c2dad22d72178917fd' ? 'selected="selected"':''} value="379725f12591b6c2dad22d72178917fd">项目管理室</option>
			<option ${zb.column07Departmen eq '48300b5a9c196bc8ab1c3e5398441c34' ? 'selected="selected"':''} value="48300b5a9c196bc8ab1c3e5398441c34">工程建设室</option>
			</select>	                  
		</td> 
  		
  		<th width="9%" align="right">汇报人：</th>
  		<td width="30%">
	    	<input id="column07" name="column07" onfocus="autoCompletesColumn07()" value="${zb.column07 }"  type="text"  placeholder="请填写汇报人" class="text01" style="width:195px;"  />
		</td> 
	</tr>
	
	<tr>
  		<th width="9%" align="right">事项状态：</th>
	    <td width="30%">
	    	<select id="column10" name="column10" class="ui-select" style="width:200px;" defaultValue="${zb.column10 }"  dictType="PROJECT_STATUS"/>
		</td> 
  		
  		<th width="9%" align="right">本周状态：</th>
	    <td width="30%">
	    	<select id="column11" name="column11" class="ui-select" style="width:200px;" defaultValue="${zb.column11 }"  dictType="PROJECT_STATUS"/>
		</td> 
  		
	</tr>  	 
	
	<tr>
		<th width="9%" align="right">汇报时间：</th>
	    <td width="30%">
	    	<div class="date l">
	    	<input readonly="readonly" name="column08" id="column08" type="text"  placeholder="请您输入"  class="text02 l" value="<fmt:formatDate value='${zb.column08}' pattern='yyyy-MM-dd'/>"
	                      onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" /><i></i>
	        </div>
		</td> 
		<th width="9%" align="right"></th>
	    <td width="30%">
	    	<div class="date l">
	    	<input readonly="readonly" name="column09" id="column09" type="text"  placeholder="请您输入"  class="text02 l" value="<fmt:formatDate value='${zb.column09}' pattern='yyyy-MM-dd'/>"
	                      onclick="WdatePicker({minDate:'#F{$dp.$D(\'column08\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" /><i></i>
	        </div>
		</td> 
	</tr>
	
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >审核列表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
   			<a class="btn_common01" href="javascript:ev_add()" /><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>
		</div>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox"></th>
	  		<th>序号</th>
			<th>项目类型</th>
			<th>项目编号</th>
			<th>项目名称</th>
			<th>周报名称</th>
			<th>事项状态</th>
			<th>本周状态</th>
			<th>汇报周期</th>
			<th>本周工作</th>
			<th>下周计划</th>
			<th>关键点</th>
			<th>存在问题</th>
			<th>审核人</th>
			<th>周报状态</th>
			<th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" type="checkbox" value="${vo.id}"></td>
	  	  	<td>${i.count }</td>
		    <td>
				<jsp:include page="../../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="PROJECT_TYPE"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.column14}"></jsp:param>
			    </jsp:include>
		    </td>
		    <td>
			    ${vo.column02}
		    </td>
		    <td title="${vo.projectName}">
			    <a style="color: #40baff;" target="_blank" href="<%=basePath%>zb/view?id=${vo.id}&code=${vo.projectId}"><div class="shenglue">${vo.projectName}</div></a>
		    </td>
		    <td title="${vo.column01}">
		    	<a style="color: #40baff;" href="<%=basePath%>zb/view?id=${vo.id}"><div class="shenglue">${vo.column01}</div></a>
		    </td>
			<td>
			    <jsp:include page="../../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="PROJECT_STATUS"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.column10}"></jsp:param>
			    </jsp:include>
			</td>
			<td>
				<jsp:include page="../../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="PROJECT_STATUS"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.column11}"></jsp:param>
			    </jsp:include>
			</td>
			<td><fmt:formatDate value="${vo.column08}" pattern="yyyy/MM/dd"/>--<fmt:formatDate value="${vo.column09}" pattern="yyyy/MM/dd"/></td>
			<td title="${vo.column03}"><div class="shenglue">${vo.column03}</div></td>
			<td title="${vo.column04}"><div class="shenglue">${vo.column04}</div></td>
			<td title="${vo.column05}"><div class="shenglue">${vo.column05}</div></td>
			<td title="${vo.column06}"><div class="shenglue">${vo.column06}</div></td>
			<td>${vo.auditUsername}</td>
		    <td>
		    	<jsp:include page="../../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="ZB_STATUS"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.zbStatus}"></jsp:param>
			    </jsp:include>
		    </td>
		    <td >
		    	<!-- 周报管理员 或者本人创建 -->
		    	<c:if test="${(not empty userRoles && userRoles eq 2 )  || vo.createUserId == sessionScope.VISITOR.userId }">
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="<%=basePath%>zb/add?id=${vo.id}&proType=6">编辑</a></span>
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${vo.id}')">删除</a></span>
		    	</c:if>
				<!-- 审核中 -->
		    	<c:if test="${vo.zbStatus eq 0 && vo.auditUserid == sessionScope.VISITOR.userId}">
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="javascript:pastRemain('${vo.id}')">通过</a></span>
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a class="fancybox_Return" id="ba_${vo.id}" value="${vo.id}" href="javascript:void(0)">不通过</a></span>
		    	</c:if>
		    	<c:if test="${vo.zbStatus ne 0 && vo.auditUserid == sessionScope.VISITOR.userId}">
		    		<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png">
		    		<a href="<%=basePath%>zb/view?id=${vo.id}">查看</a>
		    		</span>
		    	</c:if>
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
<script type="text/javascript" >var basePath = "<%=basePath%>zbremain";var baseUrl = "<%=basePath%>";</script>
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/zb/js/common.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>

<jsp:include page="../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="PROJECT_STATUS,PROJECT_TYPE,ZB_STATUS"></jsp:param>
</jsp:include>
<script type="text/javascript">
var sonList = $.parseJSON('${sonList}'.replace("\r\n", "\\r\\n"));
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
    returnZbRemain();
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>zbremain/list";
	document.forms[0].submit();
}

//新增
function ev_add(){
	document.forms[0].action="<%=basePath%>zb/add?proType=6";
	document.forms[0].submit();
}
//重置查询
function ev_reset(){
	$("#column14").data("ui-select").val("");
	$("#column01").val("");
	$("#column02").val("");
	$("#projectName").val("");
	$("#column07Departmen").data("ui-select").val("");
	$("#column07").val("");
	$("#column10").data("ui-select").val("");
	$("#column11").data("ui-select").val("");
	$("#column08").val("");
	$("#column09").val("");
	ev_search();
}
//删除
function ev_delete(id){
	if(confirm("确定删除？")){
		jQuery.ajax({
	        type:"POST",
	        async:true,
	        url:"<%=basePath%>zb/delete",
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

//数据源审核通过
function ajaxPast(id){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>zbremain/ajaxPast",
        data:"ids=" + id,
        success:function(data){
        	if(data == 1){
        		ev_search();
        	}else{
        		alert("审核通过失败！");
        	}
        },
        error:function(){
            alert("审核通过失败！");
        }
    });
}

//单个通过
function pastRemain(id){
	ajaxPast(id);
}
</script>
</body>
</html>
