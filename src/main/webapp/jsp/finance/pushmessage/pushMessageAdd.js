/**
 * 摘要：消息管理新增js
 * @author Gavin.guo
 * @date   2015-07-23
 */
var pushMessageAdd = {
	init : function(){
		pushMessage.initUEditor('contentAdd');
	},
	/**
	 * 功能：改变消息类型时
	 */
	changeMessageType : function(val){
		if(val == 1){  //自定义
			$("#contentAddTr").show();
			$("#noticeDiv").hide();
		}else if(val == 2 || val == 5){  //文章资讯或公告
			$("#contentAddTr").hide();
			$("#noticeDiv").show();
		}
	},
	/**
	 * 功能：新增时保存
	 */
	onSaveAdd : function(){
		if(isBlank($("#pushMessageAddForm input[name=title]").val())){
			alert("标题不能为空！");
			return;
		}
		if($("#pushDateStr").val() == ''){
			alert("推送时间不能为空！");
			return;
		}
		if(isBlank($('#platformAddStr').combotree('getValues'))){
			alert("应用平台不能为空！");
			return;
		}
		var tipTypeFlag = false;
		$("#pushMessageAddForm input[name=tipTypeStr]").each(function(){ 
			if($(this).attr("checked")) { 
				tipTypeFlag = true;
				return false;
			}
		});
		if(!tipTypeFlag){
			alert("请选择消息提示方式！");
			return;
		}
		var selectedMessageType = $("#pushMessageAddForm select[name=messageType]").find("option:selected").val()
		    ,categoryArr=[],combotree =$('#categoryIdAdd').combotree('tree'),getSelectedTree = combotree.tree('getSelected');
		if(selectedMessageType == 1){
			if(isBlank(UE.getEditor('contentAdd').getContent())){
		    	alert("消息内容是必填项！");
		    	return;
		    }
		}else{
			if(null == getSelectedTree){
				alert("请选择栏目！");
				return;
			}
			if(isBlank($("#articleIdAdd").val())){
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
		var serializeData = $("#pushMessageAddForm").serialize();
		serializeData += "&fullCategoryId="+categoryArr.join("#");
		getJson(formatUrl(basePath + '/pushMessageController/create.do'),serializeData,function(data){
			$.messager.progress('close');
			if(data.success){
				alert("新增消息成功 !");
				jumpRequestPage(basePath + '/pushMessageController/index.do');
			}else{
				alert("新增消息失败，错误信息："+data.msg);
			}
		},true);
	}
};

$(function() {
	pushMessageAdd.init();
});