<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
	String baseUrl = request.getContextPath(); 
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
 width:10em;
 overflow: hidden; 
 text-overflow: ellipsis;
 white-space:nowrap; 
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
	<input type="hidden" value="${command.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${command.pageSize}" id="pageSize"	name="pageSize"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">文件类型：</th>
	    <td width="30%">
	    	<select id="commandType" name="commandType" class="ui-select" style="width:200px;" defaultValue="${command.commandType }"  dictType="COMMAND_TYPE"/>
		</td> 
  		
  		<th width="9%" align="right">文件编号：</th>
	    <td width="30%">
	    	<input id="commandNum" name="commandNum" value="${command.commandNum }"  type="text"  placeholder="请填写文件编号" class="text01" style="width:195px;"  />
		</td> 
  	  		
	</tr>
	
	<tr>
		<th width="9%" align="right">发起人：</th>
  		<td width="30%">
	    	<input id="launchUsername" name="launchUsername"  value="${command.launchUsername }"  type="text"  placeholder="请填写发起人" class="text01" style="width:195px;"  />
		</td> 
  		<th width="9%" align="right">状态：</th>
  		<td width="30%">
	    	<select id="commandStatus" name="commandStatus" class="ui-select" style="width:200px;" defaultValue="${command.commandStatus }"  dictType="COMMAND_STATUS"/>
		</td> 
	</tr>
	
	<tr>
  		<th width="9%" align="right">单位名称：</th>
	    <td width="30%">
	    	<input id="orgName" name="orgName"  value="${command.orgName}"  type="text"  placeholder="请填写单位名称" class="text01" style="width:195px;"  />
		</td> 
  		
  		<th width="9%" align="right">合同名称：</th>
	    <td width="30%">
		    <input 
	                  name="contractName" id= "contractName" value="${command.contractName}" type="text" class="text01" style="width:195px;" placeholder="请填写合同名称">
	                  <input type="hidden" value="${command.contractCode}" id="contractCode"	name="contractCode"/>	
		</td> 
		

  		
	</tr>  	 
	<tr>
		<th width="9%" align="right">发起时间：</th>
	    <td  width="30%">
	    	<div class="date l">
	    	<input readonly="readonly" name="launchBeginTime" id="launchBeginTime" type="text"  placeholder="请您输入"  class="text02 l" value="<fmt:formatDate value='${command.launchBeginTime}' pattern='yyyy-MM-dd'/>"
	                      onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" /><i></i>
	        </div>
		</td> 
		<th width="9%" align="right"></th>
	    <td  width="0%">
	    	<div class="date l">
	    	<input readonly="readonly" name="launchEndTime" id="launchEndTime" type="text"  placeholder="请您输入"  class="text02 l" value="<fmt:formatDate value='${command.launchEndTime}' pattern='yyyy-MM-dd'/>"
	                      onclick="WdatePicker({minDate:'#F{$dp.$D(\'launchBeginTime\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td> 
	</tr>
	
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >文件列表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
   			<a class="btn_common01" href="javascript:void(0)" id="launchCommand" /><img src="/SRMC/dc/images/btnIcon/add.png" /><span>发起</span></a>
    		<a class="btn_common01" href="javascript:ev_export()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0"launchCommand class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox"></th>
	  		<th>序号</th>
			<th>文件类型</th>
			<th>是否已被关联</th>
			<th>文件编号</th>
			<th>单位（施工/乙方/来文）名称</th>
			<th>合同名称</th>
			<th>摘要</th>
			<!-- <th>归档位置</th> -->
			<th>发起人</th>
			<th>发起时间</th>
			<th>状态</th>
			<th>操作</th>
	  		 
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" type="checkbox" value="${vo.commandId}"></td>
	  	  	<td>${i.count }</td>
		    <td>
				<jsp:include page="../../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="COMMAND_TYPE"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.commandType}"></jsp:param>
			    </jsp:include>
		    </td>
		    <td>
		    	<c:if test="${not empty  vo.commandMaterials}">
			    	<c:forEach items="${vo.commandMaterials }" var="cmvo" varStatus="j">
				    	${cmvo.commandInfo.commandNum } <br/>
			    	</c:forEach>
		    	</c:if>
		    	<c:if test="${empty  vo.commandMaterials}">
		    		未关联
		    	</c:if>
		    </td>
		    <td>
		    	<span>
		    			<a href="<%=baseUrl%><jsp:include page="../../sys/dict/include/dict_config.jsp">
						    <jsp:param name="paramTypeCode" value="COMMAND_TYPE"></jsp:param>
						    <jsp:param name="paramCode" value="${vo.commandType}"></jsp:param>
						    <jsp:param name="paramDesc" value="true"></jsp:param>
					    </jsp:include>Query?id=${vo.commandForid}">${vo.commandNum}</a>
				</span>
		    </td>
		    <td title="${vo.orgName}">
		    	<div class="shenglue">${vo.orgName}</div>
		    </td>
		    <td title="${vo.contractName}">
			    <div class="shenglue">${vo.contractName}</div>
		    </td>
		    
		   <td >
			    <div class="shenglue">${vo.digEst}</div>
		    </td> 
		    <!-- 未设置值 -->
		   <%-- <td>
			    ${vo.commandFolder.folderPosition}
		    </td>   --%>
		    <td>
			    ${vo.launchUsername}
		    </td>
		    <td><fmt:formatDate value="${vo.launchTime}" pattern="yyyy-MM-dd"/></td>
		    <td>
		    	<span>
			    	<a href="javascript:void(0)" id="flows_${vo.commandId }" class="fancybox_flows" value="${vo.commandId }">
				    <jsp:include page="../../sys/dict/include/dict_config.jsp">
					    <jsp:param name="paramTypeCode" value="COMMAND_STATUS"></jsp:param>
					    <jsp:param name="paramCode" value="${vo.commandStatus}"></jsp:param>
				    </jsp:include>
			    	</a>
		    	</span>
		    </td>
		    <td >
		    	<c:choose>
				    <c:when test="${vo.commandStatus ne 4}">
				    	<c:if test="${vo.commandStatus eq 1}">
					    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="<%=baseUrl%><jsp:include page="../../sys/dict/include/dict_config.jsp">
							    <jsp:param name="paramTypeCode" value="COMMAND_TYPE"></jsp:param>
							    <jsp:param name="paramCode" value="${vo.commandType}"></jsp:param>
							    <jsp:param name="paramDesc" value="true"></jsp:param>
						    </jsp:include>?id=${vo.commandForid}">编辑</a></span>
					    </c:if>
				    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="<%=baseUrl%><jsp:include page="../../sys/dict/include/dict_config.jsp">
						    <jsp:param name="paramTypeCode" value="COMMAND_TYPE"></jsp:param>
						    <jsp:param name="paramCode" value="${vo.commandType}"></jsp:param>
						    <jsp:param name="paramDesc" value="true"></jsp:param>
					    </jsp:include>View?id=${vo.commandForid}">打印</a></span>
<!--    已归档 -->
					    <c:if test="${not empty COMMAND_ADMIN && COMMAND_ADMIN eq 1 && vo.commandStatus eq 3}">
					    	<span>
					    		<img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png">
					    		<a href="javascript:void(0)" id="folder_${vo.commandId }" class="fancybox_folder" value="${vo.commandId }">编辑归档</a>
					    	</span>
					    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_undo('${vo.commandId}')">撤销归档</a></span>
					    </c:if>
					    <c:if test="${not empty COMMAND_ADMIN && COMMAND_ADMIN eq 1 && (vo.commandStatus eq 2 || vo.commandStatus eq 5)}">
					    	<span>
					    		<img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png">
					    		<a href="javascript:void(0)" id="folder_${vo.commandId }" class="fancybox_folder" value="${vo.commandId }">归档</a>
					    	</span>
				    	</c:if>
				    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${vo.commandId}')">作废</a></span>
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
           
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>command";var baseUrl = "<%=basePath%>";</script>
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/command/js/common.js"></script>
<!-- 页面弹窗 -->
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
<jsp:include page="../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="COMMAND_STATUS,COMMAND_TYPE"></jsp:param>
</jsp:include>
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
    //发起弹窗
    launchCommand();
    //流转详情弹窗
    viewFlows();
    //保存归档信息
    saveFolder();
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>command/list";
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#commandType").data("ui-select").val("");
	$("#commandNum").val("");
	$("#launchUsername").val("");
	$("#commandStatus").data("ui-select").val("");
	$("#orgName").val("");
	$("#contractName").val("");
	$("#digEst").val("");
	$("#contractCode").val("");
	$("#launchBeginTime").val("");
	$("#launchEndTime").val("");
	ev_search();
}

function ev_export(){
	
	var $subBoxChecks = $("input[name='subBox']:checked");
	var zbIds = "";
	$.each($subBoxChecks,function(k,v){
		if(k == 0){
			zbIds += "'"+$(v).val()+"'";
		}else{
			zbIds += ",'"+$(v).val()+"'";
		}
	});

	document.forms[0].action="<%=basePath%>command/exportFile?zbIds="+zbIds+"&key="+Math.random();
	document.forms[0].submit();
	document.forms[0].action="<%=basePath%>command/list";
}


function ev_add(){
	document.forms[0].action="<%=basePath%>command/add";
	document.forms[0].submit();
}

function ev_undo(id){
	if(confirm("指令撤销归档后可以重新再归档，确定对该指令进行撤销归档操作？")){
		jQuery.ajax({
	        type:"POST",
	        async:false,
	        url:"<%=basePath%>command/undoFolder",
	        data:"id=" + id,
	        success:function(data){
	        	if(data == 1){
	        		ev_search();
	        	}else{
	        		alert("撤销归档失败！");
	        	}
	        },
	        error:function(){
	            alert("撤销归档失败！");
	        }
	    });
	}
}

function ev_delete(id){
	if(confirm("指令作废后不能编辑、打印或归档操作，确定对该指令进行作废处理？")){
		jQuery.ajax({
	        type:"POST",
	        async:false,
	        url:"<%=basePath%>command/del",
	        data:"id=" + id,
	        success:function(data){
	        	if(data == 1){
	        		ev_search();
	        	}else{
	        		alert("指令作废失败！");
	        	}
	        },
	        error:function(){
	            alert("指令作废失败！");
	        }
	    });
	}
}
</script>
</body>
</html>
