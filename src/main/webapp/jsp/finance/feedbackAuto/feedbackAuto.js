/**
 * 摘要：会员反馈公用js
 * @author Gavin.guo
 * @date   2015-07-13
 */
var feedbackAuto = {
	gridId : 'finance_feedbackAuto_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : feedbackAuto.gridId,
			idField : 'feedbackAutoId',
			sortName : 'feedbackAutoId',
			sortOrder : 'desc',
			singleSelect : false,
			url : basePath+'/finance/feedbackAutoController/datagrid.do',
			columns : [[
			            {title : 'feedbackAutoId',field : 'feedbackAutoId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
			            	$("#finance_feedbackAuto_datagrid_rowOperation input").val(rowData.feedbackAutoId);
							return $("#finance_feedbackAuto_datagrid_rowOperation").html();
						}},
						{title : '类型',field : 'type', formatter : function(value, rowData, rowIndex) {
							return value === 1 ? "自定义" : "系统";
						}},
						{title : '关键字',field : 'antistop'},
						{title : '回复内容',field : 'content'}
			]],
			toolbar : '#finance_feedbackAuto_datagrid_toolbar'
		});
	},
	setEvent:function(){
		// 列表查询
		$("#finance_feedbackAuto_queryForm_search").on("click",function(){
			var queryParams = $('#'+feedbackAuto.gridId).datagrid('options').queryParams;
			$("#finance_feedbackAuto_queryForm input[name]").add("#finance_feedbackAuto_queryForm select[name]").each(function(){
				queryParams[this.name] = $(this).val();
			});
			$('#'+feedbackAuto.gridId).datagrid({
				url : basePath+'/finance/feedbackAutoController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#finance_feedbackAuto_queryForm_reset").on("click",function(){
			$("#finance_feedbackAuto_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：新增
	 */
	add: function(){
		$('#'+feedbackAuto.gridId).datagrid('unselectAll');
		var url = formatUrl(basePath + '/finance/feedbackAutoController/preAdd.do');
		var submitUrl = formatUrl(basePath + '/finance/feedbackAutoController/add.do');
		goldOfficeUtils.openEditorDialog({
			title : '新增自动回复配置',
			width:650,
			height:480,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				try{
					if($("#finance_feedbackAuto_addForm").form('validate')){
						var loc_type = $("#finance_feedbackAuto_addForm select[name='type']").val();
						var loc_ueEditor = UE.getEditor('finance_feedbackAuto_content');
						if(loc_type == "1" && isBlank(loc_ueEditor.getContent())){
							alert("请输入回复信息！");
							return;
						}
						if(loc_type != "1"){
							loc_ueEditor.setContent("");
						}
						goldOfficeUtils.ajaxSubmitForm({
							url : submitUrl,
							formId : 'finance_feedbackAuto_addForm',
							onSuccess : function(data){  		//提交成功后处理
								var d = $.parseJSON(data);
								if (d.success) {
									$("#myWindow").dialog("close");
									feedbackAuto.refresh();
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
			}
		});
	},
	/**
	 * 功能：修改
	 */
	edit: function(item){
		$('#'+feedbackAuto.gridId).datagrid('unselectAll');
		var feedbackAutoId = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/finance/feedbackAutoController/preEdit.do', {feedbackAutoId : feedbackAutoId});
		var submitUrl = formatUrl(basePath + '/finance/feedbackAutoController/edit.do');
		goldOfficeUtils.openEditorDialog({
			title : '修改自动回复配置',
			width:650,
			height:480,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				try{
					if($("#finance_feedbackAuto_editForm").form('validate')){
						var loc_type = $("#finance_feedbackAuto_editForm select[name='type']").val();
						var loc_ueEditor = UE.getEditor('finance_feedbackAuto_content');
						if(loc_type == "1" && isBlank(loc_ueEditor.getContent())){
							alert("请输入回复信息！");
							return;
						}
						if(loc_type != "1"){
							loc_ueEditor.setContent("");
						}
						goldOfficeUtils.ajaxSubmitForm({
							url : submitUrl,
							formId : 'finance_feedbackAuto_editForm',
							onSuccess : function(data){  		//提交成功后处理
								var d = $.parseJSON(data);
								if (d.success) {
									$("#myWindow").dialog("close");
									feedbackAuto.refresh();
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
				feedbackAuto.typeChange($("#finance_feedbackAuto_editForm select:first"), 'finance_feedbackAuto_editForm');
			}
		});
	},
	/**
	 * 功能：批量删除
	 */
	batchDel : function(){
		var url = formatUrl(basePath + '/finance/feedbackAutoController/batchDel.do');
		goldOfficeUtils.deleteBatch(feedbackAuto.gridId,url,'feedbackAutoId');
	},
	/**
	 * 功能：删除单行
	 * @param item
	 */
	del : function(item){
		$('#'+feedbackAuto.gridId).datagrid('unselectAll');
		var feedbackAutoId = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/finance/feedbackAutoController/delete.do');
		goldOfficeUtils.deleteOne(feedbackAuto.gridId, feedbackAutoId, url, "确认删除吗？");
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+feedbackAuto.gridId).datagrid('reload');
	},
	/**
	 * 功能：返回到主列表页面
	 */
	back : function(){
		jumpRequestPage(basePath + '/finance/feedbackAutoController/index.do');
	},
	
	/**
	 * 类型改变
	 */
	typeChange : function(item, formId){
		var loc_type = $(item).val();
		if(loc_type == "1"){
			$("#" + formId + " tr:gt(1)").show();
		}else{
			$("#" + formId + " tr:gt(1)").hide();
		}
	}
};
		
//初始化
$(function() {
	feedbackAuto.init();
});