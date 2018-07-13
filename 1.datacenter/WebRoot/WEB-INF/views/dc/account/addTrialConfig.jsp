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
<title>新增会计配置</title>
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
		<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
		
</head>
<body>
<!-- 遮罩内容开始 -->
<form  action="" method="post" id="submitForm">
<div class="taskCreatBox">
            <div class="box_title" style="color: #4084b6;">
                	新增<span><a href="#none"></a></span>
            </div>
            <div class="box_task">
            <table cellpadding="0" cellspacing="5" class="taskCreate" width="100%">
       		<input type="hidden" name="department" id="department" />
       		<input type="hidden" name="costType" id="costType" "/>
       		<%-- <input type="hidden" name="accountingId" id="accountingId_input" value="${accountForm.accountingId}"/> --%>
            <tr>
               <th width="35%"><em>*</em>费用类型：</th>
               <td>
               <div class="checxTreeWrap"  style="width:170px;">
			    <%-- <input id="cosId" name="cosId" value="${accountForm.costType}"/>  --%>
			    <select  multiple style="width:200px" id="cosId" name="cosId" class="easyui-combotree" data-options="url:'<%=basePath%>/account/getCostType',method:'get'" multiple style="width:180px;">
			    </select>
			    </div>
               </td>
            </tr>
            <tr>
               <th><em>*</em>部门：</th>
               <td>
               	<div class="checxTreeWrap"  style="width:170px;">
			    <select multiple style="width:200px" id="departmentId" name="departmentId" class="easyui-combotree" data-options="url:'<%=basePath%>/account/getDepartment',method:'get'" >
			    </select>
			    </div>
               </td>
               <!-- <input   onfocus="if(this.value=='请输入报账部门'){this.value='';};this.style.color='#333333';autoDepartment();" 
			                     onkeyup="clean()"
			                     onblur="if(this.value==''||this.value=='请输入报账部门'){this.style.color='#b6b6b6';}" placeholder="请输入报账部门" type="text" name="department" id="sel_02" style="width:160px;" class="text01" /> -->
            </tr>
            <tr>
               <th><em>*</em>初审会计：</th>
               <td>
	    		<input style="width:165px" value="" name="accountings" type="text" class="text01" id="accountings"  onfocus="autoCompletes(this);" onkeyup="clean(this)"/>
				<span id="selected_accountings">
					<c:forEach items="${queryerList}" var="item" varStatus="i">
						<a href="javascript:void(0);" class="" id="${item.userId }">
							<input type="hidden" id="accountingId" name="accountingId" value="${item.userId }">
							<span style="padding: 0 3px 0 0;">${item.userName }</span>
							<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10">
						</a>
					</c:forEach>
				</span>
		        </td>
           </tr>
           <tr>
               <td colspan="2" align="center">
               <input name="" type="button" onclick="save()" class="btn_common02" value="保 存" />  <input name="" type="button" onclick="back()" class="btn_common01" value="取 消" /></td>
            </tr>
          </table>
          </div>
        </div>
<!-- 遮罩内容结束 -->
</form>
<script src="/SRMC/dc/account/js/jquery.js"></script>
<script src="/SRMC/dc/account/js/ui-select.js"></script>

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
		if(jQuery("#sel_02").val()!=null && jQuery("#sel_02").val()!=""){
		}
}

//用户可选择用户(sel_09) 
function autoCompletes(target){
		jQuery(target).autocomplete({
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
				if(jQuery("#selected_"+$(target).attr('id')).children("[id='" + ui.item.userId + "']").length != 0){
					jQuery(target).val("");
					return;
				}else{
					jQuery("#selected_"+$(target).attr('id')).append('<a href="javascript:void(0);" class="" id="' + ui.item.userId + '">\
																		<input type="hidden" id="accountingId" name="accountingId" value="' + ui.item.userId + '"/>\
																		<input type="hidden" id="accounting" name="accounting" value="'+ui.item.userName+'">\
																		<span style="padding: 0 3px 0 0;">' + ui.item.userName + '</span>\
																		<img onclick="jQuery(this).parent().remove()" src="/SRMC/dc/images/close_icon01.png" width="10" height="10" />\
																	  </a>');
					jQuery(target).val("");
					return false;
				}
			}
		});
	}
	
	function clean(){
		if (event.keyCode == 13) {
        //js 监听对应的id
         //document.getElementById("demandFzrId_input_focus").value="";
        } 
	}
	
	function checkTrialConfig(){
		var result;
			$.ajax({
			        type:"POST",
			        dataType:'json',
			        async:false,
			        url:"<%=basePath%>account/checkTrialConfig",
			        data:"departmentId=" + j("#departmentId").combotree("getValue") + "&cosId="+j("#cosId").combotree("getValue")+"&accountingId="+jQuery("#accountingId").val(),
			        success:function(data){
			        	if(data == "0"){
				        	result= "0";
			        	}else{
			        		result = "1";
			        	}
			        },
			        error:function(){
			            alert("网络异常请联系管理员！");
			        }
			    });
	    return result;
	}
	
	//保存
	function save(){
	if(checkTrialConfig()=="1"){
		alert("已存在相同的配置，请重新输入");
		return;
	}
	
	var text = j("#departmentId").combotree("getText");
	jQuery("#department").val(text);	
	
	var cosType = j("#cosId").combotree("getText");
	jQuery("#costType").val(cosType);	
	
	if(jQuery.trim(cosType) == ""){
		alert("请选择费用类型");
		return;
	}
	if(jQuery.trim(text) == ""){
		alert("请选择部门");
		return;
	}
	if(jQuery.trim(jQuery("#accountingId").val()) == ""){
		alert("请选择初审会计");
		return;
	}
	
	$.ajax({
		url:"<%=basePath%>account/saveTrialConfig",
    	dataType:'json',
        async:true,
        type:'POST',
        data:$('#submitForm').serialize(),// 你的formid
        success:function (result) {
        	if(result == '1'){
        		alert("新增成功");
	        	parent.window.location.reload();
        	}
        },
        error:function () {
        	alert("新增失败");
        }
	});

}

function change(){
	var department=$("#sel_02").find("option:selected").text();
	var costType=$("#sel_01").find("option:selected").text();  //获取Select选择的Text
	$("#typeName").val(costType);	
	$("#department").val(department);	
}

//返回
function back(){
		parent.window.location.reload();
}

$(function (){
	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	// 获取已经实例化的select对象
	var obj = $('#sel_api').data('ui-select');
	
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