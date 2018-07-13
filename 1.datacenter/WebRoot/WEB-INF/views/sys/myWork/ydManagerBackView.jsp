<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/"; 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		
		<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
		<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
		
	    <script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
		<link href="/SRMC/rmpb/css/date_input.css" type="text/css" rel="stylesheet" />
		
		<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
		<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script>
		
		<script type="text/javascript" src="/SRMC/rmpb/js/bootstrap-tooltip.js"></script>
		
		<script type="text/javascript">
		function ellipsisStr(str){
			 	var newStr = '';
			 	var array = new Array(); 
			 	array = str.split("");
			 	if(array.length > 20){
			 		newStr = str.substr(0, 20) + "..."; 
			 	}else{
			 		newStr = str;
			 	}
				return newStr;
         }
        function splitContent(content,type){
		var newStr = '';
	 	var array = new Array(); 
	 	array = content.split(";");
	 	if(type=='0'){
	 		newStr = array[0]; 
	 	}else{
	 	if(array[1]!=''){
	 		var array2 = new Array(); 
	 		array2=array[1].split(":");
	 		newStr = array2[1];
	 		}
	 	if(array[1]==''){
	 		newStr='—';
	 		}
	 	}
		return newStr;
		}
		</script>
	
<!--		<script type="text/javascript" src="/SRMC/rmpb/js/common.js"></script>-->
<!--		<script type="text/javascript" src="/SRMC/rmpb/js/common2.js"></script>-->
		
		<script type="text/javascript">
			function clickOptions(i, n, name){
				var li = $('options_' + name).getElementsByTagName('li');
				
				$('selected_' + name).className='open';
				$('selected_' + name).id='';
				li[n].id='selected_' + name;
				li[n].className='open_hover';
				$('select_' + name).removeChild($('select_info_' + name));
			
				select_info = document.createElement('div');
				select_info.id = 'select_info_' + name;
				select_info.className='tag_select';
				select_info.style.cursor='pointer';
				$('options_' + name).parentNode.insertBefore(select_info,$('options_' + name));
			
				mouseSelects(name);
			
				$('select_info_' + name).appendChild(document.createTextNode(li[n].innerHTML));
				$( 'options_' + name ).style.display = 'none' ;
				$( 'select_info_' + name ).className = 'tag_select';
				selects[i].options[n].selected = 'selected';
				
				if(name=="projectId"){
					searchVersionByProject();
					//searchProjectSonByProject();
				}
				
				
			}
			
			function clickOptionsSepecify(i, n, name){
				var li = $('options_' + name).getElementsByTagName('li');
				
				$('selected_' + name).className='open';
				$('selected_' + name).id='';
				li[n].id='selected_' + name;
				li[n].className='open_hover';
				$('select_' + name).removeChild($('select_info_' + name));
			
				select_info = document.createElement('div');
				select_info.id = 'select_info_' + name;
				select_info.className='tag_select';
				select_info.style.cursor='pointer';
				$('options_' + name).parentNode.insertBefore(select_info,$('options_' + name));
			
				mouseSelectsSepecify(name);
			
				$('select_info_' + name).appendChild(document.createTextNode(li[n].innerHTML));
				$( 'options_' + name ).style.display = 'none' ;
				$( 'select_info_' + name ).className = 'tag_select';
				selectsSepecify[i].options[n].selected = 'selected';
			}
			
			jQuery(document).ready(function() {
             jQuery('#uploadFiles').uploadify({
                  'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf', 
                  'script'    : '<%=basePath%>developFlowManage/versionFlow/versionPublish/versionUploads?fileTypes=1',  
                  'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',  
                  'fileDataName':'file',  
                  'buttonImg' : '/SRMC/rmpb/images/tb_n_02b.png',  
                  //允许连续上传多个文件   
                  'multi':'true',  
                  //一次性最多允许上传多少个,不设置的话默认为999个   
                  'queueSizeLimit' : 99, 
                  'auto' : true, 
                  'onComplete'  : function(event, ID, fileObj, response, data) {  
                        //当每个文件上传完成后的操作   
                       
                       //  configPerInfo.window.location.href ="<%=basePath%>developFlowManage/versionFlow/versionPublish/queryVersionFileList?fileTypes=1&createWorkorderId="+createWorkorderId;
                         var url = "<%=basePath%>developFlowManage/versionFlow/versionPublish/doqueryVersionFileList";
						var params = {
							fileTypes:1,
							createWorkorderId:jQuery("#createWorkorderId").val()
							
						};
						jQuery.post(url, params, function(data){
							var fileList = data;
						
							var temp = '<tr><th>序号</th><th>节点名称</th><th>附件名称</th><th>上传人</th><th>上传时间</th><th width="160">操作</th></tr>';
							for(var i=0; i<fileList.length; i++){
								var uploadYear = 1900 + fileList[i].uploadDate.year;
								var uploadMonth = ((1+fileList[i].uploadDate.month)<10)?("0" + (1+fileList[i].uploadDate.month)):(1+fileList[i].uploadDate.month);
								var uploadDay = (fileList[i].uploadDate.date<10)?("0" + fileList[i].uploadDate.date):(fileList[i].uploadDate.date);
								var uploadHours = (fileList[i].uploadDate.hours<10)?("0" + fileList[i].uploadDate.hours):(fileList[i].uploadDate.hours);
								var uploadMinutes = (fileList[i].uploadDate.minutes<10)?("0" + fileList[i].uploadDate.minutes):(fileList[i].uploadDate.minutes);
								var uploadSeconds = (fileList[i].uploadDate.seconds<10)?("0" + fileList[i].uploadDate.seconds):(fileList[i].uploadDate.seconds);
								temp += '<tr id="' + fileList[i].publishFlowAttaId + '"><td>' + (i+1) + '</td><td>'+fileList[i].flowNodeName+'</td><td>' + fileList[i].attachmentName + '</td><td>' + fileList[i].uploadOperator + '</td><td>'
									+ uploadYear + "-" + uploadMonth + "-" + uploadDay  + " " + uploadHours + ":" + uploadMinutes + ":" + uploadSeconds + '</td><td>' 
									+ '<table class="jz_a01" border="0" cellspacing="0" cellpadding="0"><tr><td width="20"><img src="/SRMC/rmpb/images/tab_tb09.png" /></td><td style="padding-right:18px"><a href="<%=basePath%>developFlowManage/downloadAttachmentVersion?createFlowAttaId=' + fileList[i].publishFlowAttaId + '">下载</a></td>';
									///alert(jQuery("#userName").val()+fileList[i].uploadOperator);
									if(jQuery("#userName").val()==fileList[i].uploadOperator){
										temp += '<td width="20"><img src="/SRMC/rmpb/images/tab_tb07.png"/></td><td style="padding-right:18px"><a href="javascript:deleteFileUpload(\''+ fileList[i].publishFlowAttaId + '\')">删除</a><td>';
									}
							       temp +="</tr></table></td></tr>";
							}
							jQuery("#filesListTable").html(temp);
						} , 'json'); 
                     
                       },  
                  'onAllComplete':function(event,data) {  
                 // alert("当所有文件上传完成后的操作");
                        //当所有文件上传完成后的操作   
                        jQuery("#cancelBtn").hide();  
                        if(data.errors==0){  
                           jQuery('#result').html("所有文件已上传成功(本次共上传"+data.filesUploaded+"个)"); 
                          
                        }else{  
                           jQuery('#result').html("成功上传"+data.filesUploaded+"个文件，失败"+data.errors+"个"); 
                        }  
                  },  
                  'onOpen': function(event,ID,fileObj) { 
                        //当有文件正在上传时的操作   
                        jQuery("#cancelBtn").show(); 
                  },  
                  'onQueueFull': function (event,queueSizeLimit) {  
                        //当添加待上传的文件数量达到设置的上限时的操作   
                        jQuery("#full").append("<font color='red'><b>已经达到上传数量限制了,不能再添加了</b></color><br/>");  
                        jQuery("#full").show();  
                        return false;  
                  },  
                  'onCancel': function(event,ID,fileObj,data) {  
                        //当取消所有正在上传文件后的操作   
                      //  jQuery("#cancelBtn").hide();  
                  } ,
                  "wmode":"transparent" 
            });
            });
			
			
			jQuery(document).ready(function() {
            jQuery('#uploadFiles2').uploadify({  
                  'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf', 
                  'script'    : '<%=basePath%>developFlowManage/versionFlow/versionPublish/versionUploads?fileTypes=2',  
                  'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',  
                  'fileDataName':'file',  
                  'buttonImg' : '/SRMC/rmpb/images/tb_n_02b.png',  
                  //允许连续上传多个文件   
                  'multi':'true',  
                  //一次性最多允许上传多少个,不设置的话默认为999个   
                  'queueSizeLimit' : 99, 
                  'auto' : true, 
                  'onComplete'  : function(event, ID, fileObj, response, data) {  
                        //当每个文件上传完成后的操作   
                       
                        jQuery("#cancel").hide(); 
                        jQuery("#full").hide();  
                        // configPerInfo2.window.location.href ="<%=basePath%>developFlowManage/versionFlow/versionPublish/queryVersionFileList?fileTypes=2&createWorkorderId="+createWorkorderId;
                        var url = "<%=basePath%>developFlowManage/versionFlow/versionPublish/doqueryVersionFileList";
						var params = {
							fileTypes:2,
							createWorkorderId:jQuery("#createWorkorderId").val()
							
						};
						jQuery.post(url, params, function(data){
							var fileList = data;
						
							var temp = '<tr><th>序号</th><th>节点名称</th><th>附件名称</th><th>上传人</th><th>上传时间</th><th width="160">操作</th></tr>';
							for(var i=0; i<fileList.length; i++){
								var uploadYear = 1900 + fileList[i].uploadDate.year;
								var uploadMonth = ((1+fileList[i].uploadDate.month)<10)?("0" + (1+fileList[i].uploadDate.month)):(1+fileList[i].uploadDate.month);
								var uploadDay = (fileList[i].uploadDate.date<10)?("0" + fileList[i].uploadDate.date):(fileList[i].uploadDate.date);
								var uploadHours = (fileList[i].uploadDate.hours<10)?("0" + fileList[i].uploadDate.hours):(fileList[i].uploadDate.hours);
								var uploadMinutes = (fileList[i].uploadDate.minutes<10)?("0" + fileList[i].uploadDate.minutes):(fileList[i].uploadDate.minutes);
								var uploadSeconds = (fileList[i].uploadDate.seconds<10)?("0" + fileList[i].uploadDate.seconds):(fileList[i].uploadDate.seconds);
								temp += '<tr id="' + fileList[i].publishFlowAttaId + '"><td>' + (i+1) + '</td><td>'+fileList[i].flowNodeName+'</td><td>' + fileList[i].attachmentName + '</td><td>' + fileList[i].uploadOperator + '</td><td>'
									+ uploadYear + "-" + uploadMonth + "-" + uploadDay  + " " + uploadHours + ":" + uploadMinutes + ":" + uploadSeconds + '</td><td>' 
									+ '<table class="jz_a01" border="0" cellspacing="0" cellpadding="0"><tr><td width="20"><img src="/SRMC/rmpb/images/tab_tb09.png" /></td><td style="padding-right:18px"><a href="<%=basePath%>developFlowManage/downloadAttachmentVersion?createFlowAttaId=' + fileList[i].publishFlowAttaId+ '">下载</a></td>';
									///alert(jQuery("#userName").val()+fileList[i].uploadOperator);
									if(jQuery("#userName").val()==fileList[i].uploadOperator){
										temp += '<td width="20"><img src="/SRMC/rmpb/images/tab_tb07.png"/></td><td style="padding-right:18px"><a href="javascript:deleteFileUpload(\''+ fileList[i].publishFlowAttaId + '\')">删除</a><td>';
									}
							       temp +="</tr></table></td></tr>";
							}
							jQuery("#filesListTable2").html(temp);
						} , 'json'); 
                     
                     
                       },  
                  'onAllComplete':function(event,data) {  
                 // alert("当所有文件上传完成后的操作");
                        //当所有文件上传完成后的操作   
                        jQuery("#cancelBtn").hide();  
                        if(data.errors==0){  
                           jQuery('#result2').html("所有文件已上传成功(本次共上传"+data.filesUploaded+"个)"); 
                          
                        }else{  
                           jQuery('#result2').html("成功上传"+data.filesUploaded+"个文件，失败"+data.errors+"个"); 
                        }  
                  },  
                  'onOpen': function(event,ID,fileObj) { 
                        //当有文件正在上传时的操作   
                        jQuery("#cancelBtn").show(); 
                  },  
                  'onQueueFull': function (event,queueSizeLimit) {  
                        //当添加待上传的文件数量达到设置的上限时的操作   
                        jQuery("#full").append("<font color='red'><b>已经达到上传数量限制了,不能再添加了</b></color><br/>");  
                        jQuery("#full").show();  
                        return false;  
                  },  
                  'onCancel': function(event,ID,fileObj,data) {  
                        //当取消所有正在上传文件后的操作   
                      //  jQuery("#cancelBtn").hide();  
                  } ,
                  "wmode":"transparent" 
            });
            });
		
		
		jQuery(document).ready(function() {
		  //  var createWorkorderId=jQuery("#createWorkorderId").val();
            jQuery('#uploadFiles3').uploadify({  
                  'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf', 
                  'script'    : '<%=basePath%>developFlowManage/versionFlow/versionPublish/versionUploads?fileTypes=3',  
                  'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',  
                  'fileDataName':'file',  
                  'buttonImg' : '/SRMC/rmpb/images/tb_n_02b.png',  
                  //允许连续上传多个文件   
                  'multi':'true',  
                  //一次性最多允许上传多少个,不设置的话默认为999个   
                  'queueSizeLimit' : 99,  
                  'auto' : true,
                  'onComplete'  : function(event, ID, fileObj, response, data) {  
                        //当每个文件上传完成后的操作   
                       
                        jQuery("#cancel").hide(); 
                        jQuery("#full").hide();  
                         //configPerInfo3.window.location.href ="<%=basePath%>developFlowManage/versionFlow/versionPublish/queryVersionFileList?fileTypes=3&createWorkorderId="+createWorkorderId;
                        var url = "<%=basePath%>developFlowManage/versionFlow/versionPublish/doqueryVersionFileList";
						var params = {
							fileTypes:3,
							createWorkorderId:jQuery("#createWorkorderId").val()
							
						};
						jQuery.post(url, params, function(data){
							var fileList = data;
						
							var temp = '<tr><th>序号</th><th>节点名称</th><th>附件名称</th><th>上传人</th><th>上传时间</th><th width="160">操作</th></tr>';
							for(var i=0; i<fileList.length; i++){
								var uploadYear = 1900 + fileList[i].uploadDate.year;
								var uploadMonth = ((1+fileList[i].uploadDate.month)<10)?("0" + (1+fileList[i].uploadDate.month)):(1+fileList[i].uploadDate.month);
								var uploadDay = (fileList[i].uploadDate.date<10)?("0" + fileList[i].uploadDate.date):(fileList[i].uploadDate.date);
								var uploadHours = (fileList[i].uploadDate.hours<10)?("0" + fileList[i].uploadDate.hours):(fileList[i].uploadDate.hours);
								var uploadMinutes = (fileList[i].uploadDate.minutes<10)?("0" + fileList[i].uploadDate.minutes):(fileList[i].uploadDate.minutes);
								var uploadSeconds = (fileList[i].uploadDate.seconds<10)?("0" + fileList[i].uploadDate.seconds):(fileList[i].uploadDate.seconds);
								temp += '<tr id="' + fileList[i].publishFlowAttaId + '"><td>' + (i+1) + '</td><td>'+fileList[i].flowNodeName+'</td><td>' + fileList[i].attachmentName + '</td><td>' + fileList[i].uploadOperator + '</td><td>'
									+ uploadYear + "-" + uploadMonth + "-" + uploadDay  + " " + uploadHours + ":" + uploadMinutes + ":" + uploadSeconds + '</td><td>' 
									+ '<table class="jz_a01" border="0" cellspacing="0" cellpadding="0"><tr><td width="20"><img src="/SRMC/rmpb/images/tab_tb09.png" /></td><td style="padding-right:18px"><a href="<%=basePath%>developFlowManage/downloadAttachmentVersion?createFlowAttaId=' + fileList[i].publishFlowAttaId + '">下载</a></td>';
									///alert(jQuery("#userName").val()+fileList[i].uploadOperator);
									if(jQuery("#userName").val()==fileList[i].uploadOperator){
										temp += '<td width="20"><img src="/SRMC/rmpb/images/tab_tb07.png"/></td><td style="padding-right:18px"><a href="javascript:deleteFileUpload(\''+ fileList[i].publishFlowAttaId + '\')">删除</a><td>';
									}
							       temp +="</tr></table></td></tr>";
							}
							jQuery("#filesListTable3").html(temp);
						} , 'json'); 
                     
                     
                       },  
                  'onAllComplete':function(event,data) {  
                 // alert("当所有文件上传完成后的操作");
                        //当所有文件上传完成后的操作   
                        jQuery("#cancelBtn").hide();  
                        if(data.errors==0){  
                           jQuery('#result3').html("所有文件已上传成功(本次共上传"+data.filesUploaded+"个)"); 
                          
                        }else{  
                           jQuery('#result3').html("成功上传"+data.filesUploaded+"个文件，失败"+data.errors+"个"); 
                        }  
                  },  
                  'onOpen': function(event,ID,fileObj) { 
                        //当有文件正在上传时的操作   
                        jQuery("#cancelBtn").show(); 
                  },  
                  'onQueueFull': function (event,queueSizeLimit) {  
                        //当添加待上传的文件数量达到设置的上限时的操作   
                        jQuery("#full").append("<font color='red'><b>已经达到上传数量限制了,不能再添加了</b></color><br/>");  
                        jQuery("#full").show();  
                        return false;  
                  },  
                  'onCancel': function(event,ID,fileObj,data) {  
                        //当取消所有正在上传文件后的操作   
                      //  jQuery("#cancelBtn").hide();  
                  } ,
                  "wmode":"transparent" 
            });
            });
		
		
		</script>

	</head>

	<body class="bg_c_g">
	 <form action="" method="post" id="submitForm">
	 <input type="hidden" id="developWorkorderId" name="developWorkorderId" value="${order.publishWorkorderId}"/>
	 <input type="hidden" id="publishWorkorderId" name="publishWorkorderId" value="${order.publishWorkorderId}"/>
	 <input type="hidden" id="fromNode" name="fromNode" value="${order.fromNode}"/>
	 <input type="hidden" id="opitionContent" name="opitionContent" />
	 <input type="hidden" id="flowinstanceId" name="flowinstanceId" value="${flowInstanceId}"/>
	 <input type="hidden" id="transitionId" name="transitionId" />
	 <input type="hidden" id="approveUserIdStr" name="approveUserIdStr" />
	 <input type="hidden" id="CCUserIdStr" name="CCUserIdStr" />
	 <input type="hidden" id="isBack" name="isBack" value="1"/>
		<c:choose>
			<c:when test="${empty requestScope.indexLocation }">
				<div class="gl_m_r_nav">当前位置&nbsp;:&nbsp;版本流程管理&nbsp;>&nbsp;版本发布&nbsp;>&nbsp;管理版本发布&nbsp;>&nbsp;待办的版本事项&nbsp;>&nbsp;版本发布详情</div>
			</c:when>
			<c:when test="${not empty requestScope.indexLocation and requestScope.indexLocation eq '1' }">
				<div class="gl_m_r_nav">当前位置 : 我的工作台 &gt; 待办事项</div>
			</c:when>
			<c:when test="${not empty requestScope.indexLocation and requestScope.indexLocation eq '2' }">
				<div class="gl_m_r_nav">当前位置 : 我的工作台 &gt; 待阅事项</div>
			</c:when>
		</c:choose>
			<table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <th width="100"><span style="color:red; font-family:微软雅黑; font-size:14px;">*</span>版本主题:</th>
                 <td>
                 <input class="gl_text01_a" type="text" name="theme" id="theme" value="${order.theme}"/>
                 </td>
                 <th width="100"><span style="color:red; font-family:微软雅黑; font-size:14px;">*</span>紧急程度:</th>
                 <td>
                  <select class="select_new01" name="emergencyDegree" id="emergencyDegree" >
	  		     <option value="-1">请选择</option>
	  		     <option value="1" <c:if test="${order.emergencyDegree=='1'}">selected</c:if>>低</option>
	  		     <option value="2" <c:if test="${order.emergencyDegree=='2'}">selected</c:if>>中</option>
	  		     <option value="3" <c:if test="${order.emergencyDegree=='3'}">selected</c:if>>高</option>
	  		     <option value="4" <c:if test="${order.emergencyDegree=='4'}">selected</c:if>>极高</option>
	  		   </select>
                 </td>
                 <th width="100">工单编号:</th>
                 <td>
               		${order.workorderId} 
		  		    <input type="hidden" id="workId" value="${order.workorderId}"/>
		  		    <input type="hidden" id="workorderId" name="workorderId" value="${order.workorderId}"/>
		 			<input type="hidden" id="userName4" value="${userName4}"/>
		 			<input type="hidden" id="userName" value="${userName4}"/>
                 </td>
               
               </tr>
             
               <tr>
                 <th width="100">所属项目:</th>
                 <td>
                 ${order.projectName}
	  		  <input type="hidden" id="projectId" name="projectId" value="${order.projectId}"/>
	  		  <input type="hidden" id="projectName" name="projectName" value="${order.projectName}"/>
				</td>
                 <th width="100">所属子系统:</th>
                 <td>
                  ${order.projectSonName}
	  		   <input type="hidden" id="projectSonId" name="projectSonId" value="${order.projectSonId}"/>
	  		   <input type="hidden" id="projectSonName" name="projectSonName" value="${order.projectSonName}"/>
				</td>
                 <th width="100">所属版本:</th>
                 <td width="254">
                   ${order.versionName}
	  		     <input type="hidden" id="versionId" name="versionId" value="${order.versionId}"/>
	  		     <input type="hidden" id="versionName" name="versionName" value="${order.versionName}"/>
                   </td>
               </tr>
                 <tr>
                 <th width="100"><span style="color:red; font-family:微软雅黑; font-size:14px;">*</span>申请事由:</th>
                 <td colspan="5"><textarea class="gl_text01_d" name="applyReason" id="applyReason">${order.applyReason}</textarea></td>
               </tr>
               <tr>
                 <th>风险描述:</th>
                 <td colspan="5"><textarea class="gl_text01_d" name="riskDescription" id="riskDescription">${order.riskDescription}</textarea></td>
               </tr>
      </table>
      <div class="ge_a01b"></div>	
       <!-- 研发单列表 -->
      
       <div class="gl_bt_bnt01"> 研发单列表</div>
	  <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	  	    <td colspan="7" style="text-align: left;">
	  	    
	  	          &nbsp;&nbsp;&nbsp;<font color="red">${order.projectName} &nbsp;${order.versionName}版本</font>，共有研发单<b><font color="red" size="3">${sy}</font></b>张，本次发布版本的研发单<b><font color="red" size="3">${wc}</font></b>张，待研发的研发单<b><c:if test='${sx!="0"}'><a href="javascript:searchDevelop();"><font size="3" color="red">${sx}</font></a></c:if><c:if test='${sx=="0"}'><font size="3" color="red">${sx}</font></c:if></b>张，待研发中的研发单请走“需求变更流程”更改为下一版本
	  	    </td>
	  	</tr>
	  	<tr align="center">
	  		<th>序号</th>
	  		<th>工单编号</th>
	  		<th>需求名称</th>
	  		<th>所属项目</th>
	  		<th>版本号</th>
	  		<th>发起人</th>
	  		<th>发起时间</th>
	  	</tr>
	  	<c:forEach items="${list2}" var="dan" varStatus="i">
	  	<tr align="center">
	  	  <td>${i.index+1}</td>
	  	  <td>${dan[4].workorderId}</td>
	  	  <td>${dan[4].name}</td>
	  	  <td>${dan[1].projectName}</td>
	  	  <td>${dan[2].versionCode}</td>
	  	  <td>${dan[5].userName}</td>
	  	  <td><fmt:formatDate value="${dan[4].developDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	  	</tr>
	  	</c:forEach>
	  </table>
				<div align="left" id="allfiles" style="display: block;">
				<div>
				<div class="ge_a01b"></div>	
	<div class="gl_bt_bnt01"> 部署计划</div>
	<div class="gl_bnt_nav01" style="border-bottom: none;">
					<a id="addDisButton" onclick="addDisplay()"><input type="button" class="gl_cx_bnt04b" style="float:left; margin-right:8px;" value="添加部署计划" /></a>
				<a  id="editWorkloadButton" onclick="editWorkload()"><input type="button" class="gl_cx_bnt04b" style="float:left; " value="更新部署计划" /></a>
				<a  id="deleteWorkloadButton" onclick="deleteWorkload()"><input type="button" class="gl_cx_bnt04b" style="float:left; " value="删除部署计划" /></a>
				
			
	</div>
	  <table  id="workloadListTable" class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
	
	  	<tr align="center">
	  	    <th></th>
	  		<th>操作步骤</th>
	  		<th>责任人</th>
	  		<th>工作项</th>
	  		<th>实际操作人</th>
	  		<th>开始时间</th>
	  		<th>结束时间</th>
	  		<th>备注</th>
	  	</tr>
		<c:forEach items="${listDisp}" var="dip" varStatus="i">
		 <tr>
			<td><input type="radio" class="myClass" id="developWorkloadId" name="developWorkloadId" value="${dip.publishDeploymentId}"/></td>
			<td>${dip.operatingSteps}</td>
			<td>${dip.responsibleName}</td>
			<td><a rel="tooltip" title="${dip.workItem}"  style="text-decoration:none;"><script>document.write(ellipsisStr('${dip.workItem}'));</script></a></td>
			<td>${dip.operatorName}</td>
			<td>${dip.startDateStr}</td>
			<td>${dip.finishDateStr}</td>
			<td>
				<c:choose>
					<c:when test="${dip.remarks == null || dip.remarks == ''}">
						—
					</c:when>
					<c:otherwise>
						<a id="${dip.publishDeploymentId}" class="fone_style02" onclick="viewRiskRemarks('${dip.publishDeploymentId}')"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/>查看</a>
					</c:otherwise>
				</c:choose>
			</td>
		 </tr>
		</c:forEach>
	  </table>
	
	
			
	<div class="ge_a01b"></div>
	  <div class="gl_bt_bnt01"><span style="color:red; font-family:微软雅黑; font-size:14px;">*</span>任务操作方案</div>
	 <textarea class="gl_text01_c" name="operateScheme" id="operateScheme" style="margin:0 0;">${order.operateScheme}</textarea>
	  	<div class="ge_a01"></div>
             <div class="gl_bt_bnt01">流程信息</div>
             <table  class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
			    <tr>
			    <th width="10%">当前节点:</th>
			    <td width="40%">版本上线申请</td>
			    <th width="10%">应办事项:</th>
			    <td width="40%">请填写版本上线部署详细操作步骤和方案，并提交至移动项目经理审核</td>
			    </tr>
			</table>
			<div class="ge_a01b"></div>
			<div class="gl_bt_bnt01">
				流程跟踪<input type="button" class="gl_cx_bnt01b" value="收起跟踪"
							onclick="search()" id="toggleQueryButton" />
			</div>
			<div id="queryDiv">
			<table class="gl_table_a02" width="100%" border="0" cellspacing="0"
					cellpadding="0">
				<tr>
					<th width="15%">
						节点名称
					</th>
					<th width="10%">
						处理人
					</th>
					<th width="8%">
						处理时限
					</th>
					<th width="10%">
						超时状态
					</th>
					<th width="13%">
						处理时间
					</th>
					<th width="15%">
						处理方式
					</th>
					<th width="15%">
					下一节点处理人
					</th>
					<th style="width: 10%">
						处理意见
					</th>
				</tr>
				<c:forEach items="${flowTrackingList}" var="flowTrackingList" varStatus="i">
					<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if> >
						<td><script>document.write(ellipsisStr('${flowTrackingList.fromNodeName}'));</script></td>
						<td>${flowTrackingList.userName}</td>
						<c:choose>
					         <c:when test="${flowTrackingList.fromNode=='13062109000000000001'||
					         	flowTrackingList.fromNode=='13062309000000000024'||
					         	flowTrackingList.fromNode=='13062309000000000010'||
					         	flowTrackingList.fromNode=='13062309000000000011'||
					         	flowTrackingList.fromNode=='13062309000000000029'}">
							    <td>—</td>
	          					<td>—</td>   
					         </c:when>
				         <c:otherwise>
				             <c:if test="${flowTrackingList.doneTime==null || flowTrackingList.doneTime==0}">
								<td>无时限</td>
	          					<td>未超时</td>
							 </c:if>
							 <c:if test="${flowTrackingList.doneTime!=null && flowTrackingList.doneTime!=0}">
								<td>${flowTrackingList.doneTime}天</td>
	          					<td>
		          					 <c:if test="${flowTrackingList.doneStatus=='1'}">
										已超时
									 </c:if>
									 <c:if test="${flowTrackingList.doneStatus=='2'}">
										未超时
									 </c:if>
							    </td> 
							 </c:if>
				         </c:otherwise>
				        </c:choose>
						<td><fmt:formatDate value="${flowTrackingList.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><script>document.write(splitContent('${flowTrackingList.content}','0'));</script></td>
						<td><script>document.write(splitContent('${flowTrackingList.content}','1'));</script></td>
						<%-- <td>${flowTrackingList.approveResult}</td> --%>
						<td title="${flowTrackingList.approveResult}"><c:set var="approveResult" value="${flowTrackingList.approveResult}" />  
					            <c:choose>  
					                <c:when test="${fn:length(approveResult) > 6}">  
					                   <c:out value="${fn:substring(approveResult, 0, 6)}" />... 
					                </c:when>  
					                <c:otherwise>  
					                   <c:out value="${approveResult}" />  
					                </c:otherwise>  
					            </c:choose> 			
						
						</td>	
					</tr>
				</c:forEach>
				
			</table>
			</div>
			<div class="ge_a01b"></div>	
					<input type="hidden" value="${order.workorderId}" id="createWorkorderId"/>
					<div class="gl_bt_bnt01"> 附件列表</div>
			         <table class="" width="100%" border="0" cellspacing="0" cellpadding="0">
				      <tr>
						<td width="80">
							<b>上传部署附件:</b>
						</td>
						<td width="80">
							<input  type="file" name="fileName" id="uploadFiles" /> 
							<!-- <a onclick="javascript:jQuery('#uploadFiles').uploadifyUpload()"><input name="input" type="button" class="ipt_tb_n02b" value="" /></a> -->	                                  
						</td>
						
						<td> <span id="result" style="font-size: 13px;color: red"></span></td>	
				     </tr>
			       </table>	
				
				
					<!-- 附件列表 start 
			 		
                 
				      <iframe id="configPerInfo" name="configPerInfo"
							style="height: 200px; width: 100%; visibility: inherit;"
							scrolling="auto" frameborder="0" marginwidth="0"
							marginheight="0" src="<%=basePath%>developFlowManage/versionFlow/versionPublish/queryVersionFileList?fileTypes=1&createWorkorderId=${workId}">
					 </iframe>-->
					 <table id="filesListTable" class="gl_table_a02" width="100%"
				border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>
						序号
					</th>
					<th>
						附件名称
					</th>
					<th>
						上传人
					</th>
					<th>
						上传时间
					</th>
					<th width='160'>
						操作
					</th>
				</tr>
					
				<c:forEach items="${attaList1}" var="file" varStatus="i">
					<tr id="${file.publishFlowAttaId}" >
						<td>
							${i.index+1}
						</td>
						<td>
							${file.attachmentName}
						</td>
						<td>
							${file.uploadOperator}
						</td>
						<td>
							<fmt:formatDate value="${file.uploadDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
						   <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
						       <tr>
						           <td><img src="/SRMC/rmpb/images/tab_tb09.png" /></td>
						            <td style="padding-right:18px;"><a href="<%=basePath%>developFlowManage/downloadAttachmentVersion?createFlowAttaId=${file.publishFlowAttaId}">下载</a></td>
						            <td><img src="/SRMC/rmpb/images/tab_tb07.png" /></td>
						            <td style="padding-right:18px;" ><a href="javascript:deleteFileUpload('${file.publishFlowAttaId}')">删除</a></td>
						        
						       </tr>
						   </table>
						</td>
					</tr>
					
				</c:forEach>
				</table>
                     <div class="ge_a01b"> </div> 
                 		<!--附件列表 end -->
                 
                     <table class="" width="100%" border="0" cellspacing="0" cellpadding="0">
				      <tr>
						<td width="80">
							<b>上传验收文档:</b>
						</td>
						<td width="80">
							<input  type="file" name="fileName" id="uploadFiles2" /> 
							<!-- <a onclick="javascript:jQuery('#uploadFiles2').uploadifyUpload()"><input name="input" type="button" class="ipt_tb_n02b" value="" /></a> -->	                                  
						</td>
						
						<td> <span id="result2" style="font-size: 13px;color: red"></span></td>	
				     </tr>
			       </table>	
				
				
					<!-- 附件列表 start 
			 		
                   
				      <iframe id="configPerInfo2" name="configPerInfo2"
							style="height: 200px; width: 100%; visibility: inherit;"
							scrolling="auto" frameborder="0" marginwidth="0"
							marginheight="0" src="<%=basePath%>developFlowManage/versionFlow/versionPublish/queryVersionFileList?fileTypes=2&createWorkorderId=${workId}">
					 </iframe>-->
					 <table id="filesListTable2" class="gl_table_a02" width="100%"
				border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>
						序号
					</th>
					<th>
						附件名称
					</th>
					<th>
						上传人
					</th>
					<th>
						上传时间
					</th>
					<th width='160'>
						操作
					</th>
				</tr>
					
				<c:forEach items="${attaList2}" var="file" varStatus="i">
					<tr id="${file.publishFlowAttaId}" >
						<td>
							${i.index+1}
						</td>
						<td>
							${file.attachmentName}
						</td>
						<td>
							${file.uploadOperator}
						</td>
						<td>
							<fmt:formatDate value="${file.uploadDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
						   <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
						       <tr>
						           <td><img src="/SRMC/rmpb/images/tab_tb09.png" /></td>
						            <td style="padding-right:18px;"><a href="<%=basePath%>developFlowManage/downloadFlowAttachment?createFlowAttaId=${file.publishFlowAttaId}">下载</a></td>
						            <td><img src="/SRMC/rmpb/images/tab_tb07.png" /></td>
						            <td style="padding-right:18px;" ><a href="javascript:deleteFileUpload('${file.publishFlowAttaId}')">删除</a></td>
						        
						       </tr>
						   </table>
						</td>
					</tr>
					
				</c:forEach>
				</table>
                      <div class="ge_a01b"> </div>
                 <!-- 附件列表 end -->
                 
                 <table class="" width="100%" border="0" cellspacing="0" cellpadding="0">
				      <tr>
						<td width="80">
							<b>上传其它附件:</b>
						</td>
						<td width="80">
							<input  type="file" name="fileName" id="uploadFiles3" /> 
							<!-- <a onclick="javascript:jQuery('#uploadFiles3').uploadifyUpload()"><input name="input" type="button" class="ipt_tb_n02b" value="" /></a> -->	                                  
						</td>
						
						<td> <span id="result3" style="font-size: 13px;color: red"></span></td>	
				     </tr>
			       </table>	
				
				
					<!-- 附件列表 start 
			 		
                   
				      <iframe id="configPerInfo3" name="configPerInfo3"
							style="height: 200px; width: 100%; visibility: inherit;"
							scrolling="auto" frameborder="0" marginwidth="0"
							marginheight="0" src="<%=basePath%>developFlowManage/versionFlow/versionPublish/queryVersionFileList?fileTypes=3&createWorkorderId=${workId}">
					 </iframe> 
                  
                	 附件列表 end -->
                 <table id="filesListTable3" class="gl_table_a02" width="100%"
				border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>
						序号
					</th>
					<th>
						附件名称
					</th>
					<th>
						上传人
					</th>
					<th>
						上传时间
					</th>
					<th width='160'>
						操作
					</th>
				</tr>
					
				<c:forEach items="${attaList3}" var="file" varStatus="i">
					<tr id="${file.publishFlowAttaId}" >
						<td>
							${i.index+1}
						</td>
						<td>
							${file.attachmentName}
						</td>
						<td>
							${file.uploadOperator}
						</td>
						<td>
							<fmt:formatDate value="${file.uploadDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
						   <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
						       <tr>
						           <td><img src="/SRMC/rmpb/images/tab_tb09.png" /></td>
						            <td style="padding-right:18px;"><a href="<%=basePath%>developFlowManage/downloadFlowAttachment?createFlowAttaId=${file.publishFlowAttaId}">下载</a></td>
						            <td><img src="/SRMC/rmpb/images/tab_tb07.png" /></td>
						            <td style="padding-right:18px;" ><a href="javascript:deleteFileUpload('${file.publishFlowAttaId}')">删除</a></td>
						        
						       </tr>
						   </table>
						</td>
					</tr>
					
				</c:forEach>
				</table>
                 
                 <div class="gl_ipt_03">
				<a id="submitButton" ><input type="button" class="ipt_tb_n03" value="提 交" onclick="workderSubmit()" /></a>				
			     </div>
				</div>
				<div style="height: 25px;"></div>
			</div>
			
		
<script type="text/javascript">

function searchDevelop(){
	formdev.target="_blank";
	formdev.action="<%=basePath%>developFlowManage/developFlow/manageRequireDevelop/queryRequireDevelopItemPage";
	formdev.submit();
}
function addDisplay(){
	jQuery("#addDisButton").attr("href", "<%=basePath%>developFlowManage/versionFlow/versionPublish/toEditDisplay?workId=" + encodeURI(jQuery("#workId").val()));
	jQuery("#addDisButton").fancybox({
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
}
function deleteFileUpload(versionFlowAttaId){
	if(confirm("确定要删除此文件？")){
		var url="<%=basePath%>developFlowManage/versionFlow/versionPublish/deleteVersionFileUpload";
		var params = {
			
			versionFlowAttaId:versionFlowAttaId
		};
		jQuery.post(url, params, function(data){
			if(data== "1"){
				jQuery("#" + versionFlowAttaId).remove();
			}
		} , 'json');		
	}
}
function deleteWorkload(){
	var developWorkloadId = "";
	jQuery("input[id='developWorkloadId']").each(function () {
		if(jQuery(this).attr("checked") == "checked"){
			developWorkloadId = jQuery(this).val();
		}
	});
	if(developWorkloadId == ""){
		alert("请先选择需要删除的记录");
		return;
	}
	if(confirm("确定要删除此部署计划吗？")){
		var url = "<%=basePath%>developFlowManage/versionFlow/versionPublish/delDisplay";
		var params = {
			developWorkloadId:developWorkloadId
		};
		jQuery.post(url, params, function(data){
			if(data == "" || data == "0"){
				alert("删除失败！");
			}else{
				refreshWorkloadArea("");
				alert("删除成功！");
			}
		} , 'json');
	}
}
function editWorkload(){
	var developWorkloadId = "";
	jQuery("input[id='developWorkloadId']").each(function () {
		if(jQuery(this).attr("checked") == "checked"){
			developWorkloadId = jQuery(this).val();
		}
	});
	var bl=false;
	if(developWorkloadId == ""){
		alert("请先选择需要修改的记录");
		jQuery("#editWorkloadButton").fancybox({
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'onStart'           :function(current,previous){
		if(bl==false){
		return false ;
		}
		}});
		return;
	}else{
		bl=true;
	}
	jQuery("#editWorkloadButton").attr("href", "<%=basePath%>developFlowManage/versionFlow/versionPublish/toEditDisplay?developWorkorderId="+developWorkloadId);
	jQuery("#editWorkloadButton").fancybox({
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'onStart'           :function(current,previous){
		if(bl==false){
		return false;
		}
		}
		
	});
}
function doCloseSubpage(){
	jQuery.fancybox.close();
}
function workderSubmit(){
  var workId=jQuery("#projectId").val();
  var workidd = encodeURI(jQuery("#workId").val());
  var projectId = document.getElementById("projectId").value;
  var flowinstanceId = document.getElementById("flowinstanceId").value;
  if(jQuery.trim(jQuery("#theme").val()) == ""){
		alert("请输入版本主题");
		return;
	}
/*	if(jQuery("#emergencyDegree").val() == "-1"){
	    alert("请选择紧急程度");
		return false;
	}*/
	if(jQuery.trim(jQuery("#applyReason").val()) == ""){
		alert("请输入申请事由");
		return;
	}
	var bl=false;
	if(jQuery.trim(jQuery("#riskDescription").val()) == ""){
	bl=window.confirm("您确定不填写风险描述吗？");
		if(bl){
		}else{
			jQuery("#submitButton").fancybox({
			'width'				: '80%',
			'height'			: '100%',
			'autoScale'			: false,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe',
			'onStart'           :function(current,previous){
			if(bl==false){
			return ;
			}
			}
		});
		}
	}else{
	bl=true;
	}
	if(jQuery.trim(jQuery("#operateScheme").val()) == ""){
		alert("请输入任务操作方案");
		return;
	}
	var operValue=jQuery("#operateScheme").val();
	if(operValue.length>1500){
		alert("任务操作方案请不要超过1500个字符！");
		return;
	}
	jQuery("#submitButton").attr("href","<%=basePath%>core/portal/toPreApproveVersionSubpage?developWorkorderId=" + jQuery("#developWorkorderId").val() + "&fromNode=" + jQuery("#fromNode").val() +"&projectId="+projectId+"&flowinstanceId="+flowinstanceId);
	jQuery("#submitButton").fancybox({
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'onStart'           :function(current,previous){
		if(bl==false){
		return false;;
		}
		}
	});
}
function show(){
	jQuery("#submitButton").attr("href","<%=basePath%>developFlowManage/versionFlow/versionPublish/toPublishSubpage?developWorkorderId=" + workId);

		jQuery("#submitButton").fancybox({
			'width'				: '80%',
			'height'			: '100%',
			'autoScale'			: false,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe'
		});
}
function refreshWorkloadArea(developWorkloadId){
	var url = "<%=basePath%>developFlowManage/versionFlow/versionPublish/queryDisPlayList";
	var params = {
		developWorkorderId:jQuery("#workId").val()
	};
	jQuery.post(url, params, function(data){
		var workloadList = data;
		jQuery("#workloadListTable").html("");
		var temp = '<tr align="center"><th></th><th>操作步骤</th><th>责任人</th><th>工作项</th><th>实际操作人</th><th>开始时间</th><th>结束时间</th><th>备注</th></tr>';
		for(var i=0; i<workloadList.length; i++){
				var remark = "";
				if(workloadList[i].remarks == null || workloadList[i].remarks == ""){
					remark = "—";
				}else{
					remark = '<a id="' + workloadList[i].publishDeploymentId + '" class="fone_style02" onclick="viewRiskRemarks(\'' + workloadList[i].publishDeploymentId + '\')"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/>查看</a>';
				}
				temp += '<tr>'
					+ '<td><input type="radio" class="myClass" id="developWorkloadId" name="developWorkloadId" value="' + workloadList[i].publishDeploymentId + '"/></td>'
					//+ '<td>' + workloadList[i].operatingSteps + '</td>'
					+'<td> <a rel="tooltip" title= "'+workloadList[i].operatingSteps+'" >'+ellipsisStr(workloadList[i].operatingSteps)+'</a> </td>'
					+ '<td>' + workloadList[i].responsibleName + '</td>'
					//+ '<td>' + workloadList[i].workItem + '</td>'
					+'<td> <a rel="tooltip" title= "'+workloadList[i].workItem+'" >'+ellipsisStr(workloadList[i].workItem)+'</a> </td>'
					+ '<td>' + workloadList[i].operatorName + '</td>'
					+ '<td>' + workloadList[i].startDateStr + '</td>'
					+ '<td>' + workloadList[i].finishDateStr + '</td>'
					//+ '<td>'+workloadList[i].remarks +'</td>'
					+ '<td>' + remark + '</td>'
					+ '</tr>';
			
		}
		jQuery("#workloadListTable").html(temp);
	} , 'json');
}
function allSubmit(){
	 jQuery.fancybox.close();
     var workId=jQuery("#workId").val();
     var theme=jQuery("#theme").val();
     var opitionContent=jQuery("#opitionContent").val();
     var transitionId=jQuery("#transitionId").val();
     var CCUserIdStr=jQuery("#CCUserIdStr").val();
     var approveUserIdStr=jQuery("#approveUserIdStr").val();
     var projectId=jQuery("#projectId").val();
     var projectName=jQuery("#projectName").val();
     var projectSonId=jQuery("#projectSonId").val();
     var projectSonName=jQuery("#projectSonName").val();
     var versionId=jQuery("#versionId").val();
     var versionName=jQuery("#versionName").val();
     var emergencyDegree=jQuery("#emergencyDegree").val();
     var applyReason=jQuery("#applyReason").val();
     var riskDescription=jQuery("#riskDescription").val();
     var operateScheme=jQuery("#operateScheme").val();
	$.post('<%=basePath%>/developFlowManage/versionFlow/versionPublish/savePublishWorkorder',{"approveUserIdStr":approveUserIdStr,"workId":workId,"theme":theme,"opitionContent":opitionContent,"transitionId":transitionId,"CCUserIdStr":CCUserIdStr,"projectId":projectId,"projectName":projectName,"projectSonId":projectSonId,"projectSonName":projectSonName,"versionId":versionId,"versionName":versionName,"emergencyDegree":emergencyDegree ,"applyReason":applyReason,"riskDescription":riskDescription,"operateScheme":operateScheme},function(data){
	  if(data=="ok"){
	      alert("版本提交成功！");
	      document.getElementById("daninfo").innerHTML="";
    	  document.getElementById("allfiles").style.display='none';
	  }else{
	  	 alert("版本提交失败！");
	  }
	});
}
function doSubmit(){
	jQuery.fancybox.close();
	jQuery("#submitForm").attr("action","<%=basePath%>core/portal/submitVersionApprove");
	document.forms[0].submit();
}
function viewRiskRemarks(developWorkloadId){
	jQuery("#" + developWorkloadId).attr("href", "<%=basePath%>developFlowManage/viewVersionDisRemarks?developWorkloadId=" + developWorkloadId );
	jQuery("#" + developWorkloadId).fancybox({
		'width'				: '80%',
		'height'			: '50%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
}
</script>
</form>
</body>
</html>