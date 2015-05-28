/**
 * 摘要：管理员聊天公用js
 * @author Gavin.guo
 * @date   2015-03-16
 */
var adminChat = {
	pannelCount : 0,
	init : function(){
	},
	/**
	 * 功能：添加聊天室
	 * @param groupId   聊天室Id
	 * @param groupName	聊天室name
	 */
	add : function(groupId,groupName){
		goldOfficeUtils.ajax({
			url : basePath +'/adminChatController/getToken.do?groupId='+groupId,
			type : 'get',
			success : function(data){
				if(data.obj != null){
					$('#'+groupId).linkbutton('disable');
					var iframeSrc = $("#chatURL").val()+'&groupId='+ groupId+"&token="+data.obj
								  + '&timestamp='+new Date();
					$("#pp").append("<div style='margin:1%;border:solid #ccc 1px;width:47%;height:80%;display:inline-block'>"+'<iframe src="' + iframeSrc+'" frameborder=0 height=100% width=100% scrolling=no></iframe>'+"</div>");
				}
			}
		})
	}
};

$(function() {
	adminChat.init();
});