/**
 * 摘要：晒单
 * @author Henry.cao
 * @date   2016-06-22
 */
var chatShowTrade = {
	gridId : 'show_trade_datagrid',
	opType : '',
	init : function(){
		this.opType = $("#userOpType").val();
		this.initGrid();
		this.setEvent();
		this.setUserList();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : chatShowTrade.gridId,
			idField:"userId",
			sortName : 'createDate',
			sort:'desc',
			singleSelect : false,
			url : basePath+'/chatShowTradeController/datagrid.do?opType=' + chatShowTrade.opType,
			columns : [[
			            {title : 'id',field : 'id',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#show_trade_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.id);
						    });
							return $("#show_trade_datagrid_rowOperation").html();
						}},
						
			            {title : '分析师账号',field : 'boUser.userNo',formatter : function(value, rowData, rowIndex) {
							return rowData.boUser.userNo;
						}},                   	
			            {title : $.i18n.prop("user.name"),field : 'boUser.userName',sortable : true,formatter : function(value, rowData, rowIndex) {
							return rowData.boUser.userName;
						}},
						{title : '房间类别',field : 'groupTypeName',formatter : function(value, rowData, rowIndex) {
							return chatShowTrade.getDictNameByCode("#showTrade_groupType_select",rowData.groupType);
						}},
						
			            {title : "头像",field : 'boUser.avatar',sortable : true ,formatter : function(value, rowData, rowIndex) {
							return '<img src="'+rowData.boUser.avatar+'" style="height:60px;">';
						}},
						
						{title : "微信号",field : 'boUser.wechatCode',sortable : true ,formatter : function(value, rowData, rowIndex) {
							return rowData.boUser.wechatCode;
						}},
						
						{title : "胜率",field : 'boUser.winRate',sortable : true ,formatter : function(value, rowData, rowIndex) {
							return rowData.boUser.wechatCode;
						}},
			            
						{title : "获利",field : 'profit',sortable : true , formatter : function(value, rowData, rowIndex) {
							return rowData.profit == '' ? '持仓中' : rowData.profit}},		
						{title : "晒单图片",field : 'tradeImg' , formatter : function(value, rowData, rowIndex) {
							return rowData.tradeImg ? '<img src="'+rowData.tradeImg+'" style="height:60px;">' : '没有图片'}},
						{title : "晒单时间", field : 'showDate' , formatter : function(value, rowData, rowIndex) {
							return rowData.showDate ? timeObjectUtil.longMsTimeConvertToDateTime(value) : '';
						}},
						{title : "备注", field : 'remark'}
						
			]],
			toolbar : '#show_trade_datagrid_toolbar'
		});
	},
	setEvent:function(){
		// 列表查询
		$("#show_trade_queryForm_search").on("click",function(){
			var userNo = $("#userNo").val();                     //账号
			if(userNo == '请选择'){
				userNo = '';
			}
			
			var groupType = $("#showTrade_groupType_select").val();  
			var queryParams = $('#'+chatShowTrade.gridId).datagrid('options').queryParams;
			queryParams['userNo'] = userNo;
			queryParams['groupType'] = groupType;
			$('#'+chatShowTrade.gridId).datagrid({
				url : basePath+'/chatShowTradeController/datagrid.do?opType=' + chatShowTrade.opType,
				pageNumber : 1
			});
		});
		// 重置
		$("#show_trade_queryForm_reset").on("click",function(){
			$("#show_trade_queryForm")[0].reset();
		});
	},
	//显示用户列表
	setUserList:function(){
		var authorListId="authorList_";
	     $("select[name=authorAvatar]").attr("id",authorListId).attr("name",authorListId);
	     chatShowTrade.setAuthorList(authorListId);
	},
	setUserEdit:function(value){
		var authorListId="authorEdit_";
	     $("select[name=authorAvatar]").attr("id",authorListId).attr("name",authorListId);
	     chatShowTrade.setAuthorList(authorListId);
	     $('#'+authorListId).combogrid('setValue', value);
	},
	setUserAdd:function(){
		var authorListId="authorAdd_";
	     $("select[name=authorAvatar]").attr("id",authorListId).attr("name",authorListId);
	     chatShowTrade.setAuthorList(authorListId);
	},
	/**
	 * 功能：查看
	 * @param recordId   dataGrid行Id
	 */
	view : function(recordId){
		$("#show_trade_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/chatShowTradeController/'+recordId+'/view.do');
		goldOfficeUtils.openSimpleDialog({
			title : $.i18n.prop("common.operatetitle.view"),       /**查看记录*/
			height : 300 ,
			href : url ,
			iconCls : 'pag-view'
		});
	},

	/**
	 * 功能：增加
	 */
	add : function(){
		
		var url = formatUrl(basePath + '/chatShowTradeController/add.do?opType=' + chatShowTrade.opType);
		var submitUrl =  formatUrl(basePath + '/chatShowTradeController/create.do');
		goldOfficeUtils.openEditorDialog({
			dialogId : "editWindow",
			title : $.i18n.prop("common.operatetitle.add"),			/**添加记录*/
			width : 650,
			height : 260,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				if($("#showTradeAddFrom").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'showTradeAddFrom',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if(d.success) {
								$("#editWindow").dialog("close");
								chatShowTrade.refresh();
								$.messager.alert("操作提示",'新增晒单成功');
							}else{
								$.messager.alert('新增晒单失败，原因：'+d.msg,'error');
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
		$("#show_trade_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/chatShowTradeController/'+recordId+'/edit.do?opType=' + chatShowTrade.opType);
		var submitUrl =  formatUrl(basePath + '/chatShowTradeController/update.do');
		goldOfficeUtils.openEditorDialog({
			dialogId : "editWindow",
			title : $.i18n.prop("common.operatetitle.edit"),   /**修改记录*/
			width : 650,
			height : 260,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){    //提交时处理
				if($("#showTradeEditFrom").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'showTradeEditFrom',
						onSuccess : function(data){   //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								$("#editWindow").dialog("close");
								chatShowTrade.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.editsuccess"),'info');/**操作提示  修改成功!*/
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'修改失败，原因：'+d.msg,'error');  /**操作提示  修改失败!*/
							}
						}
					});
				}
			},
			onLoad : function(){
				var avatarSrc=$("#currentAvatarPath").val();
				var loc_defaultFlag = false;
				if(isValid(avatarSrc)){
					$("#user_header_default div img").each(function(){
						if(this.src==avatarSrc){
							$("#user_header_default div input[name=defaultHeader][t="+$(this).attr("t")+"]").click();
							loc_defaultFlag = true;
						}
					});
					if(!loc_defaultFlag){
						$("#user_header_tab").tabs("select", "本地上传");
					}
				}
			}
		});
	},
	/**
	 * 提取名称
	 */
	getDictNameByCode:function(id,code){
		return $(id).find("option[value='"+code+"']").text();
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+chatShowTrade.gridId).datagrid('reload');
	},
	
	/**
	 * 功能：批量删除
	 */
	batchDel : function(){
		var url = formatUrl(basePath + '/chatShowTradeController/batchDel.do');
		goldOfficeUtils.deleteBatch('show_trade_datagrid',url,"id");	
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#show_trade_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/chatShowTradeController/oneDel.do');
		goldOfficeUtils.deleteOne('show_trade_datagrid',recordId,url);
	},
	/**
	 * 设置作者选择列表
	 * @param id
	 */
	setAuthorList:function(id){
		
		$('#'+id).combogrid({
		    idField:'userName',
		    textField:'userName',
		    url:basePath+'/userController/getAnalystList.do?hasOther=true',
		    columns:[[
		        {field : 'userNo', hidden:true},
		        {field : 'author_Key_id',hidden:true,formatter : function(value, rowData, rowIndex) {
					return 'author_Key_id';
				}},
		        {field : 'userName',title : '姓名', width:100},
				{field : 'position', hidden:true},
		        {field : 'avatar',title : '头像',width:40,formatter : function(value, rowData, rowIndex) {
		        	if(isBlank(value)){
		        		return '';
		        	}
					return '<img src="'+value+'" style="height:35px;width:35px;"/>';
				}}
		    ]],
		    onSelect:function(rowIndex, rowData){
		       var lang=id.replace("authorList_","");
			   $('#selectUserArea input[name=userNo]').val(rowData.userNo);
			   $('#selectUserArea input[name=name]').val(rowData.userName);
			   $('#selectUserArea input[name=position]').val(rowData.position);
			   $('#selectUserArea input[name=avatar]').val(rowData.avatar);
		    },
		    onChange:function(val){
		    	$('#selectUserArea input[name=userNo]').val(val);
		    }
		}); 
	},
};
		
//初始化
$(function() {
	chatShowTrade.init();
	
});