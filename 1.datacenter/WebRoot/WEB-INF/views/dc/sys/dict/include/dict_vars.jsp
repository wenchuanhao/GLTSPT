<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cdc.sys.dict.model.SysParameter" %>
<%@ page import="com.cdc.system.core.cache.SysParamHelper" %>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page import="java.io.IOException" %>
<%
	String dict = (String)request.getAttribute("javax.servlet.include.query_string");
	String[] dictCodes = dict.replace("dict=","").split("%2c");
    StringBuilder sb = new StringBuilder();
    sb.append("<script type=\"text/javascript\">var dict_values=");
	JSONObject obj = new JSONObject();
	for(String dictCode : dictCodes){
		List<SysParameter> list = SysParamHelper.getSysParamListByParamTypeCode(dictCode);
	    if(list!=null){
	    	JSONArray json = JSONArray.fromObject(list);
			obj.put(dictCode, json);
	    }
	}
   	sb.append(obj);
    sb.append(";</script>");
    out.print(sb.toString());
%>
<script type="text/javascript">
$(function(){
		_rebuildSelect();
//     _rebuildDictInputByAttr("checkbox");
//     _rebuildDictInputByAttr("radio");
});

function _rebuildSelect($areaId) {
    var selector = arguments.length == 1 ? "#" + $areaId + " " + " select" : "select";
    var selectList = $(selector);
    $.each(selectList, function (index, content) {
        var selObj = $(content);
        var dictName = selObj.attr("dictType");
		if (!_isNullValue(dictName)) {
	        selObj.html("");
	        
	        var $data = dict_values[dictName];
	        //默认值
	        var defaultValue = selObj.attr("defaultValue");
	        //默认提示
	        var tipStr = selObj.attr("defaultTip");
	        if (_isNullValue(tipStr)){
	        	tipStr = "<option value=''>请选择</option>";
	       	}else {
	       		tipStr = (tipStr == "" ? "" : "<option value=''>" + tipStr + "</option>");
	       	}
	        selObj.append(tipStr);
	        for (var keyTemp in $data) {
	        	var key = $data[keyTemp].parameterCode;
                selObj.append("<option value='" + key + "'" + (key == defaultValue ? "selected" : "") + ">" + $data[keyTemp].parameterValue + "</option>");
	        }
		}

    });
}
function _isNullValue($value) {
    return $value == undefined || $value == null;
}
</script>