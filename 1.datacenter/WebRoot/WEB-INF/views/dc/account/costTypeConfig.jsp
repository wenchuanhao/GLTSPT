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
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />


<script type="text/javascript" src="/SRMC/dc/js/jquery.ztree.core.min.js"></script>
<link rel="stylesheet" href="/SRMC/dc/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/SRMC/dc/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery.ztree.exedit.min.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" >var basePath = "<%=basePath%>rulesController";</script>
<!-- 	表单异步提交 -->
<script src="/SRMC/rmpb/js/jquery.form.js"></script>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
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
		 var zNodes =[
	      	{ id:'ROOT', pId:0, name:'${busTypesName}', open:true},
	     ];
		var data = $.parseJSON('${rulesTypeList}'.replace("\r\n", "\\r\\n"));
		$.each(data,function(k,v){
			zNodes.push({
					id:v.typeId,
					pId:v.parentTypeId,
					name:v.typeName
				});
		});

    var log, className = "dark";
    function beforeDrag(treeId, treeNodes) {
      return false;
    }
    
    //执行编辑之前
    function beforeEditName(treeId, treeNode) {
    
    //点击编辑
    	$("#submit").show();
    	$("#save").hide();
    	if(treeNode.id == "ROOT"){
			$("#rules_type_tr01").hide();
			$("#rules_type_th02").html('<em>*</em>'+busTypeName+'一级：');
			$("#typeId_input").val('');
			$("#parentTypeId_input").val('ROOT');
			$("#typeName_input").val('');
		}else if(treeNode.pId == "ROOT"){
			$("#rules_type_tr01").hide();
			$("#rules_type_th02").html('<em>*</em>'+busTypeName+'一级：');
			$("#typeId_input").val(treeNode.id);
			$("#parentTypeId_input").val(treeNode.pId);
			$("#typeName_input").val(treeNode.name);
		}else{
			$("#rules_type_tr01").show();
			$("#rules_type_th02").html('<em>*</em>'+busTypeName+'二级：');
			$("#typeId_input").val(treeNode.id);
			$("#parentTypeId_input").val(treeNode.pId);
			$("#typeName_input").val(treeNode.name);
		}
		treeNodeObj = treeNode;
		obj2.val(treeNode.pId);
		return false;
    }
    //执行删除之前
    function beforeRemove(treeId, treeNode) {
    	jQuery("#typeId_input").val(treeNode.id);
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
        setTimeout(function(){zTree.editName(treeNode);}, 10);
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
      	  treeNodeObj = treeNode;
		$("#submit").hide();
    	$("#save").show();
		if(treeNode.id == "ROOT"){
			$("#rules_type_tr01").hide();
			$("#rules_type_th02").html('<em>*</em>'+busTypeName+'一级：');
			$("#typeId_input").val('');
			$("#parentTypeId_input").val('ROOT');
			$("#typeName_input").val('');
			obj2.val("");
		}else if(treeNode.pId == "ROOT"){
			$("#rules_type_tr01").show();
			$("#rules_type_th02").html('<em>*</em>'+busTypeName+'二级：');
			$("#typeId_input").val("");
			$("#parentTypeId_input").val(treeNode.id);
			$("#typeName_input").val("");
			obj2.val(treeNode.id);
		}else{
			$("#rules_type_tr01").show();
			$("#rules_type_th02").html('<em>*</em>'+busTypeName+'二级：');
			$("#typeId_input").val(treeNode.id);
			$("#parentTypeId_input").val(treeNode.pId);
			$("#typeName_input").val(treeNode.name);
			obj2.val(treeNode.pId);
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
<div class="searchCondition">
	<span class="searchCondition_header">${busTypesName }配置ddd</span>d
</div>

<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable cifTable reeditTable" align="center">
	<tr>
	    <td width="25%" valign="top" style="border-right:1px dashed #e6e6e6;height: 100%">
	      <div class="zTreeDemoBackground left">
	        <ul id="treeDemo" class="ztree"></ul>
	      </div>
	    </td>
	    <td valign="top">
	    	<table id="table01" cellpadding="0" cellspacing="5" class="taskCreate" width="100%">
	    		<form action=""  method="post"  id="form1">
	    		<input type="hidden" name="busTypes" id="busTypes_input" value="${busTypes }"/>
	    		<input type="hidden" name="typeId" id="typeId_input" value=""/>
	    		<input type="hidden" name="parentTypeId" id="parentTypeId_input" value=""/>
	    		<input type="hidden" name="parentTypeName" id="parentTypeName_input" value=""/>
	            <tr id="rules_type_tr01">
	               <th width="35%"><em>*</em>${busTypesName }一级：</th>
	               <td>
		               	<select class="ui-select" id="sel_01" style="width:180px;">
							<option value="">请选择asdfasd</option>
				          	<c:forEach items="${list}" var="item" varStatus="i">
				    			<c:if test="${item.isRoot == 1 }">
				    				<option value="${item.typeId}">${item.typeName}</option>
				    			</c:if>
							</c:forEach>
				        </select>
	        	   </td>
	            </tr>
	             <tr>
	               <th width="35%" id="rules_type_th02"><em>*</em>${busTypesName }二级：</th>
	               <td><input  name="typeName" id="typeName_input"  type="text" class="text01" style="width:174px;" /></td>
	             </tr>
	             </form>
	            <tr>
	               <th>&nbsp;</th>
	               <td> 
	               		<input style="display: none;" onclick="ev_submit()"  id="submit" name="" type="button" class="btn_common02" value="编辑配置" />
						<input onclick="ev_save()"  id="save" name="" type="button" class="btn_common02" value="新增配置" />
<!-- 						<input onclick="ev_delete()"  id="delete" name="" type="button" class="btn_common01" value="删除配置" /> -->
				   </td>
	            </tr>
	         </table>
	    </td>
    </tr>
</table>
</div> 
<script type="text/javascript">
var obj2 ;
$(function(){

});
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
    //${busTypesName }
	obj2 = $('#sel_01').data('ui-select');
	obj2.onClick = function(value) {
		 var zTree = $.fn.zTree.getZTreeObj("treeDemo");
         treeNodeObj = zTree.getNodeByParam("id", value, null);
		 $("#parentTypeId_input").val(value);
	};
});

//滚动页面并弹框
function animateAlert(id,focusId,msg){
	//清除未选择，未填写的提示
	jQuery("html,body").find(".noselected_tips").remove();
	//滚动页面至表格
	jQuery("html,body").animate({scrollTop:jQuery("#"+id).offset().top},10);
	//提示
	if(focusId == "sel_01"){
		$("#"+focusId).parent().parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}else{
		$("#"+focusId).parent().append('<b class="noselected_tips">'+msg+'！</b>');
	}
}

function checkSubmit(){

	if( jQuery.trim(jQuery("#parentTypeId_input").val())=="" ){
		animateAlert("table01","sel_01", "你选择${busTypesName }一级" );
		return false;
	}
	if( jQuery.trim(jQuery("#typeName_input").val())=="" ){
		animateAlert("table01","typeName_input", "${busTypesName }名称不能为空" );
		return false;
	}
	if( $("#typeName_input").val().length > 30 ){
		animateAlert("table01","typeName_input","${busTypesName }名称不得超过30个字" );
		return false;
	}
	
	return true;
}

function ev_submit(){
	if(!checkSubmit()){
		return;
	}
	//制度相关文档保存
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>rulesController/addRulesType",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result.result == 1){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		        
		        //根据主键获取节点
				var treeNodeObjNew = zTree.getNodeByParam("id", result.rulesType.typeId, null);
				treeNodeObjNew.name= result.rulesType.typeName;
				treeNodeObjNew.pId= result.rulesType.parentTypeId;
		        //若父节点没有变化,说明未选中新的一级
		        if(treeNodeObj.pId == treeNodeObjNew.pId){
		        	zTree.updateNode(treeNodeObjNew);
		        }else{
		        	zTree.removeNode(treeNodeObjNew);
		        	zTree.addNodes(treeNodeObj, {id:result.rulesType.typeId, pId:treeNodeObj.id, name:result.rulesType.typeName});
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
	$("#typeId_input").val("");
	//制度相关文档保存
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>rulesController/saveRulesType",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result.result == 1){
        		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		        zTree.addNodes(treeNodeObj, {id:result.rulesType.typeId, pId:treeNodeObj.id, name:result.rulesType.typeName});
		        if(treeNodeObj.id == "ROOT"){
			        $("#sel_01").append('<option value="'+result.rulesType.typeId+'">'+result.rulesType.typeName+'</option>');
					$("#sel_01").parent().find(".ui-select-list").append('<li class="" data-value="'+result.rulesType.typeId+'" title="'+result.rulesType.typeName+'">'+result.rulesType.typeName+'</li>');
			        	$('.ui-select').ui_select();
						obj2 = $('#sel_01').data('ui-select');
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

	if( jQuery.trim(jQuery("#typeId_input").val())=="" ){
		alert("请选择你要删除的配置项！" );
		return false;
	}
	var id = jQuery("#typeId_input").val();
	if(confirm("确定删除该配置及其子配置项么？")){
		jQuery.ajax({
	        type:"POST",
	        dataType:'json',
	        async:true,
	        url:"<%=basePath%>rulesController/delRulesType",
	        data:"typeId=" + id + "&busTypes=${busTypes }",
	        success:function(result){
	        	if(result == 1){
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
