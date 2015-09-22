/**
 * 摘要：产品管理公用js
 * @author Gavin.guo
 * @date   2015-06-08
 */
var product = {
	gridId : 'product_datagrid',
	init : function(){
		this.initTreeGrid();
		this.setEvent();
	},
	/**
	 * 功能：treeGrid初始化
	 */
	initTreeGrid : function(){
		$('#'+product.gridId).treegrid({
			idField : 'id', 
 			treeField : 'code',
			method : 'post',
			url : basePath+'/productController/treeGrid.do',
			nowrap: false,
			fit : true,
			fitColumns:true ,
			rownumbers: true,
			animate:true,
			collapsible:true,
			frozenColumns:[[
	            {title : $.i18n.prop("common.operate"),field : 'todo',width:250,fixed : false,formatter : function(value, rowData, rowIndex) {
				    	$("#product_datagrid_rowOperation a").each(function(){
				    		$(this).attr("id",rowData.id);
				    		$(this).attr("parentCode",rowData.parentCode);
				    		if(rowData.status == 0){ //禁用
				    			$(this).filter("[data-options*=ope-redo]").show();
				    			$(this).filter("[data-options*=ope-undo]").hide();
				    		}else{
				    			$(this).filter("[data-options*=ope-redo]").hide();
				    			$(this).filter("[data-options*=ope-undo]").show();
				    		}
						});
				    	return $("#product_datagrid_rowOperation").html();
	           	}},
			    {title:'产品编号',field:'code',width:250}   	   					/**产品编号*/
			]],
			columns:[[
			    {title : 'id',field:'id',hidden : true},
			    {title : 'parentCode',field:'parentCode',hidden : true},
			    {title : '产品名称',field:'name',width:200},   					/**产品名称*/
			    {title : 'type',field:'type',width:100,hidden : true},       	/**1: 父 2: 子*/
			    {title : '状态',field:'status',width:150,formatter : function(value, rowData, rowIndex) {
			    	if (value == 0) {
						return '禁用';
					} else {
						return '启用';
					}
				}}
			]],
			// 此方法主要用于异步处理
			onBeforeLoad:function(row,param){
				if(row){
					$(this).treegrid('options').url = basePath+'/productController/loadChildTreeGrid.do?parentCode='+row.code;
				} else {
					$(this).treegrid('options').url = basePath+'/productController/treeGrid.do?code='
													+$("#productCode").val()+"&name="+$("#productName").val();
				}
			},
			toolbar : '#product_datagrid_toolbar'
		});
	},
	setEvent:function(){
		$("#product_queryForm_search").on("click",function(){
			$('#'+product.gridId).treegrid({
				url : basePath+'/productController/treeGrid.do?code='+$("#productCode").val()
					          +"&name="+$("#productName").val(),
				method : 'post'
			});
		});
		$("#product_queryForm_reset").on("click",function(){
			$("#product_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：新增产品分类
	 */
	addProductType : function(){
		product.addCommon(1);
	},
	/**
	 * 功能：新增产品
	 */
	addProduct : function(){
		var node = $('#'+product.gridId).treegrid('getSelected');
		if(node == null || node.type == 2){
			$.messager.alert($.i18n.prop("common.operate.tips"),'请选择一个产品分类！','warning'); /**操作提示 请选择一个产品分类!*/
			return;
		}
		product.addCommon(2);
	},
	addCommon : function(type){
		var node = $('#'+product.gridId).treegrid('getSelected');
		if(node && type == 2){
			parentCode = node.code;
		}else{
			parentCode="";
		}
		var url = basePath + '/productController/add.do?type='+type+'&parentCode='+parentCode;
		var submitUrl =  basePath + '/productController/create.do';
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.add"),			/**添加记录*/
			height : 180,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				if($("#productAddForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'productAddForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								//如果新增的是产品分类，则刷新treeGrid,如果新增的是产品,则将新增的节点添加到选中的分类下面
								if(type == 1){                    
									$('#'+product.gridId).treegrid('reload');
								}
								else{
									if(d.obj != null){
										var node = $('#'+product.gridId).treegrid('getSelected');
										$('#'+product.gridId).treegrid('append',{
											parent: node.id,
											data: [{
												code : d.obj.code,
												parentCode:node.parentCode,
												name: d.obj.name,
												type : 2
											}]
										});
										$('#'+product.gridId).treegrid('reload',node.id);
									}
								}
								$("#myWindow").dialog("close");
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.addsuccess"),'info'); /**操作提示 新增成功!*/
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.addfail"),'error'); /**操作提示 新增失败!*/
							}
						}
					});
				}
			}
		});
	},
	/**
	 * 功能：修改
	 */
	edit : function(thisObj){
		$(thisObj).parent().click();//先触发父类点击事件使其选中
		var node = $('#'+product.gridId).treegrid('getSelected');
		var url = basePath + '/productController/edit.do'+'?id='+node.id+'&type='+node.type;
		var submitUrl =  basePath + '/productController/update.do';
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.edit"),	 /**修改记录*/
			height : 180,
			href : encodeURI(url),
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				if($("#productEditForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'productEditForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								$('#'+product.gridId).treegrid('update',{
									id: d.obj.productId,
									row: {
										name: d.obj.name
									}
								});
								$("#myWindow").dialog("close");
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.editsuccess"),'info');/**操作提示  修改成功!*/
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.editfail"),'error');  /**操作提示  修改失败!*/
							}
						}
					});
				}
			}
		});
	},
	/**
	 * 功能：删除
	 * @param id treeGrid 字段(字段之间以,分割)
	 */
	del : function(thisObj){
		$(thisObj).parent().click();//先触发父类点击事件使其选中
		var node = $('#'+product.gridId).treegrid('getSelected');
		var type = node.type , message = $.i18n.prop("common.confirmdel");  /**您确定要删除记录吗?*/
		if(type == 1){
			message = $.i18n.prop("menu.delete");	/**确定要删除该节点及对应的子节点吗?*/
		}
		goldOfficeUtils.deleteTreeGridOne({
			message : message,
			url : basePath + '/productController/oneDel.do',
			data : {
				id : node.id,
				type : type
			},
			success : function(data){
				if(data.success){
					$('#'+product.gridId).treegrid('remove',node.id);
					$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.delsuccess"),'info'); /**操作提示 删除成功!*/
				}else{
					$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.delfail"),'error');  /**操作提示 删除失败!*/
				}
			}
		});
	},
	/**
	 * 启用
	 */
	enable : function(obj){
		var enableUrl = formatUrl(basePath + '/productController/enable.do');
		$.messager.confirm("操作提示", '您确定要启用该产品吗?', function(r) {
			   if (r) {
				   goldOfficeUtils.ajax({
						url : enableUrl,
						data : {
							productId : obj.id,
							parentCode : $(obj).attr("parentCode")
						},
						success: function(data) {
							if(data.success) {
								$('#'+product.gridId).treegrid('reload');
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
		var disableUrl = formatUrl(basePath + '/productController/disable.do');
		$.messager.confirm("操作提示", '您确定要禁用该产品吗?', function(r) {
			   if (r) {
				   goldOfficeUtils.ajax({
						url : disableUrl,
						data : {
							productId : obj.id,
							parentCode : $(obj).attr("parentCode")
						},
						success: function(data) {
							if(data.success) {
								$('#'+product.gridId).treegrid('reload');
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
	 * 功能：刷新
	 */
	reload : function(){
		var node = $('#'+product.gridId).treegrid('getSelected');
		if(node){
			$('#'+product.gridId).treegrid('reload',node.id);
		}else{
			$('#'+product.gridId).treegrid('reload');
		}
	}
};
		
//初始化
$(function() {
	product.init();
});