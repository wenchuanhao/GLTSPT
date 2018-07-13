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
	<jsp:param name="dict" value="PROJECT_STATUS,PROJECT_TYPE"></jsp:param>
</jsp:include>
<style>
    .word_5{ text-align:left; }
	.word_5 i{ font-style:normal;}
    .word_5 p{ text-indent:28px; line-height:28px;}
	.borderBtm{ display:inline-block; border:none; outline:none; border-bottom:1px solid #000; text-align:left; line-height:28px; text-indent:10px;}
    </style>
</head>
  
<body>
<div class="gl_import_m">
	<div class="searchCondition">
		<span class="searchCondition_header">发起工程指令</span>
	</div>
<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<input type="hidden" name="worksId" id="worksId" value="${vo.worksId}"/>
		<input type="hidden" name="worksType" id="worksType" value="9"/>
		<tr>
			<td colspan="4">
			<div class="contract_head" align="center">
				<h1 class="contract_title">采购订单-工程建设类请款审批表<i>（材料、设备）</i></h1>
			</div>
			</td>
		</tr>
		<tr>
		    <%-- <th align="right"><b>*</b>合同名称：</th>
		    <td>
		    <input name="contractName" <c:if test="${not empty vo.contractName }">readonly="readonly"</c:if> id="contractName" onfocus="autoCompletesProject(this)" type="text" class="text01" style="width:300px;" placeholder="请输入合同名称"  value="${vo.contractName}"/>
		    </td> --%>
		    <th width="20%" align="right"><b>*</b>合同名称：</th>
					<td>
						<select name="contractName" id="ContractName">
							<option value='0'>请选择</option>
								<c:forEach items="${list}" var="list">
									<option>${list}</option>
								</c:forEach>
						</select>
					</td>
		    <th align="right"><b>*</b>合同编号：</th>
		    <td>
		    <input  name="contractCode" <c:if test="${not empty vo.contractCode }">readonly="readonly"</c:if>  id= "contractCode" onfocus="autoCompletesProject(this)" value="${vo.contractCode}" type="text" class="text01" style="width:300px;<c:if test="${not empty  vo.contractCode}">background-color: #f5f5f5;</c:if>" placeholder="请输入合同编号" >
		    </td>		    		    
	    </tr>
	    <tr>
		    <th align="right">框架合同名称：<br/>（非框架合同此行不填）</th>
		    <td>
		    <input name="frameworkName"  id="frameworkName" value="${vo.frameworkName}" type="text" class="text01" style="width:300px;" placeholder="请输入框架合同名称" />
		    </td>
		    <th align="right">框架合同编号：<br/>（非框架合同此行不填）</th>
		    <td>
		    <input name="frameworkCode"  id="frameworkCode" value="${vo.frameworkCode}" type="text" class="text01" style="width:300px;" placeholder="请输入框架合同名称" />
		    </td>		 
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>订单名称：</th>
		    <td>
		    <input name="orderName"  id="orderName"  type="text" class="text01" style="width:300px;" placeholder="请输入订单名称"  value="${vo.orderName}"/>
		    </td>
		    <th align="right"><b>*</b>订单编号：</th>
		    <td>
		    <input  name="orderCode"  id= "orderCode"  value="${vo.orderCode}" type="text" class="text01" style="width:300px;" placeholder="请输入订单编号" />
		    </td>		    		    
	    </tr>
	    <tr>
		    <%-- <th align="right"><b>*</b>投资项目名称：</th>
		    <td>
		    <input name="projectName"  id="projectName" onfocus="autoProject(this)" type="text" class="text01" style="width:300px;" placeholder="请输入投资项目名称"  value="${vo.projectName}"/>
		    </td> --%>
		    <th width="20%" align="right"><b>*</b>投资项目名称：</th>
					<td><input id="orgName" type="text"
						class="text01"  style="width:300px;" onfocus="autoProject(this)"
						placeholder="请输入投资项目名称"/></td>
		    <th align="right"><b>*</b>投资项目编号：</th>
		    <td>
		    <input  name="projectCode"  id= "projectCode" onfocus="autoProject(this)" value="${vo.projectCode}" type="text" class="text01" style="width:300px;" placeholder="请输入投资项目编号" />
		    </td>		    		    
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>乙方单位：</th>
		    <td>
		    <input name="constructionName"  id="constructionName"  type="text" class="text01" style="width:300px;" placeholder="请输入乙方单位名称"  value="${vo.constructionName}"/>
		    <input type="hidden" name="constructionId" value="${vo.constructionId}" id="constructionId"/>
		    </td>
		    <th align="right"><b>*</b>合同金额：</th>
		    <td>
		    <input  name="contractAmount"  id= "contractAmount"  value="<fmt:formatNumber value='${vo.contractAmount}' pattern='##.##' minFractionDigits="2"></fmt:formatNumber>" type="text" class="text01" style="width:300px;" placeholder="￥" />
		    </td>		    		    
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>请款类型：</th>
		    <td>
		    	<div id="contract_chex" class="contract_chex" style="border:1px solid #ffffff;border-radius: 5px;width: 305px;">
						<span>
	        				<input <c:if test="${vo.qkType == 1 }">checked="checked"</c:if> type="radio" name="qkType" id="qkType1" value="1" onclick="add()" /> 
							<label class="chex" for="qkType">预付款 </label>
						</span>
                        <span>
	        				<input <c:if test="${vo.qkType == 2 }">checked="checked"</c:if> type="radio" name="qkType" id="qkType2" value="2" onclick="add()" /> 
							<label class="chex" for="qkType">进度款 </label>
						</span>
                        <br/>
                         <span>
	        				<input <c:if test="${vo.qkType == 3 }">checked="checked"</c:if> type="radio" name="qkType" id="qkType3" value="3" onclick="add()" /> 
							<label class="chex" for="qkType">结算款 </label>
						</span>
						<span>
	        				<input <c:if test="${vo.qkType == 4 }">checked="checked"</c:if> type="radio" name="qkType" id="qkTypex4" value="4" onclick="rem()" /> 
							<label class="chex" for="qkTypex">其他<input id="qkTypedec" name="qkTypedec" value="${vo.qkTypedec}" class="borderBtm" style="width:100px;" /></label>
						</span>
						
						
				</div>
		    </td>
		    <th align="right"><b>*</b>对应合同付款条款：</th>
		    <td>
		    <input  name="paymentTerms"  id= "paymentTerms"  value="${vo.paymentTerms}" type="text" class="text01" style="width:300px;" placeholder="请输入付款条款" />
		    </td>		    		    
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>乙方申请金额：</th>
		    <td>
		    <input  name="applicationAmount"  id= "applicationAmount"  value="<fmt:formatNumber value='${vo.applicationAmount}' pattern='##.##' minFractionDigits="2"></fmt:formatNumber>" type="text" class="text01" style="width:300px;" placeholder="￥" />
		    </td>		    		    
	    </tr>
	    <tr>
	    	<th align="right">乙方单位申请事由：</th>
	    </tr>
	    <tr>
	    	<th align="right"></th>
	    	<td colspan="3">	
	    		<div class="word_5">
                	<p>于<input class="borderBtm" style="width:100px;" name="beginYear" id="beginYear" value="${vo.beginYear }" />年<input class="borderBtm" style="width:40px;" name="beginMonth" id="beginMonth" value="${vo.beginMonth }" />月期间，我方已完成<input class="borderBtm" style="width:100px;" name="workContent" id="workContent" value="${vo.workContent }" />工作内容，
根据合同条款<input class="borderBtm" style="width:40px;" name="termsNum" id="termsNum" value="${vo.termsNum }" />条：<textarea class="borderBtm" style="width:770px; height:30px;" id="termsContent" value="${vo.termsContent }"></textarea>。<br/><br/>本期应扣除金额为<input class="borderBtm" style="width:140px;" name="deductAmount" id="deductAmount" value="<fmt:formatNumber value='${vo.deductAmount}' pattern='##.##' minFractionDigits="2"></fmt:formatNumber>" placeholder="￥" />元，
我方本次申请支付金额为<input class="borderBtm" style="width:140px;" name="payAmount" id="payAmount" value="<fmt:formatNumber value='${vo.payAmount}' pattern='##.##' minFractionDigits="2"></fmt:formatNumber>" placeholder="￥" />元。            
</p>
                </div>
            </td>
        </tr>
        <tr>
	    	<th align="right">计算过程：</th>
	    </tr>
	    <tr>
	    	<th align="right"></th>
	    	<td colspan="3"><textarea rows="5" cols="20" class="textArea01" style="width:89.4%" placeholder="请填写计算过程" id="calPro" name="calPro">${vo.calPro}</textarea></td>
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
//设置下拉框选中带出数据
$(function(){
$("#ContractName").change(function(){
var name=$(this).children('option:selected').val();
$.ajax({
	url:"<%=basePath%>command/queryByOrgNameE",
	type:"post",
	data:{orgName : name},
	success:function(data){
	console.log(data);
		$("#orgName").val(data);
	}
});
}); 
});
window.onload = function() {
	$("#termsContent").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 200){
	    	$(this).val($(this).val().substring(0,200));
	   }
	});
	$("#workContent").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 17){
	    	$(this).val($(this).val().substring(0,17));
	   }
	});
	$("#calPro").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 70){
	    	$(this).val($(this).val().substring(0,70));
	   }
	});
}

function add(){
	$("#qkTypedec").attr("readonly",'readonly');
	$("#qkTypedec").val("");
}

function rem(){
	$("#qkTypedec").removeAttr("readonly");
}

function ev_dosubmit(){
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>command/clqkSave",
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

function ev_save(){
   
	if(!ev_required_item()){
		return false;
	}
	//乙方申请金额  合同金额
	if(parseFloat($("#applicationAmount").val()) > parseFloat($("#contractAmount").val())){
		if(confirm("乙方申请金额大于合同金额，确认保存？")){
			ev_dosubmit();
		}
	}else{
		ev_dosubmit();
	}	

}


/**
 * 必填项
 * @author weifei
 * @date Mar 8, 2012 10:02:40 AM
 */
function ev_required_item(){
		var k = 0;
		var b = true;
		var k1 = true;
		
		if(bindChange("#contractName",100,"合同名称不得超过100个字")){
			return false;
		}
		if(bindChange("#contractCode",36,"合同编号不得超过36个字")){
			return false;
		}
		if(bindChange("#frameworkName",30,"框架合同名称不得超过30个字")){
			return false;
		}
		if(bindChange("#frameworkCode",30,"框架合同编号不得超过30个字")){
			return false;
		}
		if(bindChange("#orderName",14,"订单名称不得超过14个字")){
			return false;
		}
		if(bindChange("#orderCode",30,"订单编号不得超过30个字")){
			return false;
		}
		if(bindChange("#projectName",20,"投资项目名称不得超过20个字")){
			return false;
		}
		if(bindChange("#projectCode",30,"投资项目编号不得超过30个字")){
			return false;
		}
		if(bindChange("#constructionName",20,"乙方单位名称不得超过20个字")){
			return false;
		}
		if(bindChange1("#contractAmount",Math.pow(10,15),"合同金额不得超过15位数")){
			return false;
		}
		if(bindChange("#qkTypedec",3,"请款类型描述不得超过3个字")){
			return false;
		}
		if(bindChange("#paymentTerms",30,"付款条款不得超过30个字")){
			return false;
		}
		if(bindChange1("#applicationAmount",Math.pow(10,15),"乙方申请金额不得超过15位数")){
			return false;
		}
		if(bindChange("#beginYear",4,"开始年份不得超过4个字")){
			return false;
		}
		if(bindChange("#beginMonth",2,"开始月份不得超过2个字")){
			return false;
		}
		if(bindChange("#workContent",30,"工作内容不得超过30个字")){
			return false;
		}
		if(bindChange("#termsNum",4,"合同条款条数不得超过4个字")){
			return false;
		}
		if(bindChange("#termsContent",200,"合同条款内容不得超过200个字")){
			return false;
		}
		if(bindChange1("#deductAmount",Math.pow(10,15),"本期应扣除金额不得超过15位数")){
			return false;
		}
		if(bindChange1("#payAmount",Math.pow(10,15),"本次申请支付金额不得超过15位数")){
			return false;
		}
		if(bindChange("#calPro",70,"计算过程不得超过70个字")){
			return false;
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
		//订单名称
		var orderName = $("#orderName");
		if(orderName.val() == ""){
       		k++;bindC(orderName);			
		}
		//订单编号
		var orderCode = $("#orderCode");
		if(orderCode.val() == ""){
       		k++;bindC(orderCode);			
		}
		//投资项目名称
		var projectName = $("#projectName");
		if(projectName.val() == ""){
       		k++;bindC(projectName);			
		}
		//投资项目编号
		var projectCode = $("#projectCode");
		if(projectCode.val() == ""){
       		k++;bindC(projectCode);			
		}
		//乙方单位
		var constructionName = $("#constructionName");
		if(constructionName.val() == ""){
       		k++;bindC(constructionName);			
		}
		//合同金额
		var contractAmount = $("#contractAmount");
		if(contractAmount.val() == ""){
       		k++;bindC(contractAmount);			
		}
		//请款类型
		var qkType = jQuery("input[name='qkType']:checked").val();
		if(qkType!="1" && qkType!="2" && qkType!="3" && qkType!="4"){
			k++;bindCQK(contract_chex);
		}
		//付款请款
		var paymentTerms = $("#paymentTerms");
		if(paymentTerms.val() == ""){
       		k++;bindC(paymentTerms);			
		}
		//乙方申请金额
		var applicationAmount = $("#applicationAmount");
		if(applicationAmount.val() == ""){
       		k++;bindC(applicationAmount);			
		}
		
		if(k > 0){
      		alert("带*号的为必填字段，请完善必填字段。");
      		b = false;
      		return b;
      	}
		
		//合同金额填入的必须为数字
		var contractAmount = $("#contractAmount");
		var value = document.getElementById("contractAmount").value;
		var reg=/^\d+(\.\d+)?$/; //能输入数字和小数点
		if(contractAmount.val() != "" && reg.test(value)!=true){
			bindC(contractAmount);
			k1 = false;
		}
		//乙方申请金额填入的必须为数字
		var applicationAmount = $("#applicationAmount");
		var value = document.getElementById("applicationAmount").value;
		var reg=/^\d+(\.\d+)?$/; //能输入数字和小数点
		if(applicationAmount.val() != "" && reg.test(value)!=true){
			bindC(applicationAmount);
			k1 = false;
		}
		//本期应扣除金额填入的必须为数字
		var deductAmount = $("#deductAmount");
		var value = document.getElementById("deductAmount").value;
		var reg=/^\d+(\.\d+)?$/; //能输入数字和小数点
		if(deductAmount.val() != "" && reg.test(value)!=true){
			bindC(deductAmount);
			k1 = false;
		}
		//本次申请支付金额填入的必须为数字
		var payAmount = $("#payAmount");
		var value = document.getElementById("payAmount").value;
		var reg=/^\d+(\.\d+)?$/; //能输入数字和小数点
		if(payAmount.val() != "" && reg.test(value)!=true){
			bindC(payAmount);
			k1 = false;
		}
		
		if(!k1){
      		alert("请确认金额输入的只为数字");
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

function bindCQK(field){
	$(field).css("border-color","red");
    $(field).bind("click", function(){
		$(this).css("border-color","#ffffff");
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
function bindChange1(field,max,msg){
	if( $(field).val() >= max ){
		$(field).css("border-color","red");
	    $(field).bind("change", function(){
			$(this).css("border-color","");
		});
		alert(msg);
		return true;
	}
}
</script>