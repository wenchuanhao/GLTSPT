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
<div class="jf_super_wrap">
  <!-- 统计数据 S -->
  <div class="col-7 row-1 statistic">
    <div class="statistic_wrap">
        <p class="sta_title">${year }年经营目标</p>
        <div class="sta_con">
            <p id="01_fuwurentian">服务人天：> 0</p>
            <p id="01_yingyeshouru">营业收入：> 0元</p>
            <p id="01_zonghemanyidu">综合满意度：> 0</p>
            <p>&nbsp;</p>
        </div>
    </div>
  </div>
  <div class="col-7 row-1 statistic">
    <div class="statistic_wrap">
        <p class="sta_title">${year }年经营目标</p>
        <div class="sta_con">
            <p id="02_yusuanjiesuanqian">预算结算前：0万</p>
            <p id="02_yusuanjiesuanhou">预算目标：0万</p>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
        </div>
    </div>
  </div>
  <div class="col-7 row-1 statistic">
    <div class="statistic_wrap">
        <p class="sta_title">经营收入</p>
        <div class="sta_con">
            <p id="03_benyueshouru">本月营收：0万</p>
            <p id="03_benyuemaoli">本月毛利：0万</p>
            <p id="03_nianduleijiyingshou">年度累计营收：0万（0%）</p>
            <p>&nbsp;</p>
        </div>
    </div>
  </div>
  <div class="col-7 row-1 statistic">
    <div class="statistic_wrap">
        <p class="sta_title">业务规模</p>
        <div class="sta_con">
            <p id="04_benyuetuandui">本月团队数：0个</p>
            <p id="04_benyuerenti">本月人天：0天</p>
            <p id="04_leijituandui">累计团队：0</p>
            <p id="04_leijirenti">累计人天：0（0%）</p>
        </div>
    </div>
  </div>
  <div class="col-7 row-1 statistic">
    <div class="statistic_wrap">
        <p class="sta_title">人均成本</p>
        <div class="sta_con">
            <p id="05_benyuezongchengben">本月总成本：0万</p>
            <p id="05_nianduleijichengben">年度累计成本：0万</p>
            <p id="05_rencanyanhuichengben">人餐宴会成本：0</p>
            <p id="05_rencanshicaichengben">人餐食材成本：0</p>
        </div>
    </div>
  </div>
  <div class="col-7 row-1 statistic">
    <div class="statistic_wrap">
        <p class="sta_title">人均成本</p>
        <div class="sta_con">
            <p id="06_fanwanchengben">房晚成本：0</p>
            <p id="06_fanwanfuwuchengben">房晚服务成本：0</p>
            <p id="06_renjunhuichangfuwuchengben">人均会场服务成本：0</p>
            <p id="06_renjunhuichangchengben">人均会场成本：0</p>
        </div>
    </div>
  </div>
  <div class="col-7 row-1 statistic">
    <div class="statistic_wrap">
        <p class="sta_title">预算</p>
        <div class="sta_con">
            <p id="07_benyueyusuanjiesuanqian">本月预算结算前：0万</p>
            <p id="07_nianduleijiwanchengqian">年度累计完成结算前：0万（0%）</p>
            <p id="07_benyueyusuanjiesuanhou">本月预算结算后：0万</p>
            <p id="07_nianduleijiwanchenghou">年度累计完成结算后：0万（0%）</p>
        </div>
    </div>
  </div>
  <!-- 统计数据 E -->
  <!-- 图表 S -->
  <div class="col-1 row-1">
    <div class="chart_wrap">
        <h3 class="chart_title">收入指标完成情况</h3>
        <span class="switch02 switch_list"><i>数据</i></span>
        <div class="chart_col01" id="container01"></div>
        <div style="display: none;" class="list_wrap01 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable">
                <tbody>
                    <tr>
                    	<th>月份</th>
                    	<th>当月完成</th>
                    	<th>累计完成</th>
                    	<th>进度差</th>
                    	<th>完成比例</th>
                    </tr>
                    <c:forEach items="${dataContainer01}" var="item" varStatus="i">
	                    <tr>
	                        <td>${item[0]}</td>
	                        <td>${item[1]}</td>
	                        <td>${item[2]}</td>
	                        <td>${item[3]}</td>
	                        <td>${item[4]}</td>
	                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
  </div>
  <div class="col-1 row-1">
    <div class="chart_wrap">
        <h3 class="chart_title">服务指标完成情况</h3>
        <span class="switch02 switch_list"><i>数据</i></span>
        <div class="chart_col01" id="container02"></div>
        <div style="display: none;" class="list_wrap01 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable">
                <tbody>
                    <tr>
                    	<th>月份</th>
                    	<th>当月完成</th>
                    	<th>累计完成</th>
                    </tr>
                    <c:forEach items="${dataContainer02}" var="item" varStatus="i">
	                    <tr>
	                        <td>${item[0]}</td>
	                        <td>${item[1]}</td>
	                        <td>${item[2]}</td>
	                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
  </div>
  <div class="col-1 row-1">
    <div class="chart_wrap">
        <h3 class="chart_title">成本使用情况</h3>
        <span class="switch02 switch_list"><i>数据</i></span>
        <div class="chart_col01" id="container03"></div>
        <div style="display: none;" class="list_wrap01 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable">
                <tbody>
                    <tr>
                    	<th>月份</th>
                    	<th>本月成本合计（万元）</th>
                    	<th>当月累计（万元）</th>
                    </tr>
                    <c:forEach items="${dataContainer03}" var="item" varStatus="i">
	                    <tr>
	                        <td>${item[0]}</td>
	                        <td>${item[1]}</td>
	                        <td>${item[2]}</td>
	                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
  </div>
  <div class="col-1 row-1">
    <div class="chart_wrap">
        <h3 class="chart_title">人均成本使用情况</h3>
        <span class="switch02 switch_list"><i>数据</i></span>
        <div class="chart_col01" id="container04"></div>
        <div style="display: none;" class="list_wrap01 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable">
                <tbody>
                    <tr>
                    	<th>月份</th>
                    	<th>项目类别</th>
                    	<th>金额</th>
                    	<th>全年平均</th>
                    </tr>
                    <c:forEach items="${dataContainer04}" var="item" varStatus="i">
	                    <tr>
	                        <td>${item[0]}</td>
	                        <td>${item[1]}</td>
	                        <td>${item[2]}</td>
	                        <td>${item[3]}</td>
	                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
  </div>
  <div class="col-1 row-1">
    <div class="chart_wrap">
        <h3 class="chart_title">预算完成情况</h3>
        <ul class="chart_bar row-1">
          <li onclick="changeChart05(this,'结算前','container0501')" class="current">结算前</li>
          <li onclick="changeChart05(this,'结算后','container0502')">结算后</li>
        </ul>
        <span class="switch02 switch_list"><i>数据</i></span>
        <div class="chart_col01" id="container05"></div>
        <div style="display: none;" class="list_wrap01 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable">
                <tbody id="container05_tbody">
                    <tr>
                    	<th>月份</th>
                    	<th>当月完成（结算前）</th>
                    	<th>累计完成（结算前）</th>
                    	<th>进度差</th>
                    	<th>完成比例</th>
                    </tr>
                    <c:forEach items="${dataContainer05}" var="item" varStatus="i">
	                    <tr>
	                        <td>${item[0]}</td>
	                        <td>${item[1]}</td>
	                        <td>${item[2]}</td>
	                        <td>${item[3]}</td>
	                        <td>${item[4]}</td>
	                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
  </div>
  <div class="col-1 row-1">
    <div class="chart_wrap">
        <h3 class="chart_title">满意度达标情况</h3>
        <ul class="chart_bar">
          <li onclick="changeChart06(this,'部门维度')" class="current">部门维度</li>
          <li onclick="changeChart06(this,'主题维度')">主题维度</li>
        </ul>
        <span class="switch02 switch_list"><i>数据</i></span>
        <div class="chart_col01" id="container06"></div>
        <div style="display: none;" class="list_wrap01 scroll">
        	<a href="javascript:void(0);" onclick="export_table(this)" class="export_chart">导出</a>
            <table class="listTable">
                <tbody id="container06_tbody">
                    <tr>
                    	<th>月份</th>
                    	<th>部门</th>
                    	<th>满意度</th>
                    	<th>累计</th>
                    	<th>月平均</th>
                    	<th>全年平均</th>
                    </tr>
                    <c:forEach items="${dataContainer06}" var="item" varStatus="i">
	                    <tr>
	                        <td>${item[0]}</td>
	                        <td>${item[1]}</td>
	                        <td>${item[2]}</td>
	                        <td>${item[3]}</td>
	                        <td>${item[4]}</td>
	                        <td>${item[5]}</td>
	                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
  </div>
  <!-- 图表 E -->
</div>
<script src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/dc/js/hchart/highcharts.js"></script>
<script src="/SRMC/dc/js/hchart/modules/exporting.js"></script>
<script src="/SRMC/dc/js/jquery.nicescroll.js"></script>
<script type="text/javascript" >var base_url="<%=basePath%>";var basePath = "<%=basePath%>/copperationController";</script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/home.js"></script>
<script type="text/javascript" src="/SRMC/dc/cooperation/js/exportExcel.js"></script>
<script type="text/javascript">
var year = "${year }";
$(function(){
	// 图表 1
	  initContainerFor125('line','收入指标完成情况','全年累计收入','万元','container01','实际收入','目标收入','#container01');
    //图表2
	  initContainerFor125('line','服务指标完成情况','全年服务人天','人天','container02','实际人天','目标人天','#container02');
    //图表5
	  initContainerFor125('line','预算完成情况','结算前','万元','container0501','实际收入','目标收入','#container05');
	  
	  initContainerFor03('container03');
	  
	  initContainerFor04('container04');
	  
	  initContainerFor06('scatter','满意度达标情况','部门维度','百分比','container0601');
	
    
});

 $(document).ready(function(){
 	// 将所有top初始化
 	
    //经营目标01
 	getTopData("01_fuwurentian","服务人天：> ","天");
 	getTopData("01_yingyeshouru","营业收入：> ","万");
 	getTopData("01_zonghemanyidu","综合满意度：","");
 	//经营目标02
 	getTopData("02_yusuanjiesuanqian","预算结算前：","万");
 	getTopData("02_yusuanjiesuanhou","预算目标：","万");
 	//经营收入
 	getTopData("03_benyueshouru","本月营收：","万");
 	getTopData("03_benyuemaoli","本月毛利：","万");
 	getTopData("03_nianduleijiyingshou","年度累计营收：","");
 	//业务规模
    getTopData("04_benyuetuandui","本月团队数：","个");
    getTopData("04_benyuerenti","本月人天：","天");
    getTopData("04_leijituandui","累计团队：","");
    getTopData("04_leijirenti","累计人天：","");
 	//人均成本
    getTopData("05_benyuezongchengben","本月总成本：","万");
    getTopData("05_nianduleijichengben","年度累计成本：","万");
    getTopData("05_rencanyanhuichengben","人餐宴会成本：","");
    getTopData("05_rencanshicaichengben","人餐食材成本：","");
    //人均成本
    getTopData("06_fanwanchengben","房晚成本：","");
    getTopData("06_fanwanfuwuchengben","房晚服务成本：","");
    getTopData("06_renjunhuichangfuwuchengben","人均会场服务成本：","");
    getTopData("06_renjunhuichangchengben","人均会场成本：","");
    //预算
 	getTopData("07_benyueyusuanjiesuanqian","本月预算结算前：","万");
 	getTopData("07_nianduleijiwanchengqian","年度累计完成结算前：","");
 	getTopData("07_benyueyusuanjiesuanhou","本月预算结算后：","万");
 	getTopData("07_nianduleijiwanchenghou","年度累计完成结算后：","");
});
//图表五，预算完成情况切换
function changeChart05(target,subTitle,contanier){
	initContainerFor125('line','预算完成情况',subTitle,'万元',contanier,'实际收入','目标收入','#container05');
	getChartData(contanier,"container05_tbody");
	$(target).parent().children().removeClass('current');
	$(target).addClass('current');
}

function changeChart06(target,subTitle){
	$(target).parent().children().removeClass('current');
	$(target).addClass('current');
	if(subTitle == '部门维度'){
		initContainerFor06('scatter','满意度达标情况','部门维度','百分比','container0601');
		getChartData('container05_0601',"container06_tbody");
	}else if(subTitle == '主题维度'){
		initContainerFor0602('scatter','满意度达标情况','主题维度','百分比','container0602');
		getChartData('container05_0602',"container06_tbody");
	}
}

function getChartData(type,tbodyId){
	jQuery.ajax({
		 type:"POST",
		 async:true,
		 url:basePath+"/getChartData",
		 data:"type="+type + "&month=&year="+year,
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
// 			 alert("查询失败！");
		 }
	 });
}
/**
id,提示语(带:冒号),单位
*/
function getTopData(target,tips,unit){
	jQuery.ajax({
		 type:"POST",
		 async:true,
		 url:basePath+"/getTopData",
		 data:"target="+target,
		 dataType: "json",
		 success:function(data){
			 if(data.value){
			 	$("#"+target).html(tips + data.value + unit);
			 	$("#"+target).prop("title",tips + data.value + unit);
			 }else{
			 	$("#"+target).html(tips + 0 + unit);
			 	$("#"+target).prop("title",tips + 0 + unit);
			 }
			 if(data.color){
			 	$("#"+target).css("color","red");
			 }
		 },
		 error:function(){
// 			 alert("查询失败！");
		 }
	 });
}
</script>
</body>
</html>
