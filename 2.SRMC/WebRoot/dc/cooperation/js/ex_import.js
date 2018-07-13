var config = {
      'uploadScript'    : '',
      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
//      'buttonImg' : '/SRMC/dc/images/upload_001.png',
      'multi':false,//允许连续上传多个文件
      'buttonText' : '选择文件',
      'uploadLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
      'queueSizeLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
      'auto' : true,
      'method':'post',
      'removeCompleted':true,
      'formData':{
    	  fileTempId:jQuery("#fileTempId").val(),
	      fileId:"",
	      type:"9",
	      busType:"DS",
	      status:"1",//保存
	      isParent:"0" //config附件上传均不为父信息
      },
      'onUploadComplete'  : function(response) {
    	  if(response == "0") {
    		alert("附件上传失败！");
    	  }else{
    		  alert("附件上传成功！");
    		  afterUploadFiles();
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
		  			cancel("#uploadFiles",file);
	  			}, 50);
		  	}
		    
		    if(confirmEnding(file.name,".xlsx") 
		  		|| confirmEnding(file.name,".xls") 
		  		){
		  		
		  	}else{
		  		alert("文件限制为Excel！");
		  		setTimeout(function() { 
		  			cancel("#uploadFiles",file);
  	  			}, 50);
		  	}
		  
	  }
};

function setConfig(url){
	config.uploadScript = url;
	jQuery('#uploadFiles').uploadifive(config);
}
