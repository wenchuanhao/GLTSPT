/*
 * jQuery Simple Overlay
 * 修改by  lijunhao 20140817
 * 去除了一些不常用的功能，加了关闭功能和loading图标 功能
 * 
 * 调用方法：
 * $('#aaa , #bbb').overlay();
 * 
 * 关闭方法：
 * $('#aaa , #bbb').overlay(false); //必须为false 
 * 
 * 默认参数配置
 * {
    color: '#a0a0a0',
    opacity: 0.1,
    effect: 'none',
    container: 'body',
    imgUrl:'loading/loading.gif'
	}
 */

(function($) {

	$.fn.overlay = function(options) {

    var opts = $.extend({}, $.fn.overlay.defaults, options);
		return this.each(function(evt) { //这个this是代表 jq elements集合
		  opts.container = this; //容器设定为自身
		  //alert(opts.container.toString());
	      if( options != false  && !$(this).hasClass('overlay-trigger') ) {
	        show(create($(this), opts, this.id ), opts);
	        show(createLoading($(this), opts, this.id ), opts);
	      }else if( options === false ) {
	    	  //关闭
	    	  var loading = $("#loading_" + this.id );
	    	  var overlay = $("#overlay_" + this.id );
	    	  close(loading, opts);
	          close(overlay, opts);
	    	  $(this).removeClass('overlay-trigger');
	      }
      
		});
    
	}; // end overlay

  /*--------------------------------------------------*
   * helper functions
   *--------------------------------------------------*/
  
  /**
   * Creates the overlay element, applies the styles as specified in the 
   * options, and sets up the event handlers for closing the overlay.
   *
   * opts The plugin's array options.
   */
  function create($src, opts , triggerId) {
  
    // prevents adding multiple overlays to a container
    $src.addClass('overlay-trigger');
  
    // create the overlay and add it to the dom
    var overlay = $("<div></div>")
      .addClass('overlay')
      .css({
        background: opts.color,
        opacity: opts.opacity,
        top: opts.container.nodeName.toLowerCase() === 'body' ? $(opts.container).scrollTop() : $(opts.container).offset().top,
        left: $(opts.container).offset().left,
        width: opts.container.nodeName.toLowerCase() === 'body' ? '100%' : $(opts.container).width(),
        height: opts.container.nodeName.toLowerCase() === 'body' ? $(document).height() : $(opts.container).height(),
        position: 'absolute',
        zIndex: 1000,
        display: 'none',
        overflow: 'hidden'
      })
      .attr('id', 'overlay_'+triggerId );

    
    // finally add the overlay
    $(opts.container).append(overlay);
   
    return overlay;
    
  } // end createOverlay
  
  
  

  /**
   * Creates the overlay element, applies the styles as specified in the 
   * options, and sets up the event handlers for closing the overlay.
   *
   * opts The plugin's array options.
   */
  function createLoading($src, opts , triggerId) {
  
  
    // create the overlay and add it to the dom
    var loading = $("<table><tr><td align='center' valign='middle' ><img src='" +opts.imgUrl+ "' /></td></tr></table>")
      .css({
        top: opts.container.nodeName.toLowerCase() === 'body' ? $(opts.container).scrollTop() : $(opts.container).offset().top,
        left: $(opts.container).offset().left,
        width: opts.container.nodeName.toLowerCase() === 'body' ? '100%' : $(opts.container).width(),
        height: opts.container.nodeName.toLowerCase() === 'body' ?  $(window).height() : $(opts.container).height(),
        position: 'absolute',
        zIndex: 1200,
        display: 'none',
        overflow: 'hidden',
        opacity: 0.5
      })
      .attr('id', 'loading_'+triggerId );

    
    // finally add the overlay
    $(opts.container).append(loading);
   
    return loading;
    
  } // end createOverlay
  
  
  
  /**
   * Displays the overlay using the effect specified in the options. Optionally
   * triggers the onShow callback function.
   *
   * opts The plugin's array options.
   */
  function show(overlay, opts) {
    
    switch(opts.effect.toString().toLowerCase()) {
    
      case 'fade':
        $(overlay).fadeIn('fast' );
        break;
      
      case 'slide':
        $(overlay).slideDown('fast');
        break;
        
      default:
        $(overlay).show();
        break;
    
    } // end switch/case
    
    //$(opts.container).css('overflow', 'hidden');
    
  } // end show
  
  /**
   * Hides the overlay using the effect specified in the options. Optionally
   * triggers the onHide callback function.
   *
   * opts The plugin's array options.
   */
  function close(overlay, opts) {
    
    switch(opts.effect.toString().toLowerCase()) {
        
      case 'fade':
        $(overlay).fadeOut('fast', function() {
          $(this).remove();
        });
        break;
            
      case 'slide':
        $(overlay).slideUp('fast', function() {
          $(this).remove();
        });
        break;
            
      default:
        $(overlay).hide();
        $(overlay).remove();
        break;
            
    } // end switch/case
    
    $(opts.container).css('overflow', 'auto');
    
  } // end close
  

  
  
  /*--------------------------------------------------*
   * default settings
   *--------------------------------------------------*/
   
	$.fn.overlay.defaults = {
    color: '#a0a0a0',
    opacity: 0.1,
    effect: 'none',
    //container: 'body',
    imgUrl:'/SRMC/rmpb/images/loading.gif'
	}; // end defaults

})(jQuery);