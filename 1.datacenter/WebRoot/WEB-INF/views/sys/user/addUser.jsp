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
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dtree.css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/rmpb/js/dtree2.js" type=text/javascript></script>
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>

<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
<script type="text/javascript">
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
		$.post("<%=basePath%>/sys/user/userchecknameuniqueness", {"account" :account}, function(data) {
		if (data == "0"){
			$.post("<%=basePath%>/sys/user/addSysUser", jQuery("#form").serializeArray(),function(s){
				if(s=="y"){
					alert("保存成功！");
					//location.reload();
					st=1;
					var url="<%=basePath%>/sys/user/addUser?st="+st;
					window.location.href=url;
					
				}else{
				    alert("保存失败！");
				}
			});
			
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
//密码格式验证
function checkValueStyle(v){
	//大写字母
	var reO=/^[A-Z]+$/;
	if(!reO.test(v)){
		alert("您输入的密码格式不正确，密码需要包含大写字母、小写字母以及数字!");
		return  false;
	}
	//小写字母
	var reT=/^[a-z]+$/;
	if(!reT.test(v)){
		alert("您输入的密码格式不正确，密码需要包含大写字母、小写字母以及数字!");
		return false;
	}
	//数字
	var reF=/^[0-9]*$/;
	if(!reF.test(v)){
		alert("您输入的密码格式不正确，密码需要包含大写字母、小写字母以及数字!");
		return false;
	}
	
	return true;
}
function doSave() {
		if(!checkAccount("请输入登录账号",2)){
			return false;
		}
		var pwd=document.getElementById("password").value;
		var r=/^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{6,24}$/;
		//var re=/^[\u4e00-\u9fa5]+$/gi;
		var pattern=/[^\x00-\xff]/g;
		var reg =/\s/;
		if(pwd==""){
			alert("请输入密码！");
			return false;
		}else{
		if(!r.test(pwd)){
            	alert("您好，您设置的密码强度太弱！\n用户密码需满足以下规则：至少包含字母（大小写其中一种皆可）和数字；特殊符号等可选；至少6位，最高24位。");
            	return false;
        	}
			if(pattern.test(pwd)){
			 	alert("密码不允许输入中文，中文符号或全角字符。");
            	return false;
			}
			if(reg.test(pwd)){
				alert("密码不能包含空格！");
            	return false;
			}
		}
		
        /**
		var countDX=0;
		var countXX=0;
		var countSZ=0;
		var aLLcount=0;
		for(var i=0;i<pwd.length;i++){
			if(pwd[i]=='A'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='a'){
				countXX++;
				aLLcount++;
			}
			if(pwd[i]=='B'){
			    countDX++;
			    aLLcount++;
			}
			if(pwd[i]=='b'){
			    countXX++;
			    aLLcount++;
			}
			if(pwd[i]=='C'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='c'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='D'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='d'){
				 aLLcount++;
				 countXX++;
			}
			if(pwd[i]=='E'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='e'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='F'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='f'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='G'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='g'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='H'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='h'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='I'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='i'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='J'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='j'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='K'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='k'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='L'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='l'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='M'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='m'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='N'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='n'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='O'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='o'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='P'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='p'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='Q'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='q'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='R'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='r'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='S'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='s'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='T'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='t'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='U'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='u'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='V'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='v'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='W'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='w'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='X'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='x'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='Y'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='y'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='Z'){
				countDX++;
				aLLcount++;
			}
			if(pwd[i]=='z'){
				 countXX++;
				 aLLcount++;
			}
			if(pwd[i]=='0'){
				countSZ++;
				aLLcount++;
			}
			if(pwd[i]=='1'){
				countSZ++;
				aLLcount++;
			}
			if(pwd[i]=='2'){
				countSZ++;
				aLLcount++;
			}
			if(pwd[i]=='3'){
				countSZ++;
				aLLcount++;
			}
			if(pwd[i]=='4'){
				countSZ++;
				aLLcount++;
			}
			if(pwd[i]=='5'){
				countSZ++;
				aLLcount++;
			}
			if(pwd[i]=='6'){
				countSZ++;
				aLLcount++;
			}
			if(pwd[i]=='7'){
				countSZ++;
				aLLcount++;
			}
			if(pwd[i]=='8'){
				countSZ++;
				aLLcount++;
			}
			if(pwd[i]=='9'){
				countSZ++;
				aLLcount++;
			}
			
		}
		if(pwd.length!=aLLcount){
			alert("您设置的密码强度太弱！"+"\n"+"密码需要包含大写字母、小写字母以及数字，密码长度为8~24位。");
			return  false;
		}
		if(countDX==0){
			alert("您设置的密码强度太弱！"+"\n"+"密码需要包含大写字母、小写字母以及数字，密码长度为8~24位。");
			return  false;
		}
		if(countXX==0){
			alert("您设置的密码强度太弱！"+"\n"+"密码需要包含大写字母、小写字母以及数字，密码长度为8~24位。");
			return  false;
		}
		if(countSZ==0){
			alert("您设置的密码强度太弱！"+"\n"+"密码需要包含大写字母、小写字母以及数字，密码长度为8~24位。");
			return  false;
		}
		**/
		/**
		//大写字母
		var reO=/^[A-Z]+$/;
		if(!reO.match(pwd)){
			
		}
		//小写字母
		var reT=/^[a-z]+$/;
		if(!reT.match(pwd)){
			alert("您输入的密码格式不正确，密码需要包含大写字母、小写字母以及数字!");
			return false;
		}
		//数字
		var reF=/^[0-9]+$/;
		if(!reF.match(pwd)){
			alert("您输入的密码格式不正确，密码需要包含大写字母、小写字母以及数字!");
			return false;
		}
		var ref=/^[A-Za-z0-9]{8}$/;
		if(!ref.test(pwd)){
			alert("您输入的密码格式不正确，密码格式为大小写字母加数字共八位数!");
			return false;
		}
		**/
		//if(checkVal("userName",20,"请输入用户真实姓名!",false))
		var username=document.getElementById('userName').value;
		//alert(username);
		if(username.length <0 || username==""){
			alert("请输入用户真实姓名!");
			return false;
		}
		if(username.length>20){
			alert("姓名超长,仅能输入20个以内的字节,请重新输入.");
			return false;
		}
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
			url:"<%=basePath%>sys/user/setUserNestOrg",
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
function doReturn(location){
    var path = "<%=basePath%>" + location;
    window.location.href = path;
}
</script>
</head>

<body class="bg_c_g" onload="clearCookieDD();">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 用户管理 > 录入用户</div>
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
             <td width="248" valign="top">
               
               <div class="gl_bt_bnt01">组织架构</div>
              
               <div class="gl_bnt_tree01">
               <div class="gl_bnt_tree02" style="display: block;"></div>
               
                 <!--tree-->
                 <div class="d_tree_div">
           <script type="text/javascript">
		d = new dTree('d');
				<c:forEach items="${orgs}" var="org">
					<c:choose>
					<c:when test="${org.orgName == '南方基地'}">
							d.add('${org.organizationId}', '-1', "<font loadMenu='${org.organizationId}'= color=#EF5900><b>公司组织架构</b></font>", 'setAll()', '', 'sysM_mainFrame', '','//');
						</c:when>
						<c:otherwise>
							d.add('${org.organizationId}','${org.parentId}', "<font loadMenu='${org.organizationId}'>${org.orgName}</font>", "setOrg('${org.organizationId}','${org.orgName}')", '', 'sysM_mainFrame');
						</c:otherwise>
					</c:choose>	
				</c:forEach>
			document.write(d);
			
			
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
             <td valign="top" style="display: block;">
            
               <div class="gl_bt_bnt01" style="margin-left: 20px">用户信息</div>
               <form name="form" id="form" method="post" action="<%=basePath%>/sys/user/addSysUser">
               <input type="hidden" value="${st}" id="st"	name="st"/>             
               
               <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-left: 20px">
                <tr>
                   <th width="100" >用户登录帐号：</th>
                   <td><input class="gl_text01" style="width: 90%" type="text" name="account" id="account"  onkeyup="value=value.replace(/[^\w\/]/ig,'')" /></td>
                   <th width="100">用户登录密码：</th>
                   <td><input class="gl_text01" style="width: 90%" type="password" name="password" id="password"/></td>
                 </tr>
                 <tr>
                   <th width="100">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户姓名：</th>
                   <td><input class="gl_text01" style="width: 90%" type="text" name="userName" id="userName" /></td>
                   <th width="100">用户手机号码：</th>
                   <td><input class="gl_text01" style="width: 90%" type="text" name="mobile" id="mobile" maxlength="11"/></td>
                 </tr>
                <tr>
                   <th width="100">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户邮箱：</th>
                   <td><input class="gl_text01" style="width: 90%" type="text" name="email" id="email" /></td>
                   <th width="100">用户默认角色：</th>
                   <td>
                       <select id="roleId" name="userDefaultRole" class="select_new01" style="width: 90%">
                         <option value="">请选择角色</option>
                          <c:forEach items="${listrole}" var="org">
						       <option value="${org.roleId}" >${org.roleName}</option>
						  </c:forEach>
                       </select>
                    </td>
                 </tr>
                  <tr>
<!--                    <th width="100">是否接受SMS：</th> -->
<!--                    <td> -->
<!--                        <input type="radio" value="1" name="isReceiveSms" checked="checked"/>接受 -->
<!-- 					   &nbsp;&nbsp;<input type="radio" value="0" name="isReceiveSms" />不接受  -->
<!-- 				   </td> -->
                   <th width="100">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账户状态：</th>
                   <td colspan="3">
                       <input type="radio" value="1" name="isActivate" checked="checked"/>正常
                       &nbsp;&nbsp;<input type="radio" value="0" name="isActivate" />已注销</td>
                 </tr>
                 <tr>
                   <th width="100">用户所属组织：</th>
                   <td colspan="3">
                   		<input type="hidden" name="organizationId" id="organizationId"/>
                   		<div style="display: none;" id="newName"><input type="text" name="organizationName" id="organizationName" disabled="disabled"/></div>
                   		<span id="div1" style="display: block;">
						   <select id="orgName" name="orgName" onchange="setNextOrg(this.value,'orgName')" class="select_new01" style="width: 27%">
						     <option value="">请选择组织</option>
						     <c:forEach items="${listOrg}" var="org">
						       <option value="${org.organizationId},${org.orgName }" >${org.orgName }</option>
						     </c:forEach>
						   </select>
						   </span>
                   </td>
                   
                 </tr>
               </table>
                </form>
               <div  class="gl_ipt_03">
        		<input name="input" type="button" class="gl_cx_bnt03" value="保 存" onclick="doSave()"/>&nbsp;
        		<input name="input" type="button" class="gl_cx_bnt03" value="取 消" onclick="window.history.back();"/>
        	   </div>
               </td>
             </tr>
           
               </table>
   
</body>
</html>