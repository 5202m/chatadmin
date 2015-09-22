package com.gwghk.mis.model;

import java.util.Date;

/**
 * 摘要：帖子或文章收藏信息
 * @author Gavin.guo
 * @date   2015年08月06日
 */
public class Collect{
	
	/**
	 * 收藏帖子或文章Id
	 */
	private String topicId;
	
	/**
	 * 类型(1:帖子  2:文章)
	 */
	private Integer type;
	
	/**
	 * 收藏时间
	 */
	private Date collectDate;

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

	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}
}
