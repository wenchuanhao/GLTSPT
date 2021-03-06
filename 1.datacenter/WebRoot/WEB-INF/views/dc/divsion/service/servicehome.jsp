<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath(); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<body style="background-color: #f2f2f2;">
<div class="jf_super_wrap">
		<div class="searchCondition"><span class="searchCondition_header">查询条件</span><font color="red" size="3">&nbsp;&nbsp;&nbsp;温馨提示：数据来源于园区服务综合支撑平台。</font>
			<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />
		</div>
		<form name="form" id="pageForm" method="post"  >
	  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	  	<tr>
		    <th width="9%" align="right">统计周期：</th>
		    <td width="10%">
		    	<div class="date l" style="width: 163px;">
					<input readonly="readonly" name="year" id="year" type="text"  placeholder="请您输入"  class="text02 l"  value="${year }"
						  onfocus="WdatePicker({startDate:'%y', dateFmt:'yyyy',alwaysUseStartDate:true});" style="width:90%;" /><i></i>
		        </div>
			</td> 
		</tr>
		</table>
		</form>
  <!-- 图表 S -->
  <div class="col-2 row-1">
    <div class="chart_wrap">
        <h3 class="chart_title">${year}年员工服务e平台访问人数</h3>
        <span class="switch02 switch_list"><i>数据</i></span>
        <div class="chart_col02" id="containerService01"></div>
        <div style="display: none;" class="list_wrap02 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable" id="tbodycontainerService01">
            </table>
        </div>
    </div>
  </div>
  <div class="col-2 row-2">		 			
  	<div class="chart_wrap">					     
  	<h3 class="chart_title">${year}年掌上南基总激活用户数</h3>					     
  	<span class="switch02 switch_list"><i>数据</i></span>					     
  	<div class="chart_col03" id="containerService02"></div>
	<div style="display: none;" class="list_wrap03 scroll">
  		<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>				          
  			<table class="listTable" id="tbodycontainerService02">
  			</table>
     </div>				 	
     </div>				
  </div>
  <div class="col-2 row-2">		 			
  	<div class="chart_wrap">					     
  	<h3 class="chart_title">${year}年掌上南基平均日活跃人数</h3>					     
  	<span class="switch02 switch_list"><i>数据</i></span>					     
  	<div class="chart_col03" id="containerService03"></div>
	<div style="display: none;" class="list_wrap03 scroll">
  		<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>				          
  			<table class="listTable" id="tbodycontainerService03">
  			</table>
     </div>				 	
     </div>				
  </div>
  <!-- 图表 E -->
</div>
<script src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script src="/SRMC/dc/js/hchart/highcharts.js"></script>
<script src="/SRMC/dc/js/hchart/modules/exporting.js"></script>
<script src="/SRMC/dc/js/jquery.nicescroll.js"></script>
<script type="text/javascript" >var base_url="<%=basePath%>";var basePath = "<%=basePath%>/divsion";</script>
<script type="text/javascript" src="/SRMC/dc/divsion/js/divsion_home.js"></script>
<script type="text/javascript" src="/SRMC/dc/divsion/js/service.js"></script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/exportExcel.js"></script>
<script type="text/javascript">
 var month = "${month}";//月份
 var year = "${year}";//年份
 
 //提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>/divsion/service";
	document.forms[0].submit();
}

$(function(){

	initContainerForTimeColumn('line','#containerService01','人次','人次','containerService01',month,year);
	
	initContainerForTimeColumn('line','#containerService02','用户数','用户数','containerService02',month,year);
	
	initContainerForTimeColumn('line','#containerService03','用户数','用户数','containerService03',month,year);
	
	//数据与列表切换
	$('.switch02').click(function(){
	  $(this).toggleClass('switch_list');
	  if($(this).children('i').html()=='数据'){
	    $(this).children('i').html('图表');
	    $(this).prev().css('right',140+'px');
	    $(this).next().hide();
	    $(this).next().next().show();
	    //加载数据
	    loadChartDataAjax(this);
	  }else{
	    $(this).children('i').html('数据');
	    $(this).prev().css('right',82+'px');
	    $(this).next().show();
	    $(this).next().next().hide();
	  }
	});
	// 列表滚动条
	$(".scroll").niceScroll({  
	    cursorcolor:"#a7c0d4",  
	    cursoropacitymax:1,  
	    touchbehavior:false,  
	    cursorwidth:"5px",  
	    cursorborder:"0",  
	    cursorborderradius:"5px"  
	});
});

$(document).ready(function(){
});

</script>
</body>
</html>
