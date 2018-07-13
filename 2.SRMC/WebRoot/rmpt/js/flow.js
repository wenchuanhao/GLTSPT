var uploadstate = -1;
var uploadProcess = "no";

/***页面提交按钮***/
function ev_submit(){
	/*jQuery("#submitButton").fancybox({
		"autoScale"			: true,
		"transitionIn"		: "none",
		'centerOnScroll'	: true,
		"transitionOut"		: "none",
		"type"				: "iframe",
		"onStart"			:function(current,previous){
		   return false;
		}
	});*/

	/******save***/
	var url = "&demandFzrId=" + jQuery("#demandFzrId").val() + "&headUserName=1&applyId=" + jQuery("#applyId").val() + "&type=1&tgORbtg=TG&key="+Math.random();
	jQuery("#submitButton").fancybox({
							'href'				: ""+basePath+"/toCreateRequirementSubpage?1=1"+url,
							'width'    : '41%',    'height'   : '95%',
							 'autoScale'			: true,
							'transitionIn'		: 'none',
							'transitionOut'		: 'none',
							'type'				: 'iframe',
							'scrolling'			:'no',
							'centerOnScroll'	: true,
							'onStart'		:function(current, previous){
							if(uploadProcess == "ing") {
								alert("文件文件未上传完毕，请等文件上传完毕再行提交");
								return false;
							}
							if(uploadstate > 0 && !(confirm("有文件上传出错,是否继续提交"))) {
								return false;
							}
						}
				});
}

/***页面提交按钮***/
function ev_submit_BTG(){

	/******save***/
	var url = "&demandFzrId=" + jQuery("#demandFzrId").val() + "&headUserName=1&applyId=" + jQuery("#applyId").val() + "&type=1&tgORbtg=BTG&key="+Math.random();
	jQuery("#SP_BTG").fancybox({
							'href'				: ""+basePath+"/toCreateRequirementSubpage?1=1"+url,
							'width'    : '41%',    'height'   : '95%',
							 'autoScale'			: true,
							'transitionIn'		: 'none',
							'transitionOut'		: 'none',
							'type'				: 'iframe',
							'scrolling'			:'no',
							'centerOnScroll'	: true,
							'onStart'		:function(current, previous){
							if(uploadProcess == "ing") {
								alert("文件文件未上传完毕，请等文件上传完毕再行提交");
								return false;
							}
							if(uploadstate > 0 && !(confirm("有文件上传出错,是否继续提交"))) {
								return false;
							}
						}
				});
}

/**关闭fancybox**/
function doCloseSubpage(){
	jQuery.fancybox.close();
}

/**提交流程**/
function doSubmit(flowParam){
	jQuery.fancybox.close();
	jQuery("#form1").attr("action",basePath+"/submit?1=1&"+flowParam);
	jQuery("#form1").submit();
	//document.getElementById("form1").disabled=true;
	//jQuery("$#doSave").hide();
}

/**刷新页面**/
function doRefresh(){
}

//body onload 加载获取流程信息
function onloadFlowInfo(applyId,buttonIds){
	if(applyId == ""){
		return false;
	}
		 jQuery.ajax({
			type:"POST",
			async:false,
			url:""+basePath+"/getFlowInfo",
			data:"applyId="+applyId+"&type=0",
			success:function(data){
				var flowInsactor = eval("("+data+")"); 
				var flag = flowInsactor[0].flag;
				if(flag == 1){
					//隐藏按钮
					var bts = buttonIds.split("|");
					for(var i=0;i<bts.length;i++){
						jQuery("#"+bts[i]).hide();
					}
				}else{
					
				}
			},
			error:function(){
				alert("查找流程信息失败！");
			}
			});
}