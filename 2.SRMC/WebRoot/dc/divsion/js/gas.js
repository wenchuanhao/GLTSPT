 /**
  * 一级饼图数据获取
  */
 function initContainerForPie(target,type,month,year){
	 //initContainerForPie('#container02','container02',month,year);
 	jQuery.ajax({
 		type:"POST",
 		async:true,
 		url:basePath+"/getSeriesData",
 		data:"type="+type + "&month=" + month +"&year="+year,
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
//			                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'//显示触角
			                }
//		 			       dataLabels: {
//			                    distance: -30,
//			                    format: '{y}%',
//			                    style:{"color": "#ffffff", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
//			                },
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
	 //initContainerForTimeColumn('column','#container01','用电量（度）','度','container01',month,year);
	 jQuery.ajax({
		 type:"POST",
		 async:true,
		 url:basePath+"/getSeriesData",
		 data:"type="+type + "&month=" + month +"&year="+year + "&name="+name,
		 dataType: "json",
		 success:function(data){
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
			 var tr = "";
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
							 valueTemp.data[j] = v[2];
						 }
					 });
				 });
				 series.push(valueTemp);
				 /*
				 tr += "<tr><td>"+n+"</td>";
				 var total = 0;
				 $.each(months,function(k,v){
					 $.each(data,function(j,d){
						 //年份和名称相同
						 if(d[1] == n && d[0] == v){
							 tr += "<td>"+d[2]+"</td>";
							 total += d[2];
							 return true;//跳出当前循环
						 }
					 });
				 });
				 tr += "<td>"+total+"</td></tr>";
				 */
			 });
			 //$("#listTable tbody").append(tr);
			 
			  
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
		 error:function(){
			 //alert("同步失败！");
		 }
	 });
 }
 
 function loadChartDataAjax(target){
		//表格内容ID
		var tbodyId = "tbody" + $(target).next().attr("id");
		$("#"+tbodyId).html('');

		jQuery.ajax({
			 type:"POST",
			 async:true,
			 url:basePath+"/getChartData",
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
			 	//饼图
			 	if('tbodycontainer02' == tbodyId || 'tbodycontainer01_01' == tbodyId){
			 		var sum = 0;
			 		$.each(list,function(n,m){
			 			sum += m[1];
			 		});
			 		var tdTemp = '';
			 		$.each(list,function(n,m){
			 			tdTemp += '<tr><td>'+m[0]+'</td><td>'+m[1]+'</td><td>'+parseFloat((m[1]/sum * 100).toFixed(2)) + '%' +'</td></tr>';
			 		});
				 	thTemp += tdTemp;
			 	}
			 	//总计
			 	if('tbodycontainer01' == tbodyId || 'tbodycontainer01_04' == tbodyId || 'tbodycontainer01_02' == tbodyId){
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
						 			t = '<td>'+i[2]+'</td>';
						 			if(!isNaN(i[2])){
						 				sum += i[2];
						 			}
						 		}
						 	});
						 	tdTemp += t;
					 	});
					 	if('tbodycontainer01_02' == tbodyId){
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
  * 钻取至二级页面
  * @param target
  */
 function initSndLevel(target,homeId){
 	if('#container01' == homeId){
 		window.location.href = basePath + '/gasSnd?name='+encodeURI(encodeURI(target.series.name))+'&month='+(target.x + 1)+'&year='+year;
 	}else if('#container02' == homeId){
 		window.location.href = basePath + '/gasSnd?name='+encodeURI(encodeURI(target.name))+'&month='+(target.x + 1)+'&year='+year;
 	}else{
 		return false;
 	}
 }