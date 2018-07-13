<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<option value="-1">请选择</option>
<option ${rulesForm.status==3 ? "selected=\"selected\"":null} value="3">已发布</option>
<option ${rulesForm.status==2 ? "selected=\"selected\"":null} value="2">审核中</option>
<option ${rulesForm.status==5 ? "selected=\"selected\"":null} value="5">已修订</option>
<option ${rulesForm.status==4 ? "selected=\"selected\"":null} value="4">已废止</option>