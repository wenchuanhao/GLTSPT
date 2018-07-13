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
</head>
<body style="background-color: #f2f2f2;">
<div class="jf_super_wrap" id="chart_super_div">
</div>
<script src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/dc/js/hchart/highcharts.js"></script>
<script src="/SRMC/dc/js/hchart/modules/exporting.js"></script>
<script src="/SRMC/dc/js/jquery.nicescroll.js"></script>
<script type="text/javascript" >var base_url="<%=basePath%>";var basePath = "<%=basePath%>/divsion";</script>
<script type="text/javascript" src="/SRMC/dc/divsion/js/divsion_home.js"></script>
<script type="text/javascript" src="/SRMC/dc/divsion/js/water.js"></script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/exportExcel.js"></script>
<script type="text/javascript">

 var month = "${month}";//月份
 var year = "${year}";//年份
 var name = "${name}";//系统名称
 var container = "${container}";//一级页面图表ID
$(function(){
 	//中
 	$("#chart_super_div").append(setContainerDiv('col-1','row-1','chart_col01','list_wrap01',year+'年'+name+'每月总用水量统计表',"${year}",'containerWater01_01'));
	//中
 	//小
 	$("#chart_super_div").append(setContainerDiv('col-1','row-1','chart_col01','list_wrap01',year+'年'+name+'每月总用水量同比/环比分析表',"${year}",'containerWater01_02'));
 	
	//趋势
	initContainerForTimeColumn('column|line','#containerWater01_01','用水量(度)','度','containerWater01_01',month,year);
	//同比、环比
	initContainerForTimeColumn('spline','#containerWater01_02','用水量(度)','%','containerWater01_02',month,year);
	
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

</script>
</body>
</html>
