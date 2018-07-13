<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加模块</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<!--<script type="text/javascript" src="/SRMC/rmpb/js/test.js"></script>-->

<script type="text/javascript">

		function changeParentModule(){
			var obj = document.getElementById("uboxstyle");
		 	if(obj.style.display=="none"){
		  		obj.style.display="block";
		 	}
		  	else{
		  		obj.style.display="none";
		  	}
		} 
		
	   function checkNamexUniqueness(){
			var moduleCode =jQuery("#moduleCode").val();
			if(moduleCode!=""){
				jQuery.post("<%=basePath%>/sys/module/checknameuniqueness", {
					"moduleCode" :moduleCode
				}, function(data) {
					if (data == "0"){
						moduleForm.submit();
					}
					if (data == "1"){
					alert("模块编码已经存在!");
					}
				});
			}
		}
		
		function doSave() {
		    if(checkVal("moduleName",128,"请输入模块名称!",false))
			if(checkVal("moduleCode",50,"请输入模块编码!",false))
			if(checkVal("seq",10,"请输入显示顺序!",false))
			if(checkValLenth("description",256))
			if(checkValLenth("url",128))
			checkNamexUniqueness();	
	    }
		
		function canels(){
		 window.location.href = "<%=basePath%>sys/module/manageSysModule";
		}
</script>
</head>
<body class="bg_c_g" >
	<form action="<%=basePath%>sys/module/addSysModule" method="post" name="moduleForm">
	  <div class="gl_m_r_nav">当前位置 : 系统管理 &gt;管理模块  &gt; 新增模块</div>
	 <div class="gl_import_m">
          
           <div class="gl_bt_bnt01">模块信息</div>
            <table class="gl_table_a023" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <th width="100">是否是根节点:</th>
                 <td>
                 <input type="radio" name="isRoot" id="isRoot"  checked="checked" onclick="changeParentModule()" value="1" />
		 		是
		 		&nbsp;&nbsp;<input type="radio" name="isRoot" id="isRoot"  onclick="changeParentModule()" value="0"/>
						否
				   
		 </td>
                 <th width="100">父模块:</th>
                 <td ><!-- style="display:none" -->
                 <div id="uboxstyle" style="display:none">
                        <select id="parentCode" name="parentCode" class="select_new01" >
					    	<c:forEach items="${modules}" var="module">
					    		<option value="${module.moduleCode}" >-${module.moduleName}-</option>
						    	<c:forEach items="${module.nextList}" var="v2">
						    		<option value="${v2.moduleCode}" >-----${v2.moduleName}-</option>
							    	<c:forEach items="${v2.nextList}" var="v3">
							    		<option value="${v3.moduleCode}" >----------${v3.moduleName}-</option>
								    	<c:forEach items="${v3.nextList}" var="v4">
								    		<option value="${v4.moduleCode}" >---------------${v4.moduleName}-</option>
								    	</c:forEach>							    		
							    	</c:forEach>						    		
						    	</c:forEach>					    		
					    	</c:forEach>					    	
					    </select>
				</div>
		 </td>
                 <th width="100">模块名称:</th>
                 <td>
		 	<input class="gl_text01_a" type="text" name="moduleName" id="moduleName" />
		 </td>
               </tr>
               <tr>
                 <th>模块编码:
                </th>
                 <td><input class="gl_text01_a" type="text" name="moduleCode" id="moduleCode"/>
              		
                 </td>
                 <th> 是否是菜单:</th>
                 <td>
                 <input type="radio" name="isMenu" id="isMenu" checked="checked" value="1" />
           			       是
           			       &nbsp;&nbsp;<input type="radio" name="isMenu" id="isMenu"  value="0"/>
						否
                 </td>
                 <th>请求路径:</th>
                 <td>
                 <input class="gl_text01_a" type="text" name="url" id="url" />
				</td>
               </tr>
              
               <tr>
                 <th>显示排序:</th>
                 <td>  <input class="gl_text01_a" type="text" name="seq" id="seq" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "   onkeyup="value=value.replace(/[^\w\/]/ig,'')"/>
				   </td>
                 <th>菜单级别:</th>
                 <td>
                 <div id="uboxstyle">
	                     <select name="menuLevel" class="select_new01"> 
								<option value="1">一级菜单</option>
								<option value="2">二级菜单</option>
								<option value="3">三级菜单</option>
								<option value="4">四级菜单</option>
						  </select>
	                   </div>
				</td>
                 
                 <th>菜单图标:</th>
                 <td>
                 <input class="gl_text01_a" type="text" name="imgName" id="imgName" />
				</td>
               </tr>
               <tr>
					<th width="100">
						模块描述:
					</th>
					<td colspan="6" >
						<textarea name="description" id="description" class="gl_text01_c"></textarea>
					</td>
				</tr>
             </table>
        
            <div class="gl_ipt_03">
		        <input name="input" type="button" class="gl_cx_bnt03" value="保 存" onclick="doSave();"/>&nbsp;
		        <input name="input" type="button" class="gl_cx_bnt03" value="取 消" onclick="canels()";/>
            </div>
        </div>
	</form>	
	
</body>
</html>
