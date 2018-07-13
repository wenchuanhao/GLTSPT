<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.trustel.common.ItemPage"%>
<%@page import="org.trustel.system.SystemConstant"%>
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
	<title>业务配置</title>
	<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">

	function selectAll() {
		for (var i=0;i<form.length ;i++) {
			var e = form[i];
			if (e.name == 'itemOffset') {
	   			e.checked = form.checkAll.checked;
   			}
   		}
	}
	function getSelectCount() {
		var ids="";
		document.getElementById("userIds").value="";
		var chks = document.getElementsByName('itemOffset');
		var j = 0;
		for ( var i = 0; i < chks.length; i++) {
			if (chks[i].checked){
				j++;
				ids+=chks[i].value+",";
			}
		}
		document.getElementById("userIds").value=ids;
		return j;
	}

	function configUser(userId,optionId){
		var flowNodeId = window.parent.document.getElementById("flowNodeId").value;

		if(flowNodeId==""||null==flowNodeId){			    
			alert("请选择配置角色！");			
			return;
		}
		
		var option = document.getElementById(optionId);
		if(!option.checked){
			return;
		}
		var url ="<%=basePath%>sys/rolegroup/saveConfigUser";
		var params = {
			"userId":userId,
			"flowNodeId":flowNodeId,
		};
		jQuery.post(url, params, function(data){
				if(data=="0"){
					alert("添加失败");
				}else{
					var account = document.getElementById("account").value;
					var userName = document.getElementById("userName").value;
					var organizationId = document.getElementById("organizationId").value;
					var pageSize = document.getElementById("pageSize").value;
					var pageIndex = document.getElementById("pageIndex").value;
					//用户,配置
					window.parent.document.getElementById("toGetUser").src="<%=basePath%>sys/rolegroup/userList?flowNodeId="+flowNodeId + "&flowCode=${flowCode}&account="+account+"&userName="+userName+"&organizationId="+organizationId + "&pageSize=" +pageSize+"&pageIndex="+ pageIndex;
					window.parent.document.getElementById("userFlowActor").src="<%=basePath%>sys/rolegroup/getAllConfigUser?flowNodeId="+flowNodeId + "&flowCode=${flowCode}";
				}
		});
	} 
	
	 function doSelect(){
	    if (jQuery("#selBox").attr("checked") == true || jQuery("#selBox").attr("checked") == "checked") {
            var flowNodeId = window.parent.document.getElementById("flowNodeId").value;
			if(flowNodeId==""||null==flowNodeId){
				alert("请选择配置角色！");
				jQuery("#selBox").removeAttr("checked");
				return;
			}
            jQuery("input[name='checkbox']:not(':disabled')").each(function() {
                 jQuery(this).attr("checked","true");       
                 jQuery(this).click();   
                 setTimeout(function(){},200);          
           	});           	
			jQuery("#selBox").removeAttr("checked");
       }else{
  		  jQuery("input[name='checkbox']:not(':disabled')").each(function() {
   		  		jQuery(this).click(); 
  				jQuery(this).attr("checked",false);
	  	  });
       }
	 }
	 
	
</script>
</head>

<body class="bg_c_g">
	<form id="form2">
		<table class="gl_table_a02" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<input type="hidden" id="userIds" name="userIds" />
			<input type="hidden" name="account" id="account" value="${form.account }" />
			<input type="hidden" name="userName" id="userName" value="${form.userName }" />
			<input type="hidden" name="organizationId" id="organizationId" value="${form.organizationId }" />
			<input type="hidden" value="" id="flowNodeIdTemp1" name="flowNodeIdTemp1" />
			
			<tr class="tuan01a">
				<!--  <th width="32"></th> -->
				<th width="32">
					<input type="checkbox" id="selBox" onclick="doSelect();" />
				</th>
				<th>登录账号</th>
				<th>用户姓名</th>
			</tr>
			<c:forEach items="${ITEMPAGE.items}" var="org" varStatus="i">
				<tr <c:if test="${i.index%2 != 0}">class="tab_c_tr01"</c:if>>
					<td>
						<c:choose>
							<c:when test="${listFlow[org.userId] eq org.userId}">
								<input type="checkbox" name='checkbox' disabled="disabled"
									value="${org.userId}" checked="true" />
							</c:when>
							<c:otherwise>
								<input id="${fn:substring(org.userId,2,20)}" type="checkbox"
									name='checkbox' value="${org.userId}" name="itemOffset"
									onclick="configUser('${org.userId}','${fn:substring(org.userId,2,20)}')" />
							</c:otherwise>
						</c:choose>
					</td>
					<td>${org.account}</td>
					<td>${org.userName}</td>
				</tr>
			</c:forEach>
		</table>
		<div class="ge01"></div>
		<%
			//CHENJUFU MODIFY 20140107
			ItemPage itempage = (ItemPage) request
					.getAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE);
			int pagesize = 5;//每页显示记录数
			int liststep = 6;//最多显示分页页数
			int pages = 1;//默认显示第一页
			long count = 0;//假设取出记录总数
			int pagescount = 0;
			int listbegin = 0;
			int listend = 0;
			if (itempage != null) {
				count = itempage.getTotal();
				pages = itempage.getPageIndex();
				pagesize = itempage.getPageSize();
				pagescount = (int) Math.ceil((double) count / pagesize);//求总页数，ceil（num）取整不小于num
				if (pagescount < pages) {
					pages = pagescount;//如果分页变量大总页数，则将分页变量设计为总页数
				}
				if (pages < 1) {
					pages = 1;//如果分页变量小于１,则将分页变量设为１
				}
				listbegin = (pages - (int) Math.ceil((double) liststep / 2)) + 1;//从第几页开始显示分页信息
				if (listbegin < 1) {
					listbegin = 1;
				}
				if (pages < 3 && pagescount >= 5) {
					listend = 5;
				} else {
					listend = pages + liststep / 2;//分页信息显示到第几页
					if (listend > 5) {
						listend = listend - 1;
					}
					if (pagescount <= 5) {
						listend = pagescount;
					}
					if (listend > pagescount) {
						listend = pagescount;
						listbegin = pagescount - 4;
					}
				}
			}
		%>


		<div class="pageBox">
			<div class="page" style="width:600px; float:right;">
				<%
					if (pages > 1) {
				%>
				<a href="#" class="prev_b" onclick="javascript:gotoPage(1)">首页</a> <a
					href="javascript:void(0);"
					onclick="javascript:gotoPage(<%=pages - 1%>)" class="prev">上一页</a>
				<%
					}
				%>
				<%
					for (int i = listbegin; i <= listend; i++) {
						if (i == pages) {
				%>
				<a href="javascript:void(0);" onclick="javascript:gotoPage(<%=i%>)"
					class="current"><%=i%></a>
				<%
					} else {
				%>
				<a href="javascript:void(0);" onclick="javascript:gotoPage(<%=i%>)"><%=i%></a>
				<%
					}
					}
				%>
				<%
					if (pages != pagescount) {
				%>
				<a href="javascript:void(0);"
					onclick="javascript:gotoPage(<%=pages + 1%>)" class="next">下一页</a> <a
					href="javascript:void(0);"
					onclick="javascript:gotoPage(<%=pagescount%>)" class="next_b">尾页</a>
				<%
					}
				%>
			</div>
			<br />
			<div class="page" style="float:right; width:195px;">
				<span class="pageBox_font">每页显示:</span> <a
					href="javascript:void(0);" onclick="javascript:gotoPage('-10');"
					<%if (pagesize == 10) {%> class="current" <%}%>>10</a> <a
					href="javascript:void(0);" onclick="javascript:gotoPage('-20');"
					<%if (pagesize == 20) {%> class="current" <%}%>>20</a> <a
					href="javascript:void(0);" onclick="javascript:gotoPage('-50');"
					<%if (pagesize == 50) {%> class="current" <%}%>>50</a> <span
					class="pageBox_font" style="float:right;">页</span>
			</div>
		</div>

		<input type="hidden" name="pageIndex" id="pageIndex"
			value="<%=pages%>"> <input type="hidden" name="pageSize"
			id="pageSize" value="<%=pagesize%>">

		<script type="text/javascript">
	function gotoPage(pageaction) {
	    var flowNodeIdTemp=window.parent.document.getElementById("flowNodeId").value;
	    if(flowNodeIdTemp!=null&&flowNodeIdTemp!=""){
	       document.getElementById("flowNodeIdTemp1").value =flowNodeIdTemp;
	    }
	    /* alert(flowNodeIdTemp); */
		if(pageaction=='-10'){
			document.getElementById("pageIndex").value =1;
			document.getElementById("pageSize").value =10;
			window.parent.document.getElementById("pageSize1").value=10;
			window.parent.document.getElementById("pageIndex1").value=1;
		}else if(pageaction=='-20'){
			document.getElementById("pageIndex").value =1;
			document.getElementById("pageSize").value =20;
			window.parent.document.getElementById("pageSize1").value=20;
			window.parent.document.getElementById("pageIndex1").value=1;
		}else if(pageaction=='-50'){
			document.getElementById("pageIndex").value =1;
			document.getElementById("pageSize").value =50;
			window.parent.document.getElementById("pageSize1").value=50;
			window.parent.document.getElementById("pageIndex1").value=1;
		}else{
			document.getElementById("pageIndex").value = pageaction;
			//window.parent.document.getElementById("pageSize1").value=pageaction;
			window.parent.document.getElementById("pageIndex1").value=pageaction;
		}
		//return;
		var form1 = document.getElementById("form2");
		form1.method="post";
       form1.target="toGetUser";
       var action = window.location.href;
       if(action.indexOf("?")>0){
    	   action=action.replace(/&?userName=[^&]*&?/,"");
    	   action=action.replace(/&?account=[^&]*&?/,"");
    	   action=action.replace(/&?pageSize=[^&]*&?/,"");
           form1.action = action;
       }
       form1.submit();
	}

</script>
	</form>

</body>
</html>