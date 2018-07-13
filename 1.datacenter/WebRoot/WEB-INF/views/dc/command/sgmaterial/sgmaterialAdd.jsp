<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<title>南方基地管理提升平台</title>
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!--公共JS -->
	<script type="text/javascript" >var basePath = "<%=basePath%>command";var baseURL = "<%=basePath%>";</script>
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/command/js/zl.js"></script>
<jsp:include page="../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="PROJECT_STATUS,FILE_TYPE"></jsp:param>
</jsp:include>
</head>
  
<body>
<div class="gl_import_m">
	<div class="searchCondition">
		<span class="searchCondition_header">发起工程指令</span>
	</div>
<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<input type="hidden" name="worksId" id="worksId" value="${vo.worksId}"/>
		<input type="hidden" name="worksType" id="worksType" value="10"/>
		<tr>
			<td colspan="4">
			<div class="contract_head" align="center">
				<h1 class="contract_title">中国移动南方基地筹建办工程收文处理表<i>（施工）</i></h1>
			</div>
			</td>
		</tr>
		<tr>
		    <th align="right"><b>*</b>来文单位：</th>
		    <td>
		    <input name="constructionName"  id="constructionName" value="${vo.constructionName}" type="text" class="text01" style="width:300px;" placeholder="请输入来文单位"/>
		    <input type="hidden" name="constructionId" value="${vo.constructionId}" id="constructionId"/>
		    </td>		 

		    <th align="right"><b>*</b>关联合同：</th>
		    <td>
		    <input name="contractName"  id="contractName" <c:if test="${not empty vo.contractName }">readonly="readonly"</c:if> onfocus="autoCompletesProject(this)" value="${vo.contractName}" type="text" class="text01" style="width:300px;" placeholder="请输入关联合同"/>
		    <input type="hidden" name="contractCode" value="${vo.contractCode}" id="contractCode"/>
		    </td>		 
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>文件类型：</th>
		    <td>
		    	<select id="fileType" name="fileType" class="ui-select" style="width:306px;" defaultValue="${vo.fileType }"  dictType="FILE_TYPE"/>
		    </td>		 

		    <th align="right">收文时间：</th>
		    <td>
		    	<div class="date l" style="width: 100px;">
		    	<input readonly="readonly" name="receivedTime" id="receivedTime" type="text"  placeholder="请输入" onchange="clearColor(this)"  class="text02 l" value="<fmt:formatDate value='${vo.receivedTime}' pattern='yyyy-MM-dd'/>"
		                      onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width:190px;" /><i></i>
		        </div>
		    </td>		 
	    </tr>
	    <tr>
		    <th align="right">原件份数：</th>
		    <td>
		    <input name="fileNum"  id="fileNum" value="${vo.fileNum}" type="text" class="text01" style="width:300px;" placeholder="请输入原件份数" />
		    </td>		 
		    <th align="right">关联指令：</th>
		    <td>
		    <input name="commandNum"  id="commandNum" onfocus="autoCompletesCommandNum(this)" value="${vo.commandNum}" type="text" class="text01" style="width:300px;" placeholder="请输入关联指令" />
		    </td>		 
	    </tr>
	    <tr>
		    <th align="right">文件摘要：</th>
		    <td colspan="3"><textarea rows="5" cols="20" class="textArea01" style="width:89.4%" placeholder="请填写文件摘要" id="fileSummary" name="fileSummary">${vo.fileSummary}</textarea></td>
	    </tr>
	    <tr>
	   		<th colspan="4" align="center" height="50">
	   			<input name="" type="button" class="btn_common02" onclick="ev_save()" value="保 存" />
	   		 	<input name="" type="button" class="btn_common04" onclick="javascript:window.history.back();" value="取 消" />
	   		</th>
		 </tr>  
	</table>
</form>
</div>
</body>
</html>
<script type="text/javascript">
/**
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
 * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18  
 */
Date.prototype.format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

window.onload = function() {
	$("#fileSummary").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 480){
	    	$(this).val($(this).val().substring(0,480));
	   }
	});
}

function clearColor(target){
	$(target).parent().css("border-color","");
}


function ev_save(){
	if(!ev_required_item()){
		return false;
	}
	
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>command/sgmaterialSave",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result == 1){
	        	window.location.href = "<%=basePath%>command/list";
        	}else{
        		alert("保存失败!");
        	}
        },
        error:function () {
        	alert("保存失败!");
        }
    });
}

var obj;
$(document).ready(function(){
	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	
	obj = $('#fileType').data('ui-select');
});

/**
 * 必填项
 * @author weifei
 * @date Mar 8, 2012 10:02:40 AM
 */
function ev_required_item(){
		var k = 0;
		var b = true;
		
		if(bindChange("#constructionName",30,"来文单位不得超过30个字")){
			return false;
		}
		if(bindChange("#contractName",100,"关联合同不得超过100个字")){
			return false;
		}
		if(bindChange("#fileNum",9,"原件份数不得超过9位字")){
			return false;
		}
		if(bindChange("#fileSummary",480,"文件摘要不得超过480个字")){
			return false;
		}
		//来文单位
		var constructionName = $("#constructionName");
		if(constructionName.val() == ""){
       		k++;bindC(constructionName);			
		}
		//关联合同
		var contractName = $("#contractName");
		if(contractName.val() == ""){
       		k++;bindC(contractName);			
		}
		//文件类型
      	var fileType = $("#fileType");
        if($.trim($(fileType).val())=="" ){
         	k++;
       		$(fileType).parent().css("border-color","red");
       		$(fileType).bind("change", function(){
			  	$(this).parent().css("border-color","");
			});
         }
		//收文时间
// 		var receivedTime = $("#receivedTime");
// 		if(receivedTime.val() == ""){
//            		k++;
// 				$(receivedTime).parent().css("border-color","red");
//        			$(receivedTime).bind("change", function(){
// 			  		$(this).parent().css("border-color","");
// 			});
// 		}
		//原件份数填入的必须为份数
		var fileNum = $("#fileNum");
		var value = document.getElementById("fileNum").value;
		var reg=/^\d+$/; // 注意：若需要限制 0321 这种格式，则reg=/^[1-9]\d*$|^0$/;
		if(fileNum.val() != "" && reg.test(value)!=true){
			alert("请确认输入的只为数字");
			bindC(fileNum);
			return false;
		}
      	if(k > 0){
      		alert("带*号的为必填字段，请完善必填字段。");
      		b = false;
      	}
	    
		return b;
}

function bindC(field){
	$(field).css("border-color","red");
    $(field).bind("click", function(){
		$(this).css("border-color","");
	});
}
function bindChange(field,max,msg){
	if( $(field).val().length > max ){
		$(field).css("border-color","red");
	    $(field).bind("change", function(){
			$(this).css("border-color","");
		});
		alert(msg);
		return true;
	}
}
</script>