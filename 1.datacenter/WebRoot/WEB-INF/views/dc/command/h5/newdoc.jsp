<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>
<title>接收新归档</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
<link href="/SRMC/dc/command/css/css.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/command/css/main.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/command/css/style.css" rel="stylesheet" type="text/css" />
</style>
<script type="text/javascript" src="/SRMC/dc/command/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/command/js/Popup.js"></script>

<!-- 	表单异步提交 -->
<script src="/SRMC/rmpb/js/jquery.form.js"></script>
</head>

<body style="background-color:#eeeeee;">
<ul class="top">
	<li class="top_li"><a href="javascript:window.history.back()">&nbsp;</a></li>
	<li class="cen">搜索指令</li>
</ul>
<div class="top_false"></div>
<form action=""  method="post"  id="form1">
<input type="hidden" value="N" name="isPages" id="isPages"/>
<input type="hidden" value="${command.pageIndex}" id="pageIndex"	name="pageIndex"/>
   <input type="hidden" value="${command.pageSize}" id="pageSize"	name="pageSize"/>	
<ul class="data_A">
	<li class="data1">
		<span>指令编号：</span>
		<input type="text" id="commandNum" name="commandNum"  value="${command.commandNum }" placeholder="请输入指令编号" maxlength="100">
	</li>
</ul>
</form>
<div class="but_A">
	<button type="button" style="width: 48%" id="b" onclick="ev_search()" class="but1">搜索</button>
	<button type="button" style="width: 48%" onclick="cancel()" class="but2">取消</button>
</div>
<ul class="data_A">
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
			<li class="data1" style="padding-top: 6px;">
    			<a href="<%=basePath%>command/h5/receiveDoc?id=${vo[0].commandId}">${vo[0].commandNum}</a>
			</li>
	   </c:forEach>
</ul>

<!---弹窗开始-->
<div id="goodcover_div" class="goodcover"></div>
<div id="code_div" class="code">
  <div class="goodtxt">
  	<img src="/SRMC/dc/command/images/icon_06.png" class="img_1">
	<p class="p1" id="msg_p">文件上传失败！</p>
  </div>
<div class="close1"><a href="javascript:void(0)" class="closebt">我知道了</a></div>
</div>
<!---弹窗结束-->
<!--遮罩层-->
<div id="areaMask" class="mask" style="display: none;"></div>

<!---弹窗开始-->
<div id="goodcover_dialog" class="goodcover"></div>
<div id="code_dialog" class="code">
	  <div class="goodtxt">
		  <img src="/SRMC/dc/command/images/icon_09.png" class="img_2">
		<p class="p2">文件已成功归档</p>
	  </div>
	<div class="close1"><a href="javascript:void(0)" onclick="$('.code').hide();$('.goodcover').hide();" class="a_1" style="color:#76cdff;">确定</a></div>
</div>
<!---弹窗结束-->
<!-- loading开始 -->
<div id="goodcover_loading" class="goodcover"></div>
<div id="code_loading" class="code">
	<div class="wait">
		<img src="/SRMC/dc/command/images/icon_03.gif">
		<p>正在上传，请稍后......</p>
	</div>
</div>
<!-- loading结束 -->
</body>
<script type="text/javascript">
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>command/h5/receiveNewDoc?id=${id}";
	document.forms[0].submit();
}
var cancheck = true;
function searchDoc(){
   	if(!cancheck){
   		return false;
   	}
   	var commandNum=$("#commandNum").val();
	if(commandNum==null||$.trim(commandNum)==""){
		$("#b").html("请输入指令编号");
		setTimeout(function(){
			$("#b").html("搜索");
		},"1000");
		return;
	}
	
	cancheck = false;
	
	//制度相关文档保存	
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>command/h5/searchDoc",
        async:true,
        dataType:'json',
        type:'POST',
        success:function (result) {
        	if(result.result){
        		cancheck = false;
        		window.location.href='<%=basePath%>command/h5/receiveDoc?id='+result.message;
        	}else{
        		cancheck = true;
        		alertMsg("该指令编号不存在！");
        	}
        },
        error:function () {
        	cancheck = true;
			$("#b").html("系统异常，请稍后再试！");
			setTimeout(function(){
				$("#b").html("搜索");
			},"1000");
        }
    });
}
function cancel(){
	window.location.href='<%=basePath%>command/h5/receiveDoc?id=${id}';
}

</script>
</html>
