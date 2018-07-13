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
		
		<script type="text/javascript">
		
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
		<input type="hidden" id="insactorId" name="insactorId" value="${insactorId}"/>
		<input type="hidden" id="createWorkorderId" name="createWorkorderId" value="${createWorkorder.createWorkorderId}"/>
		<c:choose>
			<c:when test="${empty requestScope.indexLocation }">
				<div class="gl_m_r_nav">当前位置&nbsp;:&nbsp;我的工作台&nbsp;>&nbsp;需求条目&nbsp;>&nbsp;需求详情</div>
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
				需求信息
			</div>
			<table class="gl_table_a01_6L" width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<th width="100">
						工单编号:
					</th>
					<td>
						${createWorkorder.workorderId}
					</td>
					<th width="100">
						需求负责人:
					</th>
					<td>
						${createWorkorder.headName}
					</td>
					<th width="100">
						所属项目:
					</th>
					<td>
						${projectManagement.projectName}
					</td>
				</tr>
				<tr>
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
						${createWorkorder.name}
					</td> --%>
					<td title="${createWorkorder.name}">
						<%-- ${developWorkorder.name} --%>
						<script>document.write(ellipsisStr3('${createWorkorder.name}'));</script>
					</td>
					<th width="100">
						需求类型:
					</th>
					<td>
						<c:if test="${createWorkorder.type=='1'}">产品功能</c:if>
						<c:if test="${createWorkorder.type=='2'}">运营支撑</c:if>
						<c:if test="${createWorkorder.type=='3'}">系统优化</c:if>
						<c:if test="${createWorkorder.type=='4'}">营销策划</c:if>
						<c:if test="${createWorkorder.type=='5'}">其它</c:if>
					</td>
				</tr>
				<tr>
					<th width="100">
						紧急程度:
					</th>
					<td>
						<c:if test="${createWorkorder.emergencyDegree=='1'}">低</c:if>
						<c:if test="${createWorkorder.emergencyDegree=='2'}">中</c:if>
						<c:if test="${createWorkorder.emergencyDegree=='3'}">高</c:if>
						<c:if test="${createWorkorder.emergencyDegree=='4'}">极高</c:if>
					</td>
					<th width="100">
						期望完成时间:
					</th>
					<td>
						<fmt:formatDate value="${createWorkorder.expectFinishDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td colspan="2"></td>
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
						<textarea style="background-color: #eff4fa;" name="reason" class="gl_text01_d" id="reason" readonly="readonly">${createWorkorder.reason}</textarea>
					</td>
				</tr>
				<tr>
					<th width="100">
						需求描述:
					</th>
					<td>
						<textarea style="background-color: #eff4fa;" name="description" class="gl_text01_d" id="description" readonly="readonly">${createWorkorder.description}</textarea>
					</td>
				</tr>
				<tr>
					<th width="100">
						预期带来的收益:
					</th>
					<td>
						<textarea style="background-color: #eff4fa;" name="expectBenefits" class="gl_text01_d" id="expectBenefits" readonly="readonly">${createWorkorder.expectBenefits}</textarea>
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
					<th width="10%">
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
						<th>
							序号
						</th>
						<th>
							附件名称
						</th>
						<th>
							附件类型
						</th>
						<th>
							是否最终版
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
					
					<c:forEach items="${requireAttaList}" var="requireAttaList" varStatus="j">
						<tr id="${j.index+1}"
							<c:if test="${j.index%2 != 0}">class="tab_c_tr01"</c:if>>
							<td>
								${j.index+1}
							</td>
							<td>
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
			
			<c:if test="${fileUploads!=null && fn:length(fileUploads)!=0}">
			<div class="ge_a01"></div>
			<div class="gl_bt_bnt01">
				附件列表
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
					<tr id="${i.index+1}"
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
						<td align="center">
							<span class="gl_tab_tr_l"><img
									src="/SRMC/rmpb/images/tab_tb09.png" align="middle"/>
							</span>
							<span class="gl_tab_tr_r"> <a
								href="<%=basePath%>developFlowManage/downloadFlowAttachment?createFlowAttaId=${file.createFlowAttaId}">下载</a>
							</span>
						</td>
					</tr>
				</c:forEach>

			</table>
			</c:if>
			<div class="gl_ipt_03">
				<a id="submitButton" onclick="doSubmit()"><input type="button" class="ipt_tb_n03" value="已 阅" /></a>&nbsp;
				<a id="returnButton" onclick="javascript:history.back();"><input type="button" class="ipt_tb_n03" value="返回" /></a>
			</div>
			<div style="height: 25px;"></div>
	</div>
	</form>

<script language="javascript" type="text/javascript">

function doSubmit(){
	jQuery("#submitForm").attr("action","<%=basePath%>core/portal/submitRequireReadFlow");
	document.forms[0].submit();
}






</script>

	</body>


</html>