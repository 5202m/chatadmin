/**
 * 摘要：管理员聊天公用js
 * @author Gavin.guo
 * @date   2015-03-16
 */
var adminChat = {
	pannelCount : 0,
	init : function(){
		$('#pp').portal({
			border:false,
			fit:true
		});
	},
	/**
	 * 功能：添加聊天室
	 * @param groupId   聊天室Id
	 * @param groupName	聊天室name
	 */
	add : function(groupId,groupName){
		$('#'+groupId).linkbutton('disable');
		var p = $('<div/>').appendTo('body');
		p.panel({
			title : groupName,
			content:'<iframe src="' + 'http://127.0.0.1:3000/chat?userId=33&'+'groupId='+ groupId
					+ '" frameborder=0 height=100% width=100% scrolling=no></iframe>',
			height:500,
			closable:true,
			collapsible:true,
			onClose : function(){
				$('#'+groupId).linkbutton('enable');
				adminChat.pannelCount --;
			}
		});
		$('#pp').portal('add', {
			panel : p,
			columnIndex: adminChat.pannelCount%2
		});
		$('#pp').portal('resize');
		adminChat.pannelCount ++;
	}
};

$(function() {
	adminChat.init();
});