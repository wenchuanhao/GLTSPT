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
<script src="/SRMC/rmpb/js/isIe.js"></script>
<script type="text/javascript" src="/SRMC/dc/command/js/zl.js"></script>
<jsp:include page="../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="PROJECT_STATUS,PROJECT_TYPE"></jsp:param>
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
		<input type="hidden" name="worksType" id="worksType" value="4"/>
		<tr>
			<td align="center" colspan="2">
				<div class="contract_head">
					<h1 class="contract_title">C类工作指令</h1>
				</div>
			</td>
		</tr>
		<tr>
		    <th width="20%" align="right"><b>*</b>至：</th>
		    <td>
		    <input onfocus="onfocuses(this)"  onkeyup="clean(this)" onblur="onblurs(this)"
		     name="supportorgName"  id="supportorgName"  type="text" class="text01"  style="width:500px;" placeholder="请输入支撑单位"  value="${vo.supportorgName}"/>
		    	<input type="hidden" name="supportorgId" value="${vo.supportorgId}" id="supportorgId"/>
			<a onclick="selectSupportorgName()" style="cursor: pointer;color: blue;" title="选择支撑单位">选择</a>
		    </td>
	    </tr>
		<tr>
		    <th width="20%" align="right"><b>*</b>合同名称：</th>
		    <td>
		    <input name="contractName" <c:if test="${not empty vo.contractName }">readonly="readonly"</c:if> onkeyup="javascript:$(this).prop('title',$(this).val())"  onfocus="autoCompletesProject(this)" id="contractName"  type="text" class="text01" style="width:500px;<c:if test="${not empty  vo.contractName}">background-color: #f5f5f5;</c:if>" placeholder="请输入合同名称"  value="${vo.contractName}"/>
		    </td>
	    </tr>
		<tr>
		    <th align="right"><b>*</b>合同编号：</th>
		    <td>
		    <input  name="contractCode" <c:if test="${not empty vo.contractCode }">readonly="readonly"</c:if>  id= "contractCode" onfocus="autoCompletesProject(this)" value="${vo.contractCode}" type="text" class="text01" style="width:500px;<c:if test="${not empty  vo.contractCode}">background-color: #f5f5f5;</c:if>" placeholder="请输入合同编号" >
		    </td>		    		    
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>施工单位：</th>
		    <td>
		    <input name="constructionName"  id="constructionName" value="${vo.constructionName}" type="text" class="text01" style="width:500px;" placeholder="请输入施工单位"/>
		    <input type="hidden" name="constructionId" value="${vo.constructionId}" id="constructionId"/>
		    </td>		 
	    </tr>
	    <tr>
		    <th align="right">工作指令内容：</th>
		    <td colspan="3"><textarea rows="5" cols="20" class="textArea01" style="width:89.4%;overflow: hidden;" placeholder="请填写工作指令内容" id="worksContent" name="worksContent">${vo.worksContent}</textarea></td>
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
window.onload = function() {
	$("#worksContent").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 1000){
	    	$(this).val($(this).val().substring(0,1000));
	   }
	});
}

function ev_save(){
	if(!ev_required_item()){
		return false;
	}
	
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>command/zlcSave",
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


/**
 * 必填项
 * @author weifei
 * @date Mar 8, 2012 10:02:40 AM
 */
function ev_required_item(){
		var k = 0;
		var b = true;
		
		if(bindChange("#supportorgName",100,"支撑单位不得超过100个字")){
			return false;
		}
		if(bindChange("#contractName",100,"合同名称不得超过100个字")){
			return false;
		}
		if(bindChange("#contractCode",36,"合同编号不得超过36个字")){
			return false;
		}
		if(bindChange("#constructionName",40,"施工单位不得超过40个字")){
			return false;
		}
		if(bindChange("#worksContent",1000,"工作指令内容不得超过1000个字")){
			return false;
		}
			//支撑单位
		var supportorgName = $("#supportorgName");
		if(supportorgName.val() == ""){
       		k++;bindC(supportorgName);			
		}
		//合同名称
		var contractName = $("#contractName");
		if(contractName.val() == ""){
       		k++;bindC(contractName);			
		}
		//合同编号
		var contractCode = $("#contractCode");
		if(contractCode.val() == ""){
       		k++;bindC(contractCode);			
		}
		//施工单位
		var constructionName = $("#constructionName");
		if(constructionName.val() == ""){
       		k++;bindC(constructionName);			
		}
		//内容
// 		var worksContent = $("#worksContent");
// 		if(worksContent.val() == ""){
//        		k++;bindC(worksContent);			
// 		}
      	
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