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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>初审提交</title>
		<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script src="/SRMC/dc/account/js/jquery.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
		<link href="/SRMC/rmpb/css/date_input.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="gl_import_m">
<div class="searchCondition"><span class="searchCondition_header">提交</span></div>
<form action="" method="post" id="pageForm">
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="readOnlyTable">
  <input type="hidden" name="orderId" id="orderId" value="${orderId }"/>
   <input type="hidden" name="collectionMenber1" id="collectionMenber1"/>
   <input type="hidden" name="collectionMenberId" id="collectionMenberId" value="${infoId }"/>
   <input type="hidden" name="type" id="type" value="${type }"/>
   <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
  <tr>
    <th width="12%">单据是否有问题</th>
    <td width="21%"><c:if test="${ item[10]=='0'}">否</c:if><c:if test="${item[10]!='0'}">是</c:if></td>
    <th width="12%">整改状态</th>
    <td width="21%"><c:if test="${ empty info.status}">-</c:if>
    <c:if test="${info.status=='1'}">整改中</c:if>
    <c:if test="${info.status=='2'}">无问题</c:if>
    <c:if test="${info.status=='3'}">整改结束</c:if>
    <c:if test="${info.status=='4'}">退单</c:if></td>
    <th width="12%">问题整改耗时</th>
    <td width="21%">${proHaoshiSum }</td>
    </tr>
  <tr>
    <th>是否超时</th>
    <td><c:if test="${ empty item[7] || item[7]=='0'}">否</c:if><c:if test="${not empty item[7] && item[7]!='0'}">是</c:if></td>
    <th>超时总时长</th>
    <td><c:if test="${ empty item[7] || item[7]=='0'}">-</c:if><c:if test="${not empty item[7] && item[7]!='0'}">${item[7]}</c:if></td>
    <th>初审后是否存在问题</th>
    <td><c:if test="${ item[8]=='0'}">否</c:if><c:if test="${ item[8]!=0}">是</c:if></td>
  </tr>
    <tr>
      <th>最耗时问题类型</th>
      <td><c:if test="${ empty haoshiType }">-</c:if>
      <jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="BZ_TCTYPE"></jsp:param>
					<jsp:param name="paramCode" value="${haoshiType }"></jsp:param>
				</jsp:include> </td>
<!-- 				
      <th><em>*</em>达到财务付款时间</th>
      <td>
	      <div class="date l" style="width:150px;">
	      <input  type="text"   name="paymentTime" id="paymentTime"  value="<fmt:formatDate value="${info.paymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" placeholder="请您输入"  class="text02 l" style="width:90%;" />
	      <i>
	      </i>
      </div>
      </td>
-->
      <th>信息补录员</th>
      <td><input type="text" id="collectionMenber" name="collectionMenber" class="text01" disabled="disabled"  value="${infoName }"/></td>
    </tr>
    </c:forEach>
  </table></form>
  <div class="buttons" align="center" style="padding-top: 20px"><input name=""  onclick="submit('${orderId}')" type="button" class="btn_common02" value="提 交" /> <input name="" type="button" onclick="back()" class="btn_common01" value="取 消" /></div>
</div>
<script type="text/javascript">
	function back(){
		parent.window.location.reload();
	}
	
	function submit(orderId){
		//设置信息补录员
		var str = $("#collectionMenber").val();
		$("#collectionMenber1").val(str);
// 		if($("#paymentTime").val()==""){
// 			alert("请输入达到财务付款时间");
// 			return false;
// 		}
		
		$.ajax({
			type:"post",
			dataType:"json",
			async:false,
			url:"<%=basePath%>account/ajaxSubmit?orderId="+orderId,
			data:$('#pageForm').serialize(),// 你的formid
			success:function(data){	
				alert("提交成功");
				if(data=="2"){
					history.go(-1);
				}else{
					parent.window.location.reload();
				}
			},
			error:function(){
				alert("提交失败，请检查网络");
			}
		});//ajax
	}
</script>
</body>
</html>