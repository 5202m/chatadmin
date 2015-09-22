/**
 * 摘要：统计管理公用js
 * @author Gavin.guo
 * @date   2015-09-07
 */
var memberBalance = {
	gridId : 'memberBalance_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : memberBalance.gridId,
			idField : 'memberBalanceId',
			sortName : 'percentYield',
			sortOrder : 'desc',
			singleSelect : false,
			url : basePath+'/memberBalanceController/datagrid.do',
			columns : [[
			            {title : 'memberBalanceId',field : 'memberBalanceId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
			            	$("#memberBalance_datagrid_rowOperation a").each(function(){
			            		if(rowData.isRecommend == 1){    		//推荐
									$(this).filter("[data-options*=ope-redo]").linkbutton('disable');
									$(this).filter("[data-options*=ope-undo]").linkbutton('enable');
								}else{  							    //取消推荐
									$(this).filter("[data-options*=ope-redo]").linkbutton('enable');
									$(this).filter("[data-options*=ope-undo]").linkbutton('disable');
								}
			            		$(this).attr("id",rowData.memberBalanceId);
								$(this).attr("memberId",rowData.memberId);
								$(this).attr("isRecommend",rowData.isRecommend);
						    });
							return $("#memberBalance_datagrid_rowOperation").html();
						}},
						{title : 'memberId',field : 'memberId',hidden: true},
						{title : '手机号',field : 'mobilePhone'},
						{title : '昵称',field : 'nickName'},
						{title : '收益率(%)',field : 'percentYield',sortable : true,formatter : function(value, rowData, rowIndex) {
							if(value){
								if(value > 0){
									return '<font color="green">'+toFix2Decimal(value)+'</font>';
								}else{
									return '<font color="red">'+toFix2Decimal(value)+'</font>';
								}
							}else{
								return '0.00';
							}
						}},
						{title : '胜率(%)',field : 'rateWin',sortable : true,formatter : function(value, rowData, rowIndex) {
							if(value){
								if(value > 0){
									return '<font color="green">'+toFix2Decimal(value)+'</font>';
								}else{
									return '<font color="red">'+toFix2Decimal(value)+'</font>';
								}
							}else{
								return '0.00';
							}
						}},
						{title : '关注数',field : 'attentionCount',sortable : true},
						{title : '粉丝数',field : 'beAttentionCount',sortable : true},
						{title : '发帖数',field : 'topicCount',sortable : true},
						{title : '回帖数',field : 'replyCount',sortable : true},
						{title : '评论数',field : 'commentCount',sortable : true},
						{title : '喊单数',field : 'shoutCount',sortable : true},
						{title : '被跟单数',field : 'beShoutCount',sortable : true},
						{title : '盈利次数',field : 'timesProfit',sortable : true},
						{title : '是否推荐用户',field : 'isRecommend',formatter : function(value, rowData, rowIndex) {
							if (value == 0) {
								return '否';
							} else if(value == 1){
								return '是';
							}else{
								return '否';
							}
						}}
			]],
			toolbar : '#memberBalance_datagrid_toolbar'
		});
	},
	setEvent:function(){
		// 列表查询
		$("#memberBalance_queryForm_search").on("click",function(){
			var queryParams = $('#'+memberBalance.gridId).datagrid('options').queryParams;
			$("#memberBalance_queryForm input[name]").each(function(){
				queryParams[this.name] = $(this).val();
			});
			$('#'+memberBalance.gridId).datagrid({
				url : basePath+'/memberBalanceController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#memberBalance_queryForm_reset").on("click",function(){
			$("#memberBalance_queryForm")[0].reset();
		});
		
	},
	/**
	 * 功能：重设会员资产
	 */
	rebuild : function(obj){
		$.messager.confirm("操作提示", '确定要重设会员资产吗？', function(r) {
			if(r){
				var recordId = $(obj).attr("memberId");
				getJson(formatUrl(basePath + '/memberBalanceController/rebuild.do'),{"memberId":recordId},function(data){
					if(data.success){
						$.messager.alert($.i18n.prop("common.operate.tips"),'成功重设会员资产为100000USB！','info');
						memberBalance.refresh();
					}else{
						$.messager.alert($.i18n.prop("common.operate.tips"),'重设会员资产失败，原因：'+data.msg,'error');
					}
				},true);    
		    }
		});
	},
	/**
	 * 功能：推荐(取消推荐)
	 */
	recommand : function(obj,type){
		var recordId =obj.id,isRecommend = $(obj).attr("isRecommend");
		if(isRecommend == type){
			alert("禁止该操作！");
			return;
		}
		var recordId = $(obj).attr("memberId");
		getJson(formatUrl(basePath + '/memberBalanceController/recommand.do'),{"memberId":recordId,"isRecommend" : type},function(data){
			var msg = (type == 1 ? '推荐' : '取消推荐');
			if(data.success){
				$.messager.alert($.i18n.prop("common.operate.tips"),msg+'成功！','info');
				memberBalance.refresh();
			}else{
				$.messager.alert($.i18n.prop("common.operate.tips"),msg+'失败，原因：'+data.msg,'error');
			}
		},true);
	},
	/**
	 * 功能：同步数据
	 */
	sysnData : function(){
		getJson(formatUrl(basePath + '/memberBalanceController/sysnData.do'),{},function(data){
			if(data.success){
				$.messager.alert($.i18n.prop("common.operate.tips"),'同步成功！','info');
				memberBalance.refresh();
			}else{
				$.messager.alert($.i18n.prop("common.operate.tips"),'同步失败，原因：'+data.msg,'error');
			}
		},true);
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+memberBalance.gridId).datagrid('reload');
	}
};
		
//初始化
$(function() {
	memberBalance.init();
});