$(document).ready(function(){
	$(".nav li").hover(function(){
			$(this).addClass("hover");
			$(this).children("ul li").attr('class','');
		},function(){
			$(this).removeClass("hover");  
			$(this).children("ul li").attr('class','');
		}
	); 
/*	$(".menu li.no_sub").hover(function(){
			$(this).addClass("hover1");
		},function(){
			$(this).removeClass("hover1");  
		}
	); */
//µ¯´°
//$('a[rel*=leanModal]').leanModal({ top: 100, closeButton: ".modal_close" });
})

$().ready(function(){
  $('input.myClass').Checkable({
  });
});

