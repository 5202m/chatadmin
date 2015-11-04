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
	 * 新增
	 */
	add : function(){
		var url = formatUrl(basePath + '/smsConfig/preAdd.do');
		var submitUrl = formatUrl(basePath + '/smsConfig/save.do');
		goldOfficeUtils.openEditorDialog({
			title : '新增短信配置',
			width:710,
			height:200,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				try{
					if($("#smsConfig_addForm").form('validate')){
						goldOfficeUtils.ajaxSubmitForm({
							url : submitUrl,
							formId : 'smsConfig_addForm',
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
			height:200,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				try{
					if($("#smsConfig_editForm").form('validate')){
						goldOfficeUtils.ajaxSubmitForm({
							url : submitUrl,
							formId : 'smsConfig_editForm',
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
				$("#smsConfig_editType").text(smsConfig.formatByDicts("type", $("#smsConfig_editType").text()));
				$("#smsConfig_editUseType").text(smsConfig.formatByDicts("useType", $("#smsConfig_editUseType").text()));
				
				var loc_item = $("#smsConfigEdit_status");
				loc_item.html($("#smsConfig_status").html());
				loc_item.find("option:first").remove();
				
				$("#smsConfig_editForm select").each(function(){
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
		alert(loc_smsCfgId);
		goldOfficeUtils.deleteOne(smsConfig.gridId, loc_smsCfgId, url, "确认删除吗？");
	}
};
		
//初始化
$(function() {
	smsConfig.init();
});