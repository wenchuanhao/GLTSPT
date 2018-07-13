<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>报账查询列表</title>
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
	<link rel="stylesheet" type="text/css" href="/SRMC/dc/css/combotree/easyui.css">
	<script type="text/javascript" src="/SRMC/dc/js/combotree/jquery.easyui.min.js"></script>  
	<script type="text/javascript">
		var j = jQuery.noConflict(true);
	</script>   
	<link href="/SRMC/rmpb/css/date_input.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/common2.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
</head>
<SCRIPT language=javascript1.2>
function showsubmenu(ss,ii,aa,openimg,closeimg){
	if (ss.style.display=="none"){
		ss.style.display="";
	    ii.src="/SRMC/dc/account/images/btn_shou.png";
	    ii.alt="关闭菜单";
	}
    else{
	    ss.style.display="none"; 
		ii.src="/SRMC/dc/account/images/btn_zhan.png";
		ii.alt="展开菜单";
   }
}

</SCRIPT>
<body>
<div class="gl_import_m">
  <form  action="" name="form" method="post" id="pageForm">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
		<input name="" type="button" onclick="resets()" class="btn_reset r" value="重置" />
		<input name="" onclick="query('${type}')" type="button" class="btn_search r" value="查询" />
	</div>
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
    <input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${accountForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${accountForm.pageSize}" id="pageSize"	name="pageSize"/>	
    <input type="hidden" name="firstTrialId" id="firstTrialId_input" value="${accountForm.firstTrialId}"/>
    <input type="hidden" name="trialAccountId" id="trialAccountId_input" value="${accountForm.trialAccountId}"/>
    <input type="hidden" name="sementPeopleId" id="sementPeopleId_input" value="${accountForm.sementPeopleId}"/>
    <input type="hidden" name="department" id="department" value="${accountForm.department}"/>
    <input type="hidden" name="costType" id="costType" value="${accountForm.costType}"/>
		
		<tr>
		    <th width="9%" align="right">年月：</th>
		    <td width="20%">
        	<div class="date l" style="width:140px;"><input  name="reachSementStr" autocomplete="off" id="sel_01" type="text"  value="<fmt:formatDate value="${reachSementStr}" pattern="yyyy-MM"/>" onfocus="WdatePicker({dateFmt:'yyyy-MM',alwaysUseStartDate:true})" placeholder="请您输入"  class="text02 l" style="width:95%;" /><i></i></div>
        	</td>
		    <th width="9%" align="right">费用类型：</th>
		    <td width="20%">
		    <div class="checxTreeWrap"  style="width:180px;">
		    <%-- <input id="cosId" name="cosId" value="${accountForm.costType}"/>  --%>
		    <select  multiple style="width:200px" id="cosId" name="cosId" autocomplete="off" class="easyui-combotree" data-options="url:'<%=basePath%>/account/getCostType',method:'get'" multiple style="width:180px;">
		    <option selected="selected">${accountForm.costType}</option>
		    </select>
		    </div>
		    <%-- <select class="ui-select" name="costType" id="sel_02" style="width:180px;">
		          <option value="">请选择</option>
		          <c:forEach items="${rulesList }" var="list" varStatus="i">
		          <option value="${list.typeId }" <c:if test="${list.typeId== accountForm.costType}">selected="selected" </c:if> >${list.typeName }</option>
		          </c:forEach>
		        </select> --%></td>
		    <th width="9%" align="right">整改状态：</th>
		    <td width="18%"><select class="ui-select" name="status" id="sel_03" style="width:180px;">
         		  <option value="">请选择</option>
         		  <option value="5" <c:if test="${accountForm.status eq '5'}">selected="selected"</c:if>>有问题(整改中/整改结束)</option>
         		  <c:forEach items="${sysParameter}" var="list" varStatus="i">
         		  	<option <c:if test="${list.parameterCode== accountForm.status}">selected="selected" </c:if> value="${list.parameterCode }">${list.parameterName }</option>
         		  </c:forEach>
		        </select></td>
		   <td align="right" style="padding-right:5px;"><img src="/SRMC/dc/account/images/btn_zhan.png" width="56" height="26" onClick="showsubmenu(submenuboard1,subimgboard1,'folderopen.gif','folder.gif')" id=subimgboard1 name=subimg style="vertical-align:middle;cursor:pointer;"></td> 
		  </tr>
		  <tr>
		    <th  align="right">报账部门：</th>
		    <td>
		    <div class="checxTreeWrap"  style="width:180px;">
		    <select multiple style="width:185px" id="departmentId" name="departmentId" class="easyui-combotree" data-options="url:'<%=basePath%>/account/getDepartment',method:'get'" >
		    <option selected="selected">${accountForm.department}</option>
		    </select>
		    </div>
		    <%-- <input id="departmentId" name="departmentId" value="${accountForm.department}"/>  --%>
		    <%-- <input   onfocus="if(this.value=='请输入报账部门'){this.value='';};this.style.color='#333333';autoDepartment();" 
			                     onkeyup="clean()"
			                     onblur="if(this.value==''||this.value=='请输入报账部门'){this.style.color='#b6b6b6';}" placeholder="请输入报账部门" type="text" name="department" id="sel_04" style="width:160px;" value="${accountForm.department}" class="text01" /> --%>
		    </td>
		   
		    <th align="right">报账单号：</th>
		    <td><input  name="orderId"  type="text" autocomplete="off" placeholder="报账单号" class="text01" value="${accountForm.orderId }" style="width:175px;" /></td>
		    <td align="right">&nbsp;</td>
		    <td width="10%"><input  id="people"  type="text" autocomplete="off"  placeholder="报账单号或报账人" name="people" class="text01" value="${people }" style="width:175px;" /></td> 
		  </tr>
		    <tbody  style="display:none"  id='submenuboard1'>
		    <tr>
		    <th align="right">纸质提交财务时间：</th>
		    <td><div class="date l" style="width:140px;">
		    <input name="pstartDate" id="pstartDate" autocomplete="off" type="text" value="<fmt:formatDate value="${pstartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
		    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'pendDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  placeholder="请您输入"  class="text02 l" style="width:95%;" /><i></i>
		    </div> 
		    <div class="date l" style="width:140px;">
		    <input name="pendDate" id="pendDate" type="text" autocomplete="off" value="<fmt:formatDate value="${pendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
		    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'pstartDate\')||\'%y-%M-%d\'}',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" placeholder="请您输入"  class="text02 l" style="width:95%;" /><i></i>
		    </div>
		    </td>
		    <th align="right">创建时间：</th>
		    <td><div class="date l" style="width:140px;"><input name="cstartDate" id="cstartDate" autocomplete="off" type="text"  value="<fmt:formatDate value="${cstartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'cendDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" placeholder="请您输入"  class="text02 l" style="width:95%;" /><i></i></div><div class="date l" style="width:140px;">
		    <input name="cendDate" id="cendDate" type="text" autocomplete="off"  value="<fmt:formatDate value="${cendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'cstartDate\')||\'%y-%M-%d\'}',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" placeholder="请您输入"  class="text02 l" style="width:95%;" /><i></i></div></td>
		    <th align="right">是否包含抵扣：</th>
		    <td><select class="ui-select" name="includeBuckle" id="sel_09" style="width:180px;">
	          <option value="">请选择</option>
	          <option value="1" <c:if test="${accountForm.includeBuckle =='1' }">selected="selected"</c:if> >是</option>
	          <option value="0" <c:if test="${accountForm.includeBuckle =='0' }">selected="selected"</c:if>>否</option>
        	</select></td>
	   		 <td>&nbsp;</td>
	    </tr>
	    <tr>
		    <th align="right">送达财务付款时间：</th>
		    <td><div class="date l" style="width:140px;">
		    <input name="payStartDate" id="payStartDate" type="text" autocomplete="off" value="<fmt:formatDate value="${payStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'payEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" placeholder="请您输入"  class="text02 l" style="width:95%;" /><i></i></div> <div class="date l" style="width:140px;">
		    <input name="payEndDate" id="payEndDate" type="text" autocomplete="off"  value="<fmt:formatDate value="${payEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'payStartDate\')||\'%y-%M-%d\'}',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" placeholder="请您输入"  class="text02 l" style="width:95%;" /><i></i></div></td>
		    <th align="right">初审会计：</th>
		    <td>
		        <input autocomplete="off" onfocus="if(this.value=='请填写初审会计'){this.value='';}this.style.color='#333333';autoCompletes();" 
			                     onkeyup="clean()"
			                     onblur="if(this.value==''||this.value=='请填写初审会计'){this.style.color='#b6b6b6';}" type="text" placeholder="请填写初审会计" name="trialAccount" id="trialAccount" class="text01" value="${accountForm.trialAccount }" style="width:175px;"/>
		        </td>
		    <th align="right">所处环节：</th>
		    <td><select class="ui-select" name="currentLink" id="sel_07" style="width:180px;">
		          <option value="">请选择</option>
		          <option value="初审录入" <c:if test="${accountForm.currentLink=='初审录入' }">selected="selected"</c:if> >初审录入</option>
		          <option value="信息补录" <c:if test="${accountForm.currentLink=='信息补录' }">selected="selected"</c:if>>信息补录</option>
		        </select></td>
		    <td>&nbsp;</td>
	    </tr>
	    <tr>
		    <th align="right">报账人：</th>
		    <td>
		    <input autocomplete="off"  onfocus="if(this.value=='请填写报账人'){this.value='';};this.style.color='#333333';autoCompletes();" 
			                     onkeyup="clean()"
			                     onblur="if(this.value==''||this.value=='请填写报账人'){this.style.color='#b6b6b6';}" placeholder="请输入报账人" type="text" name="sementPeople" id="sel_08" style="width:160px;" value="${accountForm.sementPeople }" class="text01" /></td>
		    <th align="right">是否专票抵扣：</th>
		    <td><select class="ui-select" name="deduction" id="sel_10" style="width:180px;">
	          <option value="">请选择</option>
	          <option value="1" <c:if test="${accountForm.deduction =='1' }">selected="selected"</c:if> >是</option>
	          <option value="0" <c:if test="${accountForm.deduction =='0' }">selected="selected"</c:if>>否</option>
        	</select></td>
		    <td>&nbsp;</td>
	    </tr>
</tbody>  
</form> 
</table>
  <div class="ge01"></div>
  <div class="tabpages">
    <ul class="l">
      <li <c:if test="${type==1 }"> class="current" </c:if>  onclick="setTab(1);">我创建的报账单据</li>
      <li  <c:if test="${type==2 }"> class="current" </c:if>  onclick="setTab(2);">我处理的报账单据</li>
      <c:if test="${iSbaozAdmin }">
      <li <c:if test="${type==3 }"> class="current" </c:if>  onclick="setTab(3);">所有报账单据</li>
    	</c:if>
    </ul>
    <div class="otherButtons r">
    	<!-- <div class="date l" style="width: 110px;min-width: 100px;border: 0;line-height: 28px;color: #40baff;font-size: 13px;">
    		纸质提交财务时间:
    	</div>
    	<div class="date l" style="width: 140px;min-width:120px;">
		    <input readonly="readonly" autocomplete="off" name="eSDate" id="eSDate" type="text"  placeholder="请选择开始时间"  class="text02 l" 
	     		onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width:90%;" />
    	</div>
    	<div class="date l" style="width: 140px;min-width:120px;">
		    <input readonly="readonly" autocomplete="off" name="eEDate" id="eEDate" type="text"  placeholder="请选择结束时间"  class="text02 l"  
	     		onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width:90%;" />
    	</div> -->
    	<a  class="btn_common01"  onclick="toEx('${type}');"><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导出</span></a>
    	<a  class="btn_common01"  onclick="export_invoice('${type}');"><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导出发票信息</span></a>
    </div>
  </div>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
  <tr>
    <!-- <th><input id="checkAll" type="checkbox"></th> -->
    <th>序号</th>
    <th>报账单号</th>
    <th>报账部门</th>
    <th>费用类型</th>
    <th>报账人</th>
    <th>初审会计</th>
    <th>纸质提交财务时间</th>
    <th>是否包含抵扣</th>
    <th>是否专票抵扣</th>
    <th>状态</th>
  </tr>
  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
  <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
    <%-- <td >
    	<input name="subBox" type="checkbox" value="${item[10]}">	
    </td> --%>
    <td >${i.index+1 }</td>
    <td  ><a style="color:#005ea7;" href="<%=basePath%>account/trailDetail?source=accountQuery&sourceType=${type}&orderId=${item[0]}&type=2&id=${item[10]}">${item[0]}</a></td>
    <td >${item[3]}</td>
    <td >${item[4]}</td>
    <td >${item[1]}</td>
    <td >${item[2]}</td>
    <td >${item[6]}</td>
    <td ><c:if test="${item[7]=='1'}">是</c:if><c:if test="${item[7]=='0'}">否</c:if></td>
    <td ><c:if test="${item[11]=='1'}">是</c:if><c:if test="${item[11]=='0'}">否</c:if></td>
    <td ><c:if test="${ empty item[9]}">-</c:if>
    <c:if test="${item[9]=='1'}">整改中</c:if>
    <c:if test="${ item[9]=='2'}">无问题</c:if>
    <c:if test="${ item[9]=='3'}">整改结束</c:if>
    <c:if test="${ item[9]=='4'}">退单</c:if></td>
  </tr>
 </c:forEach>
</table>
 <div class="gd_page">
      <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
 </div>
  <div class="ge01"></div>
</div>
<script src="/SRMC/dc/account/js/jquery.js"></script>
<script src="/SRMC/dc/account/js/ui-select.js"></script>
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>

<script>
	function nYearsLater(n){
	    var today=new Date();
	    var h=today.getFullYear() + n;
	    var m=today.getMonth()+1;
	    var d=today.getDate();
	    m= m<10?"0"+m:m;      
	    d= d<10?"0"+d:d;  
	    return h+"-"+m+"-"+d;
	}

function toEx(cursel){
	var $subBoxChecks = $("input[name='subBox']:checked");
	var accids = "";
	$.each($subBoxChecks,function(k,v){
		if(k == 0){
			accids += "'"+$(v).val()+"'";
		}else{
			accids += ",'"+$(v).val()+"'";
		}
	});
	
	document.forms[0].action= "<%=basePath%>account/toExportTrial2?type="+cursel+"&accids="+accids;
	document.forms[0].method="post";
	document.forms[0].submit();
	//重新设定form表单action
	document.forms[0].action= "<%=basePath%>account/accountQuery?type="+cursel;
}

//导出发票信息
function export_invoice(cursel){
	var $subBoxChecks = $("input[name='subBox']:checked");
	var accids = "";
	$.each($subBoxChecks,function(k,v){
		if(k == 0){
			accids += "'"+$(v).val()+"'";
		}else{
			accids += ",'"+$(v).val()+"'";
		}
	});
	document.forms[0].action= "<%=basePath%>account/exportInvoice?type="+cursel+"&accids="+accids;
	document.forms[0].method="post";
	document.forms[0].submit();
	//重新设定form表单action
	document.forms[0].action= "<%=basePath%>account/accountQuery?type="+cursel;
}

function query(cursel){
	 jQuery("#department").val(j("#departmentId").combotree("getText"));	
	 jQuery("#costType").val(j("#cosId").combotree("getText"));
	 document.forms[0].action= "<%=basePath%>account/accountQuery?type="+cursel;
	 document.forms[0].submit();
}

//tab切换
function setTab(cursel){
	var ss = jQuery("#type").val(cursel);
	query(cursel);
}

//部门联想
function autoDepartment(){
	jQuery("#sel_04").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>account/queryDepartment",
					dataType: "json",
					data: {
						Value: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
				     				value:item[0].userName,//+" - "+item[0].account+" - "+item[1].orgName
									userName:item[0].userName,
									userId:item[0].userId,
									account:item[0].account,
									orgName:item[1].orgName
								};
							}));
						}else{
							return false;
						};
					}
				});
			},
			minLength: 1,
			select: function( event, ui ) {
				jQuery("#sel_04").val(ui.item.userName);
				jQuery("#departmentId_input").val(ui.item.userId);
				return false;
			}
		});
		if(jQuery("#sel_04").val()!=null && jQuery("#sel_04").val()!=""){
		}
}

//用户可选择初审会计(trialAccount)  或 报账人(sel_08)
function autoCompletes(){
		jQuery("#sel_08").autocomplete({
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
									orgName:item[1].orgName
								};
							}));
						}else{
							return false;
						}
					}
				});
			},
			minLength: 1,
			select: function( event, ui ) {
				jQuery("#sel_08").val(ui.item.userName);
				jQuery("#sementPeopleId_input").val(ui.item.userId);
				return false;
			}
		});
		if(jQuery("#sel_08").val()!=null && jQuery("#sel_08").val()!=""){
		}
		
		jQuery("#trialAccount").autocomplete({
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
									orgName:item[1].orgName
								};
							}));
						}else{
							return false;
						}
					}
				});
			},
			minLength: 1,
			select: function( event, ui ) {
				jQuery("#trialAccount").val(ui.item.userName);
				jQuery("#trialAccountId_input").val(ui.item.userId);
				return false;
			}
		});
		if(jQuery("#trialAccount").val()!=null && jQuery("#trialAccount").val()!=""){
		}
	}
	
function clean(){
	if (event.keyCode == 13) {
        //js 监听对应的id
         //document.getElementById("demandFzrId_input_focus").value="";
      } 
}

///清空
function resets(){
	$('#pageForm')[0].reset();
	obj1.val("");
	obj2.val("");
	obj3.val("");
	obj6.val("");
	$(':input','#pageForm') 
	.not(':button, :submit, :reset, :hidden') 
	.val('') 
	.removeAttr('checked') 
	.removeAttr('selected');
	jQuery("#trialAccountId_input").val("");
	jQuery("#sementPeopleId_input").val("");
	jQuery("#department").val(j("#departmentId").combotree("getText"));	
	 jQuery("#costType").val(j("#cosId").combotree("getText"));
	 j("#departmentId").combotree({value:""});
	 j("#cosId").combotree({value:""});
}

var obj1;
var obj2;
var obj3;
var obj5;
var obj6;
// 将所有.ui-select实例化
$(function (){
	//jQuery("#eSDate").val(nYearsLater(-1));
	//jQuery("#eEDate").val(nYearsLater(0));

	$('.ui-select').ui_select();
	// 获取已经实例化的select对象
	obj = $('#sel_api').data('ui-select');
	obj1 = $('#sel_03').data('ui-select');
	obj2 = $('#sel_09').data('ui-select');
	obj3 = $('#sel_07').data('ui-select');
	obj5 = $('#sel_04').data('ui-select');
	obj6 = $('#sel_10').data('ui-select');
	
	j("#cosId").combotree({
		onBeforeSelect: function(node) {
			if (!j("#cosId").tree('isLeaf', node.target)) {
				return false;
			}
		},
   		width:170,
   		lines:'true',
		checkbox:true ,
		cascadeCheck:false,
		required:false,
		editable:false,
		multiple:false//设置是否多选	
	       });  
	
	j("#departmentId").combotree({
		
   		width:170,
   		lines:'true',
		checkbox:true ,
		cascadeCheck:false,
		required:false,
		editable:false,
		multiple:false//设置是否多选	
     });  
	
	$("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked); 
    });
    var $subBox = $("input[name='subBox']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
    });
});

</script>   

</body>
</html>

