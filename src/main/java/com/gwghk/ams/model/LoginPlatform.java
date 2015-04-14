package com.gwghk.ams.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 登录平台（内嵌在Member里）
 * @author Alan.wu
 * @date   2015年4月10日
 */
@Document
public class LoginPlatform{
	/**
	 * 聊天室用户组登陆信息集
	 */
	private List<ChatUserGroup> chatUserGroup;
	
	/**
	 * pmApp登陆信息集
	 */
	private PmApp pmApp;

	public List<ChatUserGroup> getChatUserGroup() {
		return chatUserGroup;
	}

	public void setChatUserGroup(List<ChatUserGroup> chatUserGroup) {
		this.chatUserGroup = chatUserGroup;
	}

	public PmApp getPmApp() {
		return pmApp;
	}

	public void setPmApp(PmApp pmApp) {
		this.pmApp = pmApp;
	}
	
	
	
}
