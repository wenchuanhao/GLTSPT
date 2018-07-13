<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>        
  <head>   
  <script type="text/javascript">  
  function cjkEncode(text) {       
    if (text == null) {       
        return "";       
    }       
    var newText = "";       
    for (var i = 0; i < text.length; i++) {       
        var code = text.charCodeAt (i);        
        if (code >= 128 || code == 91 || code == 93) {//91 is "[", 93 is "]".       
            newText += "[" + code.toString(16) + "]";       
        } else {       
            newText += text.charAt(i);       
        }       
    }       
    return newText;       
}  
    function doSubmit() {        
    alert("123");
        var username = cjkEncode("0270102"); //获取输入的用户名    
        alert(username);
        var password = cjkEncode("123456");  //获取输入的参数        
        alert(password);
        var scr = document.createElement("iframe");      //创建iframe      
        scr.src = " http://localhost:8075/WebReport/ReportServer?op=fs_load&cmd=sso&fr_username=" + username + "&fr_password=" + password;   //将报表验证用户名密码的地址指向此iframe
        alert(scr.src);        
        if (scr.attachEvent)  
            {       //判断是否为ie浏览器    
               scr.attachEvent("onload", function(){                    //如果为ie浏览器则页面加载完成后立即执行    
                   /*跳转到指定登录成功页面，index.jsp   
                   var f = document.getElementById("login");   
                   f.submit(); */   
               window.location=" http://localhost:8075/WebReport/ReportServer?reportlet=WorkBook7.cpt"; //直接跳转到数据决策系统  
               });    
            } else {    
               scr.onload = function(){              //其他浏览器则重新加载onload事件    
                   /*跳转到指定登录成功页面,index.jsp   
                    var f = document.getElementById("login");   
                    f.submit();  */  
                window.location=" http://localhost:8075/WebReport/ReportServer?reportlet=WorkBook7.cpt"; //直接跳转到数据决策系统    
            };    
         }    
         alert("abc");
     document.getElementsByTagName("head")[0].appendChild(scr);   //将iframe标签嵌入到head中      
   }     
 </script>        
</head>        
<body>        
  <p>请登录</p>        
  <form id="login" name="login" method="GET"  action="" >        
    <p>用户名:<input id="username" type="text" name="username" /></p>        
    <p>密 码:<input id="password" type="password" name="password"/></p>          
    <input type="button" value="登录" onClick="doSubmit()" />        
  </form>        
 </body>        
</html> 
