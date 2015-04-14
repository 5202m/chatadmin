/**
 * 摘要：栏目管理公用js
 * @author Alan.wu
 * @date   2015/3/17
 */
var category = {
	gridId : 'category_datagrid',
	init : function(){
		this.initTreeGrid();
		this.setEvent();
	},
	/**
	 * 功能：treeGrid初始化
	 */
	initTreeGrid : function(){
		$('#'+category.gridId).treegrid({
			idField : 'id', 
 			treeField : 'name',
			method : 'post',
			height:'100px',
			url : basePath+'/categoryController/treeGrid.do',
			nowrap: false,
			fit : true,
			fitColumns:true ,
			rownumbers: true,
			animate:true,
			collapsible:true,
			frozenColumns:[[
	            {title : $.i18n.prop("common.operate"),field : 'todo',width:250,fixed : false,formatter : function(value, rowData, rowIndex) {
		               var parentId=rowData.parentId;
	            	    $("#category_datagrid_rowOperation a").each(function(){
				    		$(this).attr("id",rowData.id);
				    		$(this).attr("parentId",parentId);
						});
				    	return $("#category_datagrid_rowOperation").html();
	           	}},
			    {title:'名称',field:'name',width:180}
			]],
			columns:[[
			    {title : 'id',field:'id',hidden : true},
			    {title : 'parentPath',field:'parentPath',hidden : true},
			    {title : 'parentId',field:'parentId',hidden : true},
			    {title : '编号',field:'code'},
			    {title : '状态',field:'status'},
			    {title : '排序',field:'sort'}
			]],
			toolbar : '#category_datagrid_toolbar'
		});
	},
	setEvent:function(){
		$("#category_queryForm_search").on("click",function(){
			$('#'+category.gridId).treegrid({
				url : basePath+'/categoryController/treeGrid.do?code='+$("#categoryCode").val()
					          +"&name="+$("#categoryName").val()+"&status="+$("#categoryStatus").val(),
				method : 'post'
			});
		});
		$("#category_queryForm_reset").on("click",function(){
			$("#category_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：新增栏目
	 */
	add: function(thisObj){
		var parentId="";
		if(thisObj){//新增子栏目，所选列表id为需要新增的记录的父类id
			parentId=$(thisObj).attr("id");
			parentId=isValid(parentId)?'?parentId='+parentId:""; 
		}
		var url = basePath + '/categoryController/view.do'+parentId;
		var submitUrl =  basePath + '/categoryController/create.do';
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.add"),			/**添加记录*/
			height : 200,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				if($("#categoryViewForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'categoryViewForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								$('#'+category.gridId).treegrid('reload');
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
		var parentId=$(thisObj).attr("parentId");
		if(isBlank(parentId)){
			parentId="";
		}
		var url = basePath + '/categoryController/view.do'+'?id='+$(thisObj).attr("id")+'&parentId='+parentId;
		var submitUrl =  basePath + '/categoryController/update.do';
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.edit"),	 /**修改记录*/
			height : 200,
			href : encodeURI(url),
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				if($("#categoryViewForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'categoryViewForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								$('#'+category.gridId).treegrid('update',{
									id: d.obj.id,
									row: {
										name: d.obj.name,
										code: d.obj.code,
										status: d.obj.status,
										sort: d.obj.sort
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
	 * @param id treeGrid 字段
	 */
	del : function(thisObj){
		var idStr=$(thisObj).attr("id");
		goldOfficeUtils.deleteTreeGridOne({
			message : "确定要删除该节点吗(注：如有对应的子节点，子节点也会被删除)?",
			url : basePath + '/categoryController/del.do',
			data : {
				id : idStr
			},
			success : function(data){
				if(data.success){
					$('#'+category.gridId).treegrid('remove',idStr);
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
		$('#'+category.gridId).treegrid('reload');
	}
};
		
//初始化
$(function() {
	category.init();
});