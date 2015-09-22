/**
 * 摘要：会员管理公用js
 * @author Gavin.guo
 * @date   2015-03-18
 */
var member = {
	gridId : 'member_datagrid',
	init : function(){
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : member.gridId,
			idField:"memberId",
			singleSelect : false,
			url : basePath+'/memberController/datagrid.do',
			columns : [[
			            {title : 'memberId',field : 'memberId',checkbox : true},
			            {title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) { /**操作*/
							$("#member_datagrid_rowOperation a").each(function(){
								$(this).attr("id",rowData.memberId);
						    });
							return $("#member_datagrid_rowOperation").html();
						}},
			            {title : '手机号',field : 'mobilePhone'},                   					/**手机号*/
			          /*  {title : '邮箱',field : 'email'},												*//**邮箱*//*
			            {title : '是否VIP',field : 'isVip',formatter : function(value, rowData, rowIndex) {
							if (value == 0) {
								return '否';
							} else {
								return '是';
							}
						}},											*//**是否VIP*//*
			            {title : '总在线时长',field : 'totalOnlineTime'},							    *//**总在线时长*//*
			            {title : '总积分',field : 'totalCreditScore'},							    *//**总积分*//*
			            {title : '等级',field : 'level'},	*/						    			    /**等级*/
			            {title : '用户状态',field : 'status',formatter : function(value, rowData, rowIndex) {/**用户状态*/
							if (value == 1) {
								return '启用';
							} else {
								return '禁用';
							}
						}},						    						
						/*{title : '登录IP',field : 'loginIp'},											*//**登录IP*//*	
						{title : '登录次数',field : 'loginTimes'},										*//**登录次数*//*
						{title : '最后登录时间',field : 'lastLoginDate',formatter : function(value, rowData, rowIndex) {*//**最后登录时间*//*
							return value ? timeObjectUtil.longMsTimeConvertToDateTime(value) : '';
						}},*/
						{title : '创建时间',field : 'createDate',formatter : function(value, rowData, rowIndex) {		/**创建时间*/
							return value ? timeObjectUtil.longMsTimeConvertToDateTime(value) : '';
						}}								
			]],
			toolbar : '#member_datagrid_toolbar'
		});
	},
	/**
	 * 功能：初始化文件上传
	 */
	initUploadFile : function(){
		goldOfficeUtils.uploadFile({
			'fileId' : 'avatarImageId',
			'formData' : {'fileDir' : 'pic/header/finance'},
			'fileSizeLimit' : 10*1024*1024,
			'fileTypeDesc': '只能上传*.jpg;*.gif;*.png;*.jpeg类型的图片',
			'fileTypeExts' : '*.jpg;*.gif;*.png;*.jpeg',
			'uploader' : basePath+'/uploadController/upload.do',
			'onUploadSuccess' : function(file, data, response){
				var d = $.parseJSON(data);
				if(d.success){
					alert(file.name + ' 上传成功！');
					if(d.obj != null){
						$("#currentImageFilePath").val("/"+d.obj);
						$("#sourceImagePath").val("/"+d.obj);
						$("#cutedImagePath").val("/"+d.obj);
					}
				}else{
					alert(file.name + d.msg);
				}
			}
		});
	},
	setEvent:function(){
		// 列表查询
		$("#member_queryForm_search").on("click",function(){
			var mobilePhone = $("#mobilePhone").val();                       //账号
			var status = $("#status  option:selected").val();      			 //状态
			var queryParams = $('#'+member.gridId).datagrid('options').queryParams;
			queryParams['mobilePhone'] = mobilePhone;
			queryParams['status'] = status;
			$('#'+member.gridId).datagrid({
				url : basePath+'/memberController/datagrid.do',
				pageNumber : 1
			});
		});
		// 重置
		$("#member_queryForm_reset").on("click",function(){
			$("#member_queryForm")[0].reset();
		});
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		jumpRequestPage(formatUrl(basePath + '/memberController/add.do'));
	},
	/**
	 * 功能：修改
	 * @param recordId   dataGrid行Id
	 */
	edit : function(recordId){
		jumpRequestPage(formatUrl(basePath + '/memberController/edit.do?memberId='+recordId));
	},
	/**
	 * 功能：批量删除
	 */
	batchDel : function(){
		var url = formatUrl(basePath + '/memberController/batchDel.do');
		goldOfficeUtils.deleteBatch('member_datagrid',url,"memberId");	
	},
	/**
	 * 功能：删除单行
	 * @param recordId  dataGrid行Id
	 */
	del : function(recordId){
		$("#member_datagrid").datagrid('unselectAll');
		var url = formatUrl(basePath + '/memberController/oneDel.do');
		goldOfficeUtils.deleteOne('member_datagrid',recordId,url);
	},
	/**
	 * 功能：重置密码
	 */
	resetPwd : function(recordId){
		jumpRequestPage(formatUrl(basePath + '/memberController/resetPwd.do?memberId='+recordId));
	},
	/**
	 * 功能：保存重置密码
	 */
	onResetPwd : function(){
		if($("#memberResetPwdForm").form('validate')){
			$.messager.progress();   		  				//提交时，加入进度框
			var serializememberResetPwdFormData = $("#memberResetPwdForm").serialize();
			getJson(formatUrl(basePath + '/memberController/saveResetPwd.do'),serializememberResetPwdFormData,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("重置密码成功 !");
					jumpRequestPage(basePath + '/memberController/index.do');
				}else{
					alert("重置密码失败，错误信息："+data.msg);
				}
			},true);
		}
	},
	/**
	 * 功能：返回到主列表页面
	 */
	back : function(){
		jumpRequestPage(basePath + '/memberController/index.do');
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+member.gridId).datagrid('reload');
	}
};
		
//初始化
$(function() {
	member.init();
});