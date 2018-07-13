function filter(id) {
	var keywords = ["-","%","#","@","!","^","&","*"];
	var value = jQuery("#"+id).val();
	for(var i = 0 ; i < keywords.length ; i++) {
		var keyword = keywords[i];
		if(value.toUpperCase().indexOf(keyword) != -1) {
			alert("对不起,输入了非法的字符串，为了保证系统的正确运行，请不要输入非法的字符串！");
			return false;
		}
	}
	return true;
}
