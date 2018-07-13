<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script src="/SRMC/dc/account/js/jquery.js"></script>

	<!--文件上传样式，js -->
	<!-- <link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script> -->
	
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadifive/uploadifive.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadifive/jquery.uploadifive.js"></script>
	
	
	<!--公共JS -->
	<script type="text/javascript" >var basePath = "<%=basePath%>";var isCcFlag = "1";</script>
	<script type="text/javascript" src="/SRMC/dc/rules/js/common2.js"></script> 
	<style>
		#uploadifive-commonuploadTAX, #uploadifive-commonuploadMIS{
    		margin-left: 75%;
    		border: 0;		
		}
		.muban{
			color:#40baff !important;
		}
		a:visited {
		    text-decoration: none;
		    color: #40baff !important;
		}
		a:hover{
			text-decoration: none;
		}
	</style>
</head>
<body>
<form action="<%=basePath%>account/importInfoCollect?type=${type}" method="post" id="pageForm">
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${flowInfoForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${flowInfoForm.pageSize}" id="pageSize"	name="pageSize"/>
    <input type="hidden" value="${type}" id="type"	name="type"/>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
		<input name="" type="button" onclick="resets()" class="btn_reset r" value="重置" />
		<input name="" onclick="ev_query()" type="button" class="btn_search r" value="查询" />
	</div>
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	  	<th align="right">报账单号：</th>
	    <td><input  name="orderId" id="orderId"  type="text"  placeholder="报账单号" class="text01" value="${flowInfoForm.orderId }" style="width:175px;" /></td>
    </table>
<div class="searchCondition"><span class="searchCondition_header">信息补录</span></div>
  <table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	  <tr>
	    <th width="30%" align="right">导入税务合同文件：</th>
	    <td>
			<div class="text01" style="width:400px;" id="fi_box_div_4" value="8">
				<input name="commonuploadTAX" id="commonuploadTAX" type="file" class="btn_upload" style="display: none">
			</div>
		</td>
		<th align="left" style="padding-right:33%"><a class="muban" href="<%=basePath%>rulesController/downloadFile?type=shuiwu">模板下载</a></th>
	  </tr>
	  <tr id="fi_box_tr">
	    <th align="right">导入MIS流程文件：</th>
	    <td>	    	
	    	<div class="text01" style="width:400px;" id="fi_box_div_4" value="8">
				<input name="commonuploadMIS" id="commonuploadMIS" type="file" class="btn_upload" style="display: none">
			</div>
	    </td>
	    <th align="left" style="padding-right:33%"><a class="muban" href="<%=basePath%>rulesController/downloadFile?type=liuchen">模板下载</a></th>
	  </tr>
</table>
<div style="text-align: center;">
</div>
  <div class="ge01"></div>
  <div class="tabpages">
    <ul class="l">
      <li <c:if test="${type==1 }">class="current"</c:if>  id="one1" onclick="setTab(1);">税务信息</li>
      <li <c:if test="${type==2 }">class="current"</c:if> id="one2" onclick="setTab(2);">合同发票信息</li>
      <li <c:if test="${type==3 }">class="current"</c:if> id="one3" onclick="setTab(3);">审批流程信息</li>
    </ul>
  </div>
  <c:if test="${type==1 }">
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable" id="listTable_001">
  <tr>
    <th>序号</th>
    <th>报账单号</th>
    <th>税务认证时间</th>
    <th>备注</th>
  </tr>
  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
	  <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
	    <td>${item[0] }</td>
	    <td>${item[1] }</td>
	    <td>${item[11] }</td>
	    <td>${item[20] }</td>
	  </tr>
  </c:forEach>
</table>
</c:if>
<c:if test="${type==2 }">
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable" id="listTable_002">
  <tr>
    <th>序号</th>
    <th>报账单号</th>
    <th>摘要</th>
<!--     <th>合同名称</th> -->
<!--     <th>合同编码</th> -->
<!--     <th>供应商</th> -->
    <th>发票代码</th>
    <th>发票金额(不含税)</th>
    <th>增值税发票号码</th>
    <th>抵和凭证类型</th>
    <th>税率</th>
  </tr>
  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
  <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
    <td>${item[0] }</td>
    <td>${item[1] }</td>
    <td>${item[20] }</td>
    <td>${item[2] }</td>
    <td>${item[8] }</td>
    <td>${item[3] }</td>
    <td>${item[12] }</td>
    <td>${item[10] * 100 }%</td>
  </tr>
  </c:forEach>
  </table>
  </c:if>
  <c:if test="${type==3 }">
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable" id="listTable_003">
  <tr>
    <th>序号</th>
    <th>报账单号</th>
    <th colspan="2">提交电子报账单</th>
    <th colspan="2">室经理审批</th>
    <th colspan="2">部门领导审批</th>
    <th colspan="2">初审会计审批</th>
    <th colspan="2">税务会计审批</th>
    <th colspan="2">主办会计审批</th>
    <th colspan="2">财务经理审批</th>
    <th colspan="2">南基领导审批</th>
    <th colspan="2">省财务审批</th>
    <th colspan="2">出纳付款</th>
    </tr>
    <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
  <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
    <td>${i.index+1 }</td>
    <td>${item[1] }</td>
    <td>${item[2] }</td>
    <td>${item[3] }</td>
    <td>${item[4] }</td>
    <td>${item[5] }</td>
    <td>${item[6] }</td>
    <td>${item[7] }</td>
    <td>${item[8] }</td>
    <td>${item[9] }</td>
    <td>${item[10] }</td>
    <td>${item[11] }</td>
    <td>${item[12] }</td>
    <td>${item[13] }</td>
    <td>${item[14] }</td>
    <td>${item[15] }</td>
    <td>${item[16] }</td>
    <td>${item[17] }</td>
    <td>${item[18] }</td>
    <td>${item[19] }</td>
    <td>${item[20] }</td>
    <td>${item[21] }</td>
  </tr>
  </c:forEach>
  </table>
  </c:if>
 <div class="gd_page">       	
               <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>     
            </div>
  <div style="margin:30px auto;width:110px;"><input name="" type="button" class="btn_common02" onclick="back()" value="返回列表" /></div>
</div>

</form>
     <script type="text/javascript">
     $(function(){
     	//configMIS.uploadScript="<%=basePath%>rulesController/importExcel";

     	//8信息补录excel文件导入
     	//configMIS.formData.type = "8";
		jQuery('#commonuploadMIS').uploadifive(configMIS);//流程信息文件导入
		
		//税务合同信息导入
		//configTAX.uploadScript="<%=basePath%>rulesController/importExcel";
		//configTAX.formData.type = "suiwu";
		jQuery('#commonuploadTAX').uploadifive(configTAX);
	 });
	 
     function setTab(cursel){
		var ss = jQuery("#type").val(cursel);
		window.location.href="<%=basePath%>account/importInfoCollect?type="+cursel;	
	 }
	 
	 function ev_query(){
	 	document.forms[0].submit();
	 }
	 
	 function resets(){
	 	$("#orderId").val("");
	 	ev_query();
	 }
	
	function back(){
		window.location.href="<%=basePath%>account/infoCollectIndex";
	}
     </script>      
         
</body>
</html>