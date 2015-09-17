/**
 * 摘要：应用类别管理公用js
 * @author Gavin.guo
 * @date   2015-03-19
 */
var appCategory = {
	gridId : 'appCategory_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : appCategory.gridId,
			idField : 'appCategoryId',
			sortName : 'appCategoryId',
			singleSelect : false,
			url : basePath+'/appCategoryController/datagrid.do',
			columns : [[
			            {title : 'appCategoryId',field : 'appCategoryId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#appCategory_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.appCategoryId);
						    });
							return $("#appCategory_datagrid_rowOperation").html();
						}},
			            {title : '编号',field : 'code'},
			            {title : '名称',field : 'name'}
			]],
			toolbar : '#appCategory_datagrid_toolbar'
		});
	},
	setEvent:function(){
		// 列表查询
		$("#appCategory_queryForm_search").on("click",function(){
			var code = $("#code").val();                        //编号
			var name = $("#name").val();                   		//名称
			var queryParams = $('#'+appCategory.gridId).datagrid('options').queryParams;
			queryParams['code'] = code;
			queryParams['name'] = name;
			$('#'+appCategory.gridId).datagrid({
				url : basePath+'/appCategoryController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#appCategory_queryForm_reset").on("click",function(){
			$("#appCategory_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		var url = formatUrl(basePath + '/appCategoryController/add.do');
		var submitUrl =  formatUrl(basePath + '/appCategoryController/create.do');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.add"),			/**添加记录*/
			height : 140,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				if($("#appCategoryAddForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'appCategoryAddForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								// 提交成功后先关闭弹出框,然后刷新表格,弹出对应的成功提示
								$("#myWindow").dialog("close");
								appCategory.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),'新增成功!','info');
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
	 */
	edit : function(recordId){
		$("#appCategory_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/appCategoryController/edit.do?appCategoryId='+recordId);
		var submitUrl =  formatUrl(basePath + '/appCategoryController/update.do');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.edit"),   /**修改记录*/
			height : 150,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){    //提交时处理
				if($("#appCategoryEditForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'appCategoryEditForm',
						onSuccess : function(data){   //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								$("#myWindow").dialog("close");
								appCategory.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),'修改成功!','info');
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
	 * 功能：批量删除
	 */
	batchDel : function(){
		var url = formatUrl(basePath + '/appCategoryController/batchDel.do');
		goldOfficeUtils.deleteBatch('appCategory_datagrid',url,"appCategoryId",'该类别下的所有应用将不关联任何类别，您确定要删除吗？');
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#appCategory_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/appCategoryController/oneDel.do');
		goldOfficeUtils.deleteOne('appCategory_datagrid',recordId,url,'该类别下的所有应用将不关联任何类别，您确定要删除吗？');
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+appCategory.gridId).datagrid('reload');
	}
};
		
//初始化
$(function() {
	appCategory.init();
});