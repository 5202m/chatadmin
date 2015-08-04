package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * 房间组对象
 * 备注:（用于内嵌在LoginPlatfrom里，记录用户登录第三聊天室所需信息）
 * @author Alan.wu
 * @date   2015年8月4日
 */
public class ChatRoom {
	/**
	 * 组id
	 */
	@Id
	private String id;
		
	/**
	 * 在线状态(0为下线，1为在线）
	 */
	@Indexed
	private Integer onlineStatus;
	
	/**
	 * 上线时间
	 */
	private Date onlineDate;

	/**
     * 禁言-开始时间
     */
	@Indexed
	private Date  gagStartDate;
	
	/**
     * 禁言-结束时间
     */
	@Indexed
	private Date  gagEndDate;
	
	/**
     * 禁言提示语
     */
	private String gagTips; 
	
	/**
	 * 发言条数
	 */
	private Integer sendMsgCount;
	
	/**
	 * 禁言备注
	 */
	private String gagRemark;
	

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

	public Integer getSendMsgCount() {
		return sendMsgCount;
	}

	public void setSendMsgCount(Integer sendMsgCount) {
		this.sendMsgCount = sendMsgCount;
	}

	public String getGagRemark() {
		return gagRemark;
	}

	public void setGagRemark(String gagRemark) {
		this.gagRemark = gagRemark;
	}
}
