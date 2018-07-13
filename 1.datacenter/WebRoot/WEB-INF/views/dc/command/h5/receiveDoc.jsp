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
<title>接收文档页面</title>
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
<jsp:include page="../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="COMMAND_STATUS,COMMAND_TYPE"></jsp:param>
</jsp:include>
</head>
<script type="text/javascript">
	var province = $.parseJSON('${roles}'.replace("\r\n", "\\r\\n"));
</script>

<body style="background-color:#eeeeee;">
<div class="data_fill">
	<ul class="top">
		<li class="top_li"><a href="#">&nbsp;</a></li>
		<li class="cen">接收文档信息</li>
	</ul>
	<div class="top_false"> </div>
	<div class="top3">
  		<img src="/SRMC/dc/command/images/icon_08.png"> 
  	</div>
  	<form action=""  method="post"  id="form1">
  		<input type="hidden" name="flowRoleid" id="flowRoleid" />
  		<input type="hidden" name="flowRolename" id="flowRolename" />
  	</form>
	<ul class="data_A">
		<li class="data1">
			<div class="browser">
		        <!--选择地区-->
		        <section class="express-area">
		            <a id="expressArea" href="javascript:void(0)">
		                <dl>
		                    <dt>接收单位：</dt>
		                    <dd>请选择接收单位<img src="/SRMC/dc/command/images/icon_07.png"></dd>
		                </dl>
		            </a>
		        </section>
		        <!--选择地区弹层-->
		        <section id="areaLayer" class="express-area-box" style="bottom: -100%;">
		            <header>
		                <h3>请选择接收单位</h3>
		                <a id="backUp" class="back" href="javascript:void(0)" title="返回" style="display: none;"></a>
		                <a id="closeArea" class="close" href="javascript:void(0)" title="关闭"></a>
		            </header>
		            <article id="areaBox">
		                <ul id="areaList" class="area-list">
		                </ul>
		            </article>
		        </section>
		        <!--遮罩层-->
		        <script src="/SRMC/dc/command/js/jquery.area.js"></script>
		    </div>
		</li>
	</ul>
	<ul class="data_A">
		<li class="data1">
			<span>文件类型：</span>
			<span>
				<jsp:include page="../../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="COMMAND_TYPE"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.commandType}"></jsp:param>
			    </jsp:include>
			</span>
		</li>
		<li class="data1">
			<span>文件编号：</span>
			<span title="${vo.commandNum }">
				<c:choose> 
				     <c:when test="${fn:length(vo.commandNum) > 15}"> 
				      	<c:out value="${fn:substring(vo.commandNum, 0, 15)}......" /> 
				     </c:when> 
				     <c:otherwise> 
				      	<c:out value="${vo.commandNum}" /> 
				     </c:otherwise>
			    </c:choose>
			</span>
		</li>
		<li class="data1">
			<span>合同名称：</span>
			<span title="${vo.contractName}">
				<c:choose> 
				     <c:when test="${fn:length(vo.contractName) > 10}"> 
				      	<c:out value="${fn:substring(vo.contractName, 0, 10)}......" /> 
				     </c:when> 
				     <c:otherwise> 
				      	<c:out value="${vo.contractName}" /> 
				     </c:otherwise>
			    </c:choose>
			</span>
		</li>
		<li class="data1">
			<span>发<span style="font-size: 1.2rem">&nbsp;&nbsp;</span>起<span style="font-size: 1.2rem">&nbsp;&nbsp;</span>人：</span>
			<span>${vo.launchUsername }</span>
		</li>
		<li class="data1 border_no">
			<span>发起时间：</span>
			<span><fmt:formatDate value="${vo.launchTime}" pattern="yyyy年MM月dd日"/></span>
		</li>
	</ul>
	<div class="but_A">
		<c:if test="${(not empty COMMAND_ADMIN and COMMAND_ADMIN eq '1') and (vo.commandStatus eq 2 || vo.commandStatus eq 5)}">
			<button type="button" onclick="folder()" class="but1" id="setFloder">提交归档</button>
		</c:if>
		<c:choose>
		    <c:when test="${vo.commandStatus eq 1 || vo.commandStatus eq 2 || vo.commandStatus eq 5 }">
				<button type="button" onclick="receive()" class="but1" id="b">接收文档</button>
		    </c:when>
		    <c:when test="${vo.commandStatus eq 3}">
		    	<button type="button" class="but1">文档已归档</button>
		    </c:when>
		    <c:when test="${vo.commandStatus eq 4}">
		    	<button type="button" class="but1">文档已作废</button>
		    </c:when>
		</c:choose>
		<button type="button" onclick="logout()" class="but2">退出</button>
	</div>
</div>
<!---弹窗开始-->
<div id="goodcover_div" class="goodcover"></div>
	<div id="code_div" class="code">
	  <div class="goodtxt">
		  <img src="/SRMC/dc/command/images/icon_09.png" class="img_2">
		<p class="p2">文件已成功接收</p>
	  </div>
	<div class="close1">
		<a href="javascript:void(0)" onclick="$('.code').hide();$('.goodcover').hide();" class="a_1" style="color:#76cdff;">确定</a>
	</div>
</div>
<!---弹窗结束-->
</body>
<script type="text/javascript">
var cancheck = true;
function receive(){
   	if(!cancheck){
   		return false;
   	}
   	var flowRoleid=$("#flowRoleid").val();
   	var flowRolename=$("#flowRolename").val();
	if(flowRoleid==null||flowRoleid==""){
		$("#b").html("请选择接收单位");
		setTimeout(function(){
			$("#b").html("接收文档");
		},"1000");
		return;
	}
	cancheck = false;
	$.ajax({
		url:'<%=basePath%>command/h5/receive',
		data:{"flowRoleid":flowRoleid,"flowRolename":flowRolename,"id":"${id}"},
		dataType:'json',
		type:'post',
		success:function(result){
			if(result.result){
				cancheck = false;
				$("#b").html(result.message);
				alertMsg();
			}else{
				cancheck = true;
				$("#b").html(result.message);
			}
		},
		error:function(){
			cancheck = true;
			$("#b").html("系统异常，请稍后再试！");
				setTimeout(function(){
				$("#b").html("接收文档");
			},"1000");
		}
	});
}

function logout(){
	window.location.href='<%=basePath%>command/h5/logout?id=${id}';
}

function folder(){
	window.location.href='<%=basePath%>command/h5/folder?id=${id}';
}

function receiveNewDoc(){
	window.location.href='<%=basePath%>command/h5/receiveNewDoc?id=${id}';
}
</script>
</html>
