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
var smsConfig = {
	gridId : 'smsConfig_datagrid',
	dicts : {
		cycle : {
			"H":"小时",
			"D":"天",
			"W":"周",
			"M":"月",
			"Y":"年"
		}
	},
	init : function(){
		this.initDicts($("#smsConfig_type"));
		this.initDicts($("#smsConfig_useType"));
		this.initDicts($("#smsConfig_status"));
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : smsConfig.gridId,
			idField:"smsCfgId",
			sortName : 'smsCfgId',
			sortOrder : "desc",
			url : basePath+'/smsConfig/datagrid.do',
			columns : [[
			    {title : $.i18n.prop("common.operate"), field:'smsCfgId', formatter: function(value, rowData, rowIndex){
			    	$("#smsConfig_datagrid_rowOperation input").val(value);
			    	return $("#smsConfig_datagrid_rowOperation").html();
			    }},
	            {title : "信息类型",field : 'type',formatter : function(value, rowData, rowIndex) {
	            	return smsConfig.formatByDicts("type", value);
				}},
	            {title : "应用点",field : 'useType',formatter : function(value, rowData, rowIndex) {
	            	return smsConfig.formatByDicts("useType", value);
				}},
				{title : "验证码有效时长",field : 'validTime',formatter : function(value, rowData, rowIndex) {
					if(typeof value !== "number" || value <= 0){
						return rowData['type'] === "AUTH_CODE" ? "" : "--";
					}else if(value % 86400000 === 0){//一天
						return (value/86400000) + "天";
					}else if(value % 3600000 === 0){
						return (value/3600000) + "小时";
					}else{
						return (value/60000) + "分钟";
					}
				}},
				{title : "最大发送次数",field : 'cnt',formatter : function(value, rowData, rowIndex) {
					return value + "次/" + smsConfig.formatByDicts("cycle", rowData.cycle);
				}},
	            {title : "状态",field : 'status',formatter : function(value, rowData, rowIndex) {
	            	return smsConfig.formatByDicts("status", value);
				}},
			]],
			toolbar : '#smsConfig_datagrid_toolbar'
		});
	},
	
	/** 绑定事件 */
	setEvent:function(){
		/**查询*/
		$("#smsConfig_queryForm_search").on("click",function(){
			var queryParams = $('#'+smsConfig.gridId).datagrid('options').queryParams;
			$("#smsConfig_queryForm select").each(function(){
				queryParams[$(this).attr("name")] = $(this).val();
			});
			$('#'+smsConfig.gridId).datagrid({
				url : basePath+'/smsConfig/datagrid.do',
				pageNumber : 1
			});
		});
		
		/**重置*/
		$("#smsConfig_queryForm_reset").on("click",function(){
			$("#smsConfig_queryForm")[0].reset();
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
		smsConfig.dicts[loc_name] = loc_metas;
	},
	
	/**
	 * 数据格式化
	 * @param key
	 * @param value
	 */
	formatByDicts : function(key, value){
		if(isBlank(value) || smsConfig.dicts.hasOwnProperty(key) == false){
			return "";
		}
		var loc_obj = smsConfig.dicts[key];
		return loc_obj.hasOwnProperty(value) ? loc_obj[value] : (value || "");
	},
	
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+smsConfig.gridId).datagrid('reload');
	},
	
	/**
	 * 获取验证码有效时长
	 * @param $panel 存在三个子元素，需要注意顺序
	 * @param type 短信类型
	 */
	getValidTime : function($panel, type){
		var $items = $panel.children();
		var loc_validTime = 0;
		var loc_unitStr = $items.eq(1).val();
		if(type !== 'AUTH_CODE'){
			loc_validTime = -1;
		}else{
			var loc_num = parseInt($items.eq(0).val(), 10);
			loc_num = isNaN(loc_num) ? 1 : loc_num;
			var loc_unit = 0;
			if(loc_unitStr === "M"){
				loc_unit = 60000;
			}else if(loc_unitStr === "H"){
				loc_unit = 3600000;
			}else if(loc_unitStr === "D"){
				loc_unit = 86400000;
			}
			loc_validTime = loc_num * loc_unit;
		}
		$items.eq(2).val(loc_validTime);
		return loc_validTime;
	},
	
	/**
	 * 设置验证码有效时长
	 * @param $panel 存在三个子元素，需要注意顺序
	 * @param type
	 */
	setValidTime : function($panel, type){
		var $items = $panel.children();
		var loc_validTime = parseInt($items.eq(2).val(), 10);
		loc_validTime = isNaN(loc_validTime) ? 0 : loc_validTime;
		var loc_num = "";
		var loc_unit = "";
		if(type !== "AUTH_CODE"){
			loc_num = 1;
			loc_unit = "D";
		}else if(loc_validTime % 86400000 === 0){
			loc_unit = "D";
			loc_num = loc_validTime / 86400000;
		}else if(loc_validTime % 3600000 === 0){
			loc_unit = "H";
			loc_num = loc_validTime / 3600000;
		}else if(loc_validTime % 60000 === 0){
			loc_unit = "M";
			loc_num = loc_validTime / 60000;
		}
		$items.eq(0).val(loc_num);
		$items.eq(1).val(loc_unit);
	},
	
	/**
	 * 新增
	 */
	add : function(){
		var url = formatUrl(basePath + '/smsConfig/preAdd.do');
		var submitUrl = formatUrl(basePath + '/smsConfig/save.do');
		goldOfficeUtils.openEditorDialog({
			title : '新增短信配置',
			width:710,
			height:230,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				try{
					if($("#smsConfigAdd_Form").form('validate')){
						smsConfig.getValidTime($("#smsConfigAdd_validTime"), $("#smsConfigAdd_type").val());
						goldOfficeUtils.ajaxSubmitForm({
							url : submitUrl,
							formId : 'smsConfigAdd_Form',
							onSuccess : function(data){  		//提交成功后处理
								var d = $.parseJSON(data);
								if (d.success) {
									$("#myWindow").dialog("close");
									smsConfig.refresh();
									$.messager.alert($.i18n.prop("common.operate.tips"),'添加成功！','info');
								}else{
									$.messager.alert($.i18n.prop("common.operate.tips"),'添加失败，原因：'+d.msg,'error');
								}
							}
						});
					}
				}catch(e){
					alert(e);
				}
			},
			onLoad : function(){
				var loc_item = $("#smsConfigAdd_useType");
				loc_item.html($("#smsConfig_useType").html());
				loc_item.find("option:first").remove();
				
				loc_item = $("#smsConfigAdd_status");
				loc_item.html($("#smsConfig_status").html());
				loc_item.find("option:first").remove();
				
				$("#smsConfigAdd_type").bind("change", function(){
					var loc_isAuthCode = $(this).val() === "AUTH_CODE";
					if(loc_isAuthCode){
						$("#smsConfigAdd_validTime").parent().show();
					}else{
						$("#smsConfigAdd_validTime").parent().hide();
						$("#smsConfigAdd_validTime input:first").val(1);
						$("#smsConfigAdd_validTime input:last").val(0);
					}
				})
			}
		});
	},
	
	/**
	 * 修改
	 * @param item
	 */
	edit : function(item){
		var loc_smsCfgId = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/smsConfig/preEdit.do?smsCfgId=' + loc_smsCfgId);
		var submitUrl = formatUrl(basePath + '/smsConfig/save.do');
		goldOfficeUtils.openEditorDialog({
			title : '修改短信配置',
			width:710,
			height:230,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				try{
					if($("#smsConfigEdit_Form").form('validate')){
						smsConfig.getValidTime($("#smsConfigEdit_validTime"), $("#smsConfigEdit_typeInput").val());
						goldOfficeUtils.ajaxSubmitForm({
							url : submitUrl,
							formId : 'smsConfigEdit_Form',
							onSuccess : function(data){  		//提交成功后处理
								var d = $.parseJSON(data);
								if (d.success) {
									$("#myWindow").dialog("close");
									smsConfig.refresh();
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
				$("#smsConfigEdit_Type").text(smsConfig.formatByDicts("type", $("#smsConfigEdit_Type").text()));
				$("#smsConfigEdit_UseType").text(smsConfig.formatByDicts("useType", $("#smsConfigEdit_UseType").text()));
				
				var loc_item = $("#smsConfigEdit_status");
				loc_item.html($("#smsConfig_status").html());
				loc_item.find("option:first").remove();

				smsConfig.setValidTime($("#smsConfigEdit_validTime"), $("#smsConfigEdit_typeInput").val());
				$("#smsConfigEdit_Form select[name]").each(function(){
					$(this).val($(this).next().val());
				})
			}
		});
	},
	
	/**
	 * 删除
	 * @param item
	 */
	del : function(item){
		var loc_smsCfgId = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/smsConfig/delete.do');
		goldOfficeUtils.deleteOne(smsConfig.gridId, loc_smsCfgId, url, "确认删除吗？");
	}
};
		
//初始化
$(function() {
	smsConfig.init();
});