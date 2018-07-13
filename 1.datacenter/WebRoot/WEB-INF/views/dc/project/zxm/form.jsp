<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

		<form action=""  method="post"  id="form1">
		<c:set var="tzbm" value="${vo.tzbm}"/>
		<c:set var="jsxm" value="${tzbm.jsxm}"/>
		<input type="hidden" name="jsxmId" id="jsxmId" value="${jsxm.id}"/>
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<tr>
		    <th width="20%" align="right"><b>*</b>项目类型：</th>
		    <td id="rulesGrade_td">
		    	<select class="ui-select" id="sel_01"  name="column01" style="width:306px;" placeholder="请填写项目类型"  disabled="disabled">
						<option value="">请选择</option>
						<option ${vo.column01 eq '1' ? 'selected="selected"':''} value="1">软件工程</option>
						<option ${vo.column01 eq '2' ? 'selected="selected"':''} value="2">集成工程</option>
						<option ${vo.column01 eq '3' ? 'selected="selected"':''} value="3">土建工程</option>
						<option ${vo.column01 eq '4' ? 'selected="selected"':''} value="4">征地工程</option>    	
		        </select>
		    </td>
		    <th align="right"><b>*</b>创建时间：</th>
		    <td><input name="column08"  id="column08"  type="text" class="text01" style="width:300px;" placeholder="请填写创建时间"  
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column08}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" disabled="disabled"/></td>		    		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>子项目编号：</th>
		    <td><input name="column02"  id="column02"  type="text" class="text01" style="width:300px;" placeholder="请填写子项目编号"  value="${vo.column02}" disabled="disabled"/></td>
		    <th align="right"><b>*</b>子项目名称：</th>
		    <td><input name="column03"  id="column03"  type="text" class="text01" style="width:300px;" placeholder="请填写子项目名称"  value="${vo.column03}" disabled="disabled"/></td>		    
	    </tr>
	    
	    <tr>
	    	<th align="right"><b>*</b>子项目管理员：</th>
		    <td><input name="column09"  id="column09"  type="text" class="text01" style="width:300px;" placeholder="请填写子项目管理员"  value="${vo.column09Name}" disabled="disabled"/></td>
		    <th align="right"><b>*</b>子项目审核人：</th>
		    <td><input name="column05"  id="column05"  type="text" class="text01" style="width:300px;" placeholder="请填写子项目审核人"  value="${vo.column05Name}" disabled="disabled"/></td>		    
	    </tr>
	    <tr>
		    <th align="right"><b></b>建设内容：</th>
		    <td colspan="3"><textarea rows="5" cols="20" style="width:89.4%" name="column06" placeholder=""  disabled="disabled" title="${vo.column06}">${vo.column06}</textarea></td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>投资编号：</th>
		    <input type="hidden" name="column07" id="column07" value="${tzbm.id}"/>
		    <td><input name="column07Code"  id="column07Code"  type="text" class="text01" style="width:300px;" placeholder=""  onfocus="autoCompletes();" value="${tzbm.column02}" disabled="disabled"/></td>
		    <th align="right"><b></b>投资项目名称：</th>
		    <td><input name="column07Name"  id="column07Name"  type="text" class="text01" style="width:300px;" placeholder=""  onfocus="autoCompletes();"  value="${tzbm.column03}" disabled="disabled"/></td>		    
	    </tr>
	    
	    <c:set var="zxmHtList" value="${vo.zxmHtList}"/>
	    <c:forEach items="${zxmHtList}" var="vo" varStatus="i">
	    <tr>
		    <th align="right"><b></b>子项目合同编号${i.count}：</th>
		    <td><input name=""  id=""  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column01}"  readonly="readonly" disabled="disabled"/></td>
		    <th align="right"><b></b>子项目合同名称${i.count}：</th>
			<td><input name=""  id=""  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column03}"  readonly="readonly" disabled="disabled"/></td>
	    </tr>
	    </c:forEach>
	   </form>