<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

 <div class="contract_wraper">
	<div class="contract" id="contract" style="height:auto;">
		<div class="contract_head">
			<h1 class="contract_title">C类工作指令</h1>
			<div class="contract_title_content">
				<div class="contract_route"><i>至：</i>
					${vo.supportorgName }监理
				</div>
				<div class="contract_zcode">
					<img src="/SRMC/command/images/${cdinfo.commandNum }.jpg"/>
				</div>
				<span class="contract_nomber">编号：<i>${cdinfo.commandNum }</i></span>
			</div>
		</div>
		<table class="contract_table print02" >
			<tr>
				<td style="    font-weight: bold;text-align: left;" class="contract_item">合同名称：${vo.contractName }</td>
				<td style="    font-weight: bold;text-align: left;" class="contract_item">合同编号：${vo.contractCode }</td>
			</tr>
			<tr>
				<td style="    font-weight: bold;text-align: left;border-right-style:none;border-bottom-style: none;border-left-style: none;border-top-style: none;" class=" contract_item">施工单位：${vo.constructionName }</td>
				<td style="    font-weight: bold;text-align: left;border-right-style:none;border-bottom-style: none;border-left-style: none;border-top-style: none;" class="contract_item">指令编号：${cdinfo.commandNum }</td>
			</tr>
		</table>
		<table class="contract_table">
			<!-- 隐藏行，作用只是设定宽度 -->
		<!-- 
			<tr style="height:0;"> 
				<th style="width:28%;"></th> 
				<th style="width:24%;"></th> 
				<th style="width:14.6%;"></th> 
				<th style="width:34.4%;"></th>
			</tr>
		 -->
            <tr class="noBorder">
             <td class="noBorderR">
                 <span class="contract_txt2 txt3">
							<i>工作指令内容：</i>
						</span>
             </td>
			 <td colspan="3">&nbsp;</td>
            </tr>
            <tr class="noBorder">
			 <td colspan="4" ><c:if test="${empty vo.worksContent }">&nbsp;</c:if><c:if test="${not empty vo.worksContent }">${vo.worksContent }</c:if></td>
            </tr>
		   <tr class="noBorder">
				<td colspan="4">
					<div class="contract_area_wrap">
						<textarea class="contract_area" style="height:50px;overflow: hidden;"></textarea>
					</div>
				</td>
			</tr>
            <tr class=" noBorder">
             <td class="noBorderR">
                 <div class="contract_area_wrap">
                 <span class="contract_txt2 txt3">
							<i>工作指令内容开始日期：</i>
							<input type="text" />
						</span>
                 </div>
             </td>
			 <td colspan="3">&nbsp;</td>
            </tr>
            <tr class="noBorder">
             <td class="noBorderR">
                 <div class="contract_area_wrap">
                 <span class="contract_txt2 txt3">
							<i>工作指令内容完成日期：</i>
							<input type="text" />
						</span>
                 </div>
             </td>
			 <td colspan="3">&nbsp;</td>
            </tr>
            <tr>
	            <td colspan="" class="noBorderR">&nbsp;</td>
	            <td colspan="" class="noBorderR">&nbsp;</td>
             <td colspan="2">
                 <div class="contract_area_wrap">
				 <span class="contract_txt2">
							<i>专业执行经理：</i>
							<input type="text" />
						</span>
				 <span class="contract_txt2">
							<i>日期：</i>
							<input type="text" class="contract_time" />
						</span>
                 </div>
             </td>
            </tr>
            <tr class="noBorder">
				<td colspan="4">总控/项目主管审核意见：</td>
			</tr>
		   <tr>
				<td colspan="4">
					<div class="contract_area_wrap">
						<textarea class="contract_area" style="overflow: hidden;"></textarea>
						<span class="contract_txt2">
							<i>签名：</i>
							<input type="text" />
						</span>
						<span class="contract_txt2">
							<i>日期：</i>
							<input type="text" class="contract_time" />
						</span>
					</div>
				</td>
			</tr>
		</table>
		<div class="contract_remark">
			<i>备注</i>
			<p>1、A1类工作指令：由业主、设计单位提出的涉及工程变更的工作指令。<br/>
				2、A2类工作指令：由施工单位提出的涉及工程变更的工作指令（必须附上“设计变更洽商申请表”）。<br/>
				3、B类工作指令：与造价有关的临时工程和新增（减）工程的工作指令。<br/>
				4、C类工作指令：与造价无关的工作指令。<br/>
				5、变更资料附件为：施工单位发起的工程变更单或设计院出具的设计变更通知单。</p>
		</div>
	</div>
</div>