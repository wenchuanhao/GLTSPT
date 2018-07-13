Array.prototype.unique = function(){
	 var res = [];
	 var json = {};
	 for(var i = 0; i < this.length; i++){
	  if(!json[this[i]]){
		  res.push(this[i]);
	   json[this[i]] = 1;
	  }
	 }
	 return res;
} 
var  colors = ['#b2e31a', '#40baff','#ff6d26','#dc1ced','#7d1dff'],
    // 按钮样式
    navigation  =  {
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
    legend  =  {
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
    credits  =  {
        enabled: false
    },
    plotOptions =  {
        line: {
            dataLabels: {
                enabled: true,
                borderRadius: 3,
                backgroundColor: 'rgba(255, 255, 255, 0.5)'
            },
        }
    },
    exporting = {
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

 $(function(){

 });
 /**
  * 总营业收入
  * @param target
  */
 function changeChart01(target){
	 $(target).parent().find('.current').removeClass('current');
	 $(target).addClass('current');
	 $("#container01_04").parent().parent().show();
	 var typeName = $(target).html();
	 if(typeName == "主题"){
		 initContainerForDoublePie('#container01_01','sndlevel0101_zhuti',month,year);//主题维度
	 	//同比
		initContainerForColumn('column','#container01_02','总营业收入','%','sndlevel0102_zhuti',month,year,'YEAR');
		//环比
		initContainerForColumn('column','#container01_03','总营业收入','%','sndlevel0103_zhuti',month,year,'MON');
		//趋势
		initContainerForTimeColumn('column','#container01_04','主题维度','元','sndlevel0104_zhuti',month,year);
	 }else if(typeName == "部门"){
		 initContainerForPie('#container01_01','sndlevel0101_bumen',month,year);//部门维度
		//同比
		initContainerForColumn('column','#container01_02','总营业收入','%','sndlevel0102_bumen',month,year,'YEAR');
		//环比
		initContainerForColumn('column','#container01_03','总营业收入','%','sndlevel0103_bumen',month,year,'MON');
		//趋势
		initContainerForTimeColumn('column','#container01_04','部门维度','元','sndlevel0104_bumen',month,year);
	 }else if(typeName == "结算类型"){
		 $("#container01_04").parent().parent().hide();
		 initContainerForPie('#container01_01','sndlevel0101_jiesuan',month,year);//结算类型维度
		//同比
		initContainerForColumn('column','#container01_02','总营业收入','%','sndlevel0102_jiesuan',month,year,'YEAR');
		//环比
		initContainerForColumn('column','#container01_03','总营业收入','%','sndlevel0103_jiesuan',month,year,'MON');
	 }else if(typeName == "项目"){
		 initContainerForDoublePie('#container01_01','sndlevel0101_xiangmu',month,year);//项目维度
		//同比
		initContainerForColumn('column','#container01_02','总营业收入','%','sndlevel0102_xiangmu',month,year,'YEAR');
		//环比
		initContainerForColumn('column','#container01_03','总营业收入','%','sndlevel0103_xiangmu',month,year,'MON');
		//趋势
		initContainerForTimeColumn('column','#container01_04','项目维度','元','sndlevel0104_xiangmu',month,year);
	 }
 }
 /**
  * 满意度
  * @param target
  */
 function changeChart06(target){
	 $(target).parent().find('.current').removeClass('current');
	 $(target).addClass('current');
	 var typeName = $(target).html();
	 if(typeName == "主题"){
		//同比
			initContainerForColumn('column','#container06_01','主题维度','%','sndlevel0601_zhuti',month,year,'YEAR');
			//环比
			initContainerForColumn('column','#container06_02','主题维度','%','sndlevel0602_zhuti',month,year,'MON');
			//趋势
			initContainerForTimeColumn('column','#container06_03','主题维度','','sndlevel0603_zhuti',month,year);
	 }else if(typeName == "部门"){
		//同比
			initContainerForColumn('column','#container06_01','部门维度','%','sndlevel0601_bumen',month,year,'YEAR');
			//环比
			initContainerForColumn('column','#container06_02','部门维度','%','sndlevel0602_bumen',month,year,'MON');
			//趋势
			initContainerForTimeColumn('column','#container06_03','部门维度','','sndlevel0603_bumen',month,year);
	 }
 }
 /**
  * 业务规模-服务人天
  * @param target
  */
 function changeChart02(target){
	$(target).parent().children().toggleClass('current');
	
	var typeName = $(target).html();
	if(typeName == "主题"){
		initContainerForPie('#container02_01','sndlevel0201',month,year);//主题维度
		initContainerForTimeColumn('column','#container02_03','服务人天','人天','sndlevel0203_zhuti',month,year);
	}else if(typeName == "部门"){
		initContainerForPie('#container02_01','sndlevel0201_bumen',month,year);//部门维度
		initContainerForTimeColumn('column','#container02_03','服务人天','人天','sndlevel0203_bumen',month,year);
	}
}
 /**
  * 总成本
  * @param target
  */
 function changeChart03(target){
	 $(target).parent().children().toggleClass('current');
	 
	 var typeName = $(target).html();
	 if(typeName == "部门"){
		 initContainerForPie('#container03_01','sndlevel0301_bumen',month,year);//部门维度
			//同比
			initContainerForColumn('line','#container03_02','部门维度','%','sndlevel0302_bumen',month,year,'YEAR');
			//环比
			initContainerForColumn('line','#container03_03','部门维度','%','sndlevel0303_bumen',month,year,'MON');
			//趋势
			initContainerForTimeColumn('line','#container03_04','部门维度','元','sndlevel0304_bumen',month,year);
	 }else if(typeName == "项目"){
		 initContainerForPie('#container03_01','sndlevel0301_xiangmu',month,year);//项目维度
			//同比
			initContainerForColumn('line','#container03_02','项目维度','%','sndlevel0302_xiangmu',month,year,'YEAR');
			//环比
			initContainerForColumn('line','#container03_03','项目维度','%','sndlevel0303_xiangmu',month,year,'MON');
			//趋势
			initContainerForTimeColumn('line','#container03_04','项目维度','元','sndlevel0304_xiangmu',month,year);
	 }
 }

 
 /**
  * 设置按钮
  */
 function setDimension(dimension,method){
	 var temp = '<div class="col-1 row-1"><ul class="dimension" id="dimension_ul">';
	 $.each(dimension,function(k,v){
		 if(k == 0){
			 temp += '<li onclick="'+method+'(this)" class="current">'+v+'</li>';
		 }else{
			 temp += '<li onclick="'+method+'(this)" >'+v+'</li>';
		 }
	 });
	 temp += '</ul></div>';
	 return temp;
 }
 /**
  *   //长:100% 高:100% ：：：'col-1','row-1','chart_col01','list_wrap01'
  *   //长:50% 高:100%：：：'col-2','row-1','chart_col02','list_wrap02'
  *   //长:50%,高:50%：：：'col-2','row-2','chart_col03','list_wrap03'
  *   标题/周期/id
  */
 function setContainerDiv(col,row,chart_col,list_wrap,chart_title,tips,ids){
	 var temp = '<div class="'+col+' '+row+'">\
		 			<div class="chart_wrap">\
					     <h3 class="chart_title">'+chart_title+'</h3>\
					     <p class="tips02">周期：'+tips+'</p>\
					     <span class="switch02 switch_list"><i>数据</i></span>\
					     <div class="'+chart_col+'" id="'+ids+'"></div>\
					     <div style="display: none;" class="'+list_wrap+' scroll">\
					     	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>\
				            <table class="listTable">\
				                <tbody id="tbody'+ids+'">\
				                </tbody>\
				            </table>\
				        </div>\
				 	</div>\
				</div>';

	 return temp;
 }
 
 

 /**
  * 一级饼图数据获取
  */
 function initContainerForPie(target,type,month,year){
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
			                },
//		 			       dataLabels: {
//			                    distance: -30,
//			                    format: '{y}%',
//			                    style:{"color": "#ffffff", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
//			                },
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
  * 双级饼图数据获取
  */
 function initContainerForDoublePie(target,type,month,year){
	 jQuery.ajax({
		 type:"POST",
		 async:true,
		 url:basePath+"/getSeriesData",
		 data:"type="+type + "&month=" + month +"&year="+year,
		 dataType: "json",
		 success:function(result){
			 //父级数据初始化
			 var parentName = [];//名称
			 var parentValue = [];//值
			 
			 var totalParent = 0;
			 
			 $.each(result,function(k,v){
				 parentName.push(v[1]);
				 totalParent += v[2];
			 });
			 parentName = parentName.unique();//去重
			 //初始化总和
			 $.each(parentName,function(m,n){
				 parentValue.push(0);
			 });
			 //赋值
			 $.each(result,function(k,v){
				 $.each(parentName,function(m,n){
					 if(v[1] == n){
						 parentValue[m] += v[2];
					 }
				 });
			 });
			 var data = [];
			 //封装二层数据
			 $.each(parentName,function(m,n){
				 var dataCategories = [];
				 var dataValue = [];
				 $.each(result,function(k,v){
					 if(v[1] == n){
						 dataCategories.push(v[0]);
						 dataValue.push(parseFloat((v[2] / totalParent * 100).toFixed(2)));
					 }
				 });
				 var dataObj = {
		            y: parseFloat((parentValue[m] / totalParent * 100).toFixed(2)),
		            color: colors[m],
		            drilldown: {
		                name: n,
		                categories: dataCategories,
		                data: dataValue,
		                color: colors[m]
		            }
		        };
				 data.push(dataObj);
			 });
			 
			 
		     var categories = parentName,
		        browserData = [],
		        versionsData = [],
		        i,
		        j,
		        dataLen = data.length,
		        drillDataLen,
		        brightness;
			 
		    // Build the data arrays
		    for (i = 0; i < dataLen; i += 1) {

		        // add browser data
		        browserData.push({
		            name: categories[i],
		            y: data[i].y,
		            color: data[i].color
		        });

		        // add version data
		        drillDataLen = data[i].drilldown.data.length;
		        for (j = 0; j < drillDataLen; j += 1) {
		            brightness = 0.2 - (j / drillDataLen) / 5;
		            versionsData.push({
		                name: data[i].drilldown.categories[j],
		                y: data[i].drilldown.data[j],
		                color: Highcharts.Color(data[i].color).brighten(brightness).get()
		            });
		        }
		    }
			
		    var series = [{
	            name: '百分比',
	            data: browserData,
	            size: '60%',
	            dataLabels: {
	                formatter: function () {
	                    return this.y > 5 ? this.point.name : null;
	                },
	                color: '#ffffff',
	                distance: -30
	            }
	        }, {
	            name: '百分比',
	            data: versionsData,
	            size: '80%',
	            innerSize: '60%',
	            dataLabels: {
	                formatter: function () {
	                    // display only if larger than 1
	                    return this.y > 1 ? '<b>' + this.point.name + ':</b> ' + this.y + '%' : null;
	                }
	            }
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
						 plotShadow: false,
//						 margin:[20,20,80,20],
						 type: 'pie'
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
			                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'//显示触角
			                 },
//					 		 dataLabels: {
//				                    distance: -30,
//				                    format: '{y}%',
//				                    style:{"color": "#ffffff", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
//			                 },
							 shadow: false,
			                 center: ['50%', '50%']
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
  * 柱状图数据获取
  */
 function initContainerForColumn(chartType,target,yAxisTitle,tooltip,type,month,year,cycle){
	 jQuery.ajax({
		 type:"POST",
		 async:true,
		 url:basePath+"/getSeriesData",
		 data:"type="+type + "&month=" + month +"&year="+year,
		 dataType: "json",
		 success:function(data){
			 var categories = [];
			 
			 $.each(data,function(k,v){
				 categories.push(v[1]);
			 });
			 categories = categories.unique();
			 
			 var series = [];//保存结果数据(同比/环比值),五期值
			 var seriesTemp = [];//保存原有数据,六期值
			 //同比是五年，数据有六年
			 if(cycle == "YEAR"){
				 //原始数据,六期
				 for ( var int = 5; int >= 0; int--) {
					 var dataTemp = [];
					 $.each(categories,function(k,v){
						 dataTemp.push(0);
					 });
					 var temp = {
							 name: (year- int)+'年',
							 data: dataTemp
					 };
					 seriesTemp.push(temp);
				 }
				 //结果数据初始化,五期
				 for ( var int = 4; int >= 0; int--) {
					 var dataTemp = [];
					 $.each(categories,function(k,v){
						 dataTemp.push(0);
					 });
					 var temp = {
							 name: (year- int)+'年',
							 data: dataTemp
					 };
					 series.push(temp);
				 }
				 //六期数据赋值
				 $.each(data,function(k,v){
					 $.each(categories,function(j,m){
						 //最大年
						 if(v[0].indexOf(year+"-") >= 0 && v[1] == m){
							 seriesTemp[5].data[j] = v[2];
						 }
						 if(v[0].indexOf((year - 1)+"-") >= 0 && v[1] == m){
							 seriesTemp[4].data[j] = v[2];
						 }
						 if(v[0].indexOf((year - 2)+"-") >= 0 && v[1] == m){
							 seriesTemp[3].data[j] = v[2];
						 }
						 if(v[0].indexOf((year - 3)+"-") >= 0 && v[1] == m){
							 seriesTemp[2].data[j] = v[2];
						 }
						 if(v[0].indexOf((year - 4)+"-") >= 0 && v[1] == m){
							 seriesTemp[1].data[j] = v[2];
						 }
						 //最小年
						 if(v[0].indexOf((year - 5)+"-") >= 0 && v[1] == m){
							 seriesTemp[0].data[j] = v[2];
						 }
					 });
				 });
			 }
			 //环比是五个月
			 if(cycle == "MON"){
				 var timeTemp = [];
				 //封装六个月数据
				 for ( var int = 5; int >= 0; int--) {
					 var dataTemp = [];
					 $.each(categories,function(k,v){
						 dataTemp.push(0);
					 });
					 var monthTemp = month - int;
					 //遍历生成环比月份
					 if(monthTemp < 10 && monthTemp > 0){
						 monthTemp = year + "-0" + (monthTemp);
					 }else if(monthTemp >= 10){
						 monthTemp = year + "-" + monthTemp;
						 //上年月份
					 }else if(monthTemp <= 0){
						 if(monthTemp + 12 < 10){
							 monthTemp = (year-1) + "-0" + (monthTemp + 12);
						 }else{
							 monthTemp = (year-1) + "-" + (monthTemp + 12);
						 }
					 }
					 
					 timeTemp.push(monthTemp);
					 var temp = {
							 name: monthTemp,
							 data: dataTemp
					 };
					 seriesTemp.push(temp);
				 }
				 
				 //封装五个月数据
				 for ( var int = 4; int >= 0; int--) {
					 var dataTemp = [];
					 $.each(categories,function(k,v){
						 dataTemp.push(0);
					 });
					 var monthTemp = month - int;
					 //遍历生成环比月份
					 if(monthTemp < 10 && monthTemp > 0){
						 monthTemp = year + "-0" + (monthTemp);
					 }else if(monthTemp >= 10){
						 monthTemp = year + "-" + monthTemp;
						 //上年月份
					 }else if(monthTemp <= 0){
						 if(monthTemp + 12 < 10){
							 monthTemp = (year-1) + "-0" + (monthTemp + 12);
						 }else{
							 monthTemp = (year-1) + "-" + (monthTemp + 12);
						 }
					 }
					 var temp = {
							 name: monthTemp,
							 data: dataTemp
					 };
					 series.push(temp);
				 }
				//六期数据赋值
				 $.each(data,function(k,v){
					 $.each(categories,function(j,m){
						 //最大期
						 if(v[0] == timeTemp[5] && v[1] == m){
							 seriesTemp[5].data[j] = v[2];
						 }
						 if(v[0] == timeTemp[4] && v[1] == m){
							 seriesTemp[4].data[j] = v[2];
						 }
						 if(v[0] == timeTemp[3] && v[1] == m){
							 seriesTemp[3].data[j] = v[2];
						 }
						 if(v[0] == timeTemp[2] && v[1] == m){
							 seriesTemp[2].data[j] = v[2];
						 }
						 if(v[0] == timeTemp[1] && v[1] == m){
							 seriesTemp[1].data[j] = v[2];
						 }
						 //最小期
						 if(v[0] == timeTemp[0] && v[1] == m){
							 seriesTemp[0].data[j] = v[2];
						 }
					 });
				 });
				 
			 }
			 //比值计算,（本期的值-上一期的值）/上一期的值
			 $.each(seriesTemp,function(k,v){
				 //从正数第二期开始
				 if(k > 0){
					 $.each(v.data,function(n,m){
						 //上一期值
						 var prevValue = seriesTemp[(k-1)].data[n];
						 if(prevValue != 0){
							 series[(k-1)].data[n] = parseFloat(((m - prevValue) / prevValue * 100).toFixed(2));
						 }
					 });
				 }
			 });
			 
			 var config = {
				    	colors:colors,
				    	navigation:navigation,
				    	legend:legend,
				    	credits:credits,
				        exporting: exporting,
				        chart: {
				            type: chartType
				        },
				        title: {
				            text: false
				        },
				        xAxis: {
				        	labels: {
				                style: {
//				                    whiteSpace:'normal'
				                }
				            },
				            categories: categories,
				            crosshair: true
				        },
				        yAxis: {
//				            min: 0,
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
		 url:basePath+"/getSeriesData",
		 data:"type="+type + "&month=" + month +"&year="+year,
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
			 });
			 
			  
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