<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

		<form action=""  method="post"  id="form1">
		<input type="hidden" name="jsxmId" id="jsxmId" value="${vo.id}"/>
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<tr>
		    <th width="30%" align="right"><b>*</b>项目类型：</th>
		    <td id="rulesGrade_td">
		    	<select class="ui-select" id="sel_01"  name="column01" style="width:506px;" disabled="disabled">
						<option value="">请选择</option>
						<option ${vo.column01 eq '1' ? 'selected="selected"':''} value="1">软件工程</option>
						<option ${vo.column01 eq '2' ? 'selected="selected"':''} value="2">集成工程</option>
						<option ${vo.column01 eq '3' ? 'selected="selected"':''} value="3">土建工程</option>
						<option ${vo.column01 eq '4' ? 'selected="selected"':''} value="4">征地工程</option>    	
		        </select>
		    </td>		    
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>建设项目编号：</th>
		    <td>
		    	<input name="column02"  id="column02"  type="text" class="text01" style="width:500px;" placeholder="请填写项目类型"  value="${vo.column02}" disabled="disabled"/>
		    </td>
	    </tr>
	    <tr>
	    	<th align="right"><b>*</b>建设项目名称：</th>
		    <td>
		    	<input name="column03"  id="column03"  type="text" class="text01" style="width:500px;" placeholder="请填写项目类型"  value="${vo.column03}" disabled="disabled"/>
		    </td>
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>建设总控：</th>
		    <td>
		    	<input name="column04"  id="rulesName"  type="text" class="text01" style="width:500px;" placeholder="请填写项目类型"  value="${vo.column04Name}" disabled="disabled"/>
		    </td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>审核人：</th>
		    <td>
		    	<input name="column05"  id="column05"  type="text" class="text01" style="width:500px;" placeholder="请填写项目类型"  value="${vo.column05Name}" disabled="disabled"/>
		    </td>
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>建设项目管理员：</th>
		    <td>
		    	<input name="column10"  id="column10"  type="text" class="text01" style="width:500px;" placeholder="请填写建设项目管理员"  value="${vo.column10Name}" disabled="disabled"/>
		    </td>
	    </tr>	    
	    <tr>
		    <th align="right"><b></b>建设内容：</th>
		    <td><textarea rows="3" cols="20" style="width:500px" name="column06"  id="column06" placeholder=""  disabled="disabled" title="${vo.column06}">${vo.column06}</textarea></td>
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>创建时间：</th>
		    <td>
		    	<input name="column07"  id="column07"  type="text" class="text01" style="width:500px;" placeholder="请填写项目类型"  
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column07}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" disabled="disabled"/>
		    </td>
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>项目状态：</th>
		    <td id="rulesGrade_td">
		    	<select class="ui-select" id="sel_01"  name="column08" style="width:506px;" disabled="disabled">
		    			<option ${vo.column08 eq null ? 'selected="selected"':''} value="">请选择</option>
						<option ${vo.column08 eq '1' ? 'selected="selected"':''} value="1">立项完成</option>
						<option ${vo.column08 eq '2' ? 'selected="selected"':''} value="2">采购完成</option>
						<option ${vo.column08 eq '3' ? 'selected="selected"':''} value="3">设计完成</option>
						<option ${vo.column08 eq '4' ? 'selected="selected"':''} value="4">施工完成</option>
						<option ${vo.column08 eq '5' ? 'selected="selected"':''} value="5">初验完成</option>
						<option ${vo.column08 eq '6' ? 'selected="selected"':''} value="6">终验完成</option>
		        </select>
		    </td>
	    </tr>
	    <c:set var="tzbmList" value="${vo.tzbmList}"/>
	    <c:forEach items="${tzbmList}" var="vo" varStatus="i">
	    <tr>
		    <th align="right"><b></b>投资编号${i.count}：</th>
		    <td><input name="column09Code"  id="column09Code"  type="text" class="text01" style="width:500px;" placeholder=""  value="${vo.column02}"  readonly="readonly" disabled="disabled"/></td>
	    </tr>
	    <tr>
		    <th align="right"><b></b>投资编码名称${i.count}：</th>
		    <td><input name="column09Name"  id="column09Name"  type="text" class="text01" style="width:500px;" placeholder=""  value="${vo.column03}"  readonly="readonly" disabled="disabled"/></td>
	    </tr>	    
	    </c:forEach>
	   </form>