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
		<title>系统菜单</title>
		<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
		<link  href="/SRMC/rmpb/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"  type="text/css" />
        <script type="text/javascript" src="/SRMC/rmpb/ztree/js/jquery.ztree.core-3.5.js"></script>
        <script type="text/javascript" src="/SRMC/rmpb/ztree/js/jquery.ztree.excheck-3.5.js"></script>
		
		 <script type="text/javascript">
			function check(nodeid, treecheckbox) {
			
			//alert();
				for ( var i = 0; i < document.form.elements.length; i++) {
					var e = document.form.elements[i];//document.form[i];
					if (e.type == "checkbox" && e.value == nodeid) {
						if (e.checked == true && treecheckbox == '') {
							treecheckbox = true;
						}
						break;
					}
				}
				if (treecheckbox == true) {
					checkFather(nodeid);
				}
			
				if (checkIfHasChild(nodeid)) {
					checkChildByFather(nodeid, treecheckbox);
				}
			
				function checkFather(fid) {
					if (fid == "-1") {
						return;
					}
					for ( var i = 0; i < document.form.elements.length; i++) {
						//var e = document.form[i];
						var e = document.form.elements[i];
						if (e.type == "checkbox" && e.value == fid) {
							e.checked = true;
							checkFather(e.pid);
						}
					}
				}
			
				//选中所有的子节点
				function checkChildByFather(sid, isCheck) {
			
					if (!checkIfHasChild(sid)) {
						return;
					}
			
					for ( var i = 0; i < document.form.elements.length; i++) {
						//var e = document.form[i];
						var e = document.form.elements[i];
						if (e.type == "checkbox" && e.pid == sid) {
							if (e.disabled == false) {
								e.checked = isCheck;
							}
							checkChildByFather(e.value, isCheck);
						}
					}
				}
			
				//返回是否有子节点
				function checkIfHasChild(ssid) {
					for ( var i = 0; i < document.form.elements.length; i++) {
						//var e = document.form[i];
						var e = document.form.elements[i];
						//alert(e.pid);
						if (e.type == "checkbox" && e.pid == ssid) {
							return true;
						}
					}
					return false;
				}
			}
			//保存选中的组织机构
			

			//保存选中的组织机构
			function doSave(btn){
                $(btn).attr("disabled","disabled");
                var roleId=jQuery("#roleId").val();
                $("#msg").show();
                var nodes =  menuTree.getCheckedNodes(true);
                var childrenCheckboxValue = [];
                for(var i=0;i < nodes.length;i++){
                    //if(!nodes[i].isParent && nodes[i].id != 'ROOT') {
                        childrenCheckboxValue.push(nodes[i].id);
                        childrenCheckboxValue.push(nodes[i].pId);
                  //  }
                }
                /***
                if(childrenCheckboxValue.length>10){
                    alert('您选择的模块不能超过10个,请重新选择!');
                    $("#msg").hide();
                    $(btn).removeAttr("disabled");
                    return;
                }
                ***/
                //请求保存已选菜单
                
                $.post("<%=basePath%>sys/role/saveRoleprivilege", {
                    "privileges" : childrenCheckboxValue.join(","),
                    "roleId":roleId
                }, function(data) {
                    if (data == "ok") {
                        alert("保存成功！");
                        //jQuery.fancybox.close();
                        window.parent.closeFanc();
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
   	<input type="hidden" value="${roleId}" id="roleId" name="roleId">
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

                    <c:forEach items="${privileges}" var="i">
        <c:choose>
        <c:when test="${i.status=='3'}">
        zNodes.push({id:"${i.moduleCode}",pId:"${i.parentCode}",checked:true,${i.moduleCode eq "ROOT" ? "open:true," : ""}name:"${i.moduleCode eq "ROOT" ? "系统菜单" : i.moduleName}"});
        </c:when>
        <c:otherwise>
        zNodes.push({id:"${i.moduleCode}",pId:"${i.parentCode}",name:"${i.moduleCode eq "ROOT" ? "系统菜单" : i.moduleName}"${i.moduleName eq "ROOT" ? "open:true" : ""}});
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
