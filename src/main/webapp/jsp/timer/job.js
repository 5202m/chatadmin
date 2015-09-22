/**
 * 摘要：定时任务公用js
 * @author Gavin.guo
 * @date   2015-07-30
 */
var job = {
	gridId : 'job_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : job.gridId,
			idField : 'jobId',
			sortName : 'jobId',
			sortOrder : 'desc',
			singleSelect : false,
			url : basePath+'/jobController/datagrid.do',
			columns : [[
			            {title : 'jobId',field : 'jobId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#job_datagrid_rowOperation a").each(function(){
								// (0：未运行  1：待运行 2：运行成功  3：运行失败)
								if(rowData.status == 1){
									$(this).filter("[data-options*=ope-redo]").show();  //停止
									$(this).filter("[data-options*=ope-undo]").hide();  //启动
									$(this).filter("[data-options*=ope-edit]").show();  //修改
								}else if(rowData.status == 0){
									$(this).filter("[data-options*=ope-redo]").hide();
									$(this).filter("[data-options*=ope-undo]").show();
									$(this).filter("[data-options*=ope-edit]").hide();
								}else if(rowData.status == 2 || rowData.status == 3){
									$(this).filter("[data-options*=ope-redo]").hide();
									$(this).filter("[data-options*=ope-undo]").hide();
									$(this).filter("[data-options*=ope-edit]").hide();
								}
								$(this).attr("id",rowData.jobId);
								$(this).attr("status",rowData.status);
						    });
							return $("#job_datagrid_rowOperation").html();
						}},
						{title : 'job组名',field : 'jobGroup'},
						{title : 'job名称',field : 'jobName'},
						{title : 'job执行时间',field : 'cronExpression',sortable : true,formatter : function(value, rowData, rowIndex) {
							return timeObjectUtil.longMsTimeConvertToDateTime(value);
						}},
						{title : 'job描述',field : 'desc'},
						{title : '运行状态',field : 'status',formatter : function(value, rowData, rowIndex) {
							if (value == 0) {
								return '未运行';
							}else if(value == 1){
								return '待运行';
							}else if(value == 2){
								return '运行成功';
							}else if(value == 3){
								return '运行失败';
							}else{
								return '未运行';
							}
						}},
						{title : '创建时间',field : 'createDate',formatter : function(value, rowData, rowIndex) {
							return timeObjectUtil.longMsTimeConvertToDateTime(value);
						}}
			]],
			toolbar : '#job_datagrid_toolbar'
		});
	},
	setEvent:function(){
		// 列表查询
		$("#job_queryForm_search").on("click",function(){
			var queryParams = $('#'+job.gridId).datagrid('options').queryParams;
			$("#job_queryForm input[name],#job_queryForm select[name]").each(function(){
				queryParams[this.name] = $(this).val();
			});
			$('#'+job.gridId).datagrid({
				url : basePath+'/jobController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#job_queryForm_reset").on("click",function(){
			$("#job_queryForm")[0].reset();
		});
		
	},
	/**
	 * 功能：修改
	 */
	edit : function(recordId){
		$("#"+job.gridId).datagrid('unselectAll');
		var url = formatUrl(basePath + '/jobController/'+recordId+'/edit.do');
		var submitUrl =  formatUrl(basePath + '/jobController/update.do');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.edit"),			/**修改记录*/
			height : 120,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				if(isBlank($("#cronExpressionTempEdit").val())){
			    	alert("请选择执行时间！");
			    	return;
			    }
				goldOfficeUtils.ajaxSubmitForm({
					url : submitUrl,
					formId : 'jobEditForm',
					onSuccess : function(data){  //提交成功后处理
						var d = $.parseJSON(data);
						if (d.success) {
							$("#myWindow").dialog("close");
							job.refresh();
							$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.editsuccess"),'info');	/**操作提示  修改成功!*/
						}else{
							$.messager.alert($.i18n.prop("common.operate.tips"),'修改失败，原因：'+d.msg,'error');	/**操作提示  修改失败!*/
						}
					}
				});
			}
		});
	},
	/**
	 * 功能：停止
	 */
	stop : function(obj){
		var stopUrl = formatUrl(basePath + '/jobController/stop.do');
		$.messager.confirm("操作提示", '您确定要停止该定时任务吗?', function(r) {
			   if (r) {
				   goldOfficeUtils.ajax({
						url : stopUrl,
						data : {
							jobId : obj.id
						},
						success: function(data) {
							if(data.success) {
								$("#"+job.gridId).datagrid('unselectAll');
								$('#'+job.gridId).datagrid('reload');
								$.messager.alert($.i18n.prop("common.operate.tips"),'停止成功!','info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'停止失败，原因：'+data.msg,'error');
					    	}
						}
					});
				}
		});
	},
	/**
	 * 功能：启动
	 */
	start : function(obj){
		var startUrl = formatUrl(basePath + '/jobController/start.do');
		$.messager.confirm("操作提示", '您确定要启动该定时任务吗?', function(r) {
			   if (r) {
				   goldOfficeUtils.ajax({
						url : startUrl,
						data : {
							jobId : obj.id
						},
						success: function(data) {
							if(data.success) {
								$("#"+job.gridId).datagrid('unselectAll');
								$('#'+job.gridId).datagrid('reload');
								$.messager.alert($.i18n.prop("common.operate.tips"),'启动成功!','info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'启动失败，原因：'+data.msg,'error');
					    	}
						}
					});
				}
		});
	},
	/**
	 * 功能：批量删除
	 */
	batchDel : function(){
		var url = formatUrl(basePath + '/jobController/del.do');
		goldOfficeUtils.deleteBatch('job_datagrid',url,'jobId');
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#job_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/jobController/del.do');
		goldOfficeUtils.deleteOne('job_datagrid',recordId,url);
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+job.gridId).datagrid('reload');
	},
	/**
	 * 功能：返回到主列表页面
	 */
	back : function(){
		jumpRequestPage(basePath + '/jobController/index.do');
	}
};
		
//初始化
$(function() {
	job.init();
});