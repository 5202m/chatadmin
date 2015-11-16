/**
 * 摘要：APP版本管理公用js
 * @author Gavin.guo
 * @date   2015-09-15
 */
var appVersion = {
	gridId : 'appVersion_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : appVersion.gridId,
			idField : 'appVersionId',
			sortName : 'appVersionId',
			sortOrder : 'desc',
			singleSelect : false,
			url : basePath+'/appVersionController/datagrid.do',
			columns : [[
			            {title : 'appVersionId',field : 'appVersionId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#appVersion_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.appVersionId);
						    });
							return $("#appVersion_datagrid_rowOperation").html();
						}},
			            {title : '平台',field : 'platform',formatter : function(value, rowData, rowIndex) {
							if(value == 1) {
								return 'Android平台';
							}else if(value == 2){
								return 'IOS平台';
							}else{
								return 'Android平台';
							}
						}},
			            {title : '版本号',field : 'versionNo'},	
			            {title : '版本名称',field : 'versionName'},
						{title : '是否强制更新',field : 'isMustUpdate',sortable : true,formatter : function(value, rowData, rowIndex) {
							if(value == 1) {
								return '非强制更新';
							}else if(value == 2){
								return '强制更新';
							}else{
								return '非强制更新';
							}
						}},
						{title : '创建时间',field : 'createDate',formatter : function(value, rowData, rowIndex) {
							return value ? timeObjectUtil.formatterDate(value) : '';
						}}
			]],
			toolbar : '#appVersion_datagrid_toolbar'
		});
	},
	/**
	 * 初始化上传控件
	 */
	initUploadFile : function(){
		goldOfficeUtils.uploadFile({
			'fileId' : 'appPathId',
			'formData' : {'fileDir' : 'app'},
			'fileSizeLimit' : 20*1024*1024,
			'fileTypeDesc': '只能上传*.apk;*.ipa类型的文件',
			'fileTypeExts' : '*.apk;*.ipa',
			'uploader' : basePath+'/uploadController/upload.do',
			'onUploadSuccess' : function(file, data, response){
				var d = $.parseJSON(data);
				if(d.success){
					alert(file.name + ' 上传成功！');
					if(d.obj != null){
						$("#currentAppPath").val(d.obj);
						$("#saveAppPath").val(d.obj);
					}
				}else{
					alert(file.name + d.msg);
				}
			}
		});
	},
	setEvent:function(){
		// 列表查询
		$("#appVersion_queryForm_search").on("click",function(){
			var queryParams = $('#'+appVersion.gridId).datagrid('options').queryParams;
			$("input[name], select[name]","#appVersion_queryForm").each(function(){
				queryParams[this.name] = $(this).val();
			});
			$('#'+appVersion.gridId).datagrid({
				url : basePath+'/appVersionController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#appVersion_queryForm_reset").on("click",function(){
			$("#appVersion_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		jumpRequestPage(formatUrl(basePath + '/appVersionController/add.do'));
	},
	/**
	 * 功能：修改
	 */
	edit : function(recordId){
		jumpRequestPage(formatUrl(basePath + '/appVersionController/edit.do?appVersionId='+recordId));
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+appVersion.gridId).datagrid('reload');
	},
	/**
	 * 功能：批量删除
	 */
	batchDel : function(){
		var url = formatUrl(basePath + '/appVersionController/batchDel.do');
		goldOfficeUtils.deleteBatch('appVersion_datagrid',url,"appVersionId");	
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#appVersion_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/appVersionController/oneDel.do');
		goldOfficeUtils.deleteOne('appVersion_datagrid',recordId,url);
	},
	/**
	 * 功能：返回到主列表页面
	 */
	back : function(){
		jumpRequestPage(basePath + '/appVersionController/index.do');
	}
};
		
//初始化
$(function() {
	appVersion.init();
});