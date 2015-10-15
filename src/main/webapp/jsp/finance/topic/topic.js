/**
 * 摘要：帖子管理公用js
 * @author Gavin.guo
 * @date   2015-06-05
 */
var topic = {
	gridId : 'topic_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : topic.gridId,
			idField : 'topicId',
			sortName : 'topicId',
			sortOrder : 'desc',
			singleSelect : false,
			url : basePath+'/topicController/datagrid.do',
			columns : [[
			            {title : 'topicId',field : 'topicId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#topic_datagrid_rowOperation a").each(function(){
								if(rowData.isTop == 1){    		//置顶
									$(this).filter("[data-options*=ope-redo]").linkbutton('disable');
									$(this).filter("[data-options*=ope-undo]").linkbutton('enable');
								}else{  //取消置顶
									$(this).filter("[data-options*=ope-redo]").linkbutton('enable');
									$(this).filter("[data-options*=ope-undo]").linkbutton('disable');
								}
								if(rowData.infoStatus == 1){    //有效
									$(this).filter("[data-options*=ope-ok]").linkbutton('disable');
									$(this).filter("[data-options*=ope-cancel]").linkbutton('enable');
								}else{  					   //无效
									$(this).filter("[data-options*=ope-ok]").linkbutton('enable');
									$(this).filter("[data-options*=ope-cancel]").linkbutton('disable');
								}
								$(this).attr("id",rowData.topicId);
								$(this).attr("isTop",rowData.isTop);
								$(this).attr("infoStatus",rowData.infoStatus);
								$(this).attr("subjectType",rowData.subjectType);
						    });
							return $("#topic_datagrid_rowOperation").html();
						}},
						{title : '标题',field : 'title',formatter : function(value, rowData, rowIndex) {
							return !isBlank(value) ? cutStr(value,40)  : '';
						}},
						{title : '内容',field : 'content',formatter : function(value, rowData, rowIndex) {
							return !isBlank(value) ? cutStr(value,20)  : '';
						}},
						{title : '发布时间',field : 'publishTime',formatter : function(value, rowData, rowIndex) {
							return value ? timeObjectUtil.longMsTimeConvertToDateTime(value) : '';
						}},
			            {title : '手机号',field : 'mobilePhone'},
						{title : '昵称',field : 'nickName'},
						{title : '发帖权限',field : 'topicAuthority',formatter : function(value, rowData, rowIndex) {
							if (value == 0) {
								return '正常';
							} else if(value == 1){
								return '禁止发帖';
							}else{
								return '正常';
							}
						}},
						{title : '是否推荐帖子',field : 'isRecommend',sortable : true,formatter : function(value, rowData, rowIndex) {
							if (value == 0) {
								return '否';
							} else if(value == 1){
								return '是';
							}else{
								return '否';
							}
						}},
						{title : '发帖设备',field : 'device'},
						{title : '主题分类code',field : 'subjectType',hidden:true},
						{title : '主题分类',field : 'subjectTypeTxt'},
						{title : '信息类别',field : 'infoType',formatter : function(value, rowData, rowIndex) {
							if (value == 1) {
								return '发帖';
							} else if(value == 2){
								return '回帖';
							}else{
								return '发帖';
							}
						}},
						{title : '信息状态',field : 'infoStatus',formatter : function(value, rowData, rowIndex) {
							if (value == 1) {
								return '有效';
							} else if(value == 2){
								return '无效';
							}else{
								return '有效';
							}
						}},
						{title : '点赞数',field : 'praiseCounts'},
						{title : '回复数',field : 'replyCounts'},
						{title : '举报人数',field : 'reportCounts'},
						{title : '审核结果',field : 'approvalResult',formatter : function(value, rowData, rowIndex) {
							if(rowData.infoStatus == 2){
								return '有效举报';
							}else{
								if(rowData.reportCounts == 0){
									return '--';
								}else{
									if (value == 0) {
										return '无效举报';
									} else if(value == 1){
										return '有效举报';
									}else{
										return '无效举报';
									}
								}
							}
						}},
						{title : '是否置顶',field : 'isTop',formatter : function(value, rowData, rowIndex) {
							if (value == 0) {
								return '否';
							} else if(value == 1){
								return '是';
							}else{
								return '否';
							}
						}}
			]],
			toolbar : '#topic_datagrid_toolbar'
		});
	},
	setEvent:function(){
		// 列表查询
		$("#topic_queryForm_search").on("click",function(){
			var subjectType = topic.getSelectSubjectType(),queryParams = $('#'+topic.gridId).datagrid('options').queryParams;
			$("#topic_queryForm input[name],#topic_queryForm select[name]").each(function(){
				queryParams[this.name] = $(this).val();
			});
			queryParams['subjectType'] = subjectType;
			$('#'+topic.gridId).datagrid({
				url : basePath+'/topicController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#topic_queryForm_reset").on("click",function(){
			$("#topic_queryForm")[0].reset();
			$('#topic_queryForm_subjectType').combotree('clear');
		});
	},
	/**
	 * 功能：获取选中的主题类别
	 */
	getSelectSubjectType : function(){
		var subjectTypeArr=[],combotree =$('#topic_queryForm_subjectType').combotree('tree');
		if(combotree.tree('getSelected') != null){
			var subSubjectTypeList = combotree.tree('getChildren',combotree.tree('getSelected').target);
			if(subSubjectTypeList != null && subSubjectTypeList.length > 0){
				for(var k in subSubjectTypeList){
					subjectTypeArr.push(subSubjectTypeList[k].id);
			    }
			}else{
				subjectTypeArr.push(combotree.tree('getSelected').id);
			}
			return subjectTypeArr.join(",");
		}else{
			return '';
		}
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		jumpRequestPage(formatUrl(basePath + '/topicController/add.do'));
	},
	/**
	 * 功能：修改
	 */
	edit : function(recordId){
		jumpRequestPage(formatUrl(basePath + '/topicController/edit.do?topicId='+recordId));
	},
	/**
	 * 功能：查看回帖
	 */
	reply : function(recordId){
		$("#topic_datagrid").datagrid('unselectAll');
		var loc_url = formatUrl(basePath + '/topicController/reply.do?topicId='+recordId);
		goldOfficeUtils.openSimpleDialog({
			title : '查看回复帖子',
			width:1000,
			height:600,
			href : loc_url + "&rows=20&page=1",
			iconCls : 'pag-view',
			onLoad : function(){
				$('#topic_reply_page').pagination({
					pageList : [10, 20, 30, 50, 100],
					onSelectPage:function(pageNumber, pageSize){
						$('#myWindow').dialog('refresh', loc_url + "&rows=" + pageSize + "&page=" + pageNumber);
					}
				});
			}
		});
	},
	
	/**
	 * 删除回帖
	 */
	replyDel : function(topicId, replyId, subReplyId){
		$.messager.confirm("操作提示", "您确定要删除记录吗?" , function(r) {
			   if (r) {
				   goldOfficeUtils.ajax({
						url : formatUrl(basePath + '/topicController/replyDel.do'),
						data : {
							topicId : topicId,
							replyId : replyId,
							subReplyId : subReplyId
						},
						success: function(data) {
							if (data.success) {
								$('#myWindow').dialog('refresh');
								$.messager.alert($.i18n.prop("common.operate.tips"),'删除成功!','info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'删除失败，原因：'+data.msg,'error');
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
		var url = formatUrl(basePath + '/topicController/batchDel.do');
		goldOfficeUtils.deleteBatch('topic_datagrid',url,'topicId');
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#topic_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/topicController/oneDel.do');
		goldOfficeUtils.deleteOne('topic_datagrid',recordId,url);
	},
	/**
	 * 功能：置顶(取消置顶)
	 */
	doTop : function(obj,type){
		var recordId =obj.id,isTop = $(obj).attr("isTop");
		if(isTop == type){
			alert("禁止该操作！");
			return;
		}
		getJson(formatUrl(basePath + '/topicController/update.do'),{"topicId":recordId,"isTop" : type},function(data){
			var msg = (type == 1 ? '置顶' : '取消置顶');
			if(data.success){
				$.messager.alert($.i18n.prop("common.operate.tips"),msg+'成功！','info');
				topic.refresh();
			}else{
				$.messager.alert($.i18n.prop("common.operate.tips"),msg+'失败，原因：'+data.msg,'error');
			}
		},true);
	},
	/**
	 * 功能：有效(无效)
	 */
	doShield : function(obj,type){
		var recordId =obj.id,infoStatus = $(obj).attr("infoStatus");
		if(infoStatus == type){
			alert("禁止该操作！");
			return;
		}
		getJson(formatUrl(basePath + '/topicController/update.do'),{"topicId":recordId,"infoStatus" : type},function(data){
			var msg = (type == 2 ? '信息状态设为无效' : '信息状态设为有效');
			if(data.success){
				$.messager.alert($.i18n.prop("common.operate.tips"),msg+'成功！','info');
				topic.refresh();
			}else{
				$.messager.alert($.i18n.prop("common.operate.tips"),msg+'失败，原因：'+data.msg,'error');
			}
		},true);
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
	 * 功能：推荐帖子到
	 */
	recommand : function(obj){
		$("#topic_datagrid").datagrid('unselectAll');
		var topicId = $(obj).attr("id"),subjectType = $(obj).attr("subjectType");
		var url = formatUrl(basePath + '/topicController/toRecommand.do'+'?topicId='+topicId+'&subjectType='+subjectType);
		var submitUrl =  formatUrl(basePath + '/topicController/doRecommand.do');
		goldOfficeUtils.openEditorDialog({
			title : '推荐帖子',
			height : 130,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				if($("#topicRecommandForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'topicRecommandForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								// 提交成功后先关闭弹出框,然后刷新表格,弹出对应的成功提示
								$("#myWindow").dialog("close");
								topic.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),'推荐帖子成功！','info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'推荐帖子失败，原因：'+d.msg,'error');
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
		$('#'+topic.gridId).datagrid('reload');
	},
	/**
	 * 功能：返回到主列表页面
	 */
	back : function(){
		jumpRequestPage(basePath + '/topicController/index.do');
	}
};
		
//初始化
$(function() {
	topic.init();
});