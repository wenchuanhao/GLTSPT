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
<script src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>

<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script src="/SRMC/dc/js/hchart/highcharts.js"></script>
<script src="/SRMC/dc/js/hchart/modules/exporting.js"></script>
<script src="/SRMC/dc/js/jquery.nicescroll.js"></script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/exportExcel.js"></script>
</head>
<body>
<div class="gl_import_m">
	<jsp:include flush="true" page="public/purchaseEv1.jsp"></jsp:include>
  	
  	<div class="col-2 row-1" style="width:100%">
    <div class="chart_wrap" >
        <h3 class="chart_title">采购全景监控总表</h3>
        <div class="otherButtons r" style="float:left;margin-top:-25px;margin-left:90%;">
    		<a class="btn_common01" href="javascript:ev_export()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>   			
		</div> 
		 <span id="switch_1" class="switch02 switch_list" style="right:80%"><i>数据</i></span>       
		      
        <div  style="display:none;width:330px; height:250px;float:left" class="chart_col01" id="container01"></div>
        <div  style="display:none;width:330px; height:250px;float:left;margin-left:35px" class="chart_col01" id="container02"></div>
        <div  style="display:none;width:330px; height:250px;float:left;margin-left:35px" class="chart_col01" id="container03"></div>        
        <div  class="list_wrap02" style="width:104%;height:35%;">
            <table class="listTable" id="tbodycontainer01">
             <tr>
			<th>总数</th>
			<th>收到请购单</th>
			<th>工作组讨论</th>
			<th>采购方案呈批</th>
			<th>发布公告</th>
			<th>完成评审</th>
			<th>结果确认</th>
			<th>结果公示</th>
			<th>合同签订呈批</th>
			<th>合同系统审批</th>
			<th>签订纸质合同</th>
			<th>已归档</th>
			<th>已取消</th>
			<th>完成评审项目</th>
			<th>已完成项目数</th>
		</tr>
 		<c:forEach items="${tqc}" var="vo" varStatus="i">
 			<tr >
 				<td>${vo.c1}</td>
				<td>${vo.c2}</td>
				<td>${vo.c3}</td>
				<td>${vo.c4}</td>
				<td>${vo.c5}</td>
				<td>${vo.c6}</td>
				<td>${vo.c7}</td>
				<td>${vo.c8}</td>
				<td>${vo.c9}</td>
				<td>${vo.c10}</td>
				<td>${vo.c11}</td>
				<td>${vo.c12}</td>
				<td>${vo.c13}</td>
				<td>${vo.c14}</td>
				<td>${vo.c15}</td>
 			</tr>
 		</c:forEach>
	    <c:if test="${empty tqc}">
	       <tr> <td colspan="15">找不到对应的数据</td></tr>
	   </c:if>
            </table>
        </div>
    </div>
    
  </div>
  <div style="width:100%;float:left;">
  <div class="chart_wrap" style="float:left;margin-left:20px;width:39%;" >
        <h3 class="chart_title">需求确认时长</h3>
		<span id="switch_2" class="switch02 switch_list" style="float:left;position:relative;margin-left:32%;margin-top:-40px"><i>数据</i></span>       		      
        <div  style="display:none;width:400px; height:250px;float:left;margin-left:0px" class="chart_col01" id="container04"></div>
        <div  class="list_wrap02" style="height:35%;">
        <table class="listTable" id="tbodycontainer02">
         <tr>
			<th>平均时长</th>
			<th>最大时长</th>
			<th>最小时长</th>
			<th>超过标准时长项目数</th>
		</tr>
 		<c:forEach items="${con}" var="v1" varStatus="i">
 			<tr>
 				<td>${v1.avgtime}</td>
				<td>${v1.maxtime}</td>
				<td>${v1.mintime}</td>
				<td>${v1.stime}</td>
			  
 			</tr>
 		</c:forEach>
	    <c:if test="${empty con}">
	       <tr> <td colspan="4">找不到对应的数据</td></tr>
	   </c:if>
            </table>
        </div>
    </div>
    
    <div class="chart_wrap" style="float:left;margin-left:10px;width:30%;" >
        <h3 class="chart_title">需求确认完毕时间-评审时长</h3>
		<span id="switch_3" class="switch02 switch_list" style="float:left;position:relative;margin-left:70%;margin-top:-40px"><i>数据</i></span>       		      
        <div  style="display:none;width:328px; height:250px;float:left" class="chart_col01" id="container05"></div>
        <div  class="list_wrap02" style="height:35%;">
        <table class="listTable" id="tbodycontainer03">
        <tr>
			<th>平均时长</th>
			<th>最大时长</th>
			<th>最小时长</th>
		</tr>
 		<c:forEach items="${rev}" var="v2" varStatus="i">
 			<tr>
 				<td>${v2.avgtime}</td>
				<td>${v2.maxtime}</td>
				<td>${v2.mintime}</td>			  
 			</tr>
 		</c:forEach>
	    <c:if test="${empty rev}">
	       <tr> <td colspan="3">找不到对应的数据</td></tr>
	   </c:if>
            </table>
        </div>
    </div>
    
    <div class="chart_wrap" style="float:left;margin-left:10px;width:25%;" >
        <h3 class="chart_title">需求确认完毕-合同系统审批完毕</h3>
		<span id="switch_4" class="switch02 switch_list" style="float:left;position:relative;margin-left:95%;margin-top:-40px"><i>数据</i></span>       		      
        <div  style="display:none;width:316px; height:250px;" class="chart_col01" id="container06"></div>
        <div  class="list_wrap02" style="height:35%;">
        <table class="listTable" id="tbodycontainer04">
        <tr>
			<th>平均时长</th>
			<th>最大时长</th>
			<th>最小时长</th>
		</tr>
 		<c:forEach items="${app}" var="v3" varStatus="i">
 			<tr>
 				<td>${v3.avgtime}</td>
				<td>${v3.maxtime}</td>
				<td>${v3.mintime}</td>			  
 			</tr>
 		</c:forEach>
	    <c:if test="${empty app}">
	       <tr> <td colspan="3">找不到对应的数据</td></tr>
	   </c:if>
            </table>
        </div>
    </div>
    </div>
    <div style="width:100%;float:left;">
    <div class="chart_wrap" style="float:left; display:inline;  margin-left:20px;width:39%;margin-top:15px;" >
        <h3 class="chart_title">规范性</h3>
		<span id="switch_5" class="switch02 switch_list" style="float:left;position:relative;margin-left:32%;margin-top:-40px"><i>数据</i></span>       		      
        <div  style="display:none;width:400px; height:250px;float:left" class="chart_col01" id="container07"></div>
        <div  class="list_wrap02" style="height:35%;">
        <table class="listTable" id="tbodycontainer02">
         <tr>
			<th>未使用技术<br/>评分表模板项目数</th>
			<th>未使用合<br/>同模板项目数</th>
			<th>技术与商务比例<br/>不符合公司规定项目数</th>
			<th>采购过程被<br/>供应商投诉项目数</th>
		</tr>
 		<c:forEach items="${tem}" var="v4" varStatus="i">
 			<tr>
 				<td>${v4.c1}</td>
				<td>${v4.c2}</td>
				<td>${v4.c3}</td>
				<td>${v4.c4}</td>
			  
 			</tr>
 		</c:forEach>
	    <c:if test="${empty tem}">
	       <tr> <td colspan="4">找不到对应的数据</td></tr>
	   </c:if>
            </table>
        </div>
    </div>
    
     <div class="chart_wrap" style="float:left;margin-left:10px;width:30%;margin-top:15px;" >
        <h3 class="chart_title">流标情况</h3>
		<span id="switch_6" class="switch02 switch_list" style="float:left;position:relative;margin-left:70%;margin-top:-40px"><i>数据</i></span>       		      
        <div  style="display:none;width:328px; height:250px;float:left" class="chart_col01" id="container08"></div>
        <div  class="list_wrap02" style="height:35%;">
        <table class="listTable" id="tbodycontainer01">
        <tr>
			<th>发生过流标<br/>情况的项目数</th>
			<th>流标次数总计</th>
			<th>流标后更改<br/>采购方式项目数</th>
		</tr>
 		<c:forEach items="${bid}" var="v5" varStatus="i">
 			<tr>
 				<td>${v5.c1}</td>
				<td>${v5.c2}</td>
				<td>${v5.c3}</td>			  
 			</tr>
 		</c:forEach>
	    <c:if test="${empty bid}">
	       <tr> <td colspan="3">找不到对应的数据</td></tr>
	   </c:if>
            </table>
        </div>
    </div>
    
    <div class="chart_wrap" style="float:left;margin-left:10px;width:26%;margin-top:15px;" >
        <h3 class="chart_title">单一来源采购情况</h3>
		<span id="switch_7" class="switch02 switch_list" style="float:left;position:relative;margin-left:90%;margin-top:-40px"><i>数据</i></span>       		      
        <div  style="display:none;float:left;width:328px; height:250px;" class="chart_col01" id="container09"></div>
        <div  style="display:none;width:328px; height:250px;" class="chart_col01" id="container10"></div>
        
        <div  class="list_wrap02" style="height:35%;">
        <table class="listTable" id="tbodycontainer01">
         <tr>
			<th>总数</th>
			<th>数量占比</th>
			<th>总额（万元）</th>
			<th>金额占比</th>
		</tr>
 		<c:forEach items="${onl}" var="v6" varStatus="i">
 			<tr>
 				<td>${v6.c1}</td>
				<td>${v6.c2}</td>
				<td>${v6.c3}</td>
				<td>${v6.c4}</td>			  
 			</tr>
 		</c:forEach>
	    <c:if test="${empty onl}">
	       <tr> <td colspan="4">找不到对应的数据</td></tr>
	   </c:if>
            </table>
        </div>
    </div>
    
    </div>
    <div style="width:100%;float:left;">
    <div class="chart_wrap" style="margin-left:20px;width:39%;margin-top:15px;" >
        <h3 class="chart_title">采购成本监控表</h3>
		<span id="switch_8" class="switch02 switch_list" style="float:left;position:relative;margin-left:35%;margin-top:-40px"><i>数据</i></span>       		      
        <div  style="display:none;width:379px; height:250px;float:left" class="chart_col01" id="container11"></div>
        <div  class="list_wrap02" style="height:35%;">
        <table class="listTable" id="tbodycontainer02">
         <tr>
			<th >采购项目预算金额<br/>（万元）</th>
			<th >签订合同金额<br/>（万元）</th>
			<th >&nbsp;&nbsp;&nbsp;&nbsp;节约金额&nbsp;&nbsp;&nbsp;&nbsp;<br/>（万元）</th>
			<th >&nbsp;&nbsp;&nbsp;&nbsp;节支率&nbsp;&nbsp;&nbsp;&nbsp;</th>
		</tr>
 		<c:forEach items="${cos}" var="v7" varStatus="i">
 			<tr>
				<td>${fn:contains(v7.c1, '.') ?  fn:substring(v7.c1, 0, fn:indexOf(v7.c1, '.')+3)  : v7.c1 }</td>								
				<td>${fn:contains(v7.c2, '.') ?  fn:substring(v7.c2, 0, fn:indexOf(v7.c2, '.')+3)  : v7.c2 }</td>								
				<td>${fn:contains(v7.c3, '.') ?  fn:substring(v7.c3, 0, fn:indexOf(v7.c3, '.')+3)  : v7.c3 }</td>								
				<td>${v7.c4}</td>			  
 			</tr>
 		</c:forEach>
	    <c:if test="${empty cos}">
	       <tr> <td colspan="4">找不到对应的数据</td></tr>
	   </c:if>
            </table>
        </div>
    </div>
    </div>

</div>
<script type="text/javascript" >var basePath = "<%=basePath%>";</script>
<script type="text/javascript" src="/SRMC/dc/purchase/purchase_chart.js"></script>
<script src="/SRMC/rmpb/js/jquery.form.js"></script>

<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript">
function ev_search(){
	document.forms[0].action="<%=basePath%>purchaseExp/purchaseAllQuery?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
	$("#columnC").val("");
	$("#columnB").val("");
	ev_search();
}

function ev_export(){
	document.forms[0].action="<%=basePath%>purchaseExp/exportAllFile?key="+Math.random();
	document.forms[0].submit();
}

$(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});

$(function(){
  
	//饼状图
	initContainerForPie('#container01','container01');
	initContainerForPie('#container02','container02');
	initContainerForPie('#container03','container03');
	initContainerForPie('#container09','container09');
	initContainerForPie('#container10','container10');
	 
	//柱状图
	initContainerForTimeColumn('#container04','container04');
	initContainerForTimeColumn('#container05','container05');
	initContainerForTimeColumn('#container06','container06');
	initContainerForTimeColumn('#container07','container07');
    initContainerForTimeColumn('#container08','container08');
    initContainerForTimeColumn('#container11','container11');
	
	
	$('#switch_1').click(function(){
	  $(this).toggleClass('switch_list');
	  if($(this).children('i').html()=='数据'){
	    $(this).children('i').html('图表');
	    $(this).prev().css('right',140+'px');
	    $(this).next().show();
	    $(this).next().next().show();
	    $(this).next().next().next().show();
	    $(this).next().next().next().next().hide();
	    //加载数据
	    //loadDataAjax(this);
	  }else{
	    $(this).children('i').html('数据');
	    $(this).prev().css('right',82+'px');
	    $(this).next().hide();
	    $(this).next().next().hide();
	    $(this).next().next().next().hide();
	    $(this).next().next().next().next().show();
	  }
	});
	
	$('#switch_2').click(function(){
	  $(this).toggleClass('switch_list');
	  if($(this).children('i').html()=='数据'){
	    $(this).children('i').html('图表');
	    //$(this).css('margin-left','3%');
	    $(this).next().show();
	    $(this).next().next().hide();

	  }else{
	    $(this).children('i').html('数据');
	   // $(this).css('margin-left','32%');
	    $(this).next().hide();
	    $(this).next().next().show();
	  }
	});
	
	$('#switch_3').click(function(){
	  $(this).toggleClass('switch_list');
	  if($(this).children('i').html()=='数据'){
	    $(this).children('i').html('图表');
	    $(this).next().show();
	    $(this).next().next().hide();
	    //加载数据
	    //loadDataAjax(this);
	  }else{
	    $(this).children('i').html('数据');
	    $(this).next().hide();
	    $(this).next().next().show();
	  }
	});
	
	$('#switch_4').click(function(){
	  $(this).toggleClass('switch_list');
	  if($(this).children('i').html()=='数据'){
	    $(this).children('i').html('图表');
	    $(this).next().show();
	    $(this).next().next().hide();
	  }else{
	    $(this).children('i').html('数据');
	    $(this).next().hide();
	    $(this).next().next().show();
	  }
	});
	
	$('#switch_5').click(function(){
	  $(this).toggleClass('switch_list');
	  if($(this).children('i').html()=='数据'){
	    $(this).children('i').html('图表');
	    // $(this).css('margin-left','3%');
	    $(this).next().show();
	    $(this).next().next().hide();
	  }else{
	    $(this).children('i').html('数据');
	    // $(this).css('margin-left','32%');
	    $(this).next().hide();
	    $(this).next().next().show();
	  }
	});
	
	$('#switch_6').click(function(){
	  $(this).toggleClass('switch_list');
	  if($(this).children('i').html()=='数据'){
	    $(this).children('i').html('图表');
	    // $(this).css('margin-left','40%');
	    $(this).next().show();
	    $(this).next().next().hide();
	  }else{
	    $(this).children('i').html('数据');
	   // $(this).css('margin-left','70%');
	    $(this).next().hide();
	    $(this).next().next().show();
	  }
	});
	
	$('#switch_7').click(function(){
	  $(this).toggleClass('switch_list');
	  if($(this).children('i').html()=='数据'){
	    $(this).children('i').html('图表');
	    $(this).next().show();
	    $(this).next().next().show();
	    $(this).next().next().next().hide();
	  }else{
	    $(this).children('i').html('数据');
	    $(this).next().hide();
	    $(this).next().next().hide();
	    $(this).next().next().next().show();
	  }
	});
	
	$('#switch_8').click(function(){
	  $(this).toggleClass('switch_list');
	  if($(this).children('i').html()=='数据'){
	    $(this).children('i').html('图表');
	   // $(this).css('margin-left','6%');
	    $(this).next().show();
	    $(this).next().next().hide();
	  }else{
	    $(this).children('i').html('数据');
	   // $(this).css('margin-left','35%');
	    $(this).next().hide();
	    $(this).next().next().show();
	  }
	});

});

//用户可选择创建人
function autoCompletes(){
		jQuery("#columnB").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>rulesController/searchUser",
					dataType: "json",
					data: {
						userValue: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
				     			 value:item[0].userName+" -- "+item[1].orgName,
									userName:item[0].userName,
									userId:item[0].userId,
									account:item[0].account,
									orgName:item[1].orgName,
								}
							}));
						}else{
							return false;
						}
					}
				});
			},
			minLength: 1,
			select: function( event, ui ) {
				jQuery("#columnB").val(ui.item.userName);
				return false;
			}
		});
	}
</script>
</body>
</html>
