<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<!--  -->
<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
	<!--公共JS -->
	<script src="/SRMC/dc/smsManage/js/common.js"></script>
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />	



<script type="text/javascript" >
	function doSearch(){
		document.all("pageIndex").value = "1";
		document.forms[0].submit();
	}
	
	function doReset(){
		jQuery("#userId").val("");
		jQuery("#userName").val("");
		jQuery("#logStartTime").val("");
		jQuery("#operaterIP").val("");
	}
	
function aa(id) {
    jQuery("#xxx"+id).attr("href", "#"+id );
    /*弹出框js*/
        $("#xxx"+id).fancybox({                   
            'autoDimensions': false,
            'centerOnScroll': true,
            'scrolling': 'yes',
            'width': '70%',
            'height': 350           
        });
        
}
		
</script>
</head>
	<body class="bg_c_g">
		<form method="post" name="pageForm" id="pageForm" action="">
			<input type="hidden" id="ParIds" name="ParIds" />
			<div class="gl_m_r_nav">
				当前位置 : 系统管理 &gt; 用户日记 &gt; 用户异常日志
			</div>
			<div class="gl_import_m">
				<div class="gl_bt_bnt01">
					查询
				</div>
				<table class="gl_table_a01" width="100%" border="0" cellspacing="0"	cellpadding="0">
					<tr>
						<th width="100">
							登录id:
						</th>
						<td>
							<input class="gl_text01_a" type="text" name="userId"	id="userId" value="${form.userId}" />
						</td>
						<th width="100">
							登录名:
						</th>
						<td>
							<input class="gl_text01_a" type="text" name="userName" id="userName" value="${form.userName}" />
						</td>
					</tr>
					<tr>
						<th width="100">
							操作时间:
						</th>
						<td>
							<input readonly="readonly" class="gl_text01_a" type="text" name="logStartTime" id="logStartTime" value="${form.logStartTime}" onfocus="WdatePicker({logStartTime:'%y-%M-01',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"/>
						</td>
						<th width="100">
							请求IP:
						</th>
						<td>
							<input class="gl_text01_a" type="text" name="operaterIP" id="operaterIP" value="${form.operaterIP}" />
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
							登录id
						</th>
						<th>
							请求URI
						</th>
						<th>
							ip地址
						</th>
						<th>
							异常描述
						</th>

						<th>
							操作时间
						</th>
						<th>
							详细信息
						</th>
					</tr>
					<c:forEach items="${ITEMPAGE.items}" var="log" varStatus="i">
						<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if>>
						   	<td>
								${log.userId}
							</td>
							<td>
								${log.logModuleType}
							</td>
							<td>
								${log.operaterIP}
							</td>
							<td>
							 ${log.logModuleNote !=''? fn:substring(log.logModuleNote,0, 100):'未知异常'}							
							<td>
								<fmt:formatDate value="${log.operateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							
							<td>
							 <a id="xxx${i.count}"  ><span onclick="javascript:aa('${i.count}');">查看异常详细信息</span></a>
							  <div style="display: none;">
                                 <pre id="${i.count}">${log.logDesc}</pre>
                              </div>
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
