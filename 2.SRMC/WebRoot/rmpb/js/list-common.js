jQuery(function(){
	if(parent.window["url"] == window.location.href && parent.window["open"] == "true"){
		jQuery("#toggleQueryButton").attr("class","gl_cx_bnt01");
		jQuery("#toggleQueryButton").val("收起查询");
		jQuery("#queryDiv").show();
		
	    jQuery("#setDivs").val("收起查询");
	    jQuery("#setDivs").attr("class","gl_cx_bnt01");
	    jQuery("#serachDisplay").show();
	}
	parent.window["url"] = window.location.href;
});

function toggleQueryDiv(){
	if(jQuery("#toggleQueryButton").attr("class") == "gl_cx_bnt01"){
		jQuery("#toggleQueryButton").attr("class","gl_cx_bnt01b");
		jQuery("#toggleQueryButton").val("展开查询");
		jQuery("#queryDiv").hide();
		parent.window["open"] = "false";
	}else{
		jQuery("#toggleQueryButton").attr("class","gl_cx_bnt01");
		jQuery("#toggleQueryButton").val("收起查询");
		jQuery("#queryDiv").show();
		//写入windows对象
		parent.window["open"] = "true";
		
	}
}

//收起查询
function setDiv(){
  var setValue=jQuery("#setDivs").val();
  if('收起查询'==setValue){
	  
	  jQuery("#setDivs").val("展开查询");
	  jQuery("#setDivs").attr("class","gl_cx_bnt01b");
	  jQuery("#serachDisplay").hide();
	  parent.window["open"] = "false";
  }
  if('展开查询'==setValue){
	  
	  jQuery("#setDivs").val("收起查询");
	  jQuery("#setDivs").attr("class","gl_cx_bnt01");
	  jQuery("#serachDisplay").show();
	  parent.window["open"] = "true";
  }
}