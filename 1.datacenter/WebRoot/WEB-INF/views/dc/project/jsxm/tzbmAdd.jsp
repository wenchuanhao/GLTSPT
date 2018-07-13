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
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="/SRMC/rmpb/js/isIe.js"></script>
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<span class="searchCondition_header">新增-投资编码</span>
	</div>
	<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<input type="hidden" name="column01" id="column01" value="${vo.column01}"/><!-- 项目类型 -->
		<tr>
		    <th align="right"><b>*</b>建设项目编号：</th>
		    <c:set var="jsxm" value="${vo.jsxm}"/>
		    <input type="hidden" name="column04" id="column04" value="${jsxm.id}"/>		    
		    <td><input name="column04Code"  id="column04Code"  type="text" class="text01" style="width:300px;" placeholder=""  onfocus="autoCompletes1();" onchange="this.value='';$('#column04').val('');$('#column04Name').val('')"  value="${jsxm.column02}" maxlength="20"/>
		    <a onclick="ev_selectList()" style="cursor: pointer;color: blue;" title="选择建设项目">选择</a></td>
		    <th align="right"><b>*</b>项目总投资：</th>
		    <td><input name="column06"  id="column06"  type="text" class="text01" style="width:300px;" placeholder="请填写项目总投资"  value="<fmt:formatNumber value="${vo.column06}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>万元</td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>建设项目名称：</th>
		    <td><input name="column04Name"  id="column04Name"  type="text" class="text01" style="width:300px;" placeholder=""  onfocus="autoCompletes1();" onchange="this.value='';$('#column04').val('');$('#column04Code').val('')"  value="${jsxm.column03}" maxlength="50"/>
		    <a onclick="ev_selectList()" style="cursor: pointer;color: blue;" title="选择建设项目">选择</a></td>	    
		    <th align="right"><b>*</b>至上年度安排投资计划：</th>
		    <td><input name="column11"  id="column11"  type="text" class="text01" style="width:300px;" placeholder="请填写至上年度安排投资计划"  value="<fmt:formatNumber value="${vo.column11}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>万元</td>		    		    
	    </tr>
	    
	    <tr>
		    <th width="20%" align="right"><b></b>项目类型：</th>
		    <td id="xmType">
					${vo.column01 ne '1' ? '':'软件工程'}
					${vo.column01 ne '2' ? '':'集成工程'}
					${vo.column01 ne '3' ? '':'土建工程'}
					${vo.column01 ne '4' ? '':'征地工程'}
		    </td>
		    <th align="right"><b>*</b>年度投资：</th>
		    <td><input name="column08"  id="column08"  type="text" class="text01" style="width:300px;" placeholder="请填写年度投资"  value="<fmt:formatNumber value="${vo.column08}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>万元</td>
	    </tr>
	    
	    <tr>
	    	<th align="right"><b>*</b>投资编号：</th>
			<td><input name="column02"  id="column02"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column02}"  onchange="column02Code(this)" maxlength="20"/></td>		    
		    <th align="right"><b>*</b>资本开支目标：</th>
		    <td><input name="column09"  id="column09"  type="text" class="text01" style="width:300px;" placeholder="请填写资本开支目标"  value="<fmt:formatNumber value="${vo.column09}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>万元</td>
	    </tr>
	    
	    <tr>
	    	<th align="right"><b>*</b>投资项目名称：</th>
		    <td><input name="column03"  id="column03"  type="text" class="text01" style="width:300px;" placeholder="请填写投资项目名称"  value="${vo.column03}" maxlength="50"/></td>	    
		    <th align="right"><b>*</b>年度转资目标：</th>
		    <td><input name="column12"  id="column12"  type="text" class="text01" style="width:300px;" placeholder="请填写年度转资目标"  value="<fmt:formatNumber value="${vo.column12}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>万元</td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>建设内容：</th>
		    <td colspan="3"><textarea rows="5" cols="20" style="width:89.4%" name="column05"  id="column05" placeholder=""  title="${vo.column05}">${vo.column05}</textarea></td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>计划书文号：</th>
		    <td><input name="column15"  id="column15"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column15}" maxlength="20"/></td>		    
		    <th align="right"><b></b>任务书文号：</th>
		    <td><input name="column16"  id="column16"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column16}" maxlength="20"/>
		    </td>		    
	    </tr>
	    <tr>
		    <th align="right"><b></b>建设期限：</th>
		    <td><input name="column07"  id="column07"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column07}" maxlength="30"/></td>
		    <th align="right"><b>*</b>创建时间：</th>
		    <td>
		    	<input name="column17"  id="column17"  type="text" class="text01" style="width:300px;" placeholder="请填写创建时间"  
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column17}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"/>
		    </td>		    
	    </tr>	    
	    
	    <tr>
		    <th align="right"><b>*</b>投资项目联系人：</th>
		    <td><input name="column13"  id="column13"  type="text" class="text01" style="width:100px;" placeholder="请填写投资项目联系人"  onfocus="autoCompletes(this);" onblur="this.value=''"/>
				<span id="selected_column13">
					<c:forEach items="${vo.list13}" var="item" varStatus="i">
						<a href="javascript:void(0);" id="${item.userId }">
							<input type="hidden" id="column13Id" name="column13Id" value="${item.userId }" />
							<span style="padding: 0 3px 0 0;">${item.userName }</span>
							<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10">
						</a>
					</c:forEach>
				</span>		    
		    </td>	    
		    <th align="right"><b>*</b>状态：<span id="m" style="font: bolder;color: red;"></span></th>
		    <td >
		    	<select class="ui-select" id="column18"  name="column18" style="width:306px;" placeholder="请选择状态" onchange="ev_m(this.value)">
		    			<option ${vo.column18 eq null ? 'selected="selected"':''} value="">请选择</option>
						<option ${vo.column18 eq '1' ? 'selected="selected"':''} value="1">立项完成</option>
						<option ${vo.column18 eq '2' ? 'selected="selected"':''} value="2">采购完成</option>
						<option ${vo.column18 eq '3' ? 'selected="selected"':''} value="3">设计完成</option>
						<option ${vo.column18 eq '4' ? 'selected="selected"':''} value="4">施工完成</option>
						<option ${vo.column18 eq '5' ? 'selected="selected"':''} value="5">初验完成</option>
						<option ${vo.column18 eq '6' ? 'selected="selected"':''} value="6">终验完成</option>
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
	    <tr>
		    <th align="right"><b>*</b>投资项目督办人：</th>
		    <td><input name="column14"  id="column14"  type="text" class="text01" style="width:100px;" placeholder="请填写投资项目督办人"   onfocus="autoCompletes(this);" onblur="this.value=''"/>
				<span id="selected_column14">
					<c:forEach items="${vo.list14}" var="item" varStatus="i">
						<a href="javascript:void(0);" id="${item.userId }">
							<input type="hidden" id="column14Id" name="column14Id" value="${item.userId }" />
							<span style="padding: 0 3px 0 0;">${item.userName }</span>
							<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10">
						</a>
					</c:forEach>
				</span>		    
		    </td>	    
		    <th align="right"><b>*</b>投资编码管理员：</th>
		    <td><input name="column19"  id="column19"  type="text" class="text01" style="width:100px;" placeholder="请填写投资编码管理员"   onfocus="autoCompletes(this);" onblur="this.value=''"/>
				<span id="selected_column19">
					<c:forEach items="${vo.list19}" var="item" varStatus="i">
						<a href="javascript:void(0);" id="${item.userId }">
							<input type="hidden" id="column19Id" name="column19Id" value="${item.userId }" />
							<span style="padding: 0 3px 0 0;">${item.userName }</span>
							<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10">
						</a>
					</c:forEach>
				</span>		    
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>备注：</th>
		    <td colspan="3"><textarea rows="3" cols="20" style="width:89.4%" name="column10"  id="column10"  placeholder="" title="${vo.column10}">${vo.column10}</textarea></td>
	    </tr>
		<tr>
	   		<th colspan="4" align="center" height="50">
	   			<input name="" type="button" class="btn_common02" id="save" onclick="ev_save()" value="保 存" />
	   		 	<input name="" type="button" class="btn_common04" onclick="history.back()" value="返 回" />
	   		</th>
	   </tr>    
  </table>
  </form>
</div>
</body>
</html>

<script type="text/javascript">
window.onload = function() { 
	document.getElementById("column05").onkeydown = function() { if(this.value.length >= 500) event.returnValue = false; } 
	document.getElementById("column10").onkeydown = function() { if(this.value.length >= 500) event.returnValue = false; }
}

$(document).ready(function(){
	var m = "${mess}";
	if(m != "" && m == "s"){
		ev_list();
	}
	else if(m != "" && m == "e"){
		alert("保存失败");
	}

	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});

function ev_list(){
	window.location.href="<%=basePath%>jsxm/tzbmList?id=${jsxmId}";
}

<%--function ev_selectList(){--%>
<%--	var returnValue;--%>
<%--	if(isIE()){--%>
<%--		 returnValue = window.showModalDialog("<%=basePath%>jsxm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); --%>
<%--	}else{--%>
<%--		 returnValue = window.open("<%=basePath%>zxm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px");--%>
<%--	}--%>
<%--	var v = returnValue.split("_");--%>
<%--	jQuery("#column04").val(v[0]);--%>
<%--	jQuery("#column04Code").val(v[1]);--%>
<%--	jQuery("#column04Name").val(v[2]);--%>
<%--	jQuery("#column01").val(v[3]);--%>
<%--	getTName(v[3]);	--%>
<%--}--%>
function ev_selectList(){
	//判断是不是IE浏览器
	if(isIE()){
		var returnValue = window.showModalDialog("<%=basePath%>jsxm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
		putValue(returnValue);
	}else{
		//其他浏览器
		window.open("<%=basePath%>jsxm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px");
	}
}
//给父窗口元素赋值
function putValue(returnValue){
	var v = returnValue.split("_");
	jQuery("#column04").val(v[0]);
	jQuery("#column04Code").val(v[1]);
	jQuery("#column04Name").val(v[2]);
	jQuery("#column01").val(v[3]);
	getTName(v[3]);	
}

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
       	
   		//建设项目编码
		var column = $("#column04Code");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}
  		//建设项目名称
		var column = $("#column04Name");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}		      	
       	
       	
       	//至上年度安排投资计划
       	var column = $("#column11");
       	var v = column.val();
  		if(v == ""){
  			k++;bindC(column);
  		}else{
  			var rv = validate(v);
	   		if(rv != ""){
	   			$(column).css("border-color","red");
	   			alert(rv);return false;
	   		}else{
	   			$(column).css("border-color","");
	   			$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   		}  		
  		}
  		//年度转资目标
       	var column = $("#column12");
       	var v = column.val();
  		if(v == ""){
  			k++;bindC(column);
  		}else{
  			var rv = validate(v);
	   		if(rv != ""){
	   			$(column).css("border-color","red");
	   			alert(rv);return false;
	   		}else{
	   			$(column).css("border-color","");
	   			$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   		} 		
  		}
  		//投资项目名称
		var column = $("#column03");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}  		
  		
       	//投资项目联系人
		var column = $("#column13");
		if($.trim($("#selected_column13").html()) == ""){
           	k++;bindC(column);			
		}      	
  		//投资编码
		var column = $("#column02");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}      	
       	//投资项目督办人
		var column = $("#column14");
		if($.trim($("#selected_column14").html()) == ""){
           	k++;bindC(column);			
		}
		       	
       	//项目总投资  		
       	var column = $("#column06");
       	var v = column.val();
  		if(v == ""){
  			k++;bindC(column);
  		}else{
  			var rv = validate(v);
	   		if(rv != ""){
	   			$(column).css("border-color","red");
	   			alert(rv);return false;
	   		}else{
	   			$(column).css("border-color","");
	   			$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   		}  		
  		}
  		
  		//建设期限		
       	/*var column = $("#column07");
       	var v = column.val();
  		var rv = validate1(v);
	   	if(rv != ""){
	   		$(column).css("border-color","red");
	   		alert(rv);return false;
	   	}else{
	   		$(column).css("border-color","");
	   		$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   	}*/  		
  		
  		//创建时间
 		var column = $("#column17");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		} 
		 		
  		//年度投资
       	var column = $("#column08");
       	var v = column.val();
  		if(v == ""){
  			k++;bindC(column);
  		}else{
  			var rv = validate(v);
	   		if(rv != ""){
	   			$(column).css("border-color","red");
	   			alert(rv);return false;
	   		}else{
	   			$(column).css("border-color","");
	   			$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   		}  		
  		}
  		//状态
 		var column = $("#column18");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}
		  		
  		//资本开支目标
       	var column = $("#column09");
       	var v = column.val();
  		if(v == ""){
  			k++;bindC(column);
  		}else{
  			var rv = validate(v);
	   		if(rv != ""){
	   			$(column).css("border-color","red");
	   			alert(rv);return false;
	   		}else{
	   			$(column).css("border-color","");
	   			$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   		}  		
  		}
      	
       	//投资编码管理员
		var column = $("#column19");
		if($.trim($("#selected_column19").html()) == ""){
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

function validate(value){
    if(isNaN(value.replace("￥","").replace(new RegExp(",","g"),""))){
        return "请输入正数!";
    }
    return "";  
}

function validate1(value){
	var reg=/^\d+$/;
    if(value != "" && reg.test(value)!=true){
        return "请输入正整数!";
    }
    return "";
}

function ev_save(){
	if(ev_required_item()){
		$("#form1").attr("action","<%=basePath%>tzbm/saveOrUpdate1");
		$("#form1").submit();
	}
}

//自动匹配建设项目
function autoCompletes1(){
		jQuery("#column04Code").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>jsxm/searchJSXM",
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
					jQuery("#column04").val(ui.item.id);
					jQuery("#column04Code").val(ui.item.code);
					jQuery("#column04Name").val(ui.item.name);
					jQuery("#column01").val(ui.item.type);
					getTName(ui.item.type);
					//getColumn02Code(ui.item.type);
					return false;
			}
		});
		
		jQuery("#column04Name").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>jsxm/searchJSXM",
					dataType: "json",
					data: {
						column03: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
									value:item.column03,
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
					jQuery("#column04").val(ui.item.id);
					jQuery("#column04Code").val(ui.item.code);
					jQuery("#column04Name").val(ui.item.name);
					jQuery("#column01").val(ui.item.type);
					getTName(ui.item.type);
					//getColumn02Code(ui.item.type);
			}
		});	
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

//投资编码唯一性验证
function column02Code(obj){
			$.ajax({
				type:"POST",
				async:false,
				url: "<%=basePath%>tzbm/searchCode",
				data:"column02="+obj.value,
				dataType:"text",
				success:function(result){
					if(result != "" && parseInt(result) >= 1){
						$(obj).css("border-color","red");
						alert("投资编号："+obj.value+"已经存在，请重新输入");
						$(obj).val("");
					}else{
						$(obj).css("border-color","");
					}
				},
				error:function(){}
			});
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
function getColumn02Code(type){
			$.ajax({
				type:"post",
				async:false,
				url: "<%=basePath%>tzbm/getCode",
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