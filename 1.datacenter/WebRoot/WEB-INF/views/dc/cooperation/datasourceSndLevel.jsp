<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath(); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<body style="background-color: #f2f2f2;">
<div class="jf_super_wrap" id="chart_super_div">
</div>
<script src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/dc/js/hchart/highcharts.js"></script>
<script src="/SRMC/dc/js/hchart/modules/exporting.js"></script>
<script src="/SRMC/dc/js/jquery.nicescroll.js"></script>
<script type="text/javascript" >var base_url="<%=basePath%>";var basePath = "<%=basePath%>/copperationController";</script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/sndlevel.js"></script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/exportExcel.js"></script>
<script type="text/javascript">

 var month = "${month}";//月份
 var year = "${year}";//年份
 var container = "${container}";//一级页面图表ID
$(function(){
	 if(container == "container01"){
	 		// 总营业收入构成（主题维度）	双级饼图
			// 总营业收入构成（部门维度）	饼图
			// 总营业收入构成（结算类型维度）	饼图
			
			// 总营业收入同比（结算类型维度）	柱状图
			// 总营业收入环比（结算类型维度）	柱状图
			// 总营业收入同比（主题维度）	柱状图
			// 总营业收入环比（主题维度）	柱状图
			// 总营业收入同比（部门维度）	柱状图
			// 总营业收入环比（部门维度）	柱状图
			// 全年趋势（按月，主题维度）	柱状图
			// 全年趋势（按月，部门维度）	柱状图
	 
	 		var dimension = ['主题','部门','结算类型','项目'];//维度切换
		 	$("#chart_super_div").append(setDimension(dimension,"changeChart01"));
		 	//中
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-1','chart_col02','list_wrap02','总营业收入构成',"${cycle}",'container01_01'));
		 	//小
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03','总营业收入同比',"${cycle}",'container01_02'));
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03','总营业收入环比',"${cycle}",'container01_03'));
		 	//大
		 	$("#chart_super_div").append(setContainerDiv('col-1','row-1','chart_col01','list_wrap01','总营业收入全年趋势',"${year}",'container01_04'));
		 	
		 	initContainerForDoublePie('#container01_01','sndlevel0101_zhuti',month,year);//主题维度
			//同比
			initContainerForColumn('column','#container01_02','总营业收入','%','sndlevel0102_zhuti',month,year,'YEAR');
			//环比
			initContainerForColumn('column','#container01_03','总营业收入','%','sndlevel0103_zhuti',month,year,'MON');
			//趋势
			initContainerForTimeColumn('column','#container01_04','主题维度','元','sndlevel0104_zhuti',month,year);

	 }
	 //服务人天二级页面初始化
	 if(container == "container02"){
		 var dimension = ['部门','主题'];//维度切换
		 $("#chart_super_div").append(setDimension(dimension,"changeChart02"));
		 //标题/周期/id
		 $("#chart_super_div").append(setContainerDiv('col-2','row-1','chart_col02','list_wrap02','服务人天构成',"${cycle}",'container02_01'));
		 $("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03','服务人天同比-部门维度',"${cycle}",'container02_02'));
		 $("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03','服务人天环比-部门维度',"${cycle}",'container02_04'));
		 $("#chart_super_div").append(setContainerDiv('col-1','row-1','chart_col01','list_wrap01','服务人天全年趋势',"${year}",'container02_03'));
	    // 图表 1
// 		initContainerForPie('#container01','sndlevel0201',month,year);//主题维度
		//图表1
		initContainerForPie('#container02_01','sndlevel0201_bumen',month,year);//部门维度
		//同比
		initContainerForColumn('column','#container02_02','服务人天','%','sndlevel0202',month,year,'YEAR');
		//环比
		initContainerForColumn('column','#container02_04','服务人天','%','sndlevel020201',month,year,'MON');
		
		initContainerForTimeColumn('column','#container02_03','服务人天','人天','sndlevel0203_bumen',month,year);
		
	 }

	if(container == "container03"){
			// 总成本构成及金额（指定月，部门维度）
			// 总成本构成及金额（指定月，项目维度）
			// 成本同比（项目维度）
			// 成本环比（项目维度）
			// 成本同比（部门维度）
			// 成本环比（部门维度）
			// 全年趋势（以月份为周期，部门维度）
			// 全年趋势（以月份为周期，项目维度）
	 
	 		var dimension = ['部门','项目'];//维度切换
		 	$("#chart_super_div").append(setDimension(dimension,"changeChart03"));
		 	//中
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-1','chart_col02','list_wrap02','总成本构成及金额',"${cycle}",'container03_01'));
		 	//小
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03','总成本同比',"${cycle}",'container03_02'));
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03','总成本环比',"${cycle}",'container03_03'));
		 	//大
		 	$("#chart_super_div").append(setContainerDiv('col-1','row-1','chart_col01','list_wrap01','总成本全年趋势',"${year}",'container03_04'));
		 	
		 	
		 	initContainerForPie('#container03_01','sndlevel0301_bumen',month,year);//部门维度
			//同比
			initContainerForColumn('line','#container03_02','部门维度','%','sndlevel0302_bumen',month,year,'YEAR');
			//环比
			initContainerForColumn('line','#container03_03','部门维度','%','sndlevel0303_bumen',month,year,'MON');
			//趋势
			initContainerForTimeColumn('line','#container03_04','部门维度','元','sndlevel0304_bumen',month,year);

	 }
	if(container == "container04"){
		// 人餐宴会成本（同比、环比）
		// 人餐食材成本（同比、环比）
		// 房晚服务成本（同比、环比）
		// 房晚成本成本（同比、环比）
		// 人均会场服务成本（同比、环比）
		// 人均会场成本（同比、环比）
		// 全年趋势（按月，人餐宴会和人餐食材、房晚服务、房晚成本、人均会场服务成本、人均会场成本）
			//小
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03','人均成本同比',"${cycle}",'container04_01'));
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03','人均成本环比',"${cycle}",'container04_02'));
		 	//大
		 	$("#chart_super_div").append(setContainerDiv('col-1','row-1','chart_col01','list_wrap01','人均成本全年趋势',"${year}",'container04_03'));
		 	//同比
			initContainerForColumn('line','#container04_01','成本','%','sndlevel0401',month,year,'YEAR');
			//环比
			initContainerForColumn('line','#container04_02','成本','%','sndlevel0402',month,year,'MON');
			//趋势
			initContainerForTimeColumn('line','#container04_03','成本','元','sndlevel0403',month,year);
	}

	 if(container == "container06"){
	 			 
			// 满意度同比（部门维度）
			// 满意度环比（部门维度）
			// 满意度同比（主题维度）
			// 满意度环比（主题维度）
			// 全年趋势（按月，部门维度）
			// 全年趋势（按月，主题维度）
	 
	 		var dimension = ['部门','主题'];//维度切换
		 	$("#chart_super_div").append(setDimension(dimension,"changeChart06"));
		 	//小
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03','满意度同比',"${cycle}",'container06_01'));
		 	$("#chart_super_div").append(setContainerDiv('col-2','row-2','chart_col03','list_wrap03','满意度环比',"${cycle}",'container06_02'));
		 	//大
		 	$("#chart_super_div").append(setContainerDiv('col-1','row-1','chart_col01','list_wrap01','满意度全年趋势',"${year}",'container06_03'));
		 	
			//同比
			initContainerForColumn('column','#container06_01','部门维度','%','sndlevel0601_bumen',month,year,'YEAR');
			//环比
			initContainerForColumn('column','#container06_02','部门维度','%','sndlevel0602_bumen',month,year,'MON');
			//趋势
			initContainerForTimeColumn('column','#container06_03','部门维度','','sndlevel0603_bumen',month,year);

	 }
	 
	 //数据与列表切换
 $('.switch02').click(function(){
   $(this).toggleClass('switch_list');
   if($(this).children('i').html()=='数据'){
     $(this).children('i').html('图表');
     $(this).prev().css('right',140+'px');
     $(this).next().hide();
     $(this).next().next().show();
     //加载数据
     loadChartDataAjax(this);
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
});

function loadChartDataAjax(target){
	//表格内容ID
	var tbodyId = "tbody" + $(target).next().attr("id");
	$("#"+tbodyId).html('');
	//切换主题/部门/结算类型
	var dimension_ul = $("#dimension_ul").find(".current").html();
	var typeTemp = "";
	if(dimension_ul == "主题"){
		typeTemp = "_zhuti";
	}else if(dimension_ul == "部门"){
		typeTemp = "_bumen";
	}else if(dimension_ul == "结算类型"){
		typeTemp = "_jiesuan";
	}else if(dimension_ul == "项目"){
		typeTemp = "_xiangmu";
	}else{
		typeTemp = "_other";
	}
	
	//根据类型调用不同sql语句
	var type = $(target).next().attr("id") + typeTemp;
	jQuery.ajax({
		 type:"POST",
		 async:true,
		 url:basePath+"/getChartData",
		 data:"type="+type + "&month=" + month +"&year="+year,
		 dataType: "json",
		 success:function(data){
		 	var head = data.head;
		 	var list = data.list;
		 	//计算占比
			if(inArray(head,'占比')){
			 	//数据,计算值
			 	getPercent(list,data.fromIndex);
			}
			if(inArray(head,'同比')){
				getYearOrMonPercent(list,data.fromIndex,'YEAR');
			}
			if(inArray(head,'环比')){
				getYearOrMonPercent(list,data.fromIndex,'MON');
			}
		 	//标题
		 	var thTemp = '<tr>';
		 	$.each(head,function(k,v){
		 		thTemp += '<th>'+v+'</th>';
		 	});
		 	thTemp += '</tr>';
		 	
		 	//内容
		 	$.each(list,function(n,m){
			 	var tdTemp = '<tr>';
			 	$.each(head,function(k,v){
			 		tdTemp += '<td>'+m[k]+'</td>';
			 	});
			 	tdTemp += '</tr>';
			 	thTemp += tdTemp;
		 	});
		 	$("#"+tbodyId).html(thTemp);
		 },
		 error:function(){
			 alert("查询失败！");
		 }
	 });
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
* 计算占比
* 数据
* 数据所在索引位置
**/
function getPercent(data,fromIndex){
	var total = 0;
	$.each(data,function(k,v){
		total += v[fromIndex];
	});
	$.each(data,function(k,v){
		if(total == 0){
			v.push('0%');
		}else{
			v.push(parseFloat((v[fromIndex] / total * 100).toFixed(2)) + '%');
		}
	});
}

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
/**
* 计算同比、环比
* 数据
* 数据所在索引位置，以逗号分隔
**/
function getYearOrMonPercent(data,fromIndex,type){
	var timeIndex = fromIndex[0];//时间索引
	var nameIndex = fromIndex[1];//分组名称索引
	var valueIndex = fromIndex[2];//值索引
	var tempData = cloneObj(data);
	$.each(tempData,function(k,v){
		var tempTime = v[timeIndex];//本期时间
		var tempName = v[nameIndex];//本期名称,类型
		var tempValue = v[valueIndex];//本期值
		var prevValue = 0;
		
		//获取当前日期
		var date = new Date((tempTime+"-01").replace(/-/g,"/"));
		//获取上一期时间字符串
		var prevTime = "";
		//同比
		if(type == "YEAR"){
			//获取上一年同月日期
			prevTime = (date.getFullYear() - 1) + tempTime.substr(4);
			//环比
		}else if(type == "MON"){
			//获取上个月日期
			var d = new Date(date.getFullYear(),date.getMonth() - 1,1);
			prevTime = d.format("yyyy-MM");
		}
		
		//根据时间,名称获取上一期的值
		$.each(tempData,function(n,m){
			//如果名称和上一期日期相同
			if(m[timeIndex] == prevTime && m[nameIndex] == tempName){
				prevValue = m[valueIndex];
				return false;
			}
		});
		//计算同比
		if(prevValue == 0){
			data[k].push('-');
		}else{
			data[k].push(parseFloat(((tempValue - prevValue) / prevValue * 100).toFixed(2)) + '%');//本期-上期 / 上期
		}
	});
}
</script>
</body>
</html>
