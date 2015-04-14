package com.gwghk.ams.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 聊天室内容实体类
 * @author Alan.wu
 * @date   2015年3月16日
 */
@Document
public class ChatContent extends ChatOnlineUser{
	
	/**
	 * 内容Id
	 */
	@Id
	private String id;
	
	/**
	 * 内容
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

}
