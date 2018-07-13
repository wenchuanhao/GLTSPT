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
<script type="text/javascript">
var baseURL = "<%=basePath%>";
var cMon = "${cMon}";
var cSc = "${cSc}";
</script>
</head>

<body style="background-color: #f2f2f2;">
<div class="jf_super_wrap">
  <!-- 图表 S -->
  <!-- 左侧图表 S -->
  <div class="col-2 row-1">
    <div class="chart_wrap">
        <h3 class="chart_title">全流程时效</h3>
        <p class="tips01">周期：${zq}</p>
        <div class="chart_col02" id="container1"></div>
    </div>
  </div>
   <!-- 左侧图表 E -->
  <!-- 右侧图表 S -->
    <div class="col-2 row-1">
    <div class="chart_wrap">
        <h3 class="chart_title">全流程时效</h3>
        <span class="switch02"><i>列表</i></span>
        <div class="chart_col02" id="container5"></div>
        <div style="display: none;" class="list_wrap01 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable">
                <tbody>
                    <tr>
                        <th width="90px">周期</th>
                        <th>环节</th>
                        <th>平均时长</th>
                        <th>总平均时长</th>
                        <th>单据量</th>
                    </tr>
                    <c:forEach items="${container5_list}" var="vo" varStatus="i">
                    <tr>
                        <td>${vo.ZQ}</td>
                        <td>${vo.HJ_NAME}</td>
                        <td>${vo.HJ_PJSC}</td>
                        <td>${vo.ZPJSC}</td>
                        <td>${vo.DJL}</td>
                    </tr>                    
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
  </div>
  
  <!-- 右侧图表 E -->
  <!-- 图表 E -->
  <div class="col-1 row-1">
    <div class="chart_wrap">
        <h3 class="chart_title">全流程时效-全视图</h3>
        <span class="switch02"><i>列表</i></span>
        <div class="chart_col01" id="container4"></div>
        <div style="display: none;" class="list_wrap01 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable">
                <tbody>
                    <tr>
                    	<th>部门</th>
                    	<th>类型</th>
                        <th width="90px">周期</th>
                        <th>发起报账</th>
                        <th>业务审批</th>
                        <th>问题单据整改</th>
                        <th>南基财务审批</th>
                        <th>基地领导审批</th>
                        <th>省财务审批</th>
                        <th>出纳支付</th>
                        <th>单据量</th>
                    </tr>
                    <c:forEach items="${container4_list}" var="vo" varStatus="i">
                    <tr>
                    	<td>${vo.DEPARTMENT}</td>
                    	<td>${vo.TYPE_NAME}</td>
                        <td>${vo.ZQ}</td>
                        <td>${vo.HJ_1_SC}</td>
                        <td>${vo.HJ_2_SC}</td>
                        <td>${vo.HJ_3_SC}</td>
                        <td>${vo.HJ_4_SC}</td>
                        <td>${vo.HJ_5_SC}</td>
                        <td>${vo.HJ_6_SC}</td>
                        <td>${vo.HJ_7_SC}</td>
                        <td>${vo.DJL}</td>
                    </tr>                    
                    </c:forEach>                        
                </tbody>
            </table>
        </div>
    </div>
  </div>


  <div class="col-2 row-2">
    <div class="chart_wrap">
        <h3 class="chart_title">全流程时效-同比分析</h3>
        <p class="tips02">周期：${zq}</p>
        <span class="switch02"><i>列表</i></span>
        <div class="chart_col03" id="container2"></div>
        <div style="display: none;" class="list_wrap03 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable">
                <tbody>
                    <tr>
                        <th width="30px">周期</th>
                        <th style="width: 47px;">发起报账</th>
                        <th style="width: 47px;">业务审批</th>
                        <th style="width: 70px;">问题单据整改</th>
                        <th style="width: 70px;">南基财务审批</th>
                        <th style="width: 70px;">基地领导审批</th>
                        <th style="width: 60px;">省财务审批</th>
                        <th style="width: 60px;">出纳支付</th>
                        <th style="width: 33px;">单据量</th>
                    </tr>
                    <c:forEach items="${container2_list}" var="vo" varStatus="i">
                    <tr>
                        <td>${vo.ZQ}</td>
                        <td>${vo.HJ_1_BFB}%</td>
                        <td>${vo.HJ_2_BFB}%</td>
                        <td>${vo.HJ_3_BFB}%</td>
                        <td>${vo.HJ_4_BFB}%</td>
                        <td>${vo.HJ_5_BFB}%</td>
                        <td>${vo.HJ_6_BFB}%</td>
                        <td>${vo.HJ_7_BFB}%</td>
                        <td>${vo.DJL}</td>
                    </tr>                    
                    </c:forEach>                    
                </tbody>
            </table>
        </div>
    </div>
    </div>
<div class="col-2 row-2">
    <div class="chart_wrap" style="margin-bottom: 0">
        <h3 class="chart_title">全流程时效-环比分析</h3>
        <p class="tips02">周期：${zq}</p>
        <span class="switch02"><i>列表</i></span>
        <div class="chart_col03" id="container3"></div>
        <div style="display: none;" class="list_wrap03 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable">
                <tbody>
                    <tr>
                        <th width="90px">周期</th>
                        <th style="width: 47px;">发起报账</th>
                        <th style="width: 47px;">业务审批</th>
                        <th style="width: 70px;">问题单据整改</th>
                        <th style="width: 70px;">南基财务审批</th>
                        <th style="width: 70px;">基地领导审批</th>
                        <th style="width: 60px;">省财务审批</th>
                        <th style="width: 50px;">出纳支付</th>
                        <th style="width: 33px;">单据量</th>
                    </tr>
                    <c:forEach items="${container3_list}" var="vo" varStatus="i">
                    <tr>
                        <td>${vo.ZQ}</td>
                        <td>${vo.HJ_1_BFB}%</td>
                        <td>${vo.HJ_2_BFB}%</td>
                        <td>${vo.HJ_3_BFB}%</td>
                        <td>${vo.HJ_4_BFB}%</td>
                        <td>${vo.HJ_5_BFB}%</td>
                        <td>${vo.HJ_6_BFB}%</td>
                        <td>${vo.HJ_7_BFB}%</td>
                        <td>${vo.DJL}</td>
                    </tr>                    
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
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

<script src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/dc/js/hchart/highcharts.js"></script>
<script src="/SRMC/dc/js/hchart/modules/exporting.js"></script>
<script src="/SRMC/dc/js/jquery.nicescroll.js"></script>
<script type="text/javascript" >var base_url="<%=basePath%>";</script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/exportExcel.js"></script>
<script type="text/javascript">
$(function() {
    Highcharts.setOptions({
        colors:  ['#24CBE5', '#50B432', '#ED561B','#DDDF00',  '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
        exporting : {
			buttons : {
				contextButton : {
					menuItems : [ {
						text : '导出PNG',
						onclick : function() {
							this.exportChart({
								type : "image/png"
							});
						}
					}, {
						text : '导出JPEG',
						onclick : function() {
							this.exportChart({
								type : "image/jpeg"
							});
						},
						separator : false
					}, {
						text : '导出PDF',
						onclick : function() {
							this.exportChart({
								type : "application/pdf"
							});
						},
						separator : false
					}, {
						text : '导出SVG',
						onclick : function() {
							this.exportChart({
								type : "image/svg+xml"
							});
						},
						separator : false
					} ]
				}
			}
		},
        // 按钮样式
        navigation: {
	        buttonOptions: {
	            height: 26,
	            width: 30,
	            symbolSize: 18,
	            symbolX: 15,
	            symbolY: 13,
	            symbolStrokeWidth: 2,
	            y:-10
	        }
        },
        legend: {
            layout: 'vertical',
	        align: 'right',
	        verticalAlign: 'bottom',
	        itemMarginTop: 10,
	        enabled: true,
	        itemStyle: {
	            color: '#a5a5a5',
	            fontSize:'12px',
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
    
    // 图表 1
	HC.container1();

    // 图表 2
	HC.container2();
    
    // 图表 3
    HC.container3();
    
    // 图表 4
    HC.container4();
    
     // 图表 5
    HC.container5();
});

/**
 * highcharts js
 * @author weifei
 * @date 2016-6-27 下午2:37:57
 */
var HC = {

	// 图表 1
	container1:function(){
			    $('#container1').highcharts({
			        chart: {            
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false
// 			            margin:[20,20,80,20]
			        },
			        exporting: {
			            enabled: false
			        },
			        title: {
			            text: ' '
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                showInLegend: true,
			                dataLabels: {
			                    distance: -30,
			                    format: '{y}%',
			                    style:{"color": "black", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}      
			                }
			            },
			            series: {
			                point: {
			                    events: {
			                        'click': function() {
			                        	initSndLevel(this,'1');
			                        }
			                    }
			                }
			            }
			        },
			        series: [{
			            type: 'pie',
			            name: '百分比',
			            data: [${container1}]
			        }]
			    });	
	},

	//图表2
	container2:function(){
			   $("#container2").highcharts({
			       chart: {
			           type: 'column'
			       },
			       title: {
			           text: false
			       },
			       xAxis: {
			           categories: [${container_key}],
			           crosshair: true,
			           labels: {
			                rotation: -45
			           }
			       },
			       yAxis: {
			           min: 0,
			           title: {
			               text: '统计:全流程时效'	
			           }
			       },
			       tooltip: { //鼠标提示
			           formatter: function() {
			               return this.series.name+ this.x + '为' + Highcharts.numberFormat(this.y, 2, null, ' ')+'%';
			           }
			       },
			       plotOptions: {
			       	series: {
				        point: {
				        	events: {
					            click: function(){
						            // 显示或隐藏图表
						            parent.$('.dark').show();
						            parent.$('.zoomchart').show();
						            parent.$("#same_container3").hide();
						            parent.$("#same_container2").show();
						            HC.container2D();
					            }
				            }
				        },
				        borderWidth: 0,
		                dataLabels: {
		                    enabled: true,
		                    style:{"color": "#77cdfe", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
		                }
				     }
			       },
			       series: [${container2_value}]
			   });	
	},
	
	//图表2放大
	container2D:function(){
			  parent.$("#same_container2").highcharts({
			       chart: {
			           type: 'column'
			       },
			       exporting : {
						buttons : {
							contextButton : {
								menuItems : [ {
									text : '导出PNG',
									onclick : function() {
										this.exportChart({
											type : "image/png"
										});
									}
								}, {
									text : '导出JPEG',
									onclick : function() {
										this.exportChart({
											type : "image/jpeg"
										});
									},
									separator : false
								}, {
									text : '导出PDF',
									onclick : function() {
										this.exportChart({
											type : "application/pdf"
										});
									},
									separator : false
								}, {
									text : '导出SVG',
									onclick : function() {
										this.exportChart({
											type : "image/svg+xml"
										});
									},
									separator : false
								} ]
							}
						}
					},
			       title: {
			           text: false
			       },
			       xAxis: {
			           categories: [${container_key}],
			           crosshair: true,
			           labels: {
			                rotation: -45
			           }
			       },
			       yAxis: {
			           min: 0,
			           title: {
			               text: '统计:全流程时效'	
			           }
			       },
			       tooltip: { //鼠标提示
			           formatter: function() {
			               return this.series.name+ this.x + '为' + Highcharts.numberFormat(this.y, 2, null, ' ')+'%';
			           }
			       },
			       plotOptions: {
			       	series: {
				        point: {
				        	events: {
					            click: function(){
						            //alert();	
					            }
				            }
				        },
				        borderWidth: 0,
		                dataLabels: {
		                    enabled: true,
		                    style:{"color": "#77cdfe", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
		                }
				     }
				     
			       },
			       series: [${container2_value}]
			   });	
	},	
	
	//图表3
	container3:function(){
			    $('#container3').highcharts({
			        chart: {
			            type: 'column'
			        },
			        title: {
			            text: false
			        },
			        xAxis: {
			            categories: [${container_key}],
			            crosshair: true,
			            labels: {
			                rotation: -45
			           }
			        },
			        yAxis: {
			            min: 0,
			            title: {
			                text: '统计:全流程时效'
			            }
			        },
			        tooltip: { //鼠标提示
			            formatter: function() {
			                return this.series.name+ this.x + '为' + Highcharts.numberFormat(this.y, 2, null, ' ')+'%';
			            }
			        },
			       plotOptions: {
			       	series: {
				        point: {
				        	events: {
					            click: function(){
						            // 显示或隐藏图表
						            parent.$('.dark').show();
						            parent.$('.zoomchart').show();
						            parent.$("#same_container2").hide();
						            parent.$("#same_container3").show();
						            HC.container3D();
					            }
				            }
				        },
				        borderWidth: 0,
		                dataLabels: {
		                    enabled: true,
		                    style:{"color": "#77cdfe", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
		                }
				     }
			       },			        
			        series: [${container3_value}]
			    });	
	},

	//图表3放大
	container3D:function(){
			   parent.$("#same_container3").highcharts({
			       chart: {
			           type: 'column'
			       },
			       exporting : {
						buttons : {
							contextButton : {
								menuItems : [ {
									text : '导出PNG',
									onclick : function() {
										this.exportChart({
											type : "image/png"
										});
									}
								}, {
									text : '导出JPEG',
									onclick : function() {
										this.exportChart({
											type : "image/jpeg"
										});
									},
									separator : false
								}, {
									text : '导出PDF',
									onclick : function() {
										this.exportChart({
											type : "application/pdf"
										});
									},
									separator : false
								}, {
									text : '导出SVG',
									onclick : function() {
										this.exportChart({
											type : "image/svg+xml"
										});
									},
									separator : false
								} ]
							}
						}
					},
			       title: {
			           text: false
			       },
			       xAxis: {
			           categories: [${container_key}],
			           crosshair: true,
			           labels: {
			                rotation: -45
			           }
			       },
			       yAxis: {
			           min: 0,
			           title: {
			               text: '统计:全流程时效'	
			           }
			       },
			       tooltip: { //鼠标提示
			           formatter: function() {
			               return this.series.name+ this.x + '为' + Highcharts.numberFormat(this.y, 2, null, ' ')+'工作日';
			           }
			       },
			       plotOptions: {
			       	series: {
				        point: {
				        	events: {
					            click: function(){
						            //alert();	
					            }
				            }
				        }
				     },
				    borderWidth: 0,
	                dataLabels: {
	                    enabled: true,
	                    style:{"color": "#77cdfe", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
	                }
			       },
			       series: [${container3_value}]
			   });	
	},	
	
	//图表4
	container4:function(){
			   $('#container4').highcharts({
			        chart: {
			            type: 'column'
			        },
			        title: {
			            text: false
			        },
			        tooltip: { //鼠标提示
			            formatter: function() {
			                return this.x + this.series.name + '为' + Highcharts.numberFormat(this.y, 2, null, ' ') + '工作日';
			            }
			        },
			        yAxis: {
			            allowDecimals: false,
			            min: 0,
			            title: {
			                text: ''
			            }            
			        },        
			        plotOptions: {
			            column: {
			                stacking: 'normal',
			                dataLabels: {
			                    enabled: true,
			                    color: 'black',
			                    style: {
			                        textShadow: false
			                    }
			                }
			             }
			        },
			        xAxis: {
			            categories: [${container4_key}]
			        },        
			        series: [${container4_value}]
			       
			    });
	},
	
	//图表5
	container5:function(){
			    $('#container5').highcharts({
			        chart: {
			            type: 'column'
			        },
			        title: {
			            text: false
			        },
			        xAxis: {
			            categories: [
			                ${container5_key}
			            ],
			            crosshair: true,
			            labels: {
			                rotation: -45
			           }
			        },
			        yAxis: {
			            min: 0,
			            title: {
			                text: '统计：全流程时效(工作日)'
			            }
			        },
			        plotOptions: {
			            column: {
			                pointPadding: 0.2,
			                borderWidth: 0
			            }
			            ,
			            series: {
			                point: {
			                    events: {
			                        'click': function() {
			                        	initSndLevel(this,'2');
			                        }
			                    }
			                },
			                borderWidth: 0,
			                dataLabels: {
			                    enabled: true,
			                    style:{"color": "#77cdfe", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
			                }
			            }
			        },
			        series: [{
			            color: '#40baff',
			            name: '${zq}',
			            data: [${container5_value}]
			        }]
			    });		
	}
	
	
}


parent.$('.chart_close').click(function(){
	var target = $(this);
	$(target).parent().parent().find('.dark').hide();
	$(target).parent().hide();
    parent.$('.zoomchart').hide();
    parent.$('.dark').hide();
});

// 数据与列表切换
$('.switch02').click(function(){
  $(this).toggleClass('switch_list');
  if($(this).children('i').html()=='数据'){
    $(this).children('i').html('列表');
    $(this).prev().css('right',82+'px');
    $(this).next().show();
    $(this).next().next().hide();
  }else{
    $(this).children('i').html('数据');
    $(this).prev().css('right',140+'px');
    $(this).next().next().show();
    $(this).next().hide();
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

/**
 * 钻取至二级页面
 * @param target
 */
function initSndLevel(target,value){
	if(value=='1'){
		window.parent.location.href = baseURL + '/account/launchAging?linkName='+target.name+"&startTime="+"${startDate}"+'&endTime='+"${endDate}";
	}else{
		window.parent.location.href = baseURL + '/account/launchAging?linkName='+target.category+"&startTime="+"${startDate}"+'&endTime='+"${endDate}";
	}
	
}


</script>                
</body>
</html>
