$(document).ready(function($) {
	$("#yfgl_sub_tb").on("click",".gl_m_r_dl dt",function(e){
		$("#yfgl_sub_tb dt.hover").removeClass("hover").next().hide();
		$(this).addClass("hover").next().show();
	});
	$("#yfgl_sub_tb").on("click",".gl_m_r_dl dd a",function(e){
		$("#yfgl_sub_tb dd a.hover").removeClass("hover");
		$(this).addClass("hover");
	});
});

