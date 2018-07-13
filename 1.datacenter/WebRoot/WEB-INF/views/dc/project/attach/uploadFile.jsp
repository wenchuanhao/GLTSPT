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
	<title>请选择附件</title>
	<base  target="_self"/>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>

	<!--文件上传样式，js -->
	<!-- <link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script> -->
	
	<!--公共JS -->
	<script type="text/javascript" >
		var basePath = "<%=basePath%>";
	</script>
	<script type="text/javascript" src="/SRMC/dc/project/common.js"></script>
	
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadifive/uploadifive.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/jquery.uploadifive.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/isIe.js"></script>
	
	<style>
	.gl_import_m_file{
		width:90%;
		margin-bottom: 60px;
		margin-top: 0;
		margin-left: auto;
		margin-right: auto;
	}
	#uploadifive-uploadFiles1{
   		margin-left: 80%;
   		border: 0;		
	}
	</style>
</head>

<body>
<div class="gl_import_m_file" >
	<div class="searchCondition">
		<span class="searchCondition_header">新增-上传文档</span>
	</div>
	<form action=""  method="post"  id="form1" enctype="multipart/form-data">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<input type="hidden" name="parentId" id="parentId" value="${vo.parentId}"/>
		<input type="hidden" name="column10" id="column10" />
		<input type="hidden" name="jsxmId"  id="jsxmId" />
		
	    <tr>
		    <th align="right"><b>*</b>文档类型：</th>
		    <td>
		    	<c:if test="${vo.id ne ''}">
		    		${vo.column07 eq '1' ? '电子文档':''}
		    		${vo.column07 eq '2' ? '纸质原件':''}
		    		${vo.column07 eq '3' ? '纸质复印件':''}
		    	</c:if>
		    	<select class="ui-select" id="column07"  name="column07" style="width:506px;display: none;" placeholder="请选择文档类型" >
						<option ${vo.column07 eq '1' ? 'selected="selected"':''} value="1">电子文档</option>
						<option ${vo.column07 eq '2' ? 'selected="selected"':''} value="2">纸质原件</option>
						<option ${vo.column07 eq '3' ? 'selected="selected"':''} value="3">纸质复印件</option>
		        </select>
		    </td>
	    </tr>
		<tr>
		    <th width="30%" align="right"><b>*</b>文档名称：</th>
		    <td><input name="column01"  id="column01"  type="text" class="text01" style="width:500px;" placeholder="系统默认使用当前上传文件名称，也可以自由填写"  value="${vo.column01}" maxlength="50"/></td>
	    </tr>
	    
	    <tr id="dz_file" style="display:none">
	    	<th width="30%" align="right"><b>*</b>附件：</th>
		    <td>
		    <input type="hidden"  name="queueSize" id="queueSize"  value="1"/>
		    <div class="text01" style="width:500px;" id="fi_box_div_1" value="1" >
		    	<a id="file_download" style="display: none;color: blue;">${vo.column08} (<fmt:formatNumber value="${vo.column09/1024}" pattern="#"/>KB)
		    	<img onclick="javascript:ev_delete('${vo.id}')" src="/SRMC/dc/images/close_icon01.png" width="17" height="17" style="cursor:pointer;" /></a>
		    	<input type="hidden"  name="fileName" id="fileName"  value="${vo.column08}"/>
		    	<input type="file" name="file"  id="uploadFiles1"  style="display:none"/>
		    </div>
		    </td>	    
	    </tr>
   
	    <tr id="zz_file1" style="display:none">
	    	<th width="10%" align="right"><b>*</b>有效期：</th>
		    <td>
		    	<input name="column02" id="column02" type="text" class="text01"   style="width:500px;" placeholder="请填写有效期" 
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column02}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"/>
		    </td>	    
	    </tr>
	    <tr id="zz_file2" style="display:none">
	    	<th width="10%" align="right"><b>*</b>份数：</th>
		    <td>
		    	<input name="column03" id="column03" type="text" class="text01" style="width:500px;" placeholder="请填写份数"  value="${vo.column03}" maxlength="5"/>
		    </td>	    
	    </tr>
	    <tr id="zz_file3" style="display:none">
	    	<th width="10%" align="right"><b>*</b>存放位置：</th>
		    <td>
		    	<input name="column04" id="column04" type="text" class="text01" style="width:500px;" placeholder="请填写存放位置"  value="${vo.column04}"/>
		    </td>	    
	    </tr>
	    <tr><th align="right" colspan="2"></th></tr>
		<tr>
	   		<th colspan="2" align="center" height="50">
	   			<c:if test="${vo.id eq null || userId eq vo.createUserId}">
	   		 		<input name="" type="button" class="btn_common02" id="save" onclick="ev_save()" value="开始上传" />
	   		 	</c:if>
	   		 	<input name="" type="button" class="btn_common04" id="close" onclick="javascript:ev_close()" value="关闭" /> 
	   		</th>
	   </tr>    
  </table>
  </form>
</div>
</body>
</html>

<script type="text/javascript">
$(document).ready(function(){
	if($("#id").val() == "" || $("#fileName").val() == ""){
		$("#uploadFiles1").uploadifive(config);//页面上传文件初始化
	}else{
		if($("#fileName").val() != ""){
			$("#file_download").show();
		}
	}
	
	if($("#id").val() == ""){
		$("#column07").show();
	}
	
	var m = "${mess}";
	if(m != "" && m == "s"){
		alert("上传成功");
		window.dialogArguments.ev_refresh("${vo.parentId}");
		window.close();
	}
	else if(m != "" && m == "e"){
		alert("上传失败");
	}
	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	
	// 获取已经实例化的select对象
	var obj = $('#column07').data('ui-select');
	obj.onClick = function(value) {
		setSelect(value);
	}
	
	setSelect($("#column07").val());
});

function setSelect(value){
	$("#dz_file").hide();
	$("#zz_file1").hide();
	$("#zz_file2").hide();
	$("#zz_file3").hide();
	
	if(value == "1"){
		$("#dz_file").show();
	}else if(value == "2" || value == "3"){
		$("#zz_file1").show();
		$("#zz_file2").show();
		$("#zz_file3").show();
	}
}

/**
 * 必填项
 * @author weifei
 * @date Mar 8, 2012 10:02:40 AM
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
		
		if(k > 0){
			alert("请选择文档类型");
			return false;
      	}
		
		var column01 = $("#column01");
		column01.bind("click", function(){$(this).css("border-color","");});		
		if(column01.val() == ""){
			$(column01).css("border-color","red");k++;	
		}
		
		var column07 = $("#column07").val();
        if(column07 == "1"){
			var fi_box_div_1 = $("#fi_box_div_1");
			fi_box_div_1.bind("click", function(){$(this).css("border-color","");});	
			if($("#fileName").val() == ""){
				$(fi_box_div_1).css("border-color","red");k++;	
			}else{
				$(fi_box_div_1).css("border-color","");
			}
        }else if(column07 != "1" && column07 != ""){
			var column02 = $("#column02");
			column02.bind("click", function(){$(this).css("border-color","");});		
			if(column02.val() == ""){
				$(column02).css("border-color","red");k++;	
			}
			var column03 = $("#column03");
			column03.bind("click", function(){$(this).css("border-color","");});		
			if(column03.val() == ""){
				$(column03).css("border-color","red");k++;	
			}else{
			    var reg = new RegExp("^[0-9]*$"); 
			    if(!reg.test(column03.val())){
			        $(column03).css("border-color","red");
					alert("请输入大于0的整数");
					return false;			        
			    }
			    if(parseFloat(column03.val()) == 0){
			    	$(column03).css("border-color","red");
					alert("请输入大于0的整数");
					return false;			    
			    }else{
			    	column03.val(parseFloat(column03.val()));
			    }			
			}
			var column04 = $("#column04");
			column04.bind("click", function(){$(this).css("border-color","");});		
			if(column04.val() == ""){
				$(column04).css("border-color","red");k++;	
			}
		}
					     	
      	if(k > 0){
      		alert("*为必填字段");
      		b = false;
      	}
      	
		return b;
}

function ev_save(){
	if(ev_required_item()){
		
		
		var id = "";
		var jsxmId = "";
		if(isIE()){
			id = window.dialogArguments.$("#id").val();
			jsxmId = window.dialogArguments.$("#jsxmId").val();
		}else{
			var obj = window.opener.document;
			id = jQuery("#id",obj).val();
			jsxmId = jQuery("#jsxmId",obj).val();
		}
		$("#column10").val(id);
		$("#jsxmId").val(jsxmId);
		
		//$("#column10").val(window.dialogArguments.$("#id").val());
		//$("#jsxmId").val(window.dialogArguments.$("#jsxmId").val());
	
		if($("#fileName").val() != ""){
			var file_download = $("#file_download").css("display");
			if(file_download == "none"){
	    	  	var queueSize = config.queueSizeLimit;
    	  		if(queueSize > 1){$("#queueSize").val(queueSize);}	
    	  		$("#uploadFiles1").data('uploadifive').settings.formData = {
						"id":$("#id").val(),//附件ID
			    	 	"parentId":$("#parentId").val(),//文档目录ID
			    	  	"column07":$("#column07").val(),//文档类型1电子文档2纸质原件3纸质复印件
			    	  	"column01":$("#column01").val(),//文档名称
			    	  	//"column10":window.dialogArguments.$("#id").val(),//业务类型1建设项目2投资编码3子项目4子项目合同 资源ID
			    	  	//"jsxmId":window.dialogArguments.$("#jsxmId").val(),//建设项目ID
			    	  	"column10":id,//业务类型1建设项目2投资编码3子项目4子项目合同 资源ID
			    	  	"jsxmId":jsxmId,//建设项目ID
			    	  	"queueSize":$("#queueSize").val()//当前队列文件数量
	    	  	};
	    	  	$("#uploadFiles1").uploadifive('upload');
	    	}else{
				$("#form1").attr("action","<%=basePath%>fileUpload/saveOrUpdate");
				$("#form1").submit();	    	
	    	}
		}else{
			$("#form1").attr("action","<%=basePath%>fileUpload/saveOrUpdate");
			$("#form1").submit();
		}
	}
}

function ev_close(){
	window.close();
}

function ev_delete(id){
		if(confirm("确定删除附件？")){
			$.ajax({
				type 	: "post",
				url 	: "<%=basePath%>fileUpload/deleteFile?id="+id,
				async	: true,
				error 	: function() {
					alert("删除失败");
				},
				success : function(data, textStatus) {
					if(data != "" && data == "s"){
						alert("删除成功");
						$("#file_download").text("");
						$("#fileName").val("");
						$("#uploadFiles1").uploadify(config);//页面上传文件初始化
						$("#file_download").hide();
					}
					else if(m != "" && m == "e"){
						alert("删除失败");
					}
				},
				dataType : "text"
			});	
		}
}
</script>