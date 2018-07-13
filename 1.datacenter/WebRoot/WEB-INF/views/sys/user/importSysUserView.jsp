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
		<title>批量导入用户</title>
		<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/ajax.js"></script>
		<script type="text/javascript">
			 //选择所属组织
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
			//下载模板
			    function exportModel(){
		            importform.action='<%=basePath%>sys/user/dolownTemplate/'+ Math.random();
		            importform.submit();
		       }
		       
		         //检查文件类型
			   	function checkType(fileName){
					if(fileName.lastIndexOf(".")!=-1){
						var fileType = (fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)).toLowerCase();
						var suppotFileTXT = "xls";
						if(suppotFileTXT == fileType){
							return true;
						}
						alert("不支持文件类型: "+fileType);
						return false;
					}else{
						alert("文件类型不支持");
						return false;
					}
				}
		      function clean(){
		      	$("#orgName").val("");
		      	setNextOrg("-1,-1","");
		      	$("#commonupload").val("");
		      	var file_input_obj = document.getElementById("commonupload");//.outerHTML.replace(/(value=\").+\"/i,"$1\"");
		      	file_input_obj.outerHTML=file_input_obj.outerHTML.replace(/(value=\").+\"/i,"$1\"");
		      	$("#roleId2").val("");
		      }
		      //导入用户信息（Excel文件）
		      function toSaveImport(){
		          var commonupload=$("#commonupload").val();
		          if(null==commonupload || ""==commonupload){
		            alert("请选择要导入的文件!");
		            return false;
		          }
		          if(checkType(commonupload) == false){
			          return false;
			      }
		          
		          
		          var organizationName=$("#organizationName").val();
		          if(null==organizationName || ''==organizationName){
		            alert("请选择用户所属组织!");
		            return false;
		          }
		          var roleId=$("#roleId2").val();
		           if(null==roleId || ''==roleId){
		            alert("请选择用户所属角色!");
		            return false;
		          }
		          var formId = '#importform';
		          var controller = "<%=basePath%>sys/user/importSysUser/"+Math.random();
		          $(formId).attr('action',controller);
		          $(formId).submit();
		         
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
		</script>
		
	</head>
	<body class="bg_c_g">
	  <form name="importform" id="importform" method="post" action="" enctype="multipart/form-data">
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 > 用户管理 > 批量导入用户
		</div>

		<div class="gl_import_m">
			<div class="gl_bt_bnt01">
				用户信息批量导入
			</div>
			<div class="gl_ipt_01">
				<img src="/SRMC/rmpb/images/usr_bj.gif"/>
			</div>
			<div class="ge_a01b"></div>
			<div class="gl_ipt_02" >
			<!--  
				<div style="text-align: center;">
				<table width="96%" align="center">
				<tr><td></td></tr>
						<c:if test="${listObject!=null}">
						  <tr><td><font color="red" size="3">导入失败！</font></td></tr>
						  <c:forEach items="${listObject}" var="objects">
						     <tr>
						       <td>
						       <font color="red" size="3"> 
						        <c:choose>
						          <c:when test="${objects.row ne null}">
						              第${objects.row}行出错，出错原因：${objects.failReason}   请修正，再导入。
						          </c:when>
						           <c:when test="${objects.row ne null || objects.sheetIndex eq -1}">
						              ${objects.failReason} 
						          </c:when>
						          <c:otherwise>
						              出错原因：${objects.failReason} 请修正，再导入。
						          </c:otherwise>
						        </c:choose> 
						       </font>
						       </td>
						     </tr>
						  </c:forEach> 
						</c:if>
						<c:if test="${flag eq 'ok'}">
						  <tr>
						    <td><font color="green" size="3">导入成功！</font></td>
						  </tr>
						</c:if>
			</table>
				</div>
				-->
				<table class="gl_ipt_03_tb" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th width="150">
							批量导入模板下载：
						</th>
						<td>
							<input name="" type="button" class="ipt_tb_n01" value="" onclick="exportModel()"/>
						</td>
					</tr>
					<tr>
						<th>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户所属组织：
						</th>
						<td>
						    <input type="hidden" name="organizationId" id="organizationId"/>
						    <input type="hidden" name="organizationName" id="organizationName"/>
							<span id="div1">
						   <select id="orgName" name="orgName" onchange="setNextOrg(this.value,'orgName')" class="select_new01"  style="width: 125px; float:left;">
						     <option value="">请选择组织</option>
						     <c:forEach items="${listOrg}" var="org">
						       <option value="${org.organizationId},${org.orgName }" >${org.orgName }</option>
						     </c:forEach>
						   </select>
						 </span>
						</td>
					</tr>
					<tr>
						<th>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户默认角色：
						</th>
						<td>
							<span id="div2">
						   <select id="roleId2" name="roleId" class="select_new01" style="width: 125px;">
						     <option value="">-请选择角色-</option>
						     <c:forEach items="${listrole}" var="org">
						       <option value="${org.roleId}" >${org.roleName}</option>
						     </c:forEach>
						   </select>
						  
						 </span>
						</td>
					</tr>
					<tr>
						<th>
							选择批量用户文件：
						</th>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="268px">
										<input class="gl_text01_a" type="file"  name="commonupload" id="commonupload" style="width:290px"/>
									</td>
									
								</tr>
							</table>
						</td>
					</tr>
				</table>

			
			</div>
				<div style="text-align: center;">
				<table width="96%" align="center">
				<tr><td></td></tr>
						<c:if test="${listObject!=null}">
						  <tr><td><font color="red" size="3">导入失败！</font></td></tr>
						  <c:forEach items="${listObject}" var="objects">
						     <tr>
						       <td>
						       <font color="red" size="3"> 
						        <c:choose>
						          <c:when test="${objects.row ne null}">
						              第${objects.row}行出错，出错原因：${objects.failReason}   请修正，再导入。
						          </c:when>
						           <c:when test="${objects.row ne null || objects.sheetIndex eq -1}">
						              ${objects.failReason} 
						          </c:when>
						          <c:otherwise>
						              出错原因：${objects.failReason} 请修正，再导入。
						          </c:otherwise>
						        </c:choose> 
						       </font>
						       </td>
						     </tr>
						  </c:forEach> 
						</c:if>
						<c:if test="${flag eq 'ok'}">
						  <tr>
						    <td><font color="green" size="3">导入成功！</font></td>
						  </tr>
						</c:if>
			</table>
				</div>
			<div class="gl_ipt_03">
				<input  type="button" class="ipt_tb_n03" onclick="javascript:toSaveImport();" name="import" id="import" value="导 入" />
				&nbsp;
				<input name="" type="button" class="ipt_tb_n03" value="取 消" onclick="clean();"/>
			</div>
			
		</div>
		</form>
	</body>
</html>
