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
<style>
	#uploadifive-uploadFiles{
   		margin-left: 80%;
   		border: 0;		
	}
</style>
</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">数据录入</span></div>
<form action=""  method="post"  id="form1">
	<input type="hidden" name="datasourceSource" id="datasourceSource"/>
	<input type="hidden" name="errFlag" id="errFlag" value="0"/>
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
	    <th width="9%" align="right">数据月份：</th>
	    <td width="10%">
	    	<div class="date l" style="width: 163px;">
			    <input readonly="readonly" name="month" id="month" type="text"  placeholder="请您输入"  class="text02 l" value="${month}"  
		     		onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM',alwaysUseStartDate:true})" style="width:90%;" /><i></i> 
	    	</div>
	    </td>
	    <th width="9%" align="right">数据源名称：</th>
	    <td width="380px">
	    	<select onchange="changeBusTypesByList(this,'#datasourceId')" class="ui-select" id="parentDatasourceId" name="parentDatasourceId" style="width:180px;">
				<option <c:if test="${empty cooperationForm.parentDatasourceId }">selected="selected"</c:if> value="">请选择</option>
	    		<c:forEach items="${parentDatasourceList}" var="item" varStatus="i">
    				<option ${cooperationForm.parentDatasourceId ==item.datasourceId ? "selected=\"selected\"":null}  value="${item.datasourceId}">${item.datasourceName}</option>
				</c:forEach>
	        </select>
	    	<select onchange="changeDatasourceSource(this,'#import_enter_input')" class="ui-select" id="datasourceId" name="datasourceId" style="width:180px;">
				<option <c:if test="${empty cooperationForm.datasourceId }">selected="selected"</c:if> value="">请选择</option>
	        </select>
	    </td>
	    <td align="left"><input name="" id="import_enter_input" onclick="toggleDivs('#datasourceId')" type="button" class="btn_search" value="导入" /></td>
	</tr>
	</table>
  	<div class="ge01"></div>
	<!--   	导入方式 -->
	<div id="import_div" style="display: none;">
	  	<div class="tabpages">
	    	<ul class="l">
	      		<li class="current">文件导入</li>
	    	</ul>
	  	</div>
	    <!-- 附件临时ID -->
		<input type="hidden" name="fileTempId" id="fileTempId" value="${fileTempId}"/>
		<input type="hidden" name="datasourceRecordsId" id="datasourceRecordsId"/>
	  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable" align="center" id="table01">
		  	<tr id="fi_box_tr_1" style="align:center;width:100%">
			    <th style="width:30%;" align="right"><b>*</b>文件上传：<br/></th>
			    <td style="width:20%;">
			    	<div class="text01" style="width:500px;" id="fi_box_div_1" value="9">
				    	<input name="fileName" id="uploadFiles" type="file" class="btn_upload" style="display: none">
		            </div>
			    </td>
			    <td>
			    	<a onclick="downloadtTemplate()" href="javascript:void(0)"><b>模板下载</b></a>
			    </td>
		    </tr>
		   	<tr>
			   		<th colspan="3" align="center">
						<iframe id="iframepage" frameborder="0" scrolling="yes" marginheight="0" marginwidth="0"></iframe>
			   		</th>
			</tr>
			<tr>
		   		<th colspan="3" align="center">
		   			<input name="" type="button" class="btn_common02" onclick="ev_save(2)" value="提 交" /> 
		   		 	<input name="" type="button" class="btn_common01" onclick="ev_save(1)" value="暂 存" />
		   		 	<input name="" type="button" class="btn_common01" onclick="ev_cancel();" value="重 置" />
		   		</th>
		   </tr>    
	  	</table>
	     <div class="ge01"></div>
	</div>  
	<!--      录入方式 -->
	<div id="enter_div" style="display: none;">
		<div class="tabpages">
	    	<ul class="l">
	      		<li class="current">数据录入</li>
	    	</ul>
	  	</div>
	  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable" align="center" id="table_of_enter">

	  	</table>
	     <div class="ge01"></div>
	</div>
	<div><br/></div>
</form>
</div>
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/rmpb/js/jquery.form.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" >var tempPath = "<%=basePath%>";var basePath = "<%=basePath%>copperationController";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
	<!--文件上传样式，js -->
	<!-- <link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script> -->
	
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadifive/uploadifive.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/jquery.uploadifive.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/uploadcommon.js"></script>
	
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<jsp:include page="../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="DATASOURCE_TYPE"></jsp:param>
</jsp:include>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/common.js"></script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/ex_import.js"></script> 
<script type="text/javascript">
var sonList = $.parseJSON('${sonList}'.replace("\r\n", "\\r\\n"));
$(function(){

});
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	//上传文件初始化
	setConfig(tempPath+"rulesController/uploadFile");
});

//提交前校验
function checkSubmit(){
	//校验输入月份和选择月份是否一致
	var flag = true;
	var month = $("#month").val();
	//数据来源
	var datasourceSource = $("#datasourceSource").val();
	//录入
	if(datasourceSource == "1"){
		$("input[name='values']").each(
			function(){
				if($.trim($(this).val())==""){
					alert("录入数据不能为空" );
					flag = false;
					return false;
				}
			}
		);
		//数据不为空，校验合法性
		if(flag){
			var datasourceId = $("#datasourceId").val();
			//异步校验录入数据
			$("#form1").ajaxSubmit({
				type:"POST",
				async:false,
				url: basePath+"/checkEnter",
				data:"datasourceId=" + datasourceId,
				dataType:"json",
				success:function(result){
					if(result.code == "1"){
						flag = false;
						alert(result.message);
					}
				},
				error:function(){
				}
			});
		}
		
	//导入
	}else if(datasourceSource == "2"){
		if( $.trim($("#datasourceRecordsId").val())=="" ){
			alert("导入数据不能为空" );
			flag = false;
		}
		//是否有校验错误提示
		if($("#errFlag").val() == "1"){
			alert("导入数据有误，请核对后重新导入！" );
			flag = false;
		}
	}
	return flag;
}

//提交查询
function ev_save(status){

	if(!checkSubmit()){
		return;
	}
	$("#form1").attr("action","<%=basePath%>copperationController/submit?status="+status);
	$("#form1").submit();
}

/*附件下载*/
function downloadtTemplate(){
	var datasourceId = $("#datasourceId").val();
	window.location.href="<%=basePath%>copperationController/downloadtTemplate?datasourceId="+datasourceId;
}
//导入取消
function ev_cancel(){
	var datasourceRecordsId = $("#datasourceRecordsId").val();
	//异步取消及删除记录
	jQuery.ajax({
		type:"POST",
		async:true,
		url: basePath+"/cancel",
		data:"datasourceRecordsId=" + datasourceRecordsId,
		dataType:"json",
		success:function(result){
		},
		error:function(){
		}
	});
	//清空记录
	$("#datasourceRecordsId").val("");
	document.getElementById("iframepage").src="";
	document.getElementById("iframepage").onload=function(){
		var ifm= document.getElementById("iframepage");   
		ifm.height = '150px';
		ifm.width = '150px';//subWeb.body.scrollWidth;
	};
}

//录入取消
function ev_cancel2(){
	$("input[name='values']").val('');
}

</script>
</body>
</html>
