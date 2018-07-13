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
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
			<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
			<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />	
	</div>
	
	<form name="form" id="pageForm" method="post"  >
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${purchaseForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${purchaseForm.pageSize}" id="pageSize"	name="pageSize"/>
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	  	<tr>
  			
  		<th width="2%" align="right">省公司采购结果确认时间：</th>
	    <td width="30%"  colspan="3">
	    	<div class="date l" >
	    	<input readonly="readonly" name="beginCreateTime" id="beginCreateTime" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value='${purchaseForm.beginCreateTime}' pattern="yyyy-MM-dd"/>"
	                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
	    	<div class="date l" >
	    	<input readonly="readonly" name="endCreateTime" id="endCreateTime" type="text"  placeholder="请输入结束时间"  class="text02 l" 
	    					value="<fmt:formatDate value='${purchaseForm.endCreateTime}' pattern="yyyy-MM-dd"/>"
	                     	onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreateTime\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
	</tr>
	
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >南方基地项目采购备案表<em></em></li>
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
<th>单位</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;月份&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
采购项目名称
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th>经办人</th>
<th>采购组织实施单位/部门（集采类型）</th>
<th>采购组织实施单位/部门</th>
<th>需求单位</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;需求部门&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
采购类型
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</th>
<th>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
采购内容
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th>开支类型</th>
<th>自行/委托采购</th>
<th>采购方式(首次)</th>
<th>采购方式(最后一次)</th>
<th>项目当前实际状态</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作方式&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th>电子采购项目编号</th>
<th>ES系统中的采购项目名称</th>
<th>采购方案决策层级（决策形式）</th>
<th>采购方案预估金额（万元）</th>
<th>项目启动时间</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;采购模式&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th>地市采购方案决策时间<br/>(首次)</th>
<th>地市采购方案决策时间<br/>(最后一次)</th>
<th>地市采购方案纪要下达时间</th>
<th>地市采购方案发文文号</th>
<th>省公司采购方案决策时间(首次)</th>
<th>省公司采购方案决策时间(最后一次)</th>
<th>省公司采购方案纪要<br/>/启动通知下达时间</th>
<th>省公司采购方案<br/>/启动通知发文文号</th>
<th>采购结果确认层级（确认形式）</th>
<th>采购结果金额（万元）</th>
<th>地市公司采购结果确认时间</th>
<th>地市采购结果上报时间</th>
<th>省公司采购结果确认时间</th>
<th>合同签署时间</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;采购结果发文文号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th>单一来源适用场景（大类）</th>
<th>单一来源适用场景（小类）</th>
<th>单一来源适用场景<br/>（具体产品服务备注）</th>
</tr>
 <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">	  
<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
<td ><input name="subBox" type="checkbox" value="${vo.id}"></td>
<td>${vo.rownum}</td>
<td>${vo.units}</td>
<td>${vo.flowMonth}</td>
<td>${vo.column_a}</td>
<td>${vo.column_b}</td>
<td>${vo.column_ac}</td>
<td>${vo.column_ad}</td>
<td>${vo.column_ae}</td>
<td>${vo.column_c}</td>
<td>${vo.column_af}</td>
<td>${vo.column_ag}</td>
<td>${vo.column_f}</td>
<td>${vo.column_g}</td>
<td>${vo.column_i}</td>
<td>${vo.column_j}</td>
<td>${vo.column_ah}</td>
<td>${vo.column_ai}</td>
<td>${vo.column_aj}</td>
<td>${vo.column_ak}</td>
<td>${vo.column_al}</td>
<td>${vo.column_n}</td>
<td>${vo.column_o}</td>
<td>${vo.column_am}</td>
<td>${vo.column_r}</td>
<td>${vo.column_s}</td>
<td>${vo.column_t}</td>
<td>${vo.column_u}</td>
<td>${vo.column_v}</td>
<td>${vo.column_w}</td>
<td>${vo.column_x}</td>
<td>${vo.column_y}</td>
<td>${vo.column_an}</td>
<td>${vo.column_ao}</td>
<td>${vo.column_ap}</td>
<td>${vo.column_aq}</td>
<td>${vo.column_ar}</td>
<td>${vo.column_as}</td>
<td>${vo.column_at}</td>
<td>${vo.column_bb}</td>
<td>${vo.column_bc}</td>
<td>${vo.column_bd}</td>	  	  	
</tr>
 </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="9">找不到对应的数据</td></tr>
	   </c:if>
	</table>
</div>
 	 <div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
<script type="text/javascript" >var basePath = "<%=basePath%>purchase";</script>
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
	document.forms[0].action="<%=basePath%>purchaseExp/purchaseRecQuery?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
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
	document.forms[0].action="<%=basePath%>purchaseExp/exportRecFile?ids="+zbIds+"&key="+Math.random();
	document.forms[0].submit();
    document.forms[0].action="<%=basePath%>purchaseExp/purchaseRecQuery?key="+Math.random();
	
}
	 
</script>
</body>
</html>
