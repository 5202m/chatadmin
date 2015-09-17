/**
 * 摘要：会员管理修改js
 * @author Gavin.guo
 * @date   2015-03-26
 */
var memberEdit = {
	init : function(){
		member.initUploadFile();
	},
	/**
	 * 功能：修改时保存
	 */
	onSaveEdit : function(){
		if($("#memberEditForm").form('validate')){
			$.messager.progress();    		  		//提交时，加入进度框
			var serializememberEditFormData = $("#memberEditForm").serialize();
			getJson(formatUrl(basePath + '/memberController/update.do'),serializememberEditFormData,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("修改会员成功 !");
					jumpRequestPage(basePath + '/memberController/index.do');
				}else{
					alert("修改会员失败，错误信息："+data.msg);
				}
			},true);
		}
	}
};
		
//初始化
$(function() {
	memberEdit.init();
});