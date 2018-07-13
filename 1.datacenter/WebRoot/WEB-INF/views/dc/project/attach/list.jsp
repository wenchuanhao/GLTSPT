<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getContextPath()+ "/"; 
%>
                                                                        	<c:forEach items="${listAttach}" var="f"  varStatus="i">
                                                                            <tr>
                                                                            	<%-- 文档名称 --%>
                                                                                <td class="seven_td" style="text-align: left;">
                                                                                <c:if test="${f.column07 eq '1'}"><input name="ids"  id="ids"  type="checkbox"  value="${f.id}" />
                                                                                		<c:if test="${f.column08 eq null}">
                                                                                		<a id="file_download" title="附件缺失,请补充"  href="javascript:ev_edit('${f.parentId}','${f.id}')"  style="color: red;cursor:pointer;">${f.column01}</a>
                                                                                		</c:if>
                                                                                		<c:if test="${f.column08 ne null}">
                                                                                		<a id="file_download" title="下载${f.column08}" href="javascript:ev_download('${f.id}')"  style="color: #40baff!important;cursor:pointer;" (<fmt:formatNumber value="${f.column09/1024}" pattern="#"/>KB)"  >${f.column01}</a>
                                                                                		</c:if>                                                                                		
                                                                                </c:if>
                                                                                <c:if test="${f.column07 ne '1'}">${f.column01}</c:if>
                                                                                </td>
                                                                                <%-- 有效期 --%>
                                                                                <td class="seven_td" width="10%"><fmt:formatDate value="${f.column02}" pattern="yyyy-MM-dd "/></td>
                                                                                <%-- 文档信息 --%>
                                                                                <td class="seven_td" width="25%">
                                                                                	<c:if test="${f.column07 ne '1'}">${f.column07 eq '2' ? '纸质原件':''}${f.column07 eq '3' ? '纸质复印件':''}|${f.column03}份<br/>${f.column04}</c:if>
                                                                                	<c:if test="${f.column07 eq '1'}">电子文档</c:if>																		    		
                                                                                </td>
                                                                                <%-- 上传信息 --%>
                                                                                <td class="seven_td" width="10%">${f.createUserName}<br/><fmt:formatDate value="${f.createDate}" pattern="yyyy-MM-dd "/></td>
                                                                                <%-- 借阅人 --%>
                                                                                <td class="seven_td" width="10%"><c:if test="${f.column07 ne '1'}">${f.column06}</c:if></td>
                                                                                <%-- 操作 --%>
                                                                                <td class="seven_td" width="10%">
                                                                                <c:if test="${fileType ne '5'}">
	                                                                            <a href="javascript:ev_add('${f.parentId}','')" style="color: #40baff!important;cursor:pointer;" class="t_btn01 upload">上传</a><br/>
	                                                                            <c:if test="${f.createUserId eq sysUserId}">
	                                                                                <a href="javascript:ev_edit('${f.parentId}','${f.id}')"  style="color: #40baff!important;cursor:pointer;" class="t_btn01">编辑</a><br/>
	                                                                                <a href="javascript:ev_delete('${f.parentId}','${f.id}')"  style="color: #40baff!important;cursor:pointer;" class="t_btn01 d_del">删除</a>
	                                                                            </c:if>
                                                                                </c:if>
                                                                                <c:if test="${fileType eq '5'}"><!-- 一览表 -->
                                                                                	<c:if test="${f.column07 eq '1'}"><!-- 电子文档 -->
                                                                                		<a href="javascript:ev_download('${f.id}')"  style="color: #40baff!important;cursor:pointer;" class="t_btn01 upload">下载</a>
                                                                                	</c:if>
                                                                                	<c:if test="${f.column07 ne '1'}">
                                                                                		<c:if test="${f.column03 > 0}"><a href="javascript:ev_openJy('${f.id}')"  style="color: #40baff!important;cursor:pointer;" class="t_btn01 d_del">借阅</a><br/></c:if>
                                                                                		<c:if test="${role ne null && (role eq 'GC_YWGLY' or (role eq 'GC_XMFZR' && (f.createUserId eq sysUserId)))}">
                                                                                			<c:if test="${not empty f.listJy}"><a href="javascript:ev_openGh('${f.id}')"  style="color: #40baff!important;cursor:pointer;" class="t_btn01">归还</a></c:if>
                                                                                		</c:if>
                                                                                	</c:if>                                                                    
                                                                                </c:if>                                                                                
                                                                                </td>
                                                                            </tr>
                                                                            </c:forEach>
																		   <c:if test="${empty listAttach}">
                                                                            <tr>
                                                                                <td class="seven_td" >&nbsp;</td>
                                                                                <td class="seven_td" width="10%">&nbsp;</td>
                                                                                <td class="seven_td" width="25%">&nbsp;</td>
                                                                                <td class="seven_td" width="10%">&nbsp;</td>
                                                                                <td class="seven_td" width="10%">&nbsp;</td>
                                                                                <td class="seven_td" width="10%">
                                                                                <c:if test="${fileType ne '5'}">
	                                                                                <a href="javascript:ev_add('${attach.parentId}','')" style="color: #40baff!important;cursor:pointer;" class="t_btn01 upload">上传</a>
	                                                                            </c:if>
	                                                                            </td>
                                                                            </tr>
																		   </c:if> 