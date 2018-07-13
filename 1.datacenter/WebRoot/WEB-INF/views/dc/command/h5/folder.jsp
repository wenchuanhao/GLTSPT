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
<title>提交归档</title>
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
	<li class="top_li"><a href="#">&nbsp;</a></li>
	<li class="cen">提交归档</li>
</ul>
<div class="top_false"></div>
<form action=""  method="post"  id="form1" enctype="multipart/form-data">
<ul class="submit" id="submit_ul">
	<li>
		<label class="c_0085D0" for="uploadID"><img src="/SRMC/dc/command/images/icon_05.png"></label>
	    <input name="file" id="uploadID" style="position: absolute;left: -10000px;" onchange="upload()" type="file" >
	</li>
	<div style="clear:both"></div>
</ul>
<ul class="data_A">
	<input type="hidden" name="folderId" id="folderId_input" value="${vo.folderId}"/>
   	<input type="hidden" name="fileTempId" id="fileTempId" value="${vo.folderId}"/>
   	<input type="hidden" name="commandInfoid" id="commandInfoid_input" value="${vo.commandInfoid}"/>
   	<input type="hidden" name="isParent" value="0"/>
   	<input type="hidden" name="type" value="12"/>
   	<input type="hidden" name="busType" value="ZL"/>
   	<input type="hidden" name="status" value="1"/>
	<li class="data1">
		<span>存放位置：</span>
		<input type="text" id="folderPosition" name="folderPosition" placeholder="请输入存放位置" maxlength="100">
	</li>
</ul>
</form>
<div class="but_A">
	<c:choose>
	    <c:when test="${cdinfo.commandStatus eq 1 || cdinfo.commandStatus eq 2 || cdinfo.commandStatus eq 5 }">
			<button type="button" id="b" onclick="savefolder()" class="but1">归档</button>
	    </c:when>
	    <c:when test="${cdinfo.commandStatus eq 3}">
	    	<button type="button" class="but1">文档已归档</button>
	    </c:when>
	    <c:when test="${cdinfo.commandStatus eq 4}">
	    	<button type="button" class="but1">文档已作废</button>
	    </c:when>
	</c:choose>
	<button type="button" onclick="cancel()" class="but2">取消</button>
</div>

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
	<div class="close1"><a href="javascript:void(0)" onclick="window.location.href='<%=basePath%>command/h5/receiveDoc?id=${id}'" class="a_1" style="color:#76cdff;">确定</a></div>
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

var cancheck = true;
function savefolder(){
   	if(!cancheck){
   		return false;
   	}
   	if($("#submit_ul").find('.img_li').length <= 0){
   		$("#b").html("请至少上传一个扫描件");
		setTimeout(function(){
			$("#b").html("归档");
		},"1000");
		return;
   	}
   	
   	var folderPosition=$("#folderPosition").val();
	if(folderPosition==null||folderPosition==""){
		$("#b").html("请输入存放位置");
		setTimeout(function(){
			$("#b").html("归档");
		},"1000");
		return;
	}
	
	cancheck = false;
	
	//制度相关文档保存	
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>command/setFolder",
        async:true,
        type:'POST',
        success:function (result) {
        	if(result == 1){
        		cancheck = false;
        		alertDialog();
        	}else{
        		cancheck = true;
        		alertMsg("提交失败");
        	}
        },
        error:function () {
        	cancheck = true;
			$("#b").html("系统异常，请稍后再试！");
			setTimeout(function(){
				$("#b").html("归档");
			},"1000");
        }
    });
}
function cancel(){
 	var folderPosition=$("#folderPosition").val();
 	if($("#submit_ul").find('.img_li').length <= 0 && (folderPosition==null||folderPosition=="")){
 		window.location.href='<%=basePath%>command/h5/receiveDoc?id=${id}';
 	}else{
		if(confirm("确定放弃编辑内容，离开提交归档页面？")){
			window.location.href='<%=basePath%>command/h5/receiveDoc?id=${id}';
		}
 	}
}

function logout(){
	window.location.href='<%=basePath%>command/h5/logout?id=${id}';
}

function upload(){
	//弹出loading
	alertLoading();
	setTimeout(function(){
		$("#form1").ajaxSubmit({
			url:'<%=basePath%>rulesController/uploadFile',
			dataType:'text',
			async:true,
			type:'post',
			success:function(result){
				colseLoading();
				if(result == "1"){
					queryFileList();
				}else if( result == "3"){
					alertMsg('文件（指令）扫描件限制为图片！');
				}else{
					alertMsg('文件上传失败！');
				}
			},
			error:function(){
				colseLoading();
				alertMsg('文件上传失败！');
			}
		});
	},"500");
}


/**
 * 查询文件列表
 * @param target
 */
function queryFileList(){
  //当每个文件上传完成后的操作
  var fileTempId = jQuery("#fileTempId").val();
  var url = "<%=basePath%>rulesController/queryFileList";
  var params = {
  		'fileTempId':fileTempId,
  		'type':'12'
  };
  jQuery.post(url, params, function(data){
      var temp = '';
      $.each(data,function(k,v){
      		temp += '<li class="img_li"><img src="<%=basePath%>rulesController/showImage?fileId='+v.fileId+'"></li>';
      });
      $("#submit_ul").find('.img_li').remove();
      $("#submit_ul").prepend(temp);
  } , 'json');

}
</script>
</html>
