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
	<title >新增-子项目</title>
	</c:if>
	<c:if test="${vo.id ne null}">
	<title >修改-子项目</title>
	</c:if>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!--文件上传样式，js -->
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script>

	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="/SRMC/rmpb/js/isIe.js"></script>
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
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<c:if test="${vo.id eq null}">
		<span class="searchCondition_header">新增-子项目</span>
		</c:if>
		<c:if test="${vo.id ne null}">
		<span class="searchCondition_header">修改-子项目</span>
		</c:if>
	</div>
	<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<input type="hidden" name="column01" id="column01" value="${vo.column01}"/><!-- 项目类型 -->
	    <tr>
		    <th align="right"><b>*</b>投资编号：</th>
		    <c:set var="tzbm" value="${vo.tzbm}"/>
		    <input type="hidden" name="column07" id="column07" value="${tzbm.id}"/>
		    <td><input name="column07Code"  id="column07Code"  type="text" class="text01" style="width:300px;" placeholder=""  onfocus="autoCompletes1();" onchange="this.value='';$('#column07').val('');$('#column07Name').val('')"   value="${tzbm.column02}"/>
		    <a onclick="ev_selectList()" style="cursor: pointer;color: blue;" title="选择投资编码">选择</a></td>
		    <th align="right"><b>*</b>投资项目名称：</th>
		    <td><input name="column07Name"  id="column07Name"  type="text" class="text01" style="width:300px;" placeholder=""  onfocus="autoCompletes1();"  onchange="this.value='';$('#column07').val('');$('#column07Code').val('')"   value="${tzbm.column03}"/>
		    <a onclick="ev_selectList()" style="cursor: pointer;color: blue;" title="选择投资编码">选择</a></td>		    
	    </tr>
	    	
		<tr>
		    <th width="20%" align="right"><b></b>项目类型：</th>
		    <td id="xmType">
					${vo.column01 ne '1' ? '':'软件工程'}
					${vo.column01 ne '2' ? '':'集成工程'}
					${vo.column01 ne '3' ? '':'土建工程'}
					${vo.column01 ne '4' ? '':'征地工程'}
		    </td>		    
		    <th align="right"><b>*</b>创建时间：</th>
		    <td><input name="column08"  id="column08"  type="text" class="text01" style="width:300px;" placeholder="请填写创建时间"  
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column08}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"/></td>		    		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>子项目编号：</th>
		    <td><input name="column02"  id="column02"  type="text" class="text01" style="width:300px;background-color: #DBDBDB" placeholder=""  value="${vo.column02}" readonly="readonly"/></td>
		    <th align="right"><b>*</b>子项目名称：</th>
		    <td><input name="column03"  id="column03"  type="text" class="text01" style="width:300px;" placeholder="请填写子项目名称"  value="${vo.column03}" maxlength="50"/></td>		    
	    </tr>
	    
	    <tr>
	    	<th align="right"><b>*</b>子项目管理员：</th>
		    <td>
		    	<input name="column09"  id="column09"  type="text" class="text01" style="width:200px;" placeholder="请填写子项目管理员"  onfocus="autoCompletes(this);" onblur="this.value=''"/>
				<span id="selected_column09">
					<c:forEach items="${vo.list09}" var="item" varStatus="i">
						<a href="javascript:void(0);" id="${item.userId }">
							<input type="hidden" id="column09Id" name="column09Id" value="${item.userId }" />
							<span style="padding: 0 3px 0 0;">${item.userName }</span>
							<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10">
						</a>
					</c:forEach>
				</span>		    
		    </td>
		    <th align="right"><b>*</b>子项目审核人：</th>
		    <td>
		    	<input name="column05"  id="column05"  type="text" class="text01" style="width:300px;" placeholder="请填写子项目审核人"  onfocus="autoCompletes(this);" onblur="this.value=''"/>
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
		    <th align="right"><b></b>建设内容：</th>
		    <td colspan="3"><textarea rows="5" cols="20" style="width:89.4%" name="column06" id="column06" placeholder=""  title="${vo.column06}">${vo.column06}</textarea></td>
	    </tr>
	    
	    <c:set var="zxmHtList" value="${vo.zxmHtList}"/>
	    <c:forEach items="${zxmHtList}" var="vo" varStatus="i">
	    <tr>
		    <th align="right"><b></b>子项目合同编号${i.count}：</th>
		    <td><input name=""  id=""  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column01}"  readonly="readonly" disabled="disabled"/></td>
		    <th align="right"><b></b>子项目合同名称${i.count}：</th>
			<td><input name=""  id=""  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column03}"  readonly="readonly" disabled="disabled"/></td>
	    </tr>
	    </c:forEach>
	    
		<tr>
	   		<th colspan="4" align="center" height="50">
	   			<input name="" type="button" class="btn_common02" id="save" onclick="ev_save()" value="保 存" />
	   		 	<input name="" type="button" class="btn_common04" onclick="window.close()" value="关闭" />
	   		</th>
	   </tr>    
  </table>
  </form>
</div>
</body>
</html>

<script type="text/javascript">
window.onload = function() { 
	document.getElementById("column06").onkeydown = function() { if(this.value.length >= 500) event.returnValue = false; } 
}

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
});

function ev_list(){
window.location.href='<%=basePath%>zxm/list';
}

/**
 * 必填项
 */
function ev_required_item(){
		var k = 0;
		var b = true;

		$("#table01").find("select[class='ui-select']").each(function(){
            if($(this).attr("placeholder") != "" && $(this).val() == ""){
	       		k++;
				$("#div_select_"+this.id).css("border-color","red");
            		$("#div_select_"+this.id).bind("change", function(){
					  	$(this).css("border-color","");
				});	       		    
            }
		});		
  		//投资编码
		var column = $("#column07Code");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}  		
  		//投资编码名称
		var column = $("#column07Name");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}
		
  		//创建时间
		var column = $("#column08");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}  		
  		//子项目名称
		var column = $("#column03");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}  
      	
       	//子项目审核人
		var column = $("#column05");
		if($.trim($("#selected_column05").html()) == ""){
           	k++;bindC(column);			
		}
       	
       	//子项目管理员
		var column = $("#column09");
		if($.trim($("#selected_column09").html()) == ""){
           	k++;bindC(column);			
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

function ev_save(){
	if(ev_required_item()){	
		$("#form1").attr("action","<%=basePath%>zxm/saveOrUpdate");
		$("#form1").submit();
	}
}

//自动匹配投资编码
function autoCompletes1(){
		jQuery("#column07Code").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>tzbm/searchTZBM",
					dataType: "json",
					data: {
						column02: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
									value:item.column02+" "+item.column03,
									id:item.id,//资源ID
									code:item.column02,//编码
									name:item.column03,//名称
									type:item.column01//项目类型
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
					jQuery("#column07").val(ui.item.id);
					jQuery("#column07Code").val(ui.item.code);
					jQuery("#column07Name").val(ui.item.name);
					jQuery("#column01").val(ui.item.type);
					column02Code(ui.item.type);//自动生成子项目编码
					getTName(ui.item.type);
					return false;
			}
		});
		
		jQuery("#column07Name").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>tzbm/searchTZBM",
					dataType: "json",
					data: {
						column03: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
									value:item.column03+" "+item.column02,
									id:item.id,//资源ID
									code:item.column02,//编码
									name:item.column03,//名称
									type:item.column01//项目类型
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
					jQuery("#column07").val(ui.item.id);
					jQuery("#column07Code").val(ui.item.code);
					jQuery("#column07Name").val(ui.item.name);
					jQuery("#column01").val(ui.item.type);
					column02Code(ui.item.type);//自动生成子项目编码
					getTName(ui.item.type);
					return false;
			}
		});		
}

//function ev_selectList(){
	//var returnValue = window.showModalDialog("<%=basePath%>tzbm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
	//if(isIE()){
		//var returnValue = window.showModalDialog("<%=basePath%>tzbm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
	//}else{
		//var returnValue = window.open("<%=basePath%>tzbm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px");
	//}
	//var v = returnValue.split("_");
	//jQuery("#column07").val(v[0]);
	//jQuery("#column07Code").val(v[1]);
	//jQuery("#column07Name").val(v[2]);
	//jQuery("#column01").val(v[3]);
	//getTName(v[3]);	
	//column02Code(v[3]);//自动生成子项目编码
//}

function ev_selectList(){
	//判断是不是IE浏览器
	if(isIE()){
		 var returnValue = window.showModalDialog("<%=basePath%>tzbm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
		 putValue(returnValue);
	}else{
		//其他浏览器
		 window.open("<%=basePath%>tzbm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px");
	}
}

//给父窗口元素赋值
function putValue(returnValue){
		var v = returnValue.split("_");
		jQuery("#column07").val(v[0]);
		jQuery("#column07Code").val(v[1]);
		jQuery("#column07Name").val(v[2]);
		jQuery("#column01").val(v[3]);
		getTName(v[3]);	
		column02Code(v[3]);//自动生成子项目编码
}
function getTName(type){
      			var xmTypeName = "";
      			if(type == "1"){
					  xmTypeName = "软件工程";
      			}else if(type == "2"){
					  xmTypeName = "集成工程";
      			}else if(type == "3"){
					  xmTypeName = "土建工程";
      			}else if(type == "4"){
					  xmTypeName = "征地工程";
      			}
				jQuery("#xmType").text(xmTypeName);
}

//用户可选择创建人
function autoCompletes(target){
	jQuery(target).autocomplete({
		source: function( request, response ) {
			jQuery.ajax({
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
			if(jQuery("#selected_"+$(target).attr('id')).children("[id='" + ui.item.userId + "']").length != 0){
				jQuery(target).val("");
				return false;
			}else{
				jQuery("#selected_"+$(target).attr('id')).append('<a href="javascript:void(0);" class="" id="' + ui.item.userId + '">\
																	<input type="hidden" id="'+$(target).attr('id')+'Id" name="'+$(target).attr('id')+'Id" value="' + ui.item.userId + '"/>\
																	<span style="padding: 0 3px 0 0;">' + ui.item.userName + '</span>\
																	<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10" />\
																  </a>');
																	
																	 
				jQuery(target).val("");
				return false;
			}
		}
	});
}

//子项目编码生成
function column02Code(type){
			$.ajax({
				type:"post",
				async:false,
				url: "<%=basePath%>zxm/getCode",
				data:"type="+type,
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