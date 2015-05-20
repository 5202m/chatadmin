/**
 * 摘要：用户管理公用js
 * @author Gavin.guo
 * @date   2014-10-14
 */
var systemUser = {
	gridId : 'system_user_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : systemUser.gridId,
			idField:"userId",
			singleSelect : false,
			url : basePath+'/userController/datagrid.do',
			columns : [[
			            {title : 'userId',field : 'userId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#system_user_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.userId);
						    });
							return $("#system_user_datagrid_rowOperation").html();
						}},
			            {title : $.i18n.prop("user.no"),field : 'userNo'},                   		/**账号*/
			            {title : $.i18n.prop("user.name"),field : 'userName',sortable : true},		/**姓名*/
			            {title : $.i18n.prop("user.email"),field : 'email'},						/**Email*/
						{title : $.i18n.prop("user.phone"),field : 'telephone',sortable : true},	/**手机号*/		
						{title : $.i18n.prop("user.role"),field :'roleName',sortable : true,formatter : function(value, rowData, rowIndex) {/**状态*/
						    if(rowData.role != null){
						    	return rowData.role.roleName;
						    }
							return '';
						}},   	/**所属角色*/
						{title : $.i18n.prop("common.status"),field : 'status',sortable : true,formatter : function(value, rowData, rowIndex) {/**状态*/
							if (value == 0) {
								return $.i18n.prop("common.enabled");
							} else {
								return $.i18n.prop("common.disabled");
							}}}, 
						{title : $.i18n.prop("user.position"),field : 'position',sortable : true},  	/**职位*/
						{title : $.i18n.prop("user.logintime"),field : 'loginDate',sortable : true,formatter : function(value, rowData, rowIndex) {/**登录时间*/
							return value ? timeObjectUtil.longMsTimeConvertToDateTime(value) : '';
						}},
						{title : $.i18n.prop("user.loginip"),field : 'loginIp'}, 					  	/**登录IP*/
						{title : $.i18n.prop("user.logintimes"),field : 'loginTimes',sortable : true}  /**登录次数*/
			]],
			toolbar : '#system_user_datagrid_toolbar'
		});
	},
	setEvent:function(){
		// 列表查询
		$("#system_user_queryForm_search").on("click",function(){
			var userNo = $("#userNo").val();                       //账号 
			var status = $("#status  option:selected").val();      //状态
			var position = $("#position").val();                   //职位
			var role = $("#role  option:selected").val();          //角色
			var queryParams = $('#'+systemUser.gridId).datagrid('options').queryParams;
			queryParams['userNo'] = userNo;
			queryParams['status'] = status;
			queryParams['position'] = position;
			queryParams['roleId'] = role;
			$('#'+systemUser.gridId).datagrid({
				url : basePath+'/userController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#system_user_queryForm_reset").on("click",function(){
			$("#system_user_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：查看
	 * @param recordId   dataGrid行Id
	 */
	view : function(recordId){
		$("#system_user_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/userController/'+recordId+'/view.do');
		goldOfficeUtils.openSimpleDialog({
			title : $.i18n.prop("common.operatetitle.view"),       /**查看记录*/
			height : 240 ,
			href : url ,
			iconCls : 'pag-view'
		});
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		var url = formatUrl(basePath + '/userController/add.do');
		var submitUrl =  formatUrl(basePath + '/userController/create.do');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.add"),			/**添加记录*/
			height : 280,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				if($("#userAddForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'userAddForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if(d.success) {
								$("#myWindow").dialog("close");
								systemUser.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),'新增成功，用户的初始密码为：admin888','info');
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
	 * 功能：修改
	 * @param recordId   dataGrid行Id
	 */
	edit : function(recordId){
		$("#system_user_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/userController/'+recordId+'/edit.do');
		var submitUrl =  formatUrl(basePath + '/userController/update.do');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.edit"),   /**修改记录*/
			height : 280,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){    //提交时处理
				if($("#userEditForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'userEditForm',
						onSuccess : function(data){   //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								$("#myWindow").dialog("close");
								systemUser.refresh();
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
		$('#'+systemUser.gridId).datagrid('reload');
	},
	/**
	 * 功能：重设密码
	 * @param recordId  dataGrid行Id
	 */
	resetPwd : function(recordId){
		$("#system_user_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/userController/resetPwd.do?id='+recordId);
		var submitUrl =  formatUrl(basePath + '/userController/saveResetPwd.do');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("user.resetpwd"),     /**重设密码*/
			height : 150,
			href : url,
			iconCls : 'ope-redo',
			handler : function(){    //提交时处理
				if($("#resetPwdForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'resetPwdForm',
						onSuccess : function(data){   //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								$("#myWindow").dialog("close");
								systemUser.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("user.resetpwd.success"),'info');/**操作提示  重设密码成功,新的密码已经发到您的邮箱!*/
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("user.resetpwd.fail"),'error');	/**操作提示  重设密码失败!*/
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
		var url = formatUrl(basePath + '/userController/batchDel.do');
		goldOfficeUtils.deleteBatch('system_user_datagrid',url,"userId");	
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#system_user_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/userController/oneDel.do');
		goldOfficeUtils.deleteOne('system_user_datagrid',recordId,url);
	}
};
		
//初始化
$(function() {
	systemUser.init();
});