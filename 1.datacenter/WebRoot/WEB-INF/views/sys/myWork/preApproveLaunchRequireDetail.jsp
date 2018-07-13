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
		
		<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
		<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script>
		
		<script type="text/javascript">
		function deleteFileUpload(createFlowAttaId){
	if(confirm("确定要删除此文件？")){
		var url="<%=basePath%>developFlowManage/requirementItem/newRequirement/deleteFileUpload";
		var params = {
			isNew:'0',
			createFlowAttaId:createFlowAttaId
		};
		jQuery.post(url, params, function(data){
			if(data== "1"){
				jQuery("#" + createFlowAttaId).remove();
			}
		} , 'json');		
	}
}
		 
		jQuery(document).ready(function() {
		var launchWorkorderId=jQuery("#launchWorkorderId").val();
		   	 var user=jQuery("#user").val();
            jQuery('#uploadFiles').uploadify({  
                  'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf', 
                  'script'    : '<%=basePath%>developFlowManage/requirementFlow/manageRequirementFlow/launchfileUploads?launchWorkorderId='+launchWorkorderId,  
                  'cancelImg' : '/SRMC/rmpb/js/uploadify/cancel.png',  
                  'fileDataName':'file',  
                  'buttonImg' : '/SRMC/rmpb/images/tb_n_02.png',  
                  //允许连续上传多个文件   
                  'multi':'true',  
                  //一次性最多允许上传多少个,不设置的话默认为999个   
                  'queueSizeLimit' : 99,  
                  'auto' : true,
                  'onComplete'  : function(event, ID, fileObj, response, data) {  
                        //当每个文件上传完成后的操作   
                       
                        jQuery("#cancel").hide();
                        jQuery("#full").hide();
                        
                        var url = "<%=basePath%>developFlowManage/requirementFlow/manageRequirementFlow/queryFlowAttachmentByLaunchId";
						var params = {
							launchWorkorderId:jQuery("#launchWorkorderId").val()
						};
						jQuery.post(url, params, function(data){
							var fileList = data;
							var temp = '<tr><th width="8%">序号</th><th width="15%">节点名称</th><th width="42%">附件名称</th><th width="8%">上传人</th><th width="15%">上传时间</th><th width="12%">操作</th></tr>';
							for(var i=0; i<fileList.length; i++){
								var uploadYear = 1900 + fileList[i].uploadDate.year;
								var uploadMonth = ((1+fileList[i].uploadDate.month)<10)?("0" + (1+fileList[i].uploadDate.month)):(1+fileList[i].uploadDate.month);
								var uploadDay = (fileList[i].uploadDate.date<10)?("0" + fileList[i].uploadDate.date):(fileList[i].uploadDate.date);
								var uploadHours = (fileList[i].uploadDate.hours<10)?("0" + fileList[i].uploadDate.hours):(fileList[i].uploadDate.hours);
								var uploadMinutes = (fileList[i].uploadDate.minutes<10)?("0" + fileList[i].uploadDate.minutes):(fileList[i].uploadDate.minutes);
								var uploadSeconds = (fileList[i].uploadDate.seconds<10)?("0" + fileList[i].uploadDate.seconds):(fileList[i].uploadDate.seconds);
								temp += '<tr id="' + fileList[i].createFlowAttaId + '"><td>' + (i+1) + '</td><td>'+fileList[i].flowNodeName+'</td><td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><b>' + fileList[i].attachmentName + '</b></td><td>' + fileList[i].uploadOperator + '</td><td>' 
									+ uploadYear + "-" + uploadMonth + "-" + uploadDay  + " " + uploadHours + ":" + uploadMinutes + ":" + uploadSeconds + '</td><td align="center">' 
									+ '<table class="jz_a01" border="0" cellspacing="0" cellpadding="0"><tr><td width="20"><img  src="/SRMC/rmpb/images/tab_tb09.png" align="middle"/></td><td style="padding-right:10px"><a href="<%=basePath%>developFlowManage/downloadFlowAttachment?createFlowAttaId=' + fileList[i].createFlowAttaId + '">下载</a></td>' 
							    if(user==fileList[i].uploaderId){
									 temp += '<td width="20"><img src="/SRMC/rmpb/images/tab_tb07.png"/></td><td style="padding-right:8px"><a href="javascript:deleteFileUpload(\''+ fileList[i].createFlowAttaId + '\')">删除</a><td>';
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
        //需求名称的省略
			function ellipsisStr(str){
			 	var newStr = '';
			 	var array = new Array(); 
			 	array = str.split("");
			 	if(array.length > 18){
			 		newStr = str.substr(0, 18) + "..."; 
			 	}else{
			 		newStr = str;
			 	}
				return newStr;
         }
		</script>
	</head>

	<body class="bg_c_g">
		<form action="" method="post" id="submitForm">
		<input type="hidden" id="launchWorkorderId" name="launchWorkorderId" value="${launchWorkorder.launchWorkorderId}"/>
		<input type="hidden" id="fromNode" name="fromNode" value="${launchWorkorder.fromNode}"/>
		<input type="hidden" id="opitionContent" name="opitionContent" />
		<input type="hidden" id="transitionId" name="transitionId" />
		<input type="hidden" id="flowinstanceId" name="flowinstanceId" value='${launchWorkorder.flowInstanceId}'/>
		<input type="hidden" id="approveUserIdStr" name="approveUserIdStr" />
		<input type="hidden" id="CCUserIdStr" name="CCUserIdStr" />
		<input type="hidden" id="projectId" name="projectId" value='${launchWorkorder.projectId}'/>
		<input type="hidden" id="user" name="user" value="${user}"/>
		
		<c:choose>
			<c:when test="${empty requestScope.indexLocation }">
				<div class="gl_m_r_nav">当前位置&nbsp;:&nbsp;我的工作台&nbsp;>&nbsp;需求流程&nbsp;>&nbsp;审批的需求</div>
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
			<div class="ge_a01"></div>

			<div class="gl_bt_bnt01">
				需求信息
			</div>
			<table class="gl_table_a01_6L" width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<th width="100">
						工单编号:
					</th>
					<td>
						${launchWorkorder.workorderId}
					</td>
					<th width="100">
						需求负责人:
					</th>
					<td>
						${sysUser.userName}
					</td>
				<th width="100">提出时间:</th>
                 <td>
                 <fmt:formatDate value="${launchWorkorder.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                 </td>
					
				</tr>
				<tr>
				<th width="100">
						所属项目:
					</th>
					<td>
						${projectManagement.projectName}
					</td>
				<th width="100">
						项目版本:
					</th>
					<td>
						${versionManagement.versionCode}
					</td>
					<th width="100">
						需求名称:
					</th>
					<%-- <td>
						${launchWorkorder.name}
					</td> --%>
					<td title="${launchWorkorder.name}">	                  
	                  <script>document.write(ellipsisStr('${launchWorkorder.name}'));</script>
	                 </td>
					
				</tr>
				<tr>
				<th width="100">
						需求类型:
					</th>
					<td>
						<c:if test="${launchWorkorder.type=='1'}">产品功能</c:if>
						<c:if test="${launchWorkorder.type=='2'}">运营支撑</c:if>
						<c:if test="${launchWorkorder.type=='3'}">系统优化</c:if>
						<c:if test="${launchWorkorder.type=='4'}">营销策划</c:if>
						<c:if test="${launchWorkorder.type=='5'}">其它</c:if>
					</td>
					<th width="100">
						紧急程度:
					</th>
					<td>
						<c:if test="${launchWorkorder.emergencyDegree=='1'}">低</c:if>
						<c:if test="${launchWorkorder.emergencyDegree=='2'}">中</c:if>
						<c:if test="${launchWorkorder.emergencyDegree=='3'}">高</c:if>
						<c:if test="${launchWorkorder.emergencyDegree=='4'}">极高</c:if>
					</td>
					<th width="100">
						期望完成时间:
					</th>
					<td>
						<fmt:formatDate value="${launchWorkorder.expectFinishDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
			</table>

			<div class="ge_a01"></div>

			<div class="gl_bt_bnt01">
				需求相关
			</div>
			<table class="gl_table_a01" width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<th width="100">
						需求提出理由:
					</th>
					<td>
						<textarea style="background-color: #eff4fa; height:90px;" name="reason" class="gl_text01_d" id="reason" readonly="readonly">${launchWorkorder.reason}</textarea>
					</td>
				</tr>
				<tr>
					<th width="100">
						需求描述:
					</th>
					<td>
						<textarea style="background-color: #eff4fa; height:135px;" name="description" class="gl_text01_d" id="description" readonly="readonly">${launchWorkorder.description}</textarea>
					</td>
				</tr>
				<tr>
					<th width="100">
						预期带来的收益:
					</th>
					<td>
						<textarea style="background-color: #eff4fa; height:90px;" name="expectBenefits" class="gl_text01_d" id="expectBenefits" readonly="readonly">${launchWorkorder.expectBenefits}</textarea>
					</td>
				</tr>
			</table>
			<div class="ge_a01"></div>
			    
			<div class="gl_bt_bnt01">
				流程跟踪<input type="button" class="gl_cx_bnt01b" value="收起跟踪"
							onclick="search()" id="toggleQueryButton" />
			</div>
			<div id="queryDiv">
			<%-- <table class="gl_table_a02" width="100%" border="0" cellspacing="0"
					cellpadding="0">
				<tr>
					<th>
						节点名称
					</th>
					<th>
						处理人
					</th>
					<th width="6%">
						处理时限
					</th>
					<th width="6%">
						超时状态
					</th>
					<th>
						处理时间
					</th>
					<th>
						处理方式
					</th>
					<th width="9%">
					下一节点处理人
					</th>
					<th width="20%">
						处理意见
					</th>
				</tr>
				<c:forEach items="${flowTrackingList}" var="flowTrackingList" varStatus="i">
					<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if> >
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
			
			<c:if test="${requireAttaList!=null && fn:length(requireAttaList)!=0}">
				<div class="ge_a01"></div>
				<div class="gl_bt_bnt01">
					需求附件列表
				</div>
				
				<table id="requireAttaListTable" class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th width="8%">
							序号
						</th>
						<th width="46%">
							附件名称
						</th>
						<th width="8%">
							附件类型
						</th>
						<th width="8%">
							是否最终版
						</th>
						<th width="8%">
							上传人
						</th>
						<th width="14%">
							上传时间
						</th>
						<th width='8%'>
							操作
						</th>
					</tr>
					
					<c:forEach items="${requireAttaList}" var="requireAttaList" varStatus="j">
						<tr id="${j.index+1}"
							<c:if test="${j.index%2 != 0}">class="tab_c_tr01"</c:if>>
							<td>
								${j.index+1}
							</td>
							<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px">
								<b>${requireAttaList[0].attachmentName}</b>
							</td>
							<td>
								<c:if test="${requireAttaList[0].type=='1'}">
									高保真
								</c:if>
								<c:if test="${requireAttaList[0].type=='2'}">
									需求文档
								</c:if>
								<c:if test="${requireAttaList[0].type=='3'}">
									技术文档
								</c:if>
								<c:if test="${requireAttaList[0].type=='4'}">
									测试用例
								</c:if>
								<c:if test="${requireAttaList[0].type=='5'}">
									其它文件
								</c:if>
							</td>
							<td>
								<c:if test="${requireAttaList[0].finalVersionFlag=='1'}">
									是
								</c:if>
								<c:if test="${requireAttaList[0].finalVersionFlag=='0'}">
									否
								</c:if>
							</td>
							<td>
								${requireAttaList[1].userName}
							</td>
							<td>
								<fmt:formatDate value="${requireAttaList[0].uploadDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<span class="gl_tab_tr_l">
									<img src="/SRMC/rmpb/images/tab_tb09.png" align="middle"/>
								</span>
								<a href="<%=basePath%>developFlowManage/downloadAttachment?createAttachmentId=${requireAttaList[0].createAttachmentId}">下载</a>
							</td>
						</tr>
					</c:forEach>
					
				</table>
			</c:if>
			
			<div class="ge_a01"></div>
			<div class="gl_bt_bnt01">
				附件列表
			</div>
			<table class="" width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<th width="90" align="left">
						上传新的附件:
					</th>
					
					<td width="80">
						<input  type="file" name="fileName" id="uploadFiles" /> 
						<!-- <a onclick="javascript:jQuery('#uploadFiles').uploadifyUpload()"><input name="input" type="button" class="ipt_tb_n02b" value="" /></a> -->	                                  
					</td>
					<td> <span id="result" style="font-size: 13px;color: red"></span></td>
						
				</tr>
				
			</table>
			<div class="yan_zhu01">
				注: 高保真原型、需求文档、技术文档、测试用例、需求其他文件请在需求池上传
			</div>
			<table id="filesListTable" class="gl_table_a02" width="100%"
				border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="8%">
						序号
					</th>
					<th width="15%">
					             节点名称
					</th>
					<th width="42%">
						附件名称
					</th>
					<th width="8%">
						上传人
					</th>
					<th width="15%">
						上传时间
					</th>
					<th width="12%">
						操作
					</th>
				</tr>
				<c:forEach items="${fileUploads}" var="file" varStatus="i">
					<tr <%-- id="${i.index+1}" --%> id="${file.createFlowAttaId}"
						<c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if>>
						<td>
							${i.index+1}
						</td>
						<td>${file.flowNodeName}</td>
						<td  style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px">
							<b>${file.attachmentName}</b>
						</td>
						<td>
							${file.uploadOperator}
						</td>
						<td>
							<fmt:formatDate value="${file.uploadDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<table  class="jz_a01" border="0" cellspacing="0" cellpadding="0">
						   <tr align="center">
						     <td align="center"><img src="/SRMC/rmpb/images/tab_tb09.png" align="middle"/></td>
						     <td style="padding-right:10px"><a href="<%=basePath%>developFlowManage/downloadFlowAttachment?createFlowAttaId=${file.createFlowAttaId}">下载</a></td>
						     <c:if test="${user == file.uploaderId}">
				 			  <td><img src="/SRMC/rmpb/images/tab_tb07.png" /></td>
				 			 <!--  a href="javascript:deleteFileUpload(\''+ fileList[i].createFlowAttaId + '\')" -->
						      <td style="padding-right:8px;" ><a href="javascript:deleteFileUpload('${file.createFlowAttaId}')">删除</a></td> 
						     </c:if>
						   </tr>
						</table>
						</td>
					</tr>
				</c:forEach>

			</table>

			<div class="gl_ipt_03">
				<a id="submitButton"><input type="button" class="ipt_tb_n03"  onclick="showSubpage()" value="办理" /></a>
				&nbsp;<a id="returnButton"><input type="button"  onclick="javascript:history.back();" class="ipt_tb_n03" value="返回" /></a>
			</div>
			<div style="height: 25px;"></div>
	</div>
	</form>

<script language="javascript" type="text/javascript">
function showSubpage(){
	var projectId = document.getElementById("projectId").value;
	var flowinstanceId = document.getElementById("flowinstanceId").value;
	jQuery("#submitButton").attr("href","<%=basePath%>core/portal/toPreApproveRequireDetailSubpageWork?launchWorkorderId=" + jQuery("#launchWorkorderId").val() + "&fromNode=" + jQuery("#fromNode").val()+"&projectId="+projectId+"&flowinstanceId="+flowinstanceId );
	jQuery("#submitButton").fancybox({
		"autoScale"			: false,
		"transitionIn"		: "none",
		"transitionOut"		: "none",
		"type"				: "iframe"
	});
	
}

function doCloseSubpage(){
	jQuery.fancybox.close();
}

function doSubmit(){
	jQuery.fancybox.close();
	jQuery("#submitForm").attr("action","<%=basePath%>core/portal/submitRequireApproveWork");
	document.forms[0].submit();
}


</script>

	</body>


</html>