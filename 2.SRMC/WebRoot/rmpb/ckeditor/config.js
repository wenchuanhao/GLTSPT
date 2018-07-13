/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For complete reference see:
	// http://docs.ckeditor.com/#!/api/CKEDITOR.config

	// The toolbar groups arrangement, optimized for two toolbar rows.




	// Define changes to default configuration here.
	// For complete reference see:
	// http://docs.ckeditor.com/#!/api/CKEDITOR.config

	// The toolbar groups arrangement, optimized for two toolbar rows.
	/*
	config.toolbarGroups = [
		{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
		{ name: 'links' },
		{ name: 'insert' },
		{ name: 'forms' },
		{ name: 'tools' },
		{ name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'others' },
		'/',
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
		{ name: 'styles' },
		{ name: 'colors' },
		{ name: 'about' }
	];
*/
	
	//精简的
	/*
	config.toolbarGroups = [
	                		//{ name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
	                		//{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
	                		//{ name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
	                		//{ name: 'forms' },
	                		//{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
	                		//{ name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
	                		//{ name: 'links' },
	                		{ name: 'insert' },
	                		{ name: 'styles' },
	                		{ name: 'colors' },
	                	//	{ name: 'tools' },
	                	//	{ name: 'others' },
	];
	*/
	
	//工具栏
	/*
config.toolbar =[
	//加粗     斜体，     下划线      穿过线      下标字        上标字
	['Bold','Italic','Underline','Strike','Subscript','Superscript'],
	//数字列表          实体列表            减小缩进    增大缩进
	['NumberedList','BulletedList','-','Outdent','Indent'],
	//左对齐             居中对齐          右对齐          两端对齐
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
	//超链接  取消超链接 锚点
	['Link','Unlink','Anchor'],
	//图片    flash    表格       水平线            表情       特殊字符        分页符
	['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
	'/',
	//样式       格式      字体    字体大小
	['Styles','Format','Font','FontSize'],
	//文本颜色     背景颜色
	['TextColor','BGColor'],
	//全屏           显示区块
	['Maximize', 'ShowBlocks','-']
]
*/
	
	
	//工具栏 客户要求版
	config.toolbar =[
		 //    字体    字体大小
		 ['Font','FontSize'],
		//加粗     斜体，     下划线    
		['Bold','Italic','Table' ]
		//文本颜色     背景颜色
		//['TextColor' ],

	]
	
	config.font_names='微软雅黑/微软雅黑;宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;新宋体/新宋体';
	// Remove some buttons provided by the standard plugins, which are
	// not needed in the Standard(s) toolbar.
	config.removeButtons = 'Underline,Subscript,Superscript';

	config.fontSize_sizes='8/8px;9/9px;10/10px;11/11px;12/12px;14/14px;16/16px;18/18px;20/20px;22/22px;24/24px;26/26px;28/28px;36/36px'
	// Set the most common block elements.
	config.format_tags = 'p;h1;h2;h3;pre';

	// Simplify the dialog windows.
	config.removeDialogTabs = 'image:advanced;link:advanced';
	
	//默认回车(去掉默认 附加<p> )
	config.enterMode = CKEDITOR.ENTER_BR;
	config.shiftEnterMode = CKEDITOR.ENTER_P;
	//删除底边栏
	config.removePlugins = 'elementspath';
	config.resize_enabled = false;

	//工具栏是否可以被收缩
    config.toolbarCanCollapse = false;
    
	//主题
	//config.skin = 'bootstrapck';
	
	//TableResize插件
	config.extraPlugins = 'tableresize';

	//默认字体
	config.font_defaultLabel = '微软雅黑';
	config.fontSize_defaultLabel = '12px';
	
	config.tabSpaces = 4;

};
