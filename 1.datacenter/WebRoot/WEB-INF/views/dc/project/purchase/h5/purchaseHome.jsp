<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String basePath = request.getContextPath() + "/";%>
	<div class="wrap">
		<div class="header_title">
			监控指标 <a class="black" href="javascript:app.exit();"></a> <a class="search"></a>
		</div>
		<div class="header_hight"></div>
		<form name="form" id="pageFormH5" method="post"  >
			<input type="hidden" value="" name="loginId" id="loginId"/>
			<input type="hidden" value="N" name="isPages" id="isPages"/>
			<input type="hidden" value="${purchaseForm.pageIndex}" id="pageIndex"	name="pageIndex"/>
			<input type="hidden" value="${purchaseForm.pageSize}" id="pageSize"	name="pageSize"/>
			<input type="hidden" id="searchType" name="searchType" value="" />
			<input id="columnC" name="columnC" value="${purchaseForm.columnC }"  type="hidden"  />
   			<input name="beginCreateTime" id="beginCreateTime" value="<fmt:formatDate value='${purchaseForm.beginCreateTime}' pattern='yyyy-MM-dd'/>" type="hidden"/>
			<input name="endCreateTime" id="endCreateTime" value="<fmt:formatDate value='${purchaseForm.endCreateTime}' pattern='yyyy-MM-dd'/>" type="hidden"/>
		<div class="header_box">
			<div class="header_list">
				<div>
					<span>创建时间：</span>
					<input id="beginCreateTime_temp" type="text" value="<fmt:formatDate value='${purchaseForm.beginCreateTime}' pattern='yyyy-MM-dd'/>" placeholder="开始时间" class="adate date_bg" />
					<span style="color:#000;margin:0 6.6%;">至</span>
					<input id="endCreateTime_temp" type="text" value="<fmt:formatDate value='${purchaseForm.endCreateTime}' pattern='yyyy-MM-dd'/>" placeholder="结束时间" class="adate date_bg right" />
				</div>
				<div class="clean_withe">
					<span class="font-size top-span">需求部门：</span>
					<div class="drop-down-box font-size">
						<span class="drop-down-txt">
							<c:if test="${not empty purchaseForm.columnC }">${purchaseForm.columnC }</c:if>
							<c:if test="${empty purchaseForm.columnC }">请选择</c:if>
						</span>
						<button class="drop-down-btn"></button>
						<ul>
							<li><a href="javascript:void(0)" onclick="setColumnValue('')">请选择</a></li>
							<c:forEach items="${depList}" var="item" varStatus="i">
								<li><a href="javascript:void(0)" onclick="setColumnValue('${item.orgName}')">${item.orgName}</a></li>
							</c:forEach>
						</ul>
					</div>
					<span style="margin-left:4%;" class="font-size top-span">经办人：</span>
					<input id="columnB" name="columnB" value="${purchaseForm.columnB }"  type="text"  placeholder="请填写经办人" class="right font-size" />
				</div>
				<div class="menu_div">
					<a class="header_menu" onclick="ev_search()" ><i class="search_bg"></i>查询</a>
					<a class="header_menu" onclick="ev_reset()"  style="float: right;"><i class="refresh"></i>重置</a>
				</div>
			</div>
			<div class="menu_down">
				<i class="menu_down_bg"></i>
			</div>
		</div>
		</form>
		<div class="body_bg"></div>
		<div class="chart_wrap">
			<h3 class="chart_title">采购全景监控总表</h3>
			<span class="switch">图表</span>
		</div>
		<div class="main01">
			<h3 class="chart_title02">采购全景监控项目情况</h3>
			<div id="container01" style="width:200;height:180px;"></div>
			<h3 class="chart_title02">完成评审项目情况</h3>
			<div id="container02" style="width:200;height:180px"></div>
			<h3 class="chart_title02">已完成项目情况</h3>
			<div id="container03" style="width:200;height:180px"></div>
		</div>
		<div class="main02">
			<table>
				<tr>
					<td>总数</td>
					<td>收到请购单</td>
					<td>工作组讨论</td>
					<td>采购方案呈批</td>
					<td>发布公告</td>
				</tr>
				<c:forEach items="${tqc}" var="vo" varStatus="i">
					<tr>
						<td>${vo.c1}</td>
						<td>${vo.c2}</td>
						<td>${vo.c3}</td>
						<td>${vo.c4}</td>
						<td>${vo.c5}</td>
					</tr>
				</c:forEach>
				<tr>
					<td>完成评审</td>
					<td>结果确认</td>
					<td>结果公示</td>
					<td>合同签订呈批</td>
					<td>合同系统审批</td>
				</tr>
				<c:forEach items="${tqc}" var="vo" varStatus="i">
					<tr>
						<td>${vo.c6}</td>
						<td>${vo.c7}</td>
						<td>${vo.c8}</td>
						<td>${vo.c9}</td>
						<td>${vo.c10}</td>
					</tr>
				</c:forEach>
				<tr>
					<td>签订纸质合同</td>
					<td>已归档</td>
					<td>已取消</td>
					<td>完成评审项目</td>
					<td>已完成项目数</td>
				</tr>
				<c:forEach items="${tqc}" var="vo" varStatus="i">
					<tr>
						<td>${vo.c11}</td>
						<td>${vo.c12}</td>
						<td>${vo.c13}</td>
						<td>${vo.c14}</td>
						<td>${vo.c15}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="chart_wrap">
			<h3 class="chart_title">需求确认时长</h3>
			<span class="switch">图表</span>
		</div>
		<div class="main01">
			<div id="container04" style="width:80%;height:180px;margin:0 auto;"></div>
		</div>
		<div class="main02">
			<table>
				<tr>
					<td>平均时长</td>
					<td>最大时长</td>
					<td>最小时长</td>
					<td>超过标准时长项目数</td>
				</tr>
				<c:forEach items="${con}" var="v1" varStatus="i">
					<tr>
						<td>${v1.avgtime}</td>
						<td>${v1.maxtime}</td>
						<td>${v1.mintime}</td>
						<td>${v1.stime}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty con}">
					<tr>
						<td colspan="4">找不到对应的数据</td>
					</tr>
				</c:if>
			</table>
		</div>
		<div class="chart_wrap">
			<h3 class="chart_title">需求确认完毕时间-评审时长</h3>
			<span class="switch">图表</span>
		</div>
		<div class="main01">
			<div id="container05" style="width:80%;height:180px;margin:0 auto;"></div>
		</div>
		<div class="main02">
			<table>
				<tr>
					<td>平均时长</td>
					<td>最大时长</td>
					<td>最小时长</td>
				</tr>
				<c:forEach items="${rev}" var="v2" varStatus="i">
					<tr>
						<td>${v2.avgtime}</td>
						<td>${v2.maxtime}</td>
						<td>${v2.mintime}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty rev}">
					<tr>
						<td colspan="3">找不到对应的数据</td>
					</tr>
				</c:if>
			</table>
		</div>
		<div class="chart_wrap">
			<h3 class="chart_title">需求确认完毕-合同系统审批完毕</h3>
			<span class="switch">图表</span>
		</div>
		<div class="main01">
			<div id="container06" style="width:80%;height:180px;margin:0 auto;"></div>
		</div>
		<div class="main02">
			<table>
				<tr>
					<td>平均时长</td>
					<td>最大时长</td>
					<td>最小时长</td>
				</tr>
				<c:forEach items="${app}" var="v3" varStatus="i">
					<tr>
						<td>${v3.avgtime}</td>
						<td>${v3.maxtime}</td>
						<td>${v3.mintime}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty app}">
					<tr>
						<td colspan="3">找不到对应的数据</td>
					</tr>
				</c:if>
			</table>
		</div>
		<div class="chart_wrap">
			<h3 class="chart_title">规范性</h3>
			<span class="switch">图表</span>
		</div>
		<div class="main01">
			<div id="container07" style="width:80%;height:250px;margin:0 auto;"></div>
		</div>
		<div class="main02">
			<table>
				<tr>
					<td>未使用技术评<br />分表模板项目数</td>
					<td>未使用合同<br />模板项目数</td>
					<td>技术与商务比例<br />不符合<br />公司规定项目数</td>
					<td>采购过程被<br />供应商投诉项目数</td>
				</tr>
				<c:forEach items="${tem}" var="v4" varStatus="i">
					<tr>
						<td>${v4.c1}</td>
						<td>${v4.c2}</td>
						<td>${v4.c3}</td>
						<td>${v4.c4}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty tem}">
					<tr>
						<td colspan="4">找不到对应的数据</td>
					</tr>
				</c:if>
			</table>
		</div>
		<div class="chart_wrap">
			<h3 class="chart_title">流标情况</h3>
			<span class="switch">图表</span>
		</div>
		<div class="main01">
			<div id="container08" style="width:80%;height:180px;margin:0 auto;"></div>
		</div>
		<div class="main02">
			<table>
				<tr>
					<td>发生过流标<br />情况的项目数</td>
					<td>流标次数<br />总计</td>
					<td>流标后更改<br />采购方式项目数</td>
				</tr>
				<c:forEach items="${bid}" var="v5" varStatus="i">
					<tr>
						<td>${v5.c1}</td>
						<td>${v5.c2}</td>
						<td>${v5.c3}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty bid}">
					<tr>
						<td colspan="3">找不到对应的数据</td>
					</tr>
				</c:if>
			</table>
		</div>
		<div class="chart_wrap">
			<h3 class="chart_title">单一来源采购情况</h3>
			<span class="switch">图表</span>
		</div>
		<div class="main01">
			<h3 class="chart_title02">单一来源采购数量情况</h3>
			<div id="container09" style="width:200;height:180px"></div>
			<h3 class="chart_title02">单一来源采购金额情况</h3>
			<div id="container10" style="width:200;height:180px"></div>
		</div>
		<div class="main02">
			<table>
				<tr>
					<td>总数</td>
					<td>数量占比</td>
					<td>总额(万元)</td>
					<td>金额占比</td>
				</tr>
				<c:forEach items="${onl}" var="v6" varStatus="i">
					<tr>
						<td>${v6.c1}</td>
						<td>${v6.c2}</td>
						<td>${v6.c3}</td>
						<td>${v6.c4}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty onl}">
					<tr>
						<td colspan="4">找不到对应的数据</td>
					</tr>
				</c:if>
			</table>
		</div>
		<div class="chart_wrap">
			<h3 class="chart_title">采购成本监控表</h3>
			<span class="switch">图表</span>
		</div>
		<div class="main01">
			<div id="container11" style="width:80%;height:150px;margin:0 auto;"></div>
		</div>
		<div class="main02">
			<table>
				<tr>
					<td>采购项目预算金额<br /> （万元）</td>
					<td>签订合同金额<br /> （万元）</td>
					<td>节约金额 <br /> （万元）</td>
					<td>节支率</td>
				</tr>
				<c:forEach items="${cos}" var="v7" varStatus="i">
					<tr>
						<td>${fn:contains(v7.c1, '.') ? fn:substring(v7.c1, 0,
							fn:indexOf(v7.c1, '.')+3) : v7.c1 }</td>
						<td>${fn:contains(v7.c2, '.') ? fn:substring(v7.c2, 0,
							fn:indexOf(v7.c2, '.')+3) : v7.c2 }</td>
						<td>${fn:contains(v7.c3, '.') ? fn:substring(v7.c3, 0,
							fn:indexOf(v7.c3, '.')+3) : v7.c3 }</td>
						<td>${v7.c4}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty cos}">
					<tr>
						<td colspan="4">找不到对应的数据</td>
					</tr>
				</c:if>
			</table>
		</div>
		<div class="footer_hight"></div>
	</div>
	<script type="text/javascript">var basePath = "<%=basePath%>";</script>
	<script type="text/javascript" src="/SRMC/dc/purchase/h5/js/jq.js"></script>
	<script type="text/javascript" src="/SRMC/dc/purchase/h5/js/js.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/jquery.form.js"></script>
	<script type="text/javascript">
		function ev_search(){
			$("#beginCreateTime").val($("#beginCreateTime_temp").val());
			$("#endCreateTime").val($("#endCreateTime_temp").val());
			init();
		}
		function setColumnValue(val){
			$("#columnC").val(val);
		}
		//重置查询
		function ev_reset(){
			$("#beginCreateTime").val("");
			$("#endCreateTime").val("");
			$("#beginCreateTime_temp").val("");
			$("#endCreateTime_temp").val("");
			$("#columnC").val("");
			$("#columnB").val("");
			ev_search();
		}
	</script>	