$(function(){
	var move=$('.time_line_wrap').width();
	var timeline=document.getElementById('time_line');
	var tl;
	init();
	$('.nxt').click(function(){
		tl=timeline.offsetLeft;
		$('.pre').removeClass('disable');
		if(($(timeline).width()+tl-move)<move){
			$(timeline).animate({left: -$(timeline).width()+move+'px'}, "slow");
			$(this).addClass('disable');
		}else{
			$(timeline).animate({left: tl-320+'px'}, "slow");
			$(this).removeClass('disable');
		}
	});
	$('.pre').click(function(){
		$('.nxt').removeClass('disable');
		tl=timeline.offsetLeft;
		if(parseInt(-tl/320)>=1){
			$(timeline).animate({left: tl+320+'px'}, "slow");
			$(this).removeClass('disable');
		}else{
			$(timeline).animate({left: 0}, "slow");
			$(this).addClass('disable');
		}
	});
	function init(){
		var d = new Date();
		var Year = d.getFullYear();
		var Mon = d.getMonth() + 1;
		var y_arry= new Array(5);
		for (var i = 0; i < y_arry.length; i++) {
			if(i==0){
				var mli='';
				for (var j = 1; j <= Mon; j++) {
					// 默认选择当前两个月
					if (j == Mon || j==Mon-1) {
						mli=mli+'<li class="select"><i class="line"></i><i class="mdata">'+j+'</i></li>';
					}else{
						mli=mli+'<li><i class="line"></i><i class="mdata">'+j+'</i></li>';
					}
				}
				y_arry[i]='<div class="year">'+
				'<ul class="month">'+mli+
					'</ul>'+
					'<span class="ydata">'+(Year+i)+'</span>'+
				'</div>';
			}else{
				y_arry[i]='<div class="year">'+
				'<ul class="month">'+
						'<li><i class="line"></i><i class="mdata">1</i></li>'+
						'<li><i class="line"></i><i class="mdata">2</i></li>'+
						'<li><i class="line"></i><i class="mdata">3</i></li>'+
						'<li><i class="line"></i><i class="mdata">4</i></li>'+
						'<li><i class="line"></i><i class="mdata">5</i></li>'+
						'<li><i class="line"></i><i class="mdata">6</i></li>'+
						'<li><i class="line"></i><i class="mdata">7</i></li>'+
						'<li><i class="line"></i><i class="mdata">8</i></li>'+
						'<li><i class="line"></i><i class="mdata">9</i></li>'+
						'<li><i class="line"></i><i class="mdata">10</i></li>'+
						'<li><i class="line"></i><i class="mdata">11</i></li>'+
						'<li><i class="line"></i><i class="mdata">12</i></li>'+
					'</ul>'+
					'<span class="ydata">'+(Year-i)+'</span>'+
				'</div>';
			}
		}
		var y_arry02=y_arry.reverse();
		$(timeline).html(y_arry02.join(''));
		$(timeline).css('width',$('.month >li').length*$('.month >li').width()+'px');
		$(timeline).css('left',-$(timeline).width()+move+'px');//-1520
		$('.nxt').addClass('disable');
		// 点击选择事件
		var index=1;
		$('.month >li').each(function(){
			if($(this).hasClass('select')){
				$(this).attr('index',index);
				index++;
			}
			$('#startTime').html(getTime().split(',')[0]);
			$('#endTime').html(getTime().split(',')[1]);
		});
		$('.month >li').click(function(){
			if(index>=3){
				$('.month >li').not($(this)).each(function(){
					if(index-parseInt($(this).attr('index')) == 2){
						$(this).removeClass('select');
					}
				});
			}
			$(this).attr('index',index);
			$(this).addClass('select');
			index++;
			$('#startTime').html(getTime().split(',')[0]);
			$('#endTime').html(getTime().split(',')[1]);
			$("#chart_content").attr("src",baseURL + "/BZJF/indexChild?startDate="+getTime().split(',')[0]+"&endDate="+getTime().split(',')[1]+"&"+Math.random());
		});
		// 时间显示
		function getTime(){
			var time='',month='',year='';
			var monthLen = 0;
			$('.month >li').each(function(){
				if($(this).hasClass('select')){
					month=$(this).find('.mdata').html();
					year=$(this).parent().parent().find('.ydata').html();
					time=time+year+'-'+month+',';
					monthLen++;
				}
			});
			//选择了同一月份
			if(monthLen == 1){
				time += time;
			}
			return time;
		};
	}
});