

var expressArea, areaCont, areaList = $("#areaList"), areaTop = areaList.offset().top;

/*初始化*/
function intProvince() {
	areaCont = "";
	for (var i=0; i < province.length; i++) {
		areaCont += '<li value="' + province[i].value + '" onClick="selectP(' + i + ');">' + province[i].name + '</li>';
	}
	areaList.html(areaCont);
	$("#areaBox").scrollTop(0);
	$("#backUp").removeAttr("onClick").hide();
	//如果只有一个角色，选择第一个
	if(province.length == 1){
		setValues(0);
	}
}
intProvince();

/*选择*/
function selectP(p) {
	setValues(p);
    clockArea();
}

function setValues(p){
	expressArea = province[p];
	$("#expressArea dl dd").html(expressArea.name);
	$("#flowRoleid").val(expressArea.value);
	$("#flowRolename").val(expressArea.name);
}

function selectD(p,c,d) {
	clockArea();
	expressArea += district[p][c][d];
	$("#expressArea dl dd").html(expressArea);
}

/*关闭选项*/
function clockArea() {
	$("#areaMask").fadeOut();
	$("#areaLayer").animate({"bottom": "-100%"});
	intProvince();
}

$(function() {
	/*打开选项*/
	$("#expressArea").click(function() {
		$("#areaMask").fadeIn();
		$("#areaLayer").animate({"bottom": 0});
	});
	/*关闭选项*/
	$("#areaMask, #closeArea").click(function() {
		clockArea();
	});
});