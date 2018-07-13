/* @author: hongru.chen
** @date: 2010-09-15
** @download:http://www.codefans.net
** @vision: 1.1
*/
var Hongru = {};
function $(id){return document.getElementById(id)}

Object.prototype.extend = function(target, /*optional*/source, /*optional*/deep) {
    target = target || {};
    var sType = typeof source, i = 1, options;
    if( sType === 'undefined' || sType === 'boolean' ) {
        deep = sType === 'boolean' ? source : false;
        source = target;
        target = this;
    }
    if( typeof source !== 'object' && Object.prototype.toString.call(source).call(source) !== '[object Function]' )
        source = {};
   
    while(i <= 2) {
        options = i === 1 ? target : source;
        if( options != null ) {
            for( var name in options ) {
                var src = target[name], copy = options[name];
                if(target === copy)
                    continue;
                if(deep && copy && typeof copy === 'object' && !copy.nodeType)
                    target[name] = this.extend(src ||
                            (copy.length != null ? [] : {}), copy, deep);
                else if(copy !== undefined)
                    target[name] = copy;
            }
        }
        i++;
    }
    return target;
};

isFunction = function( fn ) {  
    return !!fn && typeof fn != "string" && !fn.nodeName &&  
        fn.constructor != Array && /^[\s[]?function/.test( fn + "" );  
} 

Hongru.box = function(){
	var box,mask,content,_content,_height,_width,isPreload,flag = false;
	
	return{
		open:function(con,options){
		//default options
			var defaultOptions = {
				width:300,
				height:200,
				isPre:true,
				time:0,
				title:'',
				isBar:true,
				isShut:true
			};
			options = options?options:{};
			options = Object.extend(defaultOptions,options);
			
			if(!flag){
				box = document.createElement('div');box.id = "popup-box";
				box.style.cssText = "position:absolute; display:none; background:#e6f3fb url(preload.gif) no-repeat 50% 50%; border:5px solid #8cb2ca; z-index:2000";
				mask = document.createElement('div');mask.id = "popup-mask";
				mask.style.cssText = "position:absolute; display:none; top:0; left:0; height:100%; width:100%; background:#fff; z-index:1500";
				content = document.createElement('div');content.id = "popup-content";
				content.style.cssText = "background:#e6f3fb";
				bar = document.createElement('div'); bar.id = "popup-bar";
				bar.style.cssText = "background:none repeat scroll 0 0 #e6f3fb;border-bottom:4px solid #e6f3fb;border-top:3px solid #e6f3fb;margin-top:2px;position:relative";
				wrapTit = document.createElement('div'); wrapTit.id = "wrap-tit";
				wrapTit.style.cssText = "background:none repeat scroll 0 0 #e6f3fb;border-bottom:5px solid #e6f3fb;border-top:4px solid #e6f3fb;line-height:5px;margin-top:3px;";
				tit = document.createElement('span'); tit.id = "popup-tit";
				tit.style.cssText = "cursor:text;margin-left:10px;position:relative;color:#333;font-size:84%"
				
				shut = document.createElement('a'); shut.id = "popup-shut";
				shut.innerHTML = '×';
				shut.style.cssText = "color:#34538B;cursor:pointer;font-family:Tahoma;font-weight:bold;position:absolute;top:0px;right:10px;text-decoration:none;";
				document.body.appendChild(mask); document.body.appendChild(box); box.appendChild(bar); box.appendChild(content); bar.appendChild(wrapTit); bar.appendChild(shut); wrapTit.appendChild(tit);
				mask.onclick = shut.onclick = Hongru.box.hide;
				//bar.onclick = function(){alert($('sure').id)}
				
				window.onresize = Hongru.box.resize; 
				flag = true;
			}tit.innerHTML = options.title;
			options.isShut?shut.style.display = '':shut.style.display = 'none';
			options.isBar?bar.style.display = '':bar.style.display = 'none';
			if(!options.isPre){
				box.style.width = options.width?options.width+'px':'auto'; 
				box.style.height = options.height?options.height+'px':'auto';
				box.style.backgroundImage = 'none'; 
				content.innerHTML = con;
			}
			else{
				content.style.display = 'none';
				box.style.width = box.style.height = '100px';
			}
			this.mask();
			this.alpha(mask,1,50);
			_content = con; _height = options.height; _width = options.width; isPreload = options.isPre;
			if(options.time){
				setTimeout(function(){Hongru.box.hide()},1000*options.time);
			}
		},
		
		fill:function(con,options){
			if(options.isPre){
				if(!options.width || !options.height){
					var autoWidth = box.style.width, autoHeight = box.style.height;
					content.innerHTML = con;
					box.style.width = options.width?options.width+'px':''; 
					box.style.height = options.height?options.height+'px':'';
					content.style.display = '';
					options.width = parseInt(box.offsetWidth-10); 
					options.height = parseInt(box.offsetHeight-10);
					content.style.display = 'none';
					box.style.width = autoWidth;
					box.style.height = autoHeight;
				}
				else{
					content.innerHTML = con;
					
				}
				this.size(box,options.width,options.height);
			}
			else{
				box.style.backgroundImage = 'none';
			}
		},
		
		hide:function(){
			Hongru.box.alpha(box,-1,0);
		},
		
		resize:function(){
			Hongru.box.pos();
			Hongru.box.mask();
		},
		
		mask:function(){
			mask.style.height = Hongru.page.total(1)+'px';
			mask.style.width=''; mask.style.width = Hongru.page.total(0)+'px';
		},
		
		pos:function(){
			var minTop = (Hongru.page.height()/2)-(box.offsetHeight/2); minTop = minTop<10?10:minTop;
			box.style.top=(minTop+Hongru.page.top())+'px';
			box.style.left=(Hongru.page.width()/2)-(box.offsetWidth/2)+'px';
		},
		
		alpha:function(obj,direction,destination){
			clearInterval(obj.animing);
			if(direction == 1){
				obj.style.opacity=0; obj.style.filter='alpha(opacity=0)';
				obj.style.display='block'; this.pos();
			}
			obj.animing = setInterval(function(){Hongru.box.alphaAnim(obj,destination,direction)},50);
		},
		
		alphaAnim:function(obj,destination,direction){
			var opacity = Math.round(obj.style.opacity*100);
			if(opacity == destination){
				clearInterval(obj.animing);
				if(direction == -1){
					obj.style.display='none';
					obj == box?Hongru.box.alpha(mask,-1,0):content.innerHTML=box.style.backgroundImage='';
				}else{
					curOptions = {width:_width,height:_height,isPre:isPreload}
					obj == mask?this.alpha(box,1,100):Hongru.box.fill(_content,curOptions);
				}
			}else{
				var n=Math.ceil((opacity+((destination-opacity)*.5))); n=n==1?0:n;
				obj.style.opacity=n/100; 
				obj.style.filter='alpha(opacity='+n+')';
			}
		},
		
		size:function(obj,width,height){
			obj = typeof obj == 'object' ? obj : $(obj); 
			clearInterval(obj.sizing);
			var offsetW = obj.offsetWidth, offsetH = obj.offsetHeight,
			otherW = offsetW-parseInt(obj.style.width), otherH = offsetH-parseInt(obj.style.height);
			var wFlag = (offsetW-otherW>width)?0:1, hFlag = (offsetH-otherH>height)?0:1;
			obj.sizing = setInterval(function(){Hongru.box.sizeAnim(obj,width,otherW,wFlag,height,otherH,hFlag)},20);
		},
		
		sizeAnim:function(obj,width,otherW,wFlag,height,otherH,hFlag){
			var objW = obj.offsetWidth-otherW, objH = obj.offsetHeight-otherH;
			if(objW == width && objH == height){
				clearInterval(obj.sizing); 
				box.style.backgroundImage='none'; 
				content.style.display='block';
			}else{
				if(objW!=width){
					var n = objW+((width-objW)*.5); 
					obj.style.width = wFlag?Math.ceil(n)+'px':Math.floor(n)+'px';
					}
				if(objH!=height){
					var n = objH+((height-objH)*.5); 
					obj.style.height = hFlag?Math.ceil(n)+'px':Math.floor(n)+'px';
					}
				this.pos();
				
			}
		},
		
		ask:function(message,options,sureCall,cancelCall){
			var elements = '<div class="wrap-remind" style="text-align:center">'+message+'<p><button id="sure-btn" class="sure-btn">确认</button>&nbsp;&nbsp;<button id="cancel-btn" class="cancel-btn">取消</button></p></div>';
			Hongru.box.open(elements,options);
			
			function delay(){//回调
			if(($('sure-btn') && $('cancel-btn')) != null){
				clearInterval(checkComplete);
				//alert('yes');
				$('sure-btn').onclick = function(){
					if(isFunction(sureCall)){sureCall.call(this);}
				}
				$('cancel-btn').onclick = function(){
					if(isFunction(cancelCall)){cancelCall.call(this);}
					Hongru.box.hide();
				}
			}}
			var checkComplete = setInterval(delay,100);			
		}
	}
}();

Hongru.page=function(){
	return{
		top:function(){return document.documentElement.scrollTop||document.body.scrollTop},
		width:function(){return self.innerWidth||document.documentElement.clientWidth||document.body.clientWidth},
		height:function(){return self.innerHeight||document.documentElement.clientHeight||document.body.clientHeight},
		total:function(d){
			var b=document.body, e=document.documentElement;
			return d?Math.max(Math.max(b.scrollHeight,e.scrollHeight),Math.max(b.clientHeight,e.clientHeight)):
			Math.max(Math.max(b.scrollWidth,e.scrollWidth),Math.max(b.clientWidth,e.clientWidth))
		}
	}
}();
// JavaScript Document