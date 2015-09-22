package com.gwghk.mis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：帖子或文章统计信息
 * @author Gavin.guo
 * @date   2015年8月6日
 */
@Document
public class TopicStatistical extends BaseModel{
	
	@Id
	private String topicStatisticalId;
	    
	/**
	 * 帖子ID或文章ID(外键)
	 */
	private String topicId;
	
	/**
	 * 回帖类别(1：帖子  2:文章)
	 */
	private Integer type;
	
	/**
	 * 点赞数
	 */
	private Integer praiseCounts;
	
	/**
	 * 回复数
	 */
	private Integer replyCounts;
	
	/**
	 * 举报人数
	 */
	private Integer reportCounts;
	
	/**
	 * 阅读数
	 */
	private Integer readCounts;
	    
	/**
	 * 是否删除(0：删除 1：未删除)
	 */
	private Integer isDeleted;

	public String getTopicStatisticalId() {
		return topicStatisticalId;
	}

	public void setTopicStatisticalId(String topicStatisticalId) {
		this.topicStatisticalId = topicStatisticalId;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPraiseCounts() {
		return praiseCounts;
	}

	public void setPraiseCounts(Integer praiseCounts) {
		this.praiseCounts = praiseCounts;
	}

	public Integer getReplyCounts() {
		return replyCounts;
	}

	public void setReplyCounts(Integer replyCounts) {
		this.replyCounts = replyCounts;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getReportCounts() {
		return reportCounts;
	}

	public void setReportCounts(Integer reportCounts) {
		this.reportCounts = reportCounts;
	}

	public Integer getReadCounts() {
		return readCounts;
	}

	public void setReadCounts(Integer readCounts) {
		this.readCounts = readCounts;
	}
}
