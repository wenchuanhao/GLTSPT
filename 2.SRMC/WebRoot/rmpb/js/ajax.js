//收起查询
function setDiv(){

  var setValue=jQuery("#setDivs").val();
  if('收起查询'==setValue){
	  jQuery("#setDivs").val("展开查询");
	  jQuery("#setDivs").attr("class","gl_cx_bnt01b");
	  jQuery("#serachDisplay").hide();
	  document.getElementById("searchArea").value="0";
	  parent.window["open"] = "false";
  }
  if('展开查询'==setValue){
	  jQuery("#setDivs").val("收起查询");
	  jQuery("#setDivs").attr("class","gl_cx_bnt01");
	  jQuery("#serachDisplay").show();
	  document.getElementById("searchArea").value="1";
	  parent.window["open"] = "true";
  }
}
function defaultAjax(url,data,datatype,succ_fn,err_fn){
	//var uri = $.trim(url) + '/' + Math.random();	     
	jQuery.ajax({
		type:"POST",
		url:url,
		async:true,
		data:data,
		dataType:datatype,
		success:function(response){
			succ_fn(response);
		},
		error:err_fn
	}
	);
	
}

//verify date
	function dateVerify(starId,endId){
		var start = jQuery("#"+starId).val();
		var end = jQuery("#"+endId).val();
		if("" == start && "" == end){
			return true;
		}else if("" == start){
			if("" != end){
				alert("查询开始时间不能为空!");
				return false;
			}
		}else if("" != start){
			if("" != end){
				var sdate = new Date(start.replace("-","/").replace("-","/"));
				var edate = new Date(end.replace("-","/").replace("-","/"));
				if(edate < sdate){
					alert("查询结束时间不能小于开始时间!");
					return false;
				}
			}
		}
		return true;
	}

// select more than one
	function getCheckedAll() {
		var ids="";
		//get all checkboxes 
		var chks = document.getElementsByName('checkbox');
		//do a traversal of all checkboxes which are checked
		for ( var i = 0; i < chks.length; i++) {
			if (chks[i].checked){
				ids+=chks[i].value+",";
			}
		}
		return ids;
	}
//select one only
	function getChecked() {
		var id = "";
		//get all checkboxes 
		var chks = document.getElementsByName('checkbox');
		var j = 0;
		for ( var i = 0; i < chks.length; i++) {
			if (chks[i].checked){
				j++;
				id = chks[i].value;
			}
		}
		if(j > 1){
		 	id = "more";
		}else if(0 == j){
			id = "none";
		}
		return id;
	}
	
	function getRadioChecked(radioName){
		
		var radioValue = "";
		var radios = document.getElementsByName(radioName);
		for(i=0; i < radios.length; i++){
			if(radios[i].checked){
				radioValue = radios[i].value;
				break;
			}
		}
			return radioValue;
		}
//change all checkboxes to be checked
	function doCheckAll(headboxId) {
		//get leader of checkboxes
		var headbox = jQuery("#"+headboxId).attr("checked");
		//get all checkboxes 
		var chks = document.getElementsByName('checkbox');
		if("checked" == headbox || true == headbox){
			for ( var i = 0; i < chks.length; i++) {
				if (!chks[i].checked){
					chks[i].checked = true;
				}
			}
		}else{
			for ( var i = 0; i < chks.length; i++) {
				if (chks[i].checked){
					chks[i].checked = false;
				}
			}
		}
	}
//reverse all the boxes between checked and unchecked
	function doReverseAll(){
		//get all checkboxes 
		var chks = document.getElementsByName('checkbox');
			for ( var i = 0; i < chks.length; i++) {
				if (!chks[i].checked){
					chks[i].checked = true;
				}else{
					chks[i].checked = false;
				}
			}
	}
	
//calculate the capability from byte to KB/MB/GB
	function calculate(cap){
	var kb = (cap/1024).toFixed(2);
	var mb = "";
	var gb = "";
	if(kb > 1024.00){
	    mb = (kb/1024).toFixed(2);
	}else{
	    return kb+"KB";
	}
	if(mb != "" && mb > 1024.00){
	    gb = (mb/1024).toFixed(2);
	}else{
	    return mb+"MB";
	}
	if(gb != ""){
	    return gb+"GB";
	}
}