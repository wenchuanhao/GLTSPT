var config = {
//      'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf',
      'uploadScript'    : '',
//      'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',
      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
//      'buttonImg' : '/SRMC/dc/images/upload_001.png',
//      'buttonImg' : '/SRMC/dc/fxk/input.png',
      'buttonText' : '选择文件',
      'multi':false,//允许连续上传多个文件
      'uploadLimit' : 2,//一次性最多允许上传多少个,不设置的话默认为999个
      'queueSizeLimit' : 2,//一次性最多允许上传多少个,不设置的话默认为999个
//      'fileExt': '*.exe',
      'auto' : false,
      'method':'post',
//      'wmode':"transparent",
      'formData':{
    	  fileTempId:jQuery("#fileTempId").val(),
	      fileId:"",
	      type:"10",
	      busType:"FXK",
	      status:"1",//保存
	      isParent:"0" //config附件上传均不为父信息
      },
      //每个文件上传完成后触发
      'onUploadComplete'  : function(file, response) {
    	  if(response == "0") {
    		alert("附件上传失败！");
    	  }else{
    		  afterUploadFiles();
    	  }
      },
    //当所有文件上传完成后的操作
      'onQueueComplete':function(uploads) {
      },
    //当有文件正在上传时的操作
      'onProgress': function(file,event) {
      },
    //当添加待上传的文件数量达到设置的上限时的操作
      /*'onQueueFull': function (event,queueSizeLimit) {
          return false;
      },*/
    //当取消所有正在上传文件后的操作
      'onCancel': function(file) {
      } ,
      'onError': function(errorType,file) {
    	  alert("Error:" + errorType);
      } ,
	  'onAddQueueItem': function(file) {
		  	var fileType = file.type.toLowerCase();
		  	if(!checkFileType(file.name)){
		  		alert("文件限制为Excel！");
  	  			setTimeout(function() {
  	  				$("#uploadFiles").uploadifive('cancel',file);
  	  			}, 50);
  	  			return false;
		  	}
	  		if(!filesize(file.size,100)){ //100M * 1024 * 1024
	  			alert("该文件的容量大于100M！");
  	  			setTimeout(function() {
  	  				$("#uploadFiles").uploadifive('cancel',file);
  	  			}, 50);
  	  			return false;
	  		}
	  		
	  		if(confirm("注意：导入新风险库文档，旧数据将被清空！")){
	  			setTimeout(function() {
	  				$("#uploadFiles").uploadifive('upload');//上传
  	  			}, 50);
	  		}else{
	  			setTimeout(function() {
	  				$("#uploadFiles").uploadifive('cancel',file);//取消上传
  	  			}, 50);
	  			return false;
	  		}
//	  		$('#uploadFiles').uploadify('cancel', '*');新API
//	  		$('#uploadFiles').uploadify('upload', '*');
	  }
};

function setConfig(url){
	config.uploadScript = url;
	jQuery('#uploadFiles').uploadifive(config);
	document.getElementById("uploadifive-uploadFiles").className = 'btn_common01';
	document.getElementById("uploadifive-uploadFiles").style = '';
	document.getElementById("uploadifive-uploadFiles").style.position = 'relative';
	document.getElementById("uploadifive-uploadFiles").style.height = '19px';
}

/**
 * 文件上传之后的操作
 */
function afterUploadFiles(){
	var fileTempId = jQuery("#fileTempId").val();
	
	jQuery.ajax({
		url: basePath+"/afterUploadFiles",
		data: {
			fileTempId: fileTempId //文件ID
		},
		type: "POST",
		success: function(data) {
			if(data == 1){
				alert("导入成功！");
				window.location.href = basePath+"/list";
			}else{
				alert("上传文件失败，请稍后再试！");
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("上传文件失败，请联系管理员！");
		}
	});
}

//判断是否为excel类型
function checkFileType(name) {
	if(confirmEnding(name,".xlsx") || confirmEnding(name,".xls")){
		return true;
	}
	return false;
}
