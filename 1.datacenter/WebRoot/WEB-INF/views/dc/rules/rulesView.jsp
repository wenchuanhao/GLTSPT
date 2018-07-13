<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/minimal/blue.css" rel="stylesheet"><!-- icheck -->
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!--文件上传样式，js -->
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script>

	<!--公共JS -->
	<script type="text/javascript" >var basePath = "<%=basePath%>rulesController";var isCcFlag = "0";</script>
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<span class="searchCondition_header">制度详情</span>
	</div>
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<!-- 附件临时ID -->
		<input type="hidden" name="fileTempId" id="fileTempId" value="${fileTempId}"/>
		<tr>
		    <th width="30%" align="right">制度名称：</th>
		    <td>
		    	${rulesInfo.rulesName}
		    </td>
	    </tr>
	    <tr>
		    <th align="right">制度等级：</th>
		    <td id="rulesGrade_td">
		    	<jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="RULES_GRADES_CONFIG"></jsp:param>
					<jsp:param name="paramCode" value="${rulesInfo.rulesGrade }"></jsp:param>
				</jsp:include> 
		    </td>
	    </tr>
	    <tr>
	    	<th align="right">制度分类：</th>
	    	<td>
	    		${rulesType.parentTypeName}/${rulesType.typeName}
	      	</td>
	    </tr>
	    
	    <tr id="fi_box_tr_1">
		    <th align="right">制度文件：</th>
		    <td id="fi_box_td_1" value="1">
		    </td>
    	</tr>
		
		<tr id="fi_box_tr_2">
		    <th align="right">制度附件：</th>
		    <td id="fi_box_td_2" value="2">
		    </td>
		
		</tr>
	    <tr id="fi_box_tr_3">
		    <th align="right">发布依据：</th>
		    <td id="fi_box_td_3" value="3">
		    </td>
	    </tr>
  </table>
  
	<div class="ge01"></div>
	<div class="tabpages">
	  <ul class="l">
	    <li class="current">相关文档</li>
	  </ul>
	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr >
	    <th>序号</th>
	    <th>名称</th>
	    <th>文件</th>
	    <th>上传人</th>
	    <th>上传时间</th>
	    </tr>
	    <c:forEach items="${fileUpload}" var="fileUpload" varStatus="i">
	    <c:if test="${not empty fileUpload.fileList}">
	  	<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="tr_id_${item[0]}">
		    <td>${i.count }</td>
		    <td>${fileUpload.abstractName }</td>
		    <td>
		    	<c:forEach items="${fileUpload.fileList}" var="item" varStatus="j">
			    		<a style="color: #007fcc;" class="fileName l" href="<%=basePath%>rulesController/downloadRulesFile?fileId=${item.fileId }">
			    			${item.fileName }
			    			<c:if test="${item.fileSize / 1024 > 1024}">
				    			(<fmt:formatNumber type="number" value="${item.fileSize / 1024 / 1024}" maxFractionDigits="2"/>MB)
			    			
			    			</c:if>
			    			<c:if test="${item.fileSize / 1024 <= 1024}">
				    			(<fmt:formatNumber type="number" value="${item.fileSize / 1024}" maxFractionDigits="2"/>KB)
			    			</c:if>
			    		</a>
		    		</br>
		    	</c:forEach>
		    </td>
		    <td>${fileUpload.createUsername}</td>
		    <td>
		    	<fmt:formatDate value="${fileUpload.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		    </td>
		 </tr>
		 </c:if>
		 </c:forEach>
	</table>
	
	
	<div class="ge01"></div>
	<div class="tabpages">
	    <ul class="l">
	      <li class="current">流程跟踪</li>
	    </ul>
	</div>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
<!-- 	流程图 -->
    <tr>
    	<td colspan="8">
	      	<table width="500" border="0" align="center" cellpadding="0" cellspacing="0" class="flowTable">
				  	<tr>
				    <td width="140"><span>制度创建</span></td>
				    <td width="40">&nbsp;</td>
				    <td width="140"><span>制度发布</span></td>
				    <td width="40">&nbsp;</td>
				    <td width="140"><span>制度废止</span></td>
				  </tr>
				  <tr>
				    <td class="bg01"><img src="/SRMC/dc/images/circle01.png" width="43" height="43" /></td>
				    <td class="bg02">&nbsp;</td>
				    <c:choose>
					    <c:when test="${rulesInfo.status == 3 || rulesInfo.status == 4}">
					    	<td class="bg02"><img src="/SRMC/dc/images/circle01.png" width="43" height="43" /></td>
					    </c:when>
					    <c:otherwise>
					    	<td class="bg02"><img src="/SRMC/dc/images/circle00.png" width="43" height="43" /></td>
					    </c:otherwise>
					</c:choose>
				    <td class="bg02">&nbsp;</td>
				    <c:choose>
					    <c:when test="${rulesInfo.status == 4}">
					    	<td class="bg03"><img src="/SRMC/dc/images/circle01.png" width="43" height="43" /></td>
					    </c:when>
					    <c:otherwise>
					    	<td class="bg03"><img src="/SRMC/dc/images/circle00.png" width="43" height="43" /></td>
					    </c:otherwise>
					</c:choose>
				  </tr>
				  <tr>
				    <td height="20"><i>${rulesFlowInfoCJ.handelUsername }</i></td>
				    <td>&nbsp;</td>
				    <c:choose>
					    <c:when test="${rulesInfo.status == 3 || rulesInfo.status == 4}">
						    <td><i>${rulesFlowInfoFB.handelUsername }</i></td>
					    </c:when>
					    <c:otherwise>
					    	<td>&nbsp;</td>
					    </c:otherwise>
				    </c:choose>
				    <td>&nbsp;</td>
				    <c:choose>
					    <c:when test="${rulesInfo.status == 4}">
						    <td><i>${rulesFlowInfoFZ.handelUsername }</i></td>
					    </c:when>
					    <c:otherwise>
					    	<td>&nbsp;</td>
					    </c:otherwise>
					</c:choose>
				  </tr>
				  <tr>
				    <td height="20"><i><fmt:formatDate value="${rulesFlowInfoCJ.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></i></td>
				    <td>&nbsp;</td>
				    <c:choose>
					    <c:when test="${rulesInfo.status == 3 || rulesInfo.status == 4}">
						    <td><i><fmt:formatDate value="${rulesFlowInfoFB.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></i></td>
					    </c:when>
					    <c:otherwise>
					    	<td>&nbsp;</td>
					    </c:otherwise>
				    </c:choose>
				    <td>&nbsp;</td>
				    <c:choose>
					    <c:when test="${rulesInfo.status == 4}">
						    <td><i><fmt:formatDate value="${rulesFlowInfoFZ.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></i></td>
					    </c:when>
					    <c:otherwise>
					    	<td>&nbsp;</td>
					    </c:otherwise>
					</c:choose>
				  </tr>
			</table>
    	</td>
    </tr>
	<jsp:include flush="true" page="include/rulesFlowInfos.jsp"></jsp:include>
</table>
<div  align="center"  style="margin-top: 10px;margin-bottom: 10px;">
	<input name="" type="button" class="btn_common01" onclick="javascript:window.history.back();" value="返回" />
</div>
</div>
</body>
</html>

<script type="text/javascript">
$(function(){
	queryFileListView("#fi_box_td_1");
	queryFileListView("#fi_box_td_2");
	queryFileListView("#fi_box_td_3");
});
$(document).ready(function(){
	
});
/**
 * 查询文件列表
 * @param target
 */
function queryFileListView(target){
  //当每个文件上传完成后的操作
  var fileTempId = jQuery("#fileTempId").val();
  var type = jQuery(target).attr("value");
  var url = basePath+"/queryFileList";
  var params = {
  		'fileTempId':fileTempId,
  		'type':type
  };
  jQuery.post(url, params, function(data){
      var temp = '';
      $.each(data,function(k,v){
	        var file_size = '';
	        if(v.fileSize / 1024 > 1024){
	        	file_size = (v.fileSize / 1024 / 1024).toFixed(2) + "MB";
	        }else{
	        	file_size = (v.fileSize / 1024).toFixed(2) + "KB";
	        }
	        temp +=  '<a href="'+basePath+'/downloadRulesFile?fileId='+v.fileId+'">'+(k+1)+'、'+v.fileName+'('+file_size+')</a><br/>';
      });
      jQuery(target).html(temp);
  } , 'json');

}
</script>