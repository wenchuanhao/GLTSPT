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
		jQuery("#logModuleType").val("");
		jQuery("#userId").val("");
		jQuery("#logStartTime").val("");
		jQuery("#logEndTime").val("");
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
		<form method="post" name="pageForm" id="pageForm" action="">
			<input type="hidden" id="ParIds" name="ParIds" />
			<div class="gl_m_r_nav">
				当前位置 : 系统管理 &gt; 用户日记 &gt; 用户操作日志
			</div>
			<div class="gl_import_m">
				<div class="gl_bt_bnt01">
					查询
				</div>
				<table class="gl_table_a01" width="100%" border="0" cellspacing="0"	cellpadding="0">
					<tr>
						<th width="100">
							操作帐号:
						</th>
						<td>
							<input class="gl_text01_a" type="text" name="userId" id="userId" value="${form.userId}" />
						</td>
						<th width="100">
							菜单名称:
						</th>
						<td>
							<input class="gl_text01_a" type="text" name="logModuleType"	id="logModuleType" value="${form.logModuleType}" />
						</td>
					</tr>
					<tr>
						<th width="100">
							开始时间:
						</th>
						<td>
							<input readonly="readonly" class="gl_text01_a" type="text" name="logStartTime" id="logStartTime" value="${form.logStartTime}" onfocus="WdatePicker({logStartTime:'%y-%M-01',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"/>
						</td>
						<th width="100">
							结束时间:
						</th>
						<td>
							<input readonly="readonly" class="gl_text01_a" type="text" name="logEndTime" id="logEndTime" value="${form.logEndTime}" onfocus="WdatePicker({logEndTime:'%y-%M-01',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" onchange="YZtime();"/>
						</td>
					</tr>
				</table>
				<div class="gl_ipt_03">
					<input type="button" class="gl_cx_bnt03" value="查 询" onclick="doSearch()" />
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
						<th width="9%">
							操作帐号
						</th>
						<th width="20%">
							菜单名称
						</th>
						<th width="8%">
							相关操作
						</th>
						<th width="40%">
							操作描述
						</th>
						<th width="10%">
							操作IP
						</th>
						<th width="13%">
							操作时间
						</th>
					</tr>
					<c:forEach items="${ITEMPAGE.items}" var="log" varStatus="i">
						<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if>>
							<td>
								${log.userId}
							</td>
							<td style="word-break:break-all;text-align:left;padding:3px 8px 3px 8px;line-height:19px;">
								${log.logModuleType}
							</td>
							<td>
								${log.logModuleNote}
							</td>
							<td style="word-break:break-all;text-align:left;padding:3px 8px 3px 8px;line-height:19px;">
								${log.logDesc}
							</td>
							<td>
								${log.operaterIP}
							</td>
							<td>
								<fmt:formatDate value="${log.operateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
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