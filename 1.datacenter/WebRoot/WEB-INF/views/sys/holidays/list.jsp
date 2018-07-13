<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link rel="stylesheet" type="text/css"  href="/SRMC/rmpb/css/style_calendar.css"/>
    <link rel="stylesheet" type="text/css"  href="/SRMC/rmpb/css/style.css"/>
     <link type="text/css"  href="/SRMC/dc/css/rewrite.css" rel="stylesheet" />
    <style type="text/css">
        .gl_text01_xxxfj {
            font-family: "微软雅黑";
            height: 20px;
            line-height: 20px;
            width: 43.9%;
            border: #cfd0d0 1px solid;
            font-size: 12px;
        }
    </style>
    <script type="text/javascript">
   function doSubmit(){
	   document.all("pageIndex").value = "1";
	   document.forms[0].submit();
	}
   </script>
</head>
<body class="bg_c">
<div class="gl_m_r_nav">当前位置 : 系统管理 > 业务配置  > 节假日配置 > 节假日变更管理</div>

<div class="gl_import_m">
    <form:form commandName="form" action="${ctx}/sys/holidaymanager/query" method="post" id="pageForm">
    <div class="gl_bt_bnt01"><input name="" type="button" class="gl_cx_bnt01" value="收起查询" id="showBtn"/>
        查询条件</div>
    <span id="searchTable">
    <table class="gl_table_a01" width="100%" border="0" cellspacing="0" cellpadding="0" id="searchFormTable">
        <tr>
            <th>年份：</th>
            <td><form:input cssClass="gl_text01_a" path="year" onfocus="WdatePicker({dateFmt:'yyyy'})"/></td>
            <th>月份：</th>
            <td>
                <form:select cssClass="select_new01" path="month">
                    <form:option value="">全部</form:option>
                    <form:option value="01">1</form:option>
                    <form:option value="02">2</form:option>
                    <form:option value="03">3</form:option>
                    <form:option value="04">4</form:option>
                    <form:option value="05">5</form:option>
                    <form:option value="06">6</form:option>
                    <form:option value="07">7</form:option>
                    <form:option value="08">8</form:option>
                    <form:option value="09">9</form:option>
                    <form:option value="10">10</form:option>
                    <form:option value="11">11</form:option>
                    <form:option value="12">12</form:option>
                </form:select>
            </td>
            <th>配置人：</th>
            <td><form:input cssClass="gl_text01_a" path="createUser"/></td>
        </tr>
        <tr>
            <th>原类型：</th>
            <td>
                <form:select cssClass="select_new01" path="sourceType">
                    <form:option value="">全部</form:option>
                    <form:option value="workday">工作日</form:option>
                    <form:option value="holiday">休息日</form:option>
                </form:select>
            </td>
            <th>现类型：</th>
            <td>
                <form:select cssClass="select_new01" path="targetType">
                    <form:option value="">全部</form:option>
                    <form:option value="workday">工作日</form:option>
                    <form:option value="holiday">休息日</form:option>
                </form:select>
            </td>
            <th>配置时间：</th>
            <td><form:input cssClass="gl_text01_xxxfj" path="queryStartCreateDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true"/><font style="width:10%;">&nbsp;-&nbsp;</font><form:input cssClass="gl_text01_xxxfj" path="queryEndCreateDate" cssStyle="width: 47%;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true"/></td>
        </tr>
    </table>

    <div  class="gl_ipt_03">
        <!-- <input name="input" type="submit" class="ipt_tb_n03" value="查 询" />&nbsp; -->
        <input name="input" type="button" class="ipt_tb_n03" value="查 询" onclick="javascript:doSubmit();"/>&nbsp;
        <input name="input" type="button" class="ipt_tb_n03" value="重 置" id="resetBtn"/>&nbsp;
    </div>
    </span>
    <div class="ge_a01"></div>

    <div class="gl_bt_bnt01">配置列表</div>
    <div class="gl_bnt_nav01" style="border-bottom:none;">
        <input name="input2" type="button" class="gl_cx_bnt04" id="mutilBtn" value="撤 销"/>
    </div>
    <table class="gl_table_a02" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <th width="25"><input type="checkbox" name="checkbox"/></th>
            <th>日期</th>
            <th>星期</th>
            <th>原类型</th>
            <th>现类型</th>
            <th>配置人</th>
            <th>配置时间</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${ITEMPAGE.items}" var="holidayEntity">
            <tr>
                <td><input type="checkbox" name="checkbox3" value="${holidayEntity.id}"/></td>
                <td>${holidayEntity.id}</td>
                <td><fmt:formatDate value="${holidayEntity.targetDate}" pattern="EEEE"/></td>
                <td>${holidayEntity.dayType eq "holiday" ? "工作日" : "休息日"}</td>
                <td>${holidayEntity.dayType eq "holiday" ? "休息日" : "工作日"}</td>
                <td>${holidayEntity.lastUpdateUser}</td>
                <td><fmt:formatDate value="${holidayEntity.lastUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><input name="${holidayEntity.id}" type="button" class="gl_cx_bnt04" value="撤 销" /></td>
            </tr>
        </c:forEach>
    </table>
    <div class="pageBox">
        <jsp:include flush="true" page="/public/include/navigate2.jsp"></jsp:include>
    </div>
    </form:form>
</div>
<form id="actionForm" method="post">
    <input type="hidden" name="ids" value=""/>
</form>
</body>
<script type="text/javascript" src="/SRMC/rmpb/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/SRMC/rmpb/js/list-common.js"></script>
<script src="/SRMC/rmpb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
    $(function(){

        var Pageable = {
            settings:{},
            init:function(_settins){
                settings = _settins;
                var _firstCheckbox = _settins.firstCheckbox;
                var _listCheckBox = _settins.listCheckBox;
                var checkTotal = 0;
                _firstCheckbox.bind("click",function(){
                    if(this.checked) {
                        _listCheckBox.each(function(){
                            this.checked = true;
                        });
                        checkTotal = _listCheckBox.length;
                    } else {
                        _listCheckBox.each(function(){
                            this.checked = false;
                        });
                        checkTotal = 0;
                    }
                });
                _listCheckBox.bind("click",function(){
                    if(!this.checked) {
                        checkTotal-=1;
                    } else {
                        checkTotal+=1;
                    }
                    if(checkTotal == _listCheckBox.length) {
                        _firstCheckbox[0].checked = true;
                    } else {
                        _firstCheckbox[0].checked = false;
                    }
                });

            },
            getSelectedValue:function(){
                var selectedVallue = [];
                settings.listCheckBox.filter(":checked").each(function(){
                    selectedVallue.push(this.value);
                });
                return selectedVallue;
            }
        };

        $("#showBtn").bind("click",function(){
            var me = $(this);
            var searchTable = $("#searchTable");
            me.toggleClass(function(index,className){
                if(className == "gl_cx_bnt01b"){
                    me.val("收起查询");
                    searchTable.show();
                } else if(className == "gl_cx_bnt01") {
                    me.val("展开查询");
                    searchTable.hide();
                }
                return "gl_cx_bnt01b gl_cx_bnt01";
            });



        });

        Pageable.init({
            firstCheckbox:$(".gl_table_a02 :checkbox:eq(0)"),
            listCheckBox:$(".gl_table_a02 :checkbox:gt(0)")
        });


        $("#mutilBtn").bind("click",function(){
            if(confirm("您确定撤销？")) {
                var selectArray = Pageable.getSelectedValue();
                if(selectArray.length == 0) {
                    alert("请选择至少一条记录");
                    return ;
                }
                var actionForm = $("#actionForm");
                actionForm.attr("action","${ctx}/sys/holidaymanager/remove");
                actionForm.find("[name=ids]").val(selectArray.join(","));
                actionForm.submit();
            }
        });

        $(".gl_table_a02 tr:gt(0) :button").bind("click",function(){
            if(confirm("您确定撤销？")) {
                var me = $(this);
                var actionForm = $("#actionForm");
                actionForm.attr("action","${ctx}/sys/holidaymanager/remove");
                actionForm.find("[name=ids]").val(me.attr("name"));
                actionForm.submit();
            }
        });

        $("#resetBtn").bind("click",function(){
            $("#searchFormTable input,select").val("");
        });
    });
</script>
</html>
