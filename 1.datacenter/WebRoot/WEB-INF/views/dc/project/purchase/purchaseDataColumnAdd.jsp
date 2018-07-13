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
	<!--文件上传样式，js -->
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script>
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<span class="searchCondition_header">采购项目数据列配置</span>
	</div>
	<form name="form" id="pageForm" method="post"  >
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
  	<tr>
  		<th width="9%" align="right"><b>*</b>字段名：</th>
	    <td width="30%">
	    		   <input id="Column_cname" name="Column_cname"  maxlength="30" type="text" class="text01" style="width:200px;"  />  	 
		</td> 
  		
	</tr>
	<tr>
  		<th width="9%" align="right"><b>*</b>字段类型：</th>
	    <td width="30%">
             <select class="ui-select" id="Column_type" name="Column_type" style="width:200px;">
						<option value="">请选择</option>
						<option value="1">文本</option>
						<option value="2">时间</option>
	         </select>		
	    </td>   		
	</tr>
    <tr>
  		<th width="9%" align="right"><b>*</b>文本长度：</th>
	    <td width="30%">        
	    	<input id="Column_length" name="Column_length"   type="text"   class="text01" style="width:200px;"  />  	 		         		
	    </td>   		
	</tr>	
			  	
	 <!--   ============== -->
	 <tr>
	   		<th colspan="8" align="center" height="50">
	   			<input name="" type="button" class="btn_common02" id="save" onclick="ev_save()" value="保 存" />
	   			<input name="" type="button" class="btn_common02" onclick="javascript:window.location.href='<%=basePath%>purchase/dataColunmList';" value="取 消" />
	   		</th>
	   </tr>  
	</table>
	</form>
</div>
</body>
</html>

<script type="text/javascript">
$(document).ready(function(){
 $('.ui-select').ui_select();
 var operatResult = '${operatResult}';
 if(operatResult=='1'){
    alert("操作成功");
 }
 if(operatResult=='0'){
  alert("操作失败");
 }
 
  $("#Column_type").on('change',function(){
 	  var Columntype = $('#Column_type').val();
 	  if(Columntype==''||Columntype=='1') {
 	     $("#Column_length").attr("disabled", false);
 	  }else{
 	     $("#Column_length").val("");
 	     $("#Column_length").attr("disabled", true);
 	  }

  });
});



function ev_save(){
 var Columncname = $('#Column_cname').val();
 var Columntype = $('#Column_type').val();
 var Columnlength = $('#Column_length').val();
  if(Columncname==''){
    alert("请输入字段名");
    return;
  }
  if(Columntype==''){
    alert("请选择字段类型");
    return;
  }
  
  if(Columntype=='1'){
     if(isNaN(Columnlength)){
        alert("非数字");
        return;
     }else{
        if(Columnlength>2000){
           alert("最大值为2000");
           return; 
        }
        if(Columnlength==0||Columnlength==""){
           alert("值不能为0和空");
           return; 
        }
     }
       
  }
  
document.forms[0].action="<%=basePath%>purchase/submitPurchaseColumn";
document.forms[0].submit(); 
}

</script>