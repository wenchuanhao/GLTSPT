<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>数据获取管理</title>
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
		<div class="gl_m_r_nav">当前位置 : 系统管理 &gt; 数据获取管理 &gt; 数据获取管理</div>	
		<table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
		<input type="hidden" value="N" name="isPages" id="isPages"/>
		<input type="hidden" value="${manageForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
	    <input type="hidden" value="${manageForm.pageSize}" id="pageSize"	name="pageSize"/>
		   <tr>
	        <td  valign="top" class="gl_m_r_n_tb01_m"></td>
            <td valign="top">
               <div class="gl_bt_bnt01" >
               <input name="" type="button" class="gl_cx_bnt01b" id="setDivs" value="展开查询" onclick="setDiv();"/>
               查询</div>
             <div id="serachDisplay" style="display: block;">
            
              <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <th width="100">服务系统名称:</th>
                 <td>
               <input style="width:98%;font-family:'微软雅黑'; height:20px; line-height:20px; border:#cfd0d0 1px solid; font-size:12px;"  type="text" name="sysName" id="sysName" value="${manageForm.sysName }"/>
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
               
               <div class="gl_bt_bnt01">数据获取信息列表</div>
               
               <div class="gl_bnt_nav01" style="border-bottom:none;">
                 <input name="" type="button" class="gl_cx_bnt04" value="新 增" onclick="toAddDataManage();"/>
                 <input name="" type="button" class="gl_cx_bnt04" value="删 除" onclick="doDeleteItems();"/>
               </div>
             
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
	                <th width="3%"><input type="checkbox" class="myClass"  id="checkAll" name="checkAll" /></th>
					<th width="10%">接入的接口类型</th>
					<th width="10%">系统名称</th>
					<th width="8%">获取频率</th>
					<th width="10%">时间</th>
	                <th width="20%">操作</th>
                </tr>
               	<c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
					<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if> >
						<td><input type="checkbox" class="myClass" name="checkbox" id="checkbox" value="${item.id}" /></td>
						<td><c:if test="${item.type=='0' }">ws接口</c:if>
							<c:if test="${item.type=='1' }">文件接口</c:if>
							<c:if test="${item.type=='2' }">sql接口</c:if></td>
						<td>${item.sysName }</td>
						<td>${item.frequency }</td>
						<td>${item.createDate }</td>
						<td align="center">
						<span class="gl_tab_tr_l">
						  <img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/></span>
						  <span ><a href="javascript:dataManageDetail('${item.id }');">查看</a></span> 
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
		window.location.href = '<%=basePath%>sys/data/editDataManage?id='+id;
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
				v.action = '<%=basePath%>sys/data/delDataManage?id='+getSelectCount();
				v.submit();
			}
		}
	function dataManageDetail(id){
		window.location.href = '<%=basePath%>sys/data/dataManageDetail?id='+id;
	}
	
	function toAddDataManage(){
 		window.location.href = "<%=basePath%>sys/data/addDataManage";
	}
	//查询
	function doSearch(){
		window.location.href="<%=basePath%>sys/data/dataManage";
		document.forms[0].submit();
	}
	
	//重置
	function cleanValue(){
	    document.getElementById("sysId").value="";
	    document.getElementById("sysName").value="";
	}
	
	//单一删除模块
	function delManage(id){
		if (confirm("数据删除后不可以恢复，您确定要删除吗？")) {
	  		window.location.href = '<%=basePath%>sys/data/delDataManage?id='+id;
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