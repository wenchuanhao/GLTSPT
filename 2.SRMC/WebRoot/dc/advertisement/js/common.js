//上传文件
var config = {
      'uploadScript'    : basePath11+'/uploadFile',
      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
      'multi':'true',//允许连续上传多个文件
      'buttonText' : '选择文件',
      'uploadLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
      'queueSizeLimit': 99,
      'auto' : true,
      'method':'post',
      'removeCompleted' : true,
      'formData':{
	      fileTempId:"",
	      fileId:"",
	      type:"13",
	      busType:"AD",
	      status:"1",//保存
	      isParent:"0" //config附件上传均不为父信息
      },
      'onUploadComplete'  : function(response) {
      	if(response == "0") {
      		alert("附件上传失败！");
      	}
      	
      	 var type = jQuery('#uploadFiles13').parent().attr("value");
      	 if(type=="8"){ 
      	 }else{
      		 queryFileList('#uploadFiles13');
      	 }
      },
    //当所有文件上传完成后的操作
      'onQueueComplete':function(event,data) {
      },
    //当有文件正在上传时的操作
      'onProgress': function(event,ID,fileObj) {
      },
    //当添加待上传的文件数量达到设置的上限时的操作
      /*'onQueueFull': function (event,queueSizeLimit) {
          return false;
      },*/
    //当取消所有正在上传文件后的操作
      'onCancel': function(fileObj) {
      } ,
      'onError': function(errorType,file) {
      	alert('The error was: ' + errorType);
      } ,
	  'onAddQueueItem': function(file) {
		  
		  	if(!filesize(file.size,100)){
		  		alert("该文件的容量大于100M！");
		  		setTimeout(function() { 
		  			cancel("#uploadFiles13",file);
  	  			}, 50);
		  	}
		  	
		  	if(confirmEnding(file.name,".gif") 
		  			|| confirmEnding(file.name,".jpg") 
		  			|| confirmEnding(file.name,".jpeg") 
		  			|| confirmEnding(file.name,".png")){
		  		
		  	}else{
		  		alert("图片广告限制为图片！");
		  		setTimeout(function() { 
		  			cancel("#uploadFiles13",file);
  	  			}, 50);
		  	}
		  	
		  	if($("#table01").find(".complete").length == 1){
	  			alert("广告图片不得超过1个！");
  	  			setTimeout(function() {
  	  				cancel("#uploadFiles13",file);
  	  			}, 50);
	  		}
		  	
		  	queryFileList("#uploadFiles13");
	  }
};

//页面上传文件初始化
jQuery(function() {
	//==============文件上传功能==============
		var fileTempId = jQuery("#fileTempId").val();
		config.formData.fileTempId = fileTempId;
		config.formData.type = "13";
		jQuery('#uploadFiles13').uploadifive(config);
});//初始化 end


/**
 * 查询文件列表
 * @param target
 */
function queryFileList(target){
  //当每个文件上传完成后的操作
  var fileTempId = jQuery("#fileTempId").val();
  var type = jQuery(target).parent().parent().attr("value");
  var url = basePath11+"/queryFileList";
  var params = {
  		'fileTempId':fileTempId,
  		'type':type
  };
  jQuery.post(url, params, function(data){
//       onClick="shade.style.display='block';Layer2.style.display='block';changePopSize();"
      var temp = '';
      $.each(data,function(k,v){
	        var file_size = '';
	        if(v.fileSize / 1024 > 1024){
	        	file_size = (v.fileSize / 1024 / 1024).toFixed(2) + "MB";
	        }else{
	        	file_size = (v.fileSize / 1024).toFixed(2) + "KB";
	        }
	        temp += '<tr class="fj_p'+type+'" value="'+v.types+'" id="'+v.fileId+'">\
				    	<th align="right">&nbsp;</th>\
				    	<td align="left">\
					    	<div class="fileName l"><a href="'+basePath11+'/downloadRulesFile?fileId='+v.fileId+'">'+v.fileName+'('+file_size+')</a></div><i><img onclick="javascript:deleteFileUpload(\''+v.fileId+'\')" src="/SRMC/dc/images/close_icon01.png" width="17" height="17" /></i>\
					    	<div style="margin:10px auto"><img src="'+basePath11+'/showImage?fileId='+v.fileId+'" height="230" width="330"></div>\
					    </td>\
					</tr>';
      });
      jQuery("#fi_box_tr_"+type).parent().find(".fj_p"+type).remove();
      jQuery("#fi_box_tr_"+type).after(temp);
  } , 'json');

}

//删除文件功能
function deleteFileUpload(v){
    if(confirm("确定要删除此文件？")){
        var url = basePath11 + "/delRulesFile";
        var params = {
        	fileId:v
        };
        jQuery.post(url, params, function(data){
            if(data == "1"){
                jQuery("#" + v).remove();
            }
        }, 'json');
    }
}
function checkFileType(suffix) {
	var result = "0";
	var type = suffix.toLowerCase();
	if(type == ".doc") {
		result = "1";
	} else if(type == ".docx") {
		result = "1";
	} else if(type == ".xls") {
		result = "1";
	} else if(type == ".xlsx") {
		result = "1";
	} else if(type == ".ppt") {
		result = "1";
	} else if(type == ".pptx") {
		result = "1";
	} else if(type == ".rar") {
		result = "1";
	} else if(type == ".zip") {
		result = "1";
	} else if(type == ".7z") {
		result = "1";
	} else if(type == ".jpg") {
		result = "1";
	} else if(type == ".jpeg") {
		result = "1";
	} else if(type == ".bmp") {
		result = "1";
	} else if(type == ".png") {
		result = "1";
	} else if(type == ".gif") {
		result = "1";
	} else if(type == ".txt") {
		result = "1";
	} else if(type == ".html") {
		result = "1";
	} else if(type == ".xhtml") {
		result = "1";
	} else if(type == ".xml") {
		result = "1";
	} else if(type == ".pdf") {
		result = "1";
	}

	return result;
}