<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dtree.css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>

<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/js.js"></script>
<script src="/SRMC/rmpb/js/Checkable.js" type="text/javascript"></script>
<script src="/SRMC/rmpb/js/dtree.js" type=text/javascript></script>
</head>

<body class="bg_c_g">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 组织管理 > 管理组织</div>
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
             <td width="260" valign="top">
               
               <div class="gl_bt_bnt01">组织架构</div>
              
               <div class="gl_bnt_tree01">
                 <div class="gl_bnt_tree02">
                   <table  class="gl_bnt_tree03" border="0" cellspacing="0" cellpadding="0">
                     <tr>
                     <td><a href="#"><img src="/SRMC/rmpb/images/gl_tab_b01.gif" width="25" height="25" /></a></td>
                     <td><a href="#"><img src="/SRMC/rmpb/images/gl_tab_b02.gif" width="25" height="25" /></a></td>
                     <td width="30"></td>
                     <td><a href="#"><img src="/SRMC/rmpb/images/gl_tab_b03.gif" width="25" height="25" /></a></td>
                     <td><a href="#"><img src="/SRMC/rmpb/images/gl_tab_b04.gif" width="25" height="25" /></a></td>
                     </tr>
                   </table>
                 </div>
                 <!--tree-->
 					<div class="d_tree_div">
           <script type="text/javascript">
		d = new dTree('d');
				<c:forEach items="${orgs}" var="org">
					<c:choose>
					<c:when test="${org.orgName == 'ROOT'}">
							d.add('${org.organizationId}', '-1', "<font oncontextmenu=loadMenu('${org.organizationId}') color=#EF5900><b>公司组织架构</b></font>", '<%=basePath%>sys/org/queryOrg?parentId=${org.organizationId}&index=Math.random();', '', 'queryOrg', '','//');
						</c:when>
						<c:otherwise>
							d.add('${org.organizationId}','${org.parentId}', "<font oncontextmenu=loadMenu('${org.organizationId}')>${org.orgName}</font>", '<%=basePath%>sys/org/queryOrg?parentId=${org.organizationId}&index=Math.random();', '', 'queryOrg');
						</c:otherwise>
					</c:choose>	
				</c:forEach>
			document.write(d);
		
	</script>
    </div>
<!--tree END-->                 
               </div>
             </td>
             <td width="18" valign="top" class="gl_m_r_n_tb01_m"></td>
             <td valign="top">
             
               <div class="gl_bt_bnt02">
               <input name="" type="button" class="gl_cx_bnt02" value="默认项目" />
               <input name="" type="button" class="gl_cx_bnt01b" value="收起查询" />
               查询</div>
               
               <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <td width="480">组织名称：
                   <input class="gl_text01b" type="text" name="textfield" id="textfield" />
                   
                 </td>
                 <td><input name="" type="button" class="gl_cx_bnt01c" value="搜 索" /></td>
                 </tr>
               </table>
               <div class="ge_a01"></div>
               
               <div class="gl_bt_bnt01">组织机构列表</div>
               
               <div class="gl_bnt_nav01" style="border-bottom:none;">
                 <input name="" type="button" class="gl_cx_bnt04" value="新 增" />
                 <input name="" type="button" class="gl_cx_bnt04" value="删 除" />
               </div>
             
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <th width="32"><input type="checkbox" class="myClass" value="yes" id="Checkbox1" name="answer"/></th>
                <th>组织名称</th>
                <th>上级组织</th>
                <th width="60">排序</th>
                <th width="160">操作</th>
                </tr>
                <c:forEach items="${ITEMPAGE.items}" var="org" varStatus="i">
                <tr>
                  <td><input type="checkbox" class="myClass" value="yes" id="Checkbox5" name="Checkbox3"/></td>
                  <td>${org[0].orgName}</td>
				  <td>${org[1].orgName}</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb02.png"/></span><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb05.png"/></span></td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png"/></span><span class="gl_tab_tr_r"><a href="#">查看</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png"/></span><span class="gl_tab_tr_r"><a href="#">编辑</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png"/></span><span class="gl_tab_tr_r"><a href="#">删除</a></span></td>
                </tr>
                </c:forEach>
                <tr class="tab_c_tr01">
                  <td><input type="checkbox" class="myClass" value="yes" id="Checkbox4" name="Checkbox3"/></td>
                  <td>第三方厂家</td>
                  <td>ROOT</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb03.png"/></span><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb05.png"/></span></td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png"/></span><span class="gl_tab_tr_r"><a href="#">查看</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png"/></span><span class="gl_tab_tr_r"><a href="#">编辑</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png"/></span><span class="gl_tab_tr_r"><a href="#">删除</a></span></td>
                </tr>
                <tr>
                  <td><input type="checkbox" class="myClass" value="yes" id="Checkbox7" name="Checkbox3"/></td>
                  <td>第三方厂家<br /></td>
                  <td>ROOT</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb03.png"/></span><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb04.png"/></span></td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png"/></span><span class="gl_tab_tr_r"><a href="#">查看</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png"/></span><span class="gl_tab_tr_r"><a href="#">编辑</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png"/></span><span class="gl_tab_tr_r"><a href="#">删除</a></span></td>
                </tr>
                <tr class="tab_c_tr01">
                  <td><input type="checkbox" class="myClass" value="yes" id="Checkbox6" name="Checkbox3"/></td>
                  <td>第三方厂家</td>
                  <td>ROOT</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb03.png"/></span><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb05.png"/></span></td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png"/></span><span class="gl_tab_tr_r"><a href="#">查看</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png"/></span><span class="gl_tab_tr_r"><a href="#">编辑</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png"/></span><span class="gl_tab_tr_r"><a href="#">删除</a></span></td>
                </tr>
                <tr>
                  <td><input type="checkbox" class="myClass" value="yes" id="Checkbox9" name="Checkbox3"/></td>
                  <td>第三方厂家<br /></td>
                  <td>ROOT</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb03.png"/></span><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb05.png"/></span></td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png"/></span><span class="gl_tab_tr_r"><a href="#">查看</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png"/></span><span class="gl_tab_tr_r"><a href="#">编辑</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png"/></span><span class="gl_tab_tr_r"><a href="#">删除</a></span></td>
                </tr>
                <tr class="tab_c_tr01">
                  <td><input type="checkbox" class="myClass" value="yes" id="Checkbox8" name="Checkbox3"/></td>
                  <td>第三方厂家</td>
                  <td>ROOT</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb03.png"/></span><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb05.png"/></span></td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png"/></span><span class="gl_tab_tr_r"><a href="#">查看</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png"/></span><span class="gl_tab_tr_r"><a href="#">编辑</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png"/></span><span class="gl_tab_tr_r"><a href="#">删除</a></span></td>
                </tr>
                <tr>
                  <td><input type="checkbox" class="myClass" value="yes" id="Checkbox11" name="Checkbox3"/></td>
                  <td>第三方厂家<br /></td>
                  <td>ROOT</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb03.png"/></span><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb05.png"/></span></td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png"/></span><span class="gl_tab_tr_r"><a href="#">查看</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png"/></span><span class="gl_tab_tr_r"><a href="#">编辑</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png"/></span><span class="gl_tab_tr_r"><a href="#">删除</a></span></td>
                </tr>
                <tr class="tab_c_tr01">
                  <td><input type="checkbox" class="myClass" value="yes" id="Checkbox10" name="Checkbox3"/></td>
                  <td>第三方厂家</td>
                  <td>ROOT</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb03.png"/></span><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb05.png"/></span></td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png"/></span><span class="gl_tab_tr_r"><a href="#">查看</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png"/></span><span class="gl_tab_tr_r"><a href="#">编辑</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png"/></span><span class="gl_tab_tr_r"><a href="#">删除</a></span></td>
                </tr>
                <tr>
                  <td><input type="checkbox" class="myClass" value="yes" id="Checkbox13" name="Checkbox3"/></td>
                  <td>第三方厂家<br /></td>
                  <td>ROOT</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb03.png"/></span><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb05.png"/></span></td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png"/></span><span class="gl_tab_tr_r"><a href="#">查看</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png"/></span><span class="gl_tab_tr_r"><a href="#">编辑</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png"/></span><span class="gl_tab_tr_r"><a href="#">删除</a></span></td>
                </tr>
                <tr class="tab_c_tr01">
                  <td><input type="checkbox" class="myClass" value="yes" id="Checkbox12" name="Checkbox3"/></td>
                  <td>第三方厂家</td>
                  <td>ROOT</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb03.png"/></span><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb05.png"/></span></td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png"/></span><span class="gl_tab_tr_r"><a href="#">查看</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png"/></span><span class="gl_tab_tr_r"><a href="#">编辑</a></span> <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png"/></span><span class="gl_tab_tr_r"><a href="#">删除</a></span></td>
                </tr>
                <tr>
                  <td><input type="checkbox" class="myClass" value="yes" id="Checkbox2" name="Checkbox1"/></td>
                  <td>第三方厂家<br /></td>
                  <td>ROOT</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb03.png"/></span><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb05.png"/></span></td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png"/></span><span class="gl_tab_tr_r"><a href="#">查看</a></span>
                  <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png"/></span><span class="gl_tab_tr_r"><a href="#">编辑</a></span>
                  <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png"/></span><span class="gl_tab_tr_r"><a href="#">删除</a></span>
                  </td>
                  </tr>
                <tr class="tab_c_tr01">
                  <td><input type="checkbox" class="myClass" value="yes" id="Checkbox3" name="Checkbox2"/></td>
                  <td>第三方厂家</td>
                  <td>ROOT</td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb03.png"/></span><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb05.png"/></span></td>
                  <td><span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb01.png"/></span><span class="gl_tab_tr_r"><a href="#">查看</a></span>
                  <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png"/></span><span class="gl_tab_tr_r"><a href="#">编辑</a></span>
                  <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png"/></span><span class="gl_tab_tr_r"><a href="#">删除</a></span>
                  </td>
                  </tr>
               </table>
               
               <div class="pageBox">
                  <div class="page"><a href="#" class="next">下一页</a><a href="#">6</a><a href="#">5</a><a href="#">4</a><a href="#">3</a><a href="#">2</a><a href="#" class="current">1</a><a href="#" class="prev">上一页</a> <span class="pageBox_font">页</span><a href="#" class="current2">50</a><a href="#">20</a><a href="#">10</a><span class="pageBox_font">每页显示:</span></div>
                </div>
                </td>
             </tr>
           </table>
</body>
</html>
