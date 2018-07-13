<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

		<form action=""  method="post"  id="form1">
		<c:set var="zxm" value="${vo.zxm}"/>
		<c:set var="tzbm" value="${zxm.tzbm}"/>
		<c:set var="jsxm" value="${tzbm.jsxm}"/>
		<input type="hidden" name="jsxmId" id="jsxmId" value="${jsxm.id}"/>		
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<tr>
		    <th align="right"><b>*</b>子项目编号：</th>
		    <c:set var="zxm" value="${vo.zxm}"/>
		    <input type="hidden" name="column04" id="column04" value="${zxm.id}"/>
		    <td><input name="column04Code"  id="column04Code"  type="text" class="text01" style="width:300px;" placeholder="请填写子项目编号"  value="${zxm.column02}" onfocus="autoCompletes();" disabled="disabled"/></td>
		    <th align="right"><b></b>合同含税金额：</th>
		    <td><input name="column11"  id="column11"  type="text" class="text01" style="width:300px;" placeholder=""  value="<fmt:formatNumber value="${vo.column11}" type="currency"  maxFractionDigits="6"/>" disabled="disabled"/>万元</td>		    		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>子项目名称：</th>
		    <td><input name="column04Name"  id="column04Name"  type="text" class="text01" style="width:300px;" placeholder=""  value="${zxm.column03}" readonly="readonly" disabled="disabled"/></td>	    
		    <th align="right"><b></b>合同状态：</th>
		    <td><input name="column12"  id="column12"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column12}" disabled="disabled"/>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th width="20%" align="right"><b>*</b>合同编号：</th>
		    <td><input name="column01"  id="column01"  type="text" class="text01" style="width:300px;" placeholder="请填写合同编号"  value="${vo.column01}" disabled="disabled"/></td>	    
		    <th align="right"><b></b>收支类型：</th>
		    <td><input name="column13"  id="column13"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column13}" disabled="disabled"/>
		    </td>		    
	    </tr>
	    
	    <tr>
	    	<th align="right"><b>*</b>合同名称：</th>
		    <td><input name="column03"  id="column03"  type="text" class="text01" style="width:300px;" placeholder="请填写合同名称"  value="${vo.column03}" disabled="disabled"/></td>	    
		    <th align="right"><b></b>合同对方：</th>
		    <td><input name="column14"  id="column14"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column14}" disabled="disabled"/>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>合同流水号：</th>
		    <td><input name="column02"  id="column02"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column02}" disabled="disabled"/></td>
		    <th align="right"><b></b>预算项目：</th>
		    <td><input name="column15"  id="column15"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column15}" disabled="disabled"/></td>		    
	    </tr>
	    
	    
	    <tr>
		    <th align="right"><b></b>是否铁通合同：</th>
		    <td>
		    	<input type="radio" name="column05" value="Y"  ${vo.column05 eq 'Y' ? 'checked="checked"':''} disabled="disabled"/>是
		    	<input type="radio" name="column05" value="N" ${vo.column05 eq 'N' ? 'checked="checked"':''} disabled="disabled"/>否
		    </td>
		    <th align="right"><b></b>归档状态：</th>
		    <td><input name="column16"  id="column16"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column16}" disabled="disabled"/>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>主办公司：</th>
		    <td><input name="column06"  id="column06"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column06}" disabled="disabled"/>
		    </td>
		    <th align="right"><b></b>签署状态：</th>
		    <td><input name="column17"  id="column17"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column17}"  disabled="disabled"/>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>主办部门：</th>
		    <td><input name="column07"  id="column07"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column07}" disabled="disabled"/>
		    </td>
		    <th align="right"><b></b>扫描状态：</th>
		    <td id="rulesGrade_td">
		    	<select class="ui-select" id="sel_01"  name="column18" style="width:306px;" disabled="disabled">
		    			<option value="">请选择</option>
						<option ${vo.column18 eq 'Y'  ? 'selected="selected"':''} value="Y">是</option>
						<option ${vo.column18 eq 'N' ? 'selected="selected"':''} value="N">否</option>
		        </select>
		    </td>		    
	    </tr>	    
	    
	    <tr>
		    <th align="right"><b></b>主办人：</th>
		    <td><input name="column08"  id="column08"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column08}" disabled="disabled"/></td>
		    <th align="right"><b></b>合同签订日期：</th>
		    <td>
		    	<input name="column19"  id="column19"  type="text" class="text01" style="width:300px;" placeholder=""  
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column19}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" disabled="disabled"/>
		    </td>		    
	    </tr>	    
	    
	    <tr>
		    <th align="right"><b>*</b>合同不含税金额：</th>
		    <td><input name="column09"  id="column09"  type="text" class="text01" style="width:300px;" placeholder="请填写合同不含税金额"  value="<fmt:formatNumber value="${vo.column09}" type="currency"  maxFractionDigits="6"/>" disabled="disabled"/>万元</td>
		    <th align="right"><b></b>合同类型：</th>
		    <td>
		    	<select class="ui-select" id="column22"  name="column22" style="width:306px;" disabled="disabled">
						<option ${vo.column22 eq '1' ? 'selected="selected"':''} value="1">费用类</option>
						<option ${vo.column22 eq '2' ? 'selected="selected"':''} value="2">订单类</option>
		        </select>		    	
		    </td>
	    </tr>
	    
	   	<tr id="_column23" style="display: ${(vo.column22 ne '' && vo.column22 eq '2') ? 'block':'none'};">
		    <th align="right"><b></b>&nbsp;</th>
		    <td>&nbsp;</td>	    
		    <th align="right"><b></b>订单号：</th>
		    <td>
		    <input name="column23"  id="column23"  type="text" class="text01" style="width:300px;" placeholder="请填写订单号"  value="${vo.column23}" maxlength="50" disabled="disabled"/>
		    </td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>合同税金：</th>
		    <td><input name="column10"  id="column10"  type="text" class="text01" style="width:300px;" placeholder=""  value="<fmt:formatNumber value="${vo.column10}" type="currency"  maxFractionDigits="6"/>" disabled="disabled"/>万元</td>
		    <th align="right"><b>*</b>项目合同管理员：</th>
		    <td>
		    	<input name="column21"  id="column21"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column21Name}" disabled="disabled"/>
		    </td>	    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>附件：</th>
		    <td>
			    <div class="text01" style="width:300px;" id="fi_box_div_1" value="1" >
			    	<input type="hidden"  name="fileName" id="fileName" />
			    	<input type="file" name="file"  id="uploadFiles1"  style="display:none"/>
			    </div>		    
		    </td>
		    <th align="right"><b></b>合同来源：</th>
		    <td>
		    	<input type="hidden"  name="column20" id="column20" value="${vo.column20}"/>
		    	<input type="radio" name="column20R" value="1"  ${vo.column20 eq '1' ? 'checked="checked"':''} disabled="disabled"/>新增
		    	<input type="radio" name="column20R" value="2"  ${vo.column20 eq '2' ? 'checked="checked"':''} disabled="disabled"/>合同管理系统
		    </td>		    
	    </tr>
	    
	    <c:forEach items="${vo.htAttach}" var="vo" varStatus="i">
	    <tr>
		    <th align="right"><b></b></th>
		    <td colspan="3">${i.count}、
			    	<a id="file_download" style="color: blue;" href="javascript:ev_download1('${vo.id}')">${vo.column01} (<fmt:formatNumber value="${vo.column02/1024}" pattern="#"/>KB)</a>
		    </td>
	    </tr>
	    </c:forEach>	    
	   </form>
<script>
function ev_download1(id){
	window.location.href = "<%=basePath%>zxmHt/downFile?key="+Math.random()+"&id="+id;
}
</script>	   