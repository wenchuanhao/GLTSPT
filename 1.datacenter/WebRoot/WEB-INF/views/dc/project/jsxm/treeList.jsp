<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html>
<html>
<head>
<title>查看-树形列表</title>
<!--主要样式-->
<link rel="stylesheet" type="text/css"  href="/SRMC/dc/css/gctree-style.css" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<body>
<div class="gl_import_m" >
  	<div class="tabpages" >
    	<ul class="l" id="tab">
	      		<li id="tab1"  class="current">树形列表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<a class="btn_common01" href="javascript:window.close();" /><img src="/SRMC/dc/images/btnIcon/invalid.png" /><span>关闭</span></a>
		</div>    	
  	</div>
<div class="tree">
	<div class="treeTitle"><span>编号</span><em>名称</em><span>管理员</span></div>
    <ul>
        <li>
            <span><i><a href="###"><i class="icon-th"></i> 建设项目：${vo.column02}</a></i><b><a href="javascript:void(0);"  onclick="edit1('${vo.id}')" style="color: #4084b6 ; title="查看详情">${vo.column03}</a></b><em>${vo.column10Name}</em></span> 
            <ul>
			<c:set var="tzbmList" value="${vo.tzbmList}"/>
			<c:forEach items="${tzbmList}" var="tzbm" varStatus="tzbmi">               
                <li>
                	<span><a href="###"> <i class="icon-minus-sign"></i> 投资编码${tzbmi.count}：${tzbm.column02}</a><b><a href="javascript:void(0);"  onclick="edit2('${tzbm.id}')" style="color: #4084b6 ; title="查看详情">${tzbm.column03}</a></b><em>${tzbm.column19Name}</em></span>
                    <ul>
					    <c:set var="zxmList" value="${tzbm.zxmList}"/>
					    <c:forEach items="${zxmList}" var="zxm" varStatus="zxmi">                    	
                        <li>
	                        <span><a href="###"> <i class="icon-minus-sign"></i>子项目${zxmi.count}：${zxm.column02}</a><b><a href="javascript:void(0);" onclick="edit3('${zxm.id}')" style="color: #4084b6 ; title="查看详情">${zxm.column03}</a></b><em>${zxm.column09Name}</em></span>
                            <ul>
							    <c:set var="zxmhtList" value="${zxm.zxmHtList}"/>
							    <c:forEach items="${zxmhtList}" var="zxmht" varStatus="zxmhti">                            	
                                <li>
                                    <span><a href="###"><i class="icon-three"> </i>项目合同${zxmhti.count}：${zxmht.column01}</a><b><a href="javascript:void(0);"  onclick="edit4('${zxmht.id}')" style="color: #4084b6 ; title="查看详情">${zxmht.column03}</a></b><em>${zxmht.column21Name}</em></span>
                                </li>
                                </c:forEach>
                            </ul>
                        </li>
                        </c:forEach>
                    </ul>
                </li>
                 </c:forEach>
            </ul>
        </li>
    </ul>
</div>  	
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', '');
    $('.tree li.parent_li > span').on('click', function (e) {
        var children = $(this).parent('li.parent_li').find(' > ul > li');
        if (children.is(":visible")) {
            children.hide('fast');
            $(this).attr('title', 'Expand this branch').find(' > a>i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
        } else {
            children.show('fast');
            $(this).attr('title', 'Collapse this branch').find('  > a>i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
        }
        e.stopPropagation();
    });
});

function edit1(id){
	window.open("<%=basePath%>jsxm/edit?pageSource=zb&id="+id);
}
function edit2(id){
	window.open("<%=basePath%>tzbm/edit?pageSource=zb&id="+id);
}
function edit3(id){
	window.open("<%=basePath%>zxm/edit?pageSource=zb&id="+id);
}
function edit4(id){
	window.open("<%=basePath%>zxmHt/edit?pageSource=zb&id="+id);
}
</script>