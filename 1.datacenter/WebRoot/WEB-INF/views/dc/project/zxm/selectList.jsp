<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
	String source = request.getParameter("source");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>
<title>选择子项目</title>
<base target="_self"/>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script src="/SRMC/rmpb/js/isIe.js"></script>	
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
	<input type="hidden" value="${zxm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${zxm.pageSize}" id="pageSize"	name="pageSize"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">项目类型：</th>
	    <td width="30%">
	    	<select id="column01" name="column01" class="ui-select" style="width:200px;" defaultValue="${zxm.column01 }"  dictType="PROJECT_TYPE"/>
		</td> 
  		
  		<th width="9%" align="right">子项目名称：</th>
	    <td width="30%"><input id="column03" name="column03" value="${zxm.column03 }"  type="text"  placeholder="请填写子项目名称" class="text01" style="width:195px;"  /></td>  
	</tr>
	
	<tr>
		<th width="9%" align="right">子项目管理员：</th>
	    <td width="30%">
	    	<input type="hidden" name="column09" id="column09"  value="${zxm.column09}"/>
	    	<input id="column09Name" name="column09Name" value="${zxm.column09Name}" type="text"  placeholder="请填写子项目管理员" class="text01" onfocus="autoCompletes(this);" 
	    	onclick="$('#column09').val('');$('#column09Name').val('');"
	    	style="width:195.5px;"  />	    
	    </td> 
	    <th width="9%" align="right">子项目编号：</th>
	    <td width="30%"><input id="column02" name="column02" value="${zxm.column02 }"  type="text"  placeholder="请填写子项目编号" class="text01" style="width:195px;"  /></td> 
  		
	</tr>
	
	<tr>
  		
  		<th width="9%" align="right">创建时间：</th>
	    <td width="30%">
	    	<div class="date l" >
	    	<input readonly="readonly" name="beginCreateTime" id="beginCreateTime" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${zxm.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
	                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
		<th width="9%" align="right"></th>
	    <td width="30%" >
	    	<div class="date l" >
	    	<input readonly="readonly" name="endCreateTime" id="endCreateTime" type="text"  placeholder="请输入结束时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${zxm.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
	                     	onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreateTime\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>  		
	</tr>
	
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >子项目列表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th>序号</th>
			<th>项目类型</th>
			<th>子项目编号</th>
			<th>子项目名称</th>
			<th>子项目审核人</th>
			<th>子项目管理员</th>
			<th>创建时间</th>
			<th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr style="cursor: pointer;"  <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="" onclick="ev_confirm('${vo.id}_${vo.column02}_${vo.column03}_${vo.column01}')">
	  	  	<td >${i.count}</td>
		    <td>
				<jsp:include page="../../sys/dict/include/dict_config.jsp">
				    <jsp:param name="paramTypeCode" value="PROJECT_TYPE"></jsp:param>
				    <jsp:param name="paramCode" value="${vo.column01}"></jsp:param>
			    </jsp:include>		    
		    </td>
		    <td><span><a href="javascript:ev_edit('${vo.id}')">${vo.column02}</a></span></td>
		    <td>${vo.column03}</td>
			<td>${vo.column05Name}</td>
			<td>${vo.column09Name}</td>
		    <td><fmt:formatDate value="${vo.column08}" pattern="yyyy-MM-dd "/></td>
		    <td >
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="javascript:ev_confirm('${vo.id}_${vo.column02}_${vo.column03}_${vo.column01}');">使用</a></span>
		    </td>
		 </tr>
	   </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="8">找不到对应的数据</td></tr>
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
	document.forms[0].action="<%=basePath%>zxm/selectList?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#column01").data("ui-select").val("");
	$("#column03").val("");
	$("#column04").val("");
	$("#column02").val("");
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
	ev_search();
}

function ev_confirm(v){
   	if (window.opener != undefined) {  
	     //for chrome    
	     var source = "<%=source%>";
	     if(source == "zxmHt"){
	     	window.opener.modifyColumn21(v);
	     }else if(source == "add1"){
	     	window.opener.setValues(v);
	     }else{
	     	window.opener.putValue(v);
	     }
	 }  
	 else {  
	     window.returnValue = v;  
	 } 
   	 window.close();
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
					jQuery("#column09").val(ui.item.userId);
					jQuery("#column09Name").val(ui.item.userName);
					return false;
		}
	});
}
</script>
</body>
</html>
