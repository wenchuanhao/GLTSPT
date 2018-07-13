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
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<style>
	#uploadifive-uploadFiles{
   		margin-left: -48%;
    	margin-bottom: -13%;
	}
</style>
</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
			<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
			<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />	
	</div>
	
	<form name="form" id="pageForm" method="post"  >
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${purchaseForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${purchaseForm.pageSize}" id="pageSize"	name="pageSize"/>
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">需求部门：</th>
	    <td width="30%">
	    	<input id="columnC" name="columnC" value="${purchaseForm.columnC}" type="text" placeholder="请填写需求部门" class="text01" style="width:195px;"  />
		</td> 
  	    <c:if test="${(isAdmin eq 'true') || (isLeader eq 'true')}">
  		<th width="2%" align="right">经办人：</th>
	    <td width="30%">
	    	<input id="columnB" name="columnB" value="${purchaseForm.columnB}" type="text" placeholder="请填写经办人" class="text01" style="width:195px;"  />
		</td> 
		</c:if> 
	</tr>
	<tr>
  		<th width="9%" align="right">采购方案预估金额（万元）：</th>
	    <td width="30%">
	    	<input id="columnN" name="columnN" value="${purchaseForm.columnN}" type="text" placeholder="请填写采购方案预估金额" class="text01" style="width:195px;"  />
		</td> 
  		<th width="2%" align="right">采购方式（最后一次）：</th>
  		<td width="30%">
		     <select class="ui-select" id="columnJ"  name="columnJ" style="width:202px;" value="${purchaseForm.columnJ}">
		    			<option value="">请选择</option>
						<option value="公开招标">公开招标</option>
						<option value="邀请招标">邀请招标</option>
						<option value="公开比选">公开比选</option>
						<option value="邀请比选">邀请比选</option>
						<option value="竞争性谈判">竞争性谈判</option>
						<option value="公开询价">公开询价</option>
						<option value="邀请询价">邀请询价</option>
						<option value="单一来源采购">单一来源采购</option>
		     </select>  	
		</td>
	</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >采购项目列表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    			<input name="fileName" id="uploadFiles" type="file" class="btn_upload" style="display: none">               	
    	        <a class="btn_common01" href="javascript:downloadTemplate();" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>模板下载</span></a>  			    	
    			<a class="btn_common01" href="javascript:ev_add()" /><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>
		</div>    	
  	</div>
  	<div style="overflow: auto; width: 99%;">
	<table  style="table-layout:fixed;" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
<c:if test="${isLeader eq 'false' }">
<th>操作</th>
</c:if>
<th>序号</th>
<th>单位</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;月份&nbsp;&nbsp;&nbsp;&nbsp;
</th>
<th>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
采购项目名称
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</th>
<th>经办人</th>
<th>需求部门</th>
<th>项目所属年份</th>
<th>是否计划内项目</th>
<th>开支类型</th>
<th>自行/委托采购</th>
<th>代理公司名称</th>
<th>采购方式（首次）</th>
<th>采购方式（最后一次）</th>
<th>资格审查方式</th>
<th>当前进度（周报）</th>
<th>进度说明（周报）</th>
<th>采购方案预估金额（万元）</th>
<th>项目启动时间</th>
<th>工作小组会议召开时间</th>
<th>50万以上方案汇报时间<br/>/50万以下需求部门<br/>提交方案呈批时间</th>
<th>需求确认时长</th>
<th>需求确认时长超时预警</th>
<th>地市采购方案决策时间(首次)</th>
<th>地市采购方案决策时间(最后一次)</th>
<th>地市采购方案纪要下达时间</th>
<th>地市采购方案发文文号</th>
<th>省公司采购方案决策时间(首次)</th>
<th>省公司采购方案决策时间(最后一次)</th>
<th>省公司采购方案纪要<br/>/启动通知下达时间</th>
<th>省公司采购方案/启动通知发文文号</th>
<th>公告发布开始时间</th>
<th>公告发布截止时间</th>
<th>采购评审/谈判时间</th>
<th>需求确认完毕-评审时间（工作日）</th>
<th>采购组织实施单位/部门（集采类型）</th>
<th>采购组织实施单位/部门</th>
<th>需求单位</th>
<th>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
采购类型
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</th>
<th>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
采购内容
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th>项目当前实际状态</th>
<th>操作方式</th>
<th>电子采购项目编号</th>
<th>ES系统中的采购项目名称</th>
<th>采购方案决策层级（决策形式）</th>
<th>采购模式</th>
<th>采购结果确认层级（确认形式）</th>
<th>采购结果金额(万元)</th>
<th>地市公司采购结果确认时间</th>
<th>地市采购结果上报时间</th>
<th>省公司采购结果确认时间</th>
<th>合同签署时间</th>
<th>采购结果发文文号</th>
<th>合同编码</th>
<th>中选供应商</th>
<th>合同金额（万元）</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;下浮率&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th>采购节约金额（万元）</th>
<th>采购时长（工作日）</th>
<th>采购时长超时预警</th>
<th>合同归档时间</th>
<th>流标次数</th>
<th>流标后更改的采购方式</th>
<th>流标原因说明（每次流标都作说明）</th>
<th>采购工作投入天数</th>
<th>单一来源适用场景（大类）</th>
<th>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
单一来源适用场景（小类）
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</th>
<th>单一来源适用场景<br/>（具体产品服务备注）</th>
<th>技术商务比例是否符合标准</th>
<th>合同模板是否符合标准</th>
<th>技术评分模板招标<br/>文件模板是否符合标准</th>
<th>投诉情况</th>
<th>中标单位<br/>结算价格<br/>和合同单位<br/>结算价格<br/>是否完全一致</th>
<th>采购进度<br/>是否影响到成本<br/>/投资使用计划一致</th>
<th>特殊情况说明</th>
<th>是否取消</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;取消原因
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th>购货物或<br/>服务质量情况</th>
<th>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
备注
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</th>
<c:forEach items="${headInfos}" var="ho" varStatus="j">
 <th>${ho.column_cname}</th>
</c:forEach>
 </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
<c:if test="${isLeader eq 'false' }">
<td>
	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="javascript:ev_edit('${vo.id}')">编辑</a></span>
	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${vo.id}');">删除</a></span>
</td>
</c:if>

<td>${i.count}</td>
<td>${vo.units}</td>
<td>${vo.flowmonth}</td>
<td>${vo.column_A}</td>
<td>${vo.column_B}</td>
<td>${vo.column_C}</td>
<td>${vo.column_D}</td>
<td>${vo.column_E}</td>
<td>${vo.column_F eq 'CapexOpex'?'Capex+Opex':vo.column_F}</td>
<td>${vo.column_G}</td>
<td>${vo.column_H}</td>
<td>${vo.column_I}</td>
<td>${vo.column_J}</td>
<td>${vo.column_K}</td>
<td>${vo.column_L}</td>
<td><textarea class="text01"  readonly="readonly" rows="2"  type="text"  style="width:350px;min-height: 40px;">${vo.column_M}</textarea></td>
<td>${fn:contains(vo.column_N, '.') ?  fn:substring(vo.column_N, 0, fn:indexOf(vo.column_N, '.')+3)  : vo.column_N }</td>
<td>${vo.column_O}</td>
<td>${vo.column_P}</td>
<td>${vo.column_Q}</td>
<td>${vo.atime}</td>
<td ${vo.a_alert eq '正常' ? 'bgcolor="green"':'bgcolor="#FF0000"'}>${vo.a_alert}</td>
<td>${vo.column_R}</td>
<td>${vo.column_S}</td>
<td>${vo.column_T}</td>
<td>${vo.column_U}</td>
<td>${vo.column_V}</td>
<td>${vo.column_W}</td>
<td>${vo.column_X}</td>
<td>${vo.column_Y}</td>
<td>${vo.column_Z}</td>
<td>${vo.column_Aa}</td>
<td>${vo.column_Ab}</td>
<td>${vo.ctime}</td>
<td>${vo.column_Ac}</td>
<td>${vo.column_Ad}</td>
<td>${vo.column_Ae}</td>
<td>${vo.column_Af}</td>
<td>${vo.column_Ag}</td>
<td>${vo.column_Ah}</td>
<td>${vo.column_Ai}</td>
<td>${vo.column_Aj}</td>
<td>${vo.column_Ak}</td>
<td>${vo.column_Al}</td>
<td>${vo.column_Am}</td>
<td>${vo.column_An}</td>
<td>${fn:contains(vo.column_Ao, '.') ?  fn:substring(vo.column_Ao, 0, fn:indexOf(vo.column_Ao, '.')+3)  : vo.column_Ao }</td>
<td>${vo.column_Ap}</td>
<td>${vo.column_Aq}</td>
<td>${vo.column_Ar}</td>
<td>${vo.column_As}</td>
<td>${vo.column_At}</td>
<td>${vo.column_Au}</td>
<td>${vo.column_Av}</td>
<td>${fn:contains(vo.column_Aw, '.') ?  fn:substring(vo.column_Aw, 0, fn:indexOf(vo.column_Aw, '.')+3)  : vo.column_Aw }</td>
<%-- <td>${vo.frate}</td>--%>
<td><fmt:formatNumber value='${ vo.frate * 100 }' pattern="##.##"></fmt:formatNumber>%</td>
<td>${fn:contains(vo.smoney, '.') ?  fn:substring(vo.smoney, 0, fn:indexOf(vo.smoney, '.')+3)  : vo.smoney }</td>
<td>${vo.dtime}</td>
<td ${vo.e_alert eq '正常' ? 'bgcolor="green"':'bgcolor="#FF0000"'}>${vo.e_alert}</td>
<td>${vo.column_Ax}</td>
<td>${vo.column_Ay}</td>
<td>${vo.column_Az}</td>
<td>${vo.column_Ba}</td>
<td>${vo.ftime}</td>
<td>${vo.column_Bb}</td>
<td>${vo.column_Bc}</td>
<td>${vo.column_Bd}</td>
<td>${vo.column_Be}</td>
<td>${vo.column_Bf}</td>
<td>${vo.column_Bg}</td>
<td>${vo.column_Bh}</td>
<td>${vo.column_Bi}</td>
<td>${vo.column_Bj}</td>
<td><textarea class="text01"  readonly="readonly" rows="2"  type="text"  style="width:350px;min-height: 40px;">${vo.column_Bk}</textarea></td>
<td>${vo.column_Bl}</td>
<td><textarea class="text01"  readonly="readonly" rows="2"  type="text"  style="width:350px;min-height: 40px;">${vo.column_Bm}</textarea></td>
<td>${vo.column_Bn}</td>
<td><textarea class="text01"  readonly="readonly" rows="2"  type="text"  style="width:350px;min-height: 40px;">${vo.column_Bo}</textarea></td>
<c:forEach items="${headInfos}" var="to" varStatus="x">

<c:if test="${to.column_type == '2'}">
   <td>${fn:substring(vo[to.column_name],0,11)}</td>
</c:if>
<c:if test="${to.column_type == '1'}">
  <td>${vo[to.column_name]}</td>
</c:if>

</c:forEach>	  	  	
		 </tr>
	   </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="9">找不到对应的数据</td></tr>
	   </c:if>
	</table>
	 </div>
 	 <div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
<script type="text/javascript" >var basePath = "<%=basePath%>purchase";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
    <!--导入相关js-->
 	<!-- <link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script> -->
	
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadifive/uploadifive.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/jquery.uploadifive.js"></script>
	<script type="text/javascript" src="/SRMC/dc/purchase/ex_import.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/uploadcommon.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$('.ui-select').ui_select();
	setConfig("<%=basePath%>purchase/purchaseUpload");
	//重新写样式
	document.getElementById("uploadifive-uploadFiles").className = 'btn_common01';
	document.getElementById("uploadifive-uploadFiles").style = '';
	document.getElementById("uploadifive-uploadFiles").style.position = 'relative';
	document.getElementById("uploadifive-uploadFiles").style.height = '19px';
});
//新增   

function ev_add(){
    window.location.href = "<%=basePath%>purchase/addPurchase";
	//document.forms[0].action="<%=basePath%>purchase/addPurchase";
	//document.forms[0].submit();
}

/*模板下载*/
function downloadTemplate(){
	window.location.href="<%=basePath%>purchase/downloadTemplate";
}

//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>purchase/purchaseItem?key="+Math.random();
	document.forms[0].submit();
}

//重置
function ev_reset(){
	jQuery("#columnB").val("");
	jQuery("#columnC").val("");
	window.location.href="<%=basePath%>purchase/purchaseItem";
}

//编辑
function ev_edit(id){
	if(checkOperator(id)){
		window.location.href = "<%=basePath%>purchase/updatePurchase?id="+id;
	}
}

//删除服务
function ev_delete(id){
	if(checkOperator(id)){
		if(confirm("确定删除该记录么？")){
			ajaxDel(id);
		}
	}
}

//检查是否录入员、经办人、超级管理员
function checkOperator(id){
	var flag = false;
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>purchase/checkOperator",
        data:"id=" + id,
        success:function(data){
        	if(data == 1){
        		flag = true;
        	}else{
        		alert("无权限操作！");
        	}
        },
        error:function(){
            alert("操作失败！");
        }
    });
	return flag;
}

function ajaxDel(id){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>purchase/delPurchase",
        data:"id=" + id,
        success:function(data){
        	if(data == 1){
        		alert("删除成功！");
        		ev_search();
        	}else{
        		alert("删除失败！");
        	}
        },
        error:function(){
            alert("删除失败！");
        }
    });
}
	 
</script>
</body>
</html>
