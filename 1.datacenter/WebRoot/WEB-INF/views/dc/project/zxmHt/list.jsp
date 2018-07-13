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
<!-- 联想查询 -->
<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="/SRMC/rmpb/js/isIe.js"></script>
<jsp:include page="../../sys/dict/include/dict_vars.jsp">
	<jsp:param name="dict" value="PROJECT_TYPE"></jsp:param>
</jsp:include>
</head>
<body>
<div class="gl_import_m">
	<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
			<input name="" onclick="ev_reset()" type="button" class="btn_reset r" value="重置" />
			<input name="" onclick="ev_search()" type="button" class="btn_search r" value="查询" />
	</div>
	
	<form name="form" id="pageForm" method="post"  >
	
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${zxmHt.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${zxmHt.pageSize}" id="pageSize"	name="pageSize"/>	
    <input type="hidden" name="modifyColumn21" id="modifyColumn21Ids"/>	
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  	<tr>
  		<th width="9%" align="right">项目类型：</th>
	    <td width="30%">
	        <select id="xmType" name="xmType" class="ui-select" style="width:200px;" defaultValue="${zxmHt.xmType }"  dictType="PROJECT_TYPE"/>
		</td> 
  		
  		<th width="9%" align="right">合同名称：</th>
	    <td width="30%">
	    	<input id="column03" name="column03" value="${zxmHt.column03}"  type="text"  placeholder="请填写合同名称" class="text01" style="width:195px;"  />
		</td> 
  	
	</tr>
	
	<tr>
		<th width="9%" align="right">合同管理员：</th>
	    <td width="30%">
	    	<input type="hidden" name="column21" id="column21"  value="${zxmHt.column21}"/>
	    	<input id="column21Name" name="column21Name" value="${zxmHt.column21Name}"  onclick="$('#column21').val('');$('#column21Name').val('');"
	    	onfocus="autoCompletes(this);" type="text"  placeholder="请填写合同归属人" class="text01" style="width:195px;"  />
		</td> 
		<th width="9%" align="right">子项目编号：</th>
	    <td width="30%">
	    	<input id="column04" name="column04" value="${zxmHt.column04}"  type="text"  placeholder="请填写子项目编号" class="text01" style="width:195px;"  />
		</td>
	</tr>
	
	<tr>
  		
  		<th width="9%" align="right">合同编号：</th>
	    <td width="30%">
	    	<input id="column01" name="column01" value="${zxmHt.column01}"  type="text"  placeholder="请填写合同编号" class="text01" style="width:195px;"  />
		</td> 		 
  		<th width="9%" align="right">认领状态：</th>
	    <td width="30%" >
	    	<select class="ui-select" id="rlStatus" name="rlStatus" style="width:200px;">
						<option value="">请选择</option>
						<option ${zxmHt.rlStatus eq 'Y' ? 'selected="selected"':''} value="Y">已认领</option>
						<option ${zxmHt.rlStatus eq 'N' ? 'selected="selected"':''} value="N">未认领</option>
	        </select>
		</td>
  		
	</tr>
	
	<tr>
		<th width="9%" align="right">合同签订时间：</th>
	    <td width="30%" >
	    	<div class="date l" >
	    	<input readonly="readonly" name="beginCreateTime" id="beginCreateTime" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${zxmHt.beginCreateTime}" pattern="yyyy-MM-dd"/>"
	                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>  		
		<th width="9%" align="right"></th>
	    <td width="30%" >
	    	<div class="date l" >
	    	<input readonly="readonly" name="endCreateTime" id="endCreateTime" type="text"  placeholder="请输入结束时间"  class="text02 l" 
	    					value="<fmt:formatDate value="${zxmHt.endCreateTime}" pattern="yyyy-MM-dd"/>"
	                     	onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreateTime\')||\'%y-%M\'}', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	        </div>
		</td>  		
	</tr>
	
	<tr>
  		<th width="9%" align="right">预算项目：</th>
	    <td width="30%">
	    	<input id="column15" name="column15" value="${zxmHt.column15}"  type="text"  placeholder="请填写预算项目" class="text01" style="width:195px;"  />
		</td>
	 	<th width="9%" align="right">科室：</th>
	    <td width="30%" >
			<select class="ui-select"  style="width:200px;" id="ks" name="ks" >
			<option value="">请选择</option>
			<option ${zxmHt.ks eq '0313f5eef20914f55aae79b647b79fdf' ? 'selected="selected"':''} value="0313f5eef20914f55aae79b647b79fdf">网络建设室</option>
			<option ${zxmHt.ks eq '379725f12591b6c2dad22d72178917fd' ? 'selected="selected"':''} value="379725f12591b6c2dad22d72178917fd">项目管理室</option>
			<option ${zxmHt.ks eq '48300b5a9c196bc8ab1c3e5398441c34' ? 'selected="selected"':''} value="48300b5a9c196bc8ab1c3e5398441c34">工程建设室</option>
			</select>
		</td>		
	</tr>	
	</table>
	</form>
  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >子项目合同列表<em></em></li>
    	</ul>
    	<div class="otherButtons r">
    		<c:if test="${role ne null && role ne 'GC_QTRY'}">
    		<a class="btn_common01" href="javascript:ev_add()" /><img src="/SRMC/dc/images/btnIcon/add.png" /><span>新增</span></a>
    		<c:if test="${zxmHt.rlStatus eq 'N'}">
    		<a class="btn_common01" href="javascript:ev_rl('')" /><img src="/SRMC/dc/images/btnIcon/add.png" /><span>认领</span></a>
    		</c:if>
    		<a class="btn_common01" href="javascript:ev_export()" /><img src="/SRMC/dc/images/btnIcon/export.png" /><span>导 出</span></a>
    		</c:if>
		</div>    	
  	</div>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	  <tr>
	  		<th><input id="checkAll" type="checkbox" onclick="ev_checked()"></th>
			<th>子项目编号</th>
			<th>子项目名称</th>
			<th>合同编号</th>
			<th>合同名称</th>
			<th>预算项目</th>
			<th>合同含税金额<br/>（万元）</th>
			<th>合同对方</th>
			<th>合同状态</th>
			<th>合同签订时间</th>
			<th>合同管理员</th>
			<th>创建时间</th>
			<th>操作</th>
	  </tr>
	  	 <c:if test="${not empty ITEMPAGE.items}">
	  <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	 <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if> id="">
	  	  	<td ><input name="ids"  id="ids"  type="checkbox" value="${vo.id}" /></td>
	  	  	<c:set var="zxm" value="${vo.zxm}"/>
		    <td style="word-break:break-all" width="12%">${zxm.column02}</td>
		    <td style="word-break:break-all" width="15%">${zxm.column03}</td>
		    <td style="word-break:break-all" width="8%"><span><a href="javascript:ev_edit('${vo.id}')">${vo.column01}</a></span></td>
			<td style="word-break:break-all" width="15%">${vo.column03}</td>
			<td style="word-break:break-all" width="15%">${vo.column15}</td>
			<td><fmt:formatNumber value="${vo.column11}" type="currency"  maxFractionDigits="6"/></td>
			<td>${vo.column14}</td>
			<td>${vo.column12}</td>
		    <td><fmt:formatDate value="${vo.column19}" pattern="yyyy-MM-dd "/></td>
			<td>${vo.column21Name}</td>
			<td><fmt:formatDate value="${vo.createDate}" pattern="yyyy-MM-dd "/></td>
		    <td>
		    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/export.png" /><a href="javascript:ev_openFile('${vo.id}','${vo.xmType}')">文档</a></span>
	    		<c:if test="${vo.column21 eq null}"><span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="javascript:ev_rl('${vo.id}')">认领</a></span></c:if>
	    		<c:if test="${vo.column21 ne null && fn:contains(vo.column21, sysUserId)}"><span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="javascript:ev_cancelRL('${vo.id}')">取消认领</a></span></c:if>
	    		<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/read.png"><a  href="javascript:ev_htKz('${vo.id}')">合同开支</a></span><br/>
		    	<c:if test="${role ne null && (role eq 'GC_YWGLY' or (role eq 'GC_XMFZR' && fn:contains(vo.column21, sysUserId)))}">
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/publish.png"><a href="javascript:ev_zb('${vo.id}')">周报</a></span>
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/modify.png"><a href="javascript:ev_modify('${vo.id}')" >编辑</a></span>
			    	<span><img style="height:12px;width:12px;" src="/SRMC/dc/images/btnIcon/del.png"><a href="javascript:ev_delete('${vo.id}')">删除</a></span>
		    	</c:if>
		    </td>
		 </tr>
	   </c:forEach>
	  	 <tr style="background-color: #d6ebf9;">
			<td colspan="6">合计：</td>
			<td><fmt:formatNumber value="${zxmHt_T[0]}" type="currency"  maxFractionDigits="6"/></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		 </tr>	   
	   </c:if>
	   <c:if test="${empty ITEMPAGE.items}">
	   <tr><td colspan="13">找不到对应的数据</td></tr>
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
function ev_htKz(id){
	window.open("<%=basePath%>zxmHt/htKzList?id="+id);
}
//提交查询
function ev_search(){
	document.forms[0].action="<%=basePath%>zxmHt/list?key="+Math.random();
	document.forms[0].submit();
}

//重置查询
function ev_reset(){
	$("#xmType").data("ui-select").val("");
	$("#rlStatus").data("ui-select").val("");
	$("#column03").val("");
	$("#column21").val("");
	$("#column04").val("");
	$("#column01").val("");
	$("#beginCreateTime").val("");
	$("#endCreateTime").val("");
	$("#column15").val("");
	$("#ks").data("ui-select").val("");
	ev_search();
}
function ev_submit(url){
	document.forms[0].action = url;
	document.forms[0].submit();
}
function ev_edit(id){
	window.open("<%=basePath%>zxmHt/edit?pageSource=zxmHt_list&id="+id);
}
function ev_modify(id){
	window.open("<%=basePath%>zxmHt/add?id="+id);
}
function ev_add(){
	window.open("<%=basePath%>zxmHt/add");
}

function ev_delete(id){
	if(confirm("确定删除？")){
		jQuery.ajax({
	        type:"POST",
	        async:true,
	        url:"<%=basePath%>zxmHt/delete",
	        data:"id=" + id,
	        dataType:"json",
	        success:function(data){
	        	if(data == "1"){
	        		alert("删除成功");
	        		ev_search();
	        	}else{
	        		alert("删除失败");
	        	}
	        },
	        error:function(){
	        	alert("删除失败");
	        }
    	});
	}
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
    /* //兼容谷歌  
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
    } */
function ev_selectList(){
	//判断是不是IE浏览器
	if(isIE()){
		var returnValue = window.showModalDialog("<%=basePath%>zxm/selectList?source=zxmHt",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
		modifyColumn21(returnValue);
	}else{
		//其他浏览器
		window.open("<%=basePath%>zxm/selectList?source=zxmHt",window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px");
	}
}

function getIds(){
	var ids = "";
	$("input[id='ids']:checked").each(function(){ 
	   ids += ","+$(this).val();
	});
	return ids;
}

function ev_rl(id){
	var ids = "";
	ids = id;
	
	var a = getIds();
	if(a != ""){
		ids = a;
	}
	
	if(id == ""){
		if($("input[id='ids']:checked").length == 0){
			alert("请选择一个合同");return;
		}
	}
	
	//if(confirm("确定认领合同？")){
		$("#modifyColumn21Ids").val(ids);
		ev_selectList();
		//var returnValue = ev_selectList();
		//modifyColumn21(returnValue);
	//}
}



function modifyColumn21(returnValue){
	var ids = $("#modifyColumn21Ids").val();
	if(returnValue && returnValue.indexOf("_") != -1){
		$.ajax({
			type:"post",
			async:false,
			url: "<%=basePath%>zxmHt/modifyColumn21?type=N&ids="+ids+"&returnValue="+returnValue,
			dataType:"text",
			success:function(result){
				if(result != "" && result == "s"){
					alert("认领成功");
					ev_search();
				}else{alert("认领失败");}
			},
			error:function(){alert("认领失败");}
		});	
	}
}

function ev_cancelRL(id){
	var ids = "";
	ids = id;
	
	var a = getIds();
	if(a != ""){
		ids = a;
	}
	
	if(id == ""){
		if($("input[id='ids']:checked").length == 0){
			alert("请选择一个合同");return;
		}
	}
	
	if(confirm("确定取消认领合同？")){
		$.ajax({
			type:"post",
			async:false,
			url: "<%=basePath%>zxmHt/modifyColumn21?type=Y&ids="+ids,
			dataType:"text",
			success:function(result){
				if(result != ""){
					alert("取消成功");
					ev_search();
				}
			},
			error:function(){alert("取消失败");}
		});	
		}
}

function ev_openFile(id,type){
	if(type == ""){
		alert("没有关联子项目，不能上传文档");return;
	}
	window.open("<%=basePath%>zxmHt/fileList?id="+id+"&type="+type);
}

function ev_zb(id){
	ev_submit("<%=basePath%>zb/add?proType=4&code="+id);
	//window.showModalDialog("<%=basePath%>zb/add?proType=4&code="+id,window,"dialogWidth:"+window.screen.width+"px;dialogHeight:"+window.screen.height+"px"); 
}

function ev_export(){
	document.forms[0].action="<%=basePath%>zxmHt/exportFile?ids="+getIds();
	document.forms[0].submit();
	document.forms[0].action="<%=basePath%>zxmHt/list?key="+Math.random();
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
					jQuery("#column21").val(ui.item.userId);
					jQuery("#column21Name").val(ui.item.userName);
					return false;
		}
	});
}

</script>
</body>
</html>
