<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>
<script src="/SRMC/rmpb/js/isIe.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:if test="${vo.id eq null}">
	<title >新增-项目合同</title>
	</c:if>
	<c:if test="${vo.id ne null}">
	<title >修改-项目合同</title>
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
	<!-- 
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script> -->
	
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadifive/uploadifive.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/jquery.uploadifive.js"></script>
	<script src="/SRMC/rmpb/js/isIe.js"></script>
	<style>
		#uploadifive-uploadFiles1{
    		margin-left: 80%;
    		border: 0;		
		}
	</style>
	<script src="/SRMC/rmpb/js/isIe.js"></script>
	<script type="text/javascript" >
		var basePath = "<%=basePath%>"; 
		
		var config = {
			  'buttonClass'  : 'uploadifive',
		      'uploadScript'    : basePath+'zxmHt/saveOrUpdate?',
		      'fileObjName':'file',//设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
		      'buttonText' : '选择文件',
		      'multi':'true',//允许连续上传多个文件
		      'uploadLimit' : 50,//一次性最多允许上传多少个,不设置的话默认为999个
		      'queueSizeLimit' : 50,//一次性最多允许上传多少个,不设置的话默认为999个
		      'fileExt': '*.exe',
		      'auto' : false,
		      'method':'post',
		      'wmode':"transparent",
		      'formData':{
		      	  fileName:"",
		    	  id:"",
		    	  xmType:"",
		    	  column01:"",
		    	  column11:"",
		    	  column02:"",
		    	  column12:"",
		    	  column03:"",
		    	  column13:"",
		    	  column04:"",
		    	  column14:"",
		    	  column15:"",
		    	  column05:"",//是否铁通合同
		    	  column16:"",
		    	  column06:"",
		    	  column17:"",
		    	  column07:"",
		    	  column18:"",
		    	  column08:"",
		    	  column19:"",
		    	  column09:"",
		    	  column20:"",
		    	  column21:"",
		    	  column10:"",
		    	  column22:"",
		    	  queueSize:""
		      },
		      //每个文件上传完成后触发
		      'onUploadComplete'  : function(file, response) {
		      	if(response != ""){
		      		$("#id").val(response);
		      	}
		      },
		    //当所有文件上传完成后的操作
		      'onQueueComplete':function(uploads) {
		    	  ev_list();
		    	  //window.location.href ="<%=basePath%>zxmHt/add?id="+$('#id').val();
		      },
		    //当有文件正在上传时的操作
		      'onProgress': function(file,event) {
		
		      },
		    //当添加待上传的文件数量达到设置的上限时的操作
		    /*
		      'onQueueFull': function (event,queueSizeLimit) {
		      		alert("上传的文件数量达到设置的上限");
		          return false;
		      },*/
		    //当取消所有正在上传文件后的操作
		      'onCancel': function(file) {
		      	$("#fileName").val("");
		      } ,
		      'onError': function(error,fileObj) {
		      	alert("Error:" + error);
		      } ,
			  'onAddQueueItem': function(file) {
			    $("#fileName").val(file.name)
			  }
		};		
	</script>

	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<c:if test="${vo.id eq null}">
		<span class="searchCondition_header">新增-项目合同</span>
		</c:if>
		<c:if test="${vo.id ne null}">
		<span class="searchCondition_header">修改-项目合同</span>
		</c:if>		
	</div>
	<form action=""  method="post"  id="form1" enctype="multipart/form-data">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<input type="hidden" name="xmType" id="xmType" value="${vo.xmType}"/><!-- 项目类型 -->
		<input type="hidden"  name="column20" id="column20" value="${vo.column20}"/><!-- 合同来源 -->
		<tr>
		    <th align="right"><b>*</b>子项目编号：</th>
		    <c:set var="zxm" value="${vo.zxm}"/>
		    <input type="hidden" name="column04" id="column04" value="${zxm.id}"/>
		    <td><input name="column04Code"  id="column04Code"  type="text" class="text01" style="width:300px;" placeholder="请填写子项目编号"  value="${zxm.column02}"  onfocus="autoCompletes1();" onchange="this.value='';$('#column04').val('');$('#column04Name').val('')" />
		    <a onclick="ev_selectList()" style="cursor: pointer;color: blue;" title="选择子项目">选择</a></td>
		    <th align="right"><b></b>合同含税金额：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    	<input name="column11"  id="column11"  type="text" class="text01" style="width:300px;" placeholder=""  value="<fmt:formatNumber value="${vo.column11}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>万元
		    </c:if>
		     <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column11" id="column11" value="<fmt:formatNumber value="${vo.column11}" type="currency"  maxFractionDigits="6"/>"/><fmt:formatNumber value="${vo.column11}" type="currency"  maxFractionDigits="6"/>万元</c:if>
		    </td>		    		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>子项目名称：</th>
		    <td><input name="column04Name"  id="column04Name"  type="text" class="text01" style="width:300px;" placeholder="请填写子项目名称"  value="${zxm.column03}" onfocus="autoCompletes1();" onchange="this.value='';$('#column04').val('');$('#column04Code').val('')"  />
		    <a onclick="ev_selectList()" style="cursor: pointer;color: blue;" title="选择子项目">选择</a></td>	    
		    <th align="right"><b></b>合同状态：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column12"  id="column12"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column12}" maxlength="20"/>
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column12" id="column12" value="${vo.column12}"/>${vo.column12}</c:if>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th width="20%" align="right"><b>*</b>合同编号：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column01"  id="column01"  type="text" class="text01" style="width:300px;" placeholder="请填写合同编号"  value="${vo.column01}" onchange="column01Code(this)" maxlength="30"/>
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column01" id="column01" value="${vo.column01}"/>${vo.column01}</c:if>
		    </td>	    
		    <th align="right"><b></b>收支类型：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column13"  id="column13"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column13}" maxlength="20"/>
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column13" id="column13" value="${vo.column13}"/>${vo.column13}</c:if>
		    </td>		    
	    </tr>
	    
	    <tr>
	    	<th align="right"><b>*</b>合同名称：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column03"  id="column03"  type="text" class="text01" style="width:300px;" placeholder="请填写合同名称"  value="${vo.column03}" maxlength="50"/>
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column03" id="column03" value="${vo.column03}"/>${vo.column03}</c:if>
		    </td>
		    <th align="right"><b></b>合同对方：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column14"  id="column14"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column14}" maxlength="20"/>
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column14" id="column14" value="${vo.column14}"/>${vo.column14}</c:if>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>合同流水号：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column02"  id="column02"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column02}" maxlength="30"/>
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column02" id="column02" value="${vo.column02}"/>${vo.column02}</c:if>
		    </td>	    
		    <th align="right"><b></b>预算项目：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column15"  id="column15"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column15}" maxlength="20"/>
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column15" id="column15" value="${vo.column15}"/>${vo.column15}</c:if>
		    </td>		    
	    </tr>
	    
	    
	    <tr>
		    <th align="right"><b></b>是否铁通合同：</th>
		    <td>
		    	<c:if test="${vo.column20 eq '1'}">
		    	<input type="radio" name="column05" value="Y"  ${vo.column05 eq 'Y' ? 'checked="checked"':''} />是
		    	<input type="radio" name="column05" value="N" ${vo.column05 eq 'N' ? 'checked="checked"':''}/>否
		    	</c:if>
		    	<c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column05" id="column05" value="${vo.column05}"/>
		        ${vo.column05 eq 'Y' ? '是':''}
		        ${vo.column05 eq 'N' ? '否':''}		    	
		    	</c:if>
		    </td>
		    <th align="right"><b></b>归档状态：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column16"  id="column16"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column16}" maxlength="20"/>
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column16" id="column16" value="${vo.column16}"/>${vo.column16}</c:if>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>主办公司：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column06"  id="column06"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column06}" maxlength="20"/>
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column06" id="column06" value="${vo.column06}"/>${vo.column06}</c:if>
		    </td>
		    <th align="right"><b></b>签署状态：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column17"  id="column17"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column17}" maxlength="20"/>
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column17" id="column17" value="${vo.column17}"/>${vo.column17}</c:if>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>主办部门：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column07"  id="column07"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column07}" maxlength="50"/>
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column07" id="column07" value="${vo.column07}"/>${vo.column07}</c:if>
		    </td>
		    <th align="right"><b></b>扫描状态：</th>
		    <td id="rulesGrade_td">
		    	<c:if test="${vo.column20 eq '1'}">
		    	<select class="ui-select" id="column18"  name="column18" style="width:306px;">
		    			<option value="">请选择</option>
						<option ${vo.column18 eq 'Y'  ? 'selected="selected"':''} value="Y">是</option>
						<option ${vo.column18 eq 'N' ? 'selected="selected"':''} value="N">否</option>
		        </select>
		        </c:if>
		        <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column18" id="column18" value="${vo.column18}"/>
		        ${vo.column18 eq 'Y' ? '是':''}
		        ${vo.column18 eq 'N' ? '否':''}		        
		        </c:if>
		    </td>		    
	    </tr>	    
	    
	    <tr>
		    <th align="right"><b></b>主办人：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column08"  id="column08"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column08}" maxlength="10"/>
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column08" id="column08" value="${vo.column08}"/>${vo.column08}</c:if>
		    </td>
		    <th align="right"><b></b>合同签订日期：</th>
		    <td>
		    	<c:if test="${vo.column20 eq '1'}">
		    	<input name="column19"  id="column19"  type="text" class="text01" style="width:300px;" placeholder=""  
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column19}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"/>
		    	</c:if>
		    	<c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column19" id="column19" value="${vo.column19}"/><fmt:formatDate value="${vo.column19}" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>
		    </td>		    
	    </tr>	    
	    
	    <tr>
		    <th align="right"><b>*</b>合同不含税金额：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column09"  id="column09"  type="text" class="text01" style="width:300px;" placeholder="请填写合同不含税金额"  value="<fmt:formatNumber value="${vo.column09}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>万元
		    </c:if>
		     <c:if test="${vo.column20 eq '2'}">
		     <input type="hidden" name="column09" id="column09" value="<fmt:formatNumber value="${vo.column09}" type="currency"  maxFractionDigits="6"/>"/><fmt:formatNumber value="${vo.column09}" type="currency"  maxFractionDigits="6"/>万元
		     </c:if>		    
		    </td>
		    <th align="right"><b></b>合同类型：</th>
		    <td>
		    	<select class="ui-select" id="column22"  name="column22" style="width:306px;" onchange="if(this.value==1){$('#_column23').hide();$('#column23').val('');}else{$('#_column23').show();}">
						<option ${vo.column22 eq '1' ? 'selected="selected"':''} value="1">费用类</option>
						<option ${vo.column22 eq '2' ? 'selected="selected"':''} value="2">订单类</option>
		        </select>
		    </td>		    
	    </tr>
	    
	    <tr id="_column23" style="display: ${(vo.column22 ne '' && vo.column22 eq '2') ? 'block':'none'};">
		    <th align="right"><b></b>&nbsp;</th>
		    <td>&nbsp;</td>	    
		    <th align="right"><b></b>订单号：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column23"  id="column23"  type="text" class="text01" style="width:300px;" placeholder="请填写订单号"  value="${vo.column23}" maxlength="50"/>
		    </c:if>
		     <c:if test="${vo.column20 eq '2'}">
		     <input type="hidden1" name="column23" id="column23" value="${vo.column23}"/>${vo.column23}
		     </c:if>	    
		    </td>
	    </tr>	    
	    
	    <tr>
		    <th align="right"><b></b>合同税金：</th>
		    <td>
		    <c:if test="${vo.column20 eq '1'}">
		    <input name="column10"  id="column10"  type="text" class="text01" style="width:300px;" placeholder=""  value="<fmt:formatNumber value="${vo.column10}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>万元
		    </c:if>
		    <c:if test="${vo.column20 eq '2'}"><input type="hidden" name="column10" id="column10" value="<fmt:formatNumber value="${vo.column10}" type="currency"  maxFractionDigits="6"/>"/><fmt:formatNumber value="${vo.column10}" type="currency"  maxFractionDigits="6"/>万元</c:if>
		    </td>
		    <th align="right"><b>*</b>项目合同管理员：</th>
		    <td>
		    	<input name="column21"  id="column21"  type="text" class="text01" style="width:200px;" placeholder="请填写项目合同管理员"  onfocus="autoCompletes(this);" onblur="this.value=''"/>
				<span id="selected_column21">
					<c:forEach items="${vo.list21}" var="item" varStatus="i">
						<a href="javascript:void(0);" id="${item.userId}">
							<input type="hidden" id="column21Id" name="column21Id" value="${item.userId}" />
							<span style="padding: 0 3px 0 0;">${item.userName }</span>
							<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10">
						</a>
					</c:forEach>
				</span>		    	
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>附件：</th>
		    <td>
		    	<input type="hidden"  name="queueSize" id="queueSize"  value="1"/>
			    <div class="text01" style="width:500px;${vo.column20 eq '2'?'display:none':''}" id="fi_box_div_1" value="1" >
			    	<input type="hidden"  name="fileName" id="fileName" />
			    	<input type="file" name="file"  id="uploadFiles1"  style="display:none"/>
			    </div>		    
		    </td>
		    <th align="right"><b></b>合同来源：</th>
		    <td>
		        	${vo.column20 eq '1' ? '新增':''}
		        	${vo.column20 eq '2' ? '合同管理系统':''}	
		    </td>
	    </tr>
	    
	    <c:forEach items="${vo.htAttach}" var="vo1" varStatus="i">
	    <tr>
		    <th align="right"><b></b></th>
		    <td colspan="3">${i.count}、
			    		<a id="file_download" style="color: blue;" href="javascript:ev_download('${vo1.id}')">${vo1.column01} (<fmt:formatNumber value="${vo1.column02/1024}" pattern="#"/>KB)</a>
			    		<c:if test="${vo.column20 eq '1'}"><img onclick="javascript:ev_delete('${vo1.id}')" src="/SRMC/dc/images/close_icon01.png" width="17" height="17" style="cursor:pointer;" /></c:if>
			    		<input type="hidden"  name="attachIds" id="attachIds"  value="${vo1.id}"/>
		    </td>
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
	
	$("#uploadFiles1").uploadifive(config);//页面上传文件初始化
});

function ev_list(){
window.location.href='<%=basePath%>zxmHt/list';
}

/**
 * 必填项
 */
function ev_required_item(){
		var k = 0;
		var b = true;
  		//子项目编号
		var column = $("#column04Code");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}  		
  		//子项目名称
		var column = $("#column04Name");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		} 
  		//合同编号
		var column = $("#column01");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}  		
  		//合同名称
		var column = $("#column03");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}  
		  		
       	//合同不含税金额
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
 
 		//项目合同管理员
		var column10 = $("#column21");
		if($.trim($("#selected_column21").html()) == ""){
           		k++;bindC(column10);			
		}     	
      	
      	if(k > 0){
      		alert("*为必填字段");
      		b = false;
      	}
      	
      	$("#column10").val($("#column10").val().replace("￥","").replace(new RegExp(",","g"),""));
      	$("#column11").val($("#column11").val().replace("￥","").replace(new RegExp(",","g"),""));
      	
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
	if(ev_required_item()){
		if($("#fileName").val() != ""){
			var column05 = "";
			if($('input:radio[name="column05"]').is(":checked")){
			    column05 = $('input:radio[name="column05"]:checked').val();
			}
			var column21Ids = "";
			$("input[id='column21Id']").each(function(){ 
			   column21Ids += ","+$(this).val();
			});	
    	  	var queueSize = config.queueSizeLimit;
    	  	if(queueSize > 1){$("#queueSize").val(queueSize);}
    	  	$("#uploadFiles1").data('uploadifive').settings.formData = {			
    	  			"fileName":$("#fileName").val(),
					"id":$("#id").val(),
					"xmType":$("#xmType").val(),
					"column01":$("#column01").val(),
					"column11":$("#column11").val(),
					"column02":$("#column02").val(),
					"column12":$("#column12").val(),
					"column03":$("#column03").val(),
					"column13":$("#column13").val(),
					"column04":$("#column04").val(),
					"column14":$("#column14").val(),
					"column15":$("#column15").val(),
					"column05":column05,//是否铁通合同
					"column16":$("#column16").val(),
					"column06":$("#column06").val(),
					"column17":$("#column17").val(),
					"column07":$("#column07").val(),
					"column18":$("#column18").val(),
					"column08":$("#column08").val(),
					"column19":$("#column19").val(),
					"column09":$("#column09").val(),
					"column20":$("#column20").val(),
					"column21Ids":column21Ids,
					"column10":$("#column10").val(),
					"column22":$("#column22").val(),
					"queueSize":$("#queueSize").val()
    	  	};
    	  	$("#uploadFiles1").uploadifive('upload');
    	  }else{
				document.forms[0].action="<%=basePath%>zxmHt/saveOrUpdate";
				document.forms[0].submit();    	  
    	  }
	}
}

//自动匹配子项目编号
function autoCompletes1(){
		//子项目编号
		jQuery("#column04Code").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>zxm/searchZXM",
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
					jQuery("#xmType").val(ui.item.type);
					return false;
			}
		});
		
		//子项目名称
		jQuery("#column04Name").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>zxm/searchZXM",
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
					jQuery("#column04").val(ui.item.id);
					jQuery("#column04Code").val(ui.item.code);
					jQuery("#column04Name").val(ui.item.name);
					jQuery("#xmType").val(ui.item.type);
					return false;
			}
		});		
}

function ev_selectList(){
	//判断是不是IE浏览器
	if(isIE()){
		var returnValue = window.showModalDialog("<%=basePath%>zxm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
		putValue(returnValue);
	}else{
		//其他浏览器
		window.open("<%=basePath%>zxm/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px");
	}
}
//给父窗口元素赋值
function putValue(returnValue){
		var v = returnValue.split("_");
		jQuery("#column04").val(v[0]);
		jQuery("#column04Code").val(v[1]);
		jQuery("#column04Name").val(v[2]);
		jQuery("#xmType").val(v[3]);
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

function ev_delete(id){
		if(confirm("确定删除附件？")){
			$.ajax({
				type 	: "post",
				url 	: "<%=basePath%>zxmHt/deleteFile?id="+id,
				async	: true,
				error 	: function() {
					alert("删除失败");
				},
				success : function(data, textStatus) {
					if(data != "" && data == "s"){
						alert("删除成功");
						window.location.href ="<%=basePath%>zxmHt/add?id="+$('#id').val();
					}
					else if(data != "" && data == "e"){
						alert("删除失败");
					}
				},
				dataType : "text"
			});	
		}
}

function ev_download(id){
	window.location.href = "<%=basePath%>zxmHt/downFile?key="+Math.random()+"&id="+id;
}

//合同编码唯一性验证
function column01Code(obj){
			$.ajax({
				type:"POST",
				async:false,
				url: "<%=basePath%>zxmHt/searchCode",
				data:"column01="+obj.value,
				dataType:"text",
				success:function(result){
					if(result != "" && parseInt(result) >= 1){
						$(obj).css("border-color","red");
						alert("合同编号："+obj.value+"已经存在，请重新输入");
						$(obj).val("");
					}else{
						$(obj).css("border-color","");
					}
				},
				error:function(){}
			});
}
</script>