/**
 * 投资社区--成员管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月11日 <BR>
 * Description : <BR>
 * <p>
 * 
 * </p>
 */
var financeFinanceUser = {
	gridId : 'finance_financeUser_datagrid',
	constants : {
		userGroup : {"1": "普通用户","2": "分析师","3": "系统"},
		sex :{"0" : "男", "1" : "女"},
		isRecommend :{"1" : "是", "0" : "否"},
		status :{"1" : "有效", "0" : "无效"},
		isGag : {"1" : "是", "0" : "否"},
		isBack : {"1" : "是", "0" : "否"}
	},
	init : function(){
		this.initValidator();
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : financeFinanceUser.gridId,
			singleSelect : false,
			idField:"memberId",
			sortName : 'memberId',
			sortOrder : "desc",
			url : basePath+'/financeUserController/datagrid.do',
			queryParams : {
				mobilephone : "",
				memberId : ""
			},
			columns : [[
						{title : $.i18n.prop("common.operate"),field : 'todo',formatter : function(value, rowData, rowIndex) {   	/**操作*/
							$("#finance_financeUser_datagrid_rowOperation a").each(function(){
								if(rowData.loginPlatform.financePlatForm.isGag == 1){    //禁言
									$(this).filter("[data-options*=ope-redo]").linkbutton('disable');
									$(this).filter("[data-options*=ope-undo]").linkbutton('enable');
								}else{  //不禁言
									$(this).filter("[data-options*=ope-redo]").linkbutton('enable');
									$(this).filter("[data-options*=ope-undo]").linkbutton('disable');
								}
								$(this).attr("id",rowData.memberId);
								$(this).attr("isGag",rowData.loginPlatform.financePlatForm.isGag);
						    });
							
							var loc_input = $("#finance_financeUser_datagrid_rowOperation input");
							loc_input.val(rowData.memberId);
							loc_input.attr("rowIndex", rowIndex);
							return $("#finance_financeUser_datagrid_rowOperation").html();
						}},
						{title : "用户ID", field : 'memberId', sortable : true},
			            {title : "手机号", field : 'mobilePhone', sortable : true},
			            {title : "用户名",field : 'loginPlatform.financePlatForm.nickName', formatter : getValue4EasyUI},
			            {title : "姓名",field : 'loginPlatform.financePlatForm.realName', formatter : getValue4EasyUI},
			            {title : "性别",field : 'loginPlatform.financePlatForm.sex', formatter : function(value, rowData, rowIndex){
			            	value = getValue4EasyUI(value, rowData, rowIndex, this);
			            	return financeFinanceUser.constants.sex[value+""];
			            }},
			            {title : "微博绑定",field : 'bindPlatformMicroBlog', formatter : function(value, rowData, rowIndex){
			            	value = getValue4EasyUI(value, rowData, rowIndex, 'loginPlatform.financePlatForm.bindPlatformList');
			            	return financeFinanceUser.getBindPlatformAcco(1, value, rowData, rowIndex);
			            }},
			            {title : "微信绑定",field : 'bindPlatformWeiChat', formatter : function(value, rowData, rowIndex){
			            	value = getValue4EasyUI(value, rowData, rowIndex, 'loginPlatform.financePlatForm.bindPlatformList');
			            	return financeFinanceUser.getBindPlatformAcco(2, value, rowData, rowIndex);
			            }},
			            {title : "QQ绑定",field : 'bindPlatformQQ', formatter : function(value, rowData, rowIndex){
			            	value = getValue4EasyUI(value, rowData, rowIndex, 'loginPlatform.financePlatForm.bindPlatformList');
			            	return financeFinanceUser.getBindPlatformAcco(3, value, rowData, rowIndex);
			            }},
			            {title : "用户组别",field : 'loginPlatform.financePlatForm.userGroup', formatter : function(value, rowData, rowIndex){
			            	value = getValue4EasyUI(value, rowData, rowIndex, this);
			            	return financeFinanceUser.constants.userGroup[value+""];
			            }},
			            {title : "推荐用户",field : 'loginPlatform.financePlatForm.isRecommend', formatter : function(value, rowData, rowIndex){
			            	value = getValue4EasyUI(value, rowData, rowIndex, this);
			            	return financeFinanceUser.constants.isRecommend[value+""];
			            }},
			            {title : "注册时间",field : 'loginPlatform.financePlatForm.registerDate', formatter : function(value, rowData, rowIndex){
			            	value = getValue4EasyUI(value, rowData, rowIndex, this);
			            	return timeObjectUtil.formatterDate(value);
			            }},
			            {title : "是否禁言", field : 'loginPlatform.financePlatForm.isGag', formatter : function(value, rowData, rowIndex){
			            	value = getValue4EasyUI(value, rowData, rowIndex, this);
			            	var newVal = financeFinanceUser.constants.isRecommend[value+""];
			            	if(isBlank(newVal)){
			            		return '否';
			            	}
			            	return newVal;
			            }},
			            {title : "是否后台用户", field : 'loginPlatform.financePlatForm.isBack', formatter : function(value, rowData, rowIndex){
			            	value = getValue4EasyUI(value, rowData, rowIndex, this);
			            	var newVal = financeFinanceUser.constants.isBack[value+""];
			            	if(isBlank(newVal)){
			            		return '否';
			            	}
			            	return newVal;
			            }},
			            {title : "状态",field : 'loginPlatform.financePlatForm.status', formatter : function(value, rowData, rowIndex){
			            	value = getValue4EasyUI(value, rowData, rowIndex, this);
			            	return financeFinanceUser.constants.status[value+""];
			            }}
			]],
			toolbar : '#finance_financeUser_datagrid_toolbar'
		});
	},
	//获取绑定系统账号:type 1-微博、2-微信、3-QQ
	getBindPlatformAcco : function(type, value, rowData, rowIndex){
		if(value && value instanceof Array){
			var loc_accoNos = [];
			for(var i = 0, lenI = value.length; i < lenI; i++){
				if(value[i].type === type){
					loc_accoNos.push(value[i].bindAccountNo);
				}
			}
			return loc_accoNos.join("<br>");
		}else{
			return value;
		}
	},
	setEvent:function(){
		$("#finance_financeUser_queryForm_search").on("click",function(){
			var queryParams = $('#'+financeFinanceUser.gridId).datagrid('options').queryParams;
			queryParams["bindPlatformType"] = [];
			$("#finance_financeUser_queryForm input").add("#finance_financeUser_queryForm select").each(function(){
				if($(this).is("input[type='checkbox']")){
					if($(this).prop("checked")){
						queryParams[$(this).attr("name")].push($(this).val());
					}
				}else{
					queryParams[$(this).attr("name")] = $(this).val();
				}
			});
			queryParams["bindPlatformType"] = convertArr2Obj(queryParams["bindPlatformType"]);
			if(queryParams["timeStart"] !== ""){
				queryParams["timeStart"] =  formatStartDate(queryParams["timeStart"]);
			}
			if(queryParams["timeEnd"] !== ""){
				queryParams["timeEnd"] =  formatEndDate(queryParams["timeEnd"]);
			}

			$('#'+financeFinanceUser.gridId).datagrid({
				url : basePath+'/financeUserController/datagrid.do',
				pageNumber : 1
			});
		});
		$("#finance_financeUser_queryForm_reset").on("click",function(){
			$("#finance_financeUser_queryForm")[0].reset();
		});
		
		$('table.datagrid-btable tr td div').bind("cut copy paste", function(e) { 
            alert('Copy Paste is disabled');
            e.preventDefault();
        });
	},
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+financeFinanceUser.gridId).datagrid('reload');
	},
	/**
	 * 功能：查看
	 * @param item
	 */
	view : function(item){
		var loc_tr = $(item).parents("tr:first");
		var loc_rowData = $("#" + financeFinanceUser.gridId).datagrid("getRows")[$(item).siblings("input").attr("rowIndex")];
		
		var url = formatUrl(basePath + '/financeUserController/view.do');
		goldOfficeUtils.openSimpleDialog({
			title : $.i18n.prop("common.operatetitle.view"),   			 /**查看记录*/
			height : 400,
			href : url,
			iconCls : 'pag-view',
			onLoad : function(){
				var loc_panel = $("#finance_financeUser_view");
				loc_tr.find("td[field]").each(function(){
					loc_panel.find("td[field='" + $(this).attr("field") + "']").html($(this).html());
				});
				var loc_value = getValue4EasyUI(null, loc_rowData, null, "loginPlatform.financePlatForm.introduce");
				loc_panel.find("td[field='loginPlatform.financePlatForm.introduce']").text(loc_value);
				
				loc_value = getValue4EasyUI(null, loc_rowData, null, "loginPlatform.financePlatForm.address");
				loc_panel.find("td[field='loginPlatform.financePlatForm.address']").text(loc_value);
			}
		});
	},
	/**
	 * 功能：增加
	 */
	add : function(){
		var url = formatUrl(basePath + '/financeUserController/preAdd.do');
		var submitUrl =  formatUrl(basePath + '/financeUserController/add.do');
		goldOfficeUtils.openEditorDialog({
			dialogId : 'financeWidow',
			title : $.i18n.prop("common.operatetitle.add"),				/**添加记录*/
			height : 530,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				try{
					if(isBlank($("#saveAvatarPath").val())){
						alert('请选择上传的头像！');
						return;
					}
					if($("#financeUserAddForm").form('validate')){
						goldOfficeUtils.ajaxSubmitForm({
							url : submitUrl,
							formId : 'financeUserAddForm',
							onSuccess : function(data){  //提交成功后处理
								var d = $.parseJSON(data);
								if (d.success) {
									// 提交成功后先关闭弹出框,然后刷新表格,弹出对应的成功提示
									$("#financeWidow").dialog("close");
									financeFinanceUser.refresh();
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
		var loc_id = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/financeUserController/preEdit.do?memberId=' + loc_id);
		var submitUrl =  formatUrl(basePath + '/financeUserController/edit.do');
		goldOfficeUtils.openEditorDialog({
			dialogId : 'financeWidow',
			title : $.i18n.prop("common.operatetitle.edit"),			/**修改记录*/
			height : 530,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				if($("#financeUserEditForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'financeUserEditForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								// 提交成功后先关闭弹出框,然后刷新表格,弹出对应的成功提示
								$("#financeWidow").dialog("close");
								financeFinanceUser.refresh();
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
	 * 功能：注销
	 * @param item
	 */
	del : function(item){
		var memberId = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/financeUserController/delete.do');
		goldOfficeUtils.deleteOne(financeFinanceUser.gridId, memberId, url, "确认要注销吗？");
	},

	/**
	 * 功能：重置密码
	 * @param item
	 */
	resetPwd : function(item){
		var loc_id = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/financeUserController/resetPwd.do?memberId=' + loc_id);
		var submitUrl =  formatUrl(basePath + '/financeUserController/saveResetPwd.do');
		goldOfficeUtils.openEditorDialog({
			title : '重置密码',
			height : 200,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				if($("#financeUserResetPwdForm").form('validate')){
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'financeUserResetPwdForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if (d.success) {
								// 提交成功后先关闭弹出框,然后刷新表格,弹出对应的成功提示
								$("#myWindow").dialog("close");
								financeFinanceUser.refresh();
								$.messager.alert($.i18n.prop("common.operate.tips"), "重置密码成功",'info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'重置密码失败，原因：'+d.msg,'error');
							}
						}
					});
				}
			}
		});
	},
	/**
	 * 功能：禁言(取消禁言)
	 */
	doGag : function(obj,type){
		var recordId = obj.id,isGag = $(obj).attr("isGag");
		if(isGag == type){
			alert("禁止该操作！");
			return;
		}
		getJson(formatUrl(basePath + '/financeUserController/doGag.do'),{"memberId":recordId,"isGag" : type},function(data){
			var msg = (type == 1 ? '禁言' : '取消禁言');
			if(data.success){
				$.messager.alert($.i18n.prop("common.operate.tips"),msg+'成功！','info');
				financeFinanceUser.refresh();
			}else{
				$.messager.alert($.i18n.prop("common.operate.tips"),msg+'失败，原因：'+data.msg,'error');
			}
		},true);
	},
	/**
	 * 功能：导出
	 */
	export : function(){
		var params = "";
		if(!isBlank($("#finance_financeUser_timeStart").val())){
			params += 'timeStart='+formatStartDate($("#finance_financeUser_timeStart").val());
		}
		if(!isBlank($("#finance_financeUser_timeEnd").val())){
			params += '&timeEnd='+formatStartDate($("#finance_financeUser_timeEnd").val());
		}
		var bindPlatformType =[];
		$('input[name=bindPlatformType]:checked').each(function(){ 
			bindPlatformType.push($(this).val());
		}); 
		//params += '&bindPlatformType='+convertArr2Obj(bindPlatformType);
		params += $("#finance_financeUser_queryForm").serialize();
		window.location.href = basePath+ '/financeUserController/export.do?'+params;
	},
	/**
	 * 初始化校验器
	 */
	initValidator : function(){
		$.fn.validatebox.defaults.rules['bindPlatformFinanceUser'] = { // 判断绑定账号
			validator : function(value) {
				return /^(\w+(;\w+)*)?$/.test(value);
			},
			message : "绑定平台无效！"
		};
	}
};
		
//初始化
$(function() {
	financeFinanceUser.init();
});