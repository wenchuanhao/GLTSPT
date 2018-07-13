<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>编辑模块</title> 
	<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
    <script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
    <!--<script type="text/javascript" src="/SRMC/rmpb/js/test.js"></script>-->

 <script type="text/javascript">
 
  function changeParentModule(){
		var obj = document.getElementById("parentModule");
	 	if(obj.style.display=="none"){
	  		obj.style.display="block";
	 	}
	  	else{
	  		obj.style.display="none";
	  	}
  }
		
  function doSave(){
    var moduleName=jQuery("#moduleName").val();
    var moduleCode =jQuery("#moduleCode").val();
    var seq=jQuery("#seq").val();
    if(null==moduleName || ''==moduleName){
      alert("请输入模块名称!");
      return false;
    }
    if(null==moduleCode || ''==moduleCode){
      alert("请输入模块编码!");
      return false;
    }
    if(null==seq || ''==seq){
      alert("请输入显示顺序!");
      return false;
    }
    var oldCode =jQuery("#oldCode").val();

	if(moduleCode!=oldCode && moduleCode!=""){
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
    if(moduleCode==oldCode){
	   moduleForm.submit();
    }
  }
 
 </script>
  </head>
  
  <body class="bg_c_g">
    <form action="<%=basePath%>sys/module/modifySysModule" method="post" name="moduleForm">
		<input type="hidden" name="moduleId" id="moduleId"  value="${sysModule.moduleId}"/>
		<input type="hidden" name="oldCode" id="oldCode"  value="${sysModule.moduleCode}"/>
	  <div class="gl_m_r_nav">当前位置 : 系统管理 &gt; 管理模块 &gt; 编辑模块</div>
	  <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
	    <tr>
	      <td  valign="top" class="gl_m_r_n_tb01_m"></td>
          <td valign="top">
          
           <div class="gl_bt_bnt01">模块信息</div>
           
            <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <th width="100">是否是根节点:</th>
                 <td>
		 <input type="radio" name="isRoot" id="isRoot"  onclick="changeParentModule()" value="1"  <c:if test="${sysModule.parentCode=='ROOT'}">checked="checked"</c:if> />是
		&nbsp;&nbsp;<input type="radio" name="isRoot" id="isRoot"  onclick="changeParentModule()" value="0" <c:if test="${sysModule.parentCode!='ROOT'}">checked="checked"</c:if>/>否
				   		   
		 </td>
                 <th width="100">父模块:</th>
                 <td>
		 <div id="uboxstyle">
                       <select class="select_new01" id="parentModule" <c:if test="${sysModule.parentCode=='ROOT'}">style="display: none;"</c:if> name="parentCode" class="select_new01">
					    	<option value="-1">-选择-</option>
					    	<c:forEach items="${modules}" var="module">
					    		<option value="${module.moduleCode}" <c:if test="${module.moduleCode==sysModule.parentCode}">selected="selected"</c:if>>-${module.moduleName}-</option>
						    	<c:forEach items="${module.nextList}" var="v2">
						    		<option value="${v2.moduleCode}" <c:if test="${v2.moduleCode==sysModule.parentCode}">selected="selected"</c:if>>-----${v2.moduleName}-</option>
							    	<c:forEach items="${v2.nextList}" var="v3">
							    		<option value="${v3.moduleCode}" <c:if test="${v3.moduleCode==sysModule.parentCode}">selected="selected"</c:if>>----------${v3.moduleName}-</option>
								    	<c:forEach items="${v3.nextList}" var="v4">
								    		<option value="${v4.moduleCode}" <c:if test="${v4.moduleCode==sysModule.parentCode}">selected="selected"</c:if>>---------------${v4.moduleName}-</option>
								    	</c:forEach>							    		
							    	</c:forEach>						    		
						    	</c:forEach>					    		
					    	</c:forEach>
					    </select>
					 </div>
		 </td>
                 <th width="100">模块名称:</th>
                 <td>
		 <input class="gl_text01_a" type="text" name="moduleName" id="moduleName"  value="${sysModule.moduleName}" style="height:25px; width:250px;" />
		 </td>
               </tr>
               <tr>
                 <th>模块编码:</th>
                 <td>
               <input class="gl_text01_a" type="text" name="moduleCode" id="moduleCode" value="${sysModule.moduleCode}" style="height:25px; width:250px;" />
                 </td>
                 <th>是否是菜单:</th>
                 <td>
		 <input type="radio" name="isMenu" id="isMenu" <c:if test='${sysModule.isMenu == "1" }'>checked="checked"</c:if> value="1" />是
						&nbsp;&nbsp;<input type="radio" name="isMenu" id="isMenu" <c:if test='${sysModule.isMenu == "0" }'>checked="checked"</c:if> value="0"/>否
                </td>
                 <th>请求路径:</th>
                 <td>
                 <input class="gl_text01_a" type="text" name="url" id="url" value="${sysModule.url}" style="height:25px; width:250px;"  />
		</td>
               </tr>
               <tr>
                 <th>显示排序:</th>
                 <td >
		    <input class="gl_text01_a" type="text"  name="seq" id="seq" value="${sysModule.seq}" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "   onkeyup="value=value.replace(/[^\w\/]/ig,'')" style="height:25px; width:250px;" />
		 </td>
                 
                 
              
                 <th>菜单级别:</th>
                 <td>
		  <div id="uboxstyle">
	                     <select name="menuLevel" class="select_new01"> 
							<option value="1" <c:if test='${sysModule.menuLevel == "1" }'>selected="selected"</c:if>>一级菜单</option>
							<option value="2" <c:if test='${sysModule.menuLevel == "2" }'>selected="selected"</c:if>>二级菜单</option>
							<option value="3" <c:if test='${sysModule.menuLevel == "3" }'>selected="selected"</c:if>>三级菜单</option>
							<option value="4" <c:if test='${sysModule.menuLevel == "4" }'>selected="selected"</c:if>>四级菜单</option>
						  </select>
	                   </div>
		</td>
                 <th>菜单图标:</th>
                 <td>
                 <input class="gl_text01_a" type="text" name="imgName" id="imgName"  value="${sysModule.imgName}"/>
				</td>
                </tr>
		<tr>
		<th width="100">
			模块描述:
			</th>
		<td colspan="5" >
			<textarea name="description" id="description" class="gl_text01_c">${sysModule.description}</textarea>
		</td>
		</tr>
             </table>
           
               
            <div class="gl_ipt_03">
		        <input name="input" type="button" class="gl_cx_bnt03" value="保 存" onclick="doSave();"/>&nbsp;
             <!-- <input name="input" type="button" class="gl_cx_bnt03" value="返 回" onclick="javascript:history.back();"/> -->
                <input name="input" type="button" class="gl_cx_bnt03" value="返 回" onclick="doReturn('${location}')"/>
            </div>
          </td>
	    </tr>
     </table>
	</form>	
	<script type="text/javascript">		
		 function doReturn(location){
		            var path = "<%=basePath%>" + location;
		            window.location.href = path;
		        }
	   </script>
  </body>
</html>
