<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<link href="/SRMC/dc/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/dc/css/ui-select.css" rel="stylesheet" type="text/css" />
	<link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
	<script type="text/javascript" src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
	<script src="/SRMC/dc/js/jquery.js"></script>
	<script src="/SRMC/dc/js/ui-select.js"></script>
	<!--文件上传样式，js -->
	<link rel="stylesheet" href="/SRMC/rmpb/js/uploadify/uploadify.css" type="text/css" />
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/uploadify/swfobject.js"></script>
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css"/>
	<script type="text/javascript" src="/SRMC/rmpb/js/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
	
    <script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
    <style>
        .Pagecontent{
            overflow: hidden;
            width:100%;
        }
        .tab_box{
            width:80%;
            height:40px;
            border: 1px solid #d4e4ef;
            border-bottom: 0px;
            border-radius:5px 5px 0 0;
            -webkit-border-radius:5px 5px 0 0;
            -moz-border-radius:5px 5px 0 0;
        }
        .tab_btn{
            width:19.912%;
            line-height: 40px;
            display: inline-block;
            float: left;
            text-align: center;
            border-right: 1px solid #d4e4ef;
            cursor:pointer;
        }
        .tab_btn:last-child{
            border-right: 0;
        }
        .tab_btn a{
            text-decoration: none;
            color:#4084b6;
            font-size:15px;
        }
        .tab_btn.active{
            background: rgba(64,132,182,.3);
        }
        .tab_btn.active a{
            color: #fff;
        }
        .tab_box_content{
            width:99%;
            min-height:250px;
            border: 1px solid #d4e4ef;
            border-radius:0 5px 5px 5px;
            -webkit-border-radius:0 5px 5px 5px;
            -moz-border-radius:0 5px 5px 5px;
        }
    </style>
    <script>
        $(document).ready(function () {
            //默认active
            $(".tab_content").hide(); //隐藏全部的tab菜单内容
            $(".tab_btn:first").addClass("active").show(); //对第一个li标签添加class="active属性"
            $(".tab_content:first").show(); //显示第一个tab内容

            //点击事件
            $(".tab_btn").click(function () {
                $(".tab_btn").removeClass("active"); //移除class="active"属性
                $(this).addClass("active"); //添加class="active"到选择标签中
                $(".tab_content").hide(); //隐藏全部标签内容
                var activeTab = $(this).find("a").attr("href"); //找到所属属性值来识别活跃选项卡和内容
                $(activeTab).fadeIn(); //使内容消失
                return false;
            });

            $("#tb1").click(function () {
                $(".tab_btn2").removeClass("active");
                $(".tab_btn1").addClass("active");
                $(".tab_content2").hide();
                $(".tab_content1").show();
            });
            $("#tb2").click(function () {
                $(".tab_btn1").removeClass("active");
                $(".tab_btn3").removeClass("active");
                $(".tab_btn2").addClass("active");
                $(".tab_content1").hide();
                $(".tab_content3").hide();
                $(".tab_content2").show();
            });
            $("#b2").click(function () {
                $(".tab_btn1").removeClass("active");
                $(".tab_btn3").removeClass("active");
                $(".tab_btn2").addClass("active");
                $(".tab_content1").hide();
                $(".tab_content3").hide();
                $(".tab_content2").show();
            });
            
            $("#tb3").click(function () {
                $(".tab_btn2").removeClass("active");
                $(".tab_btn4").removeClass("active");
                $(".tab_btn3").addClass("active");
                $(".tab_content2").hide();
                $(".tab_content4").hide();
                $(".tab_content3").show();
            });
            $("#b3").click(function () {
                $(".tab_btn2").removeClass("active");
                $(".tab_btn4").removeClass("active");
                $(".tab_btn3").addClass("active");
                $(".tab_content2").hide();
                $(".tab_content4").hide();
                $(".tab_content3").show();
            });

            $("#tb4").click(function () {
                $(".tab_btn3").removeClass("active");
                $(".tab_btn5").removeClass("active");
                $(".tab_btn4").addClass("active");
                $(".tab_content3").hide();
                $(".tab_content5").hide();
                $(".tab_content4").show();
            });
            $("#b4").click(function () {
                $(".tab_btn3").removeClass("active");
                $(".tab_btn5").removeClass("active");
                $(".tab_btn4").addClass("active");
                $(".tab_content3").hide();
                $(".tab_content5").hide();
                $(".tab_content4").show();
            });
            
            $("#tb5").click(function () {
                $(".tab_btn4").removeClass("active");
                $(".tab_btn5").addClass("active");
                $(".tab_content4").hide();
                $(".tab_content5").show();
            });
        });
    </script>
</head>

<body>

<div class="gl_import_m">
	<div class="searchCondition">
		<span class="searchCondition_header">新增-采购项目</span>
	</div>
	<form name="form" id="pageForm" method="post"  >
    	
  	
  	<div class="Pagecontent">
  	<div class="tab_box">
            <div class="tab_btn tab_btn1 active"><a href="#tab1">第一步</a></div>
            <div class="tab_btn tab_btn2"><a href="#tab2">第二步</a></div>
            <div class="tab_btn tab_btn3"><a href="#tab3">第三步</a></div>
            <div class="tab_btn tab_btn4"><a href="#tab4">第四步</a></div>
            <div class="tab_btn tab_btn5"><a href="#tab5">第五步</a></div>
    </div>
    <div class="tab_box_content">
    <div id="tab1" class="tab_content tab_content1" style="display: none;">
         <div>  
         <table width="100%" height="60%"border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" style="padding:15px;" align="center" id="table01">           
		  	<tr>
		  		<th width="9%" align="right"><b>*</b>采购项目名称:</th>
			    <td width="30%">
			    		   <input id="columna" name="columna"   type="text"   class="text01"  maxlength="50" style="width:195px;"  />  	 
				</td> 
		  		
		  		<th width="9%" align="right"><b>*</b>经办人:</th>
			    <td width="30%">
			    <input  onfocus="if(this.value=='请填写创建人'){this.value=''};this.style.color='#333333';autoCompletes();" 
					                     onkeyup="clean()"
					                     onblur="if(this.value==''||this.value=='请填写创建人'){jQuery('#createUserid_input').val('');this.style.color='#b6b6b6';}"
		                      name="columnb" id= "columnb" value="${currentOperator}" type="text" class="text01" style="width:195px;" placeholder="请填写创建人">
				</td> 
		  		
		   		<th width="9%" align="right"><b>*</b>需求部门:</th>
			    <td width="30%">
				    	    <input id="columnc" name="columnc"   type="text"  class="text01" maxlength="100" style="width:195px;"  />
				</td>
			</tr>
			
			  	<tr>
		  		<th width="9%" align="right"><b>*</b>项目所属年份:</th>
			    <td width="30%">
			        <div class="date l" >
			    	<input readonly="readonly" name="columnd" id="columnd" type="text"  placeholder="请输入开始时间"  class="text02 l" 
			    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy"/>"
			                      	onclick="WdatePicker({startDate:'%y', dateFmt:'yyyy',alwaysUseStartDate:true})"  /><i></i>
			        </div>
				</td> 
		  		
		  		<th width="9%" align="right"><b>*</b>是否计划内项目:</th>
			    <td width="30%" >
			        <select class="ui-select" id="columne"  name="columne" style="width:202px;">
				    			<option value="">请选择</option>
								<option value="是">是</option>
								<option value="否">否</option>
				     </select>
		
				</td>
				 <th width="9%" align="right"><b>*</b>开支类型:</th>
			    <td width="30%">
			    	 <select class="ui-select" id="columnf" name="columnf" style="width:202px;">
						     <option value="">请选择</option>
						     <option  value="Capex">Capex</option>
						     <option  value="Opex">Opex</option>
						     <option  value="CapexOpex">Capex+Opex</option>							
			        </select>
				</td>
			</tr>
			<tr>
		  		<th width="9%" align="right"><b>*</b>自行/委托采购:</th>
			    <td width="30%">
			         <select class="ui-select" id="columng"  name="columng" style="width:202px";>
				    			<option value="">请选择</option>
								<option value="自行采购">自行采购</option>
								<option value="委托代理">委托代理</option>
				     </select>  	 
				</td> 
		  		<th width="9%" align="right">代理公司名称:</th>
			    <td width="30%">
			         	   <input id="columnh" name="columnh"   type="text"   class="text01"  maxlength="100" style="width:195px;"/>
				</td> 
		  		
		   		<th width="9%" align="right"><b>*</b>采购方式(首次):</th>
			    <td width="30%">
				     <select class="ui-select" id="columni"  name="columni" style="width:202px;">
				    			<option value="">请选择</option>
								<option value="公开招标">公开招标</option>
								<option value="邀请招标">邀请招标</option>
								<option value="公开比选">公开比选</option>
								<option value="邀请比选">邀请比选</option>
								<option value="竞争性谈判">竞争性谈判</option>
								<option value="公开询价">公开询价</option>
								<option value="邀请询价">邀请询价</option>
								<option value="单一来源采购">单一来源采购</option>
				     </select>  	
				</td>
			</tr>
			<tr>
		  		<th width="9%" align="right"><b>*</b>采购方式(最后一次):</th>
			    <td width="30%">
			    		   <select class="ui-select" id="columnj"  name="columnj" style="width:202px;">
				    			<option value="">请选择</option>
								<option value="公开招标">公开招标</option>
								<option value="邀请招标">邀请招标</option>
								<option value="公开比选">公开比选</option>
								<option value="邀请比选">邀请比选</option>
								<option value="竞争性谈判">竞争性谈判</option>
								<option value="公开询价">公开询价</option>
								<option value="邀请询价">邀请询价</option>
								<option value="单一来源采购">单一来源采购</option>
				     </select>  
				</td> 
		  		
		  		<th width="9%" align="right"><b>*</b>资格审查方式:</th>
			    <td width="30%">
			         	 <select class="ui-select" id="columnk"  name="columnk" style="width:202px;">
								<option value="资格后审">资格后审</option>
								<option value="资格预审">资格预审</option>
				     </select>
				</td> 
		  		
		   		<th width="9%" align="right"><b>*</b>当前进度（周报）:</th>
			    <td width="30%">
		             <select class="ui-select" id="columnl"  name="columnl" style="width:202px;">
				    			<option value="">请选择</option>
				    			<option value="收到请购单">收到请购单</option>
				    			<option value="工作组讨论">工作组讨论</option>
				    			<option value="采购方案呈批">采购方案呈批</option>
								<option value="发布公告">发布公告</option>
								<option value="完成评审">完成评审</option>
								<option value="结果确认">结果确认</option>
								<option value="结果公示">结果公示</option>						
								<option value="合同签订呈批">合同签订呈批</option>
								<option value="合同系统审批">合同系统审批</option>
								<option value="签订纸质合同">签订纸质合同</option>
								<option value="已归档">已归档</option>
				     </select>  		
				  </td>
			</tr>
			<tr>
		  		<th width="9%" align="right"></>进度说明（周报）:</th>
			    <td width="30%">
			    		   <input id="columnm" name="columnm"   type="text"   class="text01" maxlength="200" style="width:195px;"  />  	 
				</td> 
		  		
		  		<th width="9%" align="right"><b>*</b>采购方案预估金额（万元）:</th>
			    <td width="30%">
			         	   <input id="columnn" name="columnn"  type="text"   class="text01" maxlength="100" style="width:195px;"  />
				</td> 
		  		
		   		<th width="9%" align="right"><b>*</b>项目启动时间:</th>
			    <td width="30%">
		            <div class="date l" >
			    	<input readonly="readonly" name="columno" id="columno" type="text"  placeholder="请输入开始时间"  class="text02 l" 
			    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
			                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
			        </div>		
			        </td>
			</tr>
			<tr>
				<th colspan="8" align="center" height="50">
			    	<input type="button" value="下一步" class="btn_common04" id="tb2"/>
			    </th>
			</tr>				
		</table>		
        </div>
    </div>
	
	<div id="tab2" class="tab_content tab_content2" style="display: none;">
         <div>
         <table width="100%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" style="padding:15px;" align="center" >
			<tr>
		  		<th width="9%" align="right">工作小组会议召开时间:</th>
			    <td width="30%">
			        <div class="date l" >
			    	<input readonly="readonly" name="columnp" id="columnp" type="text"  placeholder="请输入开始时间"  class="text02 l" 
			    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
			                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
			        </div>
				</td> 
		  		
		  		<th width="9%" align="right">50万以上方案汇报时间/<br/>
		  		50万以下需求部门提交方案<br/>
		  		呈批时间:</th>
			    <td width="30%">
			        <div class="date l" >
			    	 <input readonly="readonly" name="columnq" id="columnq" type="text"  placeholder="请输入开始时间"  class="text02 l" 
			    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
			                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
			        </div>
			    </td> 
		  		
		   		<th width="9%" align="right">地市采购方案决策时间<br/>
		   		(首次)</th>
			    <td width="30%">
			      <div class="date l" >
			    	 <input readonly="readonly" name="columnr" id="columnr" type="text"  placeholder="请输入开始时间"  class="text02 l" 
			    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
			                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
			        </div>
				</td>
			</tr>
			<tr>
		  		<th width="9%" align="right">地市采购方案决策时间<br/>
		  		(最后一次)</th>
			    <td width="30%">
		           <div class="date l" >
			    	 <input readonly="readonly" name="columns" id="columns" type="text"  placeholder="请输入开始时间"  class="text02 l" 
			    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
			                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
			        </div>		
			       </td> 
		  		
		  		<th width="9%" align="right">地市采购方案纪要下达时间:</th>
			    <td width="30%">
			       <div class="date l" >
			    	 <input readonly="readonly" name="columnt" id="columnt" type="text"  placeholder="请输入开始时间"  class="text02 l" 
			    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
			                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
			        </div>	
				</td> 
		  		
		   		<th width="9%" align="right">地市采购方案发文文号:</th>
			    <td width="30%">
				    	    <input id="columnu" name="columnu"   type="text"   class="text01" maxlength="100" style="width:195px;"  />
				</td>
			</tr>
			<tr>
		  		<th width="9%" align="right">省公司采购方案<br/>
		  		决策时间(首次):</th>
			    <td width="30%">
		            <div class="date l" >
			    	 <input readonly="readonly" name="columnv" id="columnv" type="text"  placeholder="请输入开始时间"  class="text02 l" 
			    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
			                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
			        </div>	
			     </td> 
		  		
		  		<th width="9%" align="right">省公司采购方案决策时间<br/>
		  		 (最后一次):</th>
			    <td width="30%">
		            <div class="date l" >
			    	 <input readonly="readonly" name="columnw" id="columnw" type="text"  placeholder="请输入开始时间"  class="text02 l" 
			    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
			                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
			        </div>			
			      </td> 
		  		
		   		<th width="9%" align="right">省公司采购方案纪要/<br/>
		   		启动通知下达时间:</th>
			    <td width="30%">
			         <div class="date l" >
			    	 <input readonly="readonly" name="columnx" id="columnx" type="text"  placeholder="请输入开始时间"  class="text02 l" 
			    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
			                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
			        </div>	
				</td>
			</tr>
			<tr>
		  		<th width="9%" align="right">省公司采购方案/<br/>
		  		启动通知发文文号:</th>
			    <td width="30%">
			    		   <input id="columny" name="columny"   type="text"   class="text01"  maxlength="100" style="width:195px;"  />  	 
				</td> 
		  		
		  		<th width="9%" align="right">公告发布开始时间:</th>
			    <td width="30%">
		            <div class="date l" >
			    	 <input readonly="readonly" name="columnz" id="columnz" type="text"  placeholder="请输入开始时间"  class="text02 l" 
			    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
			                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
			        </div>	
				</td> 
		  		
		   		<th width="9%" align="right">公告发布截止时间:</th>
			    <td width="30%">
			    <div class="date l" >
			    	 <input readonly="readonly" name="columnaa" id="columnaa" type="text"  placeholder="请输入开始时间"  class="text02 l" 
			    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
			                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
			        </div>
				</td>
			</tr>
			<tr>
				<th colspan="8" align="center" height="70px">
					<input type="button" value="上一步" class="btn_common04" id="tb1"/>
         			<input type="button" value="下一步" class="btn_common04" id="tb3"/>
         		</th>
			</tr>			
			</table>	 	 
         </div>
    </div>
	
	<div id="tab3" class="tab_content tab_content3" style="display: none;">
        <div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" style="padding:15px;" align="center" >
				<tr>
			  		<th width="9%" align="right">采购评审/谈判时间:</th>
				    <td width="30%">
				    <div class="date l" >
				    	 <input readonly="readonly" name="columnab" id="columnab" type="text"  placeholder="请输入开始时间"  class="text02 l" 
				    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
				                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
				        </div>
					</td> 
			  		
			  		<th width="9%" align="right">采购组织实施单位/<br/>
			  		部门（集采类型）:</th>
				    <td width="30%">
				    <select class="ui-select" id="columnac"  name="columnac" style="width:202px;">	                   
					    			<option value="省本部采购部门（二级集采）">省本部采购部门（二级集采）</option>
									<option value="省本部横向部门（非集采）">省本部横向部门（非集采）</option>
									<option value="地市公司采购部门">地市公司采购部门</option>
									<option value="地市公司横向部门">地市公司横向部门</option>
									
					     </select>  
					</td> 
			  		
			   		<th width="9%" align="right">采购组织实施单位/部门:</th>
				    <td width="30%">
					    	    <input id="columnad" name="columnad"  value="南方基地综合管理部"  type="text"   class="text01"  maxlength="100" style="width:195px;"  />
					</td>
				</tr>
				<tr>
			  		<th width="9%" align="right">需求单位:</th>
				    <td width="30%">
				    <select class="ui-select" id="columnae"  name="columnae" style="width:202px;">
					    			<option value="省本部">省本部</option>
									<option value="地市分公司">地市分公司</option>						
					     </select>  
					</td> 
			  		
			  		<th width="9%" align="right">采购类型:</th>
				    <td width="30%">
				    <select class="ui-select" id="columnaf"  name="columnaf" style="width:202px;">
					    			<option value="工程建设项目（工程）">工程建设项目（工程）</option>
									<option value="工程建设项目（货物）">工程建设项目（货物）</option>
									<option value="工程建设项目（服务）">工程建设项目（服务）</option>
									<option value="非工程建设项目（货物）">非工程建设项目（货物）</option>
									<option value="非工程建设项目（服务）">非工程建设项目（服务）</option>
									
					     </select>  
					</td> 
			  		
			   		<th width="9%" align="right">采购内容:</th>
				    <td width="30%">
				    <input id="columnag" name="columnag"    type="text"   class="text01" maxlength="500" style="width:195px;"  />
					</td>
				 </tr>
				 
				 <tr>
			  		<th width="9%" align="right">项目当前实际状态:</th>
				    <td width="30%">
				    <select class="ui-select" id="columnah"  name="columnah" style="width:202px;">
				                    <option value="">请选择</option>
					    			<option value="采购方式已审批">采购方式已审批</option>
									<option value="公告/邀请函已发布">公告/邀请函已发布</option>
									<option value="投标已完成">投标已完成</option>
									<option value="评标已完成">评标已完成</option>
									<option value="采购结果已决策">采购结果已决策</option>
									<option value="项目已结束">项目已结束</option>						
					     </select>  
					</td> 
			  		
			  		<th width="9%" align="right">操作方式:</th>
				    <td width="30%">
				    <select class="ui-select" id="columnai"  name="columnai" style="width:202px;">
				                    <option value="">请选择</option>
					    			<option value="上平台操作">上平台操作</option>
									<option value="上平台报备">上平台报备</option>
									<option value="未上平台">未上平台</option>
									
					     </select>  
					</td> 
			  		
			   		<th width="9%" align="right"></>电子采购项目编号:</th>
				    <td width="30%">
					    	    <input id="columnaj" name="columnaj"   type="text"   class="text01"  maxlength="30" style="width:195px;"  />
					</td>
				 </tr>
				 
    
         
				 <tr>
			  		<th width="9%" align="right">ES系统中的采购项目名称:</th>
				    <td width="30%">
					    	    <input id="columnak" name="columnak"   type="text"   class="text01" maxlength="100" style="width:195px;"  />
			 
					</td> 
			  		
			  		<th width="9%" align="right">采购方案决策层级（决策形式）:</th>
				    <td width="30%">
				    <select class="ui-select" id="columnal"  name="columnal" style="width:202px;">
				                    <option value="">请选择</option>
					    			<option value="省公司三重一大（会议决策）">省公司三重一大（会议决策） </option>
									<option value="省公司总经理主持（会议决策）">省公司总经理主持（会议决策） </option>
									<option value="省公司副总经理主持（会议决策）">省公司副总经理主持（会议决策） </option>
									<option value="省公司部门层面（会议决策）">省公司部门层面（会议决策） </option>
									<option value="省公司总经理（签批决策）">省公司总经理（签批决策） </option>
									<option value="省公司副总经理（签批决策）">省公司副总经理（签批决策）  </option>
									<option value="省公司部门领导（签批决策）">省公司部门领导（签批决策）  </option>
									<option value="地市公司三重一大（会议决策）">地市公司三重一大（会议决策）  </option>
									<option value="地市公司总经理主持（会议决策）">地市公司总经理主持（会议决策）  </option>
									<option value="地市公司副总经理主持（会议决策）">地市公司副总经理主持（会议决策）  </option>
			                        <option value="地市公司总经理（签批决策）">地市公司总经理（签批决策）   </option>
									<option value="地市公司副总经理（签批决策）">地市公司副总经理（签批决策）  </option>
									<option value="地市公司部门领导（签批决策）">地市公司部门领导（签批决策）  </option>
															
									
					     </select>  
					</td> 
			  		
			   		<th width="9%" align="right">采购模式:</th>
				    <td width="30%">
				    	    <select class="ui-select" id="columnam"  name="columnam" style="width:202px;">
				                    <option value="">请选择</option>
					    			<option value="二采（直接采购）">二采（直接采购）</option>
									<option value="二采（平行采购）">二采（平行采购）</option>
									<option value="二采（两级决策）">二采（两级决策）</option>
									<option value="自采">自采</option>
									
					     </select> 
					</td>
				 </tr>
				 
				 <tr>
			  		<th width="9%" align="right">采购结果确认层级<br/>
			  		（确认形式）:</th>
				    <td width="30%">
				   <select class="ui-select" id="columnan"  name="columnan" style="width:202px;">
				                    <option value="">请选择</option>
					    			<option value="省公司三重一大（会议决策）">省公司三重一大（会议决策） </option>
									<option value="省公司总经理主持（会议确认）">省公司总经理主持（会议确认）  </option>
									<option value="省公司副总经理主持（会议确认）">省公司副总经理主持（会议确认）   </option>
									<option value="省公司部门层面（会议确认）">省公司部门层面（会议确认）  </option>
									<option value="省公司总经理（签批确认）">省公司总经理（签批确认）  </option>
									<option value="省公司副总经理（签批确认）">省公司副总经理（签批确认）   </option>
									<option value="省公司部门领导（签批确认）">省公司部门领导（签批确认）  </option>
									<option value="地市公司三重一大（会议确认）">地市公司三重一大（会议确认）   </option>
									<option value="地市公司总经理主持（会议确认）">地市公司总经理主持（会议确认）   </option>
									<option value="地市公司副总经理主持（会议确认）">地市公司副总经理主持（会议确认）   </option>
			                        <option value="地市公司总经理（签批确认）">地市公司总经理（签批确认）    </option>
									<option value="地市公司副总经理（签批确认）">地市公司副总经理（签批确认）  </option>
									<option value="地市公司部门领导（签批确认）">地市公司部门领导（签批确认）  </option>
									<option value="授权评标/评审委员会确定中标人/成交供应商 ">授权评标/评审委员会确定中标人/成交供应商 </option>												
					     </select>  
					</td> 
			  		
			  		<th width="9%" align="right">采购结果金额(万元):</th>
				    <td width="30%">
				   		    	    <input id="columnao" name="columnao"   type="text"   class="text01"  maxlength="100" style="width:195px;"  />
					</td> 
			  		
			   		<th width="9%" align="right">地市公司采购结果确认时间:</th>
				    <td width="30%">
				    <div class="date l" >
				    	 <input readonly="readonly" name="columnap" id="columnap" type="text"  placeholder="请输入开始时间"  class="text02 l" 
				    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
				                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
				        </div>
					</td>
				 </tr>
				 <tr>
				 	<th colspan="8" align="center" height="50">
				 		<input type="button" value="上一步" class="btn_common04" id="b2"/>
				 		<input type="button" value="下一步" class="btn_common04" id="tb4"/>
				 	</th>
				 </tr>
				 </table>
        </div>
    </div>	
    <div id="tab4" class="tab_content tab_content4" style="display: none;">
         <div>     
         <table width="100%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" style="padding:15px;" align="center" >  
				 <tr>
			  		<th width="9%" align="right">地市采购结果上报时间:</th>
				    <td width="30%">
				    <div class="date l" >
				    	 <input readonly="readonly" name="columnaq" id="columnaq" type="text"  placeholder="请输入开始时间"  class="text02 l" 
				    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
				                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
				        </div>
					</td> 
			  		
			  		<th width="9%" align="right">省公司采购结果确认时间:</th>
				    <td width="30%">
				   	    <div class="date l" >
				    	 <input readonly="readonly" name="columnar" id="columnar" type="text"  placeholder="请输入开始时间"  class="text02 l" 
				    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
				                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
				        </div>
					</td> 
			  		
			   		<th width="9%" align="right">合同签署时间:</th>
				    <td width="30%">
				   	    <div class="date l" >
				    	 <input readonly="readonly" name="columnas" id="columnas" type="text"  placeholder="请输入开始时间"  class="text02 l" 
				    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
				                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
				        </div>		
				        </td>
				 </tr>
				 
				 <tr>
			  		<th width="9%" align="right">采购结果发文文号:</th>
				    <td width="30%">
			     <input id="columnat" name="columnat"   type="text"   class="text01" maxlength="100" style="width:195px;"  />
			
					</td> 
			  		
			  		<th width="9%" align="right">合同编码:</th>
				    <td width="30%">
				         <input id="columnau" name="columnau"   type="text"   class="text01" maxlength="100" style="width:195px;"  />
					</td> 
			  		
			   		<th width="9%" align="right">中选供应商:</th>
				    <td width="30%">
					     <input id="columnav" name="columnav"   type="text"   class="text01" maxlength="100" style="width:195px;"  />
					</td>
				 </tr>
				 
       	 
				 <tr>
			  		<th width="9%" align="right">合同金额(万元):</th>
				    <td width="30%">
				          <input id="columnaw" name="columnaw"  type="text" class="text01" maxlength="100" style="width:195px;"  />
					</td> 
			  		
			  		<th width="9%" align="right">合同归档时间:</th>
				    <td width="30%">
				     <div class="date l" >
				    	 <input readonly="readonly" name="columnax" id="columnax" type="text"  placeholder="请输入开始时间"  class="text02 l" 
				    					value="<fmt:formatDate value="${jsxm.beginCreateTime}" pattern="yyyy-MM-dd"/>"
				                      	onclick="WdatePicker({startDate:'%y-%M', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
				        </div>	
					</td> 
			  		
			   		<th width="9%" align="right">流标次数:</th>
				    <td width="30%">
					    	    <input id="columnay" name="columnay"   type="text"   class="text01" maxlength="10" style="width:195px;"  />
					</td>
				 </tr>
				 
				 <tr>
			  		<th width="9%" align="right">流标后更改的采购方式:</th>
				    <td width="30%">
				    		    <input id="columnaz" name="columnaz"   type="text"   class="text01" maxlength="100" style="width:195px;"  />
			
					</td> 
			  		
			  		<th width="9%" align="right">流标原因说明<br/>
			  		（每次流标都作说明）:</th>
				    <td width="30%">
				    	  <input id="columnba" name="columnba"   type="text"   class="text01" maxlength="150" style="width:195px;"  />
			
					</td> 
			  		
			   		<th width="9%" align="right">单一来源适用场景（大类）:</th>
				    <td width="30%">
			 <select class="ui-select" id="columnbb"  name="columnbb" style="width:202px;">
				                    <option value="">请选择</option>
					    			<option value="场景1">场景1 </option>
									<option value="场景2">场景2  </option>
									<option value="场景3">场景3   </option>
									<option value="场景4">场景4  </option>
									<option value="场景5">场景5  </option>
									<option value="场景6">场景6   </option>
									<option value="场景7">场景7  </option>
									<option value="场景8">场景8   </option>
									<option value="场景9">场景9   </option>
									<option value="场景10">场景10   </option>
			                        <option value="场景11">场景11    </option>
									<option value="特殊场景">特殊场景  </option>
															
					     </select> 
					     
					  </td>
				 </tr>
				 
				 <!-- ===================== -->
				 
				 <tr>
			  		<th width="9%" align="right">单一来源适用场景<br/>
			  		（小类）:</th>
				    <td width="30%">
			<select class="ui-select" id="columnbc"  name="columnbc" style="width:202px;">
				                    <option value="">请选择</option>
					    			<option value="0101.设备类扩容:核心网、无线网、传输网、承载网等设备"> 0101.设备类扩容:核心网、无线网、传输网、承载网等设备  </option>
									<option value="0102.平台类扩容升级:数据平台扩容升级等 "> 0102.平台类扩容升级:数据平台扩容升级等   </option>
									<option value="0103.（慎用）服务类:无法由第三方提供的原厂服务（包括维保、现场支撑、优化、搬迁、返修等）"> 0103.（慎用）服务类:无法由第三方提供的原厂服务（包括维保、现场支撑、优化、搬迁、返修等）  </option>
									<option value="0104.其他情形，请注明具体情况"> 0104.其他情形，请注明具体情况   </option>
									<option value="0201.备品备件类:如核心网络设备备件（华为、爱立信备件）"> 0201.备品备件类:如核心网络设备备件（华为、爱立信备件）  </option>
									<option value="0202.配件辅件类:ODF机架增补配线子框等"> 0202.配件辅件类:ODF机架增补配线子框等    </option>
									<option value="0301.系统软件功能增加、完善等(如报账平台软件增加营改增功能、网管软件增加管理功能模块、NGBOSS系统功能完善）"> 0301.系统软件功能增加、完善等(如报账平台软件增加营改增功能、网管软件增加管理功能模块、NGBOSS系统功能完善）</option>
									<option value="0401.硬件:由于技术方案或市场垄断造成的唯一性硬件采购"> 0401.硬件:由于技术方案或市场垄断造成的唯一性硬件采购   </option>
									<option value="0402.软件:由于技术方案造成的数据库、中间件、虚拟化软件等产品的采购；由于市场垄断造成的windows操作系统等产品的采购"> 0402.软件:由于技术方案造成的数据库、中间件、虚拟化软件等产品的采购；由于市场垄断造成的windows操作系统等产品的采购    </option>
									<option value="0501.联合建设类，如高铁覆盖合作建设、城市管道施工联合建设等">0501.联合建设类，如高铁覆盖合作建设、城市管道施工联合建设等    </option>
			                        <option value="0601.媒体类，如电视、广播、报纸等媒体 ">0601.媒体类，如电视、广播、报纸等媒体   </option>
									<option value="0602.传播类，如机场、地铁、高速公路等">0602.传播类，如机场、地铁、高速公路等  </option>
									<option value="0603.赞助类">0603.赞助类    </option>
									<option value="0604.会展服务类">0604.会展服务类  </option>
									<option value="0701.集团客户指定的产品或供应商，如使用某品牌光缆、交换机、路由器设备等">0701.集团客户指定的产品或供应商，如使用某品牌光缆、交换机、路由器设备等   </option>
									<option value="0702.为推广我公司某项集团客户业务，需采购对方产品的情况">0702.为推广我公司某项集团客户业务，需采购对方产品的情况   </option>
									<option value="0801.购置房屋">0801.购置房屋   </option>
									<option value="0802.购置土地 ">0802.购置土地 </option>
									<option value="0803.购置管道或纤芯">0803.购置管道或纤芯    </option>
									<option value="0804.租赁房屋">0804.租赁房屋   </option>
									<option value="0805.租赁土地">0805.租赁土地    </option>
									<option value="0806.租赁管道或纤芯">0806.租赁管道或纤芯 </option>
									<option value="0807.站址租赁">0807.站址租赁   </option>
									<option value="0808.水电气引入">0808.水电气引入  </option>
									<option value="0809.特殊检测，如环境测评、防雷测试等">0809.特殊检测，如环境测评、防雷测试等   </option>
									<option value="0810.其他情形，请注明具体情况">0810.其他情形，请注明具体情况     </option>
									<option value="0901.政府、行政部门指定，如重点市政道路管线施工单位指定，市政重点道路指定管道人井盖产品的具体技术规范标准，经论证仅一家供应商可提供等情况">0901.政府、行政部门指定，如重点市政道路管线施工单位指定，市政重点道路指定管道人井盖产品的具体技术规范标准，经论证仅一家供应商可提供等情况  </option>
									<option value="0902.对集团公司下属单位而言，集团公司相关文件已确定了供应商或产品的情况">0902.对集团公司下属单位而言，集团公司相关文件已确定了供应商或产品的情况 </option>
									<option value="1001.涉及国家军事基地、国家安全有保密要求的采购 ">1001.涉及国家军事基地、国家安全有保密要求的采购   </option>
									<option value="1101.默认情形">1101.默认情形    </option>
									<option value="1201.默认情形">1201.默认情形    </option>
									<option value="1301.抢险救灾物资，如:遭遇山洪需紧急采购皮划艇">1301.抢险救灾物资，如:遭遇山洪需紧急采购皮划艇  </option>
									<option value="1401.符合公司维稳政策，且集团公司所属各单位（包括省公司全资子公司）具备国家规定的资格资质，可由其直接承接的各类采购，如中移、铁通等公司直接承接的各类采购">1401.符合公司维稳政策，且集团公司所属各单位（包括省公司全资子公司）具备国家规定的资格资质，可由其直接承接的各类采购，如中移、铁通等公司直接承接的各类采购   </option>
									<option value="1402.需国家、行业权威或垄断机构提供的咨询、培训">1402.需国家、行业权威或垄断机构提供的咨询、培训   </option>
									<option value="1403.原有咨询项目、业务运营等连续性内容扩展 ">1403.原有咨询项目、业务运营等连续性内容扩展    </option>
									<option value="1404.由于业务运营决策导致相关产品或服务只能使用唯一供应商的，如终端采购，公司入驻园区，使用园区物业服务的">1404.由于业务运营决策导致相关产品或服务只能使用唯一供应商的，如终端采购，公司入驻园区，使用园区物业服务的     </option>
									<option value="1405.地铁、高铁、高速、校园等特殊场景中涉及的部分采购（如施工）">1405.地铁、高铁、高速、校园等特殊场景中涉及的部分采购（如施工）</option>
									<option value="1406.涉及公司核心机密、重大发展规划等，由于保密要求指定集团内部某单位承接的">1406.涉及公司核心机密、重大发展规划等，由于保密要求指定集团内部某单位承接的  </option>
									<option value="1407.试点项目、创新项目、试用产品,仅唯一供应商可提供的">1407.试点项目、创新项目、试用产品,仅唯一供应商可提供的   </option>
									<option value="1408.属于一级集采目录，但是所需型号、功能等未包含在一级集采结果中，需与一级集采产品配套且与集团分配的集采厂商采用单一来源方式可以明显降低成本、缩短采购、供货周期的 ">1408.属于一级集采目录，但是所需型号、功能等未包含在一级集采结果中，需与一级集采产品配套且与集团分配的集采厂商采用单一来源方式可以明显降低成本、缩短采购、供货周期的  </option>
									<option value="1409.其他情形，请注明具体情况">1409.其他情形，请注明具体情况    </option>
									<option value="1501.（不属于13+1），在备注中说明情况">1501.（不属于13+1），在备注中说明情况   </option>
																					
					     </select> 
					</td> 
			  		
			  		<th width="9%" align="right">单一来源适用场景<br/>
			  		（具体产品服务备注）:</th>
				    <td width="30%">
				    	  <input id="columnbd" name="columnbd"   type="text"   class="text01" maxlength="100" style="width:195px;"  />
			
					</td> 
			  		
			   		<th width="9%" align="right">技术商务比例是否符合标准:</th>
				    <td width="30%">
			 <select class="ui-select" id="columnbe"  name="columnbe" style="width:202px;">
					    			<option value="使用省公司模板">使用省公司模板 </option>
									<option value="未使用省公司模板">未使用省公司模板  </option>
					     </select> 
					     
					  </td>
				 </tr>
				 <tr>
				 	<th colspan="8" align="center" height="50">
				 		 <input type="button" value="上一步" class="btn_common04" id="b3"/>
			             <input type="button" value="下一步" class="btn_common04" id="tb5"/>
				 	</th>
				 </tr>
				 </table>
         </div>
     </div>	
			
     <div id="tab5" class="tab_content tab_content5" style="display: none;">
         <div> 
         <table width="100%" border="0" cellspacing="0" cellpadding="0" class="editTable reeditTable" style="padding:15px;" align="center" >  	 
				 <tr>
			  		<th width="9%" align="right">合同模板是否符合标准:</th>
				    <td width="30%">
			 <select class="ui-select" id="columnbf"  name="columnbf" style="width:202px;">
				                   
					    			<option value="使用省公司模板">使用省公司模板 </option>
									<option value="未使用省公司模板">未使用省公司模板  </option>
					     </select> 
					</td> 
			  		
			  		<th width="9%" align="right">技术评分模板<br/>
			  		                                        招标文件模板是否符合标准:</th>
				    <td width="30%">
			 <select class="ui-select" id="columnbg"  name="columnbg" style="width:202px;">
				                   
					    			<option value="使用省公司模板">使用省公司模板 </option>
									<option value="未使用省公司模板">未使用省公司模板  </option>
					     </select> 
					</td> 
			  		
			   		<th width="9%" align="right">投诉情况:</th>
				    <td width="30%">
			 
			 <input id="columnbh" name="columnbh"   type="text"   class="text01" maxlength="100" style="width:195px;"  />
			     
					  </td>
				 </tr>
				 <tr>
			  		<th width="9%" align="right">
			  		中标单位、<br/>
			  		结算价格和合同单位、<br/>
			  		结算价格是否完全一致:</th>
				    <td width="30%">
			       <select class="ui-select" id="columnbi"  name="columnbi" style="width:202px;">
					    			<option value="是">是 </option>
									<option value="否">否  </option>
					     </select> 
					</td> 
			  		
			  		<th width="9%" align="right">采购进度是否影响到成本/<br/>
			  		投资使用计划一致:</th>
				    <td width="30%">
			       <select class="ui-select" id="columnbj"  name="columnbj" style="width:202px;">
				                    <option value="否">否  </option>
					    			<option value="是">是 </option>
									
					     </select> 
					</td> 
			  		
			   		<th width="9%" align="right">特殊情况说明:</th>
				    <td width="30%">
			 	     <input id="columnbk" name="columnbk"   type="text"   class="text01" maxlength="150" style="width:195px;"  />
			 		     
					  </td>
				 </tr>
				 <tr>
			  		<th width="9%" align="right">是否取消:</th>
				    <td width="30%">
			       <select class="ui-select" id="columnbl"  name="columnbl" style="width:202px;">
			       					<option value="否">否  </option>
					    			<option value="是">是 </option>					
					     </select> 
					</td> 
			  		
			  		<th width="9%" align="right">取消原因:</th>
				    <td width="30%">
				    	  <input id="columnbm" name="columnbm"   type="text"   class="text01"  maxlength="150" style="width:195px;"  />
			
					</td> 
			  		
			   		<th width="9%" align="right">购货物或服务质量情况:</th>
				    <td width="30%">
			 	     <input id="columnbn" name="columnbn"   type="text"   class="text01" maxlength="2000" style="width:195px;"  />
			
					     
					  </td>
				 </tr>
	 <tr>
  		<th width="2%" align="right">备注:</th>
	    <td width="37%" >
	    		    <textarea id="columnbo" name="columnbo"   rows="3"  type="text"  class="text01"  maxlength="500" style="width:220px;overflow:hidden;min-height: 80px;"></textarea>

		</td>
	</tr>	
	
     <tr>
		<c:forEach items="${headaaa}" begin="0" end="1" var="to" varStatus="i">
		<th width="9%" align="right">${to.column_cname}:</th>
           <td width="30%">
             <c:if test="${to.column_type=='1'}"> 
             	    <input id="${to.column_name}" name="${to.column_name}"  type="text"   class="text01" style="width:195px;"  />
             </c:if>
              <c:if test="${to.column_type=='2'}">            
	          <div class="date l" >
	    	  <input readonly="readonly" name="${to.column_name}" id="${to.column_name}" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value='${purchase[to.column_name]}' pattern="yyyy-MM-dd"/>"
	                      	onclick="WdatePicker({startDate:'%y-%M-%d', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	           </div> 
             </c:if>
             </td>
         </c:forEach>	 
	 </tr>
	 
	 
	 <!-- ====== 动态增加新的输入框======= -->
	   <c:if test="${fn:length(headaaa)>2}">
	      <c:forEach items="${headaaa}" begin="2" var="to" varStatus="i">
	           <c:if test="${(i.count+2)%3==0}">
	               <tr>
	             </c:if>     
	                  <th width="9%" align="right">${to.column_cname}:</th>
                      <td width="30%">
                <c:if test="${to.column_type=='1'}"> 
             	    <input id="${to.column_name}" name="${to.column_name}"  type="text"   class="text01" style="width:195px;"  />
                </c:if>
                <c:if test="${to.column_type=='2'}">            
	               <div class="date l" >
	    	          <input readonly="readonly" name="${to.column_name}" id="${to.column_name}" type="text"  placeholder="请输入开始时间"  class="text02 l" 
	    					value="<fmt:formatDate value='${purchase[to.column_name]}' pattern='yyyy-MM-dd'/>"
	                      	onclick="WdatePicker({startDate:'%y-%M-%d', dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  /><i></i>
	               </div> 
                </c:if>
             </td> 
             <c:if test="${(i.count+3)%3==0}">             
	               </tr>	          
	          </c:if>		
          </c:forEach>	          
	   </c:if>


	 <!-- ====== 动态增加新的输入框======= -->
	 <tr>
	   	<th colspan="8" align="center" height="50">
	   		<input name="" type="button" class="btn_common02" id="save" onclick="ev_save()" value="保 存" />
	   		<input name="" type="button" class="btn_common04" onclick="ev_list()" value="返 回" />
	   		<input type="button" value="上一步" class="btn_common04" id="b4"/>
	   	</th>
	   </tr>  
	</table>
         </div>
     </div>	
  	</div>
 </div>
	</form>
</div>
</body>
</html>

<script type="text/javascript">
$(document).ready(function(){
 
 $('#columnaj').on("blur",function(){
   if($('#columnaj').val()==""){
    $('#save').attr('disabled',false); 
     return;
    }
 	jQuery.ajax({
        type:"POST",
        url:"<%=basePath%>purchase/queryPurchaseByItemId",
        data:{"itemId":$('#columnaj').val()},
        success:function(data){
        	if(data == 1){
        		alert("已经存在此电子采购项目编号,不允许保存");
        		$('#save').attr('disabled',true);  
        	}else{
        	    $('#save').attr('disabled',false); 
        	}
        },
        error:function(){
            alert("查询此电子采购项目编号异常！");
        }
    });
 });
 
 $('.ui-select').ui_select();
 
});

function bindC(field){
	$(field).css("border-color","red");
    $(field).bind("click", function(){
		$(this).css("border-color","");
	});
}

function checktime(){
	var time=new Array();
	/*time[0] =$('#columno').val();
	time[1] =$('#columnp').val();
	time[2] =$('#columnq').val();
	time[3] =$('#columnr').val();
	time[4] =$('#columns').val();
	time[5] =$('#columnt').val();
	time[6] =$('#columnv').val();
	time[7] =$('#columnw').val();*/
	time[0] =$('#columnx').val();
	time[1] =$('#columnz').val();
	time[2] =$('#columnaa').val();
	time[3] =$('#columnab').val();
	time[4] =$('#columnap').val();
	time[5] =$('#columnaq').val();
	time[6] =$('#columnar').val();
	time[7] =$('#columnas').val();
	time[8] =$('#columnax').val();
	var head=new Array();
	/*head[0] ='columno';
	head[1] ='columnp';
	head[2] ='columnq';
	head[3] ='columnr';
	head[4] ='columns';
	head[5] ='columnt';
	head[6] ='columnv';
	head[7] ='columnw';*/
	head[0] ='columnx';
	head[1] ='columnz';
	head[2] ='columnaa';
	head[3] ='columnab';
	head[4] ='columnap';
	head[5] ='columnaq';
	head[6] ='columnar';
	head[7] ='columnas';
	head[8] ='columnax';
	var i;
	var max=new Date(time[0].replace(/-/g,"\/"));	
	for (i=1;i<time.length;i++){
	var a = time[i];
	var b;
	if(a != ""){
	 var b=new Date(a.replace(/-/g,"\/"));
	    if(b<max){
	       var headInfo = head[i];	        
	       $('#'+headInfo).parent().css("border-color","red");
	       $('#'+headInfo).bind("click", function(){
	       $(this).parent().css("border-color","");
	       });	   
	       alert("时间应大于"+max.getFullYear()+"-"+(max.getMonth()+1)+"-"+max.getDate());
	       return false;
	    }
	    else{
	       
	       max = b;
	    }
	  }	
	}
	return true;
}

function ev_save(){

 	var Columna = $('#columna').val();
 	if(Columna==""){
    alert("采购项目名称不能为空");
    $('#columna').css("border-color","red");
    bindC('#columna');
    return;
  	}
 	var Columnb = $('#columnb').val();
  	if(Columnb==""){
    alert("经办人不能为空");
    $('#columnb').css("border-color","red");
    bindC('#columnb');
    return;
  	}
 	var Columnc = $('#columnc').val();
  	if(Columnc==""){
    alert("需求部门不能为空");
    $('#columnc').css("border-color","red");
    bindC('#columnc');
    return;
  	}
 	var Columnd = $('#columnd').val();
    if(Columnd==""){
    alert("项目所属年份不能为空");
    $('#columnd').parent().css("border-color","red");
    $('#columnd').bind("click", function(){
	$(this).parent().css("border-color","");
	});
    return;
  	}
 	var Columne = $('#columne').val();
    if(Columne==""){
    alert("是否计划内项目不能为空");
    $('#columne').parent().css("border-color","red");
    $('#columne').bind("change", function(){
		$(this).parent().css("border-color","");
	});
    return;
  	}
 	var Columnf = $('#columnf').val();
    if(Columnf==""){
    alert("开支类型不能为空");
   	$('#columnf').parent().css("border-color","red");
    $('#columnf').bind("change", function(){
		$(this).parent().css("border-color","");
	});
    return;
  	}
 	var Columng = $('#columng').val();
    if(Columng==""){
    alert("自行/委托采购不能为空");
    $('#columng').parent().css("border-color","red");
    $('#columng').bind("change", function(){
		$(this).parent().css("border-color","");
	});
    return;
  	}
  	var Columnh = $('#columnh').val();
	if(Columng == "委托代理" ){
   		if(Columnh==""){
   		alert("代理公司名称不能为空");
		$('#columnh').css("border-color","red");
		bindC('#columnh');
		return;
		}
	}
  	var Columni = $('#columni').val();
    if(Columni==""){
    alert("采购方式(首次)不能为空");
    $('#columni').parent().css("border-color","red");
    $('#columni').bind("change", function(){
		$(this).parent().css("border-color","");
	});
    return;
  	}
  	var Columnj = $('#columnj').val();
    if(Columnj==""){
    alert("采购方式(最后一次)不能为空");
    $('#columnj').parent().css("border-color","red");
    $('#columnj').bind("change", function(){
		$(this).parent().css("border-color","");
	});
    return;
  	}
  	var Columnk = $('#columnk').val();
    if(Columnk==""){
    alert("资格审查方式不能为空");
    $('#columnk').parent().css("border-color","red");
    $('#columnk').bind("change", function(){
		$(this).parent().css("border-color","");
	});
    return;
  	}
  	var Columnl = $('#columnl').val();
    if(Columnl==""){
    alert("当前进度（周报）不能为空");
    $('#columnl').parent().css("border-color","red");
    $('#columnl').bind("change", function(){
		$(this).parent().css("border-color","");
	});
	return;
  	}
    var Columnn = $('#columnn').val();
    if(Columnn==""){
  	alert("采购方案预估金额不能为空");
   	$('#columnn').css("border-color","red");
   	bindC('#columnn');
   	return;
	}
	var reg =/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,6})?$/ ; 
	if(!reg.test(Columnn)){
	alert("采购方案预估金额格式为数字且最多六位小数");
	$('#columnn').css("border-color","red");
	bindC('#columnn');
	return;
	}
	var Columnao = $('#columnao').val();
	if(!(Columnao=="")){
		if(!reg.test(Columnao)){
		alert("采购结果金额（万元）格式为数字且最多六位小数");
		$('#columnao').css("border-color","red");
		bindC('#columnao');
		return;
		}
		var nn = parseFloat($('#columnn').val());
		var ao = parseFloat($('#columnao').val());
			if (nn < ao){
			alert("采购结果金额（万元）应小于等于采购方案预估金额（万元）");
			$('#columnao').css("border-color","red");
			bindC('#columnao');
			return;
			}
	}
	var Columnaw = $('#columnaw').val();
	if(!(Columnaw=="")){
		if(!reg.test(Columnaw)){
		alert("合同金额（万元）格式为数字且最多六位小数");
		$('#columnaw').css("border-color","red");
		bindC('#columnaw');
		return;
		}
		var ao = parseFloat($('#columnao').val());
		var aw = parseFloat($('#columnaw').val());
		if (ao < aw){
		alert("合同金额（万元）应小于等于采购结果金额（万元）");
		$('#columnaw').css("border-color","red");
		bindC('#columnaw');
		return;
		} 
	}
	var Columnay = $('#columnay').val();
	var count =/^[0-9]+$/; 
	if(!(Columnay=="")){
		if(!count.test(Columnay)){
		alert("流标次数只能为整数不能为空");
		$('#columnay').css("border-color","red");
		bindC('#columnay');
		return;
		}
		if(Columnay>=1){
		var Columnba = $('#columnba').val();
		if(Columnba==""){
		alert("流标原因说明（每次流标都作说明）不能为空");
		$('#columnba').css("border-color","red");
		bindC('#columnba');
		return;
		}		
		}
	}
	var Columnbl = $('#columnbl').val();
	if(!(Columnbl=="")){
		if(Columnbl =="是"){
		var Columnbm = $('#columnbm').val();
			if(Columnbm==""){
			alert("取消原因不能为空");
			$('#columnbm').css("border-color","red");
			bindC('#columnbm');
			return;
			}		
		}
	}
	if(Columnl=="收到请购单"){
		var a = shoudao();
		if(a == false){
	 	return;
	 	}	
	}
	if(Columnl=="工作组讨论"){
		var b = taolun();
		if(b == false){
	 	return;
	 	}	
	}
	if(Columnl=="采购方案呈批"){
		var b = chengpi();
		if(b == false){
	 	return;
	 	}	
	}	
	if(Columnl=="发布公告" || Columnl=="完成评审"){
		var c = fabu();
		if(c == false){
	 	return;
	 	}	
	}
	if(Columnl=="结果确认"){
		var d = queren();
		if(d == false){
	 	return;
	 	};	
	}
	if(Columnl=="结果公示" || Columnl=="合同签订呈批" || Columnl=="合同系统审批"){
		var e = gongshi();
		if(e == false){
	 	return;
	 	};	
	}	
	if(Columnl=="签订纸质合同"){
		var f = qianding();
		if(f == false){
	 	return;
	 	};	
	}
	if(Columnl=="已归档"){
		var g = guidang();
		if(g == false){
	 	return;
	 	};
	}
	var o=checktime();
	if (o==false){
	return;
	};
	
document.forms[0].action="<%=basePath%>purchase/submitPurchaseForm";
document.forms[0].submit(); 
}

function shoudao(){
	var Columno = $('#columno').val();
	if(Columno==""){
	alert("项目启动时间不能为空");
	$('#columno').parent().css("border-color","red");
	$('#columno').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}	
	return true;
}

function taolun(){

	var h = shoudao();
	if(h==false){
	return false;
	}
	/*var Columnp = $('#columnp').val();
	var no=new Date($("#columno").val().replace(/-/g,"\/"));
	var np=new Date($("#columnp").val().replace(/-/g,"\/"));
	if(Columnp==""){
	alert("工作小组会议召开时间不能为空");
	$('#columnp').parent().css("border-color","red");
	$('#columnp').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	if(np < no){
	alert("工作小组会议召开时间应在项目启动时间之后");
	$('#columnp').parent().css("border-color","red");
	$('#columnp').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}*/
	return true;
}

function chengpi(){

	var i = taolun();
	if(i==false){
	return false;
	}
	var Columnq = $('#columnq').val();
	var np=new Date($("#columnp").val().replace(/-/g,"\/"));
	var nq=new Date($("#columnq").val().replace(/-/g,"\/"));
	if(Columnq==""){
	alert("50万以上方案汇报时间/50万以下需求部门提交方案呈批时间不能为空");
	$('#columnq').parent().css("border-color","red");
	$('#columnq').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	/*if(nq < np){
	alert("50万以上方案汇报时间/50万以下需求部门提交方案呈批时间应在工作小组会议召开时间之后");
	$('#columnq').parent().css("border-color","red");
	$('#columnq').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}*/
	return true;
}		
		
function fabu(){ 

	var j = chengpi();
	if(j==false){
	return false;
	}
	var Columnr = $('#columnr').val();
    /*if(Columnr==""){
    alert("地市采购方案决策时间(首次)不能为空");
    $('#columnr').parent().css("border-color","red");
    $('#columnr').bind("click", function(){
		$(this).parent().css("border-color","");
	});
    return false;
 	}*/
 	var Columnt = $('#columnt').val();
	/*if(Columnt==""){
	alert("地市采购方案纪要下达时间不能为空");
	$('#columnt').parent().css("border-color","red");
	$('#columnt').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}*/
 	var Columnu = $('#columnu').val();
	if(Columnu==""){
	alert("地市采购方案发文文号不能为空");
	$('#columnu').css("border-color","red");
	bindC('#columnu');
	return false;
	} 
	var Columnv = $('#columnv').val();
	/*if(Columnv==""){
	alert("省公司采购方案决策时间(首次)不能为空");
	$('#columnv').parent().css("border-color","red");
	$('#columnv').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}*/
	var Columnx = $('#columnx').val();
	if(Columnx==""){
	alert("省公司采购方案纪要/启动通知下达时间不能为空");
	$('#columnx').parent().css("border-color","red");
	$('#columnx').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	var Columnz = $('#columnz').val();
	if(Columnz==""){
	alert("公告发布开始时间不能为空");
	$('#columnz').parent().css("border-color","red");
	$('#columnz').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	var Columnaa = $('#columnaa').val();
	if(Columnaa==""){
	alert("公告发布截止时间不能为空");
	$('#columnaa').parent().css("border-color","red");
	$('#columnaa').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	var Columnab = $('#columnab').val();
	if(Columnab==""){
	alert("采购评审/谈判时间不能为空");
	$('#columnab').parent().css("border-color","red");
	$('#columnab').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	
	var nq=new Date($("#columnq").val().replace(/-/g,"\/"));
	var nr=new Date($("#columnr").val().replace(/-/g,"\/"));
	var ns=new Date($("#columns").val().replace(/-/g,"\/"));
	var nt=new Date($("#columnt").val().replace(/-/g,"\/"));
	var nv=new Date($("#columnv").val().replace(/-/g,"\/"));
	var nw=new Date($("#columnw").val().replace(/-/g,"\/"));
	var nx=new Date($("#columnx").val().replace(/-/g,"\/"));
	var nz=new Date($("#columnz").val().replace(/-/g,"\/"));
	var aa=new Date($("#columnaa").val().replace(/-/g,"\/"));
	var ab=new Date($("#columnab").val().replace(/-/g,"\/"));
	var Columns = $('#columns').val();
	var Columnw = $('#columnw').val();
	
	/*if(nr < nq){
	alert("地市采购方案决策时间(首次)应在50万以上方案汇报时间/50万以下需求部门提交方案呈批时间之后");
	$('#columnr').parent().css("border-color","red");
	$('#columnr').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	if (!(Columns == "")){
		if(ns < nr){
		alert("地市采购方案决策时间(最后一次)应在地市采购方案决策时间(首次)之后");
		$('#columns').parent().css("border-color","red");
		$('#columns').bind("click", function(){
		$(this).parent().css("border-color","");
		});
		return false;
		}
		if(nt < ns){
		alert("地市采购方案纪要下达时间应在地市采购方案决策时间(最后一次)之后");
		$('#columnt').parent().css("border-color","red");
		$('#columnt').bind("click", function(){
		$(this).parent().css("border-color","");
		});
		return false;
		}
	}
	if(Columns == ""){
		if(nt < nr){
		alert("地市采购方案纪要下达时间应在地市采购方案决策时间(首次)之后");
		$('#columnt').parent().css("border-color","red");
		$('#columnt').bind("click", function(){
		$(this).parent().css("border-color","");
		});
		return false;
		}
	}
	if(nv < nt){
	alert("省公司采购方案决策时间(首次)应在地市采购方案纪要下达时间之后");
	$('#columnv').parent().css("border-color","red");
	$('#columnv').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	if(!(Columnw == "")){
		if(nw < nv){
		alert("省公司采购方案决策时间(最后一次)应在省公司采购方案决策时间(首次)之后");
		$('#columnw').parent().css("border-color","red");
		$('#columnw').bind("click", function(){
		$(this).parent().css("border-color","");
		});
		return false;
		}
		if(nx < nw){
		alert("省公司采购方案纪要/启动通知下达时间应在省公司采购方案决策时间(最后一次)之后");
		$('#columnx').parent().css("border-color","red");
		$('#columnx').bind("click", function(){
		$(this).parent().css("border-color","");
		});
		return false;
		}			
	}
	if(Columnw == ""){
		if(nx < nv){
		alert("省公司采购方案纪要/启动通知下达时间应在省公司采购方案决策时间(首次)之后");
		$('#columnx').parent().css("border-color","red");
		$('#columnx').bind("click", function(){
		$(this).parent().css("border-color","");
		});
		return false;
		}
	}*/
	if(nz < nx){
	alert("公告发布开始时间应在省公司采购方案纪要/启动通知下达时间之后");
	$('#columnz').parent().css("border-color","red");
	$('#columnz').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	if(aa < nz){
	alert("公告发布截止时间应在公告发布开始时间之后");
	$('#columnaa').parent().css("border-color","red");
	$('#columnaa').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	if(ab < aa){
	alert("采购评审/谈判时间应在公告发布截止时间之后");
	$('#columnab').parent().css("border-color","red");
	$('#columnab').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	return true;
}	

function queren(){
    
	var k = fabu();
	if(k==false){
	return false;
	}
	var Columnac = $('#columnac').val();
    if(Columnac==""){
    alert("采购组织实施单位/部门（集采类型）不能为空");
    $('#columnac').parent().css("border-color","red");
    $('#columnac').bind("change", function(){
		$(this).parent().css("border-color","");
	});
	return false;
  	}
	var Columnad = $('#columnad').val();
	if(Columnad==""){
	alert("采购组织实施单位/部门不能为空");
	$('#columnad').css("border-color","red");
	bindC('#columnad');
	return false;
	} 
	var Columnae = $('#columnae').val();
    if(Columnae==""){
    alert("需求单位不能为空");
    $('#columnae').parent().css("border-color","red");
    $('#columnae').bind("change", function(){
		$(this).parent().css("border-color","");
	});
	return false;
  	}
  	var Columnaf = $('#columnaf').val();
    if(Columnaf==""){
    alert("采购类型不能为空");
    $('#columnaf').parent().css("border-color","red");
    $('#columnaf').bind("change", function(){
		$(this).parent().css("border-color","");
	});
	return false;
  	}
  	var Columnag = $('#columnag').val();
	if(Columnag==""){
	alert("采购内容不能为空");
	$('#columnag').css("border-color","red");
	bindC('#columnag');
	return false;
	} 
	var Columnah = $('#columnah').val();
    if(Columnah==""){
    alert("项目当前实际状态不能为空");
    $('#columnah').parent().css("border-color","red");
    $('#columnah').bind("change", function(){
		$(this).parent().css("border-color","");
	});
	return false;
  	}
  	var Columnai = $('#columnai').val();
    if(Columnai==""){
    alert("操作方式不能为空");
    $('#columnai').parent().css("border-color","red");
    $('#columnai').bind("change", function(){
		$(this).parent().css("border-color","");
	});
	return false;
  	}
  	var Columnaj = $('#columnaj').val();
    if(Columnaj==""){
    alert("电子采购项目编号不能为空");
    $('#columnaj').css("border-color","red");
	bindC('#columnaj');
	return false;
  	}
  	var Columnak = $('#columnak').val();
    if(Columnak==""){
    alert("ES系统中的采购项目名称不能为空");
    $('#columnak').css("border-color","red");
	bindC('#columnak');
	return false;
  	}
	return true;
}


function gongshi(){
    
	var l = queren();
	if(l==false){
	return false;
	}
	var Columnal = $('#columnal').val();
    if(Columnal==""){
    alert("采购方案决策层级（决策形式）不能为空");
    $('#columnal').parent().css("border-color","red");
    $('#columnal').bind("change", function(){
		$(this).parent().css("border-color","");
	});
	return false;
  	}
  	var Columnam = $('#columnam').val();
    if(Columnam==""){
    alert("采购模式不能为空");
    $('#columnam').parent().css("border-color","red");
    $('#columnam').bind("change", function(){
		$(this).parent().css("border-color","");
	});
	return false;
  	}
  	var Columnan = $('#columnan').val();
    if(Columnan==""){
    alert("采购结果确认层级（确认形式）不能为空");
    $('#columnan').parent().css("border-color","red");
    $('#columnan').bind("change", function(){
		$(this).parent().css("border-color","");
	});
	return false;
  	}
  	var Columnao = $('#columnao').val();
	if(Columnao==""){
	alert("采购结果金额（万元）不能为空");
	$('#columnao').css("border-color","red");
	bindC('#columnao');
	return false;
	}
	var reg1 =/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,6})?$/;		  
	if(!reg1.test(Columnao)){
	alert("采购结果金额（万元）格式为数字且最多六位小数");
	$('#columnao').css("border-color","red");
	bindC('#columnao');
	return false;
	}
	var Columnap = $('#columnap').val();
	if(Columnap==""){
	alert("地市公司采购结果确认时间不能为空");
	$('#columnap').parent().css("border-color","red");
	$('#columnap').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	var Columnar = $('#columnar').val();
	if(Columnar==""){
	alert("省公司采购结果确认时间不能为空");
	$('#columnar').parent().css("border-color","red");
	$('#columnar').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	var nn = parseFloat($('#columnn').val());
	var ao = parseFloat($('#columnao').val());
	if (nn < ao){
	alert("采购结果金额（万元）应小于等于采购方案预估金额（万元）");
	$('#columnao').css("border-color","red");
	bindC('#columnao');
	return false;
	}
	var ab=new Date($("#columnab").val().replace(/-/g,"\/"));
	var ap=new Date($("#columnap").val().replace(/-/g,"\/"));
	var aq=new Date($("#columnaq").val().replace(/-/g,"\/"));
	var ar=new Date($("#columnar").val().replace(/-/g,"\/"));
	var Columnaq = $('#columnaq').val();
	if(ap < ab){
	alert("地市公司采购结果确认时间应在采购评审/谈判时间之后");
	$('#columnap').parent().css("border-color","red");
	$('#columnap').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	if(!(Columnaq=="")){
		if(aq < ap){
		alert("地市采购结果上报时间应在地市公司采购结果确认时间之后");
		$('#columnaq').parent().css("border-color","red");
		$('#columnaq').bind("click", function(){
		$(this).parent().css("border-color","");
		});
		return false;
		}
		if(ar < aq){
		alert("省公司采购结果确认时间应在地市采购结果上报时间之后");
		$('#columnar').parent().css("border-color","red");
		$('#columnar').bind("click", function(){
		$(this).parent().css("border-color","");
		});
		return false;
		}	
	}
	if(Columnaq==""){
	if(ar < ap){
	alert("省公司采购结果确认时间应在地市公司采购结果确认时间之后");
	$('#columnar').parent().css("border-color","red");
	$('#columnar').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
}	
	return true;
}
	
function qianding(){

	var m = gongshi();
	if(m==false){
	return false;
	}
	var Columnas = $('#columnas').val();
	if(Columnas==""){
	alert("合同签署时间不能为空");
	$('#columnas').parent().css("border-color","red");
	$('#columnas').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	var Columnat = $('#columnat').val();
    if(Columnat==""){
    alert("采购结果发文文号不能为空");
    $('#columnat').css("border-color","red");
	bindC('#columnat');
	return false;
  	}
  	var Columnau = $('#columnau').val();
    if(Columnau==""){
    alert("合同编码不能为空");
    $('#columnau').css("border-color","red");
	bindC('#columnau');
	return false;
  	}
  	var Columnav = $('#columnav').val();
    if(Columnav==""){
    alert("中选供应商不能为空");
    $('#columnav').css("border-color","red");
	bindC('#columnav');
	return false;
  	}	
	var Columnaw = $('#columnaw').val();
	if(Columnaw==""){
	alert("合同金额不能为空");
	$('#columnaw').css("border-color","red");
	bindC('#columnaw');
	return false;
	}
	var reg2 =/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,6})?$/;		  
	if(!reg2.test(Columnaw)){
	alert("合同金额格式为数字且最多六位小数");
	$('#columnaw').css("border-color","red");
	bindC('#columnaw');
	return false;
	}	
	var ao = parseFloat($('#columnao').val());
	var aw = parseFloat($('#columnaw').val());
	if (ao < aw){
	alert("合同金额（万元）应小于等于采购结果金额（万元）");
	$('#columnaw').css("border-color","red");
	bindC('#columnaw');
	return false;
	} 
	var ar=new Date($("#columnar").val().replace(/-/g,"\/"));
	var as=new Date($("#columnas").val().replace(/-/g,"\/"));
	if(as < ar){
	alert("合同签署时间应在省公司采购结果确认时间之后");
	$('#columnas').parent().css("border-color","red");
	$('#columnas').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	return true;
}
	
function guidang(){

	var n = qianding();
	if(n==false){
	return false;
	}
	var Columnax = $('#columnax').val();
	if(Columnax==""){
	alert("合同归档时间不能为空");
	$('#columnax').parent().css("border-color","red");
	$('#columnax').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	var Columnay = $('#columnay').val();
	if(Columnay==""){
	alert("流标次数不能为空");
	$('#columnay').css("border-color","red");
	bindC('#columnay');
	return false;
	}
	var count =/^[0-9]+$/; 
	if(!count.test(Columnay)){
	alert("流标次数只能为整数不能为空");
	$('#columnay').css("border-color","red");
	bindC('#columnay');
	return false;
	}
	if(Columnay>=1){
		var Columnba = $('#columnba').val();
		if(Columnba==""){
		alert("流标原因说明（每次流标都作说明）不能为空");
		$('#columnba').css("border-color","red");
		bindC('#columnba');
		return false;
		}		
	}
	var Columnbe = $('#columnbe').val();
	if(Columnbe==""){
	alert("技术商务比例是否符合标准不能为空");
	$('#columnbe').parent().css("border-color","red");
	$('#columnbe').bind("change", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}		  
	var Columnbf = $('#columnbf').val();
	if(Columnbf==""){
	alert("合同模板是否符合标准不能为空");
	$('#columnbf').parent().css("border-color","red");
	$('#columnbf').bind("change", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	var Columnbg = $('#columnbg').val();
	if(Columnbg==""){
	alert("技术评分模板招标文件模板是否符合标准不能为空");
	$('#columnbg').parent().css("border-color","red");
	$('#columnbg').bind("change", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	var Columnbh = $('#columnbh').val();
	if(Columnbh==""){
	alert("投诉情况不能为空");
	$('#columnbh').css("border-color","red");
	bindC('#columnbh');
	return false;
	}
	var Columnbi = $('#columnbi').val();
	if(Columnbi==""){
	alert("中标单位、结算价格和合同单位、结算价格是否完全一致不能为空");
	$('#columnbi').parent().css("border-color","red");
	$('#columnbi').bind("change", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	var Columnbj = $('#columnbj').val();
	if(Columnbj==""){
	alert("采购进度是否影响到成本/投资使用计划一致不能为空");
	$('#columnbj').parent().css("border-color","red");
	$('#columnbj').bind("change", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	var Columnbl = $('#columnbl').val();
	if(Columnbl==""){
	alert("是否取消不能为空");
	$('#columnbl').parent().css("border-color","red");
	$('#columnbl').bind("change", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}
	if(Columnbl =="是"){
		var Columnbm = $('#columnbm').val();
		if(Columnbm==""){
		alert("取消原因不能为空");
		$('#columnbm').css("border-color","red");
		bindC('#columnbm');
		return false;
		}		
	}
	var as=new Date($("#columnas").val().replace(/-/g,"\/"));
	var ax=new Date($("#columnax").val().replace(/-/g,"\/"));
	if(ax < as){
	alert("合同归档时间应在合同签署时间之后");
	$('#columnax').parent().css("border-color","red");
	$('#columnax').bind("click", function(){
	$(this).parent().css("border-color","");
	});
	return false;
	}	
	return true;
}

function ev_list(){
window.location.href="<%=basePath%>purchase/purchaseItem";
}

function autoCompletes(){
		jQuery("#columnb").autocomplete({
			source: function( request, response ) {
				jQuery.ajax({
					url: "<%=basePath%>rulesController/searchUser",
					dataType: "json",
					data: {
						userValue: request.term
					},
					type: "POST",
					success: function(data) {
						if(data!=null){
				     		response(jQuery.map(data, function( item ) {
				     			return {
				     			 value:item[0].userName+" -- "+item[1].orgName,
									userName:item[0].userName,
									userId:item[0].userId,
									account:item[0].account,
									orgName:item[1].orgName,
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
				jQuery("#columnb").val(ui.item.userName);
				return false;
			}
		});
	}
function clean(){
	if (event.keyCode == 13) {
        //js 监听对应的id
         //document.getElementById("demandFzrId_input_focus").value="";
      } 
}
</script>