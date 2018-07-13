<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>查看-合同开支</title>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	
	<!-- 联想查询 -->
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
</head>

<body>

<div class="gl_import_m">
  	<div class="ge01"></div>
  	<div class="tabpages" >
    	<ul class="l" id="tab">
	      		<li id="tab1"  class="current">查看-合同开支<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<a class="btn_common01" href="javascript:window.close();" /><img src="/SRMC/dc/images/btnIcon/invalid.png" /><span>关闭</span></a>
		</div>    	
  	</div>	
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<form action=""  method="post"  id="form1">
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<tr>
		    <c:set var="zxmHt" value="${vo.zxmHt}"/>
		    <th width="20%" align="right"><b>*</b>合同编号：</th>
		    <input type="hidden" name="column01" id="column01" value="${vo.column01}"/>
		    <td><input name="column01Code"  id="column01Code"  type="text" class="text01" style="width:300px;" placeholder="请填写合同编号"  value="${zxmHt.column01}" onfocus="autoCompletes();" onkeyup="clean()" maxlength="20"  disabled="disabled"/></td>
		    <th align="right"><b>*</b>累计形象进度/MIS接收金额：</th>
		    <td><input name="column07"  id="column07"  type="text" class="text01" style="width:300px;" placeholder="请填写累计形象进度/MIS接收金额"  value="<fmt:formatNumber value="${vo.column07}" type="currency"  maxFractionDigits="6"/>" maxlength="11" disabled="disabled"/>万元</td>		    		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>合同项目名称：</th>
		    <td><input name="column01Name"  id="column01Name"  type="text" class="text01" style="width:300px;" placeholder="请填写合同项目名称"  value="${zxmHt.column03}" onfocus="autoCompletes();" onkeyup="clean()" maxlength="50" disabled="disabled"/></td>
		    <th align="right"><b>*</b>本年形象进度/MIS接收金额：</th>
		    <td><input name="column08"  id="column08"  type="text" class="text01" style="width:300px;" placeholder="请填写本年形象进度/MIS接收金额"  value="<fmt:formatNumber value="${vo.column08}" type="currency"  maxFractionDigits="6"/>" maxlength="11" disabled="disabled"/>万元</td>		    
	    </tr>
	    
	    <tr>
	    	<th align="right"><b></b>投资编号：</th>
		    <td><input name="column02"  id="column02"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column02}" readonly="readonly" disabled="disabled"/></td>
		    <th align="right"><b>*</b>累计转资金额：</th>
		    <td><input name="column09"  id="column09"  type="text" class="text01" style="width:300px;" placeholder="请填写累计转资金额"  value="<fmt:formatNumber value="${vo.column09}" type="currency"  maxFractionDigits="6"/>"  maxlength="11" disabled="disabled"/>万元</td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>合同不含税金额：</th>
		    <td><input name="column03"  id="column03"  type="text" class="text01" style="width:300px;" placeholder="请填写合同不含税金额"  value="<fmt:formatNumber value="${vo.column03}" type="currency"  maxFractionDigits="6"/>" maxlength="11" disabled="disabled"/>万元</td>
		    <th align="right"><b>*</b>本年转资金额：</th>
		    <td><input name="column10"  id="column10"  type="text" class="text01" style="width:300px;" placeholder="请填写本年转资金额"  value="<fmt:formatNumber value="${vo.column10}" type="currency"  maxFractionDigits="6"/>" maxlength="11" disabled="disabled"/>万元</td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>合同含税金额：</th>
		    <td><input name="column04"  id="column04"  type="text" class="text01" style="width:300px;" placeholder="请填写合同含税金额"  value="<fmt:formatNumber value="${vo.column04}" type="currency"  maxFractionDigits="6"/>" maxlength="11" disabled="disabled"/>万元</td>
		    <th align="right"><b>*</b>累计付款金额：</th>
		    <td><input name="column11"  id="column11"  type="text" class="text01" style="width:300px;" placeholder="请填写累计付款金额"  value="<fmt:formatNumber value="${vo.column11}" type="currency"  maxFractionDigits="6"/>" maxlength="11" disabled="disabled"/>万元</td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>合同对方：</th>
		    <td><input name="column05"  id="column05"  type="text" class="text01" style="width:300px;" placeholder=""  value="${vo.column05}" maxlength="30" disabled="disabled"/>
		    <th align="right"><b>*</b>时间：</th>
		    <td>
		    	<input name="column12"  id="column12"  type="text" class="text01" style="width:300px;" placeholder="请填写时间"  
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column12}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" disabled="disabled"/>		    
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>合同类型：</th>
		    <td>
		    	${vo.column06 eq '1' ? '费用类':''}${vo.column06 eq '2' ? '订单类':''}		    
		    </td>
		    <th align="right"><b>*</b>负责人：</th>
		    <input type="hidden" name="column13" id="column13" value="${vo.column13}"/>
		    <td><input name="column13Name"  id="column13Name"  type="text" class="text01" style="width:300px;" placeholder="请填写负责人"  value="${vo.column13SysUser.userName}" maxlength="10" disabled="disabled"/></td>		    
	    </tr>
	   </form>	   
  	</table>
</div>
</body>
</html>
<script>
 $(document).ready(function(){
 
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});
</script>