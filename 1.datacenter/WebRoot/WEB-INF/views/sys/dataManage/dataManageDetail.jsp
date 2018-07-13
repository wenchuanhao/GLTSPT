<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新增数据获取</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<!--<script type="text/javascript" src="/SRMC/rmpb/js/test.js"></script>-->
</head>
<script type="text/javascript">
		function canels(){
			 window.location.href = "<%=basePath%>sys/data/dataManage";
		}
</script>
<body class="bg_c_g" >
	<form action=""  method="post" name="moduleForm">
	  <div class="gl_m_r_nav">当前位置 : 系统管理 &gt;数据获取管理  &gt; 数据获取详情</div>
	 <div class="gl_import_m">
           <div class="gl_bt_bnt01">数据信息</div>
            <table class="gl_table_a023" width="100%" border="0" cellspacing="0" cellpadding="0">
            <input type="hidden" id="id" name="id" value="${list[0].id }"/>
               <tr>
                 <th width="100">服务端系统名称:</th>
                 <td >
                 ${list[0].sysName }
				 </td>
                 <th width="100">接入的接口类型:</th>
                 <td>
	                <c:if test="${list[0].type=='0' }">ws接口</c:if>
	                <c:if test="${list[0].type=='1' }">文件接口</c:if>
	                <c:if test="${list[0].type=='2' }">sql接口</c:if>
			 	</td>
               </tr>
               <tr>
                 <th>获取频率:
                </th>
                 <td>
                 ${list[0].frequency }
                 </td>
               </tr>
               <tr>
					<th width="100">
						备注:
					</th>
					<td colspan="6" >
						<textarea name="remark" id=remark class="gl_text01_c" readonly="readonly">${list[0].remark }</textarea>
					</td>
				</tr>
             </table>
        
            <div class="gl_ipt_03">
		        <input name="input" type="button" class="gl_cx_bnt03" value="取 消" onclick="canels();"/>
            </div>
        </div>
	</form>	
</body>
</html>
