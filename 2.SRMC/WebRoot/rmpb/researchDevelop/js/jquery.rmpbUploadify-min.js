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

(function($){
	
	/**
	 * ajax上传
	 * queueAll 全部队列
	 * opts json格式配置
	 * queueTemp 临时数组，复制自 参数files
	 */
	var doAjaxUpload = function(queueAll ,opts , queueTemp , dropTarget ){
		//alert("05");
		var file = queueTemp.shift();
		var f_me = arguments.callee;
		//console.log("doAjaxUpload , queueTemp=" + queueTemp.length);
		if( ! file ){//如果当前file项为null，但也许下一个元素是有效的file，则继续执行。
			if(queueTemp.length>0){
				f_me(queueAll ,opts ,queueTemp , dropTarget); //如果还有，继续调用。
			}else{
				finishAllDefaultProgress(queueAll , opts);	
				opts.onQueueComplete(queueAll); //全部完成事件				
				return; //不执行其后的操作。
			}
		}
		
		//alert(file.name);
		//alert("052");
		var xhr = new XMLHttpRequest();
		//xhr.timeout=500; //单位ms，超时间之后会自动cancel
		
		//记录xhr到file结构里
		var f_tmp = getFileById(queueAll , file.id);
		if(f_tmp) f_tmp.xhr=xhr;
		//
		
		xhr.onreadystatechange = function(){
			if(xhr.readyState==4){
				if(xhr.status==200){
					var text = xhr.responseText;
					//alert(text+"成功");
					
					//触发用户事件
					opts.onUploadComplete(file);
				}
			}
		};//onreadystatechange
		
		
		//构建form参数
		var fd = new FormData();
		for(var n in opts.formData){
			fd.append(n , opts.formData[n]); //json参数
		}
		fd.append("file1" , file); //相当于  name=file1
		
		
		//xhr.setRequestHeader("Content-Type" ,"application/x-www-form-urlencoded");
		xhr.open("post", opts.uploader ,true);
		//xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); //open之后
		//xhr.send("user=aa123456");
		
		xhr.upload.onprogress=function(e){ //监听进度
			if(e.lengthComputable){
				var percentComplete = parseInt((e.loaded / e.total) * 100);
				//console.log("Upload: " +percentComplete + "% complete");
				
				opts.onUploadProgress(file, e.loaded, e.total ); //用户监听
				
				if(opts.showProgress){//默认ui监听
					updateDefaultProgress(file, e.loaded, e.total );
				}
			}
		}
		
		xhr.onabort = function(e){
		}
		xhr.onerror = function(e){
		}
		xhr.onload = function(e){
		}
		
		xhr.onloadend = function(e){ //无论请求成功与否，都会触发的事件
			//完成当前file的ui交互，例如不能操作。。
			if(opts.showProgress) {
				endDefaultProgress(file);
			}
			if(queueTemp.length>0){
				f_me(queueAll ,opts ,queueTemp , dropTarget); //如果还有，继续递归调用。
			}else{
				finishAllDefaultProgress(queueAll , dropTarget);	
				opts.onQueueComplete(queueAll); //全部完成事件
			}
		}
		
		
		xhr.send(fd); //必须post方式， 并且不加Content-Type
		
	}; //end doAjaxUpload
	
	
	/**
	 * 初始化显示默认的ui进度交互
	 * files 数组
	 * opts json格式配置
	 */
	var initDefaultProgress = function(files ,opts ,dropTarget ){
		var dd_upload_progress = "";
		var template = "";
		template += "<tr id='$fileId' >";
		template += 	"<td width='70%' >$fileName（$fileSize）</td>";
		template += 	"<td class='percentage' align='center' width='45'>$percentage</td>";
		template += 	"<td class='oper' align='center' width='45'><a href='javascript:;' onclick=\"jQuery(this).rmpbUploadifyCancel('$fileId')\" style='color: #1e5494;'>取消</a></td>";
		template += "</tr>";
		//初始化
		if($(dropTarget).children(".dd_upload_progress").length==0){ //如果不存在元素
			var w0 = $(dropTarget).width();
			var h0 = $(dropTarget).height();
			dd_upload_progress += "<div class='dd_upload_progress'  style='width:" +w0+ "px; max-height:" +h0+ "px; overflow-y:scroll; opacity:0.9;  padding:6px 0 6px 0; background: #f0f0f0;  position:absolute; z-index:999; '>";
			dd_upload_progress += "	 <table class='dd_upload_progress_table'  style='background: #eff5fb; border-color:#eff5fb; font-size: 12px;' width='100%' border='1' cellspacing='0' cellpadding='3'>";
			dd_upload_progress += "	 </table>";
			dd_upload_progress += "</div>";
			$(dropTarget).prepend(dd_upload_progress);
		}
		
		//增加tr项
		for(var i = 0; i<files.length; i++){
			var html = template.replace(/\$fileId/g, files[i].id)
			.replace("$fileName", files[i].name)
			.replace("$fileSize", convertKbMb(files[i].size) )
			.replace("$percentage", "0%");
			var $table = $(dropTarget).find(".dd_upload_progress > table.dd_upload_progress_table");
			$table.append(html);
		}
		
	}//end initDefaultProgress
	
	var convertKbMb = function(byte){
		var kb = byte/1024;
		if(kb<1024){
			return kb.toFixed(2)+"KB";
		}else if(kb>=1024 && kb<1024000){
			return (kb/1024).toFixed(2)+"MB";
		}else{
			return (kb/1024/1024).toFixed(2)+"GB";
		}
	}//end convertSize
	

	/**
	 * 更新 默认的ui进度交互
	 * file 
	 * loaded
	 * total
	 */
	var updateDefaultProgress = function(file, loaded, total ){
		var percentComplete = parseInt((loaded / total) * 100);
		$("#"+file.id).children("td.percentage").html(percentComplete+"%");
	}//end updateDefaultProgress
	
	
	/**
	 * 结束 默认的ui进度交互-单个
	 * file 
	 * loaded
	 * total
	 */
	var endDefaultProgress = function(file){
		$("#"+file.id).children("td.oper").html("√");
	}//end endDefaultProgress
	
	/**
	 * 完成 默认的ui进度交互- 所有
	 * file
	 */
	var finishAllDefaultProgress = function(files , dropTarget){
		//$("#"+file.id).children("td.percentage").html(percentComplete+"%");
		//alert("finishAllDefaultProgress()");
		//关闭所有的弹出的东西..(带有效果)
		$(dropTarget).children(".dd_upload_progress").slideUp("slow",function(){
			$(this).remove();
		});
		
	}//end finishAllDefaultProgress
	
	
	
	
	/**
	 * 是否符合文件扩展名
	 * files 数组
	 * opts json格式配置
	 * 如果有不符合的文件，返回文件名的 数组。全部符合就返回null。
	 */
	var getInValidExt = function(files , opts){
		var exts = opts.fileTypeExts;
		var arr = exts.split(";");
		var inValidFiles = [];
		lable1:for(var i = 0 ; i<files.length; i++){
			var isValidExt = false;
			var fileName = files[i].name;
			for(var j = 0 ; j<arr.length; j++){
				arr[j] = arr[j].replace("*","");
				var reg = new RegExp(".+\\" + arr[j] +"$" , "i");
				if(reg.test(fileName)){
					isValidExt = true;
					continue lable1;
				}
			}//for j
			//不通过验证，记录到变量中
			if(isValidExt==false) inValidFiles.push(fileName);
		
		}//for i
		//
		
		if(inValidFiles.length==0){
			return null;
		}else{
			return inValidFiles;
		}
	}//end getInValidExt
	
	
	/**
	 * 是否符合文件大小
	 * files 数组
	 * opts json格式配置
	 * 如果有不符合的文件，返回文件名的 数组。全部符合就返回null。
	 */
	var getInValidSize = function(files , opts){
		var inValidFiles = [];
		for(var i = 0 ; i<files.length; i++){
			var fileName = files[i].name;
			var fileSize = files[i].size;
			if(fileSize==0 || fileSize > opts.fileSizeLimit*1024){ //fileSizeLimit 单位 kb
				//不通过验证，记录到变量中
				inValidFiles.push(fileName); 
			}
			
		}//for i
		//
		
		if(inValidFiles.length==0){
			return null;
		}else{
			return inValidFiles;
		}
	}//end getInValidSize
	
	
	/**
	 * 根据id查找file
	 * 成功返回file
	 * 没有返回null。
	 */
	var getFileById = function(files , id){
		for(var i = 0; i<files.length; i++){
			if(files[i].id == id){
				return files[i];
			}
		}
		return null;
	}//end getFileById
	
	/**
	 * 插件开始入口
	 */
	$.fn.rmpbUploadify = function(options){
		var opts = $.extend({}, $.fn.rmpbUploadify.defaults, options);
		this.each(function(index, domEle){
			//这里就不考虑不支持html5的浏览器了。
			
			//console.log($(domEle));
			//alert( window.applicationCache  );
			
			if(!domEle.addEventListener) return false; //旧浏览器，退出循环
			//alert(domEle.id);
			$(domEle).addClass("rmpbUploadify"); //重复加 ，也只会保留一个。。
			$(domEle).data("opts",opts);//保存配置信息
			
			//创建遮罩
			if($(domEle).find(".dd_upload_mask").length>0){
				return;
			}
			var padding = 15;
			if(opts.showProgress==false) padding=3;
			var w0_ = $(domEle).width() -padding;//边缘padding
			var h0_ = $(domEle).height() -padding;
			var marginLeft_ = ($(domEle).width()-w0_)/2;
			var marginTop_ = ($(domEle).height()-h0_)/2;
			var dd_upload_mask_ = "<div class='dd_upload_mask'  style='display:none;width:" +w0_+ "px; height:" +h0_+ "px; margin-top:" +marginTop_+ "px;margin-left:" +marginLeft_+ "px; background:#ffee00; opacity:0.2; position:absolute; z-index:99; text-align: center; '></div>";
			$(domEle).prepend(dd_upload_mask_);
			
			
			//******dragenter********//
			//************************//
			
			domEle.addEventListener("dragenter", function(e){
				//console.log("dragenter--" + e.target.id );
				//if( !e.dataTransfer ) return;//不支持的浏览器，退出循环。
				//var files = e.dataTransfer.files;
				//if(!files) return;//如果没有文件。
				e.stopPropagation();
				e.preventDefault();
				
				//不允许的浏览器
				if( window.navigator.userAgent.indexOf("MSIE 9.0")>-1 ){
					return;
				}

				///////////////////////////
				//开启遮罩
				//console.log(dragoverCount);
				var dragoverCount = $(e.currentTarget).data("dragoverCount")||0;
				dragoverCount++;
				$(e.currentTarget).data("dragoverCount" ,dragoverCount);
				///
				/*if($(e.currentTarget).find("#dd_upload_mask").length==0){ //如果不存在元素
					var w0 = $(e.currentTarget).width() -15;//边缘15px
					var h0 = $(e.currentTarget).height() -15;
					var marginLeft = ($(e.currentTarget).width()-w0)/2;
					var marginTop = ($(e.currentTarget).height()-h0)/2;
					var dd_upload_mask = "<div id='dd_upload_mask'  style='width:" +w0+ "px; height:" +h0+ "px; margin-top:" +marginTop+ "px;margin-left:" +marginLeft+ "px; background:#ffee00; opacity:0.2; position:absolute; z-index:99; text-align: center; '></div>";
					$(e.currentTarget).prepend(dd_upload_mask);
				}*/
				if($(e.currentTarget).children(".dd_upload_mask:visible").length==0){ //如果元素没显示。
					$(e.currentTarget).children(".dd_upload_mask").show();
				}
				///
				///
				
				return false;
			})
			

			//******dragover********//
			//************************//
			domEle.addEventListener("dragover", function(e){
				e.preventDefault();
				e.stopPropagation();
			//	console.log("dragover" + e.target.id );
				return false;
			})
			
			//******dragleave********//
			//************************//
			domEle.addEventListener("dragleave", function(e){
				e.stopPropagation();
				e.preventDefault();
				//console.log("dragleave--" + e.target.id );
				
				///////
				var dragoverCount = $(e.currentTarget).data("dragoverCount") || 0;
				dragoverCount--;
				if(dragoverCount<0) dragoverCount=0;//最小为0
				$(e.currentTarget).data("dragoverCount" ,dragoverCount);
				
				if(dragoverCount<=0) {
					//关闭遮罩
					if($(e.currentTarget).children(".dd_upload_mask:visible").length>0){ //如果存在元素可见
						$(e.currentTarget).children(".dd_upload_mask").hide();
					}
				}
				///////
				///////
				
				//console.log(dragoverCount);
				return false;
			})

			
			//************************//
			//******DROP********//
			//************************//
			domEle.addEventListener("drop", function(e){
				//console.log("drop--" + e.target.id );
				//console.log(e.dataTransfer );
				//console.log(e.dataTransfer.files );
				
				e.preventDefault();
				e.stopPropagation();
				
				//不允许的浏览器
				if( window.navigator.userAgent.indexOf("MSIE 9.0")>-1 ){
					alert("也许您的浏览器不支持此操作，建议使用ie10+或者谷歌浏览器");
					return;
				}
				
				//先关闭遮罩。。不管是否正确。。
				/////// 由于html5拖拽特性，drop的话就不会dragleave ，因此有必要调用dragleave里的同样的逻辑。
				var dragoverCount = $(e.currentTarget).data("dragoverCount") || 0;
				dragoverCount--;
				if(dragoverCount<0) dragoverCount=0;//最小为0
				$(e.currentTarget).data("dragoverCount" ,dragoverCount);
				
				if(dragoverCount<=0) {
					//关闭遮罩
					if($(e.currentTarget).children(".dd_upload_mask:visible").length>0){ //如果存在元素可见
						$(e.currentTarget).children(".dd_upload_mask").hide();
					}
				}
				///////
				///////
				

				if( !e.dataTransfer ) return;//不支持的浏览器，退出循环。
				var files = e.dataTransfer.files;//IE9 都不支持
				if(!files || files.length==0) return;//如果没有文件。
				
				
				/*
				 * file的几个属性
				 * 
				 * id 随机id，后来添加表示当前id
				 * xhr 后来添加的表示当前xhr
				 * lastModifiedDate 最后修改时间
				 * name 文件名
				 * size 容量kb
				 * type 例如text/plain
				 * */
				
				

				
				
				//验证 扩展名
				var inValidFiles = getInValidExt(files, opts);
				if(inValidFiles!=null && inValidFiles.length>0){
					alert(inValidFiles+' 格式有误\n' + '请选择'+ 
							opts.fileTypeExts+'格式的文件');
					return;
				}
				//
				//验证 文件大小
				var inValidFiles2 = getInValidSize(files, opts);
				if(inValidFiles2!=null && inValidFiles2.length>0){
					alert(inValidFiles2+' 文件容量有误\n' + '文件容量需大于0且小于'+ 
							opts.fileSizeLimit/1024+'mb');
					return;
				}
				
				
				//$(e.target).trigger("dragleave"); //触发leave事件。。

				

				
//return;
				/*
				var reader = new FileReader();
				reader.onload=function(e){
					console.log(e.target.result);
				}
				reader.readAsText(files[0]);
				*/

				//opts.files = files;
				
				//先获取 总队列
				var queueAll = $(domEle).data("queueAll"); //历史queueAll。
				var queueTemp = $(domEle).data("queueTemp"); //历史queueTemp。
				var isNewFlag = false;
				if( ! queueTemp || queueTemp.length==0 ){ //如果临时队列没数据,相当于上传完成了。
					queueAll = []; //重新初始化 总队列
					queueTemp = [];
					$(domEle).data("queueAll" , queueAll); //重新开始
					$(domEle).data("queueTemp" ,queueTemp);
					isNewFlag = true;
				}
				
				for(var i = 0; i<files.length; i++){
					//产生随机队列id
					var max = 99999999 , min=100000;
					var randomInt = Math.floor(Math.random() * (max - min + 1)) + min;
					files[i].id= "fileId_" + randomInt;
					queueAll.push(files[i]);//总队列
					queueTemp.push(files[i]);//临时队列
				}
				//console.log("domEle data ID= "+domEle.id);
				

				//alert("通过");
				//return;
				
				//刚选择事件触发
				opts.onSelect_Drop(files); 
				//
				
				 //显示默认的ui交互
				if(opts.showProgress){
					initDefaultProgress(files , opts , e.currentTarget);
				}
				//console.log(files);
				
				//如果是新的队列，则开始异步上传
				if(isNewFlag) {
					for(var i =0; i<opts.thread; i++){
						doAjaxUpload(queueAll ,opts,queueTemp , e.currentTarget);	
					}
				}
				
				
				return false;
			})

			//return true  ; //相当于continue
			//return false ; //相当于break
			
		});//end each
		
		/*return jqObjs;*/
	}// rmpbUploadify end


	/**
	 * 取消队列
	 */
	$.fn.rmpbUploadifyCancel = function(fileId){
		this.each(function(index, domEle){
			
			//取得相应的数据
			var $rmpbUploadify = $(domEle).closest(".rmpbUploadify");
			var queueAll = $rmpbUploadify.data("queueAll");
			var queueTemp = $rmpbUploadify.data("queueTemp");
			var opts = $rmpbUploadify.data("opts");
			
			var queueAll01 = getFileById(queueAll , fileId);
			var queueTemp01 = getFileById(queueTemp , fileId);
			
			//alert("开始中断");
			queueTemp01=null; //队列设为null
			//中断xhr
			if(queueAll01) {
				var xhr = queueAll01.xhr;
				if(xhr){
					xhr.abort();
				}
			}
			//
			var $dd_upload_progress = $(domEle).closest(".dd_upload_progress");
			//删掉tr
			$(domEle).closest("tr[id]").remove();

			//如果tr全删除，则关闭dd_upload_progress
			var trSize = $dd_upload_progress.find("tr[id]").length;
			//alert(trSize);
			if(trSize==0) {
				$dd_upload_progress.remove();
				//alert("已经删除");
			}
			
			//触发cancel事件
			opts.onCancel(queueAll01);
			
			return false; //break;
		});//end each
		
	}//end rmpbUploadifyCancel
	
	
	/*--------------------------------------------------*
	 * default settings
	 *--------------------------------------------------*/
	$.fn.rmpbUploadify.defaults = {
	    
	    formData:{},//使用json格式
	    fileObjName:'file1', //相当于 name='file1'
	    fileSizeLimit:'102400', //单位kb ， 102400kb==100mb
	    fileTypeExts: '*.zip;*.rar;*.7z;*.doc;*.xls;*.ppt;*.docx;*.xlsx;*.pptx;*.txt;*.pdf;*.chm;*.gif;*.bmp;*.jpg;*.png;*.tif',
	    uploader:'',
	    showProgress:true, //是否显示默认的进度条
	    thread:2,//允许n个线程同时间上传。太多会对IO造成压力。
	    //简化了保留3个参数..
	    onUploadProgress:function(file, bytesUploaded, bytesTotal ){//可以监测进度条
	    	
		},
		onSelect_Drop : function(files) { //代替了onSelect 刚选择事件触发
			
		},
		onUploadComplete: function(file){//单个上传成功
			
		},
		onQueueComplete: function(files){ //全部队列上传成功
			
		},
		onCancel:function(file){ //取消上传
			
		}
	    
	    
	}; // end defaults
		
	
})(jQuery);

