function lumpcur1(id){
	var a=document.getElementById(id); 
	if(a.style.zIndex>1){
		$(".lumpWrap-bg").css("display","none");
		$(a).css("z-index","1");
		$(".lumpPop-1").animate({height:"0",opacity:"0"});
		$(".lumpInform").animate({top:"166px"});
	}
	else{
		$(".lumpWrap-bg").css("display","block");
		$(a).css("z-index","3");
		$(".lumpPop-1").animate({height:"333px",opacity:"1"});
		$(".lumpInform").animate({top:"499px"});
	}
}
function lumpcur2(id){
	var a=document.getElementById(id); 
	if(a.style.zIndex>1){
		$(".lumpWrap-bg").css("display","none");
		$(a).css("z-index","1");
		$(".lumpList-2").animate({top:"0"});
		$(".lumpPop-2").animate({height:"0",opacity:"0",top:"0"});
		$(".Qtoolbars").animate({top:"0"});
		$(".lumpInform").animate({top:"166px"});
	}
	else{
		$(".lumpWrap-bg").css("display","block");
		$(a).css("z-index","3");
		$(".lumpList-2").animate({top:"-166px"});
		$(".lumpPop-2").animate({height:"333px",opacity:"1",top:"-166px"});
		$(".Qtoolbars").animate({top:"-166px"});
		$(".lumpInform").animate({top:"333px"});
	}
}