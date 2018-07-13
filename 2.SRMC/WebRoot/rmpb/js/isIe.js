/**
 * other 不是ie浏览器
 * ie6	ie版本<=6
 * ie7	ie7
 * ie8	ie8
 * ie9	ie9
 * ie10	ie10
 * ie11	ie11
 * edge	ie的edge浏览器
**/

function isIE(){
    var ie = IEVersion();
    var index = ie.indexOf('ie');
    if(index > -1){
        return true;
    }else{
        return false;
    }
}

function IEVersion() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    if(isIE) {
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if(fIEVersion == 7) {
            return 'ie7';
        } else if(fIEVersion == 8) {
            return 'ie8';
        } else if(fIEVersion == 9) {
            return 'ie9';
        } else if(fIEVersion == 10) {
            return 'ie10';
        } else {
            return 'ie6';//IE版本<=7
        }   
    } else if(isEdge) {
        return 'edge';//edge
    } else if(isIE11) {
        return 'ie11'; //IE11  
    }else{
        return 'other';//不是ie浏览器
    }
}
