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
	<input type="hidden" value="${zxmHt.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${zxmHt.pageSize}" id="pageSize"	name="pageSize"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">项目类型：</th>
	    <td width="30%">
	    	<select class="ui-select" id="xmType" name="xmType" style="width:200px;">
						<option value="">请选择</option>
						<option ${zxmHt.xmType eq '1' ? 'selected="selected"':''} value="1">软件工程</option>
						<option ${zxmHt.xmType eq '2' ? 'selected="selected"':''} value="2">集成工程</option>
						<option ${zxmHt.xmType eq '3' ? 'selected="selected"':''} value="3">土建工程</option>
						<option ${zxmHt.xmType eq '4' ? 'selected="selected"':''} value="4">征地工程</option>
	        </select>
		</td> 
  		
  		<th width="9%" align="right">合同名称：</th>
	    <td width="30%">
	    	<input id="column03" name="column03" value="${zxmHt.column03}"  type="text"  placeholder="请填写合同名称" class="text01" style="width:195px;"  />
		</td> 
	</tr>
	
	<tr>
		<th width="9%" align="right">合同管理员：</th>
	    <td width="30%">
	    	<input type="hidden" name="column21" id="column21"  value="${zxmHt.column21}"/>
	    	<input id="column21Name" name="column21Name" value="${zxmHt.column21Name}"  onclick="$('#column21').val('');$('#column21Name').val('');"
	    	onfocus="autoCompletes(this);" type="text"  placeholder="请填写合同归属人" class="text01" style="width:195px;"  />
		</td> 
		<th width="9%" align="right">子项目编号：</th>
	    <td width="30%">
	    	<input id="column04" name="column04" value="${zxmHt.column04}"  type="text"  placeholder="请填写子项目编号" class="text01" style="width:195px;"  />
		</td>
	</tr>
	
	<tr>
  		
  		<th width="9%" align="right">合同编号：</th>
	    <td width="30%">
	    	<input id="column01" name="column01" value="${zxmHt.column01}"  type="text"  placeholder="请填写合同编号" class="text01" style="width:195px;"  />
		</td> 		 
  		<th width="9%" align="right">认领状态：</th>
	    <td width="30%" >
	    	<select class="ui-select" id="rlStatus" name="rlStatus" style="width:200px;">
						<option value="">请选择</option>
						<option ${zxmHt.rlStatus eq 'Y' ? 'selected="selected"':''} value="Y">已认领</option>
						<option ${zxmHt.rlStatus eq 'N' ? 'selected="selected"':''} value="N">未认领</option>
	        </select>
		</td>
  				
	</tr>
	
	<tr>
		<th width="9%" align="right">合同签订时间：</th>
	    <td width="30%" >
	    	<div class="date l" >
	    	<input readonly="readonly" name="beginCreateTime" id="beginCreateTime" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${zxmHt.beginCreateTime}" pattern="yyyy-MM-dd"/>"
	                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>  
		<th width="9%" align="right"></th>
	    <td width="30%" >
	    	<div class="date l" >
	    	<input readonly="readonly" name="endCreateTime" id="endCreateTime" type="text"  placeholder="请输入结束时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${zxmHt.endCreateTime}" pattern="yyyy-MM-dd"/>"
	                     	onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreateTime\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>  
	</tr>
	
	<tr>
  		
  		<th width="9%" align="right">预算项目：</th>
	    <td width="30%">
	    	<input id="column15" name="column15" value="${zxmHt.column15}"  type="text"  placeholder="请填写预算项目" class="text01" style="width:195px;"  />
		</td>
	 	<th width="9%" align="right">科室：</th>
	    <td width="30%" >
			<select class="ui-select"  style="width:200px;" id="ks" name="ks" >
			<option value="">请选择</option>
			<option ${zxmHt.ks eq '0313f5eef20914f55aae79b647b79fdf' ? 'selected="selected"':''} value="0313f5eef20914f55aae79b647b79fdf">网络建设室</option>
			<option ${zxmHt.ks eq '379725f12591b6c2dad22d72178917fd' ? 'selected="selected"':''} value="379725f12591b6c2dad22d72178917fd">项目管理室</option>
			<option ${zxmHt.ks eq '48300b5a9c196bc8ab1c3e5398441c34' ? 'selected="selected"':''} value="48300b5a9c196bc8ab1c3e5398441c34">工程建设室</option>
			</select>
		</td>		
	</tr>	
	</table>
	</form>
 	<jsp:include flush="true" page="include.jsp"></jsp:include>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th>序号</th>
			<th>子项目编号</th>
			<th>子项目名称</th>
			<th>合同编号</th>
			<th>合同名称</th>
			<th>预算项目</th>
			<th>合同含税金额<br/>（万元）</th>
			<th>合同对方</th>
			<th>合同状态</th>
			<th>合同签订时间</th>
			<th>合同管理员</th>
			<th>创建时间</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td>${i.count}</td>
	  	  	<c:set var="zxm" value="${vo.zxm}"/>
		    <td style="word-break:break-all" width="12%">${zxm.column02}</td>
		    <td style="word-break:break-all" width="15%">${zxm.column03}</td>
		    <td style="word-break:break-all" width="8%"><span><a href="javascript:ev_edit('${vo.id}')">${vo.column01}</a></span></td>
			<td style="word-break:break-all" width="15%">${vo.column03}</td>
			<td style="word-break:break-all" width="15%">${vo.column15}</td>
			<td><fmt:formatNumber value="${vo.column11}" type="currency"  maxFractionDigits="6"/></td>
			<td>${vo.column14}</td>
			<td>${vo.column12}</td>
		    <td><fmt:formatDate value="${vo.column19}" pattern="yyyy-MM-dd "/></td>
			<td>${vo.column21Name}</td>
			<td><fmt:formatDate value="${vo.createDate}" pattern="yyyy-MM-dd "/></td>
		 </tr>
	   </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="12">找不到对应的数据</td></tr>
	   </c:if>	   
	</table>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
</body>

<script type="text/javascript">
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});
function ev_edit(id){
	window.open("<%=basePath%>zxmHt/edit?pageSource=zb&id="+id);
}
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>related/zxmHt?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#xmType").data("ui-select").val("");
	$("#rlStatus").data("ui-select").val("");
	$("#column03").val("");
	$("#column21").val("");
	$("#column04").val("");
	$("#column01").val("");
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
	$("#column15").val("");
	$("#ks").data("ui-select").val("");
	ev_search();
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
					jQuery("#column21").val(ui.item.userId);
					jQuery("#column21Name").val(ui.item.userName);
					return false;
		}
	});
}

</script>
</html>
