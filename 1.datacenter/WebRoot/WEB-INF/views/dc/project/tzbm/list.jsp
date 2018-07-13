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
<jsp:include page="../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="PROJECT_TYPE"></jsp:param>
</jsp:include>
</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
			<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
			<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />		
	</div>
	
	<form name="form" id="pageForm" method="post"  >
	
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${tzbm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${tzbm.pageSize}" id="pageSize"	name="pageSize"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">项目类型：</th>
	    <td width="30%">
	    	<select id="column01" name="column01" class="ui-select" style="width:200px;" defaultValue="${tzbm.column01 }"  dictType="PROJECT_TYPE"/>
		</td> 
  		
  		<th width="9%" align="right">投资项目名称：</th>
	    <td width="30%">
	    	<input id="column03" name="column03" value="${tzbm.column03 }"  type="text"  placeholder="请填写投资项目名称" class="text01" style="width:195px;"  />
		</td> 
  		
	</tr>
	
	<tr>
		<th width="9%" align="right">投资编码管理员：</th>
	    <td width="30%">
	    	<input type="hidden" name="column19" id="column19"  value="${tzbm.column19}"/>
	    	<input id="column19Name" name="column19Name" value="${tzbm.column19Name}"  type="text"  placeholder="请填写投资编码管理员" class="text01" style="width:195px;"  onfocus="autoCompletes(this);" 
	    	onclick="$('#column19').val('');$('#column19Name').val('');"/>
		</td> 
		<th width="9%" align="right">投资编号：</th>
	    <td width="30%">
	    	<input id="column02" name="column02" value="${tzbm.column02 }"  type="text"  placeholder="请填写投资编号" class="text01" style="width:195px;"  />
		</td> 
	</tr>
	
	<tr>
  		<th width="9%" align="right">创建时间：</th>
	    <td width="30%">
	    	<div class="date l" >
	    	<input readonly="readonly" name="beginCreateTime" id="beginCreateTime" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${tzbm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
	                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
		<th width="9%" align="right"></th>
	    <td width="30%">
	    	<div class="date l" >
	    	<input readonly="readonly" name="endCreateTime" id="endCreateTime" type="text"  placeholder="请输入结束时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${tzbm.endCreateTime}" pattern="yyyy-MM-dd"/>"
	                     	onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreateTime\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
	</tr>
  	<tr>
	<th width="9%" align="right">状态：<span id="m" style="font: bolder;color: red;"></span></th>
    <td width="30%">
    	<select class="ui-select"  style="width:200px;" id="column18" name="column18" >
    			<option ${tzbm.column18 eq null ? 'selected="selected"':''} value="">请选择</option>
				<option ${tzbm.column18 eq '1' ? 'selected="selected"':''} value="1">立项完成</option>
				<option ${tzbm.column18 eq '2' ? 'selected="selected"':''} value="2">采购完成</option>
				<option ${tzbm.column18 eq '3' ? 'selected="selected"':''} value="3">设计完成</option>
				<option ${tzbm.column18 eq '4' ? 'selected="selected"':''} value="4">施工完成</option>
				<option ${tzbm.column18 eq '5' ? 'selected="selected"':''} value="5">初验完成</option>
				<option ${tzbm.column18 eq '6' ? 'selected="selected"':''} value="6">终验完成</option>
        </select>
	</td>  	
 	<th width="9%" align="right">科室：</th>
    <td width="30%" colspan="3">
		<select class="ui-select"  style="width:200px;" id="ks" name="ks" >
		<option value="">请选择</option>
		<option ${tzbm.ks eq '0313f5eef20914f55aae79b647b79fdf' ? 'selected="selected"':''} value="0313f5eef20914f55aae79b647b79fdf">网络建设室</option>
		<option ${tzbm.ks eq '379725f12591b6c2dad22d72178917fd' ? 'selected="selected"':''} value="379725f12591b6c2dad22d72178917fd">项目管理室</option>
		<option ${tzbm.ks eq '48300b5a9c196bc8ab1c3e5398441c34' ? 'selected="selected"':''} value="48300b5a9c196bc8ab1c3e5398441c34">工程建设室</option>
		</select>
	</td>
	</tr>	
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >投资编码列表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<c:if test="${role ne null && role ne 'GC_QTRY'}">
    		<a class="btn_common01" href="javascript:void(0);" onclick="ev_add()"/><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>
    		<a class="btn_common01" href="javascript:ev_export()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>
    		</c:if>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox" onclick="ev_checked()"></th>
			<th>项目类型</th>
			<th>投资编号</th>
			<th>项目名称</th>
			<th>投资总金额（万元）</th>
			<th>建设期限</th>
			<th>投资编码管理员</th>
			<th>状态</th>
			<th>创建时间</th>
			<th>操作</th>
	  </tr>
	 <c:if test="${not empty ITEMPAGE.items}"> 
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="ids"  id="ids"  type="checkbox" value="${vo.id}"></td>
		    <td>
				<jsp:include page="../../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="PROJECT_TYPE"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.column01}"></jsp:param>
			    </jsp:include>			    
		    </td>
		    <td><span><a href="javascript:void(0);" onclick="ev_edit('${vo.id}')">${vo.column02}</a></span></td>
		    <td style="word-break:break-all" width="15%">${vo.column03}</td>
		    <td><fmt:formatNumber value="${vo.column06}" type="currency"  maxFractionDigits="6"/></td>
			<td>${vo.column07}</td>
			<td>${vo.column19Name}</td>
		    <td>
					${vo.column18 ne '1' ? '':'立项完成'}
					${vo.column18 ne '2' ? '':'采购完成'}
					${vo.column18 ne '3' ? '':'设计完成'}
					${vo.column18 ne '4' ? '':'施工完成'}
					${vo.column18 ne '5' ? '':'初验完成'}
					${vo.column18 ne '6' ? '':'终验完成'}			    
		    </td>			
		    <td><fmt:formatDate value="${vo.column17}" pattern="yyyy-MM-dd "/></td>
		    <td >
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/export.png"><a href="javascript:void(0);" target="_blank" onclick="ev_openFile('${vo.id}','${vo.column01}')">文档</a></span>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="javascript:void(0);" onclick="ev_zxm('${vo.id}')">子项目</a></span><br/>
		    	<c:if test="${role ne null && (role eq 'GC_YWGLY' or (role eq 'GC_XMFZR' && fn:contains(vo.column19, sysUserId)))}">
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/publish.png"><a href="javascript:ev_zb('${vo.id}')">周报</a></span>
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/timeout.png"><a  href="javascript:ev_yearList('${vo.id}')">年度目标</a></span>  
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="javascript:void(0);" onclick="ev_modify('${vo.id}');">编辑</a></span>
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${vo.id}')">删除</a></span>
		    	</c:if>
		    </td>
		 </tr>
	   </c:forEach>
	  	 <tr style="background-color: #d6ebf9;">
			<td colspan="4">合计：</td>
		    <td ><fmt:formatNumber value="${tzbm_T.column06}" type="currency"  maxFractionDigits="6"/></td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		 </tr>	   
		</c:if>	   
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="10">找不到对应的数据</td></tr>
	   </c:if>	   
	</table>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>

<script type="text/javascript">
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>tzbm/list?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#column01").data("ui-select").val("");
	$("#column03").val("");
	$("#column19").val("");
	$("#column19Name").val("");
	$("#column02").val("");
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
	$("#column18").data("ui-select").val("");
	$("#ks").data("ui-select").val("");
	ev_search();
}

function ev_checked(){
	var checkAll = document.getElementById("checkAll");
	if(checkAll != null && checkAll.type=="checkbox"){
		var ids = document.getElementsByName("ids");
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

function getIds(){
	var ids = "";
	$("input[id='ids']:checked").each(function(){ 
	   ids += ","+$(this).val();
	});
	return ids;
}

function ev_addIndex(){
	if($("input[id='ids']:checked").length > 0){
		if(confirm("确定添加到首页？")){
			$.ajax({
				type:"post",
				async:false,
				url: "<%=basePath%>tzbm/addIndex?type=Y&ids="+getIds(),
				dataType:"text",
				success:function(result){
					if(result != ""){
						alert("添加成功");
					}
				},
				error:function(){alert("添加失败");}
			});	
		}
	}else{
		alert("请选择一个投资编码");
	}
}

function ev_cancelIndex(){
	if($("input[id='ids']:checked").length > 0){
		if(confirm("确定取消到首页？")){
			$.ajax({
				type:"post",
				async:false,
				url: "<%=basePath%>tzbm/addIndex?type=N&ids="+getIds(),
				dataType:"text",
				success:function(result){
					if(result != ""){
						alert("取消成功");
					}
				},
				error:function(){alert("取消失败");}
			});	
		}
	}else{
		alert("请选择一个投资编码");
	}
}

function ev_add(){
	window.open("<%=basePath%>tzbm/add");
}
function ev_submit(url){
	document.forms[0].action = url;
	document.forms[0].submit();
}
function ev_edit(id){
	window.open("<%=basePath%>tzbm/edit?pageSource=tzbm_list&id="+id);
}
function ev_modify(id){
	window.open("<%=basePath%>tzbm/add?id="+id);
}
function ev_export(){
	document.forms[0].action="<%=basePath%>tzbm/exportFile?ids="+getIds();
	document.forms[0].submit();
	document.forms[0].action="<%=basePath%>tzbm/list?key="+Math.random();
}

function ev_delete(id){
	if(confirm("确定删除？\r1.当前投资编码")){
		jQuery.ajax({
	        type:"POST",
	        async:true,
	        url:"<%=basePath%>tzbm/delete",
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

function ev_openFile(id,type){
	if(type == ""){
		alert("没有关联建设项目，不能上传文档");return;
	}
	var url = "<%=basePath%>tzbm/fileList?id="+id+"&type="+type;
	window.open(url);
	//window.showModalDialog(url,window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
}

function ev_zb(id){
	ev_submit("<%=basePath%>zb/add?proType=2&code="+id);
}

function ev_zxm(id){
	window.open("<%=basePath%>tzbm/zxmList?id="+id);
}

function ev_yearList(id){
	var url = "<%=basePath%>tzbm/yearList?parentId="+id;
	ev_submit(url);
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
					jQuery("#column19").val(ui.item.userId);
					jQuery("#column19Name").val(ui.item.userName);
					return false;
		}
	});
}
</script>
</body>
</html>
