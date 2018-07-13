<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户信息详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript">
		   function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		        }
		
		</script>
	</head>
	<body class="bg_c_g">
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 > 用户管理 > 用户信息详情
		</div>
		<table class="gl_m_r_n_tb01" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td valign="top">
					<div class="gl_bt_bnt01">
						用户信息详情
					</div>
					<!-- 
               <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <th width="100" >用户登录帐号：</th>
                   <td>${user.account}</td>
                   <th width="100">登录密码：</th>
                   <td>${user.password}
                   <input type="hidden" value="${user.userId}" name="userId"/>
                   </td>
                   
                 </tr>
                 <tr>
                   <th width="100">用户姓名：</th>
                   <td>${user.userName}</td>
                   <th width="100">用户手机号：</th>
                   <td>${user.mobile}</td>
                 </tr>
                <tr>
                   <th width="100">用户邮箱：</th>
                   <td>${user.email}</td>
                   <th width="100">默认角色：</th>
                   <td>
                     ${ro}
                    </td>
                 </tr>
                  <tr>
                   <th width="100">是否接受SMS：</th>
                   <td>
                       ${js}
				   </td>
                   <th width="100">用户状态：</th>
                   <td>
                      ${zt}</td>
                 </tr>
                   <tr>
                   <th width="100">用户所属组织：</th>
                   <td colspan="3">
                       ${orga}
				   </td>
                  
                 </tr>
               </table>
                -->
					<table class="gl_table_a01" width="100%" border="0"
						cellspacing="0" cellpadding="0">
						<tr>
							<th width="14%">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户帐号：
							</th>
							<td width="18%">
								${user.account}
							</td>
							<th width="14%">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户姓名：
							</th>
							<td width="18%">
								${user.userName}
							</td>
                            <th width="14%">
                                用户手机号码：
                            </th>
                            <td width="18%">
                                ${user.mobile}
                            </td>
						</tr>
						<tr>
							<th >
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户邮箱：
							</th>
							<td>
								${user.email}
							</td>
							<th >
							用户默认角色：
							</th>
							<td>
								${ro}
							</td>
                            <th >
                                是否接受SMS：
                            </th>
                            <td>
                                ${js}
                            </td>
						</tr>
						<tr>
							<th >
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账户状态：
							</th>
							<td>
								${zt}
							</td>
						 	<th >
                                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用状态：
                            </th>
                            <td>
                                <c:choose>
                                    <c:when test="${user.isDongjie}">
                                        冻结
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test='${user.freezeStatus == "0"}'>正常</c:if>
                                        <c:if test='${user.freezeStatus == "2"}'>禁用</c:if>
                                        <c:if test='${user.freezeStatus == "1"}'>待激活</c:if>
                                    </c:otherwise>
                                </c:choose>
                            </td>						
                            <th >
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建人：
                            </th>
                            <td>
                                ${createPerson}
                            </td>							
                          	
                            
						</tr>
						<tr>
						  <th >
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建时间：
							</th>
							<td>
								<fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>						
						  <th >
                                                                             最后登录时间：
                            </th>
                            <td colspan="3">
                                <fmt:formatDate value="${lastLogined.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        
						</tr> 
						<tr>
						   <th >
									用户所属组织：
								</th>
								<td colspan="5">
									${orga}
							</td>
						
						</tr>       
                            
					
                           
					</table>
					<div class="gl_ipt_03">
						<input name="input" type="button" class="gl_cx_bnt03" value="返回"
						onclick="window.history.back();" />
<!-- 							onclick="doReturn('${location}')" /> -->
							<%-- value="返回" onclick="history.go(-1);"  /> --%>
					</div>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
		function  doAaddOrg(){
			orgForm.submit();
		}
	</script>
	</body>
</html>
