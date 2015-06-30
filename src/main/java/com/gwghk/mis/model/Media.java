package com.gwghk.mis.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：多媒体实体对象
 * @author Alan.wu
 * @date   2015年6月29日
 */
@Document
public class Media extends BaseModel{ 
	/**
	 * Id
	 */
	@Id
	private String id;
	
	/**
     * 栏目id
     */
	@Indexed
	private String categoryId;
	
	/**
     * 状态,0为禁用，1为启用，默认为1
     */
	private Integer status;
	
	/**
	 * 媒体地址路径
	 */
	private String  mediaUrl;
	
	/**
	 * 媒体图片（视频专用字段）
	 */
	private String mediaImgUrl;
	
	/**
	 * 点击媒体链接的路径
	 */
	private String  linkUrl;
	
	/**
	 * 应用平台(数据字典的code，多个则逗号分隔）
	 */
	private String platform;
	
	/**
	 * 发布时间
	 */
	public Date publishStartDate;
	
	/**
	 * 发布时间
	 */
	public Date publishEndDate;
	 /**
     * 媒体详细信息
     */
	private List<MediaDetail> detailList;
	
	/**
	 * 是否删除/有效(1为有效，0为无效）
	 */
	private Integer valid;
	
	/**
	 * 栏目路径名(仅用于输出)
	 */
	private String categoryNamePath;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<MediaDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<MediaDetail> detailList) {
		this.detailList = detailList;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
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

	public String getCategoryNamePath() {
		return categoryNamePath;
	}

	public void setCategoryNamePath(String categoryNamePath) {
		this.categoryNamePath = categoryNamePath;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getMediaImgUrl() {
		return mediaImgUrl;
	}

	public void setMediaImgUrl(String mediaImgUrl) {
		this.mediaImgUrl = mediaImgUrl;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}
	
	
}