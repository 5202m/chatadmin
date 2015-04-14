/**
 * 聊天室内容管理列表js
 * @author alan.wu
 * @date   2015/03/19
 */
var chatContent = {
	gridId : 'chatContent_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : chatContent.gridId,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			singleSelect : false,
			url : basePath+'/chatContentController/datagrid.do',
			columns : [[
			            {title : 'id',field : 'id',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#chatContent_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.id);
						    });
							return $("#chatContent_datagrid_rowOperation").html();
						}},
						{title : '账号',field : 'userId'},
						{title : '昵称',field : 'userNickname'},
						{title : '使用状态',field : 'statusName',formatter : function(value, rowData, rowIndex) {
							return chatContent.getComboxNameByCode("#chatContentStatus",rowData.status);
						}},
						{title : '用户类型',field : 'typeName',formatter : function(value, rowData, rowIndex) {
							return chatContent.getComboxNameByCode("#chatContentUserType",rowData.userType);
						}},
			            {title : '所属组',field : 'groupName',formatter : function(value, rowData, rowIndex) {
							return chatContent.getComboxNameByCode("#chatContentGroupId",rowData.groupId);
						}},
						{title : '内容',field : 'content'},
						{title : '发布时间',field : 'publishDate',sortable : true,formatter : function(value, rowData, rowIndex) {
							return value ? timeObjectUtil.longMsTimeConvertToDateTime(value) : '';
						}}
						
			]],
			toolbar : '#chatContent_datagrid_toolbar'
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
		$("#chatContent_queryForm_search").on("click",function(){
			var queryParams = $('#'+chatContent.gridId).datagrid('options').queryParams;
			$("#chatContent_queryForm input[name],#chatContent_queryForm select[name]").each(function(){
				queryParams[this.name] = $(this).val();
			});
			$('#'+chatContent.gridId).datagrid({
				url : basePath+'/chatContentController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#chatContent_queryForm_reset").on("click",function(){
			$("#chatContent_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+chatContent.gridId).datagrid('reload');
	},
	/**
	 * 功能：批量删除
	 */
	batchDel : function(){
		var url = formatUrl(basePath + '/chatContentController/del.do');
		goldOfficeUtils.deleteBatch('chatContent_datagrid',url);	
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#chatContent_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/chatContentController/del.do');
		goldOfficeUtils.deleteOne('chatContent_datagrid',recordId,url);
	}
};
		
//初始化
$(function() {
	chatContent.init();
});