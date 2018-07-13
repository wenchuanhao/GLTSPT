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
//深拷贝
var cloneObj = function(obj){
    var str, newobj = obj.constructor === Array ? [] : {};
    if(typeof obj !== 'object'){
        return;
    } else if(window.JSON){
        str = JSON.stringify(obj), //系列化对象
        newobj = JSON.parse(str); //还原
    } else {
        for(var i in obj){
            newobj[i] = typeof obj[i] === 'object' ? 
            cloneObj(obj[i]) : obj[i]; 
        }
    }
    return newobj;
};
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
  * 环节时效
  * @param target
  */
 var typeName="";
 function changeChart01(target){
	 $(target).parent().find('.current').removeClass('current');
	 $(target).addClass('current');
	 typeName = $(target).html();
	 $("#container04").parent().parent().hide();
		$("#container05").parent().parent().hide();
		$("#container06").parent().parent().hide();
	 if(typeName == "部门"){
		 initContainerForPie('#container01',typeName,startTime,endTime,linkName,shixiao);
	 	//同比
		initContainerForColumn('column','#container02','工作日','',typeName,startTime,endTime,linkName,2,shixiao);
		//环比
		initContainerForColumn('column','#container03','工作日','',typeName,startTime,endTime,linkName,1,shixiao);
		
//		if(linkName=="3"){
//			/*问题整改环节多出3个图*/
//			initContainerForPie('#container04',typeName,startTime,endTime,linkName,'时效');
//			//同比
//			initContainerForColumn('column','#container05','%','',typeName,startTime,endTime,linkName,2,shixiao);
//			//环比
//			initContainerForColumn('column','#container06','%','',typeName,startTime,endTime,linkName,1,shixiao);
//		}
	 }else if(typeName == "费用"){
		 initContainerForDoublePie('#container01',typeName,startTime,endTime,linkName);
		//同比
		initContainerForColumn('column','#container02','工作日','',typeName,startTime,endTime,linkName,2,shixiao);
		//环比
		initContainerForColumn('column','#container03','工作日','',typeName,startTime,endTime,linkName,1,shixiao);
		
//		if(linkName=="3"){
//			/*问题整改环节多出3个图*/
//			initContainerForPie('#container04',typeName,startTime,endTime,linkName,'时效');
//			//同比
//			initContainerForColumn('column','#container05','%','',typeName,startTime,endTime,linkName,2,shixiao);
//			//环比
//			initContainerForColumn('column','#container06','%','',typeName,startTime,endTime,linkName,1,shixiao);
//		}
	 }else if(typeName == "问题类型"){
		 initContainerForPie('#container01',typeName,startTime,endTime,linkName,'时效');
		//同比
		initContainerForColumn('column','#container02','%','',typeName,startTime,endTime,linkName,2,shixiao);
		//环比
		initContainerForColumn('column','#container03','%','',typeName,startTime,endTime,linkName,1,shixiao);
		
		$("#container04").parent().parent().show();
		$("#container05").parent().parent().show();
		$("#container06").parent().parent().show();
//		/*问题整改环节多出3个图*/
		initContainerForPie('#container04',typeName,startTime,endTime,linkName,zhanbi);
		//同比
		initContainerForColumn('column','#container05','%','',typeName,startTime,endTime,linkName,2,zhanbi);
		//环比
		initContainerForColumn('column','#container06','%','',typeName,startTime,endTime,linkName,1,zhanbi);
	 }
 }

 
 /**
  * 设置按钮
  */
 function setDimension(dimension,method){
	 var temp = '<div class="col-1 row-1"><ul class="dimension"  id="dimension_ul">';
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
  *   //长:100% 高:100% ：：：'col-1','row-1','chart_col01'
  *   //长:50% 高:100%：：：'col-2','row-1','chart_col02'
  *   //长:50%,高:50%：：：'col-2','row-2','chart_col03'
  *   标题/周期/id
  */
 function setContainerDiv(col,row,chart_col,list_wrap,chart_title,tips,ids){
	 if(row!='row-1'){
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
	 }else{
		 var temp = '<div class="'+col+' '+row+'">\
			<div class="chart_wrap">\
			     <h3 class="chart_title">'+chart_title+'</h3>\
			     <p class="tips02">周期：'+tips+'</p>\
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
 }
 
 

 /**
  * 一级饼图数据获取
  */
 function initContainerForPie(target,type,startTime,endTime,linkName,calType){
 	jQuery.ajax({
 		type:"POST",
 		async:true,
 		url:basePath+"/account/getAgingData",
		data:"type="+type + "&startTime=" + startTime +"&endTime="+endTime +"&linkName="+linkName+"&calType="+calType,
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
 			                    style:{"color": "#ffffff", "fontSize": "12px", "fontWeight": "normal", "textShadow":false}
 			                }
 			            },
 			           series: {
			                point: {
			                    events: {
			                        'click': function() {
			                        	initSndLevel(this);
			                        }
			                    }
			                }
			            }
 			        
 			        },
 			        series: series
 				};
 			$(target).highcharts(config);
 		},
 		error:function(){
             alert("同步失败！");
         }
 	});
 }
 
 /**
  * 双级饼图数据获取
  */
 function initContainerForDoublePie(target,type,startTime,endTime,linkName,calType){
	 jQuery.ajax({
		 type:"POST",
		 async:false,
		 url:basePath+"/account/getAgingData",
		 data:"type="+type + "&startTime=" + startTime +"&endTime="+endTime +"&linkName="+linkName+"&calType="+calType,
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
			                    enabled: true,
			                    color: '#000000',
			                    format: '<b>{point.name}</b>: {point.percentage:.2f} %'
			                 },
							 shadow: false,
			                 center: ['50%', '50%']
						 },
						 series: {
				                point: {
				                    events: {
				                        'click': function() {
				                        	initSndLevel(this);
				                        }
				                    }
				                }
				            }
					 },
					 series: series
			 };
			 $(target).highcharts(config);
		 },
		 error:function(d){
			 alert("同步失败！");
		 }
	 });
 }
 
 /**
  * 柱状图数据获取
  */
 function initContainerForColumn(chartType,target,yAxisTitle,tooltip,type,startTime,endTime,linkName,agingType,calType){
	 jQuery.ajax({
		 type:"POST",
		 async:true,
		 url:basePath+"/account/getClumnData",
		 data:"type="+type + "&startTime=" + startTime +"&endTime="+endTime+"&linkName="+linkName+"&agingType="+agingType+"&calType="+calType,
		 dataType: "json",
		 success:function(data){
			 var categories = data.container01xAxis;//周期
			 var xValue = data.container01series;//部门,费用,问题类型列表
			 //封装数据
			 var series = [];
			 $.each(categories,function(m,n){
				 //柱状图,初始化12个月的值为0
				 var valueTemp = {
						 type: chartType,
						 name: n,
						 data: []
				 }
				 $.each(data.container01List,function(k,v){
					 $.each(xValue,function(j,l){
						 //年份和名称相同
						 if(v[0] == n && v[2] == l){
							 valueTemp.data[j] = v[5];
						 }
					 });
				 });
				 series.push(valueTemp);
			 });
			 //同比/环比计算
			 var seriesTemp = cloneObj(series);
			 
			 $.each(seriesTemp,function(k,v){
				 //从第二个周期开始
				 if(k > 0){
					 $.each(v.data,function(n,m){
						 //上一周期值
						 var prevTemp = seriesTemp[k-1].data[n];
						 //计算同比/环比
						 if(prevTemp != 0){
							 series[k].data[n] = parseFloat(((m - prevTemp) / prevTemp * 100).toFixed(2));
						 }else{
							 series[k].data[n] = 0;
						 }
					 });
				 }
			 });
			 //删除第一个周期的数据
			 series.splice(0,1);
			 //图表初始化
			 $(target).highcharts({
				 	colors:colors,
			    	navigation:navigation,
			    	legend:legend,
			    	credits:credits,
			    	exporting:exporting,
			        chart: {
			            type: 'column'
			        },
			        title: {
			            text: false
			        },
			        xAxis: {
			        	labels: {
			                style: {
//			                    whiteSpace:'normal'
			                },
			                rotation: -45
			            },
			            categories: data.container01series,
			            crosshair: true
			        },
			        yAxis: {
//			            min: 0,
			            title: {
			                text: yAxisTitle
			            }
			        },
			        tooltip: { //鼠标提示
			            formatter: function() {
			                return this.series.name+ this.x + '为' + Highcharts.numberFormat(this.y, 2, null, ' ')+ '%';
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
						            click: function(){
							            // 显示或隐藏图表
							            $('.dark').show();
							            $('.zoomchart').show();
							            $("#same_container3").hide();
							            $("#same_container2").show();
							            initContainerForColumn(chartType,$("#same_container2"),yAxisTitle,tooltip,type,startTime,endTime,linkName,agingType,calType)
						            }
					            }
					        }
			            }
			        },
			        series: series
			    });
		 },
		 error:function(){
			 alert("同步失败！");
		 }
	 });
 }
 
 /**
  * 钻取至二级页面
  * @param target
  */
 function initSndLevel(target){
	window.location.href = basePath + '/Account3rdController/toPage?linkName='+linkName+'&startTime='+startTime+'&endTime='+endTime+'&type='+typeName+'&bsnType='+target.name;
 }
 