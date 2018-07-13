<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>
<!-- 下划线样式 -->
<style> 
/* 内容为空时 */
.divcss5-x5-null{border-bottom:1px solid #000;display: inline-block; } 
/* 内容不为空，保留换行下划线效果 */
.divcss5-x5{ padding-bottom:5px; border-bottom:1px solid #000; } 
</style> 

<div class="contract_wraper">
	<div class="contract" id="contract" style="height:auto;">
		<div class="contract_head">
			<h1 class="contract_title">非采购订单-工程建设类请款审批表<i>（施工类）</i></h1>
			<div class="contract_title_content">
				
				<div class="contract_zcode">
					<img src="/SRMC/command/images/${cdinfo.commandNum }.jpg"/>
				</div>
				<span class="contract_nomber">编号：<i>${cdinfo.commandNum}</i></span>
			</div>
		</div>
		<table class="contract_table">
			<!-- 隐藏行，作用只是设定宽度 -->
		<!-- 
			<tr style="height:0;"> 
				<th style="width:22%; border-right:1px solid #999;"></th> 
				<th style="width:30%; border-right:1px solid #999;"></th> 
				<th style="width:14.6%; border-right:1px solid #999;"></th> 
				<th style="width:34.4%;"></th>
			</tr>
		 -->
			<tr>
				<td class="contract_item">合同名称</td>
				<td>${vo.contractName }</td>
                <td class="contract_item">合同编号</td>
				<td>${vo.contractCode }</td>
			</tr>
            <tr>
				<td class="contract_item">框架合同名称<br/>（非框架合同此行不填）</td>
				<td><c:if test="${empty vo.frameworkName }">&nbsp;</c:if><c:if test="${not empty vo.frameworkName }">${vo.frameworkName }</c:if></td>
                <td class="contract_item">框架合同编号<br/>（非框架合同此行不填）</td>
				<td><c:if test="${empty vo.frameworkCode }">&nbsp;</c:if><c:if test="${not empty vo.frameworkCode }">${vo.frameworkCode }</c:if></td>
			</tr>
            <tr>
				<td class="contract_item">投资项目名称</td>
				<td>${vo.projectName }</td>
                <td class="contract_item">投资项目编号</td>
				<td>${vo.projectCode }</td>
			</tr>
			<tr>
				<td class="contract_item">乙方单位</td>
				<td>${vo.constructionName }</td>
                <td class="contract_item">合同金额</td>
				<td>¥ <fmt:formatNumber value="${vo.contractAmount}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></td>
			</tr>
            <tr>
				<td class="contract_item">请款类型</td>
				<td> 
                <div class="contract_chex">
						<span>
	        				<input <c:if test="${vo.qkType == 1 }">checked="checked"</c:if> type="checkbox" name="chex1" id="chex1"/> 
							<label class="chex" for="chex1">预付款 </label>
						</span>
                        <span>
	        				<input <c:if test="${vo.qkType == 2 }">checked="checked"</c:if> type="checkbox" name="chex1" id="chex1"/> 
							<label class="chex" for="chex1">进度款 </label>
						</span>
                        <br/>
                         <span>
	        				<input <c:if test="${vo.qkType == 3 }">checked="checked"</c:if> type="checkbox" name="chex1" id="chex1"/>  
							<label class="chex" for="chex1">结算款 </label>
						</span>
						<span>
	        				<input <c:if test="${vo.qkType == 4 }">checked="checked"</c:if> type="checkbox" name="chex1" id="chex1"/>  
							<label class="chex" for="chex2">其他<i class="borderBtm" style="width:70px;"><c:if test="${empty vo.qkTypedec }">&nbsp;</c:if><c:if test="${not empty vo.qkTypedec }">${vo.qkTypedec }</c:if></i></label>
						</span>
						
						
				</div>
                </td>
                <td class="contract_item">对应合同付款条款</td>
				<td>${vo.paymentTerms}</td>
			</tr>
            <tr>
				<td class="contract_item">乙方申请金额</td>
				<td colspan="3">¥ <fmt:formatNumber value="${vo.applicationAmount}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></td>
               
			</tr>
            <tr class="noBorder">
				<td colspan="4">
                 <div class="word_5">乙方单位申请事由：
                	<p>于<i class="borderBtm" style="width:60px;"><c:if test="${empty vo.beginYear }">&nbsp;</c:if><c:if test="${not empty vo.beginYear }">${vo.beginYear }</c:if></i>
                	年
                	<i class="borderBtm" style="width:40px;"><c:if test="${empty vo.beginMonth }">&nbsp;</c:if><c:if test="${not empty vo.beginMonth }">${vo.beginMonth }</c:if></i>
                	月期间，我方已完成工程款额
                	<i class="borderBtm" style="min-width:100px;"><c:if test="${empty vo.engineerAmount }">&nbsp;</c:if><c:if test="${not empty vo.engineerAmount }"><fmt:formatNumber value="${vo.engineerAmount }" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></c:if></i>
                	元，根据合同条款
                	<i class="borderBtm" style="min-width:40px;"><c:if test="${empty vo.termsNum }">&nbsp;</c:if><c:if test="${not empty vo.termsNum }">${vo.termsNum }</c:if></i>
                	条：
                	<i class="<c:if test='${empty vo.termsContent }'>divcss5-x5-null</c:if><c:if test='${not empty vo.termsContent }'>divcss5-x5</c:if>" style="min-width:80px;"><c:if test="${empty vo.termsContent }">&nbsp;</c:if><c:if test="${not empty vo.termsContent }">${vo.termsContent }</c:if></i>。
                	本期应扣除金额为
                	<i class="borderBtm" style="min-width:100px;"><c:if test="${empty vo.deductAmount }">&nbsp;</c:if><c:if test="${not empty vo.deductAmount }"><fmt:formatNumber value="${vo.deductAmount }" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></c:if></i>
                	元，我方本次申请支付金额为
                	<i class="borderBtm" style="min-width:100px;"><c:if test="${empty vo.payAmount }">&nbsp;</c:if><c:if test="${not empty vo.payAmount }"><fmt:formatNumber value="${vo.payAmount }" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></c:if></i>
                	元。计算过程：${vo.calPro}
                </p>
                </div>
                </td>
			</tr>
            <tr>
            	<td  class="noBorderR">乙方经办人签章：</td>
                <td class="noBorderR">&nbsp;</td>
                <td colspan="2"><span class="width100">申请日期：</span><input name="" type="text" class="w-text01">年<input name="" type="text" class="w-text02">月<input name="" type="text" class="w-text02">日<input name="" type="text" class="w-text02">时</td> 
            </tr>
		   
		   <tr class="noBorder">
				<td colspan="4" class="contract_item" style="text-align:left;height:50px;">
                <div class="pad-btm40">项目总监理工程师意见(审批时限: 3个工作日)：</div>
                </td>
			</tr>
           <tr >
            	<td colspan="2" class="noBorderR">接收时间：<input name="" type="text" class="w-text01">年<input name="" type="text" class="w-text02">月<input name="" type="text" class="w-text02">日<input name="" type="text" class="w-text02">时
                <span class="pl20" class="noBorderR">签名：</span><input name="" type="text" class="w-text01"></td>
                <td colspan="2"><span class="width100">日期：</span><input name="" type="text" class="w-text01">年<input name="" type="text" class="w-text02">月<input name="" type="text" class="w-text02">日<input name="" type="text" class="w-text02">时</td> 
            </tr>
           <tr class="noBorder">
				<td colspan="4" class="contract_item" style="text-align:left;height:50px;">
                <div>造价公司意见(审批时限: 500万以下3个工作日, 500万以上5个工作日)：</div>
                	<p>经复核，乙方于<i class="borderBtm" style="width:100px;">&nbsp;</i>年<i class="borderBtm" style="width:40px;">&nbsp;</i>月期间：</p>
                <p>一、已完成工程款额<i class="borderBtm" style="width:100px;color:black;">&nbsp;</i>元；</p>
                <p>二、根据合同条款<b class="borderBtm" style="width:100px;">&nbsp;</b>条：<b class="borderBtm" style="width:400px;">&nbsp;</b>。</p>
                <p class="indent">本期应扣金额为<i class="borderBtm" style="width:100px;color:black; ">&nbsp;</i>元，本次应支付金额<i class="borderBtm" style="width:100px;color:black;">&nbsp;</i>元；计算过程：</p>
                <p class="indent">附件：支付证书；</p>
                </td>
			</tr>
            <tr  >
            	<td colspan="2" class="noBorderR ">接收时间：<input name="" type="text" class="w-text01">年<input name="" type="text" class="w-text02">月<input name="" type="text" class="w-text02">日<input name="" type="text" class="w-text02">时
                <span class="pl20 noBorderR">签名：</span><input name="" type="text" class="w-text01"></td>
                <td colspan="2"><span class="width100">日期：</span><input name="" type="text" class="w-text01">年<input name="" type="text" class="w-text02">月<input name="" type="text" class="w-text02">日<input name="" type="text" class="w-text02">时</td> 
            </tr>
            <tr class="noBorderR ">
				<td colspan="4" class="contract_item noBorder" style="text-align:left;height:50px;">
                <div class="pad-btm10">专业工程师意见(审批时限:1个工作日)：</div>
                <div class="contract_chex">
						<span>
	        				<input type="checkbox" name="chex1" id="chex1"/> 
							<label class="chex" for="chex1">同意按造价公司审核金额支付。</label>
						</span>
						<span>
	        				<input type="checkbox" name="chex2" id="chex2"/> 
							<label class="chex" for="chex2">不同意，退回，整改意见：<i class="borderBtm" style="width:250px;">&nbsp;</i>。</label>
						</span>
					</div>
                </td>
			</tr>
            <tr>
            	<td colspan="2" class="noBorderR ">接收时间：<input name="" type="text" class="w-text01">年<input name="" type="text" class="w-text02">月<input name="" type="text" class="w-text02">日<input name="" type="text" class="w-text02">时
                <span class="pl20" class="noBorderR ">签名：</span><input name="" type="text" class="w-text01"></td>
                <td colspan="2"><span class="width100">日期：</span><input name="" type="text" class="w-text01">年<input name="" type="text" class="w-text02">月<input name="" type="text" class="w-text02">日<input name="" type="text" class="w-text02">时</td> 
            </tr>
            <tr class="noBorder">
				<td colspan="4" class="contract_item " style="text-align:left;height:50px;">
                <div class="pad-btm10">项目负责人意见(审批时限:1个工作日)：</div>
                <div class="contract_chex">
						<span>
	        				<input type="checkbox" name="chex1" id="chex1"/> 
							<label class="chex" for="chex1">同意按造价公司审核金额支付。</label>
						</span>
						<span>
	        				<input type="checkbox" name="chex2" id="chex2"/> 
							<label class="chex" for="chex2">不同意，退回，整改意见：<i class="borderBtm" style="width:250px;">&nbsp;</i>。</label>
						</span>
						
						
					</div>
                
                </td>
			</tr>
            <tr>
            	<td colspan="2" class="noBorderR ">接收时间：<input name="" type="text" class="w-text01">年<input name="" type="text" class="w-text02">月<input name="" type="text" class="w-text02">日<input name="" type="text" class="w-text02">时
                <span class="pl20" class="noBorderR ">签名：</span><input name="" type="text" class="w-text01"></td>
                <td colspan="2"><span class="width100">日期：</span><input name="" type="text" class="w-text01">年<input name="" type="text" class="w-text02">月<input name="" type="text" class="w-text02">日<input name="" type="text" class="w-text02">时</td> 
            </tr>
            <tr class=" noBorder">
				<td colspan="4" class="contract_item" style="text-align:left;height:50px;">
                <div class="pad-btm10">室经理意见(审批时限:2个工作日)：</div>
                <div class="contract_chex">
						<span>
	        				<input type="checkbox" name="chex1" id="chex1"/> 
							<label class="chex" for="chex1">同意按造价公司审核金额支付。</label>
						</span>
						<span>
	        				<input type="checkbox" name="chex2" id="chex2"/> 
							<label class="chex" for="chex2">不同意，退回，整改意见：<i class="borderBtm" style="width:250px;">&nbsp;</i>。</label>
						</span>
					</div>
                </td>
			</tr>
			<tr  class=" noBorder">
				<td colspan="2" class="noBorderR ">&nbsp;</td>
                <td colspan="2" style="text-align: center;"><span>（加盖筹建办公章）</span></td> 
			</tr>
            <tr>
            	<td colspan="2" class="noBorderR ">接收时间：<input name="" type="text" class="w-text01">年<input name="" type="text" class="w-text02">月<input name="" type="text" class="w-text02">日<input name="" type="text" class="w-text02">时
                <span class="pl20" class="noBorderR ">签名：</span><input name="" type="text" class="w-text01"></td>
                <td colspan="2"><span class="width100">日期：</span><input name="" type="text" class="w-text01">年<input name="" type="text" class="w-text02">月<input name="" type="text" class="w-text02">日<input name="" type="text" class="w-text02">时</td> 
            </tr>
		</table>
		<div class="contract_remark">
			<i>说明：</i>
			<p>1、请款审批表及申请资料附件须一式二份；2、造价公司出具支付证书一式四份、合同付款台账一式四份。<br/>
            </p>
		</div>
	</div>
</div>
	