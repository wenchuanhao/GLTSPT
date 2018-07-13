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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查询角色</title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/rmpb/js/Checkable.js" type="text/javascript"></script>
<script type="text/javascript">
   function doSubmit(){
	   document.forms[0].submit();
	}
   </script>

<script type="text/javascript">


</script>
</head>

<body class="bg_c_g">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 服务器监控>服务器监控</div>
           <table class="gl_m_r_n_tb01"  border="0"  width="1500PX" style="overflow: auto;" cellspacing="0" cellpadding="0">
             <tr>
             <td valign="top"> 
               <form name="form"  id="pageForm" method="post" action="<%=basePath%>sys/monitor/sysMonitor">
                <div id="queryDiv" style="display: block;">
               <div id="serchBox" class="gl_ipt_03">
					<!-- <input id="img" name="" type="submit" class="gl_cx_bnt03" value="查 询" />&nbsp; -->
                 <input id="img" name="" type="button"  class="gl_cx_bnt03" value="刷新" onclick="javascript:doSubmit();"/>&nbsp;
				</div>
				</div>
               <!-- </form> -->
              
               <div class="ge_a01"></div>
               
               <div class="gl_bt_bnt01">服务器性能监控</div>
               <table class="gl_table_a02"   border="0" cellspacing="0" cellpadding="0" style="overflow: auto;table-layout: fixed;" >
                <tr>
                <th width="70px">CPU使用率</th>
                <th width="70px">内存总量</th>
                <th width="70px">内存使用</th>
                <th width="70px">内存剩余</th>
                <th width="70px">内存使用百分比</th>
                <th width="70px">操作系统</th>
                <th width="70px">系统版本号</th>
                <c:forEach items="${monitor2 }" var="list" >
                <th colspan="4" width="320px">${list.dir }盘</th>
                </c:forEach>
                </tr>
                <tr>
                  <td>${monitor.cpuUsed }</td>
                  <td>${monitor.menSum }</td>
                  <td>${monitor.menUsed}</td>
                  <td>${monitor.menFree }</td>
                  <td>${monitor.menUsePercent }</td>
                  <td>${monitor.operaSystem }</td>
                  <td>${monitor.version }</td>
				  <c:forEach items="${monitor2 }" var="list" >
                  <td>${list.diskFree }</td>
                  <td>${list.diskSum }</td>
                  <td>${list.diskUsed }</td>
                  <td>${list.diskUsePercent }</td>
                  </c:forEach>
                </tr>
               </table>
               
				</form>
               <!--  </td>
             </tr>
           </table> -->
</body>
<script type="text/javascript" src="/SRMC/rmpb/js/list-common.js"></script>
</html>