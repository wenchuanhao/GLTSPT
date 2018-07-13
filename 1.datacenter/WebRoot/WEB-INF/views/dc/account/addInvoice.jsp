<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<!-- 表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
</head>

<body>

<!-- 遮罩内容开始 -->

<div class="taskCreatBox">
<div class="box_task">
	<form name="form" id="pageForm" method="post">
	<input type="hidden" name="id" value="${vo.id}" />
	<input type="hidden" name="parentId" value="${pId}" />
	<table width="99%" id="table01" border="0" cellspacing="0" cellpadding="0" class="readOnlyTable" align="center" style="border:0px;">
		<tr>
			<th>发票类型</th>
			<td><input id="invoiceType" name="invoiceType" value="${vo.invoiceType}" type="text" placeholder="请填写发票类型" class="text01" style="width:260px;" /></td>
			<th>货物或应税劳务名称</th>
			<td><input id="goodsName" name="goodsName" value="${vo.goodsName}" type="text" placeholder="请填写货物或应税劳务名称" class="text01" style="width:260px;" /></td>
		</tr>
		<tr>
			<th>发票代码</th>
			<td><input id="invoiceCode" name="invoiceCode" value="${vo.invoiceCode}" type="text" placeholder="请填写发票代码" class="text01" style="width:260px;" /></td>
			<th>发票号码</th>
			<td><input id="invoiceNum" name="invoiceNum" value="${vo.invoiceNum}" type="text" placeholder="请填写发票号码" class="text01" style="width:260px;" /></td>
		</tr>
		<tr>
			<th>开票日期</th>
			<td>
		    	<div class="date l" style="width: 255px;">
		    	<input readonly="readonly" name="createDate" id="createDate" type="text" placeholder="请输入开票日期" class="text02 l" value="${vo.createDate}"
		                      onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyyMMdd',alwaysUseStartDate:true})" style="width:250px;" />
		        </div>
			</td>
			<th>金额(不含税)</th>
			<td><input onkeyup="this.value=this.value.replace(/[^\d\.]/g,'')"  onafterpaste="this.value=this.value.replace(/[^\d\.]/g,'')" id="moneyNoTax" name="moneyNoTax" value="${vo.moneyNoTax}" type="text" placeholder="请填写金额" class="text01" style="width:260px;" /></td>
		</tr>
		<tr>
			<th>税额</th>
			<td><input id="taxNum" name="taxNum" value="${vo.taxNum}" type="text" placeholder="请填写税额" class="text01" style="width:260px;" /></td>
			<th>税率(%)</th>
			<td><input id="taxRate" name="taxRate" value="${vo.taxRate}" type="text" placeholder="请填写税率" class="text01" style="width:260px;" /></td>
		</tr>
		<tr>
			<th>购方纳税人识别号</th>
			<td><input id="gfTaxpayerNum" name="gfTaxpayerNum" value="${vo.gfTaxpayerNum}" type="text" placeholder="请填写购方纳税人识别号" class="text01" style="width:260px;" /></td>
			<th>购方纳税人名称</th>
			<td><input id="gfTaxpayerName" name="gfTaxpayerName" value="${vo.gfTaxpayerName}" type="text" placeholder="请填写购方纳税人名称" class="text01" style="width:260px;" /></td>
		</tr>
		<tr>
			<th>销方纳税人识别号</th>
			<td><input id="xfTaxpayerNum" name="xfTaxpayerNum" value="${vo.xfTaxpayerNum}" type="text" placeholder="请填写销方纳税人识别号" class="text01" style="width:260px;" /></td>
			<th>销方纳税人名称</th>
			<td><input id="xfTaxpayerName" name="xfTaxpayerName" value="${vo.xfTaxpayerName}" type="text" placeholder="请填写销方纳税人名称" class="text01" style="width:260px;" /></td>
		</tr>
	</table>
	</form>
</div>
<div class="box_task">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" style="border:0px;">
		<tr>
			<th colspan="5" align="center" height="50">
				<input type="button" class="btn_common01" onclick="javascript:addInvoice();" value="保 存" />
				<input type="button" class="btn_common01" onclick="javascript:parent.$.fancybox.close();" value="取 消" />
			</th>
		</tr> 
	</table>
</div>
</div>
<!-- 遮罩内容结束 -->
</body>
</html>

<script type="text/javascript">
function addInvoice(){
	//发票类型
	var $invoiceType = $("input[id='invoiceType']");
	if( $.trim($($invoiceType).val())=="" ){
		alert("请填写发票类型");
		return false;
	}
	//货物或应税劳务名称
	var $goodsName = $("input[id='goodsName']");
	if( $.trim($($goodsName).val())=="" ){
		alert("请填写货物或应税劳务名称");
		return false;
	}
	//发票代码
	var $invoiceCode = $("input[id='invoiceCode']");
	if( $.trim($($invoiceCode).val())=="" ){
		alert("请填写发票代码");
		return false;
	}
	//发票号码
	var $invoiceNum = $("input[id='invoiceNum']");
	if( $.trim($($invoiceNum).val())=="" ){
		alert("请填写发票号码");
		return false;
	}
	//开票日期
	var $createDate = $("input[id='createDate']");
	if( $.trim($($createDate).val())=="" ){
		alert("请填写开票日期");
		return false;
	}
	//金额（不含税）
	var $moneyNoTax = $("input[id='moneyNoTax']");
	if( $.trim($($moneyNoTax).val())=="" ){
		alert("请填写金额（不含税）");
		return false;
	}
	//税额
	var $taxNum = $("input[id='taxNum']");
	if( $.trim($($taxNum).val())=="" ){
		alert("请填写税额");
		return false;
	}
	//税率
	var $taxRate = $("input[id='taxRate']");
	if( $.trim($($taxRate).val())=="" ){
		alert("请填写税率");
		return false;
	}
	//购方纳税人识别号
	var $gfTaxpayerNum = $("input[id='gfTaxpayerNum']");
	if( $.trim($($gfTaxpayerNum).val())=="" ){
		alert("请填写购方纳税人识别号");
		return false;
	}
	//购方纳税人名称
	/*
	var $gfTaxpayerName = $("input[id='gfTaxpayerName']");
	if( $.trim($($gfTaxpayerName).val())=="" ){
		alert("请填写购方纳税人名称");
		return false;
	}
	*/
	//销方纳税人识别号
	var $xfTaxpayerNum = $("input[id='xfTaxpayerNum']");
	if( $.trim($($xfTaxpayerNum).val())=="" ){
		alert("请填写销方纳税人识别号");
		return false;
	}
	//销方纳税人名称
	var $xfTaxpayerName = $("input[id='xfTaxpayerName']");
	if( $.trim($($xfTaxpayerName).val())=="" ){
		alert("请填写销方纳税人名称");
		return false;
	}
	
	$("#pageForm").ajaxSubmit({
		url: "<%=basePath%>account/saveOrUpdateInvoice",
		dataType: "json",
		type: "POST",
		success: function(data) {
			if(data=="1"){
				alert("保存成功！");
				parent.window.location.reload();
				//parent.$.fancybox.close();
			}else{
				alert("保存失败！");
			}
		},
		error:function (){
			alert("网络异常，请联系管理员！");
		}
	});
}
	


</script>