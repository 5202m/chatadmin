/**
 * 短信配置管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年10月29日 <BR>
 * Description : <BR>
 * <p>
 * 
 * </p>
 */
var Syllabus = {
	gridId : 'syllabus_datagrid',
	daysCN : ['星期天','星期一','星期二','星期三','星期四','星期五','星期六'],
	chatGroups : {},
	
	init : function(){
		this.setEvent();
		
		$("#syllabus_queryForm select[name='chatGroupId'] option").each(function(){
			Syllabus.chatGroups[$(this).val()] = $.trim($(this).text());
		});
		$("#syllabus_groupId_select").trigger("change");
	},
	
	/** 绑定事件 */
	setEvent:function(){
		/**查询*/
		$("#syllabus_groupId_select").on("change",function(){
			var loc_chatGroupId = $("#syllabus_queryForm select[name='chatGroupId'] :selected");
			Syllabus.getSyllabus(loc_chatGroupId.attr("group_type"), loc_chatGroupId.attr("group_id"));
		});
	},

    /**
     * 设置编辑事件
     */
    setEditEvent : function() {
        /**
         * 添加
         */
        $("#panel_editSyllabus table:first a.ope-add").bind("click", function () {
            Syllabus.addTimeBucket($("#panel_editSyllabus table:first"), null, null);
        });

        /**
         * 移除
         */
        $("#panel_editSyllabus table:first a.ope-remove").bind("click", function () {
            $(this).parents("tbody:first").remove();
        });

        /**
         * 上移
         */
        $("#panel_editSyllabus table:first a.ope-up").bind("click", function () {
            var loc_tbody = $(this).parents("tbody:first");
            var loc_before = loc_tbody.prev();
            if (loc_before.is("tbody:visible")) {
                loc_before.before(loc_tbody);
            }
        });

        /**
         * 下移
         */
        $("#panel_editSyllabus table:first a.ope-down").bind("click", function () {
            var loc_tbody = $(this).parents("tbody:first");
            var loc_next = loc_tbody.next();
            if (loc_next.is("tbody:visible")) {
                loc_next.next(loc_tbody);
            }
        });

        /**
         * 休市或无效(全天)
         */
        $("#panel_editSyllabus thead select[name='status']").each(function(index){
        	$(this).bind("change", index, function (event) {
        		var loc_this = $(this);
                var loc_index = event.data;
                var loc_panel = loc_this.parents("table:first");
                var loc_size = loc_panel.find("tbody").size();
                if((loc_this.val() !== "1")){
                	//休市或无效
                	var loc_statuses = loc_panel.find("tbody select[name='status']");
                	var loc_status = null;
                	for(var i = 0; i < loc_size; i++){
                		loc_status = loc_statuses.eq(i * 7 + loc_index);
                		loc_status.prop("disabled", true);
                		loc_status.trigger("change", true);
                	}
                }else{
                	//有效
                	var loc_statuses = loc_panel.find("tbody select[name='status']");
                	var loc_status = null;
                	for(var i = 0; i < loc_size; i++){
                		loc_status = loc_statuses.eq(i * 7 + loc_index);
                		loc_status.prop("disabled", false);
                		loc_status.trigger("change");
                	}
                }
            });
        });

        /**
         * 休市(单时段)
         */
        $("#panel_editSyllabus tbody select[name='lecturerSelect']").each(function(){
        	$(this).bind("change", function () {
        		var loc_lecturer = $(this).val();
        		if(loc_lecturer)
        		{
        			var loc_input = $(this).siblings("textarea[name='lecturer']");
        			loc_input.val(loc_input.val() + '&' + loc_lecturer);
        		}
            });
        });
        
        /**
         * 选择讲师
         */
        $("#panel_editSyllabus tbody select[name='status']").each(function(index){
        	$(this).bind("change", index, function (event, status) {
        		var loc_this = $(this);
                var loc_index = event.data;
                var loc_disable = status || (loc_this.val() === "0");
                var loc_panel = loc_this.parents("tbody:first"); 
                loc_panel.find("textarea[name='lecturer']").eq(loc_index).prop("disabled", loc_disable);
                loc_panel.find("select[name='lecturerSelect']").eq(loc_index).prop("disabled", loc_disable);
                loc_panel.find("textarea[name='title']").eq(loc_index).prop("disabled", loc_disable);
            });
        });
    },
	
	/**
	 * 获取房间名称
	 * @param chatGroupType
	 * @param chatGroupId
	 * @returns
	 */
	getGroupName : function(chatGroupType, chatGroupId)
	{
		var loc_result = Syllabus.chatGroups[chatGroupType + ","];
		if(!!chatGroupId)
		{
			loc_result += "-" + Syllabus.chatGroups[chatGroupType + "," + chatGroupId];
		}
		return loc_result; 
	},
	
	/**
	 * 获取课程表记录
	 * @param chatGroupType
	 * @param chatGroupId
	 */
	getSyllabus : function(chatGroupType, chatGroupId)
	{
		$("#panel_viewSyllabus table").html("");
		goldOfficeUtils.ajax({
			url : basePath +'/chatSyllabusController/datagrid.do?chatGroupType=' + chatGroupType + '&chatGroupId=' + chatGroupId,
			type : 'get',
			success : function(data){
				var loc_syllabus = data;
				$("#panel_viewSyllabus").data("syllabus", loc_syllabus);
				$("#panel_viewSyllabus span:first").html(Syllabus.getGroupName(loc_syllabus.groupType, loc_syllabus.groupId));
				if(loc_syllabus && loc_syllabus.courses)
				{
					var loc_nowTime = new Date();
					var loc_currTime = {
							day : loc_nowTime.getDay(),
							timeLong : loc_nowTime.getTime(),
							time : (loc_nowTime.getHours() < 10 ? "0" : "") + loc_nowTime.getHours() + ":"
							+ (loc_nowTime.getMinutes() < 10 ? "0" : "") + loc_nowTime.getMinutes()
					};
					var loc_style = 1;
					if(loc_syllabus.groupType === "wechat" || loc_syllabus.groupType === "fxchat")
					{
						loc_style = 2;
					}
					var loc_syllabusPanel = $("#panel_viewSyllabus div.syllabus");
					loc_syllabusPanel.html(Syllabus.viewSyllabus(loc_syllabus.courses, loc_currTime, loc_style));
                    if(loc_style == 1)
                    {
                    	loc_syllabusPanel.find(".sy_nav a").bind("click", function(){
                            var loc_this = $(this);
                            loc_this.siblings(".active").removeClass("active");
                            loc_this.addClass("active");
                            var loc_day = loc_this.attr("d");
                            var loc_panel = loc_this.parent().next();
                            loc_panel.find("tbody:visible").hide();
                            loc_panel.find("tbody[d='" + loc_day + "']").show();
                        });
                    	loc_syllabusPanel.find(".sy_nav a.active").trigger("click");
                    };
				}
				else
				{
					var loc_html = '<table cellpadding="0" cellspacing="1" border="0" class="tableForm_L" style="width: 730px;">';
					loc_html += '<tr><th>未设置任何课程表，请点击修改按钮！</th></tr>';
					loc_html += '</table>';
					$("#panel_viewSyllabus div:last").html(loc_html);
				}
			}
		});
	},
	
	/**
	 * 修改
	 * @param item
	 */
	edit : function(item)
	{
		var loc_syllabus = $("#panel_viewSyllabus").data("syllabus");
		var loc_url = formatUrl(basePath + '/chatSyllabusController/preEdit.do?chatGroupType=' + loc_syllabus.groupType + '&chatGroupId=' + loc_syllabus.groupId);
		goldOfficeUtils.openEditorDialog({
			title : '编辑课程表',
			width : 1050,
			height : 560,
			href : loc_url,
			iconCls : 'ope-edit',
			handler : function(){
                $("#form_editSyllabus input[name='courses']").val(Syllabus.getCoursesJson($("#panel_editSyllabus table:first")));
				goldOfficeUtils.ajaxSubmitForm({
					url : formatUrl(basePath + '/chatSyllabusController/edit.do'),
					formId : 'form_editSyllabus',
					onSuccess : function(data){   //提交成功后处理
						var d = $.parseJSON(data);
						if (d.code === "OK") {
							$("#myWindow").dialog("close");
                            Syllabus.refresh();
							$.messager.alert($.i18n.prop("common.operate.tips"),'保存成功!','info');
						}else{
							$.messager.alert($.i18n.prop("common.operate.tips"),'保存失败','error');
						}
					}
				});
			},
			onLoad : function(){
				var loc_panel = $("#panel_editSyllabus");
				loc_panel.find("span:first").text(Syllabus.getGroupName(loc_syllabus.groupType, loc_syllabus.groupId));
                Syllabus.setEditEvent();
                var loc_courses = $("#form_editSyllabus input[name='courses']").val();
                if(!loc_courses){
                    loc_courses = {days : [{day : 1, status : 1},{day : 2, status : 1},{day : 3, status : 1},{day : 4, status : 1},{day : 5, status : 1}], timeBuckets : []};
                }else{
                    loc_courses = JSON.parse(loc_courses);
                }
                var loc_days = loc_courses.days || [];
                var loc_daysObj = {};
                for(var i = 0, lenI = loc_days.length; i < lenI; i++)
                {
                    loc_daysObj[loc_days[i].day + ""] = loc_days[i];
                }
                loc_panel.find("thead th:gt(0)").each(function(){
                    var loc_this = $(this);
                    var loc_day = loc_this.find("input[name='day']").val();
                    var loc_status = loc_daysObj.hasOwnProperty(loc_day) ? loc_daysObj[loc_day].status : 2;
                    var loc_item = loc_this.find("select[name='status']");
                    loc_item.val(loc_status);
                    if(loc_status != 1)
                    {
                        loc_item.trigger("change");
                    }
                });

                if(loc_courses.hasOwnProperty("timeBuckets"))
                {
                    var loc_timeBuckets = loc_courses.timeBuckets;
                    for(var i = 0, lenI = !loc_timeBuckets ? 0 : loc_timeBuckets.length; i < lenI; i++)
                    {
                        Syllabus.addTimeBucket(loc_panel.find("table"), loc_timeBuckets[i], loc_courses.days);
                    }
                }
			}
		});
	},
	
	/**
	 * 刷新
	 */
	refresh : function(){
		var loc_syllabus = $("#panel_viewSyllabus").data("syllabus");
		Syllabus.getSyllabus(loc_syllabus.groupType, loc_syllabus.groupId);
	},

    /**
     * 获取课程json字符串
     * {days : [{day: Integer, status : Integer}], timeBuckets : [{startTime : String, endTime : String, course : [{lecturer : String, title : String, status : Integer}]}]}
     * @param $panel
     */
    getCoursesJson : function($panel)
    {
        //星期
        var loc_days = [];
        var loc_daysIndex = [];
        $panel.find("thead th:gt(0)").each(function(index){
            var loc_this = $(this);
            var loc_val = {
                day : loc_this.find("input[name='day']").val(),
                status : loc_this.find("select[name='status']").val()
            };
            //仅记录有效或者休市的课程
            if(loc_val.status !== "2"){
                loc_daysIndex.push(index);
                loc_days.push({
                    day : parseInt(loc_val.day, 10),
                    status : parseInt(loc_val.status, 10)
                });
            }
        });

        //课表
        var lenI = loc_daysIndex.length;
        var loc_timeBuckets = [];
        var loc_course = null;
        var loc_courses = null;
        var loc_index = 0;
        $panel.find("tbody:visible").each(function(){
            var loc_this = $(this);
            loc_course = {};
            loc_course.startTime = loc_this.find("input[name='startTime']").timespinner('getValue');
            loc_course.endTime = loc_this.find("input[name='endTime']").timespinner('getValue');
            loc_courses = [];
            for(var i = 0; i < lenI; i++)
            {
                loc_index = loc_daysIndex[i];
                loc_courses.push({
                    status : parseInt(loc_this.find("select[name='status']").eq(loc_index).val(), 10),
                    lecturer : loc_this.find("textarea[name='lecturer']").eq(loc_index).val(),
                    title : loc_this.find("textarea[name='title']").eq(loc_index).val()
                });
            }
            loc_course.course = loc_courses;
            loc_timeBuckets.push(loc_course);
        });
        var loc_result =
        {
            days : loc_days,
            timeBuckets : loc_timeBuckets
        };
        return JSON.stringify(loc_result);
    },
	
	/**
	 * 增加一个时间段
	 * @param $panel
	 * @param timeBucket {{startTime:String, endTime:String, course:[{lecturer : String, title : String, status : Integer}]}}
	 * @param days [{day: Integer, status : Integer}]
	 */
	addTimeBucket : function($panel, timeBucket, days){
		if(!timeBucket)
		{
			timeBucket =
			{
				startTime : "00:00",
				endTime : "00:00",
				course : []
			};
		}
		var loc_newTimeBucket = $panel.find("tbody:hidden").clone(true);
        var loc_item = null;

        //时间段
        loc_item = loc_newTimeBucket.find("input[name='startTime']");
        loc_item.val(timeBucket.startTime);
        loc_item.timespinner({});
        loc_item = loc_newTimeBucket.find("input[name='endTime']");
        loc_item.val(timeBucket.endTime);
        loc_item.timespinner({});

        //课程
        var loc_courses = {};
        for(var i = 0, lenI = !days ? 0 : days.length; i < lenI; i++)
        {
            loc_courses[days[i].day + ""] = timeBucket.course[i];
        }
        loc_newTimeBucket.find("td").each(function(index){
            var loc_day = ((index + 1) % 7) + "";
            if(loc_courses.hasOwnProperty(loc_day)){
                var loc_course = loc_courses[loc_day];
                if(index < 7){
                    loc_item = $(this).find("select[name='status']");
                    loc_item.val(loc_course.status);
                    if(loc_course.status == "0"){
                        loc_item.trigger("change", false);
                    }
                    $(this).find("textarea[name='lecturer']").val(loc_course.lecturer);
                }else{
                    $(this).find("textarea[name='title']").val(loc_course.title);
                }
            }
        });
        
        $panel.append(loc_newTimeBucket);
        loc_newTimeBucket.show();
	},

    /**
     * 格式化显示课程安排表
     * @param syllabus {{days : [{day: Integer, status : Integer}], timeBuckets : [{startTime : String, endTime : String, course : [{lecturer : String, title : String, status : Integer}]}]}}
     * @param currTime {{day : Integer, time : String}}
     * @param style 类型 1-直播间 2-微解盘
     */
	viewSyllabus : function(syllabus, currTime, style){
        var loc_constants = {
            dayCN : ['星期天','星期一','星期二','星期三','星期四','星期五','星期六'],
            indexCN : ['第一节','第二节','第三节','第四节','第五节','第六节','第七节','第八节'],
            courseCls : ['prev', 'ing', 'next'],
            tableCls : "tableForm_L"
        };
        //计算时间状态,返回对应的样式
        var loc_timeClsFunc = function(day, startTime, endTime, currTime, notCheckTime){
            var loc_index = 0;
            var tempDay = (day + 6) % 7; //将1,2,3,4,5,6,0转化为0,1,2,3,4,5,6
            var currTempDay = (currTime.day + 6) % 7;
            if(tempDay > currTempDay){
                loc_index = 2;
            }else if(tempDay < currTempDay){
                loc_index = 0;
            }else{
                if(notCheckTime){
                    loc_index = 1;
                }else if(endTime <= currTime.time){
                    loc_index = 0;
                }else if(startTime > currTime.time){
                    loc_index = 2;
                }else{
                    loc_index = 1;
                }
            }
            return loc_constants.courseCls[loc_index];
        };

        var loc_html = [];
        try
        {
            if(syllabus)
            {
                var loc_courses = JSON.parse(syllabus);
                var loc_dayLen = !loc_courses.days ? 0 : loc_courses.days.length;
                if(loc_dayLen > 0)
                {
                    if(style === 1)
                    {
                        loc_html.push('<div class="sy_panel">');
                        //星期（头部）
                        var loc_startDateTime = currTime.timeLong - 86400000 * ((currTime.day + 6) % 7);
                        var loc_date = null;
                        var loc_dateStr = null;
                        var loc_activeCls = null;
                        loc_html.push('<div class="sy_nav">');
                        var loc_width = ' style="width:' + (100 / loc_dayLen) + '%;"'
                        for(var i = 0; i < loc_dayLen; i++)
                        {
                            loc_date = new Date(loc_startDateTime + ((loc_courses.days[i].day + 6) % 7) * 86400000);
                            loc_dateStr = loc_date.getFullYear() + "-"
                                        + (loc_date.getMonth() < 9 ? "0" : "") + (loc_date.getMonth() + 1) + "-"
                                        + (loc_date.getDate() < 10 ? "0" : "") + loc_date.getDate();
                            loc_activeCls = (loc_courses.days[i].day == currTime.day) ? ' class="active"' : "";
                            loc_html.push('<a href="javascript:void(0)" d="' + loc_courses.days[i].day + '"' + loc_activeCls + loc_width + '>' + loc_constants.dayCN[loc_courses.days[i].day] + '<br/><span>' + loc_dateStr + '</span></a>');
                        }
                        loc_html.push('</div>');
                        //课程
                        loc_html.push('<div class="sy_cont"><div>');
                        loc_html.push('<table cellpadding="0" cellspacing="1" border="0" class="' + loc_constants.tableCls + '">');
                        var loc_timeBucket = null;
                        var loc_timeCls = null;
                        for(var i = 0; i < loc_dayLen; i++)
                        {
                            loc_html.push('<tbody d="' + loc_courses.days[i].day + '">');
                            if(loc_courses.days[i].status == 0)
                            {
                                //全天休市
                                loc_timeCls = loc_timeClsFunc(loc_courses.days[i].day, null, null, currTime, true);
                                loc_html.push('<tr class="' + loc_timeCls + '" width="100%"><td>休市</td></tr>');
                            }
                            else
                            {
                                for(var j = 0, lenJ = loc_courses.timeBuckets.length; j < lenJ; j++)
                                {
                                    loc_timeBucket = loc_courses.timeBuckets[j];
                                    loc_timeCls = loc_timeClsFunc(loc_courses.days[i].day, loc_timeBucket.startTime, loc_timeBucket.endTime, currTime, false);
                                    loc_html.push('<tr class="' + loc_timeCls + '">');
                                    loc_html.push('<td width="25%">' + loc_timeBucket.startTime + '-' + loc_timeBucket.endTime + '</td>');
                                    if(loc_timeBucket.course[i].status == 0)
                                    {
                                        loc_html.push('<td colspan="2" width="75%">休市</td>');
                                    }
                                    else
                                    {
                                        loc_html.push('<td width="25%">' + loc_timeBucket.course[i].lecturer + '</td>');
                                        loc_html.push('<td width="50%">' + loc_timeBucket.course[i].title + '</td>');
                                    }
                                    loc_html.push('</tr>');
                                }
                            }
                            loc_html.push('</tbody>');
                        }
                        loc_html.push('</table>');
                        loc_html.push('</div></div>');
                        loc_html.push('</div>');
                    }
                    else if(style === 2)
                    {
                        loc_html.push('<table cellpadding="0" cellspacing="1" border="0" class="' + loc_constants.tableCls + '">');
                        //头部
                        loc_html.push('<tr>');
                        loc_html.push('<th>星期\\节次</th>');
                        var lenJ = !loc_courses.timeBuckets ? 0 : loc_courses.timeBuckets.length;
                        var loc_timeBucket = null;
                        var loc_timeCls = null;
                        for(var j = 0; j < lenJ; j++)
                        {
                            loc_timeBucket = loc_courses.timeBuckets[j];
                            loc_html.push('<th>' + loc_constants.indexCN[j] + "<br><span>(" + loc_timeBucket.startTime + "-" + loc_timeBucket.endTime + ')</span></th>');
                        }
                        loc_html.push('</tr>');
                        //课程
                        var loc_day = null;
                        for(var i = 0, lenI = !loc_courses.days ? 0 : loc_courses.days.length; i < lenI; i++)
                        {
                            loc_day = loc_courses.days[i];
                            loc_html.push('<tr>');
                            loc_html.push('<th>' + loc_constants.dayCN[loc_day.day] + '</th>');
                            if(loc_day.status == 0)
                            {
                                //全天休市
                                loc_timeCls = loc_timeClsFunc(loc_day.day, null, null, currTime, true);
                                loc_html.push('<td class="' + loc_timeCls + '" colspan="' + lenJ + '">休市</td>');
                            }
                            else
                            {
                                for(var j = 0; j < lenJ; j++){
                                    loc_timeBucket = loc_courses.timeBuckets[j];
                                    loc_timeCls = loc_timeClsFunc(loc_day.day, loc_timeBucket.startTime, loc_timeBucket.endTime, currTime, false);
                                    loc_html.push('<td class="' + loc_timeCls + '">' + (loc_timeBucket.status == 0 ? "休市" : loc_timeBucket.course[j].lecturer) + '</td>');
                                }
                            }
                            loc_html.push('</tr>');
                        }
                        loc_html.push('</table>');
                    }
                }
            }
        }
        catch(e1)
        {
            console.error("formatSyllabus->"+e1);
            return "";
        }
        return loc_html.join("");
    }
};
		
//初始化
$(function() {
	Syllabus.init();
});