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
		<script type="text/javascript" src="/SRMC/rmpb/js/bootstrap-tooltip.js"></script>
		
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
			//附件上传
			var developWorkorderId = jQuery("#developWorkorderId").val();
			var flowinstanceId = jQuery("#flowinstanceId").val();
		   	 var userId = jQuery("#userId").val();
		   	 var url='<%=basePath%>developFlowManage/developFlow/manageRequireDevelop/developFileUploads?developWorkorderId='+developWorkorderId+','+flowinstanceId; 
            jQuery('#uploadFiles').uploadify({  
                  'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf', 
                  'script'    : url,  
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
                        
                        var url = "<%=basePath%>developFlowManage/developFlow/manageRequireDevelop/queryFlowAttachmentByDevelopId";
						var params = {
							developWorkorderId:jQuery("#developWorkorderId").val(),
							flowinstanceId:flowinstanceId
						};
						jQuery.post(url, params, function(data){
							var fileList = data;
							var temp ='<tr><th width="5%">序号</th><th width="13%">节点名称</th><th width="51%">附件名称</th><th width="8%">上传人</th><th width="13%">上传时间</th><th width="10%">操作</th></tr>';
							for(var i=0; i<fileList.length; i++){
								var uploadYear = 1900 + fileList[i].uploadDate.year;
								var uploadMonth = ((1+fileList[i].uploadDate.month)<10)?("0" + (1+fileList[i].uploadDate.month)):(1+fileList[i].uploadDate.month);
								var uploadDay = (fileList[i].uploadDate.date<10)?("0" + fileList[i].uploadDate.date):(fileList[i].uploadDate.date);
								var uploadHours = (fileList[i].uploadDate.hours<10)?("0" + fileList[i].uploadDate.hours):(fileList[i].uploadDate.hours);
								var uploadMinutes = (fileList[i].uploadDate.minutes<10)?("0" + fileList[i].uploadDate.minutes):(fileList[i].uploadDate.minutes);
								var uploadSeconds = (fileList[i].uploadDate.seconds<10)?("0" + fileList[i].uploadDate.seconds):(fileList[i].uploadDate.seconds);
								temp += '<tr id="' + fileList[i].createFlowAttaId + '"><td>' + (i+1) + '</td><td>'+fileList[i].flowNodeName+'</td><td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><b>' + fileList[i].attachmentName + '</b></td><td>' + fileList[i].uploadOperator + '</td><td>' 
									+ uploadYear + "-" + uploadMonth + "-" + uploadDay  + " " + uploadHours + ":" + uploadMinutes + ":" + uploadSeconds + '</td><td align="center">' 
									+ '<table class="jz_a01" border="0" cellspacing="0" cellpadding="0"><tr><td width="20"><img  src="/SRMC/rmpb/images/tab_tb09.png" align="middle"/></td><td style="padding-right:5px"><a href="<%=basePath%>developFlowManage/downloadFlowAttachment?createFlowAttaId=' + fileList[i].createFlowAttaId + '">下载</a></td>' 
							    if(userId == fileList[i].uploaderId){
									 temp += '<td width="20"><img src="/SRMC/rmpb/images/tab_tb07.png"/></td><td style="padding-right:5px"><a href="javascript:deleteFileUpload(\''+ fileList[i].createFlowAttaId + '\')">删除</a><td>';
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
			
			var rolePrivilege = jQuery("#rolePrivilege").val();
			for(var i=1; i<jQuery("#workloadListTable").find("tr").length; i++ ){
				if(rolePrivilege == "1"){
					var workItem = jQuery("#workloadListTable").children().children(":eq(" + i + ")").children(":eq(2)").children().html();
					/* workItem = ellipsisStr(workItem); */
					jQuery("#workloadListTable").children().children(":eq(" + i + ")").children(":eq(2)").children().html(workItem);
				}else{
					var workItem = jQuery("#workloadListTable").children().children(":eq(" + i + ")").children(":eq(1)").children().html();
					/* workItem = ellipsisStr(workItem); */
					jQuery("#workloadListTable").children().children(":eq(" + i + ")").children(":eq(1)").children().html(workItem);
				}
			}
			
			for(var i=1; i<jQuery("#riskListTable").find("tr").length; i++ ){
				if(rolePrivilege == "1"){
					var content = jQuery("#riskListTable").children().children(":eq(" + i + ")").children(":eq(5)").children().html();
					/* content = ellipsisStr(content); */
					jQuery("#riskListTable").children().children(":eq(" + i + ")").children(":eq(5)").children().html(content);
					var countermeasures = jQuery("#riskListTable").children().children(":eq(" + i + ")").children(":eq(6)").children().html();
					/* countermeasures = ellipsisStr(countermeasures); */
					jQuery("#riskListTable").children().children(":eq(" + i + ")").children(":eq(6)").children().html(countermeasures);
				}else{
					var content = jQuery("#riskListTable").children().children(":eq(" + i + ")").children(":eq(4)").children().html();
					/* content = ellipsisStr(content); */
					jQuery("#riskListTable").children().children(":eq(" + i + ")").children(":eq(4)").children().html(content);
					var countermeasures = jQuery("#riskListTable").children().children(":eq(" + i + ")").children(":eq(5)").children().html();
					/* countermeasures = ellipsisStr(countermeasures); */
					jQuery("#riskListTable").children().children(":eq(" + i + ")").children(":eq(5)").children().html(countermeasures);
				}
			}
		});
		
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
		
		function ellipsisStr2(str){
		 	var newStr = '';
		 	var array = new Array(); 
		 	array = str.split("");
		 	if(array.length > 10){
		 		newStr = str.substr(0, 10) + "..."; 
		 	}else{
		 		newStr = str;
		 	}
			return newStr;
		}
		
		function ellipsisStrByComma(str){
		 	var newStr = str.replace(/\,/g, "、");
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
		
		function toggleFlowTrackingDiv(){
			var display = document.getElementById("flowTrackingDiv").style.display;
			if(display=='block'||display==''){
				document.getElementById("flowTrackingDiv").style.display='none';
				document.getElementById("toggleQueryButton").value="展开跟踪";
				document.getElementById("toggleQueryButton").className="gl_cx_bnt01b";
			}else{
				document.getElementById("flowTrackingDiv").style.display='block';
				document.getElementById("toggleQueryButton").value="收起跟踪";
				document.getElementById("toggleQueryButton").className="gl_cx_bnt01";
			}
		}
		
		function changeValue(value){
			document.getElementById("versionId").value=value;
			 var versionId=jQuery("#versionId").val();
			 var versionStatus=jQuery("#version_"+versionId).val();
			 if(versionStatus=="2"){
				 alert("此项目已经达到冻结时间，不可创建该项目的需求");
				 jQuery("#version").val("${versionManagement.versionId}");
				 jQuery("#versionId").val("${versionManagement.versionId}")
				 //jQuery("#saveThisButton").attr("disabled","disabled");
				 //jQuery("#submitButton").css("display","none");
			 }else if(versionStatus=="5"){
				 alert("此项目已经提交部署或已上线，不可创建该项目的需求");
				 jQuery("#version").val("${versionManagement.versionId}");
				 jQuery("#versionId").val("${versionManagement.versionId}")
				 //jQuery("#saveThisButton").attr("disabled","disabled");
				 //jQuery("#submitButton").css("display","none");
			 }else{
				 jQuery("#saveThisButton").removeAttr("disabled");
				  jQuery("#submitButton").css("display","block");
			 }
		}
		
		//需求名称的省略
			function ellipsisStr3(str){
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
		<input type="hidden" id="developWorkorderId" name="developWorkorderId" value="${developWorkorder.developWorkorderId}"/>
		<input type="hidden" id="fromNode" name="fromNode" value="${developWorkorder.fromNode}"/>
		<input type="hidden" id="projectId" name="projectId" value="${developWorkorder.projectId}"/>
		<input type="hidden" id="versionId" name="versionId" value="${developWorkorder.versionId}"/>
		<input type="hidden" id="opitionContent" name="opitionContent" />
		<input type="hidden" id="transitionId" name="transitionId" />
		<input type="hidden" id="approveUserIdStr" name="approveUserIdStr" />
		<input type="hidden" id="flowinstanceId" name="flowinstanceId" value="${developWorkorder.flowInstanceId}"/>
		<input type="hidden" id="CCUserIdStr" name="CCUserIdStr" />
<%--		<input type="hidden" id="versionId" name="versionId"/>--%>
		<input type="hidden" id="rolePrivilege" name="rolePrivilege" value="${rolePrivilege}"/>
		<input type="hidden" id="location" name="location" value="${location }"/>
		<input type="hidden" id="userId" name="userId" value="${userId}"/>
		<c:choose>
			<c:when test="${empty requestScope.indexLocation }">
				<div class="gl_m_r_nav">当前位置&nbsp;:&nbsp;研发流程管理&nbsp;&gt;&nbsp;研发管理&nbsp;&gt;&nbsp;待办的研发事项</div>
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
						${developWorkorder.workorderId}
					</td>
					<th width="100">
						需求负责人:
					</th>
					<td>
						${sysUser.userName}
					</td>
					<th width="100">
						提出时间:
					</th>
					<td>
						<fmt:formatDate value="${developWorkorder.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
						<c:if test="${flowNodeModel.flowNodeId=='13062309000000000012'||flowNodeModel.flowNodeId=='13062309000000000011'||flowNodeModel.flowNodeId=='13062309000000000014' }">
							<select name="version" onchange="changeValue(this.value)"  style="width:100%" id="version">
							<c:forEach items="${version}" var="version" varStatus="i">
								<option value='${version.versionId}' <c:if test="${versionManagement.versionId==version.versionId}">selected </c:if> >${ version.versionCode}</option>
							</c:forEach>
							</select>
							
							<c:forEach items="${version}" var="versionList1" varStatus="i">
							        <input type="hidden" id="version_${versionList1.versionId}" value="${versionList1.versionStatus}"/>
							</c:forEach>
						</c:if>
						<c:if test="${flowNodeModel.flowNodeId!='13062309000000000012'&&flowNodeModel.flowNodeId!='13062309000000000011'&&flowNodeModel.flowNodeId!='13062309000000000014' }">
							${versionManagement.versionCode}
						</c:if>
						
					</td>
					<th width="100">
						需求名称:
					</th>
					<td title="${developWorkorder.name}">
						<%-- ${developWorkorder.name} --%>
						<script>document.write(ellipsisStr3('${developWorkorder.name}'));</script>
					</td>
					
				</tr>
				<tr>
				<th width="100">
						需求类型:
					</th>
					<td>
						<c:if test="${developWorkorder.type=='1'}">产品功能</c:if>
						<c:if test="${developWorkorder.type=='2'}">运营支撑</c:if>
						<c:if test="${developWorkorder.type=='3'}">系统优化</c:if>
						<c:if test="${developWorkorder.type=='4'}">营销策划</c:if>
						<c:if test="${developWorkorder.type=='5'}">其它</c:if>
					</td>
					<th width="100">
						紧急程度:
					</th>
					<td>
						<c:if test="${developWorkorder.emergencyDegree=='1'}">低</c:if>
						<c:if test="${developWorkorder.emergencyDegree=='2'}">中</c:if>
						<c:if test="${developWorkorder.emergencyDegree=='3'}">高</c:if>
						<c:if test="${developWorkorder.emergencyDegree=='4'}">极高</c:if>
					</td>
					<th width="100">
						期望完成时间:
					</th>
					<td>
						<fmt:formatDate value="${developWorkorder.expectFinishDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
			</table>
			
			<c:if test="${rolePrivilege=='1'}">
				<div class="ge_a01"></div>
				<div class="gl_bt_bnt01">
					工作量信息
				</div>
				<div class="gl_bnt_nav01" style="border-bottom: none;">
					<a id="addWorkloadButton"><input type="button" class="gl_cx_bnt04b" style="margin-right:8px;" value="添加工作项" onclick="addWorkload()"/></a>
					<a id="editWorkloadButton"><input type="button" class="gl_cx_bnt04b" style="margin-right:8px;" value="更新工作项" onclick="editWorkload()"/></a>
					<a id="deleteWorkloadButton"><input type="button" class="gl_cx_bnt04b" style="margin-right:8px;" value="删除工作项" onclick="deleteWorkload()"/></a>
				</div>
				<table id="workloadListTable" class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th width="20"></th>
						<th width="10%;">
							子系统
						</th>
						<th width="26%;">
							工作项
						</th>
						<th  width="10%;">
							责任人
						</th>
						<th width="19%;">
							投入资源
						</th>
						<th width="12%;">
							工作量(人/天）
						</th>
						<th width="8%;">
							研发百分比
						</th>
						<th width="8%;">
							备注
						</th>
						<th width="7%;">
							状态
						</th>
					</tr>
					<c:forEach items="${workloadList}" var="workloadList" varStatus="i">
						<tr id="developWorkloadIdTR_${workloadList[0].developWorkloadId}">
							<td>
								<input type="radio" class="myClass" id="developWorkloadId" name="developWorkloadId" value="${workloadList[0].developWorkloadId}" />
							</td>
							<td>${workloadList[1].projectSonName}</td>
							<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="${workloadList[0].workItem}" style="text-decoration:none;">${workloadList[0].workItem}</a></td>
							<td>${workloadList[0].responsibleName}</td>
							<td>${workloadList[0].inputResourceName}</td>
							<td>${workloadList[0].workingHours}</td>
							<td>${workloadList[0].percent}</td>
							<td>
								<c:choose>
									<c:when test="${workloadList[0].remarks == null || workloadList[0].remarks == ''}">
										—
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0);" id="${workloadList[0].developWorkloadId}" class="fone_style02"><span onclick="viewWorkloadRemarks('${workloadList[0].developWorkloadId}')"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/>查看</span></a>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:if test="${workloadList[0].status=='1'}">即将开发</c:if>
								<c:if test="${workloadList[0].status=='2'}">开发中</c:if>
								<c:if test="${workloadList[0].status=='3'}">测试中</c:if>
								<c:if test="${workloadList[0].status=='5'}">研发完成</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<div class="ge_a01"></div>
				<div class="gl_bt_bnt01">
					风险评估信息
				</div>
				<div class="gl_bnt_nav01" style="border-bottom: none;">
					<a id="addRiskButton"><input type="button" class="gl_cx_bnt04b" style="margin-right:8px;" value="添加风险评估" onclick="addRisk()"/></a>
					<a id="editRiskButton"><input type="button" class="gl_cx_bnt04b" style="margin-right:8px;" value="更新风险评估" onclick="editRisk()"/></a>
					<a id="deleteRiskButton"><input type="button" class="gl_cx_bnt04b" style="margin-right:8px;" value="删除风险评估" onclick="deleteRisk()"/></a>
					
				</div>
				<table id="riskListTable" class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th width="20"></th>
						<th width="10%;">
							子系统
						</th>
						<th width="10%;">
							评估人
						</th>
						<th width="7%;">
							风险类型
						</th>
						<th width="15%">
							涉及模块
						</th>
						<th width="25%;">
							风险内容
						</th>
						<th width="25%;">
							应对措施
						</th>
						<th width="8%;">
							备注
						</th>
					</tr>
					<c:forEach items="${riskList}" var="riskList" varStatus="i">
						<tr id="developRiskIdTR_${riskList[0].developRiskId}">
							<td>
								<input type="radio" class="myClass" id="developRiskId" name="developRiskId" value="${riskList[0].developRiskId}" />
							</td>
							<td>${riskList[1].projectSonName}</td>
							<td>${riskList[2].userName}</td>
							<td>
								<c:if test="${riskList[0].riskType=='1'}">时间风险</c:if>
								<c:if test="${riskList[0].riskType=='2'}">质量风险</c:if>
								<c:if test="${riskList[0].riskType=='3'}">安全风险</c:if>
								<c:if test="${riskList[0].riskType=='4'}">其他风险</c:if>
							</td>
							<td><a rel="tooltip" title="${riskList[0].moduleId}" style="text-decoration:none;">${riskList[0].moduleId}</a></td>
							<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="${riskList[0].content}" style="text-decoration:none;">${riskList[0].content}</a></td>
							<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="${riskList[0].countermeasures}" style="text-decoration:none;">${riskList[0].countermeasures}</a></td>
							<td>
								<c:choose>
									<c:when test="${riskList[0].remarks == null || riskList[0].remarks == ''}">
										—
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0);" id="${riskList[0].developRiskId}" class="fone_style02"><span onclick="viewRiskRemarks('${riskList[0].developRiskId}')"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/>查看</span></a>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			
			<c:if test="${rolePrivilege=='0'}">
				<div class="ge_a01"></div>
				<div class="gl_bt_bnt01">
					工作量信息
				</div>
				<!-- <div class="gl_bnt_nav01" style="border-bottom: none;">
					<a id="changeWorkloadButton" onclick="changeWorkloadStatus()"><input type="button" class="gl_cx_bnt04b" style="margin-right:8px;" value="更改状态" /></a>
				</div> -->
				<table id="workloadListTable" class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<!--<th width="32"></th>
						-->
						<th width="10%;">
							子系统
						</th>
						<th width="41%;">
							工作项
						</th>
						<th  width="8%;">
							责任人
						</th>
						<th width="9%;">
							投入资源
						</th>
						<th width="10%">
							工作量(人/天）
						</th>
						<th width="8%;">
							研发百分比
						</th>
						<th width="7%;">
							备注
						</th>
						<th width="7%;">
							状态
						</th>
					</tr>
					<c:forEach items="${workloadList}" var="workloadList" varStatus="i">
						<tr id="developWorkloadIdTR_${workloadList[0].developWorkloadId}">
							<!-- <td>
								<input type="radio" class="myClass" id="developWorkloadId" name="developWorkloadId" value="${workloadList[0].developWorkloadId}" />
							</td> -->
							<td>${workloadList[1].projectSonName}</td>
							<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="${workloadList[0].workItem}" style="text-decoration:none;">${workloadList[0].workItem}</a></td>
							<td>${workloadList[0].responsibleName}</td>
							<td>${workloadList[0].inputResourceName}</td>
							<td>${workloadList[0].workingHours}</td>
							<td>${workloadList[0].percent}</td>
							<td>
								<c:choose>
									<c:when test="${workloadList[0].remarks == null || workloadList[0].remarks == ''}">
										—
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0);" id="${workloadList[0].developWorkloadId}" class="fone_style02"><span onclick="viewWorkloadRemarks('${workloadList[0].developWorkloadId}')"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/>查看</span></a>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:if test="${workloadList[0].status=='1'}">即将开发</c:if>
								<c:if test="${workloadList[0].status=='2'}">研发中</c:if>
								<c:if test="${workloadList[0].status=='3'}">测试中</c:if>
								<c:if test="${workloadList[0].status=='4'}">研发完成</c:if>
								<c:if test="${workloadList[0].status=='5'}">测试完成</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<div class="ge_a01"></div>
				<div class="gl_bt_bnt01">
					风险评估信息
				</div>
				<table id="riskListTable" class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th width="10%;">
							子系统
						</th>
						<th width="10%;">
							评估人
						</th>
						<th width="7%;">
							风险类型
						</th>
						<th width="15%">
							涉及模块
						</th>
						<th width="25%;">
							风险内容
						</th>
						<th width="25%;">
							应对措施
						</th>
						<th width="8%;">
							备注
						</th>
					</tr>
					<c:forEach items="${riskList}" var="riskList" varStatus="i">
						<tr id="developRiskIdTR_${riskList[0].developRiskId}">
							<td>${riskList[1].projectSonName}</td>
							<td>${riskList[2].userName}</td>
							<td><c:if test="${riskList[0].riskType=='1'}">时间风险</c:if>
								<c:if test="${riskList[0].riskType=='2'}">质量风险</c:if>
								<c:if test="${riskList[0].riskType=='3'}">安全风险</c:if>
								<c:if test="${riskList[0].riskType=='4'}">其他风险</c:if>
							</td>
							<td><a rel="tooltip" title="${riskList[0].moduleId}" style="text-decoration:none;"><${riskList[0].moduleId}</a></td>
							<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="${riskList[0].content}" style="text-decoration:none;">${riskList[0].content}</a></td>
							<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="${riskList[0].countermeasures}" style="text-decoration:none;">${riskList[0].countermeasures}</a></td>
							<td>
								<c:choose>
									<c:when test="${riskList[0].remarks == null || riskList[0].remarks == ''}">
										—
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0);" id="${riskList[0].developRiskId}" class="fone_style02"><span onclick="viewRiskRemarks('${riskList[0].developRiskId}')"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/>查看</span></a>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
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
						<textarea style="background-color: #eff4fa; height: 90px;" name="reason" class="gl_text01_d" id="reason" readonly="readonly">${developWorkorder.reason}</textarea>
					</td>
				</tr>
				<tr>
					<th width="100">
						需求描述:
					</th>
					<td>
						<textarea style="background-color: #eff4fa; height: 135px;" name="description" class="gl_text01_d" id="description" readonly="readonly">${developWorkorder.description}</textarea>
					</td>
				</tr>
				<tr>
					<th width="100">
						预期带来的收益:
					</th>
					<td>
						<textarea style="background-color: #eff4fa; height: 90px;" name="expectBenefits" class="gl_text01_d" id="expectBenefits" readonly="readonly">${developWorkorder.expectBenefits}</textarea>
					</td>
				</tr>
			</table>
			<div class="ge_a01"></div>
			    
			<div class="gl_bt_bnt01">
				流程跟踪<input type="button" class="gl_cx_bnt01" value="收起跟踪"
							onclick="toggleFlowTrackingDiv()" id="toggleQueryButton" />
			</div>
			<div id="flowTrackingDiv">
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
						<td title="${flowTrackingList.approveResult}" style="text-align:left;padding-left:8px;white-space:normal;word-break:break-all;"><c:set var="approveResult" value="${flowTrackingList.approveResult}" />  					    
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
						<th width="5%">
							序号
						</th>
						<th width='52%'>
							附件名称
						</th>
						<th width='8%'>
							附件类型
						</th>
						<th width='7%'>
							是否最终版
						</th>
						<th width='8%'>
							上传人
						</th>
						<th width='13%'>
							上传时间
						</th>
						<th width="7%">
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
						<!-- <a onclick="javascript:jQuery('#uploadFiles').uploadifyUpload()">
							<input name="input" type="button" class="ipt_tb_n02b" value="" />
						</a>	  -->                                 
					</td>
					<td>
						<span id="result" style="font-size: 13px;color: red"></span>
					</td>
				</tr>
			</table>	
				
			<div class="yan_zhu01">
				注: 高保真原型、需求文档、技术文档、测试用例、需求其他文件请在需求池上传
			</div>
			<table id="filesListTable" class="gl_table_a02" width="100%"
				border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="5%">
						序号
					</th>
					<th width="13%">
					             节点名称
					</th>
					<th width="51%">
						附件名称
					</th>
					<th width="8%">
						上传人
					</th>
					<th width="13%">
						上传时间
					</th>
					<th width="10%">
						操作
					</th>
				</tr>
				<c:forEach items="${fileUploads}" var="file" varStatus="i">
					<tr id="${file.createFlowAttaId}" >
						<td>
							${i.index+1}
						</td>
						<td>${file.flowNodeName}</td>
						<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px">
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
						     <td style="padding-right:5px;"><a href="<%=basePath%>developFlowManage/downloadFlowAttachment?createFlowAttaId=${file.createFlowAttaId}">下载</a></td>
						     <c:if test="${userId == file.uploaderId}">
						     <td><img src="/SRMC/rmpb/images/tab_tb07.png" /></td>
						      <td style="padding-right:5px;" ><a href="javascript:deleteFileUpload('${file.createFlowAttaId}')">删除</a></td>
						     </c:if>
						   </tr>
						</table>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<!-- <div class="gl_ipt_03">
				<a id="submitButton"><input onclick="return showSubpage();" type="button" class="ipt_tb_n03" value="办理" /></a>
				&nbsp;
				<input type="button" class="ipt_tb_n03" value="返 回" onclick="javascript:history.back();"/>
			</div> -->
			
			
			<div>
			    <br/>
			     <table border="0" style="width: 100%;text-align: center;vertical-align: top;">
			     <tr><td align="right" style="width: 50%;vertical-align: top;"><a id="submitButton"><input onclick="return showSubpage();" type="button" class="ipt_tb_n03" value="办理" /></a>
				 &nbsp;
				</td>
			     <td align="left" style="width: 50%;vertical-align: top;">
			      &nbsp;<input type="button" class="ipt_tb_n03" value="返 回" onclick="javascript:history.back();"/></td></tr>
			     </table>
			</div>
			<div style="height: 25px;"></div>
	</div>
	</form>

<script language="javascript" type="text/javascript">
function changeWorkloadStatus(){
	var developWorkloadId = "";
	jQuery("input[id='developWorkloadId']").each(function () {
		if(jQuery(this).attr("checked") == "checked"){
			developWorkloadId = jQuery(this).val();
		}
	});
	if(developWorkloadId == ""){
		alert("请先选择需要修改的记录");
		return;
	}
	jQuery("#changeWorkloadButton").attr("href", "<%=basePath%>developFlowManage/developFlow/manageRequireDevelop/toChangeWorkloadStatus?developWorkloadId=" + developWorkloadId );
	jQuery("#changeWorkloadButton").fancybox({
		'width'				: '30%',
		'height'			: '60%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
}



function showSubpage(){
    var bl=false;
    var projectId = document.getElementById("projectId").value;
    var flowinstanceId = document.getElementById("flowinstanceId").value;
    jQuery("#submitButton").attr("href","");
	var m=jQuery("tr[id^='developWorkloadIdTR_']").length;
	var n=jQuery("tr[id^='developRiskIdTR_']").length;
	if(jQuery("#rolePrivilege").val() == "2"){
		jQuery("#submitButton").attr("href","<%=basePath%>core/portal/toPreApproveRequireDevelopDetailSubpageWork?developWorkorderId=" + jQuery("#developWorkorderId").val() + "&fromNode=" + jQuery("#fromNode").val()+"&projectId="+projectId+"&flowinstanceId="+flowinstanceId );
		jQuery("#submitButton").fancybox({
			"autoScale"			: false,
			"transitionIn"		: "none",
			"transitionOut"		: "none",
			"type"				: "iframe",
		});
		return true;
	}else if(m==0){
		jQuery.fancybox.close(); 
		alert("请添加工作量信息!");
		jQuery("#submitButton").fancybox({
			"autoScale"			: false,
			"transitionIn"		: "none",
			"transitionOut"		: "none",
			"type"				: "iframe",
			"onStart"			:function(current,previous){
			   return false;
			}
		});
		return false;
	}else{
		if(n==0){
		bl=true;
		if(bl==true){
			jQuery("#submitButton").attr("href","<%=basePath%>core/portal/toPreApproveRequireDevelopDetailSubpageWork?developWorkorderId=" + jQuery("#developWorkorderId").val() + "&fromNode=" + jQuery("#fromNode").val()+ "&refresh=" + new Date().getTime()+"&projectId="+projectId+"&flowinstanceId="+flowinstanceId );
			jQuery("#submitButton").fancybox({
				"autoScale"			: false,
				"transitionIn"		: "none",
				"transitionOut"		: "none",
				"type"				: "iframe",
				"onStart"			:function(current,previous){
				    if('${rolePrivilege}'==1){
				     return confirm("您确定不填写风险评估信息吗？");
				   }
				}
			});
			//alert("r");
			return true;
			}else{
				//alert("g");
				return false;
			}
		}else{
		 	bl=true;
			jQuery("#submitButton").attr("href","<%=basePath%>core/portal/toPreApproveRequireDevelopDetailSubpageWork?developWorkorderId=" + jQuery("#developWorkorderId").val() + "&fromNode=" + jQuery("#fromNode").val()+ "&refresh=" + new Date().getTime()+"&projectId="+projectId+"&flowinstanceId="+flowinstanceId );
			jQuery("#submitButton").fancybox({
				"autoScale"			: false,
				"transitionIn"		: "none",
				"transitionOut"		: "none",
				"type"				: "iframe",
				"onStart"			:function(current,previous){
				   return true;
				}
			});
			return true;
		}
	}
}

function doCloseSubpage(){
	jQuery.fancybox.close();
}

function doSubmit(){
	jQuery.fancybox.close();
	jQuery("#submitForm").attr("action","<%=basePath%>core/portal/submitDevelopApprove");
	document.forms[0].submit();
}

function doReturn(location){
	var path = "<%=basePath%>" + location;
	window.location.href = path;
}
	
function addWorkload(){
	jQuery("#addWorkloadButton").attr("href", "<%=basePath%>developFlowManage/developFlow/launchRequireDevelop/toEditWorkloadSubpage?developWorkorderId=" + jQuery("#developWorkorderId").val() + "&projectId=" + jQuery("#projectId").val() + "&versionId=" + jQuery("#versionId").val() );
	jQuery("#addWorkloadButton").fancybox({
		'width'				: '80%',
		'height'			: '100%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
}

function editWorkload(){
	jQuery("#editWorkloadButton").fancybox({
		"width"				: "80%",
		"height"			: "100%",
		"autoScale"			: false,
		"transitionIn"		: "none",
		"transitionOut"		: "none",
		"type"				: "iframe",
		"onStart"			:function(current,previous){
		   return false;
		}
	});
	var developWorkloadId = "";
	jQuery("input[id='developWorkloadId']").each(function () {
		if(jQuery(this).attr("checked") == "checked"){
			developWorkloadId = jQuery(this).val();
		}
	});
	if(developWorkloadId == ""){
		alert("请先选择需要修改的记录");
		return;
	}
	jQuery("#editWorkloadButton").attr("href", "<%=basePath%>developFlowManage/developFlow/launchRequireDevelop/toEditWorkloadSubpage?developWorkorderId=" + jQuery("#developWorkorderId").val() + "&developWorkloadId=" + developWorkloadId + "&projectId=" + jQuery("#projectId").val() + "&versionId=" + jQuery("#versionId").val() );
	jQuery("#editWorkloadButton").fancybox({
		'width'				: '80%',
		'height'			: '100%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
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
	if(confirm("确定要删除此工作量？")){
		var url = "<%=basePath%>developFlowManage/developFlow/launchRequireDevelop/deleteWorkload";
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

function refreshWorkloadArea(developWorkloadId){
	var url = "<%=basePath%>developFlowManage/developFlow/launchRequireDevelop/queryWorkloadList";
	var params = {
		developWorkorderId:jQuery("#developWorkorderId").val()
	};
	jQuery.post(url, params, function(data){
		var workloadList = data;
		jQuery("#workloadListTable").html("");
		var temp = '<tr><th width="20"></th><th width="10%;">子系统</th><th width="26%;">工作项</th><th width="10%;">责任人</th>';
		temp += '<th width="19%;">投入资源</th><th width="12%;">工作量(人/天）</th><th width="8%;">研发百分比</th><th width="8%;">备注';
		temp += '</th><th width="7%;">状态</th></tr>';
		for(var i=0; i<workloadList.length; i++){
			var statusName = "";
			if(workloadList[i][0].status=="1"){
				statusName = "即将开发";
			}else if(workloadList[i][0].status=="2"){
				statusName = "研发中";
			}else if(workloadList[i][0].status=="3"){
				statusName = "测试中";
			}else if(workloadList[i][0].status=="4"){
				statusName = "研发完成";
			}else if(workloadList[i][0].status=="5"){
				statusName = "测试完成";
			}
			if(developWorkloadId == workloadList[i][0].developWorkloadId){
				var remark = "";
				if(workloadList[i][0].remarks == null || workloadList[i][0].remarks == ""){
					remark = "—";
				}else{
					remark = '<a href="javascript:void(0);" id="' + workloadList[i][0].developWorkloadId + '" class="fone_style02"><span onclick="viewWorkloadRemarks(\'' + workloadList[i][0].developWorkloadId + '\')"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/>查看</span></a>';
				}
				temp += '<tr id="developWorkloadIdTR_' + workloadList[i][0].developWorkloadId + '">'
					+ '<td><input type="radio" class="myClass" id="developWorkloadId" name="developWorkloadId" value="' + workloadList[i][0].developWorkloadId + '" checked="checked"/></td>'
					+ '<td>' + workloadList[i][1].projectSonName + '</td>'
					+'<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="'+workloadList[i][0].workItem+'" style="text-decoration:none;">'+workloadList[i][0].workItem+'</a></td>'
					+ '<td>'+workloadList[i][0].responsibleName+'</td>'
					+'<td>'+workloadList[i][0].inputResourceName+'</td>'
					+ '<td>' + workloadList[i][0].workingHours +'</td>'
					+ '<td>' + workloadList[i][0].percent + '</td>'
					+'<td>' + remark + '</td>'			
					+ '<td>' + statusName + '</td>'
					+ '</tr>';
			}else{
				var remark = "";
				if(workloadList[i][0].remarks == null || workloadList[i][0].remarks == ""){
					remark = "—";
				}else{
					remark = '<a href="javascript:void(0);" id="' + workloadList[i][0].developWorkloadId + '" class="fone_style02"><span onclick="viewWorkloadRemarks(\'' + workloadList[i][0].developWorkloadId + '\')"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/>查看</span></a>';
				}
				temp += '<tr id="developWorkloadIdTR_' + workloadList[i][0].developWorkloadId + '">'
					+ '<td><input type="radio" class="myClass" id="developWorkloadId" name="developWorkloadId" value="' + workloadList[i][0].developWorkloadId + '"/></td>'
					+ '<td>' + workloadList[i][1].projectSonName + '</td>'
//					+'<td><a rel="tooltip" title="'+workloadList[i][0].workItem+'" style="text-decoration:none;">'+ellipsisStr(workloadList[i][0].workItem)+'</a></td>'			
					+'<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px" ><a rel="tooltip" title="'+workloadList[i][0].workItem+'" style="text-decoration:none;">'+workloadList[i][0].workItem+'</a></td>'			
					+'<td>'+ellipsisStrByComma(workloadList[i][0].responsibleName)+'</td>'
					+'<td>'+ellipsisStrByComma(workloadList[i][0].inputResourceName)+'</td>'
					+ '<td>' + workloadList[i][0].workingHours +'</td>'
					+ '<td> ' + workloadList[i][0].percent + '</td>'
					+'<td>' + remark + '</td>'
					+ '<td>' + statusName + '</td>'
					+ '</tr>';
			}
		}
		jQuery("#workloadListTable").html(temp);
	} , 'json');
}

function viewWorkloadRemarks(developWorkloadId){
	jQuery("#" + developWorkloadId).attr("href", "<%=basePath%>developFlowManage/viewDevelopWorkloadRemarks?developWorkloadId=" + developWorkloadId );
	jQuery("#" + developWorkloadId).fancybox({
		'width'				: '80%',
		'height'			: '60%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
}

function addRisk(){
	jQuery("#addRiskButton").attr("href", "<%=basePath%>developFlowManage/developFlow/launchRequireDevelop/toEditRiskSubpage?developWorkorderId=" + jQuery("#developWorkorderId").val() + "&projectId=" + jQuery("#projectId").val() );
	jQuery("#addRiskButton").fancybox({
		'width'				: '80%',
		'height'			: '100%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
}

function editRisk(){
	jQuery("#editRiskButton").fancybox({
		"width"				: "80%",
		"height"			: "100%",
		"autoScale"			: false,
		"transitionIn"		: "none",
		"transitionOut"		: "none",
		"type"				: "iframe",
		"onStart"			:function(current,previous){
		   return false;
		}
	});
	var developRiskId = "";
	jQuery("input[id='developRiskId']").each(function () {
		if(jQuery(this).attr("checked") == "checked"){
			developRiskId = jQuery(this).val();
		}
	});
	if(developRiskId == ""){
		alert("请先选择需要修改的记录");
		return;
	}
	jQuery("#editRiskButton").attr("href", "<%=basePath%>developFlowManage/developFlow/launchRequireDevelop/toEditRiskSubpage?developWorkorderId=" + jQuery("#developWorkorderId").val() + "&developRiskId=" + developRiskId + "&projectId=" + jQuery("#projectId").val() );
	jQuery("#editRiskButton").fancybox({
		'width'				: '80%',
		'height'			: '100%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
}

function deleteRisk(){
	var developRiskId = "";
	jQuery("input[id='developRiskId']").each(function () {
		if(jQuery(this).attr("checked") == "checked"){
			developRiskId = jQuery(this).val();
		}
	});
	if(developRiskId == ""){
		alert("请先选择需要删除的记录");
		return;
	}
	if(confirm("确定要删除此风险评估信息？")){
		var url = "<%=basePath%>developFlowManage/developFlow/launchRequireDevelop/deleteRisk";
		var params = {
			developRiskId:developRiskId
		};
		jQuery.post(url, params, function(data){
			if(data == "" || data == "0"){
				alert("删除失败！");
			}else{
				refreshRiskArea("");
				alert("删除成功！");
			}
		} , 'json');
	}
}

function refreshRiskArea(developRiskId){
	var url = "<%=basePath%>developFlowManage/developFlow/launchRequireDevelop/queryRiskList";
	var params = {
		developWorkorderId:jQuery("#developWorkorderId").val()
	};
	jQuery.post(url, params, function(data){
		var riskList = data;
		jQuery("#riskListTable").html("");
		var temp = '<tr><th width="20"><th width="10%;">子系统</th><th width="10%;">评估人</th><th width="7%;">风险类型</th>';
		temp += '<th width="15%">涉及模块</th><th width="25%;">风险内容</th><th width="25%;">应对措施</th><th width="8%;">备注</th></tr>';
		for(var i=0; i<riskList.length; i++){
			
			var statusName = "";
			if(riskList[i][0].riskType=="1"){
				statusName = "时间风险";
			}else if(riskList[i][0].riskType=="2"){
				statusName = "质量风险";
			}else if(riskList[i][0].riskType=="3"){
				statusName = "安全风险";
			}else if(riskList[i][0].riskType=="4"){
				statusName = "其他风险";
			}
		
			if(developRiskId == riskList[i][0].developRiskId){
				var remark = "";
				if(riskList[i][0].remarks == null || riskList[i][0].remarks == ""){
					remark = "—";
				}else{
					remark = '<a href="javascript:void(0);" id="' + riskList[i][0].developRiskId + '" class="fone_style02"><span onclick="viewRiskRemarks(\'' + riskList[i][0].developRiskId + '\')"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/>查看</span></a>';
				}
				temp += '<tr  id="developRiskIdTR_' + riskList[i][0].developRiskId + '">'
					+ '<td><input type="radio" class="myClass" id="developRiskId" name="developRiskId" value="' + riskList[i][0].developRiskId + '" checked="checked"/></td>'
					+ '<td>' + riskList[i][1].projectSonName + '</td>'
					+ '<td>' + riskList[i][2].userName + '</td>'
					+ '<td>' + statusName + '</td>'
					+'<td><a rel="tooltip" title="'+riskList[i][0].moduleId+'" style="text-decoration:none;">'+riskList[i][0].moduleId+'</a></td>'			
					+'<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="'+riskList[i][0].content+'" style="text-decoration:none;">'+riskList[i][0].content+'</a></td>'				
					+'<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="'+riskList[i][0].countermeasures+'" style="text-decoration:none;">'+riskList[i][0].countermeasures+'</a></td>'				
					+'<td>' + remark + '</td>'
					+ '</tr>';
			}else{
				var remark = "";
				if(riskList[i][0].remarks == null || riskList[i][0].remarks == ""){
					remark = "—";
				}else{
					remark = '<a href="javascript:void(0);" id="' + riskList[i][0].developRiskId + '" class="fone_style02"><span onclick="viewRiskRemarks(\'' + riskList[i][0].developRiskId + '\')"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/>查看</span></a>';
				}
				temp += '<tr  id="developRiskIdTR_' + riskList[i][0].developRiskId + '">'
					+ '<td><input type="radio" class="myClass" id="developRiskId" name="developRiskId" value="' + riskList[i][0].developRiskId + '"/></td>'
					+ '<td>' + riskList[i][1].projectSonName + '</td>'
					+ '<td>' + riskList[i][2].userName + '</td>'
					+ '<td>' + statusName + '</td>'
					+'<td><a rel="tooltip" title="'+riskList[i][0].moduleId+'" style="text-decoration:none;">'+riskList[i][0].moduleId+'</a></td>'			
					+'<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="'+riskList[i][0].content+'" style="text-decoration:none;">'+riskList[i][0].content+'</a></td>'				
					+'<td style="word-break:break-all;text-align:left;padding-left:8px;padding-right:8px"><a rel="tooltip" title="'+riskList[i][0].countermeasures+'" style="text-decoration:none;">'+riskList[i][0].countermeasures+'</a></td>'				
					+'<td>' + remark + '</td>'
					+ '</tr>';
			}
		}
		jQuery("#riskListTable").html(temp);
	} , 'json');
}

function viewRiskRemarks(developWorkloadId){
	jQuery("#" + developWorkloadId).attr("href", "<%=basePath%>developFlowManage/viewDevelopRiskRemarks?developWorkloadId=" + developWorkloadId );
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