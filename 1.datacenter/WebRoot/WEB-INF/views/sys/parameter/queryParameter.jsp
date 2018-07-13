<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
		action="<%=basePath%>sys/parameter/queryParameter">
		<input type="hidden" id="ParIds" name="ParIds" />
		<div class="gl_m_r_nav">当前位置 : 系统管理 &gt; 系统参数 &gt; 管理参数</div>
		<table class="gl_m_r_n_tb01" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td valign="top" class="gl_m_r_n_tb01_m"></td>
				<td valign="top">
					<div class="gl_bt_bnt01">
						<input name="" type="button" class="gl_cx_bnt01" id="setDivs"
							value="收起查询" onclick="setDiv();" /> 查询
					</div>
					<div id="serachDisplay" style="display:block;" >

						<table id="queryDiv" class="gl_table_a01" order="0" cellspacing="0" cellpadding="0"
							style="width:100%;">
							<tr>
								<th>参数类型名称:</th>
								<td><input class="gl_text01_a" 
									type="text" name="parameterTypeName" id="parameterTypeName" style="width:100%;"
									value="${form.parameterTypeName}" /></td>
								<th>参数名称:</th>
								<td><input class="gl_text01_a" 
									type="text" name="parameterName" id="parameterName" style="width:100%;"
									value="${form.parameterName}" /></td>
							</tr>
							<tr>
								<th>参数编码:</th>
								<td><input class="gl_text01_a"
									type="text" name="parameterCode" id="parameterCode" style="width:100%;"
									value="${form.parameterCode}" /></td>
								<th >参数值:</th>
								<td><input class="gl_text01_a"
									type="text" name="parameterValue" id="parameterValue" style="width:100%;"
									value="${form.parameterValue}" /></td>
							</tr>
						</table>
						<div id="serchBox" class="gl_ipt_03">
							<input id="img" name="" type="button" class="gl_cx_bnt03"
								value="查 询" onclick="javascript:doSubmit();" />&nbsp; <input
								name="input" type="button" class="gl_cx_bnt03" value="重 置"
								onclick="clean();" />
						</div>
					</div>
					<div class="ge_a01"></div>

					<div class="gl_bt_bnt01">参数列表</div>

					<div class="gl_bnt_nav01" style="border-bottom:none;">
						<input name="" type="button" class="gl_cx_bnt04" value="新 增"
							onclick="toAddParameter();" /> <input name="" type="button"
							class="gl_cx_bnt04" value="删 除" onclick="doDeleteItems();" />
					</div>

					<table class="gl_table_a02" width="100%" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
							<th width="3%"><input type="checkbox" class="myClass"
								id="checkAll" name="checkAll" onclick="selectAll();" />
							</th>
							<th width="18%">参数类型名称</th>
							<th width="18%">参数名称</th>
							<th width="18%">参数编码</th>
							<th width="18%">参数值</th>
							<th width="8%">是否允许修改</th>
							<th width="19%">操作</th>
						</tr>
						<c:forEach items="${ITEMPAGE.items}" var="parameter" varStatus="i">
							<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if>>
								<td><input type="checkbox" class="myClass" name="checkbox"
									id="checkbox" value="${parameter[0].parameterId}" />
								</td>
								<td>${parameter[1].parameterTypeName}</td>
								<td>${parameter[0].parameterName}</td>
								<td>${parameter[0].parameterCode}</td>
								<td style="word-break:break-all">${parameter[0].parameterValue}</td>
								<td><c:choose>
										<c:when test='${parameter[0].allowUpdate == "1"}'>
				  				允许
				  			</c:when>
										<c:otherwise>
				  				不允许
				  			</c:otherwise>
									</c:choose></td>
								<td align="center"><span class="gl_tab_tr_l"> <img
										src="/SRMC/rmpb/images/tab_tb01.png" align="middle" />
								</span> <span class="gl_tab_tr_r"><a
										href="javascript:parameterDetail('${parameter[0].parameterId }');">查看</a>
								</span> <c:if test='${parameter[0].allowUpdate == "1"}'>
										<span class="gl_tab_tr_l"><img
											src="/SRMC/rmpb/images/tab_tb06.png" align="middle" />
										</span>
										<span class="gl_tab_tr_r"><a
											href="javascript:toModifyParameter('${parameter[0].parameterId }');">编辑</a>
										</span>
										<span class="gl_tab_tr_l"><img
											src="/SRMC/rmpb/images/tab_tb07.png" align="middle" />
										</span>
										<span class="gl_tab_tr_r"><a
											href="javascript:delParameter('${parameter[0].parameterId }');">删除</a>
										</span>
								</td>
								</c:if>
								<c:if test='${parameter[0].allowUpdate != "1"}'>
						 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</c:if>
							</tr>
						</c:forEach>
					</table>

					<div class="pageBox">
						<jsp:include flush="true" page="/public/include/navigate2.jsp"></jsp:include>
					</div></td>
			</tr>
		</table>
	</form>

	<!--</form>-->
	<script type="text/javascript" src="/SRMC/rmpb/js/list-common.js"></script>
	<script type="text/javascript">
   function doSubmit(){
	   document.all("pageIndex").value = "1";
	   document.forms[0].submit();
   }
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
		document.getElementById("ParIds").value="";
		var chks = document.getElementsByName('checkbox');
		var j = 0;
		for ( var i = 0; i < chks.length; i++) {
			if (chks[i].checked){
				j++;
				ids+=chks[i].value+",";
			}
		}
		document.getElementById("ParIds").value=ids;
		return j;
	}
	function doDeleteItems() {
		if (getSelectCount() < 1) {
			alert("请选择需要删除的数据！");
		} else if (confirm("数据删除后不可以恢复，您确定要删除吗？")) {
			form.action = '<%=basePath%>sys/parameter/deleteParameter';
			form.submit();
		}
	}
	function toAddParameter(){
		window.location.href="<%=basePath%>sys/parameter/addParameter";
	}
	function toModifyParameter(parameterID){
		window.location.href='<%=basePath%>sys/parameter/toModifyParameter/'+parameterID+'?'+Math.random();
	}
	
	//单一删除模块
	function delParameter(parameterId){
	   if (confirm("数据删除后不可以恢复，您确定要删除吗？")){
	      window.location.href = '<%=basePath%>sys/parameter/deleteParameterById/'+parameterId+'?'+Math.random();
	   }
	}
	
	//查看
	function parameterDetail(parameterId){
	  window.location.href = '<%=basePath%>sys/parameter/parameterDetail/'+parameterId+'?'+Math.random();
	}
	function clean(){
		document.getElementById("parameterName").value="";
		document.getElementById("parameterTypeName").value="";
		document.getElementById("parameterCode").value="";
		document.getElementById("parameterValue").value="";
		
	}
	</script>
</body>
</html>