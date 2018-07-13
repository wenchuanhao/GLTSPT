<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>补寄终端</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/adapterManage/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
		

<script type="text/javascript">
	function textareaClick(){
	 var area = document.getElementById("opitionContent");
		if(area.value == '此处填写备注信息。'){
		 	area.style.color="";
		 	area.value = '';
		 }
	}
	function textareaBlur(){
	 	var area = document.getElementById("opitionContent");
		 if(area.value == ''){
		 	area.style.color="#999";
		 	area.value = '此处填写备注信息。';
		 }
	}

	function doSubmit(){
		document.all("pageIndex").value = "1";
		document.forms[0].submit();
	}
	
	function doClose(){
		window.parent.doCloseSubpage();
	}
	
	function doAdd(){
		var brandsId = "";
		var type = ""; 
		var brandEName = ""; 
		jQuery("input[name='sysId']").each(function () {
			if(jQuery(this).attr("checked") == "checked"){
				brandsId = jQuery(this).val();
				//brand = jQuery("mobileBrands").val();
				 type= jQuery(this).next().val();
				 brandEName=jQuery(this).next().next().val();
			}
		});
		
		if(brandsId == ""){
		alert("请选择一个应用系统名称进行添加");
		return;
	}
	
	var type2 = "";
	if(type=="0"){
		type2 = "ws接口";
	}if(type=="1"){
		type2 = "文件接口";
	}if(type=="2"){
		type2 = "sql接口";
	}
	
	window.parent.document.getElementById("pId").value=brandsId;
	window.parent.document.getElementById("pName").value=brandEName;
	/* window.parent.document.getElementById("type").value=type;
	window.parent.document.getElementById("type2").value=type2; */
	window.parent.getSysManage();
	window.parent.doCloseSubpage();	
}
	
</script>
</head>
<body class="bg_c_g" >
	<div class="gl_m_r_nav">当前位置 : 系统管理 &gt;短信管理&gt; 短信发送管理</div>
	<div class="zy_a01"  style="width:94%; margin:0 auto 10px auto; padding:3px 0 3px 0; ">
		<span style="color:red; margin-left:10px;">温馨提示：</span>请您输入业务系统名称进行查找，选择您需要的名称，谢谢！
	</div>
	<div class="gl_import_m">
	<form id="pageForm" name="form"   method="post">
	 <table class="gl_table_a01" border="0" cellspacing="0" width="100%"
						cellpadding="0">
		 <tr>
			<th width="10%">业务系统名称:</th>
			<td width="30%"><input class="gl_text01_a" style="width: 100%"  type="text"  name="sysName" id="sysName" value="${manage.name}" /></td>
			<td align="center">
					<input name="input" type="button" class="gl_cx_bnt03" value="查 询" onclick="doSubmit();"/>
			</td>
         </tr>
	</table>
	<div class="ge_a01"></div>
	<table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <th width="10%"></th>
                 <th width="30%">获取频率</th>
                 <th width="30%">业务系统名称</th>
                 <th width="30%">接口类型</th>
               </tr>
               <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
               <tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if>>
                 <td>
                     <input type="radio" class="myClass" name="sysId" id="sysId" value="${item[0]}" />
                     <input type="hidden" class="myClass" name="type" value="${item[3] }" />
                     <input type="hidden" class="myClass" name="name2" value="${item[2] }" />
                 </td>
                 <td>${item[1] }</td>
                 <td>${item[2] }</td>
                 <td><c:if test="${item[3]=='0' }">ws接口</c:if>
                 <c:if test="${item[3]=='1' }">文件接口</c:if>
                 <c:if test="${item[3]=='2' }">sql接口</c:if></td>
               </tr>
               </c:forEach>
             </table>
             <jsp:include flush="true" page="/public/include/navigate4.jsp"></jsp:include>
             <div class="ge01" style="height:0px; "></div>
             <div class="gl_ipt_03" style=" padding-top:0;">
				<input name="input" type="button" class="gl_cx_bnt03" value="确认" onclick="doAdd();"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="input" type="button" class="gl_cx_bnt03" value="取消" onclick="doClose();"/>
			</div>
              <div class="ge01"></div>
  </form>
</div>
</body>
</html>