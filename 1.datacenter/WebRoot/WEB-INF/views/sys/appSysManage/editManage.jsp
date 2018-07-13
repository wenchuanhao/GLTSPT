<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新增应用系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<!--<script type="text/javascript" src="/SRMC/rmpb/js/test.js"></script>-->
</head>
<script type="text/javascript">
		function doSave() {
		    if($("#code").val()==""){
		    	alert("请输入系统编码");
		    	return false;
		    }
		    if($("#name").val()==""){
		    	alert("请输入系统名称");
		    	return false;
		    }
		    if($("#adminName").val()==""){
		    	alert("请输入应用管理员名称");
		    	return false;
		    }
		    var phone = $("#adminPhone").val();
		     if(phone==""){
		    	alert("请输入应用管理员电话");
		    	return false;
		    }else{
		    	var reg = /(^(\d{3,4}-)?\d{7,8})$|(1[0-9]{10})$/;  
		    	if(!reg.test(phone)){
		    		alert("手机号码格式不正确");
		    		return false;
		    	}
		    }
		    var email = $("#adminEmail").val();
		     if(email==""){
		    	alert("请输入应用管理员邮箱");
		    	return false;
		    }else{
		    	var reg=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	     		if(!reg.test(email)){
	     			alert("应用管理员邮箱格式不正确");
	     			return false;
	     		}
		     }
			
			document.forms[0].submit();
	    }
		
		function canels(){
			 window.location.href = "<%=basePath%>sys/app/appSysManage";
		}
</script>
<body class="bg_c_g" >
	<form action="<%=basePath%>/sys/app/addAppSysManage?update=1"  method="post" name="moduleForm">
	  <div class="gl_m_r_nav">当前位置 : 系统管理 &gt;应用系统管理  &gt; 编辑应用系统</div>
	 <div class="gl_import_m">
           <div class="gl_bt_bnt01">应用系统信息</div>
            <table class="gl_table_a023" width="100%" border="0" cellspacing="0" cellpadding="0">
            <input type="hidden" id="id" name="id" value="${list[0].id }"/>
            <input type="hidden" id="createDate" name="createDate" value="${list[0].createDate }"/>
            <input type="hidden" id="createUserId" name="createUserId" value="${list[0].createUserId }"/>
            <input type="hidden" id="createUserName" name="createUserName" value="${list[0].createUserName }"/>
               <tr>
                 <th width="100">模块编码:</th>
                 <td>
					<input class="gl_text01_a" type="text" name="code" id="code" value="${list[0].code }"/>				   
		 		</td>
                 <th width="100">系统名称:</th>
                 <td ><!-- style="display:none" -->
                 <input class="gl_text01_a" type="text" name="name" id="name" value="${list[0].name }" />
				 </td>
                 <th width="100">接入的接口类型:</th>
                 <td>
		 	 		<select class="select_new01" id="type" name="type">
	                 	<option value="0" <c:if test="${list[0].type=='0' }">selected="selected"</c:if>  >ws接口</option>
	                 	<option value="1" <c:if test="${list[0].type=='1' }">selected="selected"</c:if> >文件接口</option>
	                 	<option value="2" <c:if test="${list[0].type=='2' }">selected="selected"</c:if> >sql接口</option>
                 </select>
			 	</td>
               </tr>
               <tr>
                 <th>数据分域:
                </th>
                 <td>
                 	<select class="select_new01" id="domain" name="domain">
	                 	<option value="0" <c:if test="${list[0].domain=='0' }">selected="selected"</c:if>>成本类</option>
	                 	<option value="1" <c:if test="${list[0].domain=='1' }">selected="selected"</c:if>>收入类</option>
	                 	<option value="2" <c:if test="${list[0].domain=='2' }">selected="selected"</c:if>>预算类</option>
	                 	<option value="3" <c:if test="${list[0].domain=='3' }">selected="selected"</c:if>>服务量</option>
	                 	<option value="4" <c:if test="${list[0].domain=='4' }">selected="selected"</c:if>>专题类</option>
	                 	<option value="5" <c:if test="${list[0].domain=='5' }">selected="selected"</c:if>>指标类</option>
                 	</select>
                 </td>
                 <th>应用管理员名称:</th>
                 <td>
                 	  <input class="gl_text01_a" type="text" name="adminName" id="adminName" value="${list[0].adminName }"/>
                 </td>
                 <th>应用管理员电话:</th>
                 <td>
                 <input class="gl_text01_a" type="text" name="adminPhone" id="adminPhone" value="${list[0].adminPhone }" />
				</td>
               </tr>
              <tr>
                 <th>应用管理员邮箱:
                </th>
                 <td>
                 	<input class="gl_text01_a" type="text" name="adminEmail" id="adminEmail" value="${list[0].adminEmail }"/>
                 </td>
               </tr>
               <tr>
					<th width="100">
						模块描述:
					</th>
					<td colspan="6" >
						<textarea name="remark" id=remark class="gl_text01_c" >${list[0].remark }</textarea>
					</td>
				</tr>
             </table>
        
            <div class="gl_ipt_03">
		        <input name="input" type="button" class="gl_cx_bnt03" value="保 存" onclick="doSave();"/>&nbsp;
		        <input name="input" type="button" class="gl_cx_bnt03" value="取 消" onclick="canels();"/>
            </div>
        </div>
	</form>	
	
</body>
</html>
