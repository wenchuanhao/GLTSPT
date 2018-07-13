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
<title>子项目合同列表</title>
<base  target="_self"/>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<body>
<div class="gl_import_m" >
	 <form name="form" id="pageForm" method="post"  >
	 	<input type="hidden" value="N" name="isPages" id="isPages"/>
		<input type="hidden" value="${zxmHt.pageIndex}" id="pageIndex"	name="pageIndex"/>
	    <input type="hidden" value="${zxmHt.pageSize}" id="pageSize"	name="pageSize"/>
	 </form>
  	<div class="ge01"></div>
  	<div class="tabpages" >
    	<ul class="l" id="tab">
	      		<li id="tab1"  class="current">子项目合同列表<em></em></li>
	      		<li id="tab2" >子项目详情<em></em></li>
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
			<th>子项目编号</th>
			<th>合同序号</th>
			<th>合同编号</th>
			<th>合同名称</th>
			<th>合同含税<br/>金额（万元）</th>
			<th>合同不含税<br/>金额（万元）</th>
			<th>累计形象进度<br/>MIS接收金额<br/>（万元）</th>
			<th>累计转资金额<br/>（万元）</th>
			<th>累计付款金额<br/>（万元）</th>
			<th>合同对方</th>
			<th>合同状态</th>
			<th>合同签订时间</th>
			<th>合同管理员</th>
			<th>操作</th>
	  </tr>
	  	 <c:if test="${not empty ITEMPAGE.items}">
	  	 <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<c:set var="zxm" value="${vo.zxm}"/>
		    <td>${zxm.column02}</td>
		    <td>${i.count}</td>
		    <td style="word-break:break-all" width="8%"><span><a href="javascript:ev_edit('${vo.id}')">${vo.column01}</a></span></td>
			<td>${vo.column03}</td>
			<td><fmt:formatNumber value="${vo.column11}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo.column09}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo.htKzcolumn07}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo.htKzcolumn09}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo.htKzcolumn11}" type="currency"  maxFractionDigits="6"/></td>
			<td>${vo.column14}</td>
			<td>${vo.column12}</td>
		    <td><fmt:formatDate value="${vo.column19}" pattern="yyyy-MM-dd "/></td>
			<td>${vo.column21Name}</td>
			<td>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/export.png" /><a href="javascript:void(0);" onclick="ev_openFile('${vo.id}','${vo.xmType}')">文档</a></span>
	    		<c:if test="${vo.column21 eq null}"><span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="javascript:ev_rl('${vo.id}')">认领</a></span></c:if>
	    		<c:if test="${vo.column21 ne null && fn:contains(vo.column21, sysUserId)}"><span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="javascript:ev_cancelRL('${vo.id}')" title="解除关联">取消认领</a></span></c:if>
	    		<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="javascript:void(0);" onclick="ev_htKz('${vo.id}')">合同开支</a></span><br/>
		    	<c:if test="${role ne null && (role eq 'GC_YWGLY' or (role eq 'GC_XMFZR' && fn:contains(vo.column21, sysUserId)))}">
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/publish.png"><a href="javascript:ev_zb('${vo.id}')">周报</a></span>
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="<%=basePath%>zxmHt/add1?id=${vo.id}">编辑</a></span>
			    	<span style="display: none;"><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${vo.id}')" title="删除合同，不解除关联">删除</a></span>
		    	</c:if>
			</td>		    		    
		 </tr>
		 </c:forEach>
	  	 <tr style="background-color: #d6ebf9;">
			<td colspan="4">合计：</td>
			<td><fmt:formatNumber value="${zxmHt_T[0]}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${zxmHt_T[1]}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${zxmHt_T[2]}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${zxmHt_T[3]}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${zxmHt_T[4]}" type="currency"  maxFractionDigits="6"/></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		    <td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		 </tr>		 
		 </c:if>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="14">找不到对应的数据</td></tr>
	   </c:if>		 
	</table>
	
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" style="display: none;" id="table2">
		<jsp:include page="../zxm/form.jsp" />
  </table>
  	
 	<div class="gd_page">
		<jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include> 	
     </div>
     <div class="ge01"></div>
</div>
          
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>copperationController";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
<script type="text/javascript" src="/SRMC/dc/cooperation/js/common.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
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

function ev_zb(id){
	ev_submit("<%=basePath%>zb/add?proType=4&code="+id);
}

function ev_htKz(id){
	window.open("<%=basePath%>zxmHt/htKzList?id="+id);
}
function ev_edit(id){
	ev_submit("<%=basePath%>zxmHt/edit?id="+id);
}
function ev_add(){
	window.location.href =  "<%=basePath%>zxmHt/add1?zxmId=${zxmId}";
}
function ev_delete(id){
	if(confirm("确定删除？")){
		jQuery.ajax({
	        type:"POST",
	        async:true,
	        url:"<%=basePath%>zxmHt/delete",
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
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>zxm/htList?id=${zxmId}";
	document.forms[0].submit();
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
function ev_submit(url){
	document.forms[0].action = url;
	document.forms[0].submit();
}
function ev_openFile(id,type){
	if(type == ""){
		alert("没有关联子项目，不能上传文档");return;
	}
	window.open("<%=basePath%>zxmHt/fileList?id="+id+"&type="+type);
}

function ev_rl(id){
	
	var returnValue = ev_selectList();
	if(returnValue.indexOf("_") != -1){
		$.ajax({
			type:"post",
			async:false,
			url: "<%=basePath%>zxmHt/modifyColumn21?type=N&ids="+id+"&returnValue="+returnValue,
			dataType:"text",
			success:function(result){
				if(result != "" && result == "s"){
					alert("认领成功");
					ev_search();
				}else{alert("认领失败");}
			},
			error:function(){alert("认领失败");}
		});	
	}
}

function ev_cancelRL(id){
	
	if(confirm("确定取消认领合同？")){
		$.ajax({
			type:"post",
			async:false,
			url: "<%=basePath%>zxmHt/modifyColumn21?type=Y&ids="+id,
			dataType:"text",
			success:function(result){
				if(result != ""){
					alert("取消成功");
					ev_search();
				}
			},
			error:function(){alert("取消失败");}
		});	
		}
}
</script>
</body>
</html>
