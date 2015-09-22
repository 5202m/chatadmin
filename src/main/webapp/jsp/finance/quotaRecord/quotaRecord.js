/**
 * 投资社区--额度记录<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月5日 <BR>
 * Description : <BR>
 * <p>
 * 
 * </p>
 */
var financeQuotaRecord = {
	gridId : 'finance_quotaRecord_datagrid',
	tradeItems : {},
	init : function(){
		this.initData();
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 初始化数据
	 */
	initData : function(){
		$("#finance_quotaRecord_queryForm select[name='item'] option").each(function(){
			var key = $(this).val();
			if(!!key){
				financeQuotaRecord.tradeItems[key] = $(this).text();
			}
		});
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : financeQuotaRecord.gridId,
			singleSelect : false,
			idField:"quotaRecordId",
			sortName : 'quotaRecordId',
			sortOrder : "desc",
			url : basePath+'/finance/quotaRecordController/datagrid.do',
			columns : [[
			            {title : $.i18n.prop("finance.trade.memberId"),field : 'memberId',sortable : true},
			            {title : $.i18n.prop("finance.trade.memberNickName"),field : 'memberNickName'},
			            {title : $.i18n.prop("finance.trade.orderNo"),field : 'orderNo',sortable : true},
			            {title : $.i18n.prop("finance.trade.tradeTime"),field : 'tradeTime',sortable : true, formatter : timeObjectUtil.formatterDateTime},
			            {title : $.i18n.prop("finance.trade.item"),field : 'item', formatter : function(value, rowData, rowIndex) {
			            	return financeQuotaRecord.tradeItems.hasOwnProperty(value) ? financeQuotaRecord.tradeItems[value] : value;
						}},
						{title : $.i18n.prop("finance.trade.beforeTradeBalance"),field : 'beforeTradeBalance'},
						{title : $.i18n.prop("finance.trade.income"),field : 'income', formatter : function(value, rowData, rowIndex) {
							return value <= 0 ? '---' : value;
						}},
						{title : $.i18n.prop("finance.trade.expenditure"),field : 'expenditure', formatter : function(value, rowData, rowIndex) {
							return value <= 0 ? '---' : value;
						}},
						{title : $.i18n.prop("finance.trade.afterTradeBalance"),field : 'afterTradeBalance'},
			            {title : $.i18n.prop("common.remark"),field : 'remark'},
			]],
			toolbar : '#finance_quotaRecord_datagrid_toolbar'
		});
	},
	setEvent:function(){
		$("#finance_quotaRecord_queryForm_search").on("click",function(){
			var queryParams = $('#'+financeQuotaRecord.gridId).datagrid('options').queryParams;
			$("#finance_quotaRecord_queryForm input").add("#finance_quotaRecord_queryForm select").each(function(){
				queryParams[$(this).attr("name")] = $(this).val();
			})
			if(queryParams["timeStart"] !== ""){
				queryParams["timeStart"] =  formatStartDate(queryParams["timeStart"]);
			}
			if(queryParams["timeEnd"] !== ""){
				queryParams["timeEnd"] =  formatEndDate(queryParams["timeEnd"]);
			}

			$('#'+financeQuotaRecord.gridId).datagrid({
				url : basePath+'/finance/quotaRecordController/datagrid.do',
				pageNumber : 1
			});
		});
		$("#finance_quotaRecord_queryForm_reset").on("click",function(){
			$("#finance_quotaRecord_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+financeQuotaRecord.gridId).datagrid('reload');
	}
};
		
//初始化
$(function() {
	financeQuotaRecord.init();
});