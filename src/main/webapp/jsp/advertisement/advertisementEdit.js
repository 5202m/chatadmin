/**
 * 摘要：广告管理修改js
 * @author Gavin.guo
 * @date   2015-04-14
 */
var advertisementEdit = {
	init : function(){
		advertisement.initUploadFile();
	},
	/**
	 * 功能：修改时保存
	 */
	onSaveEdit : function(){
		if($("#advertisementEditForm").form('validate')){
			$.messager.progress();    		  		//提交时，加入进度框
			var serializeadvertisementEditFormData = $("#advertisementEditForm").serialize();
			getJson(formatUrl(basePath + '/advertisementController/update.do'),serializeadvertisementEditFormData,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("修改应用成功 !");
					jumpRequestPage(basePath + '/advertisementController/index.do');
				}else{
					alert("修改应用失败，错误信息："+data.msg);
				}
			},true);
		}
	}
};
		
//初始化
$(function() {
	advertisementEdit.init();
});