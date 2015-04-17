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
	 * 信息类型(text,img,file)
	 */
	private String msgType;
	
	/**
	 * 信息内容
	 */
	private String content;
	
	/**
	 * 内容状态：0 、禁用 ；1、启动
	 */
	private String status;
	
	/**
	 * 发布日期
	 */
	private Date publishDate;
	
	private String createUser;

	private String createIp;

	private Date createDate;

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

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
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

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
