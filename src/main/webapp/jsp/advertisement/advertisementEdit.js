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
		if($("#platform").val() == ''){
			alert("请选择广告应用平台!");
			$("#platform").focus();
			return;
		}
		if($("#saveImagePath").val() == ''){
			alert("请上传广告图片!");
			return;
		}
		if($("#advertisementEditForm").form('validate')){
			$.messager.progress();    		  		//提交时，加入进度框
			var advertisementFormData = $("#advertisementEditForm").serialize();
			getJson(formatUrl(basePath + '/advertisementController/update.do'),advertisementFormData,function(data){
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