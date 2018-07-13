<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>短信发送日志</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
	function doSearch(){
		document.all("pageIndex").value = "1";
		document.forms[0].action="<%=basePath%>sys/sms/smsManageLog"+"?key="+Math.random();
		document.forms[0].submit();
	}
	
	function doReset(){
		jQuery("#mobile").val("");
		jQuery("#createTime").val("");
		window.location.href="<%=basePath%>sys/sms/smsManageLog";
	}
</script>
</head>

<body class="bg_c_g">
	<form  method="post" id="pageForm" name="pageForm">
		<input type="hidden" id="ParIds" name="ParIds" />
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 &gt; 短信管理 &gt; 短信发送日志
		</div>
		<div class="gl_import_m">
			<div class="gl_bt_bnt01">
				查询
			</div>
			<table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="100">
						创建时间:
					</th>
					<td>
						<input class="gl_text01_a" type="text" name="createTime" id="createTime" value="${form.createTime}" onfocus="WdatePicker({logStartTime:'%y-%M-01',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" />
					</td>
					<th width="100">
						手机号码:
					</th>
					<td>
						<input class="gl_text01_a" type="text" name="mobile" id="mobile" value="${form.mobile}" />
					</td>
				</tr>
				</tr>
			</table>
			<div class="gl_ipt_03">
				<input type="button" class="gl_cx_bnt03" value="查 询" onclick="doSearch();" />
				&nbsp;
				<input type="button" class="gl_cx_bnt03" value="重 置" onclick="doReset();"/>
			</div>
			<div class="ge_a01"></div>

			<div class="gl_bt_bnt01">
				发送列表
			</div>
			<table class="gl_table_a02" width="100%" border="0" cellspacing="0"	cellpadding="0">
				<tr>
				    <th>
						序号
					</th>
					<th>
						创建时间
					</th>
					<th>
						手机号码
					</th>
					<th>
						短信内容
					</th>
					<th>
						服务标识
					</th>
					<th>
						返回代码
					</th>
				</tr>
				<c:forEach items="${ITEMPAGE.items}" var="log" varStatus="i">
					<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if>>
					    <td>
					       ${i.count}
					    </td>
						<td>
							<fmt:formatDate value="${log.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${log.mobile}
						</td>
						<td>
							${log.content}
						</td>
						<td>
					    <c:if test="${log.serviceFlag =='Y' }">成功</c:if>
						<c:if test="${log.serviceFlag !='Y' }">失败</c:if>
						</td>
						<td>
						<c:if test="${log.returnCode =='0' }">发送成功</c:if>
						<c:if test="${log.returnCode =='1' }">接口鉴权失败</c:if>
						<c:if test="${log.returnCode =='2' }">参数输入错误</c:if>
						<c:if test="${log.returnCode =='3' }">已达到当前端口发送量限制</c:if>
						<c:if test="${log.returnCode =='4' }">内容含非法关键字，已被过滤</c:if>
						<c:if test="${log.returnCode =='5' }">系统内部错误</c:if>						
						</td>
					</tr>
				</c:forEach>
			</table>

			<div class="pageBox">
				<jsp:include flush="true" page="/public/include/navigate2.jsp"></jsp:include>
			</div>

		</div>

	</form>
</body>

</html>