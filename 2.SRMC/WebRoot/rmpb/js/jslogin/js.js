jQuery(document).ready(function(){
	jQuery(".nav li").hover(function(){
		jQuery(this).addClass("hover");
		jQuery(this).children("ul li").attr('class','');
		},function(){
			jQuery(this).removeClass("hover");  
			jQuery(this).children("ul li").attr('class','');
		}
	); 
/*	$(".menu li.no_sub").hover(function(){
			$(this).addClass("hover1");
		},function(){
			$(this).removeClass("hover1");  
		}
	); */
//����
//$('a[rel*=leanModal]').leanModal({ top: 100, closeButton: ".modal_close" });
});
/*
jQuery().ready(function(){
	jQuery('input.myClass').Checkable({
  });
});*/

