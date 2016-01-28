/**
 * 摘要：会员反馈公用js
 * @author Gavin.guo
 * @date   2015-07-13
 */
var feedback = {
	gridId : 'feedback_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : feedback.gridId,
			idField : 'feedbackId',
			sortName : 'isReply',
			sortOrder : 'asc',
			singleSelect : false,
			url : basePath+'/feedbackController/datagrid.do',
			columns : [[
			            {title : 'feedbackId',field : 'feedbackId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#feedback_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.feedbackId);
								$(this).attr("memberId",rowData.memberId);
						    });
							return $("#feedback_datagrid_rowOperation").html();
						}},
						{title : '会员Id',field : 'memberId',hidden:true},
						{title : '手机号',field : 'mobilePhone'},
						{title : '昵称',field : 'nickName'},
						{title : '是否回复',field : 'isReply',sortable : true,formatter : function(value, rowData, rowIndex) {
							if (value == 1) {
								return '<font color="red">是</font>';
							} else if(value == 0){
								return '<font color="red">否</font>';
							}else{
								return '<font color="red">否</font>';
							}
						}},
						{title : '最后反馈时间',field : 'lastFeedbackDate',formatter : function(value, rowData, rowIndex) {
							return value ? timeObjectUtil.longMsTimeConvertToDateTime(value) : '';
						}},
						{title : '最后反馈内容',field : 'lastFeedbackContent',formatter : function(value, rowData, rowIndex) {
							return value ? value  : '';  //.replace(/<[^>]+>/g, "")
						}}
			]],
			toolbar : '#feedback_datagrid_toolbar'
		});
	},
	setEvent:function(){
		// 列表查询
		$("#feedback_queryForm_search").on("click",function(){
			var queryParams = $('#'+feedback.gridId).datagrid('options').queryParams;
			$("#feedback_queryForm input[name],#feedback_queryForm select[name]").each(function(){
				queryParams[this.name] = $(this).val();
			});
			$('#'+feedback.gridId).datagrid({
				url : basePath+'/feedbackController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#feedback_queryForm_reset").on("click",function(){
			$("#feedback_queryForm")[0].reset();
			$('#feedback_queryForm_subjectType').combotree('clear');
		});
	},
	/**
	 * 功能：回复
	 */
	toReply : function(obj){
		var recordId = $(obj).attr("id"),memberId = $(obj).attr("memberId");
		$("#feedback_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/feedbackController/reply.do?feedbackId='+recordId+'&memberId='+memberId);
		var submitUrl = formatUrl(basePath + '/feedbackController/doReply.do');
		goldOfficeUtils.openEditorDialog({
			title : '回复',
			width:650,
			height:550,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				if(isBlank(UE.getEditor('feedBackContent').getContent())){
					alert("请输入回复信息！");
					return;
				}
				goldOfficeUtils.ajaxSubmitForm({
					url : submitUrl,
					formId : 'feebackReplyForm',
					onSuccess : function(data){  		//提交成功后处理
						var d = $.parseJSON(data);
						if (d.success) {
							$("#myWindow").dialog("close");
							feedback.refresh();
							$.messager.alert($.i18n.prop("common.operate.tips"),'回复成功！','info');
						}else{
							$.messager.alert($.i18n.prop("common.operate.tips"),'回复失败，原因：'+d.msg,'error');
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
		var url = formatUrl(basePath + '/feedbackController/batchDel.do');
		goldOfficeUtils.deleteBatch('feedback_datagrid',url,'feedbackId');
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#feedback_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/feedbackController/oneDel.do');
		goldOfficeUtils.deleteOne('feedback_datagrid',recordId,url);
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+feedback.gridId).datagrid('reload');
	},
	/**
	 * 功能：导出记录
	 */
	exportRecord : function(){
		var loc_params = $('#'+feedback.gridId).datagrid('options').queryParams;
		var path = basePath+ '/feedbackController/exportRecord.do?'+$.param(loc_params);
		window.location.href = path;
	},
	/**
	 * 功能：返回到主列表页面
	 */
	back : function(){
		jumpRequestPage(basePath + '/feedbackController/index.do');
	}
};
		
//初始化
$(function() {
	feedback.init();
});