package com.gwghk.mis.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 摘要：会员反馈信息
 * @author Gavin.guo
 * @date   2015年07月12日
 */
@Document
public class Feedback extends BaseModel{

	/**
	 * 主键
	 */
	@Id
	private String feedbackId;
	
	/**
	 * 会员Id
	 */
	@Indexed
	private String memberId;
	
	/**
	 * 手机号
	 */
	private String mobilePhone;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 是否删除
	 */
	private Integer isDeleted;
	
	/**
	 * 最后反馈时间
	 */
	private Date lastFeedbackDate;
	
	/**
	 * 最后反馈时间-开始(查询用)
	 */
	private String lastFeedbackDateStart;
	
	/**
	 * 最后反馈时间-结束(查询用)
	 */
	private String lastFeedbackDateEnd;
	
	/**
	 * 最后反馈内容
	 */
	private String lastFeedbackContent;
	
	/**
	 * 是否回复用户反馈内容(1：是  0：否)
	 */
	private Integer isReply;
	
	/**
	 * 回复列表
	 */
	private List<FeedbackDetail> replyList;

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<FeedbackDetail> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<FeedbackDetail> replyList) {
		this.replyList = replyList;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getLastFeedbackDate() {
		return lastFeedbackDate;
	}

	public void setLastFeedbackDate(Date lastFeedbackDate) {
		this.lastFeedbackDate = lastFeedbackDate;
	}

	public String getLastFeedbackContent() {
		return lastFeedbackContent;
	}

	public void setLastFeedbackContent(String lastFeedbackContent) {
		this.lastFeedbackContent = lastFeedbackContent;
	}

	public Integer getIsReply() {
		return isReply;
	}

	public void setIsReply(Integer isReply) {
		this.isReply = isReply;
	}

	public String getLastFeedbackDateStart() {
		return lastFeedbackDateStart;
	}

	public void setLastFeedbackDateStart(String lastFeedbackDateStart) {
		this.lastFeedbackDateStart = lastFeedbackDateStart;
	}

	public String getLastFeedbackDateEnd() {
		return lastFeedbackDateEnd;
	}

	public void setLastFeedbackDateEnd(String lastFeedbackDateEnd) {
		this.lastFeedbackDateEnd = lastFeedbackDateEnd;
	}
}
