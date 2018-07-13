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
		<title>编辑用户</title>
		<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
		<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dtree.css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script src="/SRMC/rmpb/js/dtree2.js" type=text/javascript></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>

		<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
		<script type="text/javascript">
		   function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		        }
		
		</script>
		
		<script type="text/javascript">
function search(){
	if(document.getElementById("queryDiv").style.display=='block'){
		document.getElementById("queryDiv").style.display='none';
		document.getElementById("toggleQueryButton").value="展开查询";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01";
	}else{
		document.getElementById("queryDiv").style.display='block';
		document.getElementById("toggleQueryButton").value="收起查询";
		document.getElementById("toggleQueryButton").className="gl_cx_bnt01b";
	}
}
function checkAccount(msg,type){
	var account = document.getElementById("account").value;
	if(account!=null && account!="" ){
		if(account.length < 3){
			alert("用户登录帐号在3-20个字符之间");
			$("#account")[0].focus();
			return false;
		}else{
			return checkVal("account",20,"请输入登录账号!",false);
		}
	}else
	{
		if(type==2){
			alert(msg);
			$("#account")[0].focus();
		}
		return false;
	}
}
function checkNamexUniqueness(){
	var account =document.getElementById("account").value;
	var oac= document.getElementById("oacc").value;
	var js= document.getElementsByName("isReceiveSms");
	var sj="";
	var st= document.getElementsByName("isActivate");
	var ts="";
	for(var i=0;i<js.length;i++){
	   if(js[i].checked==true){
	   	   sj=js[i].value;
	   }
	}
	for(var i=0;i<st.length;i++){
	   if(st[i].checked==true){
	   	    ts=st[i].value;
	   }
	}
	if(checkAccount("请输入登录账号",1))
	{	
		if(account==oac){
			form.submit();
			return;
		}
		$.post("<%=basePath%>/sys/user/checknameuniqueness", {"account" :account}, function(data) {
		if (data == "0"){
			form.submit();
		}
		if (data == "1"){
			alert("登录账号存在!");
			$("#account")[0].focus();
			return false;
		}
	});
  }
}


function trim(str){
   var strnew=str.replace(/^\s*|\s*$/g,"");
   return strnew;
}
function checkPhone(v){
  var phone = v;
  var re= /(^1[3|5|8|7][0-9]{9}$)/;
  var tips = '';
  if(v==''){
  	alert("请输入手机号！");
  	return false;
  }
  if(trim(phone)!=''){
     if(!re.test(phone)){
        tips = '手机号格式错误!';
        alert("手机号错误！");
        return false;
     }else{
        tips = '手机号格式正确!';
        return true;
     }
  }else{
  	alert("请输入手机号！");
  	return false;
  }
}

function doSave() {
		if(checkAccount("请输入登录账号",2))
		if(checkVal("password",200,"请输入用户密码!",false))
		if(checkVal("userName",20,"请输入用户真实姓名!",false))
		//if(checkVal("mobile",13,"请输入手机号码!",false))
		if(checkPhone($("#mobile").val()))
		if(checkVal("email",50,"请输入邮箱!",false))
		if(checkVal("roleId",50,"请选择用户默认角色!",false))
		if(checkVal("organizationId",100,"用户所属组织不能为空!",false))
		if(checkEmail("email"))
		if(isMobilAdd("mobile"))
		checkNamexUniqueness();
		
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
function setOrg(id,na){
	document.getElementById("div1").style.display='none';
	document.getElementById("newName").style.display='block';
	document.getElementById("organizationId").value=id;
	document.getElementById("organizationName").value=na;
}
function setAll(){
	document.getElementById("organizationId").value="";
	document.getElementById("organizationName").value="";
	document.getElementById("div1").style.display='block';
	document.getElementById("newName").style.display='none';
}

function isMobilAdd(s) {
	var sMobile = document.getElementById(s);
	if(!(/^1[3|4|5|8|7][0-9]\d{4,8}$/.test(sMobile.value))){ 
		alert("不是完整的11位手机号或者正确的手机号前七位"); 
		sMobile.focus(); 
		return false; 
	}else
		return true;
}
</script>
	</head>

	<body class="bg_c_g">
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 > 用户管理 > 编辑用户
		</div>
		<table class="gl_m_r_n_tb01" border="0" cellspacing="0"
			cellpadding="0">
			<tr>


				<td valign="top" style="display: block;">

					<div class="gl_bt_bnt01">
						用户信息
					</div>
					<form name="form" id="form" method="post"
						action="<%=basePath%>/sys/user/updateSysUser">
						<table class="gl_table_a01" width="100%" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<th width="14%">
									用户登录帐号:
								</th>
								<td width="18%">
									<input class="gl_text01_a" type="text" name="account"
										value="${user.account}" id="account"
										onkeyup="value=value.replace(/[^\w\/]/ig,'')" />
								</td>
								<th width="14%">
									用户姓名:
								</th>
								<td width="18%">
									<input class="gl_text01_a" type="text" name="userName"
										value="${user.userName}" id="userName" />
								</td>
								<th width="14%">
									用户手机号:
								</th>
								<td width="18%">
									<input class="gl_text01_a" type="text" name="mobile"
										id="mobile" value="${user.mobile}" maxlength="11"/>
								</td>
							</tr>
							<tr>
								<th>
									用户邮箱:
								</th>
								<td>
									<input class="gl_text01_a" type="text" name="email" id="email"
										value="${user.email}" />
								</td>
								<th>
									用户默认角色:
								</th>
								<td>
									<input type="hidden" value="${user.userDefaultRole}" name="roleOld"/>
									<select id="roleId" name="userDefaultRole" class="select_new01">
										<option value="">
											请选择角色
										</option>
										<c:forEach items="${listrole}" var="org">
											<option value="${org.roleId}"
												<c:if test="${user.userDefaultRole==org.roleId}">selected="selected"</c:if>>
												${org.roleName}
											</option>
										</c:forEach>
									</select>
								</td>
								<th>
									是否接受SMS:
								</th>
								<td>
									<input type="radio" value="1" name="isReceiveSms"
										<c:if test='${user.isReceiveSms == "1" }'>checked="checked"</c:if> />
									接受 &nbsp;
									<input type="radio" value="0" name="isReceiveSms"
										<c:if test='${user.isReceiveSms == "0" }'>checked="checked"</c:if> />
									不接受
								</td>
							</tr>
							<tr>
							
								<th>
									账户状态:
								</th>
								<td>
									<label><input type="radio" value="1" name="isActivate"
										<c:if test='${user.isActivate == "1" }'>checked="checked"</c:if> />
									正常 </label>&nbsp;
									<label><input type="radio" value="0" name="isActivate"
										<c:if test='${user.isActivate == "0" }'>checked="checked"</c:if> />
									已注销</label>
								</td>
								<th>
									使用状态:
								</th>
								<td colspan="3">
                                    <label><input type="radio" value="0" name="freezeStatus"
                                           <c:if test='${user.freezeStatus == "0"}'>checked="checked"</c:if> />
                                    正常</label>&nbsp;
									<label><input type="radio" value="2" name="freezeStatus"
										<c:if test='${user.freezeStatus=="2"}'>checked="checked"</c:if> />
									禁用</label>&nbsp;
                                    <label><input type="radio" value="1" name="freezeStatus"
                                                  <c:if test='${user.freezeStatus=="1"}'>checked="checked"</c:if> />
                                        待激活</label>&nbsp;
                                    <label><input type="radio" value="3" name="freezeStatus"
                                                  <c:if test='${user.freezeStatus=="3"}'>checked="checked"</c:if> />
                                        冻结</label>
								</td>
								
								
							</tr>
							<tr>
							   <th>
									用户所属组织:
								</th>
								<td colspan="5">
								    ${orga}
									<%-- <input class="gl_text01_a" type="text" name="orga" id="orga"
										value="${orga}" disabled="disabled" style="width:99%"/> --%>
									<input type="hidden" value="${user.userId}" name="userId" />
									<input type="hidden" value="${user.orderNum}" name="orderNum" />
									<input type="hidden" value="${user.createrId}" name="createrId" />
									<input type="hidden" value="${user.createTime}" name="createTime" />
									<input type="hidden" name="password" id="password" value="${user.password}"/>
									<input type="hidden" value="${user.account}" id="oacc" />
								</td>
							
							</tr>

							<tr>
								<th>
									将组织变更为:
								</th>
								<td colspan="5">
									<input type="hidden" name="organizationId" id="organizationId"
										value="${user.organizationId}" />
									<div style="display: none;" id="newName">
										<input type="text" name="organizationName"
											id="organizationName" disabled="disabled" />
									</div>
									<span id="div1" style="display: block;"> <select
											id="orgName" name="orgName"
											onchange="setNextOrg(this.value,'orgName')"
											class="select_new01">
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
					</form>
					<div class="gl_ipt_03">
						<input name="input" type="button" class="gl_cx_bnt03" value="保 存"
							onclick="doSave()" />
						&nbsp;
						<input name="input" type="button" class="gl_cx_bnt03" value="取 消"
								onclick="window.history.back();" />
<!-- 							onclick="doReturn('${location}')"/> -->
							<!-- <input name="input" type="button" class="gl_cx_bnt03"
										value="取 消" onclick="history.go(-1);" /> -->
					</div>
				</td>
			</tr>

		</table>

	</body>
	<script type="text/javascript" src="/SRMC/rmpb/js/list-common.js"></script>
</html>