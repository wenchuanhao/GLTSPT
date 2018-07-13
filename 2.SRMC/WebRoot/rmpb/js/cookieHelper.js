
//根据name获取cookie值，若不存在返回null
function getCookie(c_name)
{
	if(document.cookie.length>0){		
		c_start=document.cookie.indexOf(c_name + "=");
		if (c_start!=-1){ 
		    c_start=c_start + c_name.length+1; 
		    c_end=document.cookie.indexOf(";",c_start);
		    if (c_end==-1) c_end=document.cookie.length;
		    return unescape(document.cookie.substring(c_start,c_end));
	    } 
	}
	return ""
}
//添加cookie(名称，值，保留天数)
function setCookie(c_name,value,expiredays)
{
	var exdate=new Date();
	exdate.setDate(exdate.getDate()+expiredays);
	document.cookie=c_name+ "=" +escape(value)+((expiredays==null) ? "" : ";expires="+exdate.toGMTString())+";path=/";
}
//删除cookie(名称)
function delCookie(c_name)
{
	setCookie(c_name,null,-1);
}