package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：消息推送
 * @author Gavin.guo
 * @date   2015年7月21日
 */
@Document
public class PushMessage extends BaseModel{
	
	/**
	 * 消息推送id
	 */
	@Id
	private String pushMessageId;
	
	/**
	 * 数据Id
	 */
	private String dataid;
	
	/**
	 * 语言
	 */
	private String lang;
	
	/**
	 * 标题
	 */
	@Indexed
	private String title;
	
	/**
	 * 应用平台(数据字典的code，多个则逗号分隔）
	 */
	private String platform;
	
	/**
	 * 通知方式(1:系统通知中心  2:小秘书 3:首次登陆时弹窗  备注：可以选择多个,之间使用#连接)
	 */
	private String tipType;
	
	/**
	 * 消息类型(1:自定义 2：系统通知 3：关注订阅 4：评论提醒) 
	 */
	private Integer messageType;
	
	/**
	 * 消息内容(当消息类型为1,该字段有值)
	 */
	private String content;
	
	/**
	 * 外部url
	 */
	private String url;
	
	/**
	 * 栏目Id
	 */
	private String categoryId;
	
	/**
	 * 完整栏目Id(包括父级Id,父ID与子ID用#连接)
	 */
	private String fullCategoryId;
	
	/**
	 * 文章Id
	 */
	private String articleId;
	
	/**
	 * 发布开始时间
	 */
	private Date publishStartDate;
	
	/**
	 * 发布结束时间
	 */
	private Date publishEndDate;
	
	/**
	 * 推送时间
	 */
	private Date pushDate;
	
	/**
	 * 推送人(如果为'',向所有人推送,如果多个人，用#连接)
	 */
	private String pushMember;
	
	/**
	 * 推送状态(0为未推送 1为待推送  2为发送成功  3为发送失败 4为取消推送)
	 */
	private Integer pushStatus;
	
	/**
	 * 是否有效(1为有效，0为无效）
	 */
	private Integer valid;
	
	/**
	 * 是否删除(0：删除 1：未删除)
	 */
	private Integer isDeleted;
	
	/**
	 * 推送消息后返回的消息Id
	 */
	private Long msgId;

	public String getPushMessageId() {
		return pushMessageId;
	}

	public void setPushMessageId(String pushMessageId) {
		this.pushMessageId = pushMessageId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getTipType() {
		return tipType;
	}

	public void setTipType(String tipType) {
		this.tipType = tipType;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Date getPublishStartDate() {
		return publishStartDate;
	}

	public void setPublishStartDate(Date publishStartDate) {
		this.publishStartDate = publishStartDate;
	}

	public Date getPublishEndDate() {
		return publishEndDate;
	}

	public void setPublishEndDate(Date publishEndDate) {
		this.publishEndDate = publishEndDate;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(Integer pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getFullCategoryId() {
		return fullCategoryId;
	}

	public void setFullCategoryId(String fullCategoryId) {
		this.fullCategoryId = fullCategoryId;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getPushMember() {
		return pushMember;
	}

	public void setPushMember(String pushMember) {
		this.pushMember = pushMember;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public Date getPushDate() {
		return pushDate;
	}

	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}
}
