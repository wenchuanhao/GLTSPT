<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统角色组管理</title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/rmpb/js/Checkable.js" type="text/javascript"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
</head>
<body class="bg_c_g">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 角色管理 > 角色组管理</div>
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
				<td valign="top">
               <div class="gl_bt_bnt01">
	               <input name="" type="button" class="gl_cx_bnt02" value="默认项目" style="display: none;"/>
	               <input name="" type="button" class="gl_cx_bnt01" value="收起查询" onclick="toggleQueryDiv()" id="toggleQueryButton"/>
               	查询</div>
               <form name="form" method="post" id="pageForm" action="<%=basePath%>sys/rolegroup/querySysRoleGroup">
                <div id="queryDiv" style="display: block;">
	                <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0" >
		               	<tr>
			               	 <th width="100">角色组编码:</th>
			                 <td>
			                   <input class="gl_text01_a" style="width: 90%;" type="text" name="roleGroupcode" id="textfield" value="${form.roleGroupcode}"/>
			                 </td>
			                 <th width="100">角色组名称:</th>
			                 <td>
			                   <input class="gl_text01_a" style="width: 90%;" type="text" name="roleGroupname" id="textfield2" value="${form.roleGroupname}"/>
			                 </td>
		                 </tr>
	               </table>
	                <div id="serchBox" class="gl_ipt_03">
	                	<input id="img" name="" type="button" class="gl_cx_bnt03" value="查 询" onclick="javascript:doSubmit();"/>&nbsp;
	                 	<input name="" type="button" class="gl_cx_bnt03" value="重 置" onclick="doReset();"/>
					</div>
				</div>
              
               <div class="ge_a01"></div>
               
               <div class="gl_bt_bnt01">角色组列表</div>
               
               <div class="gl_bnt_nav01" style="border-bottom:none;">
                 <input name="" type="button" class="gl_cx_bnt04" value="新增角色组" onclick="addRoleGroup()"/>
                 <input name="" type="button" class="gl_cx_bnt04" value="分配用户" onclick="allotUser()" />
                 <input name="" type="button" class="gl_cx_bnt04" value="批量删除" onclick="delRoleGroup('del')"/>
               </div>
             
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <th><input type="checkbox" onclick="selectAll()" name="checkAll"/></th>
                <th>序号</th>
                <th>角色组编码</th>
                <th>角色组名称</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>操作</th>
                </tr>
                <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
                	<tr>
		                  <td><input type="checkbox" value="${vo.roleGroupid}" name="roleradio"/></td>
		                  <td>${i.index+1}</td>
		                  <td>${vo.roleGroupcode}</td>
		                  <td>${vo.roleGroupname}</td>
		                  <td>${vo.createUsername}</td>
		                  <td><fmt:formatDate value="${vo.createTime}" pattern="yyyy-MM-dd"/></td>
		                  <td align="center">
			                  <span class="gl_tab_tr_l" style="margin-right: 2px;"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/></span>
			                  <span class="gl_tab_tr_r"><a href="javascript:void(0)" onclick="viewRoleGroup('${vo.roleGroupid}')">查看</a></span>
			               		
			               		<span style="padding-left: 5px;"></span>
			                    <span class="gl_tab_tr_l" style="margin-right: 2px;">
				                  		<img src="/SRMC/rmpb/images/tab_tb06.png" align="middle">
				                  	</span>
				                  	<span class="gl_tab_tr_r">
				                  		<a href="javascript:void(0)" onclick="editRoleGroup('${vo.roleGroupid}')">编辑</a>
				                  	</span>
			                  
			                  <span style="padding-left: 5px;"></span>
				                  <span class="gl_tab_tr_l" style="margin-right: 2px;">
				                  	<img src="/SRMC/rmpb/images/tab_tb07.png" align="middle"> 
				                  </span>
			                  	<span class="gl_tab_tr_r">
			                  		<a href="javascript:void(0)" onclick="delRoleById('${vo.roleGroupid}')">删除</a>
			                  	</span>
			                  	
			                  	<span style="padding-left: 5px;"></span>
						  <span class="gl_tab_tr_r">
						  	<a id="user_${vo.roleGroupid}">
						  		<input name="" onclick="userAllot('${vo.roleGroupid}')" type="button" class="gl_cx_bnt04" value="分配用户" style="width:96px; background:url(/SRMC/rmpb/images/tab_bnt01a.png)" />
						  	</a>
						  </span>
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
   function doSubmit(){
	   document.all("pageIndex").value = "1";
	   document.forms[0].submit();
	}
	function doReset(){
		jQuery("#textfield").val("");
		jQuery("#textfield2").val("");
		doSubmit();
	}
	/**
	* 分配用户
	*/
	function userAllot(roleGroupid){
		  jQuery("#user_"+roleGroupid).attr("href","<%=basePath%>sys/rolegroup/groupUser?roleGroupid="+roleGroupid)
	      jQuery("#user_"+roleGroupid).fancybox({
			'width'				: '100%',
			'height'			: '100%',
			'autoScale'			: false,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe'
	      });
	}
	/**
	 * 新增角色组
	*/
	function addRoleGroup(){
		window.location.href = "<%=basePath%>sys/rolegroup/addRoleGroup";
	}
	/**
	 * 编辑角色组
	*/
	function editRoleGroup(roleGroupid){
		window.location.href = "<%=basePath%>sys/rolegroup/addRoleGroup?roleGroupid="+roleGroupid;
	}
	/**
	* 查看角色组
	*/
	function viewRoleGroup(roleGroupid){
		window.location.href = "<%=basePath%>sys/rolegroup/addRoleGroup?roleGroupid="+roleGroupid+"&isView=1";
	}
	function allotUser(){
		window.location.href = "<%=basePath%>sys/rolegroup/groupUser";
	}
	/**
	* 删除角色组
	*/
	function delRoleById(roleGroupid){
		if(confirm("数据删除后不可以恢复，您确定要删除吗？")){
			doDel(roleGroupid);
		}
	}
	/**
* 全选
*/
function selectAll() {
	for (var i=0;i<form.length ;i++) {
		var e = form[i];
		if (e.name == 'roleradio') {
   			e.checked = form.checkAll.checked;
		}
	}
}

function doDel(roleGroupid){
	jQuery.ajax({
        type:"POST",
        async:true,
        url:"<%=basePath%>sys/rolegroup/delRoleById",
        data:"roleGroupid=" + roleGroupid,
        dataType:"json",
        success:function(data){
        	if(data == "1"){
        		alert("删除成功");
        		doSubmit();
        	}else{
        		alert("删除失败");
        	}
        },
        error:function(){
        	alert("删除失败");
        }
   	});
}

function delRoleGroup(v){
	var roleIds = document.getElementsByName("roleradio");
	var count=0;
	var roleid="";
	for(var i=0;i<roleIds.length;i++){
		if(roleIds[i].checked==true){
			count++;
			roleid +=roleIds[i].value+",";
		}
	}
	if(count==0){
	    if(v=='del'){
	    	alert("请选择您要删除的角色组!");
	    }
		return false;
		
	}else{
		if(v=='del'){
			if(confirm("数据删除后不可以恢复，您确定要删除吗？")){
				doDel(roleid);
			}
		}
	}
}
</script>
</html>