package com.gwghk.mis.model;

import java.util.List;

/**
 * 登录平台（内嵌在Member里）
 * @author Alan.wu
 * @date   2015年4月10日
 */
public class LoginPlatform{
	/**
	 * 聊天室用户组登陆信息集
	 */
	private List<ChatUserGroup> chatUserGroup;
	
	public List<ChatUserGroup> getChatUserGroup() {
		return chatUserGroup;
	}

	public void setChatUserGroup(List<ChatUserGroup> chatUserGroup) {
		this.chatUserGroup = chatUserGroup;
	}
}
