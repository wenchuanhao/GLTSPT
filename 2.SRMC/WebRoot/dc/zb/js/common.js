

//用户可选择创建人
function autoCompletesColumn02(){
		jQuery("#column02").autocomplete({
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
									proUserName:item[0].proUserName,
									proCreateUser:item[0].proCreateUser,
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
				$("#column02").val(ui.item.proCode);
				return false;
			}
		});
	}

function autoCompletesProjectName(){
	jQuery("#projectName").autocomplete({
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
								proUserName:item[0].proUserName,
								proCreateUser:item[0].proCreateUser,
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
			$("#projectName").val(ui.item.proName);
			return false;
		}
	});
}




//用户可选择创建人
function autoCompletesColumn07(){
		jQuery("#column07").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: baseUrl+"rulesController/searchUser",
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
									orgName:item[1].orgName,
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
				jQuery("#column07").val(ui.item.userName);
				return false;
			}
		});
	}