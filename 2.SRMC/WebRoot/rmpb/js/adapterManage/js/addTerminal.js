
function proTypeOnchange(){
	var typeVal = jQuery("#adapterTerminalProtype").val();
	if(typeVal.indexOf("双卡双待")<0){
		jQuery("#adapterTerminalIsdduble").val("0");
	}else{
		jQuery("#adapterTerminalIsdduble").val("1");
	}
	
	/*if( "TD高端双卡双待手机" == typeVal ||
		"TD中端双卡双待手机" == typeVal||
		"TD普及双卡双待手机" == typeVal||
		"TD低端双卡双待手机" == typeVal){
		jQuery("#adapterTerminalIsdduble").val("1");
	}else{
		jQuery("#adapterTerminalIsdduble").val("0");
	}*/
}

function isWifiOnchange(){
	var isWifi = jQuery("#adapterTerminalIswifi").val();
	if("1" == isWifi || "" == isWifi){
		jQuery("#adapterTerminalWifimodel").show();
	}else{
		jQuery("#adapterTerminalWifimodel").val("");
		jQuery("#adapterTerminalWifimodel").hide();
	}
	
}

// adapterTerminalIsdduble 由下拉改为单选  20140729
function proTypeOnchange_2(){
	var typeVal = jQuery("#adapterTerminalProtype").val();
	if(typeVal.indexOf("双卡双待")<0){
		jQuery(":radio[name='adapterTerminalIsdduble'][value='0']").attr('checked',true);
	}else{
		jQuery(":radio[name='adapterTerminalIsdduble'][value='1']").attr('checked',true);
	}
}
