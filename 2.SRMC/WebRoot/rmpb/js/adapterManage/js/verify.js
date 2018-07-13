
function noLetter(e,elementId){
	var keynum;
	if(window.event) {// IE
	  	keynum = e.keyCode;
	  } else if(e.which){// Netscape/Firefox/Opera
	  	keynum = e.which;
	  }
	  alert();
	 //press the "Backspace"
	 if(8 == parseInt(keynum) || 46 == parseInt(keynum)){
		 return true;
	 }
	if(parseInt(keynum) < 48 || parseInt(keynum) > 57){
		var oldVal = document.getElementById(elementId).value;
		if(oldVal.length == 1){
			document.getElementById(elementId).value = "";
		}else{
		document.getElementById(elementId).value = oldVal.substring(0, oldVal.length-1)
		}
		return false;
	}
}
