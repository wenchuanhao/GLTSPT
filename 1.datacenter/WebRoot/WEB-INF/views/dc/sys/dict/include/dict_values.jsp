<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page import="com.cdc.sys.dict.model.SysParameter" %>
<%@ page import="com.cdc.system.core.cache.SysParamHelper" %>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page import="java.io.IOException" %>
<%
	String dict = request.getParameter("dict");
	String[] dictCodes = dict.split(",");
    StringBuilder sb = new StringBuilder();
    sb.append("<script type=\"text/javascript\">var dict_values=");
	JSONObject obj = new JSONObject();
	for(String dictCode : dictCodes){
		List<SysParameter> rulesGrades = SysParamHelper.getSysParamListByParamTypeCode(dictCode);
	    if(rulesGrades!=null){
	    	JSONArray json = JSONArray.fromObject(rulesGrades);
			obj.put(dictCode, json);
	    }
	}
   	sb.append(obj);
    sb.append(";</script>");
    out.print(sb.toString());
%>
<script type="text/javascript">
$(function(){
	var $th = $("[dictType]");//默认是表格中th属性
	$.each($th,function(kd,th){
	
		var result = dict_values[$(th).attr("dictType")];//获取字典值
		var index = $(th).index();
		
		var $td = $(th).parent().parent().find("tr").find("td:eq("+index+")");
		
		//遍历td,并从result数据字典中设置值
		$.each($td,function(k,v){
			$.each(result,function(k1,v1){
				if($(v).html() == v1.parameterCode){
					$(v).html(v1.parameterName);
				}
			});
		});	
	});
});
</script>