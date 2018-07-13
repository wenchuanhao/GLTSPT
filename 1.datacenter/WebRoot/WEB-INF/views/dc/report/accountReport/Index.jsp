<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="java.text.*"  language="java" %>
<%@ page import="java.util.Date"  language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,target-densitydpi=medium-dpi"/>
<title>报账经分首页</title>
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />

<script type="text/javascript">
var baseURL = "<%=basePath%>";
var cMon = "${cMon}";
var cSc = "${cSc}";
var startDate="${startDate}";//开始日期
var endDate="${endDate}";//结束时期
</script>

</head>
<body style="background-color: #f2f2f2;overflow: hidden;">
<div class="col-1 row-1">
<div class="time_line_w">
    <h3 class="time_title" style="color:#4084b6;">时间轴</h3>
    <p class="tips01">统计周期：<em id="startTime"></em>至<em id="endTime"></em><a href="javascript:void(0);" onclick="doExport()" class="export_chart" style="color:#4084b6;">导出</a></p>
    <!-- 时间轴 S -->
	<div class="time_line_f">
		<a href="javascript:;" class="pre"></a>
		<a href="javascript:;" class="nxt"></a>
		<div class="time_line_wrap">
		<div class="time_line" id="time_line"></div>
		</div>
	</div>
	<!-- 时间轴 E -->

    </div>
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

<iframe id="chart_content" src="<%=basePath%>/BZJF/indexChild" frameborder="0" scrolling="yes" width="100%"></iframe>

<script src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/dc/js/timeline.js"></script>
<script src="/SRMC/dc/js/hchart/highcharts.js"></script>
<script src="/SRMC/dc/js/hchart/modules/exporting.js"></script>
<script type="text/javascript" language="javascript">
var client_h=document.documentElement.clientHeight || document.dody.clientHeight;
var chart_iframe_h=client_h-$(".row-1").height()-21;
$("#chart_content").load(function(){
  $(this).height(chart_iframe_h);
});

    Highcharts.setOptions({
        colors: ['#b2e31a', '#40baff','#ff6d26','#dc1ced','#7d1dff'],
        // 按钮样式
        navigation: {
            buttonOptions: {
                height: 30,
                width: 34,
                symbolSize: 20,
                symbolX: 17,
                symbolY: 15,
                symbolStrokeWidth: 2
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'bottom',
            itemMarginBottom: 6,
            enabled: true,
            itemStyle: {
                color: '#a5a5a5',
                fontWeight: 'normal',
                fontFamily: '微软雅黑'
            }
        },
        credits: {
            enabled: false
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true,
                    borderRadius: 3,
                    backgroundColor: 'rgba(255, 255, 255, 0.5)'
                }
            }
        }
    });
    
    function doExport(){
   		window.location.href = '<%=basePath%>/BZJF/exportFile?startDate='+$("#startTime").html()+'&endDate='+$("#endTime").html();
    }
</script>               
</body>
</html>
