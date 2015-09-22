/**
 * 投资社区--交易记录<BR>
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
var financeTradeRecord = {
	gridId : 'finance_tradeRecord_datagrid',
	products : {},
	init : function(){
		this.initData(function(){
			this.initGrid();
			this.setEvent();
		});
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : financeTradeRecord.gridId,
			singleSelect : false,
			idField:"tradeRecordId",
			sortName : 'tradeRecordId',
			sortOrder : "desc",
			url : basePath+'/finance/tradeRecordController/datagrid.do',
			columns : [[
			            {title : $.i18n.prop("finance.trade.memberId"),field : 'memberId',sortable : true},
			            {title : $.i18n.prop("finance.trade.memberNickName"),field : 'memberNickName'},
			            {title : $.i18n.prop("finance.trade.orderNo"),field : 'orderNo',sortable : true},
			            {title : $.i18n.prop("finance.trade.tradeTime"),field : 'tradeTime',sortable : true, formatter : timeObjectUtil.formatterDateTime},
			            {title : $.i18n.prop("finance.trade.operType"),field : 'operType', formatter : function(value, rowData, rowIndex) {
			            	return value == 1 ? $.i18n.prop("finance.trade.operTypeOpen") : $.i18n.prop("finance.trade.operTypeClose");
						}},
			            {title : $.i18n.prop("finance.trade.product"),field : 'productCode', formatter : function(value, rowData, rowIndex) {
			            	return financeTradeRecord.products.hasOwnProperty(value) ? financeTradeRecord.products[value].text : value;
						}},
						{title : $.i18n.prop("finance.trade.tradeDirection"),field : 'tradeDirection', formatter : function(value, rowData, rowIndex) {
			            	return value == 1 ? $.i18n.prop("finance.trade.tradeDirectionBuy") : $.i18n.prop("finance.trade.tradeDirectionSale");
						}},
						{title : '合约单位',field : 'contractPeriod'},
			            {title : $.i18n.prop("finance.trade.leverageRatio"),field : 'leverageRatio'},
			            {title : $.i18n.prop("finance.trade.volume"),field : 'volume'},
			            {title : $.i18n.prop("finance.trade.transactionPrice"),field : 'transactionPrice'},
			            {title : $.i18n.prop("finance.trade.profitLoss"),field : 'profitLoss', formatter : function(value, rowData, rowIndex) {
			            	return rowData["operType"] === 1 ? "---" : value; 
						}},
						{title : $.i18n.prop("finance.trade.relationOrderNo"),field : 'relationOrderNo'},
			            {title : $.i18n.prop("common.remark"),field : 'remark'},
			]],
			toolbar : '#finance_tradeRecord_datagrid_toolbar'
		});
	},
	setEvent:function(){
		$("#finance_tradeRecord_queryForm_search").on("click",function(){
			var queryParams = $('#'+financeTradeRecord.gridId).datagrid('options').queryParams;
			$("#finance_tradeRecord_queryForm input").add("#finance_tradeRecord_queryForm select").each(function(){
				queryParams[$(this).attr("name")] = $(this).val();
			})
			if(queryParams["timeStart"] !== ""){
				queryParams["timeStart"] =  formatStartDate(queryParams["timeStart"]);
			}
			if(queryParams["timeEnd"] !== ""){
				queryParams["timeEnd"] =  formatEndDate(queryParams["timeEnd"]);
			}
			//产品
			if(queryParams["productCode"] !== ""){
				var loc_prod = financeTradeRecord.products[queryParams["productCode"]];
				var loc_prods = [];
				if(loc_prod.attributes.isProduct){
					loc_prods[0] = loc_prod;
				}else{
					loc_prods = loc_prod["children"];
				}
				queryParams["queryProdCodes"] = convertArr2Obj(loc_prods, function(prod){return prod.id});
			}else{
				queryParams["queryProdCodes"] = '';
			}

			$('#'+financeTradeRecord.gridId).datagrid({
				url : basePath+'/finance/tradeRecordController/datagrid.do',
				pageNumber : 1
			});
		});
		$("#finance_tradeRecord_queryForm_reset").on("click",function(){
			$("#finance_tradeRecord_queryForm")[0].reset();
			$("#finance_tradeRecord_productCode").combotree('clear');
		});
	},

	/**
	 * 初始化产品列表
	 */
	initData : function(callback){
		goldOfficeUtils.ajax({
			url : basePath + '/productController/getProductTree.do',
			success: function(data) {
				visitTree(data, function(node){
					financeTradeRecord.products[node.id] = node;
				});
				var loc_treeJq = $('#finance_tradeRecord_queryForm input[name="productCode"]');
				var kk = loc_treeJq.combotree({
					valueField:'id',
					textField:'text'
				});
				loc_treeJq.combotree("loadData", data);
				callback.apply(financeTradeRecord);
			}
		});
	},
	
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+financeTradeRecord.gridId).datagrid('reload');
	}
};
		
//初始化
$(function() {
	financeTradeRecord.init();
});