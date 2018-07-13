//上传文件
var config = {
	  'buttonClass'  : 'uploadifive',
//      'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf',
      'uploadScript'    : basePath11+'/uploadFile',
//      'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',
      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
      'buttonText' : '选择文件',
//      'buttonImg' : '/SRMC/dc/images/cloud.png',
      'multi':'true',//允许连续上传多个文件
      'uploadLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
      'queueSizeLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
//      'fileExt': '*.exe',
      'removeCompleted' : true,
      'auto' : true,
      'method':'post',
//      'wmode':"transparent",
      'formData':{
	      fileTempId:"",
	      fileId:"",
	      type:"11",
	      busType:"ZL",
	      status:"1",//保存
	      isParent:"0" //config附件上传均不为父信息
      },
      //每个文件上传完成后触发
      'onUploadComplete'  : function(file, response) {
      	if(response == "0") {
      		alert("附件上传失败！");
      	}
      	
      	 var type = jQuery("#uploadFiles2").parent().parent().attr("value");
      	 if(type=="8"){
      	 }else{
      		 queryFileList("#uploadFiles2");
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
    	  alert('The error was: ' + errorType);
      } ,
	  'onAddQueueItem': function(file) {
		  	var type = jQuery("#uploadFiles2").parent().parent().attr("value");
		  	if(type == 1 && !checkFileType_1(name) ){
		  		alert("制度文件限制为Word、PDF！");
  	  			setTimeout(function() {
  	  				$("#uploadFiles2").uploadifive('cancel',file);
  	  			}, 50);
		  	}
		  	
		  	if(type == 3 && !checkFileType_3(name) ){
		  		alert("发布依据限制为Word、PDF、图片！");
  	  			setTimeout(function() {
  	  				$("#uploadFiles2").uploadifive('cancel',file);
  	  			}, 50);
		  	}
	  		if(checkFileTypeAll(file.name) == "0") {//不允许上传的类型
	  			alert("该文件类型不允许上传！");
  	  			setTimeout(function() {
  	  				$("#uploadFiles2").uploadifive('cancel',file);
  	  			}, 50);
	  	  	}
	  		if(!filesize(file.size,100)){ //100M * 1024 * 1024
	  			alert("该文件的容量大于100M！");
  	  			setTimeout(function() {
  	  				$("#uploadFiles2").uploadifive('cancel',file);
  	  			}, 50);
	  		}
	  		if(type == 1 && $("#table01").find(".fj_p1").length == 1){
	  			alert("制度文件不得超过1个！");
  	  			setTimeout(function() {
  	  				$("#uploadFiles2").uploadifive('cancel',file);
  	  			}, 50);
	  		}
	  }
};

//页面上传文件初始化
jQuery(function() {
	//==============文件上传功能==============
		var fileTempId = jQuery("#fileTempId").val();
		config.formData.fileTempId = fileTempId;
		config.formData.type = "11";
		jQuery('#uploadFiles2').uploadifive(config);
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
