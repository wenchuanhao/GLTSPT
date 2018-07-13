function checkVal(objId, max, msg, isConfirm) {
	var byteValLen = 0;
	var obj = document.getElementById(objId);
	var val = obj.value.replace(/(^\s*)/g, "");
	if (val == null || val == "" ) {
		if (isConfirm) {
			if (confirm(msg)) {
				return true;
			}
		} else {
			alert(msg);
		}
		obj.value="";
		obj.focus();
		return false;
	} else {
		for (var i = 0; i < val.length; i++) {
			if (val.charAt(i).match(/[^\x00-\xff]/ig) != null) {
				byteValLen += 2;
			} else {
				byteValLen += 1;
			}
			if (byteValLen > max) {
				alert("\u8d85\u51fa\u6307\u5b9a\u957f\u5ea6\uff1a" + max + "\uff0c\u8bf7\u4fee\u6539\u3002(\u6ce8\uff1a\u4e2d\u6587\u53602\u4e2a\u5b57\u7b26\u3002)");
				selectFun(obj, i);
				return false;
			}
		}
	}
	return true;
}
//对象id ，最大长度
function checkValLenth(objId, max) {
	var byteValLen = 0;
	var obj = document.getElementById(objId);
	var val = obj.value.replace(/(^\s*)|(\s*$)/g, "");
	if (val != null || val != "") {
		for (var i = 0; i < val.length; i++) {
			if (val.charAt(i).match(/[^\x00-\xff]/ig) != null) {
				byteValLen += 2;
			} else {
				byteValLen += 1;
			}
			if (byteValLen > max) {
				alert("\u8d85\u51fa\u6307\u5b9a\u957f\u5ea6\uff1a" + max + "\uff0c\u8bf7\u4fee\u6539\u3002(\u6ce8\uff1a\u4e2d\u6587\u53602\u4e2a\u5b57\u7b26\u3002)");
      			//当前第i个以后的字符都超出长度限制
				selectFun(obj, i);
				return false;
			}
		}
	}
	return true;
} 
function selectFun(obj, s) {
	var textRange = obj.createTextRange();
	textRange.moveStart("character", s);
	textRange.select();
}
function checkRadio(objName,msg){
	var objs = document.getElementsByName(objName);
	if(objs.length>0){
		var val="";
		for(var i = 0;i<objs.length;i++){
		 	if(objs[i].checked)
         		val =objs[i].value;
		}
		if(val == null || val== "" ){
			alert(msg);
			return false;
		}
		return true;
	}
	return false;
} 
//校验电邮地址
function checkEmail(email){
	var obj=document.getElementById(email);
	//对电子邮件的验证
	var myreg = /^([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[\-|_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(obj.value!=""){
		if(!myreg.test(obj.value)){
			alert('提示:请输入有效的电子邮箱地址!');
			obj.focus();
			return false;
		}
		return true;
	}
	return false;
}
function isMobil(s) {
	var sMobile = document.getElementById(s);
	if(!(/^1[3|4|5|8|7][0-9]\d{4,8}$/.test(sMobile.value))){ 
		alert("不是完整的11位手机号或者正确的手机号前七位"); 
		sMobile.focus(); 
		return false; 
	}else
		return true;
}
 function checkPhone(id)    
{    
	var phone = document.getElementById(id);
	var p1 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
	if (!p1.test(phone.value)){
		alert('请输入有效电话号码！');
		phone.focus();
		return false;
	}else
		return true;
}
function isPhoneOrTel(s) {
	var sMobile = document.getElementById(s);
	var p1 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
	if (!p1.test(sMobile.value) && !(/^1[3|4|5|8][0-9]\d{4,8}$/.test(sMobile.value))){ 
		alert("请输入有效的电话或者手机号码!"); 
		sMobile.focus(); 
		return false; 
	}else
		return true;
}

 
//删除
function doDel(id,url) {
	if (confirm("确定要删除吗？"))
		window.location = url+ id;
}
//搜索
function doSearch(){
	document.getElementById("pageIndex").value=0;
	form.submit();
}
//1.控制键盘输入，只允许数字键录入整数
	function intOnly() {
	    if (! ( (window.event.keyCode >= 48) && (window.event.keyCode <= 57))) {
	  		window.event.keyCode = 0;
		}
	}
//控制键盘输入，只允许数字键录入小数和整数
	function numberOnly() {
	    if (! ( ( (window.event.keyCode >= 48) && (window.event.keyCode <= 57)) ||
	           (window.event.keyCode == 13) || (window.event.keyCode == 46))) {
			window.event.keyCode = 0;
		}
	}
	function trim(str){
		   var strnew=str.replace(/^\s*|\s*$/g,"");
		   return strnew;
		}
		function checkPhone(v){
		  var phone = v;
		  var re= /(^1[3|5|8][0-9]{9}$)/;
		  var tips = '';
		  if(v==''){
		  	alert("请输入手机号！");
		  	return false;
		  }
		  if(trim(phone)!=''){
		     if(!re.test(phone)){
		        tips = '手机号格式错误!';
		        alert("手机号错误！");
		        return false;
		     }else{
		        tips = '手机号格式正确!';
		        return true;
		     }
		  }else{
		  	alert("请输入手机号！");
		  	return false;
		  }
		}