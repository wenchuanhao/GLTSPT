  /*鼠标移至上面时显示编辑按钮*/
  function toEdit(id){
	  if(lockstatus==0){
		  var i=id.indexOf('-');
		  var tname='';
		  var tid=id;	  
		  if(i>-1){
			  tname=id.substring(0,i);
			  tid=id.substring(i+1,id.length);
		  }	 
		  jQuery("#"+tname+"-edit"+tid).show();
	  }	 
  }
  
  /*退出编辑状态*/
  function cacelEdit(id){
	  if(lockstatus==0){
		  var i=id.indexOf('-');
		  var tname='';
		  var tid=id;	  
		  if(i>-1){
			  tname=id.substring(0,i);
			  tid=id.substring(i+1,id.length);
		  }	 
		  jQuery("#"+tname+"-edit"+tid).hide();
	  }	 
  }
  
  /*进入编辑状态*/
  function edit(id){
	  var i=id.indexOf('-');
	  var tname='';
	  var tid=id;	  
	  if(i>-1){
		  tname=id.substring(0,i);
		  tid=id.substring(i+1,id.length);
	  }	 
	  var divElement=jQuery("#"+tname+"-toEdit"+tid);
	  jQuery("#"+tname+"-edit"+tid).hide();
	  toReplace(divElement,id);
	  lockstatus=1;	 
  }
  
  //此函数功能是新建一个input元素替换div  
  //当input元素失去焦点时又变回原来的div  
  toReplace = function(divElement,id) {
      //将修改按钮显示
      var divElement=divElement.get(0);
      var i=id.indexOf('-');
	  var tname='';
	  var tid=id;	  
	  if(i>-1){
		  tname=id.substring(0,i);
		  tid=id.substring(i+1,id.length);
	  }	 
      var updateBtn=document.getElementById(tname+"-demandName"+tid);
      updateBtn.style.display="inline-block";
      // 创建一个input元素  
      var inputElement = document.createElement("input");  
      // 把obj里面的元素以及文本内容赋值给新建的inputElement  
      inputElement.value = divElement.innerHTML;  
      inputElement.style.width ="84%";
      // 用新建的inputElement代替原来的oldDivElement元素  
      divElement.parentNode.replaceChild(inputElement, divElement);  
      // 当inputElement失去焦点时触发下面函数，使得input变成div  
      updateBtn.onclick = function() {  
          //把input的值交给原来的div  
          divElement.innerHTML = inputElement.value; 
          //把修改的值暂存起来
          var temp=document.getElementById("temp");
          temp.value = inputElement.value;  
          //用原来的div重新替换inputElement  
          inputElement.parentNode.replaceChild(divElement, inputElement); 
          //隐藏按钮
           updateBtn.style.display="none";
      }       
      jQuery(tname+"-demandName"+tid).show();
      lockstatus=0;	
  } 