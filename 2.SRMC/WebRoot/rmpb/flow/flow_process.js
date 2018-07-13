/**
 * 流程处理方式1
 */
(function($){
    var root={},
        setting = {
            processModeTd:"",//处理方式td的id
            processModeObj:"",//处理方式TD对象
            processActorTd:"",//处理人td的id
            processActorObj:"",//处理人的TD对象
            processModes:[],
            actorUrl:"",//处理人获取地址
            searchUserUrl:"",//建议查询人员地址
            adapterWorkorderId:"",//工单编号
            selectName:"ApproveUsersDiv",//处理人select名字
            selectedUserInputName:"userId",//选择的处理人的隐藏域的input的name
            processModeRadioName:"transitionRadio",//处理方式radio名字
            copyToObj:"",//抄送人td对象
            processModeCallBack:"",
            processNoActor:[]//不要处理人的节点
        },
        data = {
            flowModeValue:"",
            flowModeLabel:"",
            flowModeStatus:"",
            flowModeKey:"",
            flowModeChecked:false
        },
        _cache={
            suggestInput:"",
            selectedUserSpan:"",
            copyToSelectedUserSpan:"",
            checkedProcessMode:""
        },
        _actor = {
            userId:"",
            userName:""
        },
        _consts = {
            fenfa:"2",
            huiqian:"3",
            pingzhuan:"4"
        },
        event = {
            bindEvent:function(cSetting){
                cSetting.processModeObj.find(":radio").bind("click",cSetting,view.fillActor);
            },
            bindSelectedUserBtn:function(){
                _cache.selectedUserSpan.find(":button").bind("click",function(){
                    var me = this;
                    $(this).parent().remove();
                });
            },
            bindCopyToSelectedUserBtn:function(){
                _cache.copyToSelectedUserSpan.find(":button").bind("click",function(){
                    var me = this;
                    $(this).parent().remove();
                });
            }
        },
        tools = {
            getActor:function(setting,processMode){
                var _actors=[],params = {
                    workorderId:setting.adapterWorkorderId,
                    transitionId:processMode.flowModeValue
                };

                $.ajax({
                    type:"POST",
                    url:setting.actorUrl,
                    data:params,
                    dataType:"JSON",
                    async:false,
                    beforeSend:function(){
                        view.loading(setting.processActorObj,"加载中...");
                    },
                    success:function(data){
                        _actors = data;
                    },
                    error:function(){
                        _actors = "fail";
                        alert("联系人获取失败，请检查网络异常或稍后再试！");
                    }

                });
                return _actors;
            },
            isArray: function(arr) {
                return Object.prototype.toString.apply(arr) === "[object Array]";
            },
            clone: function (obj){
                if (obj === null) return null;
                var o = tools.isArray(obj) ? [] : {};
                for(var i in obj){
                    o[i] = (obj[i] instanceof Date) ? new Date(obj[i].getTime()) : (typeof obj[i] === "object" ? arguments.callee(obj[i]) : obj[i]);
                }
                return o;
            }
        },
        flowTools = {
            checkNeedActor:function(setting,nodeId){
                var isNeed = true;
                var i = setting.processNoActor.length;
                while(i--){
                    if(setting.processNoActor[i] == nodeId) {
                        isNeed = false;
                        break;
                    }
                }
                return isNeed;
            },
            createCopyToSuggestInput:function(setting){
                if(_cache.suggestInput == ""){
                    _cache.suggestInput = $("<input type=\"text\" class=\"gl_text01_sp\" style=\"width:216px\">");
                }

                var copyToInput = _cache.suggestInput.clone();


                setting.copyToObj.html("加载中..");
                copyToInput.autocomplete({
                    source: function( request, response ) {
                        jQuery.ajax({
                            url: setting.searchUserUrl,
                            dataType: "json",
                            data: {
                                userValue: request.term
                            },
                            type: "POST",
                            success: function(data) {
                                if(data!=null){
                                    response(jQuery.map(data, function( item ) {
                                        return {
                                            value:item[0].userName+" — "+item[0].account+" — "+item[1].orgName,
                                            userName:item[0].userName,
                                            userId:item[0].userId,
                                            account:item[0].account,
                                            orgName:item[1].orgName
                                        }
                                    }));
                                }else{
                                    return false;
                                }
                            }
                        });
                    },
                    minLength: 1,
                    select: function( event, ui ) {
                        flowTools.createCopyToSelectUser(setting,true,ui);
                        copyToInput.val("");
                        return false;
                    }
                });
                setting.copyToObj.html("");
                copyToInput.appendTo(setting.copyToObj);
            },
            createSuggestInput:function(setting,isMuti){
                if(_cache.suggestInput == ""){
                    _cache.suggestInput = $("<input type=\"text\" class=\"gl_text01_sp\" style=\"width:216px\">");
                }

                setting.processActorObj.html("加载中..");
                _cache.suggestInput.autocomplete({
                    source: function( request, response ) {
                        jQuery.ajax({
                            url: setting.searchUserUrl,
                            dataType: "json",
                            data: {
                                userValue: request.term
                            },
                            type: "POST",
                            success: function(data) {
                                if(data!=null){
                                    response(jQuery.map(data, function( item ) {
                                        return {
                                            value:item[0].userName+" — "+item[0].account+" — "+item[1].orgName,
                                            userName:item[0].userName,
                                            userId:item[0].userId,
                                            account:item[0].account,
                                            orgName:item[1].orgName
                                        }
                                    }));
                                }else{
                                    return false;
                                }
                            }
                        });
                    },
                    minLength: 1,
                    select: function( event, ui ) {
                        flowTools.createSelectUser(setting,isMuti,ui);
                        _cache.suggestInput.val("");
                        return false;
                    }
                });
                setting.processActorObj.html("");
                _cache.suggestInput.appendTo(setting.processActorObj);

                _cache.selectedUserSpan = "";
            },
            createSelectUser:function(setting,isMuti,ui){
                if(_cache.selectedUserSpan == ""){
                    _cache.selectedUserSpan = $("<span>");
                }
                if(!isMuti) {
                    _cache.selectedUserSpan.html(flowTools.appendUserBtn(ui).join(''));
                }else{
                    if(_cache.selectedUserSpan.find(":hidden[value="+ui.item.userId+"]").length == 0){
                        _cache.selectedUserSpan.append(flowTools.appendUserBtn(ui).join(''));
                    }
                }

                //绑定按钮删除事件
                event.bindSelectedUserBtn();

                _cache.selectedUserSpan.appendTo(setting.processActorObj);

            },
            createCopyToSelectUser:function(setting,isMuti,ui){
                if(_cache.copyToSelectedUserSpan==""){
                    _cache.copyToSelectedUserSpan = $("<span>");
                }
                if(!isMuti) {
                    _cache.copyToSelectedUserSpan.html(flowTools.appendUserBtn(ui).join(''));
                }else{
                    if(_cache.copyToSelectedUserSpan.find(":hidden[value="+ui.item.userId+"]").length == 0){
                        _cache.copyToSelectedUserSpan.append(flowTools.appendUserBtn(ui).join(''));
                    }
                }

                //绑定按钮删除事件
                event.bindCopyToSelectedUserBtn();

                _cache.copyToSelectedUserSpan.appendTo(setting.copyToObj);

            },
            appendUserBtn:function(ui){
                var html = [];
                html.push("<span>")
                html.push("<input type=\"hidden\" name=\"",setting.selectedUserInputName,"\" value=\"",ui.item.userId,"\"/>");
                html.push("<input type=\"button\" class=\"wd_cx_bnt01\" value=\"",ui.item.userName,"\"/>");
                html.push("&nbsp;</span>");
                return html;
            }
        };
    view = {
        loading:function(obj,loadmessage){
            obj.html(loadmessage);
        },
        fillMode:function(cSetting,flowModes){
            var html = view.appendMode(flowModes);
            cSetting.processModeObj.html(html.join(''));
        },
        fillActor:function(eventObject){
            var processModeRadio = $(eventObject.target);
            var _processMode = root[processModeRadio.attr("id")];
            var cSetting = eventObject.data;


            var nextMsg = $(".sp_next_01");
            if(nextMsg.length > 0) {
                var firstElement = nextMsg.find(".orange_01");
                nextMsg.html("");
                firstElement.appendTo(nextMsg);
                nextMsg.append(_processMode.flowModeLabel);
            }

            //判断是否结束节点
            if(!flowTools.checkNeedActor(cSetting,_processMode.flowModeValue)){
                //结束节点，删除处理人行
                cSetting.processActorObj.parent().parent().hide();
                cSetting.processActorObj.html("");
            }else{
                cSetting.processActorObj.parent().parent().show();
                switch (_processMode.flowModeStatus){
                    case _consts.fenfa :
                        view.fenfa(cSetting,_processMode);
                        break;
                    case _consts.huiqian:
                        view.huiqian(cSetting,_processMode);
                        break;
                    case _consts.pingzhuan:
                        view.pingzhuan(cSetting,_processMode);
                        break;
                    default:
                        view.showActor(cSetting,_processMode);
                }
            }

            cSetting.processModeCallBack(cSetting,_processMode);
            processModeRadio.siblings(".yan_w_tab01_select").attr("class","yan_w_tab01_not_select");
            processModeRadio.attr("class","yan_w_tab01_select");
            _cache.checkedProcessMode = _processMode;
        },
        appendMode:function(flowModes){
            var html = [];
            for(var i=0;i<flowModes.length;i++) {
                var _data = tools.clone(data);
                $.extend(true,_data,flowModes[i]);

                var _flowModeKey = "pm_index_"+_data.flowModeValue;
                _data.flowModeKey = _flowModeKey;
                root[_flowModeKey] = _data;
                html.push("<input type=\"radio\" class=\"yan_w_tab01_not_select\" name=\"",setting.processModeRadioName,"\" value=\"",_data.flowModeValue,"\" id=\"",_data.flowModeKey,"\"/>");
                html.push("<label for=\"",_data.flowModeKey,"\">",_data.flowModeLabel,"</label>&nbsp;&nbsp;");
            }
            if(html.length == 0){
                html.push("暂无处理方式");
            }
            return html;
        },
        appendSelectActor:function(setting,actors){
            var selectHtml = [];
            var actor = tools.clone(_actor);
            for(var i=0;i<actors.length;i++){
                $.extend(true,actor,actors[i]);
                selectHtml.push("<option value=\"",actor.userId,"\">",actor.userName,"</option>");
            }
            setting.processActorObj.html("<select class=\"select_new02\" name=\""+setting.selectName+"\">"+selectHtml.join('')+"</select>");
        },
        appendShowActor:function(setting,actors){
            //分发
            alert("暂时没有分发");
        },
        appendInputActor:function(setting,isMuti){
            //平转和会签
            flowTools.createSuggestInput(setting,isMuti);
        },
        showActor:function(setting,processMode){
            var actorSelect = tools.getActor(setting,processMode);
            if(tools.isArray(actorSelect) && actorSelect.length > 0) {
                view.appendSelectActor(setting,actorSelect);
            } else if(actorSelect == "" || actorSelect.length == 0){
                setting.processActorObj.html("<font color=\"red\">未指定处理人</font>");
            } else if(actorSelect == "fail") {
                setting.processActorObj.html("<font color=\"red\">获取处理人失败</font>");
            } else {
                alert("未知原因");
            }
        },
        huiqian:function(setting,processMode){
            view.appendInputActor(setting,true);
        },
        pingzhuan:function(setting,processMode){
            view.appendInputActor(setting,false);
        },
        fenfa:function(setting,processMode){
            var actorSelect = tools.getActor(setting,processMode);
            if(tools.isArray(actorSelect) && actorSelect.length > 0) {
                view.appendSelectActor(setting,actorSelect);
            } else if(actorSelect == "" || actorSelect.length == 0){
                setting.processActorObj.html("<font color=\"red\">未指定处理人</font>");
            } else if(actorSelect == "fail") {
                setting.processActorObj.html("<font color=\"red\">获取处理人失败</font>");
            } else {
                alert("未知原因");
            }
            view.appendShowActor(setting,processMode);
        }
    };
    $.fn.flowProcess = {
        init:function(fSetting,flowModes){

            var settings = tools.clone(setting);
            $.extend(true,settings,fSetting);

            if(!tools.isArray(flowModes)){
                flowModes = [flowModes];
            }

            settings.processActorTd = settings.processActorObj.attr("id");
            settings.processModeTd = settings.processModeObj.attr("id");

            view.loading(settings.processModeObj,"加载中...");
            view.loading(settings.processActorObj,"加载中...");

            view.fillMode(settings,flowModes);//填充处理方式

            if(settings.copyToObj != ""){
                flowTools.createCopyToSuggestInput(settings);//创建抄送人相关信息
            }
            event.bindEvent(settings);

            //默认选择第一种方式
            var processModes = settings.processModeObj.find(":radio"),passed_index = -1;
            if(processModes.length > 0) {
                for(var i=0;i<flowModes.length;i++){
                    if(flowModes[i].flowModeChecked){
                        passed_index = i;
                        break;
                    }
                }
                if(passed_index != -1) {
                    processModes.eq(i).click();
                } else {
                    processModes.eq(0).click();
                }

            }

            var returnFlowTools = {
                /**
                 * 检查是否已选处理人[不用选的直接返回true，例如结束处理方式]
                 * @returns {boolean}
                 */
                checkActor:function(){
                    if(settings.processActorObj.find("input[type=hidden],select").length == 0 && flowTools.checkNeedActor(settings,_cache.checkedProcessMode.flowModeValue)){
                        return false;
                    }
                    return true;
                },
                /**
                 * 获取当前处理方式是否平转
                 * @returns {boolean}
                 */
                isPinZhuan:function(){
                    return _cache.checkedProcessMode.flowModeStatus == _consts.pingzhuan;
                },
                /**
                 * 获取当前处理方式为会签
                 * @returns {boolean}
                 */
                isHuiQian:function(){
                    return _cache.checkedProcessMode.flowModeStatus == _consts.huiqian;
                },
                /**
                 * 获取已选择的处理人的userID数组
                 * @returns {Array}
                 */
                getActor:function(){
                    var actor_ids = [];
                    settings.processActorObj.find("input[type=hidden],select").each(function(){
                        actor_ids.push($(this).val());
                    });
                    return actor_ids;
                },
                /**
                 * 获取已选择的抄送者的userID数组
                 * @returns {Array}
                 */
                getCopyToUser:function(){
                    var copy_to_user_id = [];
                    settings.copyToObj.find("input[type=hidden]").each(function(){
                        copy_to_user_id.push($(this).val());
                    });
                    return copy_to_user_id;
                }
            };
            return returnFlowTools;
        }
    };
})(jQuery);