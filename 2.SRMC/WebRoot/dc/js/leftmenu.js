$(function(){
	var leftMenu=$('.leftmenu');
	var a_arry=$('.primary').children('a');
	// 设置全部导航默认显示
	a_arry.each(function(){
		var aobj=$(this);
		if (!aobj.hasClass('noclick')) {
			aobj.click(function(){
				if (!aobj.hasClass('on')){
					aobj.next('').slideDown();
					aobj.addClass('on');
				}else{
					aobj.next('.leftMenu').slideUp();
					aobj.removeClass('on');
				}
			});
		}else{
			aobj.next('').show();
		}
	});
	$('.second').find('li').each(function(){
		$(this).hover(function(e){
			if($(this).children('ul').html()){
				var c_ul_obj=$(this).children('ul');
				c_ul_obj.show();
				var ulLeft=c_ul_obj.offset().left+c_ul_obj.width();
				// 设置超出屏幕左右显示
				if (ulLeft>$(window).width()) {
					c_ul_obj.addClass('sLeft');
				}
			}
            // 设置鼠标经过图标变色
			$(this).addClass('on');
            if($(this).children('img').attr("src")){
                var nsrc=$(this).children('img').attr('src').replace('.png', '');
                $(this).children('img').attr('src',nsrc+'_hover.png');
            }
			e.stopPropagation();
		},
        function(){
        	$(this).children('ul').hide();
            if($(this).children('img').attr('src')){
            	var osrc=$(this).children('img').attr('src').replace('_hover', '');
            	$(this).children('img').attr('src',osrc);
            }
            $(this).removeClass('on');
        });
	});
	// 3.设置箭头
	$('.leftmenu').find('li').each(function(){
		if ($(this).children('ul').html()) {
			$(this).addClass('hasNext');
		}
	});
});


