<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link href="/SRMC/rmpb/css/left_a01.css" rel="stylesheet" type="text/css" />
<title></title>
</head>
<body>
<div class="mm_main_top01">
    		<span class="mm_main_top01a">
    			<img src="/SRMC/rmpb/images/005.png" width="33" height="32" />
    		</span>
    		<span class="mm_main_top01c">当前位置：系统管理 &gt; 组织机构管理 &gt;<span class="or">组织机构列表</span></span>
  </div>
       <div class="ge01"></div>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
	      
	        <tr>
	        		<td width="17%" valign="top">
	        		 
								<table width="98%" border="0" align="center" cellpadding="0"
									cellspacing="0" class="main_table05">
									<tr>
										<td align="center">
											<iframe id="typical" name="typical"
												style="height: 450px; width: 100%; visibility: inherit;"
												scrolling="auto" frameborder="0" marginwidth="0"
												marginheight="0" src="<%=basePath%>sys/org/queryOrgTree"></iframe>
										</td>
									</tr>
								</table>
				</td>
				<td width="83%">
						<table width="98%" border="0" align="center" cellpadding="0"
									cellspacing="0" >
									<tr>
										<td align="center">
											<iframe id="queryOrg" name="queryOrg"
												style="height: 450px; width: 100%; visibility: inherit;"
												scrolling="auto" frameborder="0" marginwidth="0"
												marginheight="0" src="<%=basePath%>sys/org/queryOrg"></iframe>
										</td>
									</tr>
								</table>
				</td>
	        </tr>
	      	</table>
	<div class="ge01"></div>
</body>
</html>
