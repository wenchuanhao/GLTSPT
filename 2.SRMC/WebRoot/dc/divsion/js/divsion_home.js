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
var  colors = ['#40baff','#ff6d26','#dc1ced','#b2e31a', '#7d1dff'],
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
					     <p class="tips02">年份：'+tips+'</p>\
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


//判断某个字符串是否在数组里
var inArray = function(arr, item) {
  for (var i = 0; i < arr.length; i++) {
      if (arr[i] == item) {
          return true;
      }
  }
  return false;
};
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
/**
* 对Date的扩展，将 Date 转化为指定格式的String
* 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
* (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
* (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18  
*/
Date.prototype.format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};