package com.gwghk.ams.model;

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
	 * 跳转到主页的规则
	 */
	private ChatGroupRule homeUrlRule;
	
	/**
	 * 内容规则
	 */
	private List<ChatGroupRule> contentRules;
	
	/**
	 * 跳转到主页的规则id(用于数据传输）
	 */
	private String homeUrlRuleId;
	
	/**
	 * 内容规则id(用于数据传输）
	 */
	private String contentRuleIds;
	/**
     * 是否删除
     */
	private Integer valid;
	
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

	public String getHomeUrlRuleId() {
		return homeUrlRuleId;
	}

	public void setHomeUrlRuleId(String homeUrlRuleId) {
		this.homeUrlRuleId = homeUrlRuleId;
	}

	public String getContentRuleIds() {
		return contentRuleIds;
	}

	public void setContentRuleIds(String contentRuleIds) {
		this.contentRuleIds = contentRuleIds;
	}

	public ChatGroupRule getHomeUrlRule() {
		return homeUrlRule;
	}

	public void setHomeUrlRule(ChatGroupRule homeUrlRule) {
		this.homeUrlRule = homeUrlRule;
	}

	public List<ChatGroupRule> getContentRules() {
		return contentRules;
	}

	public void setContentRules(List<ChatGroupRule> contentRules) {
		this.contentRules = contentRules;
	}
}
