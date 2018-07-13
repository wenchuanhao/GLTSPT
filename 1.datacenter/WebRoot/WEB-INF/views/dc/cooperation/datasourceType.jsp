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

<link rel="stylesheet" href="/SRMC/dc/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/SRMC/dc/js/jquery.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery.ztree.exedit.min.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>copperationController";</script>
<!-- 	表单异步提交 -->
<script src="/SRMC/rmpb/js/jquery.form.js"></script>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
<jsp:include page="../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="EFFECTIVENESS,DATA_SOURCE,REPORT_TYPES"></jsp:param>
</jsp:include>
<!-- 树节点配置 -->
<script type="text/javascript">
var busTypeName = "${busTypesName }";
var treeNodeObj;
    var setting = {
      view: {
        addHoverDom: addHoverDom,//鼠标覆盖事件
        removeHoverDom: removeHoverDom,//鼠标移除事件
        selectedMulti: false
      },
      edit: {
        enable: true,
        editNameSelectAll: true,
        showRemoveBtn: setRemoveBtn,//设置删除按钮显隐
        showRenameBtn: setRenameBtn//设置编辑按钮显隐
      },
      data: {
        simpleData: {
          enable: true
        }
      },
      callback: {
        beforeDrag: beforeDrag,
        beforeEditName: beforeEditName,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename,
        onRemove: onRemove,
        onRename: onRename
      }
    };

	//根目录不显示删除按钮
	function setRemoveBtn(treeId, treeNode) {
		if(treeNode.id == "ROOT"){
			return false;
	    }
	    return true;
	}
	
	//根目录不显示编辑按钮
	function setRenameBtn(treeId, treeNode) {
		if(treeNode.id == "ROOT"){
			return false;
	    }
	    return true;
	}
		//树结构数据初始化
	var zNodes = $.parseJSON('${zNodes}'.replace("\r\n", "\\r\\n"));
    var log, className = "dark";
    function beforeDrag(treeId, treeNodes) {
      return false;
    }
    
    //执行编辑之前
    function beforeEditName(treeId, treeNode) {
    	jQuery("html,body").find(".noselected_tips").remove();
    //点击编辑
    	$("#edit_input").show();
    	$("#add_input").hide();
		if(treeNode.id == "ROOT"){
			$("#datasource_type_tr01").hide();
			$("#datasource_type_th02").html('<em>*</em>父类名称：');
			$("#datasourceId_input").val('');
			$("#datasourceName_input").val('');
		}else if(treeNode.pId == "ROOT"){
			$("#datasource_type_tr01").hide();
			$("#datasource_type_th02").html('<em>*</em>父类名称：');
			$("#datasourceId_input").val(treeNode.id);
			$("#datasourceName_input").val(treeNode.name);
		}else{
			$("#datasource_type_tr01").show();
			$("#datasource_type_th02").html('<em>*</em>子类名称：');
			$("#datasourceId_input").val(treeNode.id);
			$("#datasourceName_input").val(treeNode.name);
		}
		if(treeNode.id != "ROOT"){
			jQuery.ajax({
				type:"POST",
				async:false,
				url: "<%=basePath%>copperationController/viewDatasourceType",
				data:"datasourceId=" + treeNode.id,
				dataType:"json",
				success:function(result){
					obj2.val(treeNode.id);
					statusObj.val(result.status);
					datasourceSourceObj.val(result.datasourceSource);
					$("#datasourceCode").val(result.datasourceCode);
					$("#interfaceTable_input").val(result.interfaceTable);
					$("#interfaceTableName_input").val(result.interfaceTableName);
				},
				error:function(){
				}
			});
		}
		treeNodeObj = treeNode;
		obj2.val(treeNode.pId);
		return false;
    }
    //执行删除之前
    function beforeRemove(treeId, treeNode) {
    	jQuery("#datasourceId_input").val(treeNode.id);
    	treeNodeObj = treeNode;
    	ev_delete();
      	return false;
    }
    function onRemove(e, treeId, treeNode) {
      showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    }
    function beforeRename(treeId, treeNode, newName, isCancel) {
      className = (className === "dark" ? "":"dark");
      showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
      if (newName.length == 0) {
        alert("节点名称不能为空.");
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        setTimeout(function(){zTree.editName(treeNode)}, 10);
        return false;
      }
      return true;
    }
    function onRename(e, treeId, treeNode, isCancel) {
      showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
    }
    function showLog(str) {
      if (!log) log = $("#log");
      log.append("<li class='"+className+"'>"+str+"</li>");
      if(log.children("li").length > 8) {
        log.get(0).removeChild(log.children("li")[0]);
      }
    }
    function getTime() {
      var now= new Date(),
      h=now.getHours(),
      m=now.getMinutes(),
      s=now.getSeconds(),
      ms=now.getMilliseconds();
      return (h+":"+m+":"+s+ " " +ms);
    }

    var newCount = 1;
    function addHoverDom(treeId, treeNode) {
      if(treeNode.id == "ROOT" || treeNode.pId == "ROOT"){
	      var sObj = $("#" + treeNode.tId + "_span");
	      if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	      var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
	        + "' title='add node' onfocus='this.blur();'></span>";
	      sObj.after(addStr);
      }
      var btn = $("#addBtn_"+treeNode.tId);
      if (btn) btn.bind("click", function(){
		jQuery("html,body").find(".noselected_tips").remove();
     	treeNodeObj = treeNode;
		$("#edit_input").hide();
    	$("#add_input").show();
    	obj2.val("");
		statusObj.val("");
		datasourceSourceObj.val("");
    	$("#interfaceTable_input").val("");
		$("#interfaceTableName_input").val("");
		if(treeNode.id == "ROOT"){
			$("#datasource_type_tr01").hide();
			$("#datasource_type_th02").html('<em>*</em>父类名称：');
			$("#datasourceId_input").val('');
			$("#datasourceName_input").val('');
			$("#datasourceCode").val("自动生成序列号");
			
		}else if(treeNode.pId == "ROOT"){
			$("#datasource_type_tr01").show();
			$("#datasource_type_th02").html('<em>*</em>子类名称：');
			$("#datasourceId_input").val("");
			$("#datasourceName_input").val("");
			$("#datasourceCode").val("自动生成序列号");
			obj2.val(treeNode.id);
		}else{
			$("#datasource_type_tr01").show();
			$("#datasource_type_th02").html('<em>*</em>子类名称：');
			$("#datasourceId_input").val(treeNode.id);
			$("#datasourceName_input").val(treeNode.name);
			$("#datasourceCode").val("自动生成序列号");
		}
      });
    };
    function removeHoverDom(treeId, treeNode) {
      $("#addBtn_"+treeNode.tId).unbind().remove();
    };
    function selectAll() {
      var zTree = $.fn.zTree.getZTreeObj("treeDemo");
      zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
    }
    $(document).ready(function(){
      $.fn.zTree.init($("#treeDemo"), setting, zNodes);
      $("#selectAll").bind("click", selectAll);
    });
</script>
</head>
<body>

<div class="gl_import_m">
	<div class="tabpages">
	<ul class="l">
			<c:if test="${busTypes == 'DS' }">
	      		<li onclick="window.location.href='<%=basePath%>copperationController/queryDatasourceType?busTypes=DS';" class="current">${busTypesName }配置</li>
	      		<li onclick="window.location.href='<%=basePath%>copperationController/queryDatasourceType?busTypes=YB';" >业务报表配置</li>
			</c:if>
			<c:if test="${busTypes == 'YB' }">
				<li onclick="window.location.href='<%=basePath%>copperationController/queryDatasourceType?busTypes=DS';">数据源配置</li>
	      		<li onclick="window.location.href='<%=basePath%>copperationController/queryDatasourceType?busTypes=YB';" class="current">${busTypesName }配置</li>
			</c:if>
    	</ul>
	</div>
<form action=""  method="post"  id="form1">
<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable cifTable reeditTable" align="center">
	<tr>
	    <td width="25%" valign="top" style="border-right:1px dashed #e6e6e6;height: 100%">
	      <div class="zTreeDemoBackground left">
	        <ul id="treeDemo" class="ztree"></ul>
	      </div>
	    </td>
	    <td valign="top">
	    	<table id="table01" cellpadding="0" cellspacing="5" class="taskCreate" width="100%">
	    		<input type="hidden" name="busTypes" id="busTypes_input" value="${busTypes }"/>
	    		<input type="hidden" name="datasourceId" id="datasourceId_input" value=""/>
	             <tr>
	               <th width="35%" id=""><em>*</em>大类：</th>
	               <td><input  name="" id="" value="${busTypesName }" readonly="readonly" type="text" class="text01" style="width:174px;" /></td>
	             </tr>
	            <tr id="datasource_type_tr01">
	               <th width="35%"><em>*</em>父类：</th>
	               <td>
		               	<select class="ui-select" id="parentDatasourceId_input" name="parentDatasourceId" style="width:180px;">
							<option value="">请选择</option>
				          	<c:forEach items="${list}" var="item" varStatus="i">
				    			<c:if test="${item.datasourceLevel == 1 }">
				    				<option value="${item.datasourceId}">${item.datasourceName}</option>
				    			</c:if>
							</c:forEach>
				        </select>
	        	   </td>
	            </tr>
	             <tr>
	               <th width="35%" id="datasource_type_th02"><em>*</em>子类名称：</th>
	               <td><input  name="datasourceName" id="datasourceName_input"  type="text" class="text01" style="width:174px;" /></td>
	             </tr>
	            <tr id="status_tr01">
	               <th width="35%"><em>*</em>有效性：</th>
	               <td>
	               		<select id="status" name="status" class="ui-select" style="width:180px;" defaultValue="${datasourceType.status }"  dictType="EFFECTIVENESS"/>
	        	   </td>
	            </tr>
	            <c:if test="${busTypes == 'DS' }">
		            <tr id="datasourceSource_tr01">
		               <th width="35%"><em>*</em>数据来源：</th>
		               <td>
		               		<select id="datasourceSource" name="datasourceSource" class="ui-select" style="width:180px;" defaultValue="${datasourceType.datasourceSource }"  dictType="DATA_SOURCE"/>
		        	   </td>
		            </tr>
				</c:if>
				<c:if test="${busTypes == 'YB' }">
		            <tr id="datasourceSource_tr01">
		               <th width="35%"><em>*</em>报表类型：</th>
		               <td>
		               		<select id="datasourceSource" name="datasourceSource" class="ui-select" style="width:180px;" defaultValue="${datasourceType.datasourceSource }"  dictType="REPORT_TYPES"/>
		        	   </td>
		            </tr>
				</c:if>
	            <tr id="datasourceSource_tr02">
	               <th width="35%"><em>*</em>元数据表编码/接口地址：</th>
	               <td>
	               	 	<input  name="interfaceTable" id="interfaceTable_input"  type="text" class="text01" style="width:174px;" />
	        	   </td>
	            </tr>
	            <tr id="datasourceSource_tr03">
	               <th width="35%"><em>*</em>元数据表名称/接口系统名称：</th>
	               <td>
	               	<input  name="interfaceTableName" id="interfaceTableName_input"  type="text" class="text01" style="width:174px;" />
	        	   </td>
	            </tr>
	            <tr>
	               <th width="35%" id="">编号：</th>
	               <td><input id="datasourceCode" value="自动生成序列号" readonly="readonly" type="text" class="text01" style="width:174px;" /></td>
	             </tr>
	            <tr>
	               <th>&nbsp;</th>
	               <td> <input style="display: none;" onclick="ev_submit()" id="edit_input" type="button" class="btn_common02" value="编辑配置" />
						<input onclick="ev_save()" id="add_input" type="button" class="btn_common02" value="新增配置" />
<!-- 						<input onclick="ev_delete()" id="" type="button" class="btn_common01" value="删除配置" /> -->
				   </td>
	            </tr>
	         </table>
	    </td>
    </tr>
</table>
</form>
</div> 
<script type="text/javascript">
var obj2 ;
var statusObj;
var datasourceSourceObj;
$(function(){

});
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	obj2 = $('#parentDatasourceId_input').data('ui-select');
	obj2.onClick = function(value) {
		 var zTree = $.fn.zTree.getZTreeObj("treeDemo");
         treeNodeObj = zTree.getNodeByParam("id", value, null);
	}
	statusObj = $('#status').data('ui-select');
	datasourceSourceObj = $('#datasourceSource').data('ui-select');
 
});

//滚动页面并弹框
function animateAlert(id,focusId,msg){
	//清除未选择，未填写的提示
	jQuery("html,body").find(".noselected_tips").remove();
	//滚动页面至表格
	jQuery("html,body").animate({scrollTop:jQuery("#"+id).offset().top},10);
	//提示
	if(focusId == "datasourceName_input"){
		$("#"+focusId).parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}else{
		$("#"+focusId).parent().parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}
}

function checkSubmit(){

//当一级/父类可选时,必选
	if(jQuery("#datasource_type_tr01").is(':visible') && jQuery.trim(jQuery("#parentDatasourceId_input").val())=="" ){
		animateAlert("table01","parentDatasourceId_input", "你选择${busTypesName }父类" );
		return false;
	}
	if( jQuery.trim(jQuery("#datasourceName_input").val())=="" ){
		animateAlert("table01","datasourceName_input", "${busTypesName }名称不能为空" );
		return false;
	}
	if( $("#datasourceName_input").val().length > 30 ){
		animateAlert("table01","datasourceName_input","${busTypesName }名称不得超过30个字" );
		return false;
	}
	
	if(jQuery("#status_tr01").is(':visible') &&  jQuery.trim(jQuery("#status").val())=="" ){
		animateAlert("table01","status", "${busTypesName }有效性不能为空" );
		return false;
	}
	
	if(jQuery("#datasourceSource_tr01").is(':visible') &&  jQuery.trim(jQuery("#datasourceSource").val())=="" ){
		animateAlert("table01","datasourceSource", "${busTypesName }数据来源不能为空" );
		return false;
	}
	
	
	return true;
}

function ev_submit(){
	if(!checkSubmit()){
		return;
	}
	//编辑保存
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>copperationController/addDatasourceType",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result.result == 1){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        		//根据主键获取节点
				var treeNodeObjNew = zTree.getNodeByParam("id", result.datasourceType.datasourceId, null);
				treeNodeObjNew.name= result.datasourceType.datasourceName;
				treeNodeObjNew.pId= result.datasourceType.parentDatasourceId;
		        //若父节点没有变化,说明未选中新的一级
		        if(treeNodeObj.pId == treeNodeObjNew.pId){
		        	zTree.updateNode(treeNodeObjNew);
		        }else{
		        	zTree.removeNode(treeNodeObjNew);
		        	zTree.addNodes(treeNodeObj, {id:result.datasourceType.datasourceId, pId:treeNodeObj.id, name:result.datasourceType.datasourceName});
		        }
		        return false;
        	}else{
        		alert("提交失败");
        	}
        },
        error:function () {
        	alert("提交失败");
        }
    });
}

function ev_save(){
	if(!checkSubmit()){
		return;
	}
	$("#datasourceId_input").val("");
	//新增保存
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>copperationController/addDatasourceType",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result.result == 1){
        		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		        zTree.addNodes(treeNodeObj, {id:result.datasourceType.datasourceId, pId:treeNodeObj.id, name:result.datasourceType.datasourceName});
		        if(treeNodeObj.id == "ROOT"){
			        $("#parentDatasourceId_input").append('<option value="'+result.datasourceType.datasourceId+'">'+result.datasourceType.datasourceName+'</option>');
					$("#parentDatasourceId_input").parent().find(".ui-select-list").append('<li class="" data-value="'+result.datasourceType.datasourceId+'" title="'+result.datasourceType.datasourceName+'">'+result.datasourceType.datasourceName+'</li>');
			        	$('.ui-select').ui_select();
						obj2 = $('#parentDatasourceId_input').data('ui-select');
		        }
		        return false;
        	}else{
        		alert("提交失败");
        	}
        },
        error:function () {
        	alert("提交失败");
        }
    });
}

function ev_delete(){
	jQuery("html,body").find(".noselected_tips").remove();
	if( jQuery.trim(jQuery("#datasourceId_input").val())=="" ){
		alert("请选择你要删除的配置项！" );
		return false;
	}
	var id = jQuery("#datasourceId_input").val();
	if(confirm("确定删除该配置及其子配置项么？")){
		jQuery.ajax({
	        type:"POST",
	        async:false,
	        url:"<%=basePath%>copperationController/delDatasourceType",
	        data:"datasourceId=" + id + "&busTypes=${busTypes }",
	        success:function(data){
	        	if(data == 1){
	        		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		        	zTree.removeNode(treeNodeObj);
		        	if(treeNodeObj.pId == "ROOT"){
		        		obj2.remove(id);
			        }
	        	}else{
	        		alert("删除失败！");
	        	}
	        },
	        error:function(){
	            alert("删除失败！");
	        }
	    });
	}
}

</script>
</body>
</html>
