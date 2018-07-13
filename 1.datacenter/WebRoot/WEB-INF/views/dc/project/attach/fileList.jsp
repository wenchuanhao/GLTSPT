<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>文档上传</title>
<base  target="_self"/>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
	<style>
	.gl_import_m_file{
		width:90%;
		margin-bottom: 60px;
		margin-top: 0;
		margin-left: auto;
		margin-right: auto;
	}
	</style>
<style>
.formTable{ table-layout:fixed; width:100%; border:1px  solid #d5e5ef; font-size:14px; word-break:break-all;}
.formTable td,.formTable th{padding: 0; text-align:center; vertical-align:middle;}
/*边框样式 S */
.first_th,.second_th,.fird_th,.four_th,.five_th,.six_th,.seven_th{border-bottom:1px solid #d5e5ef;border-right:1px solid #d5e5ef;box-sizing:content-box;}
.first_td,.second_td,.fird_td,.four_td,.five_td,.six_td,.seven_td{border-bottom:1px solid #d5e5ef;border-right:1px solid #d5e5ef; box-sizing:content-box;background-color: #fff;}
/*边框样式 E */
.formTable table,.formTable{width: 100%;height:100%;table-layout: fixed;border-spacing:0;background-color: #fff;}
.formTable th{ font-weight:normal; line-height:34px; background:#eff5fb;}
.t_btn01{ color:#279ff2; padding:0 4px; text-decoration:none;}
</style>

</head>
<body style="overflow-x:hidden;">
<div class="gl_import_m_file" >
  	<div class="tabpages" >
    	<ul class="l" id="tab">
	      		<li id="tab1"  class="current">
					<c:if test="${fileType eq '1'}">建设项目文档</c:if>
					<c:if test="${fileType eq '2'}">投资编码文档</c:if>
					<c:if test="${fileType eq '3'}">子项目文档</c:if>
					<c:if test="${fileType eq '4'}">子项目合同文档</c:if>
					<c:if test="${fileType eq '5'}">项目文档汇总</c:if>	      		
	      		<em></em></li>
	      		
	      		<c:if test="${fileType ne '5'}">
	      		<li id="tab2" >
					<c:if test="${fileType eq '1'}">建设项目详情</c:if>
					<c:if test="${fileType eq '2'}">投资编码详情</c:if>
					<c:if test="${fileType eq '3'}">子项目详情</c:if>
					<c:if test="${fileType eq '4'}">子项目合同详情</c:if>	      		
	      		<em></em></li>
	      		</c:if>
                <c:if test="${fileType eq '1'}"><c:set var="userId" value="${vo.column10}"/></c:if>
                <c:if test="${fileType eq '2'}"><c:set var="userId" value="${vo.column19}"/></c:if>
                <c:if test="${fileType eq '3'}"><c:set var="userId" value="${vo.column09}"/></c:if>
                <c:if test="${fileType eq '4'}"><c:set var="userId" value="${vo.column21}"/></c:if>	      		
    	</ul>
    	<div class="otherButtons r">
    		
    		<a class="btn_common01" href="javascript:ev_batchDownload()" ><img src="/SRMC/dc/images/btnIcon/export.png" /><span>批量下载附件</span></a>
    		<a class="btn_common01" href="javascript:window.close();"><img src="/SRMC/dc/images/btnIcon/invalid.png" /><span>关闭</span></a>
		</div>    	
  	</div>
<table class="formTable" id="table1">
    <!-- 标题 S -->
    <tr>
        <th class="first_th" width='6%'>类型</th>
        <th>
            <table class="second_table">
                <tr>
                    <th class="second_th" width="5%">阶段</th>
                    <th>
                        <table class="fird_table">
                            <tr>
                                <th class="fird_th" width="8%">子阶段</th>
                                <th>
                                    <table class="four_table">
                                        <tr>
                                            <th class="four_th" width="10%">名称</th>
                                            <th>
                                                <table class="five_table">
                                                    <tr>
                                                        <th class="five_th" width="5%">必须</th>
                                                        <th>
                                                            <table class="six_table">
                                                                <tr>
                                                                    <th class="six_th" width="9%">运维移交需要</th>
                                                                    <th class="six_th" width="12%">文档要求</th>
                                                                    <th>
                                                                        <table class="seven_table">
                                                                            <tr>
                                                                                <th class="seven_th" ><input  id="checkAll" type="checkbox" onclick="ev_checked()" title="全选/反选"/>文档名称</th>
                                                                                <th class="seven_th" width="10%">有效期</th>
                                                                                <th class="seven_th" width="25%">文档信息</th>
                                                                                <th class="seven_th" width="10%">上传信息</th>
                                                                                <th class="seven_th" width="10%">最后借阅人</th>
                                                                                <th class="seven_th" width="10%">操作</th>
                                                                            </tr>
                                                                        </table>
                                                                    </th>
                                                                </tr>
                                                            </table>
                                                        </th>
                                                    </tr>
                                                </table>
                                            </th>
                                        </tr>
                                    </table>
                                </th>
                            </tr>
                        </table>
                    </th>
                </tr>
            </table>
        </td>
    </tr>
    <!-- 标题 E -->
    <!-- 内容 S -->
    
    <!-- 第一层 start -->
    <c:forEach items="${one}" var="vo" varStatus="i">
    <tr>
        <td class="first_td" width="6%">${vo.COLUMN_03}</td>
        <td>
            <table class="second_table">
            <c:forEach items="${vo.two}" var="vo" varStatus="i">
            <!-- 第二层 start -->
                <tr>
                    <td class="second_td" width="5%">${vo.COLUMN_02}</td>
                    <td>
                        <table class="fird_table">
                        <c:forEach items="${vo.three}" var="vo" varStatus="i">
                        <!-- 第三层 start -->
                            <tr>
                                <td class="fird_td" width="8%">${vo.COLUMN_02}</td>
                                <td>
                                    <table class="four_table">
                                    <c:forEach items="${vo.four}" var="vo" varStatus="i">
                                    <!-- 第四层 start -->
                                        <tr>
                                            <td class="four_td" width="10%">${vo.COLUMN_01}</td>
                                            <td>
                                                <table class="five_table">
                                                    <tr>
                                                        <td class="five_td" width="5%">${vo.COLUMN_02}</td>
                                                        <td>
                                                            <table class="six_table">
                                                                <tr>
                                                                    <td class="six_td" width="9%">${vo.COLUMN_03}</td>
                                                                    <td class="six_td" width="12%">${vo.COLUMN_04}</td>
                                                                    <td>
                                                                        <table class="seven_table" id="seven_table${vo.ID}">
                                                                        <%-- 第五级，用户上传附件 --%>
                                                                        	<c:set var="listAttach" value="${vo.listAttach}"/>
                                                                        	<c:forEach items="${listAttach}" var="f"  varStatus="i">
                                                                            <tr>
                                                                            	<!-- 文档名称 -->
                                                                                <td class="seven_td"  style="text-align: left;">
                                                                                <c:if test="${f.column07 eq '1'}"><input name="ids"  id="ids"  type="checkbox"  value="${f.id}" />
                                                                                		<c:if test="${f.column08 eq null}">
                                                                                		<a id="file_download" title="附件缺失,请补充" href="javascript:ev_edit('${f.parentId}','${f.id}')"  style="color: red;cursor:pointer;">附件缺失,请补充</a>
                                                                                		</c:if>
                                                                                		<c:if test="${f.column08 ne null}">
                                                                                		<a id="file_download" title="下载${f.column08}" href="javascript:ev_download('${f.id}')"  style="color: #40baff!important;cursor:pointer;" (<fmt:formatNumber value="${f.column09/1024}" pattern="#"/>KB)"  >${f.column01}</a>
                                                                                		</c:if>                                                                                 	
                                                                                </c:if>
                                                                                <c:if test="${f.column07 ne '1'}">${f.column01}</c:if>
                                                                                </td>
                                                                                <!-- 有效期 -->
                                                                                <td class="seven_td" width="10%"><fmt:formatDate value="${f.column02}" pattern="yyyy-MM-dd "/></td>
                                                                                <!-- 文档信息 -->
                                                                                <td class="seven_td" width="25%">
                                                                                	<c:if test="${f.column07 ne '1'}">${f.column07 eq '2' ? '纸质原件':''}${f.column07 eq '3' ? '纸质复印件':''}|${f.column03}份<br/>${f.column04}</c:if>
                                                                                	<c:if test="${f.column07 eq '1'}">电子文档</c:if>																		    		
                                                                                </td>
                                                                                <!-- 上传信息 -->
                                                                                <td class="seven_td" width="10%">${f.createUserName}<br/><fmt:formatDate value="${f.createDate}" pattern="yyyy-MM-dd "/></td>
                                                                                <!-- 借阅人 -->
                                                                                <td class="seven_td" width="10%">
                                                                                <c:if test="${f.column07 ne '1'}">${f.column06}</c:if></td>
                                                                                <!-- 操作 -->
                                                                                <td class="seven_td" width="10%" >
		                                                                                <c:if test="${fileType ne '5'}">
		                                                                                <c:if test="${role ne null && (role eq 'GC_YWGLY' or (role eq 'GC_XMFZR' && fn:contains(userId, sysUserId)))}">
		                                                                                	<a href="javascript:ev_add('${f.parentId}','')"  style="color: #40baff!important;cursor:pointer;" class="t_btn01 upload">上传</a><br/>
		                                                                                </c:if>
		                                                                                <c:if test="${role ne null && (role eq 'GC_YWGLY' or f.createUserId eq sysUserId)}">
		                                                                               		<a href="javascript:ev_edit('${f.parentId}','${f.id}')"  style="color: #40baff!important;cursor:pointer;" class="t_btn01">编辑</a><br/>
		                                                                               		<a href="javascript:ev_delete('${f.parentId}','${f.id}')"  style="color: #40baff!important;cursor:pointer;" class="t_btn01 d_del">删除</a>
		                                                                               	</c:if>
		                                                                                </c:if>
		                                                                                <c:if test="${fileType eq '5'}"><!-- 一览表 -->
		                                                                                	<c:if test="${f.column07 eq '1'}"><!-- 电子文档 -->
		                                                                                		<a href="javascript:ev_download('${f.id}')" style="color: #40baff!important;cursor:pointer;" class="t_btn01 upload">下载</a>
		                                                                                	</c:if>
		                                                                                	<c:if test="${f.column07 ne '1'}">
		                                                                                		<c:if test="${f.column03 > 0}"><a href="javascript:ev_openJy('${f.id}')"  style="color: #40baff!important;cursor:pointer;" class="t_btn01 d_del">借阅</a><br/></c:if>
		                                                                                		<c:if test="${role ne null && (role eq 'GC_YWGLY' or (role eq 'GC_XMFZR' && (f.createUserId eq sysUserId)))}">
		                                                                                			<c:if test="${not empty f.listJy}"><a href="javascript:ev_openGh('${f.id}')"  style="color: #40baff!important;cursor:pointer;"  class="t_btn01">归还</a></c:if>
		                                                                                		</c:if>
		                                                                                	</c:if>                                                                    
		                                                                                </c:if>
                                                                               </td>
                                                                            </tr>
                                                                            </c:forEach>
																		   <c:if test="${empty listAttach}">
                                                                            <tr>
                                                                                <td class="seven_td" >&nbsp;</td>
                                                                                <td class="seven_td" width="10%">&nbsp;</td>
                                                                                <td class="seven_td" width="25%">&nbsp;</td>
                                                                                <td class="seven_td" width="10%">&nbsp;</td>
                                                                                <td class="seven_td" width="10%">&nbsp;</td>
                                                                                <td class="seven_td" width="10%">
                                                                                <c:if test="${fileType ne '5'}">
                                                                                <c:if test="${role ne null && (role eq 'GC_YWGLY' or (role eq 'GC_XMFZR' && fn:contains(userId, sysUserId)))}">
	                                                                                <a href="javascript:ev_add('${vo.ID}','')"  style="color: #40baff!important;cursor:pointer;" class="t_btn01 upload">上传</a>
	                                                                            </c:if>
                                                                                </c:if>
                                                                                </td>
                                                                            </tr>
																		   </c:if>                                                                            
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                        <!-- 第四层 end -->
                                    </table>
                                </td>
                            </tr>
                            </c:forEach>
                            <!-- 第三层 end -->
                        </table>
                    </td>
                </tr>
                </c:forEach>
                <!-- 第二层 end -->
            </table>
        </td>
    </tr>
    </c:forEach>
    <!-- 第一层 end -->
    <!-- 内容 E -->
</table>
<table width="99%" border="0" cellspacing="0" cellpadding="0"  id="table3">
<!-- 子项目、软件工程；全部文档，软件工程 -->
<c:if test="${(fileType eq '3' || fileType eq '5') && (vo.column01 eq '1')}">
		<tr>
		    <td>
关键文档要求：<br />
一、项目需求规格说明书的要求<br />
1、项目范围；<br />
2、业务功能需求；<br />
3、性能需求；<br />
4、系统功能需求；<br />
5、其它辅助说明需求的部分，如目的、编写依据、术语、开发环境、约束、假设、风险、质量要求、易用性需求等。<br /><br />

二、项目详细设计说明书的要求<br />
1、整体设计方案：达到主体需求的技术架构，含网络架构、服务器、操作系统、数据库、开发语言、web解析、应用进程的设计；<br />
2、业务功能和系统功能的设计方案：对应到详细功能列表；<br />
3、界面设计：最终用户使用的关键页面和交互方式设计；<br />
4、数据库设计：初步达到数据字典的要求。<br /><br />

三、测试报告：功能列表和内测<br /><br />

四、漏扫复查报告<br /><br />

五、系统配置手册（系统部署文档）：要求完备，和详细设计和实际情况一致，至少要和实际一致，形成配置一张表<br />
1、网络结构，具体的网络图和地址、网络策略；<br />
2、服务器、操作系统：账号密码、进程；<br />
3、web环境：环境说明、配置文件、应用文件等；<br />
4、数据库：账号密码、连接方式、具体格式数据字典说明；<br />
5、其它：集成配置、接口服务、数据交互等项目完成的功能部署情况。<br /><br />

六、数据字典：要求完备，和详细设计和实际情况一致，至少要和实际一致<br />
1、实体关系图；<br />
2、业务表和系统表，表和字段的字典，含中文名和数据库名；<br />
3、关键数据说明：含性别、类型、排序等。<br /><br />

七、账号清单：网络架构、服务器、操作系统、数据库、开发语言、web解析、应用等各个层面，形成一张表<br /><br />

八、管理员维护手册：后台管理功能说明<br /><br />

九、用户使用手册：前台用户功能说明<br /><br />

十、系统巡检手册：部署文档一致即可<br /><br />

十一、源代码		    
		    </td>
		 </tr>
</c:if>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center" style="display: none;" id="table2">
	<c:if test="${fileType eq '1'}"><jsp:include page="../jsxm/form.jsp" /></c:if>
	<c:if test="${fileType eq '2'}"><jsp:include page="../tzbm/form.jsp" /></c:if>
	<c:if test="${fileType eq '3'}"><jsp:include page="../zxm/form.jsp" /></c:if>
	<c:if test="${fileType eq '4'}"><jsp:include page="../zxmHt/form.jsp" /></c:if>
</table>
</div>
<form name="form" id="pageForm" method="post"  ></form>	
</body>
</html>

<script type="text/javascript">
$(function(){
     // 最后一个table自适应高度
     $('.second_table table').each(function(){
         var prevTd=$(this).parent().prev();
         $(this).css('minHeight',prevTd.height());
         //console.log(prevTd.height());
     });
});

 $(document).ready(function(){
	var m = "${mess}";
	if(m != "" && m == "s"){
		alert("删除成功");
	}
	else if(m != "" && m == "e"){
		alert("删除失败");
	} 
 
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	
});

$(function(){
	$("#tab").find("li").each(function(){
		var id = $(this).attr("id");
		$(this).hover(
			function(){
				if(id == "tab1"){
					$("#tab1").addClass("current"); 
					$("#tab2").removeClass("current");
					$("#table1").show();
					$("#table3").show();
					$("#table2").hide();
				}else{
					$("#tab1").removeClass("current"); 
					$("#tab2").addClass("current");				
					$("#table1").hide();
					$("#table3").hide();
					$("#table2").show();				
				}
			},
			function(){
			}
		);
	});
});
    //兼容谷歌  
    if(!window.showModalDialog){
        window.showModalDialog=function(url,name,option){
            if(window.hasOpenWindow){  
                window.newWindow.focus();  
            }  
            var re = new RegExp(";", "g");    
            var option  = option.replace(re, '","'); //把option转为json字符串  
            var re2 = new RegExp(":", "g");  
            option = '{"'+option.replace(re2, '":"')+'"}';  
            option = JSON.parse(option);  
            var openOption = 'width='+parseInt(option.dialogWidth)+',height='+parseInt(option.dialogHeight)+',left='+(window.screen.width-parseInt(option.dialogWidth))/2+',top='+(window.screen.height-30-parseInt(option.dialogHeight))/2;  
            window.hasOpenWindow = true;  
            window.newWindow = window.open(url,name,openOption);
        }
    }
function ev_add(parentId,id){
		window.showModalDialog("<%=basePath%>fileUpload/add?parentId="+parentId+"&id="+id,window,"dialogWidth:"+(window.screen.width - (window.screen.width/2)+200)+"px;dialogHeight:"+(window.screen.height - (window.screen.height/2)+20)+"px"); 
}
    
function ev_edit(parentId,id){
      	ev_add(parentId,id);
}

function ev_checked(){
	var checkAll = document.getElementById("checkAll");
	if(checkAll != null && checkAll.type=="checkbox"){
		var ids = document.getElementsByName("ids");
		 if(ids != null){
			for(var i=0;i<ids.length;i++){
				var id = ids[i];
				if(checkAll.checked){
					id.checked=true;
				}else{
					id.checked=false;
				}
			}	 
		 }
	}
}
    
function ev_delete(parentId,id){
		if(id == ""){
			ev_add(parentId,id);
			return;
		}
		if(confirm("确定删除？")){
			$.ajax({
				type 	: "post",
				url 	: "<%=basePath%>fileUpload/delete?id="+id,
				async	: true,
				error 	: function() {
					alert("删除失败");
				},
				success : function(data, textStatus) {
					if(data != "" && data == "s"){
						alert("删除成功");
						ev_refresh(parentId);
					}
					else if(m != "" && m == "e"){
						alert("删除失败");
					}
				},
				dataType : "text"
			});	
		}
}

function ev_refresh(parentId){
	$.ajax({
		type 	: "post",
		url 	: "<%=basePath%>fileUpload/list?fileType=${fileType}&parentId="+parentId+"&column10="+$("#id").val()+"&key="+Math.random(),
		async	: true,
		error 	: function() {
			alert("刷新失败");
		},
		success : function(data, textStatus) {
			$("#seven_table"+parentId).html(data);
		},
		dataType : "text"
	});	
}

function ev_refresh1(parentId,column10){
	$.ajax({
		type 	: "post",
		url 	: "<%=basePath%>fileUpload/list?fileType=${fileType}&parentId="+parentId+"&column10="+column10+"&key="+Math.random(),
		async	: true,
		error 	: function() {
			alert("刷新失败");
		},
		success : function(data, textStatus) {
			$("#seven_table"+parentId).html(data);
		},
		dataType : "text"
	});	
}

function getIds(){
	var ids = "";
	$("input[id='ids']:checked").each(function(){ 
	   ids += ","+$(this).val();
	});
	return ids;
}

function ev_batchDownload(){
	var ids = getIds();
	if(ids == ""){
		alert("请选择附件！");
		return;
	}
	
	var flag = false;
	var idm = ids.split(",");
	for(var i = 0; i < idm.length; i++){
	   if(idm[i].replace(/\s+/g,"") != ""){
	        flag = true;
	   		break;
	   }
	}
	
	if(flag){
		$.ajax({
			type 	: "post",
			url 	: "<%=basePath%>fileUpload/batchDownFile?key="+Math.random()+"&ids="+ids,
			async	: false,
			error 	: function() {
				alert("下载失败");
			},
			success : function(data, textStatus) {
				if(data != null && data == "N"){
					alert("文件不存在，不能下载");
				}else{
					window.location.href = "<%=basePath%>fileUpload/batchDownFile?key="+Math.random()+"&ids="+ids;
				}
			},
			dataType : "text"
		});	
	}else{
		alert("请选择附件！");
		return;
	}
}

function ev_download(id){
	$.ajax({
		type 	: "post",
		url 	: "<%=basePath%>fileUpload/downFile?key="+Math.random()+"&id="+id,
		async	: false,
		error 	: function() {
			alert("下载失败");
		},
		success : function(data, textStatus) {
			if(data != null && data == "N"){
				alert("文件不存在，不能下载");
			}else if(data != null && data != "N"){
				window.location.href = "<%=basePath%>fileUpload/downFile?key="+Math.random()+"&id="+id;
		    }
		},
		dataType : "text"
	});	

	
}

function ev_submit(url){
	document.forms[0].action = url;
	document.forms[0].submit();
}

//借阅
function ev_openJy(id){
	//ev_submit("<%=basePath%>fileUpload/addJy?attachId="+id);
	window.showModalDialog("<%=basePath%>fileUpload/addJy?attachId="+id,window,"dialogWidth:"+(window.screen.width - (window.screen.width/2)+200)+"px;dialogHeight:"+(window.screen.height - (window.screen.height/2)+20)+"px;");
}
//归还
function ev_openGh(id){
	//ev_submit("<%=basePath%>fileUpload/listJy?attachId="+id);
	window.showModalDialog("<%=basePath%>fileUpload/listJy?attachId="+id,window,"dialogWidth:"+(window.screen.width - (window.screen.width/2)+200)+"px;dialogHeight:"+(window.screen.height - (window.screen.height/2)+20)+"px;");
}
function ev_checked(){
	var checkAll = document.getElementById("checkAll");
	if(checkAll != null && checkAll.type=="checkbox"){
		var ids = document.getElementsByName("ids");
		 if(ids != null){
			for(var i=0;i<ids.length;i++){
				var id = ids[i];
				if(checkAll.checked){
					id.checked=true;
				}else{
					id.checked=false;
				}
			}	 
		 }
	}
}
</script>