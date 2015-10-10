/**
 * 聊天室直播管理列表js
 * @author alan.wu
 * @date   2015/03/19
 */
var chatStudio = {
	cGroupComboxData:null,
	gridId : 'chatStudio_datagrid',
	init : function(){
		this.initCombox();
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 初始化下拉框
	 */
	initCombox:function(){
		chatStudio.cGroupComboxData=getJson(basePath +"/chatClientGroupController/getClientGroupList.do");
		//设置下拉框
		$("#chatClientGroupId").combotree({
		    data:chatStudio.cGroupComboxData
		});
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : chatStudio.gridId,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			singleSelect : false,
			url : basePath+'/chatGroupController/studioDataGrid.do',
			columns : [[
			            {title : 'id',field : 'id',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#chatStudio_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.id);
						    });
							return $("#chatStudio_datagrid_rowOperation").html();
						}},
			            {title : '编号',field : 'idStr',formatter : function(value, rowData, rowIndex) {
							return rowData.id;
						}},
						{title : '名称',field : 'name'},
						{title : '状态',field : 'status',formatter : function(value, rowData, rowIndex) {
							return chatStudio.getDictNameByCode("#studioStatus",value);
						}},
						{title : '客户组别',field : 'clientGroup',formatter : function(value, rowData, rowIndex) {
							var nameArr=[],valTmp=rowData.chatStudio.clientGroup,tmpData=null;
							for(var i in chatStudio.cGroupComboxData){
								tmpData=chatStudio.cGroupComboxData[i];
								if(valTmp.indexOf(tmpData.id)!=-1){
									nameArr.push(tmpData.text);
								}
							}
							return nameArr.join("，");
						}},
						{title : 'YY频道号',field : 'yyChannel',formatter : function(value, rowData, rowIndex) {
							return rowData.chatStudio.yyChannel;
						}},
						{title : '小频道号',field : 'minChannel',formatter : function(value, rowData, rowIndex) {
							return rowData.chatStudio.minChannel;
						}},
						{title : '直播时间(红色当前生效)',field : 'studioDate',formatter : function(value, rowData, rowIndex) {
							value = rowData.chatStudio.studioDate;
							if(!value){
								return "";
							}else{
								return "<font " + (dateTimeWeekCheck(value, true) ? "style='color:red;'" : "") + " >" + formatDateWeekTime(value) + "</font>"
							}
						}}
						
			]],
			toolbar : '#chatStudio_datagrid_toolbar'
		});
	},
	setEvent:function(){
		// 列表查询
		$("#chatStudio_queryForm_search").on("click",function(){
			var queryParams = $('#'+chatStudio.gridId).datagrid('options').queryParams;
			chatStudio.clearQueryParams(queryParams);
			$("#chatStudio_queryForm input[name],#chatStudio_queryForm select[name]").each(function(){
				var qp=queryParams[this.name];
				if(isValid(qp)){
					queryParams[this.name]+=(","+$(this).val());
				}else{
					queryParams[this.name] = $(this).val();
				}
			});
			$('#'+chatStudio.gridId).datagrid({
				url : basePath+'/chatGroupController/studioDataGrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#chatStudio_queryForm_reset").on("click",function(){
			$("#chatStudio_queryForm")[0].reset();
		});
	},
	/**
	 * 提取名称
	 */
	getDictNameByCode:function(id,code){
		return $(id).find("option[value='"+code+"']").text();
	},
	/**
	 * 清空旧的参数
	 */
	clearQueryParams:function(queryParams){
		$("#chatStudio_queryForm input[name],#chatStudio_queryForm select").each(function(){
			if(isValid($(this).attr("name"))){
				queryParams[this.name] = "";
			}
			if(isValid($(this).attr("comboname"))){
				queryParams[$(this).attr("comboname")] = "";
			}
		});
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		var url = formatUrl(basePath + '/chatGroupController/editStudio.do');
		var submitUrl =  formatUrl(basePath + '/chatGroupController/saveStudio.do?isUpdate=false');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.add"),			/**添加记录*/
			height : 430,
			width:550,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				if($("#chatStudioSubmitForm").form('validate')){
					$("#chatStudio_studioDate").val($("#studioDateDiv").dateTimeWeek.getData());
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'chatStudioSubmitForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								// 提交成功后先关闭弹出框,然后刷新表格,弹出对应的成功提示
								$("#myWindow").dialog("close");
								chatStudio.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),'操作成功！','info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'新增失败，原因：'+d.msg,'error');	/**操作提示 新增失败!*/
							}
						}
					});
				}
			}
		});
	},
	/**
	 * 功能：修改
	 * @param recordId   dataGrid行Id
	 */
	edit : function(recordId){
		$("#system_user_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/chatGroupController/editStudio.do?chatGroupId='+recordId);
		var submitUrl =  formatUrl(basePath + '/chatGroupController/saveStudio.do?isUpdate=true');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.edit"),   /**修改记录*/
			height : 430,
			width:550,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){    //提交时处理
				if($("#chatStudioSubmitForm").form('validate')){
					$("#chatStudio_studioDate").val($("#studioDateDiv").dateTimeWeek.getData());
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'chatStudioSubmitForm',
						onSuccess : function(data){   //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								$("#myWindow").dialog("close");
								chatStudio.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.editsuccess"),'info');/**操作提示  修改成功!*/
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'修改失败，原因：'+d.msg,'error');  /**操作提示  修改失败!*/
							}
						}
					});
				}
			}
		});
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+chatStudio.gridId).datagrid('reload');
	},
	/**
	 * 功能：批量删除
	 */
	batchDel : function(){
		goldOfficeUtils.deleteBatch('chatStudio_datagrid',formatUrl(basePath + '/chatGroupController/delStudio.do'));	
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#chatStudio_datagrid").datagrid('unselectAll');
		goldOfficeUtils.deleteOne('chatStudio_datagrid',recordId,formatUrl(basePath + '/chatGroupController/delStudio.do'));
	}
};
		
//初始化
$(function() {
	chatStudio.init();
});