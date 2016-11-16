/**
 * 摘要：角色管理公用js
 * @author Gavin.guo
 * @date   2014-10-22
 */
var systemRole = {
	gridId : 'system_role_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : systemRole.gridId,
			singleSelect : false,
			idField:"roleId",
			sortName : 'roleId',
			url : basePath+'/roleController/datagrid.do?systemCategory='+$("#role_list_systemCategory").val(),
			columns : [[
			            {title : 'roleId',field : 'roleId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {   	/**操作*/
							$("#system_role_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.roleId);
						    });
							return $("#system_role_datagrid_rowOperation").html();
						}},
			            {title : $.i18n.prop("role.roleno"),field : 'roleNo'},   													/**角色编号*/
			            {title : $.i18n.prop("role.rolename"),field : 'roleName',sortable : true},  								/**角色名称*/
						{title : $.i18n.prop("common.status"),field : 'status',sortable : true,formatter : function(value, rowData, rowIndex) {  /**状态*/
							if (value == 0) {
								return $.i18n.prop("common.enabled");
							} else {
								return $.i18n.prop("common.disabled");
							}
						}},
						{title : $.i18n.prop("common.remark"),field : 'remark'}	/**备注*/
			]],
			toolbar : '#system_role_datagrid_toolbar'
		});
	},
	setEvent:function(){
		$("#system_role_queryForm_search").on("click",function(){
			var roleNo = $("#roleNo").val();
			var roleName =  $("#roleName").val();
			var queryParams = $('#'+systemRole.gridId).datagrid('options').queryParams;
			queryParams['roleNo'] = roleNo;
			queryParams['roleName'] = roleName;
			queryParams['systemCategory'] = $("#role_list_systemCategory").val();
			$('#'+systemRole.gridId).datagrid({
				url : basePath+'/roleController/datagrid.do',
				pageNumber : 1
			});
		});
		$("#system_role_queryForm_reset").on("click",function(){
			$("#system_role_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：查看
	 * @param recordId   dataGrid行Id
	 */
	view : function(recordId){
		$("#system_role_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/roleController/'+recordId+'/view.do');
		goldOfficeUtils.openSimpleDialog({
			title : $.i18n.prop("common.operatetitle.view"),   			 /**查看记录*/
			height : 220 ,
			href : url,
			iconCls : 'pag-view'
		});
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		var url = formatUrl(basePath + '/roleController/add.do');
		var submitUrl =  formatUrl(basePath + '/roleController/create.do');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.add"),				/**添加记录*/
			height : 220,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				if($("#roleAddForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'roleAddForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								// 提交成功后先关闭弹出框,然后刷新表格,弹出对应的成功提示
								$("#myWindow").dialog("close");
								systemRole.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.addsuccess"),'info');   /**操作提示  新增成功!*/
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'新增失败，原因：'+d.msg,'error');   	/**操作提示 新增失败!*/
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
		$("#system_role_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/roleController/'+recordId+'/edit.do');
		var submitUrl =  formatUrl(basePath + '/roleController/update.do');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.edit"),			/**修改记录*/
			height : 220,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				if($("#roleEditForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'roleEditForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								// 提交成功后先关闭弹出框,然后刷新表格,弹出对应的成功提示
								$("#myWindow").dialog("close");
								systemRole.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.editsuccess"),'info');	/**操作提示  修改成功!*/
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'修改失败，原因：'+d.msg,'error');	/**操作提示  修改失败!*/
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
		$('#'+systemRole.gridId).datagrid('reload');
	},
	/**
	 * 功能：批量删除
	 */
	batchDel : function(){
		var url = formatUrl(basePath + '/roleController/batchDel.do');
		goldOfficeUtils.deleteBatch('system_role_datagrid',url,'roleId',$.i18n.prop("role.delete")); /**角色下面关联的人员将会被同时删除,您确定要删除吗？*/	
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#system_role_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/roleController/oneDel.do');
		goldOfficeUtils.deleteOne('system_role_datagrid',recordId,url,$.i18n.prop("role.delete"));/**角色下面关联的人员将会被同时删除,您确定要删除吗？*/
	},
	/**
	 * 功能：查询未分配的用户
	 */
	selectUnAssignUser : function(){
		goldOfficeUtils.ajax({
			url : basePath + '/roleController/searchUnAssignUser.do',
			data : {
				roleId : $("#roleId").val(),
				userNoOrName : $("#userNoOrName").val()
			},
			success: function(data) {
				var unselectedUserList = $("#unselectedUserList");
				unselectedUserList.find("option").remove();
				var userList = data.attributes.userList;
				if(userList.length > 0){
					$.each(userList,function(n,obj){
						var val = obj['userId'];
						var text = obj['userNo']+'/'+obj['userName'];
						unselectedUserList.append("<option value='"+val+"'>"+text+"</option>");
				    });
				}
			}
		});
	},
	/**
	 * 功能：分配聊天室
	 */
	assignChatGroup : function(obj){
		$("#system_role_datagrid").datagrid('unselectAll');
		var roleId = obj.id;
		var url = formatUrl(basePath + '/roleController/assignChatGroup.do?roleId='+roleId);
		var submitUrl = formatUrl(basePath + '/roleController/updateAssignChatGroup.do');
		var cg = $("#assignChatGroupDiv").dialog({
			title : '分配聊天室',
			iconCls : 'pag-add',
			href : url,
			width : 550,
			height : 435,
			cache : false,
			buttons : [{
						text : $.i18n.prop("buttons.submit"),
						iconCls : 'ope-save',
						handler : function() {
							var $select = $(this).parent().siblings().find('select');
							$select.each(function(i, n) {
								if($(n).hasClass('rSelect')) {// 需要选中的select class属性为 rSelect
									//判断选中的项 n.options.length
									var returnArr = yxui.findSelectMultipleValue(n.options); // IE 单独处理
									var ids = returnArr[0], texts = returnArr[1];
									$(obj).siblings('input').each(function(i, n) {
										if ($(n).attr('type') == 'hidden') {
											$(n).attr('value', ids.join(','));
										} else {
											$(n).attr('value', texts.join(','));
										}
									});
									goldOfficeUtils.ajax({
										url : submitUrl,
										data : {
											roleId : roleId,
											groupIds : ids+''
										},
										success: function(data) {
											if(data.success) {
												cg.dialog('close');
												$.messager.alert($.i18n.prop("common.operate.tips"),'分配聊天室成功','info');
											}else{
												$.messager.alert($.i18n.prop("common.operate.tips"),'分配聊天室失败，原因：'+data.msg,'error');
											}
										}
									});
								}
							});
						}
					}, {
							text : $.i18n.prop("buttons.cancel"),
							iconCls : 'ope-close',
							handler : function() {
								cg.dialog('close');
							}
					}]
		});
	},
	/**
	 * 功能：分配用户
	 */
	assignUser : function(obj){
		$("#system_role_datagrid").datagrid('unselectAll');
		var roleId = obj.id;
		var url = formatUrl(basePath + '/roleController/assignUser.do?roleId='+roleId);
		var submitUrl = formatUrl(basePath + '/roleController/updateAssignUser.do');
		var p = $("#assignUserDiv").dialog({
			title : $.i18n.prop("role.assignuser"),        		 	/**分配用户*/
			iconCls : 'pag-add',
			href : url,
			width : 550,
			height : 435,
			cache : false,
			buttons : [{
						text : $.i18n.prop("buttons.submit"),
						iconCls : 'ope-save',
						handler : function() {
							var $select = $(this).parent().siblings().find('select');
							$select.each(function(i, n) {
								if($(n).hasClass('rSelect')) {// 需要选中的select class属性为 rSelect
									//判断选中的项 n.options.length
									var returnArr = yxui.findSelectMultipleValue(n.options); // IE 单独处理
									var ids = returnArr[0], texts = returnArr[1];
									$(obj).siblings('input').each(function(i, n) {
										if ($(n).attr('type') == 'hidden') {
											$(n).attr('value', ids.join(','));
										} else {
											$(n).attr('value', texts.join(','));
										}
									});
									goldOfficeUtils.ajax({
										url : submitUrl,
										data : {
											roleId : roleId,
											userIds : ids+''
										},
										success: function(data) {
											if(data.success) {
												p.dialog('close');
												$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("role.assignuser.success"),'info');  /**操作提示  分配用户成功!*/
											}else{
												$.messager.alert($.i18n.prop("common.operate.tips"),'分配用户失败，原因：'+data.msg,'error');  /**操作提示  分配用户失败!*/
											}
										}
									});
								}
							});
						}
					}, {
							text : $.i18n.prop("buttons.cancel"),
							iconCls : 'ope-close',
							handler : function() {
								p.dialog('close');
							}
					}]
		});
	}
};
		
//初始化
$(function() {
	systemRole.init();
});