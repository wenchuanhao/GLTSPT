<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>日志管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/SRMC/rmpb/css/style.css" type="text/css" rel="stylesheet"/>
<link href="/SRMC/rmpb/css/date_input.css" type="text/css"  rel="stylesheet" />
<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
<script type="text/javascript" src="/SRMC/rmpb/js/rmpcommon.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/time.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
<script>
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


$(function() { 
$("#startDate").date_input();
$("#endDate").date_input(); 
}); 
</script>
<script type="text/javascript">
function doSea(){
		var endDate =$("#endDate").val();
		var startDate = $("#startDate").val();
		if(endDate!="" && startDate!=""){
			if(!isStartEndDate(startDate,endDate)){    
	                    alert("开始日期不能大于结束日期");   
	                    return false;   
	        }else{
	         	doSearch();   
	          }   
         }else{
         		doSearch(); 
         }
        

}
function isStartEndDate(startDate,endDate){   
               if(startDate.length>0&&endDate.length>0){   
                   var startDateTemp = startDate.split(" ");   
                   var endDateTemp = endDate.split(" ");   
                   var arrStartDate = startDateTemp[0].split("-");   
                   var arrEndDate = endDateTemp[0].split("-");   
                    var allStartDate = new Date(arrStartDate[0],arrStartDate[1],arrStartDate[2]);   
                    var allEndDate = new Date(arrEndDate[0],arrEndDate[1],arrEndDate[2]);   
                    if(allStartDate.getTime()>allEndDate.getTime()){   
                        return false;   
                    }   
                }   
                return true;   
            } 
</script>
</head>

<body class="main_bg">
  <form name="form" method="post" action="<%=basePath%>sys/log/querySysLog">
  	<input type="hidden"  id="userIds" name="userIds" />
    <div class="mm_main_bj">
    	<div class="mm_main_top01">
    		<span class="mm_main_top01a">
    			<img src="/SRMC/rmpb/images/005.png" width="33" height="32" />
    		</span>
    		<span class="mm_main_top01c">当前位置：系统管理 &gt; 日志管理 &gt; <span class="or">日志列表</span></span>
    </div>
    <div class="mm_main_bd01">
      <div class="mm_m_bd_a">
        <span class="mm_m_bd_b">
        	<img src="/SRMC/rmpb/images/mm_pic07.gif" width="38" height="35" />
        </span>
        <span class="mm_m_bd_c">查询</span>
      </div>
      
      <div class="ge01"></div>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table03">
	        <tr>
	          <th width="10%" >
	          	操作模块
	          </th>
	          <td width="20%">
	          	<input class="blue_bt_a" type="text" name="moduleName" value="${form.moduleName}" />
	          </td>
	          <th width="10%" >
	          	操作人
	          </th>
	          <td width="20%" >
	          	<input class="blue_bt_a" type="text" name="operatiorName" value="${form.operatiorName}" />
	          </td>
	          <th width="10%" >
	          	所属组织
	          </th>
	          <td width="20%" >
	          	<input class="blue_bt_a" type="text" name="operatiorOrg" value="${form.operatiorOrg}" />
	          </td>
	        </tr>
	        <tr>
	          <th width="10%" >
	          	开始时间
	          </th>
	          <td width="20%">
	          	<input class="blue_bt_a" type="text" name="startDate" id="startDate"   value="${form.startDate}" />
	          </td>
	          <th width="10%" >
	          	结束时间
	          </th>
	          <td width="20%" >
	          	<input class="blue_bt_a" type="text" name="endDate" id="endDate"  value="${form.endDate}" />
	          </td>
	          <td width="30%" colspan="2">
	          	<input class="bnt_class07" type="button" name="button3" id="button3" value="查 询" onclick="doSea()"/>
	          	<input class="bnt_class08" type="reset" name="button3" id="button6" value="重 置" />
	          </td>
	        </tr>
	      </table>
      <div class="ge02"></div>
  	</div>
  
      
     <div class="mm_main_bd01">
	      <div class="mm_m_bd_a">
	        <span class="mm_m_bd_b">
	        	<img src="/SRMC/rmpb/images/mm_pic04.gif" width="38" height="35" />
	        </span>
	        <span class="mm_m_bd_c">日志管理</span>
	      </div>
	      
      <div class="ge01"></div>
	      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="main_table01">
	        <tr>
	          <th width="15%">操作模块</th>
	          <th width="10%">操作方法</th>
			  <th width="10%">操作人</th>
	          <th width="10%">所属组织</th>
	          <th width="30%">操作描述</th>
			  <th width="15%">操作时间</th>
	          <th width="10%">IP</th>
	        </tr>
			<c:forEach items="${ITEMPAGE.items}" var="log" varStatus="i">
				  <tr <c:if test="${i.index%2 != 0}">class="main_table04_bg"</c:if> style="cursor: hand;" title="${log.description}">
		        
		          <td>${log.moduleName}</td>
		          <td>${log.functionName}</td>
				  <td>${log.operatiorName}</td>
		          <td>${log.operatiorOrg}</td>
		          <td >  
		          	<c:choose>  
					 	<c:when test="${fn:length(log.description) > 20}">  
					       <c:out value="${fn:substring(log.description, 0, 20)}..." />  
					    </c:when>  
					   <c:otherwise>  
					     <c:out value="${log.description}" />  
					   </c:otherwise>  
					</c:choose>  
		          	
		          </td>
				  <td>
				  	<fmt:formatDate value="${log.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				  </td>
				  <td>${log.operatiorIp}</td>
		        </tr>
			</c:forEach>
	      	</table>
			<div class="hy_next">
				<jsp:include flush="true" page="/public/include/navigate.jsp"></jsp:include>
			</div>
			<div class="ge02"></div>
		</div>
	</div>
	<div class="ge01"></div>
	</form>
</body>
</html>