<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>
<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
		<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
		<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />	
</div>

<form name="form" id="pageForm" method="post"  >
<input type="hidden" value="N" name="isPages" id="isPages"/>
<input type="hidden" value="${purchaseForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
   <input type="hidden" value="${purchaseForm.pageSize}" id="pageSize"	name="pageSize"/>
   	<input type="hidden" id="searchType" name="searchType" value="" />
   	
 	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">创建时间：</th>
	    <td width="30%">
	    	<div class="date l" >
	    	<input readonly="readonly" name="beginCreateTime" id="beginCreateTime" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value='${purchaseForm.beginCreateTime}' pattern="yyyy-MM-dd"/>"
	                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
		<th width="9%" align="right"></th>
	    <td width="30%">
	    	<div class="date l">
	    	<input readonly="readonly" name="endCreateTime" id="endCreateTime" type="text"  placeholder="请输入结束时间"  class="text02 l" 
	    					value="<fmt:formatDate value='${purchaseForm.endCreateTime}' pattern="yyyy-MM-dd"/>"
	                     	onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreateTime\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
	</tr>
	
	<tr>
		<th width="9%" align="right">需求部门：</th>
	    <td width="30%">
	    	<select class="ui-select" id="columnC" name="columnC" style="width:195px;">
				<option <c:if test="${empty purchaseForm.columnC}">selected="selected"</c:if> value="">请选择</option>
				<c:forEach items="${depList}" var="item" varStatus="i">
					<option ${purchaseForm.columnC eq item.orgName ? "selected=\"selected\"":null} value="${item.orgName}">${item.orgName}</option>
				</c:forEach>
	        </select>
		</td> 
  		
  		<th width="9%" align="right">经办人：</th>
  		<td width="30%">
	    	<input id="columnB" name="columnB" onfocus="autoCompletes();"  value="${purchaseForm.columnB }"  type="text"  placeholder="请填写经办人" class="text01" style="width:195px;"  />
		</td> 
	</tr>

</table>
</form>
