/**
 * 摘要：广告管理新增js
 * @author Gavin.guo
 * @date   2015-04-14
 */
var advertisementAdd = {
	init : function(){
		advertisement.initUploadFile();
	},
	/**
	 * 功能：新增时保存
	 */
	onSaveAdd : function(){
		if($("#advertisementAddForm").form('validate')){
			$.messager.progress();    		  		//提交时，加入进度框
			var serializeAdvertisementAddFormData = $("#advertisementAddForm").serialize();
			getJson(formatUrl(basePath + '/advertisementController/create.do'),serializeAdvertisementAddFormData,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("新增广告成功 !");
					jumpRequestPage(basePath + '/advertisementController/index.do');
				}else{
					alert("新增广告失败，错误信息："+data.msg);
				}
			},true);
		}
	}
};

$(function() {
	advertisementAdd.init();
});