/**
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
 * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18  
 */
Date.prototype.format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

//制度新增上传
var config_1 = {
		  'buttonClass'  : 'uploadifive',
//	      'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf',
	      'uploadScript'    : basePath+'/uploadFile',
//	      'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',
	      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
//	      'buttonImg' : '/SRMC/dc/images/cloud.png',
	      'buttonText' : '选择文件',
	      'multi':'true',//允许连续上传多个文件
	      'uploadLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
	      'queueSizeLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
//	      'fileExt': '*.exe',
	      'auto' : true,
	      'removeCompleted' : true,
	      'method':'post',
//	      'wmode':"transparent",
	      'formData':{
		      fileTempId:"",
		      fileId:"",
		      type:"",
		      busType:"",
		      status:"1",//保存
		      isParent:"0" //config附件上传均不为父信息
	      },
	      //每个文件上传完成后触发
	      'onUploadComplete'  : function(file, response) {
	      	if(response == "0") {
	      		alert("附件上传失败！");
	      	}
	      	
	      	 var type = jQuery("#uploadFiles1").parent().parent().attr("value");
	      	 if(type=="8"){ 
	      	 }else{
	      		 queryFileList("#uploadFiles1");
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
			  	var type = jQuery("#uploadFiles1").parent().parent().attr("value");
			  	if(type == 1 && !checkFileType_1(file.name) ){
			  		alert("制度文件限制为Word、PDF！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles1",file);
	  	  			}, 50);
			  	}
			  	
			  	if(type == 3 && !checkFileType_3(file.name) ){
			  		alert("发布依据限制为Word、PDF、图片！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles1",file);
	  	  			}, 50);
			  	}
			  	
//			  	if(type == 12 && "*.gif; *.jpg;*.jpeg;*.png".indexOf(fileType) < 0 ){
//			  		alert("文件（指令）扫描件限制为图片！");
//	  	  			setTimeout(function() {
//	  	  				jQuery(e.target).uploadifyCancel(queueId);
//	  	  			}, 50);
//			  	}
			  	
		  		if(type != 12 && checkFileTypeAll(file.name) == "0") {//不允许上传的类型
		  			alert("该文件类型不允许上传！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles1",file);
	  	  			}, 50);
		  	  	}
		  		if(!filesize(file.size,100)){ //100M * 1024 * 1024
		  			alert("该文件的容量大于100M！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles1",file);
	  	  			}, 50);
		  		}
		  		if(type == 1 && $("#table01").find(".fj_p1").length == 1){
		  			alert("制度文件不得超过1个！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles1",file);
	  	  			}, 50);
		  		}
		  }
	};

var config_2 = {
		  'buttonClass'  : 'uploadifive',
//	      'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf',
	      'uploadScript'    : basePath+'/uploadFile',
//	      'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',
	      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
//	      'buttonImg' : '/SRMC/dc/images/cloud.png',
	      'buttonText' : '选择文件',
	      'multi':'true',//允许连续上传多个文件
	      'uploadLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
	      'queueSizeLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
//	      'fileExt': '*.exe',
	      'auto' : true,
	      'removeCompleted' : true,
	      'method':'post',
//	      'wmode':"transparent",
	      'formData':{
		      fileTempId:"",
		      fileId:"",
		      type:"",
		      busType:"",
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
			  	if(type == 1 && !checkFileType_1(file.name) ){
			  		alert("制度文件限制为Word、PDF！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles2",file);
	  	  			}, 50);
			  	}
			  	
			  	if(type == 3 && !checkFileType_3(file.name) ){
			  		alert("发布依据限制为Word、PDF、图片！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles2",file);
	  	  			}, 50);
			  	}
			  	
//			  	if(type == 12 && "*.gif; *.jpg;*.jpeg;*.png".indexOf(fileType) < 0 ){
//			  		alert("文件（指令）扫描件限制为图片！");
//	  	  			setTimeout(function() {
//	  	  				jQuery(e.target).uploadifyCancel(queueId);
//	  	  			}, 50);
//			  	}
			  	
		  		if(type != 12 && checkFileTypeAll(file.name) == "0") {//不允许上传的类型
		  			alert("该文件类型不允许上传！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles2",file);
	  	  			}, 50);
		  	  	}
		  		if(!filesize(file.size,100)){ //100M * 1024 * 1024
		  			alert("该文件的容量大于100M！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles2",file);
	  	  			}, 50);
		  		}
		  		if(type == 1 && $("#table01").find(".fj_p1").length == 1){
		  			alert("制度文件不得超过1个！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles2",file);
	  	  			}, 50);
		  		}
		  }
	};

var config_3 = {
		  'buttonClass'  : 'uploadifive',
//	      'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf',
	      'uploadScript'    : basePath+'/uploadFile',
//	      'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',
	      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
//	      'buttonImg' : '/SRMC/dc/images/cloud.png',
	      'buttonText' : '选择文件',
	      'multi':'true',//允许连续上传多个文件
	      'uploadLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
	      'queueSizeLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
//	      'fileExt': '*.exe',
	      'auto' : true,
	      'removeCompleted' : true,
	      'method':'post',
//	      'wmode':"transparent",
	      'formData':{
		      fileTempId:"",
		      fileId:"",
		      type:"",
		      busType:"",
		      status:"1",//保存
		      isParent:"0" //config附件上传均不为父信息
	      },
	      //每个文件上传完成后触发
	      'onUploadComplete'  : function(file, response) {
	      	if(response == "0") {
	      		alert("附件上传失败！");
	      	}
	      	
	      	 var type = jQuery("#uploadFiles3").parent().parent().attr("value");
	      	 if(type=="8"){ 
	      	 }else{
	      		 queryFileList("#uploadFiles3");
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
			  	var type = jQuery("#uploadFiles3").parent().parent().attr("value");
			  	if(type == 1 && !checkFileType_1(file.name) ){
			  		alert("制度文件限制为Word、PDF！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles3",file);
	  	  			}, 50);
			  	}
			  	
			  	if(type == 3 && !checkFileType_3(file.name) ){
			  		alert("发布依据限制为Word、PDF、图片！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles3",file);
	  	  			}, 50);
			  	}
			  	
//			  	if(type == 12 && "*.gif; *.jpg;*.jpeg;*.png".indexOf(fileType) < 0 ){
//			  		alert("文件（指令）扫描件限制为图片！");
//	  	  			setTimeout(function() {
//	  	  				jQuery(e.target).uploadifyCancel(queueId);
//	  	  			}, 50);
//			  	}
			  	
		  		if(type != 12 && checkFileTypeAll(file.name) == "0") {//不允许上传的类型
		  			alert("该文件类型不允许上传！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles3",file);
	  	  			}, 50);
		  	  	}
		  		if(!filesize(file.size,100)){ //100M * 1024 * 1024
		  			alert("该文件的容量大于100M！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles3",file);
	  	  			}, 50);
		  		}
		  		if(type == 1 && $("#table01").find(".fj_p1").length == 1){
		  			alert("制度文件不得超过1个！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles3",file);
	  	  			}, 50);
		  		}
		  }
	};

var config_4 = {
		  'buttonClass'  : 'uploadifive',
//	      'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf',
	      'uploadScript'    : basePath+'/uploadFile',
//	      'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',
	      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
//	      'buttonImg' : '/SRMC/dc/images/cloud.png',
	      'buttonText' : '选择文件',
	      'multi':'true',//允许连续上传多个文件
	      'uploadLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
	      'queueSizeLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
//	      'fileExt': '*.exe',
	      'auto' : true,
	      'removeCompleted' : true,
	      'method':'post',
//	      'wmode':"transparent",
	      'formData':{
		      fileTempId:"",
		      fileId:"",
		      type:"",
		      busType:"",
		      status:"1",//保存
		      isParent:"0" //config附件上传均不为父信息
	      },
	      //每个文件上传完成后触发
	      'onUploadComplete'  : function(file, response) {
	      	if(response == "0") {
	      		alert("附件上传失败！");
	      	}
	      	
	      	 var type = jQuery("#uploadFiles4").parent().parent().attr("value");
	      	 if(type=="8"){ 
	      	 }else{
	      		 queryFileList("#uploadFiles4");
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
			  	var type = jQuery("#uploadFiles4").parent().parent().attr("value");
			  	if(type == 1 && !checkFileType_1(file.name) ){
			  		alert("制度文件限制为Word、PDF！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles4",file);
	  	  			}, 50);
			  	}
			  	
			  	if(type == 3 && !checkFileType_3(file.name) ){
			  		alert("发布依据限制为Word、PDF、图片！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles4",file);
	  	  			}, 50);
			  	}
			  	
//			  	if(type == 12 && "*.gif; *.jpg;*.jpeg;*.png".indexOf(fileType) < 0 ){
//			  		alert("文件（指令）扫描件限制为图片！");
//	  	  			setTimeout(function() {
//	  	  				jQuery(e.target).uploadifyCancel(queueId);
//	  	  			}, 50);
//			  	}
			  	
		  		if(type != 12 && checkFileTypeAll(file.name) == "0") {//不允许上传的类型
		  			alert("该文件类型不允许上传！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles4",file);
	  	  			}, 50);
		  	  	}
		  		if(!filesize(file.size,100)){ //100M * 1024 * 1024
		  			alert("该文件的容量大于100M！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles4",file);
	  	  			}, 50);
		  		}
		  		if(type == 1 && $("#table01").find(".fj_p1").length == 1){
		  			alert("制度文件不得超过1个！");
	  	  			setTimeout(function() {
	  	  				cancel("#uploadFiles4",file);
	  	  			}, 50);
		  		}
		  }
	};

//归档上传
var config_12 = {
	  'buttonClass'  : 'uploadifive',
//      'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf',
      'uploadScript'    : basePath+'/uploadFile',
//      'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',
      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
//      'buttonImg' : '/SRMC/dc/images/cloud.png',
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
	      fileTempId:"",
	      fileId:"",
	      type:"",
	      busType:"",
	      status:"1",//保存
	      isParent:"0" //config附件上传均不为父信息
      },
      //每个文件上传完成后触发
      'onUploadComplete'  : function(file, response) {
      	if(response == "0") {
      		alert("附件上传失败！");
      	}
      	
      	 var type = jQuery("#uploadFiles12").parent().parent().attr("value");
      	 if(type=="8"){ 
      	 }else{
      		 queryFileList("#uploadFiles12");
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
		  	var type = jQuery("#uploadFiles12").parent().parent().attr("value");
		  	if(type == 1 && !checkFileType_1(file.name) ){
		  		alert("制度文件限制为Word、PDF！");
  	  			setTimeout(function() {
  	  				cancel("#uploadFiles12",file);
  	  			}, 50);
		  	}
		  	
		  	if(type == 3 && !checkFileType_3(file.name) ){
		  		alert("发布依据限制为Word、PDF、图片！");
  	  			setTimeout(function() {
  	  				cancel("#uploadFiles12",file);
  	  			}, 50);
		  	}
		  	
//		  	if(type == 12 && "*.gif; *.jpg;*.jpeg;*.png".indexOf(fileType) < 0 ){
//		  		alert("文件（指令）扫描件限制为图片！");
//  	  			setTimeout(function() {
//  	  				jQuery(e.target).uploadifyCancel(queueId);
//  	  			}, 50);
//		  	}
		  	
	  		if(type != 12 && checkFileTypeAll(file.name) == "0") {//不允许上传的类型
	  			alert("该文件类型不允许上传！");
  	  			setTimeout(function() {
  	  				cancel("#uploadFiles12",file);
  	  			}, 50);
	  	  	}
	  		if(!filesize(file.size,100)){ //100M * 1024 * 1024
	  			alert("该文件的容量大于100M！");
  	  			setTimeout(function() {
  	  				cancel("#uploadFiles12",file);
  	  			}, 50);
	  		}
	  		if(type == 1 && $("#table01").find(".fj_p1").length == 1){
	  			alert("制度文件不得超过1个！");
  	  			setTimeout(function() {
  	  				cancel("#uploadFiles12",file);
  	  			}, 50);
	  		}
	  }
};

//页面上传文件初始化
jQuery(function() {
	//==============文件上传功能==============
	if(isCcFlag == "0"){
		var fileTempId = jQuery("#fileTempId").val();
		config_1.formData.fileTempId = fileTempId;
		config_1.formData.type = "1";
		jQuery('#uploadFiles1').uploadifive(config_1);
		config_2.formData.fileTempId = fileTempId;
		config_2.formData.type = "2";
		jQuery('#uploadFiles2').uploadifive(config_2);
		config_3.formData.fileTempId = fileTempId;
		config_3.formData.type = "3";
		jQuery('#uploadFiles3').uploadifive(config_3);
	}
});//初始化 end


/**
 * 查询文件列表
 * @param target
 */
function queryFileList(target){
  //当每个文件上传完成后的操作
  var fileTempId = jQuery("#fileTempId").val();
  var type = jQuery(target).parent().parent().attr("value");
  var url = basePath+"/queryFileList";
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
					    	<div class="fileName l"><a href="'+basePath+'/downloadRulesFile?fileId='+v.fileId+'">'+v.fileName+'('+file_size+')</a></div><i><img onclick="javascript:deleteFileUpload(\''+v.fileId+'\')" src="/SRMC/dc/images/close_icon01.png" width="17" height="17" /></i>\
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
        var url = basePath + "/delRulesFile";
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
