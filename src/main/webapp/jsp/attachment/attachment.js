/**
 * 附件管理列表对应js
 * @author Alan.wu
 * @date   2015/03/26
 */
var attachment = {
	gridId : 'attachment_datagrid',
	init : function(){
		this.setEvent();
		this.initGrid();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			pagination :false,
			gridId : attachment.gridId,
			idField:"path",
			url : basePath+'/attachmentController/datagrid.do?createDateStr='+$("#attachment_createDateStr").val(),
			singleSelect:false,
			columns : [[
			            {title : 'path',field : 'path',checkbox : true},
			            {title :'操作',field : 'todo',formatter : function(value, rowData, rowIndex) {
							$("#attachment_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.path);
						    });
							return $("#attachment_datagrid_rowOperation").html();
						}},
			            {title :"名称",field : 'name'},
			            {title :"类型",field : 'type'},
			            {title :"路径",field : 'pathStr',formatter : function(value, rowData, rowIndex) {
							return rowData.path;
						}},
						{title :"大小",field : 'size',sortable : true},	
						{title :"状态",field :'status',sortable : true,formatter : function(value, rowData, rowIndex) {
						  return value;
						}}, 
						{title :"创建日期",field : 'createDate',sortable : true,formatter : function(value, rowData, rowIndex) {
							return value ? timeObjectUtil.longMsTimeConvertToDateTime(value) : '';
						}}
			]],
			toolbar : '#attachment_datagrid_toolbar'
		});  
	},
	setEvent:function(){
		// 列表查询
		$("#attachment_queryForm_search").on("click",function(){
			var queryParams = $('#'+attachment.gridId).datagrid('options').queryParams;
			$("#attachment_queryForm input[name],#attachment_queryForm select[name]").each(function(){
				queryParams[this.name] = $(this).val();
			});
			$('#'+attachment.gridId).datagrid({
				url : basePath+'/attachmentController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#attachment_queryForm_reset").on("click",function(){
			$("#attachment_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+attachment.gridId).datagrid('reload');
	},
	/**
	 * 功能：批量删除
	 */
	del : function(id){
		var url=formatUrl(basePath + '/attachmentController/del.do');
		if(isValid(id)){
			$("#attachment_datagrid").datagrid('unselectAll');
			goldOfficeUtils.deleteOne('attachment_datagrid',id,url);
		}else{
			goldOfficeUtils.deleteBatch('attachment_datagrid',url,"path");	
		}
	}
};
		
//初始化
$(function() {
	attachment.init();
});