//支撑单位联想查询
function autoCompletes(target){
	jQuery(target).autocomplete({
		source: function( request, response ) {
			jQuery.ajax({
				url: basePath+"/searchSupportorg",
				dataType: "json",
				data: {
					supportorgName: request.term
				},
				type: "POST",
				success: function(data) {
					if(data!=null){
			     		response(jQuery.map(data, function( item ) {
			     			return {
			     				value:item[0].supportorgName,
			     				supportorgName:item[0].supportorgName,
			     				supportorgId:item[0].supportorgId,
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
			jQuery("#supportorgName").val(ui.item.supportorgName);
			jQuery("#supportorgId").val(ui.item.supportorgId);
			return false;
		}
	});
}

function clean(target){
	if (event.keyCode == 13) {   
		$(target).val("");
    } 
}
//失去焦点事件
function onblurs(target){
	if(target.value==''||target.value=='请输入支撑单位'){
		jQuery(target).val('');
		target.style.color='#b6b6b6';
	}
}
//光标选中输入框事件
function onfocuses(target){
	if(target.value=='请输入支撑单位'){
		target.value='';
	}
	target.style.color='#333333';
	autoCompletes(target);
}

function autoCompletesCommandNum(target){
	jQuery(target).autocomplete({
		source: function( request, response ) {
			jQuery.ajax({
				url: basePath+"/searchCommandNum",
				dataType: "json",
				data: {
					code: request.term
				},
				type: "POST",
				success: function(data) {
					if(data!=null){
					
			     		response(jQuery.map(data, function( item ) {
			     			return {
			     				value:item[0].commandNum + " - " + item[0].commandType,
			     				commandType:item[0].commandType,//+" - "+item[0].account+" - "+item[1].orgName
			     				commandNum:item[0].commandNum
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
			jQuery(target).val(ui.item.commandNum);
			return false;
		}
	});
}

//合同编号联想查询
function autoCompletesProject(target){
		jQuery(target).autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: basePath+"/searchProjectByCode",
					dataType: "json",
					data: {
						code: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
						
				     		response(jQuery.map(data, function( item ) {
				     			return {
				     				value:item[0].proCode + " - " + item[0].proName,
				     			 	proId:item[0].proId,//+" - "+item[0].account+" - "+item[1].orgName
									proType:item[0].proType,
									proCode:item[0].proCode,
									proName:item[0].proName,
									proUser:item[0].proUser,
									contractAmount:item[0].contractAmount,
									constructionName:item[0].constructionName,
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
				jQuery("#contractCode").val(ui.item.proCode);
				jQuery("#contractName").val(ui.item.proName);
				//合同金额
				jQuery("#contractAmount").val(ui.item.contractAmount);
				//合同对方(施工单位,乙方单位)
				jQuery("#constructionName").val(ui.item.constructionName);
				return false;
			}
		});
	}
//关联查询投资项目
function autoProject(target){
	jQuery(target).autocomplete({
		source: function( request, response ) {
			jQuery.ajax({
				url: basePath+"/searchProByCode",
				dataType: "json",
				data: {
					code: request.term
				},
				type: "POST",
				success: function(data) {
					if(data!=null){
						
						response(jQuery.map(data, function( item ) {
							return {
								value:item[0].projectCode + " - " + item[0].projectName,
								projectId:item[0].projectId,//+" - "+item[0].account+" - "+item[1].orgName
								projectCode:item[0].projectCode,
								projectName:item[0].projectName,
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
			jQuery("#projectCode").val(ui.item.projectCode);
			jQuery("#projectName").val(ui.item.projectName);
			return false;
		}
	});
}

function setValue(returnValue){
	var v = returnValue.split("_");
	jQuery("#contractCode").val(v[1]);//合同编号
	jQuery("#contractName").val(v[2]);//合同名称
}

//function ev_selectList(){
//	var returnValue = window.showModalDialog(baseURL+"zxmHt/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
//	var v = returnValue.split("_");
//	jQuery("#contractCode").val(v[1]);//合同编号
//	jQuery("#contractName").val(v[2]);//合同名称
//}
function ev_selectList(){
	//判断是不是IE浏览器
	if(isIE()){
		 returnValue = window.showModalDialog(baseURL+"zxmHt/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
	}else{
		//其他浏览器
		window.open(baseURL+"zxmHt/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px");
	}
	putValue(returnValue);

}
//给父窗口元素赋值
function putValue(returnValue){
	var v = returnValue.split("_");
	jQuery("#contractCode").val(v[1]);//合同编号
	jQuery("#contractName").val(v[2]);//合同名称
}
function setSupportorg(returnValue){
	var v = returnValue.split("_");
	jQuery("#supportorgId").val(v[0]);//支撑单位id
	jQuery("#supportorgName").val(v[1]);//支撑单位名称
}

//function selectSupportorgName(){
//	var returnValue = window.showModalDialog(baseURL+"support/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
//	var v = returnValue.split("_");
//	jQuery("#supportorgId").val(v[0]);//支撑单位
//	jQuery("#supportorgName").val(v[1]);//支撑单位名称
//}
function selectSupportorgName(){
	//判断是不是IE浏览器
	if(isIE()){
		 returnValue = window.showModalDialog(baseURL+"support/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
	}else{
		//其他浏览器
		window.open(baseURL+"support/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px");
	}
	putValue(returnValue);

}
//给父窗口元素赋值
function putValue(returnValue){
	var v = returnValue.split("_");
	jQuery("#supportorgId").val(v[0]);//支撑单位
	jQuery("#supportorgName").val(v[1]);//支撑单位名称
}