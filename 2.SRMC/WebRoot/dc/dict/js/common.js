
$(function(){
});
$(document).ready(function(){
	$('.ui-select').ui_select();
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
    });
});

function docheckAll(){
	var $subBox = $("input[name='subBox']");
	$("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
}

function delParameterLine(){
	var $subBoxChecks = $("input[name='subBox']:checked");
    $subBoxChecks.parent().parent().remove();
}

function addParameterLine(){
	var temp = '<tr id="tr_id_'+indexOfLine+'">\
			  		<td ><input name="subBox" onclick="docheckAll()" type="checkbox" value="'+indexOfLine+'"></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="parameter['+indexOfLine+'].parameterName" id="parameterName" /></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="parameter['+indexOfLine+'].parameterCode" id="parameterCode" /></td>\
				    <td><input style="width:95%;" class="text01" type="text" name="parameter['+indexOfLine+'].parameterValue" id="parameterValue" /></td>\
				    <td><input style="width:95%;" class="text01" type="text" onkeyup="this.value=this.value.replace(/\\D/g,\'\')"  onafterpaste="this.value=this.value.replace(/\\D/g,\'\')"  name="parameter['+indexOfLine+'].orderId" id="orderId" /></td>\
				    <td>\
					    <select class="ui-select" id="sel_01"  name="parameter['+indexOfLine+'].allowUpdate" style="width:95%;">\
					    	<option selected="selected" value="1">是</option>\
					    	<option value="0">否</option>\
					    </select>\
					</td>\
				    <td><textarea style="width:95%;" class="text01" name="parameter['+indexOfLine+'].parameterDesc" id="parameterDesc"></textarea></td>\
				 </tr>';
	$("#parameter_tb").append(temp);
	indexOfLine++;
	$('.ui-select').ui_select();
	docheckAll();
}

function checkSubmit(){

	//参数类型名称
	if( $.trim($("#parameterTypeName").val())=="" ){
		animateAlert("table01","parameterTypeName","请填写参数类型名称" );
		return false;
	}
	if( $("#parameterTypeName").val().length > 100 ){
		animateAlert("table01","parameterTypeName","参数类型名称不得超过100个字" );
		return false;
	}
	//参数类型编码
	if( $.trim($("#parameterTypeCode").val())=="" ){
		animateAlert("table01","parameterTypeCode","请填写参数类型编码" );
		return false;
	}
	if( $("#parameterTypeCode").val().length > 100 ){
		animateAlert("table01","parameterTypeCode","参数类型编码不得超过100个字" );
		return false;
	}
	//参数类型值
	if( $.trim($("#parameterTypeValue").val())=="" ){
		animateAlert("table01","parameterTypeValue","请填写参数类型值" );
		return false;
	}
	if( $("#parameterTypeValue").val().length > 200 ){
		animateAlert("table01","parameterTypeValue","参数类型值不得超过200个字" );
		return false;
	}
	//参数类型描述
	if( $("#parameterTypeDesc").val().length > 500 ){
		animateAlert("table01","parameterTypeDesc","参数类型描述不得超过500个字" );
		return false;
	}
	//参数名称
	var $parameterName = $("input[id='parameterName']");
	for ( var int = 0; int < $parameterName.length; int++) {
		if( $.trim($($parameterName[int]).val())=="" ){
			alert("请填写参数名称" );
			return false;
		}
		if( $($parameterName[int]).val().length > 100 ){
			alert("参数名称不得超过100个字" );
			return false;
		}
	}
	//参数编号
	var $parameterCode = $("input[id='parameterCode']");
	for ( var int = 0; int < $parameterCode.length; int++) {
		if( $.trim($($parameterCode[int]).val())=="" ){
			alert("请填写参数编号" );
			return false;
		}
		if( $($parameterCode[int]).val().length > 100 ){
			alert("参数编号不得超过100个字" );
			return false;
		}
	}
	//参数值
	var $parameterValue = $("input[id='parameterValue']");
	for ( var int = 0; int < $parameterValue.length; int++) {
		if( $.trim($($parameterValue[int]).val())=="" ){
			alert("请填写参数值" );
			return false;
		}
		if( $($parameterValue[int]).val().length > 200 ){
			alert("参数值不得超过200个字" );
			return false;
		}
	}
	return true;
}

//滚动页面并弹框
function animateAlert(id,focusId,msg){
	//清除未选择，未填写的提示
	jQuery("html,body").find(".noselected_tips").remove();
	//滚动页面至表格
	jQuery("html,body").animate({scrollTop:jQuery("#"+id).offset().top},10);
	//提示
	if(focusId == "sel_01" || focusId == "sel_02" ) {
		$("#"+focusId).parent().parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}else{
		$("#"+focusId).parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}
}
