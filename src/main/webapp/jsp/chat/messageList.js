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
			url : basePath+'/chatMessageController/datagrid.do?groupId='+$("#chatMessageGroupId").val()+"&status="+$("#chatMessageStatusId").val(),
			columns : [[
			            {title : 'id',field : 'id',checkbox : true},
			            /*{title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		*//**操作*//*
							$("#chatMessage_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.id);
						    });
							return $("#chatMessage_datagrid_rowOperation").html();
						}},*/
						{title : '手机号码',field:'mobilePhone'},
						{title : '账号',field:'accountNo'},
						{title : '昵称【ID号】',field : 'nicknameStr', formatter : function(value, rowData, rowIndex) {
							return rowData.nickname+"【"+rowData.userId+"】";
						}},
						{title : '用户类型',field : 'typeName',formatter : function(value, rowData, rowIndex) {
							return chatMessage.getComboxNameByCode("#chatMessageUserType",rowData.userType);
						}},
			            {title : '所属组',field : 'groupName',formatter : function(value, rowData, rowIndex) {
							return chatMessage.getComboxNameByCode("#chatMessageGroupId",rowData.groupId);
						}},
						{title : '用户来源',field : 'fromPlatform'},
						{title : '状态',field : 'statusStr',formatter : function(value, rowData, rowIndex) {
							var type=rowData.status;
							if(type==1){
								return "通过";
							}
							else if(type==2){
								return "拒绝";
							}else{
								return "待审批";
							}
						}},
						{title : '审核人',field : 'approvalUserNo'},
						{title : '发布时间隐藏字段',field : 'publishTime',hidden:true},
						{title : '用户id',field : 'userId',hidden:true},
						{title : '组',field : 'groupId',hidden:true},
						{title : '状态隐藏字段',field : 'status',hidden:true},
						{title : '发布时间',field : 'publishTimeStr',sortable : true,formatter : function(value, rowData, rowIndex) {
							return rowData.publishTime ? timeObjectUtil.longMsTimeConvertToDateTime(Number(rowData.publishTime.replace(/_.+/,""))) : '';
						}},
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
	 * 审核记录
	 */
	approval:function(_this){
		var rows = $("#"+chatMessage.gridId).datagrid('getSelections');
		var idArr = [],fuIdArr=[],groupId='',userId='',isWait=true;
		if(rows.length > 0){
			for(var i = 0; i < rows.length; i++) {
				groupId=rows[i]["groupId"];
				idArr.push(rows[i]["publishTime"]);
				userId=rows[i]["userId"];
				if(rows[i]["status"]!=0){
					isWait=false;
					break;
				}
				if(fuIdArr.length>0){
	                  var fArrStr=","+fuIdArr.join(",")+",";
	                  if(fArrStr.indexOf(","+userId+",")==-1){
	                      fuIdArr.push(userId);
	                  }
	              }else{
	                  fuIdArr.push(userId);
	              }
			}
			if(!isWait){
				alert("请选择待审核的记录！");
				return;
			}
			goldOfficeUtils.ajax({
				url : formatUrl(basePath + '/chatMessageController/approvalMsg.do'),
				data : {
					publishTimeArr : idArr.join(','),
					fuIdArr:fuIdArr.join(','),
					status:$(_this).attr("btnType"),
					groupId:groupId
				},
				success: function(data) {
					if(data.success) {
						$("#"+chatMessage.gridId).datagrid('unselectAll');
						$('#'+chatMessage.gridId).datagrid('reload');
						$.messager.alert("操作提示","执行成功!",'info');
					}else{
						$.messager.alert($.i18n.prop("common.operate.tips"),'执行失败，原因：'+data.msg,'error');
			    	}
				}
			});
		 }else{
			 $.messager.alert("操作提示", "请选择记录!"); 
		 }
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