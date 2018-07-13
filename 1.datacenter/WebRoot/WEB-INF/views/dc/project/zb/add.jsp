<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<title>南方基地管理提升平台</title>
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!--公共JS -->
	<script type="text/javascript" >var basePath = "<%=basePath%>zb";</script>
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
	<script src="/SRMC/rmpb/js/isIe.js"></script>
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<jsp:include page="../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="PROJECT_STATUS,PROJECT_TYPE"></jsp:param>
</jsp:include>
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<span class="searchCondition_header">本周汇报</span>
	</div>
<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<tr>
		    <th width="20%" align="right"><b>*</b>周报名称：</th>
		    <td>
		    <input name="column01"  id="column01" <c:if test="${not empty code || not empty vo.column01}">readonly="readonly"</c:if> type="text" class="text01" maxlength="60" 
		    style="width:300px;<c:if test="${not empty code || not empty vo.column01}">background-color: #f5f5f5;</c:if>" placeholder="请输入周报名称"  value="${vo.column01}"/>
		    </td>
		    <th align="right"><b>*</b>汇报周期：</th>
		    <td>
				<div class="date l" style="width: 100px;">
		    	<input readonly="readonly" name="column08" id="column08" type="text"  placeholder="请输入" onchange="clearColor(this)"  class="text02 l" value="<fmt:formatDate value='${vo.column08}' pattern='yyyy-MM-dd'/>"
		                      onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width:190px;" /><i></i>
		        </div>
		        <div class="l" style="width: 30px;padding-top: 5px;">至</div>
		    	<div class="date l" style="width: 100px;">
		    	<input readonly="readonly" name="column09" id="column09" type="text"  placeholder="请输入" onchange="clearColor(this)" class="text02 l" value="<fmt:formatDate value='${vo.column09}' pattern='yyyy-MM-dd'/>"
		                      onclick="WdatePicker({minDate:'#F{$dp.$D(\'column08\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width:190px;" /><i></i>
		        </div>
		    </td>		    
	    </tr>
		<tr>
		    <th align="right">项目编码：</th>
		    <td>
		    <input <c:if test="${not empty code || not empty vo.column02}">readonly="readonly"</c:if> onfocus="onfocuses(this)" 
			                     onkeyup="clean()"
			                     onblur="onblurs(this)"
                      name="column02" id= "column02" value="${vo.column02}" type="text" class="text01" style="width:300px;<c:if test="${not empty code || not empty vo.column01}">background-color: #f5f5f5;</c:if>">
		    <c:if test="${ !(not empty code || not empty vo.column02)}">
		    	<a onclick="ev_selectList()" style="cursor: pointer;color: blue;" title="选择建设项目">选择</a></td>
			</c:if>
		    </td>		    		    
		    <th align="right"><b>*</b>事项状态：</th>
		    <td id="rulesGrade_td">
		    	<select id="column10" name="column10" class="ui-select" style="width:306px;" defaultValue="${vo.column10 }"  dictType="PROJECT_STATUS"/>
		    </td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>项目名称：</th>
		    <td><input name="projectName"  id="projectName"  type="text" class="text01" style="width:300px;background-color: #f5f5f5;" readonly="readonly" value="${vo.projectName }"/></td>		 
		    <input name="projectUserid"  id="projectUserid"  type="hidden"  value="${vo.projectUserid}"/>   		    
		    <input name="projectId"  id="projectId"  type="hidden"  value="${vo.projectId}"/>   		    
		    <th align="right"><b>*</b>本周状态：</th>
		    <td id="rulesGrade_td">
		    	<select id="column11" name="column11" class="ui-select" style="width:306px;" defaultValue="${vo.column11 }"  dictType="PROJECT_STATUS"/>
		    </td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>项目类型：</th>
		    <td>
		    	
		   		<input name="column14Name" readonly="readonly" id="column14Name"  type="text" class="text01" style="width:300px;background-color: #f5f5f5;" value='<jsp:include page="../../sys/dict/include/dict_config.jsp"><jsp:param name="paramTypeCode" value="PROJECT_TYPE"></jsp:param><jsp:param name="paramCode" value="${vo.column14}"></jsp:param></jsp:include>'/>
		    	<input name="column14"  id="column14"  type="hidden"  value="${vo.column14}"/>
		    </td>
		    <th align="right"><b></b>负责人：</th>
		    <td><input name="column12"  id="column12" readonly="readonly" type="text" class="text01" style="width:300px;background-color: #f5f5f5;" value="${vo.column12}"/>
		    	<input name="column12UserId"  id="column12UserId"  type="hidden"  value="${vo.column12UserId}"/>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>本周工作：</th>
		    <td colspan="3"><textarea rows="5" cols="20" class="textArea01" style="width:89.4%" placeholder="请填写本周工作" id="column03" name="column03">${vo.column03}</textarea></td>
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>下周计划：</th>
		    <td colspan="3"><textarea rows="5" cols="20" class="textArea01" style="width:89.4%" placeholder="请填写下周计划" id="column04" name="column04">${vo.column04}</textarea></td>
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>关键点：</th>
		    <td colspan="3"><textarea rows="5" cols="20" class="textArea01" style="width:89.4%" placeholder="请填写关键点" id="column05" name="column05">${vo.column05}</textarea></td>
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>存在问题：</th>
		    <td colspan="3"><textarea rows="5" cols="20" class="textArea01" style="width:89.4%" placeholder="请填写存在问题" id="column06" name="column06">${vo.column06}</textarea></td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>汇报人：</th>
		    <td><input name="column07"  id="column07" readonly="readonly" type="text" class="text01" style="width:300px;background-color: #f5f5f5;" placeholder="请填写汇报人"  value="${vo.column07}"/>
		    	<input name="column07UserId"  id="column07UserId"  type="hidden"  value="${vo.column07UserId}"/>
		    	<input name="column07Departmen"  id="column07Departmen"  type="hidden"  value="${vo.column07Departmen}"/>
		    </td>
		    <th align="right"><b></b>汇报时间：</th>
		    <td>
		   <div class="date l" style="width: 100px;">
	    	<input readonly="readonly" name="column13" id="column13" type="text"  placeholder="请填写汇报时间" onchange="clearColor(this)"  class="text02 l" 
	    	value="<fmt:formatDate value='${vo.column13}' pattern='yyyy-MM-dd HH:mm'/>"
	                      onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm',alwaysUseStartDate:true})" style="width:190px;" /><i></i>
	        </div> 
		    
		    </td>		    
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>审核人：</th>
		    <td>
			    <select class="ui-select" <c:if test="${not empty vo.auditUserid}">disabled="disabled"</c:if>  id="audituser_sel" 
			    style="width:306px;<c:if test="${not empty vo.auditUserid}">background-color: #f5f5f5;</c:if>">
			          <option value="">请选择</option>
			          <c:if test="${not empty vo.auditUserid}">
			          	<option value="${vo.auditUserid}" title="${vo.auditUsername}" selected="selected">${vo.auditUsername}</option>
			          </c:if>
<!-- 			          部门室经理 -->
			          <c:forEach items="${audits}" var="vo" varStatus="i">
			          		<option value="${vo.userId}" title="${vo.userName}">${vo.userName}</option>
			          </c:forEach>
		        </select>
		    	<input name="auditUserid"  id="auditUserid"  type="hidden"  value="${vo.auditUserid}"/>
		    	<input name="auditUsername"  id="auditUsername"  type="hidden"  value="${vo.auditUsername}"/>
		    </td>
	    </tr>
		<tr>
	   		<th colspan="4" align="center" height="50">
	   			<input name="" type="button" class="btn_common02" onclick="ev_save()" value="保 存" />
	   		 	<input name="" type="button" class="btn_common04" onclick="ev_back()" value="返 回" />
	   		</th>
		 </tr>    
  </table>
</form>
</div>

<div class="gl_import_m" id="gl_import_m_div2" style="display: none;">
	<div class="searchCondition">
		<span>历史周报</span>
	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable" id="listTable_load">
	  
	</table>
</div>
</body>
</html>

<script type="text/javascript">
window.onload = function() {
	$("#column03").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 1300){
	    	$(this).val($(this).val().substring(0,1300));
	   }
	});
	
	$("#column04").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 1300){
	    	$(this).val($(this).val().substring(0,1300));
	   }
	});
	
	$("#column05").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 600){
	    	$(this).val($(this).val().substring(0,600));
	   }
	});
	
	$("#column06").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 1300){
	    	$(this).val($(this).val().substring(0,1300));
	   }
	});
}
/**
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
 * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18  
 */
Date.prototype.format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

$(function(){

	//新增各种周报传递的编码
	//建设项目/投资编码/子项目管理/子项目合同
	var code = "${code}";
	//判空
	if(code != ""){
		searchProjectByCode(code);
	}
});

function searchProjectByCode(code){

	jQuery.ajax({
		url: "<%=basePath%>zb/searchProjectByCode",
		dataType: "json",
		data: {
			code: code
		},
		type: "POST",
		success: function(data) {
			if(data!=null){
				setValues(data[0][0].proCode,data[0][0].proName,data[0][0].proType,data[0][0].proUser,data[0][0].proUserName,data[0][0].proCreateUser,data[0][0].proId);
				ajaxLoadHistory(data[0][0].proId);
				ajaxLoadAuditUser(data[0][0].proId,data[0][0].productType);
			}else{
				return false;
			}
		}
	});
}

var obj10,obj11,audituser;
$(document).ready(function(){
	var m = "${mess}";
	if(m != "" && m == "s"){
		alert("保存成功");
	}
	else if(m != "" && m == "e"){
		alert("保存失败");
	}

	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	
	obj10 = $('#column10').data('ui-select');
	obj11 = $('#column11').data('ui-select');
	
	
	 //审核人
	audituser = $('#audituser_sel').data('ui-select');
	audituser.onClick = function(value) {
		 $("#auditUserid").val(value);
		 $("#auditUsername").val($("#audituser_sel").parent().find(".ui-select-input").html());
	};
	
});

//兼容谷歌  
    if(!window.showModalDialog){
        window.showModalDialog=function(url,name,option){
            if(window.hasOpenWindow){  
                window.newWindow.focus();  
            }  
            var re = new RegExp(";", "g");    
            var option  = option.replace(re, '","'); //把option转为json字符串  
            var re2 = new RegExp(":", "g");  
            option = '{"'+option.replace(re2, '":"')+'"}';  
            option = JSON.parse(option);  
            var openOption = 'width='+parseInt(option.dialogWidth)+',height='+parseInt(option.dialogHeight)+',left='+(window.screen.width-parseInt(option.dialogWidth))/2+',top='+(window.screen.height-30-parseInt(option.dialogHeight))/2;  
            window.hasOpenWindow = true;  
            window.newWindow = window.open(url,name,openOption);
        }
    }
    
<%-- function ev_selectList(){
	var returnValue = window.showModalDialog("<%=basePath%>zb/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
	searchProjectByCode(returnValue);
} --%>

function ev_selectList(){
	//判断是不是IE浏览器
	if(isIE()){
		var returnValue = window.showModalDialog("<%=basePath%>zb/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
		searchProjectByCode(returnValue);
	}else{
		//其他浏览器
		window.open("<%=basePath%>zb/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px");
	}
}


/**
 * 必填项
 * @author weifei
 * @date Mar 8, 2012 10:02:40 AM
 */
function ev_required_item(){
// 		var form = document.getElementById("form1");
		var k = 0;
		var b = true;
		
		if( $("#column01").val().length > 60 ){
			$("#column01").css("border-color","red");
          		$("#column01").bind("change", function(){
			  	$(this).css("border-color","");
			});
			alert("周报名称不得超过60个字");
			return false;
		}
		
		if( $("#column03").val().length > 1300 ){
			$("#column03").css("border-color","red");
          		$("#column03").bind("change", function(){
			  	$(this).css("border-color","");
			});
			alert("本周工作不得超过1300个字");
			return false;
		}
		
		if( $("#column04").val().length > 1300 ){
			$("#column04").css("border-color","red");
          		$("#column04").bind("change", function(){
			  	$(this).css("border-color","");
			});
			alert("下周计划不得超过1300个字");
			return false;
		}
		
		if( $("#column05").val().length > 600 ){
			$("#column05").css("border-color","red");
       		$("#column05").bind("change", function(){
			  	$(this).css("border-color","");
			});
			alert("关键点不得超过600个字");
			return false;
		}
		if( $("#column06").val().length > 1300 ){
			$("#column06").css("border-color","red");
       		$("#column06").bind("change", function(){
			  	$(this).css("border-color","");
			});
			alert("存在问题不得超过1300个字");
			return false;
		}
		
		if(!checkTime()){
			alert("汇报周期开始时间不能大于结束时间");
	    	return false;
		}
		
			//周报名称
		var column01 = $("#column01");
		if(column01.val() == ""){
       		k++;bindC(column01);			
		}
		//汇报周期
		var column08 = $("#column08");
		if($.trim($(column08).val())=="" ){
			k++;
			$(column08).parent().css("border-color","red");
		}
		var column09 = $("#column09");
		if($.trim($(column09).val())=="" ){
			k++;
			$(column09).parent().css("border-color","red");
		}
		
		
      	//事态状态
      	var column10 = $("#column10");
        if($.trim($(column10).val())=="" ){
         	k++;
       		$(column10).parent().css("border-color","red");
       		$(column10).bind("change", function(){
			  	$(this).parent().css("border-color","");
			});
         }
      	//本周状态
      	var column11 = $("#column11");
        if($.trim($(column11).val())=="" ){
         	k++;
       		$(column11).parent().css("border-color","red");
       		$(column11).bind("change", function(){
			  	$(this).parent().css("border-color","");
			});
         }
      	//周报审核人
      	var audituser_sel = $("#audituser_sel");
        if($.trim($(audituser_sel).val())=="" ){
         	k++;
       		$(audituser_sel).parent().css("border-color","red");
       		$(audituser_sel).bind("change", function(){
			  	$(this).parent().css("border-color","");
			});
         }
      	//本周工作：
		if( $.trim($("#column03").val())=="" ){
			k++;bindC("#column03");
		}
		//下周计划
		if( $.trim($("#column04").val())=="" ){
			k++;bindC("#column04");
		}
		//关键点
		if( $.trim($("#column05").val())=="" ){
			k++;bindC("#column05");
		}
		//存在问题
		if( $.trim($("#column06").val())=="" ){
			k++;bindC("#column06");
		}
      	
      	if(k > 0){
      		alert("带*号的为必填字段，请完善必填字段。");
      		b = false;
      	}
	    
		return b;
}

function bindC(field){
	$(field).css("border-color","red");
    $(field).bind("click", function(){
		$(this).css("border-color","");
	});
}
//汇报周期
function checkTime(){
	var begin_date = $.trim($('#column08').val());
    var end_date = $.trim($('#column09').val());
    //清除样式
   	$('#column08').parent().css("border-color","");
    $('#column09').parent().css("border-color","");
    if(begin_date != "" && end_date != ""){
	    var begin = new Date((begin_date + " 00:00:00").replace(/-/g,"/"));
	    var end = new Date((end_date + " 00:00:00").replace(/-/g,"/"));
	    //如果开始时间大于结束时间
	    if(begin.getTime() > end.getTime()){
		    $('#column08').parent().css("border-color","red");
		    $('#column09').parent().css("border-color","red");
	    	return false;
	    }
    }
    return true;
}

function clearColor(target){
	$(target).parent().css("border-color","");
	checkTime();
}

function checkSubmit(){

	//周报名称
	if( $.trim($("#column01").val())=="" ){
		animateAlert("table01","column01","请填写周报名称" );
		return false;
	}
	if( $("#column01").val().length > 180 ){
		animateAlert("table01","column01","周报名称不得超过60个字" );
		return false;
	}
	//汇报周期
	if( $.trim($("#column08").val())=="" ){
		animateAlert("table01","column08","请填写汇报周期" );
		return false;
	}
	if( $.trim($("#column09").val())=="" ){
		animateAlert("table01","column09","请填写汇报周期" );
		return false;
	}
	//事项状态
	if( $.trim($("#column10").val())=="" ){
		animateAlert("table01","column10","请选择事项状态" );
		return false;
	}
	//本周状态
	if( $.trim($("#column11").val())=="" ){
		animateAlert("table01","column11","请选择本周状态" );
		return false;
	}
	//本周工作：
	if( $.trim($("#column03").val())=="" ){
		animateAlert("table01","column03","请填写本周工作" );
		return false;
	}
	//下周计划
	if( $.trim($("#column04").val())=="" ){
		animateAlert("table01","column04","请填写下周计划" );
		return false;
	}
	//关键点
	if( $.trim($("#column05").val())=="" ){
		animateAlert("table01","column05","请填写关键点" );
		return false;
	}
	//存在问题
	if( $.trim($("#column06").val())=="" ){
		animateAlert("table01","column06","请填写存在问题" );
		return false;
	}
	return true;
}

//滚动页面并弹框
function animateAlert(id,focusId,msg){
	//清除未选择，未填写的提示
	jQuery("html,body").find(".noselected_tips").remove();
	//滚动页面至表格
	jQuery("html,body").animate({scrollTop:jQuery("#"+id).offset().top},10);
	//提示
	if(focusId == "column10" || focusId == "column11" ) {
		$("#"+focusId).parent().parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}else{
		$("#"+focusId).parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}
}

function ev_save(){
	if(!ev_required_item()){
		return false;
	}
	
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>zb/saveOrUpdate",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result == 1){
        		var proType = "${proType}";
        		//建设项目
        		if(proType == "1"){
        			window.location.href = "<%=basePath%>jsxm/list";
        			//投资编码
        		}else if(proType == "2"){
        			window.location.href = "<%=basePath%>tzbm/list";
        			//子项目
        		}else if(proType == "3"){
        			window.location.href = "<%=basePath%>zxm/list";
        			//子项目合同
        		}else if(proType == "4"){
        			window.location.href = "<%=basePath%>zxmHt/list";
        			//建设项目一览表
        		}else if(proType == "5"){
        			window.location.href = "<%=basePath%>jsxmfile/list";
        		}else if(proType == "6"){
        			window.location.href = "<%=basePath%>zbremain/list";
        		}else{
		        	window.location.href = "<%=basePath%>zb/list";
        		}
        	}else{
        		alert("保存失败!");
        	}
        },
        error:function () {
        	alert("保存失败!");
        }
    });
}


//根据项目编码查询项目信息
function autoCompletes(){
		jQuery("#column02").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>zb/searchProjectByCode",
					dataType: "json",
					data: {
						code: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
						
				     		response(jQuery.map(data, function( item ) {
				     			return {
				     				value:item[0].proCode + " - " + item[0].proName,
				     			 	proId:item[0].proId,//+" - "+item[0].account+" - "+item[1].orgName
									proType:item[0].proType,
									proCode:item[0].proCode,
									proName:item[0].proName,
									proUser:item[0].proUser,
									proUserName:item[0].proUserName,
									proCreateUser:item[0].proCreateUser,
									productType:item[0].productType,
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
				setValues(ui.item.proCode,ui.item.proName,ui.item.proType,ui.item.proUser,ui.item.proUserName,ui.item.proCreateUser,ui.item.proId);
				ajaxLoadHistory(ui.item.proId);
				ajaxLoadAuditUser(ui.item.proId,ui.item.productType);
				return false;
			}
		});
	}
function clean(){
	if (event.keyCode == 13) {
        //js 监听对应的id
         //document.getElementById("demandFzrId_input_focus").value="";
      } 
}
//失去焦点事件
function onblurs(target){
	if(target.value==''||target.value=='请填写项目编码'){
		jQuery('#createUserid_input').val('');
		target.style.color='#b6b6b6';
	}
}
//光标选中输入框事件
function onfocuses(target){
	if(target.value=='请填写项目编码'){
		target.value='';
	}
	target.style.color='#333333';
	autoCompletes();
}

/**
*	获取建设项目总控
*/
function ajaxLoadAuditUser(proId,productType){
	jQuery.ajax({
        type:"POST",
        async:true,
        url:"<%=basePath%>zb/ajaxLoadAuditUser",
        data:"proId=" + proId + "&productType="+productType,
        dataType:"json",
        success:function(data){
        	if(data.length <= 0){
        		return false;
        	}
        	var temp = '<option value="">请选择</option>';
        	var ui_temp = '<li class="" data-value="" title="请选择">请选择</li>';
        	$.each(data,function(k,v){
        		temp += '<option value="'+v.userId+'">'+v.userName+'</option>';
        		ui_temp += '<li class="" data-value="'+v.userId+'" title="'+v.userName+'">'+v.userName+'</li>';
        	});
        	$("#audituser_sel").html(temp);
			$("#audituser_sel").parent().find(".ui-select-list").html(ui_temp);
        	$('.ui-select').ui_select();
			audituser = $('#audituser_sel').data('ui-select');
        },
        error:function(){
        }
    });
}

//历史周报查询
function ajaxLoadHistory(proCode){
	jQuery.ajax({
        type:"POST",
        async:true,
        url:"<%=basePath%>zb/ajaxLoadHistory",
        data:"proCode=" + proCode,
        dataType:"json",
        success:function(data){
        	var temp = '<tr>\
					  		<th>序号</th>\
							<th>项目类型</th>\
							<th>项目编号</th>\
							<th>项目名称</th>\
							<th>周报名称</th>\
							<th>事项状态</th>\
							<th>本周状态</th>\
							<th>汇报周期</th>\
							<th>本周工作</th>\
							<th>下周计划</th>\
							<th>关键点</th>\
							<th>存在问题</th>\
							<th>汇报人</th>\
							<th>汇报时间</th>\
							<th>操作</th>\
					  </tr>';
      		$.each(data,function(k,v){
      			var column14 = "";
      			
      			if(v.column14 == "1"){
					  column14 = "软件工程";
      			}else if(v.column14 == "2"){
					  column14 = "集成工程";
      			}else if(v.column14 == "3"){
					  column14 = "土建工程";
      			}else if(v.column14 == "4"){
					  column14 = "征地工程";
      			}
      			var column10 = "";
      			
      			if(v.column10 == "1"){
					  column10 = "正常";
      			}else if(v.column10 == "2"){
					  column10 = "超前";
      			}else if(v.column10 == "3"){
					  column10 = "滞后";
      			}else if(v.column10 == "4"){
					  column10 = "完工";
      			}
      			
      			var column11 = "";
      			
      			if(v.column11 == "1"){
					  column11 = "正常";
      			}else if(v.column11 == "2"){
					  column11 = "超前";
      			}else if(v.column11 == "3"){
					  column11 = "滞后";
      			}else if(v.column11 == "4"){
					  column11 = "完工";
      			}
      			
      			temp += '<tr class="td01" id="">\
			  	  	<td><input name="subBox" type="checkbox" value="'+v.id+'"></td>\
				    <td>'+column14+'</td>\
				    <td>'+v.column02+'</td>\
				    <td>'+v.projectName+'</td>\
				    <td>'+v.column01+'</td>\
					<td>'+column10+'</td>\
					<td>'+column11+'</td>\
					<td>'+(new Date(v.column08.time)).format("yyyy/MM/dd")+ "--" + (new Date(v.column09.time)).format("yyyy/MM/dd")+'</td>\
					<td>'+v.column03+'</td>\
					<td>'+v.column04+'</td>\
					<td>'+v.column05+'</td>\
					<td>'+v.column06+'</td>\
					<td>'+v.column07+'</td>\
				    <td>'+(new Date(v.column13.time)).format("yyyy-MM-dd hh:mm:ss")+'</td>\
				    <td>\
				    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/know.png"><a href="javascript:copyZb(\''+v.id+'\')">复制周报</a></span>\
				    </td>\
				 </tr>';
      		});
      		$("#listTable_load").html(temp);
      		$("#gl_import_m_div2").show();
        },
        error:function(){
        }
    });
}

/**
	项目编号，项目名称，项目类型，项目负责人ID，项目负责人名称，项目承建人
*/
function setValues(proCode,proName,proType,proUser,proUserName,proCreateUser,projectId){

	$("#column02").css("border-color","");
	$("#projectName").css("border-color","");
	$("#column14Name").css("border-color","");
	$("#column12").css("border-color","");
	$("#column01").css("border-color","");
	
	jQuery("#column02").val(proCode);
	jQuery("#projectName").val(proName);
	jQuery("#column14").val(proType);
	if(proType == "1"){
		jQuery("#column14Name").val("软件工程");
	}else if(proType == "2"){
		jQuery("#column14Name").val("集成工程");
	}else if(proType == "3"){
		jQuery("#column14Name").val("土建工程");
	}else if(proType == "4"){
		jQuery("#column14Name").val("征地工程");
	}
	jQuery("#column12").val(proUserName);
	jQuery("#column12UserId").val(proUser);
	jQuery("#projectUserid").val(proCreateUser);
	jQuery("#projectId").val(projectId);
	jQuery("#column01").val("关于《"+proName+"》的报告");
	
}

//复制周报
function copyZb(id){
	if(confirm("是否确定复制当前周报内容到本周？")){
		ajaxCopyZb(id);
	}
}
//返回
function ev_back(){
	 var code = "${code}";
	 //带id过来只需无效校验1/2
	 if(code != "" &&
		$.trim($("#column10").val())=="" && 
		$.trim($("#column11").val())=="" && 
		$.trim($("#column03").val())=="" && 
		$.trim($("#column04").val())=="" && 
		$.trim($("#column05").val())=="" && 
		$.trim($("#column06").val())==""){
	 	window.history.back();
	 }else if($.trim($("#column01").val())=="" && 
	 	$.trim($("#column02").val())=="" &&
		$.trim($("#column10").val())=="" && 
		$.trim($("#column11").val())=="" && 
		$.trim($("#column03").val())=="" && 
		$.trim($("#column04").val())=="" && 
		$.trim($("#column05").val())=="" && 
		$.trim($("#column06").val())=="" ){
		window.history.back();
	}else{
		if(confirm("是否放弃当前周报填写？")){
			window.history.back();
		}
	}
}


function ajaxCopyZb(id){
	jQuery.ajax({
        type:"POST",
        async:true,
        url:"<%=basePath%>zb/ajaxCopyZb",
        data:"id=" + id,
        dataType:"json",
        success:function(data){
        	//本周工作
	        jQuery("#column03").val(data.column03);
	        //下周计划
	        jQuery("#column04").val(data.column04);
	        //关键点
	        jQuery("#column05").val(data.column05);
	        //存在问题
	        jQuery("#column06").val(data.column06);
// 	        jQuery("#column08").val((new Date(data.column08.time)).format("yyyy-MM-dd"));
// 	        jQuery("#column09").val((new Date(data.column09.time)).format("yyyy-MM-dd"));
	        obj10.val(data.column10);
	        obj11.val(data.column11);
	        
	        $("#column03").css("border-color","");
			$("#column04").css("border-color","");
			$("#column05").css("border-color","");
			$("#column06").css("border-color","");
			$("#column08").parent().css("border-color","");
			$("#column09").parent().css("border-color","");
        },
        error:function(){
        }
    });
}

</script>