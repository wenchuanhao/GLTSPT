/*
 * 南基项目统一管理平台专用 拖拽uplaod。。
 * lijunhao 
 * 20151119
 * 使用方法可参照uploadify3.2。。
 * 支持主流的ie10+浏览器，推荐用谷歌内核的。
 *	
  	 jQuery("div").rmpbUploadify({
		uploader:'uploading.do',
		fileObjName:'file1',
		formData:{username:'lee',sex:'男'}, //使用json格式
		fileSizeLimit:'10240000000', //单位kb ， 102400kb==100mb
	    fileTypeExts: '*.mp4;*.zip;*.rar;*.7z;*.doc;*.xls;*.ppt;*.docx;*.xlsx;*.pptx;*.txt;*.pdf;*.chm;*.gif;*.bmp;*.jpg;*.png;*.tif',
	    showProgress:true,
	    onUploadProgress:function(file, bytesUploaded, bytesTotal ){//可以监测进度条
	    	。。。
		},
		onSelect_Drop : function(files) { //代替了onSelect 刚选择事件触发
			。。。
		},
		onUploadComplete: function(file){//单个上传成功
			。。。
		},
		onQueueComplete: function(files){ //全部队列上传成功
			。。。
		},
		onCancel:function(file){ //取消上传
			。。。
		}

		
	});
	
 * 
 * 
 */


(function(a){var t=function(h,c,e,g){var d=e.shift(),f=arguments.callee;if(!d)if(0<e.length)f(h,c,e,g);else{r(h,c);c.onQueueComplete(h);return}var n=new XMLHttpRequest,p=q(h,d.id);p&&(p.xhr=n);n.onreadystatechange=function(){if(4==n.readyState&&200==n.status)c.onUploadComplete(d)};var p=new FormData,l;for(l in c.formData)p.append(l,c.formData[l]);p.append("file1",d);n.open("post",c.uploader,!0);n.upload.onprogress=function(b){b.lengthComputable&&(parseInt(b.loaded/b.total*100),c.onUploadProgress(d,b.loaded,b.total),c.showProgress&&(b=parseInt(b.loaded/b.total*100),a("#"+d.id).children("td.percentage").html(b+"%")))};n.onabort=function(b){};n.onerror=function(b){};n.onload=function(b){};n.onloadend=function(b){c.showProgress&&a("#"+d.id).children("td.oper").html("\u221a");0<e.length?f(h,c,e,g):(r(h,g),c.onQueueComplete(h))};n.send(p)},u=function(a){a/=1024;return 1024>a?a.toFixed(2)+"KB":1024<=a&&1024E3>a?(a/1024).toFixed(2)+"MB":(a/1024/1024).toFixed(2)+"GB"},r=function(h,c){a(c).children(".dd_upload_progress").slideUp("slow",function(){a(this).remove()})},q=function(a,c){for(var e=0;e<a.length;e++)if(a[e].id==c)return a[e];return null};a.fn.rmpbUploadify=function(h){var c=a.extend({},a.fn.rmpbUploadify.defaults,h);this.each(function(h,g){if(!g.addEventListener)return!1;a(g).addClass("rmpbUploadify");a(g).data("opts",c);if(!(0<a(g).find(".dd_upload_mask").length)){var d=15;0==c.showProgress&&(d=3);var f=a(g).width()-d,d=a(g).height()-d,n=(a(g).width()-f)/2,p=(a(g).height()-d)/2,f="<div class='dd_upload_mask'  style='display:none;width:"+f+"px; height:"+d+"px; margin-top:"+p+"px;margin-left:"+n+"px; background:#ffee00; opacity:0.2; position:absolute; z-index:99; text-align: center; '></div>";a(g).prepend(f);g.addEventListener("dragenter",function(l){l.stopPropagation();l.preventDefault();if(!(-1<window.navigator.userAgent.indexOf("MSIE 9.0"))){var b=a(l.currentTarget).data("dragoverCount")||0;b++;a(l.currentTarget).data("dragoverCount",b);0==a(l.currentTarget).children(".dd_upload_mask:visible").length&&a(l.currentTarget).children(".dd_upload_mask").show();return!1}});g.addEventListener("dragover",function(a){a.preventDefault();a.stopPropagation();return!1});g.addEventListener("dragleave",function(c){c.stopPropagation();c.preventDefault();var b=a(c.currentTarget).data("dragoverCount")||0;b--;0>b&&(b=0);a(c.currentTarget).data("dragoverCount",b);0>=b&&0<a(c.currentTarget).children(".dd_upload_mask:visible").length&&a(c.currentTarget).children(".dd_upload_mask").hide();return!1});g.addEventListener("drop",function(l){l.preventDefault();l.stopPropagation();if(-1<window.navigator.userAgent.indexOf("MSIE 9.0"))alert("\u4e5f\u8bb8\u60a8\u7684\u6d4f\u89c8\u5668\u4e0d\u652f\u6301\u6b64\u64cd\u4f5c\uff0c\u5efa\u8bae\u4f7f\u7528ie10+\u6216\u8005\u8c37\u6b4c\u6d4f\u89c8\u5668");else{var b=a(l.currentTarget).data("dragoverCount")||0;b--;0>b&&(b=0);a(l.currentTarget).data("dragoverCount",b);0>=b&&0<a(l.currentTarget).children(".dd_upload_mask:visible").length&&a(l.currentTarget).children(".dd_upload_mask").hide();if(l.dataTransfer&&(b=l.dataTransfer.files)&&0!=b.length){var k;k=c.fileTypeExts.split(";");var d=[],h=0;b:for(;h<b.length;h++){for(var m=b[h].name,e=0;e<k.length;e++)if(k[e]=k[e].replace("*",""),(new RegExp(".+\\"+k[e]+"$","i")).test(m))continue b;d.push(m)}k=0==d.length?null:d;if(null!=k&&0<k.length)alert(k+" \u683c\u5f0f\u6709\u8bef\n\u8bf7\u9009\u62e9"+c.fileTypeExts+"\u683c\u5f0f\u7684\u6587\u4ef6");else{k=[];for(d=0;d<b.length;d++)h=b[d].name,m=b[d].size,(0==m||m>1024*c.fileSizeLimit)&&k.push(h);k=0==k.length?null:k;if(null!=k&&0<k.length)alert(k+" \u6587\u4ef6\u5bb9\u91cf\u6709\u8bef\n\u6587\u4ef6\u5bb9\u91cf\u9700\u5927\u4e8e0\u4e14\u5c0f\u4e8e"+c.fileSizeLimit/1024+"mb");else{k=a(g).data("queueAll");d=a(g).data("queueTemp");h=!1;d&&0!=d.length||(k=[],d=[],a(g).data("queueAll",k),a(g).data("queueTemp",d),h=!0);for(m=0;m<b.length;m++)b[m].id="fileId_"+(Math.floor(999E5*Math.random())+1E5),k.push(b[m]),d.push(b[m]);c.onSelect_Drop(b);if(c.showProgress){var m=l.currentTarget,f="",e="<tr id='$fileId' ><td width='70%' >$fileName\uff08$fileSize\uff09</td>",e=e+"<td class='percentage' align='center' width='45'>$percentage</td>",e=e+"<td class='oper' align='center' width='45'><a href='javascript:;' onclick=\"jQuery(this).rmpbUploadifyCancel('$fileId')\" style='color: #1e5494;'>\u53d6\u6d88</a></td>",e=e+"</tr>";if(0==a(m).children(".dd_upload_progress").length){var n=a(m).width(),p=a(m).height(),f=f+("<div class='dd_upload_progress'  style='width:"+n+"px; max-height:"+p+"px; overflow-y:scroll; opacity:0.9;  padding:6px 0 6px 0; background: #f0f0f0;  position:absolute; z-index:999; '>")+"\t <table class='dd_upload_progress_table'  style='background: #eff5fb; border-color:#eff5fb; font-size: 12px;' width='100%' border='1' cellspacing='0' cellpadding='3'>",f=f+"\t </table>",f=f+"</div>";a(m).prepend(f)}for(f=0;f<b.length;f++)n=e.replace(/\$fileId/g,b[f].id).replace("$fileName",b[f].name).replace("$fileSize",u(b[f].size)).replace("$percentage","0%"),a(m).find(".dd_upload_progress > table.dd_upload_progress_table").append(n)}if(h)for(m=0;m<c.thread;m++)t(k,c,d,l.currentTarget);return!1}}}}})}})};a.fn.rmpbUploadifyCancel=function(h){this.each(function(c,e){var g=a(e).closest(".rmpbUploadify"),d=g.data("queueAll"),f=g.data("queueTemp"),g=g.data("opts"),d=q(d,h);q(f,h);d&&(f=d.xhr)&&f.abort();f=a(e).closest(".dd_upload_progress");a(e).closest("tr[id]").remove();0==f.find("tr[id]").length&&f.remove();g.onCancel(d);return!1})};a.fn.rmpbUploadify.defaults={formData:{},fileObjName:"file1",fileSizeLimit:"102400",fileTypeExts:"*.zip;*.rar;*.7z;*.doc;*.xls;*.ppt;*.docx;*.xlsx;*.pptx;*.txt;*.pdf;*.chm;*.gif;*.bmp;*.jpg;*.png;*.tif",uploader:"",showProgress:!0,thread:2,onUploadProgress:function(a,c,e){},onSelect_Drop:function(a){},onUploadComplete:function(a){},onQueueComplete:function(a){},onCancel:function(a){}}})(jQuery);


