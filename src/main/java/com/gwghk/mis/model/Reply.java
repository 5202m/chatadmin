package com.gwghk.mis.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：帖子回复信息
 * @author Gavin.guo
 * @date   2015年06月29日
 */
@Document
public class Reply extends BaseModel{
	/**
	 * 主键
	 */
	@Id
	private String replyId;
	
	/**
	 * 帖子(外键)
	 */
	@Indexed
	private String topicId;
	
	/**
	 * 作者
	 */
	private String authorName;
	
	/**
	 * 类型(1:帖子 2：文章)
	 */
	private Integer type;
	
	/**
	 * 回复内容
	 */
	private String content;
	
	/**
	 * 回复时间
	 */
	private Date replyDate;
	
	/**
	 * 是否删除(0：删除 1：未删除)
	 */
	private Integer isDeleted;
	
	/**
	 * 二级回复列表
	 */
	private List<Reply> replyList;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public List<Reply> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	
	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
