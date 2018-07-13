<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<title>系统菜单</title>
		<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="/SRMC/rmpb/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="/SRMC/rmpb/ztree/js/jquery.ztree.core-3.5.js"></script>
        <script type="text/javascript" src="/SRMC/rmpb/ztree/js/jquery.ztree.excheck-3.5.js"></script>
		
		 <script type="text/javascript">

			function myReturnValue(value) {
			    if (navigator.userAgent.indexOf("Chrome") > 0) {
			        window.opener.returnCallBackValue354865588.call(window.opener, value);
			    } else {
			        window.returnValue = value;
			    }
			}

			//保存选中的组织机构
			function doSave(btn){
                $(btn).attr("disabled","disabled");
                $("#msg").show();
                var nodes =  menuTree.getCheckedNodes(true);
                var childrenCheckboxValue = [];
                for(var i=0;i < nodes.length;i++){
                    if(!nodes[i].isParent && nodes[i].id != 'ROOT') {
                        childrenCheckboxValue.push(nodes[i].id);
                    }
                }
                if(childrenCheckboxValue.length>10){
                    alert('您选择的模块不能超过10个,请重新选择!');
                    $("#msg").hide();
                    $(btn).removeAttr("disabled");
                    return;
                }
                //请求保存已选菜单

                $.post("<%=basePath%>core/portal/saveSelectModule", {
                    "privileges" : childrenCheckboxValue.join(",")
                }, function(data) {
                	myReturnValue(data);
                    //window.returnValue = data;
                    if (data == "ok") {
                        alert("保存成功！");
                        window.close();
                    } else {
                        alert("保存失败");
                        $("#msg").hide();
                        $(btn).removeAttr("disabled");
                    }
                });
			}
		</script>
	</head>
  
  <body class="bg_c_g">
   <form name="form" method="post" action="" id="form"> 
   			<div class="gl_bnt_tree02" style="display: block;">
   				<div style="float:left;"><input name="" type="button" class="gl_cx_bnt04" value="配 置" onclick="doSave(this);"/><span id="msg" style="display: none;">保存中...</span></div>
   			</div>
       <div class="zTreeDemoBackground left">
           <ul id="treeDemo" class="ztree"></ul>
       </div>
  </form>
</body>
<script type="text/javascript">
    var menuTree;
    var setting = {
        treeId:"menuTree",
        check: {
            enable: true,
            chkboxType: { "Y" : "ps", "N" : "ps" }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        view: {
		showIcon: false
	}
        
    };

    var zNodes = [];


    $(document).ready(function(){

        <c:forEach items="${listqx}" var="i">
        <c:choose>
        <c:when test="${not empty i[3]}">
        zNodes.push({id:"${i[1]}",pId:"${i[2]}",checked:true,${i[1] eq "ROOT" ? "open:true," : ""}name:"${i[1] eq "ROOT" ? "系统菜单" : i[0]}"});
        </c:when>
        <c:otherwise>
        zNodes.push({id:"${i[1]}",pId:"${i[2]}",name:"${i[1] eq "ROOT" ? "系统菜单" : i[0]}",${i[1] eq "ROOT" ? "open:true" : ""}});
        </c:otherwise>
        </c:choose>
        </c:forEach>

        menuTree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        
        var openNode = function(a){
        	if(a == null){}else{
        		menuTree.expandNode(a,true);
        		openNode(a.getParentNode());
        	}
        }
        
        var initCheckedNodes = menuTree.getCheckedNodes(true);
        for(var i=0;i<initCheckedNodes.length;i++){
        	openNode(initCheckedNodes[i].getParentNode());
        }
    });
</script>
</html>
