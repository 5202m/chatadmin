/**
 * 摘要：消息管理修改js
 * @author Gavin.guo
 * @date   2015-07-23
 */
var pushMessageEdit = {
	init : function(){
		pushMessage.initUEditor('contentEdit');
	},
	/**
	 * 功能：改变消息类型时
	 */
	changeMessageType : function(val){
		if(val == 1){  //自定义
			$("#contentEditTr").show();
			$("#noticeDiv").hide();
		}else if(val == 2 || val == 5){  //文章资讯或公告
			$("#contentEditTr").hide();
			$("#noticeDiv").show();
		}
	},
	/**
	 * 功能：修改时保存
	 */
	onSaveEdit : function(){
		if(isBlank($("#pushMessageEditForm input[name=title]").val())){
			alert("标题不能为空！");
			return;
		}
		if($("#publishStartDateStr").val() == '' || $("#publishEndDateStr").val() == ''){
			alert("发布时间不能为空！");
			return;
		}
		if(isBlank($('#platformEditStr').combotree('getValues'))){
			alert("平台不能为空！");
			return;
		}
		var tipTypeFlag = false;
		$("#pushMessageEditForm input[name=tipTypeStr]").each(function(){ 
			if($(this).attr("checked")) { 
				tipTypeFlag = true;
				return false;
			}
		});
		if(!tipTypeFlag){
			alert("请选择消息提示方式！");
			return;
		}
		var selectedMessageType = $("#pushMessageEditForm select[name=messageType]").find("option:selected").val()
	    	,categoryArr=[],combotree =$('#categoryIdEdit').combotree('tree'),getSelectedTree = combotree.tree('getSelected');
		if(selectedMessageType == 1){
			if(isBlank(UE.getEditor('contentEdit').getContent())){
		    	alert("消息内容是必填项！");
		    	return;
		    }
		}else{
			if(null == getSelectedTree){
				alert("请选择栏目！");
				return;
			}
			if(isBlank($("#articleIdEdit").val())){
				alert("请输入文章Id！");
				return;
			}
		}
		if(getSelectedTree != null){
			pushMessage.getParentNodeList(categoryArr,combotree,getSelectedTree.target);
			categoryArr.reverse();
			categoryArr.push(getSelectedTree.id);
		}
		$.messager.progress();    		  			//提交时，加入进度框
		var serializeData = $("#pushMessageEditForm").serialize();
		serializeData += "&fullCategoryId="+categoryArr.join("#");
		getJson(formatUrl(basePath + '/pushMessageController/update.do'),serializeData,function(data){
			$.messager.progress('close');
			if(data.success){
				alert("修改消息成功 !");
				jumpRequestPage(basePath + '/pushMessageController/index.do');
			}else{
				alert("修改消息失败，错误信息："+data.msg);
			}
		},true);
	}
};

$(function() {
	pushMessageEdit.init();
});