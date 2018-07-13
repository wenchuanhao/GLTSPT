var credits = {
		enabled: false
	};
var	exporting = {
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
	};
//定义图表颜色
var colors = Highcharts.getOptions().colors;


//数据与列表切换
$('.switch02').click(function(){
  $(this).toggleClass('switch_list');
  if($(this).children('i').html()=='数据'){
    $(this).children('i').html('图表');
    $(this).prev().css('right',140+'px');
    $(this).next().hide();
    $(this).next().next().show();
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

/**
 * 图表一、二、五的初始化
 * @param type
 * @param titleText
 * @param subtitleText
 * @param yAxisText
 * @param containerId
 * @param dataName1
 * @param dataName2
 * @param target
 */
function initContainerFor125(type,titleText,subtitleText,yAxisText,containerId,dataName1,dataName2,target){
	jQuery.ajax({
		type:"POST",
		async:true,
		url:basePath+"/getSeriesData",
		data:"type="+containerId+"&year="+year,
		dataType: "json",
		success:function(data){
			//实际
			var data1 = [];
			//目标
			var data2 = [];
			$.each(data,function(k,v){
				if(v[0] < v[1]){
					data1.push({y:v[0], marker: {fillColor: '#FF0000',lineWidth: 2,radius: 5} });
					data2.push(v[1]);
				}else{
					data1.push(v[0]);
					data2.push(v[1]);
				}
			});
			var temp = [
				{
					name: dataName1,
					color:colors[0],
					marker: { symbol: 'square' },
					data: data1
				},
				{
					name: dataName2, 
					color:colors[2],
					marker: { symbol: 'diamond' }, 
					data:data2
				}
			];
			
			var config = {
					credits:credits,
					exporting:exporting,
			        chart: {
			            type: type
			        },
			        title: {
			            text: ''//titleText
			        },
					subtitle:{
						text:''//subtitleText
					},
			        xAxis: {
			            categories: ['1月', '2月', '3月', '4月', '5月','6月','7月', '8月', '9月', '10月', '11月','12月']
			        },
					yAxis: {
						title: {
							text: subtitleText+"("+yAxisText+")"
						}, 
						labels: {
								formatter: function() {
									return this.value +'';
								} 
						}
					}, 
					tooltip: { 
						crosshairs: true,
						shared: true
					}, 
					plotOptions: { 
						line: {
							marker: {
								radius: 4,
								lineColor: '#666666',
								lineWidth: 1 
							}
						},
						series: {
			                point: {
			                    events: {
			                        'click': function() {
			                        	initSndLevel(this,containerId);
			                        }
			                    }
			                }
			            }
					},
					series: temp
				};
			$(target).highcharts(config);
		},
		error:function(){
            //alert("同步失败！");
        }
	});
}


/**
 * 获取图表三的数据，成本使用情况
 * @param type
 * @param dataName1
 * @param dataName2
 * @returns {Array}
 */
function initContainerFor03(type){
	jQuery.ajax({
		type:"POST",
		async:true,
		url:basePath+"/getSeriesData",
		data:"type="+type+"&year="+year,
		dataType: "json",
		success:function(data){
			
			//上月累计
			var data1 = [];
			//本月成本
			var data2 = [];
			//累计成本
			var data3 = [];
			
			$.each(data,function(k,v){
				data1.push(v[1]);
				data2.push(v[0]);
				data3.push(v[2]);
			});
			
			var temp = [{
			            name: '上月累计',
			            data: data1,
						color:colors[2],
			        },{
			            name: '本月成本',
			            data: data2,
						color:colors[0],
			        },{
						type:'line',
			            name: '累计成本',
			            data: data3,
						color:colors[3]
			        }];
			var config = {
					credits:credits,
					exporting:exporting,
			        chart: {
			            type: 'column'
			        },
			        title: {
			            text: ''//'成本使用情况'
			        },
					subtitle:{
						text:''//'全年成本'
					},
			        xAxis: {
			            categories: ['1月', '2月', '3月', '4月', '5月','6月','7月', '8月', '9月', '10月', '11月','12月']
			        },
			        yAxis: [{
			            labels: {
			                format: '{value}万元',
			                style: {
			                    color: '#89A54E'
			                }
			            },
						title: {
			                text: '全年成本(万元)',
			                style: {
			                    color: '#89A54E'
			                }
			            },
			            stackLabels: {
				                enabled: true,
				                style: {
				                	"color": "#77cdfe", "fontSize": "12px", "fontWeight": "normal", "textShadow":false
				                }
				            }
				        }
					],
			        plotOptions: {
			            column: {
			                stacking: 'normal',
			                dataLabels: {  enabled: false, color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'black'
			                }
			            },
						series: {
			                point: {
			                    events: {
			                        'click': function() {
			                        	initSndLevel(this,"container03");
			                        }
			                    }
			                }
			            }
			        },
			        series: temp
			    }
			$('#container03').highcharts(config);
		},
		error:function(){
            //alert("同步失败！");
        }
	});
}

/**
 * 获取图表四的数据，人均成本使用情况
 * @param type
 * @param dataName1
 * @param dataName2
 * @returns {Array}
 */
function initContainerFor04(type){
	jQuery.ajax({
		type:"POST",
		async:true,
		url:basePath+"/getSeriesData",
		data:"type="+type+"&year="+year,
		dataType: "json",
		success:function(data){
			
			var temp = [
				{name: '人餐宴会', 
				color:colors[0],
				type:'line',
				visible: true,//默认checkbox勾选
				data: data[0] },
				{name: '人餐食材', 
				color:colors[2],
				visible: false,//默认不显示
				data: data[1] },
				{name: '房晚服务', 
				color:colors[3],
				visible: false,//默认不显示
				data: data[2] },
				{name: '房晚成本', 
				color:colors[4],
				visible: false,//默认不显示
				data: data[3] },
				{name: '人均会场服务', 
				color:colors[5],
				visible: false,//默认不显示
				data: data[4] },
				{name: '人均会场', 
				color:colors[6],
				visible: false,//默认不显示
				data: data[5] }
				];
			
			$('#container04').highcharts({
				credits:credits,
				exporting:exporting,
		        chart: {
		            type: 'line'
		        },
		        title: {
		            text: ''//'人均成本使用情况'
		        },
				subtitle:{
					text:''//'人均成本'
				},
		        xAxis: {
		            categories: ['1月', '2月', '3月', '4月', '5月','6月','7月', '8月', '9月', '10月', '11月','12月']
		        },
				yAxis: { 
					title: { 
						text: '人均成本(团队)' }, 
						labels: { 
							formatter: function() { 
								return this.value +''; } 
								}  }, 
				tooltip: { 
								crosshairs: true, shared: true }, 
				 plotOptions: {
				 	series: { 
						showCheckbox: false,
						point: {
		                    events: {
		                        'click': function() {
		                        	initSndLevel(this,"container04");
		                        }
		                    }
		                }
					}, 
					line:{
//						events :{
//							checkboxClick: function(event) {
//								 if(event.checked==true) {
//								 	 this.show();
//								 } else {
//								 	this.hide();
//								 }
//							},
//							legendItemClick:function(event) {//return false 即可禁用LegendIteml，防止通过点击item显示隐藏系列 
//								return false; 
//							}
//						}
					}
				 },
				series: temp
			});
		},
		error:function(){
			//alert("同步失败！");
		}
	});
}

/**
 * 获取图表六的数据，满意度调查情况-部门维度
 * @param type
 * @param dataName1
 * @param dataName2
 * @returns {Array}
 */
function initContainerFor06(type,titleText,subtitleText,yAxisText,container){
	
	jQuery.ajax({
		type:"POST",
		async:true,
		url:basePath+"/getSeriesData",
		data:"type="+container+"&year="+year,
		dataType: "json",
		success:function(data){
			
			//平均满意度,前台,安保,宴会,会场,客房,指标值
			var data1 = data[0],data2 = data[1],data3 = data[2],data4 = data[3],data5 = data[4],data6 = data[5],data7 = data[6];
			var flag1 = true,flag2 = false,flag3 = false,flag4 = false,flag5 = false,flag6 = false,flag7 = false;
			
			$.each(data[6],function(k,v){
				
				if(data1[k] < v){
					data1[k] = {
							y:data1[k],
							marker: {
								fillColor: '#FF0000',lineWidth: 2,radius: 5
							}
						};
				}
				if(data2[k] < v){
					data2[k] = {
							y:data2[k],
							marker: {
								fillColor: '#FF0000',lineWidth: 2,radius: 5
							}
					};
					flag2 = true;
				}
				if(data3[k] < v){
					data3[k] = {
							y:data3[k],
							marker: {
								fillColor: '#FF0000',lineWidth: 2,radius: 5
							}
					};
					flag3 = true;
				}
				if(data4[k] < v){
					data4[k] = {
							y:data4[k],
							marker: {
								fillColor: '#FF0000',lineWidth: 2,radius: 5
							}
					};
					flag4 = true;
				}
				if(data5[k] < v){
					data5[k] = {
							y:data5[k],
							marker: {
								fillColor: '#FF0000',lineWidth: 2,radius: 5
							}
					};
					flag5 = true;
				}
				if(data6[k] < v){
					data6[k] = {
							y:data6[k],
							marker: {
								fillColor: '#FF0000',lineWidth: 2,radius: 5
							}
					};
					flag6 = true;
				}
				
			});
			
			var temp = {
					plotLines:{
						value:0,
						text:''
					},
					series:[]
				};
			
			temp.plotLines.value = data7[0];
			temp.plotLines.text = data7[0]+'%';
			
			temp.series = [
			        {
			        	name: '平均满意度',
						type:'line',
						color:colors[0],
						selected: flag1,//默认checkbox勾选
						data: data1,
						tooltip: {
			                valueSuffix: ' %'
			            }
			        },
			        {
			        	name: '前台礼宾', 
						color:colors[2],
						visible: flag2,//默认不显示
						selected: flag2,
						data: data2
			        },
			        {
			        	name: '安保', 
						color:colors[3],
						visible: flag3,//默认不显示
						selected: flag3,
						data: data3
			        },
			        {
			        	name: '餐饮', 
						color:colors[4],
						visible: flag4,//默认不显示
						selected: flag4,
						data: data4
			        },
			        {
			        	name: '会议', 
						color:colors[5],
						visible: flag5,//默认不显示
						selected: flag5,
						data: data5
			        },
			        {
			        	name: '客房', 
						color:colors[6],
						visible: flag6,//默认不显示
						selected: flag6,
						data: data6
			        }
				] ;
			var config = {
					credits:credits,
					exporting:exporting,
			        chart: {
			            type: type, zoomType: 'y' 
			        },
			        title: {
			            text: ''//titleText
			        },
					subtitle:{
						text:''//subtitleText
					},
			        xAxis: {
			            categories: ['1月', '2月', '3月', '4月', '5月','6月','7月', '8月', '9月', '10月', '11月','12月']
			        },
					yAxis: { 
						title: { 
							text: subtitleText+"("+yAxisText+")" }, 
						labels: { 
								formatter: function() { 
									return this.value +'' } 
									},
						plotLines:[{
			                color:'#b45f21',            //线的颜色，定义为红色
			                dashStyle:'solid',     //默认是值，这里定义为长虚线
			                value:temp.plotLines.value,              //定义在那个值上显示标示线，这里是在x轴上刻度为3的值处垂直化一条线
			                width:2,               //标示线的宽度，2px
			                label:{
			                    text: temp.plotLines.text,     //标签的内容
			                    align:'left',                //标签的水平位置，水平居左,默认是水平居中center
			                    x:10,                         //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
			                    style:{
			                        fontSize:'14px',
			                        fontWeight:'bold'
			                    }
			                },
			                events:{
			                    click:function(e){
			                        alert("yAxis plotLine Clicked!");
			                        //当标示线被单击时，触发的事件
			                    },
			                    mouseover:function(){
			                        console.log("yAxis plotLine Hovered!");
			                        //当标示线被鼠标悬停时，触发的事件
			                    },
			                
			                    mouseout:function(){
			                        //当标示线被鼠标移出时，触发的事件
			                    },
			                    
			                    mousemove:function(){
			                        //当标示线被鼠标移动(时，触发的事件
			                    }
			                }
			            }]
					}, 
					tooltip: { 			crosshairs: true, shared: true,valueSuffix: ' %' }, 
					plotOptions: {
						series: { 
							showCheckbox: false
						}, 
						line:{
							events :{
//								checkboxClick: function(event) {
//									 if(event.checked==true) {
//									 	 this.show();
//									 } else {
//									 	this.hide();
//									 }
//								},
//								legendItemClick:function(event) {//return false 即可禁用LegendIteml，防止通过点击item显示隐藏系列 
//									return false; 
//								}
							}
						},
						series: {
			                point: {
			                    events: {
			                        'click': function() {
			                        	initSndLevel(this,'container06');
			                        }
			                    }
			                }
			            },
						 scatter: { 
								events :{
//									checkboxClick: function(event) {
//										 if(event.checked==true) {
//										 	 this.show();
//										 } else {
//										 	this.hide();
//										 }
//									},
//									legendItemClick:function(event) {//return false 即可禁用LegendIteml，防止通过点击item显示隐藏系列 
//										return false; 
//									}
								},
								marker: { radius: 5, states: { hover: { enabled: true, lineColor: 'rgb(100,100,100)' } } }, 
						states: { hover: { marker: { enabled: false } } }, 
						tooltip: { headerFormat: '{point.x}  <br><b>{series.name}</b>: {point.y} %', pointFormat: '' } } 
						},
					series: temp.series 
				};
			
			$('#container06').highcharts(config);
		},
		error:function(){
			//alert("同步失败！");
		}
	});
}

/**
 * 获取图表六的数据，满意度调查情况-主题维度
 * @param type
 * @param dataName1
 * @param dataName2
 * @returns {Array}
 */
function initContainerFor0602(type,titleText,subtitleText,yAxisText,container){
	
	jQuery.ajax({
		type:"POST",
		async:true,
		url:basePath+"/getSeriesData",
		data:"type="+container+"&year="+year,
		dataType: "json",
		success:function(data){
			
			//标题
			var title = data.title;
			//数据
			var list = data.list;
			
			var temp = {
					plotLines:{
						value:0,
						text:''
					},
					series:[]
			};
			//指标值
			temp.plotLines.value = data.target[0][1] * 10;
			temp.plotLines.text = (data.target[0][1] * 10 )+'%';
			
			temp.series.push({
	        	name: '平均满意度',
				type:'line',
				color:colors[0],
				selected: true,//默认checkbox勾选
				data: [0,0,0,0,0,0,0,0,0,0,0,0],
				tooltip: {
	                valueSuffix: '%'
	            }
	        });
			
			//根据标题初始化名称列表
			$.each(title,function(k,v){
				if(v != "月份"){
					var datatemp = {
							name: v,
//							type:'line',
							color:colors[k + 1],
							visible: false,//默认不显示
							selected: false,//默认checkbox勾选
							data: [0,0,0,0,0,0,0,0,0,0,0,0],
							tooltip: {
				                valueSuffix: '%'
				            }
					};
					temp.series.push(datatemp);
				}
			});
			
			//取值并封装
			$.each(list,function(n,m){
				var totalAvg = 0;
				$.each(m,function(k,v){
					//该月满意度总和
					//从后面取值,排除年份和月份
					if(k > 1){
						v = parseFloat((v * 10).toFixed(2));
						totalAvg += v;
						$.each(temp.series,function(j,l){
							//排除评价满意度
							if(j > 0){
								//月份和索引判断并赋值
								if(m[1] == '1月' && (k - 1) == j){
									if(v < temp.plotLines.value){
										l.data[0] ={
												y:v,
												marker: {
													fillColor: '#FF0000',lineWidth: 2,radius: 5
												}
										}
										l.visible = true;
										l.selected = true;
									}else{
										l.data[0] = v;
									}
								}
								if(m[1] == '2月' && (k - 1) == j){
									if(v < temp.plotLines.value){
										l.data[1] ={
												y:v,
												marker: {
													fillColor: '#FF0000',lineWidth: 2,radius: 5
												}
										}
										l.visible = true;
										l.selected = true;
									}else{
										l.data[1] = v;
									}
								}
								if(m[1] == '3月' && (k - 1) == j){
									if(v < temp.plotLines.value){
										l.data[2] ={
												y:v,
												marker: {
													fillColor: '#FF0000',lineWidth: 2,radius: 5
												}
										}
										l.visible = true;
										l.selected = true;
									}else{
										l.data[2] = v;
									}
								}
								if(m[1] == '4月' && (k - 1) == j){
									if(v < temp.plotLines.value){
										l.data[3] ={
												y:v,
												marker: {
													fillColor: '#FF0000',lineWidth: 2,radius: 5
												}
										}
										l.visible = true;
										l.selected = true;
									}else{
										l.data[3] = v;
									}
								}
								if(m[1] == '5月' && (k - 1) == j){
									if(v < temp.plotLines.value){
										l.data[4] ={
												y:v,
												marker: {
													fillColor: '#FF0000',lineWidth: 2,radius: 5
												}
										}
										l.visible = true;
										l.selected = true;
									}else{
										l.data[4] = v;
									}
								}
								if(m[1] == '6月' && (k - 1) == j){
									if(v < temp.plotLines.value){
										l.data[5] ={
												y:v,
												marker: {
													fillColor: '#FF0000',lineWidth: 2,radius: 5
												}
										}
										l.visible = true;
										l.selected = true;
									}else{
										l.data[5] = v;
									}
								}
								if(m[1] == '7月' && (k - 1) == j){
									if(v < temp.plotLines.value){
										l.data[6] ={
												y:v,
												marker: {
													fillColor: '#FF0000',lineWidth: 2,radius: 5
												}
										}
										l.visible = true;
										l.selected = true;
									}else{
										l.data[6] = v;
									}
								}
								if(m[1] == '8月' && (k - 1) == j){
									if(v < temp.plotLines.value){
										l.data[7] ={
												y:v,
												marker: {
													fillColor: '#FF0000',lineWidth: 2,radius: 5
												}
										}
										l.visible = true;
										l.selected = true;
									}else{
										l.data[7] = v;
									}
								}
								if(m[1] == '9月' && (k - 1) == j){
									if(v < temp.plotLines.value){
										l.data[8] ={
												y:v,
												marker: {
													fillColor: '#FF0000',lineWidth: 2,radius: 5
												}
										}
										l.visible = true;
										l.selected = true;
									}else{
										l.data[8] = v;
									}
								}
								if(m[1] == '10月' && (k - 1) == j){
									if(v < temp.plotLines.value){
										l.data[9] ={
												y:v,
												marker: {
													fillColor: '#FF0000',lineWidth: 2,radius: 5
												}
										}
										l.visible = true;
										l.selected = true;
									}else{
										l.data[9] = v;
									}
								}
								if(m[1] == '11月' && (k - 1) == j){
									if(v < temp.plotLines.value){
										l.data[10] ={
												y:v,
												marker: {
													fillColor: '#FF0000',lineWidth: 2,radius: 5
												}
										}
										l.visible = true;
										l.selected = true;
									}else{
										l.data[10] = v;
									}
								}
								if(m[1] == '12月' && (k - 1) == j){
									if(v < temp.plotLines.value){
										l.data[11] ={
												y:v,
												marker: {
													fillColor: '#FF0000',lineWidth: 2,radius: 5
												}
										}
										l.visible = true;
										l.selected = true;
									}else{
										l.data[11] = v;
									}
								}
							}
						});
					}
				});
				//平均满意度赋值
				if(m[1] == '1月'){
					if(parseFloat((totalAvg / (m.length - 2)).toFixed(2)) < temp.plotLines.value){
						temp.series[0].data[0] = {
								y:parseFloat((totalAvg / (m.length - 2)).toFixed(2)),
								marker: {
									fillColor: '#FF0000',lineWidth: 2,radius: 5
								}
						}
					}else{
						temp.series[0].data[0] = parseFloat((totalAvg / (m.length - 2)).toFixed(2));
					}
//					temp.series[0].data[0] = parseFloat((totalAvg / (m.length - 2)).toFixed(2))
				}
				if(m[1] == '2月'){
					if(parseFloat((totalAvg / (m.length - 2)).toFixed(2)) < temp.plotLines.value){
						temp.series[0].data[1] = {
								y:parseFloat((totalAvg / (m.length - 2)).toFixed(2)),
								marker: {
									fillColor: '#FF0000',lineWidth: 2,radius: 5
								}
						}
					}else{
						temp.series[0].data[1] = parseFloat((totalAvg / (m.length - 2)).toFixed(2));
					}
				}
				if(m[1] == '3月'){
					if(parseFloat((totalAvg / (m.length - 2)).toFixed(2)) < temp.plotLines.value){
						temp.series[0].data[2] = {
								y:parseFloat((totalAvg / (m.length - 2)).toFixed(2)),
								marker: {
									fillColor: '#FF0000',lineWidth: 2,radius: 5
								}
						}
					}else{
						temp.series[0].data[2] = parseFloat((totalAvg / (m.length - 2)).toFixed(2));
					}
				}
				if(m[1] == '4月'){
					if(parseFloat((totalAvg / (m.length - 2)).toFixed(2)) < temp.plotLines.value){
						temp.series[0].data[3] = {
								y:parseFloat((totalAvg / (m.length - 2)).toFixed(2)),
								marker: {
									fillColor: '#FF0000',lineWidth: 2,radius: 5
								}
						}
					}else{
						temp.series[0].data[3] = parseFloat((totalAvg / (m.length - 2)).toFixed(2));
					}
				}
				if(m[1] == '5月'){
					if(parseFloat((totalAvg / (m.length - 2)).toFixed(2)) < temp.plotLines.value){
						temp.series[0].data[4] = {
								y:parseFloat((totalAvg / (m.length - 2)).toFixed(2)),
								marker: {
									fillColor: '#FF0000',lineWidth: 2,radius: 5
								}
						}
					}else{
						temp.series[0].data[4] = parseFloat((totalAvg / (m.length - 2)).toFixed(2));
					}
				}
				if(m[1] == '6月'){
//					temp.series[0].data[5] = parseFloat((totalAvg / (m.length - 2)).toFixed(2))
					if(parseFloat((totalAvg / (m.length - 2)).toFixed(2)) < temp.plotLines.value){
						temp.series[0].data[5] = {
								y:parseFloat((totalAvg / (m.length - 2)).toFixed(2)),
								marker: {
									fillColor: '#FF0000',lineWidth: 2,radius: 5
								}
						}
					}else{
						temp.series[0].data[5] = parseFloat((totalAvg / (m.length - 2)).toFixed(2));
					}
				}
				if(m[1] == '7月'){
					if(parseFloat((totalAvg / (m.length - 2)).toFixed(2)) < temp.plotLines.value){
						temp.series[0].data[6] = {
								y:parseFloat((totalAvg / (m.length - 2)).toFixed(2)),
								marker: {
									fillColor: '#FF0000',lineWidth: 2,radius: 5
								}
						}
					}else{
						temp.series[0].data[6] = parseFloat((totalAvg / (m.length - 2)).toFixed(2));
					}
				}
				if(m[1] == '8月'){
					if(parseFloat((totalAvg / (m.length - 2)).toFixed(2)) < temp.plotLines.value){
						temp.series[0].data[7] = {
								y:parseFloat((totalAvg / (m.length - 2)).toFixed(2)),
								marker: {
									fillColor: '#FF0000',lineWidth: 2,radius: 5
								}
						}
					}else{
						temp.series[0].data[7] = parseFloat((totalAvg / (m.length - 2)).toFixed(2));
					}
				}
				if(m[1] == '9月'){
					if(parseFloat((totalAvg / (m.length - 2)).toFixed(2)) < temp.plotLines.value){
						temp.series[0].data[8] = {
								y:parseFloat((totalAvg / (m.length - 2)).toFixed(2)),
								marker: {
									fillColor: '#FF0000',lineWidth: 2,radius: 5
								}
						}
					}else{
						temp.series[0].data[8] = parseFloat((totalAvg / (m.length - 2)).toFixed(2));
					}
				}
				if(m[1] == '10月'){
					if(parseFloat((totalAvg / (m.length - 2)).toFixed(2)) < temp.plotLines.value){
						temp.series[0].data[9] = {
								y:parseFloat((totalAvg / (m.length - 2)).toFixed(2)),
								marker: {
									fillColor: '#FF0000',lineWidth: 2,radius: 5
								}
						}
					}else{
						temp.series[0].data[9] = parseFloat((totalAvg / (m.length - 2)).toFixed(2));
					}
				}
				if(m[1] == '11月'){
					if(parseFloat((totalAvg / (m.length - 2)).toFixed(2)) < temp.plotLines.value){
						temp.series[0].data[10] = {
								y:parseFloat((totalAvg / (m.length - 2)).toFixed(2)),
								marker: {
									fillColor: '#FF0000',lineWidth: 2,radius: 5
								}
						}
					}else{
						temp.series[0].data[10] = parseFloat((totalAvg / (m.length - 2)).toFixed(2));
					}
				}
				if(m[1] == '12月'){
					if(parseFloat((totalAvg / (m.length - 2)).toFixed(2)) < temp.plotLines.value){
						temp.series[0].data[11] = {
								y:parseFloat((totalAvg / (m.length - 2)).toFixed(2)),
								marker: {
									fillColor: '#FF0000',lineWidth: 2,radius: 5
								}
						}
					}else{
						temp.series[0].data[11] = parseFloat((totalAvg / (m.length - 2)).toFixed(2));
					}
				}
			});
			
			
			var config = {
					credits:credits,
					exporting:exporting,
					chart: {
						type: type, zoomType: 'y' 
					},
					title: {
						text: ''//titleText
					},
					subtitle:{
						text:''//subtitleText
					},
					xAxis: {
						categories: ['1月', '2月', '3月', '4月', '5月','6月','7月', '8月', '9月', '10月', '11月','12月']
					},
					yAxis: { 
						title: { 
							text: subtitleText+"("+yAxisText+")" }, 
							labels: { 
								formatter: function() { 
									return this.value +'' } 
							},
							plotLines:[{
								color:'#b45f21',            //线的颜色，定义为红色
								dashStyle:'solid',     //默认是值，这里定义为长虚线
								value:temp.plotLines.value,              //定义在那个值上显示标示线，这里是在x轴上刻度为3的值处垂直化一条线
								width:2,               //标示线的宽度，2px
								label:{
									text: temp.plotLines.text,     //标签的内容
									align:'left',                //标签的水平位置，水平居左,默认是水平居中center
									x:10,                         //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
									style:{
										fontSize:'14px',
										fontWeight:'bold'
									}
								},
								events:{
									click:function(e){
										alert("yAxis plotLine Clicked!");
										//当标示线被单击时，触发的事件
									},
									mouseover:function(){
										console.log("yAxis plotLine Hovered!");
										//当标示线被鼠标悬停时，触发的事件
									},
									
									mouseout:function(){
										//当标示线被鼠标移出时，触发的事件
									},
									
									mousemove:function(){
										//当标示线被鼠标移动(时，触发的事件
									}
								}
							}]
					}, 
					tooltip: { 			
						crosshairs: true, shared: true,valueSuffix: '%'
					}, 
					plotOptions: {
						series: { 
							showCheckbox: false
						}, 
						series: {
			                point: {
			                    events: {
			                        'click': function() {
			                        	initSndLevel(this,'container06');
			                        }
			                    }
			                }
			            },
						scatter: { 
							marker: { radius: 5, states: { hover: { enabled: true, lineColor: 'rgb(100,100,100)' } } }, 
							states: { hover: { marker: { enabled: false } } }, 
							tooltip: { headerFormat: '{point.x}  <br><b>{series.name}</b>: {point.y} %', pointFormat: '' } } 
					},
					series: temp.series 
			};
			
			$('#container06').highcharts(config);
		},
		error:function(){
			//alert("同步失败！");
		}
	});
}

/**
 * 钻取至二级页面
 * @param target
 */
function initSndLevel(target,containerId){
	//预算类的无二级页面
	if("container0501" == containerId || "container0502" == containerId){
		return false;
	}else{
		window.location.href = basePath + '/initLevel?month='+(target.x + 1)+"&year="+year+'&container='+containerId;
	}
}