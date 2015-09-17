package com.gwghk.mis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：应用对象(账户能操作的应用)
 * @author Gavin.guo
 * @date   2015年3月16日
 */
@Document
public class App extends BaseModel{
	
	/**
	 * 应用Id
	 */
	@Id
	private String appId;
	
	/**
	 * 应用code
	 */
	@Indexed
	private String code;
	
	/**
	 * 应用title
	 */
	private String title;
	
	/**
	 * 应用logo
	 */
	private String logo;
	
	/**
	 * 应用描述
	 */
	private String remark;
	
	/**
	 * 应用状态(0:禁用 1：启用)
	 */
	private Integer status;
	
	/**
	 * 应用显示的顺序
	 */
	private Integer sorting;
	
	/**
	 * 评分(类似0.0,4.9)
	 */
	private String score;
	
	/**
	 * 用户量(类似于33579 位用户)
	 */
	private Integer subscribers;
	
	/**
	 * 应用类别
	 */
	private AppCategory appCategory;
	
	/**
	 * 是否默认对所有会员可见(0:不可见,1:可见)
	 */
	private Integer isDefaultVisibility;
	
	/**
	 * 应用是否收费(1:收费,0:免费)
	 */
	private Integer isCharge;
	
	/**
     * 是否删除
     */
	private Integer valid;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Integer getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Integer subscribers) {
		this.subscribers = subscribers;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSorting() {
		return sorting;
	}

	public void setSorting(Integer sorting) {
		this.sorting = sorting;
	}

	public Integer getIsDefaultVisibility() {
		return isDefaultVisibility;
	}

	public void setIsDefaultVisibility(Integer isDefaultVisibility) {
		this.isDefaultVisibility = isDefaultVisibility;
	}

	public Integer getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(Integer isCharge) {
		this.isCharge = isCharge;
	}

	public AppCategory getAppCategory() {
		return appCategory;
	}

	public void setAppCategory(AppCategory appCategory) {
		this.appCategory = appCategory;
	}
}
