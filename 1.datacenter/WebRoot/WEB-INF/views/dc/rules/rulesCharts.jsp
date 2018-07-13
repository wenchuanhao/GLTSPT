<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<body>
<div class="gl_import_m">
	<jsp:include flush="true" page="include/queryForChart.jsp"></jsp:include>
  	<div class="tabpages">
    	<ul class="l">
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/query';document.forms[0].submit();">明细清单<em>(${queryCount })</em></li>
      		<li onclick="document.forms[0].action='<%=basePath%>rulesController/charts';document.forms[0].submit();" class="current">统计列表</li>
    	</ul>
    	<div class="otherButtons r">
	    	<a class="btn_common01" href="javascript:batchExport()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导出</span></a>
    	</div>
    	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
    		<tr>
    			<td><div id="chart-container1">……</div></td>
    			<td><div id="chart-container2">……</div></td>
    		</tr>
    		<tr>
    			<td><div id="chart-container3">……</div></td>
    			<td><div id="chart-container4">……</div></td>
    		</tr>
    	</table>
    	
  	</div>
     <div class="ge01"></div>
</div>
           
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>rulesController";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script type="text/javascript" src="/SRMC/dc/fusioncharts/js/fusioncharts.js"></script>
<script type="text/javascript" src="/SRMC/dc/fusioncharts/js/fusioncharts.charts.js"></script>
<script type="text/javascript" src="/SRMC/dc/fusioncharts/js/themes/fusioncharts.theme.ocean.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/charts.js"></script>
<script src="/SRMC/dc/js/checkboxList.js"></script>
<script type="text/javascript">
function getData(list){
	var result = {"data":[]};
	var data = $.parseJSON(list.replace("\r\n", "\\r\\n"));
	var gradesConfig = config;
	$.each(data,function(k,v){
		result.data.push({
            	"label": v[0],
               "value": v[1]
           });
	});
	gradesConfig.dataSource.dataset[0]=result;
	return gradesConfig;
}

$(function(){
	FusionCharts.ready(function () {
	    var salesAnlysisChart1 = new FusionCharts(getData('${grades}')).render("chart-container1");
		config.dataSource.chart.caption="按部门统计"
	    var salesAnlysisChart2 = new FusionCharts(getData('${organizations}')).render("chart-container2");
		config.dataSource.chart.caption="按制度状态统计"
	    var salesAnlysisChart3 = new FusionCharts(getData('${statuses}')).render("chart-container3");
		config.dataSource.chart.caption="按制度分类统计"
	    var salesAnlysisChart4 = new FusionCharts(getData('${types}')).render("chart-container4");
	});

});
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	repealRules();//废止
	viewFileUpload();//查看相关文档
	uploadFileInput();//上传文档
	batchRepealInput();//批量废止
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
        batchRepealInput();//批量废止
    });
    var $subBox = $("input[name='subBox']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
        //上传文档初始化
        uploadFileInput();
        batchRepealInput();//批量废止
    });
    
    //牵头部门
	var obj1 = $('#sel_01').data('ui-select');
	obj1.onClick = function(value) {
		 $("#leadDepId_input").val(value);
	}
    //制度状态
	var obj4 = $('#sel_04').data('ui-select');
	obj4.onClick = function(value) {
		 $("#status_input").val(value);
	}
	//制度等级
	var obj3 = $('#sel_03').data('ui-select');
	obj3.onClick = function(value) {
		 $("#rulesGrade_input").val(value);
	}
    
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>rulesController/charts";
	document.forms[0].submit();
}

//导出制度详情列表
function batchExport(){
	document.forms[0].action="<%=basePath%>rulesController/exportFile";
	document.forms[0].submit();
	document.forms[0].action="<%=basePath%>rulesController/charts";
}
</script>
</body>
</html>
