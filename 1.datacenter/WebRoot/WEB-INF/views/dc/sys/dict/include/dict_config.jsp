<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page import="com.cdc.sys.dict.model.SysParameter" %>
<%@ page import="com.cdc.system.core.cache.SysParamHelper" %>
<%@ page import="java.io.IOException" %>
<%
	String params = (String)request.getAttribute("javax.servlet.include.query_string");
	String paramTypeCode = "",paramCode = "",paramDesc = "";
	for(int i=0;i<params.split("&").length;i++){
		String [] temp = params.split("&")[i].split("=");
		if("paramTypeCode".equals(temp[0])){
			if(temp.length == 2)
			paramTypeCode = temp[1];
		}else if("paramCode".equals(temp[0])){
			if(temp.length == 2)
			paramCode = temp[1];
		}else if("paramDesc".equals(temp[0])){
			if(temp.length == 2)
			paramDesc = temp[1];
		}
	}
	SysParameter sysParameter = SysParamHelper.getSysParamByCode(paramTypeCode,paramCode);
	if(sysParameter.getParameterValue() != null && !"".equals(sysParameter.getParameterValue())){
		if(paramDesc != null && "true".equals(paramDesc)){
		    out.print(sysParameter.getParameterDesc());
		}else{
		    out.print(sysParameter.getParameterValue());
		}
	}else{
		out.print("");
	}
%>