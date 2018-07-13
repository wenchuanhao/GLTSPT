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
	            <td colspan="5">
				<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
				  <tr>
				  		<th style="text-align: center;">序号</th>
				  		<th style="text-align: center;"><font color="red">原始注释</font></th>
				  		<th style="text-align: center;"><font color="red">原始列名</font></th>
				  		<th style="text-align: center;"><font color="red">原始数据类型</font></th>
				  		<th style="text-align: center;"><font color="red">原始长度</font></th>
				  		<th style="text-align: center;">当前数据类型</th>
				  		<th style="text-align: center;">当前长度</th>
				  		<th style="text-align: center;">是否允许为空</th>
				  		<th style="text-align: center;">数据格式</th>
				  </tr>
				  <c:set var="len" value="0"/>
				  <c:forEach items="${vo.tableColumn}" var="item" varStatus="i" >
				  <c:set var="len" value="${len+1}"/>
				  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> >
				  	 		<td>${i.count}</td>
				  	  		<td>${item.comments}</td>
				  	  		<td>${item.column_name}</td>
				  	  		<td>${item.data_type}</td>
				  	  		<td>${item.data_length}</td>
				  	  		<td>
				  	  				<input type="hidden"  name="last"  id="last"  value="${i.count}"/>
				  	  				<input type="hidden"  name="cuurent_id${i.count}"  id="cuurent_id${i.count}"  value="${item.cuurent_id}"/>
				  	  				<input type="hidden"  name="cuurent_table_id${i.count}"  id="cuurent_table_id${i.count}"  value="${item.cuurent_table_id}"/>
				  	  				<input type="hidden"  name="cuurent_column_name${i.count}"  id="cuurent_column_name${i.count}"  value="${item.cuurent_column_name}"/>
				  	  				<input type="hidden"  name="cuurent_show_order${i.count}"  id="cuurent_show_order${i.count}"  value="${item.cuurent_show_order}"/>
				  	  				
				  	  				<input type="radio" <c:if test="${item.cuurent_data_type=='String' }">checked="checked"</c:if> name="cuurent_data_type${i.count}"  id="cuurent_data_type${i.count}"  value="String"/>字符
				  	  				<input type="radio" <c:if test="${item.cuurent_data_type=='Number' }">checked="checked"</c:if> name="cuurent_data_type${i.count}"  id="cuurent_data_type${i.count}"  value="Number"/>数字
				  	  				<input type="radio" <c:if test="${item.cuurent_data_type=='Date' }">checked="checked"</c:if> name="cuurent_data_type${i.count}"  id="cuurent_data_type${i.count}"  value="Date"/>日期
				  	  		</td>
				  	  		<td><input type="text"  name="cuurent_data_length${i.count}"  id="cuurent_data_length${i.count}"  value="${item.cuurent_data_length}"  style="width: 35px;"/></td>
				  	  		<td>
				  	  				<input type="radio" <c:if test="${item.cuurent_nullable=='1' }">checked="checked"</c:if> name="cuurent_nullable${i.count}"  id="cuurent_nullable${i.count}"  value="1"/>是
				  	  				<input type="radio" <c:if test="${item.cuurent_nullable=='0' }">checked="checked"</c:if> name="cuurent_nullable${i.count}"  id="cuurent_nullable${i.count}"  value="0"/>否
				  	  		</td>
				  	  		<td><input type="text"  name="cuurent_data_format${i.count}"  id="cuurent_data_format${i.count}"  value="${item.cuurent_data_format}"/></td>
					 </tr>
				   </c:forEach>
				   
				   <input type="hidden"  name="len"  id="len"  value="${len}"/>
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