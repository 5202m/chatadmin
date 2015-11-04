/**
 * 短信管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年10月28日 <BR>
 * Description : <BR>
 * <p>
 * 
 * </p>
 */
var smsInfo = {
	gridId : 'sms_smsInfo_datagrid',
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
		this.initDicts($("#smsInfo_type"));
		this.initDicts($("#smsInfo_useType"));
		this.initDicts($("#smsInfo_status"));
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : smsInfo.gridId,
			idField:"smsId",
			sortName : 'smsId',
			sortOrder : "desc",
			url : basePath+'/sms/datagrid.do',
			columns : [[
			    {title : $.i18n.prop("common.operate"), field:'smsId', formatter: function(value, rowData, rowIndex){
			    	$("#sms_smsInfo_datagrid_rowOperation input").val(value);
			    	return $("#sms_smsInfo_datagrid_rowOperation").html();
			    }},
	            {title : "信息类型",field : 'type',formatter : function(value, rowData, rowIndex) {
	            	return smsInfo.formatByDicts("type", value);
				}},
	            {title : "应用点",field : 'useType',formatter : function(value, rowData, rowIndex) {
	            	return smsInfo.formatByDicts("useType", value);
				}},
	            {title : "手机号",field : 'mobilePhone'},
	            {title : "ip/mac",field : 'deviceKey'},
	            {title : "短信内容",field : 'content'},
	            {title : "状态",field : 'status',formatter : function(value, rowData, rowIndex) {
	            	return smsInfo.formatByDicts("status", value);
				}},
	            {title : "发送时间",field : 'sendTime', formatter : timeObjectUtil.formatterDateTime},
				{title : "有效期至",field : 'validUntil', formatter : timeObjectUtil.formatterDateTime},
				{title : "使用时间",field : 'useTime', formatter : timeObjectUtil.formatterDateTime}
			]],
			toolbar : '#sms_smsInfo_datagrid_toolbar'
		});
	},
	
	/** 绑定事件 */
	setEvent:function(){
		/**查询*/
		$("#sms_smsInfo_queryForm_search").on("click",function(){
			var queryParams = $('#'+smsInfo.gridId).datagrid('options').queryParams;
			$("#sms_smsInfo_queryForm input").add("#sms_smsInfo_queryForm select").each(function(){
				queryParams[$(this).attr("name")] = $(this).val();
			});
			if(queryParams["sendStart"] !== ""){
				queryParams["sendStart"] =  formatStartDate(queryParams["sendStart"]);
			}
			if(queryParams["sendEnd"] !== ""){
				queryParams["sendEnd"] =  formatEndDate(queryParams["sendEnd"]);
			}
			$('#'+smsInfo.gridId).datagrid({
				url : basePath+'/sms/datagrid.do',
				pageNumber : 1
			});
		});
		/**重置*/
		$("#sms_smsInfo_queryForm_reset").on("click",function(){
			$("#sms_smsInfo_queryForm")[0].reset();
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
		smsInfo.dicts[loc_name] = loc_metas;
	},
	
	/**
	 * 数据格式化
	 * @param key
	 * @param value
	 */
	formatByDicts : function(key, value){
		if(isBlank(value) || smsInfo.dicts.hasOwnProperty(key) == false){
			return "";
		}
		var loc_obj = smsInfo.dicts[key];
		return loc_obj.hasOwnProperty(value) ? loc_obj[value] : (value || "");
	},
	
	/**
	 * 重新发送
	 */
	resend:function(item){
		var loc_smsId = $(item).siblings().val();
		$.messager.confirm("操作提示", '确定重新发送该短信吗？', function(r)
		{
			if (r)
			{
				goldOfficeUtils.ajax({
					url : basePath + '/sms/resend.do',
					data : {
						smsId : loc_smsId
					},
					success: function(data) {
						if(data.success) {
							$.messager.alert($.i18n.prop("common.operate.tips"), "发送成功", 'info');   /**操作提示 保存成功!*/
						}else{
							$.messager.alert($.i18n.prop("common.operate.tips"), "发送失败",'error');     /**操作提示 保存失败!*/
						}
						smsInfo.refresh();
					}
				});
			}
		});
	},
	
	/**
	 * 重置计数器
	 * @param item
	 */
	resetCnt : function(item){
		var loc_smsId = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/sms/preReset.do?smsId=' + loc_smsId);
		var submitUrl = formatUrl(basePath + '/sms/reset.do');
		goldOfficeUtils.openEditorDialog({
			title : '重置短信计数',
			width:710,
			height:280,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				var loc_startTime = $("#smsInfo_resetForm input[name='resetStart']").val();
				if(!loc_startTime){
					$.messager.alert($.i18n.prop("common.operate.tips"),'没有配置相关限制次数信息，不可重置计数器','error');
				}else{
					try{
						goldOfficeUtils.ajaxSubmitForm({
							url : submitUrl,
							formId : 'smsInfo_resetForm',
							onSuccess : function(data){  		//提交成功后处理
								var d = $.parseJSON(data);
								if (d.success) {
									$("#myWindow").dialog("close");
									$.messager.alert($.i18n.prop("common.operate.tips"),'重置成功！','info');
								}else{
									$.messager.alert($.i18n.prop("common.operate.tips"),'重置失败，原因：'+d.msg,'error');
								}
							}
						});
					}catch(e){
						alert(e);
					}
				}
			},
			onLoad : function(){
				$("#smsInfo_resetType").text(smsInfo.formatByDicts("type", $("#smsInfo_resetType").text()));
				$("#smsInfo_resetUseType").text(smsInfo.formatByDicts("useType", $("#smsInfo_resetUseType").text()));
				$("#smsInfo_resetCycle").text(smsInfo.formatByDicts("cycle", $("#smsInfo_resetCycle").text()));
			}
		});
	},
	
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+smsInfo.gridId).datagrid('reload');
	}
};
		
//初始化
$(function() {
	smsInfo.init();
});