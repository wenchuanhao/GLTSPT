/*
 * 
 =============================适用于ajax 的分页控件 v1.0========================
 需要 引入 jquery.1.7 最好
需要引入 style.css样式
作者lijunhao
 日期  20140812
参数  elementID  ： div 的id
参数  liststep  ：页的显示长度 默认 10
参数  callback(pageIndex , pageSize)  ： 点击之后，执行用户定义的回调函数 ，传进 页数 和 页大小
例子
var nav1 = new Navigate("pageBox" , 5 , pageCallback1 );
nav1.gotoPage(1); //开始执行
nav1.gotoPage()//回到当前页
..
在pageCallback1函数体进行回调
nav1.updateShow(pageInfo); //pageInfo，是一个json对象
   	pageInfo = {
			total:20 ,
			pageSize:size,
			pageIndex:page,
			...
			...
	};


方法  updateShow (pageInfo) ，如果pageInfo 为undefined ， 则原有的状态刷新。
* 
* */

var Navigate = function( elementID , liststep , callback ){
	//修正页码端的css
	document.write("<style type='text/css'> .pageBox .page a{float:left; margin:0 1px; cursor: pointer;}</style>");
	this._total = 0;//总记录
	this._pageSize = 0;//每页size
	this._pageIndex = 0;//当前页
	this._pageCount = 0;//总页
	
	//初始化 页码元素
	this._pageBox =  jQuery("#"+elementID);
	this._pageBox.addClass("pageBox");
	//this._pageBox.css({"width":"auto"});
	this._page = jQuery("<div class='page' style=' float:right;' >"); //新建元素
	this._page2 = jQuery("<div class='page' style='float:right; width:185px;' >"); //新建元素

	//
	this._liststep = liststep || 10; //一共显示页(默认10)
	this._listbegin = 0; //开始页
	this._listend = 0;//结束页
	
	//清空
	Navigate.prototype.reset = function(){
		//清空以前的
		this._page.empty();
		this._page2.empty();
		this._pageBox.empty();
	}
	
	//处理事件 翻页
	Navigate.prototype.gotoPage = function( pageIndex , pageSize ){
		//pageIndex = pageIndex || 1;
		pageIndex = pageIndex || this._pageIndex;
		pageSize = pageSize || 10;
		this._callback( pageIndex , pageSize );
	}
	
	//外部的 事件回调
	this._callback = callback;
	
	//更新并显示
	Navigate.prototype.updateShow = function(pageInfo){
		
		//清空
		this.reset();
		
		var me = this;
		if(pageInfo!=undefined){
			this._total = pageInfo.total;//总记录
			this._pageSize = pageInfo.pageSize || 10;//每页size 默认10
			this._pageIndex = pageInfo.pageIndex || 1 ;//当前页 默认1 
			this._pageCount = Math.floor( (this._total-1)/this._pageSize )+1  ;//总页
			//判断当前页
			if(this._pageIndex<=0) this._pageIndex=1;
			if(this._pageIndex>this._pageCount) this._pageIndex=this._pageCount;
			
			//应该显示的页数
			var halfStep = Math.floor( this._liststep/2 );
			this._listbegin = this._pageIndex-halfStep ; 
			this._listend = this._pageIndex+halfStep ; 
			
			//判断显示页
			if(this._listbegin<=0) this._listbegin=1;
			//当前页到了倒数
			if( this._pageCount-this._pageIndex <= halfStep ) {
				this._listbegin=this._pageCount-this._liststep+1;
				if(this._listbegin<=0) this._listbegin=1;//大于0
			}
			
			if(this._listend>this._pageCount) this._listend=this._pageCount;
			//当总页数很少
			if(this._pageCount<this._liststep) this._listend=this._pageCount;
			//当比较前
			if( this._pageIndex <= halfStep ){
				this._listend = this._liststep;
				if(this._listend > this._pageCount ) this._listend=this._pageCount;
			}
		} //end if
		
		
		//for循环显示
		var links = [];
		if(this._pageIndex>1){
			links.push( jQuery(" <a class='prev_b' page='1' >首页</a> ") );
			links.push( jQuery( " <a class='prev' page='" +(this._pageIndex-1)+ "' >上一页</a> ") );
		}
		for(var i = this._listbegin ; i<= this._listend ; i++  ){
			if(i==this._pageIndex){
				links.push( jQuery(" <a class='current'  >" +i+ "</a> ") );
			}else{
				links.push( jQuery(" <a page='" +i+ "' >" +i+ "</a> ") );
			}
		}
		
		//如果记录为0 ，则显示1页
		if(this._pageCount==0) links.push( jQuery(" <a class='current' >1</a> ") );
		
		if(this._pageIndex<this._pageCount){
			links.push( jQuery(" <a class='next' page='" +(this._pageIndex+1)+ "' >下一页</a> ")  );
			links.push( jQuery(" <a class='next_b' page='" +this._pageCount+ "' >尾页</a> ") );
		}
		
		for( index in links){
			
			links[index].click(function(e){
				var page = e.target.getAttribute("page");
				if(page!=null && page!=''){//当前页无需事件
					$(e.target).unbind( "click" );//移除事件
					$(e.target).css({color:'#b0b0b0'});
					me.gotoPage( parseInt(page) , me._pageSize );
				}
			});
			this._page.append(links[index]); //添加到dom
		}
		
		//设置每页大小区域
		var links2 = [];
		links2.push( jQuery("<span class='pageBox_font'  >每页显示:</span>") );
		links2.push( jQuery("<a " +(this._pageSize==10?"class='current'":null)+ " size='10' >10</a>") );
		links2.push( jQuery("<a " +(this._pageSize==20?"class='current'":null)+ " size='20' >20</a>") );
		links2.push( jQuery("<a " +(this._pageSize==50?"class='current'":null)+ " size='50' >50</a>") );
		links2.push( jQuery("<span class='pageBox_font' style='float:right;padding:0 5px;'>页</span>") );
		
		for( index in links2){
			links2[index].click(function(e){
				var size = e.target.getAttribute("size");
				if(size!=null && size!=''){//当前页无需事件
					$(e.target).unbind( "click" );//移除事件
					$(e.target).css({color:'#b0b0b0'});
					me.gotoPage( 1 , parseInt(size) );
				}
			});
			this._page2.append(links2[index]); //添加到dom
		}
		
		this._pageBox.append( this._page );
		this._pageBox.append( "<br/ style='clear:both;' >" );
		this._pageBox.append( this._page2 );
		
		
	}; //  updateShow 结束
	
	
	
	
	
	
}; // Navigate 结束