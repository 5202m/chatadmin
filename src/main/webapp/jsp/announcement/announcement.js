/**
 * 摘要：应用管理公用js
 * @author Gavin.guo
 * @date   2015-03-16
 */
var app = {
	gridId : 'app_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : app.gridId,
			idField : 'appId',
			sortName : 'appId',
			sortOrder : 'desc',
			singleSelect : false,
			url : basePath+'/appController/datagrid.do',
			columns : [[
			            {title : 'appId',field : 'appId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {		/**操作*/
							$("#app_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.appId);
						    });
							return $("#app_datagrid_rowOperation").html();
						}},
						{title : '应用类别',field : 'appCategory',formatter : function(value, rowData, rowIndex) {
							return isBlank(value) ? "" : value.name;
						}},
			            {title : '编号',field : 'code'},
			            {title : '标题',field : 'title'},	
			            {title : '显示的顺序',field : 'sorting'},
						{title : '分数',field : 'score',formatter : function(value, rowData, rowIndex) {
							if (value == null || value == '') {
								return "0.0";
							} else {
								return toFix1Decimal(value);
							}
						}},
						{title : '用户量',field : 'subscribers',formatter : function(value, rowData, rowIndex) {
							if (value == null) {
								return '0'+" 位用户";
							} else {
								return value+" 位用户";
							}
						}},
						{title : '应用状态',field : 'status',sortable : true,formatter : function(value, rowData, rowIndex) {
							if (value == 0) {
								return '禁用';
							} else {
								return '启用';
							}
						}},
						{title : '是否默认可见',field : 'isDefaultVisibility',sortable : true,formatter : function(value, rowData, rowIndex) {
							if (value == 1) {
								return '可见';
							} else {
								return '不可见';
							}
						}},
						{title : '应用是否收费',field : 'isCharge',sortable : true,formatter : function(value, rowData, rowIndex) {
							if (value == 0) {
								return '免费';
							} else {
								return '收费';
							}
						}}
			]],
			toolbar : '#app_datagrid_toolbar'
		});
	},
	/**
	 * 初始化上传控件
	 */
	initUploadFile : function(){
		goldOfficeUtils.uploadFile({
			'fileId' : 'logoImageId',
			'formData' : {'imageDir' : 'pic'},
			'fileSizeLimit' : 10*1024*1024,
			'fileTypeDesc': '只能上传*.jpg;*.gif;*.png;*.jpeg类型的图片',
			'fileTypeExts' : '*.jpg;*.gif;*.png;*.jpeg',
			'uploader' : basePath+'/uploadController/uploadImage.do',
			'onUploadSuccess' : function(file, data, response){
				var d = $.parseJSON(data);
				if(d.success){
					alert(file.name + ' 上传成功！');
					if(d.obj != null){
						$("#currentImageFilePath").val("/"+d.obj);
						$("#sourceImagePath").val("/"+d.obj);
						$("#cutedImagePath").val("/"+d.obj);
						$("#saveImagePath").val("/"+d.obj);
					}
				}else{
					alert(file.name + d.msg);
				}
			}
		});
	},
	/**
	 * 功能：初始化Editor
	 */
	initUEditor : function(uEditorId){
		UE.getEditor(uEditorId,{
			initialFrameWidth : '100%',
			initialFrameHeight : 350,
			wordCount : false
		});
	},
	setEvent:function(){
		// 列表查询
		$("#app_queryForm_search").on("click",function(){
			var code = $("#code").val();                        //编号
			var title = $("#title").val();                   	//标题
			var appCategoryId = $("#appCategoryId").val();		//应用类别
			var status = $("#status").val();                   	//状态
			var queryParams = $('#'+app.gridId).datagrid('options').queryParams;
			queryParams['code'] = code;
			queryParams['title'] = title;
			queryParams['appCategory.appCategoryId'] = appCategoryId;
			queryParams['status'] = status;
			$('#'+app.gridId).datagrid({
				url : basePath+'/appController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#app_queryForm_reset").on("click",function(){
			$("#app_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		jumpRequestPage(formatUrl(basePath + '/appController/add.do'));
	},
	/**
	 * 功能：修改
	 */
	edit : function(recordId){
		jumpRequestPage(formatUrl(basePath + '/appController/edit.do?appId='+recordId));
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+app.gridId).datagrid('reload');
	},
	/**
	 * 功能：批量删除
	 */
	batchDel : function(){
		var url = formatUrl(basePath + '/appController/batchDel.do');
		goldOfficeUtils.deleteBatch('app_datagrid',url,"appId");	
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#app_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/appController/oneDel.do');
		goldOfficeUtils.deleteOne('app_datagrid',recordId,url);
	},
	/**
	 * 功能：返回到主列表页面
	 */
	back : function(){
		jumpRequestPage(basePath + '/appController/index.do');
	}
};
		
//初始化
$(function() {
	app.init();
});