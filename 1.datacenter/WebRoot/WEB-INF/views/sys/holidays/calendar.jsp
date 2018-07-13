<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="/SRMC/rmpb/css/style_calendar.css"/>
    <link rel="stylesheet" type="text/css" href="/SRMC/rmpb/fullcalendar/fullcalendar.css">
    <link rel="stylesheet" type="text/css" href="/SRMC/rmpb/js/My97DatePicker/skin/WdatePicker.css">
    <style type="text/css">
        #loading {
            position: absolute;
            top: 5px;
            right: 5px;
        }

        #calendarDiv {
            float: left;
            width: 76.8%;
            min-width: 640px;
            height: 496;
        }

        .fc-other-month .fc-day-number {
            display: none;
        }

        .fc-day-content {
            display: none;
        }

        .fc-day-number {
            clear: both;
            text-align: center;
            width: 100%;

        }
    </style>
</head>
<body class="bg_c">
<div class="gl_m_r_nav">当前位置 : 系统管理 > 业务配置 > 节假日配置 > 配置节假日</div>

<div class="gl_import_m">
    <div class="gl_title_text">
        <span>配置操作提示:</span>在日历控件中选择需要配置的日期，点击即可完成配置。如需取消配置，请重复点击一次所选状态。
    </div>
    <div class="gl_Calendar">
        <div id="calendarDiv"></div>
        <div class="gl_Remark r">
            <div class="gl_Remark_t">
                备注
            </div>
            <div class="gl_Remark_color">
                <ul>
                    <li class="color_1">当日</li>
                    <li class="color_2">工作日</li>
                    <li class="color_3">休息日</li>
                    <li class="color_4">已配置为工作日</li>
                    <li class="color_5">已配置为休息日</li>
                </ul>
            </div>
            <div id="fastChoice">

            </div>
        </div>
    </div>
    <div class="ge01"></div>
</div>


</body>
<script src="/SRMC/rmpb/js/jquery-1.11.0.min.js"></script>
<script src="/SRMC/rmpb/fullcalendar/fullcalendar.js"></script>
<script src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
    var ctx = "${ctx}";
    $(function () {

        var calendarAjax = function (settings) {
            $.ajax({
                url: settings.url,
                type: settings.type,
                dataType: 'json',
                data: settings.data,
                error: function (jqXHR, textStatus, errorThrown) {
                    console.dir(textStatus);
                    alert("设置失败,请检查网络连接");
                },
                success: function (data, textStatus, jqXHR) {
                    if (textStatus == "success") {
                        if (data.flag == "error") {
                            alert("操作失败:" + data.message);
                        }
                        if (data.flag == "success") {
                            settings.callback(data.content);
                        }
                    } else {
                        alert("访问出错");
                    }

                }

            });
        };

        var resizeCalendar = function () {
            var cell;
            $('#calendarDiv td:first-child').each(function (i, _cell) {
                if (i < 7) {
                    cell = $(_cell);
                    var minHeight = cell.find('> div').css('min-height');
                    cell.find('> div').css(
                            'line-height',
                            minHeight);
                }
            });
        };

        var calendar = $("#calendarDiv").fullCalendar({
            header: {
                left: 'title',
                right: 'prevYear,prev,today,next,nextYear'
            },
            titleFormat: {
                month: 'yyyy年MM月'
            },
            weekMode: 'variable',
            events: function (start, end, callback) {
                resizeCalendar();
                calendarAjax({
                    url: ctx + "/sys/holiday/load",
                    type: 'POST',
                    data: {start: $.fullCalendar.formatDate(start, 'yyyy-MM-dd'), end: $.fullCalendar.formatDate(end, 'yyyy-MM-dd')},
                    callback: function (returnData) {
                        for (var i = 0; i < returnData.length; i++) {
                            var tdMap = returnData[i];
                            for (var p in tdMap) {
                                var tdObj = $("td[data-date=" + p + "]");
                                if(!tdObj.hasClass("fc-other-month")){
                                    tdObj.addClass(tdMap[p] == 'workday' ? "fc-workday" : "fc-holiday");
                                }
                            }
                        }
                    }
                });
            },
            dayClick: function (date, allDay, jsEvent, view) {
                var dateStr = $.fullCalendar.formatDate(date, 'yyyy-MM-dd');
                var tdObj = $("td[data-date=" + dateStr + "]");
                if (!tdObj.hasClass("fc-other-month")) {
                    var dayType = '', callbackFun;
                    if (tdObj.hasClass("fc-workday")) {
                        //改为休息
                        dayType = '${holiday}';
                        callbackFun = function () {
                            tdObj.removeClass("fc-workday");
                        }
                    } else if (tdObj.hasClass("fc-holiday")) {
                        dayType = '${workday}';
                        callbackFun = function () {
                            tdObj.removeClass("fc-holiday");
                        }
                    } else if (tdObj.hasClass("fc-sun") || tdObj.hasClass("fc-sat")) {
                        dayType = '${workday}';
                        callbackFun = function () {
                            tdObj.addClass("fc-workday");
                        }
                    } else {
                        dayType = '${holiday}';
                        callbackFun = function () {
                            tdObj.addClass("fc-holiday");
                        }
                    }

                    calendarAjax({
                        url: ctx + "/sys/holiday/switch/" + dayType,
                        type: 'POST',
                        data: {monthay: dateStr},
                        callback: callbackFun
                    });
                }

            },
            loading: function (bool) {
                //if (bool) $('#loading').show();
                //else $('#loading').hide();
            }
        });

        $('span.fc-button-nextYear').after('&nbsp;<span class="fc-button fc-button-add fc-state-default fc-corner-left fc-corner-right">选择</span>&nbsp;');
        $(".fc-button-add").bind("click", function () {
            WdatePicker({
                dateFmt: 'yyyy-MM',
                isShowClear: false,
                autoUpdateOnChanged: false,
                onpicked: function (dp) {
                    var newDate = dp.cal.getNewDateStr();
                    var result = newDate.split("-");
                    calendar.fullCalendar('gotoDate', result[0], result[1] - 1, result[2]);
                    $(this).html("选择");
                }
            });
        });
    });
</script>
</html>
