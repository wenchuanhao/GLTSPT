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
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
		<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
		<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />
	</div>
	<form name="form" id="pageForm" method="post"  >
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${rulesForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${rulesForm.pageSize}" id="pageSize"	name="pageSize"/>	
	<input type="hidden" name="types" id="types_input" value="${rulesForm.types}"/>
	<input type="hidden" name="createUserid" id="createUserid_input" value="${rulesForm.createUserid}"/>
	<input type="hidden" id="busTypes_input" name="busTypes" value="${busTypeName }" />
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">名称：</th>
	    <td width="30%"><input id="abstractName" name="abstractName" value="${rulesForm.abstractName }"  type="text"  placeholder="请填写名称" class="text01" style="width:174px;"  /></td> 
  		<th width="9%" align="right">创建人：</th>
	    <td width="30%">
	    <input  onfocus="if(this.value=='请填写创建人'){this.value=''};this.style.color='#333333';autoCompletes();" 
			                     onkeyup="clean()"
			                     onblur="if(this.value==''||this.value=='请填写创建人'){jQuery('#createUserid_input').val('');this.style.color='#b6b6b6';}"
                      name="createUsername" id= "createUsername" value="${rulesForm.createUsername}" type="text" class="text01" style="width:174px;" placeholder="请填写创建人">
<!-- 	    <input id="createUsername" name="createUsername" value="${rulesForm.createUsername }"  type="text"  placeholder="请填写创建人" class="text01" style="width:174px;"  /> -->
	    
	    </td> 
	</tr>
	<tr>
		<th width="9%" align="right">类型：</th>
	    <td width="40%">
	    	<input id="busTypes" name="busTypesValue" value="${busTypesValue }" readonly="readonly" type="text"  class="text01" style="width:174px;"  hidden/>
	    	<select class="ui-select" id="sel_01" style="width:180px;">
				<option <c:if test="${empty rulesForm.types }">selected="selected"</c:if> value="">请选择</option>
	    		<c:forEach items="${busTypesList}" var="item" varStatus="i">
	    			<option ${rulesForm.types==item.typeId ? "selected=\"selected\"":null}  value="${item.typeId}">${item.typeName}</option>
				</c:forEach>
	        </select>
	    </td>
	</tr>
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
      		<li class="current">文档列表<em>(${ITEMPAGE.total })</em></li>
    	</ul>
    	<div class="otherButtons r">
    		<c:if test="${userRoles != '3' }">
				<a class="btn_common01" id="uploadDocumentInput" href="javascript:void(0)" /><img src="/SRMC/dc/images/btnIcon/upload.png" /><span>新增文档</span></a>
				<a class="btn_common01" href="javascript:batchDel();" /><img src="/SRMC/dc/images/btnIcon/del.png" /><span>删除</span></a>
    		</c:if>
		</div>
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	    <th><input id="checkAll" type="checkbox"></th>
	    <th>序号</th>
	    <th>名称</th>
	    <th>文件</th>
	    <th>类型</th>
	    <th>上传人</th>
	    <th>上传时间</th>
	    <th>更新人</th>
	    <th>更新时间</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
	  	<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="tr_id_${item[0].fileId}">
		    <td ><input name="subBox" type="checkbox" createuserid="${item[0].createUserid }" value="${item[0].fileId}"></td>
		    <td >${i.count}</td>
		    <td ><a class="fancybox_Docupload" href="javascript:void(0)" id="a_${item[0].fileId}" value="${item[0].fileId}" >${item[0].abstractName}</a></td>
		    <td class="a_l">
		    		<b class="fileName l">
				<c:forEach items="${item[0].fileList}" var="itemFile" varStatus="it">
			    		<a href="<%=basePath%>rulesController/downloadRulesFile?fileId=${itemFile.fileId }">
			    				${itemFile.fileName }
			    			<c:if test="${itemFile.fileSize / 1024 > 1024}">
				    			(<fmt:formatNumber type="number" value="${itemFile.fileSize / 1024 / 1024}" maxFractionDigits="2"/>MB)
			    			
			    			</c:if>
			    			<c:if test="${itemFile.fileSize / 1024 <= 1024}">
				    			(<fmt:formatNumber type="number" value="${itemFile.fileSize / 1024}" maxFractionDigits="2"/>KB)
			    			</c:if>
			    		</a>
		    	</c:forEach>
		    		</b>
			</td>
		    <td >
		    	<c:choose>
				    <c:when test="${not empty item[1].typeName}">
            			${item[1].typeName}
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
		    </td>
		    <td >${item[0].createUsername}</td>
            <td >
            	<c:choose>
				    <c:when test="${not empty item[0].createTime}">
			            <fmt:formatDate value="${item[0].createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
			</td>
		    <td >
		    	<c:choose>
				    <c:when test="${not empty item[0].updateUsername}">
            			${item[0].updateUsername}
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
			</td>
            <td >
            	<c:choose>
				    <c:when test="${not empty item[0].updateTime}">
            			<fmt:formatDate value="${item[0].updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				    </c:when>
				    <c:otherwise>
				    	-
				    </c:otherwise>
				</c:choose>
            </td>
		 </tr>
	   </c:forEach>
	</table>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
           
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>rulesController";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>

<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript">
//登录用户ID
var loginUserId = "${sessionScope.VISITOR.userId}";
//用户角色
var userRoles = "${userRoles}";

$(function(){

});
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
    });
    var $subBox = $("input[name='subBox']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
    });
    rulesDocUpload('${busTypeName }');
    
   	jQuery('#uploadDocumentInput').fancybox({
		'href' : basePath + '/docUploads?busTypes=${busTypeName }',
		//		'width' : '50%',
// 		'height' : '50%',
		'autoScale' : true,
		'transitionIn' : 'none',
		'transitionOut' : 'none',
		'type' : 'iframe',
		'scrolling' : 'yes',
		'centerOnScroll' : true,
		'onStart' : function(current, previous) {
		}
	});
    //文档类型
	var obj1 = $('#sel_01').data('ui-select');
	obj1.onClick = function(value) {
		 $("#types_input").val(value);
	}
    
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>rulesController/documents";
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#abstractName").val("");
	$("#createUserid_input").val("");
	//$("#busTypes_input").val("");
	$("#createUsername").val("");
	$("#types_input").val("");
	$("#sel_01").data('ui-select').val("");
	ev_search();
}

function ajaxDel(fileId){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>rulesController/delRulesFile",
        data:"fileId=" + fileId,
        success:function(data){
        	if(data == 1){
        		ev_search();
        	}else{
        		alert("删除失败！");
        	}
        },
        error:function(){
            alert("删除失败！");
        }
    });
}

//批量删除
function batchDel(){
	var $subBoxChecks = $("input[name='subBox']:checked");
	if($subBoxChecks.length == 0){
		alert("请至少选中一项文档");
		return;
	}
	 
	 //是否有权限删除文件
	 var flag = true;
	$.each($subBoxChecks,function(k,v){
		var createUserId = $(v).attr("createuserid");
		if(loginUserId != createUserId){
			flag = false;
		}
	});
	//制度管理员
     if(userRoles == "1"){
     	flag = true;
     }
	if(!flag){
		alert("抱歉，你没有权限删除别人的文档");
		return;
	}
	
	if(confirm("确定批量删除"+$subBoxChecks.length+"项文档么？")){
		var fileId = '';
		$.each($subBoxChecks,function(k,v){
			if(k == 0){
				fileId += $(v).val();
			}else{
				fileId += ","+$(v).val();
			}
		});
		ajaxDel(fileId);
	}
}



//用户可选择创建人
function autoCompletes(){
		jQuery("#createUsername").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>rulesController/searchUser",
					dataType: "json",
					data: {
						userValue: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
				     			 value:item[0].userName+" -- "+item[1].orgName,
									userName:item[0].userName,
									userId:item[0].userId,
									account:item[0].account,
									orgName:item[1].orgName,
								}
							}));
						}else{
							return false;
						}
					}
				});
			},
			minLength: 1,
			select: function( event, ui ) {
				jQuery("#createUsername").val(ui.item.userName);
				jQuery("#createUserid_input").val(ui.item.userId);
				return false;
			}
		});
	}
function clean(){
	if (event.keyCode == 13) {
        //js 监听对应的id
         //document.getElementById("demandFzrId_input_focus").value="";
      } 
}
</script>
</body>
</html>
