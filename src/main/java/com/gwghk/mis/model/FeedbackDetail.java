package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

/**
 * 摘要：会员反馈明细
 * @author Gavin.guo
 * @date   2015年07月12日
 */
public class FeedbackDetail extends BaseModel{
	
	/**
	 * 编号
	 */
	@Id
	private String FeedbackDetailId;
	
	/**
	 * 反馈时间
	 */
	private Date  feedBackDate;
	
	/**
	 * 反馈内容
	 */
	private String feedBackContent;
	
	/**
	 * 类型(管理员或会员回复  1:会员  2:管理员)
	 */
	private Integer type;
	
	/**
	 * @return the feedbackDetailId
	 */
	public String getFeedbackDetailId() {
		return FeedbackDetailId;
	}

	/**
	 * @param feedbackDetailId the feedbackDetailId to set
	 */
	public void setFeedbackDetailId(String feedbackDetailId) {
		FeedbackDetailId = feedbackDetailId;
	}

	public Date getFeedBackDate() {
		return feedBackDate;
	}

	public void setFeedBackDate(Date feedBackDate) {
		this.feedBackDate = feedBackDate;
	}

	public String getFeedBackContent() {
		return feedBackContent;
	}

	public void setFeedBackContent(String feedBackContent) {
		this.feedBackContent = feedBackContent;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
