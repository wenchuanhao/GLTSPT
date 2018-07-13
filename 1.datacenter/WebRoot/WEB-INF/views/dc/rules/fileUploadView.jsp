<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<body>
<div class="gl_import_m" style="min-width: 100px;">
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
      		<li class="current">查看制度相关文档</li>
    	</ul>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	    <th>序号</th>
	    <th>名称</th>
	    <th>文件</th>
	    <th>上传人</th>
	    <th>上传时间</th>
	  </tr>
	  <c:forEach items="${fileUpload}" var="fileUpload" varStatus="i">
	  	<c:if test="${not empty fileUpload.fileList}">
	  	<tr id="tr_id_${fileUpload.fileId}">
		    <td>${i.count }</td>
		    <td>${fileUpload.abstractName }</td>
		    <td>
		    	<c:forEach items="${fileUpload.fileList}" var="item" varStatus="j">
			    		<a id="b_${item.fileId }" style="color: #007fcc;" class="fileName l" href="<%=basePath%>rulesController/downloadRulesFile?fileId=${item.fileId }">
			    				${item.fileName }
			    			<c:if test="${item.fileSize / 1024 > 1024}">
				    			(<fmt:formatNumber type="number" value="${item.fileSize / 1024 / 1024}" maxFractionDigits="2"/>MB)
			    			
			    			</c:if>
			    			<c:if test="${item.fileSize / 1024 <= 1024}">
				    			(<fmt:formatNumber type="number" value="${item.fileSize / 1024}" maxFractionDigits="2"/>KB)
			    			</c:if>
			    		</a>
		    		<c:if test="${item.createUserid == sessionScope.VISITOR.userId }">
		    			<i id="i_${item.fileId }" style="float: left;"><img onclick="javascript:deleteFileUpload('${item.fileId }')" src="/SRMC/dc/images/close_icon01.png" width="16" height="16" /></i>
		    		</c:if>
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
	<table width="99%" id="table01" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" style="border:0px;">
		 	<tr >
			   	 <th align="center" height="50">
					<input name="" type="button" class="btn_common02" onclick="javascript:parent.$.fancybox.close();" value="关闭" />
				 </th>
			 </tr>    
	</table>
     <div class="ge01"></div>
</div>
           
<script src="/SRMC/dc/js/jquery.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>rulesController";</script>
<script type="text/javascript">
$(function(){

});
 $(document).ready(function(){
 
});

function deleteFileUpload(v){
    if(confirm("确定要删除此文件？")){
        var url = basePath + "/delRulesFile";
        var params = {
        	fileId:v
        };
        jQuery.post(url, params, function(data){
            if(data == "1"){
            	var $fileTd = $("#b_" + v).parent();
                jQuery("#b_" + v).remove();
                jQuery("#i_" + v).remove();
                //删除到最后一条记录时，清空tr
            	var fileList = $fileTd.find(".fileName");
            	if(fileList.length == 0){
            		$fileTd.parent().remove();
            	}
            }
        }, 'json');
    }
}
</script>
</body>
</html>
