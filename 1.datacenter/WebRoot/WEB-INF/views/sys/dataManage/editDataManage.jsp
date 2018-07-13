<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新增数据获取</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
<!--<script type="text/javascript" src="/SRMC/rmpb/js/test.js"></script>-->
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
</head>
<script type="text/javascript">
		function doSave() {
		    if($("#sysId").val()==""){
		    	alert("请输入服务端系统ID");
		    	return false;
		    }
		    if($("#sysName").val()==""){
		    	alert("请输入服务端系统名称");
		    	return false;
		    }
		    if($("#frequency").val()==""){
		    	alert("请输入获取频率");
		    	return false;
		    }
			document.forms[0].submit();
	    }
		
		function canels(){
			 window.location.href = "<%=basePath%>sys/data/dataManage";
		}
		
		function doCloseSubpage(){
    		jQuery.fancybox.close();
		}
		
		function getSysManage(){
		return ;
		    var totalList = "";
		    var url = "<%=basePath%>sys/app/queryAjaxAppManageById";
		   
		    var params = {
		        id:jQuery("#sysId").val()
		    };
		    jQuery.post(url, params, function(data){
		        totalList = data;
		        var brandList = totalList;
		        var option = '<option value="">请选择</option>';
		        for(var i=0; i<brandList.length; i++){
		            option += '<option value="' + brandList[i] + '">' + brandList[i] + '</option>';
		        }
		        jQuery("#type").html(option);
		    } , 'json');
		}
		
		function selectSysManage(){
		    jQuery("#sysManages").attr("href", "<%=basePath%>sys/data/selectSysManage" );
		    jQuery("#sysManages").fancybox({
	        'width'				: '65%',
	        'height'			: 350,
	        'scrolling'			:'yes',
	        //'centerOnScroll'	: true,
	        'autoScale'			: false,
	        'transitionIn'		: 'none',
	        'transitionOut'		: 'none',
	        'type'				: 'iframe'
	   	     });
	    }
</script>
<body class="bg_c_g" >
	<form action="<%=basePath%>/sys/data/addDataManage?add=update"  method="post" name="moduleForm">
	  <div class="gl_m_r_nav">当前位置 : 系统管理 &gt;数据获取管理  &gt; 编辑数据获取</div>
	 <div class="gl_import_m">
           <div class="gl_bt_bnt01">数据信息</div>
            <table class="gl_table_a023" width="100%" border="0" cellspacing="0" cellpadding="0">
            <input type="hidden" id="id" name="id" value="${list[0].id }"/>
            <input type="hidden" id="createDate" name="createDate" value="${list[0].createDate }"/>
       		<input type="hidden" id="createUserId" name="createUserId" value="${list[0].createUserId }"/>
       		<input type="hidden" id="createUserName" name="createUserName" value="${list[0].createUserName }"/>
       		<input type="hidden" class="myClass" name="sysId" id="sysId"/>
            <input type="hidden" class="myClass" name="type" id="type" value="${list[0].type }"/>
            
               <tr>
                 <th width="100">服务端系统名称:</th>
                 <td ><!-- style="display:none" -->
                 <a id="sysManages"><input id="sysName" name="sysName"  readonly="readonly"  class="select_new01" onclick="selectSysManage();"  value="${list[0].sysName }" type="text"/></a>
				 </td>
                 <th width="100">接入的接口类型:</th>
                 <td>
		 	 		<%-- <select class="select_new01" id="type" name="type">
	                 	<option value="0" <c:if test="${list[0].type=='0' }">selected="selected"</c:if>  >ws接口</option>
	                 	<option value="1" <c:if test="${list[0].type=='1' }">selected="selected"</c:if> >文件接口</option>
	                 	<option value="2" <c:if test="${list[0].type=='2' }">selected="selected"</c:if> >sql接口</option>
                 </select> --%>
                 <input class="gl_text01_a" type="text" name="type2" id="type2" 
                 <c:if test="${list[0].type=='0' }">value="ws接口"</c:if>  
                 <c:if test="${list[0].type=='1' }">value="文件接口"</c:if>  
                 <c:if test="${list[0].type=='2' }">value="sql接口"</c:if>  
                   readonly="readonly" />
			 	</td>
               </tr>
               <tr>
                 <th>获取频率:
                </th>
                 <td>
                 	<input class="gl_text01_a" type="text" name="frequency" id="frequency"  value="${list[0].frequency }" />
                 </td>
               </tr>
               <tr>
					<th width="100">
						模块描述:
					</th>
					<td colspan="6" >
						<textarea name="remark" id=remark class="gl_text01_c"  >${list[0].remark }</textarea>
					</td>
				</tr>
             </table>
        
            <div class="gl_ipt_03">
		        <input name="input" type="button" class="gl_cx_bnt03" value="保 存" onclick="doSave();"/>&nbsp;
		        <input name="input" type="button" class="gl_cx_bnt03" value="取 消" onclick="canels();"/>
            </div>
        </div>
	</form>	
	
</body>
</html>
