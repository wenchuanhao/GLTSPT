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
	<base target="_self" />
	<title>新增-年度目标</title>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<span class="searchCondition_header">新增-年度目标</span>
	</div>
	<form action=""  method="post"  id="form1">
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" id="table01">
		<input type="hidden" name="id" id="id" value="${vo.id}"/>
		<input type="hidden" name="parentId" id="parentId" value="${vo.parentId}"/>
		<tr>
		    <th width="40%" align="right"><b></b>年度：</th>
		    <td>
		    	<select class="ui-select" id="column01"  name="column01" style="width:307px;">
		    			${options}
		        </select>		    	
		    </td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>年度投资(万元)：</th>
		    <td><input name="column02"  id="column02"  type="text" class="text01" style="width:300px;" placeholder="请填写年度投资(万元)"  value="<fmt:formatNumber value="${vo.column02}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/></td>
	    </tr>
	    
	    <tr>
	    	<th align="right"><b>*</b>资本开支目标(万元)：</th>
		    <td><input name="column03"  id="column03"  type="text" class="text01" style="width:300px;" placeholder="请填写资本开支目标(万元)"  value="<fmt:formatNumber value="${vo.column03}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>
		    </td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>至上年度安排投资计划(万元)：</th>
		    <td><input name="column04"  id="column04"  type="text" class="text01" style="width:300px;" placeholder="请填写至上年度安排投资计划(万元)"  value="<fmt:formatNumber value="${vo.column04}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/>
		    </td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b>*</b>年度转资目标(万元)：</th>
		    <td><input name="column05"  id="column05"  type="text" class="text01" style="width:300px;" placeholder="请填写年度转资目标(万元)"  value="<fmt:formatNumber value="${vo.column05}" type="currency"  maxFractionDigits="6"/>" maxlength="11"/></td>
	    </tr>
	    
	    <tr>
		    <th align="right"><b></b>记录人：</th>
		    <td>
		    		<input name="column08"  id="column08"  type="hidden" value="${vo.column08}" />
		    		<input name="column06"  id="column06"  type="text" class="text01" style="width:300px;" placeholder="" value="${vo.column06}" readonly="readonly"/>
		    </td>
	    </tr>
	    <tr>
		    <th align="right"><b></b>记录时间：</th>
		    <td>
		    	<input name="column07"  id="column07"  type="text" class="text01" style="width:300px;"  placeholder="" 
		    	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
		    	value="<fmt:formatDate value="${vo.column07}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"/>		    
		    </td>
	    </tr>	    
	   
		<tr>
	   		<th colspan="2" align="center" height="50">
	   			<input name="" type="button" class="btn_common02" id="save" onclick="ev_save()" value="保 存" />
	   		 	<input name="" type="button" class="btn_common04" onclick="history.back()" value="返 回" />
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
		ev_list();
	}
	else if(m != "" && m == "e"){
		alert("保存失败");
	}
	
	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});

function ev_list(){
window.location.href='<%=basePath%>tzbm/yearList1?parentId=${vo.parentId}';
}

/**
 * 必填项
 */
function ev_required_item(){
		var k = 0;
		var b = true;
		//年度投资
       	var column = $("#column02");
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
  		//资本开支目标
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
  		//至上年度安排投资计划
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
  		//年度转资目标
       	var column = $("#column05");
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
		$("#form1").attr("action","<%=basePath%>tzbm/saveOrUpdateYear1");
		$("#form1").submit();
	}
}
</script>