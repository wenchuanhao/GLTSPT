
	function changeExpress(){
	  	var express = $("#express").val();
	  	//var express = $("#express  option:selected").text();
	  	$("#expressOther").val("请输入其他物流公司名称");
	  	document.getElementById("expressOther").style.color = "#999";
	  	if("其他" == express){
	  	 $("#expressOther").show();
	  	 $("#exp1").attr("colspan","3");
	  	 $("#note2").attr("colspan","1");  
	  	 $("#adaptersupplyNote").css("width","98%");
	  	 $("#express").css("width","206px"); 
	  	 $("#express").css("margin-right","10px");		 
	  	 
	  	}else{
	  	 $("#expressOther").hide();
	  	 $("#exp1").attr("colspan","1");
	  	 $("#note2").attr("colspan","3");
	  	 $("#express").css("width","100%");
	  	 $("#express").css("margin-right","0px");	  
	  	 $("#adaptersupplyNote").css("width","35%"); 	  	 
	  	}
	 }



    var rowid = 0;
	
	function newTerminal(){
		//必填验证
		var flag = verify();
		if(flag){
			var imei  = $("#imei").val();
			if("" == imei){
				alert("IMEI不能为空!");
				return false;
			}else{
				if(imei=="无效IMEI"){			
				}else{
					var f=/[^\d]/g;
					if(f.test(imei)){
						alert("IMEI不能超过15位字符且只能是数字!");
						$("#imei").val("");
						return false;
					}
					
				}
				
			}
			var opr = $("#newButton").val();
			if("新 增" == opr){
				rowid = rowid + 1;
				addTr();
			}else{
				var trId = $("#uniqeId").val();
				updateRecard(trId);
				$("#uniqeId").val("");
				$("#newButton").val("新 增");
				$("#resetBut").show();
				
			}
			//操作完成 清空表单
			reset();
		}
	}
	//修改记录
	function updateRecard(id){
		var tr = document.getElementById(id);
		//获取修改值
		  var brand = $("#brand").val();//品牌
		  var specification = $("#specification").val();//型号
		  //类型
		  var adaptersupplyType = getPctype();
		  if("1" == adaptersupplyType){
		  	adaptersupplyType = "适配样机";
		  }else{
		  	adaptersupplyType = "验收样机";
		  }
		  //IMEI
		  var imei  = $("#imei").val();
		  //物流公司，如果选择其他，使用手动填写的物流公司
		  var express = $("#express").val();
		  if("其他" == express){
		  	//var oldId = $("#otherId").val();
		  	tr.cells[5].id="1";
		  	express = $("#expressOther").val();// + "<input id='isOther"+id+"' type='hidden' value='1'/>";
		  }else{
		  tr.cells[5].id="0"
		  	//express = express + "<input id='isOther"+id+"' type='hidden' value='0'/>";
		  }
		  //物流单号
		  var number  = $("#number").val();
		  //邮寄时间
		  var sendTime = $("#sendTime").val();
		//单元格赋值
		tr.cells[1].innerHTML = adaptersupplyType;
		tr.cells[2].innerHTML = brand;
		tr.cells[3].innerHTML = specification;
		tr.cells[4].innerHTML = imei;
		tr.cells[5].innerHTML = express;
		tr.cells[6].innerHTML = number;
		tr.cells[7].innerHTML = sendTime;		
	}
	//添加终端
	function addTr(){		
	  //获得表格对象
	  var tb=document.getElementById('terminals');
	  //一面后台进入存在数据 发生行ID（<tr id='tr1'>）冲突
	  	if(0 == rowid){
			rowid = tb.rows.length;
		}  
	  //添加一行   
	  var newTr = tb.insertRow(-1);//在最下的位置
	  //给这个行设置id属性，以便于管理（删除）
	  newTr.id='tr'+rowid;
	  newTr.name='tr'+rowid;
	  //newTr.class='tab_c_tr01';
	  //设置对齐方式（只是告诉你，可以以这种方式来设置任何它有的属性）
	  newTr.align='left';
	  //添加列   
	  var newTd0 = newTr.insertCell(0);//序号   width="5%"
	  var newTd1 = newTr.insertCell(1);//适配类型
	  var newTd2 = newTr.insertCell(2);//品牌
	  var newTd3 = newTr.insertCell(3);//型号
	  var newTd4 = newTr.insertCell(4);//IMEI
	  var newTd5 = newTr.insertCell(5);//物流公司
	  var newTd6 = newTr.insertCell(6);//快递单号
	  var newTd7 = newTr.insertCell(7);//寄送时间
	  var newTd8 = newTr.insertCell(8);//操作 width="15%"
	  //保存物流公司 其他使用的单元格 隐藏var newTd9 = newTr.insertCell();newTd9.style="display:none";
	  //获取输入值
	  var brand = $("#brand").val();
	  var specification = $("#specification").val();
	  var adaptersupplyType = getPctype();
	  if("1" == adaptersupplyType){
	  	adaptersupplyType = "适配样机";
	  }else{
	  	adaptersupplyType = "验收样机";
	  }
	  var imei  = $("#imei").val();
	  //物流公司，如果选择其他，使用手动填写的物流公司
	  var express = $("#express").val();
	  var other = "";
	  if("其他" == express){
	  	newTd5.id="1";
	    //other = "<input id='isOthertr"+rowid+"' type='hidden' value='1'/>";
	  	express = $("#expressOther").val() + other;
	  }else{
	  	newTd5.id="0";
	  	//other = "<input id='isOthertr"+rowid+"' type='hidden' value='0'/>";
	  	express = express + other;
	  }

	  //物流单号
	  var number  = $("#number").val();
	  //邮寄时间
	  var sendTime = $("#sendTime").val();
	  var mobileCode = $("#mobileCode").val();
	  var system = $("#system").val();
	  var systemVersion = $("#systemVersion").val();
	  //将输入值写入相应列
	  newTd0.innerHTML =  tb.rows.length - 1;//id;//+"<input id='uniqe'"+id+" value='"+id+"'type='hidden'/>";
	  newTd1.innerHTML=  adaptersupplyType;
	  newTd2.innerHTML= brand;
	  newTd3.innerHTML= specification;
	  newTd4.innerHTML= imei;
	  newTd5.innerHTML= express;
	  newTd6.innerHTML= number;
	  newTd7.innerHTML= sendTime;
	  
	  var ismywork = $("#ismywork").val();
	  if('1' == ismywork){
	  newTd8.innerHTML= "<a href='#' class='fone_style08' onclick='edit("+newTr.id+");'>编辑</a>"
	  +"<a href='#' class='fone_style04' onclick='moveTr("+newTr.id+");' >删除</a>"
	  +"<a href='#' class='fone_style04' onclick='insertData('-1');'>入库</a>";
	  }else{
	  newTd8.innerHTML= "<a href='#' class='fone_style08' onclick='edit("+newTr.id+");'>编辑</a><a href='#' class='fone_style04' onclick='moveTr("+newTr.id+");' >删除</a>";
	  }
	  
	//alert(express);
	//alert(newTd5.innerHTML);
  }
  //删除终端，移除行的方法
  function moveTr(tr){
	  if(confirm("确定要删除该条终端信息吗？")){
		  //获得表格对象
		  var tb=document.getElementById('terminals');
		   //取出行的索引，设置删除行的索引
		  tb.deleteRow(tr.rowIndex);
		  //确认如果是正在修改的记录 则清空修改标示 修改操作按钮 修改操作标示
		  var uniqe = $("#uniqeId").val();
		  if(uniqe == tr.id){
		    $("#newButton").val("新 增");
			$("#uniqeId").val("");
		  }
		 
		  var co = tb.rows.length;
		  for(var i = 1; i < co; i++){
		  	tb.rows[i].cells[0].innerHTML=(i);
		  }
	  }
	  return false;
	  
  }
  //编辑终端
  function edit(tr){
  var tr2 = tr;
	  //修改使用 变更同步Id
	  //$("#otherId").val("isOther"+tr.id);
	  //唯一行标示
	  tr = document.getElementById(tr);
	  
	  if("" == tr || null == tr){
	  	tr = tr2;
	  }
	  $("#uniqeId").val(tr.id);
	  
	$("#brand").val(tr.cells[2].innerHTML);
	
	//重新读取型号,并选择当前型号
	var specification = tr.cells[3].innerHTML;
	getSepecifications(tr.cells[2].innerHTML,specification);
	
	//类型
	var ap = document.getElementsByName("adaptersupplyType");
	if("适配样机" == tr.cells[1].innerHTML){
		ap[0].checked = "checked";
	}else{
		ap[1].checked = "checked";
	}
	$("#imei").val( tr.cells[4].innerHTML);
	//判断之前是否其他物流
	var other = tr.cells[5].id;//$("#isOther"+tr.id).val();
	
	if("1" == other){
		//下拉定位到 其他
		$("#express").val("其他");
		var html = tr.cells[5].innerHTML;
		document.getElementById("expressOther").style.color = "";
		$("#expressOther").val(html);
		$("#expressOther").show();
		
		
	}else{
		//截取字符
		var html = tr.cells[5].innerHTML;
		//alert(html.length+"---------"+html+"---------");
		$("#express").val(html);
	}
	$("#number").val(tr.cells[6].innerHTML);
	$("#sendTime").val(tr.cells[7].innerHTML);
	$("#newButton").val("保存修改");//保存按钮变为保存修改
	$("#resetBut").hide();//隐藏重置按钮
	
  }
  
  
  function subTerminalsHandler(table){
  	  var subStr = "";
      var co = table.rows.length;
	  for(var i = 1; i < co; i++){
	  	var type = table.rows[i].cells[1].innerHTML;//类型
	  	var brand = table.rows[i].cells[2].innerHTML;//品牌
	  	var spec = table.rows[i].cells[3].innerHTML;//型号
	  	var imei = table.rows[i].cells[4].innerHTML;//IMEI
	  	var express = table.rows[i].cells[5].innerHTML;//物流公司

//	  	var arr = express.split("<");
//alert(arr.length+"@"+arr[0]+"@"+arr[1]);
//	  	express = arr[0];    arr[1].substr((arr[1].length-4),1);
	  	var expressType = table.rows[i].cells[5].id
	  	//alert(express+"---"+expressType);
	  	var number = table.rows[i].cells[6].innerHTML;//快递单号
	  	var senttime = table.rows[i].cells[7].innerHTML;//寄送时间
	  	
	  	var subId = table.rows[i].cells[7].id;
	  	if(undefined == subId || "" == subId){
	  		subId = -1;
	  	}

	  	if(1 == i){
	  		subStr = subStr+type+","+brand+","+spec+","+imei+","+express+","+number+","+senttime+","+expressType+","+subId;
	  	}else{
	  		subStr = subStr+"~"+type+","+brand+","+spec+","+imei+","+express+","+number+","+senttime+","+expressType+","+subId;
	  	}
	  	//alert(subStr);
	  }
  	$("#subTerminals").val(subStr);
  }
  
  
   //reset
  function reset(){	
	var ap = document.getElementsByName("adaptersupplyType");
	ap[0].checked = "checked";
	$("#brand").val("");
	$("#specification").val("");
	$("#mobileCode").val("");
	var ap1 = document.getElementsByName("system");
	ap1[0].checked = "checked";
	$("#system").change();	
	$("#imei").val("");
	$("#sendTime").val("");
	$("#number").val("");
	$("#express").val("");
	$("#expressOther").val("");
	$("#expressOther").hide();	
	$("#adaptersupplyNote").val("");
	
	$("#exp1").attr("colspan","1");
  	$("#note2").attr("colspan","3");	
  	$("#express").css("width","100%");
  	$("#express").css("margin-right","0px");	  
  	$("#adaptersupplyNote").css("width","35%");	
	
  }
  function verify(){
  	var brand = $("#brand").val();
	var specification = $("#specification").val();
	/*var adaptersupplyType = getPctype();*/
	var adaptersupplyType =jQuery("input:checked[id='adaptersupplyType']").val();
	var imei  = $("#imei").val();
	//物流公司，如果选择其他，使用手动填写的物流公司
	var express = $("#express").val();
	var number  = $("#number").val();
	var sendTime = $("#sendTime").val();
	var mobileCode = $("#mobileCode").val();
	var system = $("#system").val();	
	var systemVersion = $("#systemVersion").val();
	
	if("" == adaptersupplyType){
		alert("适配类型不能为空!");
		return false;
	}
	if("" == brand){
		alert("品牌不能为空!");
		return false;
	}
	if("" == specification||exampleStr1==specification){
		alert("型号不能为空!");
		return false;
	}
	if("" == mobileCode||exampleStr2==mobileCode){
		alert("机型代号不能为空!");
		return false;
	}	
	if(""==system){
		alert("请选择操作系统");
		return false;
	}	
	if(""==systemVersion){
		alert("请选择操作系统版本");
		return false;
	}		
	if("" == imei){
		alert("IMEI不能为空!");
		return false;
		}else{
			
			var f=/[^\d]/g;
			if(f.test(imei)){
				alert("IMEI不能超过15位字符且只能是数字!");
				$("#imei").val("");
				return false;
			}
	}	
	if("" == sendTime){
		alert("寄送时间不能为空!");
		return false;
	}
	if("" == number){
		alert("快递单号不能为空!");
		return false;
	}		
	if("" == express){
		alert("物流公司不能为空!");
		return false;
	}
	if("其他" == express){
		  express = $("#expressOther").val();
		  if("" == express || "请输入其他物流公司名称" == express){
		  express = "";
		  }
	}	
	return true;
  }
  function getPctype(){
  var adaptersupplyType = "";
   var ap = document.getElementsByName("adaptersupplyType");
		for(i=0; i<ap.length; i++){//遍历单选框
			 if(ap[i].checked){
				 adaptersupplyType = ap[i].value;
				 break;
			 }
		}
	return adaptersupplyType;
  }
  
  function otherExpressClick(elmentId){
  	var element = document.getElementById(elmentId);
  	element.style.color = "";
  	element.value = "";
  	//color:#999;
  }
  
  function otherExpressBlur(elmentId){
  	var element = document.getElementById(elmentId);
  	if("" == element.value){
  		element.style.color = "#999";
  		element.value = "请输入其他物流公司名称";
  	}
  }