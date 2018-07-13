// wmj-20160517 下拉多选框
$(function(){
	var ul_obj=$(".checxListWrap");
	ul_obj.each(function(i){
		var ip_obj=$(this).children("input");
		// 设置默认选项,默认选中第一个selected
		ip_obj.val($(this).find(".selected >span").html());
		// 添加点击显示隐藏事件
		var c_ul=$(this).children("ul");
		$(this).on("click", function(e){
		    c_ul.show();
		    $(document).one("click", function(){
		        c_ul.hide();
		    });
		    e.stopPropagation();
		});
		c_ul.on("click", function(e){
		    e.stopPropagation();
		});
		// 文本框赋值
		var i_obj=$(this).find("li > i");
		i_obj.each(function(){
			$(this).parent().click(function(){
				$(this).toggleClass("selected");
				if($(this).hasClass("selected")){
					var txt=ip_obj.val();
					if(txt!=""){
						ip_obj.val(txt+","+$(this).children("span").html());
					}else{
						ip_obj.val($(this).children("span").html());
					}
				}else{
					// 删除文本框选中的值
					var txt_arr=ip_obj.val().split(",");
					var select_txt=$(this).children("span").html();
					$(txt_arr).each(function(i){
						if (select_txt==txt_arr[i]) {
							if(i==0){
								var txt01;
								if(txt_arr.length==1){
									// 删除只有一个选项
									txt01=ip_obj.val().replace(txt_arr[i],"");
								}else{
									// 删除多个选项中第一个选项
									txt01=ip_obj.val().replace(txt_arr[i]+",","");
								}
								ip_obj.val(txt01);
							}else{
								var txt01=ip_obj.val().replace(","+txt_arr[i],"");
								ip_obj.val(txt01);
							}
						}
					});
				}
			});
		});
	});
	// 下拉树样式
	var tree_ul_obj=$(".checxListTreeWrap");
	tree_ul_obj.each(function(i){
		var ip_obj=$(this).children("input");
		// 设置默认选项,默认选中第一个selected
		ip_obj.val($(this).find(".selected >span").html());
		// 添加点击显示隐藏事件
		var c_ul=$(this).find(".list_p");
		$(this).on("click", function(e){
		    c_ul.show();
		    $(document).one("click", function(){
		        c_ul.hide();
		    });
		    e.stopPropagation();
		});
		c_ul.on("click", function(e){
		    e.stopPropagation();
		});
		// 文本框赋值
		var i_obj=$(this).find("li > span");
		i_obj.each(function(){
			if($(this).parent().hasClass("selected")){
				ip_obj.val($(this).attr("tex"));
			}
			$(this).parent().click(function(){
				c_ul.find("li").not($(this)).each(function(){
					$(this).removeClass("selected");
				});
				$(this).addClass("selected");
				$("#rulesTypeId_input").val($(this).attr("val"));
				ip_obj.val($(this).children("span").attr("tex"));
				c_ul.hide();
			});
		});
	});
});