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
		<title>用户区域编辑</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">
			function dosubmits(){
		    var selectValue="";
		    var count=0;
		    var userId=$("#userId").val();
		    var roleId=$("#roleId").val();
			if($("#orgName").val()=='N'){
				alert("用户区域设置成功!");
				window.location.href="<%=basePath%>sys/role/queryUserRoleIndex";
				return false;
			}
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
			 
	
			if(userId==""){
				alert("用户编号不能为空！");
				return false;
			}
			$.post("<%=basePath%>sys/role/editRoleUserView",{"userId":userId,"selectValue":selectValue,"roleId":roleId},function(data){
				if(data=="y"){
					alert("用户区域设置成功!");
					window.location.href="<%=basePath%>sys/role/queryUserRoleIndex";
				}
				if(data=="n"){
					alert("用户区域设置失败!");
					window.location.href="<%=basePath%>sys/role/queryUserRoleIndex";
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
    
    function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		}
	</script>
	</head>
	<body class="bg_c_g">
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 > 角色管理 > 用户区域编辑
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
								<input type="hidden" value="${roleId}" id="roleId"/>
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
					</table>
					<div class="ge_a01b"></div>
					<div class="gl_bt_bnt01">
						用户角色区域配置信息
					</div>
					<table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th width="100">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;角色名称:
							</th>
							<td colspan="4" style="word-break: break-all;">
								<div id="roleDisplay" style="display: block;">
								${roleNameN}
								</div>
							</td>
						</tr>
						<tr>
							<th width="100">
								原来配置区域:
							</th>
							<td colspan="4" style="word-break: break-all;">
								<div id="roleDisplay" style="display: block;">
								${areaNames}
								</div>
							</td>
						</tr>
						<tr>
							<th width="100">
								重新设置区域:
							</th>
							<td colspan="4">
						       <span id="div1"> 
						          <select id="orgName" name="orgName"
											onchange="setNextOrg(this.value,'orgName')"
											class="select_new01">
											<option value="NO">
												无
											</option>
											<option value="ALL">
												全部
											</option>
											<c:forEach items="${Areas}" var="org">
												<option value="${org.organizationId},${org.orgName}">
													${org.orgName}
												</option>
											</c:forEach>
								    </select> 
								</span>
							</td>
						</tr>
					</table>
					<div class="gl_ipt_03">
					   <input name="input" type="button" class="gl_cx_bnt03"
										value="保存" onclick="dosubmits()" />
									&nbsp;
						<input name="input" type="button" class="gl_cx_bnt03" value="返回" onclick="doReturn('${location}')"/>
						<%-- <input name="input" type="button" class="gl_cx_bnt03" value="返回"onclick="history.go(-1);"/> --%>
					</div>
                </div>
	</body>
</html>
