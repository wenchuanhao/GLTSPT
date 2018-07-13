<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>通知超时</title>
		<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
</head>
<body>
<!-- 遮罩内容开始 -->
<div class="taskCreatBox">
            <div class="box_title" style="color:#4084b6;">
               	 通知整改超时<span><a href="#none"></a></span>
            </div>
            <div class="box_task">
            <table cellpadding="0" cellspacing="15" class="taskCreate" width="100%">   
            	<tr>
	               <th width="40%">通知方式：</th>
	               <td><span><input type="checkbox" name="checkbox" value="1"> 短信</span><span><input type="checkbox" name="checkbox" value="0"> 邮件</span></td>
	            </tr>
	            <tr>
	               <td colspan="2" align="center">
	               <input name="" type="button" onclick="outTime('${orderId}')" class="btn_common02" value="通知整改超时" />  </td>
                </tr>
            </table>
          </div>
        </div>
<!-- 遮罩内容结束 -->
<script >
	
	function outTime(orderId){
		var str="";
        $("input[name='checkbox']:checked").each(function(){  
            if(str==""){
            	str+=""+$(this).val()+"";
            }else{
            	str+=","+$(this).val()+"";
            }		
        });
      
        var arry = str.split(",");
        if(str.length==0){
        	alert("请选择一个单据通知方式！");
        	return false; 
        }
        
		jQuery.ajax({
		type:"POST",
		async:false,
		url:"<%=basePath%>account/noticeOutTimeDo",
		data:{orderId:orderId
		,noticType:str},
		success:function(data){
			if(data=="1"){
				parent.window.location.reload();
			}
		},
		error:function(){
            alert("网络异常！");
        }
	});
	}
</script>
</body>
</html>