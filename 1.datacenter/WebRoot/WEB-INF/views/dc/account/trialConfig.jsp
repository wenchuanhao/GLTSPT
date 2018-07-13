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
<title>初审会计配置</title>
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
<body>
<div class="gl_import_m">
<form  action="" method="post" id="pageForm">
<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${accountForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${accountForm.pageSize}" id="pageSize"	name="pageSize"/>
    <input type="hidden" name="accountingId" id="accountingId_input" />
    <input type="hidden" name="department" id="department" value="${accountForm.department}"/>
    <input type="hidden" name="costType" id="costType" value="${accountForm.costType}"/>
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
		<input name="" onclick="query()" type="button" class="btn_search r" value="查询" />
	</div>
	  <table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
	  	<tr>
		    <th width="10%" align="right">费用类型：</th>
		    <td width="17%">
		    <div class="checxTreeWrap"  style="width:170px;">
		    <%-- <input id="cosId" name="cosId" value="${accountForm.costType}"/>  --%>
		    <select  multiple style="width:200px" id="cosId" name="cosId" class="easyui-combotree" data-options="url:'<%=basePath%>/account/getCostType',method:'get'" multiple style="width:180px;">
		    <option selected="selected">${accountForm.costType}</option>
		    </select>
		    </div>
		        </td>
		    <th width="10%" align="right">报账部门：</th>
		    <td width="17%">
		    <div class="checxTreeWrap"  style="width:170px;">
		    <select multiple style="width:185px" id="departmentId" name="departmentId" class="easyui-combotree" data-options="url:'<%=basePath%>/account/getDepartment',method:'get'" >
		    	<option selected="selected">${accountForm.department}</option>
		    </select>
		    </div>
		    </td>
		    <%-- <input   onfocus="if(this.value=='请输入报账部门'){this.value='';};this.style.color='#333333';autoDepartment();" 
			                     onkeyup="clean()"
			                     onblur="if(this.value==''||this.value=='请输入报账部门'){this.style.color='#b6b6b6';}" placeholder="请输入报账部门" type="text" name="department" id="sel_02" style="width:160px;" value="${accountForm.department}" class="text01" /> --%>
		    
	  	</tr>
	  	<tr>
	  		<th width="7%" align="right">用户：</th>
		    <td width="17%">
		    <input   onfocus="autoCompletes(this);"  onkeyup="clean(this)" type="text" name="accounting" id="sel_09" style="width:168px;" value="${accountForm.accounting }" class="text01" />
					</td>
	  	</tr>
	</table>
  <div class="ge01"></div>
  <div class="tabpages">
    <ul class="l">
      <li class="current">初审会计配置</li>
    </ul>
    <div class="otherButtons r"><a id="addTrialConfig" class="btn_common01" ><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>  <a  class="btn_common01"  onclick="del()"><img src="/SRMC/dc/images/btnIcon/del.png" /><span>删 除</span></a></div>
  </div>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
  <tr>
    <th><input type="checkbox" id="checkAll"></th>
    <th>序号</th>
    <th>报账部门</th>
    <th>费用类型</th>
    <th>初审会计</th>
    <th>操作</th>
  </tr>
  <c:forEach items="${ITEMPAGE.items}" var="list" varStatus="i">
   <tr>
    <td><input type="checkbox" name="checkid" id="checkid" value="${list.id }"></td>
    <td>${i.index+1}</td>
    <td>${list.department}</td>
    <td>${list.costType}</td>
    <td>${list.accounting}</td>
    <td><b><a id="edit${i.index+1 }" data="${list.id}"><img src="/SRMC/dc/images/btnIcon/modify.png" />编辑</a></b> <i><a onclick="deleteCig('${list.id }')"><img src="/SRMC/dc/images/btnIcon/del.png" />删除</a></i></td>
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
	
	//删除单个
	function deleteCig(id){
		if(!confirm("删除后无法恢复,确定删除该选项吗？")){return;}
		document.forms[0].action= "<%=basePath%>account/delTrialConfig?requireIds="+id ;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	function del(){
	var ids ="";
	jQuery("input[name='checkid']:checked").each(function(){
		if(ids==""){
		ids+=""+jQuery(this).val()+"";
		}else{
	   		ids+= ","+jQuery(this).val()+"";
	   	}
	});
	if(ids!=""){
		if(!confirm("删除后无法恢复,确定删除该选项吗？")){return;}
		document.forms[0].action= "<%=basePath%>account/delTrialConfig?requireIds="+ids ;
		document.forms[0].method="post";
		document.forms[0].submit();
	}else{
		alert("请选择要删除的数据!");
	}
}

function query(){
	jQuery("#department").val(j("#departmentId").combotree("getText"));	
	jQuery("#costType").val(j("#cosId").combotree("getText"));
	
	jQuery("#pageForm").attr("action","<%=basePath%>account/trialAccountConfig");
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
	
	var url = "<%=basePath%>account/addTrialConfig";	
	//初始化新增问题fancybox	   
   jQuery('#addTrialConfig').fancybox({
   			'width' : '60%',
   			'height': '80%',
   			'href'				:	url,
	   		'autoScale' : true,
			'transitionIn' : 'none',
			'transitionOut' : 'none',
			'type' : 'iframe',
			'scrolling' : 'yes',
			'centerOnScroll' : true,
			'onStart' : function(current, previous) {}  			
   });
   
   //初始化编辑fancybox
   var url,me;
   jQuery("a[id^=edit]").each(function(){
	   me = $(this);
	   url="<%=basePath%>account/editTrialConfig?id="+me.attr("data");		   
	   me.fancybox({
		   'width'				: '70%',
  			'height'			: '90%',
  			//'scrolling'			:'yes',
  			'autoScale'			: false,//自适应窗口大小
  			'type' : 'iframe',
  			'transitionIn'		: 'none',
  			'transitionOut'		: 'none',	  			
  			'href'				:	url
	   });
   });
   
   j("#departmentId").combotree({
		onClick: function(node) {
			if (!j(this).tree('isLeaf', node.target)) {
				j('#departmentId').combo('showPanel');
			}
		},
		
   	    url:'<%=basePath%>/account/getDepartment' ,
   		width:170,
   		lines:'true',
		checkbox:true ,
		cascadeCheck:false,
		required:false,
		editable:false,
		multiple:false//设置是否多选	
		
	       });  
	       
	       j("#cosId").combotree({
	       onBeforeSelect: function(node) {
			if (!j(this).tree('isLeaf', node.target)) {
				return false;
			}
		},
		onClick: function(node) {
			if (!j(this).tree('isLeaf', node.target)) {
				j('#cosId').combo('showPanel');
			}
		},
		
   	    url:'<%=basePath%>/account/getCostType' ,
   		width:170,
   		lines:'true',
		checkbox:true ,
		cascadeCheck:false,
		required:false,
		editable:false,
		multiple:false//设置是否多选	
	       }); 
});

</script>                
</body>
</html>