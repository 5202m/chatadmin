var orgTreeDialog, orgTree;
var valueId, nameId;

/**
 * 生成组织机构树，可以点确定和关闭
 * 组织机构树分ID和名称
 * 点击确定时，会将选中组织的ID赋给由参数id的对象（例如文本输入框）,同时将组织的名称赋给name代所表的对象
 * 
 * @param {Object} id 对象Id
 * @param {Object} name 对象ID
 */
initOrg = function(id, name){
	valueId = id;
	nameId = name;
	var orgTreeDialogDiv = $("<div id='orgTreeDialog' style='height:300px;width:200px'></div>");
	//将原来的DIV修改成UL
	var orgTreeDiv = $("<ul id='orgTreeDiv'></ul>");
	orgTreeDiv.appendTo(orgTreeDialogDiv);
	orgTreeDialogDiv.appendTo("body");

	orgTreeDialog = $("#orgTreeDialog").dialog({
			title:"组织机构",
				modal : true,
				iconCls:'ope-view',
				closed : true,
				buttons:[
					{
						text : "确定",// 关闭
						iconCls : 'ope-save',
						handler : getSelectOrg
					},
					{
						text : "取消",// 关闭
						iconCls : 'ope-close',
						handler : onOrgDialogClose
					}
				]
	});
	
	orgTree = $("#orgTreeDiv").tree();
	orgTree.tree({
			url:path + "/account/organize/organizeAction!getOrgTreeComponent.action",
			lines:true
		}
	);
	
	orgTreeDialog.dialog("open");
}

getSelectOrg = function(){
	var treeNode = orgTree.tree('getSelected');
	if(treeNode != null && treeNode != undefined){
		if(valueId != null){
			$("#" + valueId).val(treeNode.id);
		}
		if(nameId != null){
			$("#" + nameId).val(treeNode.text);
		}
	}
	onOrgDialogClose();
}

onOrgDialogClose = function(){
	if(orgTreeDialog){
		orgTreeDialog.dialog("close");
		orgTreeDialog = null;
		orgTree = null;
		valueId = null;
		nameId = null;
	}
}
