
var config = {
	  'buttonClass'  : 'uploadifive',
      'uploadScript'    : basePath+'fileUpload/saveOrUpdate?',
      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
      'buttonText' : '选择文件',
      'multi':'true',//允许连续上传多个文件
      'uploadLimit' : 50,//一次性最多允许上传多少个,不设置的话默认为999个
      'queueSizeLimit': 50,
      'auto' : false,
      'method':'post',
      'wmode':"transparent",
      'formData':{
    	  id:"",
    	  parentId:"",
    	  column07:"",
    	  column01:"",
    	  column10:"",
    	  jsxmId:"",
    	  queueSize:""
      },      
      'onUploadComplete'  : function(file, response) {
      	if(response == "0") {
      		alert("附件上传失败！");
      	}
      },
    //当所有文件上传完成后的操作
      'onQueueComplete':function(event,data) {
    	  alert("上传成功");
    	  if(isIE()){
    		  window.dialogArguments.ev_refresh($("#parentId").val());
	  	  }else{
	  		  window.opener.ev_refresh($("#parentId").val());
	  	  }
    	  ev_close();
      },
    //当有文件正在上传时的操作
      'onProgress': function(event,ID,fileObj) {
      },
    //当添加待上传的文件数量达到设置的上限时的操作
      /*'onQueueFull': function (event,queueSizeLimit) {
    	  alert("上传的文件数量达到设置的上限");
          return false;
      },*/
    //当取消所有正在上传文件后的操作
      'onCancel': function(fileObj) {
    	  if($.trim($("#column01").val()) == fileObj.name){
    		  $("#fileName").val("");
    		  $("#column01").val("");
    	  }
      } ,
      'onError': function(errorType,fileObj) {
    	  if(error.type.indexOf("Size") != -1){
    		  alert(fileObj.name+".\n大小超过3G，不能上传");
    		  $("#fileName").val("");
    		  $("#column01").val("");
    		  $("#uploadFiles1").uploadifyClearQueue();
    	  }else{
    		  alert("Error:" +errorType);
    	  }
      } ,
	  'onAddQueueItem': function(file) {
		  $("#fileName").val(file.name);
		  if($("#column01").val() == ""){
			  $("#column01").val(file.name);
		  }
	  }
};

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