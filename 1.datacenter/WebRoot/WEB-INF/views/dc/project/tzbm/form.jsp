<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

		<form action=""  method="post"  id="form1">
		<c:set var="jsxm" value="${vo.jsxm}"/>
		<input type="hidden" name="jsxmId" id="jsxmId" value="${jsxm.id}"/>
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<tr>
		    <th width="20%" align="right"><b>*</b>项目类型：</th>
		    <td id="rulesGrade_td">
		    	<select class="ui-select" id="sel_01"  name="column01" style="width:306px;" disabled="disabled">
						<option value="">请选择</option>
						<option ${vo.column01 eq '1' ? 'selected="selected"':''} value="1">软件工程</option>
						<option ${vo.column01 eq '2' ? 'selected="selected"':''} value="2">集成工程</option>
						<option ${vo.column01 eq '3' ? 'selected="selected"':''} value="3">土建工程</option>
						<option ${vo.column01 eq '4' ? 'selected="selected"':''} value="4">征地工程</option>    	
		        </select>
		    </td>
		    <th align="right"><b>*</b>项目总投资：</th>
		    <td><input name="column06"  id="column06"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="<fmt:formatNumber value="${vo.column06}" type="currency"  maxFractionDigits="6"/>" disabled="disabled"/>万元</td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>投资编号：</th>
		    <td><input name="column02"  id="column02"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column02}" disabled="disabled"/></td>
		    <th align="right"><b>*</b>至上年度安排投资计划：</th>
		    <td><input name="column11"  id="column11"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="<fmt:formatNumber value="${vo.column11}" type="currency"  maxFractionDigits="6"/>" disabled="disabled"/>万元</td>		    		    
	    </tr>
	    
	    <tr>
	    	<th align="right"><b>*</b>投资项目名称：</th>
		    <td><input name="column03"  id="column03"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column03}" disabled="disabled"/></td>
		    <th align="right"><b>*</b>年度投资：</th>
		    <td><input name="column08"  id="column08"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="<fmt:formatNumber value="${vo.column08}" type="currency"  maxFractionDigits="6"/>" disabled="disabled"/>万元</td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>建设项目编号：</th>
		    <input type="hidden" name="column04" id="column04" value="${jsxm.id}"/>		    
		    <td><input name="column04Code"  id="column04Code"  type="text" class="text01" style="width:300px;" placeholder=""  value="${jsxm.column02}" disabled="disabled"/>
		    </td>
		    <th align="right"><b>*</b>资本开支目标：</th>
		    <td><input name="column09"  id="column09"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="<fmt:formatNumber value="${vo.column09}" type="currency"  maxFractionDigits="6"/>" disabled="disabled"/>万元</td>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>建设项目名称：</th>
		    <td><input name="column04Name"  id="column04Name"  type="text" class="text01" style="width:300px;" placeholder=""  value="${jsxm.column03}" disabled="disabled"/></td>
		    <th align="right"><b>*</b>年度转资目标：</th>
		    <td><input name="column12"  id="column12"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="<fmt:formatNumber value="${vo.column12}" type="currency"  maxFractionDigits="6"/>" disabled="disabled"/>万元</td>		    
	    </tr>
	    
	    
	    <tr>
		    <th align="right"><b></b>建设内容：</th>
		    <td colspan="3"><textarea rows="5" cols="20" style="width:89.4%" name="column05" disabled="disabled" title="${vo.column05}">${vo.column05}</textarea></td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>计划书文号：</th>
		    <td><input name="column15"  id="column15"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column15}" disabled="disabled"/></td>		    
		    <th align="right"><b></b>任务书文号：</th>
		    <td><input name="column16"  id="column16"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column16}" disabled="disabled"/>
		    </td>		    
	    </tr>
	    <tr>
		    <th align="right"><b></b>建设期限：</th>
		    <td><input name="column07"  id="column07"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column07}" disabled="disabled"/></td>
		    <th align="right"><b>*</b>创建时间：</th>
		    <td>
		    	<input name="column17"  id="column17"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column17}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" disabled="disabled"/>
		    </td>		    
	    </tr>	    
	    
	    <tr>
		    <th align="right"><b>*</b>投资项目联系人：</th>
		    <td><input name="column13"  id="column13"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column13Name}" disabled="disabled"/></td>		    
		    <th align="right"><b>*</b>状态：</th>
		    <td id="rulesGrade_td">
		    	<select class="ui-select" id="sel_01"  name="column18" style="width:306px;" disabled="disabled">
		    			<option ${vo.column18 eq null ? 'selected="selected"':''} value="">请选择</option>
						<option ${vo.column18 eq '1' ? 'selected="selected"':''} value="1">立项完成</option>
						<option ${vo.column18 eq '2' ? 'selected="selected"':''} value="2">采购完成</option>
						<option ${vo.column18 eq '3' ? 'selected="selected"':''} value="3">设计完成</option>
						<option ${vo.column18 eq '4' ? 'selected="selected"':''} value="4">施工完成</option>
						<option ${vo.column18 eq '5' ? 'selected="selected"':''} value="5">初验完成</option>
						<option ${vo.column18 eq '6' ? 'selected="selected"':''} value="6">终验完成</option>
		        </select>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>投资项目督办人：</th>
		    <td><input name="column14"  id="column14"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column14Name}" disabled="disabled"/>
		    <th align="right"><b>*</b>投资编码管理员：</th>
		    <td><input name="column19"  id="column19"  type="text" class="text01" style="width:300px;" placeholder="请填写投资编码管理员"  value="${vo.column19Name}" disabled="disabled"/></td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>备注：</th>
		    <td colspan="3"><textarea rows="3" cols="20" style="width:89.4%" name="column10" disabled="disabled" title="${vo.column10}">${vo.column10}</textarea></td>
	    </tr>
	   </form>