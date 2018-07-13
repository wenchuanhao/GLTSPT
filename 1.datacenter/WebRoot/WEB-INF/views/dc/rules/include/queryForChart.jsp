<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
		<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
		<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />
	</div>
	<form name="form" id="pageForm" method="post"  >
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${rulesForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${rulesForm.pageSize}" id="pageSize"	name="pageSize"/>	
    <input type="hidden" name="status" id="status_input" value="${rulesForm.status}"/>
    <input type="hidden" name="rulesGrade" id="rulesGrade_input" value="${rulesForm.rulesGrade}"/>
	<input type="hidden" name="rulesTypeId" id="rulesTypeId_input" value="${rulesForm.rulesTypeId}"/>
	<input type="hidden" name="leadDepId" id="leadDepId_input" value="${rulesForm.leadDepId}"/>
	<input type="hidden" name="subBoxValue" id="subBoxValue"/>
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">制度名称：</th>
	    <td width="30%"><input id="rulesName" name="rulesName" value="${rulesForm.rulesName }"  type="text"  placeholder="请填写制度名称" class="text01" style="width:174px;"  /></td> 
	    <th width="9%" align="right">牵头部门：</th>
    	<td width="30%">
	    	<select class="ui-select" id="sel_01" style="width:180px;">
	    	  	<jsp:include flush="true" page="rulesLeadDepList.jsp"></jsp:include>
	        </select>
        </td>
	</tr>
	
	<tr>
		<th width="9%" align="right">制度分类：</th>
	    <td width="30%">
       		<jsp:include flush="true" page="rulesTypeList.jsp"></jsp:include>
	    </td>
	    <th width="9%" align="right">制度等级：</th>
	    <td width="30%">
	        <select class="ui-select" id="sel_03"  style="width:180px;">
	    		<jsp:include flush="true" page="rulesGrades.jsp"></jsp:include>
	        </select>
	    </td>
	</tr>
	
	<tr>
		<th width="9%" align="right">发布时间：</th>
    	<td width="30%">
    		<div class="date l">
	    	<input readonly="readonly" name="handelBeginDate" id="handelBeginDate" type="text"  placeholder="请您输入"  class="text02 l" value="<fmt:formatDate value='${rulesForm.handelBeginDate}' pattern='yyyy-MM-dd HH:mm:ss' />"
                      onclick="WdatePicker({startDate:'%y-%M-{%d} %H:00:00', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" />
	    	</div>
    	</td>
    	<th width="9%" align="right"></th>
    	<td width="30%">
	    	<div class="date l">
	    	<input readonly="readonly" name="handelEndDate" id="handelEndDate" type="text"  placeholder="请您输入"  class="text02 l" value="<fmt:formatDate value='${rulesForm.handelEndDate}' pattern='yyyy-MM-dd HH:mm:ss' />"
                      onclick="WdatePicker({minDate:'#F{$dp.$D(\'handelBeginDate\')||\'%y-%M-%d\'}',  dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" />
	    	</div>
    	</td>
	</tr>
	
    <tr>
    
    	<th width="9%" align="right">状态：</th>
	    <td width="30%">
	    	<select class="ui-select" id="sel_04" style="width:180px;">
    			<jsp:include flush="true" page="rulesStatus.jsp"></jsp:include>
	        </select>
	    </td>
	    
  	</tr>
	</table>
	</form>
  	<div class="ge01"></div>