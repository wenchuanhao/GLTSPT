var config = {
      'uploadScript'    : '',
      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
      'buttonText' : '文件上传',
      'multi':'true',//允许连续上传多个文件
      'uploadLimit' : 50,//一次性最多允许上传多少个,不设置的话默认为999个
      'queueSizeLimit': 50,
      'auto' : true,
      'method':'post',
      'removeCompleted':true,
      'formData':{
    	  
      },
      'onUploadComplete'  : function(file, response) {
    	  window.location.href = basePath+"/purchaseExcelList";
      },
    //当所有文件上传完成后的操作
      'onQueueComplete':function(uploads) {
      },
    //当有文件正在上传时的操作
      'onProgress': function(file,event) {
      },
    //当添加待上传的文件数量达到设置的上限时的操作
     /* 'onQueueFull': function (event,queueSizeLimit) {
          return false;
      },*/
    //当取消所有正在上传文件后的操作
      'onCancel': function(file) {
	   } ,
      'onError': function(errorType,file) {
      	alert(errorType + "Error:模板不匹配,请下载模板");
      	return false;
      } ,
	  'onAddQueueItem': function(file) {
		  	if(!filesize(file.size,100)){
		  		alert("该文件的容量大于100M！");
		  		setTimeout(cancel("#uploadFiles"), 50);
		  	}
		  	
		  	if(confirmEnding(file.name,".xls") || confirmEnding(file.name,".xlsx")){
		  		
		  	}else{
		  		alert("文件限制为Excel！");
		  		setTimeout(cancel("#uploadFiles"), 50);
		  	}
	  }
};

function setConfig(url){
	config.uploadScript = url;
	jQuery('#uploadFiles').uploadifive(config);
}
