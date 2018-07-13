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
<title></title>
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>

<body style="background-color: #f2f2f2;">
<div class="jf_super_wrap">
  <!-- 图表 S -->
  <div class="col-1 row-1">
    <div class="chart_wrap">
        <h3 class="chart_title">${container01seriesName}-${container01Link}时效-同比分析</h3>
        <p class="tips02">周期：${container01Time}</p>
        <span class="switch02 switch_list"><i>数据</i></span>
        <div class="chart_col01" id="container01"></div>
        <div style="display: none;" class="list_wrap01 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable">
                <tbody>
                    <tr>
                    	<th>周期</th>
                    	<th>
	                    	<c:choose>
	                    		<c:when test="${dimensionKey=='department'}">部门</c:when>
	                    		<c:when test="${dimensionKey=='parent_cos'||dimensionKey=='cos'}">费用</c:when>
	                    		<c:when test="${dimensionKey=='problem'}">问题类型</c:when>
	                    	</c:choose>
                    	</th>
                    	<th>单据总量</th>
                    	<th>总耗时(工作日)</th>
                    	<th>平均时效(工作日)</th>
                    	<th>同比</th>
                    </tr>
                    <c:forEach items="${container01List}" var="item" varStatus="i">
	                    <tr>
	                        <td>${item[0]}</td>
	                        <td>${item[2]}</td>
	                        <td>${item[3]}</td>
	                        <td>${item[4]}</td>
	                        <td>${item[5]}</td>
	                        <td>${item[6]}</td>
	                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
  </div>
  <div class="col-1 row-1">
    <div class="chart_wrap">
        <h3 class="chart_title">${container02seriesName}-${container02Link}时效-环比分析</h3>
        <p class="tips02">周期：${container02Time}</p>
        <span class="switch02 switch_list"><i>数据</i></span>
        <div class="chart_col01" id="container02"></div>
        <div style="display: none;" class="list_wrap01 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable">
                <tbody>
                    <tr>
                    	<th>周期</th>
                    	<th>
	                    	<c:choose>
	                    		<c:when test="${dimensionKey=='department'}">部门</c:when>
	                    		<c:when test="${dimensionKey=='parent_cos'||dimensionKey=='cos'}">费用</c:when>
	                    		<c:when test="${dimensionKey=='problem'}">问题类型</c:when>
	                    	</c:choose>
                    	</th>
                    	<th>单据总量</th>
                    	<th>总耗时(工作日)</th>
                    	<th>平均时效(工作日)</th>
                    	<th>环比</th>
                    </tr>
                    <c:forEach items="${container02List}" var="item" varStatus="i">
	                    <tr>
	                        <td>${item[0]}</td>
	                        <td>${item[2]}</td>
	                        <td>${item[3]}</td>
	                        <td>${item[4]}</td>
	                        <td>${item[5]}</td>
	                        <td>${item[6]}</td>
	                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
  </div>
  <c:if test="${link=='3'}">
	  <div class="col-1 row-1">
	    <div class="chart_wrap">
	        <h3 class="chart_title">${container03seriesName}-问题单据占比-同比分析</h3>
	        <p class="tips02">周期：${container03Time}</p>
	        <span class="switch02 switch_list"><i>数据</i></span>
	        <div class="chart_col01" id="container03"></div>
	        <div style="display: none;" class="list_wrap01 scroll">
	        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
	            <table class="listTable">
	                <tbody>
	                    <tr>
	                    	<th>周期</th>
	                    	<th>
		                    	<c:choose>
		                    		<c:when test="${dimensionKey=='department'}">部门</c:when>
		                    		<c:when test="${dimensionKey=='parent_cos'||dimensionKey=='cos'}">费用</c:when>
		                    		<c:when test="${dimensionKey=='problem'}">问题类型</c:when>
		                    	</c:choose>
	                    	</th>
	                    	<th>单据总量</th>
	                    	<th>问题单总量</th>
	                    	<th>问题单据占比(%)</th>
	                    	<th>占总问题单的百分百(%)</th>
	                    	<th>同比</th>
	                    </tr>
	                    <c:forEach items="${container03List}" var="item" varStatus="i">
		                    <tr>
		                        <td>${item[0]}</td>
		                        <td>${item[2]}</td>
		                        <td>${item[3]}</td>
		                        <td>${item[4]}</td>
		                        <td>${item[6]}</td>
		                        <td>${item[7]}</td>
		                        <td>${item[8]}</td>
		                    </tr>
	                    </c:forEach>
	                </tbody>
	            </table>
	        </div>
	    </div>
	  </div>
	  <div class="col-1 row-1">
	    <div class="chart_wrap">
	        <h3 class="chart_title">${container04seriesName}-问题单据占比-环比分析</h3>
	        <p class="tips02">周期：${container04Time}</p>
	        <span class="switch02 switch_list"><i>数据</i></span>
	        <div class="chart_col01" id="container04"></div>
	        <div style="display: none;" class="list_wrap01 scroll">
	        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
	            <table class="listTable">
	                <tbody>
	                    <tr>
	                    	<th>周期</th>
	                    	<th>
		                    	<c:choose>
		                    		<c:when test="${dimensionKey=='department'}">部门</c:when>
		                    		<c:when test="${dimensionKey=='parent_cos'||dimensionKey=='cos'}">费用</c:when>
		                    		<c:when test="${dimensionKey=='problem'}">问题类型</c:when>
		                    	</c:choose>
	                    	</th>
	                    	<th>单据总量</th>
	                    	<th>问题单总量</th>
	                    	<th>问题单据占比(%)</th>
	                    	<th>占总问题单的百分百(%)</th>
	                    	<th>环比</th>
	                    </tr>
	                    <c:forEach items="${container04List}" var="item" varStatus="i">
		                    <tr>
		                        <td>${item[0]}</td>
		                        <td>${item[2]}</td>
		                        <td>${item[3]}</td>
		                        <td>${item[4]}</td>
		                        <td>${item[6]}</td>
		                        <td>${item[7]}</td>
		                        <td>${item[8]}</td>
		                    </tr>
	                    </c:forEach>
	                </tbody>
	            </table>
	        </div>
	    </div>
	  </div>
  </c:if>
  <!-- 图表 E -->
</div>
<script src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/dc/js/hchart/highcharts.js"></script>
<script src="/SRMC/dc/js/hchart/modules/exporting.js"></script>
<script src="/SRMC/dc/account/js/Account3rd.js"></script>
<script type="text/javascript" >var base_url="<%=basePath%>";</script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/exportExcel.js"></script>
<script type="text/javascript">
$(function() {
    Highcharts.setOptions({
        colors: ['#77cdfe', '#2297d9'],
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
            enabled: true,
            itemStyle: {
                color: '#a5a5a5',
                fontWeight: 'normal',
                fontFamily: '微软雅黑'
            }
        },
        credits: {
            enabled: false
        }
    });
    // 图表 1
    $('#container01').highcharts({
    	exporting:exporting,
        chart: {
            type: 'column'
        },
        title: {
            text: false
        },
        xAxis: {
            categories: ${container01xAxis},
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: '工作日'
            }
        },
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true,
                    style:{"color": "#77cdfe", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
                }
            }
        },
        series: [{
            color: '#77cdfe',
            name: '${container01seriesName}',
            data: ${container01series}

        }]
    });
    $('#container02').highcharts({
    	exporting:exporting,
        chart: {
            type: 'column'
        },
        title: {
            text: false
        },
        xAxis: {
            categories: ${container02xAxis},
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: '工作日'
            }
        },
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true,
                    style:{"color": "#77cdfe", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
                }
            }
        },
        series: [{
            color: '#77cdfe',
            name: '${container02seriesName}',
            data: ${container02series}

        }]
    });
    
    $('#container03').highcharts({
    	exporting:exporting,
        chart: {
            type: 'column'
        },
        title: {
            text: false
        },
        xAxis: {
            categories: ${container03xAxis},
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: '百分比'
            }
        },
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true,
                    style:{"color": "#77cdfe", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
                }
            }
        },
        series: [{
            color: '#77cdfe',
            name: '${container03seriesName}',
            data: ${container03series}

        }]
    });
    $('#container04').highcharts({
    	exporting:exporting,
        chart: {
            type: 'column'
        },
        title: {
            text: false
        },
        xAxis: {
            categories: ${container04xAxis},
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: '百分比'
            }
        },
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true,
                    style:{"color": "#77cdfe", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
                }
            }
        },
        series: [{
            color: '#77cdfe',
            name: '${container04seriesName}',
            data: ${container04series}

        }]
    });
});

</script>                
</body>
</html>
