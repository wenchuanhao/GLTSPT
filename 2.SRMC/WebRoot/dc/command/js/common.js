/** 
 *  页面弹窗
 */
function iframeDialog(target,url) {
	jQuery('#'+target).fancybox({
		'href' : url,
//		'width' : '50%',
//		'height' : '50%',
		'autoScale' : true,
		'autoDimensions': true,
		'transitionIn' : 'none',
		'transitionOut' : 'none',
		'type' : 'iframe',
		'scrolling' : 'yes',
		'centerOnScroll' : true,
		'onStart' : function(current, previous) {
		}
	});
}


/**关闭fancybox**/
function closeIframeDialog(){
	jQuery.fancybox.close();
}

/**
 * 打印页面
 * @param id
 * 参数str可以使用id或者html等,如:printWork("divid");printWork("<div>TEST</div>")
 */
function printPage(str) {
    var orderhtml = "";
    if (document.getElementById(str)) {
    	orderhtml = document.getElementById(str).outerHTML; 
    } else {
    	orderhtml = str;
    }
    /* 创建iframe */
    var headobj = document.getElementsByTagName("head").item(0); //提取head  
    printFrame = document.getElementById("lldxi_printRegionFrame_2012_0112");
    if (printFrame) { document.body.removeChild(printFrame); }
    printFrame = document.createElement("iframe");
    printFrame.setAttribute("src", "about:blank");
    printFrame.setAttribute("id", "lldxi_printRegionFrame_2012_0112");
    printFrame.setAttribute("marginheight", "0");
    printFrame.setAttribute("marginwidth", "0");
    printFrame.style.display = "none";
    document.body.appendChild(printFrame);
    if (window.ActiveXObject)//ie
    {
        var htmlobj = printFrame.contentWindow.document.createElement("html"); 
        var bodyobj = printFrame.contentWindow.document.createElement("body");
        bodyobj.innerHTML = orderhtml; 
        htmlobj.appendChild(headobj.cloneNode(true)); 
        htmlobj.appendChild(bodyobj);
        printFrame.contentWindow.document.appendChild(htmlobj); 
        printFrame.contentWindow.document.execCommand("Print", true);
    }
    else {
        var htmlstr = "<html>" + headobj.outerHTML + "<body>" + orderhtml + "<script type=\"text/javascript\">window.print();<\/script><\/body>" + "<\/html>";
        printFrame.contentWindow.document.write(htmlstr);
    }
}

//发起指令
function launchCommand(){
	iframeDialog("launchCommand", basePath + "/launch");
}

//支撑单位新增
function supportAdd(){
	iframeDialog("supportAdd", basePath + "/supportAdd");
}

//支撑单位编辑
function supportEdit(){
	var temp =  jQuery('.fancybox_supportAdd');
	$.each(temp,function(k,v){
		iframeDialog($(v).attr("id"), basePath + "/supportAdd?id="+$(v).attr("value"));
	});
}


//查看指令流转详情
function viewFlows(){
	var temp = jQuery('.fancybox_flows');
	$.each(temp,function(k,v){
		iframeDialog($(v).attr("id"), basePath + "/viewFlows?id="+$(v).attr("value"));
	});
}

//归档指令
function saveFolder(){
	var temp = jQuery('.fancybox_folder');
	$.each(temp,function(k,v){
		iframeDialog($(v).attr("id"), basePath + "/saveFolder?id="+$(v).attr("value"));
	});
}