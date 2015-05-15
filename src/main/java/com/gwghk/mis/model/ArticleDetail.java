package com.gwghk.mis.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：文章详细信息实体对象
 * @author Alan.wu
 * @date   2015年3月16日
 */
@Document
public class ArticleDetail{ 
	
	/**
	 * 语言(zh、tw、en)
	 */
	@Indexed
	private String lang;
	
	/**
     * 标题
     */
	private String title;
	
	/**
     * 内容
    */
	private String content;
	
	/**
     * 图片
     */
	private String pic;
	
	/**
	 * 简介
	 */
	private String remark;
	
	/**
	 * seo标题
	 */
	private String seoTitle;
	
	/**
	 * seo关键字
	 */
	private String seoKeyword;
	
	/**
	 * seo描述
	 */
	private String seoDescription;
	
	/**
	 * 链接地址
	 */
	private String linkUrl;
	
	
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeyword() {
		return seoKeyword;
	}

	public void setSeoKeyword(String seoKeyword) {
		this.seoKeyword = seoKeyword;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
}