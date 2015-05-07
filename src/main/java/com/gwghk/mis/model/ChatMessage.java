package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 聊天室信息实体类
 * @author Alan.wu
 * @date   2015年3月16日
 */
@Document
public class ChatMessage extends ChatOnlineUser{
	
	/**
	 * 内容Id
	 */
	@Id
	private String id;
	
	/**
	 * 信息内容
	 */
	private ChatContent content;
	
	/**
	 * 内容状态：0 、禁用 ；1、启动
	 */
	private String status;
	
	/**
	 * 发布日期
	 */
	private String publishTime;
	
	private String createUser;

	private String createIp;

	private Date createDate;

	/**
	 * 发布开始日期（用于查询）
	 */
	private String publishStartDateStr;
	
	/**
	 * 发布结束日期（用于查询）
	 */
	private String publishEndDateStr;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public ChatContent getContent() {
		return content;
	}

	public void setContent(ChatContent content) {
		this.content = content;
	}

	public String getPublishStartDateStr() {
		return publishStartDateStr;
	}

	public void setPublishStartDateStr(String publishStartDateStr) {
		this.publishStartDateStr = publishStartDateStr;
	}

	public String getPublishEndDateStr() {
		return publishEndDateStr;
	}

	public void setPublishEndDateStr(String publishEndDateStr) {
		this.publishEndDateStr = publishEndDateStr;
	}
	
}
