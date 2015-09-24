/**
 * 摘要：帖子管理修改js
 * @author Gavin.guo
 * @date   2015-06-24
 */
var topicEdit = {
	init : function(){
		topic.initUEditor('contentEdit');
	},
	/**
	 * 功能：修改时保存
	 */
	onSaveEdit : function(){
		if(isBlank($("select[name=memberId]").val())){
			alert("发布人是必填项！");
		    return;
		}
	    var combotree = $('#tempProductEdit').combotree('tree'),selectNode = combotree.tree('getSelected');
	    if(selectNode != null && selectNode.target != null){
	    	if(isBlank($("#tempSubjectEdit  option:selected").val())){
	    		alert("请选择行情、资讯或策略！");
	    		return;
	    	}
	    	var tempProductList = combotree.tree('getChildren',selectNode.target);
	 	    if(tempProductList != null && tempProductList.length > 0){
	 	    	alert("插入主题-请选择具体产品！");
	 	    	return;
	 	    }
	    }
	    if(isBlank(UE.getEditor('contentEdit').getContent())){
	    	alert("内容是必填项！");
	    	return;
	    }
	    if(isBlank($("#publishTimeTempEdit").val())){
	    	alert("发布时间是必填项！");
	    	return;
	    }
	    if($("#topicEditForm").form('validate')){
			$.messager.progress();    		  			//提交时，加入进度框
			var serializeData = $("#topicEditForm").serialize();
			getJson(formatUrl(basePath + '/topicController/update.do'),serializeData,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("修改帖子成功 !");
					jumpRequestPage(basePath + '/topicController/index.do');
				}else{
					alert("修改帖子失败，错误信息："+data.msg);
				}
			},true);
		}
	},
	/**
	 * 功能：重置产品
	 */
	resetProduct : function(){
		$('#tempProductEdit').combotree('setValue', null);
	}
};

$(function() {
	topicEdit.init();
});