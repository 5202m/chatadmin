package com.gwghk.mis.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 聊天室组别实体类
 * @author Alan.wu
 * @date   2015年4月1日
 */
@Document
public class ChatGroup extends BaseModel{
	
	/**
	 * 组别编码
	 */
	@Id
	private String id;
		
	/**
	 * 组别名
	 */
	private String name;
	
	/**
	 * 内容状态：0 、禁用 ；1、启动
	 */
	private String status;

	/**
	 * 聊天规则
	 */
	private List<ChatGroupRule> chatRules;
	
	/**
     * 是否删除
     */
	private Integer valid;
	
	/**
	 * 聊天规则id(用于数据传输）
	 */
	private String chatRuleIds;
	
	/**
	 * 聊天室路径(用于页面显示）
	 */
	private String chatUrl;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}
	
	public List<ChatGroupRule> getChatRules() {
		return chatRules;
	}

	public void setChatRules(List<ChatGroupRule> chatRules) {
		this.chatRules = chatRules;
	}

	public String getChatRuleIds() {
		return chatRuleIds;
	}

	public void setChatRuleIds(String chatRuleIds) {
		this.chatRuleIds = chatRuleIds;
	}

	public String getChatUrl() {
		return chatUrl;
	}

	public void setChatUrl(String chatUrl) {
		this.chatUrl = chatUrl;
	}
	
}
