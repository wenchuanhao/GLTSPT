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
<script type="text/javascript"
	src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<!--公共JS -->
<script type="text/javascript">var basePath = "<%=basePath%>command";var baseURL = "<%=basePath%>";</script>
<script type="text/javascript" src="/SRMC/dc/command/js/common.js"></script>
<!-- 	表单异步提交 -->
<script src="/SRMC/rmpb/js/jquery.form.js"></script>
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet"
	href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css" />
	<script src="/SRMC/rmpb/js/isIe.js"></script>
<script type="text/javascript" src="/SRMC/dc/command/js/zl.js"></script>
<script type="text/javascript"
	src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<jsp:include page="../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="PROJECT_STATUS,PROJECT_TYPE"></jsp:param>
</jsp:include>
</head>

<body>

	<div class="gl_import_m">
		<div class="searchCondition">
			<span class="searchCondition_header">发起工程指令</span>
		</div>
		
		<form action="" method="post" id="form1">
			<table width="99%" border="0" cellspacing="0" cellpadding="0"
				class="editTable reeditTable" align="center" id="table01">
				<input type="hidden" name="worksId" id="worksId"
					value="${vo.worksId}" />
				<input type="hidden" name="worksType" id="worksType" value="3" />
				<tr>
			        <td align="center" colspan="2">
				      <div class="contract_head">
					     <h1 class="contract_title">B类工作指令</h1>
				      </div>
			        </td>
	    </tr>
				<tr>
					<th width="20%" align="right"><b>*</b>至：</th>
					<td><input name="supportorgName" id="supportorgName"
						onfocus="autoCompletes(this)" type="text" class="text01"
						maxlength="60" style="width:500px;" placeholder="请输入支撑单位"
						value="${vo.supportorgName}" /> <input type="hidden"
						name="supportorgId" id="supportorgId" />
						<a onclick="selectSupportorgName()" style="cursor: pointer;color: blue;" title="选择支撑单位">选择</a>
						</td>
				</tr>
				<tr>
					<th width="20%" align="right"><b>*</b>指令涉及合同名称</th>
					<td><input name="contractName" id="contractName" type="text" <c:if test="${not empty vo.contractName }">readonly="readonly"</c:if>
						class="text01"  style="width:500px;"onfocus="autoCompletesProject(this)"
						placeholder="请输入指令涉及合同名称" value="${vo.contractName}" />
						</td>
				</tr>
				<tr>
					<th align="right"><b>*</b>指令涉及合同编号</th>
					<td><input name="contractCode" id="contractCode" <c:if test="${not empty vo.contractCode }">readonly="readonly"</c:if>
						onfocus="autoCompletesProject(this)" value="${vo.contractCode}"
						type="text" class="text01" style="width:500px;"
						placeholder="请输入指令涉及合同编号">
						</td>
				</tr>
				<tr>
					<th align="right"><b>*</b>施工单位：</th>
					<td><input name="constructionName" id="constructionName"
						value="${vo.constructionName}" type="text" class="text01"
						style="width:500px;" placeholder="请输入施工单位" /> <input type="hidden"
						name="constructionId" id="constructionId" /></td>
				</tr>
				<tr>
					<th align="right">工作指令信息</th>
				</tr>
				<tr>
					<th width="20%" align="right">部位：</th>
					<td>
						<textarea rows="3"  class="textArea01" style="width:495px;min-height: 50px;overflow: hidden;" placeholder="请输入部位" id="orgName" name="orgName">${vo.orgName}</textarea>
					</td>
				</tr>
				<tr>
					<th align="right"><b>*</b>内容：</th>
					<td>
						<textarea rows="3"  class="textArea01" style="width:495px;min-height: 50px;overflow: hidden;" placeholder="请输入内容" id="worksContent" name="worksContent">${vo.worksContent}</textarea>	
					</td>
				</tr>
				<tr>
					<th align="right"><b>*</b>原因：</th>
					<td>
						<textarea rows="3"  class="textArea01" style="width:495px;min-height: 50px;overflow: hidden;" placeholder="请输入原因" id="reason" name="reason">${vo.reason}</textarea>
					</td>
				</tr>
				<tr>
					<th colspan="4" align="center" height="50"><input name=""
						type="button" class="btn_common02" onclick="ev_save()" value="保 存" />
						<input name="" type="button" class="btn_common04"
						onclick="javascript:window.history.back();"
						value="取 消" /></th>
				</tr>

			</table>
		</form>
	</div>

</body>
</html>

<script type="text/javascript">
$(document).ready(function(){
	$("#worksContent").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 180){
	    	$(this).val($(this).val().substring(0,180));
	   }
	});
	$("#reason").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 180){
	    	$(this).val($(this).val().substring(0,180));
	   }
	});
	$("#orgName").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 130){
	    	$(this).val($(this).val().substring(0,130));
	   }
	});
});
function ev_save(){
if(!ev_required_item()){
		return false;
	}
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>command/zlbSaveOrUpdate",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	window.location.href = "<%=basePath%>command/list";
        },
        error:function () {
        	alert("保存失败!");
        }
    });
}

function ev_required_item(){
		var k = 0;
		var b = true;
		
		if(bindChange("#supportorgName",100,"支撑单位不得超过100个字")){
			return false;
		}
		if(bindChange("#contractName",100,"指令涉及合同名称不得超过100个字")){
			return false;
		}
		if(bindChange("#contractCode",36,"指令涉及合同编号不得超过36个字")){
			return false;
		}
		if(bindChange("#constructionName",100,"施工单位不得超过100个字")){
			return false;
		}
		if(bindChange("#orgName",130,"部位不得超过130个字")){
			return false;
		}
		if(bindChange("#worksContent",180,"工程指令内容不得超过180个字")){
			return false;
		}
		if(bindChange("#reason",180,"原因不得超过180个字")){
			return false;
		}
		//支撑单位
		var supportorgName = $("#supportorgName");
		if(supportorgName.val() == ""){
       		k++;bindC(supportorgName);			
		}
		//工程名称
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
		var worksContent = $("#worksContent");
		if(worksContent.val() == ""){
       		k++;bindC(worksContent);
       	}
       		
	
      	//原因
    	var reason = $("#reason");
		if(reason.val() == ""){
       		k++;bindC(reason);			
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