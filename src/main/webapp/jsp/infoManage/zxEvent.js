/**
 * 财经日历管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2016<BR>
 * Author : Dick.guo <BR>
 * Date : 2016年3月23日 <BR>
 * Description : <BR>
 * <p>
 * 
 * </p>
 */
var ZxEvent = {
	gridId : 'zxEvent_datagrid',
	dicts : {
		importance : {
			"1" : "低",
			"2" : "中",
			"3" : "高"
		}
	},
	init : function(){
		this.initDicts($("#zxEvent_type"));
		this.initDicts($("#zxEvent_dataType"));
		this.initDicts($("#zxEvent_valid"));
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : ZxEvent.gridId,
			idField:"eventId",
			sortName : 'date',
			sortOrder : "desc",
			url : basePath+'/zxEventController/datagrid.do',
			queryParams : {
				valid : 1
			},
			columns : [[
			    {title : $.i18n.prop("common.operate"), field:'eventId', formatter: function(value, rowData, rowIndex){
			    	$("#zxEvent_datagrid_rowOperation input").val(value);
			    	return $("#zxEvent_datagrid_rowOperation").html();
			    }},
	            {title : "事件类型",field : 'type',formatter : function(value, rowData, rowIndex) {
	            	return ZxEvent.formatByDicts("type", value);
				}},
	            {title : "标题",field : 'title', width:400},
	            /*{title : "内容",field : 'content'},*/
	            {title : "时间",field : 'date',formatter : function(value, rowData, rowIndex) {
	            	return value + "&nbsp;" + rowData["time"];
				}},
				{title : "重要指数",field : 'importanceLevel',sortable : true,formatter : function(value, rowData, rowIndex) {
					value = value || 0;
					var html = [];
					for(var i = 0; i < 5; i++){
						html.push(i < value ? "★" : "☆");
					}
					return html.join("");
				}},
				/*{title : "重要性",field : 'importance',formatter : function(value, rowData, rowIndex) {
	            	return ZxEvent.formatByDicts("importance", value);
				}},*/
	            {title : "国家",field : 'country'},
	            {title : "地区",field : 'region'},
	            /*{title : "链接",field : 'link'},*/
	            {title : "产品类型",field : 'dataType',formatter : function(value, rowData, rowIndex) {
	            	return ZxEvent.formatByDicts("dataType", value);
				}},
				{title : "有效性",field : 'valid',formatter : function(value, rowData, rowIndex) {
					return ZxEvent.formatByDicts("valid", value + "");
				}},
				{title : '修改时间',field : 'updateDate',sortable : true,formatter : function(value, rowData, rowIndex) {
					return value ? timeObjectUtil.longMsTimeConvertToDateTime(value) : '';
				}}
			]],
			toolbar : '#zxEvent_datagrid_toolbar'
		});
	},
	
	/** 绑定事件 */
	setEvent:function(){
		/**查询*/
		$("#zxEvent_queryForm_search").on("click",function(){
			var queryParams = $('#'+ZxEvent.gridId).datagrid('options').queryParams;
			$("#zxEvent_queryForm select").add("#zxEvent_queryForm input").each(function(){
				queryParams[$(this).attr("name")] = $(this).val();
			});
			$('#'+ZxEvent.gridId).datagrid({
				url : basePath+'/zxEventController/datagrid.do',
				pageNumber : 1
			});
		});
		
		/**重置*/
		$("#zxEvent_queryForm_reset").on("click",function(){
			$("#zxEvent_queryForm")[0].reset();
		});
	},
	
	/**
	 * 初始化字典
	 * @param $select
	 */
	initDicts : function($select){
		var loc_name = $select.attr("name");
		var loc_metas = {};
		$select.find("option").each(function(metas){
			if(!isBlank($(this).val())){
				metas[$(this).val()] = $(this).text(); 
			}
		},[loc_metas]);
		ZxEvent.dicts[loc_name] = loc_metas;
	},
	
	/**
	 * 数据格式化
	 * @param key
	 * @param value
	 */
	formatByDicts : function(key, value){
		if(isBlank(value) || ZxEvent.dicts.hasOwnProperty(key) == false){
			return "";
		}
		var loc_obj = ZxEvent.dicts[key];
		return loc_obj.hasOwnProperty(value) ? loc_obj[value] : (value || "");
	},
	
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+ZxEvent.gridId).datagrid('reload');
	},
	
	/**
	 * 修改
	 * @param item
	 */
	edit : function(item){
		var loc_eventId = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/zxEventController/preEdit.do?eventId=' + loc_eventId);
		var submitUrl = formatUrl(basePath + '/zxEventController/save.do');
		goldOfficeUtils.openEditorDialog({
			title : '修改财经日历',
			width:800,
			height:420,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				try{
					if($("#zxEventEdit_Form").form('validate')){
						var loc_dateTime = $.trim($("#zxEventEdit_dateTime").val() || "");
						if(loc_dateTime){
							var loc_dateTimes = loc_dateTime.split(" ");
							$("#zxEventEdit_dateTime")
								.next().val(loc_dateTimes[0])
								.next().val(loc_dateTimes[1]);
						}
						goldOfficeUtils.ajaxSubmitForm({
							url : submitUrl,
							formId : 'zxEventEdit_Form',
							onSuccess : function(data){  		//提交成功后处理
								var d = $.parseJSON(data);
								if (d.success) {
									$("#myWindow").dialog("close");
									ZxEvent.refresh();
									$.messager.alert($.i18n.prop("common.operate.tips"),'修改成功！','info');
								}else{
									$.messager.alert($.i18n.prop("common.operate.tips"),'修改失败，原因：'+d.msg,'error');
								}
							}
						});
					}
				}catch(e){
					alert(e);
				}
			},
			onLoad : function(){
				$("#zxEventEdit_Form span[an]").each(function(){
					$(this).html(ZxEvent.formatByDicts($(this).attr("an"), $(this).next().val()));
				});
				$("#zxEventEdit_Form select[name]").each(function(){
					$(this).val($(this).next().val());
				});
			}
		});
	},
	
	/**
	 * 删除
	 * @param item
	 */
	del : function(item){
		var loc_eventId = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/zxEventController/delete.do');
		goldOfficeUtils.deleteOne(ZxEvent.gridId, loc_eventId, url, "确认删除吗？");
	}
};
		
//初始化
$(function() {
	ZxEvent.init();
});