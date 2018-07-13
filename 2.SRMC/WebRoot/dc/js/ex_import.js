var config = {
	  'buttonClass'  : 'uploadifive',
//     'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf',
      'uploadScript'    : '',
//      'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',
      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
//      'buttonImg' : '/SRMC/dc/images/upload_001.png',
//      'buttonImg' : '/SRMC/rmpb/images/tb_n_02b.png',
      'buttonText' : '选择文件',
      'multi':'true',//允许连续上传多个文件
      'uploadLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
      'queueSizeLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
//      'fileExt': '*.exe',
      'auto' : true,
      'removeCompleted' : true,
      'method':'post',
//      'wmode':"transparent",
      'formData':{
    	  
      },
    //每个文件上传完成后触发
      'onUploadComplete'  : function(file, response) {
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
    	  alert('The error was: ' + errorType);
      } ,
	  'onAddQueueItem': function(file) {
		  	if(!checkFileType(file.name)){
		  		alert("文件限制为Excel！");
  	  			setTimeout(function() {
  	  				cancel("#uploadFiles",file);
  	  			}, 50);
		  	}
	  		if(!filesize(file.size,100)){ //100M * 1024 * 1024
	  			alert("该文件的容量大于100M！");
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
//判断是否为excel类型
function checkFileType(name) {
	if(confirmEnding(name,".xlsx") || confirmEnding(name,".xls")){
		return true;
	}
	return false;
}
