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
		<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		
		<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
		
		<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
		<link href="/SRMC/rmpb/css/date_input.css" type="text/css" rel="stylesheet" />
		
		
		<script type="text/javascript">
			function clickOptions(i, n, name){
				var li = $('options_' + name).getElementsByTagName('li');
				
				$('selected_' + name).className='open';
				$('selected_' + name).id='';
				li[n].id='selected_' + name;
				li[n].className='open_hover';
				$('select_' + name).removeChild($('select_info_' + name));
			
				select_info = document.createElement('div');
				select_info.id = 'select_info_' + name;
				select_info.className='tag_select';
				select_info.style.cursor='pointer';
				$('options_' + name).parentNode.insertBefore(select_info,$('options_' + name));
			
				mouseSelects(name);
			
				$('select_info_' + name).appendChild(document.createTextNode(li[n].innerHTML));
				$( 'options_' + name ).style.display = 'none' ;
				$( 'select_info_' + name ).className = 'tag_select';
				selects[i].options[n].selected = 'selected';
				
				if(name=="projectId"){
					searchVersionByProject();
				}
			}
			
			function clickOptionsSepecify(i, n, name){
				var li = $('options_' + name).getElementsByTagName('li');
				
				$('selected_' + name).className='open';
				$('selected_' + name).id='';
				li[n].id='selected_' + name;
				li[n].className='open_hover';
				$('select_' + name).removeChild($('select_info_' + name));
			
				select_info = document.createElement('div');
				select_info.id = 'select_info_' + name;
				select_info.className='tag_select';
				select_info.style.cursor='pointer';
				$('options_' + name).parentNode.insertBefore(select_info,$('options_' + name));
			
				mouseSelectsSepecify(name);
			
				$('select_info_' + name).appendChild(document.createTextNode(li[n].innerHTML));
				$( 'options_' + name ).style.display = 'none' ;
				$( 'select_info_' + name ).className = 'tag_select';
				selectsSepecify[i].options[n].selected = 'selected';
			}
			
			jQuery.extend(DateInput.DEFAULT_OPTS, { 
				month_names: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"], 
				short_month_names: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"], 
				short_day_names: ["一", "二", "三", "四", "五", "六", "日"],
				dateToString: function(date) {
					var month = (date.getMonth() + 1).toString();
					var dom = date.getDate().toString();
					if (month.length == 1) month = "0" + month;
					if (dom.length == 1) dom = "0" + dom;
					return date.getFullYear() + "-" + month + "-" + dom;
				}
			});
			
		jQuery(document).ready(function() {
			//加载按钮样式
			var type = jQuery("#type").val();
			for(i=1;i<=2;i++){
				var menu = jQuery("#one" + i);
				if(i==type){
					menu.attr("class", "hover");
				}else{
					menu.attr("class", "");
				}
			}
			//动态加载进度条
			jQuery(".bar_2").each(function () {
			// body...
			/****
				var $bar=jQuery(this),
				level=$bar.attr("level");
				$bar.animate({"width":75*level},1000);
			***/
		
				var $bar=$(this),
				level=$bar.attr("level")+"";
				switch(level){
					case "0":{
						$bar.animate({"width":0},1000);
					break;
					}
					case "1":{
					$bar.animate({"width":13},1000);
					break;
					}
					case "2":{
					$bar.animate({"width":94},1000);
					break;
					}
					case "3":{
					$bar.animate({"width":165},1000);
					break;
					}
					case "4":{
					$bar.animate({"width":237},1000);
					break;
					}
					case "5":{
					$bar.animate({"width":311},1000);
					break;
					}
					case "6":{
					$bar.animate({"width":393},1000);
					break;
					}
					case "7":{
					$bar.animate({"width":474},1000);
					break;
					}
					case "8":{
					$bar.animate({"width":550},1000);
					break;
					}
					case "9":{
					$bar.animate({"width":615},1000);
					break;
					}
				}
			})
		});

		function setTab(cursel){
			jQuery("#type").val(cursel);
			document.forms[0].submit();
		}
		
		//需求名称的省略
		function ellipsisStr(str){
			 	var newStr = '';
			 	var array = new Array(); 
			 	array = str.split("");
			 	if(array.length > 20){
			 		newStr = str.substr(0, 20) + "..."; 
			 	}else{
			 		newStr = str;
			 	}
				return newStr;
         }
		
		</script>

<script language="JavaScript" type="text/JavaScript">
	/**

	var oPopup = window.createPopup();
	var sPop = null;
	
	function showPopupWin(){
		var tPopWait=250; //这里设定鼠标停留多长时间出现提示（系统alt和title延迟时间为500毫秒）
		var o=event.srcElement;
		if(o.alt!=null && o.alt!=""){o.sPop=o.alt;o.alt="";}
		if(o.title!=null && o.title!=""){o.sPop=o.title;o.title="";}

		if(sPop!=o.sPop) sPop=o.sPop;
		if(sPop==null || sPop=="")
			oPopup.hide(); //Hidden
		else
			setTimeout("showIt("+event.x+","+event.y+")",tPopWait);
	}
	
	function showIt(MouseX,MouseY){
		if(sPop){
			var foreColor="#000000"; //字符颜色
			var backColor="#e6f3fb"; //背景色
			var borderColor="#0eb6f0"; //边框颜色

			var maxWidth=1000; //这里设定提示信息的最大宽度（系统alt和title的宽度大约为295）
			var content1 ="<table border='0' cellspacing='1' cellpadding='3' bgcolor='" + borderColor + "'><td bgcolor='" + backColor + "' style='color:" + foreColor + "; font-size:12px; font-family: Verdana; line-height:120%' nowrap>" + sPop + "</td></table>";
			var content2 ="<table border='0' cellspacing='1' cellpadding='3' bgcolor='" + borderColor + "' width='" + maxWidth + "'><td bgcolor='" + backColor + "' style='color:" + foreColor + "; font-size:12px; font-family: Verdana; line-height:120%'>" + sPop + "</td></table>";
			oPopup.document.body.innerHTML=content1;
			oPopup.show(); //初始化
			var left = MouseX + 0;
			var top = MouseY + 20;
			var width = oPopup.document.body.scrollWidth;
			var height = oPopup.document.body.scrollHeight;

			if(width>maxWidth){
				oPopup.document.body.innerHTML=content2;
				width = oPopup.document.body.scrollWidth;
				height = oPopup.document.body.scrollHeight;
			}
			oPopup.show(left, top, width, height, document.body);
		}
	}
	
	document.onmouseover = showPopupWin;
	
	*/
</script>

	</head>

<body class="bg_c_g">
	<div class="gl_m_r_nav">
		当前位置&nbsp;:&nbsp;研发流程管理
	</div>
       
	<div class="gl_import_m">
	<form action="<%=basePath%>core/portal/yan_index" method="post" id="pageForm">
		<input type="hidden" id="type" name="type" value="${type}"/>
        
        <div class="yan_h_bt01">
		  <div class="yan_nv_01">
		      <p><font color="#666666">您好</font>，<font style="font-weight:normal;">尊敬的</font><span class="orange_01">${sysUser.userName}</span>！　<span style="color:#1a739b;"><span style=" font-weight:normal;">您所在的项目是：</span>
		  		<c:forEach items="${listProject}" var="item" varStatus="i">
        		<c:if test="${i.index<NumSize}">${item[0]}、</c:if>
        		
        		<c:if test="${i.index==listProjectSize}">${item[0]}</c:if>
        	</c:forEach>
		  </span>
		   </p>
		  </div>
		</div>

        
     <!-- 工具按钮DIV start -->
     <div class="yan_h_tab01">
  	
  	
       <span class="yan_h_k01" style="_width:260px;">
         <span class="yan_h_font01">常用工具</span>
         <ul class="yan_h_kul01">
           <li>
             <div class="home_t_bnt_pic"><a href="<%=basePath%>developFlowManage/requirementItem/newRequirement/toCreateRequirement"><img src="/SRMC/rmpb/images/yan_bnt01.png" width="57" height="57" /></a></div>
             <div class="home_t_bnt_font">创建需求</div>
           </li>
           <li>
             <div class="home_t_bnt_pic"><a href="<%=basePath%>developFlowManage/requirementItem/newRequirement/queryMyCreateRequirementItemPage"><img src="/SRMC/rmpb/images/yan_bnt02.png" width="57" height="57" /></a></div>
             <div class="home_t_bnt_font">我创建的需求</div>
           </li>
           <li>
             <div class="home_t_bnt_pic"><a href="<%=basePath%>developFlowManage/requirementItem/requirementManageCommon/queryRequirementPoolItemPageCommon"><img src="/SRMC/rmpb/images/yan_bnt03.png" width="57" height="57" /></a></div>
             <div class="home_t_bnt_font">需求池</div>
           </li>
       </ul>
      </span>
    
     <span class="yan_h_k01" style="_width:260px;">
        <span class="yan_h_font01">产品常用工具</span>
		<ul class="yan_h_kul01">
               <li>
                 <div class="home_t_bnt_pic"><a href="<%=basePath%>developFlowManage/requirementFlow/launchRequirement/queryRequirementList"><img src="/SRMC/rmpb/images/yan_bnt04.png" width="57" height="57" /></a></div>
                 <div class="home_t_bnt_font">发起需求审批</div>
               </li>
               <li>
                 <div class="home_t_bnt_pic"><a href="<%=basePath%>developFlowManage/requirementFlow/mylaunchRequirement/queryMylaunchRequirementItemPage"><img src="/SRMC/rmpb/images/yan_bnt05.png" width="57" height="57" /></a></div>
                 <div class="home_t_bnt_font">我发起的需求</div>
               </li>
               <li>
                 <div class="home_t_bnt_pic"><a href="<%=basePath%>developFlowManage/requirementItem/requirementManage/queryRequirementExport"><img src="/SRMC/rmpb/images/yan_bnt06.png" width="57" height="57" /></a></div>
                 <div class="home_t_bnt_font">需求导出</div>
               </li>
       </ul>
     </span>
           
      <span class="yan_h_k01" style="_width:330px; margin-right:0;">
              <span class="yan_h_font01">项目常用工具</span>
             <ul class="yan_h_kul01">
               <li>
                 <div class="home_t_bnt_pic"><a href="<%=basePath%>developFlowManage/requirementFlow/launchRequirement/queryRequirementList"><img src="/SRMC/rmpb/images/yan_bnt04.png" width="57" height="57" /></a></div>
                 <div class="home_t_bnt_font">发起需求审批</div>
               </li>
               <li>
                 <div class="home_t_bnt_pic"><a href="<%=basePath%>developFlowManage/requirementFlow/mylaunchRequirement/queryMylaunchRequirementItemPage"><img src="/SRMC/rmpb/images/yan_bnt05.png" width="57" height="57" /></a></div>
                 <div class="home_t_bnt_font">我发起的需求</div>
               </li>
               <li>
                 <div class="home_t_bnt_pic"><a href="<%=basePath%>developFlowManage/requirementItem/requirementManage/queryRequirementExport"><img src="/SRMC/rmpb/images/yan_bnt06.png" width="57" height="57" /></a></div>
                 <div class="home_t_bnt_font">需求导出</div>
               </li>
               <li>
                 <div class="home_t_bnt_pic"><a href="<%=basePath%>developFlowManage/requirementItem/requirementManage/queryRequirementExport"><img src="/SRMC/rmpb/images/yan_bnt07.png" width="57" height="57" /></a></div>
                 <div class="home_t_bnt_font">需求导出</div>
               </li>
             </ul>
      </span>
      
     </div>
     <!-- 工具按钮DIV end -->
	<div class="ge_a01b"></div>
	<div style="width:100%;">
		<div class="h_n_div01">
			<a id="defaultProjectButton" ><input type="button" onclick="toDefaultProject();" class="gl_cx_bnt02" value="默认项目" /></a>
			<input id="toggleQueryButton" type="button" class="gl_cx_bnt01" value="收起查询" onclick="toggleQueryDiv();"/>
	                 
			<ul class="home_nav_top01">
			<li id="one1" onclick="setTab(1);" class="hover">我的需求</li>
			<li id="one2" onclick="setTab(2);">全部需求</li>
			</ul>
		</div>
		<div style="height:12px;"></div>
		<div id="queryDiv" style="display: block;">
	
		<table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<th width="100">所属项目:</th>
				<td>
					<div id="projectIdDiv">
						<select id="projectId" name="projectId" class="select_new01" style="width: 100%;" 
							onchange="javascript:searchVersionByProject();">
							<option value="-1">请选择所属项目</option>
							<c:forEach items="${projectList}" var="project" varStatus="i">
								<option value="${project[1]}" <c:if test="${project[1]==form.projectId}">selected</c:if>>${project[0]}</option>
							</c:forEach>
							</select>
					</div>
				</td>
				<th width="100">所属版本:</th>
				<td>
					<div id="versionIdDiv">
					<select id="versionId" name="versionId" class="select_new01" style="width: 100%;">
						<option value="-1">请选择项目版本</option>
						<c:forEach items="${versionList}" var="version" varStatus="i">
							<option value="${version.versionId}" <c:if test="${version.versionId==form.versionId}">selected</c:if>>${version.versionCode}</option>
						</c:forEach>
					</select>
					</div>
	         	</td>
	         	<td style="width: 80px;">
	         		<input name="input" type="button" class="gl_cx_bnt03" value="查 询" onclick="doSubmit();"/>
	         	</td>
			</tr>
		</table>
		</div>
		<div class="ge_a01b"></div>
	
		
		<!-- 列表table start -->
		<table class="gl_table_a02h" width="100%" border="1" cellspacing="0" cellpadding="0">
			<tr style="height:26px">
				<th width="33%">需求名称</th>
				<th width="7%">版本</th>
				<th width="60%">总体进度</th>
			</tr>
	
			<c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
				<tr>
					<td style="text-align:left;vertical-align:middle;padding-left:7px;padding-right:7px">${item.name}</td> 
					<%-- <td title="${item.name}">
					   <script>document.write(ellipsisStr('${item.name}'));</script>				
					</td> --%>
					<td>${item.versionCode}</td>
					<td>
						<br/>
						<c:if test="${item.progressStatus=='1'}">
							<div class="bar_tb">
								<span class="bar"><span level="1" class="bar_2"></span></span>
								<ul class="bar_l">
									<li onmouseover="showTime('${item.workorderId}', '1', '${item.projectId}', '${item.versionId}', this)">创建需求</li>
									<li onmouseover="showTime('${item.workorderId}', '2', '${item.projectId}', '${item.versionId}', this)">已入需求池</li>
									<li onmouseover="showTime('${item.workorderId}', '3', '${item.projectId}', '${item.versionId}', this)">发起审批</li>
									<li onmouseover="showTime('${item.workorderId}', '4', '${item.projectId}', '${item.versionId}', this)">审批通过</li>
									<li onmouseover="showTime('${item.workorderId}', '5', '${item.projectId}', '${item.versionId}', this)">开发中</li>
									<li onmouseover="showTime('${item.workorderId}', '6', '${item.projectId}', '${item.versionId}', this)">测试中</li>
									<li onmouseover="showTime('${item.workorderId}', '7', '${item.projectId}', '${item.versionId}', this)">研发完成</li>
									<li onmouseover="showTime('${item.workorderId}', '8', '${item.projectId}', '${item.versionId}', this)">已上线</li>
								</ul>	
							</div>
						</c:if>
						<c:if test="${item.progressStatus=='2'}">
							<div class="bar_tb">
								<span class="bar"><span level="2" class="bar_2"></span></span>
								<ul class="bar_l">
									<li onmouseover="showTime('${item.workorderId}', '1', '${item.projectId}', '${item.versionId}', this)">创建需求</li>
									<li onmouseover="showTime('${item.workorderId}', '2', '${item.projectId}', '${item.versionId}', this)">已入需求池</li>
									<li onmouseover="showTime('${item.workorderId}', '3', '${item.projectId}', '${item.versionId}', this)">发起审批</li>
									<li onmouseover="showTime('${item.workorderId}', '4', '${item.projectId}', '${item.versionId}', this)">审批通过</li>
									<li onmouseover="showTime('${item.workorderId}', '5', '${item.projectId}', '${item.versionId}', this)">开发中</li>
									<li onmouseover="showTime('${item.workorderId}', '6', '${item.projectId}', '${item.versionId}', this)">测试中</li>
									<li onmouseover="showTime('${item.workorderId}', '7', '${item.projectId}', '${item.versionId}', this)">研发完成</li>
									<li onmouseover="showTime('${item.workorderId}', '8', '${item.projectId}', '${item.versionId}', this)">已上线</li>
								</ul>
							</div>	
						</c:if>
						<c:if test="${item.progressStatus=='3'}">
							<div class="bar_tb">
								<span class="bar"><span level="3" class="bar_2"></span></span>
								<ul class="bar_l">
									<li onmouseover="showTime('${item.workorderId}', '1', '${item.projectId}', '${item.versionId}', this)">创建需求</li>
									<li onmouseover="showTime('${item.workorderId}', '2', '${item.projectId}', '${item.versionId}', this)">已入需求池</li>
									<li onmouseover="showTime('${item.workorderId}', '3', '${item.projectId}', '${item.versionId}', this)">发起审批</li>
									<li onmouseover="showTime('${item.workorderId}', '4', '${item.projectId}', '${item.versionId}', this)">审批通过</li>
									<li onmouseover="showTime('${item.workorderId}', '5', '${item.projectId}', '${item.versionId}', this)">开发中</li>
									<li onmouseover="showTime('${item.workorderId}', '6', '${item.projectId}', '${item.versionId}', this)">测试中</li>
									<li onmouseover="showTime('${item.workorderId}', '7', '${item.projectId}', '${item.versionId}', this)">研发完成</li>
									<li onmouseover="showTime('${item.workorderId}', '8', '${item.projectId}', '${item.versionId}', this)">已上线</li>
								</ul>
							</div>	
						</c:if>
						<c:if test="${item.progressStatus=='4'}">
							<div class="bar_tb">
								<span class="bar"><span level="4" class="bar_2"></span></span>
								<ul class="bar_l">
									<li onmouseover="showTime('${item.workorderId}', '1', '${item.projectId}', '${item.versionId}', this)">创建需求</li>
									<li onmouseover="showTime('${item.workorderId}', '2', '${item.projectId}', '${item.versionId}', this)">已入需求池</li>
									<li onmouseover="showTime('${item.workorderId}', '3', '${item.projectId}', '${item.versionId}', this)">发起审批</li>
									<li onmouseover="showTime('${item.workorderId}', '4', '${item.projectId}', '${item.versionId}', this)">审批通过</li>
									<li onmouseover="showTime('${item.workorderId}', '5', '${item.projectId}', '${item.versionId}', this)">开发中</li>
									<li onmouseover="showTime('${item.workorderId}', '6', '${item.projectId}', '${item.versionId}', this)">测试中</li>
									<li onmouseover="showTime('${item.workorderId}', '7', '${item.projectId}', '${item.versionId}', this)">研发完成</li>
									<li onmouseover="showTime('${item.workorderId}', '8', '${item.projectId}', '${item.versionId}', this)">已上线</li>
								</ul>	
							</div>
						</c:if>
						<c:if test="${item.progressStatus=='5'}">
							<div class="bar_tb">
								<span class="bar"><span level="5" class="bar_2"></span></span>
								<ul class="bar_l">
									<li onmouseover="showTime('${item.workorderId}', '1', '${item.projectId}', '${item.versionId}', this)">创建需求</li>
									<li onmouseover="showTime('${item.workorderId}', '2', '${item.projectId}', '${item.versionId}', this)">已入需求池</li>
									<li onmouseover="showTime('${item.workorderId}', '3', '${item.projectId}', '${item.versionId}', this)">发起审批</li>
									<li onmouseover="showTime('${item.workorderId}', '4', '${item.projectId}', '${item.versionId}', this)">审批通过</li>
									<li onmouseover="showTime('${item.workorderId}', '5', '${item.projectId}', '${item.versionId}', this)">开发中</li>
									<li onmouseover="showTime('${item.workorderId}', '6', '${item.projectId}', '${item.versionId}', this)">测试中</li>
									<li onmouseover="showTime('${item.workorderId}', '7', '${item.projectId}', '${item.versionId}', this)">研发完成</li>
									<li onmouseover="showTime('${item.workorderId}', '8', '${item.projectId}', '${item.versionId}', this)">已上线</li>
								</ul>	
							</div>
						</c:if>
						<c:if test="${item.progressStatus=='6'}">
							<div class="bar_tb">
								<span class="bar"><span level="6" class="bar_2"></span></span>
								<ul class="bar_l">
									<li onmouseover="showTime('${item.workorderId}', '1', '${item.projectId}', '${item.versionId}', this)">创建需求</li>
									<li onmouseover="showTime('${item.workorderId}', '2', '${item.projectId}', '${item.versionId}', this)">已入需求池</li>
									<li onmouseover="showTime('${item.workorderId}', '3', '${item.projectId}', '${item.versionId}', this)">发起审批</li>
									<li onmouseover="showTime('${item.workorderId}', '4', '${item.projectId}', '${item.versionId}', this)">审批通过</li>
									<li onmouseover="showTime('${item.workorderId}', '5', '${item.projectId}', '${item.versionId}', this)">开发中</li>
									<li onmouseover="showTime('${item.workorderId}', '6', '${item.projectId}', '${item.versionId}', this)">测试中</li>
									<li onmouseover="showTime('${item.workorderId}', '7', '${item.projectId}', '${item.versionId}', this)">研发完成</li>
									<li onmouseover="showTime('${item.workorderId}', '8', '${item.projectId}', '${item.versionId}', this)">已上线</li>
								</ul>	
							</div>	
						</c:if>
						<c:if test="${item.progressStatus=='7'}">
							<div class="bar_tb">
								<span class="bar"><span level="7" class="bar_2"></span></span>
								<ul class="bar_l">
									<li onmouseover="showTime('${item.workorderId}', '1', '${item.projectId}', '${item.versionId}', this)">创建需求</li>
									<li onmouseover="showTime('${item.workorderId}', '2', '${item.projectId}', '${item.versionId}', this)">已入需求池</li>
									<li onmouseover="showTime('${item.workorderId}', '3', '${item.projectId}', '${item.versionId}', this)">发起审批</li>
									<li onmouseover="showTime('${item.workorderId}', '4', '${item.projectId}', '${item.versionId}', this)">审批通过</li>
									<li onmouseover="showTime('${item.workorderId}', '5', '${item.projectId}', '${item.versionId}', this)">开发中</li>
									<li onmouseover="showTime('${item.workorderId}', '6', '${item.projectId}', '${item.versionId}', this)">测试中</li>
									<li onmouseover="showTime('${item.workorderId}', '7', '${item.projectId}', '${item.versionId}', this)">研发完成</li>
									<li onmouseover="showTime('${item.workorderId}', '8', '${item.projectId}', '${item.versionId}', this)">已上线</li>
								</ul>	
							</div>
						</c:if>
						<c:if test="${item.progressStatus=='8'}">
							<div class="bar_tb">
								<span class="bar"><span level="8" class="bar_2"></span></span>
								<ul class="bar_l">
									<li onmouseover="showTime('${item.workorderId}', '1', '${item.projectId}', '${item.versionId}', this)">创建需求</li>
									<li onmouseover="showTime('${item.workorderId}', '2', '${item.projectId}', '${item.versionId}', this)">已入需求池</li>
									<li onmouseover="showTime('${item.workorderId}', '3', '${item.projectId}', '${item.versionId}', this)">发起审批</li>
									<li onmouseover="showTime('${item.workorderId}', '4', '${item.projectId}', '${item.versionId}', this)">审批通过</li>
									<li onmouseover="showTime('${item.workorderId}', '5', '${item.projectId}', '${item.versionId}', this)">开发中</li>
									<li onmouseover="showTime('${item.workorderId}', '6', '${item.projectId}', '${item.versionId}', this)">测试中</li>
									<li onmouseover="showTime('${item.workorderId}', '7', '${item.projectId}', '${item.versionId}', this)">研发完成</li>
									<li onmouseover="showTime('${item.workorderId}', '8', '${item.projectId}', '${item.versionId}', this)">已上线</li>
								</ul>
							</div>	
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	<!-- 列表table end -->
		
		
		<jsp:include flush="true" page="/public/include/navigate2.jsp"></jsp:include>
		
	</div>
            
	</form>
          </div>

<script type="text/javascript" src="/SRMC/rmpb/js/list-common.js"></script>
<script type="text/javascript">

function searchVersionByProject(){
	var url = "<%=basePath%>developFlowManage/searchVersionByProject";
	var params = {
		projectId:jQuery("#projectId").val()
	};
	jQuery.post(url, params, function(data){
		var versionList = data;
		var option = '<option value="-1">请选择项目版本</option>';
		for(var i=0; i<versionList.length; i++){
			option += '<option value="' + versionList[i].versionId + '">' + versionList[i].versionCode + '</option>';
		}
		jQuery("#versionId").html(option);
		//rSelectsSepecify("versionIdDiv");
	} , 'json');

}

function doSubmit(){
	document.all("pageIndex").value = "1";
	document.forms[0].submit();
}

function doReset(){
	//jQuery("#projectId").val("-1");
	//rSelectsSepecify("projectIdDiv");
	//jQuery("#versionId").html('<option value="-1">请选择项目版本</option>');
	//rSelectsSepecify("versionIdDiv");
	jQuery("#emergencyDegree").val("-1");
	rSelectsSepecify("emergencyDegreeDiv");
	jQuery("#name").val("");
	jQuery("#startDate").val("");
	jQuery("#finishDate").val("");
}

function toDefaultProject(){
	jQuery("#defaultProjectButton").attr("href","<%=basePath%>core/portal/toDefaultProject");
	jQuery("#defaultProjectButton").fancybox({
		'width'				: '80%',
		'height'			: '50%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
}

function doClose(){
	jQuery.fancybox.close();
}

function doRefresh(){
	jQuery.fancybox.close();
	doSubmit();
}

function showTime(workorderId, type, projectId, versionId, object){
	var url = "<%=basePath%>core/portal/queryNodeTime";
	var params = {
		workorderId:workorderId,
		type:type,
		projectId:projectId,
		versionId:versionId
	};
	jQuery.post(url, params, function(data){
		if(data == null || data == ""){
			return
		}else{
			if(type == "1"){
				jQuery(object).attr("title", "创建时间：" + data);
			}else if(type == "2"){
				jQuery(object).attr("title", "需求入池：" + data);
			}else if(type == "3"){
				jQuery(object).attr("title", "发起时间：" + data);
			}else if(type == "4"){
				jQuery(object).attr("title", "通过时间：" + data);
			}else if(type == "5"){
				jQuery(object).attr("title", "开始开发：" + data);
			}else if(type == "6"){
				jQuery(object).attr("title", "开始测试：" + data);
			}else if(type == "7"){
				jQuery(object).attr("title", "研发完成：" + data);
			}else if(type == "8"){
				jQuery(object).attr("title", "上线时间：" + data);
			}
		}
	} );
}

</script>

	</body>




</html>