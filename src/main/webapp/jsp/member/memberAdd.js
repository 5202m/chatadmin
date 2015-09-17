/**
 * 摘要：会员管理新增js
 * @author Gavin.guo
 * @date   2015-03-17
 */
var memberAdd = {
	init : function(){
		member.initUploadFile();
	},
	/**
	 * 功能：新增时保存 
	 */
	onSaveAdd : function(){
		if($("#memberAddForm").form('validate')){
			$.messager.progress();    		  		//提交时，加入进度框
			var serializememberAddFormData = $("#memberAddForm").serialize();
			getJson(formatUrl(basePath + '/memberController/create.do'),serializememberAddFormData,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("新增会员成功 !");
					jumpRequestPage(basePath + '/memberController/index.do');
				}else{
					alert("新增会员失败，错误信息："+data.msg);
				}
			},true);
		}
	}
};
		
//初始化
$(function() {
	memberAdd.init();
});