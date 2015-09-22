/**
 * 摘要：主题分类公用js
 * @author Gavin.guo
 * @date   2015-06-08
 */
var subjectType = {
	gridId : 'subjectType_datagrid',
	init : function(){
		this.initTreeGrid();
		this.setEvent();
	},
	/**
	 * 功能：treeGrid初始化
	 */
	initTreeGrid : function(){
		$('#'+subjectType.gridId).treegrid({
			idField : 'id', 
 			treeField : 'code',
			method : 'post',
			url : basePath+'/subjectTypeController/treeGrid.do',
			nowrap: false,
			fit : true,
			fitColumns:true ,
			rownumbers: true,
			animate:true,
			collapsible:true,
			frozenColumns:[[
	            {title : $.i18n.prop("common.operate"),field : 'todo',width:170,fixed : false,formatter : function(value, rowData, rowIndex) {
				    	$("#subjectType_datagrid_rowOperation a").each(function(){
				    		$(this).attr("id",rowData.id);
						});
				    	return $("#subjectType_datagrid_rowOperation").html();
	           	}},
			    {title:'主题编号',field:'code',width:250}   	   				/**主题编号*/
			]],
			columns:[[
			    {title : 'id',field:'id',hidden : true},
			    {title : 'parentCode',field:'parentCode',hidden : true},
			    {title : '主题名称',field:'name',width:250},   				/**主题名称*/
			    {title : 'type',field:'type',width:100,hidden : true}
			]],
			// 此方法主要用于异步处理
			onBeforeLoad:function(row,param){
				if(row){
					$(this).treegrid('options').url = basePath+'/subjectTypeController/loadChildTreeGrid.do?parentCode='+row.code;
				} else {
					$(this).treegrid('options').url = basePath+'/subjectTypeController/treeGrid.do?code='
													+$("#subjectTypeCode").val()+"&name="+$("#subjectTypeName").val();
				}
			},
			toolbar : '#subjectType_datagrid_toolbar'
		});
	},
	setEvent:function(){
		$("#subjectType_queryForm_search").on("click",function(){
			$('#'+subjectType.gridId).treegrid({
				url : basePath+'/subjectTypeController/treeGrid.do?code='+$("#subjectTypeCode").val()
					          +"&name="+$("#subjectTypeName").val(),
				method : 'post'
			});
		});
		$("#subjectType_queryForm_reset").on("click",function(){
			$("#subjectType_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：新增主题分类
	 */
	addsubjectTypeType : function(){
		subjectType.addCommon(1);
	},
	/**
	 * 功能：新增主题
	 */
	addsubjectType : function(){
		var node = $('#'+subjectType.gridId).treegrid('getSelected');
		if(node == null || node.type == 2){
			$.messager.alert($.i18n.prop("common.operate.tips"),'请选择一个主题分类！','warning'); /**操作提示 请选择一个主题分类!*/
			return;
		}
		subjectType.addCommon(2);
	},
	addCommon : function(type){
		var node = $('#'+subjectType.gridId).treegrid('getSelected');
		if(node && type == 2){
			parentCode = node.code;
		}else{
			parentCode="";
		}
		var url = basePath + '/subjectTypeController/add.do?type='+type+'&parentCode='+parentCode;
		var submitUrl =  basePath + '/subjectTypeController/create.do';
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.add"),			/**添加记录*/
			height : 180,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				if($("#subjectTypeAddForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'subjectTypeAddForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								//如果新增的是主题分类，则刷新treeGrid,如果新增的是主题,则将新增的节点添加到选中的分类下面
								if(type == 1){
									$('#'+subjectType.gridId).treegrid('reload');
								}
								else{
									if(d.obj != null){
										var node = $('#'+subjectType.gridId).treegrid('getSelected');
										$('#'+subjectType.gridId).treegrid('append',{
											parent: node.id,
											data: [{
												code : d.obj.code,
												parentCode:node.parentCode,
												name: d.obj.name,
												type : 2
											}]
										});
										$('#'+subjectType.gridId).treegrid('reload',node.id);
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
		var node = $('#'+subjectType.gridId).treegrid('getSelected');
		var url = basePath + '/subjectTypeController/edit.do'+'?id='+node.id+'&type='+node.type;
		var submitUrl =  basePath + '/subjectTypeController/update.do';
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.edit"),	 /**修改记录*/
			height : 180,
			href : encodeURI(url),
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				if($("#subjectTypeEditForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'subjectTypeEditForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								$('#'+subjectType.gridId).treegrid('update',{
									id: d.obj.subjectTypeId,
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
		var node = $('#'+subjectType.gridId).treegrid('getSelected');
		var type = node.type , message = $.i18n.prop("common.confirmdel");  /**您确定要删除记录吗?*/
		if(type == 1){
			message = $.i18n.prop("menu.delete");	/**确定要删除该节点及对应的子节点吗?*/
		}
		goldOfficeUtils.deleteTreeGridOne({
			message : message,
			url : basePath + '/subjectTypeController/oneDel.do',
			data : {
				id : node.id,
				type : type
			},
			success : function(data){
				if(data.success){
					$('#'+subjectType.gridId).treegrid('remove',node.id);
					$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.delsuccess"),'info'); /**操作提示 删除成功!*/
				}else{
					$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.delfail"),'error');  /**操作提示 删除失败!*/
				}
			}
		});
	},
	/**
	 * 功能：刷新
	 */
	reload : function(){
		var node = $('#'+subjectType.gridId).treegrid('getSelected');
		if(node){
			$('#'+subjectType.gridId).treegrid('reload',node.id);
		}else{
			$('#'+subjectType.gridId).treegrid('reload');
		}
	}
};
		
//初始化
$(function() {
	subjectType.init();
});