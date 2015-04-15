package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 聊天室组别规则实体类
 * @author Alan.wu
 * @date   2015年4月1日
 */
@Document
public class ChatGroupRule extends BaseModel{
	/**
	 * 规则id
	 */
	@Id
	private String id;
		
	/**
	 * 规则类别
	 */
	@Indexed
	private String type;
	
	/**
	 * 规则名称
	 */
	private String name;

	/**
     * 使用规则前的值
     */
	private String beforeRuleVal;
	
	/**
     * 使用规则后的值
     */
	private String afterRuleVal;
	
	/**
     * 时间段（开始时间）
     */
	private Date  periodStartDate;
	
	/**
     * 时间段（结束时间）
     */
	private Date  periodEndDate;
	
	/**
     * 执行规则后的提示语
     */
	private String afterRuleTips;
	
	/**
     * 是否删除
     */
	private Integer valid;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeforeRuleVal() {
		return beforeRuleVal;
	}

	public void setBeforeRuleVal(String beforeRuleVal) {
		this.beforeRuleVal = beforeRuleVal;
	}

	public String getAfterRuleVal() {
		return afterRuleVal;
	}

	public void setAfterRuleVal(String afterRuleVal) {
		this.afterRuleVal = afterRuleVal;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Date getPeriodStartDate() {
		return periodStartDate;
	}

	public void setPeriodStartDate(Date periodStartDate) {
		this.periodStartDate = periodStartDate;
	}

	public Date getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	public String getAfterRuleTips() {
		return afterRuleTips;
	}

	public void setAfterRuleTips(String afterRuleTips) {
		this.afterRuleTips = afterRuleTips;
	}
	
}
