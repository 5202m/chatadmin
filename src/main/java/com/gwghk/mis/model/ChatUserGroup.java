package com.gwghk.mis.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户组对象
 * 备注:（用于内嵌在LoginPlatfrom里，记录用户登录第三聊天室所需信息）
 * @author Alan.wu
 * @date   2015年4月10日
 */
@Document
public class ChatUserGroup {
	/**
	 * 组id
	 */
	@Id
	private String id;
	
	/**
	 * 用户id
	 */
	private String userId;
		
	/**
	 * 在线状态(0为下线，1为在线）
	 */
	private Integer onlineStatus;
	
	/**
	 * 上线时间
	 */
	private Date onlineDate;
	
	/**
	 * 交易账户
	 */
	private String accountNo;
	
	/**
	 * 头像
	 */
	private String avatar;
	
	/**
	 * 昵称
	 */
	private String nickname;
	
	/**
     * 禁言-开始时间
     */
	private Date  gagStartDate;
	
	/**
     * 禁言-结束时间
     */
	private Date  gagEndDate;
	
	/**
     * 禁言提示语
     */
	private String gagTips; 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Date getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getGagStartDate() {
		return gagStartDate;
	}

	public void setGagStartDate(Date gagStartDate) {
		this.gagStartDate = gagStartDate;
	}

	public Date getGagEndDate() {
		return gagEndDate;
	}

	public void setGagEndDate(Date gagEndDate) {
		this.gagEndDate = gagEndDate;
	}

	public String getGagTips() {
		return gagTips;
	}

	public void setGagTips(String gagTips) {
		this.gagTips = gagTips;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
