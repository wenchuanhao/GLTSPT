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
		</script>
	</head>

	<body class="bg_c_g">
	 <form action="" method="post" id="submitForm">
	 <input type="hidden" id="insactorId" name="insactorId" value="${insactorId}"/>
	 <input type="hidden" id="developWorkorderId" name="developWorkorderId" value="${order.publishWorkorderId}"/>
		<c:choose>
			<c:when test="${empty requestScope.indexLocation }">
				<div class="gl_m_r_nav">当前位置&nbsp;:&nbsp;我的工作台&nbsp;>&nbsp;版本流程&nbsp;>&nbsp;版本发布详情</div>
			</c:when>
			<c:when test="${not empty requestScope.indexLocation and requestScope.indexLocation eq '1' }">
				<div class="gl_m_r_nav">当前位置 : 我的工作台 &gt; 待办事项</div>
			</c:when>
			<c:when test="${not empty requestScope.indexLocation and requestScope.indexLocation eq '2' }">
				<div class="gl_m_r_nav">当前位置 : 我的工作台 &gt; 待阅事项</div>
			</c:when>
		</c:choose>

		<div class="gl_import_m">
			<div class="gl_bt_bnt01">
				版本发布信息
			</div>
			<table class="gl_table_a01" width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<th width="100">
						工单编号:
					</th>
					<td>
						${order.workorderId}
					</td>
					<th width="100">
						所属项目:
					</th>
					<td>
						${order.projectName}
					</td>
					<th width="100">
						所属子系统:
					</th>
					<td>
						${order.projectSonName}
					</td>
				</tr>
				<tr>
				<th width="100">
						所属版本:
					</th>
					<td>
						${order.versionName}
					</td>
					<th width="100">
						紧急程度:
					</th>
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
					<th width="100">
						申请事由:
					</th>
					<td colspan="5">
						<textarea style="background-color: #eff4fa;" name="reason" class="gl_text01_d" id="reason" readonly="readonly">${order.applyReason}</textarea>
					</td>
				</tr>
				<tr>
					<th width="100">
						风险描述:
					</th>
					<td colspan="5">
						<textarea style="background-color: #eff4fa;" name="description" class="gl_text01_d" id="description" readonly="readonly">${order.riskDescription}</textarea>
					</td>
				</tr>
				<tr>
					<th width="100">
						任务操作方案:
					</th>
					<td colspan="5">
						<textarea style="background-color: #eff4fa;" name="operateScheme" class="gl_text01_d" id="operateScheme" readonly="readonly">${order.operateScheme}</textarea>
					</td>
				</tr>
			</table>

			<div class="ge_a01"></div>
			<div class="gl_bt_bnt01">
				研发单信息
			</div>
			<table id="workloadListTable" class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>

					<th width="14%">
						序号
					</th>
					<th width="14%">
						工单编号
					</th>
					<th>
						需求名称
					</th>
					<th width="10%">
						所属项目
					</th>
					<th width="5%">
						版本号
					</th>
					<th width="10%">
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
	  	  			  <td><a rel="tooltip" title="${dan[4].name}"><script>document.write(ellipsisStr('${dan[4].name}'));</script></a></td>
	  	              <td>${dan[1].projectName}</td>
	  	              <td>${dan[2].versionCode}</td>
	  	              <td>${dan[5].userName}</td>
	  	              <td><fmt:formatDate value="${dan[4].developDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</c:forEach>
			</table>
			
			<div class="ge_a01"></div>
			<div class="gl_bt_bnt01">
				部署计划信息
			</div>
			<table id="riskListTable" class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="10%">
						序号
					</th>
					<th width="10%">
						操作步骤
					</th>
					<th width="10%">
						责任人
					</th>
					<th>
						工作项
					</th>
					<th width="10%">
						实际操作人
					</th>
					<th width="13%">
						开始时间
					</th>
					<th width="13%">
						结束时间
					</th>
					<th width="5%">
						备注
					</th>
				</tr>
				<c:forEach items="${listDisp}" var="dip" varStatus="i">
					<tr>
						<td>${i.index+1}</td>
						<td>
							<c:choose> 
							<c:when test="${fn:length(dip.operatingSteps) > 10}"> 
								<c:out value="${fn:substring(dip.operatingSteps, 0, 10)}..." /> 
							</c:when> 
							<c:otherwise> 
								<c:out value="${dip.operatingSteps}" /> 
							</c:otherwise>
						</c:choose>
						</td>
						<td>${dip.responsibleName}</td>
						<td><a rel="tooltip" title="${dip.workItem}">
							<c:choose> 
							<c:when test="${fn:length(dip.workItem) > 20}"> 
								<c:out value="${fn:substring(dip.workItem, 0, 20)}..." /> 
							</c:when> 
							<c:otherwise> 
								<c:out value="${dip.workItem}" /> 
							</c:otherwise>
						</c:choose>
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
									<a id="${dip.publishDeploymentId}" class="fone_style02" onclick="viewRiskRemarks('${dip.publishDeploymentId}')"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/>查看</a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<div class="ge_a01"></div>
			<div class="gl_bt_bnt01">
				研发单信息
			</div>
			<table id="workloadListTable" class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>

					<th width="14%">
						序号
					</th>
					<th width="14%">
						工单编号
					</th>
					<th>
						需求名称
					</th>
					<th width="10%">
						所属项目
					</th>
					<th width="5%">
						版本号
					</th>
					<th width="10%">
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
	  	  			  <td><a rel="tooltip" title="${dan[4].name}"  style="text-decoration:none;"><script>document.write(ellipsisStr('${dan[4].name}'));</script></a></td>
	  	              <td>${dan[1].projectName}</td>
	  	              <td>${dan[2].versionCode}</td>
	  	              <td>${dan[5].userName}</td>
	  	              <td><fmt:formatDate value="${dan[4].developDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</c:forEach>
			</table>
			
			<div class="ge_a01"></div>
			<div class="gl_bt_bnt01">
				部署计划信息
			</div>
			<table id="riskListTable" class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="10%">
						序号
					</th>
					<th width="10%">
						操作步骤
					</th>
					<th width="10%">
						责任人
					</th>
					<th>
						工作项
					</th>
					<th width="10%">
						实际操作人
					</th>
					<th width="13%">
						开始时间
					</th>
					<th width="13%">
						结束时间
					</th>
					<th>
						备注
					</th>
				</tr>
				<c:forEach items="${listDisp}" var="dip" varStatus="i">
					<tr>
						<td>${i.index+1}</td>
						<td><c:choose> 
							<c:when test="${fn:length(dip.operatingSteps) > 10}"> 
								<c:out value="${fn:substring(dip.operatingSteps, 0, 10)}..." /> 
							</c:when> 
							<c:otherwise> 
								<c:out value="${dip.operatingSteps}" /> 
							</c:otherwise>
						</c:choose></td>
						<td>${dip.responsibleName}</td>
						<td><a rel="tooltip" title="${dip.workItem}"  style="text-decoration:none;">
						<c:choose> 
							<c:when test="${fn:length(dip.workItem) > 20}"> 
								<c:out value="${fn:substring(dip.workItem, 0, 20)}..." /> 
							</c:when> 
							<c:otherwise> 
								<c:out value="${dip.workItem}" /> 
							</c:otherwise>
						</c:choose>
						
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
									<a id="${dip.publishDeploymentId}" class="fone_style02" ><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle" onclick="viewWorkloadRemarks('${dip.publishDeploymentId}')"/>查看</a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<c:if test="${attaList1!=null && fn:length(attaList1)!=0}">
			<div class="ge_a01"></div>
			<div class="gl_bt_bnt01">
				部署附件列表
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
					<th width="10%">
						上传人
					</th>
					<th width="15%">
						上传时间
					</th>
					<th width="10%">
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
						<td>
							<span class="gl_tab_tr_l"><img
									src="/SRMC/rmpb/images/tab_tb09.png" />
							</span>
							<span class="gl_tab_tr_r">
								<a href="<%=basePath%>developFlowManage/downloadAttachmentVersion?createFlowAttaId=${attaList1.publishFlowAttaId}">下载</a>
							</span>
						</td>
					</tr>
				</c:forEach>
			</table>
			</c:if>
			<c:if test="${attaList2!=null && fn:length(attaList2)!=0}">
			<div class="ge_a01"></div>
			<div class="gl_bt_bnt01">
				验收文档列表
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
					<th width="10%">
						上传人
					</th>
					<th width="15%">
						上传时间
					</th>
					<th width="10%">
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
						<td>
							<span class="gl_tab_tr_l"><img
									src="/SRMC/rmpb/images/tab_tb09.png" />
							</span>
							<span class="gl_tab_tr_r">
								<a href="<%=basePath%>developFlowManage/downloadAttachmentVersion?createFlowAttaId=${attaList2.publishFlowAttaId}">下载</a>
							</span>
						</td>
					</tr>
				</c:forEach>
			</table>
			</c:if>
			<c:if test="${attaList3!=null && fn:length(attaList3)!=0}">
			<div class="ge_a01"></div>
			<div class="gl_bt_bnt01">
				其它附件列表
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
					<th width="10%">
						上传人
					</th>
					<th width="15%">
						上传时间
					</th>
					<th width="10%">
						操作
					</th>
				</tr>
				<c:forEach items="${attaList3}" var="attaList3" varStatus="i">
					<tr>
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
						<td>
							<span class="gl_tab_tr_l"><img
									src="/SRMC/rmpb/images/tab_tb09.png" />
							</span>
							<span class="gl_tab_tr_r">
								<a href="<%=basePath%>developFlowManage/downloadAttachmentVersion?createFlowAttaId=${attaList3.publishFlowAttaId}">下载</a>
							</span>
						</td>
					</tr>
				</c:forEach>
			</table>
			</c:if>
			<div class="ge_a01"></div>
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
						<td>${flowTrackingList.approveResult}</td>
					</tr>
				</c:forEach>
				
			</table> --%>
			<table class="gl_table_a02" width="100%" border="0" cellspacing="0"
					cellpadding="0">
				<tr>
				    <th width="5%">序号</th>
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
					<!-- <th width="9%">
					下一节点处理人
					</th> -->
					<th width="29%">
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
						<c:choose>
									    <c:when test="${empty flowTrackingList.operateTime}"><td>—</td></c:when>
									    <c:otherwise><td width=""><fmt:formatDate value="${flowTrackingList.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td></c:otherwise>
						</c:choose>	
						<td><script>document.write(splitContent('${flowTrackingList.content}','0'));</script></td>
						<!-- <td><script>document.write(splitContent('${flowTrackingList.content}','1'));</script></td> -->
						<%-- <td>${flowTrackingList.approveResult}</td> --%>
						<td  style="text-align:left;padding-left:8px;white-space:normal;word-break:break-all;"><c:set var="approveResult" value="${flowTrackingList.approveResult}" />  
					           <c:out value="${approveResult}" />  		 			
						</td>	
					</tr>
				</c:forEach>
				
			</table>
			</div>
			<div class="ge01"></div>
			<div class="gl_ipt_03">
			<a id="submitButton"><input type="button" class="ipt_tb_n03" value="已 阅" onclick="doSubmit()"/></a>
			&nbsp;<input type="button" class="ipt_tb_n03" value="返 回" onclick="javascript:history.back();"/>
			</div>
			<div style="height: 25px;"></div>
	</div>
  </form>
<script language="javascript" type="text/javascript">

function doSubmit(){
	jQuery("#submitForm").attr("action","<%=basePath%>core/portal/submitPreversionReadFlow");
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



//function doReturn(location){
	//var path = "<%=basePath%>" + location;
	//window.location.href = path;
//}
</script>
</body>
</html>