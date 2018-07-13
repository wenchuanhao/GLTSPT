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
		
		<!--<script type="text/javascript" src="/SRMC/rmpb/js/common.js"></script>-->
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
				
				if(name=="personalOpinionId"){
					selectPersonalOpinion();
				}
			}
			
			//jQuery(document).ready(function() {
				var userTemp = "";
		function autoCompletes(){
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
/*											value:item.userName,
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
							return false;
						}else{
							jQuery("#selectedCCUserDiv").append('<input type="hidden" id="CCUserId" value="' + ui.item.userId + '"/><input type="button" id="CCUserName" class="wd_cx_bnt01" value="' + ui.item.userName + '" onclick="removeCCUser(\'' + ui.item.userId + '\',\'' + ui.item.userName + '\')"/>');
						    userTemp = ui.item.userName;
						    jQuery("#ccUserSearch").val("");
						    return false;
						}
						
					}
				});
				
				jQuery("#ccUserSearch").val("");
			//});
			}
		function clean(){
			if (event.keyCode == 13) {   
	         //js 监听对应的id   
	          document.getElementById("ccUserSearch").value="";
	       } 
			
		}
		</script>
		
		
	</head>

	<body class="bg_c"  id="Bbody">
		<input type="hidden" id="createWorkorderId" name="createWorkorderId" value="${createWorkorderId}"/>
		<input type="hidden" id="isEndNode" name="isEndNode" />
		<input type="hidden" id="type" name="type" value ="${type }"/>
		<input type="hidden" id="projectId" name="projectId" value ="${projectId }"/>
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
		   					<%--
							<c:forEach items="${transitionList}" var="transitionList" varStatus="i" >
								<li id="handleMethods${i.index}" onclick="selectHandleMethods('${i.index}','${transitionList.transitionId}')">
									${transitionList.toNodeName}
								</li>
							</c:forEach>
							 --%>
						</ul>
					</td>
					<td width="4%">
						&nbsp;
					</td>
					
					<td width="48%" valign="top">
					<div id="CCUserDiv1">
						<div class="wd_div_top">
							请选择处理人
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
							<input type="text" class="tuan_text01" id="ccUserSearch" onfocus="autoCompletes();" onkeyup="clean()" />
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
function doDel(appid){
     if(jQuery.trim(jQuery("#personalOpinionId").val()) == "-1"){
		alert("请选择要删除的常用意见");
		return;
	 }else{
	            var appid=jQuery.trim(jQuery("#personalOpinionId").val());
	            //alert(jQuery("#personalOpinionId").val());
	    	//var url = "<%=basePath%>developFlowManage/deletePersonalOpinion";
				//var params = {
					//orgAppid:appid
				//};
				//jQuery.post(url, params, function(data){
					//alert("删除常用意见成功！");
				//} );
				 jQuery.ajax({
					type:"POST",
					async:false,
					url:"<%=basePath%>developFlowManage/deletePersonalOpinion",
					data:"personalOpinion_Id="+appid,
					success:function(data){
					alert("删除常用意见成功！");
					   var url2 = "<%=basePath%>developFlowManage/versionFlow/versionPublish/getOptions";
						var params = "";
						jQuery.post(url2, params, function(data2){
						var totalList = data2;
						//var totalList = eval("("+data+")");
						var brandList = totalList;
						var option = '<option value="">请选择个人常用意见</option>';
						for(var i=0; i<brandList.length; i++){
						option += '<option value="' + brandList[i].personalOpinionId + '">' + brandList[i].outline + '</option>';
						}
						jQuery("#personalOpinionId").html(option);
						} , 'json');
					
					},
					error:function(){
					alert("删除常用意见失败！");
					}
					});
		 }
		 //window.location.reload(); 
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

function selectHandleMethods(index, transitionId){
	var length = jQuery("#transitionListLength").val();
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
			jQuery("#isEndNode").val(data);
			jQuery("#ApproveUsersDiv").html("");//清空审批人列表
			if(data == "1"){
				jQuery("#CCUserDiv1").css("display","block");
				jQuery("#CCUserDiv2").css("display","block");
				var url = "<%=basePath%>developFlowManage/searchApproveUserFromHandleMethods";
				var params = {
					workorderId:jQuery("#createWorkorderId").val(),
					transitionId:transitionId,
					flowType:"1",
					flag:"1",
					type:jQuery("#type").val(),
					projectId:jQuery("#projectId").val()
				};
				jQuery.post(url, params, function(data){
					var temp = "";
					for(var i=0; i<data.length; i++){
						var temp = temp + "<li id='"+data[i].userId+"' onclick='selectMutiplyApproveUser(\"" + data[i].userId + "\",\"" + data[i].userName + "\")'>" + data[i].userName + "</li>";
						
					}
					jQuery("#ApproveUsersDiv").html(temp);
					if(data.length == 1){
							selectMutiplyApproveUser(data[i].userId,data[i].userName);
							jQuery("#ApproveUsersDiv li").css({display:"block", background:"#fe9b12"});
					}
					
					
				},'json');
			}else if(data == "2"){
				jQuery("#CCUserDiv1").css("display","block");
				jQuery("#CCUserDiv2").css("display","block");
				var url = "<%=basePath%>developFlowManage/searchApproveUserFromHandleMethods";
				var params = {
					workorderId:jQuery("#createWorkorderId").val(),
					transitionId:transitionId,
					flowType:"1",
					flag:"1",
					type:jQuery("#type").val(),
					projectId:jQuery("#projectId").val()
				};
				jQuery.post(url, params, function(data){
					var temp = "";
					for(var i=0; i<data.length; i++){
						var temp = temp + "<li id='"+ data[i].userId +"' onclick='selectApproveUser(\"" + data[i].userId + "\",\"" + data[i].userName + "\")'>" + data[i].userName + "</li>";
					}
					jQuery("#ApproveUsersDiv").html(temp);
					
					if(data.length == 1){
							jQuery("#ApproveUsersDiv li").css({display:"block", background:"#fe9b12"});
							selectApproveUser(data[0].userId,data[0].userName);
					}
					
				},'json');
			}else if(data == "3"){
				jQuery("#CCUserDiv").css("display","block");
				jQuery("#CCUserDiv1").css("display","none");
				jQuery("#CCUserDiv2").css("display","none");
				clearAllCCUser();
			}
			
			
		}
		
	})
	
}

//单个人
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

function removeApproveUser(userId, userName){
	jQuery("#selectedApproveUserDiv").children("[value='" + userId + "']").remove();
	jQuery("#selectedApproveUserDiv").children("[value='" + userName + "']").remove();
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

function doClose(){
	window.parent.doCloseSubpage();
}
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
</script>



	</body>

</html>