$(document).ready(function($) {
	$("#yfgl_sub_tb").on("click",".gl_m_r_dl_f1",function(e){
		$("#yfgl_sub_tb .gl_m_r_dl_f1.hover").removeClass("hover").next().hide();
		$(this).addClass("hover").next().show();
	});
	$("#yfgl_sub_tb").on("click",".gl_m_r_dl_z1 a",function(e){
		$("#yfgl_sub_tb .gl_m_r_dl_z1 a.hover").removeClass("hover");
		$(this).addClass("hover");
	});
});

