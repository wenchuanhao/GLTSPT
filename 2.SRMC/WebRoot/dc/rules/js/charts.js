var config = {
        type: 'column2d',
        height: '300',
        dataFormat: 'json',
        dataSource: {
            "chart": {
                "caption": "按制度等级统计",
                "subCaption": "",
                "xAxisname": "",
                "yAxisName": "",
                "numberPrefix": "",
                "showValues": "1",
                "showLabels": "1",
                "exportEnabled":"1",//导出按钮
                "exportFormats": "PNG=导出PNG图片|JPG=导出JPG图片|PDF=导出PDF文件",
                "exportTargetWindow": "_self",
                "exportFileName": "图表文件导出",
//                "exportAtClient":"0",//客户端导出
                "paletteColors": "#0075c2,#1aaf5d, #8bbc21, #910000, #1aadce, #492970, #f28f43, #77a1e5, #c42525, #a6c96a",
                "captionFontSize" : "14",
                "subcaptionFontSize" : "14",
                "subcaptionFontBold" : "0",
                "showBorder" : "0",
                "bgColor" : "#ffffff",
                "showShadow" : "0",
                "canvasBgColor" : "#ffffff",
                "canvasBorderAlpha" : "0",
                "divlineAlpha" : "100",
                "divlineColor" : "#999999",
                "divlineThickness" : "1",
                "divLineIsDashed" : "1",
                "divLineDashLen" : "1",
                "divLineGapLen" : "1",
                "usePlotGradientColor" : "0",
                "showplotborder" : "0",
                "valueFontColor" : "#ffffff",//值字体颜色
                "placeValuesInside" : "1",//内部显示值
                "showHoverEffect" : "1",
//                "rotateValues" : "1",//旋转显示值
                "showXAxisLine" : "1",
                "xAxisLineThickness" : "1",
                "xAxisLineColor" : "#999999",
                "showAlternateHGridColor" : "0",
                "legendBgAlpha" : "0",
                "legendBorderAlpha" : "0",
                "legendShadow" : "0",
                "legendItemFontSize" : "10",
                "legendItemFontColor" : "#666666" 
            },
            "dataset": [

            ]
        },
        events: {
            "drawComplete": function (eventObj, dataObj) {
                console.log(eventObj);
                var $tspan = $("tspan");
            	$.each($tspan,function(k,v){
            		if($(v).html() == "FusionCharts XT Trial"){
            			$(v).parent().remove();//去处水印
            		}
            	});
            }
        }
    };