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
		<script type="text/javascript" src="/SRMC/rmpb/js/bootstrap-tooltip.js"></script>
		
		
		<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
		<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script>
		
		<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
		<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
		
		<script type="text/javascript">
		function ellipsisStr(str){
			 	var newStr = '';
			 	var array = new Array(); 
			 	array = str.split("");
			 	if(array.length > 20){
			 		
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
			
			jQuery(document).ready(function() {
		     var createWorkorderId = jQuery("#createWorkorderId").val();
             jQuery('#uploadFiles').uploadify({  
                  'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf', 
                  'script'    : '<%=basePath%>developFlowManage/versionFlow/versionPublish/versionUploads2',
                  'scriptData': {'createWorkorderId' : encodeURIComponent(encodeURI(createWorkorderId)) },  
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
                       
                       //  configPerInfo.window.location.href ="<%=basePath%>developFlowManage/versionFlow/versionPublish/queryVersionFileList?fileTypes=3&createWorkorderId="+createWorkorderId;
                         var url = "<%=basePath%>developFlowManage/versionFlow/versionPublish/doqueryVersionFileList";
						var params = {
							fileTypes:3,
							createWorkorderId:createWorkorderId
							
						};
						jQuery.post(url, params, function(data){
							var fileList = data;
							var temp = '<tr><th width="5%">序号</th><th width="13%">节点名称</th><th width="50%">附件名称</th><th width="8%">上传人</th><th width="13%">上传时间</th><th width="11%">操作</th></tr>';
							for(var i=0; i<fileList.length; i++){
								var uploadYear = 1900 + fileList[i].uploadDate.year;
								var uploadMonth = ((1+fileList[i].uploadDate.month)<10)?("0" + (1+fileList[i].uploadDate.month)):(1+fileList[i].uploadDate.month);
								var uploadDay = (fileList[i].uploadDate.date<10)?("0" + fileList[i].uploadDate.date):(fileList[i].uploadDate.date);
								var uploadHours = (fileList[i].uploadDate.hours<10)?("0" + fileList[i].uploadDate.hours):(fileList[i].uploadDate.hours);
								var uploadMinutes = (fileList[i].uploadDate.minutes<10)?("0" + fileList[i].uploadDate.minutes):(fileList[i].uploadDate.minutes);
								var uploadSeconds = (fileList[i].uploadDate.seconds<10)?("0" + fileList[i].uploadDate.seconds):(fileList[i].uploadDate.seconds);
								temp += '<tr id="' + fileList[i].publishFlowAttaId + '"><td>' + (i+1) + '</td><td>'+fileList[i].flowNodeName+'</td><td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px">' + fileList[i].attachmentName + '</td><td>' + fileList[i].uploadOperator + '</td><td>'
									+ uploadYear + "-" + uploadMonth + "-" + uploadDay  + " " + uploadHours + ":" + uploadMinutes + ":" + uploadSeconds + '</td><td>' 
									+ '<table class="jz_a01" border="0" cellspacing="0" cellpadding="0"><tr><td width="20"><img src="/SRMC/rmpb/images/tab_tb09.png" /></td><td style="padding-right:5px"><a href="<%=basePath%>developFlowManage/downloadAttachmentVersion?createFlowAttaId=' + fileList[i].publishFlowAttaId + '">下载</a></td>';
									///alert(jQuery("#userName").val()+fileList[i].uploadOperator);
									if(jQuery("#userName4").val()==fileList[i].uploadOperator){
										temp += '<td width="20"><img src="/SRMC/rmpb/images/tab_tb07.png"/></td><td style="padding-right:5px"><a href="javascript:deleteFileUpload(\''+ fileList[i].publishFlowAttaId + '\')">删除</a><td>';
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
                           jQuery('#result').html("所有文件已上传成功(本次共上传"+data.filesUploaded+"个)"); 
                          
                        }else{  
                           jQuery('#result').html("成功上传文件"+data.filesUploaded+"个，失败"+data.errors+"个"); 
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
            
function search(){
	var display = document.getElementById("queryDiv").style.display;
	if(display=='block'||display==''){
		document.getElementById("queryDiv").style.display='none';
		document.getElementById("toggleQueryButton").value="展开跟踪";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01b";
	}else{
		document.getElementById("queryDiv").style.display='block';
		document.getElementById("toggleQueryButton").value="收起跟踪";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01";
	}
}

//表格tab切换
function switchbox(i)
{
 selectbox(i);
}
function selectbox(i)
{
 switch(i)
 {
 case 1:
 document.getElementById("one1").className="hover";
 document.getElementById("one2").className="";
 document.getElementById("one3").className="";
 document.getElementById("con_one_1").style.display="block";
 document.getElementById("con_one_2").style.display="none";
 document.getElementById("con_one_3").style.display="none";
 break;
 case 2:
 document.getElementById("one1").className="";
 document.getElementById("one2").className="hover";
 document.getElementById("one3").className="";
 document.getElementById("con_one_1").style.display="none";
 document.getElementById("con_one_2").style.display="block";
 document.getElementById("con_one_3").style.display="none";
 break;
 case 3:
 document.getElementById("one1").className="";
 document.getElementById("one2").className="";
 document.getElementById("one3").className="hover";
 document.getElementById("con_one_1").style.display="none";
 document.getElementById("con_one_2").style.display="none";
 document.getElementById("con_one_3").style.display="block";
 break;
 }
}
		</script>
	</head>

	<body class="bg_c_g">
	 <form action="" method="post" id="submitForm">
	 <input type="hidden" id="developWorkorderId" name="developWorkorderId" value="${order.publishWorkorderId}"/>
	 <input type="hidden" id="fromNode" name="fromNode" value="${order.fromNode}"/>
	 <input type="hidden" id="opitionContent" name="opitionContent" />
	 <input type="hidden" id="transitionId" name="transitionId" />
	 <input type="hidden" id="flowinstanceId" name="flowinstanceId"  value="${order.flowInstanceId}"/>
	 <input type="hidden" id="projectId" name="projectId"  value="${order.projectId}"/>
	 <input type="hidden" id="approveUserIdStr" name="approveUserIdStr" />
	 <input type="hidden" id="CCUserIdStr" name="CCUserIdStr" />
		<c:choose>
			<c:when test="${empty requestScope.indexLocation }">
				<div class="gl_m_r_nav">当前位置&nbsp;:&nbsp;我的工作台&nbsp;>&nbsp;版本发布&nbsp;>&nbsp;待办的版本事项&nbsp;>&nbsp;版本发布详情</div>
			</c:when>
			<c:when test="${not empty requestScope.indexLocation and requestScope.indexLocation eq '1' }">
				<div class="gl_m_r_nav">当前位置 : 我的工作台 &gt; 待办事项</div>
			</c:when>
			<c:when test="${not empty requestScope.indexLocation and requestScope.indexLocation eq '2' }">
				<div class="gl_m_r_nav">当前位置 : 我的工作台 &gt; 待阅事项</div>
			</c:when>
		</c:choose>

		<div class="gl_import_m">
		
			<div class="gl_bt_bnt01">流程信息</div>
	             <table  class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
				    <tr>
				    <th width="10%">当前节点:</th>
				    <td width="40%">${flowNodeModel.nodeName}</td>
				    <th width="10%">应办事项:</th>
				    <td width="40%">${flowNodeModel.nodeText}</td>
				    </tr>
				 </table>
			<div class="ge_a01b"></div>
			<div class="gl_bt_bnt01">
				版本发布信息
			</div>
			
			<table class="gl_table_a01_6L" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <th width="100">工单编号:</th>
                 <td>
                 ${order.workorderId}
                  <input type="hidden" value="${order.workorderId}" id="createWorkorderId"/>
                 </td>
                 <th width="100">所属项目:</th>
                 <td>
                 ${order.projectName}
                 </td>
                 <th width="100">所属子系统:</th>
                 <td>
                 ${order.projectSonName}
                 </td>
               </tr>
               <tr>
                 <th>所属版本:</th>
                 <td>
                ${order.versionName}
                 </td>
                 <th>紧急程度:</th>
                 <td>
                 		<c:if test="${order.emergencyDegree=='1'}">低</c:if>
						<c:if test="${order.emergencyDegree=='2'}">中</c:if>
						<c:if test="${order.emergencyDegree=='3'}">高</c:if>
						<c:if test="${order.emergencyDegree=='4'}">极高</c:if>
				</td>
				<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请人:</th>
                 <td>
                 ${createUserName} 
                 </td>
               </tr>
                <tr>
               	 <th>版本主题:</th>
               	 <td colspan="5">
               	 ${order.theme}
               	 <input type="hidden" id="userName4" value="${userName4}"/>
               	 </td>
               </tr>
               <tr>
					<th>
						申请事由:
					</th>
					<td colspan="5">
						<textarea style="background: #EFF4FA" name="reason" class="gl_text01_d" id="reason" readonly="readonly">${order.applyReason}</textarea>
					</td>
				</tr>
				<tr>
					<th>
						风险描述:
					</th>
					<td colspan="5">
						<textarea style="background: #EFF4FA" name="description" class="gl_text01_d" id="description" readonly="readonly">${order.riskDescription}</textarea>
					</td>
				</tr>
				<tr>
					<th>
						任务操作方案:
					</th>
					<td colspan="5">
						<textarea style="background: #EFF4FA" name="operateScheme" class="gl_text01_d" id="operateScheme" readonly="readonly">${order.operateScheme}</textarea>
					</td>
				</tr>
             </table>
			
			<div class="ge_a01"></div>
			<div class="gl_bt_bnt01">
				研发单信息
			</div>
			<table id="workloadListTable" class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="5%">
						序号
					</th>
					<th width="12%">
						工单编号
					</th>
					<th width="%">
						需求名称
					</th>
					<th width="14%">
						所属项目
					</th>
					<th width="7%">
						版本号
					</th>
					<th width="8%">
						发起人
					</th>
					<th width="13%">
						发起时间
					</th>
				</tr>
				<c:forEach items="${listDeve}" var="dan" varStatus="i">
					<tr>
					  <td>${i.index+1}</td>
	  	  			  <td>${dan[4].workorderId}</td>
	  	  			  <td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="${dan[4].name}"  style="text-decoration:none;"><script>document.write(ellipsisStr('${dan[4].name}'));</script></a></td>
	  	              <td>${dan[1].projectName}</td>
	  	              <td>${dan[2].versionCode}</td>
	  	              <td>${dan[5].userName}</td>
	  	              <td><fmt:formatDate value="${dan[4].developDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</c:forEach>
			</table>
			<div class="ge_a01"></div>
			 <div class="h_n_div01">
             <ul class="home_nav_top01">
                     <li id="one1" onclick="switchbox(1);" class="hover">部署计划信息</li>
      	             <li id="one2" onclick="switchbox(2);">部署附件列表</li>
      	              <li id="one3" onclick="switchbox(3);">验收文档列表</li>
                    </ul>
   
             </div>
              <div id="con_one_1" style="display: block;" >
            <table id="riskListTable" class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="5%">
						序号
					</th>
					<th width="24%">
						操作步骤
					</th>
					<th width="8%">
						责任人
					</th>
					<th width="24%">
						工作项
					</th>
					<th width="8%">
						实际操作人
					</th>
					<th width="12%">
						开始时间
					</th>
					<th width="12%">
						结束时间
					</th>
					<th width="7%">
						备注
					</th>
				</tr>
				<c:forEach items="${listDisp}" var="dip" varStatus="i">
					<tr>
						<td>${i.index+1}</td>
						<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px" title="${dip.operatingSteps}">
							<%-- <c:choose> 
							<c:when test="${fn:length(dip.operatingSteps) > 10}"> 
								<c:out value="${fn:substring(dip.operatingSteps, 0, 10)}..." /> 
							</c:when> 
							<c:otherwise> 
								<c:out value="${dip.operatingSteps}" /> 
							</c:otherwise>
						</c:choose> --%>
						<c:out value="${dip.operatingSteps}" /> 
						</td>
						<td>${dip.responsibleName}</td>
						<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="${dip.workItem}"  style="text-decoration:none;">
						<%-- <c:choose> 
								<c:when test="${fn:length(dip.workItem) > 20}"> 
									<c:out value="${fn:substring(dip.workItem, 0, 20)}..." /> 
								</c:when> 
								<c:otherwise> 
									<c:out value="${dip.workItem}" /> 
								</c:otherwise>
							</c:choose> --%>
								<c:out value="${dip.workItem}" /> 
						</a></td>
						<td>${dip.operatorName}</td>
						<td>${dip.startDateStr}</td>
						<td>${dip.finishDateStr}</td>
						<td>
							<c:choose>
								<c:when test="${dip.remarks == null || dip.remarks == ''}">
									—
								</c:when>
								<c:otherwise>
									<a id="${dip.publishDeploymentId}" class="fone_style02" ><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle" onclick="viewRiskRemarks('${dip.publishDeploymentId}')"/>查看</a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
             </div>
             <div id="con_one_2"  style="display: none;" >
               <c:if test="${attaList1!=null && fn:length(attaList1)!=0}">
			<table id="filesListTable" class="gl_table_a02" width="100%"
				border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="5%">
						序号
					</th>
					<th width="13%">
					             节点名称
					</th>
					<th width="54%">
						附件名称
					</th>
					<th width="8%">
						上传人
					</th>
					<th width="13%">
						上传时间
					</th>
					<th width="7%">
						操作
					</th>	
				</tr>
				<c:forEach items="${attaList1}" var="attaList1" varStatus="i">
					<tr>
						<td>
							${i.index+1}
						</td>
						<td>${attaList1.flowNodeName}</td>
						<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px">
							<b>${attaList1.attachmentName}</b>
						</td>
						<td>
							${attaList1.uploadOperator}
						</td>
						<td>
							${attaList1.formatUploadDate}
						</td>
						<td align="center">
						  <table  class="jz_a01" border="0" cellspacing="0" cellpadding="0">
						   <tr>
							<td><img src="/SRMC/rmpb/images/tab_tb09.png" align="middle" /></td>
							<td><a href="<%=basePath%>developFlowManage/downloadAttachmentVersion?createFlowAttaId=${attaList1.publishFlowAttaId}">下载</a></td>
						  </tr>
						  </table>
						</td>
					</tr>
				</c:forEach>
			</table>
			</c:if>
             </div>
             <div id="con_one_3" style="display: none;">
               <c:if test="${attaList2!=null && fn:length(attaList2)!=0}">
				<table id="filesListTable" class="gl_table_a02" width="100%"
				border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="5%">
						序号
					</th>
					<th width="13%">
					             节点名称
					</th>
					<th width="54%">
						附件名称
					</th>
					<th width="8%">
						上传人
					</th>
					<th width="13%">
						上传时间
					</th>
					<th width="7%">
						操作
					</th>	
				</tr>
				<c:forEach items="${attaList2}" var="attaList2" varStatus="i">
					<tr>
						<td>
							${i.index+1}
						</td>
						<td>${attaList2.flowNodeName}</td>
						<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px">
							<b>${attaList2.attachmentName}</b>
						</td>
						<td>
							${attaList2.uploadOperator}
						</td>
						<td>
						    ${attaList2.formatUploadDate}
						</td>
						<td align="center">
						   <table  class="jz_a01" border="0" cellspacing="0" cellpadding="0">
						    <tr>
						     <td><img src="/SRMC/rmpb/images/tab_tb09.png" align="middle"/></td>
							 <td><a href="<%=basePath%>developFlowManage/downloadAttachmentVersion?createFlowAttaId=${attaList2.publishFlowAttaId}">下载</a></td>
							</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
			</table>
			</c:if>
             </div>
           <div class="ge_a01b"></div>
			<div class="gl_bt_bnt01">
				其它附件列表
			</div>
			 <table class="" width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
				<td width="80">
					<b>上传其它附件:</b>
				</td>
				<td width="80">
					<input  type="file" name="fileName" id="uploadFiles" /> 
					<!-- <a onclick="javascript:jQuery('#uploadFiles').uploadifyUpload()"><input name="input" type="button" class="ipt_tb_n02b" value="" /></a> -->	                                  
				</td>
				
				<td> <span id="result" style="font-size: 13px;color: red"></span></td>	
		     </tr>
	         </table>
				<table id="filesListTable3" class="gl_table_a02" width="100%"
				border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="5%">
						序号
					</th>
					<th width="13%">
					             节点名称
					</th>
					<th width="50%">
						附件名称
					</th>
					<th width="8%">
						上传人
					</th>
					<th width="13%">
						上传时间
					</th>
					<th width="11%">
						操作
					</th>	
				</tr>
				<c:forEach items="${attaList3}" var="attaList3" varStatus="i">
					<tr id="${attaList3.publishFlowAttaId}">
						<td>
							${i.index+1}
						</td>
						<td>${attaList3.flowNodeName}</td>
						<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px">
							<b>${attaList3.attachmentName}</b>
						</td>
						<td>
							${attaList3.uploadOperator}
						</td>
						<td>
							${attaList3.formatUploadDate}
						</td>
						<td align="center">
						  <table  class="jz_a01" border="0" cellspacing="0" cellpadding="0">
						    <tr>
							<td><img src="/SRMC/rmpb/images/tab_tb09.png" /></td>
							<td style="padding-right:5px;"><a href="<%=basePath%>developFlowManage/downloadAttachmentVersion?createFlowAttaId=${attaList3.publishFlowAttaId}">下载</a></td>
							 <c:if test="${userName4 == attaList3.uploadOperator}">
						     <td><img src="/SRMC/rmpb/images/tab_tb07.png" /></td>
						     <td style="padding-right:5px;" ><a href="javascript:deleteFileUpload('${attaList3.publishFlowAttaId}')">删除</a></td>
						     </c:if>
							</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
			</table>
			<div class="ge_a01b"></div>
				
			<div class="gl_bt_bnt01">
				流程跟踪<input type="button" class="gl_cx_bnt01b" value="收起跟踪"
							onclick="search()" id="toggleQueryButton" />
			</div>
			<div id="queryDiv">
			
			<%-- <table class="gl_table_a02" width="100%" border="0" cellspacing="0"
					cellpadding="0">
				<tr>
					<th width="15%">
						节点名称
					</th>
					<th width="7%">
						处理人
					</th>
					<th width="6%">
						处理时限
					</th>
					<th width="6%">
						超时状态
					</th>
					<th width="13%">
						处理时间
					</th>
					<th width="19%">
						处理方式
					</th>
					<th width="9%">
					下一节点处理人
					</th>
					<th width="25%">
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
						<td>${flowTrackingList.approveResult}</td>
						<td title="${flowTrackingList.approveResult}" style="text-align:left;padding-left:8px"><c:set var="approveResult" value="${flowTrackingList.approveResult}" />  					       
					           <c:out value="${approveResult}" />  					              								
						</td>	
					</tr>
				</c:forEach>
				
			</table> --%>
			
			<table class="gl_table_a02" width="100%" border="0" cellspacing="0"
					cellpadding="0">
				<tr>
				    <th width="5%">序号</th>
					<th width="13%">
						节点名称
					</th>
					<th width="7%">
						处理人
					</th>
					<th width="6%">
						处理时限
					</th>
					<th width="6%">
						超时状态
					</th>
					<th width="13%">
						处理时间
					</th>
					<th width="19%">
						处理方式
					</th>
					<!-- <th width="9%">
					下一节点处理人
					</th> -->
					<th width="31%">
						处理意见
					</th>
				</tr>
				<c:forEach items="${flowTrackingList}" var="flowTrackingList" varStatus="i">
					<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if> >
					    <td>${i.count}</td>
						<td>${flowTrackingList.fromNodeName}</td>
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
									  <c:if test="${flowTrackingList.doneStatus=='0'}">
										未超时
									 </c:if>
							    </td> 
							 </c:if>
				         </c:otherwise>
				        </c:choose>
						<%-- <td><fmt:formatDate value="${flowTrackingList.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
						<c:choose>
									    <c:when test="${empty flowTrackingList.operateTime}"><td>—</td></c:when>
									    <c:otherwise><td width=""><fmt:formatDate value="${flowTrackingList.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td></c:otherwise>
						</c:choose>
						<td><script>document.write(splitContent('${flowTrackingList.content}','0'));</script></td>
						<!-- <td><script>document.write(splitContent('${flowTrackingList.content}','1'));</script></td> -->
						<%-- <td>${flowTrackingList.approveResult}</td> --%>
						<td style="text-align:left;padding-left:8px;white-space:normal;word-break:break-all;"><c:set var="approveResult" value="${flowTrackingList.approveResult}" />  
					          <c:out value="${approveResult}" />  	     
						</td>	
					</tr>
				</c:forEach>
				
			</table>
			</div>
			<div class="gl_ipt_03">
			<a id="submitButton" ><input type="button" class="ipt_tb_n03" value="办理"  onclick="showSubpage()"/></a>
			&nbsp;<input type="button" class="ipt_tb_n03" value="返 回" onclick="javascript:history.back();"/>
			</div>
			<div style="height: 25px;"></div>
	</div>
  </form>
<script language="javascript" type="text/javascript">

function doSubmit(){
	jQuery.fancybox.close();
	jQuery("#submitForm").attr("action","<%=basePath%>core/portal/submitVersionApprove");
	document.forms[0].submit();
}



function showSubpage(){
	var projectId = document.getElementById("projectId").value;
	var flowinstanceId = document.getElementById("flowinstanceId").value;
	jQuery("#submitButton").attr("href","<%=basePath%>core/portal/toPreApproveVersionSubpage?developWorkorderId=" + jQuery("#developWorkorderId").val() + "&fromNode=" + jQuery("#fromNode").val()+"&projectId="+projectId+"&flowinstanceId="+flowinstanceId );
	jQuery("#submitButton").fancybox({
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
function doCloseSubpage(){
	jQuery.fancybox.close();
}
function viewRiskRemarks(developWorkloadId){
	jQuery("#" + developWorkloadId).attr("href", "<%=basePath%>developFlowManage/viewVersionDisRemarks?developWorkloadId=" + developWorkloadId );
	jQuery("#" + developWorkloadId).fancybox({
		'width'				: '80%',
		'height'			: '60%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
}

</script>
</body>
</html>