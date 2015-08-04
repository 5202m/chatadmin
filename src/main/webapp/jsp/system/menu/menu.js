/**
 * 摘要：菜单管理公用js(同步加载)
 * @author Gavin.guo
 * @date   2014-10-14
 */
var systemMenu = {
    menuId : '',
	init : function(){
		this.initMenuTree();
		this.setEvent();
	},
	formatName:function(obj){
		return LOCALE_ZH_CN==locale?obj.nameCN:(LOCALE_ZH_TW==locale?obj.nameTW:obj.nameEN);
	},
	/**
	 * 功能：菜单tree初始化
	 */
	initMenuTree : function(){
		//默认隐藏右边菜单form div
		$("#menuDiv").hide();
		
		//初始化左边的树形菜单
		systemMenu.menuId = $('#menuTree');
		systemMenu.menuId.tree({
            checkbox: false,
            lines: true,
            url: basePath+'/menuController/loadMenuTree.do',
            onSelect : function(node){	 //选中菜单节点时调用
            	var parentNode = systemMenu.menuId.tree('getParent',node.target) ;
            	parentNodeName = parentNode ? parentNode.text : '';
            	systemMenu.getMenu(node.id,parentNodeName);
            	$("#menuDiv").show();
            },
            //右键菜单节点时调用
            onContextMenu : function(e, node){
            	e.preventDefault();
            	$('#menuTree').tree('select', node.target);
        		$('#rightKeyMenu').menu('show', {
        			left: e.pageX,
        			top: e.pageY
        		});
        		var parentNode = systemMenu.menuId.tree('getParent',node.target) ;
            	parentNodeName = parentNode ? parentNode.text : '';
        		systemMenu.getMenu(node.id,parentNodeName);
            	$("#menuDiv").show();
            },
            onLoadSuccess : function(){  //默认不展开菜单
            	systemMenu.collapseAll();
            }
		});
	},
	/**
	 * 功能：添加菜单节点
	 */
	appendTree : function(){
		var node = systemMenu.menuId.tree('getSelected'),nodeId='';
		if(node != null){
			if(node.attributes  != null && node.attributes  != undefined && node.attributes.type == 1){
				$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("menu.add.nomenu"),'warning');   /**操作提示 对不起,功能菜单下面不能再新增菜单!*/
				return;
			}
			nodeId = node.id;
		}
		var url = basePath + '/menuController/add.do?menuId='+nodeId;
		var submitUrl =  basePath + '/menuController/create.do';
		goldOfficeUtils.openEditorDialog({
			title : $.i18n.prop("common.operatetitle.add"),			/**添加记录*/
			height : 280,
			href : url,
			iconCls : 'pag-add',
			handler : function(){   //提交时处理
				if($("#menuAddForm").form('validate')){
					if($("#parentMenuId").val() == '' && $("#menuType  option:selected").val() == '1'){
						$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("menu.add.nofunc"),'warning'); /**操作提示 对不起,一级菜单下面不能新增功能菜单!*/
						return;
					}
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'menuAddForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if(d.success){
								var menuObj = d.obj;
								if(menuObj != null){
								    //菜单添加成功后,更新对应的菜单树,同时选中新增的菜单节点
									if(node != null){
										systemMenu.menuId.tree('append', {
											parent: node.target,
											data: [{
												id: menuObj.menuId,
												text: systemMenu.formatName(menuObj)
											}]
										});
									}
									else{
										systemMenu.menuId.tree('reload');
									}
									var addNode = systemMenu.menuId.tree('find', menuObj.menuId);
									if(addNode != null){
										systemMenu.menuId.tree('select', addNode.target);
										systemMenu.getMenu(menuObj.menuId,systemMenu.formatName(menuObj));
					            		$("#menuDiv").show();
					            	}
									$("#myWindow").dialog("close");
									$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.addsuccess"),'info');	/**操作提示 新增成功!*/
								}
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.addfail"),'error');  /**操作提示 新增失败!*/
							}
						}
					});
				}
			}
		});
	},
	/**
	 * 功能：删除菜单节点
	 */
	removeTree : function(){
		var node = systemMenu.menuId.tree('getSelected');
		$.messager.confirm($.i18n.prop("common.operate.tips"), $.i18n.prop("menu.delete"), function(r) {  	/**确定要删除该节点及对应的子节点吗?*/
			if(r) {
				goldOfficeUtils.ajax({
					url : basePath + '/menuController/del.do',
					data : {
						menuId : node.id
					},
					success: function(data) {
						if(data.success) {
							systemMenu.menuId.tree('remove', node.target);
							$("#menuDiv").hide();
							$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.delsuccess"),'info'); /**操作提示 删除成功!*/  
						}else{
							$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.delfail"),'error');  /**操作提示 删除失败!*/
						}
					}
				});
			}
		});
	},
	/**
	 * 功能：更新菜单节点
	 */
	updateTree : function(menuName){
		var node = systemMenu.menuId.tree('getSelected');
		if (node){
			systemMenu.menuId.tree('update', {
				target: node.target,
				text: menuName
			});
		}
	},
	/**
	 * 功能：移动菜单
	 */
	moveTree : function(){
		var node = systemMenu.menuId.tree('getSelected');
		if(node != null){
			if(node.attributes  != null && node.attributes  != undefined && node.attributes.type == 1){
				$.messager.alert($.i18n.prop("common.operate.tips"),"功能菜单不能进行移动操作！",'warning');
				return;
			}
		}
		var url = basePath + '/menuController/move.do?menuId='+node.id;
		var submitUrl =  basePath + '/menuController/saveMove.do';
		goldOfficeUtils.openEditorDialog({
			title : '移动',
			height : 120,
			href : url,
			iconCls : 'ope-next',
			handler : function(){   //提交时处理
				if($("#menuMoveForm").form('validate')){
					if($("#toMenu").val() == ''){
						$.messager.alert($.i18n.prop("common.operate.tips"),'请选择移动后的菜单!','warning');
						return;
					}
					goldOfficeUtils.ajaxSubmitForm({
						url : submitUrl,
						formId : 'menuMoveForm',
						onSuccess : function(data){  //提交成功后处理
							var d = $.parseJSON(data);
							if(d.success){
								systemMenu.menuId.tree('reload');
								$("#menuDiv").hide();
								$("#myWindow").dialog("close");
								$.messager.alert($.i18n.prop("common.operate.tips"),'操作成功','info');
							}else{
								$.messager.alert($.i18n.prop("common.operate.tips"),'操作失败','error');
							}
						}
					});
				}
			}
		});
	},
	/**
	 * 功能：刷新菜单节点
	 */
	reloadTree : function(){
		systemMenu.menuId.tree('reload');
	},
	/**
	 * 功能：保存修改菜单
	 */
	saveEditMenu : function(){
		var url = basePath + '/menuController/update.do';
		if($("#menuForm").form('validate')){
			goldOfficeUtils.ajaxSubmitForm({
				url : url,
				formId : 'menuForm',
				onSuccess : function(data){  //提交成功后处理
					var d = $.parseJSON(data);
					if(d.success) {
						systemMenu.updateTree(systemMenu.formatName(d.obj));
						$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.editsuccess"),'info');  /**操作提示  修改成功!*/
					}else{
						$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("common.editfail"),'error');    /**操作提示  修改失败!*/
					}
				}
			});
		}
	},
	/**
	 * 功能：根据菜单Id、父节点名称-->获取菜单对象
	 * @param menuId          菜单Id
	 * @param parentNodeName  父节点名称 
	 */
	getMenu : function(menuId,parentNodeName){
		goldOfficeUtils.ajax({
			type : 'get',
			url : basePath + '/menuController/'+menuId+'/view.do',
			success: function(data) {
				if(data.success) {
					var menuObj = data.obj;
					if(menuObj != null){
						$("#code").val(menuObj.code);
						$("#menuId").val(menuObj.menuId);
						$("#nameCN").val(menuObj.nameCN);
						$("#nameTW").val(menuObj.nameTW);
						$("#nameEN").val(menuObj.nameEN);
						$("#parentMenuId").val(menuObj.parentMenuId);
						$("#parentMenuName").text(parentNodeName);
						$("#type").find("option[value='"+menuObj.type+"']").attr("selected",true);
						$("#status").find("option[value='"+menuObj.status+"']").attr("selected",true);
						$("#sort").val(menuObj.sort);
						$("#url").val(menuObj.url);
						$("#remark").val(menuObj.remark);
					}
				}else{
					$.messager.alert($.i18n.prop("common.operate.tips"),$.i18n.prop("menu.getfail"),'error');   /**操作提示 获取菜单数据失败!*/
				}
			}
		});
	},
	/**
	 * 功能：展开菜单
	 */
	expandAll : function(){
		var node = systemMenu.menuId.tree('getSelected');  
        if(node) {  
        	systemMenu.menuId.tree('expandAll', node.target);  
        }  
        else {  
        	systemMenu.menuId.tree('expandAll');  
        }  
	},
	/**
	 * 功能：收起菜单
	 */
	collapseAll : function(){
		var node = systemMenu.menuId.tree('getSelected');  
        if(node) {  
        	systemMenu.menuId.tree('collapseAll', node.target);  
        }
        else {  
        	systemMenu.menuId.tree('collapseAll');  
        }
	},
	/**
	 * 功能:注册相关事件
	 */
	setEvent:function(){
		$("#formReset").on("click",function(){
			$("#menuForm")[0].reset();
		});
	}
};
		
//初始化
$(function() {
	systemMenu.init();
});