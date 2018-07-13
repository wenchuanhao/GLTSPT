
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
				    <td><input style="width:95%;" class="text01" type="text" name="sendPersons['+indexOfLine+'].mobile" id="mobile" /></td>\
				    <td><input  onfocus="if(this.value==\'请填写姓名\'){this.value=\'\';}this.style.color=\'#333333\';autoCompletes(this);" onkeyup="clean();" onblur="if(this.value==\'\'||this.value==\'请填写姓名\'){this.value=\'请填写姓名\';this.style.color=\'#b6b6b6\';}" type="text" name="sendPersons['+indexOfLine+'].name" id="name" class="text01"  style="width:95%;" /></td>\
				    <td style="display:none;"><input style="width:95%;" class="text01" type="hidden" name="sendPersons['+indexOfLine+'].nameId" id="nameId" /></td>\
				    <td><textarea style="width:95%;" class="text01" name="sendPersons['+indexOfLine+'].detail" id="detail"></textarea></td>\
				 </tr>';
	$("#parameter_tb").append(temp);
	indexOfLine++;
	$('.ui-select').ui_select();
	docheckAll();
}


function autoCompletes(target){
		jQuery(target).autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: paths+"rulesController/searchUser",
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
								}
							}));
						}else{
							return false;
						}
					}
				});
			},
			minLength: 1,
			select: function( event, ui ) {
				jQuery(target).val(ui.item.userName);
				jQuery(target).parent('td').next().find('input').val(ui.item.userId);
//				jQuery(target).next().val(ui.item.userId);
				return false;
			}
		});
		
	}
	
function clean(){
//	if (event.keyCode == 13) {
        //js 监听对应的id
         //document.getElementById("demandFzrId_input_focus").value="";
//      } 
}

function checkSubmit(){

	//参数类型名称
	if( $.trim($("#pName").val())=="" ){
		animateAlert("table01","pName","请填写业务名称" );
		return false;
	}
	if( $("#pName").val().length > 50 ){
		animateAlert("table01","pName","业务名称不得超过50个字" );
		return false;
	}
	//参数类型编码
	if( $.trim($("#smsGroupNmae").val())=="" ){
		animateAlert("table01","smsGroupNmae","请填写短信组名称" );
		return false;
	}
	if( $("#smsGroupNmae").val().length > 50 ){
		animateAlert("table01","smsGroupNmae","短信组名称不得超过50个字" );
		return false;
	}
	//参数类型值
	if( $.trim($("#content").val())=="" ){
		animateAlert("table01","content","请填写发送内容值" );
		return false;
	}
	if( $("#content").val().length > 200 ){
		animateAlert("table01","content","发送内容值不得超过200个字" );
		return false;
	}
	//参数类型描述
	if( $("#detail").val().length > 200 ){
		animateAlert("table01","detail","备注不得超过200个字" );
		return false;
	}
	
	//nameID
	var $nameId = $("input[id='nameId']");
	for(var i = 0;i<$nameId.length;i++){
		var n = i+1;
		if($.trim($($nameId[i]).val())==""){
			alert("第"+n+"行姓名请重新选择！");
			return false;
		}
	}
	//参数编号
	var $parameterCode = $("input[id='mobile']");
	for ( var int = 0; int < $parameterCode.length; int++) {
		if( $.trim($($parameterCode[int]).val())=="" ){
			alert("请填写手机号码" );
			return false;
		}
		
		var reg = /(^(\d{3,4}-)?\d{7,8})$|(1[0-9]{10})$/;  
    	if(!reg.test($($parameterCode[int]).val())){
    		var n = int+1;
    		alert("第"+n+"行手机号码格式不正确");
    		return false;
    	}
	}
	//参数值
	var $parameterValue = $("input[id='name']");
	for ( var int = 0; int < $parameterValue.length; int++) {
		if( $.trim($($parameterValue[int]).val())=="" ){
			alert("请填写姓名" );
			return false;
		}
		if( $($parameterValue[int]).val().length > 10 ){
			alert("姓名不能超过10个字" );
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
