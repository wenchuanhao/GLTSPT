/**
 * 钻取至二级页面
 * @param target
 */
function initSndLevel(target,homeId){
	if('#containerService01' == homeId){
		window.location.href = basePath + '/servicesnd?name='+encodeURI(encodeURI(target.series.name))+'&month='+(target.x + 1)+'&year='+year + '&container='+homeId.substring(1);
	}else if('#containerService02' == homeId){
		window.location.href = basePath + '/servicesnd?name='+encodeURI(encodeURI(target.series.name))+'&month='+(target.x + 1)+'&year='+year + '&container='+homeId.substring(1);
	}else if('#containerService03' == homeId){
		window.location.href = basePath + '/servicesnd?name='+encodeURI(encodeURI(target.series.name))+'&month='+(target.x + 1)+'&year='+year + '&container='+homeId.substring(1);
	}else{
		return false;
	}
}


function loadChartDataAjax(target){
	//表格内容ID
	var tbodyId = "tbody" + $(target).next().attr("id");
	$("#"+tbodyId).html('');

	jQuery.ajax({
		 type:"POST",
		 async:true,
		 url:basePath+"/serviceChartData",
		 data:"type="+tbodyId + "&month=" + month +"&year="+year + "&name="+name,
		 dataType: "json",
		 success:function(data){
		 	var head = data.head;
		 	var list = data.list;
		 
		 	//标题
		 	var thTemp = '<tr>';
		 	$.each(head,function(k,v){
		 		thTemp += '<th>'+v+'</th>';
		 	});
		 	thTemp += '</tr>';
		 	//总计
		 	if('tbodycontainerService01' == tbodyId || 'tbodycontainerService02' == tbodyId || 'tbodycontainerService03' == tbodyId 
		 			|| 'tbodycontainerService01_02' == tbodyId || 'tbodycontainerService02_02' == tbodyId || 'tbodycontainerService03_02' == tbodyId){
			 	var months = ['-01','-02','-03','-04','-05','-06','-07','-08','-09','-10','-11','-12'];
			 	
			 	 var categories = [];
			 	 //取出名称
				 $.each(list,function(k,v){
					 categories.push(v[1]);
				 });
				 categories = categories.unique();
			 	 
			 	 $.each(categories,function(n,m){
				 	//内容
				 	var sum = 0;
				 	var tdTemp = '<tr><td>'+m+'</td>';
				 	$.each(months,function(k,v){
				 		var t = '<td>0</td>';
					 	$.each(list,function(j,i){
					 		if(i[0].indexOf(v) > 0 && i[1] == m){
					 			t = '<td>'+parseFloat(i[2].toFixed(2))+'</td>';
					 			if(!isNaN(i[2])){
					 				sum += i[2];
					 			}
					 		}
					 	});
					 	tdTemp += t;
				 	});
				 	if('tbodycontainerService01_02' == tbodyId || 'tbodycontainerService02' == tbodyId || 'tbodycontainerService02_02' == tbodyId || 'tbodycontainerService03_02' == tbodyId){
				 		tdTemp += '</tr>';
				 	}else{
				 		tdTemp += '<td>'+parseFloat(sum.toFixed(2))+'</td></tr>';
				 	}
				 	thTemp += tdTemp;
				 });
		 	}
		 	//按天统计
		 	if('tbodycontainerService01_01' == tbodyId || 'tbodycontainerService02_01' == tbodyId || 'tbodycontainerService03_01' == tbodyId){
		 		var months = [];
		 		var monC = getMonthDates(year,month);
				 //封装一年中的月份
				 for ( var int = 1; int <= monC; int++) {
					 if(int < 10){
						 months.push(year + "-" + month + "-0" + int);
					 }else{
						 months.push(year + "-" + month + "-" + int);
					 }
				 }
		 		var categories = [];
		 		//取出名称
		 		$.each(list,function(k,v){
		 			categories.push(v[1]);
		 		});
		 		categories = categories.unique();
		 		
		 		$.each(categories,function(n,m){
		 			//内容
		 			var sum = 0;
		 			var tdTemp = '<tr><td>'+m+'</td>';
		 			$.each(months,function(k,v){
		 				var t = '<td>0</td>';
		 				$.each(list,function(j,i){
		 					if(i[0].indexOf(v) >= 0 && i[1] == m){
		 						t = '<td>'+i[2]+'</td>';
		 						if(!isNaN(i[2])){
		 							sum += i[2];
		 						}
		 					}
		 				});
		 				tdTemp += t;
		 			});
		 			if('tbodycontainerService01_02' == tbodyId || 'tbodycontainerService02_01' == tbodyId){
		 				tdTemp += '</tr>';
		 			}else{
		 				tdTemp += '<td>'+sum+'</td></tr>';
		 			}
		 			thTemp += tdTemp;
		 		});
		 	}
		 	$("#"+tbodyId).html(thTemp);
		 },
		 error:function(){
			 alert("查询失败！");
		 }
	 });
}

 /**
  * 一级饼图数据获取
  */
 function initContainerForPie(target,type,month,year){
 	jQuery.ajax({
 		type:"POST",
 		async:true,
 		url:basePath+"/getServiceData",
 		data:"type="+type + "&month=" + month +"&year="+year + "&name="+name,
 		dataType: "json",
 		success:function(data){
 			var total = 0;
 			$.each(data,function(k,v){
 				total += v[1];
 			});
 			$.each(data,function(k,v){
 				v[1] = parseFloat((v[1] / total * 100).toFixed(2));
 			});
 			
 			var series = [{
		            type: 'pie',
			            name: '百分比',
			            data: data
			        }];
 			var config = {
 			    	colors:colors,
 			    	navigation:navigation,
 			    	legend:legend,
 			    	credits:credits,
 			        exporting: exporting,
 			        chart: {
 			            plotBackgroundColor: null,
 			            plotBorderWidth: null,
 			            plotShadow: false
// 			            margin:[20,20,80,20]
 			        },
 			        tooltip: {
			            valueSuffix: '%',
			            shared: true
			        },
 			        title: {
 			            text: ' '
 			        },
 			        plotOptions: {
 			            pie: {
 			                allowPointSelect: true,
 			                showInLegend: true,
 			                dataLabels: {
			                    enabled: false,
			                    color: '#000000',
			                    formatter: function () {
				                    // display only if larger than 1
				                    return this.y > 1 ? '<b>' + this.point.name + ':</b> ' + this.y + '%' : null;
				                }
			                },
 			            },
	 			       series: {
			                point: {
			                    events: {
			                        'click': function() {
			                        	initSndLevel(this,target);
			                        }
			                    }
			                }
			            }
 			        },
 			        series: series
 				};
 			$(target).prev().children('i').html('数据');
 			$(target).show();
 		    $(target).next().hide();
 			$(target).highcharts(config);
 		},
 		error:function(){
             //alert("同步失败！");
         }
 	});
 }
 
 /**
  * 时间轴柱状图数据获取
  */
 function initContainerForTimeColumn(chartType,target,yAxisTitle,tooltip,type,month,year){
	 jQuery.ajax({
		 type:"POST",
		 async:true,
		 url:basePath+"/getServiceData",
		 data:"type="+type + "&month=" + month +"&year="+year + "&name="+name,
		 dataType: "json",
		 success:function(data){
			 var chartTypes = chartType.split("|");
			 if(chartTypes.length > 1){
				 chartType = chartTypes[0];
			 }
			 var categories = [];
			 var months = [];
			 //封装一年中的月份
			 for ( var int = 1; int < 13; int++) {
				 if(int < 10){
					 months.push(year + "-0" + int);
				 }else{
					 months.push(year + "-" + int);
				 }
			}
			 //取出名称
			 $.each(data,function(k,v){
				 categories.push(v[1]);
			 });
			 categories = categories.unique();
			 //封装数据
			 var series = [];
			 $.each(categories,function(m,n){
				 //柱状图,初始化12个月的值为0
				 var valueTemp = {
						 type: chartType,
						 name: n,
						 data: [0,0,0,0,0,0,0,0,0,0,0,0]
				 }
				 $.each(data,function(k,v){
					 $.each(months,function(j,l){
						 //年份和名称相同
						 if(v[1] == n && v[0] == l){
							 valueTemp.data[j] = parseFloat(v[2].toFixed(2));
						 }
					 });
				 });
				 series.push(valueTemp);
			 });
			 
			 if(chartTypes.length > 1){
				 var copySeries = cloneObj(series);
				 copySeries[0].type = chartTypes[1];
				 copySeries[0].name = series[0].name + "折线图";
				 series.push(copySeries[0]);
			 }
			  
			 var config = {
					 colors:colors,
					 navigation:navigation,
					 legend:legend,
					 credits:credits,
					 exporting: exporting,
					 chart: {
						 type: chartType//'column'
					 },
					 title: {
						 text: false
					 },
					 xAxis: {
				            categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
				        },
					 yAxis: {
//						 min: 0,
						 title: {
							 text: yAxisTitle
						 }
					 },
					 tooltip: { //鼠标提示
						 formatter: function() {
							 return this.series.name+ this.x + '为' + Highcharts.numberFormat(this.y, 2, null, ' ')+ tooltip;
						 }
					 },
					 plotOptions: {
			            series: {
			                borderWidth: 0,
			                dataLabels: {
			                    enabled: true,
			                    style:{"color": "#77cdfe", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
			                },
			                point: {
			                    events: {
			                        'click': function() {
			                        	initSndLevel(this,target);
			                        }
			                    }
			                }
			            }
			        },
					 series: series
			 };
			 $(target).prev().children('i').html('数据');
			 $(target).show();
	 		 $(target).next().hide();
			 $(target).highcharts(config);
		 },
		 error:function(jqXHR, textStatus, errorThrown){
			 //alert("同步失败！");
		 }
	 });
 }
 
 /**
  * 时间轴柱状图数据获取
  */
 function initContainerForTimestampColumn(chartType,target,yAxisTitle,tooltip,type,month,year){
	 jQuery.ajax({
		 type:"POST",
		 async:true,
		 url:basePath+"/getServiceData",
		 data:"type="+type + "&month=" + month +"&year="+year + "&name="+name,
		 dataType: "json",
		 success:function(data){
			 var chartTypes = chartType.split("|");
			 if(chartTypes.length > 1){
				 chartType = chartTypes[0];
			 }
			 var categories = [];
			 var months = [];
			 var datas = [];
			 var monC = getMonthDates(year,month);
			 //封装一年中的月份
			 for ( var int = 1; int <= monC; int++) {
				 if(int < 10){
					 months.push(year + "-" + month + "-0" + int);
				 }else{
					 months.push(year + "-" + month + "-" + int);
				 }
				 datas.push(0);
			 }
			 //取出名称
			 $.each(data,function(k,v){
				 categories.push(v[1]);
			 });
			 categories = categories.unique();
			 //封装数据
			 var series = [];
			 $.each(categories,function(m,n){
				 //柱状图,初始化12个月的值为0
				 var valueTemp = {
						 type: chartType,
						 name: n,
						 data: datas
				 }
				 $.each(data,function(k,v){
					 $.each(months,function(j,l){
						 //年份和名称相同
						 if(type == "containerService02_01"){
//							 if(v[1] == n && v[0] == l){
//								 if(v[2] > 0){
//									 valueTemp.data[j] = parseFloat(v[2].toFixed(2));
//								 }else{
//									 valueTemp.data[j] = valueTemp.data[j - 1];
//								 }
//							 }
							 if(v[1] == n && v[0] == l){
								 valueTemp.data[j] = parseFloat(v[2].toFixed(2));
							 }
						 }else{
							 if(v[1] == n && v[0] == l){
								 valueTemp.data[j] = parseFloat(v[2].toFixed(2));
							 }
						 }
					 });
				 });
				 series.push(valueTemp);
			 });
			 
			 if(chartTypes.length > 1){
				 var copySeries = cloneObj(series);
				 copySeries[0].type = chartTypes[1];
				 copySeries[0].name = series[0].name + "折线图";
				 series.push(copySeries[0]);
			 }
			 
			 var config = {
					 colors:colors,
					 navigation:navigation,
					 legend:legend,
					 credits:credits,
					 exporting: exporting,
					 chart: {
						 type: chartType//'column'
					 },
					 title: {
						 text: false
					 },
					 xAxis: {
						 categories: months
					 },
					 yAxis: {
//						 min: 0,
						 title: {
							 text: yAxisTitle
						 }
					 },
					 tooltip: { //鼠标提示
						 formatter: function() {
							 return this.series.name+ this.x + '为' + Highcharts.numberFormat(this.y, 2, null, ' ')+ tooltip;
						 }
					 },
					 plotOptions: {
						 series: {
							 borderWidth: 0,
							 dataLabels: {
								 enabled: true,
								 style:{"color": "#77cdfe", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
							 },
							 point: {
								 events: {
									 'click': function() {
										 initSndLevel(this,target);
									 }
								 }
							 }
						 }
					 },
					 series: series
			 };
			 $(target).prev().children('i').html('数据');
			 $(target).show();
			 $(target).next().hide();
			 $(target).highcharts(config);
		 },
		 error:function(jqXHR, textStatus, errorThrown){
			 //alert("同步失败！");
		 }
	 });
 }
 
 function getMonthDates(year,month){
	//构造一个日期对象：
	  var  day = new Date(year,month,0); 
	  //获取天数：
	  var daycount = day.getDate();
	  return daycount;
 }