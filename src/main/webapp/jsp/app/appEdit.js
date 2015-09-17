/**
 * 摘要：应用管理修改js
 * @author Gavin.guo
 * @date   2015-03-18
 */
var appEdit = {
	init : function(){
		app.initUEditor('remarkEdit');
		app.initUploadFile();
	},
	/**
	 * 功能：修改时保存
	 */
	onSaveEdit : function(){
		if($("#appEditForm").form('validate')){
			$.messager.progress();    		  		//提交时，加入进度框
			var serializeappEditFormData = $("#appEditForm").serialize();
			getJson(formatUrl(basePath + '/appController/update.do'),serializeappEditFormData,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("修改应用成功 !");
					jumpRequestPage(basePath + '/appController/index.do');
				}else{
					alert("修改应用失败，错误信息："+data.msg);
				}
			},true);
		}
	}
};
		
//初始化
$(function() {
	appEdit.init();
});