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
	<input type="hidden" value="${htKz.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${htKz.pageSize}" id="pageSize"	name="pageSize"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">投资编号：</th>
	    <td width="30%"><input id="column02" name="column02" value="${htKz.column02}"  type="text"  placeholder="请填写投资编号" class="text01" style="width:195px;"  /></td> 
  		
  		<th width="9%" align="right">合同编号：</th>
	    <td width="30%">
	    	<input id="column01" name="column01" value="${htKz.column01}"  type="text"  placeholder="请填写合同编号" class="text01" style="width:195px;"  />
		</td> 
  		
  		
	</tr>
		
	<tr>
		<th width="9%" align="right">合同名称：</th>
	    <td width="30%"><input id="htName" name="htName" value="${htKz.htName}"  type="text"  placeholder="请填写合同名称" class="text01"  style="width:195px;"/></td>
	    <th width="9%" align="right">合同管理员：</th>
	    <td width="30%">
	    	<input type="hidden" name="column13" id="column13"  value="${htKz.column13}"/>
	    	<input id="column13Name" name="column13Name" value="${htKz.column13SysUser.userName}"  onclick="$('#column13').val('');$('#column13Name').val('');"
	    	onfocus="autoCompletes(this);" type="text"  placeholder="请填写合同归属人" class="text01" style="width:195px;"  />
		</td>
	</tr>
	
	<tr>
		<th width="9%" align="right">记录时间：</th>
	    <td width="30%" >
	    	<div class="date l" >
	    	<input readonly="readonly" name="beginCreateTime" id="beginCreateTime" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${htKz.beginCreateTime}" pattern="yyyy-MM-dd"/>"
	                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td> 
		<th width="9%" align="right"></th>
	    <td width="30%" >
	    	<div class="date l" >
	    	<input readonly="readonly" name="endCreateTime" id="endCreateTime" type="text"  placeholder="请输入结束时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${htKz.endCreateTime}" pattern="yyyy-MM-dd"/>"
	                     	onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreateTime\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td> 
	</tr>
	
	 <tr>
	 	<th width="9%" align="right">科室：</th>
	    <td width="30%">
			<select class="ui-select"  style="width:200px;" id="ks" name="ks" >
			<option value="">请选择</option>
			<option ${htKz.ks eq '0313f5eef20914f55aae79b647b79fdf' ? 'selected="selected"':''} value="0313f5eef20914f55aae79b647b79fdf">网络建设室</option>
			<option ${htKz.ks eq '379725f12591b6c2dad22d72178917fd' ? 'selected="selected"':''} value="379725f12591b6c2dad22d72178917fd">项目管理室</option>
			<option ${htKz.ks eq '48300b5a9c196bc8ab1c3e5398441c34' ? 'selected="selected"':''} value="48300b5a9c196bc8ab1c3e5398441c34">工程建设室</option>
			</select>    
		</td>		 		
	</tr>
	
	</table>
	
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >合同开支列表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<c:if test="${role ne null && role ne 'GC_QTRY'}">
    		<a class="btn_common01" href="javascript:ev_add()" /><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>
    		<a class="btn_common01" href="javascript:ev_export()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>
    		</c:if>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox" onclick="ev_checked()"></th>
			<th>投资编号</th>
			<th>合同编号</th>
			<th>合同名称</th>
			<th>合同不含税金额<br />（万元）</th>
			<th>合同含税金额<br />（万元）</th>
			<th>合同对方</th>
			<th>合同类型</th>
			<th>累计形象进度/<br />MIS接收金额（万元）</th>
			<th>本年形象进度/<br />MIS接收金额（万元）</th>
			<th>累计转资金额<br />（万元）</th>
			<th>本年转资金额<br />（万元）</th>
			<th>累计付款金额<br />（万元）</th>
			<th>负责人</th>
			<th>记录时间</th>
			<th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" type="checkbox" value="${vo[0].id}"></td>
		    <td>${vo[0].column02}</td>
		    <c:set var="zxmHt" value="${vo[0].zxmHt}"/>
		    <td><span><a href="javascript:ev_edit('${vo[0].id}')">${zxmHt.column01}</a></span></td>
		    <td>${zxmHt.column03}</td>
			<td><fmt:formatNumber value="${vo[0].column03}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo[0].column04}" type="currency"  maxFractionDigits="6"/></td>
			<td>${vo[0].column05}</td>
			<td>${vo[0].column06 eq '1' ? '费用类':''}${vo[0].column06 eq '2' ? '订单类':''}</td>
			<td><fmt:formatNumber value="${vo[0].column07}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo[0].column08}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo[0].column09}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo[0].column10}" type="currency"  maxFractionDigits="6"/></td>
			<td><fmt:formatNumber value="${vo[0].column11}" type="currency"  maxFractionDigits="6"/></td>
			<td>${vo[0].column13SysUser.userName}</td>
		    <td><fmt:formatDate value="${vo[0].column12}" pattern="yyyy-MM-dd"/></td>
		    <td >
		    	<c:if test="${role ne null && (role eq 'GC_YWGLY' or (role eq 'GC_XMFZR' && fn:contains(zxmHt.column21, sysUserId)))}">
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="javascript:ev_modify('${vo[0].id}')" >编辑</a></span><br/>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${vo[0].id}')">删除</a></span>
		    	</c:if>
		    </td>
		 </tr>
	   </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="16">找不到对应的数据</td></tr>
	   </c:if>	   
	</table>
	</form>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
           
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>copperationController";</script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
<script type="text/javascript" src="/SRMC/dc/cooperation/js/common.js"></script>
<script type="text/javascript" src="/SRMC/dc/rules/js/flow.js"></script>
<script type="text/javascript">
var sonList = $.parseJSON('${sonList}'.replace("\r\n", "\\r\\n"));
$(function(){

});
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});

//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>htKz/list?key="+Math.random();
	document.forms[0].submit();
}

function ev_checked(){
	var checkAll = document.getElementById("checkAll");
	if(checkAll != null && checkAll.type=="checkbox"){
		var ids = document.getElementsByName("subBox");
		 if(ids != null){
			for(var i=0;i<ids.length;i++){
				var id = ids[i];
				if(checkAll.checked){
					id.checked=true;
				}else{
					id.checked=false;
				}
			}	 
		 }
	}
}

//重置查询
function ev_reset(){
	$("#column02").val("");
	$("#column01").val("");
	$("#htName").val("");
	$("#column13").val("");
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
	$("#ks").data("ui-select").val("");
	ev_search();
}

function ev_submit(url){
	document.forms[0].action = url;
	document.forms[0].submit();
}

function ev_edit(id){
	window.open("<%=basePath%>htKz/edit?id="+id);
}
function ev_modify(id){
	window.open("<%=basePath%>htKz/add?id="+id);
}
function ev_add(){
	window.open("<%=basePath%>htKz/add");
}

function ev_delete(id){
	if(confirm("确定删除？")){
		jQuery.ajax({
	        type:"POST",
	        async:true,
	        url:"<%=basePath%>htKz/delete",
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

function ev_export(){
	var obj = $("input[name='subBox']:checked");
	if(obj.length > 0){
		document.forms[0].action="<%=basePath%>htKz/exportFile?key="+Math.random();
		document.forms[0].submit();
		document.forms[0].action="<%=basePath%>htKz/list?key="+Math.random();
	}else{
		alert("请选择合同开支！");
	}
}

//用户可选择创建人
function autoCompletes(target){
	jQuery(target).autocomplete({
		source: function( request, response ) {
			jQuery.ajax({
				url: "<%=basePath%>htKz/searchUser",
				dataType: "json",
				data: {
					userValue: request.term
				},
				type: "POST",
				success: function(data) {
					if(data!=null){
			     		response(jQuery.map(data, function( item ) {
			     			return {
			     			 value:item[0].userName+"------"+item[0].orgName,
								userName:item[0].userName,
								userId:item[0].userId,
								account:item[0].account,
								orgName:item[0].orgName
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
					jQuery("#column13").val(ui.item.userId);
					jQuery("#column13Name").val(ui.item.userName);
					return false;
		}
	});
}
</script>
</body>
</html>
