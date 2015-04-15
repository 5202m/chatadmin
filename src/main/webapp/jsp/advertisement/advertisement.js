/**
 * 摘要：广告管理公用js
 * @author Gavin.guo
 * @date   2015-04-14
 */
var advertisement = {
	gridId : 'advertisement_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : advertisement.gridId,
			idField : 'advertisementId',
			sortName : 'advertisementId',
			sortOrder : 'desc',
			singleSelect : false,
			url : basePath+'/advertisementController/datagrid.do',
			columns : [[
			            {title : 'advertisementId',field : 'advertisementId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {	/**操作*/
							$("#advertisement_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.advertisementId);
						    });
							return $("#advertisement_datagrid_rowOperation").html();
						}},
			            {title : '编号',field : 'code'},
			            {title : '标题',field : 'title'},	
			            {title : '链接URL',field : 'imgUrl'},	
						{title : '广告应用平台',field : 'platform',formatter : function(value, rowData, rowIndex) {
							if(value == 1){
								return  '微信平台';
							}else{
								return '其它平台';
							}
						}},
						{title : '状态',field : 'status',sortable : true,formatter : function(value, rowData, rowIndex) {
							if (value == 0) {
								return '禁用';
							} else {
								return '启用';
							}
						}}
			]],
			toolbar : '#advertisement_datagrid_toolbar'
		});
	},
	/**
	 * 初始化上传控件
	 */
	initUploadFile : function(){
		goldOfficeUtils.uploadFile({
			'fileId' : 'imgId',
			'formData' : {'imageDir' : 'advertisement'},
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
	setEvent:function(){
		// 列表查询
		$("#advertisement_queryForm_search").on("click",function(){
			var queryParams = $('#'+advertisement.gridId).datagrid('options').queryParams;
			$("#advertisement_queryForm input[name],#advertisement_queryForm select[name]").each(function(){
				queryParams[this.name] = $(this).val();
			});
			
			$('#'+advertisement.gridId).datagrid({
				url : basePath+'/advertisementController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#advertisement_queryForm_reset").on("click",function(){
			$("#advertisement_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		jumpRequestPage(formatUrl(basePath + '/advertisementController/add.do'));
	},
	/**
	 * 功能：修改
	 */
	edit : function(recordId){
		jumpRequestPage(formatUrl(basePath + '/advertisementController/edit.do?advertisementId='+recordId));
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+advertisement.gridId).datagrid('reload');
	},
	/**
	 * 功能：批量删除
	 */
	batchDel : function(){
		var url = formatUrl(basePath + '/advertisementController/batchDel.do');
		goldOfficeUtils.deleteBatch('advertisement_datagrid',url,"advertisementId");	
	},
	/**
	 * 功能：删除单行
	 */
	del : function(recordId){
		$("#advertisement_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/advertisementController/oneDel.do');
		goldOfficeUtils.deleteOne('advertisement_datagrid',recordId,url);
	},
	/**
	 * 功能：返回到主列表页面
	 */
	back : function(){
		jumpRequestPage(basePath + '/advertisementController/index.do');
	}
};
		
//初始化
$(function() {
	advertisement.init();
});