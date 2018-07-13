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

var configMIS = {
      'uploadScript'    : basePath+'/rulesController/importExcel',
      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
      'buttonText' : '选择文件',
      'multi':'true',//允许连续上传多个文件
      'uploadLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
      'queueSizeLimit': 99,
      'fileSizeLimit' : 100 * 1024 * 1024,//KB
      'removeCompleted' : true,
      //'fileType'     : 'application/vnd.ms-excel',
      'auto' : true,
      'method':'post',
      'formData':{
	      fileTempId:"",
	      fileId:"",
	      type:"8",
	      status:"1",//保存
	      isParent:"0" //config附件上传均不为父信息
      },
      'onUploadComplete'  : function(file, response) {
        	if(response == "0") {
        		alert("附件上传失败！");
        	}
        	alert("附件上传成功！");
        	window.location.href = basePath + "account/importInfoCollect?type=3";
        	//queryErrorInfo("8");
        },
    //当有文件正在上传时的操作
      'onProgress': function(file,event) {
      },
    //当添加待上传的文件数量达到设置的上限时的操作
    /*  'onQueueFull': function (event,queueSizeLimit) {
          return false;
      },*/
    //当取消所有正在上传文件后的操作
      'onCancel': function(file) {
      } ,
      'onError': function(errorType,file) {
      	alert('The error was: ' + errorType);
      } ,
	  'onAddQueueItem': function(file) {
		  if(file.size > 100 * 1024 *1024){
			  alert("该文件的容量大于100M！");
			  cancel("#commonuploadMIS",file);
		  }else if(!checkFileType(file.name)){
			  alert("该文件类型不允许上传！");
			  cancel("#commonuploadMIS",file);
		  }else if(checkFileType(file.name)){
			  upload("#commonuploadMIS");
		  }
	  }
};

var configTAX = {
      'uploadScript'    : basePath+'/rulesController/importExcel',
      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
      'buttonText' : '选择文件',
      'multi':'true',//允许连续上传多个文件
      'uploadLimit' : 99,//一次性最多允许上传多少个,不设置的话默认为999个
      'queueSizeLimit': 99,
      'fileSizeLimit' : 100 * 1024 * 1024,//KB
      'removeCompleted' : true,
      //'fileType'     : 'application/vnd.ms-excel',
      'auto' : true,
      'method':'post',
      'formData':{
	      fileTempId:"",
	      fileId:"",
	      type:"suiwu",
	      status:"1",//保存
	      isParent:"0" //config附件上传均不为父信息
      },
      'onUploadComplete'  : function(file, response) {
        	if(response == "0") {
        		alert("附件上传失败！");
        	}
        	alert("附件上传成功！");
        	window.location.href = basePath + "account/importInfoCollect?type=2";
        	//queryErrorInfo("suiwu");
        },
    //当有文件正在上传时的操作
      'onProgress': function(file,event) {
      },
    //当添加待上传的文件数量达到设置的上限时的操作
    /*  'onQueueFull': function (event,queueSizeLimit) {
          return false;
      },*/
    //当取消所有正在上传文件后的操作
      'onCancel': function(file) {
      } ,
      'onError': function(errorType,file) {
      	alert('The error was: ' + errorType);
      } ,
	  'onAddQueueItem': function(file) {
		  if(file.size > 100 * 1024 *1024){
			  alert("该文件的容量大于100M！");
			  cancel("#commonuploadTAX",file);
		  }else if(!checkFileType(file.name)){
			  alert("该文件类型不允许上传！");
			  cancel("#commonuploadTAX",file);
		  }else if(checkFileType(file.name)){
			  upload("#commonuploadTAX");
		  }
	  }
};

//页面上传文件初始化
jQuery(function() {
	//==============文件上传功能==============
	if(isCcFlag == "0"){
		var fileTempId = jQuery("#fileTempId").val();
		config.formData.fileTempId = fileTempId;
		config.formData.type = "1";
		config.fileType = "*.doc; *.docx; *.pdf";
		jQuery('#uploadFiles1').uploadifive(config);
		config.formData.type = "2";
		config.fileType = "*.*";
		jQuery('#uploadFiles2').uploadifive(config);
		config.formData.type = "3";
		config.fileType = "*.doc; *.docx; *.pdf;*.gif; *.jpg; *.png";
		jQuery('#uploadFiles3').uploadifive(config);
	}
});//初始化 end


/**
 * 错误信息
 * @param target
 */
function queryErrorInfo(type){
  //当每个文件上传完成后的操作
  var importType = $("#type").val();
  var fileTempId = jQuery("#fileTempId").val();
  //var type = jQuery("#uploadFiles" + type).parent().attr("value");
  var url = basePath+"/queryErrorInfo";
  var params = {
  		'fileTempId':fileTempId,
  		"importType":importType,
  		'type':type
  };
  jQuery.post(url, params, function(data){
//       onClick="shade.style.display='block';Layer2.style.display='block';changePopSize();"
      var temp = '';
      $.each(data,function(k,v){
	        temp += '<tr class="fj_p" value="" id="" >\
				    	<th align="right">&nbsp;</th>\
				    	<td align="left">\
					    	<div class="fileName l">'+'第'+v.row +'行: '+v.failReason+'</div>\
				    	</td>\
				    </tr>';
      });
      jQuery("#fi_box_tr").parent().find(".fj_p").remove();
      jQuery("#fi_box_tr").after(temp);
  } , 'json');
  
  	//加载空的表单出来
	$("#listTable_00"+importType).load(basePath+"/queryImportInfo?importType="+importType,function(){});
}

function checkFileType(name) {
	if(confirmEnding(name,".xlsx") || confirmEnding(name,".xls")){
		return true;
	}
	return false;
}

function confirmEnding(str, target) {
	  // 请把你的代码写在这里
	  var start = str.length-target.length;
	  var arr = str.substr(start,target.length);
	  if(arr == target){
	    return true;
	  }
	 return false;
}

function cancel(id,file){
	$(id).uploadifive('cancel',file);
}
function upload(id){
	$(id).uploadifive('upload');
}