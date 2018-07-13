<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String basePath111 = request.getContextPath()+ "/"; 
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
<script type="text/javascript">
    $(document).ready(function($) {
	$("#yfgl_sub_tb").on("click",".gl_m_r_ul li",function(e){
	$("#yfgl_sub_tb li.hover").removeClass("hover").next().hide();
	$(this).addClass("hover").next().show();
	});
	$("#yfgl_sub_tb").on("click",".gl_m_r_ul li a",function(e){
		$("#yfgl_sub_tb li a.hover").removeClass("hover");
		$(this).addClass("hover");
	});
});
</script>
</head>
<body class="bg_c_g"></body>
</html>
<html>
<head>
<title></title>
<meta charset="utf-8" />
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script src="/SRMC/dc/js/jquery.SuperSlide.2.1.1.js"></script>
</head>
<body style="background-color: #f2f2f2;">
	<div class="content_wrap">
		<div class="content">
			<!-- 左侧导航 S -->
			<div class="leftmenu" id="allEntry-i">
						<!-- 最后元素加上class="lastli" -->
						<ul class="second">
			        	<c:forEach items="${menuList0}" var="menu"  varStatus="i">
						              <li class="${i.last ? 'lastli':''}"  onclick="openURL('${menu.url}')">${menu.moduleName}<img src="/SRMC/dc/images/leftMenuIcon/icon0${i.count }.png" /><%-- 一级菜单 --%>
						              <c:if test="${not empty menu.nextList}">
							                <ul class="third">
							                	  <c:forEach items="${menu.nextList}" var="menu"  varStatus="i">
								                  <li class="${i.last ? 'lastli':''}" onclick="openURL('${menu.url}')"><img src="/SRMC/dc/images/leftMenuIcon/icon0${i.count }.png" />${menu.moduleName}<%-- 二级菜单 --%>
								                  		<c:if test="${not empty menu.nextList}">
									                    <ul>
										                	  <c:forEach items="${menu.nextList}" var="menu"  varStatus="i">
											                  	<li class="${i.last ? 'lastli':''}" onclick="openURL('${menu.url}')"><img src="/SRMC/dc/images/leftMenuIcon/icon0${i.count }.png" />${menu.moduleName}<%-- 三级菜单 --%>
											                  	<c:if test="${not empty menu.nextList}">
											                  			<ul>
											                  					<c:forEach items="${menu.nextList}" var="menu"  varStatus="i">
													                    				<li class="${i.last ? 'lastli':''}" onclick="openURL('${menu.url}')"><img src="/SRMC/dc/images/leftMenuIcon/icon0${i.count }.png" />${menu.moduleName}</li><%-- 四级菜单 --%>
													                    		</c:forEach>
													                    </ul>
													            </c:if>
													            </li>
										                      </c:forEach>
									                    </ul>
									                    </c:if>
								                  </li>
								                  </c:forEach>
							                </ul>
							                </c:if>
						              </li>        		            
			              </c:forEach>
						</ul>
			</div>
			<!-- 左侧导航E -->
			<!-- banner S -->
			<div class="banner">
				<div class="bd">
					<ul>
						<c:forEach items="${list}" var="list"  varStatus="i">
							<li style="background:url(<%=basePath111%>rulesController/showImage?fileId=${list[0]}) #88a9c5 center 0 no-repeat;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale');-moz-background-size:100% 100%;background-size:100% 100%;"><a target="_self" href="${list[5]}"></a></li>
						</c:forEach>
					</ul>
				</div>
				<div class="hd"><ul></ul></div>
				<a class="prev"></a>
				<a class="next"></a>
			</div>
			<!-- banner E -->
			<!-- 快捷入口 S -->
			<div class="quick_entry" style="display: none">
				<span class="perZone">
					<p><i></i>个人专区</p><hr/>
					<a href="#">我的设置</a>
					<a href="#">修改密码</a>
					<a href="#">我的资料</a>
				</span>
				<span class="jfZone">
					<p><i></i>经分专区</p><hr/>
					<a href="#">合交经分</a>
					<a href="#">报账经分</a>
					<a href="#">物业经分</a>
				</span>
				<span class="cgZone">
					<p><i></i>采购专区</p><hr/>
					<a href="#">合交经分</a>
					<a href="#">报账经分</a>
					<a href="#">物业经分</a>
				</span>
				<span class="perZone">
					<p><i></i>个人专区</p><hr/>
					<a href="#">合交经分</a>
					<a href="#">报账经分</a>
					<a href="#">物业经分</a>
				</span>
				<span class="perZone" style="margin-right: 0">
					<p><i></i>采购专区</p><hr/>
					<a href="#">合交经分</a>
					<a href="#">报账经分</a>
					<a href="#">物业经分</a>
				</span>
			</div>
			<!-- 快捷入口 E -->
		</div>
	</div>
<script type="text/javascript">jQuery(".banner").slide({ titCell:".hd ul", mainCell:".bd ul", effect:"fold",  autoPlay:true, autoPage:true, trigger:"click" });
    	/**打开内页**/
		function openURL(currentURL){
			parent.	openURL(currentURL);
    	}
    	
$(function(){
	/*var leftMenu=$('#allEntry-i');
	// 1.首页默认展开，其他页隐藏
	if(!$('#allMenu').hasClass('noclick')){
		$('#allMenu').click(function(){
			if (!$(this).hasClass('on')){
				alert();
				leftMenu.slideDown();
				$(this).addClass('on');
			}else{
				leftMenu.slideUp();
				$(this).removeClass('on');
			}
		});
	}else{
		alert("2");
		leftMenu.show();
	}*/
	// 2.移入显示下一级别ul
	$('#allEntry-i').find('li').hover(function(){
            $(this).addClass('on');
        },
        function(){
            $(this).removeClass('on');
        });
	// 3.设置箭头
	$('#allEntry-i').find('li').each(function(){
		if ($(this).children('ul').html()) {
			$(this).addClass('hasNext');
		}
	});
});    	
</script>
</body>
</html>