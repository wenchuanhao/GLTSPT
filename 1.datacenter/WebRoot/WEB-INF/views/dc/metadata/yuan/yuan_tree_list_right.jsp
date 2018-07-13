<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<script>
	//树结构数据初始化
	zNodes = $.parseJSON('${zNodes}'.replace("\r\n", "\\r\\n"));
</script>

	    	<table id="table01" cellpadding="0" cellspacing="5" class="taskCreate" width="100%">
	    		<input type="hidden"  name="id"  id="id"  value="${vo.id}"/>
	             <tr>
	               <th width="15%" id=""><em>*</em>元数据表名称：</th>
	               <td><input  name="tableName" id="tableName" value="${vo.tableName}" type="text" class="text01" style="width:174px;" /></td>
	               <th width="15%" ><em>*</em>表中的行数：</th>
	               <td><input  name="numRows" id="numRows"  value="${numRows}" type="text" class="text01" style="width:174px;" disabled="disabled"/></td>
	               <td><input style="display: ${numRows eq 0 ? 'none':'block'};" onclick="ev_edit()" type="button" class="btn_common02" value="查看数据" /></td>	               
	             </tr>
	            
	             <tr id="parentId_tr01">
	               <th width="15%"><em>*</em>父类：</th>
	               <td colspan="4">
		               	<select class="ui-select" id="parentId" name="parentId" style="width:180px;">
							<option value="">请选择</option>
				          	<c:forEach items="${list}" var="item" varStatus="i">
				    				<option value="${item.id}" ${item.id eq vo.parentId ? 'selected=selected':''} >${item.tableName}</option>
							</c:forEach>
				        </select>
	        	   </td>	               
	             </tr>
	            
	            <tr>
	            <td colspan="5">
				<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
				  <tr>
				  		<th style="text-align: center;">序号</th>
				  		<th style="text-align: center;">注释</th>
				  		<th style="text-align: center;">列名</th>
				  		<th style="text-align: center;">数据类型</th>
				  		<th style="text-align: center;">长度</th>
				  		<th style="text-align: center;">精度</th>
				  		<th style="text-align: center;">小数点后位数</th>
				  		<th style="text-align: center;">是否允许为空</th>
				  		<th style="text-align: center;">默认值</th>
				  </tr>
				  
				  <c:forEach items="${vo.tableColumn}" var="item" varStatus="i">
				  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> >
				  	 		<td>${i.count}</td>
				  	  		<td>${item.comments}</td>
				  	  		<td>${item.column_name}</td>
				  	  		<td>${item.data_type}</td>
				  	  		<td>${item.data_length}</td>
				  	  		<td>${item.data_precision}</td>
				  	  		<td>${item.data_scale}</td>
				  	  		<td>${item.nullable}</td>
				  	  		<td>${item.data_default}</td>
					 </tr>
				   </c:forEach>
				</table>
				</td>	            
	            </tr>
	            
	            <tr>
	               <th>&nbsp;</th>
	               <td>
						<input onclick="ev_save()" id="add_input" type="button" class="btn_common02" value="保存" />
				   </td>
				   <td colspan="3"></td>
	            </tr>
	         </table>