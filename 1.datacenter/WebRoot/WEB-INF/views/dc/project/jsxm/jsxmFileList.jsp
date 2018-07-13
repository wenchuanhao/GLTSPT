
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
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>

</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
			<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
			<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />	
	</div>
	
	<form name="form" id="pageForm" method="post"  >
	
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${jsxm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${jsxm.pageSize}" id="pageSize"	name="pageSize"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">项目类型：</th>
	    <td width="30%">
	    	<select class="ui-select" id="column01" name="column01" style="width:200px;">
						<option value="">请选择</option>
						<option ${jsxm.column01 eq '1' ? 'selected="selected"':''} value="1">软件工程</option>
						<option ${jsxm.column01 eq '2' ? 'selected="selected"':''} value="2">集成工程</option>
						<option ${jsxm.column01 eq '3' ? 'selected="selected"':''} value="3">土建工程</option>
						<option ${jsxm.column01 eq '4' ? 'selected="selected"':''} value="4">征地工程</option>
	        </select>
		</td> 
  		
  		<th width="9%" align="right">建设总控：</th>
	    <td width="30%">
	    	<input type="hidden" name="column04" id="column04"  value="${jsxm.column04}"/>
	    	<input id="column04Name" name="column04Name" value="${jsxm.column04Name}" type="text"  placeholder="请填写建设总控" class="text01" onfocus="autoCompletes(this);" 
	    	onclick="$('#column04').val('');$('#column04Name').val('');"
	    	style="width:195.5px;"  />
		</td> 		
	</tr>
	
	<tr>
		<th width="9%" align="right">项目状态：</th>
	    <td width="30%">
		    	<select class="ui-select" id="column08"  name="column08" style="width:200px;">
		    			<option ${jsxm.column08 eq null ? 'selected="selected"':''} value="">请选择</option>
						<option ${jsxm.column08 eq '1' ? 'selected="selected"':''} value="1">立项完成</option>
						<option ${jsxm.column08 eq '2' ? 'selected="selected"':''} value="2">采购完成</option>
						<option ${jsxm.column08 eq '3' ? 'selected="selected"':''} value="3">设计完成</option>
						<option ${jsxm.column08 eq '4' ? 'selected="selected"':''} value="4">施工完成</option>
						<option ${jsxm.column08 eq '5' ? 'selected="selected"':''} value="5">初验完成</option>
						<option ${jsxm.column08 eq '6' ? 'selected="selected"':''} value="6">终验完成</option>						
		        </select>
		</td>
		<th width="9%" align="right">建设项目名称：</th>
	    <td width="30%">
	    	<input id="column03" name="column03" value="${jsxm.column03}"  type="text"  placeholder="请填写建设项目名称" class="text01" style="width:195px;"  />
		</td> 
		
	</tr>
	
	<tr>
		<th width="9%" align="right">创建时间：</th>
	    <td width="30%">
	    	<div class="date l" >
	    	<input readonly="readonly" name="beginCreateTime" id="beginCreateTime" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
	                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
		<th width="9%" align="right"></th>
	    <td width="30%">
	    	<div class="date l" >
	    	<input readonly="readonly" name="endCreateTime" id="endCreateTime" type="text"  placeholder="请输入结束时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${jsxm.endCreateTime}" pattern="yyyy-MM-dd"/>"
	                     	onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreateTime\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
	</tr>
	
	<tr>
  		
	 	<th width="9%" align="right">科室：</th>
	    <td width="30%">
			<select class="ui-select"  style="width:200px;" id="ks" name="ks" >
			<option value="">请选择</option>
			<option ${jsxm.ks eq '0313f5eef20914f55aae79b647b79fdf' ? 'selected="selected"':''} value="0313f5eef20914f55aae79b647b79fdf">网络建设室</option>
			<option ${jsxm.ks eq '379725f12591b6c2dad22d72178917fd' ? 'selected="selected"':''} value="379725f12591b6c2dad22d72178917fd">项目管理室</option>
			<option ${jsxm.ks eq '48300b5a9c196bc8ab1c3e5398441c34' ? 'selected="selected"':''} value="48300b5a9c196bc8ab1c3e5398441c34">工程建设室</option>
			</select>    
		</td>				  		
	</tr>
	
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >建设项目一览表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<c:if test="${role ne null && role ne 'GC_QTRY'}">
    		<a class="btn_common01" href="javascript:ev_export()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>
    		</c:if>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox" onclick="ev_checked()"></th>
			<th>项目类型</th>
			<th>建设项目编号</th>
			<th>建设项目名称</th>
			<th>项目状态</th>
			<th>建设总控</th>
			<th>建设项目管理员</th>
			<th>创建时间</th>
			<th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" type="checkbox" value="${vo.id}"></td>
		    <td>
					${vo.column01 ne '1' ? '':'软件工程'}
					${vo.column01 ne '2' ? '':'集成工程'}
					${vo.column01 ne '3' ? '':'土建工程'}
					${vo.column01 ne '4' ? '':'征地工程'}			    
		    </td>
		    <td><span><a href="javascript:ev_edit('${vo.id}')">${vo.column02}</a></span></td>
		    <td>${vo.column03}</td>
		    <td>
					${vo.column08 ne '1' ? '':'立项完成'}
					${vo.column08 ne '2' ? '':'采购完成'}
					${vo.column08 ne '3' ? '':'设计完成'}
					${vo.column08 ne '4' ? '':'施工完成'}
					${vo.column08 ne '5' ? '':'初验完成'}
					${vo.column08 ne '6' ? '':'终验完成'}				    
		    </td>
			<td>${vo.column04Name}</td>
			<td>${vo.column10Name}</td>
		    <td><fmt:formatDate value="${vo.column07}" pattern="yyyy-MM-dd "/></td>
		    <td >
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="javascript:ev_tree('${vo.id}');">树形</a></span>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/export.png"><a href="javascript:ev_openFile('${vo.id}','${vo.column01}')">全部文档</a></span>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="javascript:ev_tzbm('${vo.id}');">投资编码</a></span>
		    </td>
		 </tr>
	   </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="9">找不到对应的数据</td></tr>
	   </c:if>
	</table>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>

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

//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>jsxmfile/list?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#column01").data("ui-select").val("");
	$("#column04").val("");
	$("#column08").data("ui-select").val("");
	$("#column03").val("");
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
	$("#ks").data("ui-select").val("");
	ev_search();
}

function ev_export(){
	var $subBoxChecks = $("input[name='subBox']:checked");
	var zbIds = "";
	$.each($subBoxChecks,function(k,v){
		if(k == 0){
			zbIds += "'"+$(v).val()+"'";
		}else{
			zbIds += ",'"+$(v).val()+"'";
		}
	});
	
	if(zbIds == ""){
		alert("请选择建设项目！");
		return;	
	}
	
	document.forms[0].action="<%=basePath%>jsxmfile/exportFile?zbIds="+zbIds+"&key="+Math.random();
	document.forms[0].submit();
	document.forms[0].action="<%=basePath%>jsxmfile/list?key="+Math.random();
}
function ev_submit(url){
	document.forms[0].action = url;
	document.forms[0].submit();
}
function ev_openFile(id,type){
	window.open("<%=basePath%>jsxmfile/fileList?id="+id+"&type="+type);
}

function ev_edit(id){
	window.open("<%=basePath%>jsxm/edit?pageSource=jsxmFileList&id="+id);
}
function ev_tree(id){
	window.open("<%=basePath%>jsxm/treeList?id="+id)
}
function ev_tzbm(id){
	window.open("<%=basePath%>jsxm/tzbmList?id="+id);
}

//用户可选择创建人
function autoCompletes(target){
	jQuery(target).autocomplete({
		source: function( request, response ) {
			jQuery.ajax({
				url: "<%=basePath%>jsxm/searchUser",
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
					jQuery("#column04").val(ui.item.userId);
					jQuery("#column04Name").val(ui.item.userName);
					return false;
		}
	});
}
</script>
</body>
</html>
