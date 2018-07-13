<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath(); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
</head>

<body>

	<!-- 遮罩内容开始 -->

	<div class="taskCreatBox">
            <div class="box_title" style="color:#4084b6;">
                	请选择发起文件（指令）角色类型：
            </div>
            <div class="box_task">
             	<table width="99%" id="table01" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" style="border:0px;">
             			<tr>
             				<c:forEach items="${srList}" var="vo" varStatus="i">
             				<td>
             					<input name="roleId" value="${vo.roleId }" onclick="choiceRoleId('${vo.roleId }','${vo.roleName }')"  type="radio"/>
             					<a href="javascript:void(0)" >${vo.roleName }</a>
             				</td>
             				</c:forEach>
             			</tr>
		 		 </table>
          	</div>
            <div class="box_title" style="color:#4084b6;">
                	请选择发起文件（指令）类型：
            </div>
            <div class="box_task">
             	<table width="99%" id="table01" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" style="border:0px;">
             			<c:if test="${not empty COMMAND_ROLE }">
<!--              				业主、专家顾问具有所有指令发起权限； -->
<!--  							施工单位、造价单位没有发起A1、A2、B、C指令权限，只能发起请款类和施工过程类指令； -->
<!--  							监理单位没有发起A1、B、C指令权限，只能发起A2、请款类和施工过程类指令。 -->
	             			<tr>
	             				<td>
	             					<input <c:if test="${COMMAND_ROLE ne 1}">disabled="disabled"</c:if> name="parameter" value="<%=basePath%>${types[0].parameterDesc}" onclick="choiceType('<%=basePath%>${types[0].parameterDesc}')" type="radio"/>
	             					<a href="javascript:void(0)" <c:if test="${COMMAND_ROLE ne 1}">style="color: #c7bebe"</c:if>
	             					<c:if test="${COMMAND_ROLE eq 1}">onclick="choiceType('<%=basePath%>${types[0].parameterDesc}')"</c:if>>${types[0].parameterValue }</a>
	             				</td>
	             				<td>
	             					<input <c:if test="${COMMAND_ROLE ne 1 && COMMAND_ROLE ne 2}">disabled="disabled"</c:if> name="parameter" value="<%=basePath%>${types[1].parameterDesc}" onclick="choiceType('<%=basePath%>${types[1].parameterDesc}')" type="radio"/>
	             					<a href="javascript:void(0)" <c:if test="${COMMAND_ROLE ne 1 && COMMAND_ROLE ne 2}">style="color: #c7bebe"</c:if> 
	             					<c:if test="${COMMAND_ROLE eq 1 || COMMAND_ROLE eq 2}">onclick="choiceType('<%=basePath%>${types[1].parameterDesc}')"</c:if>>${types[1].parameterValue }</a>
	             				</td>
	             				<td>
	             					<input <c:if test="${COMMAND_ROLE ne 1}">disabled="disabled"</c:if> name="parameter" value="<%=basePath%>${types[2].parameterDesc}" onclick="choiceType('<%=basePath%>${types[2].parameterDesc}')" type="radio"/>
	             					<a href="javascript:void(0)" <c:if test="${COMMAND_ROLE ne 1}">style="color: #c7bebe"</c:if> 
	             					<c:if test="${COMMAND_ROLE eq 1}">onclick="choiceType('<%=basePath%>${types[2].parameterDesc}')"</c:if>>${types[2].parameterValue }</a>
	             				</td>
	             				
	             				<td colspan="2">
	             					<input <c:if test="${COMMAND_ROLE ne 1}">disabled="disabled"</c:if> name="parameter" value="<%=basePath%>${types[3].parameterDesc}" onclick="choiceType('<%=basePath%>${types[3].parameterDesc}')" type="radio"/>
	             					<a href="javascript:void(0)" <c:if test="${COMMAND_ROLE ne 1}">style="color: #c7bebe"</c:if> 
	             					<c:if test="${COMMAND_ROLE eq 1}">onclick="choiceType('<%=basePath%>${types[3].parameterDesc}')"</c:if>>${types[3].parameterValue }</a>
	             				</td>
	             			</tr>
	             			
	             			<tr>
	             				<td>
	             					<input name="parameter" value="<%=basePath%>${types[4].parameterDesc}" onclick="choiceType('<%=basePath%>${types[4].parameterDesc}')" type="radio"/>
	             					<a href="javascript:void(0)" onclick="choiceType('<%=basePath%>${types[4].parameterDesc}')">${types[4].parameterValue }</a>
	             				</td>
	             				<td>
	             					<input name="parameter" value="<%=basePath%>${types[5].parameterDesc}" onclick="choiceType('<%=basePath%>${types[5].parameterDesc}')" type="radio"/>
	             					<a href="javascript:void(0)" onclick="choiceType('<%=basePath%>${types[5].parameterDesc}')">${types[5].parameterValue }</a>
	             				</td>
	             				<td>
	             					<input name="parameter" value="<%=basePath%>${types[6].parameterDesc}" onclick="choiceType('<%=basePath%>${types[6].parameterDesc}')" type="radio"/>
	             					<a href="javascript:void(0)" onclick="choiceType('<%=basePath%>${types[6].parameterDesc}')">${types[6].parameterValue }</a>
	             				</td>
	             				<td>
	             					<input name="parameter" value="<%=basePath%>${types[7].parameterDesc}" onclick="choiceType('<%=basePath%>${types[7].parameterDesc}')" type="radio"/>
	             					<a href="javascript:void(0)" onclick="choiceType('<%=basePath%>${types[7].parameterDesc}')">${types[7].parameterValue }</a>
	             				</td>
	             				<td>
	             					<input name="parameter" value="<%=basePath%>${types[8].parameterDesc}" onclick="choiceType('<%=basePath%>${types[8].parameterDesc}')" type="radio"/>
	             					<a href="javascript:void(0)" onclick="choiceType('<%=basePath%>${types[8].parameterDesc}')">${types[8].parameterValue }</a>
	             				</td>
	             			</tr>
	             			<tr>
	             				<td colspan="5">
	             					<input name="parameter" value="<%=basePath%>${types[9].parameterDesc}" onclick="choiceType('<%=basePath%>${types[9].parameterDesc}')" type="radio"/>
	             					<a href="javascript:void(0)" onclick="choiceType('<%=basePath%>${types[9].parameterDesc}')">${types[9].parameterValue }</a>
	             				</td>
	             			</tr>
             			</c:if>
					 <tr>
					   	 <th  colspan="5" align="center" height="50">
							<input name="" type="button" class="btn_common02" onclick="javascript:parent.$.fancybox.close();" value="取 消" />
						 </th>
					 </tr>    
		 		 </table>
          	</div>
        </div>
<!-- 遮罩内容结束 -->
</body>
</html>

<script type="text/javascript">
$(function(){

});
$(document).ready(function(){
	if(jQuery("input[name='roleId']").length == 1){
		jQuery("input[name='roleId']").attr("checked","checked");
	}
});

function choiceRoleId(roleId,roleName){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>/command/setRoleId",
        data:"roleId=" + roleId + "&roleName=" + roleName,
        success:function(data){
        	var parameter = jQuery("input[name='parameter']:checked").val();
			if(parameter != null && parameter != ""){
				parent.window.location.href = parameter;
			}
        },
        error:function(){
        }
    });
}


function choiceType(url){
	var roleId = jQuery("input[name='roleId']:checked").val();
	if(roleId == null){
		alert("请选择发起角色类型");
		return;
	}
	
	parent.window.location.href = url;
}
</script>