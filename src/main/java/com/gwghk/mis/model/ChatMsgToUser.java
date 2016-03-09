package com.gwghk.mis.model;


/**
 * 聊天室信息接收方实体类
 * @author Alan.wu
 * @date   2016年3月16日
 */
public class ChatMsgToUser {
	private Integer userType;//区分系统用户还是会员，-1游客，0会员，1管理员，2分析师，3客服，4水军
	private Integer talkStyle;//聊天方式，0对话，1私聊
	private String userId;
	private String nickname;
	private String question;
	
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getTalkStyle() {
		return talkStyle;
	}
	public void setTalkStyle(Integer talkStyle) {
		this.talkStyle = talkStyle;
	}
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
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
}
