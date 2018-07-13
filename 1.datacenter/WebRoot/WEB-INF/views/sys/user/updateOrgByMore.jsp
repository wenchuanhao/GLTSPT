<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>管理用户</title>
		<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
		<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dtree.css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script src="/SRMC/rmpb/js/dtree2.js" type=text/javascript></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>

		<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
		<script type="text/javascript">
   function setNextOrg(value,tempid){
          var c=value.split(",");
          $("#organizationId").val(c[0]);
          $("#organizationName").val(c[1]);
          var arrayList = [];
          var checkList = [];
          jQuery.ajax({
	type:"POST",
	url:"<%=basePath%>sys/user/setNestOrg",
	data:{"orgId":c[0]},
	success:function(result){	
	   if(null!=result && ''!=result && '0'!=result){
	        var flag=false;
			for(var i=0 ;i<div1.childNodes.length;i++){
			 if(div1.childNodes[i].type=="select-one" ){
			     //获取要赋值的select
	            if(!flag){
			      checkList.push({'id' : div1.childNodes[i].id,'value':div1.childNodes[i].value});
		        }
				 if(div1.childNodes[i].id== tempid ){
				   flag=true;
				   continue;
				 }
				 if(flag){
		            arrayList.push(div1.childNodes[i].id);
				 }
			   }
			}
			//移除重新选择后  原有的下拉列表
			for(var j=0;j<arrayList.length;j++){
	          try{
	            div1.removeChild(document.getElementById(arrayList[j]));
	          }catch(e){}
	        }
	       div1.innerHTML+="<select id='orgId"+c[0]+"' name='orgId"+c[0]+"' onchange=setNextOrg(this.value,\"orgId"+c[0]+"\")><option value=''>请选择</option>"+result;
           //手动赋值
		  for(var j=0;j<checkList.length;j++){
		   try{ 
		      document.getElementById(checkList[j]['id']).value=checkList[j]['value'];
		   }catch(e){}
		  }
	   }else{
	       var flag=false;
			for(var i=0 ;i<div1.childNodes.length;i++){
			 if(div1.childNodes[i].type=="select-one" ){
			     //获取要赋值的select
	            if(!flag){
			      checkList.push({'id' : div1.childNodes[i].id,'value':div1.childNodes[i].value});
		        }
				 if(div1.childNodes[i].id== tempid ){
				   flag=true;
				   continue;
				 }
				 if(flag){
		            arrayList.push(div1.childNodes[i].id);
				 }
			   }
			}
			//移除重新选择后  原有的下拉列表
			for(var j=0;j<arrayList.length;j++){
	          try{
	            div1.removeChild(document.getElementById(arrayList[j]));
	          }catch(e){}
	        }
	       //div1.innerHTML+="<select id='orgId"+c[0]+"' name='orgId"+c[0]+"' onchange=setNextOrg(this.value,\"orgId"+c[0]+"\")><option value=''>请选择</option>"+result;
           //手动赋值
		  for(var j=0;j<checkList.length;j++){
		   try{ 
		      document.getElementById(checkList[j]['id']).value=checkList[j]['value'];
		   }catch(e){}
		  }
	   }	  
	},
	error:function(){
		alert("fail!");
	}
 });
  }
function qx(){
   sysM_mainFrame.window.location.href="<%=basePath%>sys/user/updateOrg";
}
function doClose(){
	//window.parent.doCloseSubpage();
	window.parent.doCloseSubpage2();
}

</script>
	</head>

	<body class="bg_c_g">
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 > 用户管理 > 组织变更
		</div>
		<table class="gl_m_r_n_tb01" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td valign="top" style="display: block;">

					<div class="gl_bt_bnt01">
						组织变更
					</div>
					<form name="form" id="form" method="post"
						action="<%=basePath%>/sys/user/upUserOrgs">
						<!-- 
               <table class="gl_table_a01_b" width="100%" border="0" cellspacing="0" cellpadding="0">
                   <tr>
                   <th width="100">用户所属组织：</th>
                   <td colspan="3">
                       ${orga}
                       <input type="hidden" value="${userIds}" name="userId"/>
				   </td>
                 </tr>
                 <tr>
                   <th width="100">将组织变更为：</th>
                   <td colspan="3">
                   		<input type="hidden" name="organizationId" value="${orgid}" id="organizationId"/>
                   		<div style="display: none;" id="newName"><input type="text" name="organizationName" id="organizationName" disabled="disabled"/></div>
                   		<span id="div1" style="display: block;">
						   <select id="orgName" name="orgName" onchange="setNextOrg(this.value,'orgName')">
						     <option value="">请选择组织</option>
						     <c:forEach items="${listOrg}" var="org">
						       <option value="${org.organizationId},${org.orgName }" >${org.orgName }</option>
						     </c:forEach>
						   </select>
						   </span>
                   </td>
                   
                 </tr>
               </table>
                -->
						<table class="gl_table_a01_6L" width="100%" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<th width="100">
									用户所属组织:
								</th>
								<td colspan="5">
									${orga}
									<input type="hidden" value="${userIds}" name="userId" />
								</td>
							</tr>
							<tr>
								<th width="100">
									将组织变更为:
								</th>
								<td colspan="5">
									<input type="hidden" name="organizationId" value="${orgid}"
										id="organizationId" />
									<div style="display: none;" id="newName">
										<input type="text" name="organizationName"
											id="organizationName" disabled="disabled" />
									</div>
									<span id="div1" style="display: block;"> <select
											id="orgName" name="orgName"
											onchange="setNextOrg(this.value,'orgName')">
											<option value="">
												请选择组织
											</option>
											<c:forEach items="${listOrg}" var="org">
												<option value="${org.organizationId},${org.orgName }">
													${org.orgName }
												</option>
											</c:forEach>
										</select> </span>
								</td>
							</tr>
						</table>
						<div class="gl_ipt_03">
							<input name="input" type="submit" class="gl_cx_bnt03" value="保 存" />
							&nbsp;
							<input name="input" type="button" class="gl_cx_bnt03" value="取 消"
								onclick="doClose()" />
						</div>
					</form>
				</td>
			</tr>

		</table>

	</body>
</html>