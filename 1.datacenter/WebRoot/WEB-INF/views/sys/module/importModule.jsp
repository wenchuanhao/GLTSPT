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
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/ajax.js"></script>
		<script type="text/javascript">
			//下载模板
			    function exportModel(){
		            importform.action='<%=basePath%>sys/module/dolownTemplate/'+ Math.random();
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
				 window.location.href = "<%=basePath%>sys/module/manageSysModule";
		}
		      //导入菜单（Excel文件）
		      function toSaveImport(){
		          var commonupload=$("#commonupload").val();
		          if(null==commonupload || ""==commonupload){
		            alert("请选择要导入的文件!");
		            return false;
		          }
		          if(checkType(commonupload) == false){
			          return false;
			      } 
		          
	
		          importform.action='<%=basePath%>sys/module/importSysModules/'+ Math.random();
		          importform.submit();
		         
		      }
		</script>
		
	</head>
	<body class="bg_c_g">
	  <form name="importform" id="importform" method="post" action="" enctype="multipart/form-data">
		<div class="gl_m_r_nav">
			当前位置 : 系统管理 > 菜单管理 > 批量导入菜单
		</div>

		<div class="gl_import_m">
			<div class="gl_bt_bnt01">
				菜单批量导入
			</div>
			<div class="ge_a01b"></div>
			<div class="gl_ipt_02" >
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
							选择批量菜单导入文件：
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
