/**
 * 判断字符串是否以 tartget 结尾
 * @param str 字符串
 * @param target 结尾字符串
 * @returns {Boolean}
 */
function confirmEnding(str, target) {
	  str = str.toLowerCase();
	  var start = str.length-target.length;
	  var arr = str.substr(start,target.length);
	  if(arr == target){
	    return true;
	  }
	 return false;
}

/**
 * uploadifive插件取消上传
 * @param id
 * @param file
 */
function cancel(id,file){
	$(id).uploadifive('cancel',file);
}

/**
 * 判断文件大小是否符合
 * @param filesize 文件大小（B）
 * @param max 规定大小（M）
 * @returns {Boolean}
 */
function filesize(filesize, max){
	if(filesize > max * 1024 * 1024){
		return false;
	}
	return true;
}
/**
 * uploadfive插件上传操作
 * @param id
 */
function upload(id){
	$(id).uploadifive('upload');
}

/**
 * 限制type为1的上传文件类型只能为Word、PDF
 * @param name
 * @returns true：符合格式,false：不符合
 */
function checkFileType_1(name){
	if(confirmEnding(name,".doc") || confirmEnding(name,".docx") || confirmEnding(name,".pdf")){
		return true;
	}
	return false;
}

/**
 * 限制的类型：*.doc;*.docx;*.pdf;*.gif; *.jpg;*.jpeg;*.png
 * 限制type为3的上传文件类型
 * @param name
 * @returns true：符合格式,false：不符合
 */
function checkFileType_3(name){
	if(confirmEnding(name,".doc") || confirmEnding(name,".docx") || confirmEnding(name,".pdf") ||
		confirmEnding(name,".gif") || confirmEnding(name,".jpg") || confirmEnding(name,".jpeg") ||
		confirmEnding(name,".png") ){
		return true;
	}
	return false;
}


/**
 * 符合以下后缀的文件均可上传
 * @param name
 * @returns 0:不允许上传，1：允许上传
 */
function checkFileTypeAll(name) {
	var result = "0";
	if(confirmEnding(name,".doc")) {
		result = "1";
	} else if(confirmEnding(name,".docx")) {
		result = "1";
	} else if(confirmEnding(name,".xls")) {
		result = "1";
	} else if(confirmEnding(name,".xlsx")) {
		result = "1";
	} else if(confirmEnding(name,".ppt")) {
		result = "1";
	} else if(confirmEnding(name,".pptx")) {
		result = "1";
	} else if(confirmEnding(name,".rar")) {
		result = "1";
	} else if(confirmEnding(name,".zip")) {
		result = "1";
	} else if(confirmEnding(name,".7z")) {
		result = "1";
	} else if(confirmEnding(name,".jpg")) {
		result = "1";
	} else if(confirmEnding(name,".jpeg")) {
		result = "1";
	} else if(confirmEnding(name,".bmp")) {
		result = "1";
	} else if(confirmEnding(name,".png")) {
		result = "1";
	} else if(confirmEnding(name,".gif")) {
		result = "1";
	} else if(confirmEnding(name,".txt")) {
		result = "1";
	} else if(confirmEnding(name,".html")) {
		result = "1";
	} else if(confirmEnding(name,".xhtml")) {
		result = "1";
	} else if(confirmEnding(name,".xml")) {
		result = "1";
	} else if(confirmEnding(name,".pdf")) {
		result = "1";
	}

	return result;
}