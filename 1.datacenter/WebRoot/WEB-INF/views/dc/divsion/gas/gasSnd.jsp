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
<script type="text/javascript" >var base_url="<%=basePath%>";var basePath = "<%=basePath%>/divsionGas";</script>
<script type="text/javascript" src="/SRMC/dc/divsion/js/divsion_home.js"></script>
<script type="text/javascript" src="/SRMC/dc/divsion/js/gas.js"></script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/exportExcel.js"></script>
<script type="text/javascript">

 var month = "${month}";//月份
 var year = "${year}";//年份
 var name = "${name}";//系统名称
 var container = "${container}";//一级页面图表ID
$(function(){
 	//中
 	$("#chart_super_div").append(setContainerDiv('col-1','row-1','chart_col02','list_wrap02',year+'年'+name+'天然气用量统计表',"${year}",'container01_04'));
	//中
 	//$("#chart_super_div").append(setContainerDiv('col-2','row-1','chart_col02','list_wrap02',year+'年'+name+'各系统用电比例图',"${year}",'container01_01'));
 	//小
 	$("#chart_super_div").append(setContainerDiv('col-1','row-1','chart_col01','list_wrap01',year+'年'+name+'天然气用量同比/环比分析表',"${year}",'container01_02'));
 	
 	
 	//initContainerForPie('#container01_01','sndlevel0101',month,year);//部门维度
	//趋势
	initContainerForTimeColumn('column','#container01_04','立方米','立方米','sndlevel0104',month,year);
	//同比、环比
	initContainerForTimeColumn('spline','#container01_02','百分比','%','sndlevel0102',month,year);
	
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
