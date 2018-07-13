<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/leftmenu.js"></script>

<script language="javascript">
	function setTab(name, cursel, n) {
		for ( var i = 1; i <= n; i++) {
			var menu = document.getElementById(name + i);
			menu.className = i == cursel ? "hover" : "";
		}
	}
	function loginOut(){
		window.parent.location.href="<%=basePath%>core/loginOut";
	}
	function changePassword(){
		try{ 
			document.getElementById("content").src = "<%=basePath%>sys/myuser/changePassword";
	    }catch (_ex){
// 			var ss = window.parent.document.getElementById("mian").contentWindow;
// 			ss.document.getElementById("frmmain").src="<%=basePath%>sys/myuser/changePassword";
			document.getElementById("content").src = "<%=basePath%>sys/myuser/changePassword";
   		} 
	}
	
	function toViewSysNotice(){
		try{ 
    		parent.mian.frmmain.location.href="<%=basePath%>sys/noticeCommon/toViewNotice";
	    }catch (_ex){
			var ss = window.parent.document.getElementById("mian").contentWindow;
			ss.document.getElementById("frmmain").src="<%=basePath%>sys/noticeCommon/toViewNotice";
   		} 
	}
	
	function herfToMyWorkPlace(){
	    window.top.location.href ='<%=basePath %>core/portal/portal';
		<%-- parent.parent.bodyFrame.location = '<%=basePath %>/core/portal/myWorkPlace';
		var menus = document.getElementById("hMenu").getElementsByTagName("li");
		for(var i=0;i<menus.length;i++){
			if(i==0){
				menus[i].className = 'hover';
			}else{
				menus[i].className = '';
			}
		} --%>
	}
</script>
</head>

<body>
<!-- 头部 S -->
<div class="f_wrap">
  <div class="header" id="header">
    <p class="title_logo">南方基地管理提升平台</p>
    <div class="right_item">
    <img class="head_ico" src="/SRMC/dc/images/head.png"  style="display: none;"/>
      <span onclick="changePassword()" style="text-decoration:underline;cursor:pointer;" buttonMode="true" useHandCursor="true"  class="named">${sysUser.userName}</span>
      <span class="time" id="time"></span>
      <i class="exit" onclick="loginOut()"></i>
    </div>
  </div>
  <div class="nav" id="nav">
    <div class="nav_wrap" id="menu">
      <ul>
        <li class="primary current" id="oneMenu0">
          <a href="javascript:;" id="oneMenu">全部导航</a>
          <!-- 左侧导航 S -->
          <div class="leftmenu" id="allEntry-0">
            <!-- 最后元素加上class="lastli" -->
            <ul class="second">
        	<c:forEach items="${menuList0}" var="menu"  varStatus="i">
			              <li class="${i.last ? 'lastli':''}" ><img src="/SRMC/dc/images/leftMenuIcon/icon0${i.count }.png" /><span>${menu.moduleName}</span><%-- 一级菜单 --%>
			              <c:if test="${not empty menu.nextList}">
				                <ul class="third">
				                	  <c:forEach items="${menu.nextList}" var="menu"  varStatus="i">
					                  <li class="${i.last ? 'lastli':''}" onclick="openURL('${menu.url}')"><img src="/SRMC/dc/images/leftMenuIcon/icon0${i.count }.png" /><span>${menu.moduleName}</span><%-- 二级菜单 --%>
					                  		<c:if test="${not empty menu.nextList}">
						                    <ul>
							                	  <c:forEach items="${menu.nextList}" var="menu"  varStatus="i">
								                  	<li class="${i.last ? 'lastli':''}" onclick="openURL('${menu.url}')"><img src="/SRMC/dc/images/leftMenuIcon/icon0${i.count }.png" /><span>${menu.moduleName}</span><%-- 三级菜单 --%>
								                  	<c:if test="${not empty menu.nextList}">
								                  			<ul>
								                  					<c:forEach items="${menu.nextList}" var="menu"  varStatus="i">
										                    				<li class="${i.last ? 'lastli':''}" onclick="openURL('${menu.url}')"><img src="/SRMC/dc/images/leftMenuIcon/icon0${i.count }.png" /><span>${menu.moduleName}</span></li><%-- 四级菜单 --%>
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
        </li>
        
		
        <c:forEach items="${menuList}" var="menu"  varStatus="i">
				<li class="primary"  id="oneMenu${i.count}" >
					<a href="javascript:void(0);"   id="oneMenu"  ><span onclick="openURL('${menu.url}')">${menu.moduleName}</span></a><%-- 一级菜单 --%>
					<c:if test="${not empty menu.nextList}">
			          <div class="leftmenu" id="allEntry-${i.count}">
			            <ul class="second">
			            <c:forEach items="${menu.nextList}" var="menu"  varStatus="i" >
			              <li class="${i.last ? 'lastli':''}"  ><img src="/SRMC/dc/images/leftMenuIcon/icon0${i.count }.png" /><span onclick="openURL('${menu.url}')">${menu.moduleName}</span><i class="icon0${i.count}"></i><%-- 二级菜单 --%>
			              <c:if test="${not empty menu.nextList}">
				                <ul class="third">
				                	  <c:forEach items="${menu.nextList}" var="menu"  varStatus="i">
					                  <li class="${i.last ? 'lastli':''}"  onclick="openURL('${menu.url}')"><img src="/SRMC/dc/images/leftMenuIcon/icon0${i.count }.png" /><span>${menu.moduleName}</span><%-- 三级菜单 --%>
					                  		<c:if test="${not empty menu.nextList}">
						                    <ul>
							                	  <c:forEach items="${menu.nextList}" var="menu"  varStatus="i">
								                  	<li class="${i.last ? 'lastli':''}" onclick="openURL('${menu.url}')"><img src="/SRMC/dc/images/leftMenuIcon/icon0${i.count }.png" /><span>${menu.moduleName}</span></li><%-- 四级菜单 --%>
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
			          </c:if>					
				</li>
        </c:forEach>        
      </ul>
      
      <div class="search" style="display: none;">
        <input type="text" />
        <a href="javascript:;" class="search_btn"></a>
      </div>
    </div>
  </div>
</div>
<!-- 头部 E -->
</body>
</html>
<script type="text/javascript" src="/SRMC/dc/purchase/spin.js"></script>

<script type="text/javascript">

	/**初始化菜单效果**/
    $(function(){
       //$("#hMenu li:first").find("a").click();
	    	$("#menu").find("li[id*=oneMenu]").each(function(i){
	    		var menu=$("#allEntry-"+(i));
	    		var currentID = $(this).attr("id");

	    		//1.打开，隐藏toggle
		    	$(this).hover(
				    	function(){
				    		$(this).addClass("primary current"); 
				    		//当前URL
							var currentURL = document.getElementById("content").src;				    		
				    		$("#menu").find("li[id*=oneMenu]").each(function(){
				    			if(currentID != $(this).attr("id")){
				    				if(currentURL.indexOf("core/portal/main") > -1 && $(this).attr("id") == "oneMenu0"){
				    					return;
				    				}
				    				$(this).removeClass("primary current");
				            		$(this).addClass("primary");
				    			}
				    		});
		    				if(currentURL.indexOf("core/portal/main") > -1 && $(this).attr("id") == "oneMenu0"){
		    					return;
		    				}				    		
		    				//menu.slideDown();
		    				menu.show();
							$(this).addClass("on");
				    	}, 
				    	function(){
				    		//当前URL
							var currentURL = document.getElementById("content").src;				    	
		    				if(currentURL.indexOf("core/portal/main") > -1 && $(this).attr("id") == "oneMenu0"){
		    					return;
		    				}
		    				$(this).removeClass("primary current");
		            		$(this).addClass("primary");	    	
		            		
				            //menu.slideUp();
				            menu.hide();
							$(this).removeClass("on");
				    	}
		    	); 

				// 2.移入显示下一级别ul
				menu.find("li").hover(function(){
			            $(this).addClass("on");
			        },
			        function(){
			            $(this).removeClass("on");
			        });
				// 3.设置箭头
				menu.find("li").each(function(){
					if ($(this).children("ul").html()) {
						$(this).addClass("hasNext");
					}
				});
	    	});
    	});
    
    	/**打开内页**/
		function openURL(currentURL){
			if(currentURL == ""){
				return;
			}
			if(currentURL.indexOf("http") > -1){
				window.open(currentURL,"blank");
				return;
			}
			if(currentURL.indexOf("zccr") > -1){
				window.open("<%=basePath%>"+currentURL,"blank");
				return;
			}			
var a = document.getElementById("content"); 
var b = document.getElementById("loader_container");
var target = document.getElementById("process");
//alert(target);
var spinner = new Spinner(); 
spinner.spin(target);  
a.style.visibility = "hidden"; //隐藏 
b.style.display = "block"; //显示
a.src = "<%=basePath%>"+currentURL;

if (a.attachEvent){
a.attachEvent("onload", function(){
    a.style.visibility = "visible";  
    setTimeout(function(){ b.style.display = "none";spinner.spin();},"800"); 

    });
} else {
    a.onload = function(){
    a.style.visibility = "visible";
    //clearInterval(t_id);
    setTimeout(function(){ b.style.display = "none";spinner.spin();},"800"); 
    //显示
    };
} 
            
			//document.getElementById("content").src = "<%=basePath%>"+currentURL;
			
			var menu=$("#oneMenu0");
 			if(currentURL.indexOf("core/portal/main") > -1){
 				menu.addClass("primary current"); 
 			}else{
		    	menu.removeClass("primary current");
		        menu.addClass("primary"); 			
 			}			
    	}
    	
        document.getElementById("time").innerHTML = new Date().toLocaleString() + "星期" + "日一二三四五六".charAt(new Date().getDay());
        setInterval("document.getElementById('time').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
</script>