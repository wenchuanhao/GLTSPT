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
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!--树js -->
    <script src="/SRMC/rmpb/js/dtree.js" type=text/javascript></script>
    <script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<span class="searchCondition_header" style="width:130px;">采购量化角色新增</span>
	</div>
	<div>
	      <div class="d_tree_div" style="float:left; width:35%; ">
							<script type="text/javascript">
		        d = new dTree('d');
				<c:forEach items="${orgs}" var="org">
					<c:choose>
					<c:when test="${org.orgName == '南方基地'}">
							d.add('${org.organizationId}', '-1', "<font oncontextmenu=loadMenu('${org.organizationId}') color=#EF5900><b>公司组织架构</b></font>", "javascript:doSearch('${org.organizationId}','${org.orgName}');", '', '', '','//');
						</c:when>
						<c:otherwise>
							d.add('${org.organizationId}','${org.parentId}', "<font oncontextmenu=loadMenu('${org.organizationId}')>${org.orgName}</font>", "javascript:doSearch('${org.organizationId}','${org.orgName}');", '', '');
						</c:otherwise>
					</c:choose>	
				</c:forEach>
			    document.write(d);
			    			 
			    function doSearch(organizationId,orgName){

				if(jQuery("#selected_trialAccount").children("[id='" + organizationId + "']").length != 0){
					//jQuery(target).val("");
					return;
				}else{
					jQuery("#selected_trialAccount").append('<a href="javascript:void(0);" class="" id="' + organizationId + '">\
					                                            <input type="hidden" id="orgIds" name="orgIds" value="'+organizationId+'">\
																		<span style="padding: 0 3px 0 0;">' + orgName + '</span>\
																		<img onclick="jQuery(this).parent().remove();setAccount();" src="/SRMC/dc/images/close_icon01.png" width="10" height="10" />\
																	  </a>');
					jQuery(target).val("");
					setAccount();
					return false;
				}
				return false;
				}
			    
			    function clearCookieDD(){
				    var st=null;    
				    st=document.getElementById("st").value;        
				    if(st==null||st==""){              
				       d.closeAll();
				    };   
				}
				
				 function clearApartMent(){
				    jQuery("#manageApartMent").val("");
				}
		
	</script>
							<!-- 右键菜单结束 -->
	</div>
	<div class="" style="float:left;width:50%;">
	<form name="form" id="pagePurchaseForm" method="post"  >
    	
     <table cellpadding="0" cellspacing="5" class="taskCreate" width="100%">  	
     <tr>
  		<th width="9%"  align="right"><b style="color:red;">*</b>角色名：</th>
	    <td width="30%">
	        <input id="id" name="id" type="hidden" value="${purchaseRole.id }"    />  	 	        
	    	<input id="roleName" value="${purchaseRole.roleName }"  maxlength="100" name="roleName"  maxlength="30" type="text" class="text01" style="width:200px;"  />  	 
		</td> 
  		
	</tr>
	 <tr>
  		<th width="9%" align="right"><b style="color:red;">*</b>分管人：</th>
	    <td width="30%">
	     <!--  <input  type="hidden" name="userId" id="accountingId_input" value="${purchaseRole.manageAcount }"/> -->	    	    
         <input   onfocus="autoCompletes(this);"  onkeyup="clean(this)" type="text" name="manageName" id="sel_09" style="width:200px;" value="" class="text01" />
           <span id="selected_manageName">
             <c:forEach items="${users}" var="uo" varStatus="i">
              <a href="javascript:void(0);" class="" id="${uo.userId}">
				 <input type="hidden" id="userId" name="userId" value="${uo.userId}">
				 <span style="padding: 0 3px 0 0;">${uo.userName}</span>
				 <img onclick="jQuery(this).parent().remove();setAccount();" src="/SRMC/dc/images/close_icon01.png" width="10" height="10" />
			  </a>
			   </c:forEach>
		   </span>

	    </td> 
  		
	</tr>
	<tr>
	 <th width="9%" align="right"><b style="color:red;">*</b>分管部门：</th>
        <td width="30%"> 
         <!--  <textarea disabled="disabled" readonly="readonly" id="manageApartMent" name="manageApartMent"   rows="3"  type="text"  class="text01"  maxlength="500" style="disable:true;width:220px;overflow:hidden;min-height: 80px;"></textarea> -->
          	 <span id="selected_trialAccount">
          	   <c:forEach items="${aparts}" var="vo" varStatus="i">
          	     <a href="javascript:void(0);" class="" id="${vo.organizationId}">
			        <input type="hidden" id="orgIds" name="orgIds" value="${vo.organizationId}">
			        <span style="padding: 0 3px 0 0;">${vo.orgName} </span>
			        <img onclick="jQuery(this).parent().remove();setAccount();" src="/SRMC/dc/images/close_icon01.png" width="10" height="10" />
			     </a>
          	   </c:forEach>
			</span>  
        </td>  		
	</tr>
   
	 <!--   ============== -->
	 <tr>
               <td colspan="2" align="center">
               <input name="" type="button" onclick="ev_edit()" class="btn_common02" value="保 存" />  <input name="" type="button" onclick="back()" class="btn_common01" value="取 消" /></td>	   		</th>
	   </tr>  
	</table>
	</form>
</div>
</div>
</body>
</html>

<script type="text/javascript">
$(document).ready(function(){
 $('.ui-select').ui_select();

});


//用户可选择用户(sel_09) 
//用户可选择用户(sel_09) 
function autoCompletes(){
		jQuery("#sel_09").autocomplete({
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
									orgName:item[1].orgName,
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
				if(jQuery("#selected_manageName").children("[id='" + ui.item.userId + "']").length != 0){
					jQuery(target).val("");
					return;
				}else{
					jQuery.ajax({
                   type:"POST",
                url:"<%=basePath%>purchase/purchaseRoleByAcount",
                data:{"manageAcount":ui.item.userId},
                    success:function(data){
        	         if(data == 1){
        		     alert("已存在此分管人,");
        		        //$('#save').attr('disabled',true);
   
        	         }else{
        	           // $('#save').attr('disabled',false); 
        	            jQuery("#selected_manageName").append('<a href="javascript:void(0);" class="" id="' + ui.item.userId + '">\
					                                                   <input type="hidden" id="userId" name="userId" value="'+ui.item.userId+'">\
																		<span style="padding: 0 3px 0 0;">' + ui.item.userName + '</span>\
																		<img onclick="jQuery(this).parent().remove();setAccount();" src="/SRMC/dc/images/close_icon01.png" width="10" height="10" />\
																	  </a>');
        	           
        	       }
               },
                    error:function(){
                    alert("查询此分管人异常！");
                     }
                   });
				        
					return false;
				}
				return false;
			}
		});
	}
	function clean(){
		if (event.keyCode == 13) {
        } 
	}

function ev_edit(){ 
var roleName = $("#roleName").val();
if(roleName == ""){
alert("请输入角色名称");
return;
}

var accountingId = jQuery("#selected_manageName").children().length ;
if(accountingId == 0){
alert("请输入分管人");
return;
}

var trialAccount = jQuery("#selected_trialAccount").children().length;
if(trialAccount == 0){
alert("请点击左侧选择分管部门名称");
return;
}

$.ajax({
		url:"<%=basePath%>purchase/updatePurchaseRole",
    	dataType:'json',
        async:true,
        type:'POST',
        data:$('#pagePurchaseForm').serialize(),// 你的formid
        success:function (result) {
        	if(result == '1'){
        		alert("修改成功");
	            window.location.href="<%=basePath%>purchase/purchaseRoleManage";		
	        	
        	}
        },
        error:function () {
        	alert("修改失败");
        }
	});
}

//返回
function back(){
		//parent.window.location.reload();
	window.location.href="<%=basePath%>purchase/purchaseRoleManage";		
		
}

</script>