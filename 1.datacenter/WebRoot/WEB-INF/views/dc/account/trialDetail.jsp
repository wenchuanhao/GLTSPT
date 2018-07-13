<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>报账详情</title>
		<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>	
		<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
		<link rel="stylesheet" type="text/css" href="/SRMC/dc/css/combotree/easyui.css">
		<script type="text/javascript" src="/SRMC/dc/js/combotree/jquery.easyui.min.js"></script>  
		<script type="text/javascript">
			var j = jQuery.noConflict(true);
			var basePath = "<%=basePath%>";
		</script>
		<link href="/SRMC/rmpb/css/date_input.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/common2.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="gl_import_m">
<div class="searchCondition"><span class="searchCondition_header">接单信息</span>
	<c:if test="${iSbaozAdmin}"><input name="" type="button" onclick="doDraft()" class="btn_common02 r" value="保存" /></c:if>
</div>
<form  action="" method="post" id="submitForm">
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="readOnlyTable">
   <input type="hidden" name="id" id="id" value="${info.id}"/>
 		<input type="hidden" name="cosId" id="cosId" value="${info.cosId}"/>
 		<input type="hidden" name="orderTemp" id="orderTemp" value="${info.orderId}"/>
	  <input type="hidden" id="departmentId" name="departmentId" value="${info.departmentId }"/>
	  <input type="hidden" id="trialAccount" name="trialAccount" value="${info.trialAccount }"/>
	  <input type="hidden" id="trialAccountId" name="trialAccountId" value="${info.trialAccountId }"/>
	  <input type="hidden" id="department" name="department"  value="${info.department }"/>
	  <input type="hidden" name="presenterId" id="presenterId_input" value="${info.presenterId}"/>
		<input type="hidden" name="sementPeopleId" id="sementPeopleId_input" value="${info.sementPeopleId}"/>
		<input type="hidden" name="costType" id="costType" value="${info.costType}"/>
		<input type="hidden" name="createName" id="createName" value="${info.createName}"/>
		<input type="hidden" name="userId" id="userId" value="${info.userId}"/>
		<input type="hidden" name="createDate" id="createDate" value="${info.createDate}"/>
  		<input type="hidden" name="currentLink" id="currentLink" value="${info.currentLink}"/>
  		<input type="hidden" name="status" id="status" value="${info.status}"/>
  		<input type="hidden" name="istimeOut" id="istimeOut" value="${info.istimeOut}"/>
  		<input type="hidden" name="timeOut" id="timeOut" value="${info.timeOut}"/>
  		<input type="hidden" name="noticeTime" id="noticeTime" value="${info.noticeTime}"/>
  		<input type="hidden" name="noticeUpTime" id="noticeUpTime" value="${info.noticeUpTime}"/>
  		<input type="hidden" name="noticeEndTime" id="noticeEndTime" value="${info.noticeEndTime}"/>
  		<input type="hidden" name="noPassReason" id="noPassReason" value="${info.noPassReason}"/>
  		<input type="hidden" name="tDTime" id="tDTime" value="${info.tDTime}"/>
  		<input type="hidden" name="trialFlat" id="trialFlat" value="${info.trialFlat}"/>
  		<input type="hidden" name="rectifyTime" id="rectifyTime" value="${info.rectifyTime}"/>
  <c:if test="${!iSbaozAdmin}">
  <tr>
    <th width="25%">报账单号</th>
    <td width="25%">${info.orderId }</td>
    <th width="25%">费用类型</th>
    <td>${info.costType }</td>
    </tr>
  <tr>
    <th>报帐人</th>
    <td>${info.sementPeople }</td>
    <th>金额</th>
    <td>${info.cost }</td>
    </tr>
    <tr>
    <th>交单人</th>
    <td>${info.presenter }</td>
    <th>纸质提交财务时间</th>
    <td><fmt:formatDate value="${info.pageSubmitDate }" pattern="yyyy-MM-dd HH:mm"/></td>
    </tr>
     <tr>
    <th>是否包含抵扣</th>
    <td><c:if test="${info.includeBuckle=='1' }">是</c:if><c:if test="${info.includeBuckle=='0' }">否</c:if></td>
    <th>发票时间</th>
    <td><fmt:formatDate value="${info.reachSement }" pattern="yyyy-MM-dd"/></td>
    </tr>
    <tr>
    <th>是否专票抵扣</th>
    <td><c:if test="${info.deduction=='1' }">是</c:if><c:if test="${info.deduction=='0' }">否</c:if></td>
    <th>创建人</th>
    <td>${info.createName }</td>
    </tr>
     <tr>
    <th>报账部门</th>
    <td>${info.department }</td>
    <th>初审会计</th>
    <td>${info.trialAccount }</td>
    </tr>
    <tr>
    	<th>创建时间</th>
    	<td><fmt:formatDate value="${info.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    	<th>摘要</th>
    	<td>${info.accountAbstract }</td>
    </tr>
    </c:if>
    <c:if test="${iSbaozAdmin}">
    <tr>
    <th width="25%">报账单号</th>
    <td width="25%"><input style="width: 165px" type="text" class="text01" name="orderId"  id="sel_01" value="${info.orderId }" onchange="changeCheckId()" onblur="blurCheckId()" /></td>
    <th width="25%">费用类型</th>
    <td>
    <div class="checxTreeWrap"  style="width:170px;">
    	<select  multiple style="width:150px" id="cosIdTemp" name="cosIdTemp" class="easyui-combotree" data-options="url:'<%=basePath%>/account/getCostType',method:'get'" multiple style="width:180px;">
    		<option selected="selected">${info.costType }</option>
    	</select>
    </div>
    </td>
    </tr>
  <tr>
    <th>报帐人</th>
    <td><input   onfocus="if(this.value=='请填写报账人'){this.value='';};this.style.color='#333333';autoCompletes();" 
			                     onkeyup="clean()"
			                     onblur="if(this.value==''||this.value=='请填写报账人'){this.style.color='#b6b6b6';}" placeholder="请填写报账人"  type="text" class="text01" name="sementPeople" value="${info.sementPeople }"  id="sel_02"/></td>
   <th>金额</th>
    <td>
    	<input type="text" class="text01" name="cost" value="${info.cost }" id="cost"  onblur="if(isNaN(this.value))this.value=''"/>
    </td>
    </tr>
    <tr>
    <th>交单人</th>
    <td><input  onfocus="if(this.value=='请填写交单人'){this.value='';};this.style.color='#333333';autoCompletes();" 
			                     onkeyup="clean()"
			                     onblur="if(this.value==''||this.value=='请填写交单人'){this.style.color='#b6b6b6';}" placeholder="请填写交单人" type="text" class="text01" name="presenter" value="${info.presenter }" id="sel_03"/></td>
    <th>纸质提交财务时间</th>
    <td><div class="date l" style="width:160px;"><input  type="text"   name="pageSubmitDate" id="pageSubmitDate"  value="<fmt:formatDate value="${info.pageSubmitDate}" pattern="yyyy-MM-dd HH:mm"/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',alwaysUseStartDate:true})" placeholder="请您输入"  class="text02 l" style="width:90%;" /><i></i></div></td>
    </tr>
     <tr>
    <th>是否包含抵扣</th>
    <td>
    <span><input type="radio"   name="includeBuckle" id="includeBuckle" <c:if test="${info.includeBuckle=='1' }">checked="checked"</c:if>  value="1"> 是</span><span><input type="radio"  name="includeBuckle" id="includeBuckles" <c:if test="${info.includeBuckle=='0' }">checked="checked"</c:if> value="0"> 否</span>
    <th>发票时间</th>
    <td><div class="date l" style="width:160px;"><input   type="text"  name="reachSement" id="reachSement" value="<fmt:formatDate value="${info.reachSement}" pattern="yyyy-MM-dd"/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  placeholder="请您输入"  class="text02 l" style="width:90%;" /><i></i></div></td>
    </tr>
    <tr>
    <th>是否专票抵扣</th>
    <td>
    <span><input type="radio"   name="deduction" id="deduction" <c:if test="${info.deduction=='1' }">checked="checked"</c:if> value="1"> 是</span><span><input type="radio"  name="deduction" id="deduction" <c:if test="${info.deduction=='0' }">checked="checked"</c:if> value="0"> 否</span>
    <th>创建人</th>
    <td>${info.createName }</td>
    </tr>
     <tr>
     <th>报账部门</th>
    <td><input type="text" id="department1" name="department1" class="text01" title="${info.department }" onfocus="autoCompletes();" value="${info.department }"/></td>
    <th align="right">初审会计：</th>
	    <td width="20%">
	    <input type="text" id="trialAccount1" name="trialAccount1" class="text01" onfocus="autoCompletesAccount(this);"  value="" />
	    <span id="selected_trialAccount">
			</span>
	    </td>
    </tr>
    <tr>
    	<th>创建时间</th>
    	<td><fmt:formatDate value="${info.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<th>摘要</th>
    	<td><input type="text" class="text01" name="accountAbstract" value="${info.accountAbstract }" id="accountAbstract" /></td>
    </tr>
    </c:if>
  </table>
  </form>
  <div class="ge01"></div>
  <div class="tabpages">
    <ul class="l">
      <li class="current">发票信息</li>
      
      	<c:set value="0" var="sum" />
      	<c:set value="0" var="taxNum" />
      	<c:set value="0" var="totalNum" />
	    <c:forEach items="${invoiceList}" var="item">
	        <c:set value="${sum + item.moneyNoTax}" var="sum" />
	        <c:set value="${taxNum + item.taxNum}" var="taxNum" />
	        <c:set value="${totalNum + item.moneyNoTax + item.taxNum}" var="totalNum" />
	    </c:forEach>
      <font color="red" size="3">&nbsp;&nbsp;&nbsp;温馨提示：发票金额(不含税)为：
      				<i id="invoice_total"><fmt:formatNumber value="${sum}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></i>，税额为：
      				<i id="taxNum_total"><fmt:formatNumber value="${taxNum}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></i>，总额为：
      				<i id="total_total"><fmt:formatNumber value="${totalNum}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></i>。
      </font>
    	
    </ul>
    <div class="otherButtons r">
    	<a name="n_invoice" class="btn_common01" href="javascript:;" value="id_invoiceAdd--${info.id}" id="-${info.id}"><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>
    	<a class="btn_common01" onclick="batchDel();"><img src="/SRMC/dc/images/btnIcon/del.png" /><span>删除</span></a>
    </div>
  </div>
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
  <tr>
  	 <th><input id="checkAll" type="checkbox" onclick="ev_checked()"></th>
     <th>发票类型</th> 
     <th>货物或应税劳务名称</th> 
     <th>发票代码</th> 
     <th>发票号码</th> 
     <th>开票日期</th> 
     <th>金额(不含税)</th>
     <th>税额</th>
     <th>税率(%)</th>
     <th>购方纳税人识别号</th>
     <th>购方纳税人名称</th>
     <th>销方纳税人识别号</th>
     <th>销方纳税人名称</th>
     <th>操作</th>
  </tr>
  <c:forEach items="${invoiceList}" var="item" varStatus="i">
  	<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
  		<td><input name="subBox" id="subBox" type="checkbox" value="${item.id}"></td>
	  	<td>${item.invoiceType}</td>
	  	<td>${item.goodsName}</td>
	  	<td>${item.invoiceCode}</td>
	  	<td>${item.invoiceNum}</td>
	  	<td>${item.createDate}</td>
	  	<td>${item.moneyNoTax}</td>
	  	<td>${item.taxNum}</td>
	  	<td>${item.taxRate}</td>
	  	<td>${item.gfTaxpayerNum}</td>
	  	<td>${item.gfTaxpayerName}</td>
	  	<td>${item.xfTaxpayerNum}</td>
	  	<td>${item.xfTaxpayerName}</td>
	  	<td>
	  		<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a id="${item.id}-${info.id}" name="n_invoice" href="javascript:;">编辑</a></span>
	  		<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${item.id}');">删除</a></span>
	  	</td>
  	</tr>
  </c:forEach>
  </table>
  <div class="ge01"></div>
  <div class="searchCondition"><span class="searchCondition_header">时效信息</span></div>
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="readOnlyTable">
  <tr>
    <th width="25%">发起报账时长</th>
    <td width="25%">${fqbzTime }</td>
    <th width="25%">业务审批时长</th>
    <td>${ywspTime }</td>
    </tr>
  <tr>
    <th>整改总时长</th>
    <td><c:if test="${ proHaoshiSum =='0'}">-</c:if><c:if test="${proHaoshiSum!='0'}">${proHaoshiSum }</c:if></td>
    <th>总时长</th>
    <td>${f2 }</td>
  </tr>
  </table>
  <div class="ge01"></div>
  <div class="searchCondition"><span class="searchCondition_header">初审信息</span></div>
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="readOnlyTable">
  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
  <tr>
    <th width="25%">单据是否有问题</th>
    <td width="25%"><c:if test="${ item[10]=='0'}">否</c:if><c:if test="${item[10]!='0'}">是</c:if></td>
    <th width="25%">问题总量</th>
    <td><c:if test="${ proNum =='0'}">-</c:if><c:if test="${proNum!='0'}">${proNum }</c:if></td>
    </tr>
  <tr>
    <th>是否超时</th>
    <td><c:if test="${ empty item[7] || item[7]=='0'}">否</c:if><c:if test="${not empty item[7] && item[7]!='0'}">是</c:if></td>
    <th>超时总时长</th>
    <td><c:if test="${ empty item[7] || item[7]=='0'}">-</c:if><c:if test="${not empty item[7] && item[7]!='0'}">${item[7]}</c:if></td>
    </tr>
    <tr>
    <th>最耗时问题类型</th>
    <td>
    <c:if test="${ empty haoshiType }">-</c:if>
      <jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="BZ_TCTYPE"></jsp:param>
					<jsp:param name="paramCode" value="${haoshiType }"></jsp:param>
				</jsp:include> </td>
    <th>问题整改耗时</th>
    <td>${proHaoshiSum }</td>
    </tr>
     <tr>
    <th>初审后是否存在问题</th>
    <td><c:if test="${ item[8]=='0'}">否</c:if><c:if test="${ item[8]!=0}">是</c:if></td>
    <th>整改状态</th>
    <td>
    <c:if test="${ empty info.status}">-</c:if>
    <c:if test="${info.status=='1'}">整改中</c:if>
    <c:if test="${info.status=='2'}">无问题</c:if>
    <c:if test="${info.status=='3'}">整改结束</c:if>
    <c:if test="${info.status=='4'}">退单</c:if></td>
    </tr>
    <tr>
    <th>经办会计</th>
    <td>${info.trialAccount }</td>
    <th>达到财务付款条件时间</th>
    <td><fmt:formatDate value="${info.paymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    </tr>
     <tr>
    <th>问题整改回送信息</th>
    <td colspan="3">
	</td>
    </tr>
    </c:forEach>
  </table>
  <div class="ge01"></div>
  <div class="searchCondition"><span class="searchCondition_header">问题列表</span></div>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
  <tr>
    <th>序号</th>
    <th>问题提出阶段</th>
    <th>问题类型</th>
    <th>问题详情</th>
    <th>整改状态</th>
    <th>创建时间</th>
    <th>是否超时</th>
    <th>整改耗时</th>
    <th>通知整改时间</th>
    <th>整改结束时间</th>
    <th>超时时长</th>
    <th>通知超时时间</th>
  </tr>
  <c:forEach items="${problemList }" var="list" varStatus="i">
  <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
    <td >${i.index+1 }</td>
    <td>
    <jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="BZ_PHASE"></jsp:param>
					<jsp:param name="paramCode" value="${list.phase }"></jsp:param>
				</jsp:include> </td>
    
    <td>
    <jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="BZ_TCTYPE"></jsp:param>
					<jsp:param name="paramCode" value="${list.type }"></jsp:param>
				</jsp:include> 
    </td>
    <td >${list.detail }</td>
    <td ><em><c:if test="${ empty list.status}">-</c:if><c:if test="${list.status=='1'}">整改中</c:if>
    <c:if test="${list.status=='2'}">整改通过</c:if>
    <c:if test="${list.status=='3'}">整改不通过</c:if>
    <c:if test="${list.status=='4'}">退单</c:if></em></td>
    <td><fmt:formatDate value="${list.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
    <td ><c:if test="${ empty list.isOutTime}">-</c:if><c:if test="${list.isOutTime=='1'}">是</c:if><c:if test="${list.isOutTime=='0'}">否</c:if></td>
    <td ><c:if test="${ empty list.setTime}">-</c:if><c:if test="${not empty list.setTime}">${list.setTime}</c:if></td>
    <td ><c:if test="${ empty list.startTime}">-</c:if><c:if test="${not empty list.startTime}"><fmt:formatDate value="${list.startTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></c:if></td>
    <td ><c:if test="${ empty list.endTime}">-</c:if><c:if test="${not empty list.endTime}"><fmt:formatDate value="${list.endTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></c:if></td>
    <td ><c:if test="${ empty list.outDay}">-</c:if><c:if test="${not empty list.outDay}">${list.outDay}</c:if></td>
    <td ><c:if test="${ empty list.noticeOutTime}">-</c:if><c:if test="${not empty list.noticeOutTime}"><fmt:formatDate value="${list.noticeOutTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></c:if></td>
  </tr>
  </c:forEach>
  </table>
<!--   税务信息 -->
<div class="ge01"></div>
  <div class="searchCondition"><span class="searchCondition_header">税务信息</span></div>
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="readOnlyTable">
  <tr>
    <th width="25%">税务认证时间</th>
    <td>${itc[0].column12 }</td>
    </tr>
  <tr>
    <th>备注</th>
    <td>${itc[0].column21 }</td>
    </tr>
  </table>
  <div class="ge01"></div>
  
<!--   合同信息 -->
<div class="ge01"></div>
  <div class="searchCondition"><span class="searchCondition_header">合同信息</span></div>
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
  <tr>
    <th>序号</th>
    <th>报账单号</th>
    <th>摘要</th>
    <th>发票代码</th>
    <th>发票金额(不含税)</th>
    <th>增值税发票号码</th>
    <th>抵和凭证类型</th>
    <th>税率</th>
  </tr>
  <c:forEach items="${itc}" var="item" varStatus="i">
	  <tr>
	    <td>${item.column01 }</td>
	    <td>${item.column02 }</td>
	    <td>${item.column21 }</td>
	    <td>${item.column03 }</td>
	    <td>${item.column09 }</td>
	    <td>${item.column04 }</td>
	    <td>${item.column13 }</td>
	    <td>${item.column11 * 100 }%</td>
	  </tr>
  </c:forEach>
  </table>
  <div class="ge01"></div>
  
<!--   流程跟踪 -->
 <div class="ge01"></div>
 <div class="searchCondition"><span class="searchCondition_header">流程跟踪</span></div>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
  <tr>
    <th>报账单号</th>
    <th colspan="2">提交电子报账单</th>
    <th colspan="2">室经理审批</th>
    <th colspan="2">部门领导审批</th>
    <th colspan="2">初审会计审批</th>
    <th colspan="2">税务会计审批</th>
    <th colspan="2">主办会计审批</th>
    <th colspan="2">财务经理审批</th>
    <th colspan="2">南基领导审批</th>
    <th colspan="2">省财务审批</th>
    <th colspan="2">出纳付款</th>
    </tr>
    <c:if test="${not empty flowinfo }">
    <tr>
    <td class="td01">${flowinfo[0].orderId }</td>
    <td class="td01">${flowinfo[0].submiter }</td>
    <td class="td01">${flowinfo[0].submiterDate }</td>
    <td class="td01">${flowinfo[0].sjlApprover }</td>
    <td class="td01">${flowinfo[0].sjlApproveDate }</td>
    <td class="td01">${flowinfo[0].bmldApprover }</td>
    <td class="td01">${flowinfo[0].bmldApproveDate }</td>
    <td class="td01">${flowinfo[0].cskjApprover }</td>
    <td class="td01">${flowinfo[0].cskjApproveDate }</td>
    <td class="td01">${flowinfo[0].swkjApprover }</td>
    <td class="td01">${flowinfo[0].swkjApproveDate }</td>
    <td class="td01">${flowinfo[0].zbkjApprover }</td>
    <td class="td01">${flowinfo[0].zbkjApproveDate }</td>
    <td class="td01">${flowinfo[0].cwjlApprover }</td>
    <td class="td01">${flowinfo[0].cwjlApproveDate }</td>
    <td class="td01">${flowinfo[0].njldApprover }</td>
    <td class="td01">${flowinfo[0].njldApproveDate }</td>
    <td class="td01">${flowinfo[0].scwApprover }</td>
    <td class="td01">${flowinfo[0].scwApproveDate }</td>
    <td class="td01">${flowinfo[0].cnfkApprover }</td>
    <td class="td01">${flowinfo[0].cnfkApproveDate }</td>
    </tr>
    </c:if>
  </table>
   <div class="ge01"></div>
 <div class="searchCondition" style="border-bottom:1px solid #d4e4ef;"><span class="searchCondition_header">线上流程图</span></div>
  <div class="gd_bar2">
          <ul>
          <jsp:include page="include/accountFlow.jsp"></jsp:include> 
           </ul>
 </div>
 <div style="margin:30px auto;width:110px;"><input name="" type="button" onclick="back()" class="btn_common02" value="返回列表" /></div>
</div>
<script src="/SRMC/dc/account/js/jquery.js"></script>
<script src="/SRMC/dc/account/js/ui-select.js"></script>
<!-- 	表单异步提交 -->
		<script src="/SRMC/rmpb/js/jquery.form.js"></script>
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<!-- 页面弹窗 -->
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"/>	
<script type="text/javascript">
	var orderId = "${info.orderId }";
	
	function back(){
		//window.location=document.referrer;
		var url = "<%=basePath%>account/${source}?type=${sourceType}";
		window.location = url;
	}
	
	var bcheck = true;
	function blurCheckId(){
		if(bcheck){
			bcheck = false;
			if(!checkId()){
				alert("该报账单号已存在，请重新输入！");
				return ;
			}
		}
	}
	
	function changeCheckId(){
		bcheck = true;
	}
	
	//检测报账ID是否重复
	function checkId(){
		var flag = true;
		jQuery.ajax({
			async:false,
			url: "<%=basePath%>account/ajaxCheck",
			dataType: "json",
			data: {
				accoutId: jQuery.trim(jQuery("#sel_01").val())
			},
			type: "POST",
			success: function(data) {
				if(data=="1"){
					//原工单号不属于重复
					if(orderId == jQuery.trim(jQuery("#sel_01").val())){
						flag = true;
					}else{
						flag = false;
					}
				}else{
					flag = true;
				}
			}
		});
		return flag;
	}
	/**
	* 联动改变初审会计
	*/
	function setAccount(){
		//用户id
		var tempUserIds = jQuery("#selected_trialAccount").children("a");
		//用户名
		var tempUserNames = jQuery("#selected_trialAccount").find("span");
		var r = "",t = "";
		for ( var i = 0; i < tempUserIds.length; i++) {
			if(i == 0){
				r += $(tempUserIds[i]).prop("id");
			}else{
				r += ","+$(tempUserIds[i]).prop("id");
			}
		}
		for ( var i = 0; i < tempUserNames.length; i++) {
			if(i == 0){
				t += $(tempUserNames[i]).html();
			}else{
				t += ","+$(tempUserNames[i]).html();
			}
		}
		$("#trialAccount").val(t);
		$("#trialAccountId").val(r);
	}
	/**
	* 初审会计联想查询
	*/
	function autoCompletesAccount(target){
		jQuery(target).autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>rulesController/searchUser",
					dataType: "json",
					data: {
						userValue: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
				     			 value:item[0].userName+" -- "+item[1].orgName,
									userName:item[0].userName,
									userId:item[0].userId,
									account:item[0].account,
									orgName:item[1].orgName
								};
							}));
						}else{
							return false;
						}
					}
				});
			},
			minLength: 1,
			select: function( event, ui ) {
				
				if(jQuery("#selected_trialAccount").children("[id='" + ui.item.userId + "']").length != 0){
					jQuery(target).val("");
					return;
				}else{
					jQuery("#selected_trialAccount").append('<a href="javascript:void(0);" class="" id="' + ui.item.userId + '">\
																		<span style="padding: 0 3px 0 0;">' + ui.item.userName + '</span>\
																		<img onclick="jQuery(this).parent().remove();setAccount();" src="/SRMC/dc/images/close_icon01.png" width="10" height="10" />\
																	  </a>');
					jQuery(target).val("");
					setAccount();
					return false;
				}
				return false;
			}
		});
	}
	function autoCompletes(){
		jQuery("#sel_02").autocomplete({
				source: function( request, response ) {
					jQuery.ajax({
						url: "<%=basePath%>rulesController/searchUser",
						dataType: "json",
						data: {
							userValue: request.term
						},
						type: "POST",
						success: function(data) {
							if(data!=null){
					     		response(jQuery.map(data, function( item ) {
					     			return {
					     			 value:item[0].userName+" -- "+item[1].orgName,
										userName:item[0].userName,
										userId:item[0].userId,
										account:item[0].account,
										orgName:item[1].orgName
									};
								}));
							}else{
								return false;
							}
						}
					});
				},
				minLength: 1,
				select: function( event, ui ) {
					jQuery("#sel_02").val(ui.item.userName);
					jQuery("#sementPeopleId_input").val(ui.item.userId);
					
					$.ajax({
						url: "<%=basePath%>account/queryDepartmentById?userid="+ui.item.userId,
						async:false,
						type: "POST",
						success: function(data) {
							var result = data.split(",");
							$("#department").val(result[1]);
							$("#department1").val(result[1]);
							$("#departmentId").val(result[2]);
							
							var dep = $("#departmentId").val();
							var costTypeVlue = j("#cosIdTemp").combotree("getValue");
							if(dep.trim()!="" && costTypeVlue.trim()!=""){
								getTrial(costTypeVlue,dep);
							}
						},
						error:function (){
							alert("网络异常，请联系管理员！");
						}
						
					 });
					return false;
				}
			});
		if(jQuery("#sel_02").val()!=null && jQuery("#sel_02").val()!=""){
		}
		
		jQuery("#sel_03").autocomplete({
				source: function( request, response ) {
					jQuery.ajax({
						url: "<%=basePath%>rulesController/searchUser",
						dataType: "json",
						data: {
							userValue: request.term
						},
						type: "POST",
						success: function(data) {
							if(data!=null){
					     		response(jQuery.map(data, function( item ) {
					     			return {
					     			 value:item[0].userName+" -- "+item[1].orgName,
										userName:item[0].userName,
										userId:item[0].userId,
										account:item[0].account,
										orgName:item[1].orgName
									};
								}));
							}else{
								return false;
							}
						}
					});
				},
				minLength: 1,
				select: function( event, ui ) {
					jQuery("#sel_03").val(ui.item.userName);
					jQuery("#presenterId_input").val(ui.item.userId);
					return false;
				}
			});
		if(jQuery("#sel_03").val()!=null && jQuery("#sel_03").val()!=""){
		}
		
		jQuery("#department1").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>account/searchOrganization",
					dataType: "json",
					data: {
						orgValue: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			console.info(item);
				     			return {
				     			 	value:item.orgName,
									orgId:item.orgId,
									orgName:item.orgName
								};
							}));
						}else{
							return false;
						}
					}
				});
			},
			minLength: 1,
			select: function( event, ui ) {
				jQuery("#department").val(ui.item.orgName);
				jQuery("#department1").val(ui.item.orgName);
				jQuery("#departmentId").val(ui.item.orgId);
				return false;
			}
		});
	}
	
	//获取初审会计
	function getTrial(costTypeId,depId){
		$.ajax({
			type:"post",
			dataType:"json",
			async:false,
			url:"<%=basePath%>account/queryAccountById",
			data:{ costTypeId:costTypeId,depId:depId },
			success:function(data){
				if(data.length>0){
					var r = "";
					var t = "";
					for(var i=0;i<data.length;i++){
						if(i+1==data.length){
							r = r+data[i].accounting;
							t = t+data[i].accountingId;
						}else{
							r = r+data[i].accounting+",";
							t = t+data[i].accountingId+",";
						}
						
						$("#trialAccount").val(r);
						$("#trialAccount1").val(r);
						$("#trialAccountId").val(t);
					}
				}else{
					$("#trialAccount").val("");
					$("#trialAccount1").val("");
					$("#trialAccountId").val("");
				}
			},
			error:function(){
				alert("获取会计失败，请检查网络");
			}
		});//ajax
		
	}
	
	function clean(){
		if (event.keyCode == 13) {
        } 
	}
	
	function doDraft(){
		var text = j("#cosIdTemp").combotree("getText");
		jQuery("#costType").val(text);
		
		if(jQuery.trim(jQuery("#sel_01").val()) == ""||jQuery.trim(jQuery("#sel_01").val()) == "1"){
			alert("请输入报账单号！");
			return;
		}
		if(jQuery.trim(jQuery("#sel_01").val()).length>20 || jQuery.trim(jQuery("#sel_01").val()).length<14){
			alert("报账单号长度有误，应为14~20位");
			return;
		}
		if(!checkId()){
			alert("该报账单号已存在，请重新输入！");
			return ;
		}
		
		if(jQuery.trim(text) == "0"||jQuery.trim(text) == ""){
			alert("请选择费用类型");
			return;
		}
		if(jQuery.trim(jQuery("#sementPeopleId_input").val()) == ""){
			alert("请输入报账人");
			return;
		}
		if(jQuery.trim(jQuery("#presenterId_input").val())==""){
			alert("请输入交单人");
			return;
		}
		if(jQuery.trim(jQuery("#pageSubmitDate").val()) == ""){
			alert("请输入纸质提交财务时间");
			return;
		}
		
		var includeBuckle = jQuery("input[name='includeBuckle']:checked").val();
		if(includeBuckle!="1"&&includeBuckle!="0"){
			alert("请选择是否包含抵扣");
			return;
		}
		if(jQuery.trim(jQuery("#reachSement").val()) == ""){
			alert("请输入发票时间");
			return;
		}
		var deduction = jQuery("input[name='deduction']:checked").val();
		if(deduction!="1"&&deduction!="0"){
			alert("请选择是否专票抵扣");
			return;
		}
		var cost = jQuery.trim(jQuery("#cost").val());
		if(cost == ""){
			alert("请输入金额");
			return ;
		}else{
			if(isNaN(cost)){
				alert("金额不能为非数字");
				return ;
			}
			if(jQuery.trim(jQuery("#cost").val()).length > 20){
				alert("金额不能超过20位");
				return;
			}
		}
		
		if(jQuery.trim(jQuery("#accountAbstract").val()).length>125){
			alert("摘要长度有误，应不大于125字");
			return;
		}
		
		$("#submitForm").ajaxSubmit({
			url: "<%=basePath%>account/updateTrial",
			type: "POST",
			success: function(data) {
				if(data=="1"){
					alert("修改成功！");
					window.location.href="<%=basePath%>account/trailDetail?source=${source}&sourceType=${sourceType}&orderId="+jQuery.trim(jQuery("#sel_01").val())+"&type=2&id="+jQuery.trim(jQuery("#id").val());
					document.getElementById("sel_01").focus();
					$('#submitForm')[0].reset();
				}
			},
			error:function (){
				alert("网络异常，请联系管理员！");
			}
			
		});
	}
	
	$(function (){
		j("#cosIdTemp").combotree({
		onBeforeSelect: function(node) {
			if (!j(this).tree('isLeaf', node.target)) {
				return false;
			}
		},
		onClick: function(node) {
			if (!j(this).tree('isLeaf', node.target)) {
				j('#cosIdTemp').combo('showPanel');
			}
			
			var costext = j("#cosIdTemp").combotree("getText");
			jQuery("#costType").val(costext);	
			jQuery("#cosId").val(j("#cosIdTemp").combotree("getValue"));
		},
		
   	    url:'<%=basePath%>/account/getCostType' ,
   		width:170,
   		lines:'true',
		checkbox:true ,
		cascadeCheck:false,
		required:false,
		editable:false,
		multiple:false//设置是否多选	
		
	       }); 
	});
	
	$(function(){
		//初始化弹窗
		$("a[name='n_invoice']").each(function(){
			var idStr = this.id;
			var arr = idStr.split('-');
			addInvoice(idStr,arr[0],arr[1]);
		});
		
		var userIds = $("#trialAccountId").val().split(",");
		var userNames = $("#trialAccount").val().split(",");
		$.each(userIds,function(k,v){
			if(jQuery("#selected_trialAccount").children("[id='" + v + "']").length == 0){
				jQuery("#selected_trialAccount").append('<a href="javascript:void(0);" class="" id="' + v + '">\
																	<span style="padding: 0 3px 0 0;">' + userNames[k] + '</span>\
																	<img onclick="jQuery(this).parent().remove();setAccount();" src="/SRMC/dc/images/close_icon01.png" width="10" height="10" />\
																  </a>');
			}
		});
		
		jQuery("#trialAccount1").val("");
	});
	
	//新增发票
	function addInvoice(nodeId,id,parentId){
		iframeDialog(nodeId, basePath+"account/addInvoice?id="+id+"&pId="+parentId);
	}
	
	//页面弹窗
	function iframeDialog(target,url) {
		jQuery('#'+target).fancybox({
			'href' : url,
//			'width' : '50%',
//			'height' : '50%',
			'autoScale' : true,
			'autoDimensions': true,
			'transitionIn' : 'none',
			'transitionOut' : 'none',
			'type' : 'iframe',
			'scrolling' : 'yes',
			'centerOnScroll' : true,
			'onStart' : function(current, previous) {
			}
		});
	}

	//关闭弹窗
	function closeIframeDialog(){
		jQuery.fancybox.close();
	}
	
	function ev_delete(id){
		if(confirm("确定删除所选发票么？")){
			ajaxDel(id);
		}
	}
	
	function ajaxDel(id){
		jQuery.ajax({
	        type:"POST",
	        async:false,
	        url:"<%=basePath%>account/delInvoice",
	        data:"id=" + id,
	        success:function(data){
	        	if(data == 1){
	        		alert("删除成功！");
	        		window.location.reload();
	        	}else{
	        		alert("删除失败！");
	        	}
	        },
	        error:function(){
	            alert("删除失败！");
	        }
	    });
	}
	
	//批量删除
	function batchDel(){
		var $subBoxChecks = $("input[name='subBox']:checked");
		if($subBoxChecks.length == 0){
			alert("请至少选中一张发票");
			return;
		}
		if(confirm("确定批量删除"+$subBoxChecks.length+"张发票么？")){
			var rulesIds = '';
			$.each($subBoxChecks,function(k,v){
				if(k == 0){
					rulesIds += $(v).val();
				}else{
					rulesIds += ","+$(v).val();
				}
			});
			ajaxDel(rulesIds);
		}
	}
	
	function ev_checked(){
		var checkAll = document.getElementById("checkAll");
		if(checkAll != null && checkAll.type=="checkbox"){
			var ids = document.getElementsByName("subBox");
			 if(ids != null){
				for(var i=0;i<ids.length;i++){
					var id = ids[i];
					if(checkAll.checked){
						id.checked=true;
					}else{
						id.checked=false;
					}
				}	 
			 }
		}
	}
</script>
</body>
</html>