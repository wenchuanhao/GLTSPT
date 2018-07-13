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
		<span class="searchCondition_header">新增-数据提供</span>
	</div>
	<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<input type="hidden" name="jobId" id="jobId" value="${datacenterImpForm.jobId}"/>
		<tr>
		    <th align="right"><b>*</b>服务名称：</th>
		    <td><input name="jobName" id="jobName" type="text" class="text01" style="width:300px;" placeholder="请填写服务名称" value="${serviceInfo.jobName}" />
		    <th align="right"><b>*</b>服务编码：</th>
		    <td><input name="jobCode" id="jobCode" type="text" class="text01" style="width:300px;" placeholder="请填写服务编码" value="${serviceInfo.jobCode}" /></td>		    		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>调用的类名：</th>
		    <td><input name="beanClass" id="beanClass" type="text" class="text01" style="width:300px;" placeholder="请填写调用的类名" value="${serviceInfo.beanClass}" />
		    <th align="right"><b>*</b>任务运行时间表达式：</th>
		    <td><input name="cronExpression" id="column12" type="text" class="text01" style="width:300px;" placeholder="请填写任务运行时间表达式" value="${serviceInfo.cronExpression}" /></td>		    
	    </tr>
	    <tr>
		    <th align="right"><b></b>备注：</th>
		    <td colspan="3"><textarea rows="5" cols="20" style="width:89.4%" name="remark" id="remark" placeholder="" >${serviceInfo.remark}</textarea></td>
	    </tr>
	    
	    <tr>
	    	<th align="right"><b>*</b>FTP账号：</th>
		    <td><input name="ftpAccount" id="ftpAccount" type="text" class="text01" style="width:300px;" placeholder="请填写FTP账号" value="${ftpInfo.ftpAccount}"/></td>	    
		    <th align="right"><b></b>FTP密码：</th>
		    <td><input name="ftpPasswd" id="ftpPasswd" type="text" class="text01" style="width:300px;" placeholder="请填写FTP密码" value="${ftpInfo.ftpPasswd}"/></td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>文件目录：</th>
		    <td><input name="filePath" id="filePath" type="text" class="text01" style="width:300px;" placeholder="请填写文件目录" value="${ftpInfo.filePath}" /></td>
		    <th align="right"><b></b>文件格式：</th>
		    <td><input name="filePattern" id="filePattern" type="text" class="text01" style="width:300px;" placeholder="请填写文件格式" value="${ftpInfo.filePattern}" />
		    </td>		    
	    </tr>
	    <tr>
		    <th align="right"><b></b>数据库表名：</th>
		    <td><input name="dbTablename" id="dbTablename" type="text" class="text01" style="width:300px;" placeholder="请填写数据库表名" value="${ftpInfo.dbTablename}" /></td>
	    </tr>	    
	    
		<tr>
	   		<th colspan="4" align="center" height="50">
	   			<input name="" type="button" class="btn_common02" id="save" onclick="ev_save()" value="保 存" />
	   		 	<input name="" type="button" class="btn_common04" onclick="javascript:history.back();" value="返 回" />
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
		alert("保存成功");
		ev_list();
	}
	else if(m != "" && m == "e"){
		alert("保存失败");
	}

	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});

function ev_list(){
	window.location.href='<%=basePath%>datacenterExpController/queryDcExpServices';
}

<%--function ev_selectList(){--%>
<%--	var returnValue = window.showModalDialog("<%=basePath%>jsxm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); --%>
<%--	var v = returnValue.split("_");--%>
<%--	jQuery("#column04").val(v[0]);--%>
<%--	jQuery("#column04Code").val(v[1]);--%>
<%--	jQuery("#column04Name").val(v[2]);--%>
<%--	jQuery("#column01").val(v[3]);--%>
<%--	getTName(v[3]);	--%>
<%--}--%>
function ev_selectList(){
	if(isIE()){
		var returnValue = window.showModalDialog("<%=basePath%>jsxm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
		putValue(returnValue);
	}else{
		window.open("<%=basePath%>jsxm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px");
	}
}
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
  		//创建时间
 		var column = $("#column17");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		} 
		 		
  		//年度建设目标
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
		  		
  		//年度安排资本开支
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
        return "请输入数字!";
    }
    return "";  
 }

function ev_save(){
	//if(ev_required_item()){
	//	$("#form1").attr("action","<%=basePath%>datacenterExpController/saveOrUpdate");
	//	$("#form1").submit();
	//}
	$("#form1").attr("action","<%=basePath%>datacenterExpController/saveOrUpdate");
	$("#form1").submit();
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