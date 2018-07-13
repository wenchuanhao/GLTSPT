<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<script language="javascript">
function switchSysBar(){
 if (parent.document.getElementById('frame-body').cols=="210,7,*"){
 document.getElementById('leftbar').style.display="";
 parent.document.getElementById('frame-body').cols="0,7,*";
 }
 else{
 parent.document.getElementById('frame-body').cols="210,7,*";
 document.getElementById('leftbar').style.display="none"
 }
}
function load(){
 if (parent.document.getElementById('frame-body').cols=="0,7,*"){
 document.getElementById('leftbar').style.display="";
 }
}
</script>
</head>
<body marginwidth="0" marginheight="0" bgcolor="#000000" onLoad="load()" topmargin="0" leftmargin="0">
<center>
<table height="100%" cellspacing="0" cellpadding="0" border="0" width="100%">
<tbody>
<tr>
<td id="leftbar" bgcolor="#f5f4f4" style="display: none; background:url(/SRMC/tec/images/drag/bg.gif) repeat-y;">
<a onClick="switchSysBar()" href="javascript:void(0);">
<img height="51" border="0" width="7" alt="展开左侧菜单" src="/SRMC/tec/images/drag/arrow_right.gif"/>
</a>
</td>
<td id="rightbar" bgcolor="#f5f4f4" style=" background:url(/SRMC/tec/images/drag/bg.gif) repeat-y;">
<a onClick="switchSysBar()" href="javascript:void(0);">
<img height="51" border="0" width="7" alt="隐藏左侧菜单" src="/SRMC/tec/images/drag/arrow_left.gif"/>
</a>
</td>
</tr>
</tbody>
</table>
</center>
</body>
</html>