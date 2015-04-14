/**
 * 摘要：应用管理新增js
 * @author Gavin.guo
 * @date   2015-03-17
 */
var appAdd = {
	init : function(){
		app.initUEditor('remarkAdd');
		app.initUploadFile();
	},
	/**
	 * 功能：新增时保存
	 */
	onSaveAdd : function(){
		if($("#appAddForm").form('validate')){
			$.messager.progress();    		  		//提交时，加入进度框
			var serializeAppAddFormData = $("#appAddForm").serialize();
			getJson(formatUrl(basePath + '/appController/create.do'),serializeAppAddFormData,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("新增应用成功 !");
					jumpRequestPage(basePath + '/appController/index.do');
				}else{
					alert("新增应用失败，错误信息："+data.msg);
				}
			},true);
		}
	}
};
		
//初始化
$(function() {
	appAdd.init();
});