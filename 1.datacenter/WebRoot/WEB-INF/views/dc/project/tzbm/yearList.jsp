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
<title>年度目标列表</title>
<base  target="_self"/>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
</head>
<body>
<div class="gl_import_m" >
  	<div class="ge01"></div>
  	<form name="form" id="pageForm" method="post"  >
  	</form>
  	<div class="tabpages" >
    	<ul class="l" id="tab">
	      		<li id="tab1"  class="current">年度目标列表<em></em></li>
	      		<li id="tab2" >投资编码详情<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<a class="btn_common01" href="javascript:ev_add('')" ><img src="/SRMC/dc/images/btnIcon/invalid.png" /><span>新增年度目标</span></a>
    		<a class="btn_common01" href="javascript:location.href='<%=basePath%>tzbm/list';" ><img src="/SRMC/dc/images/btnIcon/back.png" /><span>返回</span></a>
		</div>    	
  	</div>

	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" class="listTable"   id="table1">
	  <tr>
	  		<th>序号</th>
	  		<th>年度</th>
			<th>年度投资<br/>万元</th>
			<th>资本开支目标<br/>万元</th>
			<th>至上年度安排投资计划<br/>万元</th>
			<th>年度转资目标<br/>万元</th>
			<th>记录人</th>
			<th>记录时间</th>
			<th>操作</th>
	  </tr>
	  	 
	  	 <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
			<td>${i.count}</td>
			<td>${vo.column01}</td>
			<td><fmt:formatNumber value="${vo.column02}" type="currency"  maxFractionDigits="6"/></td>
		    <td><fmt:formatNumber value="${vo.column03}" type="currency"  maxFractionDigits="6"/></td>
		    <td><fmt:formatNumber value="${vo.column04}" type="currency"  maxFractionDigits="6"/></td>
		    <td><fmt:formatNumber value="${vo.column05}" type="currency"  maxFractionDigits="6"/></td>
			<td>${vo.column06}</td>
		    <td><fmt:formatDate value="${vo.column07}" pattern="yyyy-MM-dd "/></td>
			<td>
				<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/export.png"><a href="javascript:ev_year('${vo.id}')">为本年度目标</a></span>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/invalid.png"><a href="javascript:ev_add('${vo.id}')">编辑</a></span>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/invalid.png"><a href="javascript:ev_delete('${vo.id}')">删除</a></span>
			</td>		    		    
		 </tr>
		 </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="9">找不到对应的数据</td></tr>
	   </c:if>		 
	</table>
	
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable" align="center" style="display: none;" id="table2">
		<form action=""  method="post"  id="form1">
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<tr>
		    <th width="20%" align="right"><b>*</b>项目类型：</th>
		    <td id="rulesGrade_td">
		    	<select class="ui-select" id="sel_01"  name="column01" style="width:306px;">
						<option value="">请选择</option>
						<option ${vo.column01 eq '1' ? 'selected="selected"':''} value="1">软件工程</option>
						<option ${vo.column01 eq '2' ? 'selected="selected"':''} value="2">集成工程</option>
						<option ${vo.column01 eq '3' ? 'selected="selected"':''} value="3">土建工程</option>
						<option ${vo.column01 eq '4' ? 'selected="selected"':''} value="4">征地工程</option>    	
		        </select>
		    </td>
		    <th align="right"><b>*</b>至上年度安排投资计划：</th>
		    <td><input name="column11"  id="column11"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column11}"/></td>		    		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>投资编号：</th>
		    <td><input name="column02"  id="column02"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column02}"/></td>
		    <th align="right"><b>*</b>年度转资目标：</th>
		    <td><input name="column12"  id="column12"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column12}"/>
		    </td>		    
	    </tr>
	    
	    <tr>
	    	<th align="right"><b>*</b>投资项目名称：</th>
		    <td><input name="column03"  id="column03"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column03}"/>
		    </td>
		    <th align="right"><b>*</b>投资项目联系人：</th>
		    <td><input name="column13"  id="column13"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column13}"/>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>建设项目编号：</th>
		    <c:set var="jsxm" value="${vo.jsxm}"/>
		    <input type="hidden" name="column04" id="column04" value="${jsxm.id}"/>		    
		    <td><input name="column04Code"  id="column04Code"  type="text" class="text01" style="width:300px;" placeholder=""  value="${jsxm.column02}"/>
		    </td>
		    <th align="right"><b>*</b>投资项目督办人：</th>
		    <td><input name="column14"  id="column14"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column14}"/>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>建设项目名称：</th>
		    <td><input name="column04Name"  id="column04Name"  type="text" class="text01" style="width:300px;" placeholder=""  value="${jsxm.column03}"/></td>
		    <th align="right"><b></b>计划书文号：</th>
		    <td><input name="column15"  id="column15"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column15}"/></td>		    
	    </tr>
	    
	    
	    <tr>
		    <th align="right"><b></b>建设内容：</th>
		    <td colspan="3"><textarea rows="5" cols="20" style="width:89.4%" name="column05" title="${vo.column05}">${vo.column05}</textarea></td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>项目总投资：</th>
		    <td><input name="column06"  id="column06"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column06}"/>
		    </td>
		    <th align="right"><b></b>任务书文号：</th>
		    <td><input name="column16"  id="column16"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column16}"/>
		    </td>		    
	    </tr>
	    <tr>
		    <th align="right"><b></b>建设期限：</th>
		    <td><input name="column07"  id="column07"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column07}"/></td>
		    <th align="right"><b>*</b>创建时间：</th>
		    <td>
		    	<input name="column17"  id="column17"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column17}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"/>
		    </td>		    
	    </tr>	    
	    
	    <tr>
		    <th align="right"><b>*</b>年度投资：</th>
		    <td><input name="column08"  id="column08"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column08}"/></td>
		    <th align="right"><b>*</b>状态：</th>
		    <td id="rulesGrade_td">
		    	<select class="ui-select" id="sel_01"  name="column18" style="width:306px;">
		    			<option ${vo.column18 eq null ? 'selected="selected"':''} value="">请选择</option>
						<option ${vo.column18 eq '1' ? 'selected="selected"':''} value="1">立项完成</option>
						<option ${vo.column18 eq '2' ? 'selected="selected"':''} value="2">采购完成</option>
						<option ${vo.column18 eq '3' ? 'selected="selected"':''} value="3">设计完成</option>
						<option ${vo.column18 eq '4' ? 'selected="selected"':''} value="4">施工完成</option>
						<option ${vo.column18 eq '5' ? 'selected="selected"':''} value="5">初验完成</option>
						<option ${vo.column18 eq '6' ? 'selected="selected"':''} value="6">终验完成</option>
		        </select>
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>资本开支目标：</th>
		    <td colspan="3"><input name="column09"  id="column09"  type="text" class="text01" style="width:300px;" placeholder="请填写项目类型"  value="${vo.column09}"/></td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>备注：</th>
		    <td colspan="3"><textarea rows="3" cols="20" style="width:89.4%" name="column10" title="${vo.column10}">${vo.column10}</textarea></td>
	    </tr>
	   </form>
  </table>
  	
 	<div class="gd_page">
     </div>
     <div class="ge01"></div>
</div>
           
<script type="text/javascript">
 $(document).ready(function(){
	var m = "${mess}";
	if(m != "" && m == "s"){
		ev_refresh("Y");
	}
	else if(m != "" && m == "e"){
		alert("删除失败");
	} 
	
	// 将所有.ui-select实例化
	$('.ui-select').ui_select();	
});

function ev_year(id){
	document.forms[0].action="<%=basePath%>tzbm/useYear?id="+id+"&parentId=${param.parentId}";
	document.forms[0].submit();
}
function ev_submit(url){
	document.forms[0].action = url;
	document.forms[0].submit();
}
function ev_add(id){
	var url = "<%=basePath%>tzbm/yearAdd?parentId=${param.parentId}&id="+id;
	ev_submit(url);
	//window.showModalDialog(url,window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 	
}

function ev_delete(id){
	if(confirm("确定删除？")){
		document.forms[0].action="<%=basePath%>tzbm/deleteYear?id="+id+"&parentId=${param.parentId}";
		document.forms[0].submit();
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

function ev_refresh(type){
	if(type == "Y"){
		alert("刷新成功");
		document.forms[0].action = "<%=basePath%>tzbm/yearList?parentId=${param.parentId}";
		document.forms[0].submit();	
	}
}
</script>
</body>
</html>
