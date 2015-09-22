package com.gwghk.mis.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：帖子主贴信息
 * @author Gavin.guo
 * @date   2015年06月04日
 */
@Document
public class Topic extends BaseModel{

	/**
	 * 主键
	 */
	@Id
	private String topicId;
	
	/**
	 * 会员Id(外键)
	 */
	@Indexed
	private String memberId;
	
	/**
	 * 昵称(查询用)
	 */
	private String nickName;
	
	/**
	 * 手机号(查询用)
	 */
	private String mobilePhone;
	
	/**
	 * 发布时间
	 */
	private Date publishTime;
	
	/**
	 * 发布时间-开始(查询用)
	 */
	private String publishTimeStart;
	
	/**
	 * 发布时间-结束(查询用)
	 */
	private String publishTimeEnd;
	
	/**
	 * 发帖权限(0：正常  1：禁止发帖)
	 */
	private Integer topicAuthority;
	
	/**
	 * 是否推荐帖子( 1:是 0: 否)
	 */
	private Integer isRecommend;
	
	/**
	 * 发帖设备
	 */
	private String device;
	
	/**
	 * 主题分类
	 */
	private String subjectType;
	
	/**
	 * 帖子附加属性
	 */
	private TopicExtend expandAttr;
	
	/**
	 * 主题分类txt(主要用于UI展示)
	 */
	private String subjectTypeTxt;
	
	/**
	 * 信息类别(1：发帖 2：回帖)
	 */
	private Integer infoType;
	
	/**
	 * 信息状态(1：有效 2：无效)
	 */
	private Integer infoStatus;
	
	/**
	 * 发布位置(1：发现-关注  2：解盘-直播)
	 */
	private Integer publishLocation;
	
	/**
	 * 举报人数
	 */
	private Integer reportCounts;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 点赞数
	 */
	private Integer praiseCounts;
	
	/**
	 * 回复数
	 */
	private Integer replyCounts;
	
	/**
	 * 是否置顶(0：否  1：是)
	 */
	private Integer isTop;
	
	/**
	 * 审核结果 (0：无效举报 1：有效举报)
	 */
	private Integer approvalResult;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 是否删除(0：删除 1：未删除)
	 */
	private Integer isDeleted;

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getTopicAuthority() {
		return topicAuthority;
	}

	public void setTopicAuthority(Integer topicAuthority) {
		this.topicAuthority = topicAuthority;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public Integer getInfoType() {
		return infoType;
	}

	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}

	public Integer getInfoStatus() {
		return infoStatus;
	}

	public void setInfoStatus(Integer infoStatus) {
		this.infoStatus = infoStatus;
	}

	public Integer getReportCounts() {
		if(null == reportCounts){
			return 0;
		}
		return reportCounts;
	}

	public void setReportCounts(Integer reportCounts) {
		this.reportCounts = reportCounts;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPraiseCounts() {
		if(null == praiseCounts){
			return 0;
		}
		return praiseCounts;
	}

	public void setPraiseCounts(Integer praiseCounts) {
		this.praiseCounts = praiseCounts;
	}

	public Integer getReplyCounts() {
		if(null == replyCounts){
			return 0;
		}
		return replyCounts;
	}

	public void setReplyCounts(Integer replyCounts) {
		this.replyCounts = replyCounts;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Integer getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(Integer approvalResult) {
		this.approvalResult = approvalResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getPublishLocation() {
		return publishLocation;
	}

	public void setPublishLocation(Integer publishLocation) {
		this.publishLocation = publishLocation;
	}

	public String getSubjectTypeTxt() {
		return subjectTypeTxt;
	}

	public void setSubjectTypeTxt(String subjectTypeTxt) {
		this.subjectTypeTxt = subjectTypeTxt;
	}

	public String getPublishTimeStart() {
		return publishTimeStart;
	}

	public void setPublishTimeStart(String publishTimeStart) {
		this.publishTimeStart = publishTimeStart;
	}

	public String getPublishTimeEnd() {
		return publishTimeEnd;
	}

	public void setPublishTimeEnd(String publishTimeEnd) {
		this.publishTimeEnd = publishTimeEnd;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public TopicExtend getExpandAttr() {
		return expandAttr;
	}

	public void setExpandAttr(TopicExtend expandAttr) {
		this.expandAttr = expandAttr;
	}
}
