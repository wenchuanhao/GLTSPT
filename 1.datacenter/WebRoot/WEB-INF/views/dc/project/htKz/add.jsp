<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:if test="${vo.id eq null}">
	<title >新增-合同开支</title>
	</c:if>
	<c:if test="${vo.id ne null}">
	<title >修改-合同开支</title>
	</c:if>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<script src="/SRMC/rmpb/js/isIe.js"></script>
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="/SRMC/rmpb/js/isIe.js"></script>
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<c:if test="${vo.id eq null}">
		<span class="searchCondition_header">新增-合同开支</span>
		</c:if>
		<c:if test="${vo.id ne null}">
		<span class="searchCondition_header">修改-合同开支</span>
		</c:if>			
	</div>
	
	<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<tr>
		    <c:set var="zxmHt" value="${vo.zxmHt}"/>
		    <th width="20%" align="right"><b>*</b>合同编号：</th>
		    <input type="hidden" name="column01" id="column01" value="${vo.column01}"/>
		    <td><input name="column01Code"  id="column01Code"  type="text" class="text01" style="width:300px;" placeholder="请填写合同编号"  value="${zxmHt.column01}" onfocus="autoCompletes();" maxlength="20"/>
		    <a onclick="ev_selectList()" style="cursor: pointer;color: blue;" title="选择项目合同">选择</a></td>
		    <th align="right"><b>*</b>累计形象进度/MIS接收金额：</th>
		    <td><input name="column07"  id="column07"  type="text" class="text01" style="width:300px;" placeholder="请填写累计形象进度/MIS接收金额"  value="<fmt:formatNumber value="${vo.column07}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>万元</td>		    		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>合同项目名称：</th>
		    <td><input name="column01Name"  id="column01Name"  type="text" class="text01" style="width:300px;" placeholder="请填写合同项目名称"  value="${zxmHt.column03}" onfocus="autoCompletes();" maxlength="50"/>
		    <a onclick="ev_selectList()" style="cursor: pointer;color: blue;" title="选择项目合同">选择</a></td>
		    <th align="right"><b>*</b>本年形象进度/MIS接收金额：</th>
		    <td><input name="column08"  id="column08"  type="text" class="text01" style="width:300px;" placeholder="请填写本年形象进度/MIS接收金额"  value="<fmt:formatNumber value="${vo.column08}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>万元</td>		    
	    </tr>
	    
	    <tr>
	    	<th align="right"><b></b>投资编号：</th>
		    <td><input name="column02"  id="column02"  type="text" class="text01" style="width:300px;background-color: #DBDBDB" placeholder=""  value="${vo.column02}" readonly="readonly" /></td>
		    <th align="right"><b>*</b>累计转资金额：</th>
		    <td><input name="column09"  id="column09"  type="text" class="text01" style="width:300px;" placeholder="请填写累计转资金额"  value="<fmt:formatNumber value="${vo.column09}" type="currency"  maxFractionDigits="6"/>"  maxlength="11"/>万元</td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>合同不含税金额：</th>
		    <td><input name="column03"  id="column03"  type="text" class="text01" style="width:300px;background-color: #DBDBDB" placeholder="请填写合同不含税金额"  value="<fmt:formatNumber value="${vo.column03}" type="currency"  maxFractionDigits="6"/>" maxlength="11" readonly="readonly"/>万元</td>
		    <th align="right"><b>*</b>本年转资金额：</th>
		    <td><input name="column10"  id="column10"  type="text" class="text01" style="width:300px;" placeholder="请填写本年转资金额"  value="<fmt:formatNumber value="${vo.column10}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>万元</td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>合同含税金额：</th>
		    <td><input name="column04"  id="column04"  type="text" class="text01" style="width:300px;background-color: #DBDBDB" placeholder="请填写合同含税金额"  value="<fmt:formatNumber value="${vo.column04}" type="currency"  maxFractionDigits="6"/>" maxlength="11" readonly="readonly"/>万元</td>
		    <th align="right"><b>*</b>累计付款金额：</th>
		    <td><input name="column11"  id="column11"  type="text" class="text01" style="width:300px;" placeholder="请填写累计付款金额"  value="<fmt:formatNumber value="${vo.column11}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>万元</td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>合同对方：</th>
		    <td><input name="column05"  id="column05"  type="text" class="text01" style="width:300px;background-color: #DBDBDB" placeholder=""  value="${vo.column05}" maxlength="30" readonly="readonly"/>
		    <th align="right"><b>*</b>时间：</th>
		    <td>
		    	<input name="column12"  id="column12"  type="text" class="text01" style="width:300px;" placeholder="请填写时间"  
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column12}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"/>		    
		    </td>		    
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>合同类型：</th>
		    <input type="hidden" name="column06" id="column06" value="${vo.column06}"/>
		    <td colspan="3" >
		    	<c:if test="${vo.column06 ne null}">
		    	<input name="column06Name"  id="column06Name"  type="text" class="text01" style="width:300px;background-color: #DBDBDB"  value="${vo.column06 eq '1' ? '费用类':'订单类'}"  readonly="readonly"/>
		    	</c:if>
		    	<c:if test="${vo.column06 eq null}">
		    	<input name="column06Name"  id="column06Name"  type="text" class="text01" style="width:300px;background-color: #DBDBDB"  readonly="readonly"/>
		    	</c:if>		    	
		    </td>
	    </tr>
	    <tr>
		    <th align="right"><b>*</b>负责人：</th>
		    <input type="hidden" name="column13" id="column13" value="${vo.column13}"/>
		    <td colspan="3">
		    	<input name="column13Name"  id="column13Name"  type="text" class="text01" style="width:300px;background-color: #DBDBDB" placeholder="请填写负责人"  
		    	value="${vo.column13SysUser.userName}" maxlength="10" readonly="readonly"/>		    
		    </td>		    
	    </tr>	    
		<tr>
	   		<th colspan="4" align="center" height="50">
	   			<input name="" type="button" class="btn_common02" id="save" onclick="ev_save()" value="保 存" />
	   		 	<input name="" type="button" class="btn_common04" onclick="window.close()" value="关闭" />
	   		</th>
	   </tr>    
  </table>
  </form>
</div>
</body>
</html>

<script type="text/javascript">
$(document).ready(function(){
	var m = "${mess}";
	if(m != "" && m == "s"){
		if("${form_S}" != "" && "${form_S}" == "modify"){
			window.opener.ev_search();
		}else{
			window.opener.ev_reset();
		}
		window.close();
	}
	else if(m != "" && m == "e"){
		alert("保存失败");
	}

	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});

function ev_list(){
window.location.href='<%=basePath%>htKz/list';
}

/**
 * 必填项
 */
function ev_required_item(){
		var k = 0;
		var b = true;
      	
  		//合同编号
		var column = $("#column01Code");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}  		
       	//累计形象进度/MIS接收金额
		var column = $("#column07");
       	var v = column.val();
  		if(v == ""){
  			k++;bindC(column);
  		}else{
  			var rv = validate(v);
	   		if(rv != ""){
	   			$(column).css("border-color","red");
	   			alert(rv);return false;
	   		}else{
	   			$(column).css("border-color","");
	   			$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   		}   		
  		}
  		//合同项目名称
		var column = $("#column01Name");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}      	
      	
       	//本年形象进度/MIS接收金额
		var column = $("#column08");
       	var v = column.val();
  		if(v == ""){
  			k++;bindC(column);
  		}else{
  			var rv = validate(v);
	   		if(rv != ""){
	   			$(column).css("border-color","red");
	   			alert(rv);return false;
	   		}else{
	   			$(column).css("border-color","");
	   			$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   		}  		
  		}       	
      	//累计转资金额
		var column = $("#column09");
       	var v = column.val();
  		if(v == ""){
  			k++;bindC(column);
  		}else{
  			var rv = validate(v);
	   		if(rv != ""){
	   			$(column).css("border-color","red");
	   			alert(rv);return false;
	   		}else{
	   			$(column).css("border-color","");
	   			$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   		}   		
  		}      	
      	//合同不含税金额
		var column = $("#column03");
       	var v = column.val();
  		if(v == ""){
  			k++;bindC(column);
  		}else{
  			var rv = validate(v);
	   		if(rv != ""){
	   			$(column).css("border-color","red");
	   			alert(rv);return false;
	   		}else{
	   			$(column).css("border-color","");
	   			$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   		}   		
  		}      	
      	//本年转资金额
		var column = $("#column10");
       	var v = column.val();
  		if(v == ""){
  			k++;bindC(column);
  		}else{
  			var rv = validate(v);
	   		if(rv != ""){
	   			$(column).css("border-color","red");
	   			alert(rv);return false;
	   		}else{
	   			$(column).css("border-color","");
	   			$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   		}   		
  		}      	
      	//合同含税金额
		var column = $("#column04");
       	var v = column.val();
  		if(v == ""){
  			k++;bindC(column);
  		}else{
  			var rv = validate(v);
	   		if(rv != ""){
	   			$(column).css("border-color","red");
	   			alert(rv);return false;
	   		}else{
	   			$(column).css("border-color","");
	   			$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   		}  		
  		}      	
      	//累计付款金额
		var column = $("#column11");
       	var v = column.val();
  		if(v == ""){
  			k++;bindC(column);
  		}else{
  			var rv = validate(v);
	   		if(rv != ""){
	   			$(column).css("border-color","red");
	   			alert(rv);return false;
	   		}else{
	   			$(column).css("border-color","");
	   			$(column).val(v.replace("￥","").replace(new RegExp(",","g"),""));
	   		}  		
  		}      	
      	//时间
		var column = $("#column12");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}       	
      	//负责人
		var column = $("#column13Name");
		var v = column.val();
		if(v == ""){
           	k++;bindC(column);			
		}       	
      	
      	if(k > 0){
      		alert("*为必填字段");
      		b = false;
      	}
      	
		return b;
}

function bindC(field){
	$(field).css("border-color","red");
    $(field).bind("click", function(){
		$(this).css("border-color","");
	});
}

function validate(value){
    if(isNaN(value.replace("￥","").replace(new RegExp(",","g"),""))){
        return "请输入数字!";
    }
    return "";  
 }
  
function ev_save(){
	if(ev_required_item()){
		$("#form1").attr("action","<%=basePath%>htKz/saveOrUpdate");
		$("#form1").submit();
	}
}

//自动匹配合同
function autoCompletes(){
		//合同编号联想功能	
		jQuery("#column01Code").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>zxmHt/searchHt",
					dataType: "json",
					data: {
						column01: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
									value:item.column01+" "+item.column03,
									id:item.id,//合同ID
									code:item.column01,//合同编码
									name:item.column03,//合同名称
									column03:item.column09,//合同不含税金额
									column04:item.column11,//合同含税金额
									column05:item.column14,//合同对方
									tzCode:item.zxm.tzbm.column02,//投资编号
									userId:item.column21Id,//负责人
									userName:item.column21Name,//负责人
									column06:item.column06//订单类型
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
					jQuery("#column01").val(ui.item.id);
					jQuery("#column01Code").val(ui.item.code);
					jQuery("#column01Name").val(ui.item.name);
					jQuery("#column02").val(ui.item.tzCode);
					jQuery("#column03").val(ui.item.column03);//合同不含税金额
					jQuery("#column04").val(ui.item.column04);//合同含税金额
					jQuery("#column05").val(ui.item.column05);//合同对方
					jQuery("#column13").val(ui.item.userId);
					jQuery("#column13Name").val(ui.item.userName);
					jQuery("#column06").val(ui.item.column06);
					jQuery("#column06Name").val(ui.item.column06 == '1' ? '费用类':'订单类');					
					return false;
			}
		});
		
		//合同项目名称联想功能	
		jQuery("#column01Name").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>zxmHt/searchHt",
					dataType: "json",
					data: {
						column03: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
									value:item.column01+" "+item.column03,
									id:item.id,//合同ID
									code:item.column01,//合同编码
									name:item.column03,//合同名称
									column03:item.column09,//合同不含税金额
									column04:item.column11,//合同含税金额
									column05:item.column14,//合同对方
									tzCode:item.zxm.tzbm.column02,//投资编号
									userId:item.column21Id,//负责人
									userName:item.column21Name,//负责人
									column06:item.column06//订单类型
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
					jQuery("#column01").val(ui.item.id);
					jQuery("#column01Code").val(ui.item.code);
					jQuery("#column01Name").val(ui.item.name);
					jQuery("#column02").val(ui.item.tzCode);
					jQuery("#column03").val(ui.item.column03);//合同不含税金额
					jQuery("#column04").val(ui.item.column04);//合同含税金额
					jQuery("#column05").val(ui.item.column05);//合同对方
					jQuery("#column13").val(ui.item.userId);
					jQuery("#column13Name").val(ui.item.userName);
					jQuery("#column06").val(ui.item.column06);
					jQuery("#column06Name").val(ui.item.column06 == '1' ? '费用类':'订单类');				
					return false;
			}
		});	
	
	//负责人联想功能	
	jQuery("#column13Name").autocomplete({
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
					jQuery("#column13").val(ui.item.userId);
					jQuery("#column13Name").val(ui.item.userName);
					return false;
		}
	});			
}
<%--function ev_selectList(){--%>
<%--	var returnValue = window.showModalDialog("<%=basePath%>zxmHt/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); --%>
<%--	var v = returnValue.split("_");--%>
<%--	jQuery("#column01").val(v[0]);//合同资源ID--%>
<%--	jQuery("#column01Code").val(v[1]);//合同编号--%>
<%--	jQuery("#column01Name").val(v[2]);//合同名称--%>
<%--	jQuery("#column02").val(v[3]);//投资编号--%>
<%--	jQuery("#column03").val(v[4]);//合同不含税金额--%>
<%--	jQuery("#column04").val(v[5]);//合同含税金额--%>
<%--	jQuery("#column05").val(v[6]);//合同对方--%>
<%--	jQuery("#column13").val(v[7]);--%>
<%--	jQuery("#column13Name").val(v[8]);--%>
<%--	jQuery("#column06").val(v[9]);--%>
<%--	jQuery("#column06Name").val(v[9] == '1' ? '费用类':'订单类');--%>
<%--}--%>
function ev_selectList(){
	//判断是不是IE浏览器
	if(isIE()){
		var returnValue = window.showModalDialog("<%=basePath%>zxmHt/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
		putValue(returnValue);
	}else{
		//其他浏览器
		window.open("<%=basePath%>zxmHt/selectList",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px");
	}
}
//给父窗口元素赋值
function putValue(returnValue){
		var v = returnValue.split("_");
		jQuery("#column01").val(v[0]);//合同资源ID
		jQuery("#column01Code").val(v[1]);//合同编号
		jQuery("#column01Name").val(v[2]);//合同名称
		jQuery("#column02").val(v[3]);//投资编号
		jQuery("#column03").val(v[4]);//合同不含税金额
		jQuery("#column04").val(v[5]);//合同含税金额
		jQuery("#column05").val(v[6]);//合同对方
		jQuery("#column13").val(v[7]);
		jQuery("#column13Name").val(v[8]);
		jQuery("#column06").val(v[9]);
		jQuery("#column06Name").val(v[9] == '1' ? '费用类':'订单类');
}

</script>