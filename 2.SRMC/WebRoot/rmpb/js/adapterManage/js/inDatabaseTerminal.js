
function verifyCondition(){
	
	var verBrand = $("#adapterTerminalBrand").val();
	var verSpecification = $("#adapterTerminalSpecification").val();
	var verType = $("#adapterTerminalType").val();
	var verStartTime = $("#adapterTerminalIntime").val();
	var verEndTime = $("#adapterTerminalIntime2").val();
	var flag = true;
//	if("" == verBrand){
//		flag = false;
//		alert("请选择品牌。");
//	}else if("" == verSpecification){
//		alert("请选择型号。");
//		flag = false;
//	}else if("" == verType){
//		alert("请选择类型。");
//		flag = false;
//	}else if("" == verStartTime){
//		 alert("请选择开始时间。");
//		flag = false;
//	}else if("" == verEndTime){
//		alert("请选择结束时间。");
//		flag = false;
//	}else{
//		var sdate = new Date(verStartTime.replace("-","/").replace("-","/"));
//		var edate = new Date(verEndTime.replace("-","/").replace("-","/"));
//		if(edate < sdate){
//			alert("查询结束时间不能早于开始时间!");
//			flag = false;
//		}
//	}
	
	return flag;
	
}