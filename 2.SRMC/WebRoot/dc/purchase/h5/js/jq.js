	$(".search").on("click",function(){
    $(".header_box").toggle();
    $(".body_bg").toggle();
  });
  
  $('.body_bg,.menu_down').on('click',function(){
         $('.body_bg').hide();
    $('.header_box').hide();
    });
  


/**/
	$('.switch').click(function(){
  	$(this).toggleClass('switch_02')
    if($(this).parent().next().is(':hidden')){
    	$(this).html('图表');
      $(this).parent().next().show();
       $(this).parent().next().next().hide();
    }
    else{
    	$(this).html('数据');
      $(this).parent().next().hide();
       $(this).parent().next().next().show();
    } 
  })
/*下拉选择*/
$(".drop-down-box").bind('click',function() {
			$(this).find("ul").toggle();
			$(this).siblings('.drop-down-box').find("ul").hide();
			return false;//阻止事件冒泡
	})
	$(".drop-down-btn").bind('click',function() {
			$(this).parent('.drop-down-box').find("ul").toggle();
			$(this).parent().siblings('.drop-down-box').find("ul").hide();
			return false;//阻止事件冒泡			
	})
	$(".drop-down-box li a").bind('click',function() {
			// $(".drop-down-txt").val($(this).text());
			$(this).parents(".drop-down-box").find(".drop-down-txt").text($(this).text());
			// $(".drop-down-box ul").hide();
			$(this).parents(".drop-down-box ul").hide();
			return false;//阻止事件冒泡
		});
		$(".drop-down-box ul").mouseleave(function() {
			$(this).hide();
		});
		$("html").bind('click',function(){
			$(".drop-down-box ul").hide();
		});
		$(".drop-down-btn").bind('click',function(){
			return false;//阻止事件冒泡
		})
