<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		
		<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
		<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
		
		<style>
			.ui-autocomplete {
				max-height: 150px;
				overflow-y: auto;
				overflow-x: hidden;
			}
			* html .ui-autocomplete {
				height: 150px;
			}
		</style>
		<script type="text/javascript">
			
			var userTemp = "";
			function autoCompletesCC(){
				jQuery("#ccUserSearch").autocomplete({
					source: function( request, response ) {
						jQuery.ajax({
							url: "<%=basePath%>developFlowManage/searchUser",
							dataType: "json",
							data: {
								userValue: request.term
							},
							type: "POST",
							success: function(data) {
								if(data!=null){
						     		response(jQuery.map(data, function( item ) {
						     			return {
						     			    value:item[0].userName+" - "+item[0].account+" - "+item[1].orgName,
											userName:item[0].userName,
											userId:item[0].userId,
											account:item[0].account,
											orgName:item[1].orgName
											/*value:item.userName,
											userId:item.userId*/
										}
									}));
								}else{
									return false;
								}
							}
						});
					},
					minLength: 1,
					select: function( event, ui ) {
						if(jQuery("#selectedCCUserDiv").children("[value='" + ui.item.userId + "']").length != 0){
							jQuery("#ccUserSearch").val("");
							return false;
						}else{
							jQuery("#selectedCCUserDiv").append('<input type="hidden" id="CCUserId" value="' + ui.item.userId + '"/><input type="button" name="CCUserName" class="wd_cx_bnt01" value="' + ui.item.userName + '" onclick="removeCCUser(\'' + ui.item.userId + '\',\'' + ui.item.userName + '\')"/>');
						    userTemp = ui.item.userName;
						    jQuery("#ccUserSearch").val("");
							return false;
						}
						
					}
				});
				//jQuery("#ccUserSearch").val("");
			}
			
			function clean(){
				if (event.keyCode == 13) {   
				//js 监听对应的id   
				document.getElementById("ccUserSearch").value="";
				}
			}
			
			
			//function autoComplete(){
			//	jQuery("#userSearch").autocomplete({
			//		source: function( request, response ) {
			//			jQuery.ajax({
			//				url: "<%=basePath%>developFlowManage/searchUser",
			//				dataType: "json",
			//				data: {
			//					userValue: request.term
			//				},
			//				type: "POST",
			//				success: function(data) {
			//					if(data!=null){
			//			     		response(jQuery.map(data, function( item ) {
			//			     			return {
			//			     			    value:item[0].userName+" - "+item[0].account+" - "+item[1].orgName,
			//								userName:item[0].userName,
			//								userId:item[0].userId,
			//								account:item[0].account,
			//								orgName:item[1].orgName
			//								/*value:item.userName,
			//								userId:item.userId*/
			//							}
			//						}));
			//					}else{
			//						return false;
			//					}
			//				}
			//			});
			//		},
			//		minLength: 1,
			//		select: function( event, ui ) {
			//			//jQuery("#userSearch").val(ui.item.userName);
			//			if(jQuery("#selectedApproveUserDiv").children("[value='" + ui.item.userId + "']").length != 0){
			//				return;
			//			}else{
			//				jQuery("#userSearch").val(ui.item.userName);
			//				var temp = '<input type="hidden" id="approveUserId" value="' + ui.item.userId + '"/><input type="button" class="wd_cx_bnt01" value="' + ui.item.userName + '" onclick="removeApproveUser(\'' + ui.item.userId + '\',\'' + ui.item.userName + '\')"/>';
			//				jQuery("#selectedApproveUserDiv").append(temp);
			//				//jQuery("#selectedApproveUserDiv").html(temp);
			//			}
			//			return false;
			//			
			//		}
			//	});
			//}

			function autoComplete2(){
				jQuery("#userSearch").autocomplete({
					source: function( request, response ) {
						jQuery.ajax({
							url: "<%=basePath%>developFlowManage/searchUser",
							dataType: "json",
							data: {
								userValue: request.term
							},
							type: "POST",
							success: function(data) {
							    //alert("test1");
								if(data!=null){
						     		response(jQuery.map(data, function( item ) {
						     			return {
						     			    value:item[0].userName+" - "+item[0].account+" - "+item[1].orgName,
											userName:item[0].userName,
											userId:item[0].userId,
											account:item[0].account,
											orgName:item[1].orgName
											/*value:item.userName,
											userId:item.userId*/
										}
									}));
								}else{
									return false;
								}
							}
						});
					},
					minLength: 1,
					select: function( event, ui ) {
						if(jQuery("#selectedApproveUserDiv").children("[value='" + ui.item.userId + "']").length != 0){
							jQuery("#userSearch").val("");
							return false;
						}else{
							var temp = '<input type="hidden" id="approveUserId" value="' + ui.item.userId + '"/><input type="button" class="wd_cx_bnt01" value="' + ui.item.userName + '" onclick="removeApproveUser(\'' + ui.item.userId + '\',\'' + ui.item.userName + '\')"/>';
							//jQuery("#selectedApproveUserDiv").append(temp);
							jQuery("#selectedApproveUserDiv").html(temp);
							jQuery("#userSearch").val("");
							return false;
						}
					}
				});
				//jQuery("#userSearch").val(userTemp);
			}	

			function autoCompletes2(){
				jQuery("#userSearch").autocomplete({
					source: function( request, response ) {
						jQuery.ajax({
							url: "<%=basePath%>developFlowManage/searchUser",
							dataType: "json",
							data: {
								userValue: request.term
							},
							type: "POST",
							success: function(data) {
								if(data!=null){
						     		response(jQuery.map(data, function( item ) {
						     			return {
						     			    value:item[0].userName+" - "+item[0].account+" - "+item[1].orgName,
											userName:item[0].userName,
											userId:item[0].userId,
											account:item[0].account,
											orgName:item[1].orgName
										}
									}));
								}else{
									return false;
								}
							}
						});
					},
					minLength: 1,
					select: function( event, ui ) {
						if(jQuery("#selectedApproveUserDiv").children("[value='" + ui.item.userId + "']").length != 0){
							jQuery("#userSearch").val("");
							return false;
						}else{
							var temp = '<input type="hidden" id="approveUserId" value="' + ui.item.userId + '"/><input type="button" class="wd_cx_bnt01" value="' + ui.item.userName + '" onclick="removeApproveUser(\'' + ui.item.userId + '\',\'' + ui.item.userName + '\')"/>';
							jQuery("#selectedApproveUserDiv").append(temp);
							//jQuery("#selectedApproveUserDiv").html(temp);
							jQuery("#userSearch").val("");
							return false;
						}
						
					}
				});
				
				//jQuery("#userSearch").val(userTemp);
			}
			
			function clean1(){
				if (event.keyCode == 13) {   
					//js 监听对应的id   
					document.getElementById("userSearch").value="";
				} 
			}
			
		</script>
		
		
	</head>

	<body class="bg_c" id="Bbody">
		<input type="hidden" id="developWorkorderId" name="developWorkorderId" value="${developWorkorderId}"/>
		<input type="hidden" id="isEndNode" name="isEndNode" />
		<input type="hidden" id="flowinstanceId" name="flowinstanceId" value="${flowinstanceId }"/>
		<div class="" style="padding:5px 10px;">
			<div class="wd_div_top">
				填写处理意见
			</div>
			<table class="tuan_table_d01" width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td colspan="2">
						<textarea name="opitionContent" class="gl_text01_d" id="opitionContent"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<input type="button" class="gl_cx_bnt01d" value="保存为常用意见" onclick="savePersionalOpinion()"/>&nbsp;&nbsp;
						<input type="button" class="gl_cx_bnt01e" value="删除选中的常用意见" onclick="doDel()"/>
					</td>
					<td width="250" align="right">
						<div id="uboxstyle">
							<select id="personalOpinionId" name="personalOpinionId" class="select_app" onchange="selectPersonalOpinion()">
								<option value="-1">
									选择个人常用意见
								</option>
								<c:forEach items="${personalOpinionList}" var="personalOpinionList" varStatus="i">
									<option value="${personalOpinionList.personalOpinionId}">${personalOpinionList.outline} </option>
								</c:forEach>
							</select>
						</div>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="48%" valign="top">
						<div class="wd_div_top">
							选择处理方式
						</div>
						<input type="hidden" id="transitionId" name="transitionId" />
						<ul class="yan_w_tab01" >
							<input type="hidden" id="transitionListLength" name="transitionListLength" value="${fn:length(transitionList)}"/>
							<c:if test="${fn:length(transitionList)==1 }" var="rs">
	        		        	<c:forEach items="${transitionList}" var="transitionList" varStatus="i" >
	        						<li id="handleMethods${i.index}" onclick="selectHandleMethods('${i.index}','${transitionList.transitionId}')">
									${transitionList.toNodeName}
								</li>
	      						<script type="text/javascript">
	      							jQuery('#Bbody').attr("onload","selectHandleMethods('${i.index}','${transitionList.transitionId}')");
	      						</script>
		   						</c:forEach>
	        				</c:if>
	        				<c:if test="${!rs}">
		        				<c:forEach items="${transitionList}" var="transitionList" varStatus="i" >
	        						<li id="handleMethods${i.index}" onclick="selectHandleMethods('${i.index}','${transitionList.transitionId}')">
										${transitionList.toNodeName}
									</li>
	        						<c:if test="${i.index==0}">
	        							<script type="text/javascript">
      										jQuery('#Bbody').attr("onload","selectHandleMethods('${i.index}','${transitionList.transitionId}')");
      									</script>
	        						</c:if>
			   					</c:forEach>
		   					</c:if>
						</ul>
					</td>
					<td width="4%">
						&nbsp;
					</td>
					<td width="48%" valign="top">
					<div id="CCUserDiv1">
						<div class="wd_div_top">
							<span id='notetext'>请选择处理人</span>
						</div>
						<ul class="yan_w_tab01">
							<div id="ApproveUsersDiv">
								<li></li>
								<li></li>
							</div>
						</ul>
						</div>
					</td>
					<td width="48%" valign="top">
						
					</td>
				</tr>
			</table>
			<div id="CCUserDiv2">
			<div class="wd_div_top">
				已选处理人
			</div>
			<table class="tuan_table_d01" width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td height="40">
						<div id="selectedApproveUserDiv"></div>
					</td>
				</tr>
			</table>
			</div>
			<div id="CCUserDiv" style="display: block;">
				<div class="wd_div_top">
					选择抄送人
				</div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="tuan_table_d01">
					<tr>
						<td>
							<input type="text" class="tuan_text01" id="ccUserSearch" onfocus="autoCompletesCC();" onkeyup="clean()"/>
						</td>
						<td>
							<input type="button" class="gl_cx_bnt01c" value="重 置" onclick="clearAllCCUser()"/>
						</td>
					</tr>
				</table>
				<div class="wd_div_top">
					已选抄送人
				</div>
				<table class="tuan_table_d01" width="100%" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<td width="250" height="40">
							<div id="selectedCCUserDiv"></div>
						</td>
					</tr>
				</table>
			</div>
			<div class="gl_ipt_03">
				<input name="input" type="button" class="gl_cx_bnt03" value="提 交" onclick="doSubmit();"/>
				&nbsp;
				<input name="input" type="button" class="gl_cx_bnt03" value="关 闭" onclick="doClose();"/>
			</div>

		</div>

<script language="javascript" type="text/javascript">
function savePersionalOpinion(){
	if(jQuery.trim(jQuery("#opitionContent").val()) == ""){
		alert("请输入处理意见");
		return;
	}else{
		var url = "<%=basePath%>developFlowManage/savePersonalOpinion";
		var params = {
			opitionContent:jQuery("#opitionContent").val()
		};
		jQuery.post(url, params, function(data){
			var datas = data.split("@#@");
        	var flag = datas[0];
        	var option = datas[1];
            if(flag == "1"){
            	$("#personalOpinionId").append(option);
                alert("保存常用意见成功！");
            }else{
                alert("保存常用意见失败！");
            }
		});
	}
}

function selectPersonalOpinion(){
	if(jQuery("#personalOpinionId").val() == "-1"){
		jQuery("#opitionContent").html("");
	}else{
		var url = "<%=basePath%>developFlowManage/queryPersonalOpinionById";
		var params = {
			personalOpinionId:jQuery("#personalOpinionId").val()
		};
		jQuery.post(url, params, function(data){
			jQuery("#opitionContent").val(data);
		} );
	}
}
function doDel(appid){
	if(jQuery.trim(jQuery("#personalOpinionId").val()) == "-1"){
		alert("请选择要删除的常用意见");
		return;
	}else{
		var appid=jQuery.trim(jQuery("#personalOpinionId").val());
		 jQuery.ajax({
			type:"POST",
			async:false,
			url:"<%=basePath%>developFlowManage/deletePersonalOpinion",
			data:"personalOpinion_Id="+appid,
			success:function(data){
				alert("删除常用意见成功！");
				var url2 = "<%=basePath%>developFlowManage/queryPersonalOpinionList";
				var params = "";
				jQuery.post(url2, params, function(data2){
					var optionList = data2;
					var option = '<option value="-1">选择个人常用意见</option>';
					for(var i=0; i<optionList.length; i++){
						option += '<option value="' + optionList[i].personalOpinionId + '">' + optionList[i].outline + '</option>';
					}
					jQuery("#personalOpinionId").html(option);
				} , 'json');
			},
			error:function(){
				alert("删除常用意见失败！");
			}
		});
	}
}

function selectHandleMethods(index, transitionId){
	var length = jQuery("#transitionListLength").val();
	var flowinstanceId = jQuery("#flowinstanceId").val();
	jQuery("#transitionId").val(transitionId);
	for(var i=0; i<length; i++){
		if(i == index){
			jQuery("#handleMethods" + i).attr("class", "yan_w_tab01_select")
		}else{
			jQuery("#handleMethods" + i).attr("class", "yan_w_tab01_not_select")
		}
	}
	
	jQuery("#selectedApproveUserDiv").html(""); //清空已选审批人
	
	//查询是否结束节点
	jQuery.ajax({
		url: "<%=basePath%>developFlowManage/queryIsEndNode",
		type: "POST",
		data: {
			transitionId:transitionId
		},
		success: function(data){
			//alert(data);
			jQuery("#isEndNode").val(data);
			jQuery("#ApproveUsersDiv").html("");//清空审批人列表
			if(data == "1"){
				jQuery("#CCUserDiv1").css("display","block");
				jQuery("#CCUserDiv2").css("display","block");
				if(transitionId==3){//单选
					var temp = "<input type='text' id='userSearch' onfocus='autoCompletes2()' style='width:98%' onkeyup='clean1()'/>";
					jQuery("#ApproveUsersDiv").html(temp);
				}else if (transitionId==4){//会签标志  多选
					var temp = "<input type='text' id='userSearch' onfocus='autoComplete2()' style='width:98%' onkeyup='clean1()'/>";
					jQuery("#ApproveUsersDiv").html(temp);
					
				}else{
					var url = "<%=basePath%>developFlowManage/searchApproveUserFromHandleMethods";
					var params = {
						workorderId:jQuery("#developWorkorderId").val(),
						flowType:"3",
						flowinstanceId:flowinstanceId,
						transitionId:transitionId,
						flag:"0"
					};
					jQuery.post(url, params, function(data){
						var temp = "";
						if(transitionId=='13062300000000000006'||transitionId=='13062300000000005011'){
							document.getElementById("notetext").innerHTML="请输入处理人";
							temp = "<input type='text' id='userSearch' onfocus='autoComplete()' style='width:98%' onkeyup='clean1()'/>";
							jQuery("#ApproveUsersDiv").html(temp);
						}else{
							for(var i=0; i<data.length; i++){
							var temp = temp + "<li id='" + data[i].userId + "' onclick='selectMutiplyApproveUser(\"" + data[i].userId + "\",\"" + data[i].userName + "\")'>" + data[i].userName + "</li>";	
							}
							jQuery("#ApproveUsersDiv").html(temp);
							if(data.length == 1){
								//alert(data[0].userId);
								selectMutiplyApproveUser(data[0].userId,data[0].userName);
							    //jQuery("#ApproveUsersDiv li").css({display:"block", background:"#fe9b12"});
							}
						}
						
					},'json');
				}
				
			}else if(data == "2"){
				jQuery("#CCUserDiv1").css("display","block");
				jQuery("#CCUserDiv2").css("display","block");
				if(transitionId==3){//单选
					var temp = "<input type='text' id='userSearch' onfocus='autoCompletes2()' style='width:98%' onkeyup='clean1()'/>";
					jQuery("#ApproveUsersDiv").html(temp);
				}else if (transitionId==4){//会签标志  多选
					var temp = "<input type='text' id='userSearch' onfocus='autoComplete2()' style='width:98%' onkeyup='clean1()'/>";
					jQuery("#ApproveUsersDiv").html(temp);
					//alert("test");
				}else{
					var url = "<%=basePath%>developFlowManage/searchApproveUserFromHandleMethods";
					var params = {
						workorderId:jQuery("#developWorkorderId").val(),
						flowType:"3",
						transitionId:transitionId,
						flowinstanceId:flowinstanceId,
						flag:"0"
					};
					jQuery.post(url, params, function(data){
						var temp = "";
						//if(transitionId=="13062300000000000016"){
						//	for(var i=0; i<data.length; i++){
						//	var temp = temp + "<li onclick='selectMutiplyApproveUser(\"" + data[i].userId + "\",\"" + data[i].userName + "\")'>" + data[i].userName + "</li>";	
						//	}
						//	jQuery("#ApproveUsersDiv").html(temp);
						//}else{
							for(var i=0; i<data.length; i++){
							var temp = temp + "<li id='" + data[i].userId + "' onclick='selectApproveUser(\"" + data[i].userId + "\",\"" + data[i].userName + "\")'>" + data[i].userName + "</li>";
						}
						jQuery("#ApproveUsersDiv").html(temp);
						if(data.length == 1){
							//jQuery("#ApproveUsersDiv li").css({display:"block", background:"#fe9b12"});
							selectApproveUser(data[0].userId,data[0].userName);
						//}
						}
					},'json');
				}
				
			}else if(data == "3"){
				jQuery("#CCUserDiv").css("display","block");
				jQuery("#CCUserDiv1").css("display","none");
				jQuery("#CCUserDiv2").css("display","none");
				clearAllCCUser();
			}
		}
		
	})
	
}

function selectMutiplyApproveUser(userId, userName){
	if(jQuery("#selectedApproveUserDiv").children("[value='" + userId + "']").length != 0){
		return;
	}else{
		var temp = '<input type="hidden" id="approveUserId" value="' + userId + '"/><input type="button" class="wd_cx_bnt01" value="' + userName + '" onclick="removeApproveUser(\'' + userId + '\',\'' + userName + '\')"/>'
		jQuery("#selectedApproveUserDiv").append(temp);
		jQuery("#ApproveUsersDiv").children("li[id='" + userId + "']").css("background", "#fe9b12");
		//jQuery("#selectedApproveUserDiv").html(temp);
	}
}

function selectApproveUser(userId, userName){
	if(jQuery("#selectedApproveUserDiv").children("[value='" + userId + "']").length != 0){
		return;
	}else{
		var temp = '<input type="hidden" id="approveUserId" value="' + userId + '"/><input type="button" class="wd_cx_bnt01" value="' + userName + '" onclick="removeApproveUser(\'' + userId + '\',\'' + userName + '\')"/>'
		//jQuery("#selectedApproveUserDiv").append(temp);
		jQuery("#selectedApproveUserDiv").html(temp);
		jQuery("#ApproveUsersDiv").children("li[id='" + userId + "']").css("background", "#fe9b12");
		jQuery("#ApproveUsersDiv").children("li[id!='" + userId + "']").css("background", "");
	}
}

//联想
function autoComplete(){
	jQuery("#userSearch").autocomplete({
		source: function( request, response ) {
			jQuery.ajax({
				url: "<%=basePath%>developFlowManage/searchApproveUserFromHandleMethodsload",
				dataType: "json",
				data: {
					userValue: request.term,
					workorderId:jQuery("#developWorkorderId").val(),
					transitionId:jQuery("#transitionId").val(),
					flowType:"3",
					flag:"0"
				},
				type: "POST",
				success: function(data) {
					if(data!=null){
			     		response(jQuery.map(data, function( item ) {
			     			return {
			     			   value:item[0].userName+" - "+item[0].account+" - "+item[1].orgName,
								userName:item[0].userName,
								userId:item[0].userId,
								account:item[0].account,
								orgName:item[1].orgName
								
							}
						}));
					}else{
						return false;
					}
				}
			});
		},
		minLength: 1,
		select: function( event, ui ) {
			jQuery("#userSearch").val(ui.item.userName);
			if(jQuery("#selectedApproveUserDiv").children("[value='" + ui.item.userId + "']").length != 0){
				jQuery("#userSearch").val("");
				return false;
			}else{
				var temp = '<input type="hidden" id="approveUserId" value="' + ui.item.userId + '"/><input type="button" class="wd_cx_bnt01" value="' + ui.item.userName + '" onclick="removeApproveUser(\'' + ui.item.userId + '\',\'' + ui.item.userName + '\')"/>';
				jQuery("#selectedApproveUserDiv").append(temp);
				//jQuery("#selectedApproveUserDiv").html(temp);
				jQuery("#userSearch").val("");
				return false;
			}
			
			return false;
		
		}
	});
	
	//jQuery("#userSearch").val(userTemp);
}

function removeApproveUser(userId, userName){
	jQuery("#selectedApproveUserDiv").children("[value='" + userId + "']").remove();
	jQuery("#selectedApproveUserDiv").children("[value='" + userName + "']").remove();
	jQuery("#ApproveUsersDiv").children("li[id='" + userId + "']").css("background", "");
}

function removeCCUser(userId, userName){
	jQuery("#selectedCCUserDiv").children("[value='" + userId + "']").remove();
	jQuery("#selectedCCUserDiv").children("[value='" + userName + "']").remove();
}

	
function clearAllCCUser(){
	jQuery("#selectedCCUserDiv").html("");
	jQuery("#ccUserSearch").val("");
	
}

function doSubmit(){
	if(jQuery.trim(jQuery("#opitionContent").val()) == ""){
		alert("请输入处理意见");
		return;
	}
	if(jQuery.trim(jQuery("#transitionId").val()) == ""){
		alert("请选择处理方式");
		return;
	}
	
	var approveUserIdStr = "";
	if(jQuery("#isEndNode").val() != "3"){
		jQuery("input[id='approveUserId']").each(function () {
			approveUserIdStr = approveUserIdStr + jQuery(this).val() + "_"; 
		});
		if(approveUserIdStr == ""){
			alert("请选择处理人");
			return;
		}else{
			approveUserIdStr = approveUserIdStr.substring(0,approveUserIdStr.length-1);
		}
	}
	
	//验证联想非法字符
	if(!checkValue()){
		jQuery("#ccUserSearch").val("");
		return;
	}
	var CCUserIdStr = "";
	jQuery("input[id='CCUserId']").each(function () {
		CCUserIdStr = CCUserIdStr + jQuery(this).val() + "_";
	});
	if(CCUserIdStr != ""){
		CCUserIdStr = CCUserIdStr.substring(0,CCUserIdStr.length-1);
	}
	
	jQuery("#opitionContent", window.parent.document).val(jQuery.trim(jQuery("#opitionContent").val()));
	jQuery("#transitionId", window.parent.document).val(jQuery.trim(jQuery("#transitionId").val()));
	jQuery("#approveUserIdStr", window.parent.document).val(approveUserIdStr);
	jQuery("#CCUserIdStr", window.parent.document).val(CCUserIdStr);
	window.parent.doSubmit();
}

//验证联想非法字符
function checkValue(){
	var flag = true;
	var ccUserSearch = jQuery("#ccUserSearch").val();
	if(ccUserSearch!=null&&ccUserSearch!=""){
		var CCUserIdStr = "";
		jQuery("input[id='CCUserName']").each(function () {
			CCUserIdStr = CCUserIdStr + jQuery(this).val() + "_";
		});
		if(CCUserIdStr==""){
			flag=false;
		}else{
			CCUserIdStr = CCUserIdStr.substring(0,CCUserIdStr.length-1);
			if(CCUserIdStr.indexOf(ccUserSearch)<0){
				flag=false;
			}
		}
		if(ccUserSearch!=""||!flag){
			alert("抄送人未选择或错误，请重新选择再进行相关操作!");
		}
	}
	return flag;
	
}

function doClose(){
	window.parent.doCloseSubpage();
}

</script>



	</body>

</html>