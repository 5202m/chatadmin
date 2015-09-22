/**
 * 摘要：消息推送公用js
 * @author Gavin.guo
 * @date   2015-07-21
 */
var pushMessage = {
	gridId : 'pushMessage_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : pushMessage.gridId,
			idField : 'pushMessageId',
			sortName : 'createDate',
			sortOrder : 'desc',
			singleSelect : false,
			url : basePath+'/pushMessageController/datagrid.do',
			columns : [[
			            {title : 'pushMessageId',field : 'pushMessageId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#pushMessage_datagrid_rowOperation a").each(function(){
								if(rowData.pushStatus == 0){    		//未推送
									$(this).filter("[data-options*=ope-redo]").show();
									$(this).filter("[data-options*=ope-edit]").show();
								}else{  								//已推送
									$(this).filter("[data-options*=ope-redo]").hide();
									$(this).filter("[data-options*=ope-edit]").hide();
								}
								$(this).attr("id",rowData.pushMessageId);
								$(this).attr("pushStatus",rowData.pushStatus);
								$(this).attr("pushDate",rowData.pushDate);
						    });
							return $("#pushMessage_datagrid_rowOperation").html();
						}},
						{title : '会员Id',field : 'memberId',hidden:true},
						{title : '标题',field : 'title'},
						{title : '推送时间',field : 'pushDate',formatter : function(value, rowData, rowIndex) {
							return value ? timeObjectUtil.longMsTimeConvertToDateTime(rowData.pushDate)  : '';
						}},
						{title : '语言',field : 'lang',formatter : function(value, rowData, rowIndex) {
							if (value == 'zh') {
								return '简体';
							} else if(value == 'tw'){
								return '繁体';
							}else if(value == 'en'){
								return '英文';
							}else{
								return '简体';
							}
						}},
						{title : '应用平台',field : 'platform',formatter : function(value, rowData, rowIndex) {
							return formatPlatfrom(value,platformJsonStr);
						}},
						{title : '通知方式',field : 'tipType',formatter : function(value, rowData, rowIndex) {
							if(value != ''){
								var vArr = value.split(",");
								var vStr = "";
								for(var i=0;i<vArr.length;i++){
									var ov = vArr[i];
									if(ov == 1){
										vStr+='系统通知中心#';
									}else if(ov == 2){
										vStr+='小秘书#';
									}else if(ov == 3){
										vStr+='首次登陆时弹窗#';
									}
								}
								return vStr.indexOf("#") != -1 ? vStr.substring(0,vStr.length-1) : vStr;
							}
						}},
						{title : '消息类型',field : 'messageType',formatter : function(value, rowData, rowIndex) {
							if (value == 1) {
								return '自定义';
							} else if(value == 2){
								return '文章资讯';
							}else if(value == 3){
								return '关注订阅';
							}else if(value == 4){
								return '评论提醒';
							}else if(value == 5){
								return '公告';
							}else if(value == 6){
								return '反馈';
							}else{
								return '自定义';
							}
						}},
						{title : '推送状态',field : 'pushStatus',sortable : true,formatter : function(value, rowData, rowIndex) {
							if (value == 0) {
								return '未推送';
							}else if(value == 1){
								return '待推送';
							}else if(value == 2){
								return '发送成功 ';
							}else if(value == 3){
								return '发送失败 ';
							}else if(value == 4){
								return '取消推送 ';
							}else{
								return '未推送';
							}
						}},
						{title : '是否有效',field : 'valid',formatter : function(value, rowData, rowIndex) {
							if (value == 1) {
								return '有效';
							} else if(value == 0){
								return '无效';
							}else{
								return '未推送';
							}
						}}
			]],
			toolbar : '#pushMessage_datagrid_toolbar'
		});
	},
	setEvent:function(){
		// 列表查询
		$("#pushMessage_queryForm_search").on("click",function(){
			var queryParams = $('#'+pushMessage.gridId).datagrid('options').queryParams;
			$("#pushMessage_queryForm input[name],#pushMessage_queryForm select[name]").each(function(){
				queryParams[this.name] = $(this).val();
			});
			var platform = $("#pushMessage_queryForm_platform").combotree("getValues");
			if(isBlank(platform)){
				queryParams["pushMessage_platform"] = '';
			}
			$('#'+pushMessage.gridId).datagrid({
				url : basePath+'/pushMessageController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#pushMessage_queryForm_reset").on("click",function(){
			$("#pushMessage_queryForm")[0].reset();
			$("#pushMessage_queryForm_platform").combotree('clear');
		});
		
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		jumpRequestPage(formatUrl(basePath + '/pushMessageController/add.do'));
	},
	/**
	 * 功能：修改
	 */
	edit : function(recordId){
		jumpRequestPage(formatUrl(basePath + '/pushMessageController/'+recordId+'/edit.do'));
	},
	/**
	 * 功能：推送
	 */
	push : function(obj){
		var pushStatus = $(obj).attr("pushStatus");
		if(pushStatus == 1 || pushStatus == 2){
			alert("禁止该操作！");
			return;
		}
		if($(obj).attr("pushDate") < new Date().getTime()){
			alert("对不起，推送时间小于当前系统时间，该消息将不能被推送！");
			return;
		}
		var pushUrl = formatUrl(basePath + '/pushMessageController/push.do');
		$.messager.confirm("操作提示", '您确定要推送该条消息吗?', function(r) {
			   if (r) {
				   goldOfficeUtils.ajax({
						url : pushUrl,
						data : {
							pushMessageId : obj.id,
							pushStatus : 1,
							pushDateStr : $(obj).attr("pushDate")
						},
						success: function(data) {
							if(data.success) {
								$("#"+pushMessage.gridId).datagrid('unselectAll');
								$('#'+pushMessage.gridId).datagrid('reload');
								$.messager.alert($.i18n.prop("common.operate.tips"),'提交成功!','info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'提交失败，原因：'+data.msg,'error');
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
		var url = formatUrl(basePath + '/pushMessageController/del.do');
		goldOfficeUtils.deleteBatch('pushMessage_datagrid',url,'pushMessageId');
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#pushMessage_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/pushMessageController/del.do');
		goldOfficeUtils.deleteOne('pushMessage_datagrid',recordId,url);
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+pushMessage.gridId).datagrid('reload');
	},
	/**
	 * 功能：初始化Editor
	 */
	initUEditor : function(uEditorId){
		UE.getEditor(uEditorId,{
			initialFrameWidth : '100%',
			initialFrameHeight : 350,
			wordCount : false
		});
	},
	/**
	 * 功能：获取父节点(递归处理)
	 */
	getParentNodeList : function(categoryArr,combotree,node){
		var parentNode = combotree.tree('getParent',node);
		if(parentNode){
			categoryArr.push(parentNode.id);
			pushMessage.getParentNodeList(categoryArr,combotree,parentNode.target);
		}
	},
	/**
	 * 功能：返回到主列表页面
	 */
	back : function(){
		jumpRequestPage(basePath + '/pushMessageController/index.do');
	}
};
		
//初始化
$(function() {
	pushMessage.init();
});