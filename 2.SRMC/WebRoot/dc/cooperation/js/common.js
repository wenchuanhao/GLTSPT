function changeBusTypes(frommtarget,totarget){
	var objData = $(totarget).data('ui-select');
	var busTypes = $("#busTypes").val();
	var parentDatasourceId = "ROOT";
	if(frommtarget != "ROOT"){
		parentDatasourceId = $(frommtarget).val();
	}
	if(parentDatasourceId == null || parentDatasourceId == ""){
		objData.removeAll("请选择");
		objData.add("","请选择");
		return;
	}
	jQuery.ajax({
		url: basePath+"/changeBusTypes",
		dataType: "json",
		data: {
			parentDatasourceId: parentDatasourceId,
			busTypes:busTypes
		},
		type: "POST",
		success: function(data) {
			if(data!=null){
				objData.removeAll("请选择");
				objData.add("","请选择");
				$.each(data,function(k,v){
					objData.add(v.datasourceId,v.datasourceName);
				});
			}else{
				return false;
			}
		}
	});
}
/**
 * 联动查询带权限
 * @param frommtarget
 * @param totarget
 */
function changeBusTypesByList(frommtarget,totarget){
	var objData = $(totarget).data('ui-select');
	var busTypes = $("#busTypes").val();
	var parentDatasourceId = "ROOT";
	if(frommtarget != "ROOT"){
		parentDatasourceId = $(frommtarget).val();
	}
	if(parentDatasourceId == null || parentDatasourceId == ""){
		objData.removeAll("请选择");
		objData.add("","请选择");
		$("#import_div").hide();
		$("#enter_div").hide();
		ev_cancel();
		return;
	}
	var data = [];
	$.each(sonList,function(k,v){
		if(v.parentDatasourceId == parentDatasourceId){
			data.push(v);
		}
	});
	
	if(data!=null){
		objData.removeAll("请选择");
		objData.add("","请选择");
		$.each(data,function(k,v){
			objData.add(v.datasourceId,v.datasourceName);
		});
	}else{
		return false;
	}
}

function changeDatasourceSource(target,chaget){
	var datasourceId = $(target).val();
	var data;
	$.each(sonList,function(k,v){
		if(v.datasourceId == datasourceId){
			data = v;
			return false;
		}
	});
	if(!data){
		$("#import_div").hide();
		$("#enter_div").hide();
		ev_cancel();
		return;
	}
	//记录数据来源
	$("#datasourceSource").val(data.datasourceSource);
	//判断录入或导入
	if(data.datasourceSource == "1"){
		//查询录入字段
		jQuery.ajax({
			url: basePath+"/queryEnterById",
			data: {
				datasourceId:datasourceId
			},
			dataType: "json",
			type: "POST",
			success: function(data) {
				var temp = '';
				$.each(data,function(k,v){
					var value = "";
					if(v[2] == '月份'){
						var d = new Date()
						if(d.getMonth() < 10){
							value = d.getFullYear() + "-0" + (d.getMonth());
						}else if(d.getMonth() == 0){
							value = (d.getFullYear()-1) + "-12";
						}
					}
					temp += '<tr>\
						<th width="30%" align="right"><b>*</b>'+v[2]+'：</th>\
						<td>\
						<input name="values"  value="'+value+'" id="'+v[1]+'"  type="text" class="text01" style="width:500px;" placeholder="'+v[2]+'" />\
						</td>\
						</tr>';
				});
				temp += '<tr>\
						 	<th colspan="2" align="center" height="50">\
					   			<input name="" type="button" class="btn_common02" onclick="ev_save(2)" value="提 交" /> \
					   		 	<input name="" type="button" class="btn_common01" onclick="ev_cancel2();" value="重 置" />\
					   		</th>\
					    </tr>';
				$("#table_of_enter").html(temp);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
		$(chaget).val("录入");
	}else if(data.datasourceSource == "2"){
		$(chaget).val("导入");
	}
	ev_cancel();
	toggleDivs('#datasourceId');
}

function toggleDivs(target){
	var datasourceId = $(target).val();
	var data;
	$.each(sonList,function(k,v){
		if(v.datasourceId == datasourceId){
			data = v;
			return false;
		}
	});
	if(data.datasourceSource == "1"){
		$("#import_div").hide();
		$("#enter_div").show();
		
	}else if(data.datasourceSource == "2"){
		$("#import_div").show();
		$("#enter_div").hide();
	}
}

function afterUploadFiles(){
	var fileTempId = jQuery("#fileTempId").val();
	var datasourceId = $("#datasourceId").val();
	var busTypes = $("#busTypes").val();
	var month = $("#month").val();
	jQuery.ajax({
		url: basePath+"/afterUploadFiles",
		data: {
			fileTempId: fileTempId,
			busTypes:busTypes,
			datasourceId:datasourceId,
			month:month		//月份
		},
		type: "POST",
		success: function(data) {
			//导入后数据默认无错误
			$('#errFlag').val("0");
			if(data == "0"){
				alert("导入数据有误，请核对后重新导入！");
				return false;
			}else if(data == "1"){
				alert("导入数据不能为空！");
				return false;
			}else if(data != "0" && data != "1"){
				$("#datasourceRecordsId").val(data);
				document.getElementById("iframepage").src=basePath+"/dataEnterDetailOfSession?flag=1&id="+data;
				//iframe自适应大小
				document.getElementById("iframepage").onload=function(){
					var ifm= document.getElementById("iframepage");   
					var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument;   
					if(ifm != null && subWeb != null) {
						ifm.height = subWeb.body.scrollHeight;
						ifm.width = '100%';//subWeb.body.scrollWidth;
					}
				};
			}else{
				alert("导入数据有误，请核对后重新导入！");
				return false;
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
		}
	});
}

function changeChildDatasource(target){
	$("#pageIndex").val(1);
	var datasourceId = $(target).val();
	jQuery.ajax({
		type:"POST",
		async:false,
		url: basePath+"/viewDatasourceType",
		data:"datasourceId=" + datasourceId,
		dataType:"json",
		success:function(result){
			var myDate = new Date();
			$("#flag_month").val(result.datasourceSource);
			if(result.datasourceSource == "2"){
				var monthstr = myDate.getMonth();
				if(monthstr == 0){
					$("#month").val(myDate.getFullYear()+"-01");
				}else{
					$("#month").val(myDate.getFullYear()+"-"+ ("00" + (myDate.getMonth())).substr(("" + (myDate.getMonth())).length));
				}
	
			}else{
				$("#month").val(myDate.getFullYear());
			}
			

		},
		error:function(){
		}
	});
}
function doWdatePicker(){
	$("#pageIndex").val(1);
	var flag_month = $("#flag_month").val();
	if(flag_month == "2"){
		WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM',alwaysUseStartDate:true});
	}else{
		WdatePicker({startDate:'%y', dateFmt:'yyyy',alwaysUseStartDate:true});
	}
}