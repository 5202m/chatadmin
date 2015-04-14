package com.gwghk.mis.model;

import java.util.Date;

/**
 * 聊天室在线用户实体类
 * @author Alan.wu
 * @date   2015年3月16日
 */
public class ChatOnlineUser{
	
	/**
	 * 用户id
	 */
	protected String userId;
	
	/**
	 * 用户昵称
	 */
	protected String userNickname;
	
	/**
	 * 用户头像
	 */
	protected String userAvatar;
	
	
	/**
	 * 区分系统用户还是会员，0表示会员，1表示系统用户
	 */
	protected Integer userType;
	
	/**
	 * 组别Id
	 */
	protected String groupId;
	
	/**
	 * 上线时间
	 */
	private Date onlineDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Date getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}
}
