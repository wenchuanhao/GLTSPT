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
			<h1 class="contract_title">B类工作指令 </h1>
			<div class="contract_title_content">
				<div class="contract_route"><i>至：</i>
					${vo.supportorgName}监理
				</div>
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
				<th style="width:8%;"></th>
				<th style="width:10%;"></th>
				<th style="width:4%; border-right:1px solid #999;"></th> 
				<th style="width:30%;"></th> 
				<th style="width:14.6%;"></th> 
				<th style="width:34.4%;"></th>
			</tr>
			 -->
			<tr>
				<td class="contract_item" colspan="3" style="text-align: left;">指令涉及合同名称</td>
				<td colspan="9">${vo.contractName }</td>
			</tr>
			<tr>
				<td class="contract_item" colspan="3" style="text-align: left;">指令涉及合同编号</td>
				<td colspan="9">${vo.contractCode }</td>
			</tr>
			<tr>
				<td class="contract_item" colspan="3" style="text-align: left;">施工单位</td>
				<td colspan="9">${vo.constructionName }</td>
			</tr>
			<tr class="noBorder">
				<td class="noBorderR" colspan="2">工作指令信息:</td>
				<td colspan="10"><div class="contract_txt"></div></td>
			</tr>
			<tr class="noBorder">
				<td class="noBorderR">部位:</td>
				<td colspan="11"><div class="contract_txt"></div><c:if test="${empty vo.orgName }">&nbsp;</c:if><c:if test="${not empty vo.orgName }">${vo.orgName }</c:if></td>
			</tr>
			<tr class="noBorder">
				<td class="noBorderR">内容:</td>
				<!-- textarea内容前有空格或者换行符 会导致内容缩进-->
				<td colspan="11"><div class="contract_txt"></div>${vo.worksContent}</td>
			</tr>
			<tr class="noBorder">
				<td class="noBorderR">原因:</td>
				<td colspan="11"><div class="contract_txt"></div>${vo.reason}</td>
			</tr>
			<tr>
				<td colspan="12">
					<div class="contract_area_wrap">
						<textarea class="contract_area" style="overflow: hidden;"></textarea>
						<div>
						<span class="contract_txt2">
							<i>专业工程师：</i>
							<input type="text" />
						</span>
						<span class="contract_txt2">
							<i>日期：</i>
							<input type="text" class="contract_time" />
						</span>
						</div>
						<div>
						<span class="contract_txt2">
							<i>专业主管：</i>
							<input type="text" />
						</span>
						<span class="contract_txt2">
							<i>日期：</i>
							<input type="text" class="contract_time" />
						</span>
						</div>
					</div>
				</td>
			</tr>
			<tr class="noBorder">
				<td colspan="12"><div class="pad-btm40">计价原则：</div></td>
				
		  </tr>
          
			<tr class="noBorder">
				<td class="contract_item noBorderR" colspan="3" style="text-align: left;width:2%;">变化造价估算:</td>
				<td colspan="9" style="text-align: left;">
					<div class="contract_chex">
						<span>
	        				<input type="checkbox" name="chex1" id="chex1"/> 
							<label class="chex" for="chex1">5万元以下</label>
						</span>
						<span>
	        				<input type="checkbox" name="chex2" id="chex2"/> 
							<label class="chex" for="chex2">5-10万元</label>
						</span>
						<span>
	        				<input type="checkbox" name="chex3" id="chex3"/> 
							<label class="chex" for="chex3">10-15万元区间</label>
						</span>
						<span>
	        				<input type="checkbox" name="chex4" id="chex4" /> 
							<label class="chex" for="chex4">50万元以上</label>
						</span>
					</div>
				</td>
			</tr>
            <tr>
				<td colspan="12">
					<div class="contract_area_wrap" style="text-align: left;">
						<div>
						<span class="contract_txt2">
							<i>造价工程师：</i>
							<input type="text" class="contract_time"/>
						</span>
						<span class="contract_txt2">
							<i>日期：</i>
							<input type="text" class="contract_time" />
						</span>
						</div>
						<textarea class="contract_area" style="overflow: hidden;"></textarea>
						<div>
						<span class="contract_txt2">
							<i>造价组签名：</i>
							<input type="text" class="contract_time"/>
						</span>
						<span class="contract_txt2">
							<i>日期：</i>
							<input type="text" class="contract_time" />
						</span>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="6" style="border-right:1px solid #999; vertical-align:top;">
                <div class="pad-btm40">总控审核意见:</div>
                 <div class="contract_chex"s style="height:36px; vertical-align:middle; line-height:36px;"></div>
					<div class="contract_area_wrap"  style="text-align: left;">
						
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
				
				<td colspan="6" style=" vertical-align:top;">
					<div class="pad-btm40">项目主管审核意见:</div>
                       	<div>审批权限:</div>
                       	<div>
						<span>
	        				<input type="checkbox" name="chex5" id="chex5" /> 
							<label class="chex" for="chex5">在本文件完成审批</label>
						</span>
						<span>
	        				<input type="checkbox" name="chex6" id="chex6"/> 
							<label class="chex" for="chex6">完成公司/部门呈批</label>
						</span>
						</div>
					</div>
					<div class="contract_area_wrap" style="text-align: left;">
						
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
			 <tr>
				<td class="contract_item  noBorderR" colspan="4" style="text-align: left;">室经理审核意见:</td>
				<td  class="noBorderR" colspan="2">&nbsp;</td>
				<td colspan="6">
					<div class="contract_area_wrap" style="text-align: left;">
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