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
		<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
		<link href="/SRMC/rmpb/css/date_input.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
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
		jQuery.extend(DateInput.DEFAULT_OPTS, { 
				month_names: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"], 
				short_month_names: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"], 
				short_day_names: ["一", "二", "三", "四", "五", "六", "日"],
				dateToString: function(date) {
					
					var month = (date.getMonth() + 1).toString();
					var dom = date.getDate().toString();
					if (month.length == 1) month = "0" + month;
					if (dom.length == 1) dom = "0" + dom;
					return date.getFullYear() + "-" + month + "-" + dom;
				}
			});
		
		jQuery(document).ready(function() {
			//jQuery("#expectFinishDate").date_input();
		var createWorkorderId=jQuery("#createWorkorderId").val();
		   	 var user=jQuery("#user").val();
		   	 //alert(user);
		   	// alert(createWorkorderId);
            jQuery('#uploadFiles').uploadify({  
                  'uploader'  : '/SRMC/rmpb/js/uploadify/uploadify.swf', 
                  'script'    : '<%=basePath%>developFlowManage/requirementItem/requirementApprove/createfileUploads?createWorkorderId='+createWorkorderId,  
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
                        
                     	var url = "<%=basePath%>developFlowManage/requirementItem/requirementApprove/queryFlowAttachmentByCreateId";
						var params = {
							createWorkorderId:jQuery("#createWorkorderId").val()
						};
						jQuery.post(url, params, function(data){
							var fileList = data;
							var temp = '<tr><th width="5%">序号</th><th width="11%">节点名称</th><th width="52%">附件名称</th><th width="8%">上传人</th><th width="13%">上传时间</th><th width="11%">操作</th></tr>';
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
									if(user==fileList[i].uploaderId){
									 temp += '<td width="20"><img src="/SRMC/rmpb/images/tab_tb07.png"/></td><td style="padding-right:18px"><a href="javascript:deleteFileUpload(\''+ fileList[i].createFlowAttaId + '\')">删除</a><td>';
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
		
function searchVersionByProject(){
	var url = "<%=basePath%>developFlowManage/requirementItem/newRequirement/searchVersionByProject";
	
	var pro = jQuery("#projectId").val();
	//alert(pro);
	if(pro==-1){
		return;
	}
	var params = {
		projectId:pro
	};
		jQuery("input[id^='version_']").remove();
		jQuery.post(url, params, function(data){
			var versionList = data;
			var option = '<option value="-1">请选择项目版本</option>';
			if(versionList!=null && versionList.length !=0){
			for(var i=0; i<versionList.length; i++){
				option += '<option value="' + versionList[i].versionId + '">' + versionList[i].versionCode + '</option>';
				
				jQuery("body").append("<input type='hidden' id='version_"+versionList[i].versionId+"' value='"+versionList[i].versionStatus+"'/>");
			}
			}
			jQuery("#versionId").html(option);
			//rSelectsSepecify("versionIdDiv");
		  } , 'json');
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
	function changeVersionStatus(){
	 var versionId=jQuery("#versionId").val();
	 //alert(versionId);
	 var versionStatus=jQuery("#version_"+versionId).val();
	//alert(versionStatus);
	
	   if(versionStatus=="2"){
	     //jQuery("#versionId").val("-1");
	     jQuery("#versionId").val("${createWorkorder.versionId}");
		 alert("此项目已经达到冻结时间，不可创建该项目的需求");
		 //jQuery("#saveThisButton").attr("disabled","disabled");
		 //jQuery("#submitButton").css("display","none");
		
		}else if(versionStatus=="5"){
		   //jQuery("#versionId").val("-1");
		   jQuery("#versionId").val("${createWorkorder.versionId}");
		   alert("此项目已经提交部署或已上线，不可创建该项目的需求");
		   //jQuery("#saveThisButton").attr("disabled","disabled");
		   //jQuery("#submitButton").css("display","none");
		   
		}else{	  
		      jQuery("#saveThisButton").removeAttr("disabled");
		      jQuery("#submitButton").css("display","block");	  	 
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
		<input type="hidden" id="createWorkorderId" name="createWorkorderId" value="${createWorkorder.createWorkorderId}"/>
		<input type="hidden" id="fromNode" name="fromNode" value="${createWorkorder.fromNode}"/>
		<input type="hidden" id="opitionContent" name="opitionContent" />
		<input type="hidden" id="location" name="location" value="${location }"/>
		<input type="hidden" id="transitionId" name="transitionId" />
        <%--<input type="hidden" id="projectId" name="projectId" value="${createWorkorder.projectId}"/>--%>
		<input type="hidden" id="approveUserIdStr" name="approveUserIdStr" />
		<input type="hidden" id="user" name="user" value="${user}"/>
		<input type="hidden" id="CCUserIdStr" name="CCUserIdStr" />
		<input type="hidden" id="flowinstanceId" name="flowinstanceId"  value="${createWorkorder.flowInstanceId}"/>
		<input type="hidden" id="headUserId" name="headUserId" value="${createWorkorder.headId}"/>
		<input type="hidden" id="isPrivilege" name="isPrivilege" value="${isPrivilege}"/>
		
		
		<c:choose>
			<c:when test="${empty requestScope.indexLocation }">
				<div class="gl_m_r_nav">当前位置&nbsp;:&nbsp;研发流程管理&nbsp;>&nbsp;需求管理&nbsp;>&nbsp;待审批的需求&nbsp;>&nbsp;需求详情</div>
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
				需求信息
			</div>
		
			<table class="gl_table_a01_6L" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <th width="100">工单编号:</th>
                 <td>${createWorkorder.workorderId}</td>
                 <th width="100">需求负责人:</th>
                 <td>
                 ${createWorkorder.headName}
                 </td>
                  <th width="100">提出时间:</th>
                 <td>
                 <fmt:formatDate value="${createWorkorder.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                 </td>
                 </tr>
                 <c:if test="${isPrivilege=='1'}">
                 	<tr>
                 <th>所属项目:
                 </th>
	                 <c:if test="${privilege=='1'}">
	                   <input type="hidden" id="projectId" name="projectId" value="${createWorkorder.projectId}"/>
		                 <td>
		                    ${projectManagement.projectName}
		                    
		                 </td>
	                 </c:if>
	                  <c:if test="${privilege=='0'}">
		                 <td>
		                    <DIV id=uboxstyle>
									<select id="projectId" name="projectId" class="select_new01" onchange="javascript:searchVersionByProject();">
										<option value="-1">请选择所属项目</option>
										<c:forEach items="${projectList}" var="projectList" varStatus="i">
											<option value="${projectList.projectId}" <c:if test="${projectList.projectId==createWorkorder.projectId}">selected</c:if>>${projectList.productName}-${projectList.projectName}</option>
										</c:forEach>
									</select>
									<c:if test="${projectList != null && fn:length(projectList) > 0}">
		                             <input type="hidden" name="projectId" value="${createWorkorder.projectId}"/>
		                           </c:if>
								</DIV>
		                 </td>
	                 </c:if>
                 <th>项目版本:</th>
                 <td>
                  <DIV id=uboxstyle>
							<div id="versionIdDiv">
							<select id="versionId" name="versionId" class="select_new01" onchange="changeVersionStatus();">
								<option value="-1">
									请选择项目版本
								</option>
								<c:forEach items="${versionList}" var="versionList" varStatus="i">
									<option value="${versionList.versionId}" <c:if test="${versionList.versionId==createWorkorder.versionId}">selected</c:if>>${versionList.versionCode}</option>
								</c:forEach>
							</select>
							<input type="hidden" id="version_-1" value="-1"/>
							<c:forEach items="${versionList}" var="versionList1" varStatus="i">							
							        <input type="hidden" id="version_${versionList1.versionId}" value="${versionList1.versionStatus}"/>
							</c:forEach>
							</div>
						</DIV>
                 </td>
                 <th>需求类型:</th>
                 <td><DIV id=uboxstyle>
							<select id="type" name="type" class="select_new01">
								<option value="-1">
									请选择需求类型
								</option>
								<option value="1" <c:if test="${createWorkorder.type=='1'}">selected</c:if>>
									产品功能
								</option>
								<option value="2" <c:if test="${createWorkorder.type=='2'}">selected</c:if>>
									运营支撑
								</option>
								<option value="3" <c:if test="${createWorkorder.type=='3'}">selected</c:if>>
									系统优化
								</option>
								<option value="4" <c:if test="${createWorkorder.type=='4'}">selected</c:if>>
									营销策划
								</option>
								<option value="5" <c:if test="${createWorkorder.type=='5'}">selected</c:if>>
									其它
								</option>
							</select>
						</DIV>
						</td>
               </tr>
               <tr>
                 <th>需求名称:</th>
                 <td ><input class="gl_text01_a" type="text" name="name" id="name" value="${createWorkorder.name}"/></td>
                
                 <th>紧急程度:</th>
                 <td><DIV id=uboxstyle>
							<select id="emergencyDegree" name="emergencyDegree" class="select_new01">
								<option value="1" <c:if test="${createWorkorder.emergencyDegree=='1'}">selected</c:if>>
									低
								</option>
								<option value="2" <c:if test="${createWorkorder.emergencyDegree=='2'}">selected</c:if>>
									中
								</option>
								<option value="3" <c:if test="${createWorkorder.emergencyDegree=='3'}">selected</c:if>>
									高
								</option>
								<option value="4" <c:if test="${createWorkorder.emergencyDegree=='4'}">selected</c:if>>
									极高
								</option>
							</select>
						</DIV>
						</td>
                 <th>期望完成时间:</th>
                 <td>
                  <input readonly="readonly" class="gl_text01_a" type="text" id="expectFinishDate" name="expectFinishDate" value='<fmt:formatDate value="${createWorkorder.expectFinishDate}" pattern="yyyy-MM-dd"/>' onfocus="WdatePicker({minDate:'%y-%M-{%d+1}',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"/>
                   </td>
               </tr>
                 </c:if>
                 <input type="hidden" value="${isPrivilege}"  id="isP"/>
                 <c:if test="${isPrivilege=='0'}">
               <tr>
                 <th width="100">所属项目:</th>
                <input type="hidden" id="projectId" name="projectId" value="${createWorkorder.projectId}"/>
                 <td>
                 
                   ${projectManagement.projectName}
                 </td>
               
                 <th>项目版本:</th>
                 <td>
                 ${versionManagement.versionCode}
                 </td>
                 <th>需求名称:</th>
                 <td title="${createWorkorder.name}">
                  <%-- ${createWorkorder.name} --%>
                  <script>document.write(ellipsisStr('${createWorkorder.name}'));</script>
                 </td>
                 </tr>
               <tr>
                 <th>需求类型:</th>
                 <td>
                 <input type="hidden" name="type" value ="${createWorkorder.type}" id="type"/>
                 <c:if test="${createWorkorder.type=='1'}">产品功能</c:if>
						<c:if test="${createWorkorder.type=='2'}">运营支撑</c:if>
						<c:if test="${createWorkorder.type=='3'}">系统优化</c:if>
						<c:if test="${createWorkorder.type=='4'}">营销策划</c:if>
						<c:if test="${createWorkorder.type=='5'}">其它</c:if>
				</td>
                 <th>紧急程度:</th>
                 <td >
                 <c:if test="${createWorkorder.emergencyDegree=='1'}">低</c:if>
						<c:if test="${createWorkorder.emergencyDegree=='2'}">中</c:if>
						<c:if test="${createWorkorder.emergencyDegree=='3'}">高</c:if>
						<c:if test="${createWorkorder.emergencyDegree=='4'}">极高</c:if>
                 </td>
                  <th>期望完成时间:</th>
                 <td>
                 <fmt:formatDate value="${createWorkorder.expectFinishDate}" pattern="yyyy-MM-dd"/>
				</td>
               </tr>
               </c:if>
             </table>
             <div class="ge_a01"></div>
             
             <div class="gl_bt_bnt01">需求相关</div>
           	<c:if test="${isPrivilege=='0'}">
              <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0" style=" font-weight:normal;">
               <tr>
                 <th>需求提出理由:</th>
                 <td><textarea style="background-color: #eff4fa; height:90px;" readonly="readonly"  class="gl_text01_d" >${createWorkorder.reason}</textarea></td>
               </tr>
               <tr>
                 <th>需求描述:</th>
                 <td><textarea style="background-color: #eff4fa; height:135px;" readonly="readonly"  class="gl_text01_d"  >${createWorkorder.description}</textarea></td>
               </tr>
               <tr>
                 <th width="120">预期带来的收益:</th>
                 <td><textarea style="background-color: #eff4fa; height:90px;" readonly="readonly"  class="gl_text01_d"  >${createWorkorder.expectBenefits}</textarea></td>
               </tr>
             </table>
             </c:if>
			<c:if test="${isPrivilege=='1'}">
				<table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0" style=" font-weight:normal;">
               <tr>
                 <th>需求提出理由:</th>
                 <td><textarea name="reason" class="gl_text01_d" id="reason" style="height:90px;" >${createWorkorder.reason}</textarea></td>
               </tr>
               <tr>
                 <th>需求描述:</th>
                 <td><textarea name="description" class="gl_text01_d" id="description" style="height:135px;">${createWorkorder.description}</textarea></td>
               </tr>
               <tr>
                 <th width="120">预期带来的收益:</th>
                 <td><textarea name="expectBenefits" class="gl_text01_d" id="expectBenefits" style="height:90px;">${createWorkorder.expectBenefits}</textarea></td>
               </tr>
             </table>
			</c:if>
			<div class="ge_a01b"></div>
			 <%--    <div class="gl_bt_bnt01">流程信息</div>
	             <table  class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
				    <tr>
				    <th width="10%">当前节点:</th>
				    <td width="40%">${flowNodeModel.nodeName}</td>
				    <th width="10%">应办事项:</th>
				    <td width="40%">${flowNodeModel.nodeText}</td>
				    </tr>
				 </table>
			<div class="ge_a01b"></div>
			--%>
			<div class="gl_bt_bnt01">
				流程跟踪<input type="button" class="gl_cx_bnt01b" value="收起跟踪"
							onclick="search()" id="toggleQueryButton" />
			</div>
			<div id="queryDiv">
			
			<%-- <table class="gl_table_a02" width="100%" border="0" cellspacing="0"
					cellpadding="0">
				<tr>
					<th width="12%">
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
					<th width="28%">
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
					             <c:choose> 	
					          <c:when test="${fn:length(approveResult) > 6}">  
					                   <c:out value="${fn:substring(approveResult, 0, 6)}" />... 
					                </c:when>  
					                <c:otherwise>  
					                   <c:out value="${approveResult}" />  					                 
					                </c:otherwise> 
					                 
					            </c:choose> 			
						 
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
			<div class="ge_a01b"></div>
			
			<div class="gl_bt_bnt01">
				附件列表
			</div>
			<table class="" width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td width="90">
						<b>上传新的附件:</b>
					</td>
					
					<td width="80">
						<input  type="file" name="fileName" id="uploadFiles" /> 
						<!-- <a onclick="javascript:jQuery('#uploadFiles').uploadifyUpload()"><input name="input" type="button" class="ipt_tb_n02b" value="" /></a>	    -->                               
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
					<th width="5%">
						序号
					</th>
					<th width="11%">
					             节点名称
					</th>
					<th width="52%">
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
				<c:forEach items="${fileUploads}" var="file" varStatus="i">
					<tr id="${file.createFlowAttaId }"
						<c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if>>
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
							${file.formatUploadDate}
						</td>
						<td>
						<table  class="jz_a01" border="0" cellspacing="0" cellpadding="0">
						   <tr align="center">
						     <td align="center"><img src="/SRMC/rmpb/images/tab_tb09.png" align="middle"/></td>
						     <td><a href="<%=basePath%>developFlowManage/downloadFlowAttachment?createFlowAttaId=${file.createFlowAttaId}">下载</a></td>
						     <c:if test="${user == file.uploadOperator}">
						     <td><img src="/SRMC/rmpb/images/tab_tb07.png" /></td>
						      <td style="padding-right:18px;" ><a href="javascript:deleteFileUpload('${file.createFlowAttaId}')">删除</a></td>
						     </c:if>
						   </tr>
						</table>
						</td>
					</tr>
				</c:forEach>

			</table>
		

			<div class="gl_ipt_03">
				<%--<a id="submitButton" onclick="showSubpage()"><input type="button" class="ipt_tb_n03" value="办理" /></a>
				&nbsp;<a id="returnButton" onclick="javascript:history.back();"><input type="button" class="ipt_tb_n03" value="返回" /></a>
			
			   --%><br/>
			     <table border="0" style="width: 100%;text-align: center;vertical-align: top;">
			     <tr><td align="right" style="width: 50%;vertical-align: top;"><a id="submitButton"><input id="saveThisButton" onclick="showSubpage()" type="button" class="ipt_tb_n03" value="办理" /></a>
				 &nbsp;
				</td>
			     <td align="left" style="width: 50%;vertical-align: top;">
			      &nbsp;<input id="returnButton" type="button" class="ipt_tb_n03" value="返回" onclick="javascript:history.back();"/></td></tr>
			     </table>
			
			</div>
			
			
			<div style="height: 25px;"></div>
	</div>
	</form>

<script language="javascript" type="text/javascript">
function showSubpage(){  
           jQuery("#submitButton").fancybox({
				'autoScale'			: false,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'type'				: 'iframe',
				'scrolling'			:'yes',
				'centerOnScroll'	:true,
				'onStart'			:function(current,previous){
				         return false;
				 }
		
			});	 

	var createWorkorderId = jQuery("#createWorkorderId").val();
	var projectId = jQuery("#projectId").val();
		//var flowinstanceId = document.getElementById("flowinstanceId").value;
	if(null!=projectId||""!=projectId){
		if(projectId=='-1'){
			alert("请选择项目!");
			return;
		}
	}
	var versionId = jQuery("#versionId").val();
	//var flowinstanceId = document.getElementById("flowinstanceId").value;
	var isP = jQuery("#isP").val();	
    if(isP=='1'){      
        if(versionId=='-1'||versionId==null){
           alert("请选择项目版本!");
		   return ;
        }		
    }	

	
	 
	 if(undefined==projectId){
				projectId="";
	 }	
			//alert("test");		
	    var fromNode = jQuery("#fromNode").val();
		var type = jQuery("#type").val();
		var url="<%=basePath%>core/portal/toPreApproveRequireDetailSubpageApprove?createWorkorderId="+createWorkorderId+"&fromNode=" +fromNode+"&type="+type+"&projectId="+projectId; 
		jQuery("#submitButton").attr("href",url);
		
		jQuery("#submitButton").fancybox({
			'autoScale'			: false,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe',
			'scrolling'			:'yes',
			'centerOnScroll'	:true,
			'onStart'			:function(current,previous){
			    if(isP=='1'){
				     if(versionId=='-1'||versionId==null){
				      return false;
				    }	
			    }			
			 }		
		});	
	
	//jQuery("#createWorkorderId").click();
}

function doCloseSubpage(){
	jQuery.fancybox.close();
}

function doSubmit(){
	jQuery.fancybox.close();
	//jQuery("#submitForm").attr("action","<%=basePath%>developFlowManage/requirementItem/requirementApprove/submitRequireApprove");
	jQuery("#submitForm").attr("action","<%=basePath%>core/portal/submitRequireApprove");
	
	document.forms[0].submit();
}


</script>

	</body>


</html>