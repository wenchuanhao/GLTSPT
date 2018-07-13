//截取字符，length为汉字的长度，非汉字则相占 两个长度
//例如 "helloo你好" length为6/2+2 = 5 
function ellipsisStr(str , length){
	length = length || 10;
	var notChineseLen =null;
	if(str.length>length){
		var chineseArr = str.substr(0, length).match(/[\u4e00-\u9fa5]/g)||[];
		notChineseLen = str.substr(0, length).length - chineseArr.length;
	}else{
		var chineseArr = str.match(/[\u4e00-\u9fa5]/g)||[];
		notChineseLen = str.length - chineseArr.length;
	}
	if(notChineseLen>=length){
		length = length+length*0.3;
	}else{
		length = length+notChineseLen*0.3;
	}
 	var newStr = '';
 	//var array = new Array(); 
 	//array = str.split("");
 	if(str.length > length){
 		newStr = str.substr(0, length) + "..."; 
 	}else{
 		newStr = str;
 	}
	return newStr;
}

//简易调用
function subStr(str , length){
	document.write( ellipsisStr(str , length) );
}