<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增问题</title>
		<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
		<link href="/SRMC/rmpb/css/date_input.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/rmpb/js/common2.js"></script>
		<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
		<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<form  action="" method="post" id="submitForm">
<!-- 遮罩内容开始 -->
<input type="hidden" name="orderid" id="orderid" value="${orderid }" >
<input type="hidden" name="orderid" id="orderid" value="${orderid }" >
<input type="hidden" name="id" id="id" value="${problemList.id }" >
<div class="taskCreatBox">
            <div class="box_title" style="color: #4084b6;">
               <c:if test="${empty problemList.id  }">新增问题</c:if><c:if test="${not empty problemList.id  }">编辑问题</c:if> <span><a href="#none"></a></span>
            </div>
            <div class="box_task">
            <table cellpadding="0" cellspacing="5" class="taskCreate" width="100%">
            <tr>
               <th width="35%"><em>*</em>问题提出阶段：</th>
               <td><select name="phase" class="ui-select" id="sel_01" style="width:180px;">
		          <option value="">请选择</option>
		          <c:forEach items="${sysParameter}" var="list" varStatus="i">
         		  	<option <c:if test="${list.parameterCode== problemList.phase  || list.parameterCode== draftList[0].phase}">selected="selected" </c:if> value="${list.parameterCode }">${list.parameterName }</option>
         		  </c:forEach>
        		</select>
        	  </td>
            </tr>
            <tr>
               <th><em>*</em>问题类型：</th>
               <td>
              		<c:forEach items="${proType}" var="list" varStatus="i">
              			<span><input value="${list.parameterCode }" 
              			<c:if test="${problemList.type eq list.parameterCode || fn:contains(type_list,list.parameterCode) }">checked="checked"</c:if> 
              			<c:if test="${empty problemList}">type="checkbox" </c:if>
              			<c:if test="${not empty problemList}">type="radio" </c:if>
              			name="type" id="type_${list.parameterCode }">${list.parameterName }</span>
              		</c:forEach>
		      </td>
            </tr>
            <tr>
               <th><em>*</em>问题详情：</th>
               <td>
               <div class="it_wrap">
                 <!-- <i>报修单号</i> -->
                <!--  <input class="txt01"  type="text" value="0005" /> -->
                 <i>报账单号:<em>${orderid }</em></i>
               <textarea name="opitionContent" id="opitionContent"  cols="" rows="" class="textArea01" >${problemList.detail }${draftList[0].detail }</textarea>
               </div>
               </td>
            </tr>
            <tr>
               <th>保存到常用问题详情：</th>
               <td><select class="ui-select" id="sel_04" name="personalOpinionId"   onchange="selectPersonalOpinion();" style="width:180px;">
          			<option selected="selected" value="-1" id="noperson">选择个人常用意见</option>
                	<c:forEach items="${personalOpinionList}" var="personalOpinionList" varStatus="i">
                    <option title="${personalOpinionList.content }" value="${personalOpinionList.personalOpinionId}">${personalOpinionList.outline} </option>
                	</c:forEach>
        		</select>&nbsp;<input name="" type="button" class="btn_common02" value="保存常用意见" onclick="savePersionalOpinion();"/>
        		<input
							name="" type="button" class="btn_common02" value="删除选中意见"
							onclick="delPersionalOpinion();" />
        		</td>
            </tr>
             <tr>
               <th><em>*</em>通知整改：</th>
           		<td>
           		<c:choose> 
				     <c:when test="${draftList[0].remindWay eq '1'}">
				     	<span><input type="checkbox"  checked="checked" name="remindWay" id="remindWay" value="1"> 短信</span>
		           		<span><input type="checkbox"  name="remindWay" id="remindWay" value="0"> 邮件</span>
				     </c:when>
				     <c:when test="${draftList[0].remindWay eq '0'}"> 
				     	<span><input type="checkbox"  name="remindWay" id="remindWay" value="1"> 短信</span>
		           		<span><input type="checkbox"  checked="checked" name="remindWay" id="remindWay" value="0"> 邮件</span>
				     </c:when> 
				     <c:when test="${draftList[0].remindWay eq '1,0'}"> 
		           		<span><input type="checkbox"  checked="checked" name="remindWay" id="remindWay" value="1"> 短信</span>
		           		<span><input type="checkbox"  checked="checked" name="remindWay" id="remindWay" value="0"> 邮件</span>
				     </c:when> 
				     <c:otherwise> 
		           		<span><input type="checkbox" name="remindWay" id="remindWay" value="1"> 短信</span>
		           		<span><input type="checkbox" name="remindWay" id="remindWay" value="0"> 邮件</span>
				     </c:otherwise>
			    </c:choose>
           		</td>
            </tr>
             <tr>
               <td colspan="2" align="center">
               <c:if test="${empty problemList.id  }">
               		<input name="" type="button" class="btn_common01" onclick="saveProblem(1)" value="暂 存" /> 
               </c:if> 
               <input name="" type="button" onclick="saveProblem(2)" class="btn_common02" value="保存并通知整改" /></td>
              </tr>
            </table>
             
          </div>
        </div>
<!-- 遮罩内容结束 -->
</form>
<script src="/SRMC/dc/account/js/jquery.js"></script>
<script src="/SRMC/dc/account/js/ui-select.js"></script>
	<!-- 	表单异步提交 -->
	<script src="/SRMC/rmpb/js/jquery.form.js"></script>
<script>

var obj2;
$(function (){
	// 将所有.ui-select实例化
	$('.ui-select').ui_select();
	// 获取已经实例化的select对象
	obj2 = $('#sel_04').data('ui-select');
});	

function delPersionalOpinion(){
    if(jQuery.trim(jQuery("#sel_04").val()) == "-1"){
        alert("请选择要删除的常用意见");
        return;
    }else{
        var appid=jQuery.trim(jQuery("#sel_04").val());
        jQuery.ajax({
            type:"POST",
            async:false,
            url:"<%=basePath%>account/deletePersonalOpinion",
            data:"personalOpinion_Id="+appid,
            success:function(data){
                jQuery("#sel_04").children("[value='" + appid + "']").remove();
                //obj2.remove(appid);
                $("#sel_04").parent().find(".ui-select-list").remove(appid);
                $("#sel_04").ui_select();
                alert("删除常用意见成功！");
            },
            error:function(){
                alert("删除常用意见失败！");
            }
        });
    }
}

function selectPersonalOpinion(){
	    if(jQuery("#sel_04").val() == "-1"){
	        jQuery("#opitionContent").html("");
	    }else{
	        var url = "<%=basePath%>account/queryPersonalOpinionById";
	        var params = {
	            personalOpinionId:jQuery("#sel_04").val()
	        };
	        jQuery.post(url, params, function(data){
	            jQuery("#opitionContent").val(data);
	        });
	    }
	}

//staus1 保存  staus2保存并通知整改
function saveProblem(staus){
		var types = "";
		jQuery("input[name='type']:checked").each(function(){
			types += jQuery(this).val()+",";
		});
		var ids ="";
		jQuery("input[name='remindWay']:checked").each(function(){
			ids+=""+jQuery(this).val()+"";
		});
	if(staus == "2"){
		if(jQuery.trim(jQuery("#sel_01").val()) == ""){
			alert("请选择问题提出阶段");
			return;
		}
		//问题类型
		if(jQuery.trim(types) == ""){
			alert("请选择问题类型");
			return;
		}
	
		if(jQuery.trim(jQuery("#opitionContent").val()) == ""){
			alert("请输入问题详情");
			return;
		}
		if(jQuery.trim(jQuery("#opitionContent").val()).length>200){
			alert("问题详情字符过多，应小于200个字符");
			return;
		}
		//通知方式
		if(ids==""){
			alert("请选择通知整改方式");
			return false;
		}
	}
	
	$("#submitForm").ajaxSubmit({
       	url:"<%=basePath%>account/saveProblem?staus="+staus+"&types="+types,
    	dataType:'json',
        async:true,
        type:'POST',
        success:function (result) {
        	if(result == 1 || result == 2){
	        	parent.window.location.reload();
        	}else{
        		alert("新增问题保存失败");
        	}
        },
        error:function () {
        	alert("新增问题保存失败");
        }
    });
}

function savePersionalOpinion(){
	    if(jQuery.trim(jQuery("#opitionContent").val()) == ""){
	        alert("请输入问题详情");
	        return;
	    }else{
	        if(jQuery("#opitionContent").val().length >300){
	            alert("问题详情不可超过300个字符,保存失败!");
	            return;
	        }
	        var url = "<%=basePath%>account/savePersonalOpinion";
	        var params = {
	            opitionContent:jQuery("#opitionContent").val()
	        };
	        jQuery.post(url, params, function(data){
	        	var datas = data.split("@#@");
	        	var flag = datas[0];
	        	var option = datas[1];
	            if(flag == "1"){
	            	$("#sel_04").append(option);
	            	$("#sel_04").parent().find(".ui-select-list").append('<li class="" data-value="'+datas[3]+'" title="'+datas[2]+'">'+datas[2]+'</li>');
		        	$("#sel_04").ui_select();
	                alert("保存问题详情成功！");
	            }else{
	                alert("该意见已保存为问题详情，无需重复操作！");
	            }
	            
	        });
	    }
	}

</script>        
</body>
</html>