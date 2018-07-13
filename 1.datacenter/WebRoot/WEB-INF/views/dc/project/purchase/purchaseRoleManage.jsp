<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>采购量化角色配置</title>
		<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script src="/SRMC/dc/js/jquery.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
		<link rel="stylesheet" type="text/css" href="/SRMC/dc/css/combotree/easyui.css">
		<script type="text/javascript" src="/SRMC/dc/js/combotree/jquery.easyui.min.js"></script>  
		<link href="/SRMC/rmpb/css/date_input.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/common2.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="gl_import_m">
<form  action="" method="post" id="pageForm">
<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${purchaseForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${purchaseForm.pageSize}" id="pageSize"	name="pageSize"/>
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
		<input name="" onclick="query()" type="button" class="btn_search r" value="查询" />
	</div>
	  <table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	  	<tr>
		    <th width="10%" align="right">角色名：</th>
		    <td width="17%">
                <input id="roleName" name="roleName" value="${purchaseForm.roleName}" type="text" placeholder="请填写角色名" class="text01" style="width:195px;"  />
                 
		    </td>		  
		    <th width="7%" align="right">分管人：</th>
		    <td width="17%">
		         <input   onfocus="autoCompletes(this);"  onkeyup="clean(this)" type="text" name="manageName" id="sel_09" style="width:180px;" value="${PurchaseForm.manageName }" class="text01" />
		    </td>
	  	</tr>
	</table>
  <div class="ge01"></div>
  <div class="tabpages">
    <ul class="l">
      <li class="current">采购量化角色配置</li>
    </ul>
    <div class="otherButtons r">
     <a id="addTrialConfig" href="javascript:addRole();" class="btn_common01" ><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>  
     <a  class="btn_common01"  onclick="del()"><img src="/SRMC/dc/images/btnIcon/del.png" /><span>删 除</span></a></div>
  </div>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
  <tr>
    <th><input type="checkbox" id="checkAll"></th>
    <th>序号</th>
    <th>角色名</th>
    <th>分管人</th>
    <th>分管部门(科室)</th>
    <th>操作</th>
  </tr>
  <c:forEach items="${ITEMPAGE.items}" var="list" varStatus="i">
   <tr>
    <td><input type="checkbox" name="checkid" id="checkid" value="${list.id }"></td>
    <td>${i.index+1}</td>
    <td>${list.roleName}</td>
    <td>${list.manageName}</td>
    <td>${list.manageApartMent}</td>
    <td><b><a id="edit${i.index+1 }" data="${list.id}"  href="javascript:ev_edit('${list.id}')" ><img src="/SRMC/dc/images/btnIcon/modify.png" />编辑</a></b> 
    <i><a  href="javascript:del_role('${list.id}')" ><img src="/SRMC/dc/images/btnIcon/del.png" />删除</a></i></td>
  </tr>
  </c:forEach>
</table>
 <div class="gd_page">
   <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
 </div>
  <div class="ge01"></div>
</div>
</form>   
<script src="/SRMC/dc/account/js/jquery.js"></script>
<script src="/SRMC/dc/account/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	

<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script>
//部门联想
function autoDepartment(){
	jQuery("#sel_02").autocomplete({
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
						}
					}
				});
			},
			minLength: 1,
			select: function( event, ui ) {
				jQuery("#sel_02").val(ui.item.userName);
				jQuery("#departmentId_input").val(ui.item.userId);
				return false;
			}
		});
		if(jQuery("#sel_04").val()!=null && jQuery("#sel_04").val()!=""){
		}
}

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
				jQuery("#sel_09").val(ui.item.userName);
				jQuery("#accountingId_input").val(ui.item.userId);
				return false;
			}
		});
	}
	
	function clean(){
		if (event.keyCode == 13) {
        } 
	}
	
	
	function del(){
	   var $subBoxChecks = $("input[name='checkid']:checked");
	var zbIds = "";
	$.each($subBoxChecks,function(k,v){
		if(k == 0){
			zbIds += $(v).val();
		}else{
			zbIds += "|"+$(v).val();
		}
	});
	/*var ids ="";
	jQuery("input[name='checkid']:checked").each(function(){
		if(ids==""){
		ids+=""+jQuery(this).val()+"";
		}else{
	   		ids+= ","+jQuery(this).val()+"";
	   	}
	});*/
	//alert(zbIds);
	if(zbIds!=""){
		if(!confirm("删除后无法恢复,确定删除该选项吗？")){return;}
		document.forms[0].action= "<%=basePath%>purchase/purchaseRoleDelByBatch?ids="+zbIds ;
		document.forms[0].method="post";
		document.forms[0].submit();
	}else{
		alert("请选择要删除的数据!");
	}
}

function query(){
	jQuery("#pageForm").attr("action","<%=basePath%>purchase/purchaseRoleManage");
	document.forms[0].submit();
}


 $(document).ready(function(){
	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	// 获取已经实例化的select对象
	var obj = $('#sel_api').data('ui-select');
	
	$("#checkAll").click(function() {
        $('input[name="checkid"]').attr("checked",this.checked); 
    });
    var $subBox = $("input[name='checkid']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='checkid']:checked").length ? true : false);
    });
	
	var url = "<%=basePath%>purchase/purchaseRoleAdd";	
	//初始化新增问题fancybox	   
  /* jQuery('#addTrialConfig').fancybox({
   			'width' : '80%',
   			'height': '100%',
   			'href'				:	url,
	   		'autoScale' : false,
			'transitionIn' : 'none',
			'transitionOut' : 'none',
			'type' : 'iframe',
			'scrolling' : 'yes',
			'centerOnScroll' : false,
			'onStart' : function(current, previous) {}  			
   });
   */
   //初始化编辑fancybox
  // var url,me;
  // jQuery("a[id^=edit]").each(function(){
	  // me = $(this);
	  // url="<%=basePath%>purchase/purchaseRoleEdit?id="+me.attr("data");		   
	   //me.fancybox({
		  // 'width'				: '80%',
  		//	'height'			: '100%',
  			//'scrolling'			:'yes',
  		//	'autoScale'			: false,//自适应窗口大小
  			//'type' : 'iframe',
  			//'transitionIn'		: 'none',
  		//	'transitionOut'		: 'none',	  			
  			//'href'				:	url
	  // });
 //  });
   
    
});

function del_role(id){
    
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>purchase/purchaseRoleDelByid",
        data:"id=" + id,
        success:function(data){
        	if(data == 1){
        		alert("删除成功！");
        		query();
        	}else{
        		alert("删除失败！");
        	}
        },
        error:function(){
            alert("删除失败！");
        }
    });
}


function addRole(){
 window.location.href = "<%=basePath%>purchase/purchaseRoleAdd?key="+Math.random();

}

/**
编辑
*/
function ev_edit(id){
window.location.href = "<%=basePath%>purchase/purchaseRoleEdit?id="+id+"&key="+Math.random();
}


</script>                
</body>
</html>