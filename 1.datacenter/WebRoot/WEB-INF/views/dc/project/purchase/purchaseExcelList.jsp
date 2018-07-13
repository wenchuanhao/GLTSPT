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

  	<div class="ge01"></div>
  	<div class="tabpages">
    	<ul class="l">
	      		<li class="current" >excel列表预览<em></em></li>
    	</ul>
    	<input name="" type="button" class="btn_common04" onclick="ev_list()" value="返 回" /> 
    	<!--  <input name="" type="button" class="btn_common04" onclick="ev_cover()" value="覆盖提交" /> -->
    	<!-- <input name="" type="button" class="btn_common04" onclick="ev_uncover()" value="非覆盖提交" /> -->
    	<input id="commitButton" name="" type="button" class="btn_common04" onclick="ev_save()" value="提交" />   	 	  	 	   	   	 	  	 	
  	</div>
   <form name="form" id="pageForm" method="post"  >
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${cooperationForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${cooperationForm.pageSize}" id="pageSize"	name="pageSize"/>
   </form>	
   <table  style="table-layout:fixed;" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
	 <!--   <c:forEach items="${headInfo}" var="ho" varStatus="i">
	    <c:if test="${i.index==0 }">
	     <tr>
	       <c:forEach begin="0" end="${fn:length(ho)-1}" varStatus="st">
	          <c:if test="${ho[st.index]!='' && ho[st.index+1]!='' }">
	             <th rowspan="2"> ${ho[st.index]}</th>
	          </c:if>
	          <c:if test="${ho[st.index]!='' && ho[st.index+1]=='' }">
	             <th colspan="2"> ${ho[st.index]}</th>
	          </c:if>	       
	       </c:forEach>
	     </tr>
	    </c:if> 
	     <c:if test="${i.index==1 }">
	      <tr>
	         <c:forEach begin="0" end="${fn:length(ho)-1}" varStatus="st1">
	           <c:if test="${ho[st1.index]!=''}">
	             <th> ${ho[st1.index]}</th>
	           </c:if>      
	          </c:forEach>
	       </tr>	  
	    </c:if>	     
	</c:forEach>
	-->	
<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">*</span>采购项目名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</th>
<th><span style="color:red">*</span>经办人</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">*</span>需求部门&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th><span style="color:red">*</span>项目所属年份</th>
<th><span style="color:red">*</span>是否计划内项目</th>
<th><span style="color:red">*</span>开支类型</th>
<th><span style="color:red">*</span>自行/委托采购</th>
<th>代理公司名称</th>
<th><span style="color:red">*</span>采购方式（首次）</th>
<th><span style="color:red">*</span>采购方式（最后一次）</th>
<th><span style="color:red">*</span>资格审查方式</th>
<th><span style="color:red">*</span>当前进度（周报）</th>
<th>进度说明（周报）</th>
<th><span style="color:red">*</span>采购方案预估金额（万元）</th>
<th><span style="color:red">*</span>项目启动时间</th>
<th><span style="color:red">*</span>工作小组会议召开时间</th>
<th><span style="color:red">*</span>50万以上方案汇报时间<br/>/50万以下需求部门<br/>提交方案呈批时间</th>
<th><span style="color:red">*</span>地市采购方案决策时间(首次)</th>
<th>地市采购方案决策时间(最后一次)</th>
<th><span style="color:red">*</span>地市采购方案纪要下达时间</th>
<th><span style="color:red">*</span>地市采购方案发文文号</th>
<th><span style="color:red">*</span>省公司采购方案决策时间(首次)</th>
<th>省公司采购方案决策时间(最后一次)</th>
<th><span style="color:red">*</span>省公司采购方案纪要<br/>/启动通知下达时间</th>
<th>省公司采购方案/启动通知发文文号</th>
<th><span style="color:red">*</span>公告发布开始时间</th>
<th><span style="color:red">*</span>公告发布截止时间</th>
<th><span style="color:red">*</span>采购评审/谈判时间</th>
<th><span style="color:red">*</span>采购组织实施单位/部门（集采类型）</th>
<th><span style="color:red">*</span>采购组织实施单位/部门</th>
<th><span style="color:red">*</span>需求单位</th>
<th>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<span style="color:red">*</span>采购类型
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</th>
<th>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<span style="color:red">*</span>采购内容
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th><span style="color:red">*</span>项目当前实际状态</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">*</span>操作方式&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th><span style="color:red">*</span>电子采购项目编号</th>
<th><span style="color:red">*</span>ES系统中的采购项目名称</th>
<th><span style="color:red">*</span>采购方案决策层级（决策形式）</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">*</span>采购模式&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th><span style="color:red">*</span>采购结果确认层级（确认形式）</th>
<th><span style="color:red">*</span>采购结果金额(万元)</th>
<th><span style="color:red">*</span>地市公司采购结果确认时间</th>
<th>地市采购结果上报时间</th>
<th><span style="color:red">*</span>省公司采购结果确认时间</th>
<th><span style="color:red">*</span>合同签署时间</th>
<th><span style="color:red">*</span>采购结果发文文号</th>
<th><span style="color:red">*</span>合同编码</th>
<th><span style="color:red">*</span>中选供应商</th>
<th><span style="color:red">*</span>合同金额（万元）</th>
<th><span style="color:red">*</span>合同归档时间</th>
<th><span style="color:red">*</span>流标次数</th>
<th>流标后更改的采购方式</th>
<th>流标原因说明（每次流标都作说明）</th>
<th>单一来源适用场景（大类）</th>
<th>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
单一来源适用场景（小类）
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</th>
<th>单一来源适用场景<br/>（具体产品服务备注）</th>
<th><span style="color:red">*</span>技术商务比例是否符合标准</th>
<th><span style="color:red">*</span>合同模板是否符合标准</th>
<th><span style="color:red">*</span>技术评分模板招标<br/>文件模板是否符合标准</th>
<th><span style="color:red">*</span>投诉情况</th>
<th><span style="color:red">*</span>中标单位<br/>结算价格<br/>和合同单位<br/>结算价格<br/>是否完全一致</th>
<th><span style="color:red">*</span>采购进度<br/>是否影响到成本<br/>/投资使用计划一致</th>
<th>特殊情况说明</th>
<th><span style="color:red">*</span>是否取消</th>
<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;取消原因
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
<th>购货物或<br/>服务质量情况</th>
<th>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
备注
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</th>
   
	   <c:if test="${isErrorFormat != 'true'}">
	      <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	  	      <tr  id="">
	  	       <c:forEach begin="0" end="${fn:length(vo)-1}" varStatus="st2">
	  	          <td style="max-width:400px;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" ${( (fn:contains(fiterNumber, vo[35] )&& vo[35] !="") ||(fn:contains(dataBasefilter, vo[35] )&& vo[35] !="")  ) ? 'bgcolor="#FF0000"':'bgcolor=""'}>${vo[st2.index]}</td>  	    
	  	       </c:forEach>		
		      </tr>
	     </c:forEach>	 
	   </c:if> 
	    <!-- 格式错误 -->
	   <c:if test="${isErrorFormat == 'true'}">
	     <c:forEach items="${ITEMPAGE.items}" var="vo" varStatus="i">
	   		<tr>
		   		<c:forEach items="${vo.td}" var="vo2">
		   			<td title="${vo2.errMsg}" style="max-width:400px;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;background-color:${vo2.errColor}" >${vo2.item}</td>
		   		</c:forEach>
	   		</tr>
	   </c:forEach>
	   </c:if> 
	 
	</table>
	
 	<div class="gd_page">
          <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
     </div>
     <div class="ge01"></div>
</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
 $('.ui-select').ui_select();
   if('${isErrorExcel}'=='true'){
     alert("excel中存在重复的电子采购项目编号.不允许提交")
     $("#commitButton").attr('disabled', true);
  }
   if('${isErrorFormat}'=='true'){
	   alert("该excel中存在格式错误的数据，请检查修改!");
	   $("#commitButton").attr('disabled', true);
   }
});
function ev_list(){
window.location.href="<%=basePath%>purchase/purchaseItem";
}

function ev_save(){
//数据库中已经存在相同的电子采购编码
if('${isExitNumber}' == 'true'){
  if(confirm("红色行中的电子采购项目编码已经存在。是否覆盖")){
       window.location.href="<%=basePath%>purchase/excelCoverSubmit";
    }
  else{
      return;
  
   }
}
else{
       window.location.href="<%=basePath%>purchase/exceluncoverSubmit";
}
}

//function ev_cover(){
//window.location.href="<%=basePath%>purchase/excelCoverSubmit";
//}

//function ev_cover(){
//window.location.href="<%=basePath%>purchase/excelCoverSubmit";
//}
//function ev_uncover(){
//window.location.href="<%=basePath%>purchase/exceluncoverSubmit";
//}
</script>
