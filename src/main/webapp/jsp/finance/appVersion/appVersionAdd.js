/**
 * 摘要：应用管理新增js
 * @author Gavin.guo
 * @date   2015-09-15
 */
var appVersionAdd = {
	init : function(){
		appVersion.initUploadFile();
	},
	/**
	 * 功能：新增时保存
	 */
	onSaveAdd : function(){
		if(isBlank($("select[name=platform]").val())){
	    	alert("应用平台是必填项！");
	    	return;
	    }
	    if(isBlank($("select[name=isMustUpdate]").val())){
	    	alert("是否强制更新应用是必填项！");
	    	return;
	    }
	    if(isBlank($("textarea[name=remark]").val())){
	    	alert("升级说明是必填项！");
	    	return;
	    }
	    if(isBlank($("#saveAppPath").val())){
	    	alert("请上传应用到服务器！");
	    	return;
	    }
		if($("#appVersionAddForm").form('validate')){
			$.messager.progress();    		  		//提交时，加入进度框
			var serializeAddFormData = $("#appVersionAddForm").serialize();
			getJson(formatUrl(basePath + '/appVersionController/create.do'),serializeAddFormData,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("新增应用版本成功 !");
					jumpRequestPage(basePath + '/appVersionController/index.do');
				}else{
					alert("新增应用版本失败，错误信息："+data.msg);
				}
			},true);
		}
	}
};
		
//初始化
$(function() {
	appVersionAdd.init();
});