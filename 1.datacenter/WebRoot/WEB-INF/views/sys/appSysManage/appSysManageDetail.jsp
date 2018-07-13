<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>管理模块</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
 <!--<script type="text/javascript" src="/SRMC/rmpb/js/test.js"></script>-->
</head>
<body class="bg_c_g">
	<form action="" method="post" name="moduleForm">
		<input type="hidden" name="moduleId" id="moduleId"  value=""/>
		<input type="hidden" name="type" id="type"  value="${type }"/>
	  <div class="gl_m_r_nav">当前位置 : 系统管理 &gt; 应用系统管理 &gt; 应用系统详情</div>
	  <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
	    <tr>
	      <td  valign="top" class="gl_m_r_n_tb01_m"></td>
          <td valign="top">
          
           <div class="gl_bt_bnt01">应用系统信息</div>
            <table class="gl_table_a01_6L" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <th width="100">系统编码:</th>
                 <td>
                 ${list[0].code }
				 </td>
                 <th width="100">系统名称:</th>
                 <td>
                 <div id="parentModule"  >
                 ${list[0].name }
                 </div>
		 		</td>
                 <th width="100">接入的接口类型:</th>
                 <td>
				 <c:if test="${list[0].type=='0'}">ws接口 </c:if>
				 <c:if test="${list[0].type=='1'}">文件接口 </c:if>
				 <c:if test="${list[0].type=='2'}">sql接口 </c:if>
				 </td>
               </tr>
               <tr>
                 <th>数据分域:</th>
                 <td>
               <c:if test="${list[0].domain=='0'}">成本类 </c:if>
				 <c:if test="${list[0].domain=='1'}">收入类 </c:if>
				 <c:if test="${list[0].domain=='2'}">预算类 </c:if>
				 <c:if test="${list[0].domain=='3'}">服务量 </c:if>
				 <c:if test="${list[0].domain=='4'}">专题类 </c:if>
				 <c:if test="${list[0].domain=='5'}">指标类 </c:if>
                 </td>
                 <th>应用管理员名称:</th>
                 <td>
                  ${list[0].adminName }
                 </td>
                 <th>应用管理员电话:</th>
                 <td>
                  ${list[0].adminPhone }
				</td>
               </tr>
               <tr>
                 <th>应用管理员邮箱:</th>
                 <td >${list[0].adminEmail }</td>
		<td colspan="4"></td>
                </tr>
                <tr>
                <th width="100">
						备注:
				</th>
				<td colspan="5" >
						${list[0].remark}
					</td>
				</tr>
             </table>
            <div class="gl_ipt_03">
                <input name="input" type="button" class="gl_cx_bnt03" value="返 回" onclick="doReturn()"/>
            </div>
          </td>
	    </tr>
     </table>
	</form>	
	<script type="text/javascript">
		
	</script>
	<script type="text/javascript">		
		 function doReturn(location){
           	window.location.href = "<%=basePath%>/sys/app/appSysManage";
        }
	   </script>
</body>
</html>
