<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报账审核</title>
		<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
		<link href="/SRMC/rmpb/css/date_input.css" type="text/css" rel="stylesheet" />
		<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
		<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="gl_import_m">
<div class="searchCondition"><span class="searchCondition_header">提交</span></div>
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="readOnlyTable">
	  <form action="" method="post" id="submitForm">
	  <input type="hidden" name="orderId" id="orderId" value="${problemID }"/>
	  <tr>
	    <th width="25%"><em>*</em>整改结束时间</th>
	    <td><div class="date l" style="width:160px;">
	    	<input type="hidden" id="handelBeginDate" name="handelBeginDate" value="${createTime }">
		    <input   type="text"  name="endTime" id="endTime" value="<fmt:formatDate value="${info.reachSement}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
		    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'handelBeginDate\')||\'%y-%M-%d\'}',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  placeholder="请您输入"  class="text02 l" style="width:90%;" />
	    	<i></i>
	    </div>
	    </td>
	  </tr>
	  <tr>
	      <th>通过/不通过原因</th>
	      <td><textarea cols="" name="nopassReason" id="nopassReason" rows="" class="textArea01" style="width:370px;" value="如果整改不通过，则必需填写不通过的原因，2000个汉字"  ></textarea></td>
	  </tr>
	  <tr>
          <th>不通过通知方式</th>
          <td><span><input type="checkbox" name="remindWay" id="remindWay" value="1"> 短信</span><span><input type="checkbox" name="remindWay" id="remindWay" value="0"> 邮件</span></td>
       </tr>
	   </form>
  </table>
  <div class="buttons" >
	  <input name="" onclick="approve('tg')" type="button" class="btn_common02" value="整改通过" /> 
	  <input name="" type="button" onclick="approve('td')" class="btn_common03" value="整改不通过退单" /> 
	  <input name="" type="button" onclick="approve('btg')" class="btn_common01" value="整改不通过" />
  </div> 
</div>
</body>

<script type="text/javascript">
	
	//审核通过、不通过、退单
	function approve(app){
		if(jQuery.trim(jQuery("#endTime").val()) == ""){
			alert("请选择整改结束时间");
			return;
		}
		//不通过必须填写原因
		if(app!="tg"){
			if(jQuery.trim(jQuery("#nopassReason").val()) == ""){
				alert("请输入不通过原因");
				return;
			}
			if(jQuery.trim(jQuery("#nopassReason").val()).length>2000){
				alert("如果整改不通过，则必需填写不通过的原因，限2000个汉字");
				return ;
			}
			
			var str="";
	        $("input[name='remindWay']:checked").each(function(){  
	            if(str==""){
	            	str+=""+$(this).val()+"";
	            }else{
	            	str+=","+$(this).val()+"";
	            }		
	        });
	      
	        var arry = str.split(",");
	        if(str.length==0){
	        	alert("请至少选择一个通知方式！");
	        	return false; 
	        }
		}
		
		$.ajax({
	       	url:"<%=basePath%>account/ajaxApprove?approve="+app,
	    	dataType:'json',
	        async:true,
	        type:'POST',
	        data:$('#submitForm').serialize(),
	        success:function (result) {
	        	if(result == "1"){
	        		alert("操作成功");	
		        	parent.window.location.reload();
	        	}if(result=="2"){
	        		alert("操作成功");
	        		history.go(-1);
	        	}if(result=="3"){
	        		/* window.history.go(-1);
	        		window.location.reload; */
	        		parent.test();
	        	}else if(result == "0"){
	        		return;
	        	}
	        },
	        error:function () {
	        	alert("网络异常，请联系管理员");
	        }
   	 });
	}
</script>
</html>