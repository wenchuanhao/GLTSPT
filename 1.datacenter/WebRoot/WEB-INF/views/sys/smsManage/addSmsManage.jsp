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
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
	<!--公共JS -->
	<script type="text/javascript" >var paths= "<%=basePath%>";var basePath = "<%=basePath%>sys/dict";var isCcFlag = "0";</script>
	<script src="/SRMC/dc/smsManage/js/common.js"></script>
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
</head>

<body>
<script type="text/javascript">
	function selectSysManage(){
		    jQuery("#sysManages").attr("href", "<%=basePath%>sys/sms/selectSmsManage" );
		    jQuery("#sysManages").fancybox({
	        'width'				: '65%',
	        'height'			: 350,
	        'scrolling'			:'yes',
	        //'centerOnScroll'	: true,
	        'autoScale'			: false,
	        'transitionIn'		: 'none',
	        'transitionOut'		: 'none',
	        'type'				: 'iframe'
	   	     });
	    }
	    
	    function doCloseSubpage(){
    		jQuery.fancybox.close();
		}
		
		function getSysManage(){
		return ;
		    var totalList = "";
		    var url = "<%=basePath%>sys/app/queryAjaxAppManageById";
		   
		    var params = {
		        id:jQuery("#sysId").val()
		    };
		    jQuery.post(url, params, function(data){
		        totalList = data;
		        var brandList = totalList;
		        var option = '<option value="">请选择</option>';
		        for(var i=0; i<brandList.length; i++){
		            option += '<option value="' + brandList[i] + '">' + brandList[i] + '</option>';
		        }
		        jQuery("#type").html(option);
		    } , 'json');
		}
</script>
<div class="gl_import_m">
	<div class="searchCondition">
		<span class="searchCondition_header">短信组信息</span>
	</div>
	<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
	<input type="hidden" name="nameId" id="nameId_input" value="${form.nameId}"/>
	<input type="hidden" class="myClass" name="pId" id="pId"/>
		<tr>
		    <th width="16%" align="right"><b>*</b>业务名称:</th>
		    <td><a id="sysManages"><input name="pName" id="pName"  readonly="readonly" onclick="selectSysManage();"  type="text" class="text01" style="width:400px;"/></a></td>
	   	</tr>
		<tr>
		    <th width="16%" align="right"><b>*</b>短信组名称:</th>
		    <td><input name="smsGroupNmae" id="smsGroupNmae"  type="text" class="text01" style="width:400px;"/></td>
	   	</tr>
		<tr>
		    <th width="16%" align="right"><b>*</b>发送内容:</th>
		    <td><input name="content" id="contentText"  type="text" class="text01" style="width:400px;"/></td>
	   	</tr>
	   	<tr>
		    <th width="16%" align="right">描述:</th>
		    <td>
		    	<textarea class="text01" name="detail" id="detail" style="width:400px;"></textarea>
		    </td>
	   	</tr>
	   	<tr>
		     
	   	</tr>
	   	
  </table>
	
  	<div class="tabpages">
    	<ul class="l">
      		<li class="current">短信发送人员</li>
    	</ul>
    	<div class="otherButtons r">
			<input name="" onclick="addParameterLine()"  type="button" class="btn_common01" value="添加一行" />
			<input name="" onclick="delParameterLine()" type="button" class="btn_common01" value="删除多行" />
		</div>
  	</div>
  <table id="parameter_tb" width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  	<th><input id="checkAll" type="checkbox"></th>
	    <th>手机号码</th>
	    <th>姓名</th>
	    <th>备注</th>
	  </tr>
	  	
	</table>
	</form>
	<table width="99%" id="table03" border="0" cellspacing="0" cellpadding="0" class="editTable" align="center" style="border:0px;">
		<tr>
	   		<th colspan="2" align="center" height="50">
	   			<input name="" type="button" class="btn_common02" id="submit" onclick="ev_submit()" value="提 交" /> 
	   		 	<input name="" type="button" class="btn_common01" onclick="javascript:window.history.back();" value="取 消" />
	   		</th>
	   	</tr>   
	</table>
</div>
</body>
</html>

<script type="text/javascript">
var indexOfLine = 0;
function ev_submit(){
	if(!checkSubmit()){
		return;
	}
	
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>sys/sms/addSmsManages",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result == "1"){
	        	window.location.href="<%=basePath%>sys/sms/querySms";
        	}
        },
        error:function () {
        	alert("提交失败");
        }
    });
}

</script>