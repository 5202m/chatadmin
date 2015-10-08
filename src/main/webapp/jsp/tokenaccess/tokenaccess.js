/**
 * 摘要：token设置-公用js
 * @author Gavin.guo
 * @date   2015-05-11
 */
var tokenaccess = {
	gridId : 'tokenaccess_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : tokenaccess.gridId,
			idField : 'tokenAccessId',
			sortName : 'tokenAccessId',
			sortOrder : 'desc',
			pagination : false,
			singleSelect : false,
			url : basePath+'/tokenAccessController/datagrid.do',
			columns : [[
			            {title : 'tokenAccessId',field : 'tokenAccessId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#tokenaccess_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.tokenAccessId);
						    });
							return $("#tokenaccess_datagrid_rowOperation").html();
						}},
						{title : '使用平台',field : 'platform'},
						{title : 'appId',field : 'appId'},
			            {title : 'appSecret',field : 'appSecret'},
			            {title : '有效时间',field : 'expires',sortable : true,formatter : function(value, rowData, rowIndex) {
							if(value == 0) {
								return '一次有效';
							}else if(value == 1){
								return '1小时';
							}else if(value == 2){
								return '2小时';
							}else{
								return '';
							}
						}},
						{title : '备注',field : 'remark'},
						{title : '是否启动',field : 'status',sortable : true,formatter : function(value, rowData, rowIndex) {
							if (value == 0) {
								return '禁用';
							} else {
								return '启用';
							}
						}}
			]],
			toolbar : '#tokenaccess_datagrid_toolbar'
		});
	},
	setEvent:function(){
		$("#tokenaccess_queryForm_search").on("click",function(){
			var queryParams = $('#'+tokenaccess.gridId).datagrid('options').queryParams;
			$("#tokenaccess_queryForm input[name]").each(function(){
				queryParams[this.name] = $(this).val();
			});
			$('#'+tokenaccess.gridId).datagrid({
				url : basePath+'/tokenAccessController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#tokenaccess_queryForm_reset").on("click",function(){
			$("#tokenaccess_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		var url = formatUrl(basePath + '/tokenAccessController/add.do');
		var submitUrl =  formatUrl(basePath + '/tokenAccessController/create.do');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.add"),
			width : 650,
			height : 228,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				if($("#tokenAccessAddForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'tokenAccessAddForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								// 提交成功后先关闭弹出框,然后刷新表格,弹出对应的成功提示
								$("#myWindow").dialog("close");
								tokenaccess.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.addsuccess"),'info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'新增失败，原因：'+d.msg,'error');
							}
						}
					});
				}
			}
		});
	},
	/**
	 * 功能：新增时保存
	 */
	onSaveAdd : function(){
		if($("#tokenAccessAddForm").form('validate')){
			$.messager.progress();    		  		//提交时，加入进度框
			var serializetokenAccessAddFormData = $("#tokenAccessAddForm").serialize();
			getJson(formatUrl(basePath + '/tokenAccessController/create.do'),serializetokenAccessAddFormData,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("新增token成功 !");
					jumpRequestPage(basePath + '/tokenAccessController/index.do');
				}else{
					alert("新增token失败，错误信息："+data.msg);
				}
			},true);
		}
	},
	/**
	 * 功能：修改
	 */
	edit : function(recordId){
		var url = formatUrl(basePath + '/tokenAccessController/edit.do'+'?tokenAccessId='+recordId);
		var submitUrl =  formatUrl(basePath + '/tokenAccessController/update.do');
		goldOfficeUtils.openEditorDialog({
			title : '修改记录',
			width : 650,
			height : 228,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				if($("#tokenAccessEditForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'tokenAccessEditForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								// 提交成功后先关闭弹出框,然后刷新表格,弹出对应的成功提示
								$("#myWindow").dialog("close");
								tokenaccess.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),'修改成功！','info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'修改失败，原因：'+d.msg,'error');
							}
						}
					});
				}
			}
		});
	},
	/**
	 * 功能：修改时保存
	 */
	onSaveEdit : function(){
		if($("#tokenAccessEditForm").form('validate')){
			$.messager.progress();    		  		//提交时，加入进度框
			var serializeTokenAccessFormData = $("#tokenAccessEditForm").serialize();
			getJson(formatUrl(basePath + '/tokenAccessController/update.do'),serializeTokenAccessFormData,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("修改token成功 !");
					jumpRequestPage(basePath + '/tokenAccessController/index.do');
				}else{
					alert("修改token失败，错误信息："+data.msg);
				}
			},true);
		}
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+tokenaccess.gridId).datagrid('reload');
	},
	/**
	 * 功能：批量删除
	 */
	batchDel : function(){
		var url = formatUrl(basePath + '/tokenAccessController/batchDel.do');
		goldOfficeUtils.deleteBatch('tokenaccess_datagrid',url,"tokenAccessId");	
	},
	/**
	 * 功能：删除单行
	 */
	del : function(recordId){
		$("#tokenaccess_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/tokenAccessController/oneDel.do');
		goldOfficeUtils.deleteOne('tokenaccess_datagrid',recordId,url);
	},
	/**
	 * 功能：返回到主列表页面
	 */
	back : function(){
		jumpRequestPage(basePath + '/tokenAccessController/index.do');
	}
};
		
//初始化
$(function() {
	tokenaccess.init();
});