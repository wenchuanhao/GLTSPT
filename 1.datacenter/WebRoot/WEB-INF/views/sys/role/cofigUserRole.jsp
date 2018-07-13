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
		<title>用户配置角色</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">
		function dosubmits(){
		    var selectValue="";
		    var roles=document.getElementsByName("roles");
		    var count=0;
		    var roleIds= "";
		    var userId=$("#userId").val();
		    for(var f=0;f<roles.length;f++){
		    	if(roles[f].checked==true){
		    		roleIds += roles[f].value + "|";
		    		count++;
// 		    		break;
		    	}
		    }
		    if(count==0){
		    	alert("请选择角色！");
		    	return false;
		    }
			if('${isAdmin}'=="1"){
				if($("#orgName").val()==""){
					selectValue="";
				}else{
				      var one=$("#orgName").val();
					  var ones=one.split(",");
					  selectValue=ones[0]+"|1";
					  var selets=document.getElementsByName("AB");
					  for(var i=0;i<selets.length;i++){
					  	if(selets[i].value!=""){
					  		var two=selets[i].value;
					  		var twos=two.split(",");
					  		selectValue+=";"+twos[0]+"|"+(i+2);
					  	}
					  }
				}
			 
			}
			if('${isAdmin}'=="0"){
				selectValue="";
				var newOrgs=document.getElementsByName("newOrgs");
				for(var j=0;j<newOrgs.length;j++){
					if(newOrgs[j].value!=""){
						
						if(selectValue!=""){
						   selectValue+=";"+newOrgs[j].value+"|"+(j+1);
						}else{
						   selectValue+=newOrgs[j].value+"|"+(j+1)+";";
						}
					}
				}
				if('${ishave}'=="1"){
				     var cont=newOrgs.length+2;
					 var b1=$("#orgName").val();
					 if(b1!=""){
					 	var b2=b1.split(",");
					 	selectValue+=";"+b2[0]+"|"+cont;
					 	var SE=document.getElementsByName("AB");
						  for(var k=0;k<SE.length;k++){
						  	if(SE[k].value!=""){
						  		var T=SE[k].value;
						  		var Ts=T.split(",");
						  		selectValue+=";"+Ts[0]+"|"+(cont+1);
						  	}
						  }
					 }
				}
			}
			//if(selectValue==""){
				//alert("所属区域不能为空！");
				//return false;
			//}
			if(userId==""){
				alert("用户编号不能为空！");
				return false;
			}
			$.post("<%=basePath%>sys/role/configUserRole",{"userId":userId,"roleIds":roleIds,"selectValue":selectValue},function(data){
				if(data=="y"){
					alert("用户角色设置成功!");
					window.location.href="<%=basePath%>sys/user/queryUser";
				}
				if(data=="n"){
					alert("用户角色设置失败!");
					window.location.href="<%=basePath%>sys/user/queryUser";
				}
			});
		}
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
			       div1.innerHTML+="<select id='orgId"+c[0]+"' name='AB'"+" onchange=setNextOrg(this.value,\"orgId"+c[0]+"\")><option value=''>全部</option>"+result;
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
</script>
	</head>
	<body class="bg_c_g">
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 > 用户管理 > 用户角色配置
		</div>
		        
                <div class="gl_import_m">
        
					<div class="gl_bt_bnt01">
						用户信息
					</div>
					
					<table class="gl_table_a01_6L" width="100%" border="0"
						cellspacing="0" cellpadding="0">
						<tr>
							<th width="100">
								用户登录帐号:
							</th>
							<td>
								${user.account}
								<input type="hidden" value="${user.userId}" name="userId" id="userId"/>
							</td>
							<th width="100">
								用户姓名:
							</th>
							<td>
								${user.userName}
							</td>
								<th width="100">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户状态:
							</th>
							<td>
								${zt}
							</td>
						</tr>
						<tr>
							<th width="100">
								&nbsp;&nbsp;&nbsp;用户手机号:
							</th>
							<td>
								${user.mobile}
							</td>
							<th width="100">
								用户邮箱:
							</th>
							<td>
								${user.email}
							</td>
							<th width="100">
								是否接受SMS:
							</th>
							<td>
								${js}
							</td>
						</tr>
						<tr>
							<th width="100">
								用户所属组织:
							</th>
							<td colspan="5">
								${orga}
							</td>
						</tr>
<!--						<tr>-->
<!--							<th width="100">-->
<!--								已分配的角色 :-->
<!--							</th>-->
<!--							<td colspan="5">-->
<!--								${ypRole}-->
<!--							</td>-->
<!--						</tr>-->
					</table>
				  <div class="ge_a01b"></div>
				  <div class="gl_bt_bnt01"> 已分配角色</div>
				  <c:if test="${areasFlag=='n'}">
				     此用户暂无分配角色区域
				  </c:if>
				  <c:if test="${areasFlag=='y'}">
		          <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
		          	<tr class="tuan01a">
			          <th>序号</th>
			          <th>角色名称</th>
			          <th>角色编码</th>
			          <th>操作</th>
			        </tr>
		          	<c:forEach items="${roleAreaList}" var="org" varStatus="i">
						  <tr>
						    <th style=" width:3%; font-family:Arial; background:#e6f3fb">${i.index+1}</th>
						    <td style="width:30%">${org.roleName}</td>
						    <td style="width:30%">${org.roleCode}</td>
						    <td style="width:5%">
						    	<a href="javascript:void(0)"
						    	onclick="delUserRole('${user.userId}','${org.roleId}','${org.roleuserId}')">撤销分配</a>
						    </td>
					      </tr>
				    </c:forEach>
				  </table>
				  </c:if>
				  <div class="ge_a01b"></div>
				  <div class="gl_bt_bnt01">角色配置</div>
				  <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
							<th width="100">
								角色名称:
							</th>
							<td colspan="3">
								<input class="gl_text01_a" type="text" id="xx" name="xx" size="35" />
								&nbsp;
								<input type="button" onclick="searchRole()" class="gl_cx_bnt03"
									value="查询" />
							</td>
						</tr>
						<tr>
							<th width="100">
								可选角色:
							</th>
							<td colspan="4" style="word-break: break-all;">
								<div id="roleDisplay" style="display: block;">
									<c:forEach items="${listRoles}" var="org" varStatus="i">

										<input type="checkbox" value="${org.roleId}" name="roles" />${org.roleName}&nbsp;&nbsp;
<!--                    	 	    <c:if test="${(i.index+1)%4==0}">-->
<!--										<br/>	-->
<!--										</c:if>-->
									</c:forEach>
								</div>
							</td>
						</tr>
						<tr style="display: none;">
							<th width="100">
								所属区域:
							</th>
							<td colspan="4">
								<c:if test='${isAdmin=="1"}'>
									<span id="div1"> <select id="orgName" name="orgName"
											onchange="setNextOrg(this.value,'orgName')"
											class="select_new01">
											<option value="NO">
												无
											</option>
											<option value="ALL">
												全部
											</option>
											<c:forEach items="${listArea}" var="org">
												<option value="${org.organizationId},${org.orgName}">
													${org.orgName}
												</option>
											</c:forEach>
										</select> </span>
								</c:if>
								<c:if test='${isAdmin=="0"}'>
									<c:forEach items="${listArea}" var="org" varStatus="i">
										<select id="${i.index+11}orgName" name="newOrgs">
											<option value="${org.organizationId}">
												${org.orgName}
											</option>
										</select>
									</c:forEach>
									<c:if test='${ishave=="1"}'>
										<span id="div1"> <select id="orgName" name="orgName"
												onchange="setNextOrg(this.value,'orgName')">
												<option value="">
													全部
												</option>
												<c:forEach items="${nextArea}" var="org">
													<option value="${org.organizationId},${org.orgName}">
														${org.orgName}
													</option>
												</c:forEach>
											</select> </span>
									</c:if>
									
								</c:if>
							</td>
						</tr>
					</table>
				
					<div class="gl_ipt_03">
									<input name="input" type="button" class="gl_cx_bnt03"
										value="增加" onclick="dosubmits()" />
									&nbsp;
									<input name="input" type="button" class="gl_cx_bnt03"
										value="返回" 
										onclick="window.history.back();" />
<!-- 										onclick="doReturn('${location}')"  /> -->
										<%-- value="返回" onclick="history.go(-1);"  /> --%>
								</div>
				
                </div>
                
		<script type="text/javascript">
		/**
		* 删除角色组角色配置
		*/
		function delUserRole(userId,roleId,userRoleId){
		
			if(confirm("数据删除后不可以恢复，您确定要删除吗？")){
			      jQuery.ajax({
						type:"POST",
						url:"<%=basePath%>sys/role/delUserRole",
						data:{"userId":userId,"roleId":roleId,"userRoleId":userRoleId},
						success:function(result){
						  if(result=='1'){
						    alert("删除成功!");
						    window.location.href = "<%=basePath%>sys/role/cofigUserRole/"+userId;
						  }if(result=='0'){
						    alert("删除失败!");
						  }
						},
						error:function(){
							alert("删除失败!");
						}
			  	  });
			}
		}
		
		function  doAaddOrg(){
			orgForm.submit();
		}
		function searchRole(){
			var roleName=$("#xx").val();
			var userId=$("#userId").val();
			$.post("<%=basePath%>sys/role/getRoleByRoleName",{"roleName":roleName,"userId":userId},function(data){
				if(data!='n'){
					document.getElementById("roleDisplay").innerHTML=data;
				}
			});
		}
		
		function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		}
	</script>
	</body>
</html>
