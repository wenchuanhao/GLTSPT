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
<title>用户日记</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
	function doSearch(){
		document.all("pageIndex").value = "1";
		document.forms[0].submit();
	}
	
	function doReset(){
		jQuery("#createUserid").val("");
		jQuery("#semail").val("");
		jQuery("#logStartTime").val("");
		jQuery("#logEndTime").val("");
		window.location.href="<%=basePath%>sys/log/emailSendedLog";
	}
	function YZtime(){
		var logStartTime=jQuery("#logStartTime").val();
		var logEndTime=jQuery("#logEndTime").val();
		if(logStartTime!=null && logStartTime!='' && logEndTime!=null && logEndTime!=''){
	    	if(logStartTime>logEndTime){
			    	alert("结束时间应大于开始时间，请重新选择！");
			    	return false;
	    	}
            }
	}
</script>
</head>
	<body class="bg_c_g">
		<form action="<%=basePath%>sys/log/emailSendedLog" method="post" name="pageForm" id="pageForm"  >
			<input type="hidden" value="N" name="isPages" id="isPages"/>
			<input type="hidden" value="${form.pageIndex}" id="pageIndex" name="pageIndex"/>
    		<input type="hidden" value="${form.pageSize}" id="pageSize"	name="pageSize"/>	
			<input type="hidden" id="ParIds" name="ParIds" />
			<div class="gl_m_r_nav">
				当前位置 : 系统管理 &gt; 用户日志 &gt; 邮件发送历史查询
			</div>
			<div class="gl_import_m">
				<div class="gl_bt_bnt01">
					查询
				</div>
				<table class="gl_table_a01" width="100%" border="0" cellspacing="0"	cellpadding="0">
					<tr>
						<th width="150" style="width:150px;" >
							 用户ID :
						</th>
						<td>
							<input class="gl_text01_a" type="text" name="createUserid" id="createUserid" value="${form.createUserid}" />
						</td>
						<th width="150" style="width:150px;" >
							邮件地址:
						</th>
						<td>
							<input class="gl_text01_a" type="text" name="semail" id="semail" value="${form.semail}" />
						</td>
						
					</tr>
					<tr>
						<th width="100">
							开始时间:
						</th>
						<td>
							<input readonly="readonly" class="gl_text01_a" name="logStartTime" id="logStartTime" 
							value="<fmt:formatDate value='${form.logStartTime}' pattern='yyyy-MM-dd' />" 
							onfocus="WdatePicker({startDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'logEndTime\')}',dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<th width="100">
							结束时间:
						</th>
						<td>
							<input readonly="readonly" class="gl_text01_a" name="logEndTime" id="logEndTime" 
							value="<fmt:formatDate value='${form.logEndTime}' pattern='yyyy-MM-dd' />"
							onfocus="WdatePicker({startDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'logStartTime\')}',dateFmt:'yyyy-MM-dd'})" onchange="YZtime();"/>
						</td>
					</tr>
				</table>
				<div class="gl_ipt_03">
					<input type="button" class="gl_cx_bnt03" value="查 询" onclick="doSearch();" />
					&nbsp;
					<input type="button" class="gl_cx_bnt03" value="重 置" onclick="doReset();"/>
				</div>
				<div class="ge_a01"></div>

				<div class="gl_bt_bnt01">
					日记列表
				</div>
				<!-- 
                <div class="gl_bnt_nav01" style="border-bottom:none;">
                 <input name="" type="button" class="gl_cx_bnt04" value="查 看" onclick=""/>
               </div>
              -->
				<table class="gl_table_a02" width="100%" border="0" cellspacing="0"	cellpadding="0">
					<tr>
						<th>
							用户ID
						</th>
						<th>
							创建日期
						</th>
						<th>
							邮件内容
						</th>
						<th>
							邮件地址
						</th>
						<th>
							服务标识
						</th>
						<th>
							返回信息
						</th>
					</tr>
					<c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
						<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if>>
							<td>
								<%--用户ID--%>
								${item.createUserid}
							</td>
							<td>
								<%--创建日期--%>
								<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>
								<%--邮件内容--%>
								${item.content}
							</td>
							<td>
								<%--邮件地址--%>
								${item.semail}
							</td>
							<td>
								<%--服务标识--%>
								<c:if test="${item.serviceFlag =='Y' }">成功</c:if>
								<c:if test="${item.serviceFlag !='Y' }">失败</c:if>
							</td>
							<td>
								<%--返回信息--%>
								<c:if test="${item.returnCode =='0' }">发送成功</c:if>
								<c:if test="${item.returnCode =='-1' }">接口鉴权失败</c:if>
								<c:if test="${item.returnCode =='-2' }">邮件类型[TYPE]不能为空</c:if>
								<c:if test="${item.returnCode =='-3' }">已达到当前端口发送量限制</c:if>
								<c:if test="${item.returnCode =='-4' }">内容含非法关键字，已被过滤</c:if>
								<c:if test="${item.returnCode =='-5' }">服务接口内部错误</c:if>
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