<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title></title>
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet">
<link href="/SRMC/rmpb/css/tip/css/style.css" type="text/css" rel="stylesheet" />
<link href="/SRMC/rmpb/css/notice.css" rel="stylesheet"	type="text/css" />
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.easing-1.3.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <style type="text/css">
        .fancybox-bg{
            display: none;
        }
        #fancybox-wrap{
            padding: 20px 0px 0px 20px;
        }
        pre{
			white-space: pre-wrap;       
			white-space: -moz-pre-wrap;  
			white-space: -pre-wrap;      
			white-space: -o-pre-wrap;    
			word-wrap: break-word;       
		}
    </style>

<script type="text/javascript">
    var sysNoticeList = new Array();
    var st=1;
    var NST=1;
    var ed;
    function ellipsisStr(str){
        var newStr = '';
        var array = new Array();
        array = str.split("");
        if(array.length > 16){
            newStr = str.substr(0, 16) + "...";
        }else{
            newStr = str;
        }
        return newStr;
    }
    function ellipsisStr2(str){
        var newStr = '';
        var array = new Array();
        array = str.split(",");
        if(array.length > 2){
            newStr = array[0]+","+array[1] + "...";
        }else{
            newStr = str;
        }
        return newStr;
    }

    jQuery(document).ready(function(){
        var useragent = navigator.userAgent.toLowerCase();

        var showFlag=false;
        browser = {
            ie : /msie/.test(useragent) && !/opera/.test(useragent),  //IE
            moz : /gecko/.test(useragent),  //火狐内核浏览器
            opera : /opera/.test(useragent), //遨游
            safari : /webkit/.test(useragent), //苹果
            chrome : /chrome/.test(useragent)&& /webkit/i.test(useragent) && /mozilla/i.test(useragent),
            version : (useragent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1], //浏览器版本
            mozilla : /mozilla/.test( useragent ) && !/(compatible|webkit)/.test( useragent ) //火狐
        }

        if(browser.ie && browser.version < 8) {
            showFlag = true;
        }

        if(browser.chrome){
            showFlag = false;
        }

        if(${user.isIeTip=='1'}){
            //始终不弹窗
            showFlag = false;
        }

        if(showFlag){
            show('light');
        }        
        
        /*var noticeListSize = jQuery("#noticeListSize").val();
        if( noticeListSize!=null&&parseInt(noticeListSize)>0){
            jQuery("#drag").show();   
            var sysNoticeIdDiv = jQuery("#sysNoticeIdDiv0").val();
            jQuery("#sysNoticeIdTemp").val(sysNoticeIdDiv);
            jQuery("#indexTemp").val(0);
        }*/
        
        /* var sysNotices = jQuery("#sysNotices").val();       
        if(null!=sysNotices&&sysNotices!=""){          
			sysNoticeList = sysNotices.split(",");       
        }      
        doOpenSysNotice(sysNoticeList); */
          
      		
    });
    
           
    
   <%-- function doOpenSysNotice(sysNoticeList) {
       if(sysNoticeList.length>0){
           openSysNotice(sysNoticeList[0]);
       }		
	}
    
    function openSysNotice(sysNoticeId) {
                var stt=st;
                jQuery("#sysNotice").unbind();  
			    var url = "<%=basePath%>sys/noticeCommon/viewNoticeHome?st=1&sysNoticeId="+sysNoticeId;
			    jQuery("#sysNotice").attr("href",url);	    
			    jQuery("#sysNotice").fancybox({
			             'width'				: '64%',
				         'height'			: '84%',
			  			//'scrolling'			:'yes',
			  			'autoScale'			: false,//自适应窗口大小
			  			//'overlayShow'		: true,
			  			'transitionIn'		: 'none',
			  			'transitionOut'		: 'none',
			  			'type'				: 'iframe',
			  			'href'				:url,
			  			'onClosed'          :function(){
			  			                        doClose();
			  			                    }
			      });
			      jQuery("#sysNotice").click();	
	}  --%>
	
	function doClose(){
	   if(sysNoticeList.length>1){
	      var path = "<%=basePath%>core/portal/home";
	      window.location.href = path;
	   }else{
	     return ;
	   }	   
	} 
	
  function  doCloseNotice(i,sysNoticeId){	   
	   var noticeListSize=jQuery("#noticeListSize").val();         
	   var temp= parseInt(i) + 1;	
	   /*var urlt="<%=basePath%>sys/noticeCommon/changeReadStatus";
			var paramst = {
				sysNoticeId : sysNoticeId
			};						
			$.ajax({  
	          type : "post",  
	          url :urlt,  
	          data : paramst,  
	          async : false,  
	          success : function(data){
	                if (data != "ok") {                    				            				           
					}		
	            }
	   });*/		       
	   if(temp<noticeListSize){	
	      //jQuery("#noticeId"+i).hide();
	      jQuery("#noticeId"+i).hide();
		  jQuery("#noticeId"+temp).show();
		  var sysNoticeIdDiv=jQuery("#sysNoticeIdDiv"+temp).val();
		  jQuery("#sysNoticeIdTemp").val(sysNoticeIdDiv);
          jQuery("#indexTemp").val(temp);	   
	   }else{	        
	      jQuery("#drag").hide();	   
	   }
	}
    
    function doCloseSubpage() {
		jQuery.fancybox.close();
	}
	
	function show(tag){ 
	 	var light=document.getElementById(tag); 
	 	light.style.display='block'; 
	} 
	function hide(tag){ 
	 	var light=document.getElementById(tag); 
	 	light.style.display='none'; 
	}
	
	function changeStatus(){
  		var tip;
  		if(jQuery("input[name=isIeTip]:checked").val()){
  		  tip="1";
  		}else{
  			tip="0";
  		}
  		var url = "<%=basePath%>core/portal/ietip";
			var params = {
				operateType:'1',
				isIeTip:tip
			};
			jQuery.post(url, params, function(data){
			} );
  	}
  	
  	function toPre(sysNoticeId){
	     var url="<%=basePath%>sys/noticeCommon/preNotice?home=1&sysNoticeId="+sysNoticeId;
		 jQuery.ajax({  
	            type : "POST",  
	            url : url,
	            dataType : "html",
	            success : function(html){
	                jQuery("#light",window.top.document).empty();
		            jQuery("#light",window.top.document).append(html);
		            window.top.popShow();             
				},
				error : function() {
					alert("预览失败,请重新预览!");
				}
	    });		    
	 }
	 
	 function doSelectPass(st){
            var sysNoticeId=jQuery("#sysNoticeId").val();
            var url="<%=basePath%>sys/noticePre/doSelect";           
			var params = {sysNoticeId : sysNoticeId,st:st};						
			$.ajax({  
	          type : "post",  
	          url :url,  
	          data : params,  
	          async : false,  
	          success : function(data){
	                if (data =="success") {
	                     alert("审批成功");
	                     window.location.reload();
					}else{
					     alert("操作失败")
	                     window.location.reload();
					}					
	           }  
		});
	}
	
	function doEdit(){
	    var sysNoticeId=jQuery("#sysNoticeId").val();
	    toEdit(sysNoticeId);    
	}
	
	function toEdit(sysNoticeId){
	    var path = "<%=basePath%>sys/noticePre/toEditSysNotice?sysNoticeId="+sysNoticeId
	         +"&location=" + "core/portal/home";
		window.location.href = path;
	
	}
	
	function editNotice(sysNoticeId){
	    var path = "<%=basePath%>sys/noticeMyList/editSysNotice?sysNoticeId=" + sysNoticeId
		         +"&location=" + "core/portal/home";
		window.location.href = path;
	}
	
</script>

<style  type="text/css"  >
.gl_table_a02 td{ color: black; }
.gl_table_a02 td a:link{ color: black; }
.gl_table_a02 td a:visited{ color: black; }
</style>

</head>

<body class="bg_c_g">
<form action="<%=basePath%>core/portal/home" method="get" id="pageForm" name="pageForm">
<input type="hidden" id="type" name="type" value="${type}"/>
<input type="hidden" id="flag" name="flag" value="${flag}"/>
<input type="hidden" id="sysNotices" name="sysNotices" value="${sysNotices}"/>

<%-- <c:forEach items="${noticeList}" var="bo" varStatus="i">
   <a id="sysNotice${i.count}"></a>
</c:forEach> --%>
<a id="sysNotice"></a>

<div class="gl_import_m_g" style="padding-left:20px">
  <ul class="home_top_a01">
    <li>
      <c:if test="${FlowInstance0!='0'}">
		<div class="home_r_t">${FlowInstance0}</div>
	  </c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(1);" ><img src="/SRMC/rmpb/images/home_bnt01.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font">需求初审</div>
    </li>
    <li>
     <c:if test="${FlowInstance1!='0'}">
		<div class="home_r_t">${FlowInstance1}</div>
	  </c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(2);"><img src="/SRMC/rmpb/images/home_bnt02.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font">需求审批</div>
    </li>
    <li>
      <c:if test="${FlowInstance2!='0'}">
		<div class="home_r_t">${FlowInstance2}</div>
	  </c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(3);"><img src="/SRMC/rmpb/images/home_bnt03.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font">研发管理</div>
    </li>
    <li>
      <c:if test="${FlowInstance3!='0'}">
		<div class="home_r_t">${FlowInstance3}</div>
	  </c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(4);"><img src="/SRMC/rmpb/images/home_bnt04.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font">版本上线</div>
    </li>
    <li>
		<c:if test="${fn:length(flowInsactorMap['10'])>0}">
		<div class="home_r_t">${fn:length(flowInsactorMap['10'])}</div>
		</c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(10);"><img src="/SRMC/rmpb/images/home_bnt05.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font">故障管理</div>
    </li>
       
<%--  适配管理模块的六个流程          --%>
    <li>
		<c:if test="${fn:length(flowInsactorMap['1'])>0}">
		<div class="home_r_t">${fn:length(flowInsactorMap['1'])}</div>
		</c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(100);"><img src="/SRMC/rmpb/images/home_bnt06.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font">适配管理</div>
    </li>
    
    <li>
		<c:if test="${fn:length(flowInsactorMap['11'])>0}">
		<div class="home_r_t">${fn:length(flowInsactorMap['11'])}</div>
		</c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(1100);"><img src="/SRMC/rmpb/images/home_bnt07.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font">测试问题</div>
    </li>
    
    <li>
		<c:if test="${fn:length(flowInsactorMap['3'])>0}">
		<div class="home_r_t">${fn:length(flowInsactorMap['3'])}</div>
		</c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(300);"><img src="/SRMC/rmpb/images/home_bnt08.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font">日常问题</div>
    </li>
    
<%--    先隐藏 --%>
	<c:if test="${hasWorksheetIcon}">
    <li>
		<c:if test="${flowInsactorMap_xwcz['15']>0}">
		<div class="home_r_t">${flowInsactorMap_xwcz['15']}</div>
		</c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(1500);"><img src="/SRMC/rmpb/images/home_bnt09.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font">现网工单</div>
    </li>
    </c:if>
      
    <%--
    <li>
		<c:if test="${fn:length(flowInsactorMap['12'])>0}">
		<div class="home_r_t">${fn:length(flowInsactorMap['12'])}</div>
		</c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(1200);"><img src="/SRMC/rmpb/images/home_bnt05.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font">终端申请</div>
    </li>
    
    <li>
		<c:if test="${fn:length(flowInsactorMap['13'])>0}">
		<div class="home_r_t">${fn:length(flowInsactorMap['13'])}</div>
		</c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(1300);"><img src="/SRMC/rmpb/images/home_bnt05.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font">终端归还</div>
    </li>
    
    <li>
		<c:if test="${fn:length(flowInsactorMap['6'])>0}">
		<div class="home_r_t">${fn:length(flowInsactorMap['6'])}</div>
		</c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(600);"><img src="/SRMC/rmpb/images/home_bnt05.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font">终端寄送</div>
    </li>
    --%>
    
    <c:if test="${requestScope.sysNoticeIcon eq '1' or  requestScope.sysNoticeIcon eq '2' or requestScope.sysNoticeIcon eq '3'}">
	    <li>
			<c:if test="${flowInsactorMap_notice['3000'] gt 0}">
				<div class="home_r_t">${flowInsactorMap_notice['3000'] }</div>
			</c:if>
	      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(3000);"><img src="/SRMC/rmpb/images/home_bnt10.png" width="57" height="57" /></a></div>
	      <div class="home_t_bnt_font">系统通知</div>
	    </li>
    </c:if>
    
    <c:if test="${hasWorksheetIcon}">
    <li>
		<c:if test="${flowInsactorMap_sjzc['16']>0}">
		<div class="home_r_t">${flowInsactorMap_sjzc['16']}</div>
		</c:if>
      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(1600);"><img src="/SRMC/rmpb/images/home_bnt11.png" width="57" height="57" /></a></div>
      <div class="home_t_bnt_font" >数据工单</div>
    </li>
    </c:if>
    
    <%-- <c:if test="${hasOperateIcon}">
	    <li>
			<c:if test="${flowInsactorMap_operate['18']>0}">
			<div class="home_r_t">${flowInsactorMap_operate['18']}</div>
			</c:if>
	      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(1800);"><img src="/SRMC/rmpb/images/home_bnt11.png" width="57" height="57" /></a></div>
	      <div class="home_t_bnt_font" style="text-align:left;">运营工单</div>
	    </li>
    </c:if> --%>
    <c:if test="${hasOperateIcon}">
	    <li>
			  <c:if test="${flowInsactorMap_operate['18']>0}">
			     <div class="home_r_t">${flowInsactorMap_operate['18']}</div>
			  </c:if>
		      <div class="home_t_bnt_pic"><a style="cursor: pointer;" onclick="setflag(1800);"><img src="/SRMC/rmpb/images/home_bnt01.png" width="57" height="57" /></a></div>
		      <div class="home_t_bnt_font" >运营工单</div>
		 </li>
	 </c:if>
    
  </ul>

  <div class="ge_a01b"></div>

  <div class="h_n_div01">
              <ul class="home_nav_top01">
                        <li id="one1" onclick="setTab(1);" class="hover">待办事项
                        <c:choose>
	                        <c:when test="${flag==1}">
								<%--需求初审--%>
	                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${FlowInstance0}</span>)</span>
	                        </c:when>
	                        <c:when test="${flag==2}">
	                        	<%--需求审批--%>
	                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${FlowInstance1}</span>)</span>
	                        </c:when>
	                        <c:when test="${flag==3}">
	                        	<%--研发管理--%>
	                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${FlowInstance2}</span>)</span>
	                        </c:when>
	                        <c:when test="${flag==4}">
	                        	<%--版本上线--%>
	                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${FlowInstance3}</span>)</span>
	                        </c:when>
	                        <c:when test="${flag==10}">
	                        	<%--故障管理--%>
	                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${fn:length(flowInsactorMap['10'])}</span>)</span>
	                        </c:when>
	                        <c:when test="${flag==100}">
	                        	<%--适配管理--%>
	                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${fn:length(flowInsactorMap['1'])}</span>)</span>
	                        </c:when>
	                        <c:when test="${flag==1100}">
	                        	<%--测试问题--%>
	                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${fn:length(flowInsactorMap['11'])}</span>)</span>
	                        </c:when>
	                        <c:when test="${flag==300}">
	                        	<%--日常问题--%>
	                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${fn:length(flowInsactorMap['3'])}</span>)</span>
	                        </c:when>
	                        <c:when test="${flag==1500}">
	                        	<%--现网操作工单--%>
	                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${flowInsactorMap_xwcz['15']}</span>)</span>
	                        </c:when>
	                        <c:when test="${flag==1600}">
	                        	<%--数据工单--%>
	                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${flowInsactorMap_sjzc['16']}</span>)</span>
	                        </c:when>
	                        <c:when test="${flag==1800}">
	                        	<%--运营操作工单--%>
	                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${flowInsactorMap_operate['18']}</span>)</span>
	                        </c:when>
	                        <c:when test="${flag==3000}">
	                        	<%--系统通知--%>
	                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${flowInsactorMap_notice['3000']}</span>)</span>
	                        </c:when>
	                        <c:otherwise>
	                        	<%--全部--%>
		                        <c:if test="${Word_new_count>0}">
			                        <span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${Word_new_count}</span>)</span>
		                        </c:if>
	                        </c:otherwise>
                        </c:choose>
                        </li>
                        <c:if test="${flag != 3000 }">
		      	             <li id="one2" onclick="setTab(2);">待阅事项
		                        <c:choose>
			                        <c:when test="${flag==1}">
										<%--需求初审--%>
			                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${FlowInstance_cc0}</span>)</span>
			                        </c:when>
			                        <c:when test="${flag==2}">
			                        	<%--需求审批--%>
			                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${FlowInstance_cc1}</span>)</span>
			                        </c:when>
			                        <c:when test="${flag==3}">
			                        	<%--研发管理--%>
			                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${FlowInstance_cc2}</span>)</span>
			                        </c:when>
			                        <c:when test="${flag==4}">
			                        	<%--版本上线--%>
			                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${FlowInstance_cc3}</span>)</span>
			                        </c:when>
			                        <c:when test="${flag==10}">
			                        	<%--故障管理--%>
			                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${fn:length(flowInsactorMap_cc['10'])}</span>)</span>
			                        </c:when>
			                        <c:when test="${flag==100}">
			                        	<%--适配管理--%>
			                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${fn:length(flowInsactorMap_cc['1'])}</span>)</span>
			                        </c:when>
			                        <c:when test="${flag==1100}">
			                        	<%--测试问题--%>
			                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${fn:length(flowInsactorMap_cc['11'])}</span>)</span>
			                        </c:when>
			                        <c:when test="${flag==300}">
			                        	<%--日常问题--%>
			                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${fn:length(flowInsactorMap_cc['3'])}</span>)</span>
			                        </c:when>
			                        <c:when test="${flag==1500}">
			                        	<%--现网操作工单--%>
			                        	<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${flowInsactorMap_xwcz_cc['15']}</span>)</span>
			                        </c:when>
			                        <c:when test="${flag==1600}">
	                        		<%--数据工单--%>
	                        			<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${flowInsactorMap_sjzc_cc['16']}</span>)</span>
	                       			 </c:when>
	                       			<c:when test="${flag==1800}">
	                        		<%--运营操作工单--%>
	                        			<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${flowInsactorMap_operate_cc['18']}</span>)</span>
	                       			 </c:when>
			                        <c:otherwise>
			                        	<%--全部--%>
				      	             <c:if test="${Word_Read_count>0}">
										<span style="font-size:12px; font-family:Arial;">(<span style=" font-size:13px; font-weight:bold;">${Word_Read_count}</span>)</span>
				      	             </c:if>
			                        </c:otherwise>
		                        </c:choose>
		      	             </li>
		      	           </c:if>
               </ul>
  </div>


  <c:if test="${type=='1'}">
  <div id="con_one_1" >
  <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <th width="10%">事项类型</th>
      <th width="29%">事项名称</th>
      <th width="7%">紧急程度</th>
      <th width="7%">发起人</th>
      <th width="12%">发起组织</th>
      <th width="14%">待办产生时间</th>
      <th width="7%">处理时限</th>
      <th width="7%">超时状态</th>
      <th width="7%">操作</th>
    </tr>
    <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
 	<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if> >
      <td>
      <c:choose>
         <c:when test="${flag==5}">
		        <c:if test="${item[5]=='13062108000000000001'}">
				需求初审
				</c:if>
				<c:if test="${item[5]=='13062108000000000002'}">
				需求审批
				</c:if>
				<c:if test="${item[5]=='13062108000000000003'}">
				研发管理
				</c:if>
				<c:if test="${item[5]=='13062108000000000004'}">
				版本上线
				</c:if>
				<c:if test="${item[5]=='10'}">
				故障管理
				</c:if>
         </c:when>
         <c:otherwise>
               <c:if test="${flag==1}">
				需求初审
				</c:if>
				<c:if test="${flag==2}">
				需求审批
				</c:if>
				<c:if test="${flag==3}">
				研发管理
				</c:if>
				<c:if test="${flag==4}">
				版本上线
				</c:if>
				<c:if test="${flag==10}">
				故障管理
				</c:if>
         </c:otherwise>
      </c:choose>
      
				
<%--				适配管理新增加六个流程--%>
				<c:if test="${item[5]=='1'}">
				适配管理
				</c:if>
				<c:if test="${item[5]=='11'}">
				测试问题
				</c:if>
				<c:if test="${item[5]=='3'}">
				日常问题
				</c:if>
				<c:if test="${item[5]=='12'}">
				终端申请
				</c:if>
				<c:if test="${item[5]=='13'}">
				终端归还
				</c:if>
				<c:if test="${item[5]=='6'}">
				终端寄送
				</c:if>
				
<%--				现网操作工单--%>
				<c:if test="${item[5]=='15'}">
				现网工单
				</c:if>
				<c:if test="${item[5]=='3000'}">
				系统通知
				</c:if>
				<c:if test="${item[5]=='16'}">
				数据工单
				</c:if>
				<c:if test="${item[5]=='18'}">
				运营工单
				</c:if>
				
      </td>
      
      
<%--事项名称--%>
      <td style="word-break:break-all;text-align:left;padding:3px 8px 3px 8px;line-height:19px;">
		     <c:if test="${item[5]=='13062108000000000001'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>core/portal/toPreApproveRequireDetailApprove?createWorkorderId=${item[0]}');">${item[1]}</a>
		       </c:if>
		       <c:if test="${item[5]=='13062108000000000002'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>core/portal/toPreApproveRequireDetailWork?launchWorkorderId=${item[0]}');">${item[1]}</a>
		       </c:if>
		       <c:if test="${item[5]=='13062108000000000003'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>core/portal/toPreApproveRequireDevelopDetail?developWorkorderId=${item[0]}');">${item[1]}</a>
		       </c:if>
		       <c:if test="${item[5]=='13062108000000000004'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>core/portal/toPreAppversionDetailView?orderId=${item[0]}');">${item[1]}</a>
		       </c:if>
		       <c:if test="${item[5]=='10'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>workordermanager/failureOrderFlow/showDesktopOrderDetail?flowType=10&adapterWorkorderId=${item[0]}');">${item[1]}</a>
		       </c:if>
		       
<%--		       适配项目新增的六个流程--%>
		       <c:if test="${item[5]=='1'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>adapter/adapterProjectManage/adapterManage/toPreApproveAdapterProDetail?adapterWorkorderId=${item[0]}');">${item[1]}</a>
		       </c:if>
		       <c:if test="${item[5]=='11'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>adapter/preapprovetestproblem/dbtestProblemDetail?id=${item[0]}');">
		             ${item[1]}
                   	<%-- 当交互问题阅读状态为未读的时候并且用户是终端厂商 --%>
			      	<c:choose>
				      	<c:when test="${item[15] eq '0' and terminalCompanyFlag eq '1' }">
				      		<img src="/SRMC/rmpb/images/adapterManage/images/new.gif"/>
				      	</c:when>
				      	<c:when test="${item[16] eq '0' and terminalCompanyFlag ne '1' }">
				      		<img src="/SRMC/rmpb/images/adapterManage/images/new.gif"/>
				      	</c:when>
			      	</c:choose>
		             </a>
		       </c:if>
		       <c:if test="${item[5]=='3'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>dailyManage/preApproveDailyDevelop/queryApproveDailyDevelopItemPage?adapterWorkorderId=${item[0]}');">${item[1]}</a>
		       </c:if>
		       <c:if test="${item[5]=='12'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>adapter/stockoutFlowManage/showMyStockoutDetail?insactorId=${item[12]}&flowType=12');">${item[1]}</a>
		       </c:if>
		       <c:if test="${item[5]=='13'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>adapterManage/inDatabaseTerminalController/doWorksheet?operate=work&view=true&flag=1&worksheetId=${item[0]}');">${item[1]}</a>
		       </c:if>
		       <c:if test="${item[5]=='6'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>adapterManage/supplementTerminalController/toEditWorksheet?operate=work&view=true&worksheetId=${item[0]}');">${item[1]}</a>
		       </c:if>
		       
<%--		       现网操作工单--%>
		       <c:if test="${item[5]=='15'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>worksheetManage/worksheetManageFlow/dbWorkSheetDetail?workorderId=${item[0]}&insactorId=${item[12]}');">${item[1]}</a>
		       </c:if>
		       <!-- 运营操作工单 -->
		       <c:if test="${item[5]=='18'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>operate/flow/dbWorkSheetDetail?workorderId=${item[0]}&insactorId=${item[12]}');">${item[1]}</a>
		       </c:if>
			   <%-- 系统通知--%>
		       <c:if test="${item[5]=='3000'}">
		       		<c:choose>
		       			<c:when test="${not empty item[8] and item[8] eq 'manager' }">
		             		<a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="javascript:toPre('${item[0]}');">${item[1]}</a>
		       			</c:when>
		       			<c:when test="${not empty item[8] and item[8] ne 'manager' }">
		             		<a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="javascript:editNotice('${item[0]}');">${item[1]}</a>
		       			</c:when>
		       		</c:choose>
		       </c:if>
		       <%--	数据工单--%>
		       <c:if test="${item[5]=='16'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doApprove('<%=basePath%>extractData/dataSupportWorkOrder/dbExtractDataDetail?workorderId=${item[0]}&insactorId=${item[12]}');">${item[1]}</a>
		       </c:if>
		      
		       
	      </td>
	      
	      
<%--	      紧急程度--%>
	      <td>
	      
	      <%--
	    <c:if test="${item[5]!='10'}" var="rs">
			<c:if test="${item[2]=='1'}">
			低
			</c:if>
			<c:if test="${item[2]=='2'}">
			中
			</c:if>
			<c:if test="${item[2]=='3'}">
			高
			</c:if>
			<c:if test="${item[2]=='4'}">
			极高
			</c:if> 
		</c:if> 
		<c:if test="${!rs}">
			<c:if test="${item[2]=='0'}">
			低
			</c:if>
			<c:if test="${item[2]=='1'}">
			中
			</c:if>
			<c:if test="${item[2]=='2'}">
			高
			</c:if>
			<c:if test="${item[2]=='3'}">
			极高
			</c:if> 
		</c:if>
      --%>
	  <%--如果是 之前四个流程--%>
      <c:if test="${item[5]=='13062108000000000001' || item[5]=='13062108000000000002' || item[5]=='13062108000000000003' || item[5]=='13062108000000000004' }"  >
			<c:if test="${item[2]=='1'}">低</c:if>
			<c:if test="${item[2]=='2'}">中</c:if>
			<c:if test="${item[2]=='3'}">高</c:if>
			<c:if test="${item[2]=='4'}">极高</c:if> 
      </c:if>
      <c:if test="${ item[5]=='10' }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='0'}">低</c:when>
	      	<c:when test="${item[2]=='1'}">中</c:when>
	      	<c:when test="${item[2]=='2'}">高</c:when>
	      	<c:when test="${item[2]=='3'}">极高</c:when>
	      </c:choose>
      </c:if>
      
<%--适配管理--%>
      <c:if test="${ item[5]=='1'  }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">紧急</c:when>
	      	<c:when test="${item[2]=='2'}">一般</c:when>
	      </c:choose>
      </c:if>

<%--测试问题管理--%>
      <c:if test="${ item[5]=='11' }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">紧急</c:when>
	      	<c:when test="${item[2]=='2'}">一般</c:when>
	      </c:choose>
      </c:if>
<%--日常问题--%>
      <c:if test="${ item[5]=='3' }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">一般</c:when>
	      	<c:when test="${item[2]=='2'}">紧急</c:when>
	      </c:choose>
      </c:if>
<%--终端申请--%>
     <c:if test="${ item[5]=='12'  }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">一般</c:when>
	      	<c:when test="${item[2]=='2'}">紧急</c:when>
	      </c:choose>
      </c:if>
<%--终端归还--%>
      <c:if test="${ item[5]=='13' }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">紧急</c:when>
	      	<c:when test="${item[2]=='2'}">一般</c:when>
	      </c:choose>
      </c:if>
<%--终端寄送--%>
      <c:if test="${ item[5]=='6' }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">紧急</c:when>
	      	<c:when test="${item[2]=='2'}">一般</c:when>
	      </c:choose>
      </c:if>
      
      
<%--现网操作工单--%>
      <c:if test="${ item[5]=='15' }"  >
	      <c:choose>
	      	<%--<c:when test="${item[2]=='3'}">高</c:when>
	      	<c:when test="${item[2]=='2'}">中</c:when>
	      	<c:when test="${item[2]=='1'}">低</c:when>--%>
	      	<c:when test="${item[2]=='1'}">紧急</c:when>
	      	<c:when test="${item[2]=='2'}">一般</c:when>
	      </c:choose>
      </c:if>
      <%--运营操作工单--%>
      <c:if test="${ item[5]=='18' }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">紧急</c:when>
	      	<c:when test="${item[2]=='2'}">一般</c:when>
	      </c:choose>
      </c:if>
      
<%--系统通知--%>
      <c:if test="${ item[5]=='3000' }"  >
	      <c:choose>
	      	<%--<c:when test="${item[2]=='3'}">高</c:when>
	      	<c:when test="${item[2]=='2'}">中</c:when>
	      	<c:when test="${item[2]=='1'}">低</c:when>--%>
	      	<c:when test="${item[2]=='2'}">紧急</c:when>
	      	<c:when test="${item[2]=='1'}">一般</c:when>
	      </c:choose>
      </c:if>
      
      <%--数据工单--%>
      <c:if test="${ item[5]=='16' }"  >
	      <c:choose>
	      	<%--<c:when test="${item[2]=='3'}">高</c:when>
	      	<c:when test="${item[2]=='2'}">中</c:when>
	      	<c:when test="${item[2]=='1'}">低</c:when>--%>
	      	<c:when test="${item[2]=='1'}">一般</c:when>
	      	<c:when test="${item[2]=='2'}">紧急</c:when>
	      </c:choose>
      </c:if>
      
      </td>
      
<%--      发起人--%>

<%--
      <c:if test="${item[5]!='13062108000000000002' && item[5]!='10'}">
      	<td title="${item[11]}"><script>document.write(ellipsisStr2('${item[11]}'));</script></td>
      </c:if>
      <c:if test="${item[5]=='13062108000000000002' || item[5]=='10'}">
      	<td title="${item[3]}">${item[3]}</td>
      </c:if>
      --%>
      <%--研发的发起人（除需求审批外） --%>
      <c:choose>
      <c:when test="${item[5]=='13062108000000000001' || item[5]=='13062108000000000003' || item[5]=='13062108000000000004' }">
      	<td title="${item[11]}"><script>document.write(ellipsisStr2('${item[11]}'));</script></td>
      </c:when>
      <c:otherwise>
	<%--包括13062108000000000002--%>
      	<td title="${item[3]}">${item[3]}</td>
      </c:otherwise>
      </c:choose>
      
      
      
<%--      发起组织--%>
      <%-- <td title="${item[4]}"><c:choose><c:when test="${item[4] != null && fn:length(item[4]) > 9}">${fn:substring(item[4], 0, 9)}...</c:when><c:otherwise>${item[4]}</c:otherwise></c:choose></td> --%>
       <c:choose>
	      <c:when test="${item[5]=='13062108000000000001' || item[5]=='13062108000000000003' || item[5]=='13062108000000000004' }">
	      	<td title="${item[14]}">
	      	<%--<c:choose>
	      	<c:when test="${item[14] != null && fn:length(item[14]) > 9}">${fn:substring(item[14], 0, 9)}...</c:when><c:otherwise>${item[14]}</c:otherwise>
	      	</c:choose>--%>
	      	 ${item[14]}
	      	</td>
	      </c:when>
	      <c:otherwise>
		<%--包括13062108000000000002--%>
	      	<td title="${item[4]}">
	      	<%--<c:choose>
	      	<c:when test="${item[4] != null && fn:length(item[4]) > 9}">${fn:substring(item[4], 0, 9)}...</c:when><c:otherwise>${item[4]}</c:otherwise>
	      	</c:choose>--%>
	      	 ${item[4]} 
	      	</td>
	      </c:otherwise>
      </c:choose>
      
      
      
<%--      待办产生时间--%>
      <td>${item[9]}</td>
      
<%--      处理时限     超时状态      --%>
    <c:if test="${item[7]==0||item[7]==null}">
		<td>无时限</td>
        <td>未超时</td>
	</c:if>
	<c:if test="${item[7]!=0&&item[7]!=null}">
		<td> ${item[7]}天</td>
		<c:if test="${item[10]=='N'}">
			<td>未超时</td> 
		</c:if>
		<c:if test="${item[10]=='Y'}">
			<td style="color: red;">已超时</td> 
		</c:if>
	</c:if>
	
	<%--操作--%>
	<td>
	<c:choose>
	<c:when test="${flag==5}">
		<c:if test="${item[5]=='13062108000000000001'}">
		            	<table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td> <a href="javascript:void(0);" onclick="doApprove('<%=basePath%>core/portal/toPreApproveRequireDetailApprove?createWorkorderId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='13062108000000000002'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>core/portal/toPreApproveRequireDetailWork?launchWorkorderId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='13062108000000000003'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td> <a href="javascript:void(0);" onclick="doApprove('<%=basePath%>core/portal/toPreApproveRequireDevelopDetail?developWorkorderId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='13062108000000000004'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>core/portal/toPreAppversionDetailView?orderId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='10'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>workordermanager/failureOrderFlow/showDesktopOrderDetail?flowType=10&adapterWorkorderId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
             </c:when>
             
             <c:otherwise>
		               <c:if test="${flag==1}">
		               	<table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td> <a href="javascript:void(0);" onclick="doApprove('<%=basePath%>core/portal/toPreApproveRequireDetailApprove?createWorkorderId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		             
		            </c:if>
		            <c:if test="${flag==2}">
		            	<table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>core/portal/toPreApproveRequireDetailWork?launchWorkorderId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${flag==3}">
		            	<table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td> <a href="javascript:void(0);" onclick="doApprove('<%=basePath%>core/portal/toPreApproveRequireDevelopDetail?developWorkorderId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${flag==4}">
		            	<table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>core/portal/toPreAppversionDetailView?orderId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		              
		            </c:if>
		            <c:if test="${flag==10}">
		            	<table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>workordermanager/failureOrderFlow/showDesktopOrderDetail?flowType=10&adapterWorkorderId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		              
		            </c:if>        
               </c:otherwise>
           </c:choose>
           
<%--           			适配管理的新增的六个流程--%>
		            <c:if test="${item[5]=='1'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>adapter/adapterProjectManage/adapterManage/toPreApproveAdapterProDetail?adapterWorkorderId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='11'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>adapter/preapprovetestproblem/dbtestProblemDetail?id=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='3'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>dailyManage/preApproveDailyDevelop/queryApproveDailyDevelopItemPage?adapterWorkorderId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='12'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>adapter/stockoutFlowManage/showMyStockoutDetail?insactorId=${item[12]}&flowType=12');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='13'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>adapterManage/inDatabaseTerminalController/doWorksheet?operate=work&view=true&flag=1&worksheetId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='6'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>adapterManage/supplementTerminalController/toEditWorksheet?operate=work&view=true&worksheetId=${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            
		            <c:if test="${item[5]=='15'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>worksheetManage/worksheetManageFlow/dbWorkSheetDetail?workorderId=${item[0]}&insactorId=${item[12]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='18'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>operate/flow/dbWorkSheetDetail?workorderId=${item[0]}&insactorId=${item[12]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='3000'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
		                    <tr>
		                      <%-- <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
		                      <td>
		                      	<c:choose>
					       			<c:when test="${not empty item[8] and item[8] eq 'manager' }">
					       				<a href="javascript:void(0);" onclick="javascript:toPre('${item[0]}');" style="font-weight:bold; color:#3c6d97;">审批</a>
					       			</c:when>
					       			<c:when test="${not empty item[8] and item[8] ne 'manager' }">
					       				<a href="javascript:void(0);" onclick="javascript:editNotice('${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a>
					       			</c:when>
					       		</c:choose>
		                      </td> --%>		                     
		                      	<c:choose>
					       			<c:when test="${not empty item[8] and item[8] eq 'manager' }">
					       			    <td><img src="/SRMC/rmpb/images/tab_tb12.png"/></td>
		                                <td>
					       				    <a href="javascript:void(0);" onclick="javascript:toPre('${item[0]}');" style="font-weight:bold; color:#3c6d97;">审批</a>
					       			    </td>
					       			</c:when>
					       			<c:when test="${not empty item[8] and item[8] ne 'manager' }">
					       				<td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
		                                <td>
					       				   <a href="javascript:void(0);" onclick="javascript:editNotice('${item[0]}');" style="font-weight:bold; color:#3c6d97;">办理</a>
					       			    </td>
					       			</c:when>
					       		</c:choose>		                     
		                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='16'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doApprove('<%=basePath%>extractData/dataSupportWorkOrder/dbExtractDataDetail?workorderId=${item[0]}&insactorId=${item[12]}');" style="font-weight:bold; color:#3c6d97;">办理</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            
		            
      </td>	   
    </tr>
    </c:forEach>
  </table>
  <jsp:include flush="true" page="/public/include/navigate2.jsp"></jsp:include>
  </div>
  </c:if>
  
  
  
  
  
  
<%--  待阅的页面--%>
  
  <c:if test="${type=='2'}">
  <div id="con_one_2">
     <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr>
      <th width="10%">事项类型</th>
      <th width="36%">事项名称</th>
      <th width="7%">紧急程度</th>
      <th width="7%">发起人</th>
      <th width="7%">抄送人</th>
      <th width="12%">发起组织</th>
      <th width="14%">产生时间</th><!--

      <th width="9%">
      <span class="home_jian01">
        <p class="home_jian01_01"><a href="#"><img src="/SRMC/rmpb/images/home_j03.png" width="11" height="7" /></a></p>
        <p class="home_jian01_01"><a href="#"><img src="/SRMC/rmpb/images/home_j00.png" width="11" height="7" /></a></p>
      </span>处理时限</th>
      <th width="9%">
      <span class="home_jian01">
        <p class="home_jian01_01"><a href="#"><img src="/SRMC/rmpb/images/home_j03.png" width="11" height="7" /></a></p>
        <p class="home_jian01_01"><a href="#"><img src="/SRMC/rmpb/images/home_j00.png" width="11" height="7" /></a></p>
      </span>超时状态</th>
     --><th width="7%">操作</th>
    </tr>
    <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
 	<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if> >
 	
<%-- 	事项类型--%>
      <td>
      	 <c:choose>
         <c:when test="${flag==5}">
		        <c:if test="${item[5]=='13062108000000000001'}">
				需求初审
				</c:if>
				<c:if test="${item[5]=='13062108000000000002'}">
				需求审批
				</c:if>
				<c:if test="${item[5]=='13062108000000000003'}">
				研发管理
				</c:if>
				<c:if test="${item[5]=='13062108000000000004'}">
				版本上线
				</c:if>
				<c:if test="${item[5]=='10'}">
				故障管理
				</c:if>				
         </c:when>
         <c:otherwise>
               <c:if test="${flag==1}">
				需求初审
				</c:if>
				<c:if test="${flag==2}">
				需求审批
				</c:if>
				<c:if test="${flag==3}">
				研发管理
				</c:if>
				<c:if test="${flag==4}">
				版本上线
				</c:if>
				<c:if test="${flag==10}">
				故障管理
				</c:if>
         </c:otherwise>
      
      </c:choose>
      
			
<%--				适配管理新增加六个流程--%>
				<c:if test="${item[5]=='1'}">
				故障管理
				</c:if>
				<c:if test="${item[5]=='11'}">
				测试问题
				</c:if>
				<c:if test="${item[5]=='3'}">
				日常问题
				</c:if>
				<c:if test="${item[5]=='12'}">
				终端申请
				</c:if>
				<c:if test="${item[5]=='13'}">
				终端归还
				</c:if>
				<c:if test="${item[5]=='6'}">
				终端寄送
				</c:if>

	<%--现网操作工单--%>
				<c:if test="${item[5]=='15'}">
				现网工单
				</c:if>
				<c:if test="${item[5]=='18'}">
				运营工单
				</c:if>
				<c:if test="${item[5]=='16'}">
				数据工单
				</c:if>
				
      </td>
      
      
<%--      事项名称--%>
      <td  style="word-break:break-all;text-align:left;padding:3px 8px 3px 8px;line-height:19px;">
      		<c:if test="${item[5]=='13062108000000000001'}">
         		<a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doRead('<%=basePath%>core/portal/toPreReadRequireDetailFlow?createWorkorderId=${item[0]}&insactorId=${item[12] }')">${item[1]}</a>
         	</c:if>
         	<c:if test="${item[5]=='13062108000000000002'}">
         		<a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doRead('<%=basePath%>core/portal/toPreReadLaunchRequireDetailFlow?launchWorkorderId=${item[0]}&insactorId=${item[12]}')">${item[1]}</a>
         	</c:if>
         	<c:if test="${item[5]=='13062108000000000003'}">
         		<a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doRead('<%=basePath%>core/portal/toPreReadRequireDevelopDetailFlow?developWorkorderId=${item[0]}&insactorId=${item[12]}')">${item[1]}</a>
         	</c:if>
         	<c:if test="${item[5]=='13062108000000000004'}">
         		<a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doRead('<%=basePath%>core/portal/toPreReadversionDetailViewFlow?orderId=${item[0]}&insactorId=${item[12]}')">${item[1]}</a>
         	</c:if>
         	<c:if test="${item[5]=='10'}">
         		<a rel="tooltip" title="${item[1]}" href="javascript:void(0);" onclick="doRead('<%=basePath%>workordermanager/failureOrderFlow/showMyDesktopOrderRead?flowType=10&adapterWorkorderId=${item[0]}')">${item[1]}</a>
         	</c:if>
         	
<%--		       适配项目   测试问题 流程--%>
		       <c:if test="${item[5]=='11'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);"    onclick="doRead('<%=basePath%>adapter/prereadtestproblem/dytestProblemDetail?id=${item[0]}&dyId=${item[12]}');"   >${item[1]}</a>
		       </c:if>


<%--		       现网操作工单--%>
		       <c:if test="${item[5]=='15'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);"    onclick="doRead('<%=basePath%>worksheetManage/worksheetManageFlow/seeWorkSheetDetail?workorderId=${item[0]}&insactorId=${item[12]}');"   >${item[1]}</a>
		       </c:if>
		       
		       <%--		       运营操作工单--%>
		       <c:if test="${item[5]=='18'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);"    onclick="doRead('<%=basePath%>operate/flow/seeWorkSheetDetail?workorderId=${item[0]}&insactorId=${item[12]}');"   >${item[1]}</a>
		       </c:if>
		       
		       <%--		     数据工单--%>
		       <c:if test="${item[5]=='16'}">
		             <a rel="tooltip" title="${item[1]}" href="javascript:void(0);"    onclick="doRead('<%=basePath%>extractData/dataSupportWorkOrder/seeExtractDataDetail?workorderId=${item[0]}&insactorId=${item[12]}');"   >${item[1]}</a>
		       </c:if>
		       
		       
      </td>
      
<%--      紧急程度--%>
      <td>
      
      <%--
      	<c:if test="${item[5]!='10'}" var="rs">
			<c:if test="${item[2]=='1'}">
			低
			</c:if>
			<c:if test="${item[2]=='2'}">
			中
			</c:if>
			<c:if test="${item[2]=='3'}">
			高
			</c:if>
			<c:if test="${item[2]=='4'}">
			极高
			</c:if> 
		</c:if>
		<c:if test="${!rs}">
			<c:if test="${item[2]=='0'}">
			低
			</c:if>
			<c:if test="${item[2]=='1'}">
			中
			</c:if>
			<c:if test="${item[2]=='2'}">
			高
			</c:if>
			<c:if test="${item[2]=='3'}">
			极高
			</c:if> 
		</c:if>
      --%>
      
<%--如果是 之前四个流程--%>
      <c:if test="${item[5]=='13062108000000000001' || item[5]=='13062108000000000002' || item[5]=='13062108000000000003' || item[5]=='13062108000000000004' }"  >
			<c:if test="${item[2]=='1'}">低</c:if>
			<c:if test="${item[2]=='2'}">中</c:if>
			<c:if test="${item[2]=='3'}">高</c:if>
			<c:if test="${item[2]=='4'}">极高</c:if> 
      </c:if>
      <c:if test="${ item[5]=='10' }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='0'}">低</c:when>
	      	<c:when test="${item[2]=='1'}">中</c:when>
	      	<c:when test="${item[2]=='2'}">高</c:when>
	      	<c:when test="${item[2]=='3'}">极高</c:when>
	      </c:choose>
      </c:if>
      

<%--适配管理--%>
      <c:if test="${ item[5]=='1'  }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">紧急</c:when>
	      	<c:when test="${item[2]=='2'}">一般</c:when>
	      </c:choose>
      </c:if>

<%--测试问题管理--%>
      <c:if test="${ item[5]=='11' }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">紧急</c:when>
	      	<c:when test="${item[2]=='2'}">一般</c:when>
	      </c:choose>
      </c:if>
<%--日常问题--%>
      <c:if test="${ item[5]=='3' }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">一般</c:when>
	      	<c:when test="${item[2]=='2'}">紧急</c:when>
	      </c:choose>
      </c:if>
<%--终端申请--%>
     <c:if test="${ item[5]=='12'  }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">一般</c:when>
	      	<c:when test="${item[2]=='2'}">紧急</c:when>
	      </c:choose>
      </c:if>
<%--终端归还--%>
      <c:if test="${ item[5]=='13' }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">紧急</c:when>
	      	<c:when test="${item[2]=='2'}">一般</c:when>
	      </c:choose>
      </c:if>
<%--终端寄送--%>
      <c:if test="${ item[5]=='6' }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">紧急</c:when>
	      	<c:when test="${item[2]=='2'}">一般</c:when>
	      </c:choose>
      </c:if>
      
<%--现网操作工单--%>
      <c:if test="${ item[5]=='15' }"  >
	      <c:choose>
	      	<%--<c:when test="${item[2]=='3'}">高</c:when>
	      	<c:when test="${item[2]=='2'}">中</c:when>
	      	<c:when test="${item[2]=='1'}">低</c:when>--%>
	      	<c:when test="${item[2]=='1'}">紧急</c:when>
	      	<c:when test="${item[2]=='2'}">一般</c:when>
	      </c:choose>
      </c:if>
      <%--运营操作工单--%>
      <c:if test="${ item[5]=='18' }"  >
	      <c:choose>
	      	<c:when test="${item[2]=='1'}">紧急</c:when>
	      	<c:when test="${item[2]=='2'}">一般</c:when>
	      </c:choose>
      </c:if>
      <%--数据工单--%>
      <c:if test="${ item[5]=='16' }"  >
	      <c:choose>
	      	<%--<c:when test="${item[2]=='3'}">高</c:when>
	      	<c:when test="${item[2]=='2'}">中</c:when>
	      	<c:when test="${item[2]=='1'}">低</c:when>--%>
	      	<c:when test="${item[2]=='1'}">一般</c:when>
	      	<c:when test="${item[2]=='2'}">紧急</c:when>
	      </c:choose>
      </c:if>
      
      
      
      </td>
      
      
      
      
<%--      发起人--%>
      <td>${item[3]}</td>
      
<%--      抄送人--%>
      <td>${item[11]}</td>
      
<%--      发起组织--%>
      <td>${item[4]}</td>
      
<%--      产生时间--%>
      <td>${item[9]}</td>
      
      <!--
      <td>1天</td>
      <td>未超时</td>
      -->
      
<%--      操作--%>
      <td>
      <c:choose>
           <c:when test="${flag==5}">
		            <c:if test="${item[5]=='13062108000000000001'}">
						<table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doRead('<%=basePath%>core/portal/toPreReadRequireDetailFlow?createWorkorderId=${item[0]}&insactorId=${item[12]}')" style="font-weight:bold; color:#3c6d97;padding-left:3px">查看</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='13062108000000000002'}">
		            <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doRead('<%=basePath%>core/portal/toPreReadLaunchRequireDetailFlow?launchWorkorderId=${item[0]}&insactorId=${item[12]}')" style="font-weight:bold; color:#3c6d97;padding-left:3px">查看</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='13062108000000000003'}">
		             <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doRead('<%=basePath%>core/portal/toPreReadRequireDevelopDetailFlow?developWorkorderId=${item[0]}&insactorId=${item[12]}')" style="font-weight:bold; color:#3c6d97;padding-left:3px">查看</a></td>
			                    </tr>
			            </table> 
		            </c:if>
		            <c:if test="${item[5]=='13062108000000000004'}">
		            <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doRead('<%=basePath%>core/portal/toPreReadversionDetailViewFlow?orderId=${item[0]}&insactorId=${item[12]}')" style="font-weight:bold; color:#3c6d97;padding-left:3px">查看</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            <c:if test="${item[5]=='10'}">
		            <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doRead('<%=basePath%>workordermanager/failureOrderFlow/showMyDesktopOrderRead?flowType=10&adapterWorkorderId=${item[0]}')" style="font-weight:bold; color:#3c6d97;padding-left:3px">查看</a></td>
			                    </tr>
			            </table>
		            </c:if>
           </c:when>
           <c:otherwise>
              <c:if test="${flag==1}">
                   <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doRead('<%=basePath%>core/portal/toPreReadRequireDetailFlow?createWorkorderId=${item[0]}&insactorId=${item[12]}')" style="font-weight:bold; color:#3c6d97;">查看</a></td>
			                    </tr>
			            </table>     
            </c:if>
            <c:if test="${flag==2}">
              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doRead('<%=basePath%>core/portal/toPreReadLaunchRequireDetailFlow?launchWorkorderId=${item[0]}&insactorId=${item[12]}')" style="font-weight:bold; color:#3c6d97;">查看</a></td>
			                    </tr>
			            </table>
            </c:if>
            <c:if test="${flag==3}">
               <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doRead('<%=basePath%>core/portal/toPreReadRequireDevelopDetailFlow?developWorkorderId=${item[0]}&insactorId=${item[12]}')" style="font-weight:bold; color:#3c6d97;">查看</a></td>
			                    </tr>
			            </table>
            </c:if>
            <c:if test="${flag==4}">
                    <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doRead('<%=basePath%>core/portal/toPreReadversionDetailViewFlow?orderId=${item[0]}&insactorId=${item[12]}')" style="font-weight:bold; color:#3c6d97;">查看</a></td>
			                    </tr>
			            </table>
            </c:if>
            <c:if test="${flag==10}">
                    <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);" onclick="doRead('<%=basePath%>workordermanager/failureOrderFlow/showMyDesktopOrderRead?flowType=10&adapterWorkorderId=${item[0]}')" style="font-weight:bold; color:#3c6d97;">查看</a></td>
			                    </tr>
			            </table>
            </c:if>
           </c:otherwise>
      
      </c:choose>
       
       
<%--       适配项目 的 测试问题流程--%>
		            <c:if test="${item[5]=='11'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);"    onclick="doRead('<%=basePath%>adapter/prereadtestproblem/dytestProblemDetail?id=${item[0]}&dyId=${item[12]}');"     style="font-weight:bold; color:#3c6d97;">查看</a></td>
			                    </tr>
			            </table>
		            </c:if>
       
<%--       现网操作工单--%>
		            <c:if test="${item[5]=='15'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);"    onclick="doRead('<%=basePath%>worksheetManage/worksheetManageFlow/seeWorkSheetDetail?workorderId=${item[0]}&insactorId=${item[12]}');"     style="font-weight:bold; color:#3c6d97;">查看</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            
		            <%-- 运营操作工单--%>
		            <c:if test="${item[5]=='18'}">
		               <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);"    onclick="doRead('<%=basePath%>operate/flow/seeWorkSheetDetail?workorderId=${item[0]}&insactorId=${item[12]}');"     style="font-weight:bold; color:#3c6d97;">查看</a></td>
			                    </tr>
			            </table>
		            </c:if>
		            
		            <%-- 数据工单--%>
		            <c:if test="${item[5]=='16'}">
		              <table class="jz_a01" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><img src="/SRMC/rmpb/images/tab_tb06.png"/></td>
			                      <td><a href="javascript:void(0);"    onclick="doRead('<%=basePath%>extractData/dataSupportWorkOrder/seeExtractDataDetail?workorderId=${item[0]}&insactorId=${item[12]}');"     style="font-weight:bold; color:#3c6d97;">查看</a></td>
			                    </tr>
			            </table>
		            </c:if>
       
       </td>
    </tr>
    </c:forEach>
  </table>    
  <jsp:include flush="true" page="/public/include/navigate2.jsp"></jsp:include>
  </div>
  </c:if>
  
  <%-- 
  <input type="hidden" id="noticeListSize" name="noticeListSize" value="${fn:length(noticeList)}"/>
  <input type="hidden" id="indexTemp" name="indexTemp" value=""/>
  <input type="hidden" id="sysNoticeIdTemp" name="sysNoticeIdTemp" value=""/>
  <div id="drag" style="display: none">
	    <div class="title">
	        <!-- <h2>系统通知</h2> -->
	        <div>
	            <a style="display:none;" class="min" href="javascript:;" title="最小化"></a>
	            <a style="display:none;" class="max" href="javascript:;" title="最大化"></a>
	            <a style="display:none;" class="revert" href="javascript:;" title="还原"></a>
	            <a class="close" href="javascript:;" title="关闭"></a>
	        </div>
	    </div>
	    <div class="resizeL"></div>
	    <div class="resizeT"></div>
	    <div class="resizeR"></div>
	    <div class="resizeB"></div>
	    <div class="resizeLT"></div>
	    <div class="resizeTR"></div>
	    <div class="resizeBR"></div>
	    <div class="resizeLB"></div>
	    <div class="content">
		<c:forEach items="${noticeList}" var="sysNotice" varStatus="i">		    
		    <input type="hidden" id="sysNoticeIdDiv${i.index}" value="${sysNotice.sysNoticeId}"/>
			<div id="noticeId${i.index}" <c:if test="${i.index ne 0}">style="display:none;"</c:if>>
				<div class="window_div_new" style="width:96.5%;padding-left:10px;padding-right:10px;margin-top:21px;padding-top:0px;overflow-y:auto;max-height:360px;">
					<div style="min-height:280px;">
						<div class="pn" style="text-align:center;font-size:16px;">${sysNotice.title}</div>
						<div style="text-align:center;padding:5px;">
							发布人：${sysNotice.publishPeople}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布时间：
							<fmt:formatDate value='${sysNotice.publishTime}' pattern='yyyy年M月d日' />
						</div>
						<div class="textareafujian">
							    <div style="min-height:150px;width:99%;resize: none;backgroundAlpha=0;font-family:微软雅黑;line-height:150%;border:0px;background-color:#E6F3FB;padding-left:0%;padding-right:0%;padding-bottom: 8px">${sysNotice.notice}</div>
								<table width="100%" border="0" style="overflow-y:auto;">
								    <c:if test="${fn:length(sysNotice.attaList) > 0}">
										<tr>
											<td style="font-family:微软雅黑;font-size:12px">附件列表：</td>
										</tr>
									</c:if>
									<c:forEach items="${sysNotice.attaList}" var="fileTemp">
										<tr><td><img src="/SRMC/rmpb/images/attechment.png" align="absmiddle">&nbsp;&nbsp;<a class="fujian_link"	href="<%=basePath%>sys/noticeCommon/downloadSysNoticeAttachment?id=${fileTemp.id}">${fileTemp.attachmentName}&nbsp;(<c:choose><c:when test="${fileTemp.fileSize=='0.00'}"><0.01M</c:when><c:otherwise>${fileTemp.fileSize}M</c:otherwise></c:choose>)</td>
										</tr>
									</c:forEach>
								</table>			
						</div>	
					</div>								
					<div  style="text-align:center;margin-bottom:15px;">
						<input name="input" type="button"  class="gl_cx_bnt03" value="关 闭" onclick="doCloseNotice('${i.index}','${sysNotice.sysNoticeId}');" />
					</div>
			 </div>
          </div>
		</c:forEach>
	  </div>
	  --%>
</div>
<div class="tang_a01" id="light" style="display:none;">
    <div class="tang_xx"><a href="javascript:void(0)" onClick="hide('light')"><img src="/SRMC/rmpb/css/tip/images/tang_xx.png"/></a></div>
    <div class="tang_a01_top">友情提醒</div>
    <div class="tang_font01">&nbsp;&nbsp;&nbsp;&nbsp;尊敬的用户，您目前使用的浏览器不完全兼容本系统，建议您使用<b>IE8及以上版本浏览器或谷歌浏览器</b>访问以获取最佳的体验。我们正在不断努力改进和优化，后续将会适配更多浏览器，感谢您的理解和支持！</div>
    <div class="tang_font02">
        <input  class="tang_gg01" name="" type="button" value="" onClick="hide('light')"><input type="checkbox" name="isIeTip" id="isIeTip" value="1" <c:if test="${user.isIeTip=='1'}">checked</c:if> onclick="changeStatus();"/>不再提醒
    </div>
</div>
</form>
<script type="text/javascript">
    jQuery(document).ready(function() {
        var type = jQuery("#type").val();
        for(i=1;i<=2;i++){
            var menu = jQuery("#one" + i);
            if(i==type){
                menu.attr("class", "hover");
            }else{
                menu.attr("class", "");
            }
        }        
                
        parent.window["ori_page"] = "home";
    });

function setflag(flag){
	jQuery("#flag").val(flag);
	document.forms[0].submit();
}
			
function setTab(cursel){
	jQuery("#type").val(cursel);
	//jQuery("#flag").val(cursel);
	document.forms[0].submit();
}

//待阅事项
function doRead(url){
	location.href = url + "&indexLocation=2";
	 
}
//待办事项
function doApprove(url){
	location.href = url + "&indexLocation=1";
}
</script>
   </body>
</html>
