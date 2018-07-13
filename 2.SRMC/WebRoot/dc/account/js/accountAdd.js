var indexOfLine = 0;

$(function(){
	$('.ui-select').ui_select();
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
    });
	
	
	$("input[name='deduction']").click(function(){
		if(this.checked==true && this.value=='1'){
			$("#invoice_div").show();
		} else {
			$("#invoice_div").hide();
		}
	});
});

function docheckAll(){
	var $subBox = $("input[name='subBox']");
	$("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
}

function delInvoiceLine(){
	var $subBoxChecks = $("input[name='subBox']:checked");
    if($subBoxChecks.length == 0){
    	alert("请选择需要删除的发票信息");
    }
    
    var flag = false;
	$.each($subBoxChecks,function(k,v){
		var values = $(v).parent().parent().find(":text");
		$.each(values,function(n,m){
			if($(m).val() != ""){
				flag = true;
			}
		});
	});
	if(flag){
		if(confirm("已填写“发票信息”将被删除，是否继续？")){
			$subBoxChecks.parent().parent().remove();
		}
	}else{
		$subBoxChecks.parent().parent().remove();
	}
}

function addInvoiceLine(){
	var temp = '<tr id="tr_id_'+indexOfLine+'" name="invoice_tr">\
			  		<td ><input name="subBox" onclick="docheckAll()" type="checkbox" value="'+indexOfLine+'"></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="invoice['+indexOfLine+'].invoiceType" id="invoiceType" /></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="invoice['+indexOfLine+'].goodsName" id="goodsName" /></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="invoice['+indexOfLine+'].invoiceCode" id="invoiceCode" /></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="invoice['+indexOfLine+'].invoiceNum" id="invoiceNum" /></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="invoice['+indexOfLine+'].createDate" id="createDate" onfocus="WdatePicker({dateFmt:\'yyyyMMdd\',alwaysUseStartDate:true})"/></td>\
				    <td><input style="width:95%;" class="text01" type="text"  onkeyup="this.value=this.value.replace(/[^\\d\\.]/g,\'\');invoice_total()"  onafterpaste="this.value=this.value.replace(/[^\\d\\.]/g,\'\');invoice_total()" name="invoice['+indexOfLine+'].moneyNoTax" id="moneyNoTax" /></td>\
				    <td><input style="width:95%;" class="text01" type="text"  onkeyup="this.value=this.value.replace(/[^\\d\\.]/g,\'\');invoice_total()"  onafterpaste="this.value=this.value.replace(/[^\\d\\.]/g,\'\');invoice_total()" name="invoice['+indexOfLine+'].taxNum" id="taxNum" /></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="invoice['+indexOfLine+'].taxRate" id="taxRate" /></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="invoice['+indexOfLine+'].gfTaxpayerNum" id="gfTaxpayerNum" /></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="invoice['+indexOfLine+'].gfTaxpayerName" id="gfTaxpayerName" /></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="invoice['+indexOfLine+'].xfTaxpayerNum" id="xfTaxpayerNum" /></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="invoice['+indexOfLine+'].xfTaxpayerName" id="xfTaxpayerName" /></td>\
				 </tr>';
	$("#invoice_tb").append(temp);
	indexOfLine++;
	docheckAll();
}

function invoice_total(){
	var moneyNoTax = $("input[id='moneyNoTax']");
	var total = 0;
	$.each(moneyNoTax,function(k,v){
		if($(v).val() != "" && !isNaN($(v).val())){
			total += parseFloat($(v).val());
		}
	});
	$("#invoice_total").html(total.toFixed(2));
	
	var taxRate = $("input[id='taxNum']");
	var taxTotal = 0;
	$.each(taxRate,function(k,v){
		if($(v).val() != "" && !isNaN($(v).val())){
			taxTotal += parseFloat($(v).val());
		}
	});
	$("#taxNum_total").html(taxTotal.toFixed(2));
	
	$("#total_total").html((total + taxTotal).toFixed(2));
	
	
}