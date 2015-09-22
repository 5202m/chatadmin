/**
 * 投资社区--产品配置管理<BR>
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
var financeProdSetting = {
	gridId : 'finance_prodSetting_datagrid',
	products : {},
	prodTree : null,
	init : function(){
		this.initValidator();
		this.initProdTree(function(){
			this.initGrid();
			this.setEvent();
		});
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : financeProdSetting.gridId,
			singleSelect : false,
			idField:"productCode",
			sortName : 'productCode',
			url : basePath+'/finance/prodSettingController/datagrid.do',
			columns : [[
						{title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {   	/**操作*/
							$("#finance_prodSetting_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.productSettingId);
					    		if(rowData.status == 0){ 	//禁用
					    			$(this).filter("[data-options*=ope-redo]").show();
					    			$(this).filter("[data-options*=ope-undo]").hide();
					    		}else{
					    			$(this).filter("[data-options*=ope-redo]").hide();
					    			$(this).filter("[data-options*=ope-undo]").show();
					    		}
							});
					    	return $("#finance_prodSetting_datagrid_rowOperation").html();
					    	
						}},
						{title : 'productSettingId',field:'productSettingId',hidden : true},
			            {title : '产品名称',field : 'productCode', formatter : function(value, rowData, rowIndex) {
			            	if(financeProdSetting.products.hasOwnProperty(value)){
			            		return financeProdSetting.products[value].text;
			            	}else{
			            		return value;
			            	}
						}},
			            {title : '报价小数位',field : 'priceDecimal'},
			            {title : '合约单位',field : 'contractPeriod'},
			            {title : '杠杆倍数',field : 'leverageRatio'},
			            {title : '最小交易手数',field : 'minTradeHand'},
			            {title : '交易模式',field : 'tradeModel',formatter : function(value, rowData, rowIndex) {
			            	return value == 1 ? $.i18n.prop("finance.product.tradeModelMarket") : $.i18n.prop("finance.product.tradeModelCurr");
						}},
			            {title : '状态',field : 'status',formatter : function(value, rowData, rowIndex) {
							return value == 1 ? $.i18n.prop("common.enabled") : $.i18n.prop("common.disabled");
						}},
			            {title : '备注',field : 'remark'}
			]],
			toolbar : '#finance_prodSetting_datagrid_toolbar'
		});
	},
	setEvent:function(){
		$("#finance_prodSetting_queryForm_search").on("click",function(){
			var queryParams = $('#'+financeProdSetting.gridId).datagrid('options').queryParams;
			var loc_prod = $('#finance_prodSetting_queryForm input[name="productCode"]').val();
			loc_prod = financeProdSetting.products[loc_prod];
			var loc_prods = [];
			if(loc_prod != null && loc_prod.attributes != null){
				if(loc_prod.attributes.isProduct){
					loc_prods[0] = loc_prod;
				}else{
					loc_prods = loc_prod["children"];
				}
				queryParams["queryProdCodes"] = convertArr2Obj(loc_prods, function(prod){return prod.id});
			}else{
				queryParams["queryProdCodes"] = '';
			}
			$('#'+financeProdSetting.gridId).datagrid({
				url : basePath+'/finance/prodSettingController/datagrid.do',
				pageNumber : 1
			});
		});
		$("#finance_prodSetting_queryForm_reset").on("click",function(){
			//$("#finance_prodSetting_queryForm")[0].reset();
			$("#finance_prodSetting_productCode").combotree('clear');
		});
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+financeProdSetting.gridId).datagrid('reload');
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		var url = formatUrl(basePath + '/finance/prodSettingController/preAdd.do');
		var submitUrl =  formatUrl(basePath + '/finance/prodSettingController/add.do');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.add"),				/**添加记录*/
			height : 280,
			href : url,
			iconCls : 'pag-add',
			onLoad : function(){
				var loc_treeJq = $('#prodSettingAddForm input[name="productCode"]');
				var kk = loc_treeJq.combotree({
					valueField:'id',
					textField:'text',
					required:true,
					validType:'productCodeProdSetting',
					missingMessage:$.i18n.prop('finance.product.valid.product')
				});
				loc_treeJq.combotree("loadData", financeProdSetting.prodTree);
			},
			handler : function(){   //提交时处理
				try{
					if($("#prodSettingAddForm").form('validate')){
						goldOfficeUtils.ajaxSubmitForm({
							url : submitUrl,
							formId : 'prodSettingAddForm',
							onSuccess : function(data){  //提交成功后处理
								var d = $.parseJSON(data);
								if (d.success) {
									// 提交成功后先关闭弹出框,然后刷新表格,弹出对应的成功提示
									$("#myWindow").dialog("close");
									financeProdSetting.refresh();
									$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.addsuccess"),'info');   /**操作提示  新增成功!*/
								}else{
									$.messager.alert($.i18n.prop("common.operate.tips"),'新增失败，原因：'+d.msg,'error');   	/**操作提示 新增失败!*/
								}
							}
						});
					}
				}
				catch(e){
					alert(e);
				}
			}
		});
	},
	/**
	 * 功能：修改
	 * @param item
	 */
	edit : function(item){
		var loc_id = item.id;
		var url = formatUrl(basePath + '/finance/prodSettingController/preEdit.do?productSettingId=' + loc_id);
		var submitUrl =  formatUrl(basePath + '/finance/prodSettingController/edit.do');
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.edit"),			/**修改记录*/
			height : 280,
			href : url,
			iconCls : 'pag-edit',
			onLoad : function(){
				var loc_prodCode = $('#prodSettingEditForm input[name="productCode"]').val();
				$("#prodSettingEdit_prodName").text(financeProdSetting.products[loc_prodCode].text);
			},
			handler : function(){   //提交时处理
				if($("#prodSettingEditForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'prodSettingEditForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								// 提交成功后先关闭弹出框,然后刷新表格,弹出对应的成功提示
								$("#myWindow").dialog("close");
								financeProdSetting.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.editsuccess"),'info');	/**操作提示  修改成功!*/
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'修改失败，原因：'+d.msg,'error');	/**操作提示  修改失败!*/
							}
						}
					});
				}
			}
		});
	},
	/**
	 * 启用
	 */
	enable : function(obj){
		var enableUrl = formatUrl(basePath + '/finance/prodSettingController/enable.do');
		$.messager.confirm("操作提示", '您确定要启用该产品设置吗?', function(r) {
			   if (r) {
				   goldOfficeUtils.ajax({
						url : enableUrl,
						data : {
							productSettingId : obj.id
						},
						success: function(data) {
							if(data.success) {
								financeProdSetting.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),'启用成功!','info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'启用失败，原因：'+data.msg,'error');
					    	}
						}
					});
				}
		});
	},
	/**
	 * 禁用
	 */
	disable : function(obj){
		var disableUrl = formatUrl(basePath + '/finance/prodSettingController/disable.do');
		$.messager.confirm("操作提示", '您确定要禁用该产品设置吗?', function(r) {
			   if (r) {
				   goldOfficeUtils.ajax({
						url : disableUrl,
						data : {
							productSettingId : obj.id
						},
						success: function(data) {
							if(data.success) {
								financeProdSetting.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"),'禁用成功!','info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'禁用失败，原因：'+data.msg,'error');
					    	}
						}
					});
				}
		});
	},
	/**
	 * 功能：删除单行
	 * @param item
	 */
	del : function(item){
		var productSettingId = item.id;
		var url = formatUrl(basePath + '/finance/prodSettingController/delete.do');
		goldOfficeUtils.deleteOne(financeProdSetting.gridId, productSettingId, url, "确认删除吗？");
	},
	/**
	 * 初始化校验器
	 */
	initValidator : function(){
		$.fn.validatebox.defaults.rules['leverageRatioProdSetting'] = { // 判断杠杆比例
			validator : function(value) {
				return /^[1-9]\d*(#[1-9]\d*)*$/.test(value);
			},
			message : $.i18n.prop('finance.product.valid.leverageRatio')
		};
		
		$.fn.validatebox.defaults.rules['minTradeHandProdSetting'] = { // 最小手数
			validator : function(value) {
				if(/^[1-9]\d?$/.test(value)){
					return true;
				}
				return false;
			},
			message : $.i18n.prop('finance.product.valid.minTradeHand')
		};
		
		$.fn.validatebox.defaults.rules['productCodeProdSetting'] = { // 产品
			validator : function(value, param) {
				var prodCode = $("#prodSettingAddForm input[name='productCode']").val();
				return financeProdSetting.products.hasOwnProperty(prodCode) && financeProdSetting.products[prodCode].attributes.isProduct;
			},
			message : $.i18n.prop('finance.product.valid.productOnly')
		};
	},
	
	/**
	 * 初始化产品树
	 * @param callback
	 */
	initProdTree : function(callback){
		goldOfficeUtils.ajax({
			url : basePath + '/productController/getProductTree.do',
			success: function(data) {
				financeProdSetting.prodTree = data;
				visitTree(financeProdSetting.prodTree, function(node){
					financeProdSetting.products[node.id] = node;
				});
				var loc_treeJq = $('#finance_prodSetting_queryForm input[name="productCode"]');
				var kk = loc_treeJq.combotree({
					valueField:'id',
					textField:'text'
				});
				loc_treeJq.combotree("loadData", financeProdSetting.prodTree);
				callback.apply(financeProdSetting);
			}
		});
	}
};
		
//初始化
$(function() {
	financeProdSetting.init();
});