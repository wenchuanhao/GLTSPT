
//加载表格数据

var  colors =  ['#a3daf8', '#fa9a6d','#ee95f5','#a975ef','#c6d989','#f1c775','#f681a8','#9d81f6','#819af6','#8fdbcc','#8eefa5','#efa78e'];
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
/**
 * 饼状图
 * @param target
 * @param type
 * @param month
 * @param year
 */
function initContainerForPie(target,type){
	$("#searchType").val(type);	
	var url = basePath+"purchaseExp/getChartData";
	$("#pageForm").ajaxSubmit({
 		type:"POST",
 		url:url,
 		dataType: "json",
 		success:function(data){
// 			var total = 0;
// 			$.each(data,function(k,v){
// 				
// 				total += v[1];
// 			});
// 			$.each(data,function(k,v){
// 				if(total>0){
// 				   v[1] = parseFloat((v[1] / total * 100).toFixed(2));
// 				}else{
// 				   v[1] = 0;
// 				}
// 			});
 			
 			
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
						formatter: function () {
							if(type == 'container10'){
								return this.point.name+'<br/>'+'金额：<b>'+this.y+'</b><br/>占比:<b>'+Highcharts.numberFormat(this.percentage,2)+'%</b>';
							}else{
								return this.point.name+'<br/>'+'个数：<b>'+this.y+'</b><br/>占比:<b>'+Highcharts.numberFormat(this.percentage,2)+'%</b>';
							}
			            }
			        },
 			        title: {
 			        	text: type=='container01'?'采购全景监控项目情况':(type=='container02'?'完成评审项目情况':(type=='container09'?'单一来源采购数量情况':(type=='container10'?'单一来源采购金额情况':'已完成项目情况')))
 			            
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
 * 柱状图数据获取
 */
function initContainerForTimeColumn(target,type){
	$("#searchType").val(type);	
	var url = basePath+"purchaseExp/getColumnData";
	$("#pageForm").ajaxSubmit({
		 type:"POST",
		 url:url,
		 dataType: "json",
		 success:function(data){
			 var series=[];
			 var categorie=[];
			 var sdata=[];
			
			 $.each(data,function(k,v){
				 categorie.push(v[0]);
				 sdata.push(v[1]);
	 			});
			 
			 series.push({
				 data:sdata
				 });
			 
			 var config ={
					 colors:colors,
	 			    	navigation:navigation,
	 			    	legend:{
	 			    		 enabled: false
	 			    	},
	 			        exporting: exporting,
					 chart: {
				            type: 'column'
				        },
				        title: {
				            text:  false
				        },
				        yAxis: {
				        	title:{
				        		 text:' '
				        	}
				          
				        },
				        xAxis: {
				            categories: categorie,
						    labels : {
								formatter : function() {

									  return '<div align="center" style="word-wrap: break-word;word-break: normal;width:80px">' + this.value + '</div>';
								}
							}
				        },
				        tooltip: { // 鼠标提示
							 formatter: function() {
								 if(type=="container11"){
									 return this.x + '为 ' + this.y+'万元'; 
								 }
								 if(type=="container07"){
									 return this.x + '项目数为 ' + this.y+'万元'; 
								 }
								 else{
									 return this.x + '为 ' + this.y; 									 
								 }
								
							 }
						},
				        credits: {
				            enabled: false
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