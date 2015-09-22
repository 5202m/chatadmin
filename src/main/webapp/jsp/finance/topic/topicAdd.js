/**
 * 摘要：帖子管理新增js
 * @author Gavin.guo
 * @date   2015-06-24
 */
var topicAdd = {
	init : function(){
		topic.initUEditor('contentAdd');
	},
	/**
	 * 功能：新增时保存
	 */
	onSaveAdd : function(){
	    var combotree = $('#tempProductAdd').combotree('tree'),selectNode = combotree.tree('getSelected');
	    if(selectNode != null && selectNode.target != null){
	    	if(isBlank($("#tempSubjectAdd  option:selected").val())){
	    		alert("请选择行情或资讯！");
	    		return;
	    	}
	    	var tempProductList = combotree.tree('getChildren',selectNode.target);
	 	    if(tempProductList != null && tempProductList.length > 0){
	 	    	alert("插入主题-请选择具体产品！");
	 	    	return;
	 	    }
	    }
	    if(isBlank(UE.getEditor('contentAdd').getContent())){
	    	alert("内容是必填项！");
	    	return;
	    }
	    if(isBlank($("#publishTimeTempAdd").val())){
	    	alert("发布时间是必填项！");
	    	return;
	    }
	    if($("#topicAddForm").form('validate')){
			$.messager.progress();    		  			//提交时，加入进度框
			var serializeData = $("#topicAddForm").serialize();
			getJson(formatUrl(basePath + '/topicController/create.do'),serializeData,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("新增帖子成功 !");
					jumpRequestPage(basePath + '/topicController/index.do');
				}else{
					alert("新增帖子失败，错误信息："+data.msg);
				}
			},true);
		}
	},
	/**
	 * 功能：重置产品
	 */
	resetProduct : function(){
		$('#tempProductAdd').combotree('setValue', null);
	}
};

$(function() {
	topicAdd.init();
});