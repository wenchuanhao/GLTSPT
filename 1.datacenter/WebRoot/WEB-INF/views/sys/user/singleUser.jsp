<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



	<c:if test="${isN=='n'}">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr align="center">
						<th width="100">
							<font color="" size="3">抱歉!无相关用户信息</font>
						</th>
					</tr>
				</table>
			</c:if>

	<c:if test="${isN=='y'}">
<table class="gl_table_a01_6L" width="100%" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<th width="100">
							用户登录帐号：
						</th>
						 
						<td>
							${user.account}
						</td>
						<!--
						<th width="100">
							登录密码：
						</th>
						<td>
							${user.password}
							<input type="hidden" value="${user.userId}" name="userId" />
						</td>
						 -->
						<th width="100">
							用户姓名：
						</th>
						<td>
							${user.userName}
						</td>
					</tr>
					<tr>
						<th width="100">
							用户手机号码：
						</th>
						<td>
							${user.mobile}
						</td>
						<th width="100">
							用户邮箱：
						</th>
						<td>
							${user.email}
						</td>
						<!-- 
						<th width="100">
							默认角色：
						</th>
						<td>
							${ro}
						</td>
						 -->
					</tr>
					<tr>
					  <!-- 
						<th width="100">
							是否接受SMS：
						</th>
						<td>
							${js}
						</td>
						<th width="100">
							用户状态：
						</th>
						<td>
							${zt}
						</td>
						-->
						<th width="100">
							用户所属组织：
						</th>
						<td colspan="3">
							${orga}
						</td>
					</tr>
				</table>
	</c:if>