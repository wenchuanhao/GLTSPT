<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>应用系统管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" >
function ellipsisStr(str){
		 	var newStr = '';
		 	var array = new Array(); 
		 	array = str.split("");
		 	if(array.length > 16){
		 		newStr = str.substr(0, 16) + "..."; 
		 	}else{
		 		newStr = str;
		 	}
			return newStr;
		}
		</script>
</head>
<body class="bg_c_g">
	<form method="post" name="pageForm" id="pageForm">
		<input type="hidden"  id="moduleIds" name="moduleIds" />
		<div class="gl_m_r_nav">当前位置 : 系统管理 &gt; 应用系统管理 &gt; 管理应用系统</div>	
		<table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
		<input type="hidden" value="N" name="isPages" id="isPages"/>
		<input type="hidden" value="${manage.pageIndex}" id="pageIndex"	name="pageIndex"/>
	    <input type="hidden" value="${manage.pageSize}" id="pageSize"	name="pageSize"/>
		   <tr>
	        <td  valign="top" class="gl_m_r_n_tb01_m"></td>
            <td valign="top">
               <div class="gl_bt_bnt01" >
               <input name="" type="button" class="gl_cx_bnt01b" id="setDivs" value="展开查询" onclick="setDiv();"/>
               查询</div>
             <div id="serachDisplay" style="display: block;">
            
              <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <th width="100">系统编码:</th>
                 <td>
               <input style="width:98%;font-family:'微软雅黑'; height:20px; line-height:20px; border:#cfd0d0 1px solid; font-size:12px;" class="" type="text" name="code" id="code"  value="${manage.code }"/>                 
                 </td>
                 <th width="100">系统名称:</th>
                 <td>
               <input style="width:98%;font-family:'微软雅黑'; height:20px; line-height:20px; border:#cfd0d0 1px solid; font-size:12px;"  type="text" name="name" id="name" value="${manage.name }"/>
			  </td>
               </tr>
              
             </table>
            	 <div class="gl_ipt_03">
					<input name="input" type="button" class="gl_cx_bnt03" value="查 询" onclick="doSearch();"/>
					&nbsp;
					<input name="input2" type="button" class="gl_cx_bnt03" value="重 置" onclick="cleanValue();"/>
					
				</div>
            
               </div>
               <div class="ge_a01"></div>
               
               <div class="gl_bt_bnt01">应用信息列表</div>
               
               <div class="gl_bnt_nav01" style="border-bottom:none;">
                 <input name="" type="button" class="gl_cx_bnt04" value="新 增" onclick="toAddSysModule();"/>
                 <input name="" type="button" class="gl_cx_bnt04" value="删 除" onclick="doDeleteItems();"/>
               </div>
             
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
	                <th width="3%"><input type="checkbox" class="myClass"  id="checkAll" name="checkAll" /></th>
	                <th width="8%">系统编码</th>
					<th width="12%">系统名称</th>
					<th width="10%">接入的接口类型</th>
					<th width="10%">数据分域</th>
					<th width="8%">应用管理员名称</th>
					<th width="8%">应用管理员电话</th>
					<th width="12%">应用管理员邮箱</th>
	                <th width="20%">操作</th>
                </tr>
               	<c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
					<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if> >
						<td><input type="checkbox" class="myClass" name="checkbox" id="checkbox" value="${item.id}" /></td>
						<td>${item.code }</td>
						<td>${item.name }</td>
						<td><c:if test="${item.type=='0' }">ws接口</c:if>
							<c:if test="${item.type=='1' }">文件接口</c:if>
							<c:if test="${item.type=='2' }">sql接口</c:if>
						</td>
						<td>
						<c:if test="${item.domain=='0' }">成本类</c:if>
						<c:if test="${item.domain=='1' }">收入类</c:if>
						<c:if test="${item.domain=='2' }">预算类</c:if>
						<c:if test="${item.domain=='3' }">服务量</c:if>
						<c:if test="${item.domain=='4' }">专题类</c:if>
						<c:if test="${item.domain=='5' }">指标类</c:if>
						</td>
						<td>${item.adminName }</td>
						<td>${item.adminPhone }</td>
						<td>${item.adminEmail }</td>
						<td align="center">
						<span class="gl_tab_tr_l">
						  <img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/></span>
						  <span ><a href="javascript:moduleDetail('${item.id }');">查看</a></span> 
						  <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb06.png" align="middle"/></span>
						  <span class="gl_tab_tr_r"><a href="javascript:editManage('${item.id }');">编辑</a></span> 
						  <span class="gl_tab_tr_l"><img src="/SRMC/rmpb/images/tab_tb07.png" align="middle"/></span>
						  <span class="gl_tab_tr_r"><a href="javascript:delManage('${item.id }');">删除</a></span></td>
					</tr>
				</c:forEach>
               </table>
               
               <div class="pageBox">
                 <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
               </div>
            
            </td>
           </tr>
		</table>
	</form>
	<script type="text/javascript" src="/SRMC/rmpb/js/list-common.js"></script>
	<script type="text/javascript">
	 function selectAll() {
		for (var i=0;i<form.length ;i++) {
			var e = form[i];
			if (e.name == 'checkbox') {
	   			e.checked = form.checkAll.checked;
   			}
   		}
	}
	
	function editManage(id){
		window.location.href = '<%=basePath%>sys/app/editManage?id='+id;
	}
	
	function getSelectCount() {
		var ids="";
		document.getElementById("moduleIds").value="";
		var chks = document.getElementsByName('checkbox');
		var j = 0;
		for ( var i = 0; i < chks.length; i++) {
			if (chks[i].checked){
				j++;
				ids+=chks[i].value+",";
			}
		}
		document.getElementById("moduleIds").value=ids;
		return ids;
	}
	function doDeleteItems() {
			if (getSelectCount()=="") {
				alert("请选择需要删除的数据！");
			} else if (confirm("数据删除后不可以恢复，您确定要删除吗？")) {
				var v = document.getElementById("pageForm");
				v.action = '<%=basePath%>sys/app/delManage?id='+getSelectCount();
				v.submit();
			}
		}
	function moduleDetail(id){
		window.location.href = '<%=basePath%>sys/app/queryManageDetail?id='+id+'&type=1';
	}
	
	function toAddSysModule(){
 		window.location.href = "<%=basePath%>sys/app/addAppSysManage";
	}
	//查询
	function doSearch(){
		document.all("pageIndex").value = "1";
	document.getElementById("pageForm").submit();
	}
	
	//重置
	function cleanValue(){
	    document.getElementById("name").value="";
	    document.getElementById("code").value="";
	}
	
	//单一删除模块
	function delManage(id){
		if (confirm("数据删除后不可以恢复，您确定要删除吗？")) {
	  		window.location.href = '<%=basePath%>sys/app/delManage?id='+id;
	  }
	}
	
	$(function (){
		$("#checkAll").click(function() {
        $('input[name="checkbox"]').attr("checked",this.checked); 
    	});
		var $subBox = $("input[name='checkbox']");
    	$subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='checkbox']:checked").length ? true : false);
    });
	});
	</script>
</body>
</html>