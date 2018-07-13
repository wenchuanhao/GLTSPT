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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title>初审处理</title>
		<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
</head>
<body>
<div class="gl_import_m">
<div class="searchCondition"><span class="searchCondition_header">接单信息</span></div>
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="readOnlyTable">
  <input type="hidden" id="trialAccount1" name="trialAccount1"/>
  <input type="hidden" id="department1" name="department1"/>
  
  <tr>
    <th width="25%">报账单号</th>
    <td width="25%"><a style="color:#005ea7;">${info.orderId }</a>
    <input type="hidden" class="text01" name="orderId"  id="orderId" value="${info.orderId }"></td>
    <th width="25%">费用类型</th>
    <td>${info.costType}<%-- <select class="ui-select" id="sel_04"  onchange="change()" name="costType"  style="width:180px;">
          <option value="">请选择</option>
			<c:forEach items="${rulesList }" var="list" varStatus="i">
          <option value="${list.typeId }"  <c:if test="${info.costType ==list.typeName}">selected="selected"</c:if>  >${list.typeName }</option>
          </c:forEach>
        </select> --%></td>
    </tr>
  <tr>
    <th>报帐人</th>
    <td>${info.sementPeople }<input type="hidden" class="text01" name="sementPeople"  id="sel_02" value="${info.sementPeople }"/></td>
    <th>报账部门</th>
    <td>${info.department }<input type="hidden" id="department" name="department" class="text01" disabled="disabled" value="${info.department }"/></td>
    </tr>
    <tr>
    <th>交单人</th>
    <td>${info.presenter }<input type="hidden" class="text01" name="presenter"  id="sel_03" value="${info.presenter }"/></td>
    <th>纸质提交财务时间</th>
    <td><div class="date l" style="width:160px;"><fmt:formatDate value="${info.pageSubmitDate}" pattern="yyyy-MM-dd HH:mm"/>
    <input  type="hidden"   name="pageSubmitDate" id="pageSubmitDate"  value="<fmt:formatDate value="${info.pageSubmitDate}" pattern="yyyy-MM-dd HH:mm"/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',alwaysUseStartDate:true})" placeholder="请您输入"  class="text02 l" style="width:90%;" /><i></i></div></td>
    </tr>
     <tr>
    <th>是否包含抵扣</th>
    <td><c:if test="${info.includeBuckle=='1' }">是</c:if> 
    <c:if test="${info.includeBuckle=='0' }">否</c:if>
    <input type="hidden"   name="includeBuckle" id="includeBuckle" <c:if test="${info.includeBuckle=='1' }">checked="checked"</c:if>  value="1"> </span><span><input type="hidden"  name="includeBuckle" id="includeBuckles" <c:if test="${info.includeBuckle=='0' }">checked="checked"</c:if>  value="0"> </td>
    <th>发票时间</th>
    <td><div class="date l" style="width:160px;"><fmt:formatDate value="${info.reachSement}" pattern="yyyy-MM-dd"/>
    <input   type="hidden"  name="reachSement" id="reachSement" value="<fmt:formatDate value="${info.reachSement}" pattern="yyyy-MM-dd"/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  placeholder="请您输入"  class="text02 l" style="width:90%;" /><i></i></div></td>
    </tr>
    <tr>
    <th>专票抵扣</th>
    <td>
    <c:if test="${info.deduction=='1' }">是</c:if> 
    <c:if test="${info.deduction=='0' }">否</c:if> 
    <input type="hidden"   name="deduction" id="deduction" <c:if test="${info.deduction=='1' }">checked="checked"</c:if>  value="1"> </span><span><input type="hidden"  name="deduction" id="deduction" <c:if test="${info.deduction=='0' }">checked="checked"</c:if>  value="0"> </td>
    <th>初审会计</th>
    <td>${info.trialAccount }
    <input type="hidden" id="trialAccount" name="trialAccount" class="text01" disabled="disabled"  value="${info.trialAccount }"/></td>
    </tr>
    <tr>
    	<th>金额</th>
    	<td>${info.cost }
    </tr>
  </table>
   <div class="ge01"></div>
  <div class="tabpages">
    <ul class="l">
      <li class="current">发票信息</li>
      
      	<c:set value="0" var="sum" />
      	<c:set value="0" var="taxNum" />
      	<c:set value="0" var="totalNum" />
	    <c:forEach items="${invoiceList}" var="item">
	        <c:set value="${sum + item.moneyNoTax}" var="sum" />
	        <c:set value="${taxNum + item.taxNum}" var="taxNum" />
	        <c:set value="${totalNum + item.moneyNoTax + item.taxNum}" var="totalNum" />
	    </c:forEach>
      <font color="red" size="3">&nbsp;&nbsp;&nbsp;温馨提示：发票金额(不含税)为：
      				<i id="invoice_total"><fmt:formatNumber value="${sum}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></i>，税额为：
      				<i id="taxNum_total"><fmt:formatNumber value="${taxNum}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></i>，总额为：
      				<i id="total_total"><fmt:formatNumber value="${totalNum}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></i>。
      </font>
    	
    </ul>
  </div>
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
  <tr>
  	 <th><input id="checkAll" type="checkbox" onclick="ev_checked()"></th>
     <th>发票类型</th> 
     <th>货物或应税劳务名称</th> 
     <th>发票代码</th> 
     <th>发票号码</th> 
     <th>开票日期</th> 
     <th>金额(不含税)</th>
     <th>税额</th>
     <th>税率(%)</th>
     <th>购方纳税人识别号</th>
     <th>购方纳税人名称</th>
     <th>销方纳税人识别号</th>
     <th>销方纳税人名称</th>
  </tr>
  <c:forEach items="${invoiceList}" var="item" varStatus="i">
  	<tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
  		<td><input name="subBox" id="subBox" type="checkbox" value="${item.id}"></td>
	  	<td>${item.invoiceType}</td>
	  	<td>${item.goodsName}</td>
	  	<td>${item.invoiceCode}</td>
	  	<td>${item.invoiceNum}</td>
	  	<td>${item.createDate}</td>
	  	<td>${item.moneyNoTax}</td>
	  	<td>${item.taxNum}</td>
	  	<td>${item.taxRate}</td>
	  	<td>${item.gfTaxpayerNum}</td>
	  	<td>${item.gfTaxpayerName}</td>
	  	<td>${item.xfTaxpayerNum}</td>
	  	<td>${item.xfTaxpayerName}</td>
  	</tr>
  </c:forEach>
  </table>
  <div class="ge01"></div>
  <div class="searchCondition"><span class="searchCondition_header">初审信息</span></div>
  <table width="99%"  align="center" cellpadding="0" cellspacing="0" class="readOnlyTable">
  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
  <tr>
    <th width="12%">单据是否有问题</th>
    <td width="21%"><c:if test="${ item[10]=='0'}">否</c:if><c:if test="${item[10]!='0'}">是</c:if></td>
    <th width="12%">整改状态</th>
    <td width="21%"><c:if test="${ empty item[5]}">-</c:if>
    <c:if test="${item[5]=='1'}">整改中</c:if>
    <c:if test="${item[5]=='2'}">无问题</c:if>
    <c:if test="${item[5]=='3'}">整改结束</c:if>
    <c:if test="${item[5]=='4'}">退单</c:if></td>
    <th width="12%">问题整改耗时</th>
    <td width="21%">${item[11]}</td>
    </tr>
  <tr>
    <th>是否超时  </th>
    <td><c:if test="${ empty item[7] || item[7]=='0'}">否</c:if><c:if test="${not empty item[7] && item[7]!='0'}">是</c:if></td>
    <th>超时总时长</th>
    <td><c:if test="${ empty item[7] || item[7]=='0'}">-</c:if><c:if test="${not empty item[7] && item[7]!='0'}">${item[7]}</c:if></td>
    <th>初审后是否存在问题</th>
    <td><c:if test="${ item[8]=='0'}">否</c:if><c:if test="${ item[8]!=0}">是</c:if>
    </td>
  </tr>
    <tr>
      <th>最耗时问题类型</th>
      <td><c:if test="${ empty haoshiType }">-</c:if>
      <jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="BZ_TCTYPE"></jsp:param>
					<jsp:param name="paramCode" value="${haoshiType }"></jsp:param>
				</jsp:include> </td>
      <th>&nbsp;</th>
      <td>&nbsp;</td>
      <th>&nbsp;</th>
      <td>&nbsp;</td>
    </tr>
      </c:forEach>
  </table>
  <div class="ge01"></div>
  <div class="tabpages">
    <ul class="l">
      <li class="current">问题列表</li>
    </ul>
    <div class="otherButtons r">
    <a id="submit"><input name="" onclick="submit('${info.orderId }')" type="button" class="btn_common01" value="提 交" /></a>
    <a id="addProblem2"><input name="addProblem" type="button" id="addProblem" onclick="addProblem()" class="btn_common01" value="新增/编辑问题" /></a>
    <a id="outTime2"><input name="" type="button" onclick="OutTime1('${info.orderId }')" class="btn_common01" value="通知整改超时" /></a>
    <a id="approve"><input name="" onclick="approve()" type="button" class="btn_common01" value="整改审核" /></a></div>
  </div>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable">
  <tr>
    <th><input name="checkbox" type="checkbox" value=""></th>
    <th>序号</th>
    <th>问题提出阶段</th>
    <th>问题类型</th>
    <th>问题详情</th>
    <th>整改状态</th>
    <th>创建时间</th>
    <th>是否超时</th>
    <th>整改耗时</th>
    <th>通知整改时间</th>
    <th>整改结束时间</th>
    <th>超时时长</th>
    <th>通知超时时间</th>
  </tr>
  <c:forEach items="${problemList }" var="list" varStatus="i">
  <tr <c:if test="${i.count % 2 == 1}">class="td01"</c:if>>
  <td ><input name="checkbox" type="checkbox" value="${list.id }"></td>
    <td>${i.index+1 }</td>
    <td>
    <jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="BZ_PHASE"></jsp:param>
					<jsp:param name="paramCode" value="${list.phase }"></jsp:param>
				</jsp:include> </td>
				
    <td>
    <jsp:include page="../sys/dict/include/dict_config.jsp">
					<jsp:param name="paramTypeCode" value="BZ_TCTYPE"></jsp:param>
					<jsp:param name="paramCode" value="${list.type }"></jsp:param>
				</jsp:include> 
    </td>
    <td>${list.detail }</td>
    <td <c:if test="${list.status=='1' }">style="color='#ff8400'"</c:if> 
    <c:if test="${list.status=='3' }">style="red'"</c:if>>
    <c:if test="${ empty list.status}">-</c:if><c:if test="${list.status=='1'}">整改中</c:if>
    <c:if test="${list.status=='2'}">整改通过</c:if>
    <c:if test="${list.status=='3'}">整改不通过</c:if>
    <c:if test="${list.status=='4'}">退单</c:if>
    </td>
    <td><fmt:formatDate value="${list.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
    <td     <c:if test="${list.isOutTime=='1' }">style="red'"</c:if>>
    <c:if test="${ empty list.isOutTime}">-</c:if><c:if test="${list.isOutTime=='1'}">是</c:if><c:if test="${list.isOutTime=='0'}">否</c:if></td>
    <td><c:if test="${ empty list.setTime}">-</c:if><c:if test="${not empty list.setTime}">${list.setTime}</c:if></td>
    <td><c:if test="${ empty list.startTime}">-</c:if><c:if test="${not empty list.startTime}"><fmt:formatDate value="${list.startTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></c:if></td>
    <td><c:if test="${ empty list.endTime}">-</c:if><c:if test="${not empty list.endTime}"><fmt:formatDate value="${list.endTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></c:if></td>
    <td <c:if test="${not empty list.outDay}">style="red'"</c:if>>
    <c:if test="${ empty list.outDay}">-</c:if><c:if test="${not empty list.outDay}">${list.outDay}</c:if></td>
    <td><c:if test="${ empty list.noticeOutTime}">-</c:if><c:if test="${not empty list.noticeOutTime}"><fmt:formatDate value="${list.noticeOutTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></c:if></td>
  </tr>
  </c:forEach>
  </table>
<div class="gd_page">
    <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
</div>
<div class="ge01"></div>

<div style="margin:30px auto;width:110px;"><input name="" type="button" onclick="back()" class="btn_common02" value="返回列表" /></div>
<script src="/SRMC/dc/js/jquery.js"></script>
<script src="/SRMC/dc/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	
 <script type="text/javascript">
 	
 	function back(){
// 		window.location=document.referrer;
		window.location.href="<%=basePath%>account/accountTrialQuery?type=${type}";
	}
 	//检测编辑问题只能选一个
 	function addProblem(){
 		var str="";
        $("input[name='checkbox']:checked").each(function(){ 
            
            if(str==""){
            	str+=""+$(this).val()+"";
            }else{
            	str+=","+$(this).val()+"";
            }
        });
      
        var arry = str.split(",");
        var orderId = jQuery("#orderId").val();
        var vv =  jQuery("#addProblem2").attr("href","<%=basePath%>account/addProblem?orderid="+orderId+"&problemId="+str+"");
			//初始化新增问题fancybox	   
		   jQuery('#addProblem2').fancybox({
		   				'width'				: '60%',
						'scrolling'			:'yes',
						'type' : 'iframe',
						'autoScale'			: false,
						'transitionIn'		: 'none',
						'transitionOut'		: 'none',
  						'onStart' : function(current, previous) {
							 if(arry.length>1){
					        	alert("只能选择一个单据！");
					        	return false; 
					        } 
			}
	   });
 	}
  	//整改审核
  	function approve(){
  		var str="";
        $("input[name='checkbox']:checked").each(function(){ 
            
            if(str==""){
            	str+=""+$(this).val()+"";
            }else{
            	str+=","+$(this).val()+"";
            }
       		
        });
        var arry = str.split(",");
        
        jQuery("#approve").fancybox({
		'href': "<%=basePath%>account/accountApprove?orderId="+str+"",
		'width'				: '75%',
		'height'			: '70%',
		'scrolling'			:'yes',
		'type'				: 'iframe',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'onStart' : function(current, previous){
			if(arry.length>1){
        		alert("只能选择一个单据！");
        		return false; 
        	} 
        	if(str.length==0){
        		alert("请选择一个单据！");
        		return false; 
        	}
        	
        	//用于返回主函数的值，因为在success或error中返回值的话，主函数是没有返回值的，js就会执行接下来的事务
			var flagTemp = false;
			$.ajax({
				type:"POST",
				dataType:'json',
				async:false,
				url:"<%=basePath%>account/ajaxApprove?orderId="+str,
				data:$('#submitForm').serialize(),
				success:function(result){
					if(result == "0"){
						alert("该问题已审核通过");
						flagTemp = true;
						return false;
					}
				},
				error:function(){
					   alert("网络异常请联系管理员！");
					   flagTemp = true;
					   return false;
				}
             });
		     if(flagTemp){
				  return false;
				}
			}
	   	});
  	}
  
  	//提交
  	function submit(orderid){
  		var vv =  jQuery("#submit").attr("href","<%=basePath%>account/trailSubmit?orderId="+orderid+"&type=2");
        jQuery("#submit").fancybox({
				   'width'				: '80%',
						'height'			: '65%',
						'scrolling'			:'yes',
						'type'				: 'iframe',
						'autoScale'			: false,
						'transitionIn'		: 'none',
						'transitionOut'		: 'none'
			   });
  	}
  	
	//通知超时
	function OutTime1(arry){
		if(arry!="str"){
		$.ajax({
	        type:"POST",
	        dataType:'json',
	        async:true,
	        url:"<%=basePath%>account/checkIsOutTime",
	        data:"orderId=" + arry ,
	        success:function(data){
	        	if(data == "0"){
		        	alert("您选择的单据没有超时，请重新选择");
		        	return ;
	        	}else{
			      	var vv =  jQuery("#outTime2").attr("href","<%=basePath%>account/noticeOutTime?orderId="+arry+"&type=1");
        			jQuery("#outTime2").fancybox({
				    'width'				: '40%',
						'height'			: '50%',
						'scrolling'			:'yes',
						'type' : 'iframe',
						'autoScale'			: false,
						'transitionIn'		: 'none',
						'transitionOut'		: 'none'
			   		});
	        	}
	        },
	        error:function(){
	            alert("网络异常请联系管理员！");
	        }
    	});
	}
		
	}
	
	function test(){
		window.location.href="<%=basePath%>account/accountTrialQuery";
	}
	
  	 $(document).ready(function(){
		// 将所有.ui-select实例化
		$('.ui-select').ui_select();
		// 获取已经实例化的select对象
		var obj = $('#sel_api').data('ui-select');

	   //初始化通知超时fancybox
	   OutTime1("str");
// 	   jQuery("#outTime2").fancybox();
	});

  	function change(){
		var checkText=$("#sel_04").find("option:selected").text();  //获取Select选择的Text
		var checkValue=$("#sel_04").val();  //获取Select选择的Value
		if(checkText=="请选择"){
			$("#trialAccount").val("");
			$("#department").val("");
			$("#trialAccount1").val("");
			$("#department1").val("");
		}else{
			$.ajax({
				type:"post",
				dataType:"json",
				async:false,
				url:"<%=basePath%>account/queryAccountById",
				data:{ costTypeId:checkValue },
				success:function(data){	
					$("#trialAccount").val(data[0].accounting);
					$("#trialAccount1").val(data[0].accounting);
					$("#department").val(data[0].department);
					$("#department1").val(data[0].department);
				},
				error:function(){
					alert("获取会计失败，请检查网络");
				}
			});//ajax
		}
	} 
  </script>
</div>
</body>
</html>