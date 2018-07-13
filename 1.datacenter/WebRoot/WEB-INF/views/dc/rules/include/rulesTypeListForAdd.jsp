<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="/SRMC/dc/js/checkboxList.js"></script>
<div class="checxListTreeWrap w400">  
    <input class="clac_txt" id="sel_02" type="text" name="classic" placeholder="请选择"/>
    <ul class="list_p">
    	<li <c:if test="${empty rulesInfo.rulesTypeId}">class="selected"</c:if> val=""><span tex="请选择">--请选择</span></li>
     <c:forEach items="${rulesTypeList}" var="item"  varStatus="i">
     		<li val="${item.typeId}">--${item.typeName}
     			<c:if test="${not empty item.nextList}">
     				<ul>
     					<c:forEach items="${item.nextList}" var="item_child"  varStatus="i">
     						<li <c:if test="${rulesInfo.rulesTypeId==item_child.typeId }">class="selected"</c:if> val="${item_child.typeId}"><span tex="${item.typeName}--${item_child.typeName}">----${item_child.typeName}</span></li>
     					</c:forEach>
     				</ul>
     			</c:if>
     		</li>
     </c:forEach>
    </ul>
</div>
