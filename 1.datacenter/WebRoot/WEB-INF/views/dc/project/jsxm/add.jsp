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
	<c:if test="${vo.id eq null}">
	<title >新增-建设项目</title>
	</c:if>
	<c:if test="${vo.id ne null}">
	<title >修改-建设项目</title>
	</c:if>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
	<!-- 联想查询 -->
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<c:if test="${vo.id eq null}">
		<span class="searchCondition_header">新增-建设项目</span>
		</c:if>
		<c:if test="${vo.id ne null}">
		<span class="searchCondition_header">修改-建设项目</span>
		</c:if>	
	</div>
	<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<tr>
		    <th width="30%" align="right"><b>*</b>项目类型：</th>
		    <td>
		    	<c:if test="${vo.id ne null}">
		    		<input type="hidden" name="column01" id="column01" value="${vo.column01}"/>
					${vo.column01 ne '1' ? '':'软件工程'}
					${vo.column01 ne '2' ? '':'集成工程'}
					${vo.column01 ne '3' ? '':'土建工程'}
					${vo.column01 ne '4' ? '':'征地工程'}
				</c:if>
				<c:if test="${vo.id eq null}">		    	
			    	<select class="ui-select" id="column01"  name="column01" style="width:506px;" placeholder="请填写项目类型"  onchange="column02Code(this)">
							<option value="">请谨慎选择(保存后，不能修改) </option>
							<option ${vo.column01 eq '1' ? 'selected="selected"':''} value="1">软件工程</option>
							<option ${vo.column01 eq '2' ? 'selected="selected"':''} value="2">集成工程</option>
							<option ${vo.column01 eq '3' ? 'selected="selected"':''} value="3">土建工程</option>
							<option ${vo.column01 eq '4' ? 'selected="selected"':''} value="4">征地工程</option>    	
			        </select>
		        </c:if>
		    </td>		    
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>建设项目编号：</th>
		    <td>
		    	<input name="column02"  id="column02"  type="text" class="text01" style="width:500px;background-color: #DBDBDB" placeholder="请填写建设项目编号"  value="${vo.column02}" readonly="readonly"/>
		    </td>
	    </tr>
	    <tr>
	    	<th align="right"><b>*</b>建设项目名称：</th>
		    <td>
		    	<input name="column03"  id="column03"  type="text" class="text01" style="width:500px;" placeholder="请填写建设项目名称"  value="${vo.column03}" maxlength="50"/>
		    </td>
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>建设总控：</th>
		    <td>
		    	<input name="column04"  id="column04"  type="text" class="text01" style="width:200px;" placeholder="请填写建设总控"  value=""  onfocus="autoCompletes(this);" onblur="this.value=''"/>
				<span id="selected_column04">
					<c:forEach items="${vo.list04}" var="item" varStatus="i">
						<a href="javascript:void(0);"  id="${item.userId }">
							<input type="hidden" id="column04Id" name="column04Id" value="${item.userId }" />
							<span style="padding: 0 3px 0 0;">${item.userName }</span>
							<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10">
						</a>
					</c:forEach>
				</span>		    	
		    </td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>审核人：</th>
		    <td>
		    	<input name="column05"  id="column05"  type="text" class="text01" style="width:200px;" placeholder=""  onfocus="autoCompletes(this);" onblur="this.value=''"/>
				<span id="selected_column05">
					<c:forEach items="${vo.list05}" var="item" varStatus="i">
						<a href="javascript:void(0);" id="${item.userId }">
							<input type="hidden" id="column05Id" name="column05Id" value="${item.userId }" />
							<span style="padding: 0 3px 0 0;">${item.userName }</span>
							<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10">
						</a>
					</c:forEach>
				</span>		    	
		    </td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>建设项目管理员：</th>
		    <td>
		    	<input name="column10"  id="column10"  type="text" class="text01" style="width:200px;" placeholder="请填写建设项目管理员"  onfocus="autoCompletes(this);" onblur="this.value=''"/>
				<span id="selected_column10">
					<c:forEach items="${vo.list10}" var="item" varStatus="i">
						<a href="javascript:void(0);" id="${item.userId }">
							<input type="hidden" id="column10Id" name="column10Id" value="${item.userId }" />
							<span style="padding: 0 3px 0 0;">${item.userName }</span>
							<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10">
						</a>
					</c:forEach>
				</span>		    	
		    </td>
	    </tr>	    
	    <tr>
		    <th align="right"><b></b>建设内容：</th>
		    <td><textarea rows="3" cols="20" style="width:500px" name="column06"  id="column06" placeholder=""  title="${vo.column06}">${vo.column06}</textarea></td>
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>创建时间：</th>
		    <td>
		    	<input name="column07"  id="column07"  type="text" class="text01" style="width:500px;" placeholder="请填写创建时间"  
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column07}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"/>
		    </td>
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>项目状态：<span id="m" style="font: bolder;color: red;"></span></th>
		    <td>
		    	<select class="ui-select" id="column08"  name="column08" style="width:506px;" placeholder="请填写项目状态"  onchange="ev_m(this.value)">
		    			<option ${vo.column08 eq null ? 'selected="selected"':''} value="">请选择</option>
						<option ${vo.column08 eq '1' ? 'selected="selected"':''} value="1">立项完成</option>
						<option ${vo.column08 eq '2' ? 'selected="selected"':''} value="2">采购完成</option>
						<option ${vo.column08 eq '3' ? 'selected="selected"':''} value="3">设计完成</option>
						<option ${vo.column08 eq '4' ? 'selected="selected"':''} value="4">施工完成</option>
						<option ${vo.column08 eq '5' ? 'selected="selected"':''} value="5">初验完成</option>
						<option ${vo.column08 eq '6' ? 'selected="selected"':''} value="6">终验完成</option>						
		        </select>
			    <script>
				function ev_m(v){
					var m = "";
					if(v == 1){
						m = "建设任务书下达初始";
					}else if(v == 2){
						m = "所有合同签署完成";
					}else if(v == 3){
						m = "完成设计评审";
					}else if(v == 4){
						m = "交工文件完成";
					}else if(v == 5){
						m = "初验纪要完成";
					}else if(v == 6){
						m = "终验纪要完成";
					}
					$("#m").text(m);
				}	    
			    </script>			        
		    </td>
	    </tr>

	    <c:set var="tzbmList" value="${vo.tzbmList}"/>
	    <c:forEach items="${tzbmList}" var="vo" varStatus="i">
	    <tr>
		    <th align="right"><b></b>投资编号${i.count}：</th>
		    <td><input name="column09Code"  id="column09Code"  type="text" class="text01" style="width:500px;" placeholder=""  value="${vo.column02}"  readonly="readonly" disabled="disabled"/></td>
	    </tr>
	    <tr>
		    <th align="right"><b></b>投资编码名称${i.count}：</th>
		    <td><input name="column09Name"  id="column09Name"  type="text" class="text01" style="width:500px;" placeholder=""  value="${vo.column03}"  readonly="readonly" disabled="disabled"/></td>
	    </tr>	    
	    </c:forEach>
	   
		<tr>
	   		<th colspan="2" align="center" height="50">
   				<input name="" type="button" class="btn_common02" id=""  onclick="ev_save()" value="保 存" />
   				<input name="" type="button" class="btn_common04" onclick="window.close()" value="关闭" />
	   		</th>
	   </tr>  
  </table>
  </form>
</div>
</body>
</html>

<script type="text/javascript">
$(document).ready(function(){
	var m = "${mess}";
	if(m != "" && m == "s"){
		if("${form_S}" != "" && "${form_S}" == "modify"){
			window.opener.ev_search();
		}else{
			window.opener.ev_reset();
		}
		window.close();
	}
	else if(m != "" && m == "e"){
		alert("保存失败");
	}

	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	
	window.onload = function() { document.getElementById("column06").onkeydown = function() { if(this.value.length >= 500) event.returnValue = false; } }
});


/**
 * 必填项
 */
function ev_required_item(){
		var k = 0;
		var b = true;
		
		$("#table01").find("select[class='ui-select']").each(function(){
            if($(this).val() == ""){
	       		k++;
				$("#div_select_"+this.id).css("border-color","red");
            		$("#div_select_"+this.id).bind("change", function(){
					  	$(this).css("border-color","");
				});	       		    
            }
		});
		
		//建设项目
		var column02 = $("#column02");
		if(column02.val() == ""){
           		k++;bindC(column02);			
		}
		//建设项目名称
		var column03 = $("#column03");
		if(column03.val() == ""){
           		k++;bindC(column03);			
		}
		//建设总控
		var column04 = $("#column04");
		if($.trim($("#selected_column04").html()) == ""){
           		k++;bindC(column04);			
		}
		//建设项目管理员
		var column10 = $("#column10");
		if($.trim($("#selected_column10").html()) == ""){
           		k++;bindC(column10);			
		}		
		//创建时间
		var column07 = $("#column07");
		if(column07.val() == ""){
           		k++;bindC(column07);			
		}
		//项目状态
		var column08 = $("#column08");
		if(column08.val() == ""){
           		k++;bindC(column08);			
		}
										
      	if(k > 0){
      		alert("*为必填字段");
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

function ev_list(){
	window.location.href='<%=basePath%>jsxm/list';
}

function ev_save(){
	if(ev_required_item()){
		document.forms[0].action="<%=basePath%>jsxm/saveOrUpdate";
		document.forms[0].submit();	
	}
}

//用户可选择创建人
function autoCompletes(target){
	$(target).autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "<%=basePath%>jsxm/searchUser",
				dataType: "json",
				data: {
					userValue: request.term
				},
				type: "POST",
				success: function(data) {
					if(data!=null){
			     		response(jQuery.map(data, function( item ) {
			     			return {
			     			 value:item[0].userName+"------"+item[0].orgName,
								userName:item[0].userName,
								userId:item[0].userId,
								account:item[0].account,
								orgName:item[0].orgName
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
			if($("#selected_"+$(target).attr('id')).children("[id='" + ui.item.userId + "']").length != 0){
				$(target).val("");
				return false;
			}else{
				jQuery("#selected_"+$(target).attr('id')).append('<a href="javascript:void(0);" id="' + ui.item.userId + '">\
																	<input type="hidden" id="'+$(target).attr('id')+'Id" name="'+$(target).attr('id')+'Id" value="' + ui.item.userId + '"/>\
																	<span style="padding: 0 3px 0 0;">' + ui.item.userName + '</span>\
																	<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10" />\
																  </a>');
				$(target).val("");
				return false;
			}
		}
	});
}

//建设编码生成
function column02Code(obj){
			if(obj.value == ""){
				$("#column02").val("");
				return;
			}
			$.ajax({
				type:"post",
				async:false,
				url: "<%=basePath%>jsxm/getCode",
				data:"type="+obj.value,
				dataType:"text",
				success:function(result){
					if(result != ""){
						$("#column02").val(result);
					}
				},
				error:function(){}
			});
}
</script>