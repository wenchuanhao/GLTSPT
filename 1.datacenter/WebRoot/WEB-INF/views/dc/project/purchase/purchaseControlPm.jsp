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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>

</head>
<body>
<div class="gl_import_m">
	<jsp:include flush="true" page="public/purchaseEv.jsp"></jsp:include>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >采购全景监控表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    			<a class="btn_common01" href="javascript:ev_export()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>   			
		</div>    	
  	</div>
  	<div style="overflow: auto; width: 99%;">
	<table border="0" style="min-width: 100%" align="center" cellpadding="0" cellspacing="0" class="listTable">
		<tr>
			<th colspan="5">项目信息</th>
			<th colspan="9">采购进度</th>
			<th colspan="8">采购质量</th>
			<th colspan="4">采购成本</th>
			<th rowspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
		</tr>
		<tr>
		    <th><input id="checkAll" type="checkbox"></th>
			<th>序号</th>
			<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项目名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
			<th>经办人</th>
			<th>&nbsp;&nbsp;需求部門&nbsp;&nbsp;</th>
			<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;采购方式&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
			<th>当前进度（周报）</th>
			<th>需求到达时间</th>
			<th>预算金额(万元）</th>
			<th>公告发布开始时间</th>
			<th>公告发布截止时间</th>
			<th>采购评审/谈判时间</th>
			<th>结果决策会通过时间<br/>或结果呈批批复时间</th>
			<th>合同审批完毕时间</th>
			<th>采购时长（工作日）</th>
			<th>流标次数</th>
			<th>流标原因说明（每次流标都作说明）</th>
			<th>技术商务比例是否符合标准</th>
			<th>合同模板是否符合标准</th>
			<th>技术评分模板招标<br/>文件模板是否符合标准</th>
			<th>采购货物或<br/>服务质量情况</th>
			<th>投诉情况</th>
			<th>中标单位、结算价格<br/>和合同单位、结算价格<br/>是否完全一致</th>
			<th>采购节约金额（万元）</th>
			<th>采购工作投入天数</th>
			<th>采购进度是<br/>否影响到成本/<br/>投资使用计划一致</th>
		</tr>
 		<c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
 			<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
 			<td ><input name="subBox" type="checkbox" value="${vo.id}"></td>
 				<td>${i.count}</td>
				<td>${vo.columnA}</td>
				<td>${vo.columnB}</td>
				<td>${vo.columnC}</td>
				<td>${vo.columnJ}</td>
				<td>${vo.columnL}</td>
				<td>${vo.columnO}</td>
				<td>${fn:contains(vo.columnN, '.') ?  fn:substring(vo.columnN, 0, fn:indexOf(vo.columnN, '.')+3)  : vo.columnN }</td>				
				<td>${vo.columnZ}</td>
				<td>${vo.columnAa}</td>
				<td>${vo.columnAb}</td>
				<td>${vo.columnAr}</td>
				<td>${vo.columnAs}</td>
				<!--采购时长 -->
				<td>${vo.purTime }</td>
				<td>${vo.columnAy}</td>
				<td>${vo.columnBa}</td>
				<td>${vo.columnBe}</td>
				<td>${vo.columnBf}</td>
				<td>${vo.columnBg}</td>
				<td>${vo.columnBn}</td>
				<td>${vo.columnBh}</td>
				<td>${vo.columnBi}</td>
				<!-- 采购节约金额（万元） -->
				<td><fmt:formatNumber value='${vo.columnAw - vo.columnN }' pattern="0.00" maxFractionDigits="2"></fmt:formatNumber></td>
				<!--采购工作投入天数 -->
				<td>${vo.purDays }</td>
				<td>${vo.columnBj}</td>
				<td>${vo.columnBo}</td>
 			</tr>
 		</c:forEach>
	    <c:if test="${empty ITEMPAGE.items}">
	       <tr> <td colspan="27">找不到对应的数据</td></tr>
	   </c:if>
	</table>
	</div>
 	 <div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
<script type="text/javascript" >var basePath = "<%=basePath%>purchaseControl";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	

<script type="text/javascript">
$(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
    });
    var $subBox = $("input[name='subBox']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
    });
});
function ev_search(){
	document.forms[0].action="<%=basePath%>purchaseControl/pmlist?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	/* var time = new Date();
	var year = time.getFullYear();
	var month = time.getMonth()+1;
	if(month<10){
		month = '0'+month;
	}
	var date = time.getDate();
	if(date<10){
		date = '0'+date;
	}
	var endTime = year+'-'+month+'-'+date; */
	//$("#beginTime").val("");
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
	$("#columnC").val("");
	$("#columnB").val("");
	//ev_search();
}

function ev_export(){
    var $subBoxChecks = $("input[name='subBox']:checked");
	var zbIds = "";
	$.each($subBoxChecks,function(k,v){
		if(k == 0){
			zbIds += "'"+$(v).val()+"'";
		}else{
			zbIds += ",'"+$(v).val()+"'";
		}
	});
	document.forms[0].action="<%=basePath%>purchaseControl/pmFile?ids="+zbIds+"&key="+Math.random();
	document.forms[0].submit();
	document.forms[0].action="<%=basePath%>purchaseControl/pmlist?key="+Math.random();
}
	 
</script>
</body>
</html>
