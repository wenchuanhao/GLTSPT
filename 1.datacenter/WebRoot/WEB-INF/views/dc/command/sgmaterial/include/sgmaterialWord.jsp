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
			<h1 class="contract_title">中国移动南方基地筹建办工程收文处理表<i>（施工）</i></h1>
			<div class="contract_title_content">
				
				<div class="contract_zcode">
					<img src="/SRMC/command/images/${cdinfo.commandNum }.jpg"/>
				</div>
				<span class="contract_nomber">编号：<i>${cdinfo.commandNum }</i></span>
			</div>
		</div>
		<table class="contract_table">
			<!-- 隐藏行，作用只是设定宽度 -->
		<!-- 
			<tr style="height:0; "> 
				<th style="width:19%; border-right:1px solid #999;"></th> 
				<th style="width:32%;border-right:1px solid #999;"></th> 
				<th style="width:14.6%;border-right:1px solid #999;"></th> 
				<th style="width:14.2%;"></th>
				<th style="width:10.1%;"></th>
				<th style="width:10.1%;"></th>
			</tr>
		 -->	
			<tr>
				<td class="contract_item" style="text-align: center;">来文单位</td>
				<td>${vo.constructionName}</td>
                <td class="contract_item" style="text-align: center;">关联合同</td>
				<td colspan="3" >${vo.contractName}</td>
			</tr>
            <tr>
				<td class="contract_item" style="text-align: center;">文件类型</td>
				<td>
					<jsp:include page="../../../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="FILE_TYPE"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.fileType}"></jsp:param>
			    </jsp:include>
				</td>
                <td class="contract_item" style="text-align: center;">收文时间</td>
				<td><c:if test="${empty vo.receivedTime }">&nbsp;</c:if><c:if test="${not empty vo.receivedTime }"><fmt:formatDate value="${vo.receivedTime}" pattern='yyyy-MM-dd' /></c:if></td>
				<td class="contract_item" style="text-align: center;">原件份数</td>
				<td><c:if test="${empty vo.fileNum }">&nbsp;</c:if><c:if test="${not empty vo.fileNum }">${vo.fileNum }</c:if></td>
			</tr>
            <tr>
				<td colspan="6">
                <div class="word_5 pad-btm80">文件摘要：${vo.fileSummary}</div>
                </td>
			</tr>
             <tr>
				<td colspan="6">
                <div class="word_5 pad-btm80">专业工程师意见：</div>
                <div class="txt-r2">
                <span><input type="checkbox" name="chex1" id="chex1"/> <label class="chex" for="chex1">设计  </label></span>
                 <span><input type="checkbox" name="chex1" id="chex1"/> <label class="chex" for="chex1">造价 </label> </span>
                <span> <input type="checkbox" name="chex1" id="chex1"/> <label class="chex" for="chex1">总控</label> </span>
                <p>办理时限2天</p>  
                </div>
                </td>
			</tr>
             <tr>
				<td colspan="6">
                <div class="word_5 pad-btm80">设计/造价意见：</div>
                <div class="txt-r2"><p>办理时限1天</p>  </div>
                </td>
			</tr>
             <tr>
				<td colspan="6">
                <div class="word_5 pad-btm80">施工总控意见：</div>
                <div class="txt-r2"><p>办理时限1天</p>  </div>
                </td>
			</tr>
             <tr>
				<td colspan="6">
                <div class="word_5 pad-btm80">项目经理意见：</div>
                <div class="txt-r2"><p>办理时限1天</p>  </div>
                <div class="txt-r2"><input type="checkbox" name="chex1" id="chex1"/>  报室经理 </div>
                </td>
			</tr>
              <tr>
				<td colspan="6">
                <div class="word_5 pad-btm80">室经理意见：</div>
                <div class="txt-r2"><p>办理时限1天</p>  </div>
                </td>
			</tr>
		</table>
		<div class="contract_remark">
			<i>备注</i>
			<p> 1、主办、回复 √协办。<br/>
				2、工地传阅及处理。<br/>
                3、抄送档案室。<br/>
                
            </p>
		</div>
	</div>
</div>
<br/>
<jsp:include page="../../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="FILE_TYPE"></jsp:param>
</jsp:include>