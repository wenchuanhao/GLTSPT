<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>接单录入</title>
		<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
		<link rel="stylesheet" type="text/css" href="/SRMC/dc/css/combotree/easyui.css">
		<script type="text/javascript" src="/SRMC/dc/js/combotree/jquery.easyui.min.js"></script>  
		<script type="text/javascript">
			var j = jQuery.noConflict(true);
		</script>   
		<link href="/SRMC/rmpb/css/date_input.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body>
<div class="gl_import_m">
<form  action="" method="post" id="submitForm">
<div class="searchCondition"><span class="searchCondition_header">接单信息</span></div>
  <table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" >
	  <input type="hidden" id="trialAccountId" name="trialAccountId"/>
	  <input type="hidden" id="departmentId" name="departmentId"/>
	  <input type="hidden" id="trialAccount" name="trialAccount"/>
	  <input type="hidden" id="department" name="department"/>
	  <input type="hidden" id="orderTemp" name="orderTemp"/>
	  <input type="hidden" name="presenterId" id="presenterId_input" value="${accountForm.presenterId}"/>
		<input type="hidden" name="sementPeopleId" id="sementPeopleId_input" value="${accountForm.sementPeopleId}"/>
		<input type="hidden" name="costType" id="costType"/>
	  <tr>
	    <th width="12%" align="right"><b>*</b> 报账单号：</th>
	    <td width="20%"><input style="width: 165px" autocomplete="off" type="text" class="text01" name="orderId"  id="sel_01" onchange="changeCheckId()" onblur="blurCheckId()" /></td>
	    <th align="right"><b>*</b> 报账人：</th>
	    <td><input   onfocus="if(this.value=='请填写报账人'){this.value='';};this.style.color='#333333';autoCompletes();" 
			                     onkeyup="clean()"
			                     onblur="if(this.value==''||this.value=='请填写报账人'){this.style.color='#b6b6b6';}" placeholder="请填写报账人"  type="text" class="text01" name="sementPeople"  id="sel_02"/></td>
	    <th width="12%" align="right"><b>*</b> 纸质提交财务时间：</th>
	    <td width="20%"><div class="date l" style="width:160px;"><input  type="text" autocomplete="off"  name="pageSubmitDate" id="pageSubmitDate"  value="<fmt:formatDate value="${createWorkorder.expectFinishDate}" pattern="yyyy-MM-dd HH:mm"/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',alwaysUseStartDate:true})" placeholder="请您输入"  class="text02 l" style="width:90%;" /><i></i></div></td>
	  </tr>
	  <tr>
	  	<th align="right"><b>*</b> 费用类型：</th>
	      <td>
	      <div class="checxTreeWrap"  style="width:170px;">
		    <%-- <input id="cosId" name="cosId" value="${accountForm.costType}"/>  --%>
		    <select  multiple style="width:150px" id="cosId" name="cosId" class="easyui-combotree" data-options="url:'<%=basePath%>/account/getCostType',method:'get'" multiple style="width:180px;">
		    </select>
		    </div>
	      </td>
	    <th align="right"><b>*</b> 交单人：</th>
	    <td><input  onfocus="if(this.value=='请填写交单人'){this.value='';};this.style.color='#333333';autoCompletes();" 
			                     onkeyup="clean()"
			                     onblur="if(this.value==''||this.value=='请填写交单人'){this.style.color='#b6b6b6';}" placeholder="请填写交单人" type="text" class="text01" name="presenter"  id="sel_03"/></td>
	    
	    <th align="right"><b>*</b> 发票时间：</th>
	    <td><div class="date l" style="width:160px;"><input  autocomplete="off" type="text"  name="reachSement" id="reachSement" value="<fmt:formatDate value="${createWorkorder.expectFinishDate}" pattern="yyyy-MM-dd"/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  placeholder="请您输入"  class="text02 l" style="width:90%;" /><i></i></div></td>
	  </tr>
	  <tr>
	    
	    <th align="right"><b>*</b> 是否包含抵扣：</th>
	    <td><span><input type="radio"   name="includeBuckle" id="includeBuckle" value="1"> 是</span><span><input type="radio"  name="includeBuckle" id="includeBuckles" value="0"> 否</span></td>
	    <th align="right"><b>*</b>报账部门：</th>
	    <td ><input type="text" id="department1" autocomplete="off" name="department1" class="text01" onfocus="autoCompletes();" /></td>
	    <th align="right"><b>*</b> 金额：</th>
	    <td><input type="text" autocomplete="off" id="cost" name="cost" class="text01"   onblur="if(isNaN(this.value))this.value=''"/></td>
	  </tr>
	  <tr>
	      
	      <th align="right"><b>*</b> 是否专票抵扣：</th>
	      <td><span><input type="radio"   name="deduction" id="deduction" value="1"> 是</span><span><input type="radio"  name="deduction" id="deduction" value="0"> 否</span></td>
	    <th align="right"><b>*</b>初审会计：</th>
	    <td width="20%">
	    	<input type="text" id="trialAccount1" autocomplete="off" onfocus="autoCompletesAccount(this);" name="trialAccount1" class="text01" />
	    	<span id="selected_trialAccount">
			</span>
	    
	    </td>
	    <th align="right">摘要：</th>
	    <td width="20%"><input type="text" id="accountAbstract" autocomplete="off" name="accountAbstract" class="text01" value="${accountForm.accountAbstract}" /></td>
	  </tr>
	</table>
	
	<!-- 发票录入 S -->
  	<div class="tabpages" id="invoice_div" style="display: none;">
    	<ul class="l">
      		<li class="current">发票信息</li>
      		<font color="red" size="3">&nbsp;&nbsp;&nbsp;温馨提示：发票金额(不含税)为：<i id="invoice_total">0.00</i>，税额为：<i id="taxNum_total">0.00</i>，总额为：<i id="total_total">0.00</i>。</font>
    	</ul>
    	<div class="otherButtons r">
			<input name="" onclick="addInvoiceLine()"  type="button" class="btn_common01" value="添加一行" />
			<input name="" onclick="delInvoiceLine()" type="button" class="btn_common01" value="删除多行" />
		</div>
	  <table id="invoice_tb" width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable"> 
	   <tbody>
	    <tr> 
	     <th><input id="checkAll" type="checkbox" /></th> 
	     <th>发票类型</th> 
	     <th>货物或应税劳务名称</th> 
	     <th>发票代码</th> 
	     <th>发票号码</th> 
	     <th>开票日期</th> 
	     <th>金额(不含税)</th>
	     <th>税额</th>
	     <th>税率(%)</th>
	     <th>购方纳税人识别号</th>
	     <th>购方纳税人名称</th>
	     <th>销方纳税人识别号</th>
	     <th>销方纳税人名称</th>
	    </tr> 
	   </tbody>
	  </table>
  	</div>
	
	<!-- 发票录入 E -->
	</form>
	<table width="99%" border="0" cellspacing="0" cellpadding="0" align="center" style="margin-top: 10px;">
	  <tr>
	      <td colspan="6" align="center"><input name="" type="button" class="btn_common02" onclick="doDraft()" value="提交" /> <input name="" onclick="resets(true)" type="button" class="btn_common01" value="重置" /></td>
	  </tr>
	</table>
</div>

<script src="/SRMC/dc/account/js/jquery.js"></script>
<script src="/SRMC/dc/account/js/ui-select.js"></script>
<!-- 	表单异步提交 -->
<script src="/SRMC/rmpb/js/jquery.form.js"></script>
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/account/js/accountAdd.js"></script>
<script>
	///清空
	function resets(submit){
		var values = $("#invoice_tb").find(":text");
		var flag = false;
		$.each(values,function(k,v){
			if($(v).val() != ""){
				flag = true;
			}
		});
		if(flag && submit){
			if(confirm("重置会删除已填写“发票信息”，是否重置？")){
				doreset();
			}
		}else{
			doreset();
		}
		
	}
	
	function doreset(){
		document.getElementById("sel_01").focus();
		j("#cosId").combotree({value:""});
		jQuery("#costType").val(j("#cosId").combotree("getText"));
		$("#departmentId").val("");
		jQuery("#selected_trialAccount").html("");
		jQuery("#sementPeopleId_input").val("");
		
		$("#invoice_total").html("0.00");
		$("#taxNum_total").html("0.00");
		$("#total_total").html("0.00");
		
		$("#invoice_div").hide();
		$("input[name='subBox']").parent().parent().remove();
		$('#submitForm')[0].reset();
	}
	
	var bcheck = true;
	function blurCheckId(){
		if(bcheck){
			bcheck = false;
			if(!checkId()){
				alert("该报账单号已存在，请重新输入！");
				return ;
			}
		}
	}
	
	function changeCheckId(){
		bcheck = true;
	}
	
	//检测报账ID是否重复
	function checkId(){
	 	var flag = true;
		jQuery.ajax({
			type: "POST",
			async:false,
			url: "<%=basePath%>account/ajaxCheck",
			dataType: "json",
			data: {
				accoutId: jQuery.trim(jQuery("#sel_01").val())
			},
			success: function(data) {
				if(data=="1"){
					flag = false;
				}else{
					flag = true;
				}
			}
		});
		return flag;
	}
	/**
	* 联动改变初审会计
	*/
	function setAccount(){
		//用户id
		var tempUserIds = jQuery("#selected_trialAccount").children("a");
		//用户名
		var tempUserNames = jQuery("#selected_trialAccount").find("span");
		var r = "",t = "";
		for ( var i = 0; i < tempUserIds.length; i++) {
			if(i == 0){
				r += $(tempUserIds[i]).prop("id");
			}else{
				r += ","+$(tempUserIds[i]).prop("id");
			}
		}
		for ( var i = 0; i < tempUserNames.length; i++) {
			if(i == 0){
				t += $(tempUserNames[i]).html();
			}else{
				t += ","+$(tempUserNames[i]).html();
			}
		}
		$("#trialAccount").val(t);
		$("#trialAccountId").val(r);
	}
	//获取初审会计
	function getTrial(costTypeId,depId){
		$.ajax({
			type:"post",
			dataType:"json",
			async:false,
			url:"<%=basePath%>account/queryAccountById",
			data:{ costTypeId:costTypeId,depId:depId },
			success:function(data){
				jQuery("#selected_trialAccount").children().remove();
				if(data.length>0){
					if(data[0].accountingId && data[0].accounting){
						var userIds = data[0].accountingId.split(",");
						var userNames = data[0].accounting.split(",");
						$.each(userIds,function(k,v){
							if(jQuery("#selected_trialAccount").children("[id='" + v + "']").length == 0){
								jQuery("#selected_trialAccount").append('<a href="javascript:void(0);" class="" id="' + v + '">\
																					<span style="padding: 0 3px 0 0;">' + userNames[k] + '</span>\
																					<img onclick="jQuery(this).parent().remove();setAccount();" src="/SRMC/dc/images/close_icon01.png" width="10" height="10" />\
																				  </a>');
							}
						});
						
						jQuery("#trialAccount1").val("");
					}
					setAccount();
				}else{
					$("#trialAccount").val("");
					$("#trialAccount1").val("");
					$("#trialAccountId").val("");
				}
			},
			error:function(){
				alert("获取会计失败，请检查网络");
			}
		});//ajax
		
	}
	/**
	* 初审会计联想查询
	*/
	function autoCompletesAccount(target){
		jQuery(target).autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>rulesController/searchUser",
					dataType: "json",
					data: {
						userValue: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
				     			 value:item[0].userName+" -- "+item[1].orgName,
									userName:item[0].userName,
									userId:item[0].userId,
									account:item[0].account,
									orgName:item[1].orgName
								};
							}));
						}else{
							return false;
						}
					}
				});
			},
			minLength: 1,
			select: function( event, ui ) {
				
				if(jQuery("#selected_trialAccount").children("[id='" + ui.item.userId + "']").length != 0){
					jQuery(target).val("");
					return;
				}else{
					jQuery("#selected_trialAccount").append('<a href="javascript:void(0);" class="" id="' + ui.item.userId + '">\
																		<span style="padding: 0 3px 0 0;">' + ui.item.userName + '</span>\
																		<img onclick="jQuery(this).parent().remove();setAccount();" src="/SRMC/dc/images/close_icon01.png" width="10" height="10" />\
																	  </a>');
					jQuery(target).val("");
					setAccount();
					return false;
				}
				return false;
			}
		});
	}
	//用户可选择初审会计(sel_03)  或 报账人(sel_02)
function autoCompletes(){
			jQuery("#sel_02").autocomplete({
				source: function( request, response ) {
					jQuery.ajax({
						url: "<%=basePath%>rulesController/searchUser",
						dataType: "json",
						data: {
							userValue: request.term
						},
						type: "POST",
						success: function(data) {
							if(data!=null){
					     		response(jQuery.map(data, function( item ) {
					     			return {
					     			 value:item[0].userName+" -- "+item[1].orgName,
										userName:item[0].userName,
										userId:item[0].userId,
										account:item[0].account,
										orgName:item[1].orgName
									};
								}));
							}else{
								return false;
							}
						}
					});
				},
				minLength: 1,
				select: function( event, ui ) {
					jQuery("#sel_02").val(ui.item.userName);
					jQuery("#sementPeopleId_input").val(ui.item.userId);
					
					$.ajax({
						url: "<%=basePath%>account/queryDepartmentById?userid="+ui.item.userId,
						async:false,
						type: "POST",
						success: function(data) {
							var result = data.split(",");
							$("#department").val(result[1]);
							$("#department1").val(result[1]);
							$("#departmentId").val(result[2]);
							
							var dep = $("#departmentId").val();
							var costTypeVlue = j("#cosId").combotree("getValue");
							if(dep.trim()!="" && costTypeVlue.trim()!=""){
								getTrial(costTypeVlue,dep);
							}
						},
						error:function (){
							alert("网络异常，请联系管理员！");
						}
						
					 });
					return false;
				}
			});
		if(jQuery("#sel_02").val()!=null && jQuery("#sel_02").val()!=""){
		}
		
			jQuery("#sel_03").autocomplete({
				source: function( request, response ) {
					jQuery.ajax({
						url: "<%=basePath%>rulesController/searchUser",
						dataType: "json",
						data: {
							userValue: request.term
						},
						type: "POST",
						success: function(data) {
							if(data!=null){
					     		response(jQuery.map(data, function( item ) {
					     			return {
					     			 value:item[0].userName+" -- "+item[1].orgName,
										userName:item[0].userName,
										userId:item[0].userId,
										account:item[0].account,
										orgName:item[1].orgName
									};
								}));
							}else{
								return false;
							}
						}
					});
				},
				minLength: 1,
				select: function( event, ui ) {
					jQuery("#sel_03").val(ui.item.userName);
					jQuery("#presenterId_input").val(ui.item.userId);
					return false;
				}
			});
		if(jQuery("#sel_03").val()!=null && jQuery("#sel_03").val()!=""){
		}
		
		jQuery("#department1").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>account/searchOrganization",
					dataType: "json",
					data: {
						orgValue: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
				     			 	value:item.orgName,
									orgId:item.orgId,
									orgName:item.orgName
								};
							}));
						}else{
							return false;
						}
					}
				});
			},
			minLength: 1,
			select: function( event, ui ) {
				jQuery("#department").val(ui.item.orgName);
				jQuery("#department1").val(ui.item.orgName);
				jQuery("#departmentId").val(ui.item.orgId);
				
				var dep = $("#departmentId").val();
				var costTypeVlue = j("#cosId").combotree("getValue");
				if(dep.trim()!="" && costTypeVlue.trim()!=""){
					getTrial(costTypeVlue,dep);
				}
				return false;
			}
		});
	}
	
	function clean(){
		if (event.keyCode == 13) {
        } 
	}
	
	var obj;
	$(function (){
		// 将所有.ui-select实例化
		$('.ui-select').ui_select();  
		// 获取已经实例化的select对象
		obj = $('#sel_04').data('ui-select');
		
		document.getElementById("sel_01").focus();
		
		j("#cosId").combotree({
		onBeforeSelect: function(node) {
			if (!j(this).tree('isLeaf', node.target)) {
				return false;
			}
		},
		onClick: function(node) {
			if (!j(this).tree('isLeaf', node.target)) {
				j('#cosId').combo('showPanel');
			}
			var value = j("#cosId").combotree("getValue");
			var dep = $("#departmentId").val();
			$("#costType").val(j("#cosId").combotree("getText"));
			if(value.trim()!="" && dep.trim()!=""){
				getTrial(value,dep);
			}
			
		},
   	    url:'<%=basePath%>/account/getCostType' ,
   		width:170,
   		lines:'true',
		checkbox:true ,
		cascadeCheck:false,
		required:false,
		editable:false,
		multiple:false//设置是否多选	
		
	       });  
	});
	
	function doDraft(){
		var text = j("#cosId").combotree("getText");
		jQuery("#costType").val(text);
		
		if(jQuery.trim(jQuery("#sel_01").val()) == ""){
			alert("请输入报账单号！");
			return;
		}
		var aid = jQuery.trim(jQuery("#sel_01").val());
		if(isNaN(aid)){
			alert("请输入纯数字！");
			return;
		}
		if(aid.indexOf('.') >= 0){
			alert("请输入纯数字！");
			return;
		}
		if(jQuery.trim(jQuery("#sel_01").val()).length>20 || jQuery.trim(jQuery("#sel_01").val()).length<14){
			alert("报账单号长度有误，应为14~20位");
			return;
		}
		if(!checkId()){
			alert("该报账单号已存在，请重新输入！");
			return ;
		}
		
		if(jQuery.trim(text) == "0"||jQuery.trim(text) == ""){
			alert("请选择费用类型");
			return;
		}
		if(jQuery.trim(jQuery("#sementPeopleId_input").val()) == ""){
			alert("请输入报账人");
			return;
		}
		if(jQuery.trim(jQuery("#presenterId_input").val())==""){
			alert("请输入交单人");
			return;
		}
		if(jQuery.trim(jQuery("#pageSubmitDate").val()) == ""){
			alert("请输入纸质提交财务时间");
			return;
		}
		if(jQuery.trim(jQuery("#trialAccount").val()) == ""){
			alert("初审会计不能为空!");
			return;
		}
		
		var includeBuckle = jQuery("input[name='includeBuckle']:checked").val();
		if(includeBuckle!="1"&&includeBuckle!="0"){
			alert("请选择是否包含抵扣");
			return;
		}
		if(jQuery.trim(jQuery("#reachSement").val()) == ""){
			alert("请输入发票时间");
			return;
		}
		var cost = jQuery.trim(jQuery("#cost").val());
		if(cost == ""){
			alert("请输入金额");
			return;
		}else{
			if(isNaN(cost)){
				alert("金额不能为非数字");
				return ;
			}
			
			if(jQuery.trim(jQuery("#cost").val()).length>20){
				alert("金额不能超过20位");
				return;
			}
		}
		var deduction = jQuery("input[name='deduction']:checked").val();
		if(deduction!="1"&&deduction!="0"){
			alert("请选择是否专票抵扣");
			return;
		}
		
		if(jQuery.trim(jQuery("#accountAbstract").val()).length>125){
			alert("摘要长度有误，应不大于125字");
			return;
		}
		
		//是专票抵扣要验证发票信息
		if(deduction == "0"){
			$("tr[name='invoice_tr']").remove();
		} else {
			//发票类型
			var $invoiceType = $("input[id='invoiceType']");
			for(var int = 0; int < $invoiceType.length; int++){
				if( $.trim($($invoiceType[int]).val())=="" ){
					alert("请填写发票类型");
					return false;
				}
			}
			//货物或应税劳务名称
			var $goodsName = $("input[id='goodsName']");
			for(var int = 0; int < $goodsName.length; int++){
				if( $.trim($($goodsName[int]).val())=="" ){
					alert("请填写货物或应税劳务名称");
					return false;
				}
			}
			//发票代码
			var $invoiceCode = $("input[id='invoiceCode']");
			for(var int = 0; int < $invoiceCode.length; int++){
				if( $.trim($($invoiceCode[int]).val())=="" ){
					alert("请填写发票代码");
					return false;
				}
			}
			//发票号码
			var $invoiceNum = $("input[id='invoiceNum']");
			for(var int = 0; int < $invoiceNum.length; int++){
				if( $.trim($($invoiceNum[int]).val())=="" ){
					alert("请填写发票号码");
					return false;
				}
			}
			//开票日期
			var $createDate = $("input[id='createDate']");
			for(var int = 0; int < $createDate.length; int++){
				if( $.trim($($createDate[int]).val())=="" ){
					alert("请填写开票日期");
					return false;
				}
			}
			//金额（不含税）
			var $moneyNoTax = $("input[id='moneyNoTax']");
			for(var int = 0; int < $moneyNoTax.length; int++){
				if( $.trim($($moneyNoTax[int]).val())=="" ){
					alert("请填写金额（不含税）");
					return false;
				}
				if(isNaN($($moneyNoTax[int]).val())){
					alert("金额（不含税）请填写数字！");
					return false;
				}
			}
			//税额
			var $taxNum = $("input[id='taxNum']");
			for(var int = 0; int < $taxNum.length; int++){
				if( $.trim($($taxNum[int]).val())=="" ){
					alert("请填写税额");
					return false;
				}
				if(isNaN($($taxNum[int]).val())){
					alert("税额请填写数字！");
					return false;
				}
			}
			//税率
			var $taxRate = $("input[id='taxRate']");
			for(var int = 0; int < $taxRate.length; int++){
				if( $.trim($($taxRate[int]).val())=="" ){
					alert("请填写税率");
					return false;
				}
			}
			//购方纳税人识别号
			var $gfTaxpayerNum = $("input[id='gfTaxpayerNum']");
			for(var int = 0; int < $gfTaxpayerNum.length; int++){
				if( $.trim($($gfTaxpayerNum[int]).val())=="" ){
					alert("请填写购方纳税人识别号");
					return false;
				}
			}
			//购方纳税人名称
			/*
			var $gfTaxpayerName = $("input[id='gfTaxpayerName']");
			for(var int = 0; int < $gfTaxpayerName.length; int++){
				if( $.trim($($gfTaxpayerName[int]).val())=="" ){
					alert("请填写购方纳税人名称");
					return false;
				}
			}
			*/
			//销方纳税人识别号
			var $xfTaxpayerNum = $("input[id='xfTaxpayerNum']");
			for(var int = 0; int < $xfTaxpayerNum.length; int++){
				if( $.trim($($xfTaxpayerNum[int]).val())=="" ){
					alert("请填写销方纳税人识别号");
					return false;
				}
			}
			//销方纳税人名称
			var $xfTaxpayerName = $("input[id='xfTaxpayerName']");
			for(var int = 0; int < $xfTaxpayerName.length; int++){
				if( $.trim($($xfTaxpayerName[int]).val())=="" ){
					alert("请填写销方纳税人名称");
					return false;
				}
			}
			
		}
		
		$("#submitForm").ajaxSubmit({
			url: "<%=basePath%>account/accountSave",
			dataType: "json",
			type: "POST",
			success: function(data) {
				if(data=="1"){
					alert("提交成功！");
					document.getElementById("sel_01").focus();
					resets(false);
				}
			},
			error:function (){
				alert("网络异常，请联系管理员！");
			}
			
		});
	}
</script>        
</body>
</html>