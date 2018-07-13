<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String pid = (String)request.getSession().getAttribute("pid");
	String oid = (String)request.getSession().getAttribute("oid");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/css/dtree.css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/tree01.js"></script>

<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/js.js"></script>
<script src="/SRMC/rmpb/js/Checkable.js" type="text/javascript"></script>
<script src="/SRMC/rmpb/js/dtree.js" type=text/javascript></script>
<script type="text/javascript">

function search(){
	if(jQuery("#se").attr("class") == "gl_cx_bnt01b"){
		jQuery("#se").attr("class","gl_cx_bnt01");
		jQuery("#se").val("收起查询");
		jQuery("#sea").css("display","block");
	}else{
		jQuery("#se").attr("class","gl_cx_bnt01b");
		jQuery("#se").val("展开查询");
		jQuery("#sea").css("display","none");
	}
}
function doPageSearch(){
 document.all("pageIndex").value = "1";
 document.getElementById("isPages").value='Y';
 document.getElementById("st").value="1";
 form.submit();
 
}
function clean(){
		document.getElementById("orgName").value="";
		document.getElementById("mf2").value="";
        document.getElementById("parentId").value="";
	
}			
	 
       //颜色变量
       var colorTemp = "";
        function toAddOrg(){
	 		parent.sysM_mainFrame.window.location.href = "<%=basePath%>sys/org/addOrg?perId="+param+"&st=1";
	  }
	  function selectAll() {
		for (var i=0;i<form2.length ;i++) {
			var e = form2[i];
			if (e.name == 'itemOffset') {
	   			e.checked = form2.checkAll.checked;
   			}
   		}
	}
	function getSelectCount() {
			var ids="";
			document.getElementById("orgIds").value="";
			var chks = document.getElementsByName('itemOffset');
			var j = 0;
			for ( var i = 0; i < chks.length; i++) {
				if (chks[i].checked){
					j++;
					ids+=chks[i].value+",";
				}
			}
			document.getElementById("orgIds").value=ids;
			return j;
		}
		function doDeleteItems() {
				if (getSelectCount() < 1) {
					alert("请选择需要删除的组织！");
				} else if (confirm("数据删除后不可以恢复，您确定要删除吗？")) {
						getSelectCount();
					var orgIds=document.getElementById("orgIds").value;
					checkSub(orgIds);
				}
			}
			function checkSub(orgIds){
				$.post("<%=basePath%>sys/org/checkSub", {
					"orgIds" :orgIds
					}, function(data) {
					if (data == "0"){
						form2.action = '<%=basePath%>sys/org/deleteOrg?'+Math.random();
						form2.submit();
					}
					if(data=="1"){
						alert("对不起！你删除的组织机构下面有组织，请删除子组织后再进行删除");
					}
					if(data=="2"){
						alert("删除出现错误");
					}
				});
		}
		function checkSub(orgIds){
				$.post("<%=basePath%>sys/org/checkSub", {
					"orgIds" :orgIds
					}, function(data) {
					if (data == "0"){
						form2.action = '<%=basePath%>sys/org/deleteOrg?'+Math.random();
						form2.submit();
					}
					if(data=="1"){
						alert("对不起！你删除的组织机构下面有组织，请删除子组织后再进行删除");
					}
					if(data=="2"){
						alert("删除出现错误");
					}
				});
		}
		function checkSub2(orgIds){
				$.post("<%=basePath%>sys/org/checkSub", {
					"orgIds" :orgIds
					}, function(data) {
					if (data == "0"){
						document.getElementById("orgIds").value=orgIds;
						form2.action = '<%=basePath%>sys/org/deleteOrg?'+Math.random();
						form2.submit();
					}
					if(data=="1"){
						alert("对不起！你删除的组织机构下面有组织，请删除子组织后再进行删除");
					}
					if(data=="2"){
						alert("删除出现错误");
					}
				});
		}
		function move(id,pId,flag){
			$.post("<%=basePath%>sys/org/move", {
					"id":id,"pId":pId,"flag":flag
					}, function(data) {
						if(data.length>5){
							window.parent['sysM_mainFrame'].location="<%=basePath%>sys/org/queryOrg?parentId="+data+"&st=1";
						}
						if(data=="9"){
							alert("操作出错了，请重新操作");
						}
						if(data=="1"){
							alert("该组织已经排在最前面了");
						}
						if(data=="2"){
							alert("该组织已经排在最后了");
						}
					});
		}
		//点击树里面上移和下移按钮
		function move2(flag){
		  var oid="<%=oid%>";
		  var pid="<%=pid%>";
		  if(oid=='null' || oid=="" || pid=='null' || pid==""){
		  	  alert("请先单击树中组织，再进行移动操作!");
		  	  return false;
		  }
		  if(oid=="80df8fa55ca4048ac2314dab1a52d75e" || pid=="80df8fa55ca4048ac2314dab1a52d75e"){
		  	  alert("对不起!该组织不可以进行移动操作!");
		  	  return false;
		  }
			$.post("<%=basePath%>sys/org/move?mf=y", {
					"id":oid,"pId":pid,"flag":flag
					}, function(data) {
						if(data.length>5){
							window.parent['sysM_mainFrame'].location="<%=basePath%>sys/org/queryOrg?parentId="+data+"&st=1";
						}
						if(data=="9"){
							alert("操作出错了，请重新操作");
						}
						if(data=="1"){
							alert("该组织已经排在最前面了");
						}
						if(data=="2"){
							alert("该组织已经排在最后了");
						}
					});
		}
	
</script>
</head>
<body class="bg_c_g" onload="clearCookieDD();">
           <div class="gl_m_r_nav">当前位置 : 系统管理 > 组织架构 > 查询组织</div>
           <table class="gl_m_r_n_tb01"  border="0" cellspacing="0" cellpadding="0">
             <tr>
             <td width="29%" valign="top">
               
               <div class="gl_bt_bnt01">组织架构</div>
              
               <div class="gl_bnt_tree01">
                 <div class="gl_bnt_tree02" style="display: block;"><!--
                   <table  class="gl_bnt_tree03" border="0" cellspacing="0" cellpadding="0">
                     <tr>
                     <td><a href="javascript:void(0);"><img src="/SRMC/rmpb/images/gl_tab_b01.gif" width="25" height="25" onclick="move2('up')"/></a></td>
                     <td><a href="javascript:void(0);"><img src="/SRMC/rmpb/images/gl_tab_b02.gif" width="25" height="25" onclick="move2('down')"/></a></td>
                     <td width="30"></td>
                     <td><a href="javascript:void(0);"><img src="/SRMC/rmpb/images/gl_tab_b03.gif" width="25" height="25" /></a></td>
                     <td><a href="javascript:void(0);"><img src="/SRMC/rmpb/images/gl_tab_b04.gif" width="25" height="25" /></a></td>
                     </tr>
                   </table>
                    
                 --></div>
                 <!--tree-->
 					<div class="d_tree_div">
           <script type="text/javascript">
		d = new dTree('d');
				<c:forEach items="${orgs}" var="org">
					<c:choose>
					<c:when test="${org.orgName == '南方基地'}">
							d.add('${org.organizationId}', '-1', "<font loadMenu='${org.organizationId}' color=#EF5900><b>公司组织架构</b></font>", '<%=basePath%>sys/org/listOrg?mf2=y&parentId=${org.organizationId}&st=1&pid=${org.parentId}&index=Math.random();', '', 'sysM_mainFrame', '','//');
						</c:when>
						<c:otherwise>
							d.add('${org.organizationId}','${org.parentId}', "<font loadMenu='${org.organizationId}'>${org.orgName}</font>", '<%=basePath%>sys/org/listOrg?mf2=y&parentId=${org.organizationId}&st=1&pid=${org.parentId}&index=Math.random();', '', 'sysM_mainFrame');
						</c:otherwise>
					</c:choose>	
				</c:forEach>
			document.write(d);
			
			
			  function clearCookieDD(){
				    var st=null;    
				    st=document.getElementById("st").value;        
				    if(st==null||st==""){              
				       d.closeAll();
				    };   
				}
		
	</script>
		
    </div>
<!--tree END-->                 
               </div>
             </td>
             <td width="18" valign="top" class="gl_m_r_n_tb01_m"></td>
             <td valign="top">
             
               <div class="gl_bt_bnt01">
               <input type="button" class="gl_cx_bnt02" value="默认项目"  style="display: none;"/>
               <input type="button" class="gl_cx_bnt01" value="收起查询" onclick="toggleQueryDiv()" id="toggleQueryButton" />
               查询</div>
               <form name="form" method="post" action="<%=basePath%>sys/org/listOrg" id="pageForm">
               <div id="queryDiv" style="display: block;">
               <table class="gl_table_a01_1" align="center"  border="0" style="width: 70%;"
							cellspacing="0" cellpadding="0" id="queryDiv" style="display: block;">
							<tr>
								<td width="100" align="center" style="border: 1px rgb(207,208,208) solid;">
									<span style="font-size: 14px;font-weight: bold; margin-bottom: 1px;">组织名称：</span>
									<input type="hidden" value="N" name="isPages" id="isPages"/>
									<input type="hidden" value="${ mf2}" name="mf2" id="mf2"/>
									<input type="hidden" value="${ parentId}" name="parentId" id="parentId"/>
									
								</td>
								<td style="background-color: rgb(239,244,250);">
								&nbsp;
									<input class="gl_text01_a" style="width: 75%;" type="text"
										name="orgName" id="orgName" value="${form.orgName}" style="" />
									
								</td>
								<td width="165" style="background-color: rgb(239,244,250);" colspan="2" align="right">
									<input name="" type="button" onclick="doPageSearch()" class="ipt_tb_n03" value="搜 索" style="margin-bottom: 1px;" />
									<input name="" type="button" class="ipt_tb_n03" value="清 除" style="margin-bottom: 1px;" onclick="clean()"/>		
								</td>
							</tr>
						</table>
					</div>
               <!-- </form> -->
               <div class="ge_a01"></div>
               
               <div class="gl_bt_bnt01">组织机构列表</div>
               
             
             <!-- <form name="form2" method="post"> -->
             <input type="hidden"  id="orgIds" name="orgIds" />
              <input type="hidden" value="${st}" id="st"	name="st"/>
               <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <th>序号</th>
                <th>组织名称</th>
                <th>上级组织</th>
                
                <th width="160">操作</th>
                </tr>
                 <c:forEach items="${ITEMPAGE.items}" var="org" varStatus="i">
                <tr>
                  <td>${i.index+1}</td>
                  <td>${org[0].orgName}</td>
				  <td>${org[1].orgName}</td>
                  <td align="center"><span class="gl_tab_tr_l" style="margin-right: 2px;"><img src="/SRMC/rmpb/images/tab_tb01.png" align="middle"/></span><span class="gl_tab_tr_r"><a href="<%=basePath%>sys/org/sysOrgInfo/${back}/${org[0].organizationId}?Math.random()">查看</a></span></td>
                </tr>
                </c:forEach>
               </table>
               
              <div class="pageBox">
				<jsp:include flush="true" page="/public/include/navigate5.jsp"></jsp:include>
			</div>
			</form>
                </td>
             </tr>
           </table>
</body>
<script type="text/javascript" src="/SRMC/rmpb/js/list-common.js"></script>
</html>
