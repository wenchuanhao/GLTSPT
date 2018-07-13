function security(id) {
	var keywords = ["SCRIPT","SRC"];
	var value = document.getElementById(id).value;
	for(var i = 0 ; i < keywords.length ; i++) {
		var keyword = keywords[i];
		if(value.toUpperCase().indexOf(keyword) != -1) {
			alert("对不起,输入了非法的字符串，为了保证系统的正确运行，请不要输入非法的字符串！");
			return false;
		}
	}
	return true;
}

function validateSecurity() {
	var result = true;
	var keywords = ["SCRIPT","SRC"];
	jQuery("input,textarea").each(function() {
		var value = jQuery(this).val();
		for(var i = 0 ; i < keywords.length ; i++) {
			var keyword = keywords[i];
			if(value.toUpperCase().indexOf(keyword) != -1) {
				jQuery(this).focus();
				alert("对不起,输入了非法的字符串，为了保证系统的正确运行，请不要输入非法的字符串！");
				result = false;
				return false;
			}
		}
	});
	return result;
}

