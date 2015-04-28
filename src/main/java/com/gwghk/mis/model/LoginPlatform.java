package com.gwghk.mis.model;

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
	 * 第三方平台登录需要的信息
	 */
	private FinanceApp pmApp;
	
	public List<ChatUserGroup> getChatUserGroup() {
		return chatUserGroup;
	}

	public void setChatUserGroup(List<ChatUserGroup> chatUserGroup) {
		this.chatUserGroup = chatUserGroup;
	}

	public FinanceApp getPmApp() {
		return pmApp;
	}

	public void setPmApp(FinanceApp pmApp) {
		this.pmApp = pmApp;
	}
	
}
