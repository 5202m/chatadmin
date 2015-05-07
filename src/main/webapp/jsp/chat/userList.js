/**
 * 聊天室在线用户管理列表js
 * @author alan.wu
 * @date   2015/03/19
 */
var chatUser = {
	gridId : 'chatUser_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : chatUser.gridId,
			idField : 'memberId',
			sortName : 'memberId',
			sortOrder : 'desc',
			singleSelect : false,
			url : basePath+'/chatUserController/datagrid.do?loginPlatform.chatUserGroup[0].id='+$("#chatUserGroupId").val(),
			columns : [[
			            {title : 'id',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
			            	$("#chatUser_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.memberId);
								$(this).attr("groupid",rowData.loginPlatform.chatUserGroup[0].id);
						    });
							return $("#chatUser_datagrid_rowOperation").html();
						}},
						{title : '账号',field : 'memberId'},
						{title : '昵称',field : 'nickname'},
						{title : '头像',field : 'avatar',formatter : function(value, rowData, rowIndex) {
							return rowData.loginPlatform.chatUserGroup[0].avatar;
						}},
						{title : '所属组Id',field : 'groupId',hidden:true,formatter : function(value, rowData, rowIndex) {
							return rowData.loginPlatform.chatUserGroup[0].id;
						}},
			            {title : '所属组',field : 'groupName',formatter : function(value, rowData, rowIndex) {
							return chatUser.getComboxNameByCode("#chatUserGroupId",rowData.loginPlatform.chatUserGroup[0].id);
						}},
						{title : '在线状态',field : 'onlineStatus',formatter : function(value, rowData, rowIndex) {
							return chatUser.getComboxNameByCode("#chatUserOnlineStatus",rowData.loginPlatform.chatUserGroup[0].onlineStatus);
						}},
						{title : '上线时间',field : 'onlineDateStr',sortable : true,formatter : function(value, rowData, rowIndex) {
							return value ? timeObjectUtil.longMsTimeConvertToDateTime(rowData.loginPlatform.chatUserGroup[0].onlineDate) : '';
						}}
						
			]],
			toolbar : '#chatUser_datagrid_toolbar'
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
		$("#chatUser_queryForm_search").on("click",function(){
			var queryParams = $('#'+chatUser.gridId).datagrid('options').queryParams;
			$("#chatUser_queryForm input[name],#chatUser_queryForm select[name]").each(function(){
				queryParams[this.name] = $(this).val();
			});
			$('#'+chatUser.gridId).datagrid({
				url : basePath+'/chatUserController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#chatUser_queryForm_reset").on("click",function(){
			$("#chatUser_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+chatUser.gridId).datagrid('reload');
	},
	/**
	 * 功能：设置禁言
	 */
	setUserGag : function(obj){
		$("#chatUser_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/chatUserController/toUserGag.do?memberId='+$(obj).attr("id")+"&groupId="+$(obj).attr("groupid"));
		var submitUrl =  formatUrl(basePath + '/chatUserController/setUserGag.do');
		goldOfficeUtils.openEditorDialog({
			title : '设置禁言',
			width : 630,
			height : 160,
			href : url,
			iconCls : 'ope-redo',
			handler : function(){    //提交时处理
				if($("#userGagForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'userGagForm',
						onSuccess : function(data){   //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								$("#myWindow").dialog("close");
								chatUser.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),'设置禁言成功!','info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'设置禁言失败','error');
							}
						}
					});
				}
			}
		});
	}
};
		
//初始化
$(function() {
	chatUser.init();
});