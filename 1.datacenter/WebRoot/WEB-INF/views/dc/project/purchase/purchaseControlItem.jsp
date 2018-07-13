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
<!-- 	查询条件 -->
	<jsp:include flush="true" page="public/purchaseEv.jsp"></jsp:include>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >采购项目明细表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    			<a class="btn_common01" href="javascript:ev_export()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>   			
		</div>    	
  	</div>
  	<div style="overflow: auto; width: 99%;">
	<table border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
		<tr>
		    <th><input id="checkAll" type="checkbox"></th>
			<th>序号</th>
			<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项目名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
			<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;需求部门&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
			<th>项目所属年份</th>
			<th>是否计划内项目</th>
			<th>开支类型</th>
			<th>预算金额(万元）</th>
			<th>经办人</th>
			<th>代理公司名称</th>
			<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;采购方式&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
			<th>资格审查方式</th>
			<th>当前进度（周报）</th>
			<th>进度说明（周报）</th>
			<th>需求到达时间</th>
			<th>工作小组会议召开时间</th>
			<th>50万以上方案汇报时间<br/>/50万以下需求部门<br/>提交方案呈批时间</th>
			<th>需求确认时长</th>
			<th>需求确认时长超时预警</th>
			<th>采购方案决策时间</th>
			<th>公告发布开始时间</th>
			<th>公告发布截止时间</th>
			<th>采购评审/谈判时间</th>
			<th>需求确认完毕<br/>-评审时间（工作日）</th>
			<th>结果决策会通过时间<br/>或结果呈批批复时间</th>
			<th>合同审批完毕时间</th>
			<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;合同编码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
			<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中选供应商&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
			<th>合同金额（万元）</th>
			<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;下浮率&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
			<th>采购节约金额（万元）</th>
			<th>采购时长（工作日）</th>
			<th>采购时长超时预警</th>
			<th>合同归档时间</th>
			<th>流标次数</th>
			<th>流标后更改的采购方式</th>
			<th>流标原因说明（每次流标都作说明）</th>
			<th>采购工作投入天数</th>
			<th>技术商务比例是否符合标准</th>
			<th>合同模板是否符合标准</th>
			<th>技术评分模板<br/>招标文件模板是否符合标准</th>
			<th>投诉情况</th>
			<th>中标单位、<br/>结算价格和<br/>合同单位、<br/>结算价格<br/>是否完全一致</th>
			<th>采购进度是否<br/>影响到成本/投<br/>资使用计划一致</th>
			<th>特殊情况说明</th>
		</tr>
 		<c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">	
 			<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
 			   	<td ><input name="subBox" type="checkbox" value="${vo.id}"></td>
 				<td>${i.count}</td>
				<td>${vo.columnA}</td>
				<td>${vo.columnC}</td>
				<td>${vo.columnD}</td>
				<td>${vo.columnE}</td>
				<td>${vo.columnF}</td>
				<td>${fn:contains(vo.columnN, '.') ?  fn:substring(vo.columnN, 0, fn:indexOf(vo.columnN, '.')+3)  : vo.columnN }</td>
				<td>${vo.columnB}</td>
				<td>${vo.columnH}</td>
				<td>${vo.columnJ}</td>
				<td>${vo.columnK}</td>
				<td>${vo.columnL}</td>
				<td>${vo.columnM}</td>
				<td>${vo.columnO}</td>
				<td>${vo.columnP}</td>
				<td>${vo.columnQ}</td>
				<td>${vo.confirmTime }</td>
				
				<c:choose>
					<c:when test="${vo.confirmTime < xq }">
						<td style="background-color: #00FF00;">正常</td>
					</c:when>
					<c:otherwise>
						<td style="background-color: #ff0000;">超时提醒</td>
					</c:otherwise>
				</c:choose>
				
				<td>${vo.columnR}</td>
				<td>${vo.columnZ}</td>
				<td>${vo.columnAa}</td>
				<td>${vo.columnAb}</td>
				<td>${vo.reviewDays }</td>
				<td>${vo.columnAr}</td>
				<td>${vo.columnAs }</td>
				<td>${vo.columnAu}</td>
				<td>${vo.columnAv}</td>
				<td>${fn:contains(vo.columnAw, '.') ?  fn:substring(vo.columnAw, 0, fn:indexOf(vo.columnAw, '.')+3)  : vo.columnAw }</td>
				<td><fmt:formatNumber value='${(vo.columnAw - vo.columnN ) / vo.columnN * 100 }' pattern="##.##" maxFractionDigits="2"></fmt:formatNumber>%</td>
				<td><fmt:formatNumber value='${vo.columnAw - vo.columnN }' pattern="0.00" maxFractionDigits="2"></fmt:formatNumber></td>
				<td>${vo.purTime }</td>

				<c:choose>
					<c:when test="${vo.purTime < cg }">
						<td style="background-color: #00FF00;">正常</td>
					</c:when>
					<c:otherwise>
						<td style="background-color: #ff0000;">超时提醒</td>
					</c:otherwise>
				</c:choose>				

				<td>${vo.columnAx}</td>
				<td>${vo.columnAy}</td>
				<td>${vo.columnAz}</td>
				<td>${vo.columnBa}</td>
				<td>${vo.purDays }</td>
				<td>${vo.columnBe}</td>
				<td>${vo.columnBf}</td>
				<td>${vo.columnBg}</td>
				<td>${vo.columnBh}</td>
				<td>${vo.columnBi}</td>
				<td>${vo.columnBj}</td>
				<td>${vo.columnBk}</td>
 			</tr>  
 		</c:forEach>
	    <c:if test="${empty ITEMPAGE.items}">
	       <tr> <td colspan="45">找不到对应的数据</td></tr>
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
	document.forms[0].action="<%=basePath%>purchaseControl/list?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
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
	document.forms[0].action="<%=basePath%>purchaseControl/exportFile?ids="+zbIds+"&key="+Math.random();
	document.forms[0].submit();
	document.forms[0].action="<%=basePath%>purchaseControl/list?key="+Math.random();
}
	 
</script>
</body>
</html>
