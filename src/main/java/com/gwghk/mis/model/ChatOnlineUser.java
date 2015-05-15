package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * 聊天室在线用户实体类
 * @author Alan.wu
 * @date   2015年3月16日
 */
public class ChatOnlineUser{
	
	/**
	 * 用户id
	 */
	@Indexed
	protected String userId;
	
	/**
	 * 用户昵称
	 */
	protected String nickname;
	
	/**
	 * 用户头像
	 */
	protected String avatar;
	
	
	/**
	 * 区分系统用户还是会员，0表示会员，1表示系统用户
	 */
	protected Integer userType;
	
	/**
	 * 组别Id
	 */
	@Indexed
	protected String groupId;
	
	/**
	 * 上线时间
	 */
	private Date onlineDate;
	
	
	private String fromPlatform;//平台来源

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
	

	public String getFromPlatform() {
		return fromPlatform;
	}

	public void setFromPlatform(String fromPlatform) {
		this.fromPlatform = fromPlatform;
	}
}
