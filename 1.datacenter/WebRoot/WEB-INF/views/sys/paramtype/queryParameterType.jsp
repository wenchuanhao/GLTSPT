<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统参数</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
	</head>
	<body class="bg_c_g">
		<form method="post" name="form" id="pageForm"
			action="<%=basePath%>sys/paramtype/queryParameterType">
			<input type="hidden" id="paramTyprIds" name="paramTyprIds" />
			<div class="gl_m_r_nav">
				当前位置 : 系统管理 &gt; 系统参数 &gt; 管理参数类型
			</div>
			<table class="gl_m_r_n_tb01" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td valign="top" class="gl_m_r_n_tb01_m"></td>
					<td valign="top">
						<div class="gl_bt_bnt01">
							<input name="" type="button" class="gl_cx_bnt01" id="setDivs"
								value="收起查询" onclick="setDiv();" />
							查询
						</div>
						<div id="serachDisplay" style="display: block;">
							<!-- 
               <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
	               <tr>
	                 <td >参数类型名称：
	                   <input class="gl_text01b" type="text" name="parameterTypeName" id="parameterTypeName" value="${form.parameterTypeName}"/>&nbsp;
	                   <input id="img" name="input" type="button" class="gl_cx_bnt03" value="查 询" onclick="doSearch();"/></div>
	                 </td>
	               </tr>
	               
                </table>
                 -->
							<table class="gl_table_a01_1" align="center"  border="0" style="width: 70%;"
							cellspacing="0" cellpadding="0" id="queryDiv" style="display: block;">
							<tr>
								<td width="120" align="center" style="border: 1px rgb(207,208,208) solid;">
									<span style="font-size: 14px;font-weight: bold; margin-bottom: 1px;">参数类型名称：</span>
								</td>
								<td width="58%" style="background-color: rgb(239,244,250);">
								&nbsp;
									<input class="gl_text01_a" style="width: 95%;" type="text"
											name="parameterTypeName" id="parameterTypeName"
											value="${form.parameterTypeName}"  style="" />
								</td>
								<td width="165" style="background-color: rgb(239,244,250);" colspan="2">
									<!-- <input name="" type="submit" class="ipt_tb_n03" value="搜 索" style="margin-bottom: 1px;" /> -->
									<input name="" type="button" class="ipt_tb_n03" value="搜 索" style="margin-bottom: 1px;" onclick="javascript:doSubmit();"/>
									<input name="" type="button" class="ipt_tb_n03" value="清 除" style="margin-bottom: 1px;" onclick="clean()"/>			
								</td>
							</tr>
						</table>
							<div id="serchBox" class="gl_ipt_03">
								<!-- <input id="img" name="input" type="button" class="ipt_tb_n03"
									value="查 询" onclick="doSearch();" /> 
								<input name="" type="submit" class="ipt_tb_n03" value="搜 索" />	-->
							</div>
						</div>
						<div class="ge_a01"></div>

						<div class="gl_bt_bnt01">
							参数类型列表
						</div>

						<div class="gl_bnt_nav01" style="border-bottom: none;">
							<input name="" type="button" class="gl_cx_bnt04" value="新 增"
								onclick="toAddParameterType();" />
							<input name="" type="button" class="gl_cx_bnt04" value="批量删除"
								onclick="doDeleteItems();" />
							<input name="" type="button" class="gl_cx_bnt04" value="导出"
							onclick="exportItems();" />
							<input name="fileName" id="uploadFiles" type="file" class="gl_cx_bnt04" style="display: none">
						</div>

						<table class="gl_table_a02" width="100%" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<th width="3%">
									<input type="checkbox" class="myClass" id="checkAll"
										name="checkAll" onclick="selectAll();" />
								</th>
								<th width="19%">
									参数类型名称
								</th>
								<th width="16%">
									参数类型编码
								</th>
								<th width="16%">
									参数类型值
								</th>
								<th width="22%">
									参数类型描述
								</th>
								<th width="8%">
									是否允许修改
								</th>
								<th width="16%">
									操作
								</th>
							</tr>
							<c:forEach items="${ITEMPAGE.items}" var="parameterType"
								varStatus="i">
								<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if>>
									<td>
										<input type="checkbox" class="myClass" name="checkbox"
											id="checkbox" value="${parameterType.parameterTypeId}" />
									</td>
									<td>
										${parameterType.parameterTypeName}
									</td>
									<td>
										${parameterType.parameterTypeCode}
									</td>
									<td>
										${parameterType.parameterTypeValue}
									</td>
									<td style="word-break:break-all;text-align:left;padding:3px 8px 3px 8px;line-height:19px;">
										${parameterType.parameterTypeDesc}
									</td>
									<td>
										<c:choose>
											<c:when test='${parameterType.allowUpdate == "1"}'>
				  				允许
				  			</c:when>
											<c:otherwise>
				  				不允许
				  			</c:otherwise>
										</c:choose>
									</td>
									<td align="center">
										<span class="gl_tab_tr_l"> <img
												src="/SRMC/rmpb/images/tab_tb01.png" align="middle" />
										</span>
										<span class="gl_tab_tr_r"><a
											href="javascript:parameterTypeDetail('${parameterType.parameterTypeId }');">查看</a>
										</span>
										<c:if test='${parameterType.allowUpdate != "1"}'>
										
										 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
										</c:if>
										<c:if test='${parameterType.allowUpdate == "1"}'>
											<span class="gl_tab_tr_l"><img
													src="/SRMC/rmpb/images/tab_tb06.png" align="middle" />
											</span>
											<span class="gl_tab_tr_r"><a
												href="javascript:toModifyParameterType('${parameterType.parameterTypeId }');">编辑</a>
											</span>
											<span class="gl_tab_tr_l"><img
													src="/SRMC/rmpb/images/tab_tb07.png" align="middle" />
											</span>
											<span class="gl_tab_tr_r"><a
												href="javascript:delParameterType('${parameterType.parameterTypeId }');">删除</a>
											</span>
									</td>
									</c:if>
								</tr>
							</c:forEach>
						</table>

						<div class="pageBox">
							<jsp:include flush="true" page="/public/include/navigate2.jsp"></jsp:include>
						</div>

					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript" src="/SRMC/rmpb/js/list-common.js"></script>
		
		
			<!--文件上传样式，js -->
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script>

	<script type="text/javascript" src="/SRMC/dc/js/ex_import.js"></script> 
		
		<script type="text/javascript">
		
		
//页面上传文件初始化
jQuery(function() {
	setConfig("<%=basePath%>sys/paramtype/importParameterType");
});
   function doSubmit(){
	   	document.all("pageIndex").value = "1";
		document.forms[0].action="<%=basePath%>sys/paramtype/queryParameterType";
	   	document.forms[0].submit();
	}
	
	function exportItems(){
		document.forms[0].action="<%=basePath%>sys/paramtype/exportParameterType";
		document.forms[0].submit();
	}
   </script>
		<script type="text/javascript">
  function selectAll() {
		for (var i=0;i<form.length ;i++) {
			var e = form[i];
			if (e.name == 'checkbox') {
	   			e.checked = form.checkAll.checked;
   			}
   		}
	}
	function getSelectCount() {
		var ids="";
		document.getElementById("paramTyprIds").value="";
		var chks = document.getElementsByName('checkbox');
		var j = 0;
		for ( var i = 0; i < chks.length; i++) {
			if (chks[i].checked){
				j++;
				ids+=chks[i].value+",";
			}
		}
		document.getElementById("paramTyprIds").value=ids;
		return j;
	}
	function doDeleteItems() {
		if (getSelectCount() < 1) {
			alert("请选择需要删除的数据！");
		} else if (confirm("数据删除后不可以恢复，您确定要删除吗？")) {
			getSelectCount();
			var paramTyprIds=document.getElementById("paramTyprIds").value;
			checkSub(paramTyprIds);
		}
	}
	function toAddParameterType(){
		window.location.href="<%=basePath%>sys/paramtype/addParameterType";
	}
	function toModifyParameterType(parameterID){
		window.location.href='<%=basePath%>sys/paramtype/toModifyParameterType/'+parameterID+'?'+Math.random();
	}
	function checkSub(parameterTypeIds){
		$.post("<%=basePath%>/sys/paramtype/checkSub", {
			"parameterTypeIds" :parameterTypeIds
			}, function(data) {
			if (data == "0"){
				form.action = '<%=basePath%>sys/paramtype/deleteParameterType';
				form.submit();
			}
			if(data=="1"){
				alert("对不起！你删除的参数类型下面有参数，请删除参数后再进行删除");
			}
			if(data=="2"){
				alert("删除出现错误");
			}
		});
	}
	
	//重置
	//function cleanValue(){
	    //document.getElementById("parameterTypeName").value="";
	    //document.getElementById("parameterTypeCode").value="";
	//}
	
	
	
	//单一删除模块
	function delParameterType(parameterTypeId){
	 if (confirm("数据删除后不可以恢复，您确定要删除吗？")){
	     checkSub(parameterTypeId);
	     window.location.href = '<%=basePath%>sys/paramtype/deleteParameterTypeById/'+parameterTypeId+'?'+Math.random();
	  }
	}
	
	//查看
	function parameterTypeDetail(parameterTypeId){
	  window.location.href = '<%=basePath%>sys/paramtype/parameterTypeDetail/'+parameterTypeId+'?'+Math.random();
	}
	function clean(){
		document.getElementById("parameterTypeName").value=""
	
}
	</script>
	</body>
</html>