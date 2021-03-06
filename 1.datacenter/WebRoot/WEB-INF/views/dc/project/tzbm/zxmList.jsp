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
<title>子项目列表</title>
<base  target="_self"/>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<body>
<div class="gl_import_m" >
	 <form name="form" id="pageForm" method="post"  >
	 	<input type="hidden" value="N" name="isPages" id="isPages"/>
		<input type="hidden" value="${zxm.pageIndex}" id="pageIndex"	name="pageIndex"/>
	    <input type="hidden" value="${zxm.pageSize}" id="pageSize"	name="pageSize"/>
	 </form>
  	<div class="ge01"></div>
  	<div class="tabpages" >
    	<ul class="l" id="tab">
	      		<li id="tab1"  class="current">子项目列表<em></em></li>
	      		<li id="tab2" >投资编码详情<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<c:if test="${role ne null && role ne 'GC_QTRY'}">
    		<a class="btn_common01" href="javascript:ev_add()" /><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>
    		</c:if>    		
    		<a class="btn_common01" href="javascript:window.close();" /><img src="/SRMC/dc/images/btnIcon/invalid.png" /><span>关闭</span></a>
		</div>    	
  	</div>

	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" class="listTable"   id="table1">
	  <tr>
			<th>项目类型</th>
			<th>子项目编号</th>
			<th>子项目名称</th>
			<th>子项目管理员</th>
			<th>子项目审核人</th>
			<th>创建时间</th>
			<th>操作</th>
	  </tr>
	  	 
	  	 <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
		    <td>
					${vo.column01 ne '1' ? '':'软件工程'}
					${vo.column01 ne '2' ? '':'集成工程'}
					${vo.column01 ne '3' ? '':'土建工程'}
					${vo.column01 ne '4' ? '':'征地工程'}			    
		    </td>
		    <td>${vo.column02}</td>
		    <td>${vo.column03}</td>
		    <td>${vo.column09Name}</td>
			<td>${vo.column05Name}</td>
		    <td><fmt:formatDate value="${vo.column08}" pattern="yyyy-MM-dd "/></td>
			<td>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/export.png"><a href="javascript:void(0);" onclick="ev_openFile('${vo.id}','${vo.column01}')">文档</a></span>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="javascript:void(0);" onclick="ev_ht('${vo.id}')">合同</a></span>
		    	<c:if test="${role ne null && (role eq 'GC_YWGLY' or (role eq 'GC_XMFZR' && fn:contains(vo.column09, sysUserId)))}">
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/publish.png"><a href="javascript:ev_zb('${vo.id}')">周报</a></span>
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="<%=basePath%>zxm/add1?id=${vo.id}">编辑</a></span>
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${vo.id}')">删除</a></span>
		    	</c:if>
			</td>		    		    
		 </tr>
		 </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="7">找不到对应的数据</td></tr>
	   </c:if>		 
	</table>
	
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" style="display: none;" id="table2">
		<jsp:include page="../tzbm/form.jsp" />
  </table>
  	
 	<div class="gd_page">
 	<jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
	var m = "${mess}";
	if(m != "" && m == "s"){
		alert("删除成功");
	}
	else if(m != "" && m == "e"){
		alert("删除失败");
	} 
 
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});

function ev_add(){
	window.location.href =  "<%=basePath%>zxm/add1?tzbmId=${tzbmId}";
}

function ev_delete(id){
	if(confirm("确定删除？")){
		jQuery.ajax({
	        type:"POST",
	        async:true,
	        url:"<%=basePath%>zxm/delete",
	        data:"id=" + id,
	        dataType:"json",
	        success:function(data){
	        	if(data == "1"){
	        		alert("删除成功");
	        		ev_search();
	        	}else{
	        		alert("删除失败");
	        	}
	        },
	        error:function(){
	        	alert("删除失败");
	        }
    	});
	}
}

$(function(){
	$("#tab").find("li").each(function(){
		var id = $(this).attr("id");
		$(this).hover(
			function(){
				if(id == "tab1"){
					$("#tab1").addClass("current"); 
					$("#tab2").removeClass("current");
					$("#table1").show();
					$("#table2").hide();
				}else{
					$("#tab1").removeClass("current"); 
					$("#tab2").addClass("current");				
					$("#table1").hide();
					$("#table2").show();				
				}
			},
			function(){
			}
		);
	});
});

function ev_zb(id){
	ev_submit("<%=basePath%>zb/add?proType=3&code="+id);
}

//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>tzbm/zxmList?id=${tzbmId}";
	document.forms[0].submit();
}

function ev_submit(url){
	document.forms[0].action = url;
	document.forms[0].submit();
}
function ev_ht(id){
	window.open("<%=basePath%>zxm/htList?id="+id);
}

function ev_openFile(id,type){
	if(type == ""){
		alert("没有关联投资编码，不能上传文档");return;
	}
	window.open("<%=basePath%>zxm/fileList?id="+id+"&type="+type);
}
</script>
</body>
</html>
