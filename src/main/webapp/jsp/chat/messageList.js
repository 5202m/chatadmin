/**
 * 聊天室内容管理列表js
 * @author alan.wu
 * @date   2015/03/19
 */
var chatMessage = {
	gridId : 'chatMessage_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : chatMessage.gridId,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			singleSelect : false,
			url : basePath+'/chatMessageController/datagrid.do?groupId='+$("#chatMessageGroupId").val(),
			columns : [[
			            {title : 'id',field : 'id',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#chatMessage_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.id);
						    });
							return $("#chatMessage_datagrid_rowOperation").html();
						}},
						{title : '账号',field : 'userId'},
						{title : '昵称',field : 'nickname'},
						{title : '用户类型',field : 'typeName',formatter : function(value, rowData, rowIndex) {
							return chatMessage.getComboxNameByCode("#chatMessageUserType",rowData.userType);
						}},
			            {title : '所属组',field : 'groupName',formatter : function(value, rowData, rowIndex) {
							return chatMessage.getComboxNameByCode("#chatMessageGroupId",rowData.groupId);
						}},
						{title : '用户来源',field : 'fromPlatform'},
						{title : '信息类型',field : 'msgTypeStr',formatter : function(value, rowData, rowIndex) {
							var type=rowData.content.msgType;
							if(type=='text'){
								return "文本";
							}
							if(type=='img'){
								return "图片";
							}
							return "";
						}},
						{title : '内容',field : 'contentStr',formatter : function(value, rowData, rowIndex) {
							return rowData.content.msgType!='text'?'<img src="'+rowData.content.value+'"/>':rowData.content.value;
						}},
						{title : '发布时间',field : 'publishTime',sortable : true,formatter : function(value, rowData, rowIndex) {
							return value ? timeObjectUtil.longMsTimeConvertToDateTime(Number(value.replace(/_.+/,""))) : '';
						}}
						
			]],
			toolbar : '#chatMessage_datagrid_toolbar'
		});
	},
	/**
	 * 从下拉框中提取名称
	 */
	getComboxNameByCode:function(domId,code){
		return $(domId).find("option[value='"+code+"']").text();
	},
	setEvent:function(){
		// 列表查询
		$("#chatMessage_queryForm_search").on("click",function(){
			var queryParams = $('#'+chatMessage.gridId).datagrid('options').queryParams;
			$("#chatMessage_queryForm input[name],#chatMessage_queryForm select[name]").each(function(){
				queryParams[this.name] = $(this).val();
			});
			$('#'+chatMessage.gridId).datagrid({
				url : basePath+'/chatMessageController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#chatMessage_queryForm_reset").on("click",function(){
			$("#chatMessage_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+chatMessage.gridId).datagrid('reload');
	},
	/**
	 * 功能：批量删除
	 */
	batchDel : function(){
		var url = formatUrl(basePath + '/chatMessageController/del.do');
		goldOfficeUtils.deleteBatch('chatMessage_datagrid',url);	
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#chatMessage_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/chatMessageController/del.do');
		goldOfficeUtils.deleteOne('chatMessage_datagrid',recordId,url);
	},
	/**
	 * 功能：导出记录
	 */
	exportRecord : function(){
		var path = basePath+ '/chatMessageController/exportRecord.do?'+$("#chatMessage_queryForm").serialize();
		window.location.href = path;
	}
};
		
//初始化
$(function() {
	chatMessage.init();
});