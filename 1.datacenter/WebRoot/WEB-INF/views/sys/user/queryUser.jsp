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
<title>管理用户</title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dTree.css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/rmpb/js/Checkable.js" type="text/javascript"></script>
<script src="/SRMC/rmpb/js/dtree.js" type=text/javascript></script>


<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>

<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript">

function search(){
	if(document.getElementById("queryDiv").style.display=='block'){
		document.getElementById("queryDiv").style.display='none';
		document.getElementById("serchBox").style.display='none';
		document.getElementById("toggleQueryButton").value="展开查询";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01";
	}else{
		document.getElementById("queryDiv").style.display='block';
		document.getElementById("serchBox").style.display='block';
		document.getElementById("toggleQueryButton").value="收起查询";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01b";
	}
}


	function doReset(){
		jQuery("#account").val("");
		jQuery("#textfield2").val("");
		jQuery("#mobile").val("");
		jQuery("#email").val("");
		jQuery("#isActivate").val("");
		jQuery("#freezeStatus").val("");
		jQuery("#dep").val("");
		jQuery("#defaultRole").val("");
		
	}
	function unfreezeUser(userId, object){
		var url = "<%=basePath%>sys/user/unfreezeUser";
		var params = {
			userId:userId
		};
		jQuery.post(url, params, function(data){
			if(data == '1'){
				jQuery(object).parent().parent().parent().children(":eq(5)").html("未冻结");
				jQuery(object).parent().parent().children("[id='unfreeSpan']").remove();
				jQuery(object).parent().parent().children("[id='unfreeSpanImage']").remove();
				jQuery(object).parent().parent().children("[id='unfreeSpanLink']").remove();
				alert("操作成功！");
			}else{
				alert("操作失败！");
			}
		} );
	}
	//用户移动操作
	function movePlace(v,y){
		var selectProduct=document.getElementById("dep").value;
		var pageIndex=document.getElementById("pageIndex").value;
		var pageSize=document.getElementById("pageSize").value;
		var isSelect="Y";
		var st="1";
		if(selectProduct==""){
			isSelect="N";
			alert("请先点击左边树形菜单中的组织再进行排序操作！");
			return false;
		}
		if(isSelect=="Y"){
			var url='<%=basePath%>sys/user/exchangeOrderNum';
			jQuery.post(url,{"moveFlag":v,"userId":y,"depId":selectProduct}, function(data){
				if(data=="0"){
					location.href="<%=basePath%>sys/user/queryUser?organizationId="+selectProduct+"&pageIndex="+pageIndex+"&pageSize="+pageSize+"&st="+st;
				}
				if(data=="1"){
					alert("请先点击左边树形菜单中的组织再进行排序操作！");
					return false;
				}
				if(data=="2"){
					alert("参数传递出错!");
					return false;
				}
				if(data=="3"){
					alert("用户不存在或者已被删除!");
					return false;
				}
				if(data=="4"){
					alert("该用户已处于最顶端，不能再进行上移操作!");
					return false;
				}
				if(data=="5"){
					alert("该用户已处于最底端，不能再进行下移操作!");
					return false;
				}
			});
		}
	}
	
	function doSubmit(){
	   document.all("pageIndex").value = "1";
	   document.forms[0].submit();
	}
	
	/**
	*  录入用户
	*/
	function addUser(){
		window.location.href = "<%=basePath%>sys/user/addUser";
	}
	/**
	*  重置用户密码
	*/
	function restUserPwd(){
		window.location.href = "<%=basePath%>sys/user/toRestUserPwd";
	}
	/**
	*  用户密码变更
	*/
	function toChangeUserPwd(){
	
		if (jQuery("input[name='itemOffset']:checked").length < 1) {
			alert("请选择需要密码变更的用户！");
			return false;
		}
			
		if(jQuery("input[name='itemOffset']:checked").length > 1){
			alert("只能变更单个用户");
			return false;
		}
		var orgIds = "";
		jQuery("input[name='itemOffset']:checked").each(function(){
			orgIds = jQuery(this).val();
		});
		
	    jQuery("#toChangeUserButton").attr("href","<%=basePath%>sys/user/toChangeUserPwd?userId="+orgIds);
        jQuery("#toChangeUserButton").fancybox({
			'width'				:  700,
			'height'			: '100%',
			'autoScale'			: false,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe'
        });
	}
	/**
	*  批量删除用户
	*/
	function checkSub(orgIds){
		form.action = '<%=basePath%>sys/user/deleteSysUser?'+Math.random();
		form.submit();
	}
	/**
	*  单个删除用户
	*/
	function delUser(orgIds){
		if(confirm("数据删除后不可以恢复，您确定要删除吗？")){
			document.getElementById("orgIds").value=orgIds;
		    checkSub(orgIds);
	    }
	}
function selectAll() {
		for (var i=0;i<form.length ;i++) {
			var e = form[i];
			if (e.name == 'itemOffset') {
	   			e.checked = form.checkAll.checked;
   			}
   		}
	}
function getSelectCount() {
	var ids="";
	document.getElementById("orgIds").value="";
	var chks = document.getElementsByName('itemOffset');
	var j = 0;
	for ( var i = 0; i < chks.length; i++) {
		if (chks[i].checked){
			j++;
			ids+=chks[i].value+",";
		}
	}
	document.getElementById("orgIds").value=ids;
	return j;
}
/**
* 批量删除按钮
*/
function doDeleteItems() {
	if (getSelectCount() < 1) {
		alert("请选择需要删除的用户！");
	} else if (confirm("数据删除后不可以恢复，您确定要删除吗？")) {
			getSelectCount();
		var orgIds=document.getElementById("orgIds").value;
		checkSub(orgIds);
	}
}
/**
* 批量组织变更
*/	
function changeOrg(){
	
    if (getSelectCount() < 1) {
    	alert("请选择需要变更的用户!");
    	return false;
    }
    var orgs=$("#orgIds").val();
	jQuery("#submitButton2").attr("href","<%=basePath%>sys/user/smodifyUserOrg/"+orgs);
	jQuery("#submitButton2").fancybox({
		'width'				: '90%',
		'height'			: '100%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
 }
 
 function doCloseSubpage(){
	jQuery.fancybox.close();
	location.href='<%=basePath%>sys/user/queryUser?st=1';
}
function doCloseSubpage2(){
	jQuery.fancybox.close();
}
</script>
</head>
<!-- 
<body class="bg_c_g" onload="clearCookie();"> -->
<body class="bg_c_g" onload="clearCookieDD();">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 用户管理 > 用户管理</div>
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
             <td width="25%" valign="top">
               
               <div class="gl_bt_bnt01">组织架构</div>
              
               <div class="gl_bnt_tree01">
                 <div class="gl_bnt_tree02" style="display: block;"></div>
                 <!--tree-->
                 <div class="d_tree_div">
           <script type="text/javascript">
		       d = new dTree('d');	
		       var st="1";	       
				<c:forEach items="${orgs}" var="org">
					<c:choose>
					<c:when test="${org.orgName == '南方基地'}">
							d.add('${org.organizationId}', '-1', "<font loadMenu='${org.organizationId}' color=#EF5900><b>公司组织架构</b></font>", "javascript:doSearch('${org.organizationId}')", '', '', '','//');
						</c:when>
						<c:otherwise>
							d.add('${org.organizationId}','${org.parentId}', "<font loadMenu='${org.organizationId}'>${org.orgName}</font>", "javascript:doSearch('${org.organizationId}')", '', '');
						</c:otherwise>
					</c:choose>	
				</c:forEach>
			document.write(d);

function doSearch(organizationId){	
	jQuery("#dep").val(organizationId);
	jQuery("#st").val("1");
	document.forms[0].submit();
}

function clearCookieDD(){
    var st=null;    
    st=document.getElementById("st").value;        
    if(st==null||st==""){              
       d.closeAll();
    };   
}

	</script>
		
    </div>
<!--tree END-->                 
               </div>
             </td>
             <td width="18" valign="top" class="gl_m_r_n_tb01_m"></td>
             <td valign="top">
             
               <div class="gl_bt_bnt01">
               <input name="" type="button" class="gl_cx_bnt02" value="默认项目" style="display: none;"/>
               <input name="" type="button" class="gl_cx_bnt01" value="收起查询" onclick="toggleQueryDiv()" id="toggleQueryButton"/>
               查询</div>
               <form name="form" id="pageForm" method="post" action="<%=basePath%>sys/user/queryUser">
                <div id="queryDiv" style="display: block;">
              <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0" >
               <tr>
                <th width="100">用户姓名:</th>
                 <td>
                   <input class="gl_text01_a" style="width: 90%;" type="text" name="userName" id="textfield2" value="${form.userName}"/>
                 </td>
               	 <th width="100">登录账号:</th>
                 <td>
                   <input class="gl_text01_a" style="width: 90%;" type="text" name="account" id="account" value="${form.account}"/>
                 </td>
                
                 </tr>
                 <tr>
	               	 <th width="100">用户手机号码:</th>
	                 <td>
	                   <input class="gl_text01_a" style="width: 90%;" type="text" name="mobile" id="mobile" value="${form.mobile}"/>
	                 </td>
	                 <th width="100">用户邮箱:</th>
	                 <td>
	                   <input class="gl_text01_a" style="width: 90%;" type="text" name="email" id="email" value="${form.email}"/>
	                 </td>
                 </tr>
                  <tr>
	                  <th width="100">默认角色:</th>
	                 <td>
	                    <select class="select_new01" style="width: 90%;" name="defaultRole" id="defaultRole">
							<option value="">请选择</option>
							<c:forEach items="${requestScope.roleList }" var="role">
								<option value="${pageScope.role.roleId }" <c:if test="${form.defaultRole eq pageScope.role.roleId}">selected</c:if>>${pageScope.role.roleName }</option>
							</c:forEach>
						</select>
	                 </td>
	               	 <th width="100">账户状态:</th>
	                 <td>
	                     <select class="select_new01" style="width: 90%;" name="isActivate" id="isActivate">
									<option value="">请选择</option>
									<option value="1" <c:if test="${form.isActivate=='1'}">selected</c:if>>正常</option>
									<option value="0" <c:if test="${form.isActivate=='0'}">selected</c:if>>已注销</option>
						</select>
	                 </td>
	               
                 </tr>
               </table>
              <div id="serchBox" class="gl_ipt_03">
					<!-- <input id="img" name="" type="submit" class="gl_cx_bnt03" value="查 询" />&nbsp; -->
                 <input id="img" name="" type="button" class="gl_cx_bnt03" value="查 询" onclick="javascript:doSubmit();"/>&nbsp;
                 <input name="input" type="button" class="gl_cx_bnt03" value="重 置" onclick="doReset();"/>
                 <input type="hidden" value="${form.organizationId}" id="dep"	name="organizationId"/>
                 <input type="hidden" value="${st}" id="st"	name="st"/>
                 <input type="hidden" value="${form.pageIndex}" id="pageIndex"	name="pageIndex"/>
                 <input type="hidden" value="${form.pageSize}" id="pageSize"	name="pageSize"/>
				</div>
				</div>
               <div class="ge_a01"></div>
               
               <div class="gl_bt_bnt01">用户列表</div>
               
               <div class="gl_bnt_nav01" style="border-bottom:none;">
                 <input name="" type="button" class="gl_cx_bnt04" value="录入用户" onclick="addUser()"/>
<!--                  <input name="" type="button" class="gl_cx_bnt04" value="重置用户密码" style="width:96px; background:url(/SRMC/rmpb/images/tab_bnt01a.png)" onclick="restUserPwd()"/> -->
<!-- 				<a id="toChangeUserButton" > -->
<!--                  <input name="" type="button" class="gl_cx_bnt04" value="用户密码变更" style="width:96px; background:url(/SRMC/rmpb/images/tab_bnt01a.png)" onclick="toChangeUserPwd()"/> -->
<!--                 </a> -->
                 <input type="button" class="gl_cx_bnt04" value="批量删除" onclick="doDeleteItems()"/>
                 <a id="submitButton2" ><input type="button" style="width:96px; background:url(/SRMC/rmpb/images/tab_bnt01a.png)" onclick="changeOrg()" class="gl_cx_bnt04" value="批量组织变更" /></a>
               </div>
               <input type="hidden" id="orgIds" name="orgIds"/>
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <th width="3%"><input type="checkbox"   id="Checkbox1" name="checkAll" onclick="selectAll()"/></th>
                 <th width="15%">用户姓名</th>
                <th width="14%">登录账号</th>
                <th width="18%">所属机构</th>
                <th width="12%">用户手机号码</th>
                <th width="7%">排序</th>
                <th width="25%">操作</th>
                </tr>
                <c:forEach items="${ITEMPAGE.items}" var="org" varStatus="i">
                <tr>
                  <td><input type="checkbox"  value="${org[0].userId}"  name="itemOffset"/></td>
                  <td>${org[0].userName}</td>
                  <td>${org[0].account}</td>
                  <td>${org[3].orgName}>>${org[2].orgName}</td>
                  <td>${org[0].mobile}</td>
                  <td>
                  	<img src="/SRMC/rmpb/images/tab_tb03.png" onclick="movePlace('U','${org[0].userId}')"/>
                  	<img src="/SRMC/rmpb/images/tab_tb05.png" onclick="movePlace('D','${org[0].userId}')"/>
                  </td>
                  <td align="center">
                  	<span class="gl_tab_tr_l" style="margin-right: 2px;">
                  		<img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/>
                  	</span>
                  	<span class="gl_tab_tr_r">
                  		<a href="<%=basePath%>sys/user/sysUserInfo/${org[0].userId}">查看</a>
                  	</span>
                  	<span style="margin-right: 2px;margin-left: 2px;"></span>
                  	<span class="gl_tab_tr_l" style="margin-right: 2px;">
                  		<img src="/SRMC/rmpb/images/tab_tb06.png" align="middle"/>
                  	</span>
                  	<span class="gl_tab_tr_r">
                  		<a href="<%=basePath%>sys/user/modifySysUser/${org[0].userId}">编辑</a>
                  	</span>
                  	<span style="padding-left: 5px;"></span>
	                  <span class="gl_tab_tr_l" style="margin-right: 2px;">
	                  	<img src="/SRMC/rmpb/images/tab_tb07.png" align="middle"> 
	                  </span>
                  	<span class="gl_tab_tr_r">
                  		<a href="javascript:void(0)" onclick="delUser('${org[0].userId}')">删除</a>
                  	</span>
                  	
                  	<span style="margin-right: 2px;margin-left: 2px;"></span>
                  	<span class="gl_tab_tr_l" style="margin-right: 2px;"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/></span>
                  	<span class="gl_tab_tr_r"><a href="javascript:void(0)" onclick="doSetPwd('${org[0].userId}')">重置用户密码</a></span>
                  	
                  	<span style="margin-right: 2px;margin-left: 2px;"></span>
                  	<span class="gl_tab_tr_l" style="margin-right: 2px;"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/></span>
                  	<span class="gl_tab_tr_r"><a href="<%=basePath%>sys/role/cofigUserRole/${org[0].userId}">配置角色</a></span>
                  </td>
                </tr>
                </c:forEach>
               </table>
               
                 <div class="pageBox">
				<jsp:include flush="true" page="/public/include/navigate2.jsp"></jsp:include>
				</div>
				 </form>
                </td>
             </tr>
             
           </table>
</body>
<script type="text/javascript" src="/SRMC/rmpb/js/list-common.js"></script>
<script type="text/javascript">
/**
* 重置用户密码
*/
function doSetPwd(acc){
	if (confirm("密码会被重置为${defaultpwd}，确认信息无误后请点击确认？")) {
		var url2 = "<%=basePath%>sys/user/restUserPwd";
		var params = {
			acc:acc
		};
		$.post(url2, params, function(data){
			if(data=='1'){
				alert("抱赚！重置用户密码失败，参数传递出错：用户编号为空。");
				return false;
			}
			if(data=='2'){
				alert("抱赚！重置用户密码失败，查不到用户信息。");
				return false;
			}
			if(data=='3'){
				alert("密码重置成功！请通知用户及时修改密码以保证用户信息安全。");
				return false;
			}
		});
	}
}
</script>
</html>