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
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery.ztree.exedit.min.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<!-- 	表单异步提交 -->
<script src="/SRMC/rmpb/js/jquery.form.js"></script>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
<!-- 树节点配置 -->
<script type="text/javascript">
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
	    return false;
	}
	
	//根目录不显示编辑按钮
	function setRenameBtn(treeId, treeNode) {
		if(treeNode.id == "ROOT"){
			return false;
	    }
	
		if(treeNode.pId == "ROOT"){
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
    	
    	//加载空的表单出来
    	$("#data").load("<%=basePath%>yuancheck/add?id="+treeNode.id,function(){
	    		if(treeNode.pId == "ROOT"){
	    			$("#parentId_tr01").hide();
	    		}else{
	    			$("#parentId_tr01").show();
	    		}
    	});
		treeNodeObj = treeNode;
		return false;
    }
    
    
    //执行删除之前
    function beforeRemove(treeId, treeNode) {
    	jQuery("#id").val(treeNode.id);
    	treeNodeObj = treeNode;
    	ev_delete();
      	return false;
    }
    
    function onRemove(e, treeId, treeNode) {
      showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.tableName);
    }
    
    function beforeRename(treeId, treeNode, newName, isCancel) {
      className = (className === "dark" ? "":"dark");
      showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.tableName + (isCancel ? "</span>":""));
      if (newName.length == 0) {
        alert("节点名称不能为空.");
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        setTimeout(function(){zTree.editName(treeNode)}, 10);
        return false;
      }
      return true;
    }
    
    function onRename(e, treeId, treeNode, isCancel) {
      showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.tableName + (isCancel ? "</span>":""));
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
    
    //新增
    function addHoverDom(treeId, treeNode) {
    	return;
    }
    
    function removeHoverDom(treeId, treeNode) {
      $("#addBtn_"+treeNode.tId).unbind().remove();
    }
    
    function selectAll() {
      var zTree = $.fn.zTree.getZTreeObj("treeDemo");
      zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
    }
    
    function initTree(){
	    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	    $("#selectAll").bind("click", selectAll);
    }
    
    $(document).ready(function(){
    	initTree();
    });
</script>
</head>
<body>

<div class="gl_import_m">
	<div class="tabpages">
	<ul class="l">
	      		<li class="current">元数据管理</li>
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
	    <td valign="top" id="data"></td>
    </tr>
</table>

</form>
</div> 
<script type="text/javascript">

 $(document).ready(function(){
 	//加载空的表单出来
 	$("#data").load("<%=basePath%>yuancheck/add",function(){
 		
 	});
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
	
	if( jQuery.trim(jQuery("#tableName").val())=="" ){
		alert("元数据表名称不能为空");
		return false;
	}
	
	if(jQuery("#parentId_tr01").is(':visible') && jQuery.trim(jQuery("#parentId").val())=="" ){
		alert("请选择父类");
		return false;
	}
	return true;
}

//保存表单
function ev_save(){
// 	if(!checkSubmit()){
// 		return;
// 	}
	
	//新增保存
	$("#form1").ajaxSubmit({
       	url:"<%=basePath%>yuancheck/saveOrUpdate",
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result.result == 1){
		       //刷新表单
	    		alert("保存成功");
        	}else{
        		alert("保存失败");
        	}
        },
        error:function () {
        	alert("保存失败");
        }
    });
}

function ev_edit(){
	var id = jQuery("#id").val();
	window.showModalDialog("<%=basePath%>yuancheck/rowList?id="+id,window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
}

</script>
</body>
</html>
