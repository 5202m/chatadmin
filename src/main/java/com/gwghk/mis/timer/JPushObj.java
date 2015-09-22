package com.gwghk.mis.timer;

/**
 * 摘要：极光推送对象
 * @author Gavin.guo
 * @date 2015-07-28
 */
public class JPushObj {

	/**
	 * 消息Id
	 */
	private String pushMessageId;
	
	/**
	 * 语言
	 */
	private String lang;
	
	/**
	 * 消息标题
	 */
	private String title;
	
	/**
	 * 通知方式：1、系统通知中心 2、小秘书 3、首次登陆时弹窗
	 */
	private String tipType;
	
	/**
	 * 消息类型(1:自定义 2：公告)
	 */
	private String messageType;
	
	/**
	 * 消息内容
	 */
	private String content;
	
	/**
	 * 外部链接(当消息类型为1时，url有值，则使用外部链接)
	 */
	private String url;
	
	/**
	 * 栏目Id(上下级之间使用#连接)
	 */
	private String fullCategoryId;
	
	/**
	 * 资讯Id(对应栏目下的资讯)
	 */
	private String articleId;
	
	public String getPushMessageId() {
		return pushMessageId;
	}

	public void setPushMessageId(String pushMessageId) {
		this.pushMessageId = pushMessageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getTipType() {
		return tipType;
	}

	public void setTipType(String tipType) {
		this.tipType = tipType;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
}
