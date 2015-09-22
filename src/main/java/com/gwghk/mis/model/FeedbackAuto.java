package com.gwghk.mis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：自动回复信息
 * @author Dick.guo
 * @date   2015年07月27日
 */
@Document
public class FeedbackAuto extends BaseModel{

	/**
	 * 主键
	 */
	@Id
	private String feedbackAutoId;
	
	/**
	 * 类型，1-自定义，2-系统
	 */
	private Integer type;
	
	/**
	 * 关键字
	 */
	@Indexed
	private String antistop;
	
	/**
	 * 自动回复内容
	 */
	private String content;
	
	/**
	 * 是否删除(0：删除 1：未删除)
	 */
	private Integer isDeleted;

	/**
	 * @return the feedbackAutoId
	 */
	public String getFeedbackAutoId() {
		return feedbackAutoId;
	}

	/**
	 * @param feedbackAutoId the feedbackAutoId to set
	 */
	public void setFeedbackAutoId(String feedbackAutoId) {
		this.feedbackAutoId = feedbackAutoId;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the antistop
	 */
	public String getAntistop() {
		return antistop;
	}

	/**
	 * @param antistop the antistop to set
	 */
	public void setAntistop(String antistop) {
		this.antistop = antistop;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the isDeleted
	 */
	public Integer getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
}
