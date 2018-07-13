<%@ page language="java"  pageEncoding="UTF-8"%>
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
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
<link href="/SRMC/rmpb/css/left_a01.css" type="text/css" rel="stylesheet" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/common.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/autoDialog.js"></script>
<script type="text/javascript">
	//打开组织列表
		function openPrivilege(roleId) {
		var url = "<%=basePath%>sys/org/queryOrglist?index="+ Math.random();
		dialog = new AutoDialog({title:"分配权限", width: 300, height: 400, padding: 0});
		dialog.setContent('<iframe src="'+url+'" width="100%" height="100%" frameborder="0" border="0"></iframe>');
		dialog.show();
		
	}
	function setVal(o){
		$("#organizationName").val(o.orgName);
		$("#organizationId").val(o.orgId);
		dialog.destroy();
	}
		function doSave() {
				if(checkVal("userName",20,"请输入用户真实姓名!",false))
				if(checkVal("organizationName",100,"请输入所属组织!",false))
				if(checkVal("mobile",13,"请输入手机号码!",false))
				if(checkVal("email",50,"请输入邮箱!",false))
				if(checkEmail("email"))
				if(isMobil("mobile"))
					form.submit();
				
			}
			
			
 //设置下一级
    function setNextOrg(value,tempid){
       var c=value.split(",");
       $("#organizationId").val(c[0]);
       $("#organizationName").val(c[1]);
       var arrayList = [];
       var checkList = [];
       jQuery.ajax({
		type:"POST",
		url:"<%=basePath%>promanager/productmanager/setNestOrg",
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
		   }	  
		},
		error:function(){
			alert("fail!");
		}
	  });
    }
    
    //修改组织
    function upUser(userId){
       var organizationId=$("#organizationId").val();
       var organizationName=$("#organizationName").val();
       jQuery.ajax({
		type:"POST",
		url:"<%=basePath%>sys/user/upUser/"+userId,
		data:{"organizationId":organizationId,"organizationName":organizationName},
		success:function(result){	
		  if('1'==result){
		    alert("修改成功!");
		    window.location.href="<%=basePath%>sys/user/sysCheckUserInfo/"+userId+"?"+Math.random();
		  }
		},
		error:function(){
			alert("fail!");
		}
	  });
    }
</script>
</head>
<body class="main_bg">
	
		<div class="mm_main_bd11">
			<div class="mm_m_bd_a">
				<span class="mm_m_bd_b">
					<img src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" />
				</span>
				<span class="mm_m_bd_c">用户信息</span>
			</div>
			<div class="ge01"></div>
				<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table03">
					<tr>
						<th width="15%">
							<span class="or">*</span>用户登录账号
						</th>
						<td width="35%">
							${user.account}
						</td>
						<th width="15%">
							<span class="or">*</span>用户姓名
						</th>
						<td width="35%">
							${user.userName}
						</td>
					</tr>
					<tr>
						<th width="15%">
							<span class="or">*</span>是否接受SMS
						</th>
						<td width="35%">
							<c:if test='${user.isReceiveSms =="1" }'>接受</c:if>
							<c:if test='${user.isReceiveSms =="0" }'>不接受</c:if>
						</td>
						<th width="15%">
							<span class="or">*</span>用户状态
						</th>
						<td width="35%">
							<c:if test='${user.isActivate =="1" }'>正常</c:if>
							<c:if test='${user.isActivate =="0" }'>禁用</c:if>
						</td>
					</tr>
					<tr>
						<th width="15%">
							<span class="or">*</span>用户邮箱
						</th>
						<td width="35%">
							${user.email}
						</td>
						<th width="15%">
							<span class="or">*</span>用户手机号码
						</th>
						<td width="35%">
							${user.mobile}
						</td>
					</tr>
					<tr>
						<th width="15%">
							<span class="or">*</span>用户所属组织
						</th>
						<td width="35%" colspan="3">
						  ${user.organizationName}
						  <input type="hidden" value="${user.organizationName }" id="organizationName" name="organizationName"/>
						   <input type="hidden" value="${user.organizationId}" id="organizationId" name="organizationId"/>
					 &nbsp;&nbsp;
						  <span id="div1">
						   <select id="orgName" name="orgName" onchange="setNextOrg(this.value,'orgName')">
						     <option value="">请选择</option>
						     <c:forEach items="${listOrg}" var="org">
						       <option value="${org.orgId},${org.orgName }" >${org.orgName }</option>
						     </c:forEach>
						   </select>
						  
						 </span>
							
						</td>
					</tr>
				</table>
				
				<table width="96%" align="center">
					<tr>
						<td height="60" colspan="4" align="center">
							<input class="bnt_class03" type="button" name="button4" id="button4" value="修 改" onclick="upUser('${user.userId }');" />
						</td>
					</tr>
				</table>
				
			<div class="ge02"></div>
		</div>
</body>
</html>
