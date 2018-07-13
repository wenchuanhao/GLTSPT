/*附件上传公用方法*/
var uploadPath=$("#uploadPath").val();
$(document).ready(function() {
		    //公告内容
			var id=$("#id").val();
			var uploadType=$("#uploadType").val();
			var uploa
            $('#uploadFiles').uploadify({  
                  'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf', 
                  'script'    : uploadPath+'/uploadFile?workorderId='+id,  
                  'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',  
                  'fileDataName':'file1',  
                  'buttonImg' : '/SRMC/rmpb/images/tb_n_02b.png',  
                  //允许连续上传多个文件   
                  'multi':'true',  
                  //一次性最多允许上传多少个,不设置的话默认为999个   
                  'queueSizeLimit' : 99,      
                  'auto' : true,
                  'onSelectOnce':function(fileObj){
                        $("#result").html("");
				        $("#full").html("");
                        $("#cancel").hide(); 
                        $("#full").hide();                        
                  },
                  'onComplete': function(event, ID, fileObj, response, data) {  
                        //当每个文件上传完成后的操作         
                        if(response == "3") {
				      		alert("该文件不允许上传！");
				      	}
                        if(uploadType=="imgType"){
                        	$("#filesListTable").removeClass("gl_table_a02");
                        	refreshImgDailFile();
                        }else if(uploadType=="flowType"){
                        	$("#filesListTable").addClass("gl_table_a02");
                        	refreshFlowDailFile();
                        }else{
                        	$("#filesListTable").addClass("gl_table_a02");
                        	refreshDefaultDailFile();
                        }
                        //refreshDailFile();                      
                  },  
                  'onAllComplete':function(event,data) {  
                        //当所有文件上传完成后的操作   
                        $("#cancelBtn").hide();  
                        if(data.errors==0){
                           uploadstate = -1;
                           $('#result').html("所有文件已上传成功(本次共上传"+data.filesUploaded+"个)"); 
                        }else{  
                        	uploadstate = data.errors;
                           $('#result').html("成功上传文件"+data.filesUploaded+"个，失败"+data.errors+"个"); 
                        }  
                        uploadProcess = "over";
                  },  
                  'onOpen': function(event,ID,fileObj) { 
                  		uploadProcess = "ing";
                        //当有文件正在上传时的操作   
                        $("#cancelBtn").show();  
                  },  
                  'onQueueFull': function (event,queueSizeLimit) {  
                        //当添加待上传的文件数量达到设置的上限时的操作   
                        $("#full").append("<font color='red'><b>已经达到上传数量限制了,不能再添加了</b></color><br/>");  
                        $("#full").show();  
                        return false;  
                  },  
                  'onCancel': function(event,ID,fileObj,data) {  
                        //当取消所有正在上传文件后的操作   
                      //  $("#cancelBtn").hide();  
                  } ,
                  'wmode':'transparent'

            });  
			 if(uploadType=="imgType"){
				$("#filesListTable").removeClass("gl_table_a02");
             	refreshImgDailFile();
             }else if(uploadType=="flowType"){
            	 $("#filesListTable").addClass("gl_table_a02");
             	refreshFlowDailFile();
             }else{
            	$("#filesListTable").addClass("gl_table_a02");
             	refreshDefaultDailFile();
             }
            //进入的时候第一个文本框获得光标
            //$("#noticeCategory").focus();
  });
   
   //查看图片
   function viewImg(id,down,index,workerId){
	    var url = uploadPath+"/viewImg/"+id+"/"+workerId;		   
	    $("#"+index).attr("href",url);	    
	    $("#"+index).fancybox({
	            'width'				: '80%',
	  			'height'			: '80%',
	  			'margin'			: '8%',
	  			//'scrolling'			:'yes',
	  			'autoScale'			: false,//自适应窗口大小
	  			//'overlayShow'		: true,
	  			'transitionIn'		: 'none',
	  			'transitionOut'		: 'none',
	  			'type'				: 'iframe',
	  			'href'				:url
	      });
	     $("#"+index).click();
  }
  
  //刷新附件列表(可预览图片）
  function refreshImgDailFile(){    
    var id=$("#id").val();	
    var url = uploadPath+"/queryFileList";
	var params = {
		workorderId:id
	};
	$.post(url, params, function(data){
	    var fileList = data;	
	    var temp;
	    for(var i=0; i<fileList.length; i++){
	        imgTip="<tr><td style=\"text-align:left;\"><img src=\"/SRMC/rmpb/images/attechment.png\" align=\"absmiddle\">&nbsp;&nbsp;";
	        		 +fileList[i].fileName+"("+fileList[i].fileSizeHuman+")"+"</a>"; 
	        downUrl="<a href=\""+uploadPath+"/downloadFileById?id=" + fileList[i].attachUploadId +"\">"
	        		 +fileList[i].fileName+"</a>";
	        var imgUrl="";
	        delTip="&nbsp;&nbsp;&nbsp;<a id=\"remove3\" href=\"javascript:deleteFileUpload(\'"+ fileList[i].attachUploadId + "\')\" title=\"删除\" ><img src=\"/SRMC/rmpb/images/delete.png\" width=\"10\" hight=\"10\"></a>";
	        fileType=fileList[i].fileName.substring(fileList[i].fileName.lastIndexOf("."));
	        var img="";
	        if(fileType.indexOf("jpeg") != -1||fileType.indexOf("jpg") != -1||fileType.indexOf("png") != -1||fileType.indexOf("gif") != -1){
	        	img="<img style=\"width:100px;height:80px;padding-left:32px;\" src=\""+uploadPath+"/outputImageOper?id="+fileList[i].attachUploadId+"\"/>";
	        }
	        imgUrl="</br><a id=\"down"+i+"\" href=\"#\" onclick=\"viewImg('"+fileList[i].attachUploadId+"','down"+i+"','con"+(i+1)+"','"+id+"')\">"+img+"</a>";
		    imgUrl+="<input type=\"hidden\" id=\"con"+(i+1)+"\"/>";	
	        endTip="</td></tr>"
	        temp += (imgTip+downUrl+delTip+imgUrl+endTip);
	    }						
		$("#filesListTable").html(temp);
	 } , 'json');
  }
  
  
//刷新附件列表（附件默认信息）
  function refreshDefaultDailFile(){    
    var id=$("#id").val();	
    var url = uploadPath+"/queryFileList";
	var params = {
		workorderId:id
	};
	$.post(url, params, function(data){
	    var fileList = data;	
	    var temp;
	    var temp = '<tr><th width="6%">序号</th><th width="55%">附件名称</th><th width="8%">上传人</th><th width="15%">上传时间</th><th width="16%">操作</th></tr>';
	    for(var i=0; i<fileList.length; i++){
	    	 var uploadYear = 1900 + fileList[i].createTime.year;
             var uploadMonth = ((1+fileList[i].createTime.month)<10)?("0" + (1+fileList[i].createTime.month)):(1+fileList[i].createTime.month);
             var uploadDay = (fileList[i].createTime.date<10)?("0" + fileList[i].createTime.date):(fileList[i].createTime.date);
             var uploadHours = (fileList[i].createTime.hours<10)?("0" + fileList[i].createTime.hours):(fileList[i].createTime.hours);
             var uploadMinutes = (fileList[i].createTime.minutes<10)?("0" + fileList[i].createTime.minutes):(fileList[i].createTime.minutes);
             var uploadSeconds = (fileList[i].createTime.seconds<10)?("0" + fileList[i].createTime.seconds):(fileList[i].createTime.seconds);
             var fileName=fileList[i].fileName; 
             var cl=null;
             if(i%2!= 0){
                 cl="class='tab_c_tr01'";
             }                                 
				temp += '<tr id="' + fileList[i].attachUploadId + '"'+cl+'><td>' + (i+1) + '</td><td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px" title="'+fileList[i].fileName+'">' + fileName + '</td><td>' + fileList[i].createUserName + '</td><td>'
				+ uploadYear + "-" + uploadMonth + "-" + uploadDay  + " " + uploadHours + ":" + uploadMinutes + ":" + uploadSeconds + '</td><td>' 														
				+ '<table class="jz_a01" border="0" cellspacing="0" cellpadding="0"><tr><td width="20"><img src="/SRMC/rmpb/images/tab_tb09.png" /></td><td style="padding-right:8px"><a href="'+uploadPath+'/downloadFileById?id=' + fileList[i].attachUploadId + '">下载</a></td>';
				temp += '<td width="20"><img src="/SRMC/rmpb/images/tab_tb07.png"/></td><td style="padding-right:8px"><a href="javascript:deleteFileUpload(\''+ fileList[i].attachUploadId + '\')">删除</a><td>';
				temp +="</tr></table></td></tr>";   
	    }						
		$("#filesListTable").html(temp);
	 } , 'json');
  }
  
  
//刷新附件列表（显示流程节点信息）
  function refreshFlowDailFile(){    
    var id=$("#id").val();	
    var url = uploadPath+"/queryFileList";
	var params = {
		workorderId:id
	};
	$.post(url, params, function(data){
	    var fileList = data;	
	    var temp;
	    var temp = '<tr><th width="8%">序号</th><th width="15%">节点名称</th><th width="40%">附件名称</th><th width="8%">上传人</th><th width="15%">上传时间</th><th width="14%">操作</th></tr>';
	    for(var i=0; i<fileList.length; i++){
	    	 var uploadYear = 1900 + fileList[i].createTime.year;
             var uploadMonth = ((1+fileList[i].createTime.month)<10)?("0" + (1+fileList[i].createTime.month)):(1+fileList[i].createTime.month);
             var uploadDay = (fileList[i].createTime.date<10)?("0" + fileList[i].createTime.date):(fileList[i].createTime.date);
             var uploadHours = (fileList[i].createTime.hours<10)?("0" + fileList[i].createTime.hours):(fileList[i].createTime.hours);
             var uploadMinutes = (fileList[i].createTime.minutes<10)?("0" + fileList[i].createTime.minutes):(fileList[i].createTime.minutes);
             var uploadSeconds = (fileList[i].createTime.seconds<10)?("0" + fileList[i].createTime.seconds):(fileList[i].createTime.seconds);
             var fileName=fileList[i].fileName; 
             var cl=null;
             if(i%2!= 0){
                 cl="class='tab_c_tr01'";
             }                                 
				temp += '<tr id="' + fileList[i].attachUploadId + '"'+cl+'><td>' + (i+1) + '</td><td>'+fileList[i].nodeName+'</td><td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px" title="'+fileList[i].fileName+'">' + fileName + '</td><td>' + fileList[i].createUserName + '</td><td>'
				+ uploadYear + "-" + uploadMonth + "-" + uploadDay  + " " + uploadHours + ":" + uploadMinutes + ":" + uploadSeconds + '</td><td>' 														
				+ '<table class="jz_a01" border="0" cellspacing="0" cellpadding="0"><tr><td width="20"><img src="/SRMC/rmpb/images/tab_tb09.png" /></td><td style="padding-right:8px"><a href="'+uploadPath+'/downloadFileById?id=' + fileList[i].attachUploadId + '">下载</a></td>';
				temp += '<td width="20"><img src="/SRMC/rmpb/images/tab_tb07.png"/></td><td style="padding-right:8px"><a href="javascript:deleteFileUpload(\''+ fileList[i].attachUploadId + '\')">删除</a><td>';
				temp +="</tr></table></td></tr>";   
	    }						
		$("#filesListTable").html(temp);
	 } , 'json');
  }
	//删除文件		  
  function deleteFileUpload(id){
	if(confirm("确定要删除此文件？")){
		    var url=uploadPath+"/deleteFileUpload";
			var params = {
				fileId : id
			};
			$.post(url, params, function(data) {
				if (data == "0") {
				    $('#result').html("");
				    $('#full').html("");
					/*refreshDailFile();*/
					if(uploadType=="imgType"){
                    	$("#filesListTable").removeClass("gl_table_a02");
                    	refreshImgDailFile();
                    }else if(uploadType=="flowType"){
                    	$("#filesListTable").addClass("gl_table_a02");
                    	refreshFlowDailFile();
                    }else{
                    	$("#filesListTable").addClass("gl_table_a02");
                    	refreshDefaultDailFile();
                    }
				}
			}, 'json');
		}
}