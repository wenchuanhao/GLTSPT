<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<div class="jf_super_wrap" id="chart_super_div">
</div>

<!-- 图表放大弹窗 S -->
<div class="dark"></div>
<div class="zoomchart">
    <!-- 关闭按钮 -->
    <a href="javascript:;" class="chart_close"></a>
    <!-- 被放大的图表 -->
    <div class="orichart" id="same_container2"></div>
    <div class="orichart" id="same_container3"></div>
</div>
<!-- 图表放大弹窗 E -->
<script src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/dc/js/hchart/highcharts.js"></script>
<script src="/SRMC/dc/js/hchart/modules/exporting.js"></script>
<script src="/SRMC/dc/js/jquery.nicescroll.js"></script>
<script type="text/javascript" >var base_url="<%=basePath%>";var basePath = "<%=basePath%>";</script>
<script type="text/javascript" src="/SRMC/dc/report/laungch.js"></script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/exportExcel.js"></script>

<script type="text/javascript">
 var startTime = "${startTime}";//开始时间
 var endTime = "${endTime}";//结束时间
 var linkName = "${linkName}";//环节名称编号
 var linkNames = "${linkNames}";//环节名称
 var shixiao = "1";//时效
 var zhanbi = "2";//占比
$(function(){
	 if(linkName == "3"){
	 		var dimension = ['部门','费用','问题类型'];//维度切换
		 	$("#chart_super_div").append(setDimension(dimension,"changeChart01"));
		 	//中
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-1','chart_col02','list_wrap02','节点：'+linkNames+'时效',startTime+'-'+endTime,'container01'));
		 	//小
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03',''+linkNames+'时效同比',startTime+'-'+endTime,'container02'));
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03',''+linkNames+'时效环比',startTime+'-'+endTime,'container03'));
		 	
		 	initContainerForPie('#container01','部门',startTime,endTime,linkName,'时效');
			//同比
			initContainerForColumn('column','#container02','工作日','','部门',startTime,endTime,linkName,2,shixiao);
			//环比
			initContainerForColumn('column','#container03','工作日','','部门',startTime,endTime,linkName,1,shixiao); 
			
			/*******************************************************************************************/
			//中
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-1','chart_col02','list_wrap02','节点：'+linkNames+'占比',startTime+'-'+endTime,'container04'));
		 	//小
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03',''+linkNames+'占比-同比',startTime+'-'+endTime,'container05'));
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03',''+linkNames+'占比-环比',startTime+'-'+endTime,'container06'));
		 	$("#container04").parent().parent().hide();
			$("#container05").parent().parent().hide();
			$("#container06").parent().parent().hide();
// 		 	initContainerForPie('#container04','部门',startTime,endTime,linkName,zhanbi);
			//同比
// 			initContainerForColumn('column','#container05','%','','部门',startTime,endTime,linkName,2,zhanbi);
			//环比
// 			initContainerForColumn('column','#container06','%','','部门',startTime,endTime,linkName,1,zhanbi); 
	 }else{
	 		var dimension = ['部门','费用'];//维度切换
		 	$("#chart_super_div").append(setDimension(dimension,"changeChart01"));
		 	//中
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-1','chart_col02','list_wrap02','节点：'+linkNames+'时效',startTime+'-'+endTime,'container01'));
		 	//小
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03',''+linkNames+'时效-同比',startTime+'-'+endTime,'container02'));
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03',''+linkNames+'时效-环比',startTime+'-'+endTime,'container03'));
		 	
		 	initContainerForPie('#container01','部门',startTime,endTime,linkName,shixiao);
			//同比
			initContainerForColumn('column','#container02','工作日','','部门',startTime,endTime,linkName,2,shixiao);
			//环比
			initContainerForColumn('column','#container03','工作日','','部门',startTime,endTime,linkName,1,shixiao); 
	 }


$('.chart_close').click(function(){
    $('.zoomchart').hide();
    $('.dark').hide();
});
	 
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

function loadChartDataAjax(target){
	//表格内容ID
	var tbodyId = "tbody" + $(target).next().attr("id");
	$("#"+tbodyId).html('');
	//切换主题/部门/结算类型
	var dimension_ul = $("#dimension_ul").find(".current").html();
	var typeTemp = "";
	if(dimension_ul == "部门"){
		typeTemp = "_bumen";
	}else if(dimension_ul == "费用"){
		typeTemp = "_feiyong";
	}else if(dimension_ul == "问题类型"){
		typeTemp = "_wentiType";
	}
	
	//根据类型调用不同sql语句
	var type = $(target).next().attr("id") + typeTemp;
	jQuery.ajax({
		 type:"POST",
		 async:true,
		 url:basePath+"/account/getChartData",
		 data:"type="+type + "&startTime=" + startTime +"&endTime="+endTime+"&linkName="+linkName,
		 dataType: "json",
		 success:function(data){
		 	var head = data.head;
		 	var list = data.list;
		 	//表头
		 	var thTemp = '<tr>';
		 	$.each(head,function(k,v){
		 		thTemp += '<th>'+v+'</th>';
		 	});
		 	thTemp += '</tr>';
		 	
		 	//内容
		 	$.each(list,function(n,m){
			 	var tdTemp = '<tr>';
			 	$.each(head,function(k,v){
			 		var sta;
			 		if(k==0){sta=0;}if(k==1){sta=2;}if(k==2){sta=5;}if(k==3){sta=6;}
			 		tdTemp += '<td>'+m[sta]+'</td>';
			 	});
			 	tdTemp += '</tr>';
			 	thTemp += tdTemp;
		 	});
		 	$("#"+tbodyId).html(thTemp);
		 },
		 error:function(){
			 alert("查询失败！");
		 }
	 });
}


</script>
</body>
</html>
