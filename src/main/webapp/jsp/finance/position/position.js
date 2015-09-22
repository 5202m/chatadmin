/**
 * 投资社区--持仓管理<BR>
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
var financePosition = {
	gridId : 'finance_position_datagrid',
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
			gridId : financePosition.gridId,
			singleSelect : false,
			idField:"positionId",
			sortName : 'positionId',
			sortOrder : "desc",
			url : basePath+'/finance/positionController/datagrid.do',
			columns : [[
			            {title : $.i18n.prop("finance.trade.memberId"),field : 'memberId',sortable : true},
			            {title : $.i18n.prop("finance.trade.memberNickName"),field : 'memberNickName'},
			            {title : $.i18n.prop("finance.trade.orderNo"),field : 'orderNo',sortable : true},
			            {title : $.i18n.prop("finance.trade.openTime"),field : 'openTime',sortable : true, formatter : timeObjectUtil.formatterDateTime},
			            {title : $.i18n.prop("finance.trade.product"),field : 'productCode', formatter : function(value, rowData, rowIndex) {
			            	return financePosition.products.hasOwnProperty(value) ? financePosition.products[value].text : value;
						}},
						{title : $.i18n.prop("finance.trade.tradeDirection"),field : 'tradeDirection', formatter : function(value, rowData, rowIndex) {
			            	return value == 1 ? $.i18n.prop("finance.trade.tradeDirectionBuy") : $.i18n.prop("finance.trade.tradeDirectionSale");
						}},
						{title : '合约单位',field : 'contractPeriod'},
			            {title : $.i18n.prop("finance.trade.leverageRatio"),field : 'leverageRatio'},
			            {title : $.i18n.prop("finance.trade.volume"),field : 'volume'},
			            {title : $.i18n.prop("finance.trade.openPrice"),field : 'openPrice'},
			            {title : $.i18n.prop("finance.trade.earnestMoney"),field : 'earnestMoney'},
			            {title : $.i18n.prop("finance.trade.floatProfitLoss"),field : 'floatProfit', formatter : function(value, rowData, rowIndex) {
			            	return "---";
						}},
			            {title : $.i18n.prop("common.remark"),field : 'remark'},
			]],
			toolbar : '#finance_position_datagrid_toolbar'
		});
	},
	setEvent:function(){
		$("#finance_position_queryForm_search").on("click",function(){
			var queryParams = $('#'+financePosition.gridId).datagrid('options').queryParams;
			$("#finance_position_queryForm input").add("#finance_position_queryForm select").each(function(){
				queryParams[$(this).attr("name")] = $(this).val();
			});
			if(queryParams["timeStart"] !== ""){
				queryParams["timeStart"] =  formatStartDate(queryParams["timeStart"]);
			}
			if(queryParams["timeEnd"] !== ""){
				queryParams["timeEnd"] =  formatEndDate(queryParams["timeEnd"]);
			}
			//产品
			if(queryParams["productCode"] !== ""){
				var loc_prod = financePosition.products[queryParams["productCode"]];
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

			$('#'+financePosition.gridId).datagrid({
				url : basePath+'/finance/positionController/datagrid.do',
				pageNumber : 1
			});
		});
		$("#finance_position_queryForm_reset").on("click",function(){
			$("#finance_position_queryForm")[0].reset();
			$("#finance_position_productCode").combotree('clear');
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
					financePosition.products[node.id] = node;
				});
				var loc_treeJq = $('#finance_position_queryForm input[name="productCode"]');
				var kk = loc_treeJq.combotree({
					valueField:'id',
					textField:'text'
				});
				loc_treeJq.combotree("loadData", data);
				callback.apply(financePosition);
			}
		});
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+financePosition.gridId).datagrid('reload');
	}
};
		
//初始化
$(function() {
	financePosition.init();
});