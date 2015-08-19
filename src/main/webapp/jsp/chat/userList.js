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
	 * 格式成两行
	 * @param val
	 * @param hasBorder
	 * @returns {String}
	 */
	formatTwoRow:function(val,hasBorder,addDom){
		return '<span style="display:block;width:100%; margin:5px auto;padding-bottom:5px;'+(hasBorder?'border-bottom: 1px solid #D0D0D0;':'')+'">'+val+(addDom||'')+'</span>';
	},
	/**
	 * 是否符合查询条件
	 * @param rooms
	 * @returns {Boolean}
	 */
	isSearchField:function(room){
	   var isTrue=this.isGroupTypeSearch();
	   if(isTrue){
		   return isTrue;
	   }
	   return $("#chatUserGroupId").val()==room.id;
	},
	isGroupTypeSearch:function(){
	  return $("#chatUserGroupId").val().indexOf(",")!=-1;
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
								$(this).attr("memberId",rowData.memberId);
								$(this).attr("mobilePhone",rowData.mobilePhone);
								var ug=rowData.loginPlatform.chatUserGroup[0];
								$(this).attr("groupType",ug.id);
								$(this).attr("valueUser",ug.valueUser);
								$(this).attr("vipUser",ug.vipUser);
								$(this).attr("valueUserRemark",ug.valueUserRemark||'');
								$(this).attr("vipUserRemark",ug.vipUserRemark||'');
						    });
							return $("#chatUser_datagrid_rowOperation").html();
						}},
						{title : '手机号码',field : 'mobilePhone'},
						{title : '账号',field : 'userIds',formatter : function(value, rowData, rowIndex) {
							var row=rowData.loginPlatform.chatUserGroup[0];
							return isBlank(row.accountNo)?row.userId:row.accountNo;
						}},
						{title : '昵称【ID号】',field : 'nicknameStr', formatter : function(value, rowData, rowIndex) {
							return rowData.loginPlatform.chatUserGroup[0].nickname+"【"+rowData.loginPlatform.chatUserGroup[0].userId+"】";
						}},
						{title : '价值用户',field : 'loginPlatform.chatUserGroup.valueUser',sortable : true,formatter : function(value, rowData, rowIndex) {
							var row=rowData.loginPlatform.chatUserGroup[0];
							return row.valueUser?'是' : '否';
						}},
						{title : '用户级别',field : 'loginPlatform.chatUserGroup.vipUser',sortable : true,formatter : function(value, rowData, rowIndex) {
							var row=rowData.loginPlatform.chatUserGroup[0];
							//微解盘没有用户组别，用户级别为真实用户
							var id = row.vipUser ? 'vip' : (!row.clientGroup ? "real" : row.clientGroup);
							return name=$.trim(chatUser.getComboxNameByCode("#userList_clientGroup", id));
						}},
			            {title : '房间名称',field : 'groupName',formatter : function(value, rowData, rowIndex) {
			            	var groupRow=rowData.loginPlatform.chatUserGroup[0],rooms=groupRow.rooms,tds="",sDom='',name='';
			            	for(var i=0;i<rooms.length;i++){
			            		name=$.trim(chatUser.getComboxNameByCode("#chatUserGroupId",rooms[i].id));
					            sDom='<span class="ope-save" style="cursor:pointer;" onclick="chatUser.setUserGag(this)" mobilePhone="'+rowData.mobilePhone+'" groupName="'+name+'" memberId="'+rowData.memberId+'" groupType="'+groupRow.id+'" groupId="'+rooms[i].id+'">禁言</span>';
					            if(!chatUser.isSearchField(rooms[i])){
					            	continue;
					            }
					            tds+=chatUser.formatTwoRow(name,chatUser.isGroupTypeSearch() && i<rooms.length-1,sDom);
			            	}
							return tds;
						}},
						{title : '在线状态',field : 'onlineStatus',formatter : function(value, rowData, rowIndex) {
							var rooms=rowData.loginPlatform.chatUserGroup[0].rooms,tds="";
			            	for(var i=0;i<rooms.length;i++){
			            		if(!chatUser.isSearchField(rooms[i])){
						          continue;
						        }
			            		tds+=chatUser.formatTwoRow(chatUser.getComboxNameByCode("#chatUserOnlineStatus",rooms[i].onlineStatus),chatUser.isGroupTypeSearch() && i<rooms.length-1);
			            	}
							return tds;
						}},
						{title : '上线时间',field : 'loginPlatform.chatUserGroup.onlineDate',sortable : true,formatter : function(value, rowData, rowIndex) {
							var rooms=rowData.loginPlatform.chatUserGroup[0].rooms,tds="";
			            	for(var i=0;i<rooms.length;i++){
			            		if(!chatUser.isSearchField(rooms[i])){
							       continue;
							    }
			            		tds+=chatUser.formatTwoRow((rooms[i].onlineDate? timeObjectUtil.formatterDateTime(rooms[i].onlineDate) : ''),chatUser.isGroupTypeSearch() && i<rooms.length-1);
			            	}
							return tds;
						}},
						{title : '禁言开始时间',field : 'loginPlatform.chatUserGroup.gagStartDate',sortable : true,formatter : function(value, rowData, rowIndex) {
							var rooms=rowData.loginPlatform.chatUserGroup[0].rooms,tds="";
			            	for(var i=0;i<rooms.length;i++){
			            		if(!chatUser.isSearchField(rooms[i])){
								  continue;
								}
			            		tds+=chatUser.formatTwoRow((rooms[i].gagStartDate? timeObjectUtil.formatterDateTime(rooms[i].gagStartDate) : ''),chatUser.isGroupTypeSearch() && i<rooms.length-1);
			            	}
							return tds;
						}},
						{title : '禁言结束时间',field : 'loginPlatform.chatUserGroup.gagEndDate',sortable : true,formatter : function(value, rowData, rowIndex) {
							var rooms=rowData.loginPlatform.chatUserGroup[0].rooms,tds="";
			            	for(var i=0;i<rooms.length;i++){
			            		if(!chatUser.isSearchField(rooms[i])){
								   continue;
								}
			            		tds+=chatUser.formatTwoRow((rooms[i].gagEndDate? timeObjectUtil.formatterDateTime(rooms[i].gagEndDate) : ''),chatUser.isGroupTypeSearch() && i<rooms.length-1);
			            	}
							return tds;
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
		
		/**
		 * 微信组用户级别查询条件不可操作。
		 */
		$("#chatUserGroupId").bind("change", function(){
			var isWeichat = $(this).val().indexOf("wechat") !== -1;
			if(isWeichat){
				$("#userList_clientGroup").val("");
			}
			$("#userList_clientGroup").prop("disabled", isWeichat)
			
		});
		$("#chatUserGroupId").trigger("change");
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+chatUser.gridId).datagrid('reload');
	},
	/**
	 * 功能：导出记录
	 */
	exportRecord : function(){
		var path = basePath+ '/chatUserController/exportRecord.do?'+$("#chatUser_queryForm").serialize();
		window.location.href = path;
	},
	/**
	 * 用户设置
	 */
	userSetting:function(_this){
		$("#chatUser_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/chatUserController/toUserSetting.do?type='+$(_this).attr("t")+"&groupType="+$(_this).attr("groupType")
				+"&memberId="+$(_this).attr("memberId")+"&valueUser="+$(_this).attr("valueUser")+"&vipUser="+$(_this).attr("vipUser")
				+"&valueUserRemark="+$(_this).attr("valueUserRemark")+"&vipUserRemark="+$(_this).attr("vipUserRemark"));
		var submitUrl =  formatUrl(basePath + '/chatUserController/userSetting.do');
		goldOfficeUtils.openEditorDialog({
			title : '用户设置',
			width : 350,
			height : 140,
			href : url,
			iconCls : 'ope-redo',
			handler : function(){    //提交时处理
				if($("#userSettingForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'userSettingForm',
						onSuccess : function(data){   //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								$("#myWindow").dialog("close");
								chatUser.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),'设置成功!','info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'设置失败','error');
							}
						}
					});
				}
			}
		});
	},
	/**
	 * 用户解除绑定
	 * @param _this
	 */
	userUnbind : function(_this){
		$("#chatUser_datagrid").datagrid('unselectAll');
		goldOfficeUtils.ajax({
			url : formatUrl(basePath + '/chatUserController/userSetting.do'),
			data : {
				groupType : $(_this).attr("groupType"),
				memberId : $(_this).attr("memberId"),
				type : "unbind"
			},
			success: function(data) {
				if (data.success) {
					$("#chatUser_datagrid").datagrid('unselectAll');
					chatUser.refresh();
					$.messager.alert($.i18n.prop("common.operate.tips"),'解绑成功!','info');
				}else{
					$.messager.alert($.i18n.prop("common.operate.tips"),'解绑失败!','error');
				}
			}
		});
	},
	/**
	 * 功能：设置禁言
	 */
	setUserGag : function(obj){
		$("#chatUser_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/chatUserController/toUserGag.do?memberId='+trimStrVal($(obj).attr("memberId"))+"&groupId="+trimStrVal($(obj).attr("groupId"))+"&groupType="+trimStrVal($(obj).attr("groupType")));
		var submitUrl =  formatUrl(basePath + '/chatUserController/setUserGag.do');
		goldOfficeUtils.openEditorDialog({
			title : '设置禁言【用户：'+$(obj).attr("mobilePhone")+'；房间：'+($(obj).attr("groupName")||'所有')+'】',
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