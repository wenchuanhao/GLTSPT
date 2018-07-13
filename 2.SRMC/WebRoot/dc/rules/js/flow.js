/** 
 *  页面弹窗
 */
function iframeDialog(target,url) {
	jQuery('#'+target).fancybox({
		'href' : url,
//		'width' : '50%',
//		'height' : '50%',
		'autoScale' : true,
		'autoDimensions': true,
		'transitionIn' : 'none',
		'transitionOut' : 'none',
		'type' : 'iframe',
		'scrolling' : 'yes',
		'centerOnScroll' : true,
		'onStart' : function(current, previous) {
		}
	});
}


/**关闭fancybox**/
function closeIframeDialog(){
	jQuery.fancybox.close();
}



//上传文档
function documentUpload(){
	var temp =  jQuery('.fancybox_Upload');
	$.each(temp,function(k,v){
		iframeDialog($(v).attr("id"), basePath + "/documentUpload?rulesId="+$(v).attr("value"));
	});
}
//制度废止
function repealRules(){
	var temp = jQuery('.fancybox_Repeal');
	$.each(temp,function(k,v){
		iframeDialog($(v).attr("id"), basePath + "/repealViewRules?rulesId="+$(v).attr("value"));
	});
}

//查看制度相关文档
function viewFileUpload(){
	var temp = jQuery('.fancybox_viewFile');
	$.each(temp,function(k,v){
		iframeDialog($(v).attr("id"), basePath + "/viewFileUpload?rulesId="+$(v).attr("value"));
	});
}


//制度退回
function returnRemain(){
	var temp = jQuery('.fancybox_Return');
	$.each(temp,function(k,v){
		iframeDialog($(v).attr("id"), basePath + "/returnViewRemain?rulesId="+$(v).attr("value"));
	});
}


//文档管理
function rulesDocUpload(busTypes){
	var temp = jQuery('.fancybox_Docupload');
	$.each(temp,function(k,v){
		iframeDialog($(v).attr("id"), basePath + "/docUploads?busTypes="+busTypes+"&fileId="+$(v).attr("value"));
	});
}


//批量退回
function batchReturnInput(){
	var $subBoxChecks = $("input[name='subBox']:checked");
	var rulesIds = '';
	$.each($subBoxChecks,function(k,v){
		if(k == 0){
			rulesIds += $(v).val();
		}else{
			rulesIds += ","+$(v).val();
		}
	});
	jQuery('#batchReturnInput').fancybox({
		'href' : basePath + '/returnViewRemain?rulesId='+rulesIds,
//		'height' : '50%',
		'autoScale' : true,
		'transitionIn' : 'none',
		'transitionOut' : 'none',
		'type' : 'iframe',
		'scrolling' : 'yes',
		'centerOnScroll' : true,
		'onStart' : function(current, previous) {
			if($subBoxChecks.length == 0){
				alert("请至少选中一项制度");
				return false;
			}
		}
	});
}
//批量废止
function batchRepealInput(){
	var $subBoxChecks = $("input[name='subBox']:checked");
	var rulesIds = '';
	$.each($subBoxChecks,function(k,v){
		if(k == 0){
			rulesIds += $(v).val();
		}else{
			rulesIds += ","+$(v).val();
		}
	});
	jQuery('#batchRepealInput').fancybox({
		'href' : basePath + '/repealViewRules?rulesId='+rulesIds,
//		'height' : '50%',
		'autoScale' : true,
		'transitionIn' : 'none',
		'transitionOut' : 'none',
		'type' : 'iframe',
		'scrolling' : 'yes',
		'centerOnScroll' : true,
		'onStart' : function(current, previous) {
			if($subBoxChecks.length == 0){
				alert("请至少选中一项制度");
				return false;
			}
			var flag = true;
			$.each($subBoxChecks,function(k,v){
				if($(v).attr("val") != 3){
					flag =  false;
				}
			});
			if(!flag){
				alert("只能废止已发布制度");
				return false;
			}
		}
	});
}


function uploadFileInput(){
	var $subBoxChecks = $("input[name='subBox']:checked");
	
	jQuery('#uploadFileInput').fancybox({
		'href' : basePath + '/documentUpload?rulesId='+$($subBoxChecks[0]).attr("value"),
//		'height' : '50%',
		'autoScale' : true,
		'transitionIn' : 'none',
		'transitionOut' : 'none',
		'type' : 'iframe',
		'scrolling' : 'yes',
		'centerOnScroll' : true,
		'onStart' : function(current, previous) {
			
			if($subBoxChecks.length != 1){
				alert("请选中一项制度");
				return false;
			}
			
			if($($subBoxChecks[0]).attr("val") != 3){
				alert("只能对已发布制度上传文档");
				return false;
			}
		}
	});
}

//数据字典编辑
function dictEdit(){
	var temp = jQuery('.fancybox_Dictedit');
	$.each(temp,function(k,v){
		iframeDialog($(v).attr("id"), basePath + "/editDict?id="+$(v).attr("value"));
	});
}
//数据权限配置
function datasourceConfigure(){
	var temp = jQuery('.fancybox_Configure');
	$.each(temp,function(k,v){
		iframeDialog($(v).attr("id"), basePath + "/datasourceConfigure?id="+$(v).attr("value")+"&bustype="+$(v).attr("bustype"));
	});
}

//数据审核-不通过
function returnDatasourceRemain(){
	var temp = jQuery('.fancybox_Return');
	$.each(temp,function(k,v){
		iframeDialog($(v).attr("id"), basePath + "/returnDatasourceViewRemain?recordId="+$(v).attr("value"));
	});
}

//数据审核-批量不通过
function batchReturnDatasourceRemain(){
	var $subBoxChecks = $("input[name='subBox']:checked");
	var rulesIds = '';
	$.each($subBoxChecks,function(k,v){
		if(k == 0){
			rulesIds += $(v).val();
		}else{
			rulesIds += ","+$(v).val();
		}
	});
	jQuery('#batchReturnDatasourceRemain').fancybox({
		'href' : basePath + '/returnDatasourceViewRemain?recordId='+rulesIds,
//		'height' : '50%',
		'autoScale' : true,
		'transitionIn' : 'none',
		'transitionOut' : 'none',
		'type' : 'iframe',
		'scrolling' : 'yes',
		'centerOnScroll' : true,
		'onStart' : function(current, previous) {
			if($subBoxChecks.length == 0){
				alert("请至少选中一项数据");
				return false;
			}
		}
	});
}

//修订对话框弹窗
function modalDialog(){
	var temp = jQuery('.fancybox_Modal');
	$.each(temp,function(k,v){
		$(v).fancybox({
			'modal':false,
			'overlayShow':true,
			'hideOnOverlayClick':false,
			'hideOnContentClick':false,
			'enableEscapeButton':false,
			'showCloseButton':false,
			'centerOnScroll':true,
			'autoScale':false,
			'width':540,
			'height':360,
			'onStart' : function(current, previous) {
				$("#dialog_input").val($(v).attr('value'));
			}
		});
	});
}

//修订制度提交
function reviseRules(){
	var id = $("#dialog_input").val();
	window.location.href= basePath + "/reviseRules?rulesId=" + id;
}


//周报审核-不通过
function returnZbRemain(){
	var temp = jQuery('.fancybox_Return');
	$.each(temp,function(k,v){
		iframeDialog($(v).attr("id"), basePath + "/returnZbRemain?id="+$(v).attr("value"));
	});
}
