<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
			<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
			<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />		
	</div>
	
	<form name="form" id="pageForm" method="post"  >
	
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${datacenterImpForm.pageIndex}" id="pageIndex" name="pageIndex"/>
    <input type="hidden" value="${datacenterImpForm.pageSize}" id="pageSize" name="pageSize"/>	
    	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">服务名称：</th>
	    <td width="30%">
	    	<input id="jobName" name="jobName" value="${datacenterImpForm.jobName }"  type="text"  placeholder="请填写服务名称" class="text01" style="width:195px;"  />
		</td> 
  		
  		<th width="9%" align="right">服务编码：</th>
	    <td width="30%">
	    	<input id="jobCode" name="jobCode" value="${datacenterImpForm.jobCode}"  type="text"  placeholder="请填写服务编码" class="text01" style="width:195px;" />
		</td>
	</tr>
	
	<tr>
		<th width="9%" align="right">创建时间：</th>
	    <td width="30%">
	    	<div class="date l" >
	    	<input readonly="readonly" name="beginCreateTime" id="beginCreateTime" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${datacenterImpForm.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
	                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
		<th width="9%" align="right"></th>
	    <td width="30%">
	    	<div class="date l" >
	    	<input readonly="readonly" name="endCreateTime" id="endCreateTime" type="text"  placeholder="请输入结束时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${datacenterImpForm.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
	                     	onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreateTime\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>
	</tr>
	
  	<tr>
  		<th width="9%" align="right">服务状态：</th>
	    <td width="30%">
	    	<select class="ui-select" id="serviceStatus" name="serviceStatus" style="width:200px;">
						<option value="">请选择</option>
						<option ${datacenterImpForm.serviceStatus eq '0' ? 'selected="selected"':''} value="0">未启动</option>
						<option ${datacenterImpForm.serviceStatus eq '1' ? 'selected="selected"':''} value="1">已启动</option>
	        </select>
		</td> 
	</tr>
	
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
      		<li class="current" >数据提供服务列表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<a class="btn_common01" href="javascript:ev_add()" /><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>
    		<a class="btn_common01" href="javascript:batchDel()" /><img src="/SRMC/dc/images/btnIcon/del.png" /><span>删除</span></a>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox" onclick="ev_checked()"></th>
			<th>服务名称</th>
			<th>服务编码</th>
			<th>服务类型</th>
			<th>接口类型</th>
			<th>任务运行时间表达式</th>
			<th>服务状态</th>
			<th>创建时间</th>
			<th>操作</th>
	  </tr>
	  
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="subBox" id="subBox" type="checkbox" value="${vo.jobId}"></td>
	  	 	<td>${vo.jobName}</td>
	  	 	<td>${vo.jobCode}</td>
		    <td>
					${vo.serviceType ne 'IMP' ? '':'数据获取'}
					${vo.serviceType ne 'EXP' ? '':'数据提供'}
		    </td>
		    <td>
					${vo.interfaceType ne 'WS' ? '':'webservice接口'}
					${vo.interfaceType ne 'FTP' ? '':'ftp文件传输接口'}
		    </td>
		    <td>${vo.cronExpression}</td>
		    <td>
					${vo.serviceStatus ne '0' ? '':'未启动'}
					${vo.serviceStatus ne '1' ? '':'已启动'}
		    </td>
		    <td><fmt:formatDate value="${vo.createDate}" pattern="yyyy-MM-dd "/></td>
		    <td >
		    	<%--<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a href="<%=basePath%>datacenterImpController/add?jobId=${vo.jobId}">明细</a></span> --%>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="<%=basePath%>datacenterExpController/add?jobId=${vo.jobId}">编辑</a></span>
		    	<c:if test="${vo.serviceStatus eq '0'}">
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="javascript:ev_start('${vo.jobId}')">启动</a></span>
		    	</c:if>
		    	<c:if test="${vo.serviceStatus eq '1'}">
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="javascript:ev_pause('${vo.jobId}')">暂停</a></span>
		    	</c:if>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${vo.jobId}')">删除</a></span>
		    </td>
		 </tr>
	   </c:forEach>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="9">找不到对应的数据</td></tr>
	   </c:if>	   
	</table>
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
<script type="text/javascript">
 $(document).ready(function(){
 	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
});
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>datacenterExpController/queryDcExpServices?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#jobName").val("");
	$("#jobCode").val("");
	$("#serviceStatus").data("ui-select").val("");
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
	ev_search();
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

function getIds(){
	var ids = "";
	$("input[id='ids']:checked").each(function(){ 
	   ids += ","+$(this).val();
	});
	return ids;
}

function ev_addIndex(){
	if($("input[id='ids']:checked").length > 0){
		if(confirm("确定添加到首页？")){
			$.ajax({
				type:"post",
				async:false,
				url: "<%=basePath%>tzbm/addIndex?type=Y&ids="+getIds(),
				dataType:"text",
				success:function(result){
					if(result != ""){
						alert("添加成功");
					}
				},
				error:function(){alert("添加失败");}
			});	
		}
	}else{
		alert("请选择一个投资编码");
	}
}

function ev_add(){
	document.forms[0].action="<%=basePath%>datacenterExpController/add";
	document.forms[0].submit();
}
function ev_submit(url){
	document.forms[0].action = url;
	document.forms[0].submit();
}
function ev_edit(id){
	ev_submit("<%=basePath%>tzbm/edit?id="+id);
}

function ev_export(){
	document.forms[0].action="<%=basePath%>tzbm/exportFile?ids="+getIds();
	document.forms[0].submit();
}

//删除服务
function ev_delete(id){
	if(confirm("确定删除该配置么？")){
		ajaxDel(id);
	}
}

function ajaxDel(id){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>datacenterExpController/delDcService",
        data:"id=" + id,
        success:function(data){
        	if(data == 1){
        		ev_search();
        	}else{
        		alert("删除失败！");
        	}
        },
        error:function(){
            alert("删除失败！");
        }
    });
}

//批量删除
function batchDel(){
	var $subBoxChecks = $("input[name='subBox']:checked");
	if($subBoxChecks.length == 0){
		alert("请至少选中一项服务");
		return;
	}
	if(confirm("确定批量删除"+$subBoxChecks.length+"项服务么？")){
		var rulesIds = '';
		$.each($subBoxChecks,function(k,v){
			if(k == 0){
				rulesIds += $(v).val();
			}else{
				rulesIds += ","+$(v).val();
			}
		});
		ajaxDel(rulesIds);
	}
}

//启动服务
function ev_start(id){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>datacenterImpController/startService",
        data:"id=" + id,
        success:function(data){
        	if(data == 1){
        		alert("启动成功！");
        		ev_search();
        	}else{
        		alert("启动失败！");
        	}
        },
        error:function(){
            alert("启动失败！");
        }
    });
}

//暂停服务
function ev_pause(id){
	jQuery.ajax({
        type:"POST",
        async:false,
        url:"<%=basePath%>datacenterImpController/pauseService",
        data:"id=" + id,
        success:function(data){
        	if(data == 1){
        		alert("暂停成功！");
        		ev_search();
        	}else{
        		alert("暂停失败！");
        	}
        },
        error:function(){
            alert("暂停失败！");
        }
    });
}

function ev_openFile(id,type){
	if(type == ""){
		alert("没有关联建设项目，不能上传文档");return;
	}
	var url = "<%=basePath%>tzbm/fileList?id="+id+"&type="+type;
	ev_submit(url);
	//window.showModalDialog(url,window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
}

function ev_zb(id){
	ev_submit("<%=basePath%>zb/add?proType=2&code="+id);
}

function ev_zxm(id){
	var url = "<%=basePath%>tzbm/zxmList?id="+id;
	ev_submit(url);
}

function ev_yearList(id){
	var url = "<%=basePath%>tzbm/yearList?parentId="+id;
	ev_submit(url);
}

//用户可选择创建人
function autoCompletes(target){
	jQuery(target).autocomplete({
		source: function( request, response ) {
			jQuery.ajax({
				url: "<%=basePath%>jsxm/searchUser",
				dataType: "json",
				data: {
					userValue: request.term
				},
				type: "POST",
				success: function(data) {
					if(data!=null){
			     		response(jQuery.map(data, function( item ) {
			     			return {
			     			 value:item[0].userName+"------"+item[0].orgName,
								userName:item[0].userName,
								userId:item[0].userId,
								account:item[0].account,
								orgName:item[0].orgName
							}
						}));
					}else{
						return false;
					}
				}
			});
		},
		minLength: 1,
		select: function( event, ui ) {
					jQuery("#column13").val(ui.item.userId);
					jQuery("#column13Name").val(ui.item.userName);
					return false;
		}
	});
}
</script>
</body>
</html>
