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
<title>初审录入列表</title>
		<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
		<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
		<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/SRMC/rmpb/js/jquery.date_input.js"></script>
		<link rel="stylesheet" type="text/css" href="/SRMC/dc/css/combotree/easyui.css">
		<script type="text/javascript" src="/SRMC/dc/js/combotree/jquery.easyui.min.js"></script>  
		<script type="text/javascript">
			var j = jQuery.noConflict(true);
		</script>  
		<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<form action="<%=basePath%>account/accountTrialQuery?type=${type}" method="post" id="pageForm">
	<input type="hidden" value="N" name="isPages" id="isPages"/>
	<input type="hidden" value="${accountForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
    <input type="hidden" value="${accountForm.pageSize}" id="pageSize"	name="pageSize"/>
    <input type="hidden" name="costType" id="costType" value="${accountForm.costType}"/>
    <input type="hidden" name="type" id="type" value="${type}"/>
<div class="gl_import_m">
<div class="searchCondition"><span class="searchCondition_header">查询条件</span>
	<input name="" type="button" onclick="resets()" class="btn_reset r" value="重置" />
	<input name="" type="button" onclick="setTab(3)" class="btn_search r" value="查询" />
</div>
  <table width="99%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" align="center">
  <tr>
    <th width="15%" align="right">费用类型：</th>
    <td width="12%">
    <div class="checxTreeWrap"  style="width:170px;">
		    <%-- <input id="cosId" name="cosId" value="${accountForm.costType}"/>  --%>
		    <select  multiple style="width:200px" id="cosId" name="cosId" class="easyui-combotree" data-options="url:'<%=basePath%>/account/getCostType',method:'get'" multiple style="width:180px;">
		    <option selected="selected">${accountForm.costType}</option>
		    </select>
		    </div>
    </td>
    <th width="15%" align="right">整改状态：</th>
    <td width="12%"><select class="ui-select" name="status" id="sel_02" style="width:180px;">
          <option value="">请选择</option>
          <option value="1" <c:if test="${accountForm.status =='1' }">selected="selected"</c:if>>整改中</option>
          <option value="2" <c:if test="${accountForm.status =='2' }">selected="selected"</c:if>>无问题</option>
          <option value="3" <c:if test="${accountForm.status =='3' }">selected="selected"</c:if>>整改结束</option>
          <option value="4" <c:if test="${accountForm.status =='4' }">selected="selected"</c:if>>退单</option>
          <option value="5" <c:if test="${accountForm.status =='5' }">selected="selected"</c:if>>有问题(整改中/整改结束)</option>
        </select></td>
  </tr>
  <tr>
  	<th align="right" width="15%">纸质提交财务时间：</th>
		    <td width="12%">
		    <div class="date l">
		    <input name="eSDate" id="eSDate" type="text" autocomplete="off" value="${accountForm.eSDate}" 
		    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'pendDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  placeholder="请您输入"  class="text02 l" /><i></i>
		    </div> 
		    </td>
	<th align="right" width="15%"></th>
		    <td width="12%">
		    <div class="date l">
		    <input name="eEDate" id="eEDate" type="text" autocomplete="off" value="${accountForm.eEDate}" 
		    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'pstartDate\')||\'%y-%M-%d\'}',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" placeholder="请您输入"  class="text02 l"  /><i></i>
		    </div>
		    </td>
  </tr>
  <tr>
  	<th width="15%" align="right">是否超时：</th>
    <td width="12%"><select class="ui-select" name="istimeOut" id="sel_03" style="width:180px;">
          <option value="">请选择</option>
          <option value="1" <c:if test="${accountForm.istimeOut =='1' }">selected="selected"</c:if> >是</option>
          <option value="0" <c:if test="${accountForm.istimeOut =='0' }">selected="selected"</c:if>>否</option>
        </select></td>
     <th width="15%" align="right"></th>
     <td width="10%"><input  id="people"  type="text" autocomplete="off" placeholder="报账单号或报账人" name="people" class="text01" value="${people }" style="width:180px;" /></td> 
  
  </tr>
  
  </table>
  <div class="ge01"></div>
  <div class="tabpages">
    <ul class="l">
      <li <c:if test="${type==1 }">class="current"</c:if>  id="one1" onclick="setTab(1);">待办列表</li>
      <li <c:if test="${type==2 }">class="current"</c:if>  id="one2" onclick="setTab(2);">已办列表</li>
    </ul>
    <div class="otherButtons r">
    	<!-- 导出时间开始范围,只导出有问题的工单 -->
    	<!-- <div class="date l" style="width: 110px;min-width: 100px;border: 0;line-height: 28px;color: #40baff;font-size: 13px;">
    		纸质提交财务时间:
    	</div>
    	<div class="date l" style="width: 140px;min-width:120px;">
		    <input readonly="readonly" autocomplete="off" name="eSDate" id="eSDate" type="text"  placeholder="请选择开始时间"  class="text02 l" 
	     		onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width:90%;" />
    	</div>
    	<div class="date l" style="width: 140px;min-width:120px;">
		    <input readonly="readonly" autocomplete="off" name="eEDate" id="eEDate" type="text"  placeholder="请选择结束时间"  class="text02 l"  
	     		onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width:90%;" />
    	</div> -->
    	<a href="javascript:void(0)"><input onclick="exportProblems()" type="button" class="btn_common01" value="导出问题工单" /></a>
	    <c:if test="${type==1 }">
	    	<a id="outTime2"><input name="" id="outTime1" type="button" class="btn_common01" value="通知整改超时" /></a>
	    </c:if>
    </div>
  </div>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="listTable" id="checkboxDemo">
  <tr>
    <th><input id="checkAll" type="checkbox"></th>
    <th>序号</th>
    <th>报账单号</th>
    <th>报账部门</th>
    <th>费用类型</th>
    <th>报账人</th>
    <th>日期</th>
    <th>状态</th>
    <th>是否超时</th>
    <th>超时时长</th>
    <th>所处环节</th>
  </tr>
  <c:forEach items="${ITEMPAGE.items}" var="item" varStatus="i">
   <tr <c:if test="${i.index % 2==0}" >class="td01"</c:if>>
    <td ><input type="checkbox" name="subBox" id="checkbox" value="${item[0]}"></td>
    <td >${i.count }</td>
    <td ><a style="color:#005ea7;" href="<%=basePath%>account/trailDetail?source=accountTrialQuery&sourceType=${type}&orderId=${item[0]}&type=2&id=${item[9]}" >${item[0]}</a></td>
    <td >${item[1]}</td>
    <td >${item[2]}</td>
    <td >${item[3]}</td> 
    <td >${item[4]}</td>
    <td ><c:if test="${ empty item[5]}">-</c:if>
    <c:if test="${item[5]=='1'}">整改中</c:if>
    <c:if test="${ item[5]=='2'}">无问题</c:if>
    <c:if test="${ item[5]=='3'}">整改结束</c:if>
    <c:if test="${ item[5]=='4'}">退单</c:if></td>
    <td ><c:if test="${item[7] =='0' ||  empty item[7]}">否</c:if><c:if test="${item[7] !='0' && not empty item[7]}">是</c:if></td>
    <td ><c:if test="${ item[7] =='0' || empty item[7]}">-</c:if><c:if test="${item[7] !='0' && not empty item[7]}">${item[7]}</c:if></td>
    <td>
    <c:if test="${type==1 }">
    <b><a onclick="trail('${item[0]}','${item[9]}',1)">初审</a>&nbsp;</b> 
    <strong><a style="color:#4084b6;" id="submi${i.index+1 }"  data="${item[0]}" >提交</a>&nbsp;</strong> 
    <span><a  id="add${i.index+1 }" data="${item[0]}">新增问题</a></span>
    </c:if>
    <c:if test="${type==2 }">
    <b><a onclick="trail('${item[0]}','${item[9]}',3)">初审</a>&nbsp;</b> 
    <strong><a style="color:#4084b6;" onclick="cancelBak('${item[0]}')">撤销提交</a>&nbsp;</strong> 
    <span>${ item[8]}</span>
    </c:if>
    </td>
  </tr> 
  </c:forEach>
</table>
 <div class="gd_page">
   	 <jsp:include flush="true" page="/public/include/navigate10.jsp"></jsp:include>
 </div>
  <div class="ge01"></div>
</div>
  </form>         
<script src="/SRMC/dc/account/js/jquery.js"></script>
<script src="/SRMC/dc/account/js/ui-select.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fancybox/jquery.fancybox-1.3.4.css"  />	

<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" >
	
	//n年后日期
	function nYearsLater(n){
	    var today=new Date();
	    var h=today.getFullYear() + n;
	    var m=today.getMonth()+1;
	    var d=today.getDate();
	    m= m<10?"0"+m:m;      
	    d= d<10?"0"+d:d;  
	    return h+"-"+m+"-"+d;
	}
	
	
	function exportProblems(){
		var cursel = jQuery("#type").val();
   		
   		document.forms[0].action= "<%=basePath%>account/exportProblems?type="+cursel;
		document.forms[0].method="post";
		document.forms[0].submit();
		//重新设定form表单action
		document.forms[0].action= "<%=basePath%>account/accountTrialQuery?type="+cursel;
    }
	
	//初审处理
	function trail(orderid,id,type){
		window.location.href="<%=basePath%>account/trailDetail?orderId="+orderid+"&type="+type+"&id="+id;
	}
	
	function cancelBak(orderid){
	
		if(confirm("确定对该报账单进行回退操作吗？")){
		 	ajaxCancelBak(orderid);
		}
	}
	
	
	function ajaxCancelBak(orderid){
		jQuery.ajax({
	        type:"POST",
	        async:false,
	        url:"<%=basePath%>account/ajaxCancelBak",
	        data:"orderId=" + orderid,
	        success:function(data){
	        	if(data == 1){
	        		alert("回退成功！");
	        		setTab(3);
	        	}else{
	        		alert("删除失败！");
	        	}
	        },
	        error:function(){
	            alert("删除失败！");
	        }
	    });
	}
		
	
	//通知超时
	function OutTimes(){
		var str="";
        $("input[name='subBox']:checked").each(function(){ 
            if(str==""){
            	str+=""+$(this).val()+"";
            }else{
            	str+=","+$(this).val()+"";
            }
        });
        var orderId = str.split(",");
        jQuery("#outTime2").fancybox({
        			'href': "<%=basePath%>account/noticeOutTime?orderId="+orderId+"&type=2",
				   'width'				: '40%',
					'height'			: '50%',
					'scrolling'			:'yes',
					'type' : 'iframe',
					'autoScale'			: false,
					'transitionIn'		: 'none',
					'transitionOut'		: 'none',
					'onStart' : function(current, previous) {
						if(str.length==0){
				        	alert("请选择一个单据！");
				        	return false; 
				        }
				        var flagTemp = false;
				      	$.ajax({
					        type:"POST",
					        dataType:'json',
					        async:false,
					        url:"<%=basePath%>account/checkIsOutTime",
					        data:"orderId=" + orderId ,
					        success:function(data){
					        	if(data == "0"){
						        	alert("您选择的单据中不是所有的都超时，请重新选择");
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
	
	function resets(){
		j("#cosId").combotree({value:""});
		$('#sel_02').data('ui-select').val("");
		$('#sel_03').data('ui-select').val("");
		$("#eSDate").val("");
		$("#eEDate").val("");
	}
					    	
	function setTab(cursel){
		jQuery("#costType").val(j("#cosId").combotree("getText"));
		
		var ss = jQuery("#type").val(cursel);
		if(cursel=="3"){
			document.forms[0].submit();
		}else{
			window.location.href="<%=basePath%>account/accountTrialQuery?type="+cursel;
		}
	}
	
	$(function (){
		//jQuery("#eSDate").val(nYearsLater(-1));
		//jQuery("#eEDate").val(nYearsLater(0));
	
		//初始化提交按钮
		var url,me;
		   jQuery("a[id^=submi]").each(function(){
			   me = $(this);
			   url="<%=basePath%>account/trailSubmit?orderId="+me.attr("data");		   
			   me.fancybox({
				   'width'				: '80%',
		  			'height'			: '65%',
		  			//'scrolling'			:'yes',
		  			'autoScale'			: false,//自适应窗口大小
		  			'type' : 'iframe',
		  			'transitionIn'		: 'none',
		  			'transitionOut'		: 'none',	  			
		  			'href'				:	url
			   });
		   });
	});
	
	// 将所有.ui-select实例化
	$(function (){
		$('.ui-select').ui_select();
		// 获取已经实例化的select对象
		var obj = $('#sel_api').data('ui-select');
		var url,me;
		   jQuery("a[id^=add]").each(function(){
			   me = $(this);
			   url="<%=basePath%>account/addProblem?orderid="+me.attr("data");		   
			   me.fancybox({
				   'width'				: '60%',
		  			//'scrolling'			:'yes',
		  			'autoScale'			: false,//自适应窗口大小
		  			'type' : 'iframe',
		  			'transitionIn'		: 'none',
		  			'transitionOut'		: 'none',	  			
		  			'href'				:	url
			   });
		   });
		   
		   //初始化通知超时fancybox
		   OutTimes();//批量通知整改超时
			$("#checkAll").click(function() {
		        $('input[name="subBox"]').attr("checked",this.checked); 
		        OutTimes();//批量通知整改超时
		    });
		    var $subBox = $("input[name='subBox']");
		    $subBox.click(function(){
		        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
		        OutTimes();//批量通知整改超时
		    });
		    
	   j("#cosId").combotree({
	   onBeforeSelect: function(node) {
			if (!j(this).tree('isLeaf', node.target)) {
				return false;
			}
		},
		onClick: function(node) {
			if (!j(this).tree('isLeaf', node.target)) {
				j('#cosId').combo('showPanel');
			}
		},
		
   	    url:'<%=basePath%>/account/getCostType' ,
   		width:170,
   		lines:'true',
		checkbox:true ,
		cascadeCheck:false,
		required:false,
		editable:false,
		multiple:false//设置是否多选	
		
	       }); 
	});
	
</script>  
</body>
</html>